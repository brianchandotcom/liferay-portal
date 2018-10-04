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

package com.liferay.portal.fabric.netty.client;

import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.petra.process.TerminationProcessException;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.fabric.client.FabricClient;
import com.liferay.portal.fabric.local.agent.LocalFabricAgent;
import com.liferay.portal.fabric.netty.agent.NettyFabricAgentConfig;
import com.liferay.portal.fabric.netty.codec.serialization.AnnotatedObjectDecoder;
import com.liferay.portal.fabric.netty.codec.serialization.AnnotatedObjectEncoder;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileRequestChannelHandler;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileResponseChannelHandler;
import com.liferay.portal.fabric.netty.handlers.NettyChannelAttributes;
import com.liferay.portal.fabric.netty.handlers.NettyFabricWorkerExecutionChannelHandler;
import com.liferay.portal.fabric.netty.repository.NettyRepository;
import com.liferay.portal.fabric.netty.rpc.handlers.NettyRPCChannelHandler;
import com.liferay.portal.fabric.netty.util.NamedThreadFactory;
import com.liferay.portal.fabric.netty.util.NettyUtil;
import com.liferay.portal.fabric.repository.Repository;
import com.liferay.portal.fabric.worker.FabricWorker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

import java.io.IOException;
import java.io.Serializable;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricClient implements FabricClient {

	public NettyFabricClient(
		ProcessExecutor processExecutor,
		NettyFabricClientConfig nettyFabricClientConfig,
		NettyFabricClientShutdownCallback nettyFabricClientShutdownCallback) {

		_processExecutor = processExecutor;
		_nettyFabricClientConfig = nettyFabricClientConfig;
		_nettyFabricClientShutdownCallback = nettyFabricClientShutdownCallback;
	}

	@Override
	public synchronized void connect() {
		if (_channel != null) {
			throw new IllegalStateException(
				"Netty fabric client was already started");
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Starting Netty fabric client using " +
					_nettyFabricClientConfig);
		}

		Runtime runtime = Runtime.getRuntime();

		runtime.addShutdownHook(_shutdownThread);

		_bootstrap = new Bootstrap();

		_bootstrap.channel(NioSocketChannel.class);
		_bootstrap.group(
			new NioEventLoopGroup(
				_nettyFabricClientConfig.getEventLoopGroupThreadCount(),
				new NamedThreadFactory(
					"Netty Fabric Client/NIO Event Loop Group")));
		_bootstrap.handler(new NettyFabricClientChannelInitializer());

		int reconnectCount = _nettyFabricClientConfig.getReconnectCount();

		if (reconnectCount < 0) {
			reconnectCount = Integer.MAX_VALUE;
		}

		_reconnectCounter.set(reconnectCount);

		doConnect();
	}

	@Override
	public synchronized java.util.concurrent.Future<?> disconnect() {
		if (_channel == null) {
			throw new IllegalStateException(
				"Netty fabric client is not started");
		}

		_reconnectCounter.set(0);

		_channel.close();

		EventExecutorGroup eventExecutorGroup = _bootstrap.group();

		Future<?> future = eventExecutorGroup.terminationFuture();

		final DefaultNoticeableFuture<?> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		future.addListener(
			new FutureListener<Object>() {

				@Override
				public void operationComplete(Future<Object> future) {
					defaultNoticeableFuture.run();
				}

			});

		return defaultNoticeableFuture;
	}

	protected EventExecutorGroup createEventExecutorGroup(
		int threadCount, String threadPoolName) {

		EventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(
			threadCount, new NamedThreadFactory(threadPoolName));

		NettyUtil.bindShutdown(
			_bootstrap.group(), eventExecutorGroup,
			_nettyFabricClientConfig.getShutdownQuietPeriod(),
			_nettyFabricClientConfig.getShutdownTimeout());

		return eventExecutorGroup;
	}

	protected void doConnect() {
		ChannelFuture channelFuture = _bootstrap.connect(
			_nettyFabricClientConfig.getNettyFabricServerHost(),
			_nettyFabricClientConfig.getNettyFabricServerPort());

		_channel = channelFuture.channel();

		channelFuture.addListener(new PostConnectChannelFutureListener());
	}

	protected void terminateFabricWorkers(Channel channel) {
		Map<Long, FabricWorker<?>> fabricWorkers =
			NettyChannelAttributes.getFabricWorkers(channel);

		if (fabricWorkers == null) {
			return;
		}

		for (Map.Entry<Long, FabricWorker<?>> entry :
				fabricWorkers.entrySet()) {

			FabricWorker<?> fabricWorker = entry.getValue();

			fabricWorker.write(_runtimeExitProcessCallable);

			NoticeableFuture<?> noticeableFuture =
				fabricWorker.getProcessNoticeableFuture();

			try {
				try {
					noticeableFuture.get(
						_nettyFabricClientConfig.getExecutionTimeout(),
						TimeUnit.MILLISECONDS);
				}
				catch (TimeoutException te) {
					fabricWorker.write(_runtimeHaltProcessCallable);

					noticeableFuture.get(
						_nettyFabricClientConfig.getExecutionTimeout(),
						TimeUnit.MILLISECONDS);
				}
			}
			catch (Throwable t) {
				if (t instanceof ExecutionException) {
					Throwable cause = t.getCause();

					if (cause instanceof TerminationProcessException) {
						if (_log.isWarnEnabled()) {
							TerminationProcessException tpe =
								(TerminationProcessException)cause;

							_log.warn(
								StringBundler.concat(
									"Forcibly terminate fabric worker ",
									entry.getKey(), " with exit code ",
									tpe.getExitCode()));
						}

						continue;
					}
				}

				_log.error(
					"Unable to terminate fabric worker " + entry.getKey(), t);
			}
		}
	}

	protected class NettyFabricClientChannelInitializer
		extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel socketChannel)
			throws IOException {

			Path repositoryPath = _nettyFabricClientConfig.getRepositoryPath();

			Files.createDirectories(repositoryPath);

			Repository<Channel> repository = new NettyRepository(
				repositoryPath,
				_nettyFabricClientConfig.getRepositoryGetFileTimeout());

			ChannelFuture channelFuture = socketChannel.closeFuture();

			channelFuture.addListener(
				new PostDisconnectChannelFutureListener(repository));

			ChannelPipeline channelPipeline = socketChannel.pipeline();

			channelPipeline.addLast(
				AnnotatedObjectEncoder.NAME, AnnotatedObjectEncoder.INSTANCE);
			channelPipeline.addLast(
				AnnotatedObjectDecoder.NAME, new AnnotatedObjectDecoder());

			EventExecutorGroup fileServerEventExecutorGroup =
				createEventExecutorGroup(
					_nettyFabricClientConfig.getFileServerGroupThreadCount(),
					"Netty Fabric Client/File Server Event Executor Group");

			channelPipeline.addLast(
				fileServerEventExecutorGroup, FileRequestChannelHandler.NAME,
				new FileRequestChannelHandler(
					_nettyFabricClientConfig.
						getFileServerFolderCompressionLevel()));
			channelPipeline.addLast(
				new FileResponseChannelHandler(
					repository.getAsyncBroker(), fileServerEventExecutorGroup));

			channelPipeline.addLast(
				createEventExecutorGroup(
					_nettyFabricClientConfig.getRPCGroupThreadCount(),
					"Netty Fabric Client/RPC Event Executor Group"),
				NettyRPCChannelHandler.NAME, NettyRPCChannelHandler.INSTANCE);
			channelPipeline.addLast(
				createEventExecutorGroup(
					_nettyFabricClientConfig.getExecutionGroupThreadCount(),
					"Netty Fabric Client/Execution Event Executor Group"),
				new NettyFabricWorkerExecutionChannelHandler(
					repository, new LocalFabricAgent(_processExecutor),
					_nettyFabricClientConfig.getExecutionTimeout()));
		}

	}

	protected class PostConnectChannelFutureListener
		implements ChannelFutureListener {

		@Override
		public void operationComplete(ChannelFuture channelFuture) {
			if (channelFuture.isSuccess()) {
				Channel channel = channelFuture.channel();

				if (_log.isInfoEnabled()) {
					_log.info("Connected to " + channel.remoteAddress());
				}

				Path repositoryPath =
					_nettyFabricClientConfig.getRepositoryPath();

				ChannelFuture registerChannelFuture = _channel.writeAndFlush(
					new NettyFabricAgentConfig(repositoryPath.toFile()));

				registerChannelFuture.addListener(
					new PostRegisterChannelFutureListener());

				return;
			}

			String serverAddress =
				_nettyFabricClientConfig.getNettyFabricServerHost() + ":" +
					_nettyFabricClientConfig.getNettyFabricServerPort();

			if (channelFuture.isCancelled()) {
				_log.error("Cancelled connecting to " + serverAddress);
			}
			else {
				_log.error(
					"Unable to connect to " + serverAddress,
					channelFuture.cause());
			}
		}

	}

	protected class PostDisconnectChannelFutureListener
		implements ChannelFutureListener {

		@Override
		public void operationComplete(ChannelFuture channelFuture) {
			terminateFabricWorkers(_channel);

			repository.dispose(true);

			EventLoopGroup eventLoopGroup = _bootstrap.group();

			if (_reconnectCounter.getAndDecrement() > 0) {
				eventLoopGroup.schedule(
					new Runnable() {

						@Override
						public void run() {
							doConnect();
						}

					},
					_nettyFabricClientConfig.getReconnectInterval(),
					TimeUnit.MILLISECONDS);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Try to reconnect " +
							_nettyFabricClientConfig.getReconnectInterval() +
								" ms later");
				}
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Shutting down Netty fabric client on " + _channel);
				}

				Future<?> future = eventLoopGroup.shutdownGracefully(
					_nettyFabricClientConfig.getShutdownQuietPeriod(),
					_nettyFabricClientConfig.getShutdownTimeout(),
					TimeUnit.MILLISECONDS);

				future.addListener(new PostShutdownChannelFutureListener());
			}
		}

		protected PostDisconnectChannelFutureListener(
			Repository<Channel> repository) {

			this.repository = repository;
		}

		protected final Repository<Channel> repository;

	}

	protected class PostRegisterChannelFutureListener
		implements ChannelFutureListener {

		@Override
		public void operationComplete(ChannelFuture channelFuture) {
			if (channelFuture.isSuccess()) {
				int reconnectCount =
					_nettyFabricClientConfig.getReconnectCount();

				if (reconnectCount < 0) {
					reconnectCount = Integer.MAX_VALUE;
				}

				_reconnectCounter.set(reconnectCount);

				if (_log.isInfoEnabled()) {
					_log.info("Registered Netty fabric agent on " + _channel);
				}

				return;
			}

			_log.error("Unable to register Netty fabric agent on " + _channel);

			_channel.close();
		}

	}

	protected class PostShutdownChannelFutureListener
		implements FutureListener<Object> {

		@Override
		public void operationComplete(Future<Object> future) {
			_channel = null;
			_bootstrap = null;

			_nettyFabricClientShutdownCallback.shutdown();

			if (_shutdownThread.getState() == Thread.State.NEW) {
				Runtime runtime = Runtime.getRuntime();

				runtime.removeShutdownHook(_shutdownThread);
			}
		}

	}

	private static final int _FABRIC_AGENT_SHUTDOWN_CODE = 211;

	private static final Log _log = LogFactoryUtil.getLog(
		NettyFabricClient.class);

	private static final ProcessCallable<Serializable>
		_runtimeExitProcessCallable = new ProcessCallable<Serializable>() {

			@Override
			public Serializable call() {
				Runtime runtime = Runtime.getRuntime();

				runtime.exit(_FABRIC_AGENT_SHUTDOWN_CODE);

				return null;
			}

			private static final long serialVersionUID = 1L;

		};

	private static final ProcessCallable<Serializable>
		_runtimeHaltProcessCallable = new ProcessCallable<Serializable>() {

			@Override
			public Serializable call() {
				Runtime runtime = Runtime.getRuntime();

				runtime.halt(_FABRIC_AGENT_SHUTDOWN_CODE);

				return null;
			}

			private static final long serialVersionUID = 1L;

		};

	private volatile Bootstrap _bootstrap;
	private volatile Channel _channel;
	private final NettyFabricClientConfig _nettyFabricClientConfig;
	private final NettyFabricClientShutdownCallback
		_nettyFabricClientShutdownCallback;
	private final ProcessExecutor _processExecutor;
	private final AtomicInteger _reconnectCounter = new AtomicInteger();

	private final Thread _shutdownThread = new Thread() {

		@Override
		public void run() {
			Channel channel = _channel;

			if (channel != null) {
				_reconnectCounter.set(0);

				ChannelFuture channelFuture = channel.close();

				channelFuture.syncUninterruptibly();
			}
		}

	};

}