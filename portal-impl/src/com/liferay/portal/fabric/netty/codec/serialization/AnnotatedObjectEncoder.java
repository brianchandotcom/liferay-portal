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

import com.liferay.portal.kernel.io.AnnotatedObjectOutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
@Sharable
public class AnnotatedObjectEncoder extends MessageToByteEncoder<Serializable> {

	public static final AnnotatedObjectEncoder INSTANCE =
		new AnnotatedObjectEncoder();

	public static final String NAME = AnnotatedObjectEncoder.class.getName();

	@Override
	protected void encode(
			ChannelHandlerContext channelHandlerContext,
			Serializable serializable, ByteBuf byteBuf)
		throws IOException {

		int startIndex = byteBuf.writerIndex();

		ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(
			byteBuf);

		byteBufOutputStream.writeInt(0);

		ObjectOutputStream objectOutputStream = new AnnotatedObjectOutputStream(
			byteBufOutputStream);

		objectOutputStream.writeObject(serializable);

		objectOutputStream.flush();

		int endIndex = byteBuf.writerIndex();

		byteBuf.setInt(startIndex, endIndex - startIndex - 4);
	}

	private AnnotatedObjectEncoder() {
	}

}