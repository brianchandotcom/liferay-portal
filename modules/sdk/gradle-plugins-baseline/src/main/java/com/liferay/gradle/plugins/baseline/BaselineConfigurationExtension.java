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

package com.liferay.gradle.plugins.baseline;

import com.liferay.gradle.plugins.baseline.internal.util.GradleUtil;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class BaselineConfigurationExtension {

	public BaselineConfigurationExtension(Project project) {
	}

	public String getLowestBaselineVersion() {
		return GradleUtil.toString(_lowestBaselineVersion);
	}

	public boolean isAllowMavenLocal() {
		return _allowMavenLocal;
	}

	public void setAllowMavenLocal(boolean allowMavenLocal) {
		_allowMavenLocal = allowMavenLocal;
	}

	public void setLowestBaselineVersion(Object lowestBaselineVersion) {
		_lowestBaselineVersion = lowestBaselineVersion;
	}

	private boolean _allowMavenLocal;
	private Object _lowestBaselineVersion = "1.0.0";

}