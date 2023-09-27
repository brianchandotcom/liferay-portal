/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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