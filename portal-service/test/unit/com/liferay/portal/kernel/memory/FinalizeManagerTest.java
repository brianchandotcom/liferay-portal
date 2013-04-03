/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.memory;

import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;
import com.liferay.portal.kernel.util.ThreadUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(NewClassLoaderJUnitTestRunner.class)
public class FinalizeManagerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@After
	public void tearDown() {
		System.clearProperty(_THREAD_ENABLED_KEY);
	}

	@Test
	public void testRegisterWithoutThread() throws InterruptedException {
		System.setProperty(_THREAD_ENABLED_KEY, "false");

		Object testObject = new Object();

		MarkFinalizeAction markFinalizeAction = new MarkFinalizeAction();

		FinalizeManager.register(testObject, markFinalizeAction);

		Assert.assertFalse(markFinalizeAction.isMarked());

		testObject = null;

		long startTime = System.currentTimeMillis();

		while ((System.currentTimeMillis() - startTime) < 100) {
			System.gc();
			Thread.sleep(1);

			if (markFinalizeAction.isMarked()) {
				break;
			}
		}

		FinalizeManager.register(new Object(), markFinalizeAction);

		Assert.assertTrue(markFinalizeAction.isMarked());
	}

	@Test
	public void testRegisterWithThread() throws InterruptedException {
		System.setProperty(_THREAD_ENABLED_KEY, "true");

		Object testObject = new Object();

		MarkFinalizeAction markFinalizeAction = new MarkFinalizeAction();

		FinalizeManager.register(testObject, markFinalizeAction);

		Assert.assertFalse(markFinalizeAction.isMarked());

		testObject = null;

		long startTime = System.currentTimeMillis();

		while ((System.currentTimeMillis() - startTime) < 100) {
			System.gc();

			Thread.sleep(1);

			if (markFinalizeAction.isMarked()) {
				break;
			}
		}

		Assert.assertTrue(markFinalizeAction.isMarked());

		Thread finializeThread = null;

		for (Thread thread : ThreadUtil.getThreads()) {
			if (thread.getName().equals("Finalize Thread")) {
				finializeThread = thread;

				break;
			}
		}

		Assert.assertNotNull(finializeThread);

		// First waiting

		startTime = System.currentTimeMillis();

		while (finializeThread.getState() != Thread.State.WAITING) {
			if ((System.currentTimeMillis() - startTime) > 10000) {
				Assert.fail(
					"Timeout on waiting finialize thread to enter waiting " +
						"state.");
			}
		}

		// Interrupt to wake up

		finializeThread.interrupt();

		// Second waiting

		while (finializeThread.getState() != Thread.State.WAITING) {
			if ((System.currentTimeMillis() - startTime) > 10000) {
				Assert.fail(
					"Timeout on waiting finialize thread to enter waiting " +
						"state.");
			}
		}
	}

	private static final String _THREAD_ENABLED_KEY =
		FinalizeManager.class.getName() + ".thread.enabled";

	private class MarkFinalizeAction implements FinalizeAction {

		public void doFinalize() {
			_marked = true;
		}

		public boolean isMarked() {
			return _marked;
		}

		private volatile boolean _marked;

	}

}