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

package com.liferay.gradle.templates;

import com.beust.jcommander.Parameter;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public class GradleTemplatesArgs {

	public GradleTemplatesArgs() {
		_destinationDir = new File(System.getProperty("user.dir"));
	}

	public String getClassName() {
		return _className;
	}

	public File getDestinationDir() {
		return _destinationDir;
	}

	public String getHostBundleSymbolicName() {
		return _hostBundleSymbolicName;
	}

	public String getHostBundleVersion() {
		return _hostBundleVersion;
	}

	public String getName() {
		return _name;
	}

	public String getPackageName() {
		return _packageName;
	}

	public String getService() {
		return _service;
	}

	public String getTemplate() {
		return _template;
	}

	public File getWorkspaceDir() {
		return _workspaceDir;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setDestinationDir(File destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setHostBundleSymbolicName(String hostBundleSymbolicName) {
		_hostBundleSymbolicName = hostBundleSymbolicName;
	}

	public void setHostBundleVersion(String hostBundleVersion) {
		_hostBundleVersion = hostBundleVersion;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPackageName(String packageName) {
		_packageName = packageName;
	}

	public void setService(String service) {
		_service = service;
	}

	public void setTemplate(String template) {
		_template = template;
	}

	public void setWorkspaceDir(File workspaceDir) {
		_workspaceDir = workspaceDir;
	}

	protected boolean isHelp() {
		return _help;
	}

	protected boolean isList() {
		return _list;
	}

	@Parameter(
		description = "If a class is generated, provide the name of the class to be generated. If not provided, defaults to the project name.",
		names = "--class-name"
	)
	private String _className;

	@Parameter(
		description = "The directory where to create the new project.",
		names = "--destination"
	)
	private File _destinationDir;

	@Parameter(
		description = "Print this message.", help = true,
		names = {"-h", "--help"}
	)
	private boolean _help;

	@Parameter(
		description = "If a new JSP hook fragment is generated, provide the name of the host bundle symbolic name.",
		names = "--host-bundle-symbolic-name"
	)
	private String _hostBundleSymbolicName;

	@Parameter(
		description = "If a new JSP hook fragment is generated, provide the name of the host bundle version.",
		names = "--host-bundle-version"
	)
	private String _hostBundleVersion;

	@Parameter(
		description = "Print the list of available project templates.",
		help = true, names = "--list"
	)
	private boolean _list;

	@Parameter(
		description = "The name of the new project.", names = "--name",
		required = true
	)
	private String _name;

	@Parameter(
		description = "The main package name to use in the project.",
		names = "--package-name"
	)
	private String _packageName;

	@Parameter(
		description = "If a new DS component is generated, provide the name of the service to be implemented.",
		names = "--service"
	)
	private String _service;

	@Parameter(
		description = "The template to use when creating the project.",
		names = "--template"
	)
	private String _template = "mvcportlet";

	@Parameter(
		description = "The root directory of the Liferay Workspace where the new project is being created.",
		names = "--workspace"
	)
	private File _workspaceDir;

}