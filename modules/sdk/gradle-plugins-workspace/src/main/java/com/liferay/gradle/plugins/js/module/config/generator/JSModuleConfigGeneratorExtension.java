/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.js.module.config.generator;

import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Project;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class JSModuleConfigGeneratorExtension {

	public JSModuleConfigGeneratorExtension(Project project) {
	}

	public String getVersion() {
		return GradleUtil.toString(_version);
	}

	public void setVersion(Object version) {
		_version = version;
	}

	private Object _version = "1.2.1";

}