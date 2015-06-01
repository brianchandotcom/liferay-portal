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

package com.liferay.gradle.plugins.maven.plugin.builder;

import com.liferay.gradle.plugins.maven.plugin.builder.util.OSDetector;
import com.liferay.gradle.plugins.maven.plugin.builder.util.XMLUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecSpec;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Andrea Di Giorgi
 */
public class BuildPluginDescriptorTask extends DefaultTask {

	public BuildPluginDescriptorTask() {
		_project = getProject();

		if (OSDetector.isWindows()) {
			_mavenExecutable = "mvn.cmd";
		}
		else {
			_mavenExecutable = "mvn";
		}
	}

	@TaskAction
	public void buildPluginDescriptor() {
		File pomFile = _project.file(System.currentTimeMillis() + ".xml");

		try {
			buildPomFile(pomFile);

			buildPluginDescriptor(pomFile);
		}
		catch (Exception e) {
			throw new GradleException(e.getMessage(), e);
		}
		finally {
			_project.delete(pomFile);
		}
	}

	public void configurationScopeMapping(
		String configurationName, String scope) {

		_configurationScopeMappings.put(configurationName, scope);
	}

	@InputDirectory
	public File getClassesDir() {
		return _classesDir;
	}

	public Map<String, String> getConfigurationScopeMappings() {
		return _configurationScopeMappings;
	}

	public String getMavenExecutable() {
		return _mavenExecutable;
	}

	public String getMavenVersion() {
		return _mavenVersion;
	}

	@OutputDirectory
	public File getOutputDir() {
		return _outputDir;
	}

	public String getPomArtifactId() {
		return _pomArtifactId;
	}

	public String getPomGroupId() {
		return _pomGroupId;
	}

	public String getPomVersion() {
		return _pomVersion;
	}

	@InputDirectory
	public File getSourceDir() {
		return _sourceDir;
	}

	public void setClassesDir(File classesDir) {
		_classesDir = classesDir;
	}

	public void setMavenExecutable(String mavenExecutable) {
		_mavenExecutable = mavenExecutable;
	}

	public void setMavenVersion(String mavenVersion) {
		_mavenVersion = mavenVersion;
	}

	public void setOutputDir(File outputDir) {
		_outputDir = outputDir;
	}

	public void setPomArtifactId(String pomArtifactId) {
		_pomArtifactId = pomArtifactId;
	}

	public void setPomGroupId(String pomGroupId) {
		_pomGroupId = pomGroupId;
	}

	public void setPomVersion(String pomVersion) {
		_pomVersion = pomVersion;
	}

	public void setSourceDir(File sourceDir) {
		_sourceDir = sourceDir;
	}

	protected void appendDependencyElements(
		Document doc, Element dependenciesEl, String configurationName,
		String scope) {

		ConfigurationContainer configurationContainer =
			_project.getConfigurations();

		Configuration configuration = configurationContainer.findByName(
			configurationName);

		if (configuration == null) {
			return;
		}

		Set<Dependency> dependencies = configuration.getAllDependencies();

		for (Dependency dependency : dependencies) {
			Element dependencyEl = doc.createElement("dependency");

			dependenciesEl.appendChild(dependencyEl);

			XMLUtil.appendElement(
				doc, dependencyEl, "groupId", dependency.getGroup());
			XMLUtil.appendElement(
				doc, dependencyEl, "artifactId", dependency.getName());
			XMLUtil.appendElement(
				doc, dependencyEl, "version", dependency.getVersion());
			XMLUtil.appendElement(doc, dependencyEl, "scope", scope);
		}
	}

	protected void buildPluginDescriptor(final File pomFile) {
		_project.exec(
			new Action<ExecSpec>() {

				@Override
				public void execute(ExecSpec execSpec) {
					execSpec.args("-B");

					execSpec.args("-e");

					execSpec.args("-f");
					execSpec.args(_project.relativePath(pomFile));

					execSpec.args("-Dencoding=UTF-8");

					execSpec.args(
						"-DoutputDirectory=" +
							_project.relativePath(getOutputDir()));

					execSpec.args(
						"org.apache.maven.plugins:maven-plugin-plugin:" +
							getMavenVersion() + ":descriptor");

					execSpec.setExecutable(getMavenExecutable());
					execSpec.setWorkingDir(_project.getProjectDir());
				}

			});
	}

	protected void buildPomFile(File pomFile) throws Exception {
		Document document = XMLUtil.createDocument();

		Element projectElement = document.createElementNS(
			"http://maven.apache.org/POM/4.0.0", "project");

		document.appendChild(projectElement);

		XMLUtil.appendElement(
			document, projectElement, "modelVersion", "4.0.0");
		XMLUtil.appendElement(
			document, projectElement, "groupId", getPomGroupId());
		XMLUtil.appendElement(
			document, projectElement, "artifactId", getPomArtifactId());
		XMLUtil.appendElement(
			document, projectElement, "version", getPomVersion());
		XMLUtil.appendElement(
			document, projectElement, "packaging", "maven-plugin");

		Element buildElement = document.createElement("build");

		projectElement.appendChild(buildElement);

		XMLUtil.appendElement(
			document, buildElement, "outputDirectory",
			_project.relativePath(getClassesDir()));
		XMLUtil.appendElement(
			document, buildElement, "sourceDirectory",
			_project.relativePath(getSourceDir()));

		Element dependenciesElement = document.createElement("dependencies");

		projectElement.appendChild(dependenciesElement);

		Map<String, String> pomConfigurationScopeMappings =
			getConfigurationScopeMappings();

		for (Map.Entry<String, String> entry :
				pomConfigurationScopeMappings.entrySet()) {

			String configurationName = entry.getKey();
			String scope = entry.getValue();

			appendDependencyElements(
				document, dependenciesElement, configurationName, scope);
		}

		XMLUtil.write(document, pomFile);
	}

	private File _classesDir;
	private final Map<String, String> _configurationScopeMappings =
		new HashMap<>();
	private String _mavenExecutable;
	private String _mavenVersion = "3.4";
	private File _outputDir;
	private String _pomArtifactId;
	private String _pomGroupId;
	private String _pomVersion;
	private final Project _project;
	private File _sourceDir;

}