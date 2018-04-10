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

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Properties;

/**
 * @author Yi-Chen Tsai
 */
public class FunctionalBatchTestClassGroup extends BatchTestClassGroup {

	@Override
	public AxisTestClassGroup getAxisTestClassGroup(int axisId) {
		if (axisId != 0) {
			throw new IllegalArgumentException("axisId is not 0");
		}

		return super.getAxisTestClassGroup(axisId);
	}

	public String getRelevantTestBatchRunPropertyQuery() {
		return _relevantTestBatchRunPropertyQuery;
	}

	protected FunctionalBatchTestClassGroup(
		String batchName, PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		super(batchName, portalGitWorkingDirectory, testSuiteName);

		axisTestClassGroups.put(0, new AxisTestClassGroup(this, 0));

		_setRelevantTestBatchRunPropertyQuery();
	}

	private String _getDefaultTestBatchRunPropertyQuery() {
		return getFirstPropertyValue("test.batch.run.property.query");
	}

	private void _setRelevantTestBatchRunPropertyQuery() {
		if (!testRelevantChanges) {
			_relevantTestBatchRunPropertyQuery =
				_getDefaultTestBatchRunPropertyQuery();

			return;
		}

		List<File> modifiedModuleDirsList = null;

		try {
			modifiedModuleDirsList =
				portalGitWorkingDirectory.getModifiedModuleDirsList();
		}
		catch (IOException ioe) {
			File workingDirectory =
				portalGitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get module directories in ",
					workingDirectory.getPath()),
				ioe);
		}

		StringBuilder sb = new StringBuilder();

		for (File modifiedModuleDir : modifiedModuleDirsList) {
			File modifiedModuleTestProperties = new File(
				modifiedModuleDir, "test.properties");

			if (!modifiedModuleTestProperties.exists()) {
				continue;
			}

			Properties testProperties = JenkinsResultsParserUtil.getProperties(
				modifiedModuleTestProperties);

			String testBatchRunPropertyQuery = testProperties.getProperty(
				JenkinsResultsParserUtil.combine(
					"test.batch.run.property.query[", batchName, "][",
					testSuiteName, "]"));

			if (testBatchRunPropertyQuery != null) {
				if (sb.length() > 0) {
					sb.append(" OR (");
				}
				else {
					sb.append("(");
				}

				sb.append(testBatchRunPropertyQuery);
				sb.append(")");
			}
		}

		if (sb.length() == 0) {
			_relevantTestBatchRunPropertyQuery =
				_getDefaultTestBatchRunPropertyQuery();

			return;
		}

		_relevantTestBatchRunPropertyQuery = sb.toString();
	}

	private String _relevantTestBatchRunPropertyQuery;

}