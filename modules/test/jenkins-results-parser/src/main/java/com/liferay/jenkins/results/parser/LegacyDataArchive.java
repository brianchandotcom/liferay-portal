/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class LegacyDataArchive {

	public String getDataArchiveType() {
		return _dataArchiveType;
	}

	public File getLegacyDataArchiveFile() {
		return _legacyDataArchiveFile;
	}

	public LegacyDataArchiveHelper getLegacyDataArchiveHelper() {
		return _legacyDataArchiveHelper;
	}

	public GitWorkingDirectory getLegacyGitWorkingDirectory() {
		return _legacyGitWorkingDirectory;
	}

	public LocalGitCommit getLocalGitCommit() {
		if (_legacyDataArchiveFile.exists()) {
			List<LocalGitCommit> localGitCommits =
				_legacyGitWorkingDirectory.log(1, _legacyDataArchiveFile);

			return localGitCommits.get(0);
		}

		return null;
	}

	public boolean isUpdated() {
		LocalGitCommit localGitCommit = getLocalGitCommit();

		if (localGitCommit == null) {
			return false;
		}

		LocalGitCommit latestTestLocalGitCommit =
			_legacyDataArchivePortalVersion.getLatestTestLocalGitCommit();

		String gitCommitMessage = localGitCommit.getMessage();

		if (gitCommitMessage.contains(
				latestTestLocalGitCommit.getAbbreviatedSHA())) {

			return true;
		}

		return false;
	}

	public void stageLegacyDataArchive() throws IOException {
		File generatedArchiveDirectory =
			_legacyDataArchiveHelper.getGeneratedArchiveDirectory();

		File generatedArchiveFile = new File(
			JenkinsResultsParserUtil.combine(
				generatedArchiveDirectory.toString(), "/",
				_legacyDataArchivePortalVersion.getPortalVersion(), "/",
				_legacyDataArchiveGroup.getDataArchiveType(), "-",
				_databaseName, ".zip"));

		if (generatedArchiveFile.exists()) {
			JenkinsResultsParserUtil.copy(
				generatedArchiveFile, _legacyDataArchiveFile);

			_legacyGitWorkingDirectory.stageFileInCurrentLocalGitBranch(
				JenkinsResultsParserUtil.getCanonicalPath(
					_legacyDataArchiveFile));
		}
	}

	protected LegacyDataArchive(
		LegacyDataArchiveGroup legacyDataArchiveGroup, String databaseName) {

		_legacyDataArchiveGroup = legacyDataArchiveGroup;
		_databaseName = databaseName;

		_legacyDataArchivePortalVersion =
			legacyDataArchiveGroup.getLegacyDataArchivePortalVersion();

		_legacyDataArchiveHelper =
			_legacyDataArchivePortalVersion.getLegacyDataArchiveHelper();

		_legacyGitWorkingDirectory =
			_legacyDataArchiveHelper.getLegacyGitWorkingDirectory();

		_dataArchiveType = legacyDataArchiveGroup.getDataArchiveType();

		File legacyDataWorkingDirectory =
			_legacyGitWorkingDirectory.getWorkingDirectory();

		_legacyDataArchiveFile = new File(
			JenkinsResultsParserUtil.combine(
				legacyDataWorkingDirectory.toString(), "/",
				_legacyDataArchivePortalVersion.getPortalVersion(),
				"/data-archive/", _dataArchiveType, "-", _databaseName,
				".zip"));
	}

	private final String _dataArchiveType;
	private final String _databaseName;
	private final File _legacyDataArchiveFile;
	private final LegacyDataArchiveGroup _legacyDataArchiveGroup;
	private final LegacyDataArchiveHelper _legacyDataArchiveHelper;
	private final LegacyDataArchivePortalVersion
		_legacyDataArchivePortalVersion;
	private final GitWorkingDirectory _legacyGitWorkingDirectory;

}