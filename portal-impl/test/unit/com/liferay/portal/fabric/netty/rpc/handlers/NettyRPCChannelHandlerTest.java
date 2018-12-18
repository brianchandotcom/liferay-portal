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
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.embedded.EmbeddedChannel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NettyRPCChannelHandlerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testChannelRead0() {
		EmbeddedChannel embeddedChannel = new EmbeddedChannel(
			NettyRPCChannelHandler.INSTANCE);

		final AtomicReference<Channel> channelReference =
			new AtomicReference<>();

		RPCSerializable rpcSerializable = new RPCSerializable(
			System.currentTimeMillis()) {

			@Override
			public void execute(Channel channel) {
				channelReference.set(channel);
			}

			private static final long serialVersionUID = 1L;

		};

		embeddedChannel.writeInbound(rpcSerializable);

		Assert.assertSame(embeddedChannel, channelReference.get());
	}

	@Test
	public void testStructure() throws ReflectiveOperationException {
		Assert.assertNotNull(
			NettyRPCChannelHandler.class.getAnnotation(
				ChannelHandler.Sharable.class));

		Field instanceField = NettyRPCChannelHandler.class.getField("INSTANCE");

		Assert.assertEquals(
			Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL,
			instanceField.getModifiers());
		Assert.assertSame(
			NettyRPCChannelHandler.class, instanceField.getType());
		Assert.assertNotNull(instanceField.get(null));

		Field nameField = NettyRPCChannelHandler.class.getField("NAME");

		Assert.assertEquals(
			Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL,
			nameField.getModifiers());
		Assert.assertSame(String.class, nameField.getType());
		Assert.assertEquals(
			NettyRPCChannelHandler.class.getName(), nameField.get(null));

		Constructor<NettyRPCChannelHandler> constructor =
			NettyRPCChannelHandler.class.getDeclaredConstructor();

		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	}

}