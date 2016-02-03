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

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.LiferayOSGiExtension;
import com.liferay.gradle.plugins.tasks.DirectDeployTask;
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.plugins.util.GradleUtil;
import com.liferay.gradle.plugins.wsdd.builder.BuildWSDDTask;
import com.liferay.gradle.plugins.wsdd.builder.WSDDBuilderPlugin;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;

import org.dm.gradle.plugins.bundle.BundleExtension;
import org.dm.gradle.plugins.bundle.BundlePlugin;
import org.dm.gradle.plugins.bundle.BundleUtils;
import org.dm.gradle.plugins.bundle.JarBuilder;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.War;
import org.gradle.internal.Factory;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayOSGiPlugin extends LiferayJavaPlugin {

	public static final String AUTO_UPDATE_XML_TASK_NAME = "autoUpdateXml";

	@Override
	public void apply(Project project) {
		super.apply(project);

		addTaskAutoUpdateXml(project);
		addTasksBuildWSDDJar(project);

		configureArchivesBaseName(project);
		configureDescription(project);
		configureSourceSetMain(project);
		configureVersion(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					LiferayOSGiExtension liferayOSGiExtension =
						GradleUtil.getExtension(
							project, LiferayOSGiExtension.class);

					configureBundleExtensionDefaults(
						project, liferayOSGiExtension);
				}

			});
	}

	protected void addCleanDeployedFile(
		Project project, final Callable<String> callable) {

		Delete delete = (Delete)GradleUtil.getTask(
			project, BasePlugin.CLEAN_TASK_NAME);

		if (!isCleanDeployed(delete)) {
			return;
		}

		final Copy copy = (Copy)GradleUtil.getTask(project, DEPLOY_TASK_NAME);

		Closure<File> closure = new Closure<File>(null) {

			@SuppressWarnings("unused")
			public File doCall() throws Exception {
				return new File(
					copy.getDestinationDir(),
					getDeployedFileName(copy.getProject(), callable.call()));
			}

		};

		delete.delete(closure);
	}

	protected DirectDeployTask addTaskAutoUpdateXml(final Project project) {
		final DirectDeployTask directDeployTask = GradleUtil.addTask(
			project, AUTO_UPDATE_XML_TASK_NAME, DirectDeployTask.class);

		directDeployTask.setAppServerDeployDir(
			directDeployTask.getTemporaryDir());
		directDeployTask.setAppServerType("tomcat");

		directDeployTask.setWebAppFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					Jar jar = (Jar)GradleUtil.getTask(
						project, JavaPlugin.JAR_TASK_NAME);

					return FileUtil.replaceExtension(
						jar.getArchivePath(), War.WAR_EXTENSION);
				}

			});

		directDeployTask.setWebAppType("portlet");

		directDeployTask.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					DirectDeployTask directDeployTask = (DirectDeployTask)task;

					Jar jar = (Jar)GradleUtil.getTask(
						directDeployTask.getProject(),
						JavaPlugin.JAR_TASK_NAME);

					File jarFile = jar.getArchivePath();

					jarFile.renameTo(directDeployTask.getWebAppFile());
				}

			});

		directDeployTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					DirectDeployTask directDeployTask = (DirectDeployTask)task;

					Project project = directDeployTask.getProject();

					File warFile = directDeployTask.getWebAppFile();

					Jar jar = (Jar)GradleUtil.getTask(
						project, JavaPlugin.JAR_TASK_NAME);

					String deployedPluginDirName = FileUtil.stripExtension(
						jar.getArchiveName());

					File deployedPluginDir = new File(
						directDeployTask.getAppServerDeployDir(),
						deployedPluginDirName);

					if (!deployedPluginDir.exists()) {
						deployedPluginDir = new File(
							directDeployTask.getAppServerDeployDir(),
							project.getName());
					}

					if (!deployedPluginDir.exists()) {
						_logger.warn(
							"Unable to automatically update web.xml in " +
								jar.getArchivePath());

						return;
					}

					FileUtil.touchFiles(
						project, deployedPluginDir, 0,
						"WEB-INF/liferay-web.xml", "WEB-INF/web.xml",
						"WEB-INF/tld/*");

					deployedPluginDirName = project.relativePath(
						deployedPluginDir);

					LiferayExtension liferayExtension = GradleUtil.getExtension(
						project, LiferayExtension.class);

					String[][] filesets = new String[][] {
						{
							project.relativePath(
								liferayExtension.getAppServerPortalDir()),
							"WEB-INF/tld/c.tld"
						},
						{
							deployedPluginDirName,
							"WEB-INF/liferay-web.xml,WEB-INF/web.xml"
						},
						{
							deployedPluginDirName, "WEB-INF/tld/*"
						}
					};

					FileUtil.jar(project, warFile, "preserve", true, filesets);

					warFile.renameTo(jar.getArchivePath());
				}

			});

		directDeployTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					Project project = task.getProject();

					LiferayOSGiExtension liferayOSGiExtension =
						GradleUtil.getExtension(
							project, LiferayOSGiExtension.class);

					if (liferayOSGiExtension.isAutoUpdateXml() &&
						FileUtil.exists(
							project, "docroot/WEB-INF/portlet.xml")) {

						return true;
					}

					return false;
				}

			});

		TaskInputs taskInputs = directDeployTask.getInputs();

		taskInputs.file(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					Jar jar = (Jar)GradleUtil.getTask(
						project, JavaPlugin.JAR_TASK_NAME);

					return jar.getArchivePath();
				}

			});

		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		jar.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					directDeployTask.execute();
				}

			});

		return directDeployTask;
	}

	protected Jar addTaskBuildWSDDJar(final BuildWSDDTask buildWSDDTask) {
		Project project = buildWSDDTask.getProject();

		final Jar jar = GradleUtil.addTask(
			project, buildWSDDTask.getName() + "Jar", Jar.class);

		jar.dependsOn(buildWSDDTask);

		String taskName = buildWSDDTask.getName();

		if (taskName.equals(WSDDBuilderPlugin.BUILD_WSDD_TASK_NAME)) {
			jar.setAppendix("wsdd");
		}
		else {
			jar.setAppendix("wsdd-" + taskName);
		}

		jar.deleteAllActions();

		jar.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					BundleExtension bundleExtension = GradleUtil.getExtension(
						project, BundleExtension.class);

					Factory<JarBuilder> jarBuilderFactory =
						bundleExtension.getJarBuilderFactory();

					JarBuilder jarBuilder = jarBuilderFactory.create();

					jarBuilder.withBase(BundleUtils.getBase(project));

					SourceSet sourceSet = GradleUtil.getSourceSet(
						project, SourceSet.MAIN_SOURCE_SET_NAME);

					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					jarBuilder.withClasspath(
						new File[] {
							sourceSetOutput.getClassesDir(),
							sourceSetOutput.getResourcesDir()
						});

					LiferayOSGiExtension liferayOSGiExtension =
						GradleUtil.getExtension(
							project, LiferayOSGiExtension.class);

					Map<String, String> properties =
						liferayOSGiExtension.getBundleDefaultInstructions();

					String bundleName = getBundleInstruction(
						project, Constants.BUNDLE_NAME);

					properties.put(
						Constants.BUNDLE_NAME,
						bundleName + " WSDD descriptors");

					String bundleSymbolicName = getBundleInstruction(
						project, Constants.BUNDLE_SYMBOLICNAME);

					properties.put(
						Constants.BUNDLE_SYMBOLICNAME,
						bundleSymbolicName + ".wsdd");
					properties.put(Constants.FRAGMENT_HOST, bundleSymbolicName);
					properties.put(
						Constants.IMPORT_PACKAGE,
						"javax.servlet,javax.servlet.http");

					StringBuilder sb = new StringBuilder();

					sb.append("WEB-INF/=");
					sb.append(
						FileUtil.getRelativePath(
							project, buildWSDDTask.getServerConfigFile()));
					sb.append(',');
					sb.append(
						FileUtil.getRelativePath(
							project, buildWSDDTask.getOutputDir()));
					sb.append(";filter:=*.wsdd");

					properties.put(Constants.INCLUDE_RESOURCE, sb.toString());

					jarBuilder.withProperties(properties);

					jarBuilder.withName(
						properties.get(Constants.BUNDLE_SYMBOLICNAME));
					jarBuilder.withResources(new File[0]);
					jarBuilder.withSourcepath(BundleUtils.getSources(project));
					jarBuilder.withTrace(bundleExtension.isTrace());
					jarBuilder.withVersion(BundleUtils.getVersion(project));

					TaskOutputs taskOutputs = task.getOutputs();

					FileCollection fileCollection = taskOutputs.getFiles();

					jarBuilder.writeJarTo(fileCollection.getSingleFile());
				}

			});

		buildWSDDTask.finalizedBy(jar);

		addCleanDeployedFile(
			project,
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return jar.getArchiveName();
				}

			});

		Task task = GradleUtil.getTask(project, DEPLOY_TASK_NAME);

		if (task instanceof Copy) {
			Copy copy = (Copy)task;

			copy.from(
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						return jar.getArchivePath();
					}

				});
		}

		return jar;
	}

	@Override
	protected Copy addTaskDeploy(
		final Project project, LiferayExtension liferayExtension) {

		Copy copy = super.addTaskDeploy(project, liferayExtension);

		copy.rename(
			new Closure<String>(null) {

				@SuppressWarnings("unused")
				public String doCall(String fileName) {
					return getDeployedFileName(project, fileName);
				}

			});

		return copy;
	}

	protected void addTasksBuildWSDDJar(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildWSDDTask.class,
			new Action<BuildWSDDTask>() {

				@Override
				public void execute(BuildWSDDTask buildWSDDTask) {
					addTaskBuildWSDDJar(buildWSDDTask);
				}

			});
	}

	@Override
	protected void applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, BundlePlugin.class);

		configureBundleExtension(project);

		super.applyPlugins(project);
	}

	protected void configureArchivesBaseName(Project project) {
		BasePluginConvention basePluginConvention = GradleUtil.getConvention(
			project, BasePluginConvention.class);

		String bundleSymbolicName = getBundleInstruction(
			project, Constants.BUNDLE_SYMBOLICNAME);

		basePluginConvention.setArchivesBaseName(bundleSymbolicName);
	}

	protected void configureBundleExtension(Project project) {
		replaceJarBuilderFactory(project);

		Map<String, String> bundleInstructions = getBundleInstructions(project);

		Properties bundleProperties = null;

		try {
			bundleProperties = FileUtil.readProperties(project, "bnd.bnd");
		}
		catch (Exception e) {
			throw new GradleException("Unable to read bundle properties", e);
		}

		Enumeration<Object> keys = bundleProperties.keys();

		while (keys.hasMoreElements()) {
			String key = (String)keys.nextElement();

			String value = bundleProperties.getProperty(key);

			bundleInstructions.put(key, value);
		}
	}

	protected void configureBundleExtensionDefaults(
		Project project, LiferayOSGiExtension liferayOSGiExtension) {

		Map<String, String> bundleInstructions = getBundleInstructions(project);

		Map<String, String> bundleDefaultInstructions =
			liferayOSGiExtension.getBundleDefaultInstructions();

		for (Map.Entry<String, String> entry :
				bundleDefaultInstructions.entrySet()) {

			String key = entry.getKey();

			if (!bundleInstructions.containsKey(key)) {
				bundleInstructions.put(key, entry.getValue());
			}
		}
	}

	protected void configureDescription(Project project) {
		String description = getBundleInstruction(
			project, Constants.BUNDLE_DESCRIPTION);

		if (Validator.isNull(description)) {
			description = getBundleInstruction(project, Constants.BUNDLE_NAME);
		}

		project.setDescription(description);
	}

	protected void configureSourceSetMain(Project project) {
		File docrootDir = project.file("docroot");

		if (!docrootDir.exists()) {
			return;
		}

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		File classesDir = new File(docrootDir, "WEB-INF/classes");

		sourceSetOutput.setClassesDir(classesDir);
		sourceSetOutput.setResourcesDir(classesDir);

		SourceDirectorySet javaSourceDirectorySet = sourceSet.getJava();

		File srcDir = new File(docrootDir, "WEB-INF/src");

		Set<File> srcDirs = Collections.singleton(srcDir);

		javaSourceDirectorySet.setSrcDirs(srcDirs);

		SourceDirectorySet resourcesSourceDirectorySet =
			sourceSet.getResources();

		resourcesSourceDirectorySet.setSrcDirs(srcDirs);
	}

	protected void configureVersion(Project project) {
		String bundleVersion = getBundleInstruction(
			project, Constants.BUNDLE_VERSION);

		project.setVersion(bundleVersion);
	}

	protected String getBundleInstruction(Project project, String key) {
		Map<String, String> bundleInstructions = getBundleInstructions(project);

		return bundleInstructions.get(key);
	}

	protected Map<String, String> getBundleInstructions(Project project) {
		BundleExtension bundleExtension = GradleUtil.getExtension(
			project, BundleExtension.class);

		return (Map<String, String>)bundleExtension.getInstructions();
	}

	@Override
	protected String getDeployedFileName(Project project, File sourceFile) {
		return getDeployedFileName(project, sourceFile.getName());
	}

	protected String getDeployedFileName(
		Project project, String sourceFileName) {

		return sourceFileName.replace(
			"-" + project.getVersion() + "." + Jar.DEFAULT_EXTENSION,
			"." + Jar.DEFAULT_EXTENSION);
	}

	@Override
	protected Class<? extends LiferayExtension> getLiferayExtensionClass() {
		return LiferayOSGiExtension.class;
	}

	protected void replaceJarBuilderFactory(Project project) {
		BundleExtension bundleExtension = GradleUtil.getExtension(
			project, BundleExtension.class);

		bundleExtension.setJarBuilderFactory(new LiferayJarBuilderFactory());
	}

	private static final Logger _logger = Logging.getLogger(
		LiferayOSGiPlugin.class);

	private static class LiferayJarBuilder extends JarBuilder {

		@Override
		public JarBuilder withClasspath(Object files) {
			List<File> filesList = new ArrayList<>(
				Arrays.asList((File[])files));

			Iterator<File> iterator = filesList.iterator();

			while (iterator.hasNext()) {
				File file = iterator.next();

				String fileName = file.getName();

				if (_classpathFiles.contains(file) ||
					fileName.endsWith(".pom") || !file.exists()) {

					iterator.remove();

					continue;
				}

				_classpathFiles.add(file);

				if (_logger.isInfoEnabled()) {
					_logger.info("CLASSPATH: {}", file.getAbsolutePath());
				}
			}

			return super.withClasspath(
				filesList.toArray(new File[filesList.size()]));
		}

		@Override
		public JarBuilder withResources(Object files) {
			List<File> filesList = new ArrayList<>(
				Arrays.asList((File[])files));

			Iterator<File> iterator = filesList.iterator();

			while (iterator.hasNext()) {
				File file = iterator.next();

				if (_resourceFiles.contains(file) || !file.exists()) {
					iterator.remove();

					continue;
				}

				_resourceFiles.add(file);

				if (_logger.isInfoEnabled()) {
					_logger.info("RESOURCE: {}", file.getAbsolutePath());
				}
			}

			return super.withResources(
				filesList.toArray(new File[filesList.size()]));
		}

		private final Set<File> _classpathFiles = new HashSet<>();
		private final Set<File> _resourceFiles = new HashSet<>();

	}

	private static class LiferayJarBuilderFactory
		implements Factory<JarBuilder> {

		@Override
		public JarBuilder create() {
			return new LiferayJarBuilder();
		}

	}

}