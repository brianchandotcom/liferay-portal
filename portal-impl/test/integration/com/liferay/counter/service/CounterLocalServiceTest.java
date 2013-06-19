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

package com.liferay.counter.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class CounterLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		CounterLocalServiceUtil.reset(_COUNTER_NAME);
	}

	@Test
	public void testConcurrentIncrement() throws Exception {
		int processNumber = 4;
		int incrementCount = 10000;

		String classPath = ClassPathUtil.getJVMClassPath(true);
		List<String> jvmArguments = Arrays.asList(
			"-Xmx1024m", "-XX:MaxPermSize=200m");

		List<Future<Long[]>> futureResults = new ArrayList<Future<Long[]>>();

		for (int i = 0; i < processNumber; i++) {
			ProcessCallable<Long[]> processCallable =
				new IncrementProcessCallable(
					"Increment Process-" + i, _COUNTER_NAME, incrementCount);

			Future<Long[]> result = ProcessExecutor.execute(
				classPath, jvmArguments, processCallable);

			futureResults.add(result);
		}

		int totalIdCount = processNumber * incrementCount;

		List<Long> ids = new ArrayList<Long>(totalIdCount);

		for (Future<Long[]> futureResult : futureResults) {
			ids.addAll(Arrays.asList(futureResult.get()));
		}

		Assert.assertEquals(totalIdCount, ids.size());

		Collections.sort(ids);

		for (int i = 0; i < totalIdCount; i++) {
			Assert.assertEquals(i + 1, ids.get(i).intValue());
		}
	}

	private static String _COUNTER_NAME = "COUNTER_NAME";

	private static class IncrementProcessCallable
		implements ProcessCallable<Long[]> {

		public IncrementProcessCallable(
			String processName, String counterName, int incrementCount) {

			_processName = processName;
			_counterName = counterName;
			_incrementCount = incrementCount;
		}

		@Override
		public Long[] call() throws ProcessException {
			System.setProperty("catalina.base", ".");

			InitUtil.initWithSpring();

			PropsUtil.set(PropsValues.COUNTER_INCREMENT + _COUNTER_NAME, "1");

			List<Long> ids = new ArrayList<Long>();

			try {
				for (int i = 0; i < _incrementCount; i++) {
					ids.add(CounterLocalServiceUtil.increment(_counterName));
				}
			}
			catch (SystemException se) {
				throw new ProcessException(se);
			}

			return ids.toArray(new Long[ids.size()]);
		}

		@Override
		public String toString() {
			return _processName;
		}

		private static final long serialVersionUID = 1L;

		private String _counterName;
		private int _incrementCount;
		private String _processName;

	}

}