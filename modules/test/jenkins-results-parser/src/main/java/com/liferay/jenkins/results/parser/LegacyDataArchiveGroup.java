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

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public class LegacyDataArchiveGroup {

	public LegacyDataArchiveGroup(
		LegacyDataArchivePortalVersion legacyDataArchivePortalVersion,
		String dataArchiveType) {

		_legacyDataArchivePortalVersion = legacyDataArchivePortalVersion;
		_dataArchiveType = dataArchiveType;

		_legacyDataArchiveUtil =
			_legacyDataArchivePortalVersion.getLegacyDataArchiveUtil();

		_legacyGitWorkingDirectory =
			_legacyDataArchiveUtil.getLegacyGitWorkingDirectory();

		_legacyDataArchives = _getLegacyDataArchives();
	}

	public void commitLegacyDataArchives() throws IOException {
		for (LegacyDataArchive legacyDataArchive : _legacyDataArchives) {
			if (!legacyDataArchive.isUpdated()) {
				legacyDataArchive.stageLegacyDataArchive();
			}
		}

		String status = _legacyGitWorkingDirectory.status();

		if (!status.contains("nothing to commit") &&
			!status.contains("nothing added to commit")) {

			Commit latestTestCommit =
				_legacyDataArchivePortalVersion.getLatestTestCommit();
			String portalVersion =
				_legacyDataArchivePortalVersion.getPortalVersion();

			_legacyGitWorkingDirectory.commitStagedFilesToCurrentBranch(
				JenkinsResultsParserUtil.combine(
					"archive:ignore Update '", _dataArchiveType, "' for '",
					portalVersion, "' at ",
					latestTestCommit.getAbbreviatedSHA(), "."));
		}
	}

	public Commit getCommit() {
		return CommitFactory.newCommit(
			_legacyGitWorkingDirectory.log(1), _legacyGitWorkingDirectory);
	}

	public String getDataArchiveType() {
		return _dataArchiveType;
	}

	public LegacyDataArchivePortalVersion getLegacyDataArchivePortalVersion() {
		return _legacyDataArchivePortalVersion;
	}

	public List<LegacyDataArchive> getLegacyDataArchives() {
		return _legacyDataArchives;
	}

	public LegacyDataArchive.Status getStatus() {
		if (hasUpdatedArchives()) {
			return LegacyDataArchive.Status.UPDATED;
		}

		if (hasMissingArchives()) {
			return LegacyDataArchive.Status.MISSING;
		}

		if (hasStaleArchives()) {
			return LegacyDataArchive.Status.STALE;
		}

		return LegacyDataArchive.Status.UNCHANGED;
	}

	public boolean hasMissingArchives() {
		for (LegacyDataArchive legacyDataArchive : _legacyDataArchives) {
			if (legacyDataArchive.isMissing()) {
				return true;
			}
		}

		return false;
	}

	public boolean hasStaleArchives() {
		for (LegacyDataArchive legacyDataArchive : _legacyDataArchives) {
			if (legacyDataArchive.isStale()) {
				return true;
			}
		}

		return false;
	}

	public boolean hasUpdatedArchives() {
		for (LegacyDataArchive legacyDataArchive : _legacyDataArchives) {
			if (legacyDataArchive.isUpdated()) {
				return true;
			}
		}

		return false;
	}

	protected LegacyDataReadMeSummaryElement
		getLegacyDataReadMeSummaryElement() {

		LegacyDataArchive.Status status = getStatus();
		Element summaryContentElement = Dom4JUtil.getNewElement("div");
		Commit commit = getCommit();

		if (commit != null) {
			Dom4JUtil.getNewAnchorElement(
				commit.getGitHubCommitURL(), summaryContentElement,
				commit.getAbbreviatedSHA());

			Dom4JUtil.getNewElement(
				"span", summaryContentElement, commit.getMessage());
		}
		else {
			Dom4JUtil.getNewElement(
				"span", summaryContentElement, getDataArchiveType());
		}

		LegacyDataReadMeSummaryElement legacyDataReadMeSummaryElement =
			new LegacyDataReadMeSummaryElement();

		legacyDataReadMeSummaryElement.setSummaryContent(summaryContentElement);

		for (LegacyDataArchive legacyDataArchive : getLegacyDataArchives()) {
			if (legacyDataArchive.getStatus() == status) {
				File legacyDataArchiveFile =
					legacyDataArchive.getLegacyDataArchiveFile();

				GitWorkingDirectory.Branch dataArchiveBranch =
					_legacyDataArchiveUtil.getDataArchiveBranch();

				legacyDataReadMeSummaryElement.addLineItem(
					Dom4JUtil.getNewAnchorElement(
						_legacyGitWorkingDirectory.getGitHubFileURL(
							dataArchiveBranch.getName(),
							_legacyGitWorkingDirectory.getRemote("upstream"),
							legacyDataArchiveFile, false),
						JenkinsResultsParserUtil.getPathRelativeTo(
							legacyDataArchiveFile,
							_legacyGitWorkingDirectory.getWorkingDirectory())));
			}
		}

		return legacyDataReadMeSummaryElement;
	}

	private List<LegacyDataArchive> _getLegacyDataArchives() {
		List<LegacyDataArchive> legacyDataArchives = new ArrayList<>();

		List<String> databaseNames =
			_legacyDataArchivePortalVersion.getDatabaseNames();

		for (String databaseName : databaseNames) {
			legacyDataArchives.add(new LegacyDataArchive(this, databaseName));
		}

		return legacyDataArchives;
	}

	private final String _dataArchiveType;
	private final LegacyDataArchivePortalVersion
		_legacyDataArchivePortalVersion;
	private final List<LegacyDataArchive> _legacyDataArchives;
	private final LegacyDataArchiveUtil _legacyDataArchiveUtil;
	private final GitWorkingDirectory _legacyGitWorkingDirectory;

}