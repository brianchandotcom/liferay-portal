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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestClassFactory {

	public static List<TestClass> getTestClasses() {
		return new ArrayList<>(_testClasses.values());
	}

	public static TestClass newTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile) {

		return _newTestClass(batchTestClassGroup, null, testClassFile, null);
	}

	public static TestClass newTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile,
		String testClassMethodName) {

		return _newTestClass(
			batchTestClassGroup, null, testClassFile, testClassMethodName);
	}

	public static TestClass newTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		return _newTestClass(batchTestClassGroup, jsonObject, null, null);
	}

	public static TestClass newTestClass(
		BatchTestClassGroup batchTestClassGroup, String testClassMethodName) {

		return _newTestClass(
			batchTestClassGroup, null, null, testClassMethodName);
	}

	public static TestClassMethod newTestClassMethod(
		boolean ignored, String name, TestClass testClass) {

		return new TestClassMethod(ignored, name, testClass);
	}

	public static TestClassMethod newTestClassMethod(
		JSONObject jsonObject, TestClass testClass) {

		return new TestClassMethod(jsonObject, testClass);
	}

	private static TestClass _newTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject,
		File testClassFile, String testClassMethodName) {

		StringBuilder sb = new StringBuilder();

		sb.append(batchTestClassGroup.getBatchName());

		if ((testClassFile != null) && testClassFile.exists()) {
			sb.append("_");
			sb.append(JenkinsResultsParserUtil.getCanonicalPath(testClassFile));
		}
		else if ((jsonObject != null) && jsonObject.has("file")) {
			sb.append("_");
			sb.append(jsonObject.getString("file"));
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testClassMethodName)) {
			sb.append("_");
			sb.append(testClassMethodName);
		}
		else if ((jsonObject != null) &&
				 jsonObject.has("test_class_method_name")) {

			sb.append("_");
			sb.append(jsonObject.getString("test_class_method_name"));
		}

		String key = sb.toString();

		TestClass testClass = _testClasses.get(key);

		if (testClass != null) {
			return testClass;
		}

		if (batchTestClassGroup instanceof CompileModulesBatchTestClassGroup) {
			if (jsonObject != null) {
				testClass = new CompileModulesTestClass(
					batchTestClassGroup, jsonObject);
			}
			else {
				testClass = new CompileModulesTestClass(
					batchTestClassGroup, testClassFile);
			}
		}
		else if (batchTestClassGroup instanceof FunctionalBatchTestClassGroup) {
			if (jsonObject != null) {
				testClass = new FunctionalTestClass(
					batchTestClassGroup, jsonObject);
			}
			else {
				testClass = new FunctionalTestClass(
					batchTestClassGroup, testClassMethodName);
			}
		}
		else if (batchTestClassGroup instanceof
					JSUnitModulesBatchTestClassGroup) {

			if (jsonObject != null) {
				testClass = new JSUnitModulesTestClass(
					batchTestClassGroup, jsonObject);
			}
			else {
				testClass = new JSUnitModulesTestClass(
					batchTestClassGroup, testClassFile);
			}
		}
		else if (batchTestClassGroup instanceof JUnitBatchTestClassGroup) {
			if (jsonObject != null) {
				testClass = new JUnitTestClass(batchTestClassGroup, jsonObject);
			}
			else {
				testClass = new JUnitTestClass(
					batchTestClassGroup, testClassFile);
			}
		}
		else if (batchTestClassGroup instanceof PluginsBatchTestClassGroup) {
			if (jsonObject != null) {
				testClass = new PluginsTestClass(
					batchTestClassGroup, jsonObject);
			}
			else {
				testClass = new PluginsTestClass(
					batchTestClassGroup, testClassFile);
			}
		}
		else if (batchTestClassGroup instanceof
					PluginsGulpBatchTestClassGroup) {

			if (jsonObject != null) {
				testClass = new PluginsGulpTestClass(
					batchTestClassGroup, jsonObject);
			}
			else {
				testClass = new PluginsGulpTestClass(
					batchTestClassGroup, testClassFile);
			}
		}
		else if (batchTestClassGroup instanceof NPMTestBatchTestClassGroup) {
			if (jsonObject != null) {
				testClass = new NPMTestClass(batchTestClassGroup, jsonObject);
			}
			else {
				testClass = new NPMTestClass(
					batchTestClassGroup, testClassFile);
			}
		}
		else if (batchTestClassGroup instanceof
					RESTBuilderModulesBatchTestClassGroup) {

			if (jsonObject != null) {
				testClass = new RESTBuilderModulesTestClass(
					batchTestClassGroup, jsonObject);
			}
			else {
				testClass = new RESTBuilderModulesTestClass(
					batchTestClassGroup, testClassFile);
			}
		}
		else if (batchTestClassGroup instanceof
					SemVerModulesBatchTestClassGroup) {

			if (jsonObject != null) {
				testClass = new SemVerModulesTestClass(
					batchTestClassGroup, jsonObject);
			}
			else {
				testClass = new SemVerModulesTestClass(
					batchTestClassGroup, testClassFile);
			}
		}
		else if (batchTestClassGroup instanceof
					ServiceBuilderModulesBatchTestClassGroup) {

			if (jsonObject != null) {
				testClass = new ServiceBuilderModulesTestClass(
					batchTestClassGroup, jsonObject);
			}
			else {
				testClass = new ServiceBuilderModulesTestClass(
					batchTestClassGroup, testClassFile);
			}
		}
		else if (batchTestClassGroup instanceof TCKJunitBatchTestClassGroup) {
			if (jsonObject != null) {
				testClass = new TCKTestClass(batchTestClassGroup, jsonObject);
			}
			else {
				testClass = new TCKTestClass(
					batchTestClassGroup, testClassFile);
			}
		}
		else {
			if (jsonObject != null) {
				testClass = new BatchTestClass(batchTestClassGroup, jsonObject);
			}
			else {
				testClass = new BatchTestClass(
					batchTestClassGroup, testClassFile);
			}
		}

		_testClasses.put(key, testClass);

		return _testClasses.get(key);
	}

	private static final Map<String, TestClass> _testClasses = new HashMap<>();

}