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

package com.liferay.project.templates.spring.mvc.portlet.internal;

import com.beust.jcommander.Parameter;

import com.liferay.project.templates.extensions.ProjectTemplatesArgsExt;

/**
 * @author Gregory Amerson
 */
public class SpringMVCPortletProjectTemplatesArgsExt
	implements ProjectTemplatesArgsExt {

	public String getFramework() {
		return _framework;
	}

	public String getFrameworkDependencies() {
		return _frameworkDependencies;
	}

	@Override
	public String getTemplateName() {
		return "spring-mvc-portlet";
	}

	public String getViewType() {
		return _viewType;
	}

	public void setFramework(String framework) {
		_framework = framework;
	}

	public void setFrameworkDependencies(String frameworkDependencies) {
		_frameworkDependencies = frameworkDependencies;
	}

	public void setViewType(String viewType) {
		_viewType = viewType;
	}

	@Parameter(
		description = "The name of the framework to use in the generated project. (springportletmvc)|(portletmvc4spring)",
		names = "--framework", required = true
	)
	private String _framework;

	@Parameter(
		description = "The way that the framework dependencies will be configured. (embedded)",
		names = "--framework-dependencies"
	)
	private String _frameworkDependencies = "embedded";

	@Parameter(
		description = "Choose the view technology that will be used in the generated project. (jsp)|(thymeleaf)",
		names = "--view-type", required = true
	)
	private String _viewType;

}