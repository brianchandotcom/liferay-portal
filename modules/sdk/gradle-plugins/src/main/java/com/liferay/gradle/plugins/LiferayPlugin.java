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

package com.liferay.gradle.plugins;

import com.liferay.gradle.util.FileUtil;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayPlugin implements Plugin<Project> {

	public static final String PLUGIN_NAME = "liferay";

	@Override
	public void apply(Project project) {
		Plugin<Project> plugin = null;

		if (FileUtil.exists(project, "bnd.bnd")) {
			plugin = new LiferayOSGiPlugin();
		}
		else {
			plugin = new LiferayJavaPlugin();
		}

		plugin.apply(project);
	}

}