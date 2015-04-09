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
public class LiferayThemeExtension extends LiferayExtension {

	public LiferayThemeExtension(Project project) {
		super(project);
	}

	public File getDiffsDir() {
		File diffsDir = project.file(getDiffsDirName());

		if (diffsDir.exists()) {
			return diffsDir;
		}

		return null;
	}

	public String getDiffsDirName() {
		return _diffsDirName;
	}

	public String getThemeParent() {
		return _themeParent;
	}

	public String getThemeType() {
		return _themeType;
	}

	public void setDiffsDirName(String diffsDirName) {
		_diffsDirName = diffsDirName;
	}

	public void setThemeParent(String themeParent) {
		_themeParent = themeParent;
	}

	public void setThemeType(String themeType) {
		_themeType = themeType;
	}

	private String _diffsDirName = "docroot/_diffs";
	private String _themeParent;
	private String _themeType = "vm";

}