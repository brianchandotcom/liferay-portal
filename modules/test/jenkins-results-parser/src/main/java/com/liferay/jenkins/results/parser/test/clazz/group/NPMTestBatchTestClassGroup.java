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

import com.liferay.jenkins.results.parser.GitWorkingDirectory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author Michael Hashimoto
 */
public class NPMTestBatchTestClassGroup extends BatchTestClassGroup {

	@Override
	public int getAxisCount() {
		if (!isStableTestSuiteBatch() && testRelevantIntegrationUnitOnly) {
			return 0;
		}

		return super.getAxisCount();
	}

	@Override
	public AxisTestClassGroup getAxisTestClassGroup(int axisId) {
		if (axisId != 0) {
			throw new IllegalArgumentException("axisId is not 0");
		}

		AxisTestClassGroup axisTestClassGroup = axisTestClassGroups.get(axisId);

		if (axisTestClassGroup != null) {
			return axisTestClassGroups.get(axisId);
		}

		return TestClassGroupFactory.newAxisTestClassGroup(this);
	}

	public Map<File, NPMTestBatchTestClass> getNPMTestBatchTestClasses() {
		return NPMTestBatchTestClass.getNPMTestBatchTestClasses();
	}

	public void writeTestCSVReportFile() throws Exception {
		CSVReport csvReport = new CSVReport(
			new CSVReport.Row(
				"Module Name", "Class Name", "Method Name", "Ignored",
				"File Path"));

		Map<File, NPMTestBatchTestClass> npmTestBatchTestClasses =
			getNPMTestBatchTestClasses();

		for (NPMTestBatchTestClassGroup.NPMTestBatchTestClass
				npmTestBatchTestClass : npmTestBatchTestClasses.values()) {

			File moduleTestClassFile = npmTestBatchTestClass.getTestClassFile();

			String moduleName = moduleTestClassFile.getName();

			List<TestClassGroup.TestClass.TestClassMethod> jsTestClassMethods =
				npmTestBatchTestClass.getJSTestClassMethods();

			for (TestClassGroup.TestClass.TestClassMethod jsTestClassMethod :
					jsTestClassMethods) {

				String classMethodName = jsTestClassMethod.getName();

				int colonIndex = classMethodName.indexOf(
					_TOKEN_CLASS_METHOD_SEPARATOR);

				String filePath = classMethodName.substring(0, colonIndex);

				String className = filePath.substring(
					filePath.lastIndexOf("/") + 1);

				String methodName = classMethodName.substring(
					colonIndex + _TOKEN_CLASS_METHOD_SEPARATOR.length());

				CSVReport.Row csvReportRow = new CSVReport.Row();

				csvReportRow.add(moduleName);
				csvReportRow.add(className);
				csvReportRow.add(StringEscapeUtils.escapeCsv(methodName));

				if (jsTestClassMethod.isIgnored()) {
					csvReportRow.add("TRUE");
				}
				else {
					csvReportRow.add("");
				}

				csvReportRow.add(filePath);

				csvReport.addRow(csvReportRow);
			}
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");

		File csvReportFile = new File(
			JenkinsResultsParserUtil.combine(
				"Report_js_", simpleDateFormat.format(new Date()), ".csv"));

		try {
			JenkinsResultsParserUtil.write(csvReportFile, csvReport.toString());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static class NPMTestBatchTestClass extends BaseTestClass {

		public List<TestClassGroup.TestClass.TestClassMethod>
			getJSTestClassMethods() {

			return _jsTestClassMethods;
		}

		protected static NPMTestBatchTestClass getInstance(
			String batchName, GitWorkingDirectory gitWorkingDirectory,
			File moduleDir) {

			if (_npmTestBatchTestClasses.containsKey(moduleDir)) {
				return _npmTestBatchTestClasses.get(moduleDir);
			}

			_npmTestBatchTestClasses.put(
				moduleDir,
				new NPMTestBatchTestClass(
					batchName, gitWorkingDirectory,
					new File(
						JenkinsResultsParserUtil.getCanonicalPath(moduleDir))));

			return _npmTestBatchTestClasses.get(moduleDir);
		}

		protected static Map<File, NPMTestBatchTestClass>
			getNPMTestBatchTestClasses() {

			return _npmTestBatchTestClasses;
		}

		protected NPMTestBatchTestClass(
			String batchName, GitWorkingDirectory gitWorkingDirectory,
			File testClassFile) {

			super(testClassFile);

			addTestClassMethod(batchName);

			_gitWorkingDirectory = gitWorkingDirectory;

			_moduleFile = testClassFile;

			initJSTestClassMethods();
		}

		protected void initJSTestClassMethods() {
			List<File> jsFiles = JenkinsResultsParserUtil.findFiles(
				_moduleFile, ".*\\.(js|ts|tsx)");

			String workingDirectoryPath =
				JenkinsResultsParserUtil.getCanonicalPath(
					_gitWorkingDirectory.getWorkingDirectory());

			for (File jsFile : jsFiles) {
				try {
					String jsFileRelativePath =
						JenkinsResultsParserUtil.getCanonicalPath(jsFile);

					jsFileRelativePath = jsFileRelativePath.replace(
						workingDirectoryPath, "");

					String jsFileContent = JenkinsResultsParserUtil.read(
						jsFile);

					Matcher matcher = _itPattern.matcher(jsFileContent);

					while (matcher.find()) {
						String methodName = matcher.group("description");

						String xit = matcher.group("xit");

						boolean methodIgnored = false;

						if (xit != null) {
							methodIgnored = true;
						}

						_jsTestClassMethods.add(
							new TestClassMethod(
								methodIgnored,
								jsFileRelativePath +
									_TOKEN_CLASS_METHOD_SEPARATOR + methodName,
								this));
					}
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}
		}

		private static final Pattern _itPattern = Pattern.compile(
			"\\s+(?<xit>x)?it\\s*\\(\\s*\\'(?<description>[\\s\\S]*?)\\'");
		private static final Map<File, NPMTestBatchTestClass>
			_npmTestBatchTestClasses = new HashMap<>();

		private final GitWorkingDirectory _gitWorkingDirectory;
		private final List<TestClassMethod> _jsTestClassMethods =
			new ArrayList<>();
		private final File _moduleFile;

	}

	protected NPMTestBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		List<File> moduleDirs;

		try {
			if (testRelevantChanges &&
				!(includeStableTestSuite && isStableTestSuiteBatch())) {

				moduleDirs =
					portalGitWorkingDirectory.
						getModifiedNPMTestModuleDirsList();
			}
			else {
				moduleDirs =
					portalGitWorkingDirectory.getNPMTestModuleDirsList();
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		if (moduleDirs.isEmpty()) {
			return;
		}

		AxisTestClassGroup axisTestClassGroup =
			TestClassGroupFactory.newAxisTestClassGroup(this);

		for (File moduleDir : moduleDirs) {
			NPMTestBatchTestClass npmTestBatchTestClass =
				NPMTestBatchTestClass.getInstance(
					batchName, portalGitWorkingDirectory,
					new File(
						JenkinsResultsParserUtil.getCanonicalPath(moduleDir)));

			testClasses.add(npmTestBatchTestClass);

			axisTestClassGroup.addTestClass(npmTestBatchTestClass);
		}

		axisTestClassGroups.add(0, axisTestClassGroup);
	}

	private static final String _TOKEN_CLASS_METHOD_SEPARATOR = "::";

}