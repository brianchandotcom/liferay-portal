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
import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.io.File;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PluginsGulpAxisTestClassGroup extends AxisTestClassGroup {

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"test_base_dir",
			JenkinsResultsParserUtil.getCanonicalPath(getTestBaseDir()));

		return jsonObject;
	}

	@Override
	public File getTestBaseDir() {
		if (_testBaseDir != null) {
			return _testBaseDir;
		}

		List<TestClass> testClasses = getTestClasses();

		if (testClasses.isEmpty()) {
			return null;
		}

		TestClass testClass = testClasses.get(0);

		_testBaseDir = testClass.getTestClassFile();

		return _testBaseDir;
	}

	protected PluginsGulpAxisTestClassGroup(
		JSONObject jsonObject, SegmentTestClassGroup segmentTestClassGroup) {

		super(jsonObject, segmentTestClassGroup);

		String testBaseDirPath = jsonObject.optString("test_base_dir");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testBaseDirPath)) {
			_testBaseDir = new File(testBaseDirPath);
		}
	}

	protected PluginsGulpAxisTestClassGroup(
		PluginsGulpBatchTestClassGroup pluginsGulpBatchTestClassGroup) {

		super(pluginsGulpBatchTestClassGroup);
	}

	private File _testBaseDir;

}