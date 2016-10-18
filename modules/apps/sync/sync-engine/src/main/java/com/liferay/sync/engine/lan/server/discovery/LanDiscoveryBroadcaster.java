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

package com.liferay.sync.engine.lan.server.discovery;

import com.liferay.sync.engine.lan.util.LanClientUtil;
import com.liferay.sync.engine.util.JSONUtil;
import com.liferay.sync.engine.util.PropsValues;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * @author Dennis Ju
 */
public class LanDiscoveryBroadcaster {

	public void broadcast(int port) throws Exception {
		if ((_channel == null) || !_channel.isActive()) {
			_initialize();
		}

		byte[] bytes = JSONUtil.writeValueAsBytes(
			LanClientUtil.createSyncLanClient(port));

		DatagramPacket datagramPacket = new DatagramPacket(
			Unpooled.copiedBuffer(bytes),
			new InetSocketAddress(
				"255.255.255.255", PropsValues.SYNC_LAN_SERVER_PORT));

		ChannelFuture channelFuture = _channel.writeAndFlush(datagramPacket);

		channelFuture.sync();
	}

	public void shutdown() {
		if (_eventLoopGroup != null) {
			_eventLoopGroup.shutdownGracefully();
		}
	}

	private void _initialize() throws Exception {
		_eventLoopGroup = new NioEventLoopGroup();

		Bootstrap bootstrap = new Bootstrap();

		bootstrap.channel(NioDatagramChannel.class);
		bootstrap.group(_eventLoopGroup);
		bootstrap.handler(new LanDiscoveryBroadcasterHandler());
		bootstrap.option(ChannelOption.SO_BROADCAST, true);

		ChannelFuture channelFuture = bootstrap.bind(0);

		try {
			channelFuture = channelFuture.sync();

			_channel = channelFuture.channel();
		}
		catch (InterruptedException ie) {
			_eventLoopGroup.shutdownGracefully();

			throw ie;
		}
	}

	private Channel _channel;
	private EventLoopGroup _eventLoopGroup;

}