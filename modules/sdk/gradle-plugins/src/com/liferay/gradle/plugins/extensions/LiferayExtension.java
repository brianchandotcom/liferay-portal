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

import java.io.File;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayExtension {

	public LiferayExtension(Project project) throws Exception {
		_tmpDir = new File(project.getRootDir(), "tmp");
	}

	public String getPortalVersion() {
		return _portalVersion;
	}

	public File getTmpDir() {
		return _tmpDir;
	}

	public String getVersionPrefix() {
		String version = getPortalVersion();

		int index = version.indexOf("-");

		if (index != -1) {
			version = version.substring(0, index);
		}

		return version;
	}

	public void setPortalVersion(String portalVersion) {
		_portalVersion = portalVersion;
	}

	public void setTmpDir(File tmpDir) {
		_tmpDir = tmpDir;
	}

	private String _portalVersion = "7.0.0-SNAPSHOT";
	private File _tmpDir;

}