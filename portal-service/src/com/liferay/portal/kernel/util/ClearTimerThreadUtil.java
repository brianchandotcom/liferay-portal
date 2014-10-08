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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class ClearTimerThreadUtil {

	public static void clearTimerThread() throws Exception {
		if (!_INITIALIZED) {
			return;
		}

		Thread[] threads = ThreadUtil.getThreads();

		for (Thread thread : threads) {
			if (thread == null) {
				continue;
			}

			Class<?> threadClass = thread.getClass();

			String threadClassName = threadClass.getName();

			if (!threadClassName.equals("java.util.TimerThread")) {
				continue;
			}

			Object queue = _QUEUE_FIELD.get(thread);

			synchronized (queue) {
				_NEW_TASKS_MAY_BE_SCHEDULED_FIELD.setBoolean(thread, false);

				_CLEAR_METHOD.invoke(queue);

				queue.notify();
			}
		}
	}

	private static final Method _CLEAR_METHOD;

	private static final boolean _INITIALIZED;

	private static final Field _NEW_TASKS_MAY_BE_SCHEDULED_FIELD;

	private static final Field _QUEUE_FIELD;

	private static final Log _log = LogFactoryUtil.getLog(
		ClearTimerThreadUtil.class);

	static {
		Method clearMethod = null;
		Field newTasksMayBeScheduledField = null;
		Field queueField = null;

		boolean initialized = false;

		try {
			Class<?> taskQueueClass = Class.forName("java.util.TaskQueue");

			clearMethod = ReflectionUtil.getDeclaredMethod(
				taskQueueClass, "clear");

			Class<?> timeThreadClass = Class.forName("java.util.TimerThread");

			newTasksMayBeScheduledField = ReflectionUtil.getDeclaredField(
				timeThreadClass, "newTasksMayBeScheduled");
			queueField = ReflectionUtil.getDeclaredField(
				timeThreadClass, "queue");

			initialized = true;
		}
		catch (Throwable t) {
			if (_log.isWarnEnabled()) {
				_log.warn("Failed to initialize ClearTimerThreadUtil");
			}
		}

		_CLEAR_METHOD = clearMethod;

		_NEW_TASKS_MAY_BE_SCHEDULED_FIELD = newTasksMayBeScheduledField;

		_QUEUE_FIELD = queueField;

		_INITIALIZED = initialized;
	}

}