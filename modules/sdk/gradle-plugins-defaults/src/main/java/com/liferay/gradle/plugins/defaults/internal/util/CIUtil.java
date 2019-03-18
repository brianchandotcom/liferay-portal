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

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.util.Validator;

import groovy.json.JsonSlurper;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.util.GUtil;

/**
 * @author Peter Shin
 */
public class CIUtil {

	public static String getBNDHotfixVersion(
		Project project, String bndFileName) {

		if (!FileUtil.exists(project, bndFileName)) {
			return null;
		}

		Properties properties = GUtil.loadProperties(project.file(bndFileName));

		String version = properties.getProperty(Constants.BUNDLE_VERSION);

		if ((version == null) || (version.indexOf("hotfix") == -1)) {
			return null;
		}

		return version;
	}

	@SuppressWarnings("unchecked")
	public static String getJSONHotfixVersion(
		Project project, String jsonFileName) {

		if (!FileUtil.exists(project, jsonFileName)) {
			return null;
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> map = (Map<String, Object>)jsonSlurper.parse(
			project.file(jsonFileName));

		String version = (String)map.get("version");

		if ((version == null) || (version.indexOf("hotfix") == -1)) {
			return null;
		}

		return version;
	}

	public static boolean isExcludedDependencyProject(
		Project project, Project dependencyProject) {

		File bndFile = project.file("bnd.bnd");

		if (!Files.exists(bndFile.toPath())) {
			return false;
		}

		File dependencyBndFile = dependencyProject.file("bnd.bnd");

		if (!Files.exists(dependencyBndFile.toPath())) {
			return false;
		}

		Properties dependencyProperties = GUtil.loadProperties(
			dependencyBndFile);

		String dependencyExportPackage = dependencyProperties.getProperty(
			Constants.EXPORT_PACKAGE);

		if (Validator.isNotNull(dependencyExportPackage)) {
			Properties properties = GUtil.loadProperties(bndFile);

			String importPackage = properties.getProperty(
				Constants.IMPORT_PACKAGE);

			if (Validator.isNull(importPackage)) {
				return false;
			}

			List<String> importPackages = Arrays.asList(
				importPackage.split(","));

			for (String exportPackage : dependencyExportPackage.split(",")) {
				if (!importPackages.contains("!" + exportPackage)) {
					return false;
				}
			}
		}

		return true;
	}

	public static void restoreHotfixVersion(Project project, String fileName) {
		String hotfixVersion = getBNDHotfixVersion(project, fileName);

		if (fileName.endsWith(".json")) {
			hotfixVersion = getJSONHotfixVersion(project, fileName);
		}

		if (hotfixVersion != null) {
			int index = hotfixVersion.indexOf("-hotfix");

			if (index != -1) {
				String suffix = hotfixVersion.substring(index + 7);

				String newHotfixVersion =
					hotfixVersion.substring(0, index) + ".hotfix" + suffix;

				_write(project, fileName, newHotfixVersion, hotfixVersion);
			}
		}
	}

	public static void updateHotfixVersion(Project project, String fileName) {
		String hotfixVersion = getBNDHotfixVersion(project, fileName);

		if (fileName.endsWith(".json")) {
			hotfixVersion = getJSONHotfixVersion(project, fileName);
		}

		if (hotfixVersion != null) {
			int index = hotfixVersion.indexOf(".hotfix");

			if (index != -1) {
				String suffix = hotfixVersion.substring(index + 7);

				String newHotfixVersion =
					hotfixVersion.substring(0, index) + "-hotfix" + suffix;

				_write(project, fileName, newHotfixVersion, hotfixVersion);
			}
		}
	}

	private static void _write(
		Project project, String fileName, String newHotfixVersion,
		String oldHotfixVersion) {

		File file = project.file(fileName);

		try {
			String content = new String(
				Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

			content = content.replace(oldHotfixVersion, newHotfixVersion);

			Files.write(
				file.toPath(), content.getBytes(StandardCharsets.UTF_8));

			Logger logger = project.getLogger();

			if (logger.isLifecycleEnabled()) {
				String relativePath = project.relativePath(file);

				logger.lifecycle(
					"Updated {}:{}", relativePath, newHotfixVersion);
			}
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

}