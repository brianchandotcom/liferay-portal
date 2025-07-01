/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.testray.TestrayBuild;

/**
 * @author Michael Hashimoto
 */
public class TestrayTopLevelBuildReport extends URLTopLevelBuildReport {

	protected TestrayTopLevelBuildReport(TestrayBuild testrayBuild) {
		super(String.valueOf(testrayBuild.getTopLevelBuildURL()));

		_startYearMonth = testrayBuild.getStartYearMonth();
	}

	@Override
	protected String getStartYearMonth() {
		return _startYearMonth;
	}

	private final String _startYearMonth;

}