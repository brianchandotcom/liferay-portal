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

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Yoo
 */
public abstract class BaseTestClassGroup {

	public List<BaseTestClass> getTestClasses() {
		return testClasses;
	}

	public List<File> getTestClassFiles() {
		List<File> testClassFiles = new ArrayList<>();

		for (BaseTestClass testClass : testClasses) {
			testClassFiles.add(testClass.getFile());
		}

		return testClassFiles;
	}

	public static class BaseTestClass implements Comparable<BaseTestClass> {

		@Override
		public int compareTo(BaseTestClass testClass) {
			if (testClass == null) {
				throw new NullPointerException("Test class is null");
			}

			return _file.compareTo(testClass.getFile());
		}

		public File getFile() {
			return _file;
		}

		public List<BaseTestMethod> getTestMethods() {
			return _testMethods;
		}

		protected BaseTestClass(File file) {
			_file = file;
		}

		protected void addTestMethod(BaseTestMethod testMethod) {
			_testMethods.add(testMethod);
		}

		protected void addTestMethod(boolean methodIgnored, String methodName) {
			addTestMethod(new BaseTestMethod(methodIgnored, methodName, this));
		}

		protected void addTestMethod(String methodName) {
			addTestMethod(false, methodName);
		}

		private final File _file;
		private final List<BaseTestMethod> _testMethods = new ArrayList<>();

	}

	public static class BaseTestMethod {

		public String getName() {
			return _name;
		}

		public boolean isIgnored() {
			return _ignored;
		}

		protected BaseTestMethod(
			boolean ignored, String name, BaseTestClass testClass) {

			_ignored = ignored;
			_name = name;
			_testClass = testClass;
		}

		protected BaseTestClass getTestClass() {
			return _testClass;
		}

		private final boolean _ignored;
		private final String _name;
		private final BaseTestClass _testClass;

	}

	protected void addTestClass(BaseTestClass testClass) {
		testClasses.add(testClass);
	}

	protected final List<BaseTestClass> testClasses = new ArrayList<>();

}