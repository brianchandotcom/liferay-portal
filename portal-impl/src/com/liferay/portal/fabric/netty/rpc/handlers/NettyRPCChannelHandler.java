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

package com.liferay.portal.fabric.netty.rpc.handlers;

import com.liferay.portal.fabric.netty.rpc.RPCSerializable;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Shuyang Zhou
 */
@ChannelHandler.Sharable
public class NettyRPCChannelHandler
	extends SimpleChannelInboundHandler<RPCSerializable> {

	public static final NettyRPCChannelHandler INSTANCE =
		new NettyRPCChannelHandler();

	public static final String NAME = NettyRPCChannelHandler.class.getName();

	@Override
	protected void channelRead0(
		ChannelHandlerContext channelHandlerContext,
		RPCSerializable rpcSerializable) {

		rpcSerializable.execute(channelHandlerContext.channel());
	}

	private NettyRPCChannelHandler() {
	}

}