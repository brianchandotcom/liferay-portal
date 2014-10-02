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

import com.liferay.portal.fabric.netty.util.NettyUtil;
import com.liferay.portal.kernel.io.AnnotatedObjectInputStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.ObjectInputStream;

import java.util.Map.Entry;

/**
 * @author Shuyang Zhou
 */
public class AnnotatedObjectDecoder extends LengthFieldBasedFrameDecoder {

	public static final String NAME = AnnotatedObjectDecoder.class.getName();

	public AnnotatedObjectDecoder() {
		super(Integer.MAX_VALUE, 0, 4, 0, 4);
	}

	public void addFirst(
		ObjectDecodeChannelInboundHandler<?>...
			objectDecodeChannelInboundHandlers) {

		_channelPipeline.addFirst(objectDecodeChannelInboundHandlers);
	}

	public void addFirst(
		String name, ObjectDecodeChannelInboundHandler<?>
			objectDecodeChannelInboundHandler) {

		_channelPipeline.addFirst(name, objectDecodeChannelInboundHandler);
	}

	public void addLast(
		ObjectDecodeChannelInboundHandler<?>...
			objectDecodeChannelInboundHandlers) {

		_channelPipeline.addLast(objectDecodeChannelInboundHandlers);
	}

	public void addLast(
		String name, ObjectDecodeChannelInboundHandler<?>
			objectDecodeChannelInboundHandler) {

		_channelPipeline.addLast(name, objectDecodeChannelInboundHandler);
	}

	public <T extends ObjectDecodeChannelInboundHandler<?>> T remove(
		Class<T> handlerType) {

		return _channelPipeline.remove(handlerType);
	}

	public void remove(
		ObjectDecodeChannelInboundHandler<?>
			objectDecodeChannelInboundHandler) {

		_channelPipeline.remove(objectDecodeChannelInboundHandler);
	}

	public ObjectDecodeChannelInboundHandler<?> remove(String name) {
		return (ObjectDecodeChannelInboundHandler<?>)
			_channelPipeline.remove(name);
	}

	public ObjectDecodeChannelInboundHandler<?> removeFirst() {
		return (ObjectDecodeChannelInboundHandler<?>)
			_channelPipeline.removeFirst();
	}

	public ObjectDecodeChannelInboundHandler<?> removeLast() {
		return (ObjectDecodeChannelInboundHandler<?>)
			_channelPipeline.removeLast();
	}

	@Override
	protected Object decode(
			ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
		throws Exception {

		ByteBuf decodeByteBuf = (ByteBuf)super.decode(
			channelHandlerContext, byteBuf);

		if (decodeByteBuf == null) {
			return null;
		}

		ObjectInputStream objectInputStream = new AnnotatedObjectInputStream(
			new ByteBufInputStream(decodeByteBuf));

		Object object = objectInputStream.readObject();

		for (Entry<String, ChannelHandler> entry : _channelPipeline) {
			ObjectDecodeChannelInboundHandler<?>
				objectDecodeChannelInboundHandler =
					(ObjectDecodeChannelInboundHandler<?>)entry.getValue();

			object = objectDecodeChannelInboundHandler.channelRead(
				channelHandlerContext, object, byteBuf);
		}

		return object;
	}

	@Override
	protected ByteBuf extractFrame(
		ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, int index,
		int length) {

		return byteBuf.slice(index, length);
	}

	private final ChannelPipeline _channelPipeline =
		NettyUtil.createEmptyChannelPipeline();

}