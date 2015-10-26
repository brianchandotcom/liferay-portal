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

package com.liferay.gradle.plugins.tasks;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.InputDirectory;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BasePortalImplToolsTask extends BasePortalToolsTask {

	@InputDirectory
	public File getAppServerLibGlobalDir() {
		return _appServerLibGlobalDir;
	}

	@InputDirectory
	public File getAppServerPortalDir() {
		return _appServerPortalDir;
	}

	public void setAppServerLibGlobalDir(File appServerLibGlobalDir) {
		_appServerLibGlobalDir = appServerLibGlobalDir;
	}

	public void setAppServerPortalDir(File appServerPortalDir) {
		_appServerPortalDir = appServerPortalDir;
	}

	@Override
	protected void addDependencies() {
		addDependency("com.liferay", "net.sf.jargs", "1.0");
		addDependency("com.thoughtworks.qdox", "qdox", "1.12.1");
		addDependency("javax.activation", "activation", "1.1");
		addDependency("javax.servlet", "javax.servlet-api", "3.0.1");
		addDependency("javax.servlet.jsp", "jsp-api", "2.1");

		GradleUtil.addDependency(
			project, getConfigurationName(), getAppServerClassesPortalDir());
		GradleUtil.addDependency(
			project, getConfigurationName(),
			getJarsFileTree(getAppServerLibGlobalDir()));
		GradleUtil.addDependency(
			project, getConfigurationName(),
			getJarsFileTree(getAppServerLibPortalDir()));
	}

	protected File getAppServerClassesPortalDir() {
		return new File(getAppServerPortalDir(), "WEB-INF/classes");
	}

	protected File getAppServerLibPortalDir() {
		return new File(getAppServerPortalDir(), "WEB-INF/lib");
	}

	protected FileTree getJarsFileTree(File dir) {
		Map<String, Object> args = new HashMap<>();

		args.put("dir", dir);
		args.put("include", "*.jar");

		return project.fileTree(args);
	}

	private File _appServerLibGlobalDir;
	private File _appServerPortalDir;

}