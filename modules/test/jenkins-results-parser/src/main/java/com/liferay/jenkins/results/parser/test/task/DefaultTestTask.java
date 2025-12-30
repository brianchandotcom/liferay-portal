/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.task;

/**
 * @author Michael Hashimoto
 */
public class DefaultTestTask extends BaseTestTask {

	protected DefaultTestTask(
		long averageDuration, long averageTotalDuration, long longestDuration,
		String name) {

		super(averageDuration, averageTotalDuration, longestDuration, name);
	}

}