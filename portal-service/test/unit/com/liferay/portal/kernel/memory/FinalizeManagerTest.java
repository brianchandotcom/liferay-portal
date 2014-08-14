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

package com.liferay.portal.kernel.memory;

import com.liferay.portal.kernel.memory.FinalizeManager.ReferenceFactory;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.GCUtil;
import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ThreadUtil;

import java.lang.ref.Reference;
import java.lang.reflect.InvocationTargetException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

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
	public void testBadFinalizeAction() throws Exception {
		final RuntimeException runtimeException = new RuntimeException();

		Reference<Object> reference = FinalizeManager.register(
			new Object(), new FinalizeAction() {

				@Override
				public void doFinalize(Reference<?> reference) {
					Assert.assertNotNull(getReferent(reference));

					throw runtimeException;
				}

			}, FinalizeManager.PHANTOM_REFERENCE_FACTORY);

		Assert.assertNotNull(getReferent(reference));

		reference.enqueue();

		try {
			ReflectionTestUtil.invoke(
				FinalizeManager.class, "_pollingCleanup", new Class<?>[0]);

			Assert.fail();
		}
		catch (InvocationTargetException ite) {
			Assert.assertSame(runtimeException, ite.getCause());
		}

		Assert.assertNull(getReferent(reference));
	}

	@Test
	public void testConstructor() {
		new FinalizeManager();
	}

	@Test
	public void testRegisterPhantomWithoutThread() throws Exception {
		doTestRegister(false, ReferenceType.PHANTOM);
	}

	@Test
	public void testRegisterPhantomWithThread() throws Exception {
		doTestRegister(true, ReferenceType.PHANTOM);
	}

	@Test
	public void testRegisterSoftWithoutThread() throws Exception {
		doTestRegister(false, ReferenceType.SOFT);
	}

	@Test
	public void testRegisterSoftWithThread() throws Exception {
		doTestRegister(true, ReferenceType.SOFT);
	}

	@Test
	public void testRegisterWeakWithoutThread() throws Exception {
		doTestRegister(false, ReferenceType.WEAK);
	}

	@Test
	public void testRegisterWeakWithThread() throws Exception {
		doTestRegister(true, ReferenceType.WEAK);
	}

	protected void doTestRegister(
			boolean threadEnabled, ReferenceType referenceType)
		throws Exception {

		System.setProperty(
			_THREAD_ENABLED_KEY, Boolean.toString(threadEnabled));

		String id = "testObject";

		FinalizeRecorder finalizeRecorder = new FinalizeRecorder(id);

		MarkFinalizeAction markFinalizeAction = new MarkFinalizeAction();

		ReferenceFactory referenceFactory =
			FinalizeManager.PHANTOM_REFERENCE_FACTORY;

		if (referenceType == ReferenceType.WEAK) {
			referenceFactory = FinalizeManager.WEAK_REFERENCE_FACTORY;
		}
		else if (referenceType == ReferenceType.SOFT) {
			referenceFactory = FinalizeManager.SOFT_REFERENCE_FACTORY;
		}

		Reference<FinalizeRecorder> reference = FinalizeManager.register(
			finalizeRecorder, markFinalizeAction, referenceFactory);

		Assert.assertFalse(markFinalizeAction.isMarked());

		finalizeRecorder = null;

		// First GC to trigger finalize()

		if (referenceType == ReferenceType.SOFT) {
			GCUtil.fullGC();
		}
		else {
			GCUtil.gc();
		}

		Assert.assertEquals(id, _finalizedIds.take());

		if (referenceType == ReferenceType.PHANTOM) {
			Assert.assertFalse(markFinalizeAction.isMarked());

			// Second GC to push reference enqueue

			GCUtil.gc();
		}

		if (threadEnabled) {
			waitUntilMarked(markFinalizeAction);
		}
		else {
			ReflectionTestUtil.invoke(
				FinalizeManager.class, "_pollingCleanup", new Class<?>[0]);
		}

		Assert.assertTrue(markFinalizeAction.isMarked());

		if (referenceType == ReferenceType.PHANTOM) {
			Assert.assertEquals(id, markFinalizeAction.getId());
		}
		else {
			Assert.assertNull(markFinalizeAction.getId());
		}

		Assert.assertNull(getReferent(reference));

		if (threadEnabled) {
			checkThreadState();
		}
	}

	private void checkThreadState() {
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

		long startTime = System.currentTimeMillis();

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

	private <T> T getReferent(Reference<T> reference) {
		try {
			return (T)ReflectionTestUtil.getFieldValue(reference, "referent");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void waitUntilMarked(MarkFinalizeAction markFinalizeAction)
		throws InterruptedException {

		long startTime = System.currentTimeMillis();

		while (!markFinalizeAction.isMarked() &&
			   ((System.currentTimeMillis() - startTime) < 10000)) {

			Thread.sleep(1);
		}

		Assert.assertTrue(markFinalizeAction.isMarked());
	}

	private static final String _THREAD_ENABLED_KEY =
		FinalizeManager.class.getName() + ".thread.enabled";

	private final BlockingQueue<String> _finalizedIds =
		new LinkedBlockingDeque<String>();

	private static enum ReferenceType {

		SOFT, WEAK, PHANTOM

	}

	private class FinalizeRecorder {

		public FinalizeRecorder(String id) {
			_id = id;
		}

		@Override
		protected void finalize() {
			_finalizedIds.offer(_id);
		}

		private final String _id;

	}

	private class MarkFinalizeAction implements FinalizeAction {

		@Override
		public void doFinalize(Reference<?> reference) {
			Object referent = getReferent(reference);

			if (referent instanceof FinalizeRecorder) {
				FinalizeRecorder finalizeRecorder = (FinalizeRecorder)referent;

				_id = finalizeRecorder._id;
			}

			_marked = true;
		}

		public String getId() {
			return _id;
		}

		public boolean isMarked() {
			return _marked;
		}

		private volatile String _id;
		private volatile boolean _marked;

	}

}