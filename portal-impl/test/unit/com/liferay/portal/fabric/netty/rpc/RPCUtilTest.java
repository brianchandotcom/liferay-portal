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

package com.liferay.portal.fabric.netty.rpc;

import com.liferay.portal.fabric.netty.handlers.NettyChannelAttributes;
import com.liferay.portal.fabric.netty.rpc.handlers.NettyRPCChannelHandler;
import com.liferay.portal.kernel.concurrent.AsyncBroker;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StringPool;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.io.Serializable;

import java.nio.channels.ClosedChannelException;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RPCUtilTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(RPCSerializable.class);
				assertClasses.add(NettyRPCChannelHandler.class);
			}

		};

	@Test
	public void testConstructor() {
		new RPCUtil();
	}

	@Test
	public void testRPCWithCancellation() throws Exception {
		ChannelPipeline channelPipeline = _embeddedChannel.pipeline();

		channelPipeline.addFirst(
			new ChannelOutboundHandlerAdapter() {

				@Override
				public void write(
					ChannelHandlerContext channelHandlerContext, Object object,
					ChannelPromise channelPromise) {

					channelPromise.cancel(true);
				}

			});

		Future<String> future = RPCUtil.execute(
			_embeddedChannel, new ResultRPCCallable("result"));

		Assert.assertTrue(future.isCancelled());
	}

	@Test
	public void testRPCWithException() throws Exception {

		// RPCResponse with exception

		ProcessException testException = new ProcessException("message");

		Future<Serializable> future = RPCUtil.execute(
			_embeddedChannel, new ExceptionRPCCallable(testException));

		_embeddedChannel.writeInbound(_embeddedChannel.readOutbound());
		_embeddedChannel.writeInbound(_embeddedChannel.readOutbound());

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			Assert.assertSame(testException, throwable);
		}

		// Channel closed failure, set back exception

		_embeddedChannel.close();

		Future<String> channelFailureFuture = RPCUtil.execute(
			_embeddedChannel, new ResultRPCCallable(StringPool.BLANK));

		try {
			channelFailureFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			Assert.assertSame(
				ClosedChannelException.class, throwable.getClass());
		}

		// Channel closed failure, no match key

		Attribute<AsyncBroker<Long, String>> attribute =
			_embeddedChannel.attr(
				ReflectionTestUtil.
					<AttributeKey<AsyncBroker<Long, String>>>getFieldValue(
						NettyChannelAttributes.class, "_asyncBrokerKey"));

		final AtomicLong keyRef = new AtomicLong();

		attribute.set(
			new AsyncBroker<Long, String>() {

				@Override
				public NoticeableFuture<String> post(Long key) {
					keyRef.set(key);

					return new DefaultNoticeableFuture<String>();
				}

			});

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			RPCUtil.class.getName(), Level.SEVERE);

		try {
			RPCUtil.execute(
				_embeddedChannel, new ResultRPCCallable(StringPool.BLANK));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to place exception because no future exists with ID " +
					keyRef.get(),
				logRecord.getMessage());

			Throwable throwable = logRecord.getThrown();

			Assert.assertSame(
				ClosedChannelException.class, throwable.getClass());
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testRPCWithResult() throws Exception {
		String result = "result";

		Future<String> future = RPCUtil.execute(
			_embeddedChannel, new ResultRPCCallable(result));

		_embeddedChannel.writeInbound(_embeddedChannel.readOutbound());
		_embeddedChannel.writeInbound(_embeddedChannel.readOutbound());

		Assert.assertEquals(result, future.get());
	}

	private final EmbeddedChannel _embeddedChannel = new EmbeddedChannel(
		NettyRPCChannelHandler.INSTANCE);

	private static class ExceptionRPCCallable
		implements RPCCallable<Serializable> {

		public ExceptionRPCCallable(Throwable throwable) {
			_throwable = throwable;
		}

		@Override
		public NoticeableFuture<Serializable> call() {
			DefaultNoticeableFuture<Serializable> defaultNoticeableFuture =
				new DefaultNoticeableFuture<Serializable>();

			defaultNoticeableFuture.setException(_throwable);

			return defaultNoticeableFuture;
		}

		private static final long serialVersionUID = 1L;

		private final Throwable _throwable;

	}

	private static class ResultRPCCallable implements RPCCallable<String> {

		public ResultRPCCallable(String result) {
			_result = result;
		}

		@Override
		public NoticeableFuture<String> call() {
			DefaultNoticeableFuture<String> defaultNoticeableFuture =
				new DefaultNoticeableFuture<String>();

			defaultNoticeableFuture.set(_result);

			return defaultNoticeableFuture;
		}

		private static final long serialVersionUID = 1L;

		private final String _result;

	}

}