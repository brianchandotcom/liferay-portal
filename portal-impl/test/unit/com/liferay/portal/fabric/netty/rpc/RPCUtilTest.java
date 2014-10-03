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
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StringPool;

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

import org.junit.ClassRule;
import org.junit.Test;

import org.testng.Assert;

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
	public void testRPCWithException() throws Exception {

		// RPCResponse with exception

		ProcessException testException = new ProcessException("message");

		Future<Serializable> future = RPCUtil.execute(
			_embeddedChannel, new ExceptionProcessCallable(testException));

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
			_embeddedChannel, new ResultProcessCallable(StringPool.BLANK));

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
				_embeddedChannel, new ResultProcessCallable(StringPool.BLANK));

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
			_embeddedChannel, new ResultProcessCallable(result));

		_embeddedChannel.writeInbound(_embeddedChannel.readOutbound());
		_embeddedChannel.writeInbound(_embeddedChannel.readOutbound());

		Assert.assertEquals(result, future.get());
	}

	private final EmbeddedChannel _embeddedChannel = new EmbeddedChannel(
		NettyRPCChannelHandler.INSTANCE);

	private static class ExceptionProcessCallable
		implements ProcessCallable<Serializable> {

		public ExceptionProcessCallable(ProcessException processException) {
			_processException = processException;
		}

		@Override
		public Serializable call() throws ProcessException {
			throw _processException;
		}

		private static final long serialVersionUID = 1L;

		private final ProcessException _processException;

	}

	private static class ResultProcessCallable
		implements ProcessCallable<String> {

		public ResultProcessCallable(String result) {
			_result = result;
		}

		@Override
		public String call() {
			return _result;
		}

		private static final long serialVersionUID = 1L;

		private final String _result;

	}

}