/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.clazz.JSUnitJUnitTestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;
import com.liferay.jenkins.results.parser.test.clazz.TestClassFactory;
import com.liferay.jenkins.results.parser.test.clazz.TestClassMethod;
import com.liferay.jenkins.results.parser.test.clazz.file.TestPackage;
import com.liferay.jenkins.results.parser.test.clazz.file.TestPackageFactory;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JSUnitModulesBatchTestClassGroup
	extends ModulesBatchTestClassGroup {

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put(
			"test_file_exclude_globs",
			_getTestFileGlobs("test.batch.test.file.excludes"));
		jsonObject.put(
			"test_file_include_globs",
			_getTestFileGlobs("test.batch.test.file.includes"));

		return jsonObject;
	}

	public boolean hasTestFileGlobs() {
		List<String> testFileExcludeGlobs = _getTestFileGlobs(
			"test.batch.test.file.excludes");
		List<String> testFileIncludeGlobs = _getTestFileGlobs(
			"test.batch.test.file.includes");

		if (testFileExcludeGlobs.isEmpty() && testFileIncludeGlobs.isEmpty()) {
			return false;
		}

		return true;
	}

	protected JSUnitModulesBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);
	}

	protected JSUnitModulesBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	protected List<File> getBaseModuleDirs() throws IOException {
		return new ArrayList<>(
			portalGitWorkingDirectory.getModuleDirsList(
				getExcludesPathMatchers(), getIncludesPathMatchers()));
	}

	protected List<PathMatcher> getExcludesPathMatchers() {
		return getPathMatchers(getExcludesJobProperties());
	}

	protected String getTestClassMethodName(File jsUnitFile) {
		return JenkinsResultsParserUtil.getPathRelativeTo(
			jsUnitFile, portalGitWorkingDirectory.getWorkingDirectory());
	}

	protected boolean isModulesProjectDir(File projectDir) {
		File buildGradleFile = new File(projectDir, "build.gradle");
		File packageJSONFile = new File(projectDir, "package.json");

		if (buildGradleFile.exists() && packageJSONFile.exists()) {
			return true;
		}

		return false;
	}

	protected boolean isSkippedProjectDir(File projectDir) {
		String projectDirPath = projectDir.getAbsolutePath();

		if (projectDirPath.contains("modules") &&
			!(projectDirPath.contains("modules/apps") ||
			  projectDirPath.contains("modules/dxp"))) {

			return true;
		}

		if (_isTestGitrepoJSUnit()) {
			return false;
		}

		File gitrepoFile = new File(projectDir, ".gitrepo");

		if (gitrepoFile.exists() && !projectDirPath.contains("osb-faro")) {
			return true;
		}

		return false;
	}

	@Override
	protected void setAxisTestClassGroups() {
		super.setAxisTestClassGroups();

		TestClass faroTestClass = null;
		AxisTestClassGroup originalAxisTestClassGroup = null;

		axisTestClassGroupLoop:
		for (AxisTestClassGroup axisTestClassGroup : axisTestClassGroups) {
			for (TestClass testClass : axisTestClassGroup.getTestClasses()) {
				String testClassName = testClass.getName();

				if (testClassName.contains("osb-faro")) {
					faroTestClass = testClass;

					originalAxisTestClassGroup = axisTestClassGroup;

					break axisTestClassGroupLoop;
				}
			}
		}

		if (faroTestClass != null) {
			originalAxisTestClassGroup.removeTestClass(faroTestClass);

			AxisTestClassGroup faroAxisTestClassGroup =
				TestClassGroupFactory.newAxisTestClassGroup(this);

			faroAxisTestClassGroup.addTestClass(faroTestClass);

			axisTestClassGroups.add(faroAxisTestClassGroup);
		}
	}

	@Override
	protected void setTestClasses() throws IOException {
		List<String> excludedTestMethodNames = new ArrayList<>();

		for (JobProperty excludesJobProperty : getExcludesJobProperties()) {
			String excludesJobPropertyValue = excludesJobProperty.getValue();

			if (excludesJobPropertyValue != null) {
				for (String excludesJobPropertyValueElement :
						excludesJobPropertyValue.split("\\s*,\\s*")) {

					excludesJobPropertyValueElement =
						excludesJobPropertyValueElement.replace("/", ":");

					excludedTestMethodNames.add(
						excludesJobPropertyValueElement.replaceAll(
							"[^a-zA-Z-:]", ""));
				}
			}
		}

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		List<PathMatcher> testFileExcludesPathMatchers =
			_getTestFilePathMatchers("test.batch.test.file.excludes");
		List<PathMatcher> testFileIncludesPathMatchers =
			_getTestFilePathMatchers("test.batch.test.file.includes");

		int jsUnitFileCount = 0;

		for (File baseModuleDir : getBaseModuleDirs()) {
			List<File> moduleTestDirs = _getModulesProjectDirs(baseModuleDir);

			for (File moduleTestDir : moduleTestDirs) {
				String moduleTestDirPath =
					JenkinsResultsParserUtil.getCanonicalPath(moduleTestDir);

				TestClass testClass = TestClassFactory.newTestClass(
					this, moduleTestDir);

				if (testClass instanceof JSUnitJUnitTestClass) {
					JSUnitJUnitTestClass jsUnitJUnitTestClass =
						(JSUnitJUnitTestClass)testClass;

					jsUnitJUnitTestClass.setTestClassFileReported(
						_isTestClassFileReported());
				}

				TestPackage testPackage = TestPackageFactory.newTestPackage(
					moduleTestDir);

				for (File jsUnitFile :
						portalGitWorkingDirectory.getJSUnitFiles()) {

					String jsUnitFilePath =
						JenkinsResultsParserUtil.getCanonicalPath(jsUnitFile);

					if (!jsUnitFilePath.startsWith(moduleTestDirPath) ||
						((testPackage != null) &&
						 testPackage.isTestClassFileIgnored(jsUnitFile))) {

						continue;
					}

					jsUnitFileCount++;

					if (!JenkinsResultsParserUtil.isFileIncluded(
							testFileExcludesPathMatchers,
							testFileIncludesPathMatchers,
							new File(jsUnitFilePath))) {

						continue;
					}

					testClass.addTestClassMethod(
						TestClassFactory.newTestClassMethod(
							false, getTestClassMethodName(jsUnitFile),
							testClass));
				}

				if (!testClass.hasTestClassMethods()) {
					continue;
				}

				List<TestClassMethod> testClassMethods =
					testClass.getTestClassMethods();

				Iterator<TestClassMethod> iterator =
					testClassMethods.iterator();

				while (iterator.hasNext()) {
					TestClassMethod testClassMethod = iterator.next();

					String testClassMethodName = testClassMethod.getName();

					testClassMethodName = testClassMethodName.replace("/", ":");

					for (String excludedMethodName : excludedTestMethodNames) {
						if (testClassMethodName.contains(excludedMethodName)) {
							iterator.remove();

							break;
						}
					}
				}

				if (!testClassMethods.isEmpty()) {
					addTestClass(testClass);
				}
			}
		}

		if (hasTestFileGlobs() && (jsUnitFileCount > 0) && !hasTestClasses()) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to select any of the ",
					String.valueOf(jsUnitFileCount), " test files in the ",
					batchName, " batch. Please check the modules.excludes, ",
					"modules.includes, test.batch.test.file.excludes and ",
					"test.batch.test.file.includes properties."));
		}
	}

	private List<File> _getModulesProjectDirs(File portalModulesBaseDir)
		throws IOException {

		List<File> modulesProjectDirs = new ArrayList<>();

		Files.walkFileTree(
			portalModulesBaseDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					File file = filePath.toFile();

					File currentDirectory =
						JenkinsResultsParserUtil.getCanonicalFile(file);

					if (isSkippedProjectDir(currentDirectory)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					if (!isModulesProjectDir(currentDirectory)) {
						return FileVisitResult.CONTINUE;
					}

					File packageJSONFile = new File(
						currentDirectory, "package.json");

					try {
						JSONObject packageJSONObject = new JSONObject(
							JenkinsResultsParserUtil.read(packageJSONFile));

						if (!packageJSONObject.has("scripts")) {
							return FileVisitResult.CONTINUE;
						}

						JSONObject scriptsJSONObject =
							packageJSONObject.getJSONObject("scripts");

						if (!scriptsJSONObject.has("test")) {
							return FileVisitResult.CONTINUE;
						}

						modulesProjectDirs.add(currentDirectory);

						return FileVisitResult.SKIP_SUBTREE;
					}
					catch (IOException | JSONException exception) {
						return FileVisitResult.CONTINUE;
					}
				}

			});

		return modulesProjectDirs;
	}

	private List<String> _getTestFileGlobs(String basePropertyName) {
		String jobPropertyValue = _getTestFileJobPropertyValue(
			basePropertyName);

		if (JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue)) {
			return Collections.emptyList();
		}

		return Arrays.asList(
			JenkinsResultsParserUtil.getGlobsFromProperty(jobPropertyValue));
	}

	private String _getTestFileJobPropertyValue(String basePropertyName) {
		JobProperty jobProperty = getJobProperty(
			basePropertyName, testSuiteName, batchName);

		String jobPropertyValue = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue)) {
			return null;
		}

		recordJobProperty(jobProperty);

		return jobPropertyValue;
	}

	private List<PathMatcher> _getTestFilePathMatchers(
		String basePropertyName) {

		return getPathMatchers(
			_getTestFileJobPropertyValue(basePropertyName),
			portalGitWorkingDirectory.getWorkingDirectory());
	}

	private boolean _isTestClassFileReported() {
		JobProperty jobProperty = getJobProperty("test.batch.report.type");

		String jobPropertyValue = jobProperty.getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue)) {
			return false;
		}

		recordJobProperty(jobProperty);

		return Objects.equals(jobPropertyValue, _REPORT_TYPE_TEST_FILE);
	}

	private boolean _isTestGitrepoJSUnit() {
		if (_testGitrepoJSUnit != null) {
			return _testGitrepoJSUnit;
		}

		JobProperty jobProperty = getJobProperty("test.gitrepo.js.unit");

		String jobPropertyValue = jobProperty.getValue();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue) &&
			jobPropertyValue.equals("true")) {

			recordJobProperty(jobProperty);

			_testGitrepoJSUnit = true;
		}
		else {
			_testGitrepoJSUnit = false;
		}

		return _testGitrepoJSUnit;
	}

	private static final String _REPORT_TYPE_TEST_FILE = "test-file";

	private Boolean _testGitrepoJSUnit;

}