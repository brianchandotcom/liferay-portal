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
		LegacyDataArchiveUtil legacyDataArchiveUtil,
		String legacyDataArchiveType, String databaseName,
		String portalVersion) {

		_legacyDataArchiveBranch = legacyDataArchiveUtil;
		_legacyDataArchiveType = legacyDataArchiveType;
		_databaseName = databaseName;
		_portalVersion = portalVersion;

		File legacyDataWorkingDirectory =
			legacyDataArchiveUtil.getLegacyDataWorkingDirectory();

		_committedLegacyDataArchiveFile = new File(
			JenkinsResultsParserUtil.combine(
				legacyDataWorkingDirectory.toString(), "/", _portalVersion,
				"/data-archive/", _legacyDataArchiveType, "-", _databaseName,
				".zip"));

		File generatedLegacyDataArchiveDirectory =
			legacyDataArchiveUtil.getGeneratedLegacyDataArchiveDirectory();

		_generatedLegacyDataArchiveFile = new File(
			JenkinsResultsParserUtil.combine(
				generatedLegacyDataArchiveDirectory.toString(), "/",
				_portalVersion, "/", _legacyDataArchiveType, "-", _databaseName,
				".zip"));

		_commit = _getCommit();
	}

	public String getLegacyDataArchiveType() {
		return _legacyDataArchiveType;
	}

	public boolean isUpdated() {
		if (_commit == null) {
			return false;
		}

		List<LegacyDataArchiveCommit> latestLegacyDataArchiveCommits =
			_legacyDataArchiveBranch.getLatestLegacyDataArchiveCommits();

		for (LegacyDataArchiveCommit latestLegacyDataArchiveCommit :
				latestLegacyDataArchiveCommits) {

			if (_commit.equals(latestLegacyDataArchiveCommit)) {
				return true;
			}
		}

		return false;
	}

	public void updateLegacyDataArchive() throws IOException {
		if (_generatedLegacyDataArchiveFile.exists()) {
			JenkinsResultsParserUtil.copy(
				_generatedLegacyDataArchiveFile,
				_committedLegacyDataArchiveFile);

			String committedLegacyDataArchiveFilePath =
				_committedLegacyDataArchiveFile.getCanonicalPath();
			File legacyDataWorkingDirectory =
				_legacyDataArchiveBranch.getLegacyDataWorkingDirectory();

			committedLegacyDataArchiveFilePath =
				committedLegacyDataArchiveFilePath.replaceAll(
					legacyDataWorkingDirectory + "/", "");

			GitWorkingDirectory legacyDataGitWorkingDirectory =
				_legacyDataArchiveBranch.getLegacyDataGitWorkingDirectory();

			legacyDataGitWorkingDirectory.stageFileInCurrentBranch(
				committedLegacyDataArchiveFilePath);
		}
	}

	private Commit _getCommit() {
		if (_committedLegacyDataArchiveFile.exists()) {
			GitWorkingDirectory legacyDataGitWorkingDirectory =
				_legacyDataArchiveBranch.getLegacyDataGitWorkingDirectory();

			String gitLog = legacyDataGitWorkingDirectory.log(
				1, _committedLegacyDataArchiveFile);

			return CommitFactory.newCommit(gitLog);
		}

		return null;
	}

	private Commit _commit;
	private final File _committedLegacyDataArchiveFile;
	private final String _databaseName;
	private final File _generatedLegacyDataArchiveFile;
	private final LegacyDataArchiveUtil _legacyDataArchiveBranch;
	private final String _legacyDataArchiveType;
	private final String _portalVersion;

}