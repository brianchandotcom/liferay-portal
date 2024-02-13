/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;

import java.io.File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class PlaywrightTestClass extends BaseTestClass {

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("spec_title", _specTitle);

		return jsonObject;
	}

	@Override
	public String getName() {
		return JenkinsResultsParserUtil.combine(
			getSpecFilePath(), " > ", getSpecTitle());
	}

	public String getSpecFilePath() {
		Matcher matcher = _testFilePathPattern.matcher(
			JenkinsResultsParserUtil.getCanonicalPath(getTestClassFile()));

		if (!matcher.find()) {
			return null;
		}

		return matcher.group("specFilePath");
	}

	public String getSpecTitle() {
		return _specTitle;
	}

	protected PlaywrightTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile,
		String specTitle) {

		super(batchTestClassGroup, testClassFile);

		_specTitle = specTitle;
	}

	protected PlaywrightTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);

		_specTitle = jsonObject.getString("spec_title");
	}

	private static final Pattern _testFilePathPattern = Pattern.compile(
		".+/test/playwright/(?<specFilePath>.+)");

	private final String _specTitle;

}