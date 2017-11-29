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
import java.io.IOException;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class LegacyDataArchive {

	public LegacyDataArchive(
		LegacyDataArchiveGroup legacyDataArchiveGroup, String databaseName) {

		_legacyDataArchiveGroup = legacyDataArchiveGroup;
		_databaseName = databaseName;

		_legacyDataArchivePortalVersion =
			_legacyDataArchiveGroup.getLegacyDataArchivePortalVersion();

		_legacyDataArchiveUtil =
			_legacyDataArchivePortalVersion.getLegacyDataArchiveUtil();

		_legacyGitWorkingDirectory =
			_legacyDataArchiveUtil.getLegacyGitWorkingDirectory();

		String dataArchiveType = _legacyDataArchiveGroup.getDataArchiveType();
		String portalVersion =
			_legacyDataArchivePortalVersion.getPortalVersion();
		File legacyDataWorkingDirectory =
			_legacyGitWorkingDirectory.getWorkingDirectory();

		_legacyDataArchiveFile = new File(
			JenkinsResultsParserUtil.combine(
				legacyDataWorkingDirectory.toString(), "/", portalVersion,
				"/data-archive/", dataArchiveType, "-", _databaseName, ".zip"));

		_commit = _getCommit();
	}

	public boolean isMissing() {
		if (_commit == null) {
			return true;
		}

		return false;
	}

	public boolean isStale() {
		if (!isMissing() && !isUpdated()) {
			return true;
		}

		return false;
	}

	public boolean isUpdated() {
		if (_commit == null) {
			return false;
		}

		List<Commit> latestLegacyDataArchiveCommits =
			_legacyDataArchiveUtil.getLatestLegacyDataArchiveCommits();

		for (Commit latestLegacyDataArchiveCommit :
				latestLegacyDataArchiveCommits) {

			if (_commit.equals(latestLegacyDataArchiveCommit)) {
				return true;
			}
		}

		return false;
	}

	public void stageLegacyDataArchive() throws IOException {
		String dataArchiveType = _legacyDataArchiveGroup.getDataArchiveType();
		File generatedArchiveDirectory =
			_legacyDataArchiveUtil.getGeneratedArchiveDirectory();
		String portalVersion =
			_legacyDataArchivePortalVersion.getPortalVersion();

		File generatedLegacyDataArchiveFile = new File(
			JenkinsResultsParserUtil.combine(
				generatedArchiveDirectory.toString(), "/", portalVersion, "/",
				dataArchiveType, "-", _databaseName, ".zip"));

		if (generatedLegacyDataArchiveFile.exists()) {
			JenkinsResultsParserUtil.copy(
				generatedLegacyDataArchiveFile, _legacyDataArchiveFile);

			_legacyGitWorkingDirectory.stageFileInCurrentBranch(
				_legacyDataArchiveFile.getCanonicalPath());
		}
	}

	public void updateCommit() {
		Commit commit = _getCommit();

		if (commit == null) {
			return;
		}

		if ((_commit != null) && _commit.equals(commit)) {
			return;
		}

		_commit = commit;
	}

	private Commit _getCommit() {
		if (_legacyDataArchiveFile.exists()) {
			String gitLog = _legacyGitWorkingDirectory.log(
				1, _legacyDataArchiveFile);

			return CommitFactory.newCommit(gitLog);
		}

		return null;
	}

	private Commit _commit;
	private final String _databaseName;
	private final File _legacyDataArchiveFile;
	private final LegacyDataArchiveGroup _legacyDataArchiveGroup;
	private final LegacyDataArchivePortalVersion
		_legacyDataArchivePortalVersion;
	private final LegacyDataArchiveUtil _legacyDataArchiveUtil;
	private final GitWorkingDirectory _legacyGitWorkingDirectory;

}