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
		long rpcRelayTimeout) {

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
	}

	@Override
	protected void channelRead0(
			ChannelHandlerContext channelHandlerContext,
			NettyFabricAgentConfig nettyFabricAgentConfig)
		throws IOException {

		final Channel channel = channelHandlerContext.channel();

		SocketAddress socketAddress = channel.localAddress();

		Path repositoryPath = Paths.get(
			_repositoryParentPath.toString(), socketAddress.toString());

		Files.createDirectories(repositoryPath);

		final Repository repository = new NettyRepository(
			repositoryPath, channel, _eventExecutorGroup, _getFileTimeout);

		final NettyFabricAgentStub nettyFabricAgentStub =
			new NettyFabricAgentStub(
				channel, repository, nettyFabricAgentConfig.getRepositoryPath(),
				_rpcRelayTimeout);

		if (!_fabricAgentRegistry.registerFabricAgent(nettyFabricAgentStub)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Rejected duplicated fabric agent on " + channel);
			}

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Registered fabric agent on " + channel);
		}

		NettyChannelAttributes.setNettyFabricAgentStub(
			channel, nettyFabricAgentStub);

		ChannelFuture channelFuture = channel.closeFuture();

		channelFuture.addListener(
			new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture channelFuture) {
					if (_fabricAgentRegistry.unregisterFabricAgent(
							nettyFabricAgentStub)) {

						if (_log.isInfoEnabled()) {
							_log.info(
								"Unregistered fabric agent on " + channel);
						}
					}
					else if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to unregister fabric agent on " + channel);
					}

					repository.dispose(true);
				}

			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NettyFabricAgentRegistrationChannelHandler.class);

	private final EventExecutorGroup _eventExecutorGroup;
	private final FabricAgentRegistry _fabricAgentRegistry;
	private final long _getFileTimeout;
	private final Path _repositoryParentPath;
	private final long _rpcRelayTimeout;

}