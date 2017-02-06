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

import com.liferay.portal.fabric.netty.NettyTestUtil;
import com.liferay.portal.fabric.netty.handlers.NettyChannelAttributes;
import com.liferay.portal.kernel.concurrent.AsyncBroker;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import io.netty.channel.embedded.EmbeddedChannel;

import java.io.Serializable;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RPCResponseTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

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
			"{cancelled=true, id=" + _ID + ", result=" + _RESULT +
				", throwable=" + _throwable + "}",
			rpcResponse.toString());
	}

	protected void doTestExecute(
			boolean cancelled, String result, Throwable throwable)
		throws Exception {

		// No future exist

		RPCResponse<String> rpcResponse = new RPCResponse<>(
			_ID, cancelled, result, throwable);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RPCResponse.class.getName(), Level.SEVERE)) {

			rpcResponse.execute(_embeddedChannel);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			if (cancelled) {
				Assert.assertEquals(
					"Unable to place cancellation because no future exists " +
						"with ID " + _ID,
					logRecord.getMessage());
			}
			else if (throwable != null) {
				Assert.assertEquals(
					"Unable to place exception because no future exists with " +
						"ID " + _ID,
					logRecord.getMessage());
				Assert.assertSame(throwable, logRecord.getThrown());
			}
			else {
				Assert.assertEquals(
					"Unable to place result " + result +
						" because no future exists with ID " + _ID,
					logRecord.getMessage());
			}
		}

		// Have futures, with log

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RPCResponse.class.getName(), Level.FINEST)) {

			AsyncBroker<Long, Serializable> asyncBroker =
				NettyChannelAttributes.getAsyncBroker(_embeddedChannel);

			NoticeableFuture<Serializable> noticeableFuture = asyncBroker.post(
				_ID);

			rpcResponse.execute(_embeddedChannel);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			if (!cancelled) {
				Assert.assertTrue(logRecords.isEmpty());

				return;
			}

			Assert.assertTrue(noticeableFuture.isCancelled());
			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.remove(0);

			Assert.assertEquals(
				"Cancelled future with ID " + _ID, logRecord.getMessage());

			DefaultNoticeableFuture<Serializable> defaultNoticeableFuture =
				new DefaultNoticeableFuture<>();

			defaultNoticeableFuture.cancel(true);

			ConcurrentMap<Long, DefaultNoticeableFuture<Serializable>>
				defaultNoticeableFutures = ReflectionTestUtil.getFieldValue(
					asyncBroker, "_defaultNoticeableFutures");

			defaultNoticeableFutures.put(_ID, defaultNoticeableFuture);

			rpcResponse.execute(_embeddedChannel);

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			logRecord = logRecords.remove(0);

			Assert.assertEquals(
				"Unable to cancel future with ID " + _ID +
					" because it is already completed",
				logRecord.getMessage());
		}

		// Have futures, without log

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
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