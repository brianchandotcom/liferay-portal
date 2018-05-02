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
import com.liferay.gradle.plugins.internal.util.GradleUtil;

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
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.bundling.Jar;

import org.osgi.framework.Version;

/**
 * @author David Truong
 */
public class ModuleExtPlugin implements Plugin<Project> {

	public static final String MODULE_EXT_CONFIGURATION_NAME = "moduleExt";

	public static final String UNZIP_MODULE_EXT_JAVA_TASK_NAME =
		"unzipModuleExtJava";

	public static final String UNZIP_MODULE_EXT_MANIFEST_TASK_NAME =
		"unzipModuleExtManifest";

	public static final String UNZIP_MODULE_EXT_RESOURCES_TASK_NAME =
		"unzipModuleExtResources";

	@Override
	public void apply(final Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);
		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);

		Configuration moduleExtConfiguration = _addConfigurationModuleExt(
			project);

		_addTaskUnzipModulesExtJava(project, moduleExtConfiguration);

		_addTaskUnzipModulesExtResources(project, moduleExtConfiguration);

		Copy copy = _addTaskUnzipModulesExtManifest(
			project, moduleExtConfiguration);

		Jar jar = _configureJarTask(project, copy);

		_configureDeployTask(project, jar);
	}

	private Configuration _addConfigurationModuleExt(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, MODULE_EXT_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures the module you would like to extend.");
		configuration.setTransitive(true);
		configuration.setVisible(false);

		Configuration compileOnlyConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME);

		compileOnlyConfiguration.extendsFrom(configuration);

		return configuration;
	}

	private Copy _addTaskUnzipModulesExtJava(
		final Project project, final Configuration configuration) {

		Copy copy = GradleUtil.addTask(
			project, UNZIP_MODULE_EXT_JAVA_TASK_NAME, Copy.class);

		copy.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() {
					return project.zipTree(configuration.getSingleFile());
				}

			});

		copy.include("**/*.class");
		copy.setIncludeEmptyDirs(false);

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		copy.setDestinationDir(sourceSetOutput.getClassesDir());

		Task compileJava = GradleUtil.getTask(
			project, JavaPlugin.COMPILE_JAVA_TASK_NAME);

		compileJava.dependsOn(copy);

		return copy;
	}

	private Copy _addTaskUnzipModulesExtManifest(
		final Project project, final Configuration configuration) {

		Copy copy = GradleUtil.addTask(
			project, UNZIP_MODULE_EXT_MANIFEST_TASK_NAME, Copy.class);

		copy.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() {
					return project.zipTree(configuration.getSingleFile());
				}

			});

		copy.include("**/MANIFEST.MF");
		copy.setIncludeEmptyDirs(false);
		copy.setDestinationDir(copy.getTemporaryDir());

		return copy;
	}

	private Copy _addTaskUnzipModulesExtResources(
		final Project project, final Configuration configuration) {

		Copy copy = GradleUtil.addTask(
			project, UNZIP_MODULE_EXT_RESOURCES_TASK_NAME, Copy.class);

		copy.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() {
					return project.zipTree(configuration.getSingleFile());
				}

			});

		copy.exclude("**/*.class", "**/MANIFEST.MF");
		copy.setIncludeEmptyDirs(false);

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		copy.setDestinationDir(sourceSetOutput.getResourcesDir());

		Task processResources = GradleUtil.getTask(
			project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		processResources.dependsOn(copy);

		return copy;
	}

	private void _configureDeployTask(Project project, Jar jar) {
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

	private Jar _configureJarTask(Project project, final Copy copy) {
		final Jar jar = GradleUtil.addTask(
			project, JavaPlugin.JAR_TASK_NAME, Jar.class, true);

		jar.dependsOn(JavaPlugin.CLASSES_TASK_NAME, copy);

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		jar.from(sourceSetOutput.getClassesDir());

		jar.from(sourceSetOutput.getResourcesDir());

		jar.manifest(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(Manifest manifest) {
					File file = new File(
						copy.getTemporaryDir(), "META-INF/MANIFEST.MF");

					if (!file.exists()) {
						System.out.println("MANIFEST.MF not found");

						return;
					}

					try (FileInputStream fileInputStream =
							new FileInputStream(file)) {

						Map map = new HashMap<>();

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