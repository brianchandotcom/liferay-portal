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
import java.io.IOException;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yi-Chen Tsai
 */
public class JUnitBatchTestClassGroup extends BatchTestClassGroup {

	protected JUnitBatchTestClassGroup(
		String batchName, PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		super(batchName, portalGitWorkingDirectory, testSuiteName);

		_setTestClassNamesExcludesRelativeGlobs();
		_setTestClassNamesIncludesRelativeGlobs();

		setTestClasses();

		_setIncludeAutoBalanceTests();

		setAxisTestClassGroups();
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

	protected void setTestClasses() {
		File workingDirectory = portalGitWorkingDirectory.getWorkingDirectory();

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

					private TestClass _getPackagePathClassFile(Path path) {
						String filePath = path.toString();

						Matcher matcher = _packagePathPattern.matcher(filePath);

						if (matcher.find()) {
							String packagePath = matcher.group("packagePath");

							packagePath = packagePath.replace(
								".java", ".class");

							return JunitTestClass.getInstance(
								portalGitWorkingDirectory,
								new File(packagePath), path.toFile());
						}

						return JunitTestClass.getInstance(
							portalGitWorkingDirectory,
							new File(filePath.replace(".java", ".class")),
							path.toFile());
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

	protected static class JunitTestClass extends TestClass {

		protected static JunitTestClass getInstance(
			GitWorkingDirectory gitWorkingDirectory, File file, File srcFile) {

			if (_junitTestClasses.containsKey(file)) {
				return _junitTestClasses.get(file);
			}

			JunitTestClass junitTestClass = new JunitTestClass(
				gitWorkingDirectory, file, srcFile);

			_junitTestClasses.put(file, junitTestClass);

			return junitTestClass;
		}

		protected static JunitTestClass getInstance(
			GitWorkingDirectory gitWorkingDirectory, String fullClassName) {

			String filePath = fullClassName.substring(
				0, fullClassName.lastIndexOf("."));

			filePath = filePath.replace(".", "/");

			String simpleClassName = fullClassName.substring(
				fullClassName.lastIndexOf(".") + 1);

			File file = new File(filePath, simpleClassName + ".class");

			if (_junitTestClasses.containsKey(file)) {
				return _junitTestClasses.get(file);
			}

			String srcFileName = simpleClassName + ".java";

			List<File> srcFiles = JenkinsResultsParserUtil.findFiles(
				gitWorkingDirectory.getWorkingDirectory(), srcFileName);

			File matchingSrcFile = null;

			for (File srcFile : srcFiles) {
				String srcFilePath = srcFile.toString();

				if (srcFilePath.contains(filePath)) {
					matchingSrcFile = srcFile;

					break;
				}
			}

			if (matchingSrcFile == null) {
				throw new RuntimeException("No matching files found.");
			}

			if (srcFiles.size() > 1) {
			}

			return getInstance(gitWorkingDirectory, file, matchingSrcFile);
		}

		protected JunitTestClass(
			GitWorkingDirectory gitWorkingDirectory, File file, File srcFile) {

			super(file);

			String srcFileName = srcFile.getName();

			if (!srcFileName.endsWith(".java")) {
				throw new RuntimeException("Invalid Junit Test Class");
			}

			_gitWorkingDirectory = gitWorkingDirectory;
			_srcFile = srcFile;

			try {
				_srcFileContent = JenkinsResultsParserUtil.read(_srcFile);
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}

			_className = _getClassName();
			_packageName = _getPackageName();

			try {
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
			Matcher packageNameMatcher = _packageNamePattern.matcher(
				_srcFileContent);

			if (!packageNameMatcher.find()) {
				throw new RuntimeException("No package found in " + _srcFile);
			}

			return packageNameMatcher.group("packageName");
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
			Matcher methodHeaderMatcher = _methodHeaderPattern.matcher(
				_srcFileContent);

			while (methodHeaderMatcher.find()) {
				String annotations = methodHeaderMatcher.group("annotations");
				String methodName = methodHeaderMatcher.group("methodName");

				if (annotations.contains("@Test")) {
					addTestMethod(methodName);
				}
			}

			String parentFullClassName = _getParentFullClassName();

			if (parentFullClassName == null) {
				return;
			}

			JunitTestClass parentJunitTestClass = getInstance(
				_gitWorkingDirectory, parentFullClassName);

			for (TestMethod testMethod :
					parentJunitTestClass.getTestMethods()) {

				addTestMethod(testMethod);
			}
		}

		private static final Map<File, JunitTestClass> _junitTestClasses =
			new HashMap<>();
		private static Pattern _methodHeaderPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"\\t(?<annotations>(@[\\s\\S]+?))public\\s+void\\s+",
				"(?<methodName>[^\\(\\s]+)"));
		private static Pattern _packageNamePattern = Pattern.compile(
			"package (?<packageName>[^;]+);");

		private final String _className;
		private final GitWorkingDirectory _gitWorkingDirectory;
		private final String _packageName;
		private final File _srcFile;
		private final String _srcFileContent;

	}

	private String _getTestClassNamesExcludesPropertyValue() {
		String propertyValue = getFirstPropertyValue(
			"test.batch.class.names.excludes");

		if (propertyValue != null) {
			return propertyValue;
		}

		return JenkinsResultsParserUtil.getProperty(
			portalTestProperties, "test.class.names.excludes");
	}

	private String _getTestClassNamesIncludesPropertyValue() {
		String propertyValue = getFirstPropertyValue(
			"test.batch.class.names.includes");

		if (propertyValue != null) {
			return propertyValue;
		}

		return JenkinsResultsParserUtil.getProperty(
			portalTestProperties, "test.class.names.includes");
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

	private void _setIncludeAutoBalanceTests() {
		if (!testClasses.isEmpty()) {
			includeAutoBalanceTests = true;

			return;
		}

		List<File> modifiedJavaFilesList =
			portalGitWorkingDirectory.getModifiedFilesList(".java");

		if (!modifiedJavaFilesList.isEmpty()) {
			includeAutoBalanceTests = true;

			return;
		}

		includeAutoBalanceTests = _DEFAULT_INCLUDE_AUTO_BALANCE_TESTS;
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

		if (testRelevantChanges) {
			testClassNamesExcludesRelativeGlobs =
				getRelevantTestClassNamesRelativeGlobs(
					testClassNamesExcludesRelativeGlobs);
		}

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

		List<String> testClassNamesIncludesRelativeGlobs = Arrays.asList(
			testClassNamesIncludesPropertyValue.split(","));

		if (testRelevantChanges) {
			testClassNamesIncludesRelativeGlobs =
				getRelevantTestClassNamesRelativeGlobs(
					testClassNamesIncludesRelativeGlobs);
		}

		testClassNamesIncludesPathMatchers.addAll(
			_getTestClassNamesPathMatchers(
				testClassNamesIncludesRelativeGlobs));
	}

	private static final boolean _DEFAULT_INCLUDE_AUTO_BALANCE_TESTS = false;

	private final Pattern _packagePathPattern = Pattern.compile(
		".*/(?<packagePath>com/.*)");

}