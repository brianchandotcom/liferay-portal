/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.gradle;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Environment;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayGradleExecTask extends GradleExecTask {

	@Override
	public void execute() throws BuildException {
		_addArguments();
		_addEnvironmentVariables();

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

	public void setWebSphereHomeDir(String webSphereHomeDir) {
		_webSphereHomeDir = webSphereHomeDir;
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

	private void _addEnvironmentVariables() {
		String webSphereHomeDir = _getWebSphereHomeDir();

		if ((webSphereHomeDir != null) && !webSphereHomeDir.isEmpty()) {
			Environment.Variable variable = new Environment.Variable();

			variable.setKey("WAS_HOME");
			variable.setValue(webSphereHomeDir);

			addEnv(variable);
		}
	}

	private String _getWebSphereHomeDir() {
		String webSphereHomeDir = _webSphereHomeDir;

		if ((webSphereHomeDir == null) || webSphereHomeDir.isEmpty()) {
			Project project = getProject();

			webSphereHomeDir = project.getProperty("app.server.websphere.dir");
		}

		return webSphereHomeDir;
	}

	private boolean _forcedCacheEnabled = true;
	private boolean _portalBuild = true;
	private boolean _portalPreBuild;
	private String _webSphereHomeDir;

}