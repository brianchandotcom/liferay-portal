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

package com.liferay.gradle.plugins.maven.plugin.builder;

import com.liferay.gradle.plugins.maven.plugin.builder.tasks.BuildPluginDescriptorTask;
import com.liferay.gradle.plugins.maven.plugin.builder.tasks.WriteMavenSettingsTask;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.JavaVersion;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.plugins.osgi.OsgiHelper;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.Upload;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.external.javadoc.CoreJavadocOptions;

/**
 * @author Andrea Di Giorgi
 */
public class MavenPluginBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_PLUGIN_DESCRIPTOR_TASK_NAME =
		"buildPluginDescriptor";

	public static final String MAVEN_EMBEDDER_CONFIGURATION_NAME =
		"mavenEmbedder";

	public static final String WRITE_MAVEN_SETTINGS_TASK = "writeMavenSettings";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		Configuration mavenEmbedderConfiguration =
			_addConfigurationMavenEmbedder(project);

		WriteMavenSettingsTask writeMavenSettingsTask =
			_addTaskWriteMavenSettings(project);

		BuildPluginDescriptorTask buildPluginDescriptorTask =
			_addTaskBuildPluginDescriptor(
				writeMavenSettingsTask, mavenEmbedderConfiguration);

		JavaVersion javaVersion = JavaVersion.current();

		if (javaVersion.isJava8Compatible()) {
			_configureTasksJavadocDisableDoclint(project);
		}

		_configureTasksUpload(project, buildPluginDescriptorTask);
	}

	private Configuration _addConfigurationMavenEmbedder(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, MAVEN_EMBEDDER_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesMavenEmbedder(project);
				}

			});

		configuration.setDescription(
			"Configures Maven Embedder for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesMavenEmbedder(Project project) {
		GradleUtil.addDependency(
			project, MAVEN_EMBEDDER_CONFIGURATION_NAME, "org.apache.maven",
			"maven-embedder", "3.3.9");
		GradleUtil.addDependency(
			project, MAVEN_EMBEDDER_CONFIGURATION_NAME,
			"org.apache.maven.wagon", "wagon-http", "2.10");
		GradleUtil.addDependency(
			project, MAVEN_EMBEDDER_CONFIGURATION_NAME, "org.eclipse.aether",
			"aether-connector-basic", "1.0.2.v20150114");
		GradleUtil.addDependency(
			project, MAVEN_EMBEDDER_CONFIGURATION_NAME, "org.eclipse.aether",
			"aether-transport-wagon", "1.0.2.v20150114");
		GradleUtil.addDependency(
			project, MAVEN_EMBEDDER_CONFIGURATION_NAME, "org.slf4j",
			"slf4j-simple", "1.7.5");
	}

	private BuildPluginDescriptorTask _addTaskBuildPluginDescriptor(
		final WriteMavenSettingsTask writeMavenSettingsTask,
		FileCollection mavenEmbedderClasspath) {

		final Project project = writeMavenSettingsTask.getProject();

		BuildPluginDescriptorTask buildPluginDescriptorTask =
			GradleUtil.addTask(
				project, BUILD_PLUGIN_DESCRIPTOR_TASK_NAME,
				BuildPluginDescriptorTask.class);

		buildPluginDescriptorTask.dependsOn(
			JavaPlugin.COMPILE_JAVA_TASK_NAME, writeMavenSettingsTask);

		final SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		buildPluginDescriptorTask.setClassesDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					return sourceSetOutput.getClassesDir();
				}

			});

		buildPluginDescriptorTask.setDescription(
			"Generates the Maven plugin descriptor for the project.");
		buildPluginDescriptorTask.setGroup(BasePlugin.BUILD_GROUP);
		buildPluginDescriptorTask.setMavenEmbedderClasspath(
			mavenEmbedderClasspath);

		buildPluginDescriptorTask.setMavenSettingsFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return writeMavenSettingsTask.getOutputFile();
				}

			});

		buildPluginDescriptorTask.setOutputDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = _getSrcDir(sourceSet.getResources());

					return new File(resourcesDir, "META-INF/maven");
				}

			});

		buildPluginDescriptorTask.setPomArtifactId(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return _osgiHelper.getBundleSymbolicName(project);
				}

			});

		buildPluginDescriptorTask.setPomGroupId(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return project.getGroup();
				}

			});

		buildPluginDescriptorTask.setPomVersion(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					String version = String.valueOf(project.getVersion());

					if (version.endsWith("-SNAPSHOT")) {
						version = version.substring(0, version.length() - 9);
					}

					return version;
				}

			});

		buildPluginDescriptorTask.setSourceDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return _getSrcDir(sourceSet.getJava());
				}

			});

		Task processResourcesTask = GradleUtil.getTask(
			project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		processResourcesTask.mustRunAfter(buildPluginDescriptorTask);

		return buildPluginDescriptorTask;
	}

	private WriteMavenSettingsTask _addTaskWriteMavenSettings(
		final Project project) {

		WriteMavenSettingsTask writeMavenSettingsTask = GradleUtil.addTask(
			project, WRITE_MAVEN_SETTINGS_TASK, WriteMavenSettingsTask.class);

		writeMavenSettingsTask.setDescription(
			"Writes a temporary Maven settings file to be used during " +
				"subsequent Maven invocations.");
		writeMavenSettingsTask.setNonProxyHosts(
			System.getProperty("http.nonProxyHosts"));
		writeMavenSettingsTask.setProxyHost(
			new ProxyPropertyCallable("proxyHost", writeMavenSettingsTask));
		writeMavenSettingsTask.setProxyPassword(
			new ProxyPropertyCallable("proxyPassword", writeMavenSettingsTask));
		writeMavenSettingsTask.setProxyPort(
			new ProxyPropertyCallable("proxyPort", writeMavenSettingsTask));
		writeMavenSettingsTask.setProxyUser(
			new ProxyPropertyCallable("proxyUser", writeMavenSettingsTask));
		writeMavenSettingsTask.setRepositoryUrl(
			System.getProperty("repository.url"));

		writeMavenSettingsTask.setOutputFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(project.getBuildDir(), "settings.xml");
				}

			});

		return writeMavenSettingsTask;
	}

	private void _configureTaskJavadocDisableDoclint(Javadoc javadoc) {
		CoreJavadocOptions coreJavadocOptions =
			(CoreJavadocOptions)javadoc.getOptions();

		coreJavadocOptions.addStringOption("Xdoclint:none", "-quiet");
	}

	private void _configureTasksJavadocDisableDoclint(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			Javadoc.class,
			new Action<Javadoc>() {

				@Override
				public void execute(Javadoc javadoc) {
					_configureTaskJavadocDisableDoclint(javadoc);
				}

			});
	}

	private void _configureTasksUpload(
		Project project,
		final BuildPluginDescriptorTask buildPluginDescriptorTask) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			Upload.class,
			new Action<Upload>() {

				@Override
				public void execute(Upload upload) {
					_configureTaskUpload(upload, buildPluginDescriptorTask);
				}

			});
	}

	private void _configureTaskUpload(
		Upload upload, BuildPluginDescriptorTask buildPluginDescriptorTask) {

		upload.dependsOn(buildPluginDescriptorTask);
	}

	private File _getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	private static final OsgiHelper _osgiHelper = new OsgiHelper();

	private static class ProxyPropertyCallable implements Callable<String> {

		public ProxyPropertyCallable(
			String key, WriteMavenSettingsTask writeMavenSettingsTask) {

			_key = key;
			_writeMavenSettingsTask = writeMavenSettingsTask;
		}

		@Override
		public String call() throws Exception {
			String protocol = "https";

			String repositoryUrl = _writeMavenSettingsTask.getRepositoryUrl();

			if (Validator.isNotNull(repositoryUrl)) {
				protocol = repositoryUrl.substring(
					0, repositoryUrl.indexOf(':'));
			}

			return System.getProperty(protocol + "." + _key);
		}

		private final String _key;
		private final WriteMavenSettingsTask _writeMavenSettingsTask;

	}

}