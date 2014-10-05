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
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;

import io.netty.channel.embedded.EmbeddedChannel;

import java.io.Serializable;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.ClassRule;
import org.junit.Test;

import org.testng.Assert;

/**
 * @author Shuyang Zhou
 */
public class RPCResponseTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testExecuteWithException() throws Exception {
		doTestExecute(null, _exception);
	}

	@Test
	public void testExecuteWithResult() throws Exception {
		doTestExecute(_result, null);
	}

	@Test
	public void testToString() {
		RPCResponse<String> rpcResponse = new RPCResponse<String>(
			_id, _result, _exception);

		Assert.assertEquals(
			"{id=" + _id + ", result=" + _result + ", throwable=" + _exception +
				"}",
			rpcResponse.toString());
	}

	protected void doTestExecute(
			String result, ProcessException processException)
		throws Exception {

		RPCResponse<String> rpcResponse = new RPCResponse<String>(
			_id, result, processException);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			RPCResponse.class.getName(), Level.SEVERE);

		try {
			rpcResponse.execute(_embeddedChannel);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			if (processException != null) {
				Assert.assertEquals(
					"Unable to place exception because no future exists with " +
						"ID " + _id,
					logRecord.getMessage());
				Assert.assertSame(processException, logRecord.getThrown());
			}
			else {
				Assert.assertEquals(
					"Unable to place result " + result +
						" because no future exists with ID " + _id,
					logRecord.getMessage());
			}
		}
		finally {
			captureHandler.close();
		}

		AsyncBroker<Long, Serializable> asyncBroker =
			NettyChannelAttributes.getAsyncBroker(_embeddedChannel);

		NoticeableFuture<Serializable> noticeableFuture = asyncBroker.post(_id);

		rpcResponse.execute(_embeddedChannel);

		if (processException != null) {
			try {
				noticeableFuture.get();
			}
			catch (ExecutionException ee) {
				Assert.assertSame(processException, ee.getCause());
			}
		}
		else {
			Assert.assertSame(result, noticeableFuture.get());
		}
	}

	private final EmbeddedChannel _embeddedChannel =
		NettyTestUtil.createEmptyEmbeddedChannel();
	private final ProcessException _exception = new ProcessException(
		"This is the exception.");
	private final long _id = System.currentTimeMillis();
	private final String _result = "This is the result.";

}