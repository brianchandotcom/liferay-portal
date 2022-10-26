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