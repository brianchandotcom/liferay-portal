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

import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.fabric.netty.NettyTestUtil;
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

import java.nio.channels.ClosedChannelException;

import java.util.List;
import java.util.Queue;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RPCRequestTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testExecuteWithAsyncException() {
		RPCRequest<String> rpcRequest = new RPCRequest<>(
			_ID, new TestRPCCallable(null, false, _throwable, null));

		RPCResponse<String> rpcResponse = new RPCResponse<>(
			_ID, false, null, _throwable);

		doTestExecute(rpcRequest, rpcResponse);
	}

	@Test
	public void testExecuteWithCancellation() {
		RPCRequest<String> rpcRequest = new RPCRequest<>(
			_ID, new TestRPCCallable(null, true, null, null));

		RPCResponse<String> rpcResponse = new RPCResponse<>(
			_ID, true, null, null);

		doTestExecute(rpcRequest, rpcResponse);
	}

	@Test
	public void testExecuteWithResult() {
		RPCRequest<String> rpcRequest = new RPCRequest<>(
			_ID, new TestRPCCallable(null, false, null, _RESULT));

		RPCResponse<String> rpcResponse = new RPCResponse<>(
			_ID, false, _RESULT, null);

		doTestExecute(rpcRequest, rpcResponse);
	}

	@Test
	public void testExecuteWithSyncException() {
		RPCRequest<String> rpcRequest = new RPCRequest<>(
			_ID, new TestRPCCallable(_throwable, false, null, null));

		RPCResponse<String> rpcResponse = new RPCResponse<>(
			_ID, false, null, _throwable);

		doTestExecute(rpcRequest, rpcResponse);
	}

	@Test
	public void testSendRPCResponseCancelled() {
		ChannelPipeline channelPipeline = _embeddedChannel.pipeline();

		channelPipeline.addLast(
			new ChannelOutboundHandlerAdapter() {

				@Override
				public void write(
					ChannelHandlerContext channelHandlerContext, Object object,
					ChannelPromise channelPromise) {

					channelPromise.cancel(true);
				}

			});

		RPCRequest<String> rpcRequest = new RPCRequest<>(
			_ID, new TestRPCCallable(null, false, null, _RESULT));

		RPCResponse<String> rpcResponse = new RPCResponse<>(
			_ID, true, null, null);

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				RPCRequest.class.getName(), Level.SEVERE)) {

			rpcRequest.sendRPCResponse(_embeddedChannel, rpcResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Cancelled on sending RPC response: " + rpcResponse,
				logEntry.getMessage());
		}
	}

	@Test
	public void testSendRPCResponseFailed() {
		_embeddedChannel.close();

		RPCRequest<String> rpcRequest = new RPCRequest<>(
			_ID, new TestRPCCallable(null, false, null, _RESULT));

		RPCResponse<String> rpcResponse = new RPCResponse<>(
			_ID, true, null, null);

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				RPCRequest.class.getName(), Level.SEVERE)) {

			rpcRequest.sendRPCResponse(_embeddedChannel, rpcResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to send RPC response: " + rpcResponse,
				logEntry.getMessage());

			Throwable throwable = logEntry.getThrowable();

			Assert.assertTrue(throwable instanceof ClosedChannelException);
		}
	}

	@Test
	public void testToString() {
		RPCCallable<String> rpcCallable = new TestRPCCallable(
			null, true, null, null);

		RPCRequest<String> rpcRequest = new RPCRequest<>(_ID, rpcCallable);

		Assert.assertEquals(
			StringBundler.concat(
				"{id=", _ID, ", rpcCallable=", rpcCallable.toString(), "}"),
			rpcRequest.toString());
	}

	protected void doTestExecute(
		RPCRequest<String> rpcRequest, RPCResponse<String> rpcResponse) {

		rpcRequest.execute(_embeddedChannel);

		Queue<Object> messages = _embeddedChannel.outboundMessages();

		Assert.assertEquals(1, messages.size());

		Object message = messages.poll();

		Assert.assertTrue(message instanceof RPCResponse);
		Assert.assertEquals(rpcResponse.toString(), message.toString());
	}

	private static final long _ID = System.currentTimeMillis();

	private static final String _RESULT = "This is the result.";

	private final EmbeddedChannel _embeddedChannel =
		NettyTestUtil.createEmptyEmbeddedChannel();
	private final Throwable _throwable = new Throwable(
		"This is the throwable.");

	private static class TestRPCCallable implements RPCCallable<String> {

		public TestRPCCallable(
			Throwable throwable, boolean cancel, Throwable asyncThrowable,
			String result) {

			_cancel = cancel;
			_asyncThrowable = asyncThrowable;
			_result = result;

			_syncThrowable = throwable;
		}

		@Override
		public NoticeableFuture<String> call() throws Throwable {
			if (_syncThrowable != null) {
				throw _syncThrowable;
			}

			DefaultNoticeableFuture<String> defaultNoticeableFuture =
				new DefaultNoticeableFuture<>();

			if (_cancel) {
				defaultNoticeableFuture.cancel(true);
			}
			else if (_asyncThrowable != null) {
				defaultNoticeableFuture.setException(_asyncThrowable);
			}
			else {
				defaultNoticeableFuture.set(_result);
			}

			return defaultNoticeableFuture;
		}

		private static final long serialVersionUID = 1L;

		private final Throwable _asyncThrowable;
		private final boolean _cancel;
		private final String _result;
		private final Throwable _syncThrowable;

	}

}