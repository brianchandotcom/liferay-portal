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

package com.liferay.gradle.plugins.poshi.runner;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.Upload;
import org.gradle.api.tasks.bundling.Jar;

/**
 * @author Andrea Di Giorgi
 */
public class PoshiRunnerResourcesPlugin implements Plugin<Project> {

	public static final String PLUGIN_NAME = "poshiRunnerResources";

	public static final String POSHI_RUNNER_RESOURCES_CONFIGURATION_NAME =
		"poshiRunnerResources";

	public static final String UPLOAD_POSHI_RUNNER_RESOURCES_TASK_NAME =
		"uploadPoshiRunnerResources";

	@Override
	public void apply(Project project) {
		final PoshiRunnerResourcesExtension poshiRunnerResourcesExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, PoshiRunnerResourcesExtension.class);

		Configuration configuration = _addConfigurationPoshiRunnerResources(
			project);

		_addTaskUploadPoshiRunnerResources(project, configuration);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_addArtifactsPoshiRunnerResources(
						project, poshiRunnerResourcesExtension);
				}

			});
	}

	private Jar _addArtifactPoshiRunnerResources(
		Project project, File dir, String baseName, String appendix,
		String version) {

		Jar jar = GradleUtil.addTask(
			project,
			"jarPoshiRunnerResources" + StringUtil.capitalize(baseName),
			Jar.class);

		jar.from(dir);
		jar.setAppendix(appendix);
		jar.setBaseName(baseName);
		jar.setDescription(
			"Assembles a jar archive containing the Poshi Runner resources " +
				"in '" + project.relativePath(dir) + "'.");
		jar.setVersion(version);

		ArtifactHandler artifactHandler = project.getArtifacts();

		artifactHandler.add(POSHI_RUNNER_RESOURCES_CONFIGURATION_NAME, jar);

		return jar;
	}

	private void _addArtifactsPoshiRunnerResources(
			final Project project, File rootDir, final String markerFileName,
			final String appendix, final String version)
		throws IOException {

		final Logger logger = project.getLogger();

		Path rootDirPath = rootDir.toPath();

		if (Files.notExists(rootDirPath)) {
			if (logger.isDebugEnabled()) {
				logger.debug(
					"Ignoring non-existent directory '{}'.", rootDirPath);
			}

			return;
		}

		Files.walkFileTree(
			rootDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path markerFilePath = dirPath.resolve(markerFileName);

					if (Files.notExists(markerFilePath)) {
						return FileVisitResult.CONTINUE;
					}

					String baseName = new String(
						Files.readAllBytes(markerFilePath),
						StandardCharsets.UTF_8);

					if (Validator.isNull(baseName)) {
						if (logger.isWarnEnabled()) {
							logger.warn(
								"Ignoring directory '{}', marker file '{}' " +
									"is empty",
								dirPath, markerFileName);
						}

						return FileVisitResult.SKIP_SUBTREE;
					}

					Jar jar = _addArtifactPoshiRunnerResources(
						project, dirPath.toFile(), baseName, appendix, version);

					jar.exclude(markerFileName);

					return FileVisitResult.SKIP_SUBTREE;
				}

			});
	}

	private void _addArtifactsPoshiRunnerResources(
		Project project,
		PoshiRunnerResourcesExtension poshiRunnerResourcesExtension) {

		String appendix = poshiRunnerResourcesExtension.getArtifactAppendix();
		String version = poshiRunnerResourcesExtension.getArtifactVersion();

		Map<Object, Object> baseNameDirs =
			poshiRunnerResourcesExtension.getBaseNameDirs();

		if (baseNameDirs.isEmpty()) {
			try {
				String markerFileName =
					poshiRunnerResourcesExtension.getMarkerFileName();

				for (File rootDir :
						poshiRunnerResourcesExtension.getMarkerFileRootDirs()) {

					_addArtifactsPoshiRunnerResources(
						project, rootDir, markerFileName, appendix, version);
				}
			}
			catch (IOException ioe) {
				throw new UncheckedIOException(ioe);
			}
		}
		else {
			for (Map.Entry<Object, Object> entry : baseNameDirs.entrySet()) {
				String baseName = GradleUtil.toString(entry.getKey());
				File dir = GradleUtil.toFile(project, entry.getValue());

				_addArtifactPoshiRunnerResources(
					project, dir, baseName, appendix, version);
			}
		}
	}

	private Configuration _addConfigurationPoshiRunnerResources(
		Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, POSHI_RUNNER_RESOURCES_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures the Poshi Runner resources artifacts.");
		configuration.setVisible(false);

		return configuration;
	}

	private Upload _addTaskUploadPoshiRunnerResources(
		Project project, Configuration configuration) {

		Upload upload = GradleUtil.addTask(
			project, UPLOAD_POSHI_RUNNER_RESOURCES_TASK_NAME, Upload.class);

		upload.setConfiguration(configuration);
		upload.setDescription("Uploads all Poshi Runner resources artifacts.");
		upload.setGroup(BasePlugin.UPLOAD_GROUP);

		return upload;
	}

}