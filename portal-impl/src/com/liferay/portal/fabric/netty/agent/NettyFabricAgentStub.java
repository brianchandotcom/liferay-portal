/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.fabric.netty.agent;

import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.concurrent.FutureListener;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.reflect.ObjectGraphUtil;
import com.liferay.portal.fabric.FabricPathMappingVisitor;
import com.liferay.portal.fabric.InputResource;
import com.liferay.portal.fabric.OutputResource;
import com.liferay.portal.fabric.agent.FabricAgent;
import com.liferay.portal.fabric.netty.worker.NettyFabricWorkerConfig;
import com.liferay.portal.fabric.netty.worker.NettyFabricWorkerStub;
import com.liferay.portal.fabric.repository.Repository;
import com.liferay.portal.fabric.status.FabricStatus;
import com.liferay.portal.fabric.status.RemoteFabricStatus;
import com.liferay.portal.fabric.worker.FabricWorker;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.io.Serializable;

import java.nio.file.Path;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricAgentStub implements FabricAgent {

	public NettyFabricAgentStub(
		Channel channel, Repository<Channel> repository,
		Path remoteRepositoryPath, long rpcRelayTimeout, long startupTimeout) {

		if (channel == null) {
			throw new NullPointerException("Channel is null");
		}

		if (repository == null) {
			throw new NullPointerException("Repository is null");
		}

		if (remoteRepositoryPath == null) {
			throw new NullPointerException("Remote repository path is null");
		}

		_channel = channel;
		_repository = repository;
		_remoteRepositoryPath = remoteRepositoryPath;
		_rpcRelayTimeout = rpcRelayTimeout;
		_startupTimeout = startupTimeout;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof NettyFabricAgentStub)) {
			return false;
		}

		NettyFabricAgentStub nettyFabricAgentStub = (NettyFabricAgentStub)obj;

		if (_channel.equals(nettyFabricAgentStub._channel)) {
			return true;
		}

		return false;
	}

	@Override
	public <T extends Serializable> FabricWorker<T> execute(
		ProcessConfig processConfig, ProcessCallable<T> processCallable) {

		final long id = _idGenerator.getAndIncrement();

		FabricPathMappingVisitor fabricPathMappingVisitor =
			new FabricPathMappingVisitor(
				OutputResource.class, _remoteRepositoryPath, true);

		ObjectGraphUtil.walkObjectGraph(
			processCallable, fabricPathMappingVisitor);

		NettyFabricWorkerStub<T> nettyFabricWorkerStub =
			new NettyFabricWorkerStub<>(
				id, _channel, _repository,
				fabricPathMappingVisitor.getPathMap(), _rpcRelayTimeout);

		final DefaultNoticeableFuture<Object> startupNoticeableFuture =
			new DefaultNoticeableFuture<>();

		_startupNoticeableFutures.put(id, startupNoticeableFuture);

		startupNoticeableFuture.addFutureListener(
			new FutureListener<Object>() {

				@Override
				public void complete(Future<Object> future) {
					_startupNoticeableFutures.remove(id);
				}

			});

		fabricPathMappingVisitor = new FabricPathMappingVisitor(
			InputResource.class, _remoteRepositoryPath);

		ObjectGraphUtil.walkObjectGraph(
			processCallable, fabricPathMappingVisitor);

		ChannelFuture channelFuture = _channel.writeAndFlush(
			new NettyFabricWorkerConfig<T>(
				id, processConfig, processCallable,
				fabricPathMappingVisitor.getPathMap()));

		channelFuture.addListener(
			new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture channelFuture) {
					if (channelFuture.isSuccess()) {
						return;
					}

					if (channelFuture.isCancelled()) {
						startupNoticeableFuture.cancel(true);

						return;
					}

					startupNoticeableFuture.setException(channelFuture.cause());
				}

			});

		final ChannelFutureListener channelFutureListener =
			new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture channelFuture) {
					startupNoticeableFuture.cancel(true);
				}

			};

		final ChannelFuture closeChannelFuture = _channel.closeFuture();

		closeChannelFuture.addListener(channelFutureListener);

		startupNoticeableFuture.addFutureListener(
			new FutureListener<Object>() {

				@Override
				public void complete(Future<Object> future) {
					closeChannelFuture.removeListener(channelFutureListener);
				}

			});

		try {
			startupNoticeableFuture.get(_startupTimeout, TimeUnit.MILLISECONDS);

			_nettyFabricWorkerStubs.put(id, nettyFabricWorkerStub);
		}
		catch (CancellationException ce) {
			nettyFabricWorkerStub.setCancel();
		}
		catch (Throwable t) {
			if (t instanceof ExecutionException) {
				t = t.getCause();
			}

			nettyFabricWorkerStub.setException(t);
		}

		return nettyFabricWorkerStub;
	}

	public void finishStartup(long id) {
		DefaultNoticeableFuture<?> startupNoticeabeFuture =
			_startupNoticeableFutures.remove(id);

		if (startupNoticeabeFuture != null) {
			startupNoticeabeFuture.run();
		}
	}

	@Override
	public FabricStatus getFabricStatus() {
		return new RemoteFabricStatus(
			new NettyFabricAgentProcessCallableExecutor(_channel));
	}

	@Override
	public Collection<? extends FabricWorker<?>> getFabricWorkers() {
		return Collections.unmodifiableCollection(
			_nettyFabricWorkerStubs.values());
	}

	@Override
	public int hashCode() {
		return _channel.hashCode();
	}

	public NettyFabricWorkerStub<?> takeNettyStubFabricWorker(long id) {
		return _nettyFabricWorkerStubs.remove(id);
	}

	private final Channel _channel;
	private final AtomicLong _idGenerator = new AtomicLong();
	private final Map<Long, NettyFabricWorkerStub<?>> _nettyFabricWorkerStubs =
		new ConcurrentHashMap<>();
	private final Path _remoteRepositoryPath;
	private final Repository<Channel> _repository;
	private final long _rpcRelayTimeout;
	private final Map<Long, DefaultNoticeableFuture<?>>
		_startupNoticeableFutures = new ConcurrentHashMap<>();
	private final long _startupTimeout;

}