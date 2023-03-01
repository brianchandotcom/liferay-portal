/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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