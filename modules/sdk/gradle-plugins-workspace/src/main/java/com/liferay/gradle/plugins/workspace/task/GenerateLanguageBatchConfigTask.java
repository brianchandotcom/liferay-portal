/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Thiago Buarque
 */
public class GenerateLanguageBatchConfigTask extends DefaultTask {

	public GenerateLanguageBatchConfigTask() {
		Project project = getProject();

		ObjectFactory objectFactory = project.getObjects();

		_batchEngineDataRegularFileProperty = objectFactory.fileProperty();

		ProjectLayout projectLayout = project.getLayout();

		DirectoryProperty buildDirectory = projectLayout.getBuildDirectory();

		_batchEngineDataRegularFileProperty.convention(
			buildDirectory.file("language.batch-engine-data.json"));

		_languageFilesConfigurableFileTree = objectFactory.fileTree();

		_languageFilesConfigurableFileTree.include("Language_*.properties");
		_languageFilesConfigurableFileTree.setDir(project.file("lang"));
	}

	@TaskAction
	public void convertPropertiesFilesToBatchFiles() throws IOException {
		JsonNode rootJsonNode = _objectMapper.readTree(
			GenerateLanguageBatchConfigTask.class.getResourceAsStream(
				"dependencies/templates/language" +
					"/language-batch-engine-data.json"));

		ArrayNode itemsArrayNode = (ArrayNode)rootJsonNode.get("items");

		try {
			ConfigurableFileTree languageFiles = getLanguageFiles();

			for (File file : languageFiles.getFiles()) {
				String name = file.getName();

				String languageId = name.substring(
					"Language_".length(), name.lastIndexOf(".properties"));

				if (Objects.equals(languageId, "en")) {
					languageId = "en_US";
				}

				Properties properties = new Properties();

				properties.load(Files.newBufferedReader(file.toPath()));

				for (Map.Entry<Object, Object> entry : properties.entrySet()) {
					String value = (String)entry.getValue();

					if (value.endsWith("(Automatic Copy)")) {
						continue;
					}

					ObjectNode objectNode = _objectMapper.createObjectNode();

					objectNode.put("key", (String)entry.getKey());
					objectNode.put("languageId", languageId);
					objectNode.put("value", value);

					itemsArrayNode.add(objectNode);
				}
			}

			RegularFileProperty batchEngineDataRegularFileProperty =
				getBatchEngineDataFile();

			RegularFile batchEngineDataRegularFile =
				batchEngineDataRegularFileProperty.get();

			File batchEngineDataFile = batchEngineDataRegularFile.getAsFile();

			ObjectWriter objectWriter = _objectMapper.writer();

			Files.write(
				batchEngineDataFile.toPath(),
				objectWriter.writeValueAsBytes(rootJsonNode));
		}
		catch (Exception exception) {
			throw new GradleException(
				"Could not convert language files to batch", exception);
		}
	}

	@OutputFile
	public RegularFileProperty getBatchEngineDataFile() {
		return _batchEngineDataRegularFileProperty;
	}

	@InputFiles
	public ConfigurableFileTree getLanguageFiles() {
		return _languageFilesConfigurableFileTree;
	}

	private final RegularFileProperty _batchEngineDataRegularFileProperty;
	private final ConfigurableFileTree _languageFilesConfigurableFileTree;
	private final ObjectMapper _objectMapper = new ObjectMapper();

}