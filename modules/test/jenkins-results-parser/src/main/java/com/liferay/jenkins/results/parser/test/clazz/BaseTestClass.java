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

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.BatchHistory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.TestHistory;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestClass implements TestClass {

	@Override
	public int compareTo(TestClass testClass) {
		if (testClass == null) {
			throw new NullPointerException("Test class is null");
		}

		return _testClassFile.compareTo(testClass.getTestClassFile());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestClass)) {
			return false;
		}

		if (Objects.equals(hashCode(), object.hashCode())) {
			return true;
		}

		return false;
	}

	@Override
	public long getAverageDuration() {
		if (_averageDuration != null) {
			return _averageDuration;
		}

		BatchTestClassGroup batchTestClassGroup = getBatchTestClassGroup();

		_averageDuration = batchTestClassGroup.getAverageTestDuration(
			getTestName());

		return _averageDuration;
	}

	@Override
	public long getAverageOverheadDuration() {
		if (_averageOverheadDuration != null) {
			return _averageOverheadDuration;
		}

		BatchTestClassGroup batchTestClassGroup = getBatchTestClassGroup();

		_averageOverheadDuration =
			batchTestClassGroup.getAverageTestOverheadDuration(getTestName());

		return _averageOverheadDuration;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("average_duration", getAverageDuration());
		jsonObject.put(
			"average_overhead_duration", getAverageOverheadDuration());
		jsonObject.put("file", getTestClassFile());
		jsonObject.put("ignored", isIgnored());

		JSONArray methodsJSONArray = new JSONArray();

		for (TestClassMethod testClassMethod : getTestClassMethods()) {
			methodsJSONArray.put(testClassMethod.getJSONObject());
		}

		jsonObject.put("methods", methodsJSONArray);

		jsonObject.put("name", getName());

		return jsonObject;
	}

	@Override
	public String getName() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		return JenkinsResultsParserUtil.getPathRelativeTo(
			getTestClassFile(),
			portalGitWorkingDirectory.getWorkingDirectory());
	}

	@Override
	public File getTestClassFile() {
		return _testClassFile;
	}

	@Override
	public List<TestClassMethod> getTestClassMethods() {
		return _testClassMethods;
	}

	@Override
	public TestHistory getTestHistory() {
		if (_testHistory != null) {
			return _testHistory;
		}

		BatchTestClassGroup batchTestClassGroup = getBatchTestClassGroup();

		BatchHistory batchHistory = batchTestClassGroup.getBatchHistory();

		_testHistory = batchHistory.getTestHistory(getTestName());

		return _testHistory;
	}

	@Override
	public int hashCode() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.hashCode();
	}

	@Override
	public boolean hasTestClassMethods() {
		List<TestClassMethod> testClassMethods = getTestClassMethods();

		if ((testClassMethods == null) || testClassMethods.isEmpty()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isIgnored() {
		return false;
	}

	protected BaseTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile) {

		_batchTestClassGroup = batchTestClassGroup;
		_testClassFile = testClassFile;
	}

	protected BaseTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		_batchTestClassGroup = batchTestClassGroup;

		_testClassFile = new File(jsonObject.getString("file"));

		JSONArray methodsJSONArray = jsonObject.getJSONArray("methods");

		if ((methodsJSONArray != null) && !methodsJSONArray.isEmpty()) {
			for (int i = 0; i < methodsJSONArray.length(); i++) {
				JSONObject methodJSONObject = methodsJSONArray.getJSONObject(i);

				if (methodJSONObject == null) {
					continue;
				}

				_testClassMethods.add(
					TestClassFactory.newTestClassMethod(
						methodJSONObject, this));
			}
		}
	}

	protected void addTestClassMethod(
		boolean methodIgnored, String methodName) {

		addTestClassMethod(
			TestClassFactory.newTestClassMethod(
				methodIgnored, methodName, this));
	}

	protected void addTestClassMethod(String methodName) {
		addTestClassMethod(false, methodName);
	}

	protected void addTestClassMethod(TestClassMethod testClassMethod) {
		_testClassMethods.add(testClassMethod);
	}

	protected BatchTestClassGroup getBatchTestClassGroup() {
		return _batchTestClassGroup;
	}

	protected PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return _batchTestClassGroup.getPortalGitWorkingDirectory();
	}

	protected File getPortalWorkingDirectory() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		return portalGitWorkingDirectory.getWorkingDirectory();
	}

	protected String getTestName() {
		return getName();
	}

	private Long _averageDuration;
	private Long _averageOverheadDuration;
	private final BatchTestClassGroup _batchTestClassGroup;
	private final File _testClassFile;
	private final List<TestClassMethod> _testClassMethods = new ArrayList<>();
	private TestHistory _testHistory;

}