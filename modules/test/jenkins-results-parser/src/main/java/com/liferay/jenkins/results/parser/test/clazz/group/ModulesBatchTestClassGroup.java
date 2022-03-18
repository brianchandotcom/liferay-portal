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
import com.liferay.jenkins.results.parser.job.property.JobProperty;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Leslie Wong
 */
public abstract class ModulesBatchTestClassGroup extends BatchTestClassGroup {

	@Override
	public int getAxisCount() {
		if (!isStableTestSuiteBatch() && testRelevantIntegrationUnitOnly) {
			return 0;
		}

		return super.getAxisCount();
	}

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("exclude_globs", getGlobs(getExcludesJobProperties()));
		jsonObject.put("include_globs", getGlobs(getIncludesJobProperties()));
		jsonObject.put("modified_dirs_list", moduleDirsList);

		return jsonObject;
	}

	protected ModulesBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);

		JSONArray modifiedDirsJSONArray = jsonObject.optJSONArray(
			"modified_dirs_list");

		if ((modifiedDirsJSONArray == null) ||
			modifiedDirsJSONArray.isEmpty()) {

			return;
		}

		for (int i = 0; i < modifiedDirsJSONArray.length(); i++) {
			String modifiedDirPath = modifiedDirsJSONArray.getString(i);

			if (JenkinsResultsParserUtil.isNullOrEmpty(modifiedDirPath)) {
				continue;
			}

			moduleDirsList.add(new File(modifiedDirPath));
		}
	}

	protected ModulesBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		try {
			if (testRelevantChanges) {
				moduleDirsList.addAll(
					getRequiredModuleDirs(
						portalGitWorkingDirectory.getModifiedModuleDirsList(
							getPathMatchers(getExcludesJobProperties()),
							getPathMatchers(getIncludesJobProperties()))));
			}

			setTestClasses();

			setAxisTestClassGroups();

			setSegmentTestClassGroups();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected List<JobProperty> getExcludesJobProperties() {
		List<JobProperty> excludesJobProperties = new ArrayList<>();

		File modulesDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		String upstreamBranchName =
			portalGitWorkingDirectory.getUpstreamBranchName();

		if (upstreamBranchName.startsWith("ee-") ||
			upstreamBranchName.endsWith("-private")) {

			excludesJobProperties.add(
				getJobProperty(
					"modules.excludes.private", modulesDir,
					JobProperty.Type.EXCLUDE_GLOB));

			if (includeStableTestSuite && isStableTestSuiteBatch()) {
				excludesJobProperties.add(
					getJobProperty(
						"modules.excludes.private", NAME_STABLE_TEST_SUITE,
						modulesDir, JobProperty.Type.EXCLUDE_GLOB));
			}
		}
		else {
			excludesJobProperties.add(
				getJobProperty(
					"modules.excludes.public", modulesDir,
					JobProperty.Type.EXCLUDE_GLOB));

			if (includeStableTestSuite && isStableTestSuiteBatch()) {
				excludesJobProperties.add(
					getJobProperty(
						"modules.excludes.public", NAME_STABLE_TEST_SUITE,
						modulesDir, JobProperty.Type.EXCLUDE_GLOB));
			}
		}

		excludesJobProperties.add(
			getJobProperty(
				"modules.excludes", modulesDir, JobProperty.Type.EXCLUDE_GLOB));

		excludesJobProperties.add(
			getJobProperty(
				"modules.excludes." + portalTestClassJob.getBuildProfile(),
				modulesDir, JobProperty.Type.EXCLUDE_GLOB));

		recordJobProperties(excludesJobProperties);

		return excludesJobProperties;
	}

	protected List<JobProperty> getIncludesJobProperties() {
		List<JobProperty> includesJobProperties = new ArrayList<>();

		File modulesDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		String upstreamBranchName =
			portalGitWorkingDirectory.getUpstreamBranchName();

		if (upstreamBranchName.startsWith("ee-") ||
			upstreamBranchName.endsWith("-private")) {

			includesJobProperties.add(
				getJobProperty(
					"modules.includes.private", modulesDir,
					JobProperty.Type.INCLUDE_GLOB));

			if (includeStableTestSuite && isStableTestSuiteBatch()) {
				includesJobProperties.add(
					getJobProperty(
						"modules.includes.private", NAME_STABLE_TEST_SUITE,
						modulesDir, JobProperty.Type.INCLUDE_GLOB));
			}
		}
		else {
			includesJobProperties.add(
				getJobProperty(
					"modules.includes.public", modulesDir,
					JobProperty.Type.INCLUDE_GLOB));

			if (includeStableTestSuite && isStableTestSuiteBatch()) {
				includesJobProperties.add(
					getJobProperty(
						"modules.includes.public", NAME_STABLE_TEST_SUITE,
						modulesDir, JobProperty.Type.INCLUDE_GLOB));
			}
		}

		includesJobProperties.add(
			getJobProperty(
				"modules.includes", modulesDir, JobProperty.Type.INCLUDE_GLOB));

		includesJobProperties.add(
			getJobProperty(
				"modules.includes." + portalTestClassJob.getBuildProfile(),
				modulesDir, JobProperty.Type.INCLUDE_GLOB));

		recordJobProperties(includesJobProperties);

		return includesJobProperties;
	}

	protected abstract void setTestClasses() throws IOException;

	protected Set<File> moduleDirsList = new HashSet<>();

}