/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public interface ControllerBuildReport extends BuildReport {

	public TopLevelBuildReport getTopLevelBuildReport();

	public Map<String, String> getBuildParameters();

	public String getTestrayBuildDateString();

}