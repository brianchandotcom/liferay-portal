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

package com.liferay.project.templates;

import com.beust.jcommander.Parameter;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class ProjectTemplatesArgs {

	public ProjectTemplatesArgs() {
		_author = System.getProperty("user.name");
		_destinationDir = new File(System.getProperty("user.dir"));
	}

	public List<File> getArchetypesDirs() {
		return _archetypesDirs;
	}

	public String getAuthor() {
		return _author;
	}

	public String getClassName() {
		return _className;
	}

	public String getContributorType() {
		return _contributorType;
	}

	public File getDestinationDir() {
		return _destinationDir;
	}

	public String getGroupId() {
		return _groupId;
	}

	public String getHostBundleSymbolicName() {
		return _hostBundleSymbolicName;
	}

	public String getHostBundleVersion() {
		return _hostBundleVersion;
	}

	public String getLiferayVersion() {
		return _liferayVersion;
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

	public boolean isForce() {
		return _force;
	}

	public boolean isGradle() {
		return _gradle;
	}

	public boolean isMaven() {
		return _maven;
	}

	public void setArchetypesDirs(List<File> archetypesDirs) {
		_archetypesDirs = archetypesDirs;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setContributorType(String contributorType) {
		_contributorType = contributorType;
	}

	public void setDestinationDir(File destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setForce(boolean force) {
		_force = force;
	}

	public void setGradle(boolean gradle) {
		_gradle = gradle;
	}

	public void setGroupId(String groupId) {
		_groupId = groupId;
	}

	public void setHostBundleSymbolicName(String hostBundleSymbolicName) {
		_hostBundleSymbolicName = hostBundleSymbolicName;
	}

	public void setHostBundleVersion(String hostBundleVersion) {
		_hostBundleVersion = hostBundleVersion;
	}

	public void setLiferayVersion(String version) {
		_liferayVersion = version;
	}

	public void setMaven(boolean maven) {
		_maven = maven;
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

	protected boolean isHelp() {
		return _help;
	}

	protected boolean isList() {
		return _list;
	}

	@Parameter(hidden = true, names = {"--archetypes-dir", "--archetypes-dirs"})
	private List<File> _archetypesDirs = new ArrayList<>();

	@Parameter(
		description = "The name of the user associated with the code.",
		names = "--author"
	)
	private String _author;

	@Parameter(
		description = "If a class is generated, provide the name of the class to be generated. If not provided, defaults to the project name.",
		names = "--class-name"
	)
	private String _className;

	@Parameter(
		description = "Used to identify your module as a Theme Contributor. Also, used to add the Liferay-Theme-Contributor-Type and Web-ContextPath bundle headers.",
		names = "--contributor-type"
	)
	private String _contributorType;

	@Parameter(
		description = "The directory where to create the new project.",
		names = "--destination"
	)
	private File _destinationDir;

	@Parameter(
		description = "Forces creation of new project even if target directory contains files.",
		names = "--force"
	)
	private boolean _force;

	@Parameter(
		arity = 1,
		description = "Add the Gradle build script and the Gradle Wrapper to the new project.",
		names = "--gradle"
	)
	private boolean _gradle = true;

	@Parameter(
		description = "The group ID to use in the project.",
		names = "--group-id"
	)
	private String _groupId;

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
		description = "The version of Liferay to target when creating the project.",
		names = "--liferayVersion"
	)
	private String _liferayVersion = "7.0";

	@Parameter(
		description = "Print the list of available project templates.",
		help = true, names = "--list"
	)
	private boolean _list;

	@Parameter(
		description = "Add the Maven POM file and the Maven Wrapper to the new project.",
		names = "--maven"
	)
	private boolean _maven;

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
	private String _template = "mvc-portlet";

}