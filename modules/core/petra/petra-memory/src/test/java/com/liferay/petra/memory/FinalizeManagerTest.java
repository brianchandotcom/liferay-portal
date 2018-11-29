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

import com.liferay.petra.test.util.ThreadTestUtil;
import com.liferay.portal.kernel.test.GCUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;

import java.lang.ref.Reference;
import java.lang.reflect.Constructor;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

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

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testConstructor() {
		new FinalizeManager();
	}

	@Test
	public void testManualClear() throws Exception {
		Object object1 = new Object();

		MarkFinalizeAction markFinalizeAction1 = new MarkFinalizeAction();

		Reference<Object> reference1 = FinalizeManager.register(
			object1, markFinalizeAction1,
			FinalizeManager.WEAK_REFERENCE_FACTORY);

		Map<Object, FinalizeAction> finalizeActions =
			ReflectionTestUtil.getFieldValue(
				FinalizeManager.class, "_finalizeActions");

		Assert.assertEquals(
			markFinalizeAction1,
			finalizeActions.get(_newIdentityKey(reference1)));

		reference1.clear();

		Assert.assertNull(finalizeActions.get(_newIdentityKey(reference1)));

		object1 = null;

		Object object2 = new Object();

		MarkFinalizeAction markFinalizeAction2 = new MarkFinalizeAction();

		Reference<Object> reference2 = FinalizeManager.register(
			object2, markFinalizeAction2,
			FinalizeManager.WEAK_REFERENCE_FACTORY);

		Assert.assertEquals(
			markFinalizeAction2,
			finalizeActions.get(_newIdentityKey(reference2)));

		reference1.enqueue();

		object2 = null;

		GCUtil.gc(true);

		_waitUntilMarked(markFinalizeAction2);

		Assert.assertFalse(markFinalizeAction1.isMarked());
	}

	@Test
	public void testRegisterationIdentity() throws Exception {
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
	public void testRegisterPhantom() throws InterruptedException {
		doTestRegister(ReferenceType.PHANTOM);
	}

	@Test
	public void testRegisterSoft() throws InterruptedException {
		doTestRegister(ReferenceType.SOFT);
	}

	@Test
	public void testRegisterWeak() throws InterruptedException {
		doTestRegister(ReferenceType.WEAK);
	}

	protected void doTestRegister(ReferenceType referenceType)
		throws InterruptedException {

		String id = "testObject";

		FinalizeRecorder finalizeRecorder = new FinalizeRecorder(id);

		MarkFinalizeAction markFinalizeAction = new MarkFinalizeAction();

		FinalizeManager.ReferenceFactory referenceFactory =
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

		_waitUntilMarked(markFinalizeAction);

		Assert.assertTrue(markFinalizeAction.isMarked());

		if (referenceType == ReferenceType.PHANTOM) {
			Assert.assertEquals(id, markFinalizeAction.getId());
		}
		else {
			Assert.assertNull(markFinalizeAction.getId());
		}

		if (referenceType != ReferenceType.PHANTOM) {
			Assert.assertNull(_getReferent(reference));
		}

		_checkThreadState();
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

		for (Thread thread : ThreadTestUtil.getThreads()) {
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