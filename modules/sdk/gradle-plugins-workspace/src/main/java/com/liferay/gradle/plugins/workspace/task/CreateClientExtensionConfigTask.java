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

import com.liferay.gradle.plugins.workspace.configurator.RootProjectConfigurator;
import com.liferay.gradle.plugins.workspace.internal.client.extension.ClientExtension;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.internal.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;

import java.util.Collections;
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
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskOutputs;

/**
 * @author Gregory Amerson
 */
public class CreateClientExtensionConfigTask extends DefaultTask {

	public CreateClientExtensionConfigTask() {
		Project project = getProject();

		ProjectLayout projectLayout = project.getLayout();

		DirectoryProperty buildDirectoryProperty =
			projectLayout.getBuildDirectory();

		_dockerFile = buildDirectoryProperty.file("Dockerfile");
		_lcpJsonFile = buildDirectoryProperty.file("LCP.json");

		TaskOutputs taskOutputs = getOutputs();

		taskOutputs.files(_dockerFile, _lcpJsonFile);
	}

	public void addClientExtension(ClientExtension clientExtension) {
		_clientExtensions.add(clientExtension);

		Project project = getProject();

		File clientExtensionConfigFile = new File(
			project.getBuildDir(),
			clientExtension.id + _CLIENT_EXTENSION_CONFIG_FILE_NAME);

		TaskOutputs taskOutputs = getOutputs();

		taskOutputs.file(clientExtensionConfigFile);
	}

	@TaskAction
	public void createClientExtensionConfig() {
		Project project = getProject();

		File outputDockerFile = getDockerFile();

		try {
			String dockerFileContent = _loadTemplate(
				_CLIENT_EXTENSION_DOCKERFILE + ".tpl", Collections.emptyMap());

			File inputDockerFile = project.file("Dockerfile");

			if (inputDockerFile.exists()) {
				dockerFileContent = new String(
					Files.readAllBytes(inputDockerFile.toPath()));
			}

			Files.write(
				outputDockerFile.toPath(), dockerFileContent.getBytes());
		}
		catch (IOException ioException) {
			throw new GradleException(ioException.getMessage(), ioException);
		}

		File outputLcpJsonFile = getLcpJsonFile();

		try {
			String lcpJsonContent = _loadTemplate(
				_CLIENT_EXTENSION_LCP_JSON + ".tpl",
				Collections.singletonMap(
					"__CLIENT_EXTENSION_ID__",
					StringUtil.toAlphaNumericLowerCase(project.getName())));

			File inputLcpJsonFile = project.file("LCP.json");

			if (inputLcpJsonFile.exists()) {
				lcpJsonContent = new String(
					Files.readAllBytes(inputLcpJsonFile.toPath()));
			}

			Files.write(outputLcpJsonFile.toPath(), lcpJsonContent.getBytes());
		}
		catch (IOException ioException) {
			throw new GradleException(ioException.getMessage(), ioException);
		}

		_clientExtensions.forEach(
			clientExtension -> {
				File clientExtensionConfigFile = new File(
					project.getBuildDir(),
					clientExtension.id + _CLIENT_EXTENSION_CONFIG_FILE_NAME);

				File parentFile = clientExtensionConfigFile.getParentFile();

				parentFile.mkdirs();

				try {
					String json = clientExtension.toJSON();

					Files.write(
						clientExtensionConfigFile.toPath(), json.getBytes());
				}
				catch (Exception exception) {
					throw new GradleException(
						exception.getMessage(), exception);
				}
			});
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

	public void setDockerFile(Object dockerFile) {
		_dockerFile = dockerFile;
	}

	public void setLcpJsonFile(Object lcpJsonFile) {
		_lcpJsonFile = lcpJsonFile;
	}

	private String _loadTemplate(
		String name, Map<String, String> substitutionMap) {

		try (InputStream inputStream =
				RootProjectConfigurator.class.getResourceAsStream(
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
			throw new GradleException(
				"Unable to read template " + name, exception);
		}
	}

	private static final String _CLIENT_EXTENSION_CONFIG_FILE_NAME =
		".client-extension-config.json";

	private static final String _CLIENT_EXTENSION_DOCKERFILE =
		"client_extension_Dockerfile";

	private static final String _CLIENT_EXTENSION_LCP_JSON =
		"client_extension_LCP.json";

	private final Set<ClientExtension> _clientExtensions =
		new LinkedHashSet<>();
	private Object _dockerFile;
	private Object _lcpJsonFile;

}