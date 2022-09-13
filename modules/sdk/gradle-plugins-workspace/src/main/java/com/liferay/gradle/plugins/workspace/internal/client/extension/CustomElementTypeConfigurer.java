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

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.Zip;

import org.osgi.framework.Version;

/**
 * @author Gregory Amerson
 */
public class CustomElementTypeConfigurer
	implements ClientExtensionTypeConfigurer {

	@Override
	public void apply(
		Project project, ClientExtension clientExtension,
		TaskProvider<Zip> zipTaskProvider) {

		if (!_hasFrontendBuild(project)) {
			return;
		}

		GradleUtil.applyPlugin(project, NodePlugin.class);

		_configureNodeAndNpmVersion(project);

		TaskProvider<CreateClientExtensionConfigTask>
			createClientExtensionConfigTaskProvider =
				GradleUtil.getTaskProvider(
					project,
					ClientExtensionProjectConfigurator.
						CREATE_CLIENT_EXTENSION_CONFIG_TASK_NAME,
					CreateClientExtensionConfigTask.class);

		createClientExtensionConfigTaskProvider.configure(
			createClientExtensionConfigTask ->
				createClientExtensionConfigTask.dependsOn(
					NodePlugin.PACKAGE_RUN_BUILD_TASK_NAME));

		zipTaskProvider.configure(
			new Action<Zip>() {

				@Override
				@SuppressWarnings("serial")
				public void execute(Zip zip) {
					zip.doFirst(
						task -> {
							CreateClientExtensionConfigTask
								createClientExtensionConfigTask =
									createClientExtensionConfigTaskProvider.
										get();

							TaskOutputs taskOutputs =
								createClientExtensionConfigTask.getOutputs();

							FileCollection files = taskOutputs.getFiles();

							files.forEach(
								file -> {
									String fileName = file.getName();

									if (fileName.endsWith(
											".client-extension-config.json")) {

										_expandClientExtensionConfigURLs(
											file, project.getBuildDir());
									}
								});
						});

					zip.into(
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								return "static";
							}

						},
						new Closure<Void>(zip) {

							@SuppressWarnings("unused")
							public void doCall(CopySpec copySpec) {
								copySpec.from(project.file("public"));
								copySpec.include("**/*");
								copySpec.setIncludeEmptyDirs(false);
							}

						});

					zip.into(
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								return "static";
							}

						},
						new Closure<Void>(zip) {

							@SuppressWarnings("unused")
							public void doCall(CopySpec copySpec) {
								copySpec.from(project.getBuildDir());
								copySpec.include("static/**/*");
								copySpec.into("static");
							}

						});
				}

			});
	}

	private void _configureNodeAndNpmVersion(Project project) {
		NodeExtension nodeExtension = GradleUtil.getExtension(
			project, NodeExtension.class);

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

		Map<String, Object> clientExtensionConfig =
			(Map<String, Object>)jsonSlurper.parse(
				originalConfigFileContent.getBytes());

		AtomicReference<String> configData = new AtomicReference<>(
			originalConfigFileContent);

		Set<String> keySet = clientExtensionConfig.keySet();

		keySet.forEach(
			key -> {
				Map<String, Object> configMap =
					(Map<String, Object>)clientExtensionConfig.get(key);

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
	private Map<String, Object> _getPackageJsonMap(File packageJsonFile) {
		if (!packageJsonFile.exists()) {
			return Collections.emptyMap();
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		return (Map<String, Object>)jsonSlurper.parse(packageJsonFile);
	}

	@SuppressWarnings("unchecked")
	private boolean _hasBuildScript(Path packageJsonPath) {
		Map<String, Object> packageJsonMap = _getPackageJsonMap(
			packageJsonPath.toFile());

		Map<String, Object> liferayTheme =
			(Map<String, Object>)packageJsonMap.get("liferayTheme");
		Map<String, Object> scripts = (Map<String, Object>)packageJsonMap.get(
			"scripts");

		if ((liferayTheme == null) && (scripts != null) &&
			(scripts.get("build") != null)) {

			return true;
		}

		return false;
	}

	private boolean _hasFrontendBuild(Project project) {
		File packageJsonFile = project.file("package.json");

		if (packageJsonFile.exists() &&
			_hasBuildScript(packageJsonFile.toPath())) {

			return true;
		}

		return false;
	}

	private static final Version _MINIMUM_NODE_VERSION = Version.parseVersion(
		"10.15.3");

	private static final Version _MINIMUM_NPM_VERSION = Version.parseVersion(
		"6.4.1");

}