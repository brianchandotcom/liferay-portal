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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.fabric.netty.NettyTestUtil;
import com.liferay.portal.fabric.netty.handlers.NettyChannelAttributes;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import io.netty.channel.embedded.EmbeddedChannel;

import java.io.Serializable;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RPCResponseTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testExecuteWithCancellation() throws Exception {
		doTestExecute(true, _RESULT, null);
	}

	@Test
	public void testExecuteWithException() throws Exception {
		doTestExecute(false, null, _throwable);
	}

	@Test
	public void testExecuteWithResult() throws Exception {
		doTestExecute(false, _RESULT, null);
	}

	@Test
	public void testToString() {
		RPCResponse<String> rpcResponse = new RPCResponse<>(
			_ID, true, _RESULT, _throwable);

		Assert.assertEquals(
			StringBundler.concat(
				"{cancelled=true, id=", _ID, ", result=", _RESULT,
				", throwable=", _throwable, "}"),
			rpcResponse.toString());
	}

	protected void doTestExecute(
			boolean cancelled, String result, Throwable throwable)
		throws Exception {

		// No future exist

		RPCResponse<String> rpcResponse = new RPCResponse<>(
			_ID, cancelled, result, throwable);

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				RPCResponse.class.getName(), Level.SEVERE)) {

			rpcResponse.execute(_embeddedChannel);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			if (cancelled) {
				Assert.assertEquals(
					"Unable to place cancellation because no future exists " +
						"with ID " + _ID,
					logEntry.getMessage());
			}
			else if (throwable != null) {
				Assert.assertEquals(
					"Unable to place exception because no future exists with " +
						"ID " + _ID,
					logEntry.getMessage());
				Assert.assertSame(throwable, logEntry.getThrowable());
			}
			else {
				Assert.assertEquals(
					StringBundler.concat(
						"Unable to place result ", result,
						" because no future exists with ID ", _ID),
					logEntry.getMessage());
			}
		}

		// Have futures, with log

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				RPCResponse.class.getName(), Level.FINEST)) {

			AsyncBroker<Long, Serializable> asyncBroker =
				NettyChannelAttributes.getAsyncBroker(_embeddedChannel);

			NoticeableFuture<Serializable> noticeableFuture = asyncBroker.post(
				_ID);

			rpcResponse.execute(_embeddedChannel);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			if (!cancelled) {
				Assert.assertTrue(logEntries.toString(), logEntries.isEmpty());

				return;
			}

			Assert.assertTrue(noticeableFuture.isCancelled());
			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.remove(0);

			Assert.assertEquals(
				"Cancelled future with ID " + _ID, logEntry.getMessage());

			DefaultNoticeableFuture<Serializable> defaultNoticeableFuture =
				new DefaultNoticeableFuture<>();

			defaultNoticeableFuture.cancel(true);

			ConcurrentMap<Long, DefaultNoticeableFuture<Serializable>>
				defaultNoticeableFutures = ReflectionTestUtil.getFieldValue(
					asyncBroker, "_defaultNoticeableFutures");

			defaultNoticeableFutures.put(_ID, defaultNoticeableFuture);

			rpcResponse.execute(_embeddedChannel);

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			logEntry = logEntries.remove(0);

			Assert.assertEquals(
				"Unable to cancel future with ID " + _ID +
					" because it is already completed",
				logEntry.getMessage());
		}

		// Have futures, without log

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				RPCResponse.class.getName(), Level.OFF)) {

			AsyncBroker<Long, Serializable> asyncBroker =
				NettyChannelAttributes.getAsyncBroker(_embeddedChannel);

			NoticeableFuture<Serializable> noticeableFuture = asyncBroker.post(
				_ID);

			rpcResponse.execute(_embeddedChannel);

			Assert.assertTrue(noticeableFuture.isCancelled());

			DefaultNoticeableFuture<Serializable> defaultNoticeableFuture =
				new DefaultNoticeableFuture<>();

			defaultNoticeableFuture.cancel(true);

			ConcurrentMap<Long, DefaultNoticeableFuture<Serializable>>
				defaultNoticeableFutures = ReflectionTestUtil.getFieldValue(
					asyncBroker, "_defaultNoticeableFutures");

			defaultNoticeableFutures.put(_ID, defaultNoticeableFuture);

			rpcResponse.execute(_embeddedChannel);
		}
	}

	private static final long _ID = System.currentTimeMillis();

	private static final String _RESULT = "This is the result.";

	private final EmbeddedChannel _embeddedChannel =
		NettyTestUtil.createEmptyEmbeddedChannel();
	private final Throwable _throwable = new Throwable(
		"This is the throwable.");

}