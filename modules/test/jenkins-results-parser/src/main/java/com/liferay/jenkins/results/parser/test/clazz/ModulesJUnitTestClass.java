/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.task.TestTask;

import java.io.File;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ModulesJUnitTestClass extends JUnitTestClass {

	public TestTask getTestTask() {
		return _testTask;
	}

	public void setTestTask(TestTask testTask) {
		_testTask = testTask;
	}

	protected ModulesJUnitTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile) {

		super(batchTestClassGroup, testClassFile);
	}

	protected ModulesJUnitTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile,
		List<String> testClassMethodNames) {

		super(batchTestClassGroup, testClassFile, testClassMethodNames);
	}

	protected ModulesJUnitTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);
	}

	private TestTask _testTask;

}