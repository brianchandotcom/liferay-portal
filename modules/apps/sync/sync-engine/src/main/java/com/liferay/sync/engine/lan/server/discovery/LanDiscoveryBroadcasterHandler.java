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

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis Ju
 */
public class LanDiscoveryBroadcasterHandler
	extends SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	public void channelRead0(
			ChannelHandlerContext channelHandlerContext,
			DatagramPacket datagramPacket)
		throws Exception {

		channelHandlerContext.close();
	}

	@Override
	public void exceptionCaught(
		ChannelHandlerContext channelHandlerContext, Throwable cause) {

		_logger.error(cause.getMessage(), cause);

		channelHandlerContext.close();
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LanDiscoveryBroadcasterHandler.class);

}