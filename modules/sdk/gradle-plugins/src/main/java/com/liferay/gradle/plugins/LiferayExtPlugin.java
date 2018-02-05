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

import com.liferay.gradle.plugins.tasks.BuildExtInfoTask;
import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.bundling.War;
import org.gradle.jvm.tasks.Jar;
import org.gradle.util.GUtil;

/**
 * @author David Truong
 */
public class LiferayExtPlugin implements Plugin<Project> {

	public static final String BUILD_EXT_INFO_TASK_NAME = "buildExtInfo";

	public static final String CONFIGURATION_NAME = "ext";

	public static final String COPY_PORTAL_DEPENDENCIES_TASK_NAME =
		"copyPortalDependencies";

	@Override
	public void apply(final Project project) {
		Configuration extConfiguration = _addConfigurationExt(project);

		PluginContainer pluginContainer = project.getPlugins();

		if (!pluginContainer.hasPlugin(WarPlugin.class)) {
			GradleUtil.applyPlugin(project, WarPlugin.class);
		}

		Copy copy = GradleUtil.addTask(
			project, COPY_PORTAL_DEPENDENCIES_TASK_NAME, Copy.class);

		War war = (War)GradleUtil.getTask(project, "war");

		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		final File webInfDir = new File(
			warPluginConvention.getWebAppDir(), "WEB-INF");

		FileCollection extJars = project.files(extConfiguration);

		Jar jarExtKernel = _configureExtJar(
			war, "ext-kernel", webInfDir, extJars);

		extJars = extJars.plus(project.files(jarExtKernel));

		Jar jarExtUtilBridges = _configureExtUtilJar(
			war, "ext-util-bridges", webInfDir, extJars, copy);

		extJars = extJars.plus(project.files(jarExtUtilBridges));

		Jar jarExtUtilJava = _configureExtUtilJar(
			war, "ext-util-java", webInfDir, extJars, copy);

		extJars = extJars.plus(project.files(jarExtUtilJava));

		Jar jarExtUtilTaglib = _configureExtUtilJar(
			war, "ext-util-taglib", webInfDir, extJars, copy);

		extJars = extJars.plus(project.files(jarExtUtilTaglib));

		Jar jarExtImpl = _configureExtImplJar(
			war, "ext-impl", webInfDir, extJars, copy);

		extJars = extJars.plus(project.files(jarExtImpl));

		_addTaskBuildExtInfo(
			project, extJars, war, webInfDir, jarExtImpl, jarExtKernel,
			jarExtUtilBridges, jarExtUtilJava, jarExtUtilTaglib);
	}

	private Configuration _addConfigurationExt(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesExt(project);
				}

			});

		configuration.setDescription("Configures Portal Classloader for Ext.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesExt(Project project) {
		Class<?> clazz = getClass();

		try (InputStream in = clazz.getResourceAsStream(
				"dependencies/dependencies.txt");
			BufferedReader bufferedReader =
				new BufferedReader(new InputStreamReader(in))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				String[] artifact = line.split(":");

				GradleUtil.addDependency(
					project, CONFIGURATION_NAME, artifact[0], artifact[1],
					artifact[2], false);
			}
		}
		catch (Exception e) {
			e.printStackTrace();

			System.err.println("Could not add configuration ext");
		}
	}

	private Jar _addJarTask(Project project, String name, SourceSet sourceSet) {
		Jar jar = GradleUtil.addTask(
			project, GUtil.toLowerCamelCase("jar " + name), Jar.class);

		jar.from(sourceSet.getOutput());
		jar.setArchiveName(name + ".jar");

		return jar;
	}

	private Copy _addPortalDeployDependencies(
		final Project project, Copy copy, Jar jar) {

		copy.from(
			jar,
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.rename(
						new Closure<String>(project) {

							public String doCall(String fileName) {
								return fileName.replaceAll(
									"ext", "ext-" + project.getName());
							}

						});
				}

			});

		return copy;
	}

	private SourceSet _addSourceSet(
		Project project, String name, File srcDir,
		FileCollection fileCollection) {

		SourceSet sourceSet = GradleUtil.addSourceSet(project, name);

		Iterable<File> srcDirs = Collections.singleton(srcDir);

		sourceSet.setCompileClasspath(fileCollection);

		SourceDirectorySet javaSourceDirectorySet = sourceSet.getJava();

		javaSourceDirectorySet.setSrcDirs(srcDirs);

		SourceDirectorySet resourcesSourceDirectorySet =
			sourceSet.getResources();

		resourcesSourceDirectorySet.setSrcDirs(srcDirs);

		return sourceSet;
	}

	private BuildExtInfoTask _addTaskBuildExtInfo(
		Project project, FileCollection classpath, War war, File webInfDir,
		Jar... jars) {

		final BuildExtInfoTask buildExtInfoTask = GradleUtil.addTask(
			project, BUILD_EXT_INFO_TASK_NAME, BuildExtInfoTask.class);

		buildExtInfoTask.setDescription("Build Ext Info file.");
		buildExtInfoTask.setGroup(BasePlugin.BUILD_GROUP);
		buildExtInfoTask.setClasspath(classpath);

		File destinationDir = buildExtInfoTask.getTemporaryDir();

		Sync sync = _copyTempWebapp(project, webInfDir, destinationDir, jars);

		buildExtInfoTask.dependsOn(sync);

		List<String> args = new ArrayList<>();

		args.add(destinationDir.getPath());
		args.add(destinationDir.getPath());
		args.add(project.getName());

		buildExtInfoTask.setArgs(args);

		CopySpec copySpec = war.getWebInf();

		copySpec.from(buildExtInfoTask);

		return buildExtInfoTask;
	}

	private Copy _configureCopyPortalDeployDepedenciesTask(
		SourceSet sourceSet, Copy copy) {

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		copy.into(
			new File(
				sourceSetOutput.getClassesDir(),
				"com/liferay/portal/deploy/dependencies"));

		return copy;
	}

	private Jar _configureExtImplJar(
		War war, String name, File webInfDir, FileCollection fileCollection,
		Copy copy) {

		Project project = war.getProject();

		SourceSet sourceSet = _addSourceSet(
			project, name, new File(webInfDir, name + "/src"), fileCollection);

		_configureCopyPortalDeployDepedenciesTask(sourceSet, copy);

		Jar jar = _addJarTask(project, name, sourceSet);

		_copyToWebInf(war, jar, name);

		jar.dependsOn(copy);

		return jar;
	}

	private Jar _configureExtJar(
		War war, String name, File webInfDir, FileCollection fileCollection) {

		Project project = war.getProject();

		SourceSet sourceSet = _addSourceSet(
			project, name, new File(webInfDir, name + "/src"), fileCollection);

		Jar jar = _addJarTask(project, name, sourceSet);

		_copyToWebInf(war, jar, name);

		return jar;
	}

	private Jar _configureExtUtilJar(
		War war, String name, File webInfDir, FileCollection fileCollection,
		Copy copy) {

		Jar jar = _configureExtJar(war, name, webInfDir, fileCollection);

		_addPortalDeployDependencies(war.getProject(), copy, jar);

		return jar;
	}

	private Sync _copyTempWebapp(
		Project project, File webInfDir, File destinationDir, Jar... jars) {

		Sync sync = GradleUtil.addTask(project, "syncWebapp", Sync.class);

		sync.exclude("**/.touch");
		sync.from(webInfDir);
		sync.into(destinationDir);

		for (final Jar jar : jars) {
			sync.from(
				jar,
				new Closure<Void>(project) {

					@SuppressWarnings("unused")
					public void doCall(CopySpec copySpec) {
						String fileName = jar.getArchiveName();

						fileName = fileName.substring(0, fileName.length() - 4);

						copySpec.into(fileName);
					}

				});
		}

		return sync;
	}

	private void _copyToWebInf(War war, Jar jar, String name) {
		CopySpec copySpec = war.getWebInf();

		copySpec.from(jar);
		copySpec.into(name);
	}

}