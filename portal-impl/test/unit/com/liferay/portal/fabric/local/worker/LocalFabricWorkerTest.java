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

import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;
import com.liferay.portal.fabric.status.FabricStatus;
import com.liferay.portal.fabric.status.RemoteFabricStatus;
import com.liferay.portal.fabric.worker.FabricWorker;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class LocalFabricWorkerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		NoticeableFuture<String> noticeableFuture =
			new DefaultNoticeableFuture<>();

		FabricWorker<String> fabricWorker = new LocalFabricWorker<>(
			new EmbeddedProcessChannel<String>(noticeableFuture));

		Assert.assertSame(
			noticeableFuture, fabricWorker.getProcessNoticeableFuture());

		FabricStatus fabricStatus = fabricWorker.getFabricStatus();

		Assert.assertSame(RemoteFabricStatus.class, fabricStatus.getClass());
	}

	@Test
	public void testWrite() throws Exception {
		NoticeableFuture<String> noticeableFuture =
			new DefaultNoticeableFuture<>();

		LocalFabricWorker<String> localFabricWorker = new LocalFabricWorker<>(
			new EmbeddedProcessChannel<String>(noticeableFuture));

		final String result = "Test result";

		NoticeableFuture<String> resultNoticeableFuture =
			localFabricWorker.write(
				new ProcessCallable<String>() {

					@Override
					public String call() {
						return result;
					}

				});

		Assert.assertEquals(result, resultNoticeableFuture.get());

		final ProcessException processException = new ProcessException(
			"Test exception");

		NoticeableFuture<String> exceptionNoticeableFuture =
			localFabricWorker.write(
				new ProcessCallable<String>() {

					@Override
					public String call() throws ProcessException {
						throw processException;
					}

				});

		try {
			exceptionNoticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(processException, ee.getCause());
		}
	}

}