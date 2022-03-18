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

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.CompileModulesBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.FunctionalBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.JSUnitModulesBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.JUnitBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.NPMTestBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.PluginsBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.PluginsGulpBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.RESTBuilderModulesBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.SemVerModulesBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.ServiceBuilderModulesBatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.TCKJunitBatchTestClassGroup;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class TestClassFactory {

	public static List<TestClass> getTestClasses() {
		return new ArrayList<>(_testClasses.values());
	}

	public static TestClass newTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile) {

		return newTestClass(batchTestClassGroup, testClassFile, null);
	}

	public static TestClass newTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile,
		String testClassMethodName) {

		StringBuilder sb = new StringBuilder();

		sb.append(batchTestClassGroup.getBatchName());

		if ((testClassFile != null) && testClassFile.exists()) {
			sb.append("_");
			sb.append(JenkinsResultsParserUtil.getCanonicalPath(testClassFile));
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testClassMethodName)) {
			sb.append("_");
			sb.append(testClassMethodName);
		}

		String key = sb.toString();

		TestClass testClass = _testClasses.get(key);

		if (testClass != null) {
			return testClass;
		}

		if (batchTestClassGroup instanceof CompileModulesBatchTestClassGroup) {
			testClass = new CompileModulesTestClass(
				batchTestClassGroup, testClassFile);
		}
		else if (batchTestClassGroup instanceof FunctionalBatchTestClassGroup) {
			testClass = new FunctionalTestClass(
				batchTestClassGroup, testClassMethodName);
		}
		else if (batchTestClassGroup instanceof
					JSUnitModulesBatchTestClassGroup) {

			testClass = new JSUnitModulesTestClass(
				batchTestClassGroup, testClassFile);
		}
		else if (batchTestClassGroup instanceof JUnitBatchTestClassGroup) {
			testClass = new JUnitTestClass(batchTestClassGroup, testClassFile);
		}
		else if (batchTestClassGroup instanceof PluginsBatchTestClassGroup) {
			testClass = new PluginsTestClass(
				batchTestClassGroup, testClassFile);
		}
		else if (batchTestClassGroup instanceof
					PluginsGulpBatchTestClassGroup) {

			testClass = new PluginsGulpTestClass(
				batchTestClassGroup, testClassFile);
		}
		else if (batchTestClassGroup instanceof NPMTestBatchTestClassGroup) {
			testClass = new NPMTestClass(batchTestClassGroup, testClassFile);
		}
		else if (batchTestClassGroup instanceof
					RESTBuilderModulesBatchTestClassGroup) {

			testClass = new RESTBuilderModulesTestClass(
				batchTestClassGroup, testClassFile);
		}
		else if (batchTestClassGroup instanceof
					SemVerModulesBatchTestClassGroup) {

			testClass = new SemVerModulesTestClass(
				batchTestClassGroup, testClassFile);
		}
		else if (batchTestClassGroup instanceof
					ServiceBuilderModulesBatchTestClassGroup) {

			testClass = new ServiceBuilderModulesTestClass(
				batchTestClassGroup, testClassFile);
		}
		else if (batchTestClassGroup instanceof TCKJunitBatchTestClassGroup) {
			testClass = new TCKTestClass(batchTestClassGroup, testClassFile);
		}
		else {
			testClass = new BatchTestClass(batchTestClassGroup, testClassFile);
		}

		_testClasses.put(key, testClass);

		return _testClasses.get(key);
	}

	public static TestClass newTestClass(
		BatchTestClassGroup batchTestClassGroup, String testClassMethodName) {

		return newTestClass(batchTestClassGroup, null, testClassMethodName);
	}

	public static TestClassMethod newTestClassMethod(
		boolean ignored, String name, TestClass testClass) {

		return new TestClassMethod(ignored, name, testClass);
	}

	public static TestClassMethod newTestClassMethod(
		JSONObject jsonObject, TestClass testClass) {

		return new TestClassMethod(jsonObject, testClass);
	}

	private static final Map<String, TestClass> _testClasses = new HashMap<>();

}