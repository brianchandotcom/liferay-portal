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

import com.liferay.portal.fabric.agent.FabricAgentRegistry;
import com.liferay.portal.fabric.netty.codec.serialization.AnnotatedObjectDecoder;
import com.liferay.portal.fabric.netty.codec.serialization.AnnotatedObjectEncoder;
import com.liferay.portal.fabric.netty.fileserver.FileHelperUtil;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileRequestChannelHandler;
import com.liferay.portal.fabric.netty.handlers.NettyFabricAgentRegistrationChannelHandler;
import com.liferay.portal.fabric.netty.rpc.handlers.NettyRPCChannelHandler;
import com.liferay.portal.fabric.server.FabricServer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.NamedThreadFactory;

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
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.file.Files;

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

		EventLoopGroup workerGroup = new NioEventLoopGroup(
			_nettyFabricServerConfig.getWorkerGroupThreadCount(),
			new NamedThreadFactory(
				"Netty Fabric Server/Worker Event Loop Group",
				Thread.NORM_PRIORITY, null));

		serverBootstrap.group(
			new NioEventLoopGroup(
				_nettyFabricServerConfig.getBossGroupThreadCount(),
				new NamedThreadFactory(
					"Netty Fabric Server/Boss Event Loop Group",
					Thread.NORM_PRIORITY, null)),
			workerGroup);

		serverBootstrap.attr(
			_fileServerEventExecutorGroupAttributeKey,
			new DefaultEventExecutorGroup(
				_nettyFabricServerConfig.getFileServerGroupThreadCount(),
				new NamedThreadFactory(
					"Netty Fabric Server/File Server Event Executor Group",
					Thread.NORM_PRIORITY, null)));
		serverBootstrap.attr(
			_registrationEventExecutorGroupAttributeKey,
			new DefaultEventExecutorGroup(
				_nettyFabricServerConfig.getRegistrationGroupThreadCount(),
				new NamedThreadFactory(
					"Netty Fabric Server/Registration Event Executor Group",
					Thread.NORM_PRIORITY, null)));
		serverBootstrap.attr(
			_rpcEventExecutorGroupAttributeKey,
			new DefaultEventExecutorGroup(
				_nettyFabricServerConfig.getRPCGroupThreadCount(),
				new NamedThreadFactory(
					"Netty Fabric Server/RPC Event Executor Group",
					Thread.NORM_PRIORITY, null)));
		serverBootstrap.attr(_workerEventLoopGroupAttributeKey, workerGroup);

		ChannelFuture channelFuture = serverBootstrap.bind(
			_nettyFabricServerConfig.getNettyFabricServerHost(),
			_nettyFabricServerConfig.getNettyFabricServerPort());

		_serverChannel = channelFuture.channel();

		channelFuture.addListener(new PostBindChannelFutureListener());

		channelFuture.sync();
	}

	@Override
	public synchronized void stop() throws InterruptedException {
		if (_serverChannel == null) {
			throw new IllegalStateException(
				"Netty fabric server is not started");
		}

		try {
			ChannelFuture channelFuture = _serverChannel.close();

			channelFuture.sync();
		}
		finally {
			EventLoop eventLoop = _serverChannel.eventLoop();

			EventLoopGroup bossGroup = eventLoop.parent();

			bossGroup.shutdownGracefully();

			shutdownEventExecutorGroup(
				_serverChannel, _workerEventLoopGroupAttributeKey);
			shutdownEventExecutorGroup(
				_serverChannel, _fileServerEventExecutorGroupAttributeKey);
			shutdownEventExecutorGroup(
				_serverChannel, _registrationEventExecutorGroupAttributeKey);
			shutdownEventExecutorGroup(
				_serverChannel, _rpcEventExecutorGroupAttributeKey);

			FileHelperUtil.delete(
				_nettyFabricServerConfig.getRepositoryParentPath());

			_serverChannel = null;
		}
	}

	protected EventExecutorGroup getEventExecutorGroup(
		Channel channel, AttributeKey<EventExecutorGroup> attributeKey) {

		Attribute<EventExecutorGroup> attribute = channel.attr(attributeKey);

		return attribute.get();
	}

	protected void shutdownEventExecutorGroup(
		Channel channel,
		AttributeKey<? extends EventExecutorGroup> attributeKey) {

		Attribute<? extends EventExecutorGroup> attribute = channel.attr(
			attributeKey);

		EventExecutorGroup eventExecutorGroup = attribute.getAndRemove();

		if (eventExecutorGroup != null) {
			eventExecutorGroup.shutdownGracefully();
		}
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
				getEventExecutorGroup(
					_serverChannel, _rpcEventExecutorGroupAttributeKey),
				NettyRPCChannelHandler.NAME, NettyRPCChannelHandler.INSTANCE);
			channelPipeline.addLast(
				getEventExecutorGroup(
					_serverChannel, _fileServerEventExecutorGroupAttributeKey),
				FileRequestChannelHandler.NAME,
				new FileRequestChannelHandler(
					_nettyFabricServerConfig.
						getFileServerFolderCompressionLevel()));
			channelPipeline.addLast(
				getEventExecutorGroup(
					_serverChannel,
					_registrationEventExecutorGroupAttributeKey),
				new NettyFabricAgentRegistrationChannelHandler(
					_fabricAgentRegistry,
					_nettyFabricServerConfig.getRepositoryParentPath(),
					getEventExecutorGroup(
						_serverChannel,
						_fileServerEventExecutorGroupAttributeKey),
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

	private static final Log _log = LogFactoryUtil.getLog(
		NettyFabricServer.class);

	private static final AttributeKey<EventExecutorGroup>
		_fileServerEventExecutorGroupAttributeKey = AttributeKey.valueOf(
			"FileServerEventExecutorGroup");
	private static final AttributeKey<EventExecutorGroup>
		_registrationEventExecutorGroupAttributeKey = AttributeKey.valueOf(
			"RegistrationEventExecutorGroup");
	private static final AttributeKey<EventExecutorGroup>
		_rpcEventExecutorGroupAttributeKey = AttributeKey.valueOf(
			"RPCEventExecutorGroup");
	private static final AttributeKey<EventLoopGroup>
		_workerEventLoopGroupAttributeKey = AttributeKey.valueOf(
			"WorkerEventLoopGroup");

	private final FabricAgentRegistry _fabricAgentRegistry;
	private final NettyFabricServerConfig _nettyFabricServerConfig;
	private Channel _serverChannel;

}