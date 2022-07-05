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
import com.liferay.jenkins.results.parser.job.property.JobProperty;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;

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

		Set<File> traversedModulesProperties = new HashSet<>();

		for (File modifiedFile :
				portalGitWorkingDirectory.getModifiedFilesList()) {

			_concatProperties(
				modifiedFile, "",
				"modules.includes.required.test.batch.class.names.excludes",
				JobProperty.Type.MODULE_EXCLUDE_GLOB, excludesJobProperties,
				traversedModulesProperties);
		}

		return excludesJobProperties;
	}

	@Override
	protected List<JobProperty> getRelevantIncludesJobProperties() {
		if (includeStableTestSuite && isStableTestSuiteBatch()) {
			return super.getRelevantIncludesJobProperties();
		}

		List<File> modifiedFilesList =
			portalGitWorkingDirectory.getModifiedFilesList();

		try {
			modifiedFilesList.addAll(
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

		if (testRelevantChanges) {
			modifiedFilesList.addAll(
				getRequiredModuleDirs(Lists.newArrayList(modifiedFilesList)));
		}

		List<JobProperty> includesJobProperties = new ArrayList<>();

		Matcher matcher = _singleModuleBatchNamePattern.matcher(batchName);

		String moduleName = null;

		if (matcher.find()) {
			moduleName = matcher.group("moduleName");
		}

		Set<File> traversedTestBatchProperties = new HashSet<>();
		Set<File> traversedModulesProperties = new HashSet<>();

		for (File modifiedFile : modifiedFilesList) {
			String modifiedModuleAbsolutePath =
				JenkinsResultsParserUtil.getCanonicalPath(modifiedFile);

			if ((moduleName != null) &&
				!modifiedModuleAbsolutePath.contains("/" + moduleName)) {

				continue;
			}

			_concatProperties(
				modifiedFile, "", "test.batch.class.names.includes.modules",
				JobProperty.Type.MODULE_INCLUDE_GLOB, includesJobProperties,
				traversedTestBatchProperties);

			_concatProperties(
				modifiedFile, "",
				"modules.includes.required.test.batch.class.names.includes",
				JobProperty.Type.MODULE_INCLUDE_GLOB, includesJobProperties,
				traversedModulesProperties);
		}

		return includesJobProperties;
	}

	private String _concatProperties(
		File file, String concatedProperties, String basePropertyName,
		JobProperty.Type jobType, List<JobProperty> jobPropertiesList,
		Set<File> traversedPropertiesList) {

		if (file == null) {
			return "";
		}

		File canonicalFile = JenkinsResultsParserUtil.getCanonicalFile(file);

		File parentFile = canonicalFile.getParentFile();

		if ((parentFile == null) || !parentFile.exists()) {
			return "";
		}

		File modulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		Path modulesBaseDirPath = modulesBaseDir.toPath();

		Path parentFilePath = parentFile.toPath();

		if (parentFilePath.equals(modulesBaseDirPath)) {
			return concatedProperties;
		}

		if (!canonicalFile.isDirectory()) {
			return _concatProperties(
				parentFile, concatedProperties, basePropertyName, jobType,
				jobPropertiesList, traversedPropertiesList);
		}

		File testPropertiesFile = new File(canonicalFile, "test.properties");

		if (!testPropertiesFile.exists()) {
			return _concatProperties(
				parentFile, concatedProperties, basePropertyName, jobType,
				jobPropertiesList, traversedPropertiesList);
		}

		if (traversedPropertiesList.contains(testPropertiesFile)) {
			return concatedProperties;
		}

		traversedPropertiesList.add(testPropertiesFile);

		JobProperty jobProperty = getJobProperty(
			basePropertyName, canonicalFile, jobType);

		if (jobPropertiesList.contains(jobProperty)) {
			return concatedProperties;
		}

		String testBatchPropertyQuery = jobProperty.getValue();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testBatchPropertyQuery) &&
			!testBatchPropertyQuery.equals("false") &&
			!concatedProperties.contains(testBatchPropertyQuery)) {

			jobPropertiesList.add(jobProperty);

			recordJobProperty(jobProperty);

			if (!concatedProperties.isEmpty()) {
				concatedProperties += JenkinsResultsParserUtil.combine(
					",\\", testBatchPropertyQuery, ",\\");
			}
			else {
				concatedProperties += testBatchPropertyQuery;
			}
		}

		Properties testProperties = JenkinsResultsParserUtil.getProperties(
			testPropertiesFile);

		boolean ignoreParents = Boolean.valueOf(
			JenkinsResultsParserUtil.getProperty(
				testProperties, "ignoreParents", false, getTestSuiteName()));

		if (ignoreParents) {
			return concatedProperties;
		}

		if (!parentFilePath.equals(modulesBaseDirPath)) {
			return _concatProperties(
				parentFile, concatedProperties, basePropertyName, jobType,
				jobPropertiesList, traversedPropertiesList);
		}

		return concatedProperties;
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