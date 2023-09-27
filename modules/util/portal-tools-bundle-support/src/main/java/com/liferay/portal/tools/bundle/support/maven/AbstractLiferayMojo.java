/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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