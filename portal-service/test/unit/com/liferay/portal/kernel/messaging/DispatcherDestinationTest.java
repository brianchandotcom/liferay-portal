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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.test.AssertUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class DispatcherDestinationTest {

	public static final int LISTENER_COUNT = 10000;

	public static final int REGISTER_TASK_COUNT = 10;

	public static final int TASK_ITERATION_COUNT = 100;

	public static final int UNREGISTER_TASK_COUNT = 10;

	@Before
	public void setUp() throws Exception {
		_executorService = Executors.newFixedThreadPool(
			REGISTER_TASK_COUNT + UNREGISTER_TASK_COUNT);

		_destination = new DummyDispatcherDestination();

		_destination.setName(DispatcherDestinationTest.class.getName());

		_listeners = new MessageListener[LISTENER_COUNT];

		for (int i = 0; i < _listeners.length; i++) {
			_listeners[i] = new DummyMessageListener();
		}

		_tasks = new ArrayList<Callable<Object>>();

		DestinationRegistrationTask registerTask =
			new DestinationRegistrationTask(
				_destination, _listeners, TASK_ITERATION_COUNT, true);

		for (int i = 0; i < REGISTER_TASK_COUNT; i++) {
			_tasks.add(registerTask);
		}

		DestinationRegistrationTask unregisterTask =
			new DestinationRegistrationTask(
				_destination, _listeners, TASK_ITERATION_COUNT, false);

		for (int i = 0; i < UNREGISTER_TASK_COUNT; i++) {
			_tasks.add(unregisterTask);
		}

		_startTime = System.currentTimeMillis();
	}

	@After
	public void tearDown() throws Exception {
		_executorService.shutdownNow();
		_executorService.awaitTermination(120, TimeUnit.SECONDS);

		long expectedTime = 100;
		long actualTime = System.currentTimeMillis() - _startTime;

		AssertUtils.assertLessThan(expectedTime, actualTime);
	}

	@Test
	public void testPerformance() throws Exception {
		_executorService.invokeAll(_tasks);
	}

	private BaseDestination _destination;
	private ExecutorService _executorService;
	private MessageListener[] _listeners;
	private long _startTime;
	private List<Callable<Object>> _tasks;

}