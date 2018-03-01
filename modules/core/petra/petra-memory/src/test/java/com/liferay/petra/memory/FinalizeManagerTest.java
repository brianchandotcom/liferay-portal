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

package com.liferay.petra.memory;

import com.liferay.petra.memory.FinalizeManager.ReferenceFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.GCUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.util.ThreadUtil;

import java.lang.ref.Reference;
import java.lang.reflect.Constructor;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class FinalizeManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, NewEnvTestRule.INSTANCE);

	@After
	public void tearDown() {
		System.clearProperty(_THREAD_ENABLED_KEY);
	}

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testBadFinalizeAction() {
		final RuntimeException runtimeException = new RuntimeException();

		Reference<Object> reference = FinalizeManager.register(
			new Object(),
			new FinalizeAction() {

				@Override
				public void doFinalize(Reference<?> reference) {
					Assert.assertNotNull(_getReferent(reference));

					throw runtimeException;
				}

			},
			FinalizeManager.PHANTOM_REFERENCE_FACTORY);

		Assert.assertNotNull(_getReferent(reference));

		reference.enqueue();

		try {
			ReflectionTestUtil.invoke(
				FinalizeManager.class, "_pollingCleanup", new Class<?>[0]);

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertSame(runtimeException, e);
		}

		Assert.assertNull(_getReferent(reference));
	}

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testConstructor() {
		new FinalizeManager();
	}

	@Test
	public void testManualClear() throws Exception {
		System.setProperty(_THREAD_ENABLED_KEY, StringPool.FALSE);

		Object object = new Object();

		MarkFinalizeAction markFinalizeAction = new MarkFinalizeAction();

		Reference<Object> reference = FinalizeManager.register(
			object, markFinalizeAction, FinalizeManager.WEAK_REFERENCE_FACTORY);

		Map<Object, FinalizeAction> finalizeActions =
			ReflectionTestUtil.getFieldValue(
				FinalizeManager.class, "_finalizeActions");

		Assert.assertEquals(
			markFinalizeAction,
			finalizeActions.get(_newIdentityKey(reference)));

		reference.clear();

		Assert.assertNull(finalizeActions.get(_newIdentityKey(reference)));

		object = null;

		GCUtil.gc(true);

		ReflectionTestUtil.invoke(
			FinalizeManager.class, "_pollingCleanup", new Class<?>[0]);

		Assert.assertFalse(markFinalizeAction.isMarked());

		ReflectionTestUtil.invoke(
			FinalizeManager.class, "_finalizeReference",
			new Class<?>[] {Reference.class}, reference);

		Assert.assertFalse(markFinalizeAction.isMarked());
	}

	@Test
	public void testRegisterationIdentity() throws Exception {
		System.setProperty(_THREAD_ENABLED_KEY, StringPool.FALSE);

		String testString = new String("testString");

		MarkFinalizeAction markFinalizeAction = new MarkFinalizeAction();

		Reference<?> reference1 = FinalizeManager.register(
			testString, markFinalizeAction,
			FinalizeManager.SOFT_REFERENCE_FACTORY);

		Map<Object, FinalizeAction> finalizeActions =
			ReflectionTestUtil.getFieldValue(
				FinalizeManager.class, "_finalizeActions");

		Assert.assertEquals(
			finalizeActions.toString(), 1, finalizeActions.size());
		Assert.assertTrue(
			finalizeActions.containsKey(_newIdentityKey(reference1)));

		Reference<?> reference2 = FinalizeManager.register(
			testString, markFinalizeAction,
			FinalizeManager.SOFT_REFERENCE_FACTORY);

		Assert.assertEquals(reference1, reference2);
		Assert.assertNotSame(reference1, reference2);

		Assert.assertEquals(
			finalizeActions.toString(), 2, finalizeActions.size());
		Assert.assertTrue(
			finalizeActions.containsKey(_newIdentityKey(reference1)));
		Assert.assertTrue(
			finalizeActions.containsKey(_newIdentityKey(reference2)));

		reference2.clear();

		Assert.assertEquals(
			finalizeActions.toString(), 1, finalizeActions.size());
		Assert.assertTrue(
			finalizeActions.containsKey(_newIdentityKey(reference1)));

		reference2 = FinalizeManager.register(
			new String(testString), markFinalizeAction,
			FinalizeManager.SOFT_REFERENCE_FACTORY);

		Assert.assertEquals(
			finalizeActions.toString(), 2, finalizeActions.size());
		Assert.assertTrue(
			finalizeActions.containsKey(_newIdentityKey(reference1)));
		Assert.assertTrue(
			finalizeActions.containsKey(_newIdentityKey(reference2)));

		reference2.clear();

		Assert.assertEquals(
			finalizeActions.toString(), 1, finalizeActions.size());
		Assert.assertTrue(
			finalizeActions.containsKey(_newIdentityKey(reference1)));

		reference1.clear();

		Assert.assertTrue(
			finalizeActions.toString(), finalizeActions.isEmpty());
	}

	@Test
	public void testRegisterPhantomWithoutThread() throws InterruptedException {
		doTestRegister(false, ReferenceType.PHANTOM);
	}

	@Test
	public void testRegisterPhantomWithThread() throws InterruptedException {
		doTestRegister(true, ReferenceType.PHANTOM);
	}

	@Test
	public void testRegisterSoftWithoutThread() throws InterruptedException {
		doTestRegister(false, ReferenceType.SOFT);
	}

	@Test
	public void testRegisterSoftWithThread() throws InterruptedException {
		doTestRegister(true, ReferenceType.SOFT);
	}

	@Test
	public void testRegisterWeakWithoutThread() throws InterruptedException {
		doTestRegister(false, ReferenceType.WEAK);
	}

	@Test
	public void testRegisterWeakWithThread() throws InterruptedException {
		doTestRegister(true, ReferenceType.WEAK);
	}

	protected void doTestRegister(
			boolean threadEnabled, ReferenceType referenceType)
		throws InterruptedException {

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

		// First GC to trigger Object#finalize

		if (referenceType == ReferenceType.PHANTOM) {
			GCUtil.gc(false);
		}
		else if (referenceType == ReferenceType.SOFT) {
			GCUtil.fullGC(true);
		}
		else {
			GCUtil.gc(true);
		}

		Assert.assertEquals(id, _finalizedIds.take());

		if (referenceType == ReferenceType.PHANTOM) {
			Assert.assertFalse(markFinalizeAction.isMarked());

			// Second GC to trigger ReferenceQueue#enqueue

			GCUtil.gc(false);
		}

		if (threadEnabled) {
			_waitUntilMarked(markFinalizeAction);
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

		if (!threadEnabled || (referenceType != ReferenceType.PHANTOM)) {
			Assert.assertNull(_getReferent(reference));
		}

		if (threadEnabled) {
			_checkThreadState();
		}
	}

	private static Object _newIdentityKey(Reference<?> reference)
		throws Exception {

		ClassLoader classLoader = FinalizeManager.class.getClassLoader();

		Class<?> identityKeyClass = classLoader.loadClass(
			FinalizeManager.class.getName() + "$IdentityKey");

		Constructor<?> constructor = identityKeyClass.getDeclaredConstructor(
			Reference.class);

		constructor.setAccessible(true);

		return constructor.newInstance(reference);
	}

	private void _checkThreadState() {
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
			Assert.assertTrue(
				"Timeout on waiting finialize thread to enter waiting state",
				(System.currentTimeMillis() - startTime) <= 10000);
		}

		// Interrupt to wake up

		finalizeThread.interrupt();

		// Second waiting state

		while (finalizeThread.getState() != Thread.State.WAITING) {
			Assert.assertTrue(
				"Timeout on waiting finialize thread to enter waiting state",
				(System.currentTimeMillis() - startTime) <= 10000);
		}
	}

	private <T> T _getReferent(Reference<T> reference) {
		return ReflectionTestUtil.getFieldValue(reference, "referent");
	}

	private void _waitUntilMarked(MarkFinalizeAction markFinalizeAction)
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
		new LinkedBlockingDeque<>();

	private static enum ReferenceType {

		PHANTOM, SOFT, WEAK

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
			Object referent = _getReferent(reference);

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