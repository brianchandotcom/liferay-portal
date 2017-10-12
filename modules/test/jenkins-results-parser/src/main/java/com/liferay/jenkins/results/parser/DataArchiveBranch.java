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
public class DataArchiveBranch {

	public DataArchiveBranch(
		File generatedDataArchiveDirectory,
		GitWorkingDirectory portalLegacyGitWorkingDirectory) {

		_generatedDataArchiveDirectory = generatedDataArchiveDirectory;
		_portalLegacyGitWorkingDirectory = portalLegacyGitWorkingDirectory;

		GitWorkingDirectory.Branch upstreamBranch =
			_portalLegacyGitWorkingDirectory.getUpstreamBranch();

		_portalLegacyGitWorkingDirectory.checkoutBranch(upstreamBranch);

		_portalLegacyGitWorkingDirectory.reset("--hard");

		_portalLegacyGitWorkingDirectory.clean();

		_dataArchives = _getDataArchives(_getBuildProperties());
		_latestDataArchiveCommits = _getLatestDataArchiveCommits();
		_latestManualCommit = _getLatestManualCommit();

		_dataArchiveGroupMap = _getDataArchiveGroupMap(_dataArchives);
	}

	public File getGeneratedDataArchiveDirectory() {
		return _generatedDataArchiveDirectory;
	}

	public List<DataArchiveCommit> getLatestDataArchiveCommits() {
		return _latestDataArchiveCommits;
	}

	public GitWorkingDirectory getPortalLegacyGitWorkingDirectory() {
		return _portalLegacyGitWorkingDirectory;
	}

	public File getPortalLegacyWorkingDirectory() {
		return _portalLegacyGitWorkingDirectory.getWorkingDirectory();
	}

	private Properties _getBuildProperties() {
		Properties buildProperties = new Properties();

		File portalLegacyWorkingDirectory =
			_portalLegacyGitWorkingDirectory.getWorkingDirectory();

		File buildPropertiesFile = new File(
			portalLegacyWorkingDirectory, "build.properties");

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

	private Map<String, DataArchiveGroup> _getDataArchiveGroupMap(
		List<DataArchive> dataArchives) {

		Map<String, DataArchiveGroup> dataArchiveGroupMap = new HashMap<>();

		for (DataArchive dataArchive : dataArchives) {
			String dataArchiveType = dataArchive.getDataArchiveType();

			DataArchiveGroup dataArchiveGroup = dataArchiveGroupMap.get(
				dataArchiveType);

			if (dataArchiveGroup == null) {
				dataArchiveGroup = new DataArchiveGroup(this, dataArchiveType);
			}

			dataArchiveGroup.addDataArchive(dataArchive);

			dataArchiveGroupMap.put(dataArchiveType, dataArchiveGroup);
		}

		return dataArchiveGroupMap;
	}

	private List<DataArchive> _getDataArchives(Properties buildProperties) {
		List<DataArchive> dataArchives = new ArrayList<>();

		File portalLegacyWorkingDirectory =
			_portalLegacyGitWorkingDirectory.getWorkingDirectory();

		Set<String> portalVersions = _getPortalVersions(buildProperties);

		for (String portalVersion : portalVersions) {
			Set<String> dataArchiveTypes;

			try {
				dataArchiveTypes = _getDataArchiveTypes(
					portalLegacyWorkingDirectory, portalVersion);
			}
			catch (DocumentException | IOException e) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to get data archive names in ",
						portalLegacyWorkingDirectory.toString(),
						" for portal version ", portalVersion),
					e);
			}

			for (String dataArchiveType : dataArchiveTypes) {
				Set<String> databaseNames = _getDatabaseNames(
					buildProperties, portalVersion);

				for (String databaseName : databaseNames) {
					DataArchive dataArchive = new DataArchive(
						this, dataArchiveType, databaseName, portalVersion);

					dataArchives.add(dataArchive);
				}
			}
		}

		return dataArchives;
	}

	private Set<String> _getDataArchiveTypes(
			File portalLegacyWorkingDirectory, String portalVersion)
		throws DocumentException, IOException {

		Set<String> dataArchiveTypes = new HashSet<>();

		List<File> testcaseFiles = JenkinsResultsParserUtil.findFiles(
			new File(portalLegacyWorkingDirectory, portalVersion),
			".*\\.testcase");

		for (File testcaseFile : testcaseFiles) {
			Document document = Dom4JUtil.parse(
				JenkinsResultsParserUtil.read(testcaseFile));

			Element rootElement = document.getRootElement();

			dataArchiveTypes.addAll(
				_getPoshiPropertyValues(rootElement, "data.archive.type"));
		}

		return dataArchiveTypes;
	}

	private Set<String> _getDatabaseNames(
		Properties buildProperties, String portalVersion) {

		String dataArchiveDatabaseNames = buildProperties.getProperty(
			"data.archive.database.names");

		String databaseNamesPortalVersionKey = JenkinsResultsParserUtil.combine(
			"data.archive.database.names[", portalVersion, "]");

		if (buildProperties.containsKey(databaseNamesPortalVersionKey)) {
			dataArchiveDatabaseNames = buildProperties.getProperty(
				databaseNamesPortalVersionKey);
		}

		return new HashSet<>(
			Arrays.asList(dataArchiveDatabaseNames.split(",")));
	}

	private List<DataArchiveCommit> _getLatestDataArchiveCommits() {
		List<DataArchiveCommit> latestDataArchiveCommits = new ArrayList<>();

		String gitLog = _portalLegacyGitWorkingDirectory.log(50);

		String[] gitLogEntities = gitLog.split("\n");

		for (String gitLogEntity : gitLogEntities) {
			Commit commit = CommitFactory.newCommit(gitLogEntity);

			if (commit instanceof DataArchiveCommit) {
				latestDataArchiveCommits.add((DataArchiveCommit)commit);

				continue;
			}

			break;
		}

		return latestDataArchiveCommits;
	}

	private ManualCommit _getLatestManualCommit() {
		String gitLog = _portalLegacyGitWorkingDirectory.log(50);

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

	private Set<String> _getPortalVersions(Properties buildProperties) {
		String dataArchivePortalVersions = buildProperties.getProperty(
			"data.archive.portal.versions");

		return new HashSet<>(
			Arrays.asList(dataArchivePortalVersions.split(",")));
	}

	private Set<String> _getPoshiPropertyValues(
		Element element, String targetPoshiPropertyName) {

		Set<String> poshiPropertyValues = new HashSet<>();

		List<Element> childElements = element.elements();

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

	private final Map<String, DataArchiveGroup> _dataArchiveGroupMap;
	private final List<DataArchive> _dataArchives;
	private final File _generatedDataArchiveDirectory;
	private final List<DataArchiveCommit> _latestDataArchiveCommits;
	private final ManualCommit _latestManualCommit;
	private final GitWorkingDirectory _portalLegacyGitWorkingDirectory;

}