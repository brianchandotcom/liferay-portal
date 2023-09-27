/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestClassResult;
import com.liferay.jenkins.results.parser.TestResult;
import com.liferay.jenkins.results.parser.TopLevelBuild;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.io.IOException;

/**
 * @author Michael Hashimoto
 */
public class PortalLogTestrayCaseResult extends BatchBuildTestrayCaseResult {

	public PortalLogTestrayCaseResult(
		TestrayBuild testrayBuild, TopLevelBuild topLevelBuild,
		AxisTestClassGroup axisTestClassGroup) {

		super(testrayBuild, topLevelBuild, axisTestClassGroup);
	}

	@Override
	public String getComponentName() {
		try {
			return JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(),
				"testray.case.component");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String getErrors() {
		Build build = getBuild();

		if ((build == null) || !build.isFailing()) {
			return null;
		}

		String result = build.getResult();

		if (result == null) {
			return null;
		}

		for (TestClassResult testClassResult : build.getTestClassResults()) {
			String className = testClassResult.getClassName();

			if (!className.equals(
					"com.liferay.portal.log.assertor.PortalLogAssertorTest")) {

				continue;
			}

			StringBuilder sb = new StringBuilder();

			for (TestResult testResult : testClassResult.getTestResults()) {
				if (!testResult.isFailing()) {
					continue;
				}

				sb.append("PortalLogAssertorTest#");
				sb.append(testResult.getTestName());
				sb.append(": ");

				String errorDetails = testResult.getErrorDetails();

				if (JenkinsResultsParserUtil.isNullOrEmpty(errorDetails)) {
					sb.append("Failed for unknown reason | ");
				}
				else {
					errorDetails = errorDetails.replace(
						"Portal log assert failure, see above log for more " +
							"information:",
						"");

					errorDetails = errorDetails.trim();

					if (errorDetails.length() > 1000) {
						errorDetails = errorDetails.substring(0, 1000);

						errorDetails += "...";
					}

					sb.append(errorDetails);
					sb.append(" | ");
				}
			}

			if (sb.length() > 0) {
				sb.setLength(sb.length() - 3);

				return sb.toString();
			}
		}

		return null;
	}

	@Override
	public String getName() {
		return "PortalLogAssertorTest-" + getAxisName();
	}

}