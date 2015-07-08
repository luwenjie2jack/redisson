/**
 * Copyright 2014 Nikita Koksharov, Nickolay Borbit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redisson.connection;

import io.netty.channel.EventLoopGroup;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import io.netty.util.concurrent.Future;

import org.redisson.async.AsyncOperation;
import org.redisson.async.SyncInterruptedOperation;
import org.redisson.async.SyncOperation;
import org.redisson.client.protocol.RedisCommand;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.pubsub.RedisPubSubAdapter;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author Nikita Koksharov
 *
 */
//TODO ping support
public interface ConnectionManager {

    Future<Void> writeAsyncVoid(String key, RedisCommand<String> command, Object ... params);

    <T, R> Future<R> writeAsync(String key, RedisCommand<T> command, Object ... params);

    <T, R> Future<R> readAsync(String key, RedisCommand<T> command, Object ... params);

    RedisClient createClient(String host, int port, int timeout);

    RedisClient createClient(String host, int port);

    <V> V get(Future<V> future);

    <V, R> R read(String key, SyncOperation<V, R> operation);

    <V, R> R write(String key, SyncInterruptedOperation<V, R> operation) throws InterruptedException;

    <V, R> R write(String key, SyncOperation<V, R> operation);

    <V, R> R write(String key, AsyncOperation<V, R> asyncOperation);

    <V, T> Future<T> writeAllAsync(AsyncOperation<V, T> asyncOperation);

    <V, T> T read(String key, AsyncOperation<V, T> asyncOperation);

    <V, T> Future<T> readAsync(String key, AsyncOperation<V, T> asyncOperation);

    <V, T> Future<T> readAsync(AsyncOperation<V, T> asyncOperation);

    <V, T> Future<T> writeAsync(String key, AsyncOperation<V, T> asyncOperation);

    <V, T> Future<T> writeAsync(AsyncOperation<V, T> asyncOperation);

    <K, V> RedisConnection<K, V> connectionReadOp(int slot);

    PubSubConnectionEntry getEntry(String channelName);

    <K, V> PubSubConnectionEntry subscribe(String channelName);

    <K, V> PubSubConnectionEntry psubscribe(String pattern);

    <K, V> PubSubConnectionEntry subscribe(RedisPubSubAdapter<V> listener, String channelName);

    Future unsubscribe(String channelName);

    Future punsubscribe(String channelName);

    void releaseRead(int slot, RedisConnection сonnection);

    void shutdown();

    EventLoopGroup getGroup();

    Timeout newTimeout(TimerTask task, long delay, TimeUnit unit);
}
