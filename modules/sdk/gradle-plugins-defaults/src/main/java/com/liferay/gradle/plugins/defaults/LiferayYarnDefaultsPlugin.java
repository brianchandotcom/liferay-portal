/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults;

import com.liferay.gradle.plugins.NodeDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.LiferayCIPatcherPlugin;
import com.liferay.gradle.plugins.defaults.internal.LiferayCIPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.CIUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.YarnPlugin;
import com.liferay.gradle.plugins.node.task.YarnInstallTask;
import com.liferay.gradle.util.OSDetector;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermissions;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.gradle.StartParameter;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Peter Shin
 */
public class LiferayYarnDefaultsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, YarnPlugin.class);

		GradleUtil.applyPlugin(project, NodeDefaultsPlugin.class);

		com.liferay.gradle.plugins.defaults.internal.NodeDefaultsPlugin.
			INSTANCE.apply(project);

		if (CIUtil.isRunningInCIEnvironment()) {
			LiferayCIPlugin.INSTANCE.apply(project);
		}

		if (CIUtil.isRunningInCIPatcherEnvironment()) {
			LiferayCIPatcherPlugin.INSTANCE.apply(project);
		}

		TaskProvider<YarnInstallTask> yarnInstallTaskProvider =
			GradleUtil.getTaskProvider(
				project, YarnPlugin.YARN_INSTALL_TASK_NAME,
				YarnInstallTask.class);

		_configureTaskYarnInstallProvider(project, yarnInstallTaskProvider);
	}

	private void _configureTaskYarnInstallProvider(
		Project project,
		TaskProvider<YarnInstallTask> yarnInstallTaskProvider) {

		yarnInstallTaskProvider.configure(
			yarnInstallTask -> {
				Gradle gradle = project.getGradle();

				StartParameter startParameter = gradle.getStartParameter();

				String buildProfile = System.getProperty("build.profile");

				if (startParameter.isParallelProjectExecutionEnabled() ||
					Validator.isNotNull(buildProfile)) {

					yarnInstallTask.setEnabled(false);
				}
				else {
					yarnInstallTask.doFirst(
						task -> {
							Project rootProject = project.getRootProject();

							File directory = new File(
								rootProject.getBuildDir(), "node-scripts");

							try {
								_unzip(_RESOURCE_NAME, directory.toPath());
							}
							catch (IOException ioException) {
								throw new UncheckedIOException(ioException);
							}
						});
				}
			});
	}

	private void _unzip(String name, Path destinationDirectoryPath)
		throws IOException {

		Files.createDirectories(destinationDirectoryPath);

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(name);
			ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {

			ZipEntry zipEntry = null;

			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				Path path = destinationDirectoryPath.resolve(
					zipEntry.getName());

				if (zipEntry.isDirectory()) {
					if (!Files.exists(path)) {
						Files.createDirectory(path);
					}
				}
				else {
					Files.copy(
						zipInputStream, path,
						StandardCopyOption.REPLACE_EXISTING);
				}

				File file = path.toFile();

				FileTime fileTime = zipEntry.getLastModifiedTime();

				file.setLastModified(fileTime.toMillis());

				if (OSDetector.isWindows()) {
					file.setExecutable(true);
					file.setReadable(true);
					file.setWritable(true);
				}
				else {
					Files.setPosixFilePermissions(
						path, PosixFilePermissions.fromString("rwxrwxrwx"));
				}
			}
		}
	}

	private static final String _RESOURCE_NAME =
		"com/liferay/gradle/plugins/defaults/dependencies/node-scripts.zip";

}