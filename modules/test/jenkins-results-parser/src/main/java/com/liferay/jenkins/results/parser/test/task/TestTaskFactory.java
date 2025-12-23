/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.task;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class TestTaskFactory {

	public static TestTask newTestTask(String name, long averageDuration) {
		if (_testTasks.containsKey(name)) {
			return _testTasks.get(name);
		}

		TestTask testTask = new DefaultTestTask(name, averageDuration);

		_testTasks.put(name, testTask);

		return testTask;
	}

	private static final Map<String, TestTask> _testTasks = new HashMap<>();

}