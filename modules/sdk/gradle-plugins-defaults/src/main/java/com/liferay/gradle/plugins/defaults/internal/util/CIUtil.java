/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static boolean isRunningInCIEnvironment() {
		if (Validator.isNotNull(System.getenv("JENKINS_HOME"))) {
			return true;
		}

		return false;
	}

	public static boolean isRunningInCIPatcherEnvironment() {
		if (Validator.isNotNull(
				System.getenv("FIX_PACKS_RELEASE_ENVIRONMENT"))) {

			return true;
		}

		return false;
	}

}