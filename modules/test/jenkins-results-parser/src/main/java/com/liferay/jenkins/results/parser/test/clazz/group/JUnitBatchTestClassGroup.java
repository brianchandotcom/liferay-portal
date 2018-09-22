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

import com.google.common.collect.Lists;

import com.liferay.jenkins.results.parser.CentralMergePullRequestJob;
import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yi-Chen Tsai
 */
public class JUnitBatchTestClassGroup extends BatchTestClassGroup {

	@Override
	public int getAxisCount() {
		int axisCount = super.getAxisCount();

		if ((axisCount == 0) && _includeAutoBalanceTests) {
			return 1;
		}

		return axisCount;
	}

	public Map<File, JunitBatchTestClass> getJunitTestClasses() {
		return JunitBatchTestClass.getJunitTestClasses();
	}

	public void writeTestCSVReportFile() throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append("Class Name");
		sb.append(",");
		sb.append("Method Name");
		sb.append(",");
		sb.append("Ignored");
		sb.append(",");
		sb.append("File Path");
		sb.append("\n");

		Map<File, JunitBatchTestClass> junitTestClasses = getJunitTestClasses();

		for (Map.Entry<File, JUnitBatchTestClassGroup.JunitBatchTestClass>
				entry : junitTestClasses.entrySet()) {

			JUnitBatchTestClassGroup.JunitBatchTestClass junitBatchTestClass =
				entry.getValue();

			File testFile = junitBatchTestClass.getFile();

			String testFileAbsolutePath = testFile.getAbsolutePath();

			testFileAbsolutePath = testFileAbsolutePath.replace(
				".class", ".java");

			String className = testFile.getName();

			className = className.replace(".class", "");

			List<BaseTestClassGroup.BaseTestMethod> testMethods =
				junitBatchTestClass.getTestMethods();

			for (BaseTestClassGroup.BaseTestMethod testMethod : testMethods) {
				sb.append(className);
				sb.append(",");
				sb.append(testMethod.getName());
				sb.append(",");

				if (testMethod.isIgnored()) {
					sb.append("TRUE");
				}
				else {
					sb.append("");
				}

				sb.append(",");
				sb.append(testFileAbsolutePath);
				sb.append("\n");
			}
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");

		File reportCSVFile = new File(
			JenkinsResultsParserUtil.combine(
				"Report_", simpleDateFormat.format(new Date()), ".csv"));

		try {
			JenkinsResultsParserUtil.write(reportCSVFile, sb.toString());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public static class JunitBatchTestClass extends BaseTestClass {

		protected static JunitBatchTestClass getInstance(
			File file, GitWorkingDirectory gitWorkingDirectory, File srcFile) {

			if (_junitTestClasses.containsKey(file)) {
				return _junitTestClasses.get(file);
			}

			JunitBatchTestClass junitTestClass = new JunitBatchTestClass(
				file, gitWorkingDirectory, srcFile);

			_junitTestClasses.put(file, junitTestClass);

			return junitTestClass;
		}

		protected static JunitBatchTestClass getInstance(
			String fullClassName, GitWorkingDirectory gitWorkingDirectory) {

			File javaFile = gitWorkingDirectory.getJavaFileFromFullClassName(
				fullClassName);

			if (javaFile == null) {
				System.out.println(
					"No matching files found for " + fullClassName);

				return null;
			}

			File workingDirectory = gitWorkingDirectory.getWorkingDirectory();

			String relativeFilePath = javaFile.getAbsolutePath();

			relativeFilePath = relativeFilePath.replace(
				workingDirectory.getAbsolutePath(), "");

			File file = new File(relativeFilePath.replace(".java", ".class"));

			return getInstance(file, gitWorkingDirectory, javaFile);
		}

		protected static Map<File, JunitBatchTestClass> getJunitTestClasses() {
			return _junitTestClasses;
		}

		protected JunitBatchTestClass(
			File file, GitWorkingDirectory gitWorkingDirectory, File srcFile) {

			super(file);

			String srcFileName = srcFile.getName();

			_gitWorkingDirectory = gitWorkingDirectory;
			_srcFile = srcFile;

			_className = _getClassName();
			_packageName = _getPackageName();

			if (!srcFileName.endsWith(".java")) {
				_srcFileContent = "";

				return;
			}

			try {
				_srcFileContent = JenkinsResultsParserUtil.read(_srcFile);

				_initTestMethods();
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		private String _getClassName() {
			String srcFileName = _srcFile.getName();

			return srcFileName.substring(0, srcFileName.lastIndexOf("."));
		}

		private String _getPackageName() {
			String srcFilePath = _srcFile.toString();

			int x = srcFilePath.indexOf("/com/");
			int y = srcFilePath.lastIndexOf("/");

			srcFilePath = srcFilePath.substring(x + 1, y);

			return srcFilePath.replaceAll("/", ".");
		}

		private String _getParentClassName() {
			Pattern classHeaderPattern = Pattern.compile(
				JenkinsResultsParserUtil.combine(
					"public\\s+(abstract\\s+)?class\\s+", _className,
					"(\\<[^\\<]+\\>)?(?<classHeaderEntities>[^\\{]+)\\{"));

			Matcher classHeaderMatcher = classHeaderPattern.matcher(
				_srcFileContent);

			if (!classHeaderMatcher.find()) {
				throw new RuntimeException(
					"No class header found in " + _srcFile);
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

		private String _getParentFullClassName() {
			String parentClassName = _getParentClassName();

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

			String parentPackageName = _getParentPackageName(parentClassName);

			if (parentPackageName == null) {
				return null;
			}

			return parentPackageName + "." + parentClassName;
		}

		private String _getParentPackageName(String parentClassName) {
			Pattern parentImportClassPattern = Pattern.compile(
				JenkinsResultsParserUtil.combine(
					"import\\s+(?<parentPackageName>[^;]+)\\.", parentClassName,
					";"));

			Matcher parentImportClassMatcher = parentImportClassPattern.matcher(
				_srcFileContent);

			if (parentImportClassMatcher.find()) {
				String parentPackageName = parentImportClassMatcher.group(
					"parentPackageName");

				if (!parentPackageName.startsWith("com.liferay")) {
					return null;
				}

				return parentPackageName;
			}

			return _packageName;
		}

		private void _initTestMethods() throws IOException {
			Matcher classHeaderMatcher = _classHeaderPattern.matcher(
				_srcFileContent);

			boolean classIgnored = false;

			if (classHeaderMatcher.find()) {
				String annotations = classHeaderMatcher.group("annotations");

				if (annotations.contains("@Ignore")) {
					classIgnored = true;
				}
			}

			Matcher methodHeaderMatcher = _methodHeaderPattern.matcher(
				_srcFileContent);

			while (methodHeaderMatcher.find()) {
				String annotations = methodHeaderMatcher.group("annotations");

				boolean methodIgnored = false;

				if (classIgnored || annotations.contains("@Ignore")) {
					methodIgnored = true;
				}

				String methodName = methodHeaderMatcher.group("methodName");

				if (annotations.contains("@Test")) {
					addTestMethod(methodIgnored, methodName);
				}
			}

			String parentFullClassName = _getParentFullClassName();

			if (parentFullClassName == null) {
				return;
			}

			JunitBatchTestClass parentJunitBatchTestClass = getInstance(
				parentFullClassName, _gitWorkingDirectory);

			if (parentJunitBatchTestClass == null) {
				return;
			}

			for (BaseTestMethod testMethod :
					parentJunitBatchTestClass.getTestMethods()) {

				addTestMethod(testMethod);
			}
		}

		private static Pattern _classHeaderPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"\\t(?<annotations>(@[\\s\\S]+?))?public\\s+class\\s+",
				"(?<className>[^\\(\\s]+)"));
		private static final Map<File, JunitBatchTestClass> _junitTestClasses =
			new HashMap<>();
		private static Pattern _methodHeaderPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"\\t(?<annotations>(@[\\s\\S]+?))public\\s+void\\s+",
				"(?<methodName>[^\\(\\s]+)"));

		private final String _className;
		private final GitWorkingDirectory _gitWorkingDirectory;
		private final String _packageName;
		private final File _srcFile;
		private final String _srcFileContent;

	}

	protected JUnitBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		if (portalTestClassJob instanceof CentralMergePullRequestJob) {
			_includeUnstagedTestClassFiles = true;
		}
		else {
			_includeUnstagedTestClassFiles = false;
		}

		_setAutoBalanceTestFiles();

		_setTestClassNamesExcludesRelativeGlobs();
		_setTestClassNamesIncludesRelativeGlobs();

		setTestClasses();

		_setIncludeAutoBalanceTests();

		setAxisTestClassGroups();
	}

	protected List<String> getReleaseTestClassNamesRelativeGlobs(
		List<String> testClassNamesRelativeGlobs) {

		return testClassNamesRelativeGlobs;
	}

	protected List<String> getRelevantTestClassNamesRelativeGlobs(
		List<String> testClassNamesRelativeGlobs) {

		List<String> relevantTestClassNameRelativeGlobs = new ArrayList<>();

		List<File> moduleDirsList = null;

		try {
			moduleDirsList = portalGitWorkingDirectory.getModuleDirsList();
		}
		catch (IOException ioe) {
			File workingDirectory =
				portalGitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get module directories in ",
					workingDirectory.getPath()),
				ioe);
		}

		List<File> modifiedFilesList =
			portalGitWorkingDirectory.getModifiedFilesList();

		for (File modifiedFile : modifiedFilesList) {
			boolean foundModuleFile = false;

			for (File moduleDir : moduleDirsList) {
				if (JenkinsResultsParserUtil.isFileInDirectory(
						moduleDir, modifiedFile)) {

					foundModuleFile = true;

					break;
				}
			}

			if (foundModuleFile) {
				continue;
			}

			relevantTestClassNameRelativeGlobs.addAll(
				testClassNamesRelativeGlobs);

			return relevantTestClassNameRelativeGlobs;
		}

		return relevantTestClassNameRelativeGlobs;
	}

	protected void setAxisTestClassGroups() {
		int axisCount = getAxisCount();

		if (axisCount == 0) {
			return;
		}

		int testClassCount = testClasses.size();

		if (testClassCount == 0) {
			if (!_includeAutoBalanceTests) {
				return;
			}

			axisTestClassGroups.put(0, new AxisTestClassGroup(this, 0));
		}
		else {
			int axisSize = (int)Math.ceil((double)testClassCount / axisCount);

			int id = 0;

			for (List<BaseTestClass> axisTestClasses :
					Lists.partition(testClasses, axisSize)) {

				AxisTestClassGroup axisTestClassGroup = new AxisTestClassGroup(
					this, id);

				for (BaseTestClass axisTestClass : axisTestClasses) {
					axisTestClassGroup.addTestClass(axisTestClass);
				}

				axisTestClassGroups.put(id, axisTestClassGroup);

				id++;
			}
		}

		if (!_includeAutoBalanceTests) {
			return;
		}

		for (int i = 0; i < axisCount; i++) {
			AxisTestClassGroup axisTestClassGroup = axisTestClassGroups.get(i);

			for (File autoBalanceTestFile : _autoBalanceTestFiles) {
				String filePath = autoBalanceTestFile.getPath();

				filePath = filePath.replace(".java", ".class");

				axisTestClassGroup.addTestClass(
					JunitBatchTestClass.getInstance(
						new File(filePath), portalGitWorkingDirectory,
						autoBalanceTestFile));
			}
		}
	}

	protected void setTestClasses() {
		final File workingDirectory =
			portalGitWorkingDirectory.getWorkingDirectory();

		try {
			Files.walkFileTree(
				workingDirectory.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path filePath, BasicFileAttributes attrs)
						throws IOException {

						if (_pathExcluded(filePath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path filePath, BasicFileAttributes attrs)
						throws IOException {

						if (_pathIncluded(filePath) &&
							!_pathExcluded(filePath)) {

							testClasses.add(_getPackagePathClassFile(filePath));
						}

						return FileVisitResult.CONTINUE;
					}

					private BaseTestClass _getPackagePathClassFile(Path path) {
						String filePath = path.toString();

						Matcher matcher = _packagePathPattern.matcher(filePath);

						if (matcher.find()) {
							String packagePath = matcher.group("packagePath");

							packagePath = packagePath.replace(
								".java", ".class");

							String parentPath = matcher.group("parentPath");

							parentPath = parentPath.replace(
								workingDirectory.getAbsolutePath(), "");

							return JunitBatchTestClass.getInstance(
								new File(parentPath, packagePath),
								portalGitWorkingDirectory, path.toFile());
						}

						return JunitBatchTestClass.getInstance(
							new File(filePath.replace(".java", ".class")),
							portalGitWorkingDirectory, path.toFile());
					}

					private boolean _pathExcluded(Path path) {
						return _pathMatches(
							path, testClassNamesExcludesPathMatchers);
					}

					private boolean _pathIncluded(Path path) {
						return _pathMatches(
							path, testClassNamesIncludesPathMatchers);
					}

					private boolean _pathMatches(
						Path path, List<PathMatcher> pathMatchers) {

						for (PathMatcher pathMatcher : pathMatchers) {
							if (pathMatcher.matches(path)) {
								return true;
							}
						}

						return false;
					}

				});
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to search for test file names in " +
					workingDirectory.getPath(),
				ioe);
		}

		Collections.sort(testClasses);
	}

	protected final List<PathMatcher> testClassNamesExcludesPathMatchers =
		new ArrayList<>();
	protected final List<PathMatcher> testClassNamesIncludesPathMatchers =
		new ArrayList<>();

	private String _getTestClassNamesExcludesPropertyValue() {
		String propertyValue = getFirstPropertyValue(
			"test.batch.class.names.excludes");

		if (propertyValue != null) {
			return propertyValue;
		}

		return JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.class.names.excludes");
	}

	private String _getTestClassNamesIncludesPropertyValue() {
		String propertyValue = getFirstPropertyValue(
			"test.batch.class.names.includes");

		if (propertyValue != null) {
			return propertyValue;
		}

		return JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.class.names.includes");
	}

	private List<PathMatcher> _getTestClassNamesPathMatchers(
		List<String> testClassNamesRelativeGlobs) {

		List<PathMatcher> pathMatchers = new ArrayList<>();

		File workingDirectory = portalGitWorkingDirectory.getWorkingDirectory();

		String workingDirectoryPath = workingDirectory.getAbsolutePath();

		for (String testClassNamesRelativeGlob : testClassNamesRelativeGlobs) {
			FileSystem fileSystem = FileSystems.getDefault();

			pathMatchers.add(
				fileSystem.getPathMatcher(
					JenkinsResultsParserUtil.combine(
						"glob:", workingDirectoryPath, "/",
						testClassNamesRelativeGlob)));
		}

		return pathMatchers;
	}

	private void _setAutoBalanceTestFiles() {
		String propertyName = "test.class.names.auto.balance";

		String autoBalanceTestNames = getFirstPropertyValue(propertyName);

		if ((autoBalanceTestNames != null) &&
			!autoBalanceTestNames.equals("")) {

			for (String autoBalanceTestName : autoBalanceTestNames.split(",")) {
				_autoBalanceTestFiles.add(new File(autoBalanceTestName));
			}
		}
	}

	private void _setIncludeAutoBalanceTests() {
		if (!testClasses.isEmpty()) {
			_includeAutoBalanceTests = true;

			return;
		}

		List<File> modifiedJavaFilesList =
			portalGitWorkingDirectory.getModifiedFilesList(
				".java", _includeUnstagedTestClassFiles);

		if (!_autoBalanceTestFiles.isEmpty() &&
			!modifiedJavaFilesList.isEmpty()) {

			_includeAutoBalanceTests = true;

			return;
		}

		_includeAutoBalanceTests = _DEFAULT_INCLUDE_AUTO_BALANCE_TESTS;
	}

	private void _setTestClassNamesExcludesRelativeGlobs() {
		String testClassNamesExcludesPropertyValue =
			_getTestClassNamesExcludesPropertyValue();

		if ((testClassNamesExcludesPropertyValue == null) ||
			testClassNamesExcludesPropertyValue.isEmpty()) {

			return;
		}

		List<String> testClassNamesExcludesRelativeGlobs = Arrays.asList(
			testClassNamesExcludesPropertyValue.split(","));

		testClassNamesExcludesPathMatchers.addAll(
			_getTestClassNamesPathMatchers(
				testClassNamesExcludesRelativeGlobs));
	}

	private void _setTestClassNamesIncludesRelativeGlobs() {
		String testClassNamesIncludesPropertyValue =
			_getTestClassNamesIncludesPropertyValue();

		if ((testClassNamesIncludesPropertyValue == null) ||
			testClassNamesIncludesPropertyValue.isEmpty()) {

			return;
		}

		List<String> testClassNamesIncludesRelativeGlobs = new ArrayList<>();

		Collections.addAll(
			testClassNamesIncludesRelativeGlobs,
			testClassNamesIncludesPropertyValue.split(","));

		if (testReleaseBundle) {
			testClassNamesIncludesRelativeGlobs =
				getReleaseTestClassNamesRelativeGlobs(
					testClassNamesIncludesRelativeGlobs);
		}
		else if (testRelevantChanges) {
			testClassNamesIncludesRelativeGlobs =
				getRelevantTestClassNamesRelativeGlobs(
					testClassNamesIncludesRelativeGlobs);
		}

		String testBatchClassNamesIncludesRequiredPropertyValue =
			getFirstPropertyValue("test.batch.class.names.includes.required");

		if ((testBatchClassNamesIncludesRequiredPropertyValue != null) &&
			!testBatchClassNamesIncludesRequiredPropertyValue.isEmpty()) {

			Collections.addAll(
				testClassNamesIncludesRelativeGlobs,
				testBatchClassNamesIncludesRequiredPropertyValue.split(","));
		}

		testClassNamesIncludesPathMatchers.addAll(
			_getTestClassNamesPathMatchers(
				testClassNamesIncludesRelativeGlobs));
	}

	private static final boolean _DEFAULT_INCLUDE_AUTO_BALANCE_TESTS = false;

	private static final Pattern _packagePathPattern = Pattern.compile(
		"(?<parentPath>.*/)(?<packagePath>com/.*)");

	private final List<File> _autoBalanceTestFiles = new ArrayList<>();
	private boolean _includeAutoBalanceTests;
	private final boolean _includeUnstagedTestClassFiles;

}