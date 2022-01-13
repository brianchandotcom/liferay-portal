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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
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
	protected List<String> getReleaseTestClassNamesRelativeIncludesGlobs(
		List<String> testClassNamesRelativeIncludesGlobs) {

		Set<File> releaseModuleAppDirs = _getReleaseModuleAppDirs();

		if (releaseModuleAppDirs.isEmpty()) {
			return testClassNamesRelativeIncludesGlobs;
		}

		List<String> testClassNameRelativeIncludesGlobs = new ArrayList<>();

		for (File releaseModuleAppDir : releaseModuleAppDirs) {
			String releaseModuleAppAbsolutePath =
				JenkinsResultsParserUtil.getCanonicalPath(releaseModuleAppDir);

			String appSourceRelativePath =
				releaseModuleAppAbsolutePath.substring(
					releaseModuleAppAbsolutePath.indexOf("modules/"));

			for (String testClassNamesRelativeGlob :
					testClassNamesRelativeIncludesGlobs) {

				testClassNameRelativeIncludesGlobs.add(
					JenkinsResultsParserUtil.combine(
						appSourceRelativePath, "/",
						testClassNamesRelativeGlob));

				if (testClassNamesRelativeGlob.startsWith("**/")) {
					testClassNameRelativeIncludesGlobs.add(
						JenkinsResultsParserUtil.combine(
							appSourceRelativePath, "/",
							testClassNamesRelativeGlob.substring(3)));
				}
			}
		}

		return testClassNameRelativeIncludesGlobs;
	}

	@Override
	protected List<String> getRelevantTestClassNamesRelativeExcludesGlobs() {
		Set<File> modifiedModuleDirsList = new HashSet<>();

		try {
			modifiedModuleDirsList.addAll(
				portalGitWorkingDirectory.getModifiedModuleDirsList());
		}
		catch (IOException ioException) {
			File workingDirectory =
				portalGitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get relevant module group directories in ",
					workingDirectory.getPath()),
				ioException);
		}

		List<String> relevantTestClassNameRelativeExcludesGlobs =
			new ArrayList<>();

		for (File modifiedModuleDir : modifiedModuleDirsList) {
			String modulesTestBatchClassNamesExcludes = null;

			File modifiedDirTestProperties = new File(
				modifiedModuleDir, "test.properties");

			Properties testProperties = JenkinsResultsParserUtil.getProperties(
				modifiedDirTestProperties);

			if (modifiedDirTestProperties.exists()) {
				String firstMatchingPropertyName = getFirstMatchingPropertyName(
					"modules.includes.required.test.batch.class.names.excludes",
					testProperties, testSuiteName);

				if (firstMatchingPropertyName != null) {
					modulesTestBatchClassNamesExcludes =
						JenkinsResultsParserUtil.getProperty(
							testProperties, firstMatchingPropertyName);
				}
			}

			if (modulesTestBatchClassNamesExcludes == null) {
				continue;
			}

			for (String modulesTestBatchClassNamesExclude :
					JenkinsResultsParserUtil.getGlobsFromProperty(
						modulesTestBatchClassNamesExcludes)) {

				relevantTestClassNameRelativeExcludesGlobs.add(
					JenkinsResultsParserUtil.combine(
						"modules/", modulesTestBatchClassNamesExclude));
			}
		}

		return relevantTestClassNameRelativeExcludesGlobs;
	}

	@Override
	protected List<String> getRelevantTestClassNamesRelativeIncludesGlobs(
		List<String> testClassNamesRelativeIncludesGlobs) {

		Set<File> modifiedModuleDirsList = new HashSet<>();

		try {
			modifiedModuleDirsList.addAll(
				portalGitWorkingDirectory.getModifiedModuleDirsList());
		}
		catch (IOException ioException) {
			File workingDirectory =
				portalGitWorkingDirectory.getWorkingDirectory();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get relevant module group directories in ",
					workingDirectory.getPath()),
				ioException);
		}

		List<String> relevantTestClassNameRelativeIncludesGlobs =
			new ArrayList<>();

		if (testRelevantChanges) {
			modifiedModuleDirsList.addAll(
				getRequiredModuleDirs(
					Lists.newArrayList(modifiedModuleDirsList)));
		}

		Matcher matcher = _singleModuleBatchNamePattern.matcher(batchName);

		String moduleName = null;

		if (matcher.find()) {
			moduleName = matcher.group("moduleName");
		}

		for (File modifiedModuleDir : modifiedModuleDirsList) {
			String modifiedModuleAbsolutePath =
				JenkinsResultsParserUtil.getCanonicalPath(modifiedModuleDir);

			String modifiedModuleRelativePath =
				modifiedModuleAbsolutePath.substring(
					modifiedModuleAbsolutePath.indexOf("modules/"));

			if ((moduleName != null) &&
				!modifiedModuleRelativePath.contains("/" + moduleName)) {

				continue;
			}

			for (String testClassNamesRelativeGlob :
					testClassNamesRelativeIncludesGlobs) {

				relevantTestClassNameRelativeIncludesGlobs.add(
					JenkinsResultsParserUtil.combine(
						modifiedModuleRelativePath, "/",
						testClassNamesRelativeGlob));

				if (testClassNamesRelativeGlob.startsWith("**/")) {
					relevantTestClassNameRelativeIncludesGlobs.add(
						JenkinsResultsParserUtil.combine(
							modifiedModuleRelativePath, "/",
							testClassNamesRelativeGlob.substring(3)));
				}
			}

			String modulesTestBatchClassNamesIncludes = null;

			File modifiedDirTestProperties = new File(
				modifiedModuleDir, "test.properties");

			if (modifiedDirTestProperties.exists()) {
				Properties testProperties =
					JenkinsResultsParserUtil.getProperties(
						modifiedDirTestProperties);

				String firstMatchingPropertyName = getFirstMatchingPropertyName(
					"modules.includes.required.test.batch.class.names.includes",
					testProperties, testSuiteName);

				if (firstMatchingPropertyName != null) {
					modulesTestBatchClassNamesIncludes =
						JenkinsResultsParserUtil.getProperty(
							testProperties, firstMatchingPropertyName);
				}
			}

			if (modulesTestBatchClassNamesIncludes == null) {
				continue;
			}

			for (String modulesTestBatchClassNamesInclude :
					JenkinsResultsParserUtil.getGlobsFromProperty(
						modulesTestBatchClassNamesIncludes)) {

				relevantTestClassNameRelativeIncludesGlobs.add(
					JenkinsResultsParserUtil.combine(
						"modules/", modulesTestBatchClassNamesInclude));
			}
		}

		return relevantTestClassNameRelativeIncludesGlobs;
	}

	@Override
	protected void setTestClassNamesExcludesRelativeGlobs() {
		super.setTestClassNamesExcludesRelativeGlobs();

		if (!testRelevantChanges) {
			List<File> modulePullSubrepoDirs =
				portalGitWorkingDirectory.getModulePullSubrepoDirs();

			for (File modulePullSubrepoDir : modulePullSubrepoDirs) {
				testClassNamesExcludesPathMatchers.addAll(
					JenkinsResultsParserUtil.toPathMatchers(
						modulePullSubrepoDir.getAbsolutePath(), "/**"));
			}
		}
	}

	private String _getAppTitle(File appBndFile) {
		Properties appBndProperties = JenkinsResultsParserUtil.getProperties(
			appBndFile);

		String appTitle = appBndProperties.getProperty(
			"Liferay-Releng-App-Title");

		return appTitle.replace(
			"${liferay.releng.app.title.prefix}", _getAppTitlePrefix());
	}

	private String _getAppTitlePrefix() {
		Job job = getJob();

		if (job.getBuildProfile() == Job.BuildProfile.DXP) {
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

		Set<File> releaseModuleAppDirs = new HashSet<>();

		for (File moduleAppDir : portalGitWorkingDirectory.getModuleAppDirs()) {
			File appBndFile = new File(moduleAppDir, "app.bnd");

			String appTitle = _getAppTitle(appBndFile);

			for (String bundledAppName : bundledAppNames) {
				String regex = JenkinsResultsParserUtil.combine(
					"((.* - )?", Pattern.quote(appTitle), " -.*|",
					Pattern.quote(appTitle), ")\\.lpkg");

				if (!bundledAppName.matches(regex)) {
					continue;
				}

				List<File> skipTestIntegrationCheckFiles =
					JenkinsResultsParserUtil.findFiles(
						moduleAppDir,
						".lfrbuild-ci-skip-test-integration-check");

				if (!skipTestIntegrationCheckFiles.isEmpty()) {
					System.out.println("Ignoring " + moduleAppDir);

					continue;
				}

				releaseModuleAppDirs.add(moduleAppDir);
			}
		}

		return releaseModuleAppDirs;
	}

	private static final Pattern _singleModuleBatchNamePattern =
		Pattern.compile("modules-unit-(?<moduleName>\\S+)-jdk\\d+");

}