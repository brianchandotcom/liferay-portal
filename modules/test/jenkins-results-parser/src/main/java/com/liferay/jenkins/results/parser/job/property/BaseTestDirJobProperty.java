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