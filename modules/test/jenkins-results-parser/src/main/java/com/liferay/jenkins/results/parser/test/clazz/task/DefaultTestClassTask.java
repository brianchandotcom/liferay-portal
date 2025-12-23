/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.task;

/**
 * @author Michael Hashimoto
 */
public class DefaultTestClassTask extends BaseTestClassTask {

	protected DefaultTestClassTask(String taskName, long averageDuration) {
		super(taskName, averageDuration);
	}

}