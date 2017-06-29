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
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.defaults.tasks.WritePropertiesTask;
import com.liferay.gradle.plugins.tlddoc.builder.AppTLDDocBuilderExtension;
import com.liferay.gradle.plugins.tlddoc.builder.AppTLDDocBuilderPlugin;
import com.liferay.gradle.plugins.tlddoc.builder.tasks.TLDDocTask;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.List;
import java.util.Properties;

import org.gradle.StartParameter;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.internal.GradleInternal;
import org.gradle.api.internal.project.ProjectInternal;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.execution.ProjectConfigurer;
import org.gradle.external.javadoc.StandardJavadocDocletOptions;
import org.gradle.internal.service.ServiceRegistry;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayAppDefaultsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		String appDescription = GradleUtil.getProperty(
			project, "app.description", (String)null);
		String appTitle = GradleUtil.getProperty(
			project, "app.title", (String)null);
		String appVersion = GradleUtil.getProperty(
			project, "app.version", (String)null);

		if (Validator.isNull(appDescription)) {
			File appBndFile = project.file("app.bnd");

			if (appBndFile.exists()) {
				Properties properties = GUtil.loadProperties(appBndFile);

				appDescription = properties.getProperty(
					"Liferay-Releng-App-Description");
			}
		}

		Properties appProperties = null;

		Project privateProject = project.findProject(
			":private" + project.getPath());

		if (privateProject != null) {
			appProperties = _getAppProperties(privateProject);
		}

		if (appProperties == null) {
			appProperties = _getAppProperties(project);
		}

		if (appProperties != null) {
			if (Validator.isNull(appTitle)) {
				appTitle = appProperties.getProperty("app.marketplace.title");
			}

			if (Validator.isNull(appVersion)) {
				appVersion = appProperties.getProperty(
					"app.marketplace.version");
			}
		}

		_applyPlugins(project);

		File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");

		GradlePluginsDefaultsUtil.configureRepositories(project, portalRootDir);

		_configureAppJavadocBuilder(project, privateProject);
		_configureAppTLDDocBuilder(project, privateProject);
		_configureProject(project, appDescription, appVersion);
		_configureTaskAppJavadoc(project, portalRootDir, appTitle, appVersion);
		_configureTaskAppTlddoc(project, portalRootDir);

		if (privateProject != null) {
			Gradle gradle = project.getGradle();

			StartParameter startParameter = gradle.getStartParameter();

			List<String> taskNames = startParameter.getTaskNames();

			if (taskNames.contains(
					AppJavadocBuilderPlugin.APP_JAVADOC_TASK_NAME) ||
				taskNames.contains(
					AppJavadocBuilderPlugin.JAR_APP_JAVADOC_TASK_NAME) ||
				taskNames.contains(
					AppTLDDocBuilderPlugin.APP_TLDDOC_TASK_NAME) ||
				taskNames.contains(
					AppTLDDocBuilderPlugin.JAR_APP_TLDDOC_TASK_NAME)) {

				_forceProjectHierarchyEvaluation(privateProject);
			}
		}
	}

	private void _applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, AppJavadocBuilderPlugin.class);
		GradleUtil.applyPlugin(project, AppTLDDocBuilderPlugin.class);
	}

	private void _configureAppJavadocBuilder(
		Project project, Project privateProject) {

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
						File artifactPropertiesFile =
							recordArtifactTask.getOutputFile();

						if (artifactPropertiesFile.exists()) {
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
					return _getAppJavadocGroupName(subproject);
				}

			});

		if (privateProject != null) {
			appJavadocBuilderExtension.subprojects(
				privateProject.getSubprojects());
		}
	}

	private void _configureAppTLDDocBuilder(
		Project project, Project privateProject) {

		if (privateProject == null) {
			return;
		}

		AppTLDDocBuilderExtension appTLDDocBuilderExtension =
			GradleUtil.getExtension(project, AppTLDDocBuilderExtension.class);

		appTLDDocBuilderExtension.subprojects(privateProject.getSubprojects());
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
		Project project, File portalRootDir, String appTitle,
		String appVersion) {

		Javadoc javadoc = (Javadoc)GradleUtil.getTask(
			project, AppJavadocBuilderPlugin.APP_JAVADOC_TASK_NAME);

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

	private void _configureTaskAppTlddoc(Project project, File portalRootDir) {
		if (portalRootDir == null) {
			return;
		}

		TLDDocTask tlddocTask = (TLDDocTask)GradleUtil.getTask(
			project, AppTLDDocBuilderPlugin.APP_TLDDOC_TASK_NAME);

		File xsltDir = new File(portalRootDir, "tools/styles/taglibs");

		tlddocTask.setXsltDir(xsltDir);
	}

	private void _forceProjectHierarchyEvaluation(Project project) {
		GradleInternal gradleInternal = (GradleInternal)project.getGradle();

		ServiceRegistry serviceRegistry = gradleInternal.getServices();

		ProjectConfigurer projectConfigurer = serviceRegistry.get(
			ProjectConfigurer.class);

		projectConfigurer.configureHierarchy((ProjectInternal)project);
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

				sb.append(groupName);
				sb.append(" - com.liferay:");
				sb.append(moduleName);
				sb.append(':');
				sb.append(moduleVersion);

				groupName = sb.toString();
			}
		}

		return groupName;
	}

	private Properties _getAppProperties(Project project) {
		File relengDir = LiferayRelengPlugin.getRelengDir(project);

		if (relengDir != null) {
			File appPropertiesFile = new File(relengDir, "app.properties");

			if (appPropertiesFile.exists()) {
				return GUtil.loadProperties(appPropertiesFile);
			}
		}

		return null;
	}

}