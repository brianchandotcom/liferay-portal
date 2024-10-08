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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Thiago Buarque
 */
public class WriteLanguageBatchEngineDataTask extends DefaultTask {

	@InputFiles
	@PathSensitive(PathSensitivity.RELATIVE)
	public FileCollection getLanguageFiles() {
		Project project = getProject();

		Map<String, Object> args = new HashMap<>();

		args.put("dir", project.getProjectDir());
		args.put("include", "Language_*.properties");

		return project.fileTree(args);
	}

	@OutputFile
	public File getOutputFile() {
		Project project = getProject();

		return new File(
			project.getBuildDir(), "language.batch-engine-data.json");
	}

	@TaskAction
	public void writeLanguageBatchEngineData() throws IOException {
		JsonNode rootJsonNode = _objectMapper.readTree(
			WriteLanguageBatchEngineDataTask.class.getResourceAsStream(
				"dependencies/templates/language" +
					"/language.batch-engine-data.json"));

		ArrayNode arrayNode = (ArrayNode)rootJsonNode.get("items");

		FileCollection languageFiles = getLanguageFiles();

		for (File file : languageFiles.getFiles()) {
			String fileName = file.getName();

			String languageId = fileName.substring(
				"Language_".length(), fileName.lastIndexOf(".properties"));

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

				arrayNode.add(objectNode);
			}
		}

		File outputFile = getOutputFile();
		ObjectWriter objectWriter = _objectMapper.writer();

		Files.write(
			outputFile.toPath(), objectWriter.writeValueAsBytes(rootJsonNode));
	}

	private final ObjectMapper _objectMapper = new ObjectMapper();

}