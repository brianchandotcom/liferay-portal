/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.task;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class TestClassTaskFactory {

	public static TestClassTask newTestClassTask(
		String taskName, long averageDuration) {

		if (_testClassTasks.containsKey(taskName)) {
			return _testClassTasks.get(taskName);
		}

		TestClassTask testClassTask = new DefaultTestClassTask(
			taskName, averageDuration);

		_testClassTasks.put(taskName, testClassTask);

		return testClassTask;
	}

	private static final Map<String, TestClassTask> _testClassTasks =
		new HashMap<>();

}