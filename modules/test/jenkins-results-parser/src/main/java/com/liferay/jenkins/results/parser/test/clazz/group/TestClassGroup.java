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

import com.liferay.jenkins.results.parser.Job;

import java.io.File;

import java.util.List;

/**
 * @author Peter Yoo
 */
public interface TestClassGroup {

	public Job getJob();

	public List<TestClass> getTestClasses();

	public List<File> getTestClassFiles();

	public interface TestClass extends Comparable<TestClass> {

		public File getTestClassFile();

		public List<TestClassMethod> getTestClassMethods();

		public static class TestClassMethod {

			public String getName() {
				return _name;
			}

			public TestClassGroup.TestClass getTestClass() {
				return _testClass;
			}

			public boolean isIgnored() {
				return _ignored;
			}

			protected TestClassMethod(
				boolean ignored, String name,
				BaseTestClassGroup.BaseTestClass testClass) {

				_ignored = ignored;
				_name = name;
				_testClass = testClass;
			}

			private final boolean _ignored;
			private final String _name;
			private final TestClassGroup.TestClass _testClass;

		}

	}

}