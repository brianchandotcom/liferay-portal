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

import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.tasks.BuildExtInfoTask;

import groovy.lang.Closure;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.War;
import org.gradle.util.GUtil;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class LiferayExtPlugin implements Plugin<Project> {

	public static final String BUILD_EXT_INFO_BASE_DIR_TASK_NAME =
		"buildExtInfoBaseDir";

	public static final String BUILD_EXT_INFO_TASK_NAME = "buildExtInfo";

	public static final String EXT_IMPL_SOURCESET_NAME = "extImpl";

	public static final String EXT_KERNEL_SOURCESET_NAME = "extKernel";

	public static final String EXT_UTIL_BRIDGES_SOURCESET_NAME =
		"extUtilBridges";

	public static final String EXT_UTIL_JAVA_SOURCESET_NAME = "extUtilJava";

	public static final String EXT_UTIL_TAGLIB_SOURCESET_NAME = "extUtilTaglib";

	@Override
	public void apply(Project project) {
		_applyPlugins(project);

		War war = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		Configuration portalConfiguration = GradleUtil.getConfiguration(
			project, LiferayBasePlugin.PORTAL_CONFIGURATION_NAME);

		Jar extKernelJar = _addSourceSet(
			war, warPluginConvention, EXT_KERNEL_SOURCESET_NAME,
			portalConfiguration);

		FileCollection compileClasspath = portalConfiguration.plus(
			project.files(extKernelJar));

		Jar utilBridgesJar = _addSourceSet(
			war, warPluginConvention, EXT_UTIL_BRIDGES_SOURCESET_NAME,
			compileClasspath);

		compileClasspath = compileClasspath.plus(project.files(utilBridgesJar));

		Jar utilJavaJar = _addSourceSet(
			war, warPluginConvention, EXT_UTIL_JAVA_SOURCESET_NAME,
			compileClasspath);

		compileClasspath = compileClasspath.plus(project.files(utilJavaJar));

		Jar utilTaglibJar = _addSourceSet(
			war, warPluginConvention, EXT_UTIL_TAGLIB_SOURCESET_NAME,
			compileClasspath);

		compileClasspath = compileClasspath.plus(project.files(utilTaglibJar));

		Jar extImplJar = _addSourceSet(
			war, warPluginConvention, EXT_IMPL_SOURCESET_NAME,
			compileClasspath);

		Sync buildExtInfoBaseDirTask = _addTaskBuildExtInfoBaseDir(war);

		BuildExtInfoTask buildExtInfoTask = _addTaskBuildExtInfo(
			buildExtInfoBaseDirTask, war, portalConfiguration);

		_configureTaskBuildExtInfoBaseDir(
			buildExtInfoBaseDirTask, buildExtInfoTask);

		_configureTaskExtImplJar(
			extImplJar, utilBridgesJar, utilJavaJar, utilTaglibJar);
		_configureTaskWar(war, buildExtInfoTask);
	}

	private Jar _addSourceSet(
		War war, final WarPluginConvention warPluginConvention, String name,
		FileCollection compileClasspath) {

		// Source set

		Project project = war.getProject();

		SourceSet sourceSet = GradleUtil.addSourceSet(project, name);

		sourceSet.setCompileClasspath(compileClasspath);

		// Jar task

		final Jar jar = GradleUtil.addTask(
			project, sourceSet.getJarTaskName(), Jar.class);

		jar.from(sourceSet.getOutput());

		jar.setAppendix(GUtil.toWords(name, '-'));

		jar.setDescription(
			"Assembles a jar archive containing the " + jar.getAppendix() +
				" classes.");

		// Source directories

		Callable<File> srcDirCallable = new Callable<File>() {

			@Override
			public File call() throws Exception {
				return new File(
					warPluginConvention.getWebAppDir(),
					"WEB-INF/" + jar.getAppendix() + "/src");
			}

		};

		SourceDirectorySet javaSourceDirectorySet = sourceSet.getJava();

		javaSourceDirectorySet.srcDir(srcDirCallable);

		SourceDirectorySet resourcesDirectorySet = sourceSet.getResources();

		resourcesDirectorySet.srcDir(srcDirCallable);

		// War task

		CopySpec copySpec = war.getWebInf();

		copySpec.from(jar);

		copySpec.into(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return jar.getAppendix();
				}

			});

		copySpec.rename(
			new Closure<String>(project) {

				@SuppressWarnings("unused")
				public String doCall(String fileName) {
					return jar.getAppendix() + "." + jar.getExtension();
				}

			});

		return jar;
	}

	private BuildExtInfoTask _addTaskBuildExtInfo(
		final Sync buildExtInfoBaseDirTask, final War war,
		FileCollection classpath) {

		Project project = buildExtInfoBaseDirTask.getProject();

		final BuildExtInfoTask buildExtInfoTask = GradleUtil.addTask(
			project, BUILD_EXT_INFO_TASK_NAME, BuildExtInfoTask.class);

		buildExtInfoTask.dependsOn(buildExtInfoBaseDirTask);

		buildExtInfoTask.setBaseDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						buildExtInfoBaseDirTask.getDestinationDir(), "WEB-INF");
				}

			});

		buildExtInfoTask.setClasspath(classpath);
		buildExtInfoTask.setDescription(
			"Generates the ext information xml file.");

		buildExtInfoTask.setOutputDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return buildExtInfoTask.getTemporaryDir();
				}

			});

		buildExtInfoTask.setServletContextName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return war.getBaseName() + "-" + war.getAppendix();
				}

			});

		return buildExtInfoTask;
	}

	private Sync _addTaskBuildExtInfoBaseDir(War war) {
		final Project project = war.getProject();

		Sync sync = GradleUtil.addTask(
			project, BUILD_EXT_INFO_BASE_DIR_TASK_NAME, Sync.class);

		sync.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(project.getBuildDir(), "build-ext-info");
				}

			});

		sync.setDescription(
			"Copies the exploded war archive into a temporary directory.");
		sync.with(war);

		return sync;
	}

	private void _applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);
		GradleUtil.applyPlugin(project, WarPlugin.class);
	}

	private void _configureTaskBuildExtInfoBaseDir(
		Sync buildExtInfoBaseDirTask, final BuildExtInfoTask buildExtInfoTask) {

		buildExtInfoBaseDirTask.exclude(
			new Spec<FileTreeElement>() {

				@Override
				public boolean isSatisfiedBy(FileTreeElement fileTreeElement) {
					File outputFile = buildExtInfoTask.getOutputFile();

					String outputFileName = outputFile.getName();

					if (outputFileName.equals(fileTreeElement.getPath())) {
						return true;
					}

					return false;
				}

			});
	}

	private void _configureTaskExtImplJar(
		final Jar extImplJar, final Jar... jars) {

		final Project project = extImplJar.getProject();

		for (final Jar jar : jars) {
			extImplJar.into(
				"com/liferay/portal/deploy/dependencies",
				new Closure<Void>(project) {

					@SuppressWarnings("unused")
					public void doCall(CopySpec copySpec) {
						copySpec.from(jar);
						copySpec.rename(
							new PortalDeployDependencyRenameClosure(jar));
					}

				});
		}
	}

	private void _configureTaskWar(
		War war, final BuildExtInfoTask buildExtInfoTask) {

		war.setAppendix("ext");

		CopySpec copySpec = war.getWebInf();

		war.dependsOn(buildExtInfoTask);

		copySpec.from(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return buildExtInfoTask.getOutputFile();
				}

			});
	}

	private static class PortalDeployDependencyRenameClosure
		extends Closure<String> {

		public PortalDeployDependencyRenameClosure(Jar jar) {
			super(jar.getProject());

			_jar = jar;
		}

		@SuppressWarnings("unused")
		public String doCall(String fileName) {
			Project project = _jar.getProject();

			StringBuilder sb = new StringBuilder();

			sb.append("ext-");
			sb.append(project.getName());
			sb.append('-');
			sb.append(_jar.getAppendix());
			sb.append('.');
			sb.append(_jar.getExtension());

			return sb.toString();
		}

		private final Jar _jar;

	}

}