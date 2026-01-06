/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.task;

import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

/**
 * @author Michael Hashimoto
 */
public class TestTaskFactory {

	public static TestTask newTestTask(
		long averageDuration, long averageTotalDuration,
		TestClassGroup.GroupingStrategy groupingStrategy,
		boolean latestReportMissing, long longestDuration, String name) {

		return new DefaultTestTask(
			averageDuration, averageTotalDuration, groupingStrategy,
			latestReportMissing, longestDuration, name);
	}

}