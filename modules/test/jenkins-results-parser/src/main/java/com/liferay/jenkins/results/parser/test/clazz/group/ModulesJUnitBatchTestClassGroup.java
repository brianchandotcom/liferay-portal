/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.google.common.collect.Lists;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.job.property.JobProperty;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Yi-Chen Tsai
 */
public class ModulesJUnitBatchTestClassGroup extends JUnitBatchTestClassGroup {

	protected ModulesJUnitBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);
	}

	protected ModulesJUnitBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected List<JobProperty> getDefaultExcludesJobProperties() {
		List<JobProperty> excludesJobProperties = new ArrayList<>();

		excludesJobProperties.addAll(super.getDefaultExcludesJobProperties());

		for (File modulePullSubrepoDir :
				portalGitWorkingDirectory.getModulePullSubrepoDirs()) {

			excludesJobProperties.add(
				getJobProperty(
					"test.batch.class.names.excludes.subrepo",
					modulePullSubrepoDir, JobProperty.Type.EXCLUDE_GLOB));
		}

		return excludesJobProperties;
	}

	@Override
	protected List<JobProperty> getReleaseIncludesJobProperties() {
		List<JobProperty> includesJobProperties = new ArrayList<>();

		Set<File> releaseModuleAppDirs = _getReleaseModuleAppDirs();

		if (releaseModuleAppDirs == null) {
			return includesJobProperties;
		}

		for (File releaseModuleAppDir : releaseModuleAppDirs) {
			includesJobProperties.add(
				getJobProperty(
					"test.batch.class.names.includes.modules",
					releaseModuleAppDir, JobProperty.Type.INCLUDE_GLOB));
		}

		return includesJobProperties;
	}

	@Override
	protected List<JobProperty> getRelevantExcludesJobProperties() {
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

		List<JobProperty> excludesJobProperties = new ArrayList<>();

		excludesJobProperties.addAll(getDefaultExcludesJobProperties());

		for (File modifiedFile :
				portalGitWorkingDirectory.getModifiedFilesList()) {

			if (JenkinsResultsParserUtil.isPoshiFile(modifiedFile)) {
				continue;
			}

			excludesJobProperties.addAll(
				_getJobProperties(
					modifiedFile,
					"modules.includes.required.test.batch.class.names.excludes",
					JobProperty.Type.MODULE_EXCLUDE_GLOB, null));
		}

		return excludesJobProperties;
	}

	@Override
	protected List<JobProperty> getRelevantIncludesJobProperties() {
		if (includeStableTestSuite && isStableTestSuiteBatch()) {
			return super.getRelevantIncludesJobProperties();
		}

		Set<File> modifiedModuleDirsSet = new HashSet<>();
		List<File> modifiedNonposhiModulesList = new ArrayList<>();
		List<File> modifiedPoshiModulesList = new ArrayList<>();

		try {
			modifiedModuleDirsSet.addAll(
				portalGitWorkingDirectory.getModifiedModuleDirsList());
			modifiedNonposhiModulesList =
				portalGitWorkingDirectory.getModifiedNonposhiModules();
			modifiedPoshiModulesList =
				portalGitWorkingDirectory.getModifiedPoshiModules();
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

		if (testRelevantChanges) {
			modifiedModuleDirsSet.addAll(
				getRequiredModuleDirs(
					Lists.newArrayList(modifiedModuleDirsSet)));
		}

		Set<JobProperty> includesJobProperties = new HashSet<>();

		String moduleName = null;

		Matcher matcher = _singleModuleBatchNamePattern.matcher(batchName);

		if (matcher.find()) {
			moduleName = matcher.group("moduleName");
		}

		for (File modifiedModuleDir : modifiedModuleDirsSet) {
			if (modifiedPoshiModulesList.contains(modifiedModuleDir) &&
				!modifiedNonposhiModulesList.contains(modifiedModuleDir)) {

				continue;
			}

			String modifiedModuleAbsolutePath =
				JenkinsResultsParserUtil.getCanonicalPath(modifiedModuleDir);

			String modifiedModuleRelativePath =
				modifiedModuleAbsolutePath.substring(
					modifiedModuleAbsolutePath.indexOf("modules/"));

			if ((moduleName != null) &&
				!modifiedModuleRelativePath.contains("/" + moduleName)) {

				continue;
			}

			includesJobProperties.add(
				getJobProperty(
					"test.batch.class.names.includes.modules",
					modifiedModuleDir, JobProperty.Type.INCLUDE_GLOB));

			includesJobProperties.add(
				getJobProperty(
					"modules.includes.required.test.batch.class.names.includes",
					modifiedModuleDir, JobProperty.Type.MODULE_INCLUDE_GLOB));
		}

		for (File modifiedFile :
				portalGitWorkingDirectory.getModifiedFilesList()) {

			if (JenkinsResultsParserUtil.isPoshiFile(modifiedFile)) {
				continue;
			}

			String modifiedFileCanonicalPath =
				JenkinsResultsParserUtil.getCanonicalPath(modifiedFile);

			if (modifiedFileCanonicalPath.contains("modules")) {
				includesJobProperties.addAll(
					_getJobProperties(
						modifiedFile, "test.batch.class.names.includes.modules",
						JobProperty.Type.MODULE_INCLUDE_GLOB, null));
			}

			includesJobProperties.addAll(
				_getJobProperties(
					modifiedFile,
					"modules.includes.required.test.batch.class.names.includes",
					JobProperty.Type.MODULE_INCLUDE_GLOB, null));
		}

		return new ArrayList<>(includesJobProperties);
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

	private List<JobProperty> _getJobProperties(
		File file, String basePropertyName, JobProperty.Type jobType,
		Set<File> traversedPropertyFileSet) {

		List<JobProperty> jobPropertiesList = new ArrayList<>();

		File modulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		if ((file == null) || file.equals(modulesBaseDir) ||
			JenkinsResultsParserUtil.isPoshiFile(file)) {

			return jobPropertiesList;
		}

		if (!file.isDirectory()) {
			file = file.getParentFile();
		}

		File testPropertiesFile = new File(file, "test.properties");

		if (traversedPropertyFileSet == null) {
			traversedPropertyFileSet = new HashSet<>();
		}

		if (testPropertiesFile.exists() &&
			!traversedPropertyFileSet.contains(testPropertiesFile)) {

			JobProperty jobProperty = getJobProperty(
				basePropertyName, file, jobType);

			String jobPropertyValue = jobProperty.getValue();

			if (!JenkinsResultsParserUtil.isNullOrEmpty(jobPropertyValue) &&
				!jobPropertiesList.contains(jobProperty)) {

				jobPropertiesList.add(jobProperty);
			}

			traversedPropertyFileSet.add(testPropertiesFile);
		}

		JobProperty ignoreParentsJobProperty = getJobProperty(
			"ignoreParents[" + getTestSuiteName() + "]", file,
			JobProperty.Type.MODULE_TEST_DIR);

		boolean ignoreParents = Boolean.valueOf(
			ignoreParentsJobProperty.getValue());

		if (ignoreParents) {
			return jobPropertiesList;
		}

		jobPropertiesList.addAll(
			_getJobProperties(
				file.getParentFile(), basePropertyName, jobType,
				traversedPropertyFileSet));

		return jobPropertiesList;
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