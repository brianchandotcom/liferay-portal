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
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;

import io.netty.channel.embedded.EmbeddedChannel;

import java.nio.channels.ClosedChannelException;

import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RPCRequestTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testExecuteWithException() {
		RPCRequest<String> rpcRequest = new RPCRequest<String>(
			_id, new TestProcessCallable(null, _exception));

		RPCResponse<String> rpcResponse = new RPCResponse<String>(
			_id, null, _exception);

		doTestExecute(rpcRequest, rpcResponse);
	}

	@Test
	public void testExecuteWithResult() {
		RPCRequest<String> rpcRequest = new RPCRequest<String>(
			_id, new TestProcessCallable(_result, null));

		RPCResponse<String> rpcResponse = new RPCResponse<String>(
			_id, _result, null);

		doTestExecute(rpcRequest, rpcResponse);
	}

	@Test
	public void testToString() {
		ProcessCallable<String> processCallable = new TestProcessCallable(
			_result, null);

		RPCRequest<String> rpcRequest = new RPCRequest<String>(
			_id, processCallable);

		Assert.assertEquals(
			"{id=" + _id + ", processCallable=" + processCallable.toString() +
				"}",
			rpcRequest.toString());
	}

	protected void doTestExecute(
		RPCRequest<String> rpcRequest, RPCResponse<String> rpcResponse) {

		// Normal

		rpcRequest.execute(_embeddedChannel);

		Queue<Object> messages = _embeddedChannel.outboundMessages();

		Assert.assertEquals(1, messages.size());

		Object message = messages.poll();

		Assert.assertTrue(message instanceof RPCResponse);
		Assert.assertEquals(rpcResponse.toString(), message.toString());

		// Channel failure

		_embeddedChannel.close();

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			RPCRequest.class.getName(), Level.SEVERE);

		try {
			rpcRequest.execute(_embeddedChannel);

			messages = _embeddedChannel.outboundMessages();

			Assert.assertTrue(messages.isEmpty());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to send RPC response: " + rpcResponse.toString(),
				logRecord.getMessage());

			Throwable throwable = logRecord.getThrown();

			Assert.assertSame(
				ClosedChannelException.class, throwable.getClass());
		}
		finally {
			captureHandler.close();
		}
	}

	private final EmbeddedChannel _embeddedChannel =
		NettyTestUtil.createEmptyEmbeddedChannel();
	private final ProcessException _exception = new ProcessException(
		"This is the exception.");
	private final long _id = System.currentTimeMillis();
	private final String _result = "This is the result.";

	private static class TestProcessCallable
		implements ProcessCallable<String> {

		public TestProcessCallable(
			String result, ProcessException processException) {

			_result = result;
			_processException = processException;
		}

		@Override
		public String call() throws ProcessException {
			if (_processException != null) {
				throw _processException;
			}

			return _result;
		}

		private static final long serialVersionUID = 1L;

		private final ProcessException _processException;
		private final String _result;

	}

}