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
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
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
		CSVReport csvReport = new CSVReport(
			new CSVReport.Row(
				"Class Name", "Method Name", "Ignored", "File Path"));

		Map<File, JunitBatchTestClass> junitTestClasses = getJunitTestClasses();

		for (JUnitBatchTestClassGroup.JunitBatchTestClass junitBatchTestClass :
				junitTestClasses.values()) {

			File testClassFile = junitBatchTestClass.getTestClassFile();

			String testClassFileRelativePath = _getRelativePath(
				testClassFile, junitBatchTestClass.getWorkingDirectory());

			String className = testClassFile.getName();

			className = className.replace(".class", "");

			List<TestClassGroup.TestClass.TestClassMethod> testClassMethods =
				junitBatchTestClass.getTestClassMethods();

			for (TestClassGroup.TestClass.TestClassMethod testClassMethod :
					testClassMethods) {

				CSVReport.Row csvReportRow = new CSVReport.Row();

				csvReportRow.add(className);
				csvReportRow.add(testClassMethod.getName());

				if (testClassMethod.isIgnored()) {
					csvReportRow.add("TRUE");
				}
				else {
					csvReportRow.add("");
				}

				csvReportRow.add(testClassFileRelativePath);

				csvReport.addRow(csvReportRow);
			}
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");

		File csvReportFile = new File(
			JenkinsResultsParserUtil.combine(
				"Report_junit_", simpleDateFormat.format(new Date()), ".csv"));

		try {
			JenkinsResultsParserUtil.write(csvReportFile, csvReport.toString());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static class JunitBatchTestClass extends BaseTestClass {

		public File getWorkingDirectory() {
			return _gitWorkingDirectory.getWorkingDirectory();
		}

		@Override
		public boolean isIgnored() {
			return _classIgnored;
		}

		protected static JunitBatchTestClass getInstance(
			File testClassFile, GitWorkingDirectory gitWorkingDirectory,
			File javaFile) {

			if (_junitTestClasses.containsKey(testClassFile)) {
				return _junitTestClasses.get(testClassFile);
			}

			JunitBatchTestClass junitTestClass = new JunitBatchTestClass(
				testClassFile, gitWorkingDirectory, javaFile);

			_junitTestClasses.put(testClassFile, junitTestClass);

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

			String packagePath = fullClassName.replace('.', '/');

			packagePath = packagePath + ".class";

			File testClassFile = new File(packagePath);

			if (_junitTestClasses.containsKey(testClassFile)) {
				return _junitTestClasses.get(testClassFile);
			}

			return getInstance(testClassFile, gitWorkingDirectory, javaFile);
		}

		protected static Map<File, JunitBatchTestClass> getJunitTestClasses() {
			return _junitTestClasses;
		}

		protected JunitBatchTestClass(
			File testClassFile, GitWorkingDirectory gitWorkingDirectory,
			File srcFile) {

			super(testClassFile);

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

				_initTestClassMethods();
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
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
					"public\\s+(abstract\\s+)?(class|interface)\\s+",
					_className,
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

		private void _initTestClassMethods() throws IOException {
			Matcher classHeaderMatcher = _classHeaderPattern.matcher(
				_srcFileContent);

			_classIgnored = false;

			if (classHeaderMatcher.find()) {
				String annotations = classHeaderMatcher.group("annotations");

				if ((annotations != null) && annotations.contains("@Ignore")) {
					_classIgnored = true;
				}
			}

			Matcher methodHeaderMatcher = _methodHeaderPattern.matcher(
				_srcFileContent);

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

			String parentFullClassName = _getParentFullClassName();

			if (parentFullClassName == null) {
				return;
			}

			JunitBatchTestClass parentJunitBatchTestClass = getInstance(
				parentFullClassName, _gitWorkingDirectory);

			if (parentJunitBatchTestClass == null) {
				return;
			}

			for (TestClassGroup.TestClass.TestClassMethod testClassMethod :
					parentJunitBatchTestClass.getTestClassMethods()) {

				if (_classIgnored) {
					addTestClassMethod(
						_classIgnored, testClassMethod.getName());

					continue;
				}

				addTestClassMethod(testClassMethod);
			}
		}

		private static Pattern _classHeaderPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"\\*/(?<annotations>[^/]*)public\\s+class\\s+",
				"(?<className>[^\\(\\s]+)"));
		private static final Map<File, JunitBatchTestClass> _junitTestClasses =
			new HashMap<>();
		private static Pattern _methodHeaderPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"\\t(?<annotations>(@[\\s\\S]+?))public\\s+void\\s+",
				"(?<methodName>[^\\(\\s]+)"));

		private boolean _classIgnored;
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

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			portalTestClassJob.getPortalGitWorkingDirectory();

		_rootWorkingDirectory = portalGitWorkingDirectory.getWorkingDirectory();

		_setAutoBalanceTestFiles();

		setTestClassNamesExcludesRelativeGlobs();
		_setTestClassNamesIncludesRelativeGlobs();

		setTestClasses();

		_setIncludeAutoBalanceTests();

		setAxisTestClassGroups();

		setSegmentTestClassGroups();
	}

	protected List<String> getReleaseTestClassNamesRelativeIncludesGlobs(
		List<String> testClassNamesRelativeIncludesGlobs) {

		return testClassNamesRelativeIncludesGlobs;
	}

	protected List<String> getRelevantTestClassNamesRelativeExcludesGlobs() {
		return new ArrayList();
	}

	protected List<String> getRelevantTestClassNamesRelativeIncludesGlobs(
		List<String> testClassNamesRelativeIncludesGlobs) {

		List<File> moduleDirsList = null;

		try {
			moduleDirsList = portalGitWorkingDirectory.getModuleDirsList();
		}
		catch (IOException ioException) {
			File workingDirectory =
				portalGitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get module directories in ",
					workingDirectory.getPath()),
				ioException);
		}

		List<String> relevantTestClassNameRelativeIncludesGlobs =
			new ArrayList<>();

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

			relevantTestClassNameRelativeIncludesGlobs.addAll(
				testClassNamesRelativeIncludesGlobs);

			return relevantTestClassNameRelativeIncludesGlobs;
		}

		return relevantTestClassNameRelativeIncludesGlobs;
	}

	protected boolean isValidTestClass(TestClass testClass) {
		return true;
	}

	@Override
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

			axisTestClassGroups.add(
				0, TestClassGroupFactory.newAxisTestClassGroup(this));
		}
		else {
			int axisSize = (int)Math.ceil((double)testClassCount / axisCount);

			for (List<TestClassGroup.TestClass> axisTestClasses :
					Lists.partition(testClasses, axisSize)) {

				AxisTestClassGroup axisTestClassGroup =
					TestClassGroupFactory.newAxisTestClassGroup(this);

				for (TestClassGroup.TestClass axisTestClass : axisTestClasses) {
					axisTestClassGroup.addTestClass(axisTestClass);
				}

				axisTestClassGroups.add(axisTestClassGroup);
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
		if (testClassNamesIncludesPathMatchers.isEmpty()) {
			return;
		}

		try {
			Files.walkFileTree(
				_rootWorkingDirectory.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						if (JenkinsResultsParserUtil.isFileExcluded(
								testClassNamesExcludesPathMatchers,
								filePath.toFile())) {

							return FileVisitResult.SKIP_SUBTREE;
						}

						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						if (JenkinsResultsParserUtil.isFileIncluded(
								testClassNamesExcludesPathMatchers,
								testClassNamesIncludesPathMatchers,
								filePath.toFile())) {

							TestClass testClass = _getPackagePathClassFile(
								filePath);

							if (testClass.isIgnored()) {
								return FileVisitResult.CONTINUE;
							}

							List<TestClass.TestClassMethod> testClassMethods =
								testClass.getTestClassMethods();

							if (!testClassMethods.isEmpty() &&
								isValidTestClass(testClass)) {

								testClasses.add(testClass);
							}
						}

						return FileVisitResult.CONTINUE;
					}

					private BaseTestClass _getPackagePathClassFile(Path path) {
						return JunitBatchTestClass.getInstance(
							path.toFile(), portalGitWorkingDirectory,
							path.toFile());
					}

				});
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to search for test file names in " +
					_rootWorkingDirectory.getPath(),
				ioException);
		}

		Collections.sort(testClasses);
	}

	protected void setTestClassNamesExcludesRelativeGlobs() {
		String testClassNamesExcludesPropertyValue =
			_getTestClassNamesExcludesPropertyValue(testSuiteName, false);

		List<String> testClassNamesExcludesRelativeGlobs = new ArrayList<>();

		if ((testClassNamesExcludesPropertyValue != null) &&
			!testClassNamesExcludesPropertyValue.isEmpty()) {

			Collections.addAll(
				testClassNamesExcludesRelativeGlobs,
				JenkinsResultsParserUtil.getGlobsFromProperty(
					testClassNamesExcludesPropertyValue));
		}

		if (testRelevantChanges) {
			testClassNamesExcludesRelativeGlobs.addAll(
				getRelevantTestClassNamesRelativeExcludesGlobs());
		}

		if (includeStableTestSuite && isStableTestSuiteBatch()) {
			String stableTestClassNamesExcludesPropertyValue =
				_getTestClassNamesExcludesPropertyValue(
					NAME_STABLE_TEST_SUITE, false);

			if ((stableTestClassNamesExcludesPropertyValue != null) &&
				!stableTestClassNamesExcludesPropertyValue.isEmpty()) {

				Collections.addAll(
					testClassNamesExcludesRelativeGlobs,
					JenkinsResultsParserUtil.getGlobsFromProperty(
						stableTestClassNamesExcludesPropertyValue));
			}
		}

		testClassNamesExcludesPathMatchers.addAll(
			JenkinsResultsParserUtil.toPathMatchers(
				JenkinsResultsParserUtil.combine(
					JenkinsResultsParserUtil.getCanonicalPath(
						_rootWorkingDirectory),
					File.separator),
				testClassNamesExcludesRelativeGlobs.toArray(new String[0])));
	}

	protected final List<PathMatcher> testClassNamesExcludesPathMatchers =
		new ArrayList<>();
	protected final List<PathMatcher> testClassNamesIncludesPathMatchers =
		new ArrayList<>();

	private String _getRelativePath(File file, File parentFile) {
		String filePath = JenkinsResultsParserUtil.getCanonicalPath(file);
		String parentFilePath = JenkinsResultsParserUtil.getCanonicalPath(
			parentFile);

		if (!filePath.startsWith(parentFilePath)) {
			throw new IllegalArgumentException(
				"Working directory does not contain this file");
		}

		return filePath.replaceAll(parentFilePath, "");
	}

	private String _getTestClassNamesExcludesPropertyValue(
		String testSuiteName, boolean useRequiredVariant) {

		String propertyName = "test.batch.class.names.excludes";

		if (useRequiredVariant) {
			propertyName += ".required";
		}

		List<String> propertyValues = new ArrayList<>();

		String propertyValue = getFirstPropertyValue(
			propertyName, batchName, testSuiteName);

		if (propertyValue != null) {
			propertyValues.add(propertyValue);
		}
		else {
			propertyValues.add(
				JenkinsResultsParserUtil.getProperty(
					jobProperties, propertyName));
		}

		if (!testPrivatePortalBranch) {
			propertyValues.add(_GLOB_MODULES_PRIVATE);
		}

		return JenkinsResultsParserUtil.join(",", propertyValues);
	}

	private String _getTestClassNamesIncludesPropertyValue(
		String testSuiteName, boolean useRequiredVariant) {

		String propertyName = "test.batch.class.names.includes";

		if (useRequiredVariant) {
			propertyName += ".required";
		}

		List<String> propertyValues = new ArrayList<>();

		String propertyValue = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), propertyName, testSuiteName, batchName,
			getJobName());

		if (propertyValue != null) {
			propertyValues.add(propertyValue);
		}
		else {
			propertyValues.add(
				JenkinsResultsParserUtil.getProperty(
					jobProperties, propertyName));
		}

		return JenkinsResultsParserUtil.join(",", propertyValues);
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
				_includeUnstagedTestClassFiles, null,
				JenkinsResultsParserUtil.toPathMatchers(
					JenkinsResultsParserUtil.combine(
						"**", File.separator, "*.java")));

		if (!_autoBalanceTestFiles.isEmpty() &&
			!modifiedJavaFilesList.isEmpty()) {

			_includeAutoBalanceTests = true;

			return;
		}

		_includeAutoBalanceTests = _ENABLE_INCLUDE_AUTO_BALANCE_TESTS_DEFAULT;
	}

	private void _setTestClassNamesIncludesRelativeGlobs() {
		String testClassNamesIncludesPropertyValue =
			_getTestClassNamesIncludesPropertyValue(testSuiteName, false);

		if ((testClassNamesIncludesPropertyValue == null) ||
			testClassNamesIncludesPropertyValue.isEmpty()) {

			return;
		}

		List<String> testClassNamesIncludesRelativeGlobs = new ArrayList<>();

		Collections.addAll(
			testClassNamesIncludesRelativeGlobs,
			JenkinsResultsParserUtil.getGlobsFromProperty(
				testClassNamesIncludesPropertyValue));

		if (testReleaseBundle) {
			testClassNamesIncludesRelativeGlobs =
				getReleaseTestClassNamesRelativeIncludesGlobs(
					testClassNamesIncludesRelativeGlobs);
		}
		else if (testRelevantChanges) {
			testClassNamesIncludesRelativeGlobs =
				getRelevantTestClassNamesRelativeIncludesGlobs(
					testClassNamesIncludesRelativeGlobs);
		}

		String testBatchClassNamesIncludesRequiredPropertyValue =
			_getTestClassNamesIncludesPropertyValue(testSuiteName, true);

		if ((testBatchClassNamesIncludesRequiredPropertyValue != null) &&
			!testBatchClassNamesIncludesRequiredPropertyValue.isEmpty()) {

			Collections.addAll(
				testClassNamesIncludesRelativeGlobs,
				JenkinsResultsParserUtil.getGlobsFromProperty(
					testBatchClassNamesIncludesRequiredPropertyValue));
		}

		if (includeStableTestSuite && isStableTestSuiteBatch()) {
			Collections.addAll(
				testClassNamesIncludesRelativeGlobs,
				JenkinsResultsParserUtil.getGlobsFromProperty(
					_getTestClassNamesIncludesPropertyValue(
						NAME_STABLE_TEST_SUITE, false)));

			testBatchClassNamesIncludesRequiredPropertyValue =
				_getTestClassNamesIncludesPropertyValue(
					NAME_STABLE_TEST_SUITE, true);

			if ((testBatchClassNamesIncludesRequiredPropertyValue != null) &&
				!testBatchClassNamesIncludesRequiredPropertyValue.isEmpty()) {

				Collections.addAll(
					testClassNamesIncludesRelativeGlobs,
					JenkinsResultsParserUtil.getGlobsFromProperty(
						testBatchClassNamesIncludesRequiredPropertyValue));
			}
		}

		testClassNamesIncludesPathMatchers.addAll(
			JenkinsResultsParserUtil.toPathMatchers(
				JenkinsResultsParserUtil.combine(
					JenkinsResultsParserUtil.getCanonicalPath(
						_rootWorkingDirectory),
					File.separator),
				testClassNamesIncludesRelativeGlobs.toArray(new String[0])));
	}

	private static final boolean _ENABLE_INCLUDE_AUTO_BALANCE_TESTS_DEFAULT =
		false;

	private static final String _GLOB_MODULES_PRIVATE = "modules/private/**";

	private final List<File> _autoBalanceTestFiles = new ArrayList<>();
	private boolean _includeAutoBalanceTests;
	private final boolean _includeUnstagedTestClassFiles;
	private final File _rootWorkingDirectory;

}