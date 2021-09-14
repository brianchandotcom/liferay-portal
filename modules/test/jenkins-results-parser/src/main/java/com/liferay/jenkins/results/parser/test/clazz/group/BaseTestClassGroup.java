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

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Peter Yoo
 */
public abstract class BaseTestClassGroup implements TestClassGroup {

	@Override
	public List<TestClassGroup.TestClass> getTestClasses() {
		return testClasses;
	}

	@Override
	public List<File> getTestClassFiles() {
		List<File> testClassFiles = new ArrayList<>();

		for (TestClassGroup.TestClass testClass : testClasses) {
			testClassFiles.add(testClass.getTestClassFile());
		}

		return testClassFiles;
	}

	public abstract static class BaseTestClass
		implements TestClassGroup.TestClass {

		@Override
		public int compareTo(TestClassGroup.TestClass testClass) {
			if (testClass == null) {
				throw new NullPointerException("Test class is null");
			}

			return _testClassFile.compareTo(testClass.getTestClassFile());
		}

		@Override
		public File getTestClassFile() {
			return _testClassFile;
		}

		@Override
		public List<TestClassGroup.TestClass.TestClassMethod>
			getTestClassMethods() {

			return _testClassMethods;
		}

		@Override
		public boolean isIgnored() {
			return false;
		}

		protected BaseTestClass(File testClassFile) {
			_testClassFile = testClassFile;
		}

		protected void addTestClassMethod(
			boolean methodIgnored, String methodName) {

			addTestClassMethod(
				new TestClassMethod(methodIgnored, methodName, this));
		}

		protected void addTestClassMethod(String methodName) {
			addTestClassMethod(false, methodName);
		}

		protected void addTestClassMethod(
			TestClassGroup.TestClass.TestClassMethod testClassMethod) {

			_testClassMethods.add(testClassMethod);
		}

		private final File _testClassFile;
		private final List<TestClassMethod> _testClassMethods =
			new ArrayList<>();

	}

	protected void addTestClass(TestClassGroup.TestClass testClass) {
		testClasses.add(testClass);
	}

	protected String getBuildStartProperty(String propertyName) {
		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase(
			JenkinsResultsParserUtil.getBuildDirPath(), true);

		Properties startProperties = buildDatabase.getProperties(
			"start.properties");

		return JenkinsResultsParserUtil.getProperty(
			startProperties, propertyName);
	}

	protected final List<TestClassGroup.TestClass> testClasses =
		new ArrayList<>();

}