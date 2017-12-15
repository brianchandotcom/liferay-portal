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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

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

		_dataArchiveTypes = _getDataArchiveTypes();
		_databaseNames = _getDatabaseNames();
		_latestTestCommit = _getLatestTestCommit();

		_legacyDataArchiveGroups = _getLegacyDataArchiveGroups();
	}

	public List<String> getDatabaseNames() {
		return _databaseNames;
	}

	public Commit getLatestTestCommit() {
		return _latestTestCommit;
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
		Set<String> dataArchiveTypeSet = new HashSet<>();

		try {
			List<File> testcaseFiles = JenkinsResultsParserUtil.findFiles(
				_portalVersionTestDirectory, ".*\\.testcase");

			for (File testcaseFile : testcaseFiles) {
				Document document = Dom4JUtil.parse(
					JenkinsResultsParserUtil.read(testcaseFile));

				Element rootElement = document.getRootElement();

				dataArchiveTypeSet.addAll(
					_getPoshiPropertyValues(rootElement, "data.archive.type"));
			}
		}
		catch (DocumentException | IOException e) {
			throw new RuntimeException(e);
		}

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

	private Commit _getLatestTestCommit() {
		String gitLog = _legacyGitWorkingDirectory.log(
			50, _portalVersionTestDirectory);

		String[] gitLogEntities = gitLog.split("\n");

		for (String gitLogEntity : gitLogEntities) {
			Commit commit = CommitFactory.newCommit(
				gitLogEntity, _legacyGitWorkingDirectory);

			if (commit.getType() != Commit.Type.MANUAL) {
				continue;
			}

			return commit;
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

	private final List<String> _dataArchiveTypes;
	private final List<String> _databaseNames;
	private final Commit _latestTestCommit;
	private final List<LegacyDataArchiveGroup> _legacyDataArchiveGroups;
	private final LegacyDataArchiveUtil _legacyDataArchiveUtil;
	private final GitWorkingDirectory _legacyGitWorkingDirectory;
	private final String _portalVersion;
	private final File _portalVersionDirectory;
	private final File _portalVersionTestDirectory;

}