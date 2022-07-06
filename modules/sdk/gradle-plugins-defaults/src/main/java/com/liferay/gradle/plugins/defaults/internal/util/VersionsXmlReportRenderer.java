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

package com.liferay.gradle.plugins.defaults.internal.util;

import com.github.jk1.license.LicenseReportExtension;
import com.github.jk1.license.ManifestData;
import com.github.jk1.license.ModuleData;
import com.github.jk1.license.PomData;
import com.github.jk1.license.ProjectData;
import com.github.jk1.license.render.LicenseDataCollector;
import com.github.jk1.license.render.ReportRenderer;

import com.liferay.gradle.util.Validator;

import groovy.json.JsonSlurper;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import javax.xml.parsers.DocumentBuilder;

import org.gradle.api.GradleException;
import org.gradle.api.UncheckedIOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Andrea Di Giorgi
 * @author Yuxing Wu
 */
public class VersionsXmlReportRenderer implements ReportRenderer {

	public VersionsXmlReportRenderer(
		String fileName, LicenseReportExtension licenseReportExtension,
		Callable<String> moduleFileNamePrefixCallable) {

		_fileName = fileName;
		_licenseReportExtension = licenseReportExtension;
		_moduleFileNamePrefixCallable = moduleFileNamePrefixCallable;
	}

	@Override
	public void render(ProjectData projectData) {
		try {
			_render(projectData);
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}
		catch (Exception exception) {
			throw new GradleException(
				"Unable to render " + projectData, exception);
		}
	}

	protected String getLicenseName(
		String moduleFileName, ModuleData moduleData) {

		List<String> moduleLicenseInfo =
			LicenseDataCollector.singleModuleLicenseInfo(moduleData);

		return moduleLicenseInfo.get(1);
	}

	protected String getLicenseUrl(
		String moduleFileName, ModuleData moduleData) {

		List<String> moduleLicenseInfo =
			LicenseDataCollector.singleModuleLicenseInfo(moduleData);

		return moduleLicenseInfo.get(2);
	}

	protected boolean isExcluded(String moduleFileName, ModuleData moduleData) {
		return false;
	}

	private void _appendLibraryElement(
			Document document, Element librariesElement, String moduleFileName,
			ModuleData moduleData)
		throws Exception {

		List<String> moduleLicenseInfo =
			LicenseDataCollector.singleModuleLicenseInfo(moduleData);

		String projectUrl = moduleLicenseInfo.get(0);

		String licenseName = getLicenseName(moduleFileName, moduleData);
		String licenseUrl = getLicenseUrl(moduleFileName, moduleData);

		Element libraryElement = XMLUtil.appendElement(
			document, librariesElement, "library");

		XMLUtil.appendElement(
			document, libraryElement, "file-name", moduleFileName);
		XMLUtil.appendElement(
			document, libraryElement, "version", moduleData.getVersion());
		XMLUtil.appendElement(
			document, libraryElement, "project-name",
			_getProjectName(moduleData));
		XMLUtil.appendElement(
			document, libraryElement, "project-url", projectUrl);

		Element licensesElement = XMLUtil.appendElement(
			document, libraryElement, "licenses");

		Element licenseElement = XMLUtil.appendElement(
			document, licensesElement, "license");

		XMLUtil.appendElement(
			document, licenseElement, "license-name", licenseName);
		XMLUtil.appendElement(
			document, licenseElement, "license-url", licenseUrl);

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> map = (Map<String, Object>)jsonSlurper.parse(
			_getMavenURL(moduleData.getGroup(), moduleData.getName()));

		Map<String, Object> responseMap = (Map<String, Object>)map.get(
			"response");

		int numFound = (Integer)responseMap.get("numFound");

		if (numFound > 0) {
			List<Map<String, Object>> docsList =
				(List<Map<String, Object>>)responseMap.get("docs");

			Map<String, Object> docMap = docsList.get(0);

			long latestVersionTimestamp = (Long)docMap.get("timestamp");

			StringBuilder sb = new StringBuilder();

			sb.append(moduleData.getGroup());
			sb.append(":");
			sb.append(moduleData.getName());
			sb.append(":");
			sb.append(moduleData.getVersion());

			Iterator<Map<String, Object>> iterator = docsList.iterator();

			while (iterator.hasNext()) {
				docMap = (Map<String, Object>)iterator.next();

				String id = (String)docMap.get("id");

				if (Objects.equals(id, sb.toString())) {
					break;
				}
			}

			long currentVersionTimestamp = (Long)docMap.get("timestamp");

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			XMLUtil.appendElement(
				document, libraryElement, "current-version-publish-date",
				dateFormat.format(new Timestamp(currentVersionTimestamp)));
			XMLUtil.appendElement(
				document, libraryElement, "latest-version-publish-date",
				dateFormat.format(new Timestamp(latestVersionTimestamp)));
		}
	}

	private URL _getMavenURL(String groupId, String artifactId)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append("https://search.maven.org/solrsearch/select?q=g:");
		sb.append(groupId);
		sb.append("+AND+");
		sb.append("a:");
		sb.append(artifactId);
		sb.append("&core=gav&rows=200&wt=json");

		return new URL(sb.toString());
	}

	private String _getProjectName(ModuleData moduleData) {
		String name = null;

		for (ManifestData manifestData : moduleData.getManifests()) {
			name = _getProjectName(manifestData.getName(), name);
		}

		for (PomData pomData : moduleData.getPoms()) {
			name = _getProjectName(pomData.getName(), name);
		}

		if (Validator.isNotNull(name)) {
			return name;
		}

		return moduleData.getGroup() + ":" + moduleData.getName();
	}

	private String _getProjectName(String name, String previousName) {
		if (Validator.isNull(name) || !Character.isAlphabetic(name.charAt(0))) {
			return previousName;
		}

		if (Validator.isNull(previousName) ||
			((name.indexOf(' ') != -1) && (previousName.indexOf(' ') == -1))) {

			return name;
		}

		if (name.length() > previousName.length()) {
			return name;
		}

		return previousName;
	}

	private void _render(ProjectData projectData) throws Exception {
		Map<String, ModuleData> fileNameModuleDataMap = new TreeMap<>();

		String moduleFileNamePrefix = _moduleFileNamePrefixCallable.call();

		for (ModuleData moduleData : projectData.getAllDependencies()) {
			String moduleFileName =
				moduleFileNamePrefix + "!" + moduleData.getName() + ".jar";

			if (isExcluded(moduleFileName, moduleData)) {
				continue;
			}

			fileNameModuleDataMap.put(moduleFileName, moduleData);
		}

		DocumentBuilder documentBuilder = XMLUtil.getDocumentBuilder();

		Document document = documentBuilder.newDocument();

		Element versionsElement = XMLUtil.appendElement(
			document, document, "versions");

		Element versionElement = XMLUtil.appendElement(
			document, versionsElement, "version");

		Element librariesElement = XMLUtil.appendElement(
			document, versionElement, "libraries");

		for (Map.Entry<String, ModuleData> entry :
				fileNameModuleDataMap.entrySet()) {

			_appendLibraryElement(
				document, librariesElement, entry.getKey(), entry.getValue());
		}

		File file = new File(_licenseReportExtension.outputDir, _fileName);

		XMLUtil.write(document, file);
	}

	private final String _fileName;
	private final LicenseReportExtension _licenseReportExtension;
	private final Callable<String> _moduleFileNamePrefixCallable;

}