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

package com.liferay.portal.kernel.test.junit;

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.io.Serializable;

import java.util.Map;

import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

/**
 * @author Shuyang Zhou
 */
public class BridgeJUnitTestRunner extends BlockJUnit4ClassRunner {

	public static RunNotifier getRunNotifier(Class<?> clazz) {
		return _runNotifiers.get(clazz);
	}

	public static Result runBridgeTests(
		BridgeRunListener bridgeRunListener, Class<?>... testClasses) {

		JUnitCore jUnitCore = new JUnitCore();

		jUnitCore.addListener(bridgeRunListener);

		return jUnitCore.run(testClasses);
	}

	public BridgeJUnitTestRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	public void run(RunNotifier runNotifier) {
		TestClass testClass = getTestClass();

		_runNotifiers.put(testClass.getJavaClass(), runNotifier);

		super.run(runNotifier);
	}

	public static class BridgeRunListener
		extends RunListener implements Serializable {

		public BridgeRunListener(Class<?> testClass) {
			this.testClass = testClass;
		}

		@Override
		public void testAssumptionFailure(Failure failure) {
			bridge("fireTestAssumptionFailed", failure);
		}

		@Override
		public void testFailure(Failure failure) {
			bridge("fireTestFailure", failure);
		}

		@Override
		public void testFinished(Description description) {
			bridge("fireTestFinished", description);
		}

		@Override
		public void testIgnored(Description description) {
			bridge("fireTestIgnored", description);
		}

		@Override
		public void testRunFinished(Result result) {
			bridge("fireTestRunFinished", result);
		}

		@Override
		public void testRunStarted(Description description) {
			bridge("fireTestRunStarted", description);
		}

		@Override
		public void testStarted(Description description) {
			bridge("fireTestStarted", description);
		}

		protected void bridge(String methodName, Object argument) {
			ReflectionTestUtil.invoke(
				getRunNotifier(testClass), methodName,
				new Class<?>[] {argument.getClass()}, argument);
		}

		protected final Class<?> testClass;

		private static final long serialVersionUID = 1L;

	}

	private static final Map<Class<?>, RunNotifier> _runNotifiers =
		new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}