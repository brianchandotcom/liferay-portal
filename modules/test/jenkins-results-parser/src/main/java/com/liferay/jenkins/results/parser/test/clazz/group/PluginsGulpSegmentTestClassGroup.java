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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class PluginsGulpSegmentTestClassGroup extends SegmentTestClassGroup {

	public File getTestBaseDirName() {
		List<TestClass> testClasses = getTestClasses();

		if (testClasses.isEmpty()) {
			return null;
		}

		TestClass testClass = testClasses.get(0);

		return testClass.getTestClassFile();
	}

	@Override
	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.getTestCasePropertiesContent());
		sb.append("\n");

		File testBaseDirName = getTestBaseDirName();

		if ((testBaseDirName == null) || !testBaseDirName.exists()) {
			return sb.toString();
		}

		sb.append("TEST_BASE_DIR_NAME=");
		sb.append(JenkinsResultsParserUtil.getCanonicalPath(testBaseDirName));
		sb.append("\n");

		return sb.toString();
	}

	protected PluginsGulpSegmentTestClassGroup(
		PluginsGulpBatchTestClassGroup parentPluginsGulpBatchTestClassGroup) {

		super(parentPluginsGulpBatchTestClassGroup);
	}

}