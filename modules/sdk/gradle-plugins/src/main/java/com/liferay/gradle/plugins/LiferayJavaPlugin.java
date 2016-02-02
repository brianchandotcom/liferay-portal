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
import com.liferay.gradle.plugins.extensions.AppServer;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.jasper.jspc.JspCPlugin;
import com.liferay.gradle.plugins.javadoc.formatter.JavadocFormatterPlugin;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorPlugin;
import com.liferay.gradle.plugins.js.transpiler.JSTranspilerPlugin;
import com.liferay.gradle.plugins.lang.builder.LangBuilderPlugin;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.soy.BuildSoyTask;
import com.liferay.gradle.plugins.soy.SoyPlugin;
import com.liferay.gradle.plugins.tasks.DirectDeployTask;
import com.liferay.gradle.plugins.test.integration.TestIntegrationPlugin;
import com.liferay.gradle.plugins.tld.formatter.TLDFormatterPlugin;
import com.liferay.gradle.plugins.tlddoc.builder.TLDDocBuilderPlugin;
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.plugins.util.GradleUtil;
import com.liferay.gradle.plugins.whip.WhipPlugin;
import com.liferay.gradle.plugins.xml.formatter.XMLFormatterPlugin;
import com.liferay.gradle.util.StringUtil;

import groovy.lang.Closure;

import java.io.File;

import java.nio.charset.StandardCharsets;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.DependencyResolveDetails;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ModuleVersionSelector;
import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.testing.Test;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayJavaPlugin implements Plugin<Project> {

	public static final String AUTO_CLEAN_PROPERTY_NAME = "autoClean";

	public static final String CLEAN_DEPLOYED_PROPERTY_NAME = "cleanDeployed";

	public static final String DEPLOY_TASK_NAME = "deploy";

	public static final String PORTAL_CONFIGURATION_NAME = "portal";

	@Override
	public void apply(Project project) {
		final LiferayExtension liferayExtension = addLiferayExtension(project);

		addConfigurationPortal(project, liferayExtension);

		applyPlugins(project);

		addTaskDeploy(project, liferayExtension);

		configureConfigurations(project, liferayExtension);
		configureTaskClean(project);
		configureTaskTest(project);
		configureTasksDirectDeploy(project, liferayExtension);
		configureTasksTest(project);
	}

	protected void addCleanDeployedFile(Project project, File sourceFile) {
		Delete delete = (Delete)GradleUtil.getTask(
			project, BasePlugin.CLEAN_TASK_NAME);

		if (!isCleanDeployed(delete)) {
			return;
		}

		Copy copy = (Copy)GradleUtil.getTask(project, DEPLOY_TASK_NAME);

		File deployedFile = new File(
			copy.getDestinationDir(), getDeployedFileName(project, sourceFile));

		delete.delete(deployedFile);
	}

	protected Configuration addConfigurationPortal(
		final Project project, final LiferayExtension liferayExtension) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, PORTAL_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					addDependenciesPortal(project, liferayExtension);
				}

			});

		configuration.setDescription(
			"Configures the classpath from the local Liferay bundle.");
		configuration.setVisible(false);

		return configuration;
	}

	protected void addDependenciesPortal(
		Project project, LiferayExtension liferayExtension) {

		File appServerClassesPortalDir = new File(
			liferayExtension.getAppServerPortalDir(), "WEB-INF/classes");

		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, appServerClassesPortalDir);

		File appServerLibPortalDir = new File(
			liferayExtension.getAppServerPortalDir(), "WEB-INF/lib");

		FileTree appServerLibPortalDirJarFiles = FileUtil.getJarsFileTree(
			project, appServerLibPortalDir);

		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, appServerLibPortalDirJarFiles);

		FileTree appServerLibGlobalDirJarFiles = FileUtil.getJarsFileTree(
			project, liferayExtension.getAppServerLibGlobalDir(), "mail.jar");

		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, appServerLibGlobalDirJarFiles);

		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, "com.liferay", "net.sf.jargs",
			"1.0");
		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, "com.thoughtworks.qdox", "qdox",
			"1.12.1");
		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, "javax.activation",
			"activation", "1.1");
		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, "javax.servlet",
			"javax.servlet-api", "3.0.1");
		GradleUtil.addDependency(
			project, PORTAL_CONFIGURATION_NAME, "javax.servlet.jsp", "jsp-api",
			"2.1");

		AppServer appServer = liferayExtension.getAppServer();

		appServer.addAdditionalDependencies(PORTAL_CONFIGURATION_NAME);
	}

	protected LiferayExtension addLiferayExtension(Project project) {
		LiferayExtension liferayExtension = GradleUtil.addExtension(
			project, LiferayPlugin.PLUGIN_NAME, getLiferayExtensionClass());

		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/dependencies/config-liferay.gradle",
			project);

		return liferayExtension;
	}

	protected Copy addTaskDeploy(
		Project project, final LiferayExtension liferayExtension) {

		Copy copy = GradleUtil.addTask(project, DEPLOY_TASK_NAME, Copy.class);

		final Jar jar = (Jar)GradleUtil.getTask(
			project, JavaPlugin.JAR_TASK_NAME);

		copy.from(jar);

		copy.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return liferayExtension.getDeployDir();
				}

			});

		copy.setDescription("Assembles the project and deploys it to Liferay.");

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					addCleanDeployedFile(project, jar.getArchivePath());
				}

			});

		return copy;
	}

	protected void applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		GradleUtil.applyPlugin(project, AlloyTaglibDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, CSSBuilderDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, CSSBuilderPlugin.class);
		GradleUtil.applyPlugin(project, EclipseDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, IdeaDefaultsPlugin.class);
		GradleUtil.applyPlugin(
			project, JSModuleConfigGeneratorDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, JSModuleConfigGeneratorPlugin.class);
		GradleUtil.applyPlugin(project, JSTranspilerDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, JSTranspilerPlugin.class);
		GradleUtil.applyPlugin(project, JavadocFormatterDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, JavadocFormatterPlugin.class);
		GradleUtil.applyPlugin(project, JspCDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, JspCPlugin.class);
		GradleUtil.applyPlugin(project, LangBuilderDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, LangBuilderPlugin.class);
		GradleUtil.applyPlugin(project, NodeDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, ServiceBuilderDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
		GradleUtil.applyPlugin(project, SoyPlugin.class);
		GradleUtil.applyPlugin(project, TLDDocBuilderPlugin.class);
		GradleUtil.applyPlugin(project, TLDFormatterDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, TLDFormatterPlugin.class);
		GradleUtil.applyPlugin(project, TestIntegrationDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, TestIntegrationPlugin.class);
		GradleUtil.applyPlugin(
			project, UpgradeTableBuilderDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, WhipDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, WhipPlugin.class);
		GradleUtil.applyPlugin(project, WSDDBuilderDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, XMLFormatterDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, XMLFormatterPlugin.class);
	}

	protected void configureConfigurations(
		Project project, final LiferayExtension liferayExtension) {

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Action<Configuration> action = new Action<Configuration>() {

			@Override
			public void execute(Configuration configuration) {
				ResolutionStrategy resolutionStrategy =
					configuration.getResolutionStrategy();

				resolutionStrategy.eachDependency(
					new Action<DependencyResolveDetails>() {

						@Override
						public void execute(
							DependencyResolveDetails dependencyResolveDetails) {

							ModuleVersionSelector moduleVersionSelector =
								dependencyResolveDetails.getRequested();

							String group = moduleVersionSelector.getGroup();
							String version = moduleVersionSelector.getVersion();

							if (group.equals("com.liferay.portal") &&
								version.equals("default")) {

								dependencyResolveDetails.useVersion(
									liferayExtension.getPortalVersion());
							}
						}

					});
			}

		};

		configurationContainer.all(action);
	}

	protected void configureTaskClean(Project project) {
		Task task = GradleUtil.getTask(project, BasePlugin.CLEAN_TASK_NAME);

		if (task instanceof Delete) {
			configureTaskCleanDependsOn((Delete)task);
		}
	}

	protected void configureTaskCleanDependsOn(Delete delete) {
		Closure<Set<String>> closure = new Closure<Set<String>>(null) {

			@SuppressWarnings("unused")
			public Set<String> doCall(Delete delete) {
				Set<String> cleanTaskNames = new HashSet<>();

				Project project = delete.getProject();

				for (Task task : project.getTasks()) {
					String taskName = task.getName();

					if (taskName.equals(DEPLOY_TASK_NAME) ||
						taskName.equals(
							EclipsePlugin.getECLIPSE_CP_TASK_NAME()) ||
						taskName.equals(
							EclipsePlugin.getECLIPSE_PROJECT_TASK_NAME()) ||
						taskName.equals("ideaModule") ||
						(task instanceof BuildSoyTask)) {

						continue;
					}

					boolean autoClean = GradleUtil.getProperty(
						task, AUTO_CLEAN_PROPERTY_NAME, true);

					if (!autoClean) {
						continue;
					}

					TaskOutputs taskOutputs = task.getOutputs();

					if (!taskOutputs.getHasOutput()) {
						continue;
					}

					cleanTaskNames.add(
						BasePlugin.CLEAN_TASK_NAME +
							StringUtil.capitalize(taskName));
				}

				return cleanTaskNames;
			}

		};

		delete.dependsOn(closure);
	}

	protected void configureTaskDirectDeploy(
		DirectDeployTask directDeployTask,
		final LiferayExtension liferayExtension) {

		directDeployTask.setAppServerDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return liferayExtension.getAppServerDir();
				}

			});

		directDeployTask.setAppServerLibGlobalDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return liferayExtension.getAppServerLibGlobalDir();
				}

			});

		directDeployTask.setAppServerPortalDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return liferayExtension.getAppServerPortalDir();
				}

			});

		directDeployTask.setAppServerType(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return liferayExtension.getAppServerType();
				}

			});
	}

	protected void configureTasksDirectDeploy(
		Project project, final LiferayExtension liferayExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			DirectDeployTask.class,
			new Action<DirectDeployTask>() {

				@Override
				public void execute(DirectDeployTask directDeployTask) {
					configureTaskDirectDeploy(
						directDeployTask, liferayExtension);
				}

			});
	}

	protected void configureTasksTest(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			Test.class,
			new Action<Test>() {

				@Override
				public void execute(Test test) {
					configureTaskTestDefaultCharacterEncoding(test);
				}

			});
	}

	protected void configureTaskTest(Project project) {
		final Test test = (Test)GradleUtil.getTask(
			project, JavaPlugin.TEST_TASK_NAME);

		test.jvmArgs("-Djava.net.preferIPv4Stack=true");
		test.jvmArgs("-Dliferay.mode=test");
		test.jvmArgs("-Duser.timezone=GMT");

		test.setForkEvery(1L);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTaskTestIncludes(test);
				}

			});
	}

	protected void configureTaskTestDefaultCharacterEncoding(Test test) {
		test.setDefaultCharacterEncoding(StandardCharsets.UTF_8.name());
	}

	protected void configureTaskTestIncludes(Test test) {
		Set<String> includes = test.getIncludes();

		if (includes.isEmpty()) {
			test.setIncludes(Collections.singleton("**/*Test.class"));
		}
	}

	protected String getDeployedFileName(Project project, File sourceFile) {
		return sourceFile.getName();
	}

	protected Class<? extends LiferayExtension> getLiferayExtensionClass() {
		return LiferayExtension.class;
	}

	protected boolean isCleanDeployed(Delete delete) {
		return GradleUtil.getProperty(
			delete, CLEAN_DEPLOYED_PROPERTY_NAME, true);
	}

}