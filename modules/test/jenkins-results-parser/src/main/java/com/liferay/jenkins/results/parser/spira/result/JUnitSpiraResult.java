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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.test.clazz.group.JUnitAxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TestClassGroup;

/**
 * @author Michael Hashimoto
 */
public class JUnitSpiraResult extends BaseSpiraResult {

	@Override
	public String getTestName() {
		String testName = String.valueOf(_testClass.getTestClassFile());

		testName = testName.replaceAll(".*(com/.*)", "$1");

		testName = testName.replaceAll("\\/", ".");
		testName = testName.replaceAll(".class", "");

		return testName;
	}

	protected JUnitSpiraResult(
		JUnitAxisTestClassGroup jUnitAxisTestClassGroup,
		TestClassGroup.TestClass testClass) {

		super(jUnitAxisTestClassGroup);

		_testClass = testClass;
	}

	private final TestClassGroup.TestClass _testClass;

}