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
import com.liferay.gradle.plugins.extensions.LiferayOSGiExtension;
import com.liferay.gradle.plugins.service.builder.BuildServiceTask;
import com.liferay.gradle.plugins.tasks.DirectDeployTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.dm.gradle.plugins.bundle.BundleExtension;
import org.dm.gradle.plugins.bundle.BundlePlugin;
import org.dm.gradle.plugins.bundle.JarBuilder;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskInputs;
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

		configureBundleExtension(project);
	}

	@Override
	protected LiferayExtension addLiferayExtension(Project project) {
		return GradleUtil.addExtension(
			project, LiferayPlugin.PLUGIN_NAME, LiferayOSGiExtension.class);
	}

	protected DirectDeployTask addTaskAutoUpdateXml(final Project project) {
		DirectDeployTask directDeployTask = GradleUtil.addTask(
			project, AUTO_UPDATE_XML_TASK_NAME, DirectDeployTask.class);

		directDeployTask.setAppServerDeployDir(
			directDeployTask.getTemporaryDir());
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

					String deployedPluginDirName = jar.getArchiveName();

					deployedPluginDirName = deployedPluginDirName.substring(
						0, deployedPluginDirName.lastIndexOf('.'));

					File deployedPluginDir = new File(
						directDeployTask.getAppServerDeployDir(),
						deployedPluginDirName);

					if (!deployedPluginDir.exists()) {
						deployedPluginDir = new File(
							directDeployTask.getAppServerDeployDir(),
							project.getName());
					}

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

		jar.finalizedBy(directDeployTask);

		return directDeployTask;
	}

	@Override
	protected void addTasks(Project project) {
		super.addTasks(project);

		addTaskAutoUpdateXml(project);
	}

	@Override
	protected void applyConfigScripts(Project project) {
		super.applyConfigScripts(project);

		GradleUtil.applyScript(project, "config-bundle.gradle", project);
	}

	@Override
	protected void applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, BundlePlugin.class);

		replaceJarBuilderFactory(project);

		super.applyPlugins(project);
	}

	protected void configureBundleExtension(Project project) {
		Map<String, String> bundleInstructions = getBundleInstructions(project);

		Properties bundleProperties;

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

	@Override
	protected void configureSourceSetMain(Project project) {
		File docrootDir = project.file("docroot");

		if (!docrootDir.exists()) {
			super.configureSourceSetMain(project);

			return;
		}

		File classesDir = new File(docrootDir, "WEB-INF/classes");
		File srcDir = new File(docrootDir, "WEB-INF/src");

		configureSourceSetMain(project, classesDir, srcDir);
	}

	protected void configureTaskAutoUpdateXml(Project project) {
		DirectDeployTask directDeployTask =
			(DirectDeployTask)GradleUtil.getTask(
				project, AUTO_UPDATE_XML_TASK_NAME);

		configureTaskAutoUpdateXmlWebAppFile(directDeployTask);
	}

	protected void configureTaskAutoUpdateXmlWebAppFile(
		DirectDeployTask directDeployTask) {

		Jar jar = (Jar)GradleUtil.getTask(
			directDeployTask.getProject(), JavaPlugin.JAR_TASK_NAME);

		File warFile = FileUtil.replaceExtension(
			jar.getArchivePath(), War.WAR_EXTENSION);

		directDeployTask.setWebAppFile(warFile);
	}

	@Override
	protected void configureTaskBuildServiceOsgiModule(
		BuildServiceTask buildServiceTask) {

		buildServiceTask.setOsgiModule(true);
	}

	@Override
	protected void configureTaskBuildServicePluginName(
		BuildServiceTask buildServiceTask) {

		buildServiceTask.setPluginName("");
	}

	@Override
	protected void configureTaskBuildServiceSpringNamespaces(
		BuildServiceTask buildServiceTask) {

		buildServiceTask.setSpringNamespaces(new String[] {"beans", "osgi"});
	}

	protected void configureTaskJar(Project project) {
		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		configureTaskJarArchiveName(jar);
	}

	protected void configureTaskJarArchiveName(Jar jar) {
		String bundleSymbolicName = getBundleInstruction(
			jar.getProject(), "Bundle-SymbolicName");

		if (Validator.isNull(bundleSymbolicName)) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(bundleSymbolicName);
		sb.append("-");
		sb.append(jar.getVersion());
		sb.append(".");
		sb.append(jar.getExtension());

		jar.setArchiveName(sb.toString());
	}

	@Override
	protected void configureTasks(
		Project project, LiferayExtension liferayExtension) {

		super.configureTasks(project, liferayExtension);

		configureTaskJar(project);

		configureTaskAutoUpdateXml(project);
	}

	@Override
	protected void configureVersion(
		Project project, LiferayExtension liferayExtension) {

		String bundleVersion = getBundleInstruction(project, "Bundle-Version");

		if (Validator.isNotNull(bundleVersion)) {
			project.setVersion(bundleVersion);

			return;
		}

		super.configureVersion(project, liferayExtension);
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
	protected File getLibDir(Project project) {
		File docrootDir = project.file("docroot");

		if (!docrootDir.exists()) {
			return super.getLibDir(project);
		}

		return new File(docrootDir, "WEB-INF/lib");
	}

	@Override
	protected File getServiceBaseDir(Project project) {
		File docrootDir = project.file("docroot");

		if (!docrootDir.exists()) {
			return super.getServiceBaseDir(project);
		}

		return new File(docrootDir, "WEB-INF");
	}

	protected void replaceJarBuilderFactory(Project project) {
		BundleExtension bundleExtension = GradleUtil.getExtension(
			project, BundleExtension.class);

		bundleExtension.setJarBuilderFactory(
			new Factory<JarBuilder>() {

				@Override
				public JarBuilder create() {
					return new LiferayJarBuilder();
				}

			});
	}

	private static class LiferayJarBuilder extends JarBuilder {

		@Override
		public JarBuilder withResources(Object files) {

			// Prevent BundlePlugin from adding
			// sourceSets.main.output.classesDir/resourcesDir.

			return this;
		}

	}

}