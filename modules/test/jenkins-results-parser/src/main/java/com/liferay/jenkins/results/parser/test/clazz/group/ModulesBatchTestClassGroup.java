/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.job.property.JobProperty;

import java.io.File;
import java.io.IOException;

import java.nio.file.PathMatcher;

import java.util.ArrayList;
import java.util.Collections;
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

	protected List<PathMatcher> getIncludesPathMatchers() {
		if (!isRootCauseAnalysis()) {
			return getPathMatchers(getIncludesJobProperties());
		}

		String portalBatchTestSelector = System.getenv(
			"PORTAL_BATCH_TEST_SELECTOR");

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalBatchTestSelector)) {
			portalBatchTestSelector = getBuildStartProperty(
				"PORTAL_BATCH_TEST_SELECTOR");
		}

		List<String> includeGlobs = new ArrayList<>();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(portalBatchTestSelector)) {
			Collections.addAll(
				includeGlobs,
				JenkinsResultsParserUtil.getGlobsFromProperty(
					portalBatchTestSelector));
		}

		File portalModulesBaseDir = new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");

		return JenkinsResultsParserUtil.toPathMatchers(
			JenkinsResultsParserUtil.combine(
				JenkinsResultsParserUtil.getCanonicalPath(portalModulesBaseDir),
				File.separator),
			includeGlobs.toArray(new String[0]));
	}

	protected abstract void setTestClasses() throws IOException;

	protected Set<File> moduleDirsList = new HashSet<>();

}