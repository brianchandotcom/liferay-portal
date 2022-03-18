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
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassFactory;

import java.io.File;
import java.io.IOException;

import java.nio.file.PathMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JSUnitModulesBatchTestClassGroup
	extends ModulesBatchTestClassGroup {

	public boolean testGitrepoJSUnit() {
		JobProperty jobProperty = getJobProperty("test.gitrepo.js.unit");

		String jobPropertyValue = jobProperty.getValue();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue) &&
			jobPropertyValue.equals("true")) {

			recordJobProperty(jobProperty);

			return true;
		}

		return false;
	}

	protected JSUnitModulesBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);
	}

	protected JSUnitModulesBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected void setTestClasses() throws IOException {
		List<File> moduleDirs = new ArrayList<>();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		List<PathMatcher> excludesPathMatchers = getPathMatchers(
			getExcludesJobProperties());
		List<PathMatcher> includesPathMatchers = getPathMatchers(
			getIncludesJobProperties());

		if (testRelevantChanges) {
			moduleDirs.addAll(
				portalGitWorkingDirectory.getModifiedModuleDirsList(
					excludesPathMatchers, includesPathMatchers));
		}
		else {
			moduleDirs.addAll(
				portalGitWorkingDirectory.getModuleDirsList(
					excludesPathMatchers, includesPathMatchers));
		}

		for (File moduleDir : moduleDirs) {
			TestClass testClass = TestClassFactory.newTestClass(
				this, moduleDir);

			if (!testClass.hasTestClassMethods()) {
				continue;
			}

			testClasses.add(testClass);
		}

		Collections.sort(testClasses);
	}

}