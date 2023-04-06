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

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
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

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskProvider;

import org.osgi.framework.Version;

/**
 * @author Gregory Amerson
 */
public class NodeBuildConfigurer implements ClientExtensionConfigurer {

	@Override
	public void apply(
		Project project, Optional<ClientExtension> clientExtensionOptional,
		TaskProvider<Copy> assembleClientExtensionTaskProvider) {

		TaskProvider<CreateClientExtensionConfigTask>
			createClientExtensionConfigTaskProvider =
				GradleUtil.getTaskProvider(
					project,
					ClientExtensionProjectConfigurator.
						CREATE_CLIENT_EXTENSION_CONFIG_TASK_NAME,
					CreateClientExtensionConfigTask.class);

		createClientExtensionConfigTaskProvider.configure(
			createClientExtensionConfigTask ->
				createClientExtensionConfigTask.doLast(
					task -> _updateConfigFiles(project.getBuildDir())));

		if (!_hasFrontendBuildScript(project)) {
			return;
		}

		GradleUtil.applyPlugin(project, NodePlugin.class);

		NodeExtension nodeExtension = GradleUtil.getExtension(
			project, NodeExtension.class);

		_configureExtensionNode(nodeExtension);

		assembleClientExtensionTaskProvider.configure(
			assembleClientExtensionTask ->
				assembleClientExtensionTask.dependsOn(
					NodePlugin.PACKAGE_RUN_BUILD_TASK_NAME));
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
				"Unable to parse Node version", exception);
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
			throw new GradleException("Unable to parse NPM version", exception);
		}
	}

	@SuppressWarnings("unchecked")
	private void _expandClientExtensionConfigURLs(
		File clientExtensionConfigFile, File sourceFilesDir) {

		String originalConfigFileContent = FileUtil.read(
			clientExtensionConfigFile);

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> clientExtensionConfigMap =
			(Map<String, Object>)jsonSlurper.parse(
				originalConfigFileContent.getBytes());

		AtomicReference<String> configData = new AtomicReference<>(
			originalConfigFileContent);

		Set<String> keySet = clientExtensionConfigMap.keySet();

		Path sourceFilesDirPath = sourceFilesDir.toPath();

		keySet.forEach(
			key -> {
				Object object = clientExtensionConfigMap.get(key);

				if (!(object instanceof Map)) {
					return;
				}

				Map<String, Object> configMap = (Map<String, Object>)object;

				List<String> typeSettings = (List<String>)configMap.get(
					"typeSettings");

				Stream<String> settingsStream = typeSettings.stream();

				List<Pattern> globs = settingsStream.flatMap(
					setting -> {
						Stream<String> stream = null;

						String[] split = setting.split("=");

						if (split.length == 2) {
							String value = split[1];

							String[] encodedValues = value.split("\n");

							if (encodedValues.length == 1) {
								stream = Stream.of(value);
							}
							else {
								stream = Arrays.stream(encodedValues);
							}
						}

						return stream;
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

				try (Stream<Path> files = Files.walk(sourceFilesDirPath)) {
					files.map(
						sourceFilesDirPath::relativize
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

	private void _updateConfigFiles(File buildDir) {
		File clientExtensionBuildDir = new File(
			buildDir,
			ClientExtensionProjectConfigurator.CLIENT_EXTENSION_BUILD_DIR);

		File staticDir = new File(buildDir, "static");

		if (!staticDir.exists()) {
			return;
		}

		File[] configFiles = clientExtensionBuildDir.listFiles(
			(d, name) -> name.endsWith(".client-extension-config.json"));

		for (File configFile : configFiles) {
			_expandClientExtensionConfigURLs(configFile, staticDir);
		}
	}

	private static final Version _MINIMUM_NODE_VERSION = Version.parseVersion(
		"10.15.3");

	private static final Version _MINIMUM_NPM_VERSION = Version.parseVersion(
		"6.4.1");

}