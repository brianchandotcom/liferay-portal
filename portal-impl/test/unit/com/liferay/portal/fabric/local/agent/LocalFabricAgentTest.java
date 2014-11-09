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

package com.liferay.portal.fabric.local.agent;

import com.liferay.portal.fabric.agent.FabricAgent;
import com.liferay.portal.fabric.status.LocalFabricStatus;
import com.liferay.portal.fabric.worker.FabricWorker;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class LocalFabricAgentTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testConstructor() {
		FabricAgent fabricAgent = new LocalFabricAgent(
			new EmbeddedProcessExecutor());

		Assert.assertSame(
			LocalFabricStatus.INSTANCE, fabricAgent.getFabricStatus());

		Collection<? extends FabricWorker<?>> fabricWorkers =
			fabricAgent.getFabricWorkers();

		Assert.assertTrue(fabricWorkers.isEmpty());

		try {
			fabricWorkers.clear();

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}
	}

	@Test
	public void testExecute() throws Exception {
		FabricAgent fabricAgent = new LocalFabricAgent(
			new EmbeddedProcessExecutor());

		Collection<? extends FabricWorker<?>> fabricWorkers =
			fabricAgent.getFabricWorkers();

		Assert.assertTrue(fabricWorkers.isEmpty());

		final String result = "Test result";

		FabricWorker<String> fabricWorker = fabricAgent.execute(
			null,
			new ProcessCallable<String>() {

				@Override
				public String call() {
					return result;
				}

			});

		Assert.assertEquals(1, fabricWorkers.size());
		Assert.assertTrue(fabricWorkers.contains(fabricWorker));

		DefaultNoticeableFuture<String> defaultNoticeableFuture =
			(DefaultNoticeableFuture<String>)
				fabricWorker.getProcessNoticeableFuture();

		defaultNoticeableFuture.run();

		Assert.assertEquals(result, defaultNoticeableFuture.get());
		Assert.assertTrue(fabricWorkers.isEmpty());

		final ProcessException processException = new ProcessException(
			"Test exception");

		fabricWorker = fabricAgent.execute(
			null,
			new ProcessCallable<String>() {

				@Override
				public String call() throws ProcessException {
					throw processException;
				}

			});

		Assert.assertEquals(1, fabricWorkers.size());
		Assert.assertTrue(fabricWorkers.contains(fabricWorker));

		defaultNoticeableFuture = (DefaultNoticeableFuture<String>)
			fabricWorker.getProcessNoticeableFuture();

		defaultNoticeableFuture.run();

		try {
			defaultNoticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(processException, ee.getCause());
		}

		Assert.assertTrue(fabricWorkers.isEmpty());
	}

}