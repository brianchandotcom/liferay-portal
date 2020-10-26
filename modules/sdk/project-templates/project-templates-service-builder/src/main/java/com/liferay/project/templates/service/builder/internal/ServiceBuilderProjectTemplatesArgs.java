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

package com.liferay.project.templates.service.builder.internal;

import com.beust.jcommander.Parameter;

import com.liferay.project.templates.extensions.ProjectTemplatesArgsExt;

/**
 * @author Gregory Amerson
 */
public class ServiceBuilderProjectTemplatesArgs
	implements ProjectTemplatesArgsExt {

	public String getAddOnOptions() {
		return _addOnOptions;
	}

	public String getDependencyInjector() {
		return _dependencyInjector;
	}

	@Override
	public String getTemplateName() {
		return "service-builder";
	}

	public void setAddOnOptions(String addOnOptions) {
		_addOnOptions = addOnOptions;
	}

	public void setDependencyInjector(String dependencyInjector) {
		_dependencyInjector = dependencyInjector;
	}

	@Parameter(
		description = "Set to true for add on options.",
		names = "--add-ons"
	)
	private String _addOnOptions = "false";

	@Parameter(
		description = "Specify the preferred dependency injection method. (ds|spring)",
		names = "--dependency-injector"
	)
	private String _dependencyInjector = "ds";

}