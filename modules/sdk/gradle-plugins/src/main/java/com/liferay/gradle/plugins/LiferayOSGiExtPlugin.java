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

import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.internal.CSSBuilderDefaultsPlugin;
import com.liferay.gradle.plugins.internal.EclipseDefaultsPlugin;
import com.liferay.gradle.plugins.internal.IdeaDefaultsPlugin;
import com.liferay.gradle.plugins.internal.WatchOSGiPlugin;
import com.liferay.gradle.plugins.internal.XMLFormatterDefaultsPlugin;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.xml.formatter.XMLFormatterPlugin;

import groovy.lang.Closure;

import java.io.File;
import java.io.FileInputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileTree;
import org.gradle.api.java.archives.Manifest;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.idea.IdeaPlugin;

import org.osgi.framework.Version;

/**
 * @author David Truong
 */
public class LiferayOSGiExtPlugin implements Plugin<Project> {

	public static final String ORIGINAL_MODULE_CONFIGURATION_NAME =
		"originalModule";

	public static final String UNZIP_ORIGINAL_MODULE_JAVA_TASK_NAME =
		"unzipOriginalModuleJava";

	public static final String UNZIP_ORIGINAL_MODULE_MANIFEST_TASK_NAME =
		"unzipOriginalModuleManifest";

	public static final String UNZIP_ORIGINAL_MODULE_RESOURCES_TASK_NAME =
		"unzipOriginalModuleResources";

	@Override
	public void apply(final Project project) {
		_applyPlugins(project);

		Configuration originalModuleConfiguration =
			_addConfigurationOriginalModule(project);

		_configureConfigurationCompileOnly(
			project, originalModuleConfiguration);

		_addTaskUnzipOriginalModuleJava(project, originalModuleConfiguration);
		_addTaskUnzipOriginalModuleResources(
			project, originalModuleConfiguration);

		Copy copy = _addTaskUnzipOriginalModuleManifest(
			project, originalModuleConfiguration);

		Jar jar = _configureTaskJar(project, copy);

		_configureTaskDeploy(project, jar);
	}

	private Configuration _addConfigurationOriginalModule(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, ORIGINAL_MODULE_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures the original module you would like to extend.");
		configuration.setTransitive(false);
		configuration.setVisible(false);

		return configuration;
	}

	private Copy _addTaskUnzipOriginalModuleJava(
		final Project project, final Configuration configuration) {

		Copy copy = GradleUtil.addTask(
			project, UNZIP_ORIGINAL_MODULE_JAVA_TASK_NAME, Copy.class);

		copy.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() {
					return project.zipTree(configuration.getSingleFile());
				}

			});

		copy.include("**/*.class");
		copy.setDescription("Unzips the original module's java classes.");
		copy.setIncludeEmptyDirs(false);

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		copy.setDestinationDir(sourceSetOutput.getClassesDir());

		Task compileJavaTask = GradleUtil.getTask(
			project, JavaPlugin.COMPILE_JAVA_TASK_NAME);

		compileJavaTask.dependsOn(copy);

		return copy;
	}

	private Copy _addTaskUnzipOriginalModuleManifest(
		final Project project, final Configuration configuration) {

		Copy copy = GradleUtil.addTask(
			project, UNZIP_ORIGINAL_MODULE_MANIFEST_TASK_NAME, Copy.class);

		copy.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() {
					return project.zipTree(configuration.getSingleFile());
				}

			});

		copy.include("**/MANIFEST.MF");
		copy.setDescription("Unzips the original module's MANIFEST.MF.");
		copy.setDestinationDir(copy.getTemporaryDir());
		copy.setIncludeEmptyDirs(false);

		return copy;
	}

	private Copy _addTaskUnzipOriginalModuleResources(
		final Project project, final Configuration configuration) {

		Copy copy = GradleUtil.addTask(
			project, UNZIP_ORIGINAL_MODULE_RESOURCES_TASK_NAME, Copy.class);

		copy.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() {
					return project.zipTree(configuration.getSingleFile());
				}

			});

		copy.exclude("**/*.class", "**/MANIFEST.MF");
		copy.setDescription("Unzips the original module's resources.");
		copy.setIncludeEmptyDirs(false);

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		copy.setDestinationDir(sourceSetOutput.getResourcesDir());

		Task processResourcesTask = GradleUtil.getTask(
			project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		processResourcesTask.dependsOn(copy);

		return copy;
	}

	private void _applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, CSSBuilderPlugin.class);
		GradleUtil.applyPlugin(project, EclipsePlugin.class);
		GradleUtil.applyPlugin(project, IdeaPlugin.class);
		GradleUtil.applyPlugin(project, JavaPlugin.class);
		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
		GradleUtil.applyPlugin(project, XMLFormatterPlugin.class);

		CSSBuilderDefaultsPlugin.INSTANCE.apply(project);
		EclipseDefaultsPlugin.INSTANCE.apply(project);
		IdeaDefaultsPlugin.INSTANCE.apply(project);
		WatchOSGiPlugin.INSTANCE.apply(project);
		XMLFormatterDefaultsPlugin.INSTANCE.apply(project);
	}

	private Configuration _configureConfigurationCompileOnly(
		Project project, Configuration originalModuleConfiguration) {

		Configuration configuration = GradleUtil.getConfiguration(
			project, JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME);

		configuration.extendsFrom(originalModuleConfiguration);

		return configuration;
	}

	private void _configureTaskDeploy(Project project, Jar jar) {
		final LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		Copy copy = (Copy)GradleUtil.getTask(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME);

		copy.from(jar);

		liferayExtension.setDeployDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						liferayExtension.getAppServerParentDir(),
						"osgi/marketplace/override");
				}

			});
	}

	private Jar _configureTaskJar(Project project, final Copy copy) {
		final Jar jar = (Jar)GradleUtil.getTask(
			project, JavaPlugin.JAR_TASK_NAME);

		jar.dependsOn(copy);
		jar.mustRunAfter(copy);

		jar.manifest(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(Manifest manifest) {
					File file = new File(
						copy.getTemporaryDir(), "META-INF/MANIFEST.MF");

					if (!file.exists()) {
						Logger logger = jar.getLogger();

						logger.info("MANIFEST.MF not found");

						return;
					}

					try (FileInputStream fileInputStream =
							new FileInputStream(file)) {

						Map<String, Object> map = new HashMap<>();

						java.util.jar.Manifest originalManifest =
							new java.util.jar.Manifest(fileInputStream);

						Attributes attributes =
							originalManifest.getMainAttributes();

						for (Object key : attributes.keySet()) {
							Name name = (Name)key;

							String value = attributes.getValue(name);

							String nameString = name.toString();

							if (nameString.equals("Bundle-Version")) {
								Version version = Version.parseVersion(value);

								String qualifier = version.getQualifier();

								String suffix = "ext";

								if (qualifier.isEmpty()) {
									value += '.' + suffix;
								}
								else {
									value += '-' + suffix;
								}
							}

							map.put(nameString, value);
						}

						jar.setArchiveName(
							attributes.getValue("Bundle-SymbolicName") +
								".jar");

						manifest.attributes(map);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}

			});

		return jar;
	}

}