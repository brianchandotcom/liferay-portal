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
public class LegacyDataArchivePortalVersion {

	public LegacyDataArchivePortalVersion(
		LegacyDataArchiveUtil legacyDataArchiveUtil, String portalVersion) {

		_legacyDataArchiveUtil = legacyDataArchiveUtil;
		_portalVersion = portalVersion;

		_legacyGitWorkingDirectory =
			_legacyDataArchiveUtil.getLegacyGitWorkingDirectory();

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

	public LegacyDataArchiveUtil getLegacyDataArchiveUtil() {
		return _legacyDataArchiveUtil;
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

		if (!testProperties.containsKey(
				"test.case.available.property.values[data.archive.type]")) {

			return Collections.emptyList();
		}

		String dataArchiveTypeString = testProperties.getProperty(
			"test.case.available.property.values[data.archive.type]");

		Set<String> dataArchiveTypeSet = new HashSet<>(
			Arrays.asList(dataArchiveTypeString.split(",")));

		List<String> dataArchiveTypes = new ArrayList<>(dataArchiveTypeSet);

		Collections.sort(dataArchiveTypes);

		return dataArchiveTypes;
	}

	private List<String> _getDatabaseNames() {
		Properties buildProperties =
			_legacyDataArchiveUtil.getBuildProperties();

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
	private final LegacyDataArchiveUtil _legacyDataArchiveUtil;
	private final GitWorkingDirectory _legacyGitWorkingDirectory;
	private final String _portalVersion;
	private final File _portalVersionDirectory;
	private final File _portalVersionTestDirectory;

}