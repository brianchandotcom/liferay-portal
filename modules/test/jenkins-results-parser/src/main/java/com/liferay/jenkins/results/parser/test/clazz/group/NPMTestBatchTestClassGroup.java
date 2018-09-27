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
	public AxisTestClassGroup getAxisTestClassGroup(int axisId) {
		if (axisId != 0) {
			throw new IllegalArgumentException("axisId is not 0");
		}

		AxisTestClassGroup axisTestClassGroup = axisTestClassGroups.get(axisId);

		if (axisTestClassGroup != null) {
			return axisTestClassGroups.get(axisId);
		}

		return new AxisTestClassGroup(this, axisId);
	}

	public Map<File, NPMTestBatchTestClass> getNPMTestBatchTestClasses() {
		return NPMTestBatchTestClass.getNPMTestBatchTestClasses();
	}

	public void writeTestCSVReportFile() throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append("Module Name");
		sb.append(",");
		sb.append("Class Name");
		sb.append(",");
		sb.append("Method Name");
		sb.append(",");
		sb.append("Ignored");
		sb.append(",");
		sb.append("File Path");
		sb.append("\n");

		Map<File, NPMTestBatchTestClass> npmTestBatchTestClasses =
			getNPMTestBatchTestClasses();

		for (Map.Entry<File, NPMTestBatchTestClassGroup.NPMTestBatchTestClass>
				entry : npmTestBatchTestClasses.entrySet()) {

			NPMTestBatchTestClassGroup.NPMTestBatchTestClass
				npmTestBatchTestClass = entry.getValue();

			File moduleFile = npmTestBatchTestClass.getFile();

			String moduleName = moduleFile.getName();

			List<BaseTestClassGroup.BaseTestMethod> jsTestMethods =
				npmTestBatchTestClass.getJSTestMethods();

			for (BaseTestClassGroup.BaseTestMethod jsTestMethod :
					jsTestMethods) {

				String classMethodName = jsTestMethod.getName();

				int colonIndex = classMethodName.indexOf("::");

				String filePath = classMethodName.substring(0, colonIndex);

				String className = filePath.substring(
					filePath.lastIndexOf("/") + 1);

				String methodName = classMethodName.substring(
					colonIndex + "::".length());

				sb.append(moduleName);
				sb.append(",");
				sb.append(className);
				sb.append(",");
				sb.append(StringEscapeUtils.escapeCsv(methodName));
				sb.append(",");

				if (jsTestMethod.isIgnored()) {
					sb.append("TRUE");
				}
				else {
					sb.append("");
				}

				sb.append(",");
				sb.append(filePath);
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

	public static class NPMTestBatchTestClass extends BaseTestClass {

		public List<BaseTestMethod> getJSTestMethods() {
			return _jsTestMethods;
		}

		protected static NPMTestBatchTestClass getInstance(
			String batchName, File moduleDir) {

			if (_npmTestBatchTestClasses.containsKey(moduleDir)) {
				return _npmTestBatchTestClasses.get(moduleDir);
			}

			_npmTestBatchTestClasses.put(
				moduleDir, new NPMTestBatchTestClass(batchName, moduleDir));

			return _npmTestBatchTestClasses.get(moduleDir);
		}

		protected static Map<File, NPMTestBatchTestClass>
			getNPMTestBatchTestClasses() {

			return _npmTestBatchTestClasses;
		}

		protected NPMTestBatchTestClass(String batchName, File file) {
			super(file);

			addTestMethod(batchName);

			_moduleFile = file;

			initJSTestMethods();
		}

		protected void initJSTestMethods() {
			List<File> jsFiles = JenkinsResultsParserUtil.findFiles(
				_moduleFile, ".*\\.js");

			for (File jsFile : jsFiles) {
				try {
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

						_jsTestMethods.add(
							new BaseTestMethod(
								methodIgnored,
								jsFile.getAbsolutePath() + "::" + methodName,
								this));
					}
				}
				catch (IOException ioe) {
					throw new RuntimeException(ioe);
				}
			}
		}

		private static final Pattern _itPattern = Pattern.compile(
			"\\s+(?<xit>x)?it\\s*\\(\\s*\\'(?<description>[\\s\\S]*?)\\'");
		private static final Map<File, NPMTestBatchTestClass>
			_npmTestBatchTestClasses = new HashMap<>();

		private final List<BaseTestMethod> _jsTestMethods = new ArrayList<>();
		private final File _moduleFile;

	}

	protected NPMTestBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		List<File> moduleDirs;

		try {
			if (testRelevantChanges) {
				moduleDirs =
					portalGitWorkingDirectory.
						getModifiedNPMTestModuleDirsList();
			}
			else {
				moduleDirs =
					portalGitWorkingDirectory.getNPMTestModuleDirsList();
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		if (moduleDirs.isEmpty()) {
			return;
		}

		AxisTestClassGroup axisTestClassGroup = new AxisTestClassGroup(this, 0);

		for (File moduleDir : moduleDirs) {
			NPMTestBatchTestClass npmTestBatchTestClass =
				NPMTestBatchTestClass.getInstance(batchName, moduleDir);

			testClasses.add(npmTestBatchTestClass);

			axisTestClassGroup.addTestClass(npmTestBatchTestClass);
		}

		axisTestClassGroups.put(0, axisTestClassGroup);
	}

}