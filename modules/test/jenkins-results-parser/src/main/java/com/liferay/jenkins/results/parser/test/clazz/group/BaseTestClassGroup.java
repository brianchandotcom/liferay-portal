/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.BuildDatabase;
import com.liferay.jenkins.results.parser.BuildDatabaseUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Peter Yoo
 */
public abstract class BaseTestClassGroup implements TestClassGroup {

	@Override
	public List<TestClass> getTestClasses() {
		return testClasses;
	}

	@Override
	public List<File> getTestClassFiles() {
		List<File> testClassFiles = new ArrayList<>();

		for (TestClass testClass : testClasses) {
			testClassFiles.add(testClass.getTestClassFile());
		}

		return testClassFiles;
	}

	@Override
	public boolean hasTestClasses() {
		List<TestClass> testClasses = getTestClasses();

		if ((testClasses != null) && !testClasses.isEmpty()) {
			return true;
		}

		return false;
	}

	protected void addTestClass(TestClass testClass) {
		if (!testClasses.contains(testClass)) {
			testClasses.add(testClass);
		}
	}

	protected void addTestClasses(List<TestClass> testClasses) {
		for (TestClass testClass : testClasses) {
			addTestClass(testClass);
		}
	}

	protected String getBuildStartProperty(String propertyName) {
		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase();

		if (buildDatabase.hasProperties("start.properties")) {
			Properties startProperties = buildDatabase.getProperties(
				"start.properties");

			return JenkinsResultsParserUtil.getProperty(
				startProperties, propertyName);
		}

		return null;
	}

	protected final List<TestClass> testClasses = new ArrayList<>();

}