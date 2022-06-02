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

package com.liferay.gradle.plugins.workspace.configurators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.client.extension.ClientExtension;
import com.liferay.gradle.plugins.workspace.internal.client.extension.ClientExtensionConfigurer;
import com.liferay.gradle.plugins.workspace.internal.client.extension.IFrameConfigurer;
import com.liferay.gradle.plugins.workspace.internal.client.extension.ThemeCSSConfigurer;
import com.liferay.gradle.plugins.workspace.internal.client.extension.ThemeFaviconConfigurer;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.petra.string.StringBundler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.jvm.tasks.Jar;

/**
 * @author Gregory Amerson
 */
public class ClientExtensionProjectConfigurator
	extends BaseProjectConfigurator {

	public ClientExtensionProjectConfigurator(Settings settings) {
		super(settings);

		_clientExtensionConfigurers.put("customElement", new IFrameConfigurer());
		_clientExtensionConfigurers.put("iframe", new IFrameConfigurer());
		_clientExtensionConfigurers.put("themeCSS", new ThemeCSSConfigurer());
		_clientExtensionConfigurers.put(
			"themeFavicon", new ThemeFaviconConfigurer());

		_defaultRepositoryEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + NAME +
				".default.repository.enabled",
			_DEFAULT_REPOSITORY_ENABLED);
	}

	@Override
	public void apply(Project project) {
		_baseConfigureClientExtensionProject(project);

		File clientExtensionFile = project.file(_CLIENT_EXTENSION_YAML);

		try (FileReader fileReader = new FileReader(clientExtensionFile)) {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

			JsonNode rootJsonNode = objectMapper.readTree(clientExtensionFile);

			Iterator<Map.Entry<String, JsonNode>> nodeIterator =
				rootJsonNode.fields();

			nodeIterator.forEachRemaining(
				node -> {
					String id = node.getKey();
					JsonNode clientExtensionNode = node.getValue();

					try {
						ClientExtension clientExtension =
							objectMapper.treeToValue(
								clientExtensionNode, ClientExtension.class);

						clientExtension.id = id;
						clientExtension.projectName = project.getName();

						ClientExtensionConfigurer clientExtensionConfigurer =
							_clientExtensionConfigurers.get(
								clientExtension.type);

						if (clientExtensionConfigurer != null) {
							clientExtensionConfigurer.apply(
								project, clientExtension);
						}
					}
					catch (JsonProcessingException jsonProcessingException) {
						throw new GradleException(
							"Failed to parse client-extension " + id,
							jsonProcessingException);
					}
				});
		}
		catch (IOException ioException) {
			throw new GradleException(
				StringBundler.concat(
					"Failed parsing ", _CLIENT_EXTENSION_YAML, " file."),
				ioException);
		}
	}

	@Override
	public String getName() {
		return "client-extension";
	}

	public boolean isDefaultRepositoryEnabled() {
		return _defaultRepositoryEnabled;
	}

	@Override
	protected Iterable<File> doGetProjectDirs(File rootDir) throws Exception {
		final Set<File> projectDirs = new HashSet<>();

		Files.walkFileTree(
			rootDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String dirName = String.valueOf(dirPath.getFileName());

					if (isExcludedDirName(dirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path clientExtensionPath = dirPath.resolve(
						_CLIENT_EXTENSION_YAML);

					if (Files.exists(clientExtensionPath)) {
						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return projectDirs;
	}

	protected static final String NAME = "client.extension";

	private void _baseConfigureClientExtensionProject(Project project) {
		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		GradleUtil.applyPlugin(project, LiferayOSGiPlugin.class);

		_configureConfigurationDefault(project);
		_configureVersion(project);
		_configureTaskClean(project);

		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		TaskProvider<Jar> jarTaskProvider = GradleUtil.getTaskProvider(
			project, JavaPlugin.JAR_TASK_NAME, Jar.class);

		addTaskDockerDeploy(
			project, jarTaskProvider,
			new File(workspaceExtension.getDockerDir(), "extensions"));
	}

	private void _configureConfigurationDefault(Project project) {
		Configuration defaultConfiguration = GradleUtil.getConfiguration(
			project, Dependency.DEFAULT_CONFIGURATION);

		Configuration archivesConfiguration = GradleUtil.getConfiguration(
			project, Dependency.ARCHIVES_CONFIGURATION);

		defaultConfiguration.extendsFrom(archivesConfiguration);
	}

	private void _configureTaskClean(Project project) {
		Delete delete = (Delete)GradleUtil.getTask(
			project, BasePlugin.CLEAN_TASK_NAME);

		delete.delete("build", "dist");
	}

	private void _configureVersion(Project project) {
		project.setVersion("1.0.0");
	}

	private static final String _CLIENT_EXTENSION_YAML =
		"client-extension.yaml";

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private final Map<String, ClientExtensionConfigurer>
		_clientExtensionConfigurers = new HashMap<>();
	private final boolean _defaultRepositoryEnabled;

}