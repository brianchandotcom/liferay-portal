/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.task;

import aQute.bnd.osgi.Constants;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.google.common.collect.Sets;

import com.liferay.gradle.plugins.workspace.configurator.ClientExtensionProjectConfigurator;
import com.liferay.gradle.plugins.workspace.internal.client.extension.ClientExtension;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.internal.util.StringUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.file.RegularFile;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskOutputs;

/**
 * @author Gregory Amerson
 */
public class CreateClientExtensionConfigTask extends DefaultTask {

	public CreateClientExtensionConfigTask() {
		_clientExtensionConfigFile = _addTaskOutputFile(
			_project.getName() + _CLIENT_EXTENSION_CONFIG_FILE_NAME);

		_dockerFile = _addTaskOutputFile("Dockerfile");
		_lcpJsonFile = _addTaskOutputFile("LCP.json");
		_pluginPackagePropertiesFile = _addTaskOutputFile(
			_PLUGIN_PACKAGE_PROPERTIES_PATH);
	}

	public void addClientExtension(ClientExtension clientExtension) {
		_clientExtensions.add(clientExtension);
	}

	public void addClientExtensionProperties(
		Properties clientExtensionProperties) {

		_clientExtensionProperties = clientExtensionProperties;
	}

	@TaskAction
	public void createClientExtensionConfig() {
		Properties pluginPackageProperties = _getPluginPackageProperties();

		String classificationGrouping = _validateAndGetClassificationGrouping(
			_clientExtensions);

		Map<String, Object> jsonMap = new HashMap<>();

		jsonMap.put(":configurator:policy", "force");

		for (ClientExtension clientExtension : _clientExtensions) {
			if (Objects.equals(clientExtension.classification, "batch")) {
				pluginPackageProperties.put(
					"Liferay-Client-Extension-Batch", "batch/");
			}

			if (Objects.equals(clientExtension.classification, "frontend")) {
				_expandWildcards(clientExtension.typeSettings);

				pluginPackageProperties.put(
					"Liferay-Client-Extension-Frontend", "static/");
			}

			String pid = _clientExtensionProperties.getProperty(
				clientExtension.type + ".pid");

			if (Objects.equals(clientExtension.type, "instanceSettings")) {
				pid = clientExtension.typeSettings.remove("pid") + ".scoped";
			}

			if (pid != null) {
				jsonMap.putAll(clientExtension.toJSONMap(pid));
			}
		}

		Stream<ClientExtension> stream = _clientExtensions.stream();

		Map<String, String> substitutionMap = stream.flatMap(
			clientExtension -> {
				Set<Map.Entry<String, Object>> entrySet =
					clientExtension.typeSettings.entrySet();

				Stream<Map.Entry<String, Object>> entrySetStream =
					entrySet.stream();

				return entrySetStream.map(
					entry -> new AbstractMap.SimpleEntry<>(
						StringBundler.concat(
							"__", _getIdOrBatch(clientExtension), ".",
							entry.getKey(), "__"),
						String.valueOf(entry.getValue())));
			}
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);

		String projectId = StringUtil.toAlphaNumericLowerCase(
			_project.getName());

		substitutionMap.put("__PROJECT_ID__", projectId);

		pluginPackageProperties.put(Constants.BUNDLE_SYMBOLICNAME, projectId);

		if (!pluginPackageProperties.containsKey("module-group-id")) {
			pluginPackageProperties.put("module-group-id", "liferay");
		}

		pluginPackageProperties.put("name", _project.getName());

		_writeToOutputFile(
			classificationGrouping, getInputDockerfileFile(), getDockerFile(),
			substitutionMap);
		_writeToOutputFile(
			classificationGrouping, getInputLcpJsonFile(), getLcpJsonFile(),
			substitutionMap);

		_addRequiredDeploymentContexts(
			pluginPackageProperties, getLcpJsonFile());

		_storePluginPackageProperties(pluginPackageProperties);

		_createClientExtensionConfigFile(jsonMap);
	}

	@InputFiles
	public File getClientExtensionConfigFile() {
		return GradleUtil.toFile(_project, _clientExtensionConfigFile);
	}

	@InputFiles
	public File getDockerFile() {
		return GradleUtil.toFile(_project, _dockerFile);
	}

	@InputFiles
	public File getInputDockerfileFile() {
		return GradleUtil.toFile(_project, "Dockerfile");
	}

	@InputFiles
	public File getInputLcpJsonFile() {
		return GradleUtil.toFile(_project, "LCP.json");
	}

	@InputFiles
	public File getInputPluginPackagePropertiesFile() {
		return GradleUtil.toFile(_project, "liferay-plugin-package.properties");
	}

	@InputFiles
	public File getLcpJsonFile() {
		return GradleUtil.toFile(_project, _lcpJsonFile);
	}

	@InputFiles
	public File getPluginPackagePropertiesFile() {
		return GradleUtil.toFile(_project, _pluginPackagePropertiesFile);
	}

	@Input
	public String getType() {
		return _type;
	}

	public void setDockerFile(Object dockerFile) {
		_dockerFile = dockerFile;
	}

	public void setLcpJsonFile(Object lcpJsonFile) {
		_lcpJsonFile = lcpJsonFile;
	}

	public void setType(String type) {
		_type = type;
	}

	private void _addRequiredDeploymentContexts(
		Properties pluginPackageProperties, File lcpJsonFile) {

		try {
			JsonNode jsonNode = _objectMapper.readTree(lcpJsonFile);

			if (jsonNode.has("dependencies")) {
				List<String> dependencies = new ArrayList<>();

				for (JsonNode dependency : jsonNode.get("dependencies")) {
					dependencies.add(dependency.textValue());
				}

				pluginPackageProperties.put(
					"required-deployment-contexts",
					com.liferay.petra.string.StringUtil.merge(
						dependencies, StringPool.COMMA));
			}
		}
		catch (IOException ioException) {
			throw new GradleException(
				"Unable to parse " + lcpJsonFile.getName(), ioException);
		}
	}

	private Provider<RegularFile> _addTaskOutputFile(String path) {
		ProjectLayout projectLayout = _project.getLayout();

		DirectoryProperty buildDirectoryProperty =
			projectLayout.getBuildDirectory();

		Path buildFilePath = Paths.get(
			ClientExtensionProjectConfigurator.CLIENT_EXTENSION_BUILD_DIR,
			path);

		Provider<RegularFile> buildFileProvider = buildDirectoryProperty.file(
			buildFilePath.toString());

		TaskOutputs taskOutputs = getOutputs();

		taskOutputs.files(buildFileProvider);

		return buildFileProvider;
	}

	private void _createClientExtensionConfigFile(Map<String, Object> jsonMap) {
		File clientExtensionConfigFile = getClientExtensionConfigFile();

		try {
			ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.configure(
				SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

			ObjectWriter objectWriter =
				objectMapper.writerWithDefaultPrettyPrinter();

			String json = objectWriter.writeValueAsString(jsonMap);

			Files.write(clientExtensionConfigFile.toPath(), json.getBytes());
		}
		catch (Exception exception) {
			throw new GradleException(exception.getMessage(), exception);
		}
	}

	private void _expandWildcards(Map<String, Object> typeSettings) {
		File clientExtensionBuildDir = new File(
			_project.getBuildDir(),
			ClientExtensionProjectConfigurator.CLIENT_EXTENSION_BUILD_DIR);

		File staticDir = new File(clientExtensionBuildDir, "static");

		if (!staticDir.exists()) {
			return;
		}

		Path staticDirPath = staticDir.toPath();

		for (Map.Entry<String, Object> entry : typeSettings.entrySet()) {
			Object currentValue = entry.getValue();

			if ((currentValue instanceof String) &&
				_isWildcardValue((String)currentValue)) {

				entry.setValue(
					_getMatchingPaths(staticDirPath, (String)currentValue));
			}

			if (currentValue instanceof List) {
				List<String> values = new ArrayList<>();

				for (String value : (List<String>)currentValue) {
					if (_isWildcardValue(value)) {
						values.addAll(_getMatchingPaths(staticDirPath, value));
					}
					else {
						values.add(value);
					}
				}

				entry.setValue(values);
			}
		}
	}

	private String _getFileContent(File file) {
		if (file.exists()) {
			try {
				return new String(Files.readAllBytes(file.toPath()));
			}
			catch (IOException ioException) {
				throw new GradleException(
					ioException.getMessage(), ioException);
			}
		}

		return null;
	}

	private String _getIdOrBatch(ClientExtension clientExtension) {
		String id = clientExtension.id;

		if (Objects.equals(clientExtension.type, "batch")) {
			id = "batch";
		}

		return id;
	}

	private List<String> _getMatchingPaths(Path basePath, String glob) {
		FileSystem fileSystem = basePath.getFileSystem();

		PathMatcher pathMatcher = fileSystem.getPathMatcher("glob:" + glob);

		try (Stream<Path> files = Files.walk(basePath)) {
			List<String> matchingPaths = files.map(
				basePath::relativize
			).filter(
				pathMatcher::matches
			).map(
				String::valueOf
			).collect(
				Collectors.toList()
			);

			if (matchingPaths.isEmpty()) {
				throw new GradleException(
					"No paths matched the glob pattern \"" + glob + "\"");
			}

			Collections.sort(matchingPaths);

			return matchingPaths;
		}
		catch (IOException ioException) {
			throw new GradleException(
				"Unable to expand wildcard paths", ioException);
		}
	}

	private Properties _getPluginPackageProperties() {
		Properties pluginPackageProperties = new Properties();

		try {
			String pluginPackagePropertiesFileContent = _getFileContent(
				getInputPluginPackagePropertiesFile());

			if (pluginPackagePropertiesFileContent != null) {
				pluginPackageProperties.load(
					new StringReader(pluginPackagePropertiesFileContent));
			}
		}
		catch (IOException ioException) {
			throw new GradleException(ioException.getMessage(), ioException);
		}

		return pluginPackageProperties;
	}

	private boolean _isWildcardValue(String value) {
		if (value.contains(StringPool.STAR)) {
			return true;
		}

		return false;
	}

	private void _storePluginPackageProperties(
		Properties pluginPackageProperties) {

		File pluginPackagePropertiesFile = getPluginPackagePropertiesFile();

		try {
			File parentFile = pluginPackagePropertiesFile.getParentFile();

			parentFile.mkdirs();

			BufferedWriter bufferedWriter = Files.newBufferedWriter(
				pluginPackagePropertiesFile.toPath(),
				StandardOpenOption.CREATE);

			pluginPackageProperties.store(bufferedWriter, null);
		}
		catch (IOException ioException) {
			throw new GradleException(ioException.getMessage(), ioException);
		}
	}

	private String _validateAndGetClassificationGrouping(
		Set<ClientExtension> clientExtensions) {

		Set<String> classifications = new HashSet<>();

		clientExtensions.forEach(
			clientExtension -> classifications.add(
				clientExtension.classification));

		if (_groupConfiguration.containsAll(classifications)) {

			// Configuration must be first. The rest can be sorted.

			return "configuration";
		}

		if (_groupBatch.containsAll(classifications)) {
			Stream<ClientExtension> stream = clientExtensions.stream();

			List<ClientExtension> batches = stream.filter(
				clientExtension -> Objects.equals(clientExtension.type, "batch")
			).collect(
				Collectors.toList()
			);

			if (batches.size() > 1) {
				throw new GradleException(
					"A client extension project must not contain more than " +
						"one batch type client extension");
			}

			return "batch";
		}
		else if (_groupFrontend.containsAll(classifications)) {
			return "frontend";
		}
		else if (_groupMicroservice.containsAll(classifications)) {
			return "microservice";
		}
		else if (!classifications.isEmpty()) {
			throw new GradleException(
				StringBundler.concat(
					"The combination of client extensions in ", classifications,
					" cannot be grouped in a single project. The following ",
					"groupings are allowed: ", _groupBatch, _groupFrontend,
					_groupMicroservice));
		}

		return "frontend";
	}

	private void _writeToOutputFile(
		String classificationGrouping, File inputFile, File outputFile,
		Map<String, String> substitutionMap) {

		String templatePath = String.format(
			"dependencies/templates/%s/%s.tpl", classificationGrouping,
			inputFile.getName());

		try {
			InputStream inputStream1 = null;

			if (inputFile.exists()) {
				inputStream1 = new FileInputStream(inputFile);
			}
			else {
				inputStream1 =
					CreateClientExtensionConfigTask.class.getResourceAsStream(
						templatePath);
			}

			try (InputStream inputStream2 = inputStream1) {
				String fileContent = StringUtil.read(inputStream2);

				for (Map.Entry<String, String> entry :
						substitutionMap.entrySet()) {

					fileContent = fileContent.replace(
						entry.getKey(), entry.getValue());
				}

				Files.write(outputFile.toPath(), fileContent.getBytes());
			}
		}
		catch (IOException ioException) {
			throw new GradleException(inputFile.getName() + " not specified");
		}
	}

	private static final String _CLIENT_EXTENSION_CONFIG_FILE_NAME =
		".client-extension-config.json";

	private static final String _PLUGIN_PACKAGE_PROPERTIES_PATH =
		"WEB-INF/liferay-plugin-package.properties";

	private static final Set<String> _groupBatch = Sets.newHashSet(
		"batch", "configuration");
	private static final Set<String> _groupConfiguration = Sets.newHashSet(
		"configuration");
	private static final Set<String> _groupFrontend = Sets.newHashSet(
		"configuration", "frontend");
	private static final Set<String> _groupMicroservice = Sets.newHashSet(
		"configuration", "microservice");

	private final Object _clientExtensionConfigFile;
	private Properties _clientExtensionProperties;
	private final Set<ClientExtension> _clientExtensions = new HashSet<>();
	private Object _dockerFile;
	private Object _lcpJsonFile;
	private final ObjectMapper _objectMapper = new ObjectMapper();
	private final Object _pluginPackagePropertiesFile;
	private final Project _project = getProject();
	private String _type = "frontend";

}