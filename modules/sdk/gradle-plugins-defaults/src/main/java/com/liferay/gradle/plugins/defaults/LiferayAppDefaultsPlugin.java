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

package com.liferay.gradle.plugins.defaults;

import com.liferay.gradle.plugins.app.javadoc.builder.AppJavadocBuilderExtension;
import com.liferay.gradle.plugins.app.javadoc.builder.AppJavadocBuilderPlugin;
import com.liferay.gradle.plugins.defaults.internal.LiferayRelengPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.tasks.WritePropertiesTask;

import groovy.lang.Closure;

import java.io.File;

import java.util.Properties;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayAppDefaultsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		String appDescription = null;
		String appTitle = null;
		String appVersion = null;

		File appBndFile = project.file("app.bnd");

		if (appBndFile.exists()) {
			Properties properties = GUtil.loadProperties(appBndFile);

			appDescription = properties.getProperty(
				"Liferay-Releng-App-Description");
		}

		File relengDir = LiferayRelengPlugin.getRelengDir(project);

		if (relengDir != null) {
			File appPropertiesFile = new File(relengDir, "app.properties");

			if (appPropertiesFile.exists()) {
				Properties properties = GUtil.loadProperties(appPropertiesFile);

				appTitle = properties.getProperty("app.marketplace.title");
				appVersion = properties.getProperty("app.marketplace.version");
			}
		}

		GradleUtil.applyPlugin(project, AppJavadocBuilderPlugin.class);

		_configureAppJavadocBuilder(project);
		_configureProject(project, appDescription, appVersion);
		_configureTaskAppJavadoc(project, appTitle, appVersion);
	}

	private void _configureAppJavadocBuilder(Project project) {
		AppJavadocBuilderExtension appJavadocBuilderExtension =
			GradleUtil.getExtension(project, AppJavadocBuilderExtension.class);

		appJavadocBuilderExtension.setGroupNameClosure(
			new Closure<String>(project) {

				@SuppressWarnings("unused")
				public String doCall(Project subproject) {
					return _getAppJavadocGroupName(subproject);
				}

			});
	}

	private void _configureProject(
		Project project, String description, String version) {

		if (Validator.isNotNull(description)) {
			project.setDescription(description);
		}

		if (Validator.isNotNull(version)) {
			project.setVersion(version);
		}
	}

	private void _configureTaskAppJavadoc(
		Project project, String appTitle, String appVersion) {

		if (Validator.isNull(appTitle) || Validator.isNull(appVersion)) {
			return;
		}

		Javadoc javadoc = (Javadoc)GradleUtil.getTask(
			project, AppJavadocBuilderPlugin.APP_JAVADOC_TASK_NAME);

		String title = String.format("%s %s API", appTitle, appVersion);

		javadoc.setTitle(title);
	}

	private String _getAppJavadocGroupName(Project project) {
		String groupName = project.getDescription();

		if (Validator.isNull(groupName)) {
			groupName = project.getName();
		}

		TaskContainer taskContainer = project.getTasks();

		WritePropertiesTask recordArtifactTask =
			(WritePropertiesTask)taskContainer.findByName(
				LiferayRelengPlugin.RECORD_ARTIFACT_TASK_NAME);

		if (recordArtifactTask != null) {
			String artifactURL = null;

			File artifactPropertiesFile =
				recordArtifactTask.getPropertiesFile();

			if (artifactPropertiesFile.exists()) {
				Properties properties = GUtil.loadProperties(
					artifactPropertiesFile);

				artifactURL = properties.getProperty("artifact.url");
			}

			if (Validator.isNotNull(artifactURL)) {
				int start = artifactURL.lastIndexOf('/') + 1;
				int end = artifactURL.lastIndexOf('.');

				int pos = artifactURL.indexOf('-', start);

				String moduleName = artifactURL.substring(start, pos);
				String moduleVersion = artifactURL.substring(pos + 1, end);

				groupName =
					moduleName + ' ' + moduleVersion + " - " + groupName;
			}
		}

		return groupName;
	}

}