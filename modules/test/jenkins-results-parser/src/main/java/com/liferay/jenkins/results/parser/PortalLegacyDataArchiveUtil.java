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
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public class PortalLegacyDataArchiveUtil {

	public static List<File> getPortalLegacyDataArchives(
			String portalLegacyRepositoryDirectory)
		throws DocumentException, FileNotFoundException, IOException {

		List<File> portalLegacyDataArchives = new ArrayList<>();

		Properties buildProperties = new Properties();

		FileInputStream fileInputStream = new FileInputStream(
			new File(portalLegacyRepositoryDirectory, "build.properties"));

		buildProperties.load(fileInputStream);

		Set<String> portalVersions = _getPortalVersions(buildProperties);

		for (String portalVersion : portalVersions) {
			Set<String> dataArchiveNames = _getDataArchiveNames(
				portalLegacyRepositoryDirectory, portalVersion);

			Set<String> databaseNames = _getDatabaseNames(
				buildProperties, portalVersion);

			for (String dataArchiveName : dataArchiveNames) {
				for (String databaseName : databaseNames) {
					File portalLegacyDataArchive = new File(
						JenkinsResultsParserUtil.combine(
							portalLegacyRepositoryDirectory, "/", portalVersion,
							"/data-archive/", dataArchiveName, "-",
							databaseName, ".zip"));

					portalLegacyDataArchives.add(portalLegacyDataArchive);
				}
			}
		}

		return portalLegacyDataArchives;
	}

	private static Set<String> _getDataArchiveNames(
			String portalLegacyRepositoryDirectory, String portalVersion)
		throws DocumentException, FileNotFoundException, IOException {

		Set<String> dataArchiveNames = new TreeSet<>();

		List<File> testcaseFiles = JenkinsResultsParserUtil.findFiles(
			new File(portalLegacyRepositoryDirectory, portalVersion),
			".*.testcase");

		for (File testcaseFile : testcaseFiles) {
			Document document = Dom4JUtil.parse(
				JenkinsResultsParserUtil.read(testcaseFile));

			Element rootElement = document.getRootElement();

			dataArchiveNames.addAll(
				_getPoshiPropertyValues(rootElement, "data.archive.type"));
		}

		return dataArchiveNames;
	}

	private static Set<String> _getDatabaseNames(
		Properties buildProperties, String portalVersion) {

		String dataArchiveDatabaseNames = buildProperties.getProperty(
			"data.archive.database.names");

		String databaseNamesPortalVersionKey = JenkinsResultsParserUtil.combine(
			"data.archive.database.names[", portalVersion, "]");

		if (buildProperties.containsKey(databaseNamesPortalVersionKey)) {
			dataArchiveDatabaseNames = buildProperties.getProperty(
				databaseNamesPortalVersionKey);
		}

		return new TreeSet<>(
			Arrays.asList(dataArchiveDatabaseNames.split(",")));
	}

	private static Set<String> _getPortalVersions(Properties buildProperties) {
		Set<String> portalVersions = new TreeSet<>();

		String dataArchivePortalVersions = buildProperties.getProperty(
			"data.archive.portal.versions");

		return new TreeSet<>(
			Arrays.asList(dataArchivePortalVersions.split(",")));
	}

	private static Set<String> _getPoshiPropertyValues(
		Element element, String targetPoshiPropertyName) {

		Set<String> poshiPropertyValues = new TreeSet();

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

}