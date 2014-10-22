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

package com.liferay.portal.fabric.local.worker;

import com.liferay.portal.fabric.status.JMXProxyUtil.ProcessCallableExecutor;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class LocalFabricWorkerProcessCallableExecutorTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testExecute() throws Exception {
		ProcessCallableExecutor processCallableExecutor =
			new LocalFabricWorkerProcessCallableExecutor(
				new EmbeddedProcessChannel<Serializable>(
					new DefaultNoticeableFuture<Serializable>()));

		NoticeableFuture<Serializable> noticeableFuture =
			processCallableExecutor.execute(
				new ProcessCallable<Serializable>() {

					@Override
					public Serializable call() {
						return StringPool.BLANK;
					}

				});

		Assert.assertEquals(StringPool.BLANK, noticeableFuture.get());

		final ProcessException processException = new ProcessException("");

		noticeableFuture = processCallableExecutor.execute(
			new ProcessCallable<Serializable>() {

				@Override
				public Serializable call() throws ProcessException {
					throw processException;
				}

			});

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(processException, ee.getCause());
		}
	}

}