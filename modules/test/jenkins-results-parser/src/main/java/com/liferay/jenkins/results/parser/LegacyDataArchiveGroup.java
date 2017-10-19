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

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class LegacyDataArchiveGroup {

	public LegacyDataArchiveGroup(
		LegacyDataArchiveUtil legacyDataArchiveUtil,
		String legacyDataArchiveType) {

		_legacyDataArchiveUtil = legacyDataArchiveUtil;
		_legacyDataArchiveType = legacyDataArchiveType;
	}

	public void addLegacyDataArchive(LegacyDataArchive legacyDataArchive) {
		_legacyDataArchives.add(legacyDataArchive);
	}

	public void commitLegacyDataArchives() throws IOException {
		for (LegacyDataArchive legacyDataArchive : _legacyDataArchives) {
			if (!legacyDataArchive.isUpdated()) {
				legacyDataArchive.updateLegacyDataArchive();
			}
		}

		GitWorkingDirectory legacyDataGitWorkingDirectory =
			_legacyDataArchiveUtil.getLegacyDataGitWorkingDirectory();

		String status = legacyDataGitWorkingDirectory.status();

		if (!status.contains("nothing to commit, working directory clean") &&
			!status.contains(
				"nothing added to commit but untracked files present")) {

			ManualCommit latestManualCommit =
				_legacyDataArchiveUtil.getLatestManualCommit();

			legacyDataGitWorkingDirectory.commitStagedFilesToCurrentBranch(
				JenkinsResultsParserUtil.combine(
					"archive:ignore Update '", _legacyDataArchiveType, "' at ",
					latestManualCommit.getAbbreviatedSHA(), "."));
		}
	}

	public String getLegacyDataArchiveType() {
		return _legacyDataArchiveType;
	}

	public boolean isUpdated() {
		for (LegacyDataArchive legacyDataArchive : _legacyDataArchives) {
			if (!legacyDataArchive.isUpdated()) {
				return false;
			}
		}

		return true;
	}

	private List<LegacyDataArchive> _legacyDataArchives = new ArrayList<>();
	private final String _legacyDataArchiveType;
	private final LegacyDataArchiveUtil _legacyDataArchiveUtil;

}