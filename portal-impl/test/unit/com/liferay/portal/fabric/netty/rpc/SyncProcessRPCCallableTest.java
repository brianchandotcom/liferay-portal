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

import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class SyncProcessRPCCallableTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testCallWithException() throws Throwable {
		final RuntimeException runtimeException = new RuntimeException();

		RPCCallable<String> rpcCallable = new SyncProcessRPCCallable<String>(
			new ProcessCallable<String>() {

				@Override
				public String call() {
					throw runtimeException;
				}

			});

		NoticeableFuture<String> noticeableFuture = rpcCallable.call();

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertEquals(runtimeException, ee.getCause());
		}
	}

	@Test
	public void testCallWithResult() throws Throwable {
		final String result = "result";

		RPCCallable<String> rpcCallable = new SyncProcessRPCCallable<String>(
			new ProcessCallable<String>() {

				@Override
				public String call() {
					return result;
				}

			});

		NoticeableFuture<String> noticeableFuture = rpcCallable.call();

		Assert.assertEquals(result, noticeableFuture.get());
	}

}