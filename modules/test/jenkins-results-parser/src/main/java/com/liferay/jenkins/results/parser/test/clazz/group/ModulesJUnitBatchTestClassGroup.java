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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yi-Chen Tsai
 */
public class ModulesJUnitBatchTestClassGroup extends JUnitBatchTestClassGroup {

	protected ModulesJUnitBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected List<String> getReleaseTestClassNamesRelativeGlobs(
		List<String> testClassNamesRelativeGlobs) {

		Set<File> releaseModuleAppDirs = _getReleaseModuleAppDirs();

		if (releaseModuleAppDirs.isEmpty()) {
			return testClassNamesRelativeGlobs;
		}

		List<String> testClassNameRelativeGlobs = new ArrayList<>();

		for (File releaseModuleAppDir : releaseModuleAppDirs) {
			String releaseModuleAppAbsolutePath =
				releaseModuleAppDir.getAbsolutePath();

			String appSourceRelativePath =
				releaseModuleAppAbsolutePath.substring(
					releaseModuleAppAbsolutePath.indexOf("modules/"));

			for (String testClassNamesRelativeGlob :
					testClassNamesRelativeGlobs) {

				testClassNameRelativeGlobs.add(
					JenkinsResultsParserUtil.combine(
						appSourceRelativePath, "/",
						testClassNamesRelativeGlob));

				if (testClassNamesRelativeGlob.startsWith("**/")) {
					testClassNameRelativeGlobs.add(
						JenkinsResultsParserUtil.combine(
							appSourceRelativePath, "/",
							testClassNamesRelativeGlob.substring(3)));
				}
			}
		}

		return testClassNameRelativeGlobs;
	}

	@Override
	protected List<String> getRelevantTestClassNamesRelativeGlobs(
		List<String> testClassNamesRelativeGlobs) {

		List<String> relevantTestClassNameRelativeGlobs = new ArrayList<>();

		List<File> modifiedModuleDirsList = null;

		try {
			modifiedModuleDirsList =
				portalGitWorkingDirectory.getModifiedModuleDirsList();
		}
		catch (IOException ioe) {
			File workingDirectory =
				portalGitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get relevant module group directories in ",
					workingDirectory.getPath()),
				ioe);
		}

		String requiredModules = getFirstPropertyValue(
			"modules.includes.required");

		if (requiredModules != null) {
			File modulesDir = new File(
				portalGitWorkingDirectory.getWorkingDirectory(), "modules");

			for (String requiredModule : requiredModules.split(",")) {
				modifiedModuleDirsList.add(
					new File(modulesDir, requiredModule));
			}
		}

		Matcher matcher = _singleModuleBatchNamePattern.matcher(batchName);

		String moduleName = null;

		if (matcher.find()) {
			moduleName = matcher.group("moduleName");
		}

		for (File modifiedModuleDir : modifiedModuleDirsList) {
			String modifiedModuleAbsolutePath =
				modifiedModuleDir.getAbsolutePath();

			String modifiedModuleRelativePath =
				modifiedModuleAbsolutePath.substring(
					modifiedModuleAbsolutePath.indexOf("modules/"));

			if ((moduleName != null) &&
				!modifiedModuleRelativePath.contains("/" + moduleName)) {

				continue;
			}

			for (String testClassNamesRelativeGlob :
					testClassNamesRelativeGlobs) {

				relevantTestClassNameRelativeGlobs.add(
					JenkinsResultsParserUtil.combine(
						modifiedModuleRelativePath, "/",
						testClassNamesRelativeGlob));

				if (testClassNamesRelativeGlob.startsWith("**/")) {
					relevantTestClassNameRelativeGlobs.add(
						JenkinsResultsParserUtil.combine(
							modifiedModuleRelativePath, "/",
							testClassNamesRelativeGlob.substring(3)));
				}
			}
		}

		return relevantTestClassNameRelativeGlobs;
	}

	private String _getAppSuiteTitle(File appBndFile) {
		Properties appBndProperties = JenkinsResultsParserUtil.getProperties(
			appBndFile);

		String appSuite = appBndProperties.getProperty("Liferay-Releng-Suite");

		File appSuiteBndFile = new File(
			portalGitWorkingDirectory.getWorkingDirectory(),
			"modules/suites/" + appSuite + "/suite.bnd");

		if (!appSuiteBndFile.exists()) {
			return "";
		}

		Properties appSuiteProperties = JenkinsResultsParserUtil.getProperties(
			appSuiteBndFile);

		String appSuiteTitle = appSuiteProperties.getProperty(
			"Liferay-Releng-Suite-Title");

		appSuiteTitle = appSuiteTitle.replace(
			"${liferay.releng.app.title.prefix}", _getAppTitlePrefix());

		return appSuiteTitle;
	}

	private String _getAppTitle(File appBndFile) {
		Properties appBndProperties = JenkinsResultsParserUtil.getProperties(
			appBndFile);

		String appTitle = appBndProperties.getProperty(
			"Liferay-Releng-App-Title");

		appTitle = appTitle.replace(
			"${liferay.releng.app.title.prefix}", _getAppTitlePrefix());

		return appTitle;
	}

	private String _getAppTitlePrefix() {
		String portalBranchName =
			portalGitWorkingDirectory.getUpstreamBranchName();

		if (portalBranchName.contains("-private")) {
			return "Liferay";
		}

		return "Liferay CE";
	}

	private Set<String> _getBundledAppNames() {
		Set<String> bundledAppNames = new HashSet<>();

		File liferayHome = _getLiferayHome();

		if ((liferayHome == null) || !liferayHome.exists()) {
			return bundledAppNames;
		}

		List<File> bundledApps = JenkinsResultsParserUtil.findFiles(
			liferayHome, ".*\\.lpkg");

		for (File bundledApp : bundledApps) {
			String bundledAppName = bundledApp.getName();

			bundledAppNames.add(bundledAppName);
		}

		return bundledAppNames;
	}

	private File _getLiferayHome() {
		Properties buildProperties = JenkinsResultsParserUtil.getProperties(
			new File(
				portalGitWorkingDirectory.getWorkingDirectory(),
				"build.properties"));

		String liferayHomePath = buildProperties.getProperty("liferay.home");

		if (liferayHomePath == null) {
			return null;
		}

		return new File(liferayHomePath);
	}

	private Set<File> _getReleaseModuleAppDirs() {
		Set<String> bundledAppNames = _getBundledAppNames();

		try {
			Set<File> releaseModuleAppDirs = new HashSet<>();

			for (File moduleAppDir :
					portalGitWorkingDirectory.getModuleAppDirs()) {

				File appBndFile = new File(moduleAppDir, "app.bnd");

				String appTitle = _getAppTitle(appBndFile);
				String appSuiteTitle = _getAppSuiteTitle(appBndFile);

				for (String bundledAppName : bundledAppNames) {
					if (bundledAppName.contains(appSuiteTitle + " -") &&
						bundledAppName.contains(appTitle + " -")) {

						releaseModuleAppDirs.add(moduleAppDir);

						continue;
					}

					if (bundledAppName.contains(appTitle + ".lpkg")) {
						releaseModuleAppDirs.add(moduleAppDir);

						continue;
					}
				}
			}

			return releaseModuleAppDirs;
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static final Pattern _singleModuleBatchNamePattern =
		Pattern.compile("modules-unit-(?<moduleName>\\S+)-jdk\\d+");

}