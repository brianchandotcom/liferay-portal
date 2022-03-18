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
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassFactory;

import java.io.File;
import java.io.IOException;

import java.nio.file.PathMatcher;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Yi-Chen Tsai
 */
public class RESTBuilderModulesBatchTestClassGroup
	extends ModulesBatchTestClassGroup {

	@Override
	public int getAxisCount() {
		if (!isStableTestSuiteBatch() && testRelevantIntegrationUnitOnly) {
			return 0;
		}

		if ((_buildType == BuildType.FULL) || testClasses.isEmpty()) {
			return 1;
		}

		return super.getAxisCount();
	}

	public BuildType getBuildType() {
		return _buildType;
	}

	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("build_type", _buildType);

		return jsonObject;
	}

	public static enum BuildType {

		FULL

	}

	protected RESTBuilderModulesBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);

		_buildType = BuildType.valueOf(jsonObject.getString("build_type"));
	}

	protected RESTBuilderModulesBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected void setAxisTestClassGroups() {
		int testClassCount = testClasses.size();

		int axisCount = getAxisCount();

		if ((testClassCount == 0) && (axisCount == 1)) {
			axisTestClassGroups.add(
				0, TestClassGroupFactory.newAxisTestClassGroup(this));

			return;
		}

		super.setAxisTestClassGroups();
	}

	@Override
	protected void setTestClasses() throws IOException {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		File portalModulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		List<PathMatcher> excludesPathMatchers = getPathMatchers(
			getExcludesJobProperties());
		List<PathMatcher> includesPathMatchers = getPathMatchers(
			getIncludesJobProperties());

		if (testRelevantChanges &&
			!(includeStableTestSuite && isStableTestSuiteBatch())) {

			List<File> modifiedFiles =
				portalGitWorkingDirectory.getModifiedFilesList();

			List<File> modifiedPortalToolsRESTBuilderFiles =
				JenkinsResultsParserUtil.getIncludedFiles(
					null,
					getPathMatchers(
						"util/portal-tools-rest-builder/**",
						portalModulesBaseDir),
					modifiedFiles);

			if (!modifiedPortalToolsRESTBuilderFiles.isEmpty()) {
				_buildType = BuildType.FULL;

				return;
			}

			moduleDirsList.addAll(
				portalGitWorkingDirectory.getModifiedModuleDirsList(
					excludesPathMatchers, includesPathMatchers));
		}
		else {
			_buildType = BuildType.FULL;

			moduleDirsList.addAll(
				portalGitWorkingDirectory.getModuleDirsList(
					excludesPathMatchers, includesPathMatchers));
		}

		for (File moduleDir : moduleDirsList) {
			TestClass testClass = TestClassFactory.newTestClass(
				this, moduleDir);

			if (!testClass.hasTestClassMethods()) {
				continue;
			}

			testClasses.add(testClass);
		}

		Collections.sort(testClasses);
	}

	private BuildType _buildType;

}