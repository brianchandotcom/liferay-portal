/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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