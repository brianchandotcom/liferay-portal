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
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJNewEnvMethodRule;
import com.liferay.portal.test.aspects.ReflectionUtilAdvice;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ObjectDecodeChannelInboundHandlerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testChannelRead() throws Exception {
		DateChannelHandler dateChannelHandler = new DateChannelHandler();

		try {
			dateChannelHandler.channelRead(null, null);

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}

		ReflectionTestUtil.invoke(
			dateChannelHandler, "channelRead0",
			new Class<?>[] {ChannelHandlerContext.class, Object.class}, null,
			null);

		UUID uuid = UUID.randomUUID();

		Assert.assertSame(
			uuid, dateChannelHandler.channelRead(null, uuid, null));
		Assert.assertNull(dateChannelHandler.getDate());

		Date date = new Date();

		Assert.assertSame(
			date, dateChannelHandler.channelRead(null, date, null));
		Assert.assertSame(date, dateChannelHandler.getDate());

		dateChannelHandler.setFailRead(true);

		Assert.assertSame(
			date, dateChannelHandler.channelRead(null, date, null));
		Assert.assertSame(
			DateChannelHandler._exception, dateChannelHandler.getThrowable());
	}

	@AdviseWith(adviceClasses = ReflectionUtilAdvice.class)
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testClassLoadingFailure() {
		Throwable throwable = new Throwable();

		ReflectionUtilAdvice.setDeclaredFieldThrowable(throwable);

		try {
			new DateChannelHandler();

			Assert.fail();
		}
		catch (ExceptionInInitializerError eiie) {
			Assert.assertSame(throwable, eiie.getCause());
		}
	}

	@Test
	public void testHandlerAdded() {
		ChannelPipeline channelPipeline =
			NettyUtil.createEmptyChannelPipeline();

		DateChannelHandler dateChannelHandler = new DateChannelHandler();

		Assert.assertFalse(
			(boolean)ReflectionTestUtil.getFieldValue(
				dateChannelHandler, "_added"));

		channelPipeline.addLast(dateChannelHandler);

		Assert.assertTrue(
			(boolean)ReflectionTestUtil.getFieldValue(
				dateChannelHandler, "_added"));

		Map<String, ChannelHandler> map = channelPipeline.toMap();

		Assert.assertTrue(map.isEmpty());

		AnnotatedObjectDecoder annotatedObjectDecoder =
			new AnnotatedObjectDecoder();

		channelPipeline.addLast(annotatedObjectDecoder);

		dateChannelHandler = new DateChannelHandler();

		Assert.assertFalse(
			(boolean)ReflectionTestUtil.getFieldValue(
				dateChannelHandler, "_added"));

		channelPipeline.addLast(dateChannelHandler);

		Assert.assertTrue(
			(boolean)ReflectionTestUtil.getFieldValue(
				dateChannelHandler, "_added"));

		map = channelPipeline.toMap();

		Assert.assertEquals(1, map.size());
		Assert.assertTrue(map.containsValue(annotatedObjectDecoder));
		Assert.assertSame(
			dateChannelHandler, annotatedObjectDecoder.removeLast());
	}

	@Rule
	public final AspectJNewEnvMethodRule aspectJNewEnvMethodRule =
		new AspectJNewEnvMethodRule();

	private static class DateChannelHandler
		extends ObjectDecodeChannelInboundHandler<Date> {

		@Override
		public Date channelRead0(
				ChannelHandlerContext channelHandlerContext, Date date,
				ByteBuf byteBuf)
			throws Exception {

			_date = date;

			if (_failRead) {
				throw _exception;
			}

			return date;
		}

		@Override
		public void exceptionCaught(
			ChannelHandlerContext channelHandlerContext, Throwable throwable) {

			_throwable = throwable;
		}

		public Date getDate() {
			return _date;
		}

		public Throwable getThrowable() {
			return _throwable;
		}

		public void setFailRead(boolean failRead) {
			_failRead = failRead;
		}

		private static final Exception _exception = new Exception();

		private Date _date;
		private boolean _failRead;
		private Throwable _throwable;

	}

}