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

package com.liferay.gradle.plugins.workspace.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.gradle.plugins.workspace.internal.client.extension.ClientExtension;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.internal.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.file.RegularFile;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskOutputs;

/**
 * @author Gregory Amerson
 */
public class CreateClientExtensionConfigTask extends DefaultTask {

	public CreateClientExtensionConfigTask() {
		Project project = getProject();

		_clientExtensionConfigFile = _addTaskOutputFile(
			project.getName() + _CLIENT_EXTENSION_CONFIG_FILE_NAME);

		_dockerFile = _addTaskOutputFile("Dockerfile");
		_lcpJsonFile = _addTaskOutputFile("LCP.json");
	}

	public void addClientExtension(ClientExtension clientExtension) {
		_clientExtensions.add(clientExtension);
	}

	@TaskAction
	public void createClientExtensionConfig() {
		Project project = getProject();

		File outputDockerFile = getDockerFile();

		try {
			String dockerFileContent = _getFileContentFromProject(
				project, "Dockerfile");

			if (dockerFileContent == null) {
				dockerFileContent = _loadTemplate(
					"templates/" + _type + "/Dockerfile.tpl",
					Collections.emptyMap());
			}

			if (dockerFileContent == null) {
				throw new GradleException("Dockerfile not specified");
			}

			Files.write(
				outputDockerFile.toPath(), dockerFileContent.getBytes());
		}
		catch (IOException ioException) {
			throw new GradleException(ioException.getMessage(), ioException);
		}

		File outputLcpJsonFile = getLcpJsonFile();

		try {
			String lcpJsonContent = _getFileContentFromProject(
				project, "LCP.json");

			if (lcpJsonContent == null) {
				lcpJsonContent = _loadTemplate(
					"templates/" + _type + "/LCP.json.tpl",
					Collections.singletonMap(
						"__CLIENT_EXTENSION_ID__",
						StringUtil.toAlphaNumericLowerCase(project.getName())));
			}

			if (lcpJsonContent == null) {
				throw new GradleException("LCP.json not specified");
			}

			Files.write(outputLcpJsonFile.toPath(), lcpJsonContent.getBytes());
		}
		catch (IOException ioException) {
			throw new GradleException(ioException.getMessage(), ioException);
		}

		File clientExtensionConfigFile = getClientExtensionConfigFile();

		Map<String, Object> jsonMap = new HashMap<>();

		_clientExtensions.forEach(
			clientExtension -> {
				try {
					jsonMap.putAll(clientExtension.toJSONMap());
				}
				catch (Exception exception) {
					throw new GradleException(
						exception.getMessage(), exception);
				}
			});

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

	public File getClientExtensionConfigFile() {
		return GradleUtil.toFile(getProject(), _clientExtensionConfigFile);
	}

	public Set<ClientExtension> getClientExtensions() {
		return _clientExtensions;
	}

	public File getDockerFile() {
		return GradleUtil.toFile(getProject(), _dockerFile);
	}

	public File getLcpJsonFile() {
		return GradleUtil.toFile(getProject(), _lcpJsonFile);
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

	private Provider<RegularFile> _addTaskOutputFile(String path) {
		Project project = getProject();

		ProjectLayout projectLayout = project.getLayout();

		DirectoryProperty buildDirectoryProperty =
			projectLayout.getBuildDirectory();

		Provider<RegularFile> buildFileProvider = buildDirectoryProperty.file(
			path);

		TaskOutputs taskOutputs = getOutputs();

		taskOutputs.files(buildFileProvider);

		return buildFileProvider;
	}

	private String _getFileContentFromProject(Project project, String path) {
		File file = project.file(path);

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

	private String _loadTemplate(
		String name, Map<String, String> substitutionMap) {

		try (InputStream inputStream =
				CreateClientExtensionConfigTask.class.getResourceAsStream(
					"dependencies/" + name)) {

			Set<Map.Entry<String, String>> entrySet =
				substitutionMap.entrySet();

			Stream<Map.Entry<String, String>> substitutions = entrySet.stream();

			return substitutions.map(
				entry -> (Function<String, String>)s -> s.replace(
					entry.getKey(), entry.getValue())
			).reduce(
				Function::andThen
			).orElse(
				Function.identity()
			).apply(
				StringUtil.read(inputStream)
			);
		}
		catch (Exception exception) {
			return null;
		}
	}

	private static final String _CLIENT_EXTENSION_CONFIG_FILE_NAME =
		".client-extension-config.json";

	private final Object _clientExtensionConfigFile;
	private final Set<ClientExtension> _clientExtensions =
		new LinkedHashSet<>();
	private Object _dockerFile;
	private Object _lcpJsonFile;
	private String _type = "static";

}