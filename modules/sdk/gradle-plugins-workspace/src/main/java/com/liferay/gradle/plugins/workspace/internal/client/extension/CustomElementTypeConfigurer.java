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

package com.liferay.gradle.plugins.workspace.internal.client.extension;

import com.liferay.gradle.plugins.node.NodeExtension;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.workspace.configurator.ClientExtensionProjectConfigurator;
import com.liferay.gradle.plugins.workspace.internal.util.FileUtil;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.task.CreateClientExtensionConfigTask;

import groovy.json.JsonSlurper;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskProvider;

import org.osgi.framework.Version;

/**
 * @author Gregory Amerson
 */
public class CustomElementTypeConfigurer
	implements ClientExtensionTypeConfigurer {

	@Override
	public void apply(
		Project project, Optional<ClientExtension> clientExtensionOptional,
		TaskProvider<Copy> assembleClientExtensionTaskProvider) {

		if (!_hasFrontendBuildScript(project)) {
			return;
		}

		GradleUtil.applyPlugin(project, NodePlugin.class);

		NodeExtension nodeExtension = GradleUtil.getExtension(
			project, NodeExtension.class);

		_configureExtensionNode(nodeExtension);

		TaskProvider<CreateClientExtensionConfigTask>
			createClientExtensionConfigTaskProvider =
				GradleUtil.getTaskProvider(
					project,
					ClientExtensionProjectConfigurator.
						CREATE_CLIENT_EXTENSION_CONFIG_TASK_NAME,
					CreateClientExtensionConfigTask.class);

		createClientExtensionConfigTaskProvider.configure(
			createClientExtensionConfigTask -> {
				createClientExtensionConfigTask.dependsOn(
					NodePlugin.PACKAGE_RUN_BUILD_TASK_NAME);
				createClientExtensionConfigTask.doLast(
					task -> _updateConfigFiles(project.getBuildDir()));
			});

		assembleClientExtensionTaskProvider.configure(
			new Action<Copy>() {

				@Override
				public void execute(Copy copy) {
					copy.doLast(
						task -> _updateConfigFiles(copy.getDestinationDir()));
				}

			});
	}

	private void _configureExtensionNode(NodeExtension nodeExtension) {
		String nodeVersion = nodeExtension.getNodeVersion();

		try {
			Version version = Version.parseVersion(nodeVersion);

			if (version.compareTo(_MINIMUM_NODE_VERSION) < 0) {
				nodeVersion = _MINIMUM_NODE_VERSION.toString();

				nodeExtension.setNodeVersion(nodeVersion);
			}
		}
		catch (Exception exception) {
			throw new GradleException(
				"Unable to parse node version", exception);
		}

		String npmVersion = nodeExtension.getNpmVersion();

		try {
			Version version = Version.parseVersion(nodeVersion);

			if (version.compareTo(_MINIMUM_NPM_VERSION) < 0) {
				npmVersion = _MINIMUM_NPM_VERSION.toString();

				nodeExtension.setNpmVersion(npmVersion);
			}
		}
		catch (Exception exception) {
			throw new GradleException("Unable to parse npm version", exception);
		}
	}

	@SuppressWarnings("unchecked")
	private void _expandClientExtensionConfigURLs(
		File clientExtensionConfigFile, File buildDir) {

		String originalConfigFileContent = FileUtil.read(
			clientExtensionConfigFile);

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> clientExtensionConfigMap =
			(Map<String, Object>)jsonSlurper.parse(
				originalConfigFileContent.getBytes());

		AtomicReference<String> configData = new AtomicReference<>(
			originalConfigFileContent);

		Set<String> keySet = clientExtensionConfigMap.keySet();

		keySet.forEach(
			key -> {
				Map<String, Object> configMap =
					(Map<String, Object>)clientExtensionConfigMap.get(key);

				List<String> typeSettings = (List<String>)configMap.get(
					"typeSettings");

				Stream<String> settingsStream = typeSettings.stream();

				List<Pattern> globs = settingsStream.map(
					setting -> {
						String[] split = setting.split("=");

						if (split.length == 2) {
							return split[1];
						}

						return null;
					}
				).filter(
					Objects::nonNull
				).filter(
					value -> value.contains("*")
				).map(
					Pattern::compile
				).collect(
					Collectors.toList()
				);

				Path buildPath = buildDir.toPath();

				try (Stream<Path> files = Files.walk(buildPath)) {
					files.map(
						buildPath::relativize
					).forEach(
						buildFile -> {
							Stream<Pattern> stream = globs.stream();

							stream.filter(
								glob -> {
									Matcher matcher = glob.matcher(
										buildFile.toString());

									return matcher.matches();
								}
							).forEach(
								glob -> {
									String currentValue = configData.get();

									configData.set(
										currentValue.replace(
											glob.pattern(),
											buildFile.toString()));
								}
							);
						}
					);
				}
				catch (IOException ioException) {
					throw new GradleException(
						"Unable to expand wildcard in config file",
						ioException);
				}
			});

		String updatedConfigFileContent = configData.get();

		if (!originalConfigFileContent.equals(updatedConfigFileContent)) {
			try {
				Files.write(
					clientExtensionConfigFile.toPath(),
					updatedConfigFileContent.getBytes());
			}
			catch (IOException ioException) {
				throw new GradleException(
					"Unable to expand wildcard in config file", ioException);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private boolean _hasFrontendBuildScript(Project project) {
		File packageJsonFile = project.file("package.json");

		if (!packageJsonFile.exists()) {
			return false;
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> packageJsonMap =
			(Map<String, Object>)jsonSlurper.parse(packageJsonFile);

		Map<String, Object> liferayThemeMap =
			(Map<String, Object>)packageJsonMap.get("liferayTheme");
		Map<String, Object> scriptsMap =
			(Map<String, Object>)packageJsonMap.get("scripts");

		if ((liferayThemeMap == null) && (scriptsMap != null) &&
			(scriptsMap.get("build") != null)) {

			return true;
		}

		return false;
	}

	private boolean _isExcludedDirName(String dirName) {
		if (dirName == null) {
			return false;
		}

		if (dirName.equals(".gradle") || dirName.equals("dist") ||
			dirName.equals("gradle") || dirName.equals("node_modules") ||
			dirName.equals("node_modules_cache")) {

			return true;
		}

		return false;
	}

	private void _updateConfigFiles(File outputDir) {
		try {
			Files.walkFileTree(
				outputDir.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path dirPath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						String dirName = String.valueOf(dirPath.getFileName());

						if (_isExcludedDirName(dirName)) {
							return FileVisitResult.SKIP_SUBTREE;
						}

						File dir = dirPath.toFile();

						File[] configFiles = dir.listFiles(
							(d, name) -> name.endsWith(
								".client-extension-config.json"));

						for (File configFile : configFiles) {
							_expandClientExtensionConfigURLs(configFile, dir);
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioException) {
			throw new GradleException(
				"Error updating config files", ioException);
		}
	}

	private static final Version _MINIMUM_NODE_VERSION = Version.parseVersion(
		"10.15.3");

	private static final Version _MINIMUM_NPM_VERSION = Version.parseVersion(
		"6.4.1");

}