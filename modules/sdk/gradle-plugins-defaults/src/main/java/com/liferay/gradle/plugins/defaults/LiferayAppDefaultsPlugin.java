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
import com.liferay.gradle.plugins.defaults.tasks.WritePropertiesTask;
import com.liferay.gradle.plugins.tlddoc.builder.AppTLDDocBuilderPlugin;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.Properties;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.external.javadoc.StandardJavadocDocletOptions;
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

		_applyPlugins(project);

		LiferayOSGiDefaultsPlugin.configureRepositories(project);

		configureAppJavadocBuilder(project);
		configureProject(project, appDescription, appVersion);
		configureTaskAppJavadoc(project, appTitle, appVersion);
	}

	protected void configureAppJavadocBuilder(Project project) {
		AppJavadocBuilderExtension appJavadocBuilderExtension =
			GradleUtil.getExtension(project, AppJavadocBuilderExtension.class);

		appJavadocBuilderExtension.onlyIf(
			new Spec<Project>() {

				@Override
				public boolean isSatisfiedBy(Project project) {
					TaskContainer taskContainer = project.getTasks();

					WritePropertiesTask recordArtifactTask =
						(WritePropertiesTask)taskContainer.findByName(
							LiferayRelengPlugin.RECORD_ARTIFACT_TASK_NAME);

					if (recordArtifactTask != null) {
						File outputFile = recordArtifactTask.getOutputFile();

						if (outputFile.exists()) {
							return true;
						}
					}

					return false;
				}

			});

		appJavadocBuilderExtension.setGroupNameClosure(
			new Closure<String>(project) {

				@SuppressWarnings("unused")
				public String doCall(Project subproject) {
					return getAppJavadocGroupName(subproject);
				}

			});
	}

	protected void configureProject(
		Project project, String description, String version) {

		if (Validator.isNotNull(description)) {
			project.setDescription(description);
		}

		if (Validator.isNotNull(version)) {
			project.setVersion(version);
		}
	}

	protected void configureTaskAppJavadoc(
		Project project, String appTitle, String appVersion) {

		Javadoc javadoc = (Javadoc)GradleUtil.getTask(
			project, AppJavadocBuilderPlugin.APP_JAVADOC_TASK_NAME);

		File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");

		if (portalRootDir != null) {
			File stylesheetFile = new File(
				portalRootDir, "tools/styles/javadoc.css");

			if (stylesheetFile.exists()) {
				StandardJavadocDocletOptions standardJavadocDocletOptions =
					(StandardJavadocDocletOptions)javadoc.getOptions();

				standardJavadocDocletOptions.setStylesheetFile(stylesheetFile);
			}
		}

		if (Validator.isNotNull(appTitle) && Validator.isNotNull(appVersion)) {
			String title = String.format("%s %s API", appTitle, appVersion);

			javadoc.setTitle(title);
		}
	}

	protected String getAppJavadocGroupName(Project project) {
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

			File artifactPropertiesFile = recordArtifactTask.getOutputFile();

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

				StringBuilder sb = new StringBuilder();

				sb.append("Module ");
				sb.append(moduleName);
				sb.append(' ');
				sb.append(moduleVersion);
				sb.append(" - ");
				sb.append(groupName);

				groupName = sb.toString();
			}
		}

		return groupName;
	}

	private void _applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, AppJavadocBuilderPlugin.class);
		GradleUtil.applyPlugin(project, AppTLDDocBuilderPlugin.class);
	}

}