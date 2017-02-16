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

package com.liferay.gradle.plugins.internal;

import com.liferay.gradle.plugins.BasePortalToolDefaultsPlugin;
import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.jasper.jspc.CompileJSPTask;
import com.liferay.gradle.plugins.jasper.jspc.JspCPlugin;
import com.liferay.gradle.plugins.util.PortalTools;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.Collections;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.ModuleDependency;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.bundling.Jar;

/**
 * @author Andrea Di Giorgi
 */
public class JspCDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<JspCPlugin> {

	public static final Plugin<Project> INSTANCE = new JspCDefaultsPlugin();

	public static final String UNZIP_JAR_TASK_NAME = "unzipJar";

	protected void addDependenciesJspC(Project project) {
		ConfigurableFileCollection configurableFileCollection = project.files(
			_getUnzippedJarDir(project));

		configurableFileCollection.builtBy(UNZIP_JAR_TASK_NAME);

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

	@Override
	protected void addPortalToolDependencies(
		Project project, String configurationName, String portalToolName) {

		String portalToolVersion = PortalTools.getVersion(
			project, portalToolName);

		if (Validator.isNotNull(portalToolVersion)) {
			ModuleDependency moduleDependency =
				(ModuleDependency)GradleUtil.addDependency(
					project, configurationName, PortalTools.GROUP,
					portalToolName, portalToolVersion);

			moduleDependency.exclude(
				Collections.singletonMap("group", "com.liferay.portal"));
		}
	}

	@Override
	protected void configureDefaults(Project project, JspCPlugin jspCPlugin) {
		super.configureDefaults(project, jspCPlugin);

		_addTaskUnzipJar(project);

		_configureTaskGenerateJSPJava(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					addDependenciesJspC(project);
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

	private JspCDefaultsPlugin() {
	}

	private Task _addTaskUnzipJar(final Project project) {
		Task task = project.task(UNZIP_JAR_TASK_NAME);

		final Jar jar = (Jar)GradleUtil.getTask(
			project, JavaPlugin.JAR_TASK_NAME);

		task.dependsOn(jar);

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					FileUtil.unzip(
						project, jar.getArchivePath(),
						_getUnzippedJarDir(project));
				}

			});

		return task;
	}

	private void _configureTaskGenerateJSPJava(final Project project) {
		CompileJSPTask compileJSPTask = (CompileJSPTask)GradleUtil.getTask(
			project, JspCPlugin.GENERATE_JSP_JAVA_TASK_NAME);

		compileJSPTask.setWebAppDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File unzippedJarDir = _getUnzippedJarDir(project);

					File resourcesDir = new File(
						unzippedJarDir, "META-INF/resources");

					if (resourcesDir.exists()) {
						return resourcesDir;
					}

					return unzippedJarDir;
				}

			});
	}

	private File _getUnzippedJarDir(Project project) {
		return new File(project.getBuildDir(), "unzipped-jar");
	}

	private static final String _PORTAL_TOOL_NAME = "com.liferay.jasper.jspc";

}