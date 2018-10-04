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

package com.liferay.portal.fabric.netty.server;

import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.fabric.agent.FabricAgentRegistry;
import com.liferay.portal.fabric.netty.codec.serialization.AnnotatedObjectDecoder;
import com.liferay.portal.fabric.netty.codec.serialization.AnnotatedObjectEncoder;
import com.liferay.portal.fabric.netty.fileserver.FileHelperUtil;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileRequestChannelHandler;
import com.liferay.portal.fabric.netty.handlers.NettyFabricAgentRegistrationChannelHandler;
import com.liferay.portal.fabric.netty.rpc.handlers.NettyRPCChannelHandler;
import com.liferay.portal.fabric.netty.util.NamedThreadFactory;
import com.liferay.portal.fabric.netty.util.NettyUtil;
import com.liferay.portal.fabric.server.FabricServer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ThreadDeathWatcher;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

import java.nio.file.Files;

import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricServer implements FabricServer {

	public NettyFabricServer(
		FabricAgentRegistry fabricAgentRegistry,
		NettyFabricServerConfig nettyFabricServerConfig) {

		_fabricAgentRegistry = fabricAgentRegistry;
		_nettyFabricServerConfig = nettyFabricServerConfig;
	}

	@Override
	public synchronized void start() throws Exception {
		if (_serverChannel != null) {
			throw new IllegalStateException(
				"Netty fabric server was already started");
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Starting Netty fabric server using " +
					_nettyFabricServerConfig);
		}

		Files.createDirectories(
			_nettyFabricServerConfig.getRepositoryParentPath());

		ServerBootstrap serverBootstrap = new ServerBootstrap();

		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
		serverBootstrap.childHandler(new ChildChannelInitializer());

		EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(
			_nettyFabricServerConfig.getBossGroupThreadCount(),
			new NamedThreadFactory(
				"Netty Fabric Server/Boss Event Loop Group"));

		EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup(
			_nettyFabricServerConfig.getWorkerGroupThreadCount(),
			new NamedThreadFactory(
				"Netty Fabric Server/Worker Event Loop Group"));

		NettyUtil.bindShutdown(
			bossEventLoopGroup, workerEventLoopGroup,
			_nettyFabricServerConfig.getShutdownQuietPeriod(),
			_nettyFabricServerConfig.getShutdownTimeout());

		serverBootstrap.group(bossEventLoopGroup, workerEventLoopGroup);

		ChannelFuture channelFuture = serverBootstrap.bind(
			_nettyFabricServerConfig.getNettyFabricServerHost(),
			_nettyFabricServerConfig.getNettyFabricServerPort());

		_serverChannel = channelFuture.channel();

		channelFuture.addListener(new PostBindChannelFutureListener());

		channelFuture.sync();
	}

	@Override
	public synchronized java.util.concurrent.Future<?> stop()
		throws InterruptedException {

		if (_serverChannel == null) {
			throw new IllegalStateException(
				"Netty fabric server is not started");
		}

		EventLoop eventLoop = _serverChannel.eventLoop();

		EventLoopGroup bossEventLoopGroup = eventLoop.parent();

		DefaultNoticeableFuture<?> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		try {
			ChannelFuture channelFuture = _serverChannel.close();

			channelFuture.sync();
		}
		finally {
			Future<?> future = bossEventLoopGroup.shutdownGracefully(
				_nettyFabricServerConfig.getShutdownQuietPeriod(),
				_nettyFabricServerConfig.getShutdownTimeout(),
				TimeUnit.MILLISECONDS);

			future.addListener(
				new PostShutdownChannelListener(defaultNoticeableFuture));
		}

		return defaultNoticeableFuture;
	}

	protected EventExecutorGroup createEventExecutorGroup(
		int threadCount, String threadPoolName) {

		EventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(
			threadCount, new NamedThreadFactory(threadPoolName));

		EventLoop eventLoop = _serverChannel.eventLoop();

		NettyUtil.bindShutdown(
			eventLoop.parent(), eventExecutorGroup,
			_nettyFabricServerConfig.getShutdownQuietPeriod(),
			_nettyFabricServerConfig.getShutdownTimeout());

		return eventExecutorGroup;
	}

	protected class ChildChannelInitializer
		extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel socketChannel) {
			ChannelPipeline channelPipeline = socketChannel.pipeline();

			channelPipeline.addLast(
				AnnotatedObjectEncoder.NAME, AnnotatedObjectEncoder.INSTANCE);
			channelPipeline.addLast(
				AnnotatedObjectDecoder.NAME, new AnnotatedObjectDecoder());
			channelPipeline.addLast(
				createEventExecutorGroup(
					_nettyFabricServerConfig.getRPCGroupThreadCount(),
					"Netty Fabric Server/RPC Event Executor Group"),
				NettyRPCChannelHandler.NAME, NettyRPCChannelHandler.INSTANCE);

			EventExecutorGroup fileServerEventExecutorGroup =
				createEventExecutorGroup(
					_nettyFabricServerConfig.getFileServerGroupThreadCount(),
					"Netty Fabric Server/File Server Event Executor Group");

			channelPipeline.addLast(
				fileServerEventExecutorGroup, FileRequestChannelHandler.NAME,
				new FileRequestChannelHandler(
					_nettyFabricServerConfig.
						getFileServerFolderCompressionLevel()));
			channelPipeline.addLast(
				createEventExecutorGroup(
					_nettyFabricServerConfig.getRegistrationGroupThreadCount(),
					"Netty Fabric Server/Registration Event Executor Group"),
				new NettyFabricAgentRegistrationChannelHandler(
					_fabricAgentRegistry,
					_nettyFabricServerConfig.getRepositoryParentPath(),
					fileServerEventExecutorGroup,
					_nettyFabricServerConfig.getRepositoryGetFileTimeout(),
					_nettyFabricServerConfig.getRPCRelayTimeout(),
					_nettyFabricServerConfig.getWorkerStartupTimeout()));
		}

	}

	protected class PostBindChannelFutureListener
		implements ChannelFutureListener {

		@Override
		public void operationComplete(ChannelFuture channelFuture)
			throws InterruptedException {

			Channel channel = channelFuture.channel();

			if (channelFuture.isSuccess()) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Started Netty fabric server on " +
							channel.localAddress());
				}

				return;
			}

			String serverAddress =
				_nettyFabricServerConfig.getNettyFabricServerHost() + ":" +
					_nettyFabricServerConfig.getNettyFabricServerPort();

			if (channelFuture.isCancelled()) {
				_log.error(
					"Cancelled starting Netty fabric server on " +
						serverAddress);
			}
			else {
				_log.error(
					"Unable to start Netty fabric server on " + serverAddress,
					channelFuture.cause());
			}

			stop();
		}

	}

	protected class PostShutdownChannelListener
		implements FutureListener<Object> {

		@Override
		public void operationComplete(Future<Object> future)
			throws InterruptedException {

			FileHelperUtil.delete(
				_nettyFabricServerConfig.getRepositoryParentPath());

			_serverChannel = null;

			if (!ThreadDeathWatcher.awaitInactivity(
					_nettyFabricServerConfig.getShutdownTimeout(),
					TimeUnit.MILLISECONDS)) {

				_log.error("Unable to stop thread death watcher");
			}

			runnable.run();
		}

		protected PostShutdownChannelListener(Runnable runnable) {
			this.runnable = runnable;
		}

		protected final Runnable runnable;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		NettyFabricServer.class);

	private final FabricAgentRegistry _fabricAgentRegistry;
	private final NettyFabricServerConfig _nettyFabricServerConfig;
	private volatile Channel _serverChannel;

}