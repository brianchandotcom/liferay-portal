/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.task;

import com.liferay.jenkins.results.parser.history.TestTaskHistory;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

/**
 * @author Michael Hashimoto
 */
public class DefaultTestTask extends BaseTestTask {

	protected DefaultTestTask(
		TestClassGroup.GroupingStrategy groupingStrategy, String name,
		TestTaskHistory testTaskHistory) {

		super(groupingStrategy, name, testTaskHistory);
	}

}