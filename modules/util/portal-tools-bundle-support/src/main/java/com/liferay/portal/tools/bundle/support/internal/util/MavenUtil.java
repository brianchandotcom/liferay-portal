/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.bundle.support.internal.util;

import java.io.File;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Proxy;
import org.apache.maven.settings.Settings;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class MavenUtil {

	public static Proxy getProxy(MavenSession mavenSession) {
		Settings settings = mavenSession.getSettings();

		return settings.getActiveProxy();
	}

	public static MavenProject getRootProject(MavenProject mavenProject) {
		if (mavenProject.hasParent()) {
			return getRootProject(mavenProject.getParent());
		}

		return mavenProject;
	}

	public static File getRootProjectBaseDir(MavenProject mavenProject) {
		MavenProject rootProject = getRootProject(mavenProject);

		return rootProject.getBasedir();
	}

}