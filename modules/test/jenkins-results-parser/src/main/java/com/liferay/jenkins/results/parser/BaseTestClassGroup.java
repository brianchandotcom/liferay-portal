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
 * @author Peter Yoo
 */
public abstract class BaseTestClassGroup extends TestClassGroup {

	public List<TestClass> getTestClasses() {
		return testClasses;
	}

	public List<File> getTestClassFiles() {
		List<File> testClassFiles = new ArrayList<>();

		for (TestClass testClass : testClasses) {
			testClassFiles.add(testClass.getFile());
		}

		return testClassFiles;
	}

	protected void addTestClass(TestClass testClass) {
		testClasses.add(testClass);
	}

	protected final List<TestClass> testClasses = new ArrayList<>();

}