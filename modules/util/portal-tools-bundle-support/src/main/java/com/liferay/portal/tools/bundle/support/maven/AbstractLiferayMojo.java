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

package com.liferay.portal.tools.bundle.support.maven;

import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;
import com.liferay.portal.tools.bundle.support.internal.util.MavenUtil;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * @author David Truong
 */
public abstract class AbstractLiferayMojo extends AbstractMojo {

	protected String getLiferayHome() {
		return _liferayHome;
	}

	protected File getLiferayHomeDir() {
		if (_liferayHomeDir != null) {
			return _liferayHomeDir;
		}

		if (_liferayHome.startsWith("/") || _liferayHome.contains(":")) {
			_liferayHomeDir = new File(_liferayHome);
		}
		else {
			_liferayHomeDir = new File(
				MavenUtil.getRootProjectBaseDir(project), _liferayHome);
		}

		return _liferayHomeDir;
	}

	protected void setLiferayHome(String liferayHome) {
		_liferayHome = liferayHome;
	}

	@Parameter(defaultValue = "${project}", readonly = true, required = true)
	protected MavenProject project;

	@Parameter(
		alias = "liferayHome",
		defaultValue = BundleSupportConstants.DEFAULT_LIFERAY_HOME_DIR_NAME,
		property = "liferayHome", required = true
	)
	private String _liferayHome;

	private File _liferayHomeDir;

}