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
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class LegacyDataArchiveUtil {

	public LegacyDataArchiveUtil(
		File generatedLegacyDataArchiveDirectory,
		GitWorkingDirectory legacyGitWorkingDirectory) {

		_generatedLegacyDataArchiveDirectory =
			generatedLegacyDataArchiveDirectory;
		_legacyGitWorkingDirectory = legacyGitWorkingDirectory;

		GitWorkingDirectory.Branch upstreamBranch =
			_legacyGitWorkingDirectory.getBranch(
				_legacyGitWorkingDirectory.getUpstreamBranchName(), null);

		_legacyGitWorkingDirectory.checkoutBranch(upstreamBranch);

		_legacyGitWorkingDirectory.reset("--hard");

		_legacyGitWorkingDirectory.clean();

		_buildProperties = _getBuildProperties();

		_latestLegacyDataArchiveCommits = _getLatestLegacyDataArchiveCommits();
		_latestManualCommit = _getLatestManualCommit();
		_portalVersions = _getPortalVersions();
	}

	public Properties getBuildProperties() {
		return _buildProperties;
	}

	public File getGeneratedLegacyDataArchiveDirectory() {
		return _generatedLegacyDataArchiveDirectory;
	}

	public List<Commit> getLatestLegacyDataArchiveCommits() {
		return _latestLegacyDataArchiveCommits;
	}

	public Commit getLatestManualCommit() {
		return _latestManualCommit;
	}

	public GitWorkingDirectory getLegacyGitWorkingDirectory() {
		return _legacyGitWorkingDirectory;
	}

	public File getLegacyWorkingDirectory() {
		return _legacyGitWorkingDirectory.getWorkingDirectory();
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

	private Set<String> _getDatabaseNames(
		Properties buildProperties, String portalVersion) {

		String legacyDataArchiveDatabaseNames = buildProperties.getProperty(
			"legacy.data.archive.database.names");

		String databaseNamesPortalVersionKey = JenkinsResultsParserUtil.combine(
			"legacy.data.archive.database.names[", portalVersion, "]");

		if (buildProperties.containsKey(databaseNamesPortalVersionKey)) {
			legacyDataArchiveDatabaseNames = buildProperties.getProperty(
				databaseNamesPortalVersionKey);
		}

		return new HashSet<>(
			Arrays.asList(legacyDataArchiveDatabaseNames.split(",")));
	}

	private List<Commit> _getLatestLegacyDataArchiveCommits() {
		List<Commit> latestLegacyDataArchiveCommits = new ArrayList<>();

		String gitLog = _legacyGitWorkingDirectory.log(50);

		String[] gitLogEntities = gitLog.split("\n");

		for (String gitLogEntity : gitLogEntities) {
			Commit commit = CommitFactory.newCommit(gitLogEntity);

			if (commit.getType() == Commit.Type.LEGACY_ARCHIVE) {
				latestLegacyDataArchiveCommits.add(commit);

				continue;
			}

			break;
		}

		return latestLegacyDataArchiveCommits;
	}

	private Commit _getLatestManualCommit() {
		String gitLog = _legacyGitWorkingDirectory.log(50);

		String[] gitLogEntities = gitLog.split("\n");

		for (String gitLogEntity : gitLogEntities) {
			Commit commit = CommitFactory.newCommit(gitLogEntity);

			if (commit.getType() != Commit.Type.MANUAL) {
				continue;
			}

			return commit;
		}

		return null;
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
	private final File _generatedLegacyDataArchiveDirectory;
	private final List<Commit> _latestLegacyDataArchiveCommits;
	private final Commit _latestManualCommit;
	private final GitWorkingDirectory _legacyGitWorkingDirectory;
	private final List<String> _portalVersions;

}