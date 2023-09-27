/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.modules.ext.internal;

import com.beust.jcommander.Parameter;

import com.liferay.project.templates.extensions.ProjectTemplatesArgsExt;

/**
 * @author Gregory Amerson
 */
public class ModulesExtProjectTemplatesArgs implements ProjectTemplatesArgsExt {

	public String getOriginalModuleName() {
		return _originalModuleName;
	}

	public String getOriginalModuleVersion() {
		return _originalModuleVersion;
	}

	@Override
	public String getTemplateName() {
		return "modules-ext";
	}

	public void setOriginalModuleName(String originalModuleName) {
		_originalModuleName = originalModuleName;
	}

	public void setOriginalModuleVersion(String originalModuleVersion) {
		_originalModuleVersion = originalModuleVersion;
	}

	@Parameter(
		description = "Provide the name of the original module which you want to override.",
		names = "--original-module-name", required = true
	)
	private String _originalModuleName;

	@Parameter(
		description = "The original module version.",
		names = "--original-module-version", required = true
	)
	private String _originalModuleVersion;

}