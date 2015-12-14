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

package com.liferay.gradle.plugins.gulp;

import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Project;

/**
 * @author David Truong
 */
public class GulpExtension {

	public GulpExtension(Project project) {
	}

	public String getGulpVersion() {
		return GradleUtil.toString(_gulpVersion);
	}

	public void setGulpVersion(Object gulpVersion) {
		_gulpVersion = gulpVersion;
	}

	private Object _gulpVersion = "3.9.0";

}