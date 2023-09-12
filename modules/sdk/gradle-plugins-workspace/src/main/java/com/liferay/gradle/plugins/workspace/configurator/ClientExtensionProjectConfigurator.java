/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.configurator;

import com.bmuschko.gradle.docker.DockerRegistryCredentials;
import com.bmuschko.gradle.docker.DockerRemoteApiPlugin;
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage;
import com.bmuschko.gradle.docker.tasks.image.DockerRemoveImage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.client.extension.ClientExtension;
import com.liferay.gradle.plugins.workspace.internal.client.extension.NodeBuildConfigurer;
import com.liferay.gradle.plugins.workspace.internal.client.extension.ThemeCSSTypeConfigurer;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.internal.util.StringUtil;
import com.liferay.gradle.plugins.workspace.task.CreateClientExtensionConfigTask;
import com.liferay.gradle.util.Validator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import groovy.lang.Closure;

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
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
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
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.SetProperty;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.Zip;
import org.gradle.language.base.plugins.LifecycleBasePlugin;

/**
 * @author Gregory Amerson
 */
public class ClientExtensionProjectConfigurator
	extends BaseProjectConfigurator {

	public static final String ASSEMBLE_CLIENT_EXTENSION_TASK_NAME =
		"assembleClientExtension";

	public static final String BUILD_CLIENT_EXTENSION_ZIP_TASK_NAME =
		"buildClientExtensionZip";

	public static final String CLIENT_EXTENSION_BUILD_DIR =
		"liferay-client-extension-build";

	public static final String CREATE_CLIENT_EXTENSION_CONFIG_TASK_NAME =
		"createClientExtensionConfig";

	public static final String VALIDATE_CLIENT_EXTENSION_IDS_TASK_NAME =
		"validateClientExtensionIds";

	public ClientExtensionProjectConfigurator(Settings settings) {
		super(settings);

		_defaultRepositoryEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + NAME +
				".default.repository.enabled",
			_DEFAULT_REPOSITORY_ENABLED);
	}

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

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

		TaskProvider<DefaultTask> validateClientExtensionIdsTaskProvider =
			GradleUtil.addTaskProvider(
				project, VALIDATE_CLIENT_EXTENSION_IDS_TASK_NAME,
				DefaultTask.class);

		_baseConfigureClientExtensionProject(
			project, assembleClientExtensionTaskProvider,
			buildClientExtensionZipTaskProvider,
			createClientExtensionConfigTaskProvider,
			validateClientExtensionIdsTaskProvider, workspaceExtension);

		AtomicBoolean hasThemeCSSClientExtension = new AtomicBoolean(false);

		Map<String, JsonNode> profileJsonNodes =
			_configureClientExtensionJsonNodes(
				project, createClientExtensionConfigTaskProvider);

		for (Map.Entry<String, JsonNode> profileJsonNodeEntry :
				profileJsonNodes.entrySet()) {

			String profileName = profileJsonNodeEntry.getKey();

			JsonNode jsonNode = profileJsonNodeEntry.getValue();

			Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();

			iterator.forEachRemaining(
				entry -> {
					String fieldName = entry.getKey();

					if (Objects.equals(fieldName, "runtime")) {
						return;
					}

					JsonNode fieldJsonNode = entry.getValue();

					if (Objects.equals(fieldName, "assemble")) {
						_configureAssembleClientExtensionTask(
							project, assembleClientExtensionTaskProvider,
							fieldJsonNode, profileName);

						return;
					}

					try {
						ClientExtension clientExtension =
							_yamlObjectMapper.treeToValue(
								(ObjectNode)fieldJsonNode,
								ClientExtension.class);

						clientExtension.id = fieldName;

						if (Validator.isNull(clientExtension.type)) {
							clientExtension.type = fieldName;
						}

						clientExtension.classification = _getClassification(
							clientExtension.id, clientExtension.type);

						clientExtension.projectId =
							StringUtil.toAlphaNumericLowerCase(
								project.getName());
						clientExtension.projectName = project.getName();

						_validateClientExtension(clientExtension);

						_clientExtensionIds.compute(
							clientExtension.id,
							(key, value) -> {
								if (value == null) {
									value = new HashSet<>();
								}

								value.add(project);

								return value;
							});

						createClientExtensionConfigTaskProvider.configure(
							createClientExtensionConfigTask -> {
								if (!_isActiveProfile(project, profileName)) {
									return;
								}

								createClientExtensionConfigTask.
									addClientExtension(clientExtension);
							});

						if (clientExtension.type.equals("configuration")) {
							assembleClientExtensionTaskProvider.configure(
								copy -> {
									if (!_isActiveProfile(
											project, profileName)) {

										return;
									}

									copy.from(
										"src",
										copySpec -> copySpec.include("**/*"));
								});
						}
						else if (clientExtension.type.equals("themeCSS")) {
							hasThemeCSSClientExtension.set(true);
						}
					}
					catch (JsonProcessingException jsonProcessingException) {
						throw new GradleException(
							"Unable to parse client extension " + fieldName,
							jsonProcessingException);
					}
				});
		}

		if (hasThemeCSSClientExtension.get()) {
			_themeCSSTypeConfigurer.apply(
				project, assembleClientExtensionTaskProvider);
		}

		_nodeBuildConfigurer.apply(
			project, assembleClientExtensionTaskProvider);

		_addDockerTasks(
			project, assembleClientExtensionTaskProvider,
			createClientExtensionConfigTaskProvider, workspaceExtension);
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

	private void _addDockerTasks(
		Project project, TaskProvider<Copy> assembleClientExtensionTaskProvider,
		TaskProvider<CreateClientExtensionConfigTask>
			createClientExtensionConfigTaskProvider,
		WorkspaceExtension workspaceExtension) {

		DockerBuildImage dockerBuildImage = GradleUtil.addTask(
			project, RootProjectConfigurator.BUILD_DOCKER_IMAGE_TASK_NAME,
			DockerBuildImage.class);

		dockerBuildImage.setDescription(
			"Builds a child Docker image from the Liferay base image with " +
				"all configs deployed.");
		dockerBuildImage.setGroup(RootProjectConfigurator.DOCKER_GROUP);

		dockerBuildImage.dependsOn(createClientExtensionConfigTaskProvider);

		DirectoryProperty inputDirectoryProperty =
			dockerBuildImage.getInputDir();

		assembleClientExtensionTaskProvider.configure(
			copy -> inputDirectoryProperty.set(copy.getDestinationDir()));

		Property<Boolean> pullProperty = dockerBuildImage.getPull();

		pullProperty.set(workspaceExtension.getDockerPullPolicy());

		if (Objects.nonNull(
				workspaceExtension.getDockerLocalRegistryAddress())) {

			DockerRegistryCredentials dockerRegistryCredentials =
				dockerBuildImage.getRegistryCredentials();

			String dockerUserAccessToken =
				workspaceExtension.getDockerUserAccessToken();

			if (Objects.nonNull(dockerUserAccessToken)) {
				Property<String> passwordProperty =
					dockerRegistryCredentials.getPassword();

				passwordProperty.set(dockerUserAccessToken);
			}

			String dockerUserName = workspaceExtension.getDockerUserName();

			if (Objects.nonNull(dockerUserName)) {
				Property<String> userNameProperty =
					dockerRegistryCredentials.getUsername();

				userNameProperty.set(dockerUserName);
			}
		}

		DockerRemoveImage dockerRemoveImage = GradleUtil.addTask(
			project, RootProjectConfigurator.CLEAN_DOCKER_IMAGE_TASK_NAME,
			DockerRemoveImage.class);

		dockerRemoveImage.setDescription("Removes the Docker image.");
		dockerRemoveImage.setGroup(RootProjectConfigurator.DOCKER_GROUP);

		Property<Boolean> forceProperty = dockerRemoveImage.getForce();

		forceProperty.set(true);

		String dockerImageId = _getDockerImageId(project);

		SetProperty<String> setProperty = dockerBuildImage.getImages();

		setProperty.add(dockerImageId);

		Property<String> property = dockerRemoveImage.getImageId();

		property.set(dockerImageId);

		dockerRemoveImage.onError(
			new Action<Throwable>() {

				@Override
				public void execute(Throwable throwable) {
					Logger logger = project.getLogger();

					if (logger.isWarnEnabled()) {
						logger.warn(
							"No image with ID '" + _getDockerImageId(project) +
								"' found.");
					}
				}

			});

		Task cleanTask = GradleUtil.getTask(
			project, LifecycleBasePlugin.CLEAN_TASK_NAME);

		cleanTask.dependsOn(dockerRemoveImage);
	}

	private TaskProvider<Zip> _baseConfigureClientExtensionProject(
		Project project, TaskProvider<Copy> assembleClientExtensionTaskProvider,
		TaskProvider<Zip> buildClientExtensionZipTaskProvider,
		TaskProvider<CreateClientExtensionConfigTask>
			createClientExtensionConfigTaskProvider,
		TaskProvider<DefaultTask> validateClientExtensionIdsTaskProvider,
		WorkspaceExtension workspaceExtension) {

		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		GradleUtil.applyPlugin(project, BasePlugin.class);
		GradleUtil.applyPlugin(project, DockerRemoteApiPlugin.class);
		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);

		LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		configureLiferay(project, workspaceExtension);

		_configureLiferayExtension(project, liferayExtension);

		_configureConfigurationDefault(project);
		_configureTaskCheck(project);
		_configureTaskClean(project);
		_configureTaskDeploy(project);

		_configureClientExtensionTasks(
			project, assembleClientExtensionTaskProvider,
			buildClientExtensionZipTaskProvider,
			createClientExtensionConfigTaskProvider,
			validateClientExtensionIdsTaskProvider);

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
		Project project, TaskProvider<Copy> assembleClientExtensionTaskProvider,
		JsonNode assembleJsonNode, String profileName) {

		if (assembleJsonNode.isNull()) {
			return;
		}

		ArrayNode assembleArrayNode = (ArrayNode)assembleJsonNode;

		if (assembleArrayNode.isEmpty()) {
			return;
		}

		assembleClientExtensionTaskProvider.configure(
			assembleClientExtensionCopy -> {
				if (!_isActiveProfile(project, profileName)) {
					return;
				}

				TaskInputs taskInputs = assembleClientExtensionCopy.getInputs();

				taskInputs.file(_CLIENT_EXTENSION_YAML);

				assembleArrayNode.forEach(
					copyJsonNode -> {
						JsonNode fromJsonNode = copyJsonNode.get("from");
						JsonNode fromTaskJsonNode = copyJsonNode.get(
							"fromTask");
						JsonNode includeJsonNode = copyJsonNode.get("include");
						JsonNode intoJsonNode = copyJsonNode.get("into");

						Object fromPath = null;

						if (fromTaskJsonNode != null) {
							TaskContainer taskContainer = project.getTasks();

							fromPath = taskContainer.findByName(
								fromTaskJsonNode.asText());
						}

						if ((fromPath == null) && (fromJsonNode != null)) {
							fromPath = fromJsonNode.asText();
						}

						assembleClientExtensionCopy.from(
							(fromPath != null) ? fromPath : ".",
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
										copySpec.include(
											includeJsonNode.asText());
									}
									else {
										copySpec.include("**/*");
									}
								}

								copySpec.exclude(
									"**/" + CLIENT_EXTENSION_BUILD_DIR);

								if (intoJsonNode != null) {
									copySpec.into(intoJsonNode.asText());
								}

								copySpec.setIncludeEmptyDirs(false);
							});
					});
			});
	}

	private Map<String, JsonNode> _configureClientExtensionJsonNodes(
		Project project,
		TaskProvider<CreateClientExtensionConfigTask>
			createClientExtensionConfigTaskProvider) {

		Map<String, JsonNode> profileJsonNodes = new HashMap<>();

		File clientExtensionYamlFile = project.file(_CLIENT_EXTENSION_YAML);

		JsonNode rootJsonNode = _getJsonNode(clientExtensionYamlFile);

		profileJsonNodes.put("default", rootJsonNode);

		File parentFile = clientExtensionYamlFile.getParentFile();

		for (File file : Objects.requireNonNull(parentFile.listFiles())) {
			Matcher matcher = _overrideClientExtensionYamlPattern.matcher(
				file.getName());

			if (!matcher.find()) {
				continue;
			}

			String profileName = matcher.group(1);

			if (Objects.equals(profileName, "default")) {
				Logger logger = project.getLogger();

				if (logger.isWarnEnabled()) {
					logger.warn(
						"Ignoring client-extension.default.yaml because " +
							"\"default\" is a reserved profile name.");
				}

				continue;
			}

			_configureDeployProfileTask(
				project, clientExtensionYamlFile, file, profileName);

			JsonNode jsonNode = rootJsonNode.deepCopy();

			_overrideJsonNodeValues(jsonNode, _getJsonNode(file));

			profileJsonNodes.put(profileName, jsonNode);

			createClientExtensionConfigTaskProvider.configure(
				task -> {
					TaskInputs taskInputs = task.getInputs();

					taskInputs.file(file);
				});
		}

		return profileJsonNodes;
	}

	private void _configureClientExtensionTasks(
		Project project, TaskProvider<Copy> assembleClientExtensionTaskProvider,
		TaskProvider<Zip> buildClientExtensionZipTaskProvider,
		TaskProvider<CreateClientExtensionConfigTask>
			createClientExtensionConfigTaskProvider,
		TaskProvider<DefaultTask> validateClientExtensionIdsTaskProvider) {

		createClientExtensionConfigTaskProvider.configure(
			createClientExtensionConfigTask -> {
				createClientExtensionConfigTask.dependsOn(
					ASSEMBLE_CLIENT_EXTENSION_TASK_NAME);
				createClientExtensionConfigTask.dependsOn(
					VALIDATE_CLIENT_EXTENSION_IDS_TASK_NAME);

				TaskInputs taskInputs =
					createClientExtensionConfigTask.getInputs();

				taskInputs.file(project.file(_CLIENT_EXTENSION_YAML));

				createClientExtensionConfigTask.addClientExtensionProperties(
					_getClientExtensionProperties());
			});

		File clientExtensionBuildDir = new File(
			project.getBuildDir(), CLIENT_EXTENSION_BUILD_DIR);

		assembleClientExtensionTaskProvider.configure(
			copy -> copy.into(clientExtensionBuildDir));

		buildClientExtensionZipTaskProvider.configure(
			zip -> {
				zip.dependsOn(CREATE_CLIENT_EXTENSION_CONFIG_TASK_NAME);

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

				zip.from(clientExtensionBuildDir);
				zip.include("**/*");
			});

		validateClientExtensionIdsTaskProvider.configure(
			validateClientExtensionIdsTask -> {
				validateClientExtensionIdsTask.setDescription(
					"Validates that this project's client extension IDs are " +
						"unique among all projects.");
				validateClientExtensionIdsTask.setGroup(
					LifecycleBasePlugin.VERIFICATION_GROUP);

				validateClientExtensionIdsTask.doFirst(
					validateClientExtensionIdsTask1 -> {
						StringBundler sb = new StringBundler();

						File rootDir = project.getRootDir();

						Path rootDirPath = rootDir.toPath();

						for (Map.Entry<String, Set<Project>> entry :
								_clientExtensionIds.entrySet()) {

							Set<Project> projects = entry.getValue();

							if ((projects.size() > 1) &&
								projects.contains(project)) {

								sb.append("Duplicate client extension ID \"");
								sb.append(entry.getKey());
								sb.append("\" found in these projects:\n");

								for (Project curProject : projects) {
									File projectDir =
										curProject.getProjectDir();

									sb.append(
										rootDirPath.relativize(
											projectDir.toPath()));

									sb.append(StringPool.NEW_LINE);
								}

								sb.append(StringPool.NEW_LINE);
							}
						}

						if (sb.length() > 0) {
							throw new GradleException(sb.toString());
						}
					});
			});
	}

	private void _configureConfigurationDefault(Project project) {
		Configuration defaultConfiguration = GradleUtil.getConfiguration(
			project, Dependency.DEFAULT_CONFIGURATION);

		Configuration archivesConfiguration = GradleUtil.getConfiguration(
			project, Dependency.ARCHIVES_CONFIGURATION);

		defaultConfiguration.extendsFrom(archivesConfiguration);
	}

	private void _configureDeployProfileTask(
		Project project, File clientExtensionYamlFile,
		File overrideClientExtensionYamlFile, String profileName) {

		TaskProvider<Task> deployProfileTaskProvider =
			GradleUtil.addTaskProvider(
				project, "deploy" + StringUtil.capitalize(profileName),
				Task.class);

		deployProfileTaskProvider.configure(
			new Action<Task>() {

				@Override
				public void execute(Task deployProfileTask) {
					GradleUtil.setProperty(project, "profileName", profileName);

					deployProfileTask.finalizedBy("deploy");
					deployProfileTask.setDescription(
						"Assembles the project and deploys it to Liferay " +
							"with the \"" + profileName + "\" client " +
								"extension profile.");
					deployProfileTask.setGroup(BasePlugin.BUILD_GROUP);

					TaskInputs taskInputs = deployProfileTask.getInputs();

					taskInputs.files(
						clientExtensionYamlFile,
						overrideClientExtensionYamlFile);
				}

			});
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

				public void doCall(CopySpec copySpec) {
					Project project = assembleTask.getProject();

					ConfigurableFileCollection configurableFileCollection =
						project.files(buildClientExtensionZipTaskProvider);

					configurableFileCollection.builtBy(assembleTask);

					copySpec.from(buildClientExtensionZipTaskProvider);
				}

			});
	}

	private void _configureTaskCheck(Project project) {
		Task checkTask = GradleUtil.getTask(
			project, LifecycleBasePlugin.CHECK_TASK_NAME);

		checkTask.dependsOn(VALIDATE_CLIENT_EXTENSION_IDS_TASK_NAME);
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

	private String _getClassification(String id, String type) {
		Properties clientExtensionProperties = _getClientExtensionProperties();

		String classification = clientExtensionProperties.getProperty(
			type + ".classification");

		if (classification != null) {
			return classification;
		}

		throw new GradleException(
			StringBundler.concat(
				"Client extension ", id, " with type ", type,
				" is of unkown classification"));
	}

	private Properties _getClientExtensionProperties() {
		if (_clientExtensionProperties == null) {
			try {
				Properties properties = new Properties();

				properties.load(
					ClientExtension.class.getResourceAsStream(
						"client-extension.properties"));

				return _clientExtensionProperties = properties;
			}
			catch (Exception exception) {
				throw new GradleException(
					"Unable to parse client-extension.properties file",
					exception);
			}
		}

		return _clientExtensionProperties;
	}

	private String _getDockerImageId(Project project) {
		String propertyName = "imageId";

		if (project.hasProperty(propertyName)) {
			Object property = project.property(propertyName);

			return property.toString();
		}

		return project.getName() + ":latest";
	}

	private JsonNode _getJsonNode(File file) {
		if (!file.exists()) {
			return _yamlObjectMapper.createObjectNode();
		}

		try (FileReader fileReader = new FileReader(file)) {
			return _yamlObjectMapper.readTree(file);
		}
		catch (IOException ioException) {
			throw new GradleException(
				StringBundler.concat("Unable to parse ", file.getName(), "."),
				ioException);
		}
	}

	private File _getZipFile(Project project) {
		return project.file(
			"dist/" + GradleUtil.getArchivesBaseName(project) + ".zip");
	}

	private boolean _isActiveProfile(Project project, String profileName) {
		if (Objects.equals(
				profileName,
				GradleUtil.getProperty(project, "profileName", "default"))) {

			return true;
		}

		return false;
	}

	private void _overrideJsonNodeValues(
		JsonNode baseJsonNode, JsonNode overrideJsonNode) {

		if (overrideJsonNode.isEmpty()) {
			return;
		}

		Iterator<String> iterator = overrideJsonNode.fieldNames();

		while (iterator.hasNext()) {
			String fieldName = iterator.next();

			JsonNode fieldNameBaseJsonNode = baseJsonNode.path(fieldName);

			JsonNode fieldNameOverrideJsonNode = overrideJsonNode.path(
				fieldName);

			if (fieldNameOverrideJsonNode.isMissingNode()) {
				continue;
			}

			if (fieldNameBaseJsonNode.isObject()) {
				_overrideJsonNodeValues(
					fieldNameBaseJsonNode, fieldNameOverrideJsonNode);

				continue;
			}

			ObjectNode baseObjectNode = (ObjectNode)baseJsonNode;

			if (fieldNameBaseJsonNode.isMissingNode()) {
				baseObjectNode.set(fieldName, fieldNameOverrideJsonNode);

				continue;
			}

			baseObjectNode.replace(fieldName, fieldNameOverrideJsonNode);
		}
	}

	private void _validateClientExtension(ClientExtension clientExtension) {
		if (Objects.equals(clientExtension.type, "batch")) {
			if (!clientExtension.typeSettings.containsKey(
					"oAuthApplicationHeadlessServer")) {

				throw new GradleException(
					StringBundler.concat(
						"Client extension ", clientExtension.id, " with type ",
						clientExtension.type, " must define the property ",
						"\"oAuthApplicationHeadlessServer\""));
			}
		}
		else if (Objects.equals(clientExtension.type, "instanceSettings")) {
			if (!clientExtension.typeSettings.containsKey("pid")) {
				throw new GradleException(
					StringBundler.concat(
						"Client extension ", clientExtension.id, " with type ",
						clientExtension.type,
						" must define the property \"pid\""));
			}
		}
	}

	private static final String _CLIENT_EXTENSION_YAML =
		"client-extension.yaml";

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private static final Pattern _overrideClientExtensionYamlPattern =
		Pattern.compile("^client-extension\\.([a-z]+)\\.yaml$");

	private final Map<String, Set<Project>> _clientExtensionIds =
		new HashMap<>();
	private Properties _clientExtensionProperties;
	private final boolean _defaultRepositoryEnabled;
	private final NodeBuildConfigurer _nodeBuildConfigurer =
		new NodeBuildConfigurer();
	private final ThemeCSSTypeConfigurer _themeCSSTypeConfigurer =
		new ThemeCSSTypeConfigurer();
	private final ObjectMapper _yamlObjectMapper = new ObjectMapper(
		new YAMLFactory());

}