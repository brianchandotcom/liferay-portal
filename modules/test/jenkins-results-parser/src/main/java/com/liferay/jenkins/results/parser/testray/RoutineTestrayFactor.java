/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class RoutineTestrayFactor extends BaseTestrayFactor {

	public TestrayRoutine getTestrayRoutine() {
		return _testrayRoutine;
	}

	protected RoutineTestrayFactor(
		JSONObject jsonObject, TestrayRoutine testrayRoutine) {

		super(jsonObject, testrayRoutine.getTestrayServer());

		_testrayRoutine = testrayRoutine;
	}

	private final TestrayRoutine _testrayRoutine;

}