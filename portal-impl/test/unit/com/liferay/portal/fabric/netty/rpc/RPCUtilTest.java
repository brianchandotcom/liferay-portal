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

import com.liferay.petra.concurrent.AsyncBroker;
import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.fabric.netty.handlers.NettyChannelAttributes;
import com.liferay.portal.fabric.netty.rpc.handlers.NettyRPCChannelHandler;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RPCUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new CodeCoverageAssertor() {

				@Override
				public void appendAssertClasses(List<Class<?>> assertClasses) {
					assertClasses.add(RPCSerializable.class);
					assertClasses.add(NettyRPCChannelHandler.class);
				}

			},
			LiferayUnitTestRule.INSTANCE);

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

		ProcessException processException = new ProcessException("message");

		Future<Serializable> future = RPCUtil.execute(
			_embeddedChannel, new ExceptionRPCCallable(processException));

		_embeddedChannel.writeOneInbound(_embeddedChannel.readOutbound());
		_embeddedChannel.writeOneInbound(_embeddedChannel.readOutbound());

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Throwable throwable = executionException.getCause();

			Assert.assertSame(processException, throwable);
		}

		// Channel closed failure, set back exception

		_embeddedChannel.close();

		Future<String> channelFailureFuture = RPCUtil.execute(
			_embeddedChannel, new ResultRPCCallable(StringPool.BLANK));

		try {
			channelFailureFuture.get();

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Throwable throwable = executionException.getCause();

			Class<?> clazz = throwable.getClass();

			Assert.assertSame(
				ClosedChannelException.class, clazz.getSuperclass());
		}

		// Channel closed failure, no match key

		Attribute<AsyncBroker<Long, String>> attribute = _embeddedChannel.attr(
			ReflectionTestUtil.
				<AttributeKey<AsyncBroker<Long, String>>>getFieldValue(
					NettyChannelAttributes.class, "_asyncBrokerKey"));

		final AtomicLong keyRef = new AtomicLong();

		attribute.set(
			new AsyncBroker<Long, String>() {

				@Override
				public NoticeableFuture<String> post(Long key) {
					keyRef.set(key);

					return new DefaultNoticeableFuture<>();
				}

			});

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				RPCUtil.class.getName(), Level.SEVERE)) {

			RPCUtil.execute(
				_embeddedChannel, new ResultRPCCallable(StringPool.BLANK));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to place exception because no future exists with ID " +
					keyRef.get(),
				logEntry.getMessage());

			Throwable throwable = logEntry.getThrowable();

			Class<?> clazz = throwable.getClass();

			Assert.assertSame(
				ClosedChannelException.class, clazz.getSuperclass());
		}
	}

	@Test
	public void testRPCWithResult() throws Exception {
		String result = "result";

		Future<String> future = RPCUtil.execute(
			_embeddedChannel, new ResultRPCCallable(result));

		_embeddedChannel.writeOneInbound(_embeddedChannel.readOutbound());
		_embeddedChannel.writeOneInbound(_embeddedChannel.readOutbound());

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
				new DefaultNoticeableFuture<>();

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
				new DefaultNoticeableFuture<>();

			defaultNoticeableFuture.set(_result);

			return defaultNoticeableFuture;
		}

		private static final long serialVersionUID = 1L;

		private final String _result;

	}

}