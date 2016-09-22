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

package com.liferay.ant.gradle;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayGradleExecTask extends GradleExecTask {

	@Override
	public void execute() throws BuildException {
		_addArguments();

		super.execute();
	}

	@Override
	public void init() throws BuildException {
		super.init();

		Project project = getProject();

		setDir(new File(project.getBaseDir(), "modules"));
	}

	public void setForcedCache(boolean forcedCacheEnabled) {
		_forcedCacheEnabled = forcedCacheEnabled;
	}

	public void setPortalBuild(boolean portalBuild) {
		_portalBuild = portalBuild;
	}

	public void setPortalPreBuild(boolean portalPreBuild) {
		_portalPreBuild = portalPreBuild;
	}

	private void _addArguments() {
		Project project = getProject();

		String appServerParentDir = project.getProperty(
			"app.server.parent.dir");

		if ((appServerParentDir != null) && !appServerParentDir.isEmpty()) {
			addArgument("-Dapp.server.parent.dir=" + appServerParentDir);
		}

		addArgument("-Dforced.cache.enabled=" + _forcedCacheEnabled);

		String liferayHome = project.getProperty("liferay.home");

		if ((liferayHome != null) && !liferayHome.isEmpty()) {
			addArgument("-Dliferay.home=" + liferayHome);
		}

		addArgument("-Dportal.build=" + _portalBuild);
		addArgument("-Dportal.pre.build=" + _portalPreBuild);
	}

	private boolean _forcedCacheEnabled = true;
	private boolean _portalBuild = true;
	private boolean _portalPreBuild;

}