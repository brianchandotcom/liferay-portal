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

import com.github.jk1.license.LicenseReportPlugin.LicenseReportExtension;
import com.github.jk1.license.ManifestData;
import com.github.jk1.license.ModuleData;
import com.github.jk1.license.PomData;
import com.github.jk1.license.ProjectData;
import com.github.jk1.license.render.SingleInfoReportRenderer;

import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import javax.xml.parsers.DocumentBuilder;

import org.gradle.api.GradleException;
import org.gradle.api.UncheckedIOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Andrea Di Giorgi
 */
public class VersionsXmlReportRenderer extends SingleInfoReportRenderer {

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
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
		catch (Exception e) {
			throw new GradleException("Unable to render " + projectData, e);
		}
	}

	protected boolean isExcluded(ModuleData moduleData) {
		return false;
	}

	private void _appendLibraryElement(
		Document document, Element librariesElement, String fileName,
		ModuleData moduleData) {

		List<String> moduleLicenseInfo = moduleLicenseInfo(
			_licenseReportExtension, moduleData);

		String projectUrl = moduleLicenseInfo.get(0);
		String licenseName = moduleLicenseInfo.get(1);
		String licenseUrl = moduleLicenseInfo.get(2);

		Element libraryElement = XMLUtil.appendElement(
			document, librariesElement, "library");

		XMLUtil.appendElement(document, libraryElement, "file-name", fileName);
		XMLUtil.appendElement(
			document, libraryElement, "version", moduleData.getVersion());
		XMLUtil.appendElement(
			document, libraryElement, "project-name",
			_getProjectName(moduleData));
		XMLUtil.appendElement(
			document, libraryElement, "project-url", projectUrl);

		if (Validator.isNotNull(licenseName) ||
			Validator.isNotNull(licenseUrl)) {

			Element licensesElement = XMLUtil.appendElement(
				document, libraryElement, "licenses");

			Element licenseElement = XMLUtil.appendElement(
				document, licensesElement, "license");

			XMLUtil.appendElement(
				document, licenseElement, "license-name", licenseName);
			XMLUtil.appendElement(
				document, licenseElement, "license-url", licenseUrl);
		}
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

		if (Validator.isNull(previousName)) {
			return name;
		}

		if ((name.indexOf(' ') != -1) && (previousName.indexOf(' ') == -1)) {
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
			if (isExcluded(moduleData)) {
				continue;
			}

			String moduleFileName =
				moduleFileNamePrefix + "!" + moduleData.getName() + ".jar";

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

		File file = new File(_licenseReportExtension.getOutputDir(), _fileName);

		XMLUtil.write(document, file);
	}

	private final String _fileName;
	private final LicenseReportExtension _licenseReportExtension;
	private final Callable<String> _moduleFileNamePrefixCallable;

}