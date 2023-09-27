/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.util;

import org.gradle.api.Project;
import org.gradle.api.plugins.BasePluginConvention;

/**
 * @author Damien Abos
 * @author Peter Shin
 */
public class OSGiUtil {

	public static String getBundleSymbolicName(Project project) {
		BasePluginConvention basePluginConvention = GradleUtil.getConvention(
			project, BasePluginConvention.class);

		String archivesBaseName = basePluginConvention.getArchivesBaseName();

		String groupId = String.valueOf(project.getGroup());

		if (archivesBaseName.startsWith(groupId)) {
			return archivesBaseName;
		}

		String lastSection = groupId.substring(groupId.lastIndexOf('.') + 1);

		if (archivesBaseName.equals(lastSection)) {
			return groupId;
		}

		if (archivesBaseName.startsWith(lastSection)) {
			String artifactId = archivesBaseName.substring(
				lastSection.length());

			if (Character.isLetterOrDigit(artifactId.charAt(0))) {
				return _getBundleSymbolicName(groupId, artifactId);
			}

			return _getBundleSymbolicName(groupId, artifactId.substring(1));
		}

		return _getBundleSymbolicName(groupId, archivesBaseName);
	}

	private static String _getBundleSymbolicName(
		String groupId, String artifactId) {

		return groupId + "." + artifactId;
	}

}