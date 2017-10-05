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
import java.util.Comparator;
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
public class PortalLegacyDataArchiveUtil {

	public static List<File> getPortalLegacyDataArchives(
		String portalLegacyRepositoryDirectory) {

		List<File> portalLegacyDataArchives = new ArrayList<>();

		File buildPropertiesFile = new File(
			portalLegacyRepositoryDirectory, "build.properties");

		Properties buildProperties = new Properties();

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

		Set<String> portalVersions = _getPortalVersions(buildProperties);

		for (String portalVersion : portalVersions) {
			Set<String> dataArchiveNames;

			try {
				dataArchiveNames = _getDataArchiveNames(
					portalLegacyRepositoryDirectory, portalVersion);
			}
			catch (DocumentException | IOException e) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to get data archive names in ",
						portalLegacyRepositoryDirectory, " for portal version ",
						portalVersion),
					e);
			}

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

		Comparator<File> fileComparator = new Comparator<File>() {

			public int compare(File file1, File file2) {
				String path1 = file1.getAbsolutePath();
				String path2 = file2.getAbsolutePath();

				return path1.compareTo(path2);
			}

		};

		Collections.sort(portalLegacyDataArchives, fileComparator);

		return portalLegacyDataArchives;
	}

	private static Set<String> _getDataArchiveNames(
			String portalLegacyRepositoryDirectory, String portalVersion)
		throws DocumentException, IOException {

		Set<String> dataArchiveNames = new HashSet<>();

		List<File> testcaseFiles = JenkinsResultsParserUtil.findFiles(
			new File(portalLegacyRepositoryDirectory, portalVersion),
			".*\\.testcase");

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

		return new HashSet<>(
			Arrays.asList(dataArchiveDatabaseNames.split(",")));
	}

	private static Set<String> _getPortalVersions(Properties buildProperties) {
		String dataArchivePortalVersions = buildProperties.getProperty(
			"data.archive.portal.versions");

		return new HashSet<>(
			Arrays.asList(dataArchivePortalVersions.split(",")));
	}

	private static Set<String> _getPoshiPropertyValues(
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

}