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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public class LegacyDataArchiveUtil {

	public LegacyDataArchiveUtil(
		File generatedLegacyDataArchiveDirectory,
		GitWorkingDirectory legacyDataGitWorkingDirectory) {

		_generatedLegacyDataArchiveDirectory =
			generatedLegacyDataArchiveDirectory;
		_legacyDataGitWorkingDirectory = legacyDataGitWorkingDirectory;

		GitWorkingDirectory.Branch upstreamBranch =
			_legacyDataGitWorkingDirectory.getBranch(
				_legacyDataGitWorkingDirectory.getUpstreamBranchName(), null);

		_legacyDataGitWorkingDirectory.checkoutBranch(upstreamBranch);

		_legacyDataGitWorkingDirectory.reset("--hard");

		_legacyDataGitWorkingDirectory.clean();

		_legacyDataArchives = _getLegacyDataArchives(_getBuildProperties());
		_latestLegacyDataArchiveCommits = _getLatestLegacyDataArchiveCommits();
		_latestManualCommit = _getLatestManualCommit();

		_legacyDataArchiveGroupMap = _getLegacyDataArchiveGroupMap(
			_legacyDataArchives);
	}

	public GitWorkingDirectory.Branch createTemporaryBranch()
		throws IOException {

		GitWorkingDirectory.Branch upstreamBranch =
			_legacyDataGitWorkingDirectory.getBranch(
				_legacyDataGitWorkingDirectory.getUpstreamBranchName(), null);

		String temporaryBranchName =
			upstreamBranch.getName() + "-temp-" + System.currentTimeMillis();

		GitWorkingDirectory.Branch temporaryBranch =
			_legacyDataGitWorkingDirectory.getBranch(temporaryBranchName, null);

		if (temporaryBranch != null) {
			_legacyDataGitWorkingDirectory.deleteBranch(
				_legacyDataGitWorkingDirectory.getBranch(
					temporaryBranchName, null));
		}

		temporaryBranch = _legacyDataGitWorkingDirectory.createLocalBranch(
			temporaryBranchName);

		_legacyDataGitWorkingDirectory.checkoutBranch(temporaryBranch);

		List<LegacyDataArchiveGroup> legacyDataArchiveGroupList =
			new ArrayList<>(_legacyDataArchiveGroupMap.values());

		for (LegacyDataArchiveGroup legacyDataArchiveGroup :
				legacyDataArchiveGroupList) {

			if (!legacyDataArchiveGroup.isUpdated()) {
				legacyDataArchiveGroup.commitLegacyDataArchives();
			}
		}

		return temporaryBranch;
	}

	public File getGeneratedLegacyDataArchiveDirectory() {
		return _generatedLegacyDataArchiveDirectory;
	}

	public List<LegacyDataArchiveCommit> getLatestLegacyDataArchiveCommits() {
		return _latestLegacyDataArchiveCommits;
	}

	public ManualCommit getLatestManualCommit() {
		return _latestManualCommit;
	}

	public GitWorkingDirectory getLegacyDataGitWorkingDirectory() {
		return _legacyDataGitWorkingDirectory;
	}

	public File getLegacyDataWorkingDirectory() {
		return _legacyDataGitWorkingDirectory.getWorkingDirectory();
	}

	private Properties _getBuildProperties() {
		Properties buildProperties = new Properties();

		File legacyDataWorkingDirectory =
			_legacyDataGitWorkingDirectory.getWorkingDirectory();

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

	private List<LegacyDataArchiveCommit> _getLatestLegacyDataArchiveCommits() {
		List<LegacyDataArchiveCommit> latestLegacyDataArchiveCommits =
			new ArrayList<>();

		String gitLog = _legacyDataGitWorkingDirectory.log(50);

		String[] gitLogEntities = gitLog.split("\n");

		for (String gitLogEntity : gitLogEntities) {
			Commit commit = CommitFactory.newCommit(gitLogEntity);

			if (commit instanceof LegacyDataArchiveCommit) {
				latestLegacyDataArchiveCommits.add(
					(LegacyDataArchiveCommit)commit);

				continue;
			}

			break;
		}

		return latestLegacyDataArchiveCommits;
	}

	private ManualCommit _getLatestManualCommit() {
		String gitLog = _legacyDataGitWorkingDirectory.log(50);

		String[] gitLogEntities = gitLog.split("\n");

		for (String gitLogEntity : gitLogEntities) {
			Commit commit = CommitFactory.newCommit(gitLogEntity);

			if (!(commit instanceof ManualCommit)) {
				continue;
			}

			return (ManualCommit)commit;
		}

		return null;
	}

	private Map<String, LegacyDataArchiveGroup> _getLegacyDataArchiveGroupMap(
		List<LegacyDataArchive> legacyDataArchives) {

		Map<String, LegacyDataArchiveGroup> legacyDataArchiveGroupMap =
			new HashMap<>();

		for (LegacyDataArchive legacyDataArchive : legacyDataArchives) {
			String legacyDataArchiveType =
				legacyDataArchive.getLegacyDataArchiveType();

			LegacyDataArchiveGroup legacyDataArchiveGroup =
				legacyDataArchiveGroupMap.get(legacyDataArchiveType);

			if (legacyDataArchiveGroup == null) {
				legacyDataArchiveGroup = new LegacyDataArchiveGroup(
					this, legacyDataArchiveType);
			}

			legacyDataArchiveGroup.addLegacyDataArchive(legacyDataArchive);

			legacyDataArchiveGroupMap.put(
				legacyDataArchiveType, legacyDataArchiveGroup);
		}

		return legacyDataArchiveGroupMap;
	}

	private List<LegacyDataArchive> _getLegacyDataArchives(
		Properties buildProperties) {

		List<LegacyDataArchive> legacyDataArchives = new ArrayList<>();

		File legacyDataWorkingDirectory =
			_legacyDataGitWorkingDirectory.getWorkingDirectory();

		Set<String> portalVersions = _getPortalVersions(buildProperties);

		for (String portalVersion : portalVersions) {
			Set<String> legacyDataArchiveTypes;

			try {
				legacyDataArchiveTypes = _getLegacyDataArchiveTypes(
					legacyDataWorkingDirectory, portalVersion);
			}
			catch (DocumentException | IOException e) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to get data archive names in ",
						legacyDataWorkingDirectory.toString(),
						" for portal version ", portalVersion),
					e);
			}

			for (String legacyDataArchiveType : legacyDataArchiveTypes) {
				Set<String> databaseNames = _getDatabaseNames(
					buildProperties, portalVersion);

				for (String databaseName : databaseNames) {
					LegacyDataArchive legacyDataArchive = new LegacyDataArchive(
						this, legacyDataArchiveType, databaseName,
						portalVersion);

					legacyDataArchives.add(legacyDataArchive);
				}
			}
		}

		return legacyDataArchives;
	}

	private Set<String> _getLegacyDataArchiveTypes(
			File legacyDataWorkingDirectory, String portalVersion)
		throws DocumentException, IOException {

		Set<String> legacyDataArchiveTypes = new HashSet<>();

		List<File> testcaseFiles = JenkinsResultsParserUtil.findFiles(
			new File(legacyDataWorkingDirectory, portalVersion),
			".*\\.testcase");

		for (File testcaseFile : testcaseFiles) {
			Document document = Dom4JUtil.parse(
				JenkinsResultsParserUtil.read(testcaseFile));

			Element rootElement = document.getRootElement();

			legacyDataArchiveTypes.addAll(
				_getPoshiPropertyValues(rootElement, "data.archive.type"));
		}

		return legacyDataArchiveTypes;
	}

	private Set<String> _getPortalVersions(Properties buildProperties) {
		String legacyDataArchivePortalVersions = buildProperties.getProperty(
			"legacy.data.archive.portal.versions");

		return new HashSet<>(
			Arrays.asList(legacyDataArchivePortalVersions.split(",")));
	}

	private Set<String> _getPoshiPropertyValues(
		Element element, String targetPoshiPropertyName) {

		Set<String> poshiPropertyValues = new HashSet<>();

		List<Element> childElements = new ArrayList<>();

		for (Object elementObject : element.elements()) {
			if (elementObject instanceof Element) {
				childElements.add((Element)elementObject);
			}
		}

		if (childElements.isEmpty()) {
			return poshiPropertyValues;
		}

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (childElementName.equals("property")) {
				String poshiPropertyName = childElement.attributeValue("name");

				if (poshiPropertyName.equals(targetPoshiPropertyName)) {
					poshiPropertyValues.add(
						childElement.attributeValue("value"));
				}
			}

			poshiPropertyValues.addAll(
				_getPoshiPropertyValues(childElement, targetPoshiPropertyName));
		}

		return poshiPropertyValues;
	}

	private final File _generatedLegacyDataArchiveDirectory;
	private final List<LegacyDataArchiveCommit> _latestLegacyDataArchiveCommits;
	private final ManualCommit _latestManualCommit;
	private final Map<String, LegacyDataArchiveGroup>
		_legacyDataArchiveGroupMap;
	private final List<LegacyDataArchive> _legacyDataArchives;
	private final GitWorkingDirectory _legacyDataGitWorkingDirectory;

}