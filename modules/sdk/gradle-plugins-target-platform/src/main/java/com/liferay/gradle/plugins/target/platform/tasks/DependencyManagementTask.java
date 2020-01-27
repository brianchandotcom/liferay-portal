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

import groovy.util.XmlSlurper;
import groovy.util.slurpersupport.GPathResult;

import java.io.File;

import java.nio.file.Files;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

/**
 * @author Simon Jiang
 * @author Gregory Amerson
 */
public class DependencyManagementTask extends DefaultTask {

	@Optional
	@OutputFile
	public File getOutputFile() {
		return GradleUtil.toFile(getProject(), _outputFile);
	}

	@TaskAction
	public void report() {
		Project project = getProject();

		_writeConfigurationManagedVersions(
			_getTargetPlatformDependencies(
				project,
				GradleUtil.getConfiguration(project, "targetPlatformIDEBoms")));
	}

	@Option(
		description = "Set the output file for saving the target platform dependency information.",
		option = "output-file"
	)
	public void setOutputFile(String outputFile) {
		_outputFile = outputFile;
	}

	private Map<String, String> _getTargetPlatformDependencies(
		Project project, Configuration ideBomsConfiguration) {

		Map<String, String> managedVersions = new TreeMap<String, String>(
			new Comparator<String>() {

				@Override
				public int compare(String entry1, String entry2) {
					String[] components1 = entry1.split(":");
					String[] components2 = entry2.split(":");

					int result = components1[0].compareTo(components2[0]);

					if (result == 0) {
						result = components1[1].compareTo(components2[1]);
					}

					return result;
				}

			});

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

	private String _renderManagedVersions(Map<String, String> managedVersions) {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> entry : managedVersions.entrySet()) {
			sb.append("\t");
			sb.append(entry.getKey());
			sb.append(":");
			sb.append(entry.getValue());
			sb.append(System.lineSeparator());
		}

		return sb.toString();
	}

	private void _writeConfigurationManagedVersions(
		Map<String, String> managedVersions) {

		Logger logger = getLogger();

		if ((managedVersions != null) && !managedVersions.isEmpty()) {
			try {
				String dependenciesOutput = _renderManagedVersions(
					managedVersions);

				File outputFile = getOutputFile();

				if (outputFile != null) {
					Files.write(
						outputFile.toPath(), dependenciesOutput.getBytes());
				}
				else {
					logger.lifecycle(dependenciesOutput);
				}
			}
			catch (Exception exception) {
				throw new GradleException(
					"Unable to output dependency management information",
					exception);
			}
		}
		else {
			logger.lifecycle("No dependency management information available.");
		}

		logger.lifecycle("");
	}

	private String _outputFile;

}