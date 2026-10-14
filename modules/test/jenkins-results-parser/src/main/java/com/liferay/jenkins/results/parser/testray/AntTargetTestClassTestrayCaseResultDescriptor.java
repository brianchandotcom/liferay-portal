/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.BaseAntTargetTestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

/**
 * @author Michael Hashimoto
 */
public class AntTargetTestClassTestrayCaseResultDescriptor
	extends BaseTestClassTestrayCaseResultDescriptor<BaseAntTargetTestClass> {

	public AntTargetTestClassTestrayCaseResultDescriptor(
		AxisTestClassGroup axisTestClassGroup,
		BaseAntTargetTestClass baseAntTargetTestClass,
		TestrayCaseResultDescriptor parentTestrayCaseResultDescriptor) {

		super(
			axisTestClassGroup, parentTestrayCaseResultDescriptor,
			baseAntTargetTestClass);
	}

	@Override
	public String getName() {
		BaseAntTargetTestClass baseAntTargetTestClass = getTestClass();

		if (baseAntTargetTestClass == null) {
			return super.getName();
		}

		String antTargetName = baseAntTargetTestClass.getAntTargetName();

		if (JenkinsResultsParserUtil.isNullOrEmpty(antTargetName)) {
			return super.getName();
		}

		return JenkinsResultsParserUtil.combine(
			getBatchName(), "[", antTargetName, "]");
	}

	@Override
	public String getTeamName() {
		BaseAntTargetTestClass baseAntTargetTestClass = getTestClass();

		String teamName = baseAntTargetTestClass.getTestrayTeamName();

		if (JenkinsResultsParserUtil.isNullOrEmpty(teamName)) {
			return super.getTeamName();
		}

		return teamName;
	}

}