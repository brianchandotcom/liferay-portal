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

package com.liferay.gradle.plugins.target.platform.tasks;

import com.liferay.gradle.plugins.target.platform.internal.util.GradleUtil;
import com.liferay.gradle.plugins.target.platform.internal.util.XMLUtil;

import groovy.util.XmlSlurper;
import groovy.util.slurpersupport.GPathResult;

import java.io.File;

import java.nio.file.Files;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import org.gradle.api.tasks.options.OptionValues;

import org.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Simon Jiang
 * @author Gregory Amerson
 */
public class DependencyManagementTask extends DefaultTask {

	public DependencyManagementTask() {
		_project = getProject();

		_logger = _project.getLogger();
	}

	@OptionValues("output-type")
	public List<String> getAvailableOutputTypes() {
		return Arrays.asList("json", "text", "xml");
	}

	public Configuration getBomsConfiguration() {
		return GradleUtil.getConfiguration(_project, "targetPlatformIDEBoms");
	}

	@Optional
	@OutputFile
	public File getOutputFile() {
		return GradleUtil.toFile(getProject(), _outputFile);
	}

	@Input
	@Optional
	public String getOutputType() {
		return GradleUtil.toString(_outputType);
	}

	@TaskAction
	public void report() {
		_startProject(_project);

		Configuration bomsConfiguration = getBomsConfiguration();

		Map<String, String> managedVersions = _getTargetPlatformDependencies(
			_project, bomsConfiguration);

		_renderConfigurationManagedVersions(managedVersions, bomsConfiguration);
	}

	@Option(
		description = "Set target file of saving target platform dependency information.",
		option = "output-file"
	)
	public void setOutputFile(Object outputFile) {
		_outputFile = outputFile;
	}

	@Option(
		description = "Set output type of target platform dependency information.",
		option = "output-type"
	)
	public void setOutputType(Object outputType) {
		_outputType = outputType;
	}

	private String _generateJSON(Map<String, String> sortedVersions) {
		JSONObject jsonObject = new JSONObject(sortedVersions);

		return jsonObject.toString();
	}

	private String _generateXml(Map<String, String> managedVersions)
		throws Exception {

		Document document = XMLUtil.newDocument();

		Element dependencyManagementElement = XMLUtil.appendElement(
			document, document, "dependencyManagement");

		Element dependenciesElement = XMLUtil.appendElement(
			document, dependencyManagementElement, "dependencies");

		for (Map.Entry<String, String> entry : managedVersions.entrySet()) {
			Element dependencyElement = XMLUtil.appendElement(
				document, dependenciesElement, "dependency");

			String dependencyKey = entry.getKey();

			String[] dependencyKeyArray = dependencyKey.split(":");

			XMLUtil.appendElement(
				document, dependencyElement, "groupId", dependencyKeyArray[0]);
			XMLUtil.appendElement(
				document, dependencyElement, "artifactId",
				dependencyKeyArray[1]);

			XMLUtil.appendElement(
				document, dependencyElement, "version", entry.getValue());
		}

		return XMLUtil.toString(document);
	}

	private Map<String, String> _getTargetPlatformDependencies(
		Project project, Configuration ideBomsConfiguration) {

		Map<String, String> managedVersions = new HashMap<>();
		DependencySet allDependencies = ideBomsConfiguration.getDependencies();

		allDependencies.all(
			new Action<Dependency>() {

				@Override
				public void execute(Dependency dependency) {
					if (ideBomsConfiguration.isCanBeResolved()) {
						Set<File> files = ideBomsConfiguration.files(
							dependency);

						for (File file : files) {
							try {
								XmlSlurper xmlSlurper = new XmlSlurper();

								GPathResult gPathResult = xmlSlurper.parse(
									file);

								gPathResult =
									(GPathResult)gPathResult.getProperty(
										"dependencyManagement");

								gPathResult =
									(GPathResult)gPathResult.getProperty(
										"dependencies");

								gPathResult =
									(GPathResult)gPathResult.getProperty(
										"dependency");

								Iterator<?> iterator = gPathResult.iterator();

								while (iterator.hasNext()) {
									gPathResult = (GPathResult)iterator.next();

									String groupId = String.valueOf(
										gPathResult.getProperty("groupId"));
									String artifactId = String.valueOf(
										gPathResult.getProperty("artifactId"));
									String version = String.valueOf(
										gPathResult.getProperty("version"));

									managedVersions.put(
										groupId + ":" + artifactId, version);
								}
							}
							catch (Exception exception) {
								Logger logger = project.getLogger();

								if (logger.isWarnEnabled()) {
									logger.warn(
										"Unable to parse BOM from {}", file);
								}
							}
						}
					}
				}

			});

		return managedVersions;
	}

	private void _renderConfigurationManagedVersions(
		Map<String, String> managedVersions,
		final Configuration configuration) {

		_logger.lifecycle(
			configuration.getName() + " Dependency management for the " +
				configuration.getName() + " configuration");

		Map<String, String> sortedVersions = new TreeMap<String, String>(
			new Comparator<String>() {

				@Override
				public int compare(String one, String two) {
					String[] oneComponents = one.split(":");
					String[] twoComponents = two.split(":");

					int result = oneComponents[0].compareTo(twoComponents[0]);

					if (result == 0) {
						result = oneComponents[1].compareTo(twoComponents[1]);
					}

					return result;
				}

			});

		sortedVersions.putAll(managedVersions);

		String dependenciesOutput = null;

		if ((sortedVersions != null) && !sortedVersions.isEmpty()) {
			try {
				if (Objects.equals(_outputType, "json")) {
					dependenciesOutput = _generateJSON(sortedVersions);
				}
				else if (Objects.equals(_outputType, "xml")) {
					dependenciesOutput = _generateXml(sortedVersions);
				}
				else {
					dependenciesOutput = _renderManagedVersions(sortedVersions);
				}

				File outputFile = getOutputFile();

				if (outputFile != null) {
					Files.write(
						outputFile.toPath(), dependenciesOutput.getBytes());
				}
				else {
					_logger.lifecycle("");

					_logger.lifecycle(dependenciesOutput);
				}
			}
			catch (Exception exception) {
				_logger.error(exception.getMessage());
			}
		}
		else {
			_logger.lifecycle("No dependency management");
			_logger.lifecycle("");
		}

		_logger.lifecycle("");
	}

	private String _renderManagedVersions(Map<String, String> managedVersions) {
		StringBuilder dependencyOutputBuilder = new StringBuilder();

		for (Map.Entry<String, String> entry : managedVersions.entrySet()) {
			dependencyOutputBuilder.append(
				"    " + entry.getKey() + " " + entry.getValue() +
					System.lineSeparator());
		}

		return dependencyOutputBuilder.toString();
	}

	private void _startProject(final Project project) {
		_logger.lifecycle("");
		_logger.lifecycle(
			"------------------------------------------------------------");
		String heading;

		if (project.equals(project.getRootProject())) {
			heading = "Root project";
		}
		else {
			heading = "Project " + project.getPath();
		}

		if (project.getDescription() != null) {
			heading += " - " + project.getDescription();
		}

		_logger.lifecycle(heading);
		_logger.lifecycle(
			"------------------------------------------------------------");

		_logger.lifecycle("");
	}

	private final Logger _logger;
	private Object _outputFile;
	private Object _outputType;
	private final Project _project;

}