/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.extensions;

import com.beust.jcommander.Parameter;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Gregory Amerson
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

	public File getDestinationDir() {
		return _destinationDir;
	}

	public String getGroupId() {
		return _groupId;
	}

	public String getLiferayProduct() {
		return _liferayProduct;
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

	public ProjectTemplatesArgsExt getProjectTemplatesArgsExt() {
		return _projectTemplatesArgsExt;
	}

	public String getTemplate() {
		return _template;
	}

	public String getTemplateVersion() {
		return _templateVersion;
	}

	public boolean isDependencyManagementEnabled() {
		return _dependencyManagementEnabled;
	}

	public boolean isForce() {
		return _force;
	}

	public boolean isGradle() {
		return _gradle;
	}

	public boolean isHelp() {
		return _help;
	}

	public boolean isList() {
		return _list;
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

	public void setDependencyManagementEnabled(
		boolean dependencyManagementEnabled) {

		_dependencyManagementEnabled = dependencyManagementEnabled;
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

	public void setLiferayProduct(String liferayiProduct) {
		_liferayProduct = liferayiProduct;
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

	public void setProjectTemplatesArgsExt(
		ProjectTemplatesArgsExt projectTemplatesArgsExt) {

		_projectTemplatesArgsExt = projectTemplatesArgsExt;
	}

	public void setTemplate(String template) {
		_template = template;
	}

	public void setTemplateVersion(String templateVersion) {
		_templateVersion = templateVersion;
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
		description = "If workspace support target platform, no version number is required for the module.",
		names = "--dependency-management-enabled"
	)
	private boolean _dependencyManagementEnabled;

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
		description = "The option for Liferay Platform product. (portal)|(dxp)",
		names = "--liferay-product"
	)
	private String _liferayProduct = "portal";

	@Parameter(
		description = "The version of Liferay to target when creating the project.",
		names = "--liferay-version"
	)
	private String _liferayVersion = "7.4.0";

	@Parameter(
		description = "Print the list of available project templates.",
		help = true, names = "--list"
	)
	private boolean _list;

	@Parameter(
		arity = 1,
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

	private ProjectTemplatesArgsExt _projectTemplatesArgsExt;

	@Parameter(
		description = "The template to use when creating the project.",
		names = "--template"
	)
	private String _template = "mvc-portlet";

	@Parameter(hidden = true, names = "--template-version")
	private String _templateVersion;

}