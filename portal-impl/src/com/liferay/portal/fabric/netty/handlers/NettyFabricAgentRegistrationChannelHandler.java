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

package com.liferay.portal.fabric.netty.handlers;

import com.liferay.portal.fabric.agent.FabricAgentRegistry;
import com.liferay.portal.fabric.netty.agent.NettyFabricAgentConfig;
import com.liferay.portal.fabric.netty.agent.NettyFabricAgentStub;
import com.liferay.portal.fabric.netty.repository.NettyRepository;
import com.liferay.portal.fabric.repository.Repository;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.io.IOException;

import java.net.SocketAddress;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricAgentRegistrationChannelHandler
	extends SimpleChannelInboundHandler<NettyFabricAgentConfig> {

	public NettyFabricAgentRegistrationChannelHandler(
		FabricAgentRegistry fabricAgentRegistry, Path repositoryParentPath,
		EventExecutorGroup eventExecutorGroup, long getFileTimeout,
		long rpcRelayTimeout, long startupTimeout) {

		if (fabricAgentRegistry == null) {
			throw new NullPointerException("Fabric agent registry is null");
		}

		if (repositoryParentPath == null) {
			throw new NullPointerException("Repository parent path is null");
		}

		if (eventExecutorGroup == null) {
			throw new NullPointerException("Event executor group is null");
		}

		_fabricAgentRegistry = fabricAgentRegistry;
		_repositoryParentPath = repositoryParentPath;
		_eventExecutorGroup = eventExecutorGroup;
		_getFileTimeout = getFileTimeout;
		_rpcRelayTimeout = rpcRelayTimeout;
		_startupTimeout = startupTimeout;
	}

	@Override
	public void exceptionCaught(
		ChannelHandlerContext channelHandlerContext, Throwable throwable) {

		final Channel channel = channelHandlerContext.channel();

		_log.error("Closing " + channel + " due to:", throwable);

		ChannelFuture channelFuture = channel.close();

		channelFuture.addListener(
			new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture channelFuture) {
					if (_log.isInfoEnabled()) {
						_log.info(channel + " is closed");
					}
				}

			});
	}

	@Override
	protected void channelRead0(
			ChannelHandlerContext channelHandlerContext,
			NettyFabricAgentConfig nettyFabricAgentConfig)
		throws IOException {

		Channel channel = channelHandlerContext.channel();

		SocketAddress socketAddress = channel.localAddress();

		Path repositoryPath = Paths.get(
			_repositoryParentPath.toString(), socketAddress.toString());

		Files.createDirectories(repositoryPath);

		Repository repository = new NettyRepository(
			repositoryPath, channel, _eventExecutorGroup, _getFileTimeout);

		NettyFabricAgentStub nettyFabricAgentStub =
			new NettyFabricAgentStub(
				channel, repository, nettyFabricAgentConfig.getRepositoryPath(),
				_rpcRelayTimeout, _startupTimeout);

		if (!_fabricAgentRegistry.registerFabricAgent(
				nettyFabricAgentStub,
				new OnRegistration(
					channel, nettyFabricAgentStub, repository))) {

			if (_log.isWarnEnabled()) {
				_log.warn("Rejected duplicated fabric agent on " + channel);
			}

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Registered fabric agent on " + channel);
		}
	}

	protected class OnRegistration implements Runnable {

		public OnRegistration(
			Channel channel, NettyFabricAgentStub nettyFabricAgentStub,
			Repository repository) {

			_channel = channel;
			_nettyFabricAgentStub = nettyFabricAgentStub;
			_repository = repository;
		}

		@Override
		public void run() {
			NettyChannelAttributes.setNettyFabricAgentStub(
				_channel, _nettyFabricAgentStub);

			ChannelFuture channelFuture = _channel.closeFuture();

			channelFuture.addListener(
				new PostDisconnectChannelFutureListener(
					_channel, _nettyFabricAgentStub, _repository));
		}

		private final Channel _channel;
		private final NettyFabricAgentStub _nettyFabricAgentStub;
		private final Repository _repository;

	}

	protected class PostDisconnectChannelFutureListener
		implements ChannelFutureListener {

		public PostDisconnectChannelFutureListener(
			Channel channel, NettyFabricAgentStub nettyFabricAgentStub,
			Repository repository) {

			_channel = channel;
			_nettyFabricAgentStub = nettyFabricAgentStub;
			_repository = repository;
		}

		@Override
		public void operationComplete(ChannelFuture channelFuture) {
			if (_fabricAgentRegistry.unregisterFabricAgent(
					_nettyFabricAgentStub, null)) {

				if (_log.isInfoEnabled()) {
					_log.info("Unregistered fabric agent on " + _channel);
				}
			}
			else if (_log.isWarnEnabled()) {
				_log.warn("Unable to unregister fabric agent on " + _channel);
			}

			_repository.dispose(true);
		}

		private final Channel _channel;
		private final NettyFabricAgentStub _nettyFabricAgentStub;
		private final Repository _repository;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		NettyFabricAgentRegistrationChannelHandler.class);

	private final EventExecutorGroup _eventExecutorGroup;
	private final FabricAgentRegistry _fabricAgentRegistry;
	private final long _getFileTimeout;
	private final Path _repositoryParentPath;
	private final long _rpcRelayTimeout;
	private final long _startupTimeout;

}