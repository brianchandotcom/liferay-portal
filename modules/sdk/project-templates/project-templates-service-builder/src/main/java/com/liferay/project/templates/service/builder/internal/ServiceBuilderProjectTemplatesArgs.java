/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.service.builder.internal;

import com.beust.jcommander.Parameter;

import com.liferay.project.templates.extensions.ProjectTemplatesArgsExt;

/**
 * @author Gregory Amerson
 */
public class ServiceBuilderProjectTemplatesArgs
	implements ProjectTemplatesArgsExt {

	public String getAddOns() {
		return _addOns;
	}

	public String getDependencyInjector() {
		return _dependencyInjector;
	}

	@Override
	public String getTemplateName() {
		return "service-builder";
	}

	public void setAddOns(String addOns) {
		_addOns = addOns;
	}

	public void setDependencyInjector(String dependencyInjector) {
		_dependencyInjector = dependencyInjector;
	}

	@Parameter(
		description = "Set to true for add on options.", names = "--add-ons"
	)
	private String _addOns = "false";

	@Parameter(
		description = "Specify the preferred dependency injection method. (ds|spring)",
		names = "--dependency-injector"
	)
	private String _dependencyInjector = "ds";

}