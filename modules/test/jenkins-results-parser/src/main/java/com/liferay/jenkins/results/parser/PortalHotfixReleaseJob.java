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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalHotfixReleaseJob extends BasePortalReleaseJob {

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		PortalHotfixRelease portalHotfixRelease = getPortalHotfixRelease();

		if (portalHotfixRelease != null) {
			jsonObject.put(
				"portal_hotfix_release", portalHotfixRelease.getJSONObject());
		}

		return jsonObject;
	}

	public List<File> getModifiedFiles() {
		List<File> modifiedFiles = new ArrayList<>();

		PortalHotfixRelease portalHotfixRelease = getPortalHotfixRelease();

		if (portalHotfixRelease == null) {
			return modifiedFiles;
		}

		Set<String> modifiedPackageNames =
			portalHotfixRelease.getModifiedPackageNames();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		List<File> bndFiles = JenkinsResultsParserUtil.findFiles(
			portalGitWorkingDirectory.getWorkingDirectory(), "bnd.bnd");

		for (File bndFile : bndFiles) {
			Properties properties = JenkinsResultsParserUtil.getProperties(
				bndFile);

			String bundleSymbolicName = properties.getProperty(
				"Bundle-SymbolicName");

			if ((bundleSymbolicName == null) ||
				bundleSymbolicName.startsWith("${")) {

				File parentFile = bndFile.getParentFile();

				bundleSymbolicName = parentFile.getName();
			}

			if (!modifiedPackageNames.contains(bundleSymbolicName)) {
				continue;
			}

			modifiedFiles.add(bndFile);
		}

		return modifiedFiles;
	}

	public PortalHotfixRelease getPortalHotfixRelease() {
		return _portalHotfixRelease;
	}

	protected PortalHotfixReleaseJob(
		BuildProfile buildProfile, String jobName,
		PortalGitWorkingDirectory portalGitWorkingDirectory,
		PortalHotfixRelease portalHotfixRelease, String testSuiteName,
		String upstreamBranchName) {

		super(
			buildProfile, jobName, portalGitWorkingDirectory, testSuiteName,
			upstreamBranchName);

		_portalHotfixRelease = portalHotfixRelease;
	}

	protected PortalHotfixReleaseJob(JSONObject jsonObject) {
		super(jsonObject);

		_portalHotfixRelease = new PortalHotfixRelease(
			jsonObject.getJSONObject("portal_hotfix_release"));
	}

	private final PortalHotfixRelease _portalHotfixRelease;

}