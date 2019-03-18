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

import java.io.File;

import java.nio.file.Files;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.gradle.api.Project;
import org.gradle.util.GUtil;

/**
 * @author Peter Shin
 */
public class CIUtil {

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

}