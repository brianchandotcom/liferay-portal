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

import com.liferay.portal.fabric.client.FabricClient;
import com.liferay.portal.fabric.netty.agent.NettyFabricAgentConfig;
import com.liferay.portal.fabric.netty.codec.serialization.AnnotatedObjectDecoder;
import com.liferay.portal.fabric.netty.codec.serialization.AnnotatedObjectEncoder;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileRequestChannelHandler;
import com.liferay.portal.fabric.netty.handlers.NettyFabricWorkerExecutionChannelHandler;
import com.liferay.portal.fabric.netty.repository.NettyRepository;
import com.liferay.portal.fabric.netty.rpc.handlers.NettyRPCChannelHandler;
import com.liferay.portal.fabric.repository.Repository;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.util.NamedThreadFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
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
				"Netty fabric client has already started");
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Starting netty fabric client using : " +
					_nettyFabricClientConfig);
		}

		Runtime runtime = Runtime.getRuntime();

		runtime.addShutdownHook(_shutdownHook);

		_bootstrap = new Bootstrap();

		_bootstrap.group(
			new NioEventLoopGroup(
				_nettyFabricClientConfig.getEventLoopGroupThreadCount(),
				new NamedThreadFactory(
					"Netty Fabric Client/NIO Event Loop Group",
					Thread.NORM_PRIORITY, null)));
		_bootstrap.channel(NioSocketChannel.class);
		_bootstrap.handler(new NettyFabricClientChannelInitializer());

		_bootstrap.attr(
			_executionEventExecutorGroupAttributeKey,
			new DefaultEventExecutorGroup(
				_nettyFabricClientConfig.getExecutionGroupThreadCount(),
				new NamedThreadFactory(
					"Netty Fabric Client/Execution Event Executor Group",
					Thread.NORM_PRIORITY, null)));
		_bootstrap.attr(
			_fileServerEventExecutorGroupAttributeKey,
			new DefaultEventExecutorGroup(
				_nettyFabricClientConfig.getFileServerGroupThreadCount(),
				new NamedThreadFactory(
					"Netty Fabric Client/File Server Event Executor Group",
					Thread.NORM_PRIORITY, null)));
		_bootstrap.attr(
			_rpcEventExecutorGroupAttributeKey,
			new DefaultEventExecutorGroup(
				_nettyFabricClientConfig.getRPCGroupThreadCount(),
				new NamedThreadFactory(
					"Netty Fabric Client/RPC Event Executor Group",
					Thread.NORM_PRIORITY, null)));

		_reconnectCounter.set(_nettyFabricClientConfig.getReconnectCount());

		doConnect();
	}

	@Override
	public synchronized void disconnect() throws InterruptedException {
		_reconnectCounter.set(0);

		doDisconnect();
	}

	protected void disposeRepository(Channel channel) {
		Attribute<Repository> attribute = channel.attr(_repositoryAttributeKey);

		Repository repository = attribute.getAndRemove();

		if (repository != null) {
			repository.dispose(true);
		}
	}

	protected void doConnect() {
		ChannelFuture channelFuture = _bootstrap.connect(
			_nettyFabricClientConfig.getNettyFabricServerHost(),
			_nettyFabricClientConfig.getNettyFabricServerPort());

		_channel = channelFuture.channel();

		channelFuture.addListener(new PostConnectChannelFutureListener());
	}

	protected void doDisconnect() throws InterruptedException {
		if (_channel == null) {
			throw new IllegalStateException(
				"Netty fabric client has not started");
		}

		try {
			ChannelFuture channelFuture = _channel.close();

			channelFuture.sync();
		}
		finally {
			disposeRepository(_channel);

			EventLoop eventLoop = _channel.eventLoop();

			EventLoopGroup eventLoopGroup = eventLoop.parent();

			if (_reconnectCounter.getAndDecrement() > 0) {
				eventLoopGroup.schedule(
					new Callable<Void>() {

						@Override
						public Void call() {
							doConnect();

							return null;
						}

					},
					_nettyFabricClientConfig.getReconnectInterval(),
					TimeUnit.MILLISECONDS);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Try to reconnect " +
							_nettyFabricClientConfig.getReconnectInterval() +
								"ms later");
				}
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Shutting down netty fabric client on " + _channel);
				}

				try {
					eventLoopGroup.shutdownGracefully();

					shutdownEventExecutorGroup(
						_channel, _executionEventExecutorGroupAttributeKey);
					shutdownEventExecutorGroup(
						_channel, _fileServerEventExecutorGroupAttributeKey);
					shutdownEventExecutorGroup(
						_channel, _rpcEventExecutorGroupAttributeKey);

					_channel = null;
					_bootstrap = null;
				}
				finally {
					_nettyFabricClientShutdownCallback.shutdown();

					Runtime runtime = Runtime.getRuntime();

					runtime.removeShutdownHook(_shutdownHook);
				}
			}
		}
	}

	protected EventExecutorGroup getEventExecutorGroup(
		Channel channel, AttributeKey<EventExecutorGroup> attributeKey) {

		Attribute<EventExecutorGroup> attribute = channel.attr(attributeKey);

		return attribute.get();
	}

	protected Repository getRepository(Channel channel) throws IOException {
		Attribute<Repository> attribute = channel.attr(_repositoryAttributeKey);

		Repository repository = attribute.get();

		if (repository == null) {
			Path repositoryPath = _nettyFabricClientConfig.getRepositoryPath();

			Files.createDirectories(repositoryPath);

			repository = new NettyRepository(
				repositoryPath, channel,
				getEventExecutorGroup(
					_channel, _fileServerEventExecutorGroupAttributeKey),
				_nettyFabricClientConfig.getRepositoryGetFileTimeout());

			Repository previousRepository = attribute.setIfAbsent(repository);

			if (previousRepository != null) {
				repository.dispose(true);

				repository = previousRepository;
			}
		}

		return repository;
	}

	protected void registerNettyFabricAgent() throws IOException {
		ChannelPipeline channelPipeline = _channel.pipeline();

		Repository repository = getRepository(_channel);

		channelPipeline.addLast(
			getEventExecutorGroup(
				_channel, _executionEventExecutorGroupAttributeKey),
			new NettyFabricWorkerExecutionChannelHandler(
				repository, _processExecutor,
				_nettyFabricClientConfig.getExecutionTimeout()));

		Path repositoryPath = repository.getRepositoryPath();

		ChannelFuture channelFuture = _channel.writeAndFlush(
			new NettyFabricAgentConfig(repositoryPath.toFile()));

		channelFuture.addListener(new PostRegisterChannelFutureListener());
	}

	protected void shutdownEventExecutorGroup(
		Channel channel, AttributeKey<EventExecutorGroup> attributeKey) {

		Attribute<EventExecutorGroup> attribute = channel.attr(attributeKey);

		EventExecutorGroup eventExecutorGroup = attribute.getAndRemove();

		if (eventExecutorGroup != null) {
			eventExecutorGroup.shutdownGracefully();
		}
	}

	protected class NettyFabricClientChannelInitializer
		extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel socketChannel) {
			ChannelPipeline channelPipeline = socketChannel.pipeline();

			channelPipeline.addLast(
				AnnotatedObjectEncoder.NAME, AnnotatedObjectEncoder.INSTANCE);
			channelPipeline.addLast(
				AnnotatedObjectDecoder.NAME, new AnnotatedObjectDecoder());
			channelPipeline.addLast(
				getEventExecutorGroup(
					socketChannel, _fileServerEventExecutorGroupAttributeKey),
				FileRequestChannelHandler.NAME,
				new FileRequestChannelHandler(
					_nettyFabricClientConfig.getFileServerFolderCompressionLevel()));
			channelPipeline.addLast(
				getEventExecutorGroup(
					socketChannel, _rpcEventExecutorGroupAttributeKey),
				NettyRPCChannelHandler.NAME, NettyRPCChannelHandler.INSTANCE);
		}

	}

	protected class PostConnectChannelFutureListener
		implements ChannelFutureListener {

		@Override
		public void operationComplete(ChannelFuture channelFuture)
			throws Exception {

			Channel channel = channelFuture.channel();

			if (channelFuture.isSuccess()) {
				if (_log.isInfoEnabled()) {
					_log.info("Connected to " + channel.remoteAddress());
				}

				registerNettyFabricAgent();

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

			doDisconnect();
		}

	}

	protected class PostDisconnectChannelFutureListener
		implements ChannelFutureListener {

		@Override
		public void operationComplete(ChannelFuture channelFuture)
			throws InterruptedException {

			Channel channel = channelFuture.channel();

			if (_log.isInfoEnabled()) {
				_log.info("Disconnected from " + channel.remoteAddress());
			}

			doDisconnect();
		}

	}

	protected class PostRegisterChannelFutureListener
		implements ChannelFutureListener {

		@Override
		public void operationComplete(ChannelFuture channelFuture)
			throws InterruptedException {

			if (channelFuture.isSuccess()) {
				_reconnectCounter.set(
					_nettyFabricClientConfig.getReconnectCount());

				if (_log.isInfoEnabled()) {
					_log.info("Registered fabric agent on " + _channel);
				}

				channelFuture = _channel.closeFuture();

				channelFuture.addListener(
					new PostDisconnectChannelFutureListener());

				return;
			}

			_log.error("Unable to register fabric agent on " + _channel);

			doDisconnect();
		}

	}

	private static final Log _log = LogFactoryUtil.getLog(
		NettyFabricClient.class);

	private static final AttributeKey<EventExecutorGroup>
		_executionEventExecutorGroupAttributeKey = AttributeKey.valueOf(
			"ExecutionEventExecutorGroup");
	private static final AttributeKey<EventExecutorGroup>
		_fileServerEventExecutorGroupAttributeKey = AttributeKey.valueOf(
			"FileServerEventExecutorGroup");
	private static final AttributeKey<Repository> _repositoryAttributeKey =
		AttributeKey.valueOf("Repository");
	private static final AttributeKey<EventExecutorGroup>
		_rpcEventExecutorGroupAttributeKey = AttributeKey.valueOf(
			"RPCEventExecutorGroup");

	private Bootstrap _bootstrap;
	private volatile Channel _channel;
	private final NettyFabricClientConfig _nettyFabricClientConfig;
	private final NettyFabricClientShutdownCallback
		_nettyFabricClientShutdownCallback;
	private final ProcessExecutor _processExecutor;
	private final AtomicInteger _reconnectCounter = new AtomicInteger();

	private final Thread _shutdownHook = new Thread() {

		@Override
		public void run() {
			Channel channel = _channel;

			if (channel != null) {
				disposeRepository(channel);
			}
		}

	};

}