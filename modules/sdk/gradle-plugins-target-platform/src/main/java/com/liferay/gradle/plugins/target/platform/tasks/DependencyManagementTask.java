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
import java.io.IOException;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
		List<String> dependencies = _getTargetPlatformDependencies(
			getProject());

		if ((dependencies != null) && !dependencies.isEmpty()) {
			StringBuilder sb = new StringBuilder();

			for (String dependency : dependencies) {
				sb.append("\t");
				sb.append(dependency);
				sb.append(System.lineSeparator());
			}

			String output = sb.toString();

			File outputFile = getOutputFile();

			if (outputFile != null) {
				try {
					Files.write(outputFile.toPath(), output.getBytes());
				}
				catch (IOException ioException) {
					throw new GradleException(
						"Unable to write dependencies to file", ioException);
				}
			}
			else {
				Logger logger = getLogger();

				if (logger.isLifecycleEnabled()) {
					logger.lifecycle(output);
				}
			}
		}
		else {
			Logger logger = getLogger();

			if (logger.isLifecycleEnabled()) {
				logger.lifecycle("No dependencies found");
			}
		}
	}

	@Option(
		description = "Set the output file for saving the target platform dependency information.",
		option = "output-file"
	)
	public void setOutputFile(String outputFile) {
		_outputFile = outputFile;
	}

	private List<String> _getTargetPlatformDependencies(Project project) {
		final List<String> dependencies = new ArrayList<>();

		final Configuration configuration = GradleUtil.getConfiguration(
			project, "targetPlatformIDEBoms");

		DependencySet dependencySet = configuration.getDependencies();

		dependencySet.all(
			new Action<Dependency>() {

				@Override
				public void execute(Dependency dependency) {
					if (configuration.isCanBeResolved()) {
						Set<File> files = configuration.files(dependency);

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

									dependencies.add(
										groupId + ':' + artifactId + ':' +
											version);
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

		Collections.sort(
			dependencies,
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

		return dependencies;
	}

	private String _outputFile;

}