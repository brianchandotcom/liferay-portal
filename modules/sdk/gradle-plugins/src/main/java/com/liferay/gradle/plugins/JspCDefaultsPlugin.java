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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.jasper.jspc.JspCExtension;
import com.liferay.gradle.plugins.jasper.jspc.JspCPlugin;
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.bundling.Jar;

/**
 * @author Andrea Di Giorgi
 */
public class JspCDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<JspCPlugin> {

	public static final String UNZIP_JAR_TASK_NAME = "unzipJar";

	protected void addDependenciesJspC(Project project, Copy unzipJarTask) {
		PluginContainer pluginContainer = project.getPlugins();

		if (!pluginContainer.hasPlugin(LiferayPlugin.class)) {
			return;
		}

		LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		GradleUtil.addDependency(
			project, JspCPlugin.CONFIGURATION_NAME,
			liferayExtension.getAppServerLibGlobalDir());

		FileTree fileTree = FileUtil.getJarsFileTree(
			project, liferayExtension.getAppServerLibGlobalDir());

		GradleUtil.addDependency(
			project, JspCPlugin.CONFIGURATION_NAME, fileTree);

		fileTree = FileUtil.getJarsFileTree(
			project,
			new File(liferayExtension.getAppServerPortalDir(), "WEB-INF/lib"));

		GradleUtil.addDependency(
			project, JspCPlugin.CONFIGURATION_NAME, fileTree);

		fileTree = FileUtil.getJarsFileTree(
			project,
			new File(liferayExtension.getLiferayHome(), "osgi/modules"));

		GradleUtil.addDependency(
			project, JspCPlugin.CONFIGURATION_NAME, fileTree);

		ConfigurableFileCollection configurableFileCollection = project.files(
			unzipJarTask.getDestinationDir());

		configurableFileCollection.builtBy(unzipJarTask);

		GradleUtil.addDependency(
			project, JspCPlugin.CONFIGURATION_NAME, configurableFileCollection);
	}

	@Override
	protected void addPortalToolDependencies(Project project) {
		super.addPortalToolDependencies(project);

		GradleUtil.addDependency(
			project, getPortalToolConfigurationName(), "org.apache.ant", "ant",
			"1.9.4");
	}

	protected Copy addTaskUnzipJar(final Project project) {
		Copy copy = GradleUtil.addTask(
			project, UNZIP_JAR_TASK_NAME, Copy.class);

		final Jar jar = (Jar)GradleUtil.getTask(
			project, JavaPlugin.JAR_TASK_NAME);

		copy.dependsOn(jar);

		copy.from(
			new Closure<FileTree>(null) {

				@SuppressWarnings("unused")
				public FileTree doCall() {
					return project.zipTree(jar.getArchivePath());
				}

			});

		copy.into(new File(project.getBuildDir(), "unzipped-jar"));

		return copy;
	}

	@Override
	protected void configureDefaults(Project project, JspCPlugin jspCPlugin) {
		super.configureDefaults(project, jspCPlugin);

		final Copy unzipJarTask = addTaskUnzipJar(project);

		configureJspCExtension(project, unzipJarTask);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					addDependenciesJspC(project, unzipJarTask);
				}

			});
	}

	protected void configureJspCExtension(
		Project project, final Copy unzipJarTask) {

		JspCExtension jspCExtension = GradleUtil.getExtension(
			project, JspCExtension.class);

		jspCExtension.setModuleWeb(true);

		jspCExtension.setPortalDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					LiferayExtension liferayExtension = GradleUtil.getExtension(
						unzipJarTask.getProject(), LiferayExtension.class);

					return liferayExtension.getAppServerPortalDir();
				}

			});

		jspCExtension.setWebAppDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File unzippedJarDir = unzipJarTask.getDestinationDir();

					File resourcesDir = new File(
						unzippedJarDir, "META-INF/resources");

					if (resourcesDir.exists()) {
						return resourcesDir;
					}

					return unzippedJarDir;
				}

			});
	}

	@Override
	protected Class<JspCPlugin> getPluginClass() {
		return JspCPlugin.class;
	}

	@Override
	protected String getPortalToolConfigurationName() {
		return JspCPlugin.TOOL_CONFIGURATION_NAME;
	}

	@Override
	protected String getPortalToolName() {
		return _PORTAL_TOOL_NAME;
	}

	private static final String _PORTAL_TOOL_NAME = "com.liferay.jasper.jspc";

}