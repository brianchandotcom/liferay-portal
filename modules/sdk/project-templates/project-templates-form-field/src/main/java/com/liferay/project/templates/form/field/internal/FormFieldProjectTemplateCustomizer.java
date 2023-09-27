/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.form.field.internal;

import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.extensions.util.WorkspaceUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Renato Rego
 */
public class FormFieldProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public String getTemplateName() {
		return "form-field";
	}

	@Override
	public void onAfterGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs, File destinationDir,
			ArchetypeGenerationResult archetypeGenerationResult)
		throws Exception {

		String liferayVersion = projectTemplatesArgs.getLiferayVersion();

		List<String> fileNames = new ArrayList<>();

		String name = projectTemplatesArgs.getName();

		if (liferayVersion.startsWith("7.0")) {
			fileNames.add(".babelrc");
			fileNames.add(".npmbundlerrc");
			fileNames.add("package.json");
			fileNames.add(
				"src/main/resources/META-INF/resources/" + name + ".es.js");
		}

		if (!(liferayVersion.startsWith("7.0") ||
			  liferayVersion.startsWith("7.1"))) {

			fileNames.add("src/main/resources/META-INF/resources/config.js");
			fileNames.add(
				"src/main/resources/META-INF/resources/" + name + "_field.js");

			String className = projectTemplatesArgs.getClassName();
			String packageName = projectTemplatesArgs.getPackageName();

			fileNames.add(
				"src/main/java/" + packageName.replaceAll("[.]", "/") +
					"/form/field/" + className + "DDMFormFieldRenderer.java");

			if (!liferayVersion.startsWith("7.2") &&
				_isReactFramework(
					(FormFieldProjectTemplatesArgs)
						projectTemplatesArgs.getProjectTemplatesArgsExt())) {

				fileNames.add(
					"src/main/resources/META-INF/resources/" + name + ".soy");
				fileNames.add(
					"src/main/resources/META-INF/resources/" + name +
						"Register.soy");
			}

			Path projectPath = Paths.get(destinationDir.getPath(), name);

			if (Files.notExists(projectPath)) {
				return;
			}

			File workspaceDir = WorkspaceUtil.getWorkspaceDir(destinationDir);

			Path workspacePath = workspaceDir.toPath();

			Path gradlePropertiesPath = workspacePath.resolve(
				"gradle.properties");

			if (Files.notExists(gradlePropertiesPath) ||
				Files.isDirectory(gradlePropertiesPath)) {

				return;
			}

			try (InputStream gradlePropertiesInputStream = Files.newInputStream(
					gradlePropertiesPath, StandardOpenOption.READ)) {

				Properties gradleProperties = new Properties();

				gradleProperties.load(gradlePropertiesInputStream);

				String nodeManager = gradleProperties.getProperty(
					"liferay.workspace.node.package.manager");

				if (Validator.isNull(nodeManager) ||
					nodeManager.equals("yarn")) {

					Path projectRelativizePath = workspacePath.relativize(
						projectPath);

					StringBuilder sb = new StringBuilder("../");

					for (int i = 0;
						 i < (projectRelativizePath.getNameCount() - 1); i++) {

						sb.append("../");
					}

					_updateNodeModulesPath(projectPath, sb.toString());
				}
				else if (nodeManager.equals("npm")) {
					_updateNodeModulesPath(projectPath, "./");
				}
			}
		}
		else {
			fileNames.add(
				"src/main/resources/META-INF/resources/" + name +
					"Register.soy");
		}

		Path destinationDirPath = destinationDir.toPath();

		Path projectDirPath = destinationDirPath.resolve(name);

		for (String fileName : fileNames) {
			ProjectTemplateCustomizer.deleteFileInPath(
				fileName, projectDirPath);
		}
	}

	@Override
	public void onBeforeGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs,
			ArchetypeGenerationRequest archetypeGenerationRequest)
		throws Exception {

		setProperty(
			archetypeGenerationRequest.getProperties(), "reactTemplate",
			String.valueOf(
				_isReactFramework(
					(FormFieldProjectTemplatesArgs)
						projectTemplatesArgs.getProjectTemplatesArgsExt())));
	}

	private boolean _isReactFramework(
		FormFieldProjectTemplatesArgs formFieldProjectTemplatesArgs) {

		String jsFramework = formFieldProjectTemplatesArgs.getJSFramework();

		if (jsFramework == null) {
			return false;
		}

		return jsFramework.equals("react");
	}

	private void _updateNodeModulesPath(
			Path projectPath, String nodeModulesPath)
		throws IOException {

		Path packageJSONPath = projectPath.resolve("package.json");

		if (Files.exists(packageJSONPath)) {
			String json = new String(
				Files.readAllBytes(packageJSONPath), StandardCharsets.UTF_8);

			json = json.replaceAll(
				"../../node_modules/", nodeModulesPath + "node_modules/");

			FileUtils.writeStringToFile(
				packageJSONPath.toFile(), json, "UTF-8", false);
		}
	}

}