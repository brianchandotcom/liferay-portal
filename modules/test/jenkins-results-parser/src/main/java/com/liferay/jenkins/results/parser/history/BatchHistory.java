/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import com.liferay.jenkins.results.parser.TestHistory;
import com.liferay.jenkins.results.parser.TestTaskHistory;

/**
 * @author Michael Hashimoto
 */
public interface BatchHistory {

	public long getAverageDuration();

	public String getBatchName();

	public JobHistory getJobHistory();

	public TestHistory getTestHistory(String key);

	public TestTaskHistory getTestTaskHistory(String key);

}