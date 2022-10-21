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

package com.liferay.gradle.plugins.workspace.configurator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.client.extension.ClientExtension;
import com.liferay.gradle.plugins.workspace.internal.client.extension.ClientExtensionTypeConfigurer;
import com.liferay.gradle.plugins.workspace.internal.client.extension.ConfigurationRuntimeTypeConfigurer;
import com.liferay.gradle.plugins.workspace.internal.client.extension.CustomElementTypeConfigurer;
import com.liferay.gradle.plugins.workspace.internal.client.extension.ThemeCSSTypeConfigurer;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.task.CreateClientExtensionConfigTask;
import com.liferay.petra.string.StringBundler;

import groovy.lang.Closure;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.Zip;

/**
 * @author Gregory Amerson
 */
public class ClientExtensionProjectConfigurator
	extends BaseProjectConfigurator {

	public static final String ASSEMBLE_CLIENT_EXTENSION_TASK_NAME =
		"assembleClientExtension";

	public static final String BUILD_CLIENT_EXTENSION_ZIP_TASK_NAME =
		"buildClientExtensionZip";

	public static final String CREATE_CLIENT_EXTENSION_CONFIG_TASK_NAME =
		"createClientExtensionConfig";

	public ClientExtensionProjectConfigurator(Settings settings) {
		super(settings);

		_clientExtensionConfigurers.put(
			"configuration",
			Collections.singletonList(
				new ConfigurationRuntimeTypeConfigurer()));
		_clientExtensionConfigurers.put(
			"customElement",
			Collections.singletonList(new CustomElementTypeConfigurer()));
		_clientExtensionConfigurers.put(
			"themeCSS",
			Collections.singletonList(new ThemeCSSTypeConfigurer()));

		_defaultRepositoryEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + NAME +
				".default.repository.enabled",
			_DEFAULT_REPOSITORY_ENABLED);
	}

	@Override
	public void apply(Project project) {
		TaskProvider<CreateClientExtensionConfigTask>
			createClientExtensionConfigTaskProvider =
				GradleUtil.addTaskProvider(
					project, CREATE_CLIENT_EXTENSION_CONFIG_TASK_NAME,
					CreateClientExtensionConfigTask.class);

		TaskProvider<Copy> assembleClientExtensionTaskProvider =
			GradleUtil.addTaskProvider(
				project, ASSEMBLE_CLIENT_EXTENSION_TASK_NAME, Copy.class);

		TaskProvider<Zip> buildClientExtensionZipTaskProvider =
			GradleUtil.addTaskProvider(
				project, BUILD_CLIENT_EXTENSION_ZIP_TASK_NAME, Zip.class);

		_baseConfigureClientExtensionProject(
			project, createClientExtensionConfigTaskProvider,
			assembleClientExtensionTaskProvider,
			buildClientExtensionZipTaskProvider);

		File clientExtensionFile = project.file(_CLIENT_EXTENSION_YAML);

		try (FileReader fileReader = new FileReader(clientExtensionFile)) {
			ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

			JsonNode rootJsonNode = objectMapper.readTree(clientExtensionFile);

			Iterator<Map.Entry<String, JsonNode>> iterator =
				rootJsonNode.fields();

			iterator.forEachRemaining(
				entry -> {
					String id = entry.getKey();

					if (Objects.equals("assemble", id)) {
						JsonNode assembleJsonNode = entry.getValue();

						_configureAssembleClientExtensionTask(
							assembleClientExtensionTaskProvider,
							assembleJsonNode);
					}
					else if (Objects.equals("runtime", id)) {
						JsonNode runtimeJsonNode = entry.getValue();

						JsonNode runtimeTypeJsonNode = runtimeJsonNode.get(
							"type");

						if (runtimeTypeJsonNode != null) {
							createClientExtensionConfigTaskProvider.configure(
								createClientExtensionConfigTask ->
									createClientExtensionConfigTask.setType(
										runtimeTypeJsonNode.asText()));

							List<ClientExtensionTypeConfigurer>
								clientExtensionTypeConfigurers =
									_clientExtensionConfigurers.getOrDefault(
										runtimeTypeJsonNode.asText(),
										Collections.emptyList());

							clientExtensionTypeConfigurers.forEach(
								clientExtensionTypeConfigurer ->
									clientExtensionTypeConfigurer.apply(
										project, Optional.empty(),
										assembleClientExtensionTaskProvider));
						}
					}
					else {
						JsonNode clientExtensionJsonNode = entry.getValue();

						try {
							ClientExtension clientExtension =
								objectMapper.treeToValue(
									clientExtensionJsonNode,
									ClientExtension.class);

							clientExtension.id = id;
							clientExtension.projectName = project.getName();

							createClientExtensionConfigTaskProvider.configure(
								createClientExtensionConfigTask ->
									createClientExtensionConfigTask.
										addClientExtension(clientExtension));

							List<ClientExtensionTypeConfigurer>
								clientExtensionTypeConfigurers =
									_clientExtensionConfigurers.getOrDefault(
										clientExtension.type,
										Collections.emptyList());

							clientExtensionTypeConfigurers.forEach(
								clientExtensionTypeConfigurer ->
									clientExtensionTypeConfigurer.apply(
										project, Optional.of(clientExtension),
										assembleClientExtensionTaskProvider));
						}
						catch (JsonProcessingException
									jsonProcessingException) {

							throw new GradleException(
								"Failed to parse client-extension " + id,
								jsonProcessingException);
						}
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

					if (Files.exists(clientExtensionPath) &&
						!Objects.equals(dirPath, rootDir.toPath())) {

						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return projectDirs;
	}

	protected static final String NAME = "client.extension";

	private TaskProvider<Zip> _baseConfigureClientExtensionProject(
		Project project,
		TaskProvider<CreateClientExtensionConfigTask>
			createClientExtensionConfigTaskProvider,
		TaskProvider<Copy> assembleClientExtensionTaskProvider,
		TaskProvider<Zip> buildClientExtensionZipTaskProvider) {

		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		GradleUtil.applyPlugin(project, BasePlugin.class);
		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);

		LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		configureLiferay(project, workspaceExtension);

		_configureLiferayExtension(project, liferayExtension);

		_configureConfigurationDefault(project);
		_configureTaskClean(project);
		_configureTaskDeploy(project);

		_configureClientExtensionTasks(
			project, createClientExtensionConfigTaskProvider,
			assembleClientExtensionTaskProvider,
			buildClientExtensionZipTaskProvider);

		addTaskDockerDeploy(
			project, buildClientExtensionZipTaskProvider,
			new File(workspaceExtension.getDockerDir(), "client-extensions"));

		_configureArtifacts(project, buildClientExtensionZipTaskProvider);
		_configureRootTaskDistBundle(
			project, buildClientExtensionZipTaskProvider);

		return buildClientExtensionZipTaskProvider;
	}

	private void _configureArtifacts(
		Project project,
		TaskProvider<Zip> buildClientExtensionZipTaskProvider) {

		ArtifactHandler artifacts = project.getArtifacts();

		artifacts.add(
			Dependency.ARCHIVES_CONFIGURATION,
			buildClientExtensionZipTaskProvider);
	}

	private void _configureAssembleClientExtensionTask(
		TaskProvider<Copy> assembleClientExtensionTaskProvider,
		JsonNode assembleJsonNode) {

		assembleClientExtensionTaskProvider.configure(
			copy -> assembleJsonNode.forEach(
				copyJsonNode -> {
					JsonNode fromJsonNode = copyJsonNode.get("from");
					JsonNode includeJsonNode = copyJsonNode.get("include");
					JsonNode intoJsonNode = copyJsonNode.get("into");

					String from =
						(fromJsonNode != null) ? fromJsonNode.asText() : ".";

					copy.from(
						from,
						copySpec -> {
							if (includeJsonNode instanceof ArrayNode) {
								ArrayNode arrayNode =
									(ArrayNode)includeJsonNode;

								arrayNode.forEach(
									include -> copySpec.include(
										include.asText()));
							}
							else {
								if (includeJsonNode != null) {
									copySpec.include(includeJsonNode.asText());
								}
							}

							if (intoJsonNode != null) {
								copySpec.into(intoJsonNode.asText());
							}

							copySpec.setIncludeEmptyDirs(false);
						});
				}));
	}

	@SuppressWarnings("serial")
	private void _configureClientExtensionTasks(
		Project project,
		TaskProvider<CreateClientExtensionConfigTask>
			createClientExtensionConfigTaskProvider,
		TaskProvider<Copy> assembleClientExtensionTaskProvider,
		TaskProvider<Zip> buildClientExtensionZipTaskProvider) {

		createClientExtensionConfigTaskProvider.configure(
			createClientExtensionConfigTask -> {
				TaskInputs taskInputs =
					createClientExtensionConfigTask.getInputs();

				taskInputs.file(project.file(_CLIENT_EXTENSION_YAML));
			});

		assembleClientExtensionTaskProvider.configure(
			copy -> {
				copy.setDestinationDir(
					new File(project.getBuildDir(), "clientExtension"));

				copy.from(createClientExtensionConfigTaskProvider);

				copy.into(
					new Callable<String>() {

						@Override
						public String call() throws Exception {
							return "static";
						}

					},
					new Closure<Void>(copy) {

						@SuppressWarnings("unused")
						public void doCall(CopySpec copySpec) {
							copySpec.from(project.file("assets"));
							copySpec.include("**/*");
							copySpec.setIncludeEmptyDirs(false);
						}

					});
			});

		buildClientExtensionZipTaskProvider.configure(
			zip -> {
				DirectoryProperty destinationDirectoryProperty =
					zip.getDestinationDirectory();

				destinationDirectoryProperty.set(
					new File(project.getProjectDir(), "dist"));

				Property<String> archiveBaseNameProperty =
					zip.getArchiveBaseName();

				archiveBaseNameProperty.set(
					project.provider(
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								return project.getName();
							}

						}));

				zip.from(assembleClientExtensionTaskProvider);
				zip.include("**/*");
			});
	}

	private void _configureConfigurationDefault(Project project) {
		Configuration defaultConfiguration = GradleUtil.getConfiguration(
			project, Dependency.DEFAULT_CONFIGURATION);

		Configuration archivesConfiguration = GradleUtil.getConfiguration(
			project, Dependency.ARCHIVES_CONFIGURATION);

		defaultConfiguration.extendsFrom(archivesConfiguration);
	}

	private void _configureLiferayExtension(
		Project project, LiferayExtension liferayExtension) {

		liferayExtension.setDeployDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File dir = new File(
						liferayExtension.getAppServerParentDir(),
						"osgi/client-extensions");

					dir.mkdirs();

					return GradleUtil.getProperty(
						project, "auto.deploy.dir", dir);
				}

			});
	}

	@SuppressWarnings("serial")
	private void _configureRootTaskDistBundle(
		Project project,
		TaskProvider<Zip> buildClientExtensionZipTaskProvider) {

		Task assembleTask = GradleUtil.getTask(
			project, BasePlugin.ASSEMBLE_TASK_NAME);

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		copy.dependsOn(assembleTask);

		copy.into(
			"osgi/client-extensions",
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					Project project = assembleTask.getProject();

					ConfigurableFileCollection configurableFileCollection =
						project.files(buildClientExtensionZipTaskProvider);

					configurableFileCollection.builtBy(assembleTask);

					copySpec.from(buildClientExtensionZipTaskProvider);
				}

			});
	}

	private void _configureTaskClean(Project project) {
		Delete delete = (Delete)GradleUtil.getTask(
			project, BasePlugin.CLEAN_TASK_NAME);

		delete.delete("build", "dist");
	}

	private void _configureTaskDeploy(Project project) {
		Copy copy = (Copy)GradleUtil.getTask(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME);

		copy.dependsOn(BasePlugin.ASSEMBLE_TASK_NAME);

		copy.from(_getZipFile(project));
	}

	private File _getZipFile(Project project) {
		return project.file(
			"dist/" + GradleUtil.getArchivesBaseName(project) + ".zip");
	}

	private static final String _CLIENT_EXTENSION_YAML =
		"client-extension.yaml";

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private final Map<String, List<ClientExtensionTypeConfigurer>>
		_clientExtensionConfigurers = new HashMap<>();
	private final boolean _defaultRepositoryEnabled;

}