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
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;

import java.io.File;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JUnitTestClass extends BaseTestClass {

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		if ((_testPropertiesFile != null) && _testPropertiesFile.exists()) {
			jsonObject.put("test_properties_file", _testPropertiesFile);
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				_testrayMainComponentName)) {

			jsonObject.put(
				"testray_main_component_name", _testrayMainComponentName);
		}

		return jsonObject;
	}

	public String getTestClassName() {
		return JenkinsResultsParserUtil.combine(
			_getPackageName(), ".", _getClassName());
	}

	public String getTestrayMainComponentName() {
		return _testrayMainComponentName;
	}

	@Override
	public boolean isIgnored() {
		return _classIgnored;
	}

	protected JUnitTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile) {

		super(batchTestClassGroup, testClassFile);

		File testPropertiesBaseDir = _getTestPropertiesBaseDir(
			getTestClassFile());

		if ((testPropertiesBaseDir != null) && testPropertiesBaseDir.exists()) {
			_testPropertiesFile = new File(
				testPropertiesBaseDir, "test.properties");

			_testrayMainComponentName = JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getProperties(_testPropertiesFile),
				"testray.main.component.name");
		}
		else {
			_testPropertiesFile = null;
			_testrayMainComponentName = null;
		}

		String testClassFileName = testClassFile.getName();

		if (!testClassFileName.endsWith(".java")) {
			return;
		}

		try {
			_initTestClassMethods(
				JenkinsResultsParserUtil.read(getTestClassFile()));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected JUnitTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);

		_classIgnored = jsonObject.getBoolean("ignored");

		if (jsonObject.has("test_properties_file")) {
			_testPropertiesFile = new File(
				jsonObject.getString("test_properties_file"));
		}
		else {
			_testPropertiesFile = null;
		}

		_testrayMainComponentName = jsonObject.optString(
			"testray_main_component_name");
	}

	protected String getTestName() {
		return _getPackageName() + "." + _getClassName();
	}

	private String _getClassName() {
		File testClassFile = getTestClassFile();

		String testClassFileName = testClassFile.getName();

		return testClassFileName.substring(
			0, testClassFileName.lastIndexOf("."));
	}

	private String _getPackageName() {
		String testClassFilePath = JenkinsResultsParserUtil.getCanonicalPath(
			getTestClassFile());

		int x = testClassFilePath.indexOf("/com/");
		int y = testClassFilePath.lastIndexOf("/");

		testClassFilePath = testClassFilePath.substring(x + 1, y);

		return testClassFilePath.replaceAll("/", ".");
	}

	private String _getParentClassName(String fileContent) {
		Pattern classHeaderPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"public\\s+(abstract\\s+)?(class|interface)\\s+",
				_getClassName(),
				"(\\<[^\\<]+\\>)?(?<classHeaderEntities>[^\\{]+)\\{"));

		Matcher classHeaderMatcher = classHeaderPattern.matcher(fileContent);

		if (!classHeaderMatcher.find()) {
			throw new RuntimeException(
				"No class header found in " + getTestClassFile());
		}

		String classHeaderEntities = classHeaderMatcher.group(
			"classHeaderEntities");

		Pattern parentClassPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"extends\\s+(?<parentClassName>[^\\s\\<]+)"));

		Matcher parentClassMatcher = parentClassPattern.matcher(
			classHeaderEntities);

		if (parentClassMatcher.find()) {
			return parentClassMatcher.group("parentClassName");
		}

		return null;
	}

	private String _getParentFullClassName(String fileContent) {
		String parentClassName = _getParentClassName(fileContent);

		if (parentClassName == null) {
			return null;
		}

		if (parentClassName.contains(".") &&
			parentClassName.matches("[a-z].*")) {

			if (!parentClassName.startsWith("com.liferay")) {
				return null;
			}

			return parentClassName;
		}

		String parentPackageName = _getParentPackageName(
			fileContent, parentClassName);

		if (parentPackageName == null) {
			return null;
		}

		return parentPackageName + "." + parentClassName;
	}

	private String _getParentPackageName(
		String fileContent, String parentClassName) {

		Pattern parentImportClassPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"import\\s+(?<parentPackageName>[^;]+)\\.", parentClassName,
				";"));

		Matcher parentImportClassMatcher = parentImportClassPattern.matcher(
			fileContent);

		if (parentImportClassMatcher.find()) {
			String parentPackageName = parentImportClassMatcher.group(
				"parentPackageName");

			if (!parentPackageName.startsWith("com.liferay")) {
				return null;
			}

			return parentPackageName;
		}

		return _getPackageName();
	}

	private File _getTestPropertiesBaseDir(File file) {
		if (file == null) {
			return null;
		}

		File canonicalFile = JenkinsResultsParserUtil.getCanonicalFile(file);

		File parentFile = canonicalFile.getParentFile();

		if ((parentFile == null) || !parentFile.exists()) {
			return file;
		}

		if (!canonicalFile.isDirectory()) {
			return _getTestPropertiesBaseDir(parentFile);
		}

		File testPropertiesFile = new File(canonicalFile, "test.properties");

		if (!testPropertiesFile.exists()) {
			return _getTestPropertiesBaseDir(parentFile);
		}

		return canonicalFile;
	}

	private void _initTestClassMethods(String fileContent) {
		Matcher classHeaderMatcher = _classHeaderPattern.matcher(fileContent);

		_classIgnored = false;

		if (classHeaderMatcher.find()) {
			String annotations = classHeaderMatcher.group("annotations");

			if ((annotations != null) && annotations.contains("@Ignore")) {
				_classIgnored = true;
			}
		}

		Matcher methodHeaderMatcher = _methodHeaderPattern.matcher(fileContent);

		while (methodHeaderMatcher.find()) {
			String annotations = methodHeaderMatcher.group("annotations");

			boolean methodIgnored = false;

			if (_classIgnored || annotations.contains("@Ignore")) {
				methodIgnored = true;
			}

			if (annotations.contains("@Test")) {
				String methodName = methodHeaderMatcher.group("methodName");

				addTestClassMethod(methodIgnored, methodName);
			}
		}

		String parentFullClassName = _getParentFullClassName(fileContent);

		if (parentFullClassName == null) {
			return;
		}

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		File parentJavaFile =
			portalGitWorkingDirectory.getJavaFileFromFullClassName(
				parentFullClassName);

		if (parentJavaFile == null) {
			System.out.println(
				"No matching files found for " + parentFullClassName);

			return;
		}

		TestClass parentTestClass = TestClassFactory.newTestClass(
			getBatchTestClassGroup(), parentJavaFile);

		if (parentTestClass == null) {
			return;
		}

		for (TestClassMethod testClassMethod :
				parentTestClass.getTestClassMethods()) {

			addTestClassMethod(_classIgnored, testClassMethod.getName());
		}
	}

	private static final Pattern _classHeaderPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"\\*/(?<annotations>[^/]*)public\\s+class\\s+",
			"(?<className>[^\\(\\s]+)"));
	private static final Pattern _methodHeaderPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"\\t(?<annotations>(@[\\s\\S]+?))public\\s+void\\s+",
			"(?<methodName>[^\\(\\s]+)"));

	private boolean _classIgnored;
	private final File _testPropertiesFile;
	private final String _testrayMainComponentName;

}