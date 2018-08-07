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
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class LegacyDataArchiveUtil {

	public LegacyDataArchiveUtil(
		File generatedArchiveDirectory,
		GitWorkingDirectory legacyGitWorkingDirectory) {

		_generatedArchiveDirectory = generatedArchiveDirectory;
		_legacyGitWorkingDirectory = legacyGitWorkingDirectory;

		LocalGitBranch upstreamLocalGitBranch =
			_legacyGitWorkingDirectory.getLocalGitBranch(
				_legacyGitWorkingDirectory.getUpstreamBranchName());

		_legacyGitWorkingDirectory.checkoutLocalGitBranch(
			upstreamLocalGitBranch);

		_legacyGitWorkingDirectory.reset("--hard");

		_legacyGitWorkingDirectory.clean();

		_buildProperties = _getBuildProperties();

		_portalVersions = _getPortalVersions();

		_legacyDataArchivePortalVersions =
			_getLegacyDataArchivePortalVersions();
	}

	public LocalGitBranch createDataArchiveLocalGitBranch() throws IOException {
		String dataArchiveBranchName = JenkinsResultsParserUtil.combine(
			"data-archive-", String.valueOf(System.currentTimeMillis()));

		_dataArchiveLocalGitBranch =
			_legacyGitWorkingDirectory.getLocalGitBranch(dataArchiveBranchName);

		if (_dataArchiveLocalGitBranch != null) {
			_legacyGitWorkingDirectory.deleteLocalGitBranch(
				_dataArchiveLocalGitBranch);
		}

		_dataArchiveLocalGitBranch =
			_legacyGitWorkingDirectory.createLocalGitBranch(
				dataArchiveBranchName);

		_legacyGitWorkingDirectory.checkoutLocalGitBranch(
			_dataArchiveLocalGitBranch);

		for (LegacyDataArchivePortalVersion legacyDataArchivePortalVersion :
				_legacyDataArchivePortalVersions) {

			List<LegacyDataArchiveGroup> legacyDataArchiveGroups =
				legacyDataArchivePortalVersion.getLegacyDataArchiveGroups();

			for (LegacyDataArchiveGroup legacyDataArchiveGroup :
					legacyDataArchiveGroups) {

				legacyDataArchiveGroup.commitLegacyDataArchives();
			}
		}

		RemoteGitBranch remoteGitBranch =
			_legacyGitWorkingDirectory.pushToRemoteRepository(
				true, _dataArchiveLocalGitBranch, dataArchiveBranchName,
				_legacyGitWorkingDirectory.getUpstreamGitRemote());

		if (remoteGitBranch == null) {
			throw new RuntimeException(
				"Unable to push data archive branch to upstream");
		}

		return _dataArchiveLocalGitBranch;
	}

	public Properties getBuildProperties() {
		return _buildProperties;
	}

	public LocalGitBranch getDataArchiveBranch() {
		return _dataArchiveLocalGitBranch;
	}

	public File getGeneratedArchiveDirectory() {
		return _generatedArchiveDirectory;
	}

	public GitWorkingDirectory getLegacyGitWorkingDirectory() {
		return _legacyGitWorkingDirectory;
	}

	public List<String> getPortalVersions() {
		return _portalVersions;
	}

	private Properties _getBuildProperties() {
		Properties buildProperties = new Properties();

		File legacyDataWorkingDirectory =
			_legacyGitWorkingDirectory.getWorkingDirectory();

		File buildPropertiesFile = new File(
			legacyDataWorkingDirectory, "build.properties");

		try (FileInputStream fileInputStream = new FileInputStream(
				buildPropertiesFile)) {

			buildProperties.load(fileInputStream);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to load ", buildPropertiesFile.getPath()),
				ioe);
		}

		return buildProperties;
	}

	private List<LegacyDataArchivePortalVersion>
		_getLegacyDataArchivePortalVersions() {

		List<LegacyDataArchivePortalVersion> legacyDataArchivePortalVersions =
			new ArrayList<>();

		for (String portalVersion : _portalVersions) {
			legacyDataArchivePortalVersions.add(
				new LegacyDataArchivePortalVersion(this, portalVersion));
		}

		return legacyDataArchivePortalVersions;
	}

	private List<String> _getPortalVersions() {
		String legacyDataArchivePortalVersionsString =
			_buildProperties.getProperty("legacy.data.archive.portal.versions");

		List<String> portalVersions = Arrays.asList(
			legacyDataArchivePortalVersionsString.split(","));

		Collections.sort(portalVersions);

		return portalVersions;
	}

	private final Properties _buildProperties;
	private LocalGitBranch _dataArchiveLocalGitBranch;
	private final File _generatedArchiveDirectory;
	private final List<LegacyDataArchivePortalVersion>
		_legacyDataArchivePortalVersions;
	private final GitWorkingDirectory _legacyGitWorkingDirectory;
	private final List<String> _portalVersions;

}