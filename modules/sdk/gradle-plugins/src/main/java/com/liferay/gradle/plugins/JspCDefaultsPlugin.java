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
import com.liferay.gradle.plugins.jasper.jspc.CompileJSPTask;
import com.liferay.gradle.plugins.jasper.jspc.JspCPlugin;
import com.liferay.gradle.plugins.tasks.WritePropertiesTask;
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.plugins.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;

import java.util.Properties;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.internal.plugins.osgi.OsgiHelper;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Andrea Di Giorgi
 */
public class JspCDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<JspCPlugin> {

	public static final String JSP_PRECOMPILE_ENABLED_PROPERTY_NAME =
		"jsp.precompile.enabled";

	public static final String UNZIP_JAR_TASK_NAME = "unzipJar";

	protected void addDependenciesJspC(Project project) {
		ConfigurableFileCollection configurableFileCollection = project.files(
			getUnzippedJarDir(project));

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

	protected Task addTaskUnzipJar(final Project project) {
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
						getUnzippedJarDir(project));
				}

			});

		return task;
	}

	@Override
	protected void configureDefaults(Project project, JspCPlugin jspCPlugin) {
		super.configureDefaults(project, jspCPlugin);

		addTaskUnzipJar(project);

		configureTaskGenerateJSPJava(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					addDependenciesJspC(project);
					configureTaskCompileJSP(project);
				}

			});
	}

	protected void configureTaskCompileJSP(Project project) {
		boolean jspPrecompileEnabled = GradleUtil.getProperty(
			project, JSP_PRECOMPILE_ENABLED_PROPERTY_NAME, false);

		if (!jspPrecompileEnabled) {
			return;
		}

		JavaCompile javaCompile = (JavaCompile)GradleUtil.getTask(
			project, JspCPlugin.COMPILE_JSP_TASK_NAME);

		String dirName = null;

		TaskContainer taskContainer = project.getTasks();

		WritePropertiesTask recordArtifactTask =
			(WritePropertiesTask)taskContainer.findByName(
				LiferayRelengPlugin.RECORD_ARTIFACT_TASK_NAME);

		if (recordArtifactTask != null) {
			Properties artifactProperties;

			try {
				artifactProperties = FileUtil.readProperties(
					recordArtifactTask.getOutputFile());
			}
			catch (IOException ioe) {
				throw new UncheckedIOException(ioe);
			}

			String artifactURL = artifactProperties.getProperty("artifact.url");

			if (Validator.isNotNull(artifactURL)) {
				int index = artifactURL.lastIndexOf('/');

				dirName = artifactURL.substring(
					index + 1, artifactURL.length() - 4);
			}
		}

		if (Validator.isNull(dirName)) {
			dirName =
				_osgiHelper.getBundleSymbolicName(project) + "-" +
					project.getVersion();
		}

		LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		File dir = new File(
			liferayExtension.getLiferayHome(), "work/" + dirName);

		javaCompile.setDestinationDir(dir);
	}

	protected void configureTaskGenerateJSPJava(final Project project) {
		CompileJSPTask compileJSPTask = (CompileJSPTask)GradleUtil.getTask(
			project, JspCPlugin.GENERATE_JSP_JAVA_TASK_NAME);

		compileJSPTask.setWebAppDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File unzippedJarDir = getUnzippedJarDir(project);

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

	protected File getUnzippedJarDir(Project project) {
		return new File(project.getBuildDir(), "unzipped-jar");
	}

	private static final String _PORTAL_TOOL_NAME = "com.liferay.jasper.jspc";

	private static final OsgiHelper _osgiHelper = new OsgiHelper();

}