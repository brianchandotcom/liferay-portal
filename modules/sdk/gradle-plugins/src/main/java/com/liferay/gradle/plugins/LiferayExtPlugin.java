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
import com.liferay.gradle.plugins.tasks.BuildExtInfoTask;

import groovy.lang.Closure;

import java.io.File;

import java.util.Collections;
import java.util.concurrent.Callable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.Convention;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
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

	public static final String EXT_IMPL_SOURCE_SET_NAME = "extImpl";

	public static final String EXT_KERNEL_SOURCE_SET_NAME = "extKernel";

	public static final String EXT_UTIL_BRIDGES_SOURCE_SET_NAME =
		"extUtilBridges";

	public static final String EXT_UTIL_JAVA_SOURCE_SET_NAME = "extUtilJava";

	public static final String EXT_UTIL_TAGLIB_SOURCE_SET_NAME =
		"extUtilTaglib";

	@Override
	public void apply(Project project) {
		_applyPlugins(project);

		Configuration portalConfiguration = GradleUtil.getConfiguration(
			project, LiferayBasePlugin.PORTAL_CONFIGURATION_NAME);

		Convention convention = project.getConvention();

		JavaPluginConvention javaPluginConvention = convention.getPlugin(
			JavaPluginConvention.class);
		WarPluginConvention warPluginConvention = convention.getPlugin(
			WarPluginConvention.class);

		SourceSetContainer sourceSetContainer =
			javaPluginConvention.getSourceSets();

		SourceSet extImplSourceSet = sourceSetContainer.create(
			EXT_IMPL_SOURCE_SET_NAME);
		SourceSet extKernelSourceSet = sourceSetContainer.create(
			EXT_KERNEL_SOURCE_SET_NAME);
		SourceSet extUtilBridgesSourceSet = sourceSetContainer.create(
			EXT_UTIL_BRIDGES_SOURCE_SET_NAME);
		SourceSet extUtilJavaSourceSet = sourceSetContainer.create(
			EXT_UTIL_JAVA_SOURCE_SET_NAME);
		SourceSet extUtilTaglibSourceSet = sourceSetContainer.create(
			EXT_UTIL_TAGLIB_SOURCE_SET_NAME);

		_configureSourceSetExt(warPluginConvention, extImplSourceSet);
		_configureSourceSetExt(warPluginConvention, extKernelSourceSet);
		_configureSourceSetExt(warPluginConvention, extUtilBridgesSourceSet);
		_configureSourceSetExt(warPluginConvention, extUtilJavaSourceSet);
		_configureSourceSetExt(warPluginConvention, extUtilTaglibSourceSet);

		War war = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		_configureLiferay(project);
		_configureTaskDeploy(war);

		Jar extKernelJar = _addTaskExtJar(project, extKernelSourceSet);

		Jar extUtilBridgesJar = _addTaskExtJar(
			project, extUtilBridgesSourceSet);

		Jar extUtilJavaJar = _addTaskExtJar(project, extUtilJavaSourceSet);

		Jar extUtilTaglibJar = _addTaskExtJar(project, extUtilTaglibSourceSet);

		Jar extImplJar = _addTaskExtJar(project, extImplSourceSet);

		Sync buildExtInfoBaseDirSync = _addTaskBuildExtInfoBaseDir(war);

		BuildExtInfoTask buildExtInfoTask = _addTaskBuildExtInfo(
			buildExtInfoBaseDirSync, war, portalConfiguration);

		_configureTaskBuildExtInfoBaseDir(
			buildExtInfoBaseDirSync, buildExtInfoTask);

		_configureTaskExtImplJar(
			extImplJar, extUtilBridgesJar, extUtilJavaJar, extUtilTaglibJar);
		_configureTaskWar(
			war, buildExtInfoTask, extImplJar, extKernelJar, extUtilBridgesJar,
			extUtilJavaJar, extUtilTaglibJar);

		FileCollection fileCollection = project.files(
			extKernelJar, extUtilBridgesJar, extUtilJavaJar, extUtilTaglibJar);

		extImplSourceSet.setCompileClasspath(
			portalConfiguration.plus(fileCollection));

		fileCollection = project.files(Collections.emptyList());

		extKernelSourceSet.setCompileClasspath(
			portalConfiguration.plus(fileCollection));

		fileCollection = project.files(extKernelJar);

		extUtilBridgesSourceSet.setCompileClasspath(
			portalConfiguration.plus(fileCollection));

		fileCollection = project.files(extKernelJar, extUtilBridgesJar);

		extUtilJavaSourceSet.setCompileClasspath(
			portalConfiguration.plus(fileCollection));

		fileCollection = project.files(
			extKernelJar, extUtilBridgesJar, extUtilJavaJar);

		extUtilTaglibSourceSet.setCompileClasspath(
			portalConfiguration.plus(fileCollection));
	}

	private Jar _addTaskExtJar(Project project, SourceSet extSourceSet) {
		Jar jar = GradleUtil.addTask(
			project, extSourceSet.getJarTaskName(), Jar.class);

		jar.from(extSourceSet.getOutput());

		jar.setAppendix(GUtil.toWords(extSourceSet.getName(), '-'));

		jar.setDescription(
			"Assembles a jar archive containing the " + jar.getAppendix() +
				" classes.");

		return jar;
	}

	private void _configureSourceSetExt(
		final WarPluginConvention warPluginConvention, SourceSet extSourceSet) {

		SourceDirectorySet javaSourceDirectorySet = extSourceSet.getJava();

		javaSourceDirectorySet.srcDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					String s = GUtil.toWords(extSourceSet.getName(), '-');

					return new File(
						warPluginConvention.getWebAppDir(),
						"WEB-INF/" + s + "/src");
				}

			});

		SourceDirectorySet resourcesDirectorySet = extSourceSet.getResources();

		resourcesDirectorySet.srcDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					String s = GUtil.toWords(extSourceSet.getName(), '-');

					return new File(
						warPluginConvention.getWebAppDir(),
						"WEB-INF/" + s + "/src");
				}

			});
	}

	private BuildExtInfoTask _addTaskBuildExtInfo(
		final Sync buildExtInfoBaseDirSync, final War war,
		FileCollection classpath) {

		final BuildExtInfoTask buildExtInfoTask = GradleUtil.addTask(
			buildExtInfoBaseDirSync.getProject(), BUILD_EXT_INFO_TASK_NAME,
			BuildExtInfoTask.class);

		buildExtInfoTask.dependsOn(buildExtInfoBaseDirSync);

		buildExtInfoTask.setBaseDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						buildExtInfoBaseDirSync.getDestinationDir(), "WEB-INF");
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
					String servletContextName = war.getBaseName();

					String appendix = war.getAppendix();

					if (appendix != null) {
						servletContextName =
							servletContextName + "-" + appendix;
					}

					return servletContextName;
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

	private void _configureLiferay(Project project) {
		final LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		liferayExtension.setDeployDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						liferayExtension.getAppServerParentDir(), "deploy");
				}

			});
	}

	private void _configureTaskBuildExtInfoBaseDir(
		Sync buildExtInfoBaseDirSync, final BuildExtInfoTask buildExtInfoTask) {

		buildExtInfoBaseDirSync.exclude(
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

	private void _configureTaskDeploy(War war) {
		Copy copy = (Copy)GradleUtil.getTask(
			war.getProject(), LiferayBasePlugin.DEPLOY_TASK_NAME);

		copy.from(war);
	}

	@SuppressWarnings("serial")
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
		War war, final BuildExtInfoTask buildExtInfoTask, Jar... extJars) {

		Project project = war.getProject();

		String name = project.getName();

		if (!name.endsWith("-ext")) {
			war.setAppendix("ext");
		}

		CopySpec copySpec = war.getWebInf();

		war.dependsOn(buildExtInfoTask);

		copySpec.from(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return buildExtInfoTask.getOutputFile();
				}

			});

		for (final Jar extJar : extJars) {
			copySpec.from(extJar);

			copySpec.into(
				new Callable<String>() {

					@Override
					public String call() throws Exception {
						return extJar.getAppendix();
					}

				});

			copySpec.rename(
				new Closure<String>(project) {

					@SuppressWarnings("unused")
					public String doCall(String fileName) {
						String appendix = extJar.getAppendix();
						String extension = extJar.getExtension();

						return appendix + '.' + extension;
					}

				});
		}
	}

	@SuppressWarnings("serial")
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