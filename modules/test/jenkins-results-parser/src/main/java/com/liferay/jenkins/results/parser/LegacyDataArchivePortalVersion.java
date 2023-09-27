/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class LegacyDataArchivePortalVersion {

	public LegacyDataArchivePortalVersion(
		LegacyDataArchiveHelper legacyDataArchiveHelper, String portalVersion) {

		_legacyDataArchiveHelper = legacyDataArchiveHelper;
		_portalVersion = portalVersion;

		_legacyGitWorkingDirectory =
			legacyDataArchiveHelper.getLegacyGitWorkingDirectory();

		_portalVersionDirectory = new File(
			_legacyGitWorkingDirectory.getWorkingDirectory(), _portalVersion);

		_portalVersionTestDirectory = new File(_portalVersionDirectory, "test");

		if (!_portalVersionTestDirectory.exists()) {
			throw new RuntimeException(
				_portalVersionDirectory + " does not exist");
		}

		_dataArchiveTypes = _getDataArchiveTypes();
		_databaseNames = _getDatabaseNames();
		_latestTestLocalGitCommit = _getLatestTestLocalGitCommit();

		_legacyDataArchiveGroups = _getLegacyDataArchiveGroups();
	}

	public List<String> getDatabaseNames() {
		return _databaseNames;
	}

	public LocalGitCommit getLatestTestLocalGitCommit() {
		return _latestTestLocalGitCommit;
	}

	public List<LegacyDataArchiveGroup> getLegacyDataArchiveGroups() {
		return _legacyDataArchiveGroups;
	}

	public LegacyDataArchiveHelper getLegacyDataArchiveHelper() {
		return _legacyDataArchiveHelper;
	}

	public String getPortalVersion() {
		return _portalVersion;
	}

	public File getPortalVersionTestDirectory() {
		return _portalVersionTestDirectory;
	}

	private List<String> _getDataArchiveTypes() {
		Properties testProperties = JenkinsResultsParserUtil.getProperties(
			new File(_portalVersionTestDirectory, "test.properties"));

		String dataArchiveTypesString = JenkinsResultsParserUtil.getProperty(
			testProperties, "data.archive.types");

		if (JenkinsResultsParserUtil.isNullOrEmpty(dataArchiveTypesString)) {
			dataArchiveTypesString = JenkinsResultsParserUtil.getProperty(
				testProperties,
				"test.case.available.property.values[data.archive.type]");
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(dataArchiveTypesString)) {
			return Collections.emptyList();
		}

		List<String> dataArchiveTypes = new ArrayList<>(
			new HashSet<>(Arrays.asList(dataArchiveTypesString.split(","))));

		Collections.sort(dataArchiveTypes);

		return dataArchiveTypes;
	}

	private List<String> _getDatabaseNames() {
		Properties buildProperties =
			_legacyDataArchiveHelper.getBuildProperties();

		String legacyDataArchiveDatabaseNames = buildProperties.getProperty(
			"legacy.data.archive.database.names");

		String databaseNamesPortalVersionKey = JenkinsResultsParserUtil.combine(
			"legacy.data.archive.database.names[", _portalVersion, "]");

		if (buildProperties.containsKey(databaseNamesPortalVersionKey)) {
			legacyDataArchiveDatabaseNames = buildProperties.getProperty(
				databaseNamesPortalVersionKey);
		}

		List<String> databaseNames = Arrays.asList(
			legacyDataArchiveDatabaseNames.split(","));

		Collections.sort(databaseNames);

		return databaseNames;
	}

	private LocalGitCommit _getLatestTestLocalGitCommit() {
		List<LocalGitCommit> localGitCommits = _legacyGitWorkingDirectory.log(
			50, _portalVersionTestDirectory);

		for (LocalGitCommit localGitCommit : localGitCommits) {
			if (localGitCommit.getType() != LocalGitCommit.Type.MANUAL) {
				continue;
			}

			return localGitCommit;
		}

		return null;
	}

	private List<LegacyDataArchiveGroup> _getLegacyDataArchiveGroups() {
		List<LegacyDataArchiveGroup> legacyDataArchiveTypes = new ArrayList<>();

		for (String dataArchiveType : _dataArchiveTypes) {
			legacyDataArchiveTypes.add(
				new LegacyDataArchiveGroup(this, dataArchiveType));
		}

		return legacyDataArchiveTypes;
	}

	private final List<String> _dataArchiveTypes;
	private final List<String> _databaseNames;
	private final LocalGitCommit _latestTestLocalGitCommit;
	private final List<LegacyDataArchiveGroup> _legacyDataArchiveGroups;
	private final LegacyDataArchiveHelper _legacyDataArchiveHelper;
	private final GitWorkingDirectory _legacyGitWorkingDirectory;
	private final String _portalVersion;
	private final File _portalVersionDirectory;
	private final File _portalVersionTestDirectory;

}