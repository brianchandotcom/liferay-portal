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

package com.liferay.gradle.plugins.extensions;

import com.liferay.gradle.plugins.jasper.jspc.JspCPlugin;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Project;
import org.gradle.api.file.FileTree;

/**
 * @author Manuel de la Peña
 */
public class JOnASAppServer extends AppServer {

	public JOnASAppServer(Project project) {
		super("jonas", project);
	}

	@Override
	public void addAppServerDependencies(LiferayExtension liferayExtension) {
		Map<String, Object> args = new HashMap<>();

		args.put(
			"dir",
			new File(liferayExtension.getAppServerDir(), "lib/endorsed"));
		args.put(
			"includes",
			Arrays.asList("xercesImpl-*.jar", "xml-apis-*.jar"));

		FileTree fileTree = project.fileTree(args);

		GradleUtil.addDependency(
			project, JspCPlugin.CONFIGURATION_NAME, fileTree);
	}

}