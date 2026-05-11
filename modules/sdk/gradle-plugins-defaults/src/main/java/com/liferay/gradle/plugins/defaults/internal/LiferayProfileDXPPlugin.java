/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.internal;

import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;

import java.io.File;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Peter Shin
 */
public class LiferayProfileDXPPlugin implements Plugin<Project> {

	public static final Plugin<Project> INSTANCE =
		new LiferayProfileDXPPlugin();

	@Override
	public void apply(Project project) {
		File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");

		if (portalRootDir != null) {
			File buildProfileDXPPropertiesFile = new File(
				portalRootDir, "build.profile-dxp.properties");

			if (!buildProfileDXPPropertiesFile.exists()) {
				StringBuilder sb = new StringBuilder();

				sb.append("Please run the following command to setup the ");
				sb.append("build profile for DXP:\n");
				sb.append(portalRootDir);
				sb.append("$ ant setup-profile-dxp");

				throw new GradleException(sb.toString());
			}
		}
	}

}