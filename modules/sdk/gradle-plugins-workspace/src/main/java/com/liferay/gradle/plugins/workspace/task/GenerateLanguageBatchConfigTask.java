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

import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.liferay.gradle.plugins.workspace.internal.util.StringUtil;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.Directory;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.file.RegularFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskOutputs;

/**
 * @author Thiago Buarque
 */
public class GenerateLanguageBatchConfigTask extends DefaultTask {

	public GenerateLanguageBatchConfigTask() {
		ProjectLayout projectLayout = _project.getLayout();

		Directory projectDirectory = projectLayout.getProjectDirectory();

		File projectDir = _project.getProjectDir();

		Path buildFilePath = Paths.get(projectDir.getPath(), _OUTPUT_FILE_PATH);

		RegularFile regularFile = projectDirectory.file(
			buildFilePath.toString());

		TaskOutputs taskOutputs = getOutputs();

		taskOutputs.files(regularFile);

		_languageBatchEngineDataFile = regularFile;
	}

	@TaskAction
	public void convertPropertiesFilesToBatchFiles() throws IOException {
		Class<? extends GenerateLanguageBatchConfigTask> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/templates/language/language-batch-engine-data.json");

		JsonNode rootJsonNode = _objectMapper.readTree(inputStream);

		ArrayNode itemsArrayNode = (ArrayNode)rootJsonNode.get("items");

		try {
			for (File file : getLanguageFiles()) {
				String name = file.getName();

				name = name.replace(".properties", "");

				String languageId = name.replace("Language_", "");

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

			File languageBatchEngineDataFile = getLanguageBatchEngineDataFile();

			ObjectWriter objectWriter = _objectMapper.writer();

			Files.write(
				languageBatchEngineDataFile.toPath(),
				objectWriter.writeValueAsBytes(rootJsonNode));
		}
		catch (Exception exception) {
			throw new GradleException(
				"Could not convert language files to batch", exception);
		}
	}

	@InputFiles
	public Set<File> getLanguageFiles() {
		ConfigurableFileTree languageFileTree = _project.fileTree(".");

		languageFileTree.include("**/Language_*.properties");

		return languageFileTree.getFiles();
	}

	@OutputFile
	public File getLanguageBatchEngineDataFile() {
		return GradleUtil.toFile(_project, _languageBatchEngineDataFile);
	}

	private static final String _OUTPUT_FILE_PATH =
		"build/generated/language-batch-engine-data.json";

	private final Object _languageBatchEngineDataFile;
	private final ObjectMapper _objectMapper = new ObjectMapper();
	private final Project _project = getProject();

}