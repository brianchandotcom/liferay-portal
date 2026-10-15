/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.test.clazz.JUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

import java.io.File;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Michael Hashimoto
 */
public class JUnitTestClassTestrayCaseResultDescriptorTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetName() {
		String dirName = RandomTestUtil.randomString();
		String fileName = RandomTestUtil.randomString();

		_testGetName(
			"." + dirName + ".src.test.java.org." + fileName + ".java",
			"/" + dirName + "/src/test/java/org/" + fileName + ".java");
		_testGetName(
			"com.liferay." + fileName,
			"/" + dirName + "/com/src/test/java/com/liferay/" + fileName +
				".java");
		_testGetName(
			"com.liferay." + fileName,
			"/" + dirName + "/src/test/java/com/liferay/" + fileName + ".java");
	}

	private void _testGetName(String expectedName, String testClassFilePath) {
		JUnitTestClass jUnitTestClass = Mockito.mock(JUnitTestClass.class);

		Mockito.doReturn(
			new File(testClassFilePath)
		).when(
			jUnitTestClass
		).getTestClassFile();

		JUnitTestClassTestrayCaseResultDescriptor
			jUnitTestClassTestrayCaseResultDescriptor =
				new JUnitTestClassTestrayCaseResultDescriptor(
					Mockito.mock(AxisTestClassGroup.class), jUnitTestClass,
					null);

		testEquals(
			expectedName, jUnitTestClassTestrayCaseResultDescriptor.getName());
	}

}