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

import com.liferay.petra.io.ProtectedAnnotatedObjectInputStream;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class AnnotatedObjectEncoderTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testEncode() throws Exception {
		AnnotatedObjectEncoder annotatedObjectEncoder =
			AnnotatedObjectEncoder.INSTANCE;

		Date date = new Date();

		ByteBuf byteBuf = Unpooled.buffer();

		annotatedObjectEncoder.encode(null, date, byteBuf);

		Assert.assertEquals(byteBuf.readInt(), byteBuf.readableBytes());

		ProtectedAnnotatedObjectInputStream annotatedObjectInputStream =
			new ProtectedAnnotatedObjectInputStream(
				new ByteBufInputStream(byteBuf));

		Assert.assertEquals(date, annotatedObjectInputStream.readObject());

		Assert.assertFalse(byteBuf.isReadable());
	}

	@Test
	public void testStructure() throws ReflectiveOperationException {
		Assert.assertNotNull(
			AnnotatedObjectEncoder.class.getAnnotation(
				ChannelHandler.Sharable.class));

		Field instanceField = AnnotatedObjectEncoder.class.getField("INSTANCE");

		Assert.assertEquals(
			Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL,
			instanceField.getModifiers());
		Assert.assertSame(
			AnnotatedObjectEncoder.class, instanceField.getType());
		Assert.assertNotNull(instanceField.get(null));

		Field nameField = AnnotatedObjectEncoder.class.getField("NAME");

		Assert.assertEquals(
			Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL,
			nameField.getModifiers());
		Assert.assertSame(String.class, nameField.getType());
		Assert.assertEquals(
			AnnotatedObjectEncoder.class.getName(), nameField.get(null));

		Constructor<AnnotatedObjectEncoder> constructor =
			AnnotatedObjectEncoder.class.getDeclaredConstructor();

		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	}

}