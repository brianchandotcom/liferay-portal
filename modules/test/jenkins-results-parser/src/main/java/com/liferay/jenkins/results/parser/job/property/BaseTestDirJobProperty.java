/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.job.property;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;

import java.io.File;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestDirJobProperty
	extends BaseJobProperty implements TestDirJobProperty {

	@Override
	public File getTestBaseDir() {
		return _testBaseDir;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.toString());

		File testBaseDir = getTestBaseDir();

		if (testBaseDir != null) {
			sb.append("_");
			sb.append(JenkinsResultsParserUtil.getCanonicalPath(testBaseDir));
		}

		return sb.toString();
	}

	protected BaseTestDirJobProperty(
		Job job, Type type, File testBaseDir, String basePropertyName,
		boolean useBasePropertyName, String testSuiteName,
		String testBatchName) {

		super(
			job, type, basePropertyName, useBasePropertyName, testSuiteName,
			testBatchName);

		_testBaseDir = testBaseDir;
	}

	private final File _testBaseDir;

}