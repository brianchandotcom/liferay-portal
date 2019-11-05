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