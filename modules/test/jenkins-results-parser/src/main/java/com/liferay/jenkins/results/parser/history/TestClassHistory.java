/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

/**
 * @author Michael Hashimoto
 */
public interface TestClassHistory {

	public long getAverageDuration();

	public long getAverageOverheadDuration();

	public BatchHistory getBatchHistory();

	public int getFailureCount();

	public int getStatusChanges();

	public long getTestCount();

	public String getTestName();

	public long getTestrayCaseResultID();

	public String getTestrayCaseResultURL();

	public TestTaskHistory getTestTaskHistory();

	public String getTestTaskName();

}