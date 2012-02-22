/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

/**
 * @author Shuyang Zhou
 */
public class FinalizeManagerTest extends TestCase {

	public void testRegister() throws InterruptedException {
		Thread currentThread = Thread.currentThread();

		PortalClassLoaderUtil.setClassLoader(
			currentThread.getContextClassLoader());

		if (FinalizeManager.THREAD_ENABLED) {
			registerWithThread();
		}
		else {
			registerWithoutThread();
		}

		PortalClassLoaderUtil.setClassLoader(null);
	}

	protected void registerWithoutThread() throws InterruptedException {
		Object testObject = new Object();

		MarkFinalizeAction markFinalizeAction = new MarkFinalizeAction();

		FinalizeManager.register(testObject, markFinalizeAction);

		assertFalse(markFinalizeAction.isMarked());

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

		assertTrue(markFinalizeAction.isMarked());
	}

	protected void registerWithThread() throws InterruptedException {
		Object testObject = new Object();

		MarkFinalizeAction markFinalizeAction = new MarkFinalizeAction();

		FinalizeManager.register(testObject, markFinalizeAction);

		assertFalse(markFinalizeAction.isMarked());

		testObject = null;

		long startTime = System.currentTimeMillis();

		while ((System.currentTimeMillis() - startTime) < 100) {
			System.gc();

			Thread.sleep(1);

			if (markFinalizeAction.isMarked()) {
				break;
			}
		}

		assertTrue(markFinalizeAction.isMarked());
	}

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