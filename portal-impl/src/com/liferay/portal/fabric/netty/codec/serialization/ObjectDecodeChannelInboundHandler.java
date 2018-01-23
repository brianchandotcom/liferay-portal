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

package com.liferay.portal.fabric.netty.codec.serialization;

import com.liferay.petra.reflect.ReflectionUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Field;

/**
 * @author Shuyang Zhou
 */
public abstract class ObjectDecodeChannelInboundHandler<T>
	extends SimpleChannelInboundHandler<T> {

	@Override
	public final void channelRead(
		ChannelHandlerContext channelHandlerContext, Object object) {

		throw new UnsupportedOperationException();
	}

	public Object channelRead(
			ChannelHandlerContext channelHandlerContext, Object object,
			ByteBuf byteBuf)
		throws Exception {

		if (acceptInboundMessage(object)) {
			try {
				return channelRead0(channelHandlerContext, (T)object, byteBuf);
			}
			catch (Throwable t) {
				exceptionCaught(channelHandlerContext, t);
			}
		}

		return object;
	}

	public abstract T channelRead0(
			ChannelHandlerContext channelHandlerContext, T t, ByteBuf byteBuf)
		throws Exception;

	@Override
	public void handlerAdded(ChannelHandlerContext channelHandlerContext)
		throws Exception {

		if (_added) {
			return;
		}

		_added = true;

		ChannelPipeline channelPipeline = channelHandlerContext.pipeline();

		channelPipeline.remove(this);

		AnnotatedObjectDecoder annotatedObjectDecoder = channelPipeline.get(
			AnnotatedObjectDecoder.class);

		if (annotatedObjectDecoder != null) {
			_ADDED_FIELD.setBoolean(this, false);

			annotatedObjectDecoder.addLast(this);
		}
	}

	@Override
	protected final void channelRead0(
		ChannelHandlerContext channelHandlerContext, T t) {
	}

	private static final Field _ADDED_FIELD;

	static {
		try {
			_ADDED_FIELD = ReflectionUtil.getDeclaredField(
				ChannelHandlerAdapter.class, "added");
		}
		catch (Throwable t) {
			throw new ExceptionInInitializerError(t);
		}
	}

	private boolean _added;

}