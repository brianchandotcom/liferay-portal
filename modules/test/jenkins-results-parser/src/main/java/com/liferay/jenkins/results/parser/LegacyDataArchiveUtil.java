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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public class LegacyDataArchiveUtil {

	public LegacyDataArchiveUtil(
		File generatedArchiveDirectory,
		GitWorkingDirectory legacyGitWorkingDirectory) {

		_generatedArchiveDirectory = generatedArchiveDirectory;
		_legacyGitWorkingDirectory = legacyGitWorkingDirectory;

		GitWorkingDirectory.Branch localUpstreamBranch =
			_legacyGitWorkingDirectory.getBranch(
				_legacyGitWorkingDirectory.getUpstreamBranchName(), null);

		_legacyGitWorkingDirectory.checkoutBranch(localUpstreamBranch);

		_legacyGitWorkingDirectory.reset("--hard");

		_legacyGitWorkingDirectory.clean();

		_buildProperties = _getBuildProperties();

		_portalVersions = _getPortalVersions();

		_legacyDataArchivePortalVersions =
			_getLegacyDataArchivePortalVersions();
	}

	public GitWorkingDirectory.Branch createDataArchiveBranch()
		throws IOException {

		String dataArchiveBranchName = JenkinsResultsParserUtil.combine(
			"data-archive-", String.valueOf(System.currentTimeMillis()));

		_localDataArchiveBranch = _legacyGitWorkingDirectory.getBranch(
			dataArchiveBranchName, null);

		if (_localDataArchiveBranch != null) {
			_legacyGitWorkingDirectory.deleteBranch(
				_legacyGitWorkingDirectory.getBranch(
					dataArchiveBranchName, null));
		}

		_localDataArchiveBranch = _legacyGitWorkingDirectory.createLocalBranch(
			dataArchiveBranchName);

		_legacyGitWorkingDirectory.checkoutBranch(_localDataArchiveBranch);

		for (LegacyDataArchivePortalVersion legacyDataArchivePortalVersion :
				_legacyDataArchivePortalVersions) {

			List<LegacyDataArchiveGroup> legacyDataArchiveGroups =
				legacyDataArchivePortalVersion.getLegacyDataArchiveGroups();

			for (LegacyDataArchiveGroup legacyDataArchiveGroup :
					legacyDataArchiveGroups) {

				legacyDataArchiveGroup.commitLegacyDataArchives();
			}
		}

		_commitReadmeFile();

		GitWorkingDirectory.Remote upstreamRemote =
			_legacyGitWorkingDirectory.getRemote("upstream");

		if (!_legacyGitWorkingDirectory.pushToRemote(
				true, _localDataArchiveBranch, dataArchiveBranchName,
				upstreamRemote)) {

			throw new RuntimeException(
				"Unable to push data archive branch to upstream");
		}

		return _localDataArchiveBranch;
	}

	public Properties getBuildProperties() {
		return _buildProperties;
	}

	public GitWorkingDirectory.Branch getDataArchiveBranch() {
		return _localDataArchiveBranch;
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

	private void _commitReadmeFile() {
		Element rootElement = Dom4JUtil.getNewElement("div");

		Dom4JUtil.getNewElement("h1", rootElement, "Legacy Database Archives");

		for (LegacyDataArchivePortalVersion legacyDataArchivePortalVersion :
				_legacyDataArchivePortalVersions) {

			Dom4JUtil.getNewElement(
				"h2", rootElement,
				legacyDataArchivePortalVersion.getPortalVersion());

			Element testCommitElement = Dom4JUtil.getNewElement(
				"p", rootElement);

			Dom4JUtil.getNewElement(
				"span", testCommitElement, "Last Commit in ");

			File testDirectory =
				legacyDataArchivePortalVersion.getPortalVersionTestDirectory();

			Dom4JUtil.getNewAnchorElement(
				_legacyGitWorkingDirectory.getGitHubFileURL(
					"master", _legacyGitWorkingDirectory.getRemote("upstream"),
					testDirectory, false),
				testCommitElement, "Test");

			Dom4JUtil.getNewElement("span", testCommitElement, " Folder:");

			Commit testCommit =
				legacyDataArchivePortalVersion.getLatestTestCommit();

			Dom4JUtil.getNewAnchorElement(
				testCommit.getGitHubCommitURL(), testCommitElement,
				testCommit.getAbbreviatedSHA());

			Dom4JUtil.getNewElement(
				"span", testCommitElement, testCommit.getMessage());

			List<LegacyDataArchiveGroup> legacyDataArchiveGroups =
				legacyDataArchivePortalVersion.getLegacyDataArchiveGroups();

			if (legacyDataArchivePortalVersion.hasUpdatedArchives()) {
				Dom4JUtil.getNewElement(
					"h4", rootElement, "Updated Data Archives:");

				for (LegacyDataArchiveGroup legacyDataArchiveGroup :
						legacyDataArchiveGroups) {

					if (!legacyDataArchiveGroup.hasUpdatedArchives()) {
						continue;
					}

					Element detailsElement = Dom4JUtil.getNewElement(
						"details", rootElement);

					Element summaryElement = Dom4JUtil.getNewElement(
						"summary", detailsElement);

					List<LegacyDataArchive> updatedLegacyDataArchives =
						new ArrayList<>();

					List<LegacyDataArchive> legacyDataArchives =
						legacyDataArchiveGroup.getLegacyDataArchives();

					for (LegacyDataArchive legacyDataArchive :
							legacyDataArchives) {

						if (legacyDataArchive.isUpdated()) {
							updatedLegacyDataArchives.add(legacyDataArchive);
						}
					}

					Dom4JUtil.getNewElement(
						"b", summaryElement,
						"(" + updatedLegacyDataArchives.size() + ")");

					Commit commit = legacyDataArchiveGroup.getCommit();

					Dom4JUtil.getNewAnchorElement(
						commit.getGitHubCommitURL(), summaryElement,
						commit.getAbbreviatedSHA());

					Dom4JUtil.getNewElement(
						"span", summaryElement, commit.getMessage());

					Element dataArchivesElement = Dom4JUtil.getNewElement(
						"ul", detailsElement);

					for (LegacyDataArchive updatedLegacyDataArchive :
							updatedLegacyDataArchives) {

						File legacyDataArchiveFile =
							updatedLegacyDataArchive.getLegacyDataArchiveFile();

						Element dataArchiveElement = Dom4JUtil.getNewElement(
							"li", dataArchivesElement);

						Dom4JUtil.getNewAnchorElement(
							_legacyGitWorkingDirectory.getGitHubFileURL(
								_localDataArchiveBranch.getName(),
								_legacyGitWorkingDirectory.getRemote(
									"upstream"),
								legacyDataArchiveFile, false),
							dataArchiveElement,
							JenkinsResultsParserUtil.getPathRelativeTo(
								legacyDataArchiveFile,
								_legacyGitWorkingDirectory.
									getWorkingDirectory()));
					}
				}
			}

			if (legacyDataArchivePortalVersion.hasStaleArchives()) {
				Dom4JUtil.getNewElement(
					"h4", rootElement, "Stale Data Archives:");

				Map<Commit, List<LegacyDataArchive>> staleDataArchivesMap =
					new HashMap<>();

				for (LegacyDataArchiveGroup legacyDataArchiveGroup :
						legacyDataArchiveGroups) {

					if (!legacyDataArchiveGroup.hasStaleArchives()) {
						continue;
					}

					for (LegacyDataArchive legacyDataArchive :
							legacyDataArchiveGroup.getLegacyDataArchives()) {

						if (legacyDataArchive.isStale()) {
							Commit commit = legacyDataArchive.getCommit();

							List<LegacyDataArchive> staleDataArchives =
								staleDataArchivesMap.get(commit);

							if (staleDataArchives == null) {
								staleDataArchives = new ArrayList<>();
							}

							staleDataArchives.add(legacyDataArchive);

							staleDataArchivesMap.put(commit, staleDataArchives);
						}
					}
				}

				for (Commit commit : staleDataArchivesMap.keySet()) {
					List<LegacyDataArchive> staleDataArchives =
						staleDataArchivesMap.get(commit);

					Element detailsElement = Dom4JUtil.getNewElement(
						"details", rootElement);

					Element summaryElement = Dom4JUtil.getNewElement(
						"summary", detailsElement);

					Dom4JUtil.getNewElement(
						"b", summaryElement,
						"(" + staleDataArchives.size() + ")");

					Dom4JUtil.getNewAnchorElement(
						commit.getGitHubCommitURL(), summaryElement,
						commit.getAbbreviatedSHA());

					Dom4JUtil.getNewElement(
						"span", summaryElement, commit.getMessage());

					Element dataArchivesElement = Dom4JUtil.getNewElement(
						"ul", detailsElement);

					for (LegacyDataArchive staleDataArchive :
							staleDataArchives) {

						File legacyDataArchiveFile =
							staleDataArchive.getLegacyDataArchiveFile();

						Element dataArchiveElement = Dom4JUtil.getNewElement(
							"li", dataArchivesElement);

						Dom4JUtil.getNewAnchorElement(
							_legacyGitWorkingDirectory.getGitHubFileURL(
								"master",
								_legacyGitWorkingDirectory.getRemote(
									"upstream"),
								legacyDataArchiveFile, false),
							dataArchiveElement,
							JenkinsResultsParserUtil.getPathRelativeTo(
								legacyDataArchiveFile,
								_legacyGitWorkingDirectory.
									getWorkingDirectory()));
					}
				}
			}

			if (legacyDataArchivePortalVersion.hasMissingArchives()) {
				Dom4JUtil.getNewElement(
					"h4", rootElement, "Missing Data Archives:");

				for (LegacyDataArchiveGroup legacyDataArchiveGroup :
						legacyDataArchiveGroups) {

					if (!legacyDataArchiveGroup.hasMissingArchives()) {
						continue;
					}

					List<LegacyDataArchive> missingDataArchives =
						new ArrayList<>();

					for (LegacyDataArchive legacyDataArchive :
							legacyDataArchiveGroup.getLegacyDataArchives()) {

						if (legacyDataArchive.isMissing()) {
							missingDataArchives.add(legacyDataArchive);
						}
					}

					Element detailsElement = Dom4JUtil.getNewElement(
						"details", rootElement);

					Element summaryElement = Dom4JUtil.getNewElement(
						"summary", detailsElement);

					Dom4JUtil.getNewElement(
						"b", summaryElement,
						"(" + missingDataArchives.size() + ")");

					Dom4JUtil.getNewElement(
						"span", summaryElement,
						legacyDataArchiveGroup.getDataArchiveType());

					Element dataArchivesElement = Dom4JUtil.getNewElement(
						"ul", detailsElement);

					for (LegacyDataArchive missingDataArchive :
							missingDataArchives) {

						File legacyDataArchiveFile =
							missingDataArchive.getLegacyDataArchiveFile();

						Dom4JUtil.getNewElement(
							"li", dataArchivesElement,
							JenkinsResultsParserUtil.getPathRelativeTo(
								legacyDataArchiveFile,
								_legacyGitWorkingDirectory.
									getWorkingDirectory()));
					}
				}
			}
		}

		try {
			File readmeFile = new File(
				_legacyGitWorkingDirectory.getWorkingDirectory(), "README.md");

			JenkinsResultsParserUtil.write(
				readmeFile, Dom4JUtil.format(rootElement, true));

			_legacyGitWorkingDirectory.stageFileInCurrentBranch(
				readmeFile.getCanonicalPath());

			_legacyGitWorkingDirectory.commitStagedFilesToCurrentBranch(
				JenkinsResultsParserUtil.combine(
					"archive:ignore Update README.md to show changes."));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
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
	private final File _generatedArchiveDirectory;
	private final List<LegacyDataArchivePortalVersion>
		_legacyDataArchivePortalVersions;
	private final GitWorkingDirectory _legacyGitWorkingDirectory;
	private GitWorkingDirectory.Branch _localDataArchiveBranch;
	private final List<String> _portalVersions;

}