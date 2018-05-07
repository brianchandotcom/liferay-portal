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

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public abstract class TestClassGroup {

	public abstract List<TestClass> getTestClasses();

	public abstract List<File> getTestClassFiles();

	protected static class TestClass implements Comparable<TestClass> {

		@Override
		public int compareTo(TestClass testClass) {
			if (testClass == null) {
				throw new NullPointerException("test class is NULL");
			}

			return _file.compareTo(testClass.getFile());
		}

		protected TestClass(File file) {
			_file = file;
		}

		protected void addTestMethod(String methodName) {
			addTestMethod(new TestMethod(methodName, this));
		}

		protected void addTestMethod(TestMethod testMethod) {
			_testMethods.add(testMethod);
		}

		protected File getFile() {
			return _file;
		}

		protected List<TestMethod> getTestMethods() {
			return _testMethods;
		}

		private final File _file;
		private final List<TestMethod> _testMethods = new ArrayList<>();

	}

	protected static class TestMethod {

		protected TestMethod(String name, TestClass testClass) {
			_name = name;
			_testClass = testClass;
		}

		protected String getName() {
			return _name;
		}

		protected TestClass getTestClass() {
			return _testClass;
		}

		private final String _name;
		private final TestClass _testClass;

	}

}