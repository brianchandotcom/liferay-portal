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