/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.task.TestClassTask;
import com.liferay.jenkins.results.parser.test.clazz.task.TestClassTaskFactory;

import java.io.File;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ModulesJUnitTestClass extends JUnitTestClass {

	public TestClassTask getTestClassTask() {
		if (_testClassTask != null) {
			return _testClassTask;
		}

		_testClassTask = TestClassTaskFactory.newTestClassTask(
			getTestTaskName(), getAverageTestTaskDuration());

		_testClassTask.addTestClasses(this);

		return _testClassTask;
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

	private TestClassTask _testClassTask;

}