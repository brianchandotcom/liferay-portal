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

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

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
		System.setProperty(_THREAD_ENABLED_KEY, Boolean.FALSE.toString());

		Object testObject = new Object();

		MarkFinalizeAction markFinalizeAction = new MarkFinalizeAction();

		FinalizeManager.register(testObject, markFinalizeAction);

		Assert.assertFalse(markFinalizeAction.isMarked());

		testObject = null;

		gc();

		FinalizeManager.register(new Object(), markFinalizeAction);

		Assert.assertTrue(markFinalizeAction.isMarked());
	}

	@Test
	public void testRegisterWithThread() throws InterruptedException {
		System.setProperty(_THREAD_ENABLED_KEY, Boolean.TRUE.toString());

		Object testObject = new Object();

		MarkFinalizeAction markFinalizeAction = new MarkFinalizeAction();

		FinalizeManager.register(testObject, markFinalizeAction);

		Assert.assertFalse(markFinalizeAction.isMarked());

		testObject = null;

		gc();

		// Time wait 10 sec for the async mark

		long startTime = System.currentTimeMillis();

		while (!markFinalizeAction.isMarked() &&
			   ((System.currentTimeMillis() - startTime) < 10000)) {

			Thread.sleep(1);
		}

		Assert.assertTrue(markFinalizeAction.isMarked());

		Thread finalizeThread = null;

		for (Thread thread : ThreadUtil.getThreads()) {
			String name = thread.getName();

			if (name.equals("Finalize Thread")) {
				finalizeThread = thread;

				break;
			}
		}

		Assert.assertNotNull(finalizeThread);

		// First waiting state

		startTime = System.currentTimeMillis();

		while (finalizeThread.getState() != Thread.State.WAITING) {
			if ((System.currentTimeMillis() - startTime) > 10000) {
				Assert.fail(
					"Timeout on waiting finialize thread to enter waiting " +
						"state");
			}
		}

		// Interrupt to wake up

		finalizeThread.interrupt();

		// Second waiting state

		while (finalizeThread.getState() != Thread.State.WAITING) {
			if ((System.currentTimeMillis() - startTime) > 10000) {
				Assert.fail(
					"Timeout on waiting finialize thread to enter waiting " +
						"state");
			}
		}
	}

	private static void gc() throws InterruptedException {
		ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();

		WeakReference<Object> weakReference = new WeakReference<Object>(
			new Object(), referenceQueue);

		while (weakReference.get() != null) {
			System.gc();
			System.runFinalization();
		}

		Assert.assertSame(weakReference, referenceQueue.remove());
	}

	private static final String _THREAD_ENABLED_KEY =
		FinalizeManager.class.getName() + ".thread.enabled";

	private class MarkFinalizeAction implements FinalizeAction {

		@Override
		public void doFinalize() {
			_marked = true;
		}

		public boolean isMarked() {
			return _marked;
		}

		private volatile boolean _marked;

	}

}