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

import com.liferay.gradle.plugins.css.builder.BuildCSSTask;
import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.extensions.AppServer;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.TomcatAppServer;
import com.liferay.gradle.plugins.jasper.jspc.JspCPlugin;
import com.liferay.gradle.plugins.javadoc.formatter.JavadocFormatterPlugin;
import com.liferay.gradle.plugins.js.module.config.generator.ConfigJSModulesTask;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorPlugin;
import com.liferay.gradle.plugins.js.transpiler.JSTranspilerPlugin;
import com.liferay.gradle.plugins.js.transpiler.TranspileJSTask;
import com.liferay.gradle.plugins.lang.builder.BuildLangTask;
import com.liferay.gradle.plugins.lang.builder.LangBuilderPlugin;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;
import com.liferay.gradle.plugins.patcher.PatchTask;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.soy.SoyPlugin;
import com.liferay.gradle.plugins.tasks.AppServerTask;
import com.liferay.gradle.plugins.tasks.DirectDeployTask;
import com.liferay.gradle.plugins.tasks.InitGradleTask;
import com.liferay.gradle.plugins.tasks.SetupTestableTomcatTask;
import com.liferay.gradle.plugins.tasks.StartAppServerTask;
import com.liferay.gradle.plugins.tasks.StopAppServerTask;
import com.liferay.gradle.plugins.tld.formatter.TLDFormatterPlugin;
import com.liferay.gradle.plugins.upgrade.table.builder.BuildUpgradeTableTask;
import com.liferay.gradle.plugins.whip.WhipPlugin;
import com.liferay.gradle.plugins.whip.WhipTaskExtension;
import com.liferay.gradle.plugins.wsdl.builder.BuildWSDLTask;
import com.liferay.gradle.plugins.xml.formatter.FormatXMLTask;
import com.liferay.gradle.plugins.xml.formatter.XMLFormatterPlugin;
import com.liferay.gradle.plugins.xsd.builder.BuildXSDTask;
import com.liferay.gradle.plugins.xsd.builder.XSDBuilderPlugin;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

import nebula.plugin.extraconfigurations.OptionalBasePlugin;
import nebula.plugin.extraconfigurations.ProvidedBasePlugin;

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencyResolveDetails;
import org.gradle.api.artifacts.ModuleVersionSelector;
import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.artifacts.maven.Conf2ScopeMapping;
import org.gradle.api.artifacts.maven.Conf2ScopeMappingContainer;
import org.gradle.api.execution.TaskExecutionGraph;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.ConventionMapping;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.MavenPlugin;
import org.gradle.api.plugins.MavenPluginConvention;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.StopExecutionException;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.Zip;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.api.tasks.testing.Test;

import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayJavaPlugin implements Plugin<Project> {

	public static final String AUTO_CLEAN_PROPERTY_NAME = "autoClean";

	public static final String CLEAN_DEPLOYED_PROPERTY_NAME = "cleanDeployed";

	public static final String DELETE_LIFERAY_HOME_PROPERTY_NAME =
		"deleteLiferayHome";

	public static final String DEPLOY_TASK_NAME = "deploy";

	public static final String FORMAT_WSDL_TASK_NAME = "formatWSDL";

	public static final String FORMAT_XSD_TASK_NAME = "formatXSD";

	public static final String INIT_GRADLE_TASK_NAME = "initGradle";

	public static final String JAR_SOURCES_TASK_NAME = "jarSources";

	public static final String SETUP_ARQUILLIAN_TASK_NAME = "setupArquillian";

	public static final String SETUP_TESTABLE_TOMCAT_TASK_NAME =
		"setupTestableTomcat";

	public static final String START_TESTABLE_TOMCAT_TASK_NAME =
		"startTestableTomcat";

	public static final String STOP_TESTABLE_TOMCAT_TASK_NAME =
		"stopTestableTomcat";

	public static final String TEST_INTEGRATION_SOURCE_SET_NAME =
		"testIntegration";

	public static final String TEST_INTEGRATION_TASK_NAME = "testIntegration";

	public static final String ZIP_JAVADOC_TASK_NAME = "zipJavadoc";

	@Override
	public void apply(Project project) {
		addLiferayExtension(project);

		configureTasksBuildCSS(project);

		applyPlugins(project);

		configureConf2ScopeMappings(project);
		configureConfigurations(project);
		configureDependencies(project);
		configureProperties(project);
		configureSourceSets(project);

		addConfigurations(project);
		addTasks(project);

		applyConfigScripts(project);

		configureArtifacts(project);

		configureTaskBuildXSD(project);
		configureTaskConfigJSModules(project);
		configureTaskTranspileJS(project);
		configureTasksBuildUpgradeTable(project);
		configureTasksTest(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					LiferayExtension liferayExtension = GradleUtil.getExtension(
						project, LiferayExtension.class);

					addDependenciesJspC(project, liferayExtension);

					configureVersion(project, liferayExtension);

					configureTasks(project, liferayExtension);
				}

			});
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

	protected void addConfigurations(Project project) {
	}

	protected void addDependenciesJspC(
		Project project, LiferayExtension liferayExtension) {

		GradleUtil.addDependency(
			project, JspCPlugin.CONFIGURATION_NAME,
			liferayExtension.getAppServerLibGlobalDir());
	}

	protected LiferayExtension addLiferayExtension(Project project) {
		return GradleUtil.addExtension(
			project, LiferayPlugin.PLUGIN_NAME, LiferayExtension.class);
	}

	protected SourceSet addSourceSetTestIntegration(Project project) {
		SourceSet testIntegrationSourceSet = GradleUtil.addSourceSet(
			project, TEST_INTEGRATION_SOURCE_SET_NAME);

		Configuration testIntegrationCompileConfiguration =
			GradleUtil.getConfiguration(
				project,
				testIntegrationSourceSet.getCompileConfigurationName());

		Configuration testCompileConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME);

		testIntegrationCompileConfiguration.extendsFrom(
			testCompileConfiguration);

		Configuration testIntegrationRuntimeConfiguration =
			GradleUtil.getConfiguration(
				project,
				testIntegrationSourceSet.getRuntimeConfigurationName());

		Configuration testRuntimeConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.TEST_RUNTIME_CONFIGURATION_NAME);

		testIntegrationRuntimeConfiguration.extendsFrom(
			testRuntimeConfiguration, testIntegrationCompileConfiguration);

		SourceSet mainSourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		FileCollection compileClasspath =
			testIntegrationSourceSet.getCompileClasspath();

		testIntegrationSourceSet.setCompileClasspath(
			compileClasspath.plus(mainSourceSet.getOutput()));

		FileCollection runtimeClasspath =
			testIntegrationSourceSet.getRuntimeClasspath();

		testIntegrationSourceSet.setRuntimeClasspath(
			runtimeClasspath.plus(mainSourceSet.getOutput()));

		return testIntegrationSourceSet;
	}

	protected Copy addTaskDeploy(Project project) {
		Copy copy = GradleUtil.addTask(project, DEPLOY_TASK_NAME, Copy.class);

		copy.setDescription("Assembles the project and deploys it to Liferay.");

		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		copy.from(jar);

		GradleUtil.setProperty(copy, AUTO_CLEAN_PROPERTY_NAME, false);

		return copy;
	}

	protected FormatXMLTask addTaskFormatWSDL(
		final BuildWSDLTask buildWSDLTask) {

		Project project = buildWSDLTask.getProject();

		TaskContainer taskContainer = project.getTasks();

		FormatXMLTask formatXMLTask = taskContainer.maybeCreate(
			FORMAT_WSDL_TASK_NAME, FormatXMLTask.class);

		formatXMLTask.setDescription(
			"Runs Liferay XML Formatter to format WSDL files.");
		formatXMLTask.setIncludes(Collections.singleton("**/*.wsdl"));

		formatXMLTask.source(
			new Callable<File>() {

			@Override
			public File call() throws Exception {
				return buildWSDLTask.getInputDir();
			}

		});

		return formatXMLTask;
	}

	protected FormatXMLTask addTaskFormatXSD(Project project) {
		FormatXMLTask formatXMLTask = GradleUtil.addTask(
			project, FORMAT_XSD_TASK_NAME, FormatXMLTask.class);

		formatXMLTask.setDescription(
			"Runs Liferay XML Formatter to format XSD files.");

		return formatXMLTask;
	}

	protected InitGradleTask addTaskInitGradle(Project project) {
		InitGradleTask initGradleTask = GradleUtil.addTask(
			project, INIT_GRADLE_TASK_NAME, InitGradleTask.class);

		initGradleTask.setDescription(
			"Initializes build.gradle by migrating information from legacy " +
				"files.");

		initGradleTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					try {
						Project project = task.getProject();

						File buildGradleFile = project.file("build.gradle");

						if (!buildGradleFile.exists() ||
							(buildGradleFile.length() == 0)) {

							return true;
						}

						long buildGradleLastModified = _getLastModified(
							buildGradleFile);

						for (String sourceFileName :
								InitGradleTask.SOURCE_FILE_NAMES) {

							File sourceFile = project.file(sourceFileName);

							if (sourceFile.exists() &&
								(buildGradleLastModified <
									_getLastModified(sourceFile))) {

								return true;
							}
						}

						return false;
					}
					catch (IOException ioe) {
					}
					catch (Exception e) {
						if (_logger.isWarnEnabled()) {
							_logger.warn(e.getMessage(), e);
						}
					}

					return true;
				}

				private long _getLastModified(File file) throws Exception {
					ProcessExecutor processExecutor = new ProcessExecutor(
						"git", "log", "--format=%at", "--max-count=1",
						file.getName());

					processExecutor.directory(file.getParentFile());
					processExecutor.exitValueNormal();
					processExecutor.readOutput(true);

					ProcessResult processResult =
						processExecutor.executeNoTimeout();

					String output = processResult.outputUTF8();

					return Long.parseLong(output.trim());
				}

			});

		return initGradleTask;
	}

	protected Jar addTaskJarSources(Project project) {
		final Jar jar = GradleUtil.addTask(
			project, JAR_SOURCES_TASK_NAME, Jar.class);

		jar.setClassifier("sources");
		jar.setGroup(BasePlugin.BUILD_GROUP);
		jar.setDescription(
			"Assembles a jar archive containing the main source files.");
		jar.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);

		File docrootDir = project.file("docroot");

		if (docrootDir.exists()) {
			jar.from(docrootDir);
		}
		else {
			SourceSet sourceSet = GradleUtil.getSourceSet(
				project, SourceSet.MAIN_SOURCE_SET_NAME);

			jar.from(sourceSet.getAllSource());
		}

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PatchTask.class,
			new Action<PatchTask>() {

				@Override
				public void execute(final PatchTask patchTask) {
					jar.from(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return patchTask.getPatchesDir();
							}

						},
						new Closure<Void>(null) {

							@SuppressWarnings("unused")
							public void doCall(CopySpec copySpec) {
								copySpec.into("META-INF/patches");
							}

						});
				}

			});

		return jar;
	}

	protected void addTasks(Project project) {
		addTaskDeploy(project);
		addTaskFormatXSD(project);
		addTaskInitGradle(project);
		addTaskJarSources(project);
		addTaskSetupArquillian(project);
		addTaskSetupTestableTomcat(project);
		addTaskStartTestableTomcat(project);
		addTaskStopTestableTomcat(project);
		addTaskTestIntegration(project);
		addTaskZipJavadoc(project);

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildWSDLTask.class,
			new Action<BuildWSDLTask>() {

				@Override
				public void execute(BuildWSDLTask buildWSDLTask) {
					addTaskFormatWSDL(buildWSDLTask);
				}

			});
	}

	protected Task addTaskSetupArquillian(Project project) {
		Task task = project.task(SETUP_ARQUILLIAN_TASK_NAME);

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					try {
						String xml = FileUtil.read(
							"com/liferay/gradle/plugins/dependencies/" +
								"arquillian.xml");

						LiferayExtension liferayExtension =
							GradleUtil.getExtension(
								project, LiferayExtension.class);

						TomcatAppServer tomcatAppServer =
							(TomcatAppServer)liferayExtension.getAppServer(
								"tomcat");

						xml = xml.replace(
							"${app.server.tomcat.manager.password}",
							tomcatAppServer.getManagerPassword());
						xml = xml.replace(
							"${app.server.tomcat.manager.user}",
							tomcatAppServer.getManagerUserName());
						xml = xml.replace(
							"${jmx.remote.port}",
							String.valueOf(
								liferayExtension.getJmxRemotePort()));

						SourceSet sourceSet = GradleUtil.getSourceSet(
							project, TEST_INTEGRATION_SOURCE_SET_NAME);

						File testIntegrationDir = getSrcDir(
							sourceSet.getResources());

						File arquillianXmlFile = new File(
							testIntegrationDir, "arquillian.xml");

						Files.write(
							arquillianXmlFile.toPath(),
							xml.getBytes(StandardCharsets.UTF_8));
					}
					catch (Exception e) {
						throw new GradleException(e.getMessage(), e);
					}
				}

			});

		task.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					SourceSet sourceSet = GradleUtil.getSourceSet(
						task.getProject(), TEST_INTEGRATION_SOURCE_SET_NAME);

					File testIntegrationDir = getSrcDir(
						sourceSet.getResources());

					File arquillianXmlFile = new File(
						testIntegrationDir, "arquillian.xml");

					if (arquillianXmlFile.exists()) {
						return false;
					}

					SourceDirectorySet sourceDirectorySet =
						sourceSet.getAllJava();

					if (sourceDirectorySet.isEmpty()) {
						return false;
					}

					return true;
				}

			});

		return task;
	}

	protected SetupTestableTomcatTask addTaskSetupTestableTomcat(
		Project project) {

		final SetupTestableTomcatTask setupTestableTomcatTask =
			GradleUtil.addTask(
				project, SETUP_TESTABLE_TOMCAT_TASK_NAME,
				SetupTestableTomcatTask.class);

		setupTestableTomcatTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					_startedAppServersReentrantLock.lock();

					try {
						if (_startedAppServerBinDirs.contains(
								setupTestableTomcatTask.getTomcatBinDir())) {

							return false;
						}

						return true;
					}
					finally {
						_startedAppServersReentrantLock.unlock();
					}
				}

			});

		return setupTestableTomcatTask;
	}

	protected StartAppServerTask addTaskStartTestableTomcat(Project project) {
		StartAppServerTask startTestableTomcatTask = GradleUtil.addTask(
			project, START_TESTABLE_TOMCAT_TASK_NAME, StartAppServerTask.class);

		startTestableTomcatTask.dependsOn(SETUP_TESTABLE_TOMCAT_TASK_NAME);
		startTestableTomcatTask.finalizedBy(STOP_TESTABLE_TOMCAT_TASK_NAME);

		startTestableTomcatTask.setAppServerType("tomcat");

		startTestableTomcatTask.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					boolean deleteLiferayHome = GradleUtil.getProperty(
						task, DELETE_LIFERAY_HOME_PROPERTY_NAME, true);

					if (!deleteLiferayHome) {
						return;
					}

					Project project = task.getProject();

					LiferayExtension liferayExtension = GradleUtil.getExtension(
						project, LiferayExtension.class);

					File liferayHome = liferayExtension.getLiferayHome();

					project.delete(
						new File(liferayHome, "data"),
						new File(liferayHome, "logs"),
						new File(liferayHome, "osgi/state"),
						new File(
							liferayHome, "portal-setup-wizard.properties"));
				}

			});

		Action<Task> action = new Action<Task>() {

			@Override
			public void execute(Task task) {
				StartAppServerTask startAppServerTask =
					(StartAppServerTask)task;

				File appServerBinDir = startAppServerTask.getAppServerBinDir();

				boolean started = false;

				_startedAppServersReentrantLock.lock();

				try {
					if (_startedAppServerBinDirs.contains(appServerBinDir)) {
						started = true;
					}
					else {
						_startedAppServerBinDirs.add(appServerBinDir);
					}
				}
				finally {
					_startedAppServersReentrantLock.unlock();
				}

				if (started) {
					if (_logger.isDebugEnabled()) {
						_logger.debug(
							"Application server " + appServerBinDir +
								" is already started");
					}

					Project project = startAppServerTask.getProject();

					Gradle gradle = project.getGradle();

					StartParameter startParameter = gradle.getStartParameter();

					if (startParameter.isParallelProjectExecutionEnabled()) {
						if (_logger.isDebugEnabled()) {
							_logger.debug(
								"Waiting for application server " +
									appServerBinDir + " to be reachable");
						}

						startAppServerTask.waitForAppServer();
					}

					throw new StopExecutionException();
				}
			}

		};

		startTestableTomcatTask.doFirst(action);

		startTestableTomcatTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					StartAppServerTask startAppServerTask =
						(StartAppServerTask)task;

					if (startAppServerTask.isAppServerReachable()) {
						return false;
					}

					return true;
				}

			});

		return startTestableTomcatTask;
	}

	protected StopAppServerTask addTaskStopTestableTomcat(Project project) {
		final StopAppServerTask stopTestableTomcatTask = GradleUtil.addTask(
			project, STOP_TESTABLE_TOMCAT_TASK_NAME, StopAppServerTask.class);

		stopTestableTomcatTask.setAppServerType("tomcat");

		Action<Task> action = new Action<Task>() {

			@Override
			public void execute(Task task) {
				StopAppServerTask stopAppServerTask = (StopAppServerTask)task;

				File appServerBinDir = stopAppServerTask.getAppServerBinDir();

				_startedAppServersReentrantLock.lock();

				try {
					if (!_startedAppServerBinDirs.contains(appServerBinDir)) {
						if (_logger.isDebugEnabled()) {
							_logger.debug(
								"Application server " + appServerBinDir +
									" is already stopped");
						}

						throw new StopExecutionException();
					}

					int originalCounter = _updateStartedAppServerStopCounters(
						appServerBinDir, false);

					if (originalCounter > 1) {
						if (_logger.isDebugEnabled()) {
							_logger.debug(
								"Application server " + appServerBinDir +
									" cannot be stopped now, still " +
										(originalCounter - 1) + " to execute");
						}

						throw new StopExecutionException();
					}
				}
				finally {
					_startedAppServersReentrantLock.unlock();
				}
			}

		};

		stopTestableTomcatTask.doFirst(action);

		Gradle gradle = project.getGradle();

		TaskExecutionGraph taskExecutionGraph = gradle.getTaskGraph();

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall(TaskExecutionGraph taskExecutionGraph) {
				if (taskExecutionGraph.hasTask(stopTestableTomcatTask)) {
					_startedAppServersReentrantLock.lock();

					try {
						_updateStartedAppServerStopCounters(
							stopTestableTomcatTask.getAppServerBinDir(), true);
					}
					finally {
						_startedAppServersReentrantLock.unlock();
					}
				}
			}

		};

		taskExecutionGraph.whenReady(closure);

		return stopTestableTomcatTask;
	}

	protected Test addTaskTestIntegration(Project project) {
		Test test = GradleUtil.addTask(
			project, TEST_INTEGRATION_TASK_NAME, Test.class);

		test.dependsOn(SETUP_ARQUILLIAN_TASK_NAME);
		test.mustRunAfter(JavaPlugin.TEST_TASK_NAME);

		test.setDescription("Runs the integration tests.");
		test.setGroup("verification");

		ConventionMapping conventionMapping = test.getConventionMapping();

		final SourceSet sourceSet = GradleUtil.getSourceSet(
			project, TEST_INTEGRATION_SOURCE_SET_NAME);

		conventionMapping.map(
			"classpath",
			new Callable<FileCollection>() {

				@Override
				public FileCollection call() throws Exception {
					return sourceSet.getRuntimeClasspath();
				}

			});

		conventionMapping.map(
			"testClassesDir",
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					return sourceSetOutput.getClassesDir();
				}

			});

		conventionMapping.map(
			"testSrcDirs",
			new Callable<List<File>>() {

				@Override
				public List<File> call() throws Exception {
					SourceDirectorySet sourceDirectorySet = sourceSet.getJava();

					return new ArrayList<>(sourceDirectorySet.getSrcDirs());
				}

			});

		Task checkTask = GradleUtil.getTask(project, "check");

		checkTask.dependsOn(test);

		return test;
	}

	protected Zip addTaskZipJavadoc(Project project) {
		Zip zip = GradleUtil.addTask(project, ZIP_JAVADOC_TASK_NAME, Zip.class);

		zip.setClassifier("javadoc");
		zip.setDescription(
			"Assembles a zip archive containing the Javadoc files for this " +
				"project.");
		zip.setGroup(BasePlugin.BUILD_GROUP);

		Javadoc javadoc = (Javadoc)GradleUtil.getTask(
			project, JavaPlugin.JAVADOC_TASK_NAME);

		zip.dependsOn(javadoc);
		zip.from(javadoc.getDestinationDir());

		return zip;
	}

	protected void applyConfigScripts(Project project) {
		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/dependencies/config-liferay.gradle",
			project);

		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/dependencies/config-maven.gradle",
			project);
	}

	protected void applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);
		GradleUtil.applyPlugin(project, MavenPlugin.class);

		GradleUtil.applyPlugin(project, OptionalBasePlugin.class);
		GradleUtil.applyPlugin(project, ProvidedBasePlugin.class);

		GradleUtil.applyPlugin(project, CSSBuilderPlugin.class);
		GradleUtil.applyPlugin(project, JavadocFormatterPlugin.class);
		GradleUtil.applyPlugin(project, JSModuleConfigGeneratorPlugin.class);
		GradleUtil.applyPlugin(project, JspCPlugin.class);
		GradleUtil.applyPlugin(project, JSTranspilerPlugin.class);
		GradleUtil.applyPlugin(project, LangBuilderPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
		GradleUtil.applyPlugin(project, SoyPlugin.class);
		GradleUtil.applyPlugin(project, TLDFormatterPlugin.class);
		GradleUtil.applyPlugin(project, WhipPlugin.class);
		GradleUtil.applyPlugin(project, XMLFormatterPlugin.class);
		GradleUtil.applyPlugin(project, XSDBuilderPlugin.class);
	}

	protected void configureArtifacts(Project project) {
		ArtifactHandler artifactHandler = project.getArtifacts();

		Task jarSourcesTask = GradleUtil.getTask(
			project, JAR_SOURCES_TASK_NAME);

		artifactHandler.add(Dependency.ARCHIVES_CONFIGURATION, jarSourcesTask);

		Map<String, Object> args = new HashMap<>();

		args.put("dir", project.getProjectDir());
		args.put("include", "**/*.java");

		FileTree javaFileTree = project.fileTree(args);

		if (!javaFileTree.isEmpty()) {
			Task zipJavadocTask = GradleUtil.getTask(
				project, ZIP_JAVADOC_TASK_NAME);

			artifactHandler.add(
				Dependency.ARCHIVES_CONFIGURATION, zipJavadocTask);
		}
	}

	protected void configureConf2ScopeMappings(Project project) {
		MavenPluginConvention mavenPluginConvention = GradleUtil.getConvention(
			project, MavenPluginConvention.class);

		Conf2ScopeMappingContainer conf2ScopeMappingContainer =
			mavenPluginConvention.getConf2ScopeMappings();

		Map<Configuration, Conf2ScopeMapping> mappings =
			conf2ScopeMappingContainer.getMappings();

		Configuration configuration = GradleUtil.getConfiguration(
			project, JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME);

		mappings.remove(configuration);

		configuration = GradleUtil.getConfiguration(
			project, JavaPlugin.TEST_RUNTIME_CONFIGURATION_NAME);

		mappings.remove(configuration);
	}

	protected void configureConfigurations(final Project project) {
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
								String version =
									moduleVersionSelector.getVersion();

								if (group.equals("com.liferay.portal") &&
									version.equals("default")) {

									LiferayExtension liferayExtension =
										GradleUtil.getExtension(
											project, LiferayExtension.class);

									dependencyResolveDetails.useVersion(
										liferayExtension.getPortalVersion());
								}
						}

					});
			}

		};

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		configurationContainer.all(action);
	}

	protected void configureDependencies(Project project) {
		configureDependenciesProvided(project);
		configureDependenciesTestCompile(project);
	}

	protected void configureDependenciesProvided(Project project) {
		if (!isAddDefaultDependencies(project)) {
			return;
		}

		for (String dependencyNotation : DEFAULT_DEPENDENCY_NOTATIONS) {
			GradleUtil.addDependency(
				project, ProvidedBasePlugin.getPROVIDED_CONFIGURATION_NAME(),
				dependencyNotation);
		}

		for (String dependencyNotation : _DEFAULT_EXT_DEPENDENCY_NOTATIONS) {
			GradleUtil.addDependency(
				project, ProvidedBasePlugin.getPROVIDED_CONFIGURATION_NAME(),
				dependencyNotation);
		}
	}

	protected void configureDependenciesTestCompile(Project project) {
		if (!isAddTestDefaultDependencies(project)) {
			return;
		}

		for (String dependencyNotation : _DEFAULT_TEST_DEPENDENCY_NOTATIONS) {
			GradleUtil.addDependency(
				project, JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME,
				dependencyNotation);
		}
	}

	protected void configureProperties(Project project) {
		configureTestResultsDir(project);
	}

	protected void configureSourceSet(
		Project project, String name, File classesDir, File srcDir) {

		SourceSet sourceSet = GradleUtil.getSourceSet(project, name);

		if (classesDir != null) {
			SourceSetOutput sourceSetOutput = sourceSet.getOutput();

			sourceSetOutput.setClassesDir(classesDir);
			sourceSetOutput.setResourcesDir(classesDir);
		}

		if (srcDir != null) {
			SourceDirectorySet javaSourceDirectorySet = sourceSet.getJava();

			Set<File> srcDirs = Collections.singleton(srcDir);

			javaSourceDirectorySet.setSrcDirs(srcDirs);

			SourceDirectorySet resourcesSourceDirectorySet =
				sourceSet.getResources();

			resourcesSourceDirectorySet.setSrcDirs(srcDirs);
		}
	}

	protected void configureSourceSetMain(Project project) {
		File classesDir = project.file("classes");

		configureSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME, classesDir, null);
	}

	protected void configureSourceSets(Project project) {
		addSourceSetTestIntegration(project);

		configureSourceSetMain(project);
		configureSourceSetTest(project);
		configureSourceSetTestIntegration(project);
	}

	protected void configureSourceSetTest(Project project) {
		File classesDir = project.file("test-classes/unit");

		configureSourceSet(
			project, SourceSet.TEST_SOURCE_SET_NAME, classesDir, null);
	}

	protected void configureSourceSetTestIntegration(Project project) {
		File classesDir = project.file("test-classes/integration");

		configureSourceSet(
			project, TEST_INTEGRATION_SOURCE_SET_NAME, classesDir, null);
	}

	protected void configureTaskBuildCSSGenerateSourceMap(
		BuildCSSTask buildCSSTask) {

		String generateSourceMap = GradleUtil.getProperty(
			buildCSSTask.getProject(), "sass.generate.source.map",
			(String)null);

		if (Validator.isNotNull(generateSourceMap)) {
			buildCSSTask.setGenerateSourceMap(
				Boolean.parseBoolean(generateSourceMap));
		}
	}

	protected void configureTaskBuildCSSSassCompilerClassName(
		BuildCSSTask buildCSSTask) {

		String sassCompilerClassName = GradleUtil.getProperty(
			buildCSSTask.getProject(), "sass.compiler.class.name",
			(String)null);

		buildCSSTask.setSassCompilerClassName(sassCompilerClassName);
	}

	protected void configureTaskBuildLang(Project project) {
		BuildLangTask buildLangTask = (BuildLangTask)GradleUtil.getTask(
			project, LangBuilderPlugin.BUILD_LANG_TASK_NAME);

		configureTaskBuildLangLangDirName(buildLangTask);
		configureTaskBuildLangTranslateClientId(buildLangTask);
		configureTaskBuildLangTranslateClientSecret(buildLangTask);
	}

	protected void configureTaskBuildLangLangDirName(
		BuildLangTask buildLangTask) {

		Project project = buildLangTask.getProject();

		File langDir = new File(getResourcesDir(project), "content");

		buildLangTask.setLangDirName(project.relativePath(langDir));
	}

	protected void configureTaskBuildLangTranslateClientId(
		BuildLangTask buildLangTask) {

		if (Validator.isNotNull(buildLangTask.getTranslateClientId())) {
			return;
		}

		String translateClientId = GradleUtil.getProperty(
			buildLangTask.getProject(), "microsoft.translator.client.id",
			(String)null);

		buildLangTask.setTranslateClientId(translateClientId);
	}

	protected void configureTaskBuildLangTranslateClientSecret(
		BuildLangTask buildLangTask) {

		if (Validator.isNotNull(buildLangTask.getTranslateClientSecret())) {
			return;
		}

		String translateClientSecret = GradleUtil.getProperty(
			buildLangTask.getProject(), "microsoft.translator.client.secret",
			(String)null);

		buildLangTask.setTranslateClientSecret(translateClientSecret);
	}

	protected void configureTaskBuildUpgradeTableDir(
		BuildUpgradeTableTask buildUpgradeTableTask) {

		File file = GradleUtil.getProperty(
			buildUpgradeTableTask.getProject(), "upgrade.table.dir",
			(File)null);

		buildUpgradeTableTask.setUpgradeTableDir(file);
	}

	protected void configureTaskBuildXSD(Project project) {
		BuildXSDTask buildXSDTask = (BuildXSDTask)GradleUtil.getTask(
			project, XSDBuilderPlugin.BUILD_XSD_TASK_NAME);

		configureTaskBuildXSDDestinationDir(buildXSDTask);
		configureTaskBuildXSDInputDir(buildXSDTask);
	}

	protected void configureTaskBuildXSDDestinationDir(
		BuildXSDTask buildXSDTask) {

		buildXSDTask.setDestinationDir(getLibDir(buildXSDTask.getProject()));
	}

	protected void configureTaskBuildXSDInputDir(BuildXSDTask buildXSDTask) {
		Project project = buildXSDTask.getProject();

		File inputDir = project.file("xsd");

		buildXSDTask.setInputDir(inputDir);
	}

	protected void configureTaskClasses(Project project) {
		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		configureTaskClassesDependsOn(classesTask);
	}

	protected void configureTaskClassesDependsOn(Task classesTask) {
		classesTask.dependsOn(CSSBuilderPlugin.BUILD_CSS_TASK_NAME);
	}

	protected void configureTaskClean(Project project) {
		Task cleanTask = GradleUtil.getTask(
			project, BasePlugin.CLEAN_TASK_NAME);

		configureTaskCleanDependsOn(cleanTask);
	}

	protected void configureTaskCleanDependsOn(Task cleanTask) {
		Project project = cleanTask.getProject();

		for (Task task : project.getTasks()) {
			boolean autoClean = GradleUtil.getProperty(
				task, AUTO_CLEAN_PROPERTY_NAME, true);

			if (!autoClean) {
				continue;
			}

			TaskOutputs taskOutputs = task.getOutputs();

			if (!taskOutputs.getHasOutput()) {
				continue;
			}

			String taskName =
				BasePlugin.CLEAN_TASK_NAME +
					StringUtil.capitalize(task.getName());

			cleanTask.dependsOn(taskName);
		}
	}

	protected void configureTaskConfigJSModules(Project project) {
		ConfigJSModulesTask configJSModulesTask =
			(ConfigJSModulesTask)GradleUtil.getTask(
				project,
				JSModuleConfigGeneratorPlugin.CONFIG_JS_MODULES_TASK_NAME);

		configureTaskConfigJSModulesConfigVariable(configJSModulesTask);
		configureTaskConfigJSModulesDependsOn(configJSModulesTask);
		configureTaskConfigJSModulesIgnorePath(configJSModulesTask);
		configureTaskConfigJSModulesIncludes(configJSModulesTask);
		configureTaskConfigJSModulesModuleExtension(configJSModulesTask);
		configureTaskConfigJSModulesModuleFormat(configJSModulesTask);
		configureTaskConfigJSModulesMustRunAfter(configJSModulesTask);
		configureTaskConfigJSModulesSourceDir(configJSModulesTask);
	}

	protected void configureTaskConfigJSModulesConfigVariable(
		ConfigJSModulesTask configJSModulesTask) {

		configJSModulesTask.setConfigVariable("");
	}

	protected void configureTaskConfigJSModulesDependsOn(
		ConfigJSModulesTask configJSModulesTask) {

		configJSModulesTask.dependsOn(JavaPlugin.PROCESS_RESOURCES_TASK_NAME);
	}

	protected void configureTaskConfigJSModulesIgnorePath(
		ConfigJSModulesTask configJSModulesTask) {

		configJSModulesTask.setIgnorePath(true);
	}

	protected void configureTaskConfigJSModulesIncludes(
		ConfigJSModulesTask configJSModulesTask) {

		configJSModulesTask.setIncludes(Collections.singleton("**/*.es.js"));
	}

	protected void configureTaskConfigJSModulesModuleExtension(
		ConfigJSModulesTask configJSModulesTask) {

		configJSModulesTask.setModuleExtension("");
	}

	protected void configureTaskConfigJSModulesModuleFormat(
		ConfigJSModulesTask configJSModulesTask) {

		configJSModulesTask.setModuleFormat("/_/g,-");
	}

	protected void configureTaskConfigJSModulesMustRunAfter(
		ConfigJSModulesTask configJSModulesTask) {

		configJSModulesTask.mustRunAfter(
			JSTranspilerPlugin.TRANSPILE_JS_TASK_NAME);
	}

	protected void configureTaskConfigJSModulesSourceDir(
		final ConfigJSModulesTask configJSModulesTask) {

		configJSModulesTask.setSourceDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					TranspileJSTask transpileJSTask =
						(TranspileJSTask)GradleUtil.getTask(
							configJSModulesTask.getProject(),
							JSTranspilerPlugin.TRANSPILE_JS_TASK_NAME);

					return new File(
						transpileJSTask.getOutputDir(), "META-INF/resources");
				}

			});
	}

	protected void configureTaskDeploy(
		Project project, LiferayExtension liferayExtension) {

		Task task = GradleUtil.getTask(project, DEPLOY_TASK_NAME);

		if (!(task instanceof Copy)) {
			return;
		}

		Copy copy = (Copy)task;

		configureTaskDeployInto(copy, liferayExtension);

		configureTaskDeployFrom(copy);
	}

	protected void configureTaskDeployFrom(Copy copy) {
		Project project = copy.getProject();

		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		addCleanDeployedFile(project, jar.getArchivePath());
	}

	protected void configureTaskDeployInto(
		Copy copy, LiferayExtension liferayExtension) {

		copy.into(liferayExtension.getDeployDir());
	}

	protected void configureTaskDirectDeployAppServerDir(
		DirectDeployTask directDeployTask, LiferayExtension liferayExtension) {

		if (directDeployTask.getAppServerDir() == null) {
			directDeployTask.setAppServerDir(
				liferayExtension.getAppServerDir());
		}
	}

	protected void configureTaskDirectDeployAppServerLibGlobalDir(
		DirectDeployTask directDeployTask, LiferayExtension liferayExtension) {

		if (directDeployTask.getAppServerLibGlobalDir() == null) {
			directDeployTask.setAppServerLibGlobalDir(
				liferayExtension.getAppServerLibGlobalDir());
		}
	}

	protected void configureTaskDirectDeployAppServerPortalDir(
		DirectDeployTask directDeployTask, LiferayExtension liferayExtension) {

		if (directDeployTask.getAppServerPortalDir() == null) {
			directDeployTask.setAppServerPortalDir(
				liferayExtension.getAppServerPortalDir());
		}
	}

	protected void configureTaskDirectDeployAppServerType(
		DirectDeployTask directDeployTask, LiferayExtension liferayExtension) {

		if (Validator.isNull(directDeployTask.getAppServerType())) {
			directDeployTask.setAppServerType(
				liferayExtension.getAppServerType());
		}
	}

	protected boolean configureTaskEnabledWithAppServer(
		Task task, String appServerType) {

		LiferayExtension liferayExtension = GradleUtil.getExtension(
			task.getProject(), LiferayExtension.class);

		String curAppServerType = liferayExtension.getAppServerType();

		if (!appServerType.equals(curAppServerType)) {
			task.setEnabled(false);
		}

		return task.getEnabled();
	}

	protected void configureTaskFormatXSD(Project project) {
		FormatXMLTask formatXMLTask = (FormatXMLTask)GradleUtil.getTask(
			project, FORMAT_XSD_TASK_NAME);

		configureTaskFormatXSDSource(formatXMLTask);
	}

	protected void configureTaskFormatXSDSource(FormatXMLTask formatXMLTask) {
		FileTree fileTree = formatXMLTask.getSource();

		if (!fileTree.isEmpty()) {
			return;
		}

		BuildXSDTask buildXSDTask = (BuildXSDTask)GradleUtil.getTask(
			formatXMLTask.getProject(), XSDBuilderPlugin.BUILD_XSD_TASK_NAME);

		formatXMLTask.setSource(buildXSDTask.getInputDir());
		formatXMLTask.include("**/*.xsd");
	}

	protected void configureTaskInitGradle(Project project) {
		InitGradleTask initGradleTask = (InitGradleTask)GradleUtil.getTask(
			project, INIT_GRADLE_TASK_NAME);

		configureTaskInitGradleIgnoreMissingDependencies(initGradleTask);
		configureTaskInitGradleOverwrite(initGradleTask);
	}

	protected void configureTaskInitGradleIgnoreMissingDependencies(
		InitGradleTask initGradleTask) {

		String value = GradleUtil.getTaskPrefixedProperty(
			initGradleTask, "ignoreMissingDependencies");

		if (Validator.isNotNull(value)) {
			initGradleTask.setIgnoreMissingDependencies(
				Boolean.parseBoolean(value));
		}
	}

	protected void configureTaskInitGradleOverwrite(
		InitGradleTask initGradleTask) {

		String value = GradleUtil.getTaskPrefixedProperty(
			initGradleTask, "overwrite");

		if (Validator.isNotNull(value)) {
			initGradleTask.setOverwrite(Boolean.parseBoolean(value));
		}
	}

	protected void configureTaskJar(Project project) {
		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		configureTaskJarDependsOn(jar);
		configureTaskJarDuplicatesStrategy(jar);
	}

	protected void configureTaskJarDependsOn(Jar jar) {
		Project project = jar.getProject();

		if (isTestProject(project)) {
			jar.dependsOn(JavaPlugin.TEST_CLASSES_TASK_NAME);

			SourceSet sourceSet = GradleUtil.getSourceSet(
				project, TEST_INTEGRATION_SOURCE_SET_NAME);

			jar.dependsOn(sourceSet.getClassesTaskName());
		}
	}

	protected void configureTaskJarDuplicatesStrategy(Jar jar) {
		jar.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);
	}

	protected void configureTaskPublishNodeModule(
		PublishNodeModuleTask publishNodeModuleTask) {

		configureTaskPublishNodeModuleAuthor(publishNodeModuleTask);
		configureTaskPublishNodeModuleBugsUrl(publishNodeModuleTask);
		configureTaskPublishNodeModuleLicense(publishNodeModuleTask);
		configureTaskPublishNodeModuleNpmEmailAddress(publishNodeModuleTask);
		configureTaskPublishNodeModuleNpmPassword(publishNodeModuleTask);
		configureTaskPublishNodeModuleNpmUserName(publishNodeModuleTask);
		configureTaskPublishNodeModuleRepository(publishNodeModuleTask);
	}

	protected void configureTaskPublishNodeModuleAuthor(
		PublishNodeModuleTask publishNodeModuleTask) {

		if (Validator.isNotNull(publishNodeModuleTask.getModuleAuthor())) {
			return;
		}

		String author = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.module.author",
			(String)null);

		if (Validator.isNotNull(author)) {
			publishNodeModuleTask.setModuleAuthor(author);
		}
	}

	protected void configureTaskPublishNodeModuleBugsUrl(
		PublishNodeModuleTask publishNodeModuleTask) {

		if (Validator.isNotNull(publishNodeModuleTask.getModuleBugsUrl())) {
			return;
		}

		String bugsUrl = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.module.bugs.url",
			(String)null);

		if (Validator.isNotNull(bugsUrl)) {
			publishNodeModuleTask.setModuleBugsUrl(bugsUrl);
		}
	}

	protected void configureTaskPublishNodeModuleLicense(
		PublishNodeModuleTask publishNodeModuleTask) {

		if (Validator.isNotNull(publishNodeModuleTask.getModuleLicense())) {
			return;
		}

		String license = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.module.license",
			(String)null);

		if (Validator.isNotNull(license)) {
			publishNodeModuleTask.setModuleLicense(license);
		}
	}

	protected void configureTaskPublishNodeModuleNpmEmailAddress(
		PublishNodeModuleTask publishNodeModuleTask) {

		if (Validator.isNotNull(publishNodeModuleTask.getNpmEmailAddress())) {
			return;
		}

		String emailAddress = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.email",
			(String)null);

		if (Validator.isNotNull(emailAddress)) {
			publishNodeModuleTask.setNpmEmailAddress(emailAddress);
		}
	}

	protected void configureTaskPublishNodeModuleNpmPassword(
		PublishNodeModuleTask publishNodeModuleTask) {

		if (Validator.isNotNull(publishNodeModuleTask.getNpmPassword())) {
			return;
		}

		String password = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.password",
			(String)null);

		if (Validator.isNotNull(password)) {
			publishNodeModuleTask.setNpmPassword(password);
		}
	}

	protected void configureTaskPublishNodeModuleNpmUserName(
		PublishNodeModuleTask publishNodeModuleTask) {

		if (Validator.isNotNull(publishNodeModuleTask.getNpmUserName())) {
			return;
		}

		String userName = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.user",
			(String)null);

		if (Validator.isNotNull(userName)) {
			publishNodeModuleTask.setNpmUserName(userName);
		}
	}

	protected void configureTaskPublishNodeModuleRepository(
		PublishNodeModuleTask publishNodeModuleTask) {

		if (Validator.isNotNull(publishNodeModuleTask.getModuleRepository())) {
			return;
		}

		String repository = GradleUtil.getProperty(
			publishNodeModuleTask.getProject(), "nodejs.npm.module.repository",
			(String)null);

		if (Validator.isNotNull(repository)) {
			publishNodeModuleTask.setModuleRepository(repository);
		}
	}

	protected void configureTasks(
		Project project, LiferayExtension liferayExtension) {

		configureTaskBuildLang(project);
		configureTaskClasses(project);
		configureTaskClean(project);
		configureTaskDeploy(project, liferayExtension);
		configureTaskFormatXSD(project);
		configureTaskInitGradle(project);
		configureTaskJar(project);
		configureTaskSetupTestableTomcat(project, liferayExtension);
		configureTaskStartTestableTomcat(project, liferayExtension);
		configureTaskTestIntegration(project, liferayExtension);

		configureTasksAppServer(project, liferayExtension);
		configureTasksDirectDeploy(project);
		configureTasksPublishNodeModule(project);
	}

	protected void configureTasksAppServer(
		Project project, final LiferayExtension liferayExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			AppServerTask.class,
			new Action<AppServerTask>() {

				@Override
				public void execute(AppServerTask appServerTask) {
					String appServerType = appServerTask.getAppServerType();

					if (Validator.isNull(appServerType)) {
						return;
					}

					AppServer appServer = liferayExtension.getAppServer(
						appServerType);

					appServerTask.merge(appServer);
				}

			});
	}

	protected void configureTasksBuildCSS(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildCSSTask.class,
			new Action<BuildCSSTask>() {

				@Override
				public void execute(BuildCSSTask buildCSSTask) {
					configureTaskBuildCSSGenerateSourceMap(buildCSSTask);
					configureTaskBuildCSSSassCompilerClassName(buildCSSTask);
				}

			});
	}

	protected void configureTasksBuildUpgradeTable(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildUpgradeTableTask.class,
			new Action<BuildUpgradeTableTask>() {

				@Override
				public void execute(
					BuildUpgradeTableTask buildUpgradeTableTask) {

					configureTaskBuildUpgradeTableDir(buildUpgradeTableTask);
				}

			});
	}

	protected void configureTasksDirectDeploy(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			DirectDeployTask.class,
			new Action<DirectDeployTask>() {

				@Override
				public void execute(DirectDeployTask directDeployTask) {
					LiferayExtension liferayExtension = GradleUtil.getExtension(
						directDeployTask.getProject(), LiferayExtension.class);

					configureTaskDirectDeployAppServerDir(
						directDeployTask, liferayExtension);
					configureTaskDirectDeployAppServerLibGlobalDir(
						directDeployTask, liferayExtension);
					configureTaskDirectDeployAppServerPortalDir(
						directDeployTask, liferayExtension);
					configureTaskDirectDeployAppServerType(
						directDeployTask, liferayExtension);
				}

			});
	}

	protected void configureTaskSetupTestableTomcat(
		Project project, LiferayExtension liferayExtension) {

		SetupTestableTomcatTask setupTestableTomcatTask =
			(SetupTestableTomcatTask)GradleUtil.getTask(
				project, SETUP_TESTABLE_TOMCAT_TASK_NAME);

		if (!configureTaskEnabledWithAppServer(
				setupTestableTomcatTask, "tomcat")) {

			return;
		}

		configureTaskSetupTestableTomcatJmx(
			setupTestableTomcatTask, liferayExtension);
		configureTaskSetupTestableTomcatModuleFrameworkBaseDir(
			setupTestableTomcatTask, liferayExtension);
	}

	protected void configureTaskSetupTestableTomcatJmx(
		SetupTestableTomcatTask setupTestableTomcatTask,
		LiferayExtension liferayExtension) {

		if (setupTestableTomcatTask.getJmxRemotePort() > 0) {
			return;
		}

		setupTestableTomcatTask.setJmxRemotePort(
			liferayExtension.getJmxRemotePort());
	}

	protected void configureTaskSetupTestableTomcatModuleFrameworkBaseDir(
		SetupTestableTomcatTask setupTestableTomcatTask,
		LiferayExtension liferayExtension) {

		if (setupTestableTomcatTask.getModuleFrameworkBaseDir() != null) {
			return;
		}

		File moduleFrameworkBaseDir = new File(
			liferayExtension.getLiferayHome(), "osgi");

		setupTestableTomcatTask.setModuleFrameworkBaseDir(
			moduleFrameworkBaseDir);
	}

	protected void configureTasksPublishNodeModule(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PublishNodeModuleTask.class,
			new Action<PublishNodeModuleTask>() {

				@Override
				public void execute(
					PublishNodeModuleTask publishNodeModuleTask) {

					configureTaskPublishNodeModule(publishNodeModuleTask);
				}

			});
	}

	protected void configureTaskStartTestableTomcat(
		Project project, LiferayExtension liferayExtension) {

		StartAppServerTask startTestableTomcatTask =
			(StartAppServerTask)GradleUtil.getTask(
				project, START_TESTABLE_TOMCAT_TASK_NAME);

		configureTaskEnabledWithAppServer(startTestableTomcatTask, "tomcat");
	}

	protected void configureTasksTest(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			Test.class,
			new Action<Test>() {

				@Override
				public void execute(Test test) {
					configureTaskTestDefaultCharacterEncoding(test);
					configureTaskTestForkEvery(test);
					configureTaskTestIgnoreFailures(test);
					configureTaskTestJvmArgs(test);
					configureTaskTestWhip(test);
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					TaskContainer taskContainer = project.getTasks();

					final LiferayExtension liferayExtension =
						GradleUtil.getExtension(
							project, LiferayExtension.class);

					taskContainer.withType(
						Test.class,
						new Action<Test>() {

							@Override
							public void execute(Test test) {
								configureTaskTestIncludes(test);
								configureTaskTestSystemProperties(
									test, liferayExtension);
							}

						});
				}

			});
	}

	protected void configureTaskTestDefaultCharacterEncoding(Test test) {
		test.setDefaultCharacterEncoding(StandardCharsets.UTF_8.name());
	}

	protected boolean configureTaskTestEnabledWithCandidateClassFiles(
		Test test) {

		FileTree fileTree = test.getCandidateClassFiles();

		if (fileTree.isEmpty()) {
			test.setEnabled(false);
		}

		return test.getEnabled();
	}

	protected void configureTaskTestForkEvery(Test test) {
		String name = test.getName();

		if (name.equals(JavaPlugin.TEST_TASK_NAME)) {
			test.setForkEvery(1L);
		}
		else if (name.equals(TEST_INTEGRATION_TASK_NAME)) {
			test.setForkEvery(null);
		}
	}

	protected void configureTaskTestIgnoreFailures(Test test) {
		test.setIgnoreFailures(true);
	}

	protected void configureTaskTestIncludes(Test test) {
		Set<String> includes = test.getIncludes();

		if (includes.isEmpty()) {
			test.setIncludes(Collections.singleton("**/*Test.class"));
		}
	}

	protected void configureTaskTestIntegration(
		Project project, LiferayExtension liferayExtension) {

		Test test = (Test)GradleUtil.getTask(
			project, TEST_INTEGRATION_TASK_NAME);

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, TEST_INTEGRATION_SOURCE_SET_NAME);

		File srcDir = getSrcDir(sourceSet.getResources());

		File skipManagedAppServerFile = new File(
			srcDir, _SKIP_MANAGED_APP_SERVER_FILE_NAME);

		if (!skipManagedAppServerFile.exists() &&
			configureTaskEnabledWithAppServer(test, "tomcat") &&
			configureTaskTestEnabledWithCandidateClassFiles(test)) {

			test.dependsOn(START_TESTABLE_TOMCAT_TASK_NAME);

			Task stopTestableTomcatTask = GradleUtil.getTask(
				project, STOP_TESTABLE_TOMCAT_TASK_NAME);

			stopTestableTomcatTask.mustRunAfter(test);
		}
	}

	protected void configureTaskTestJvmArgs(Test test) {
		test.jvmArgs("-Djava.net.preferIPv4Stack=true");
		test.jvmArgs("-Dliferay.mode=test");
		test.jvmArgs("-Duser.timezone=GMT");

		String name = test.getName();

		if (name.equals(JavaPlugin.TEST_TASK_NAME)) {
			name = "junit.java.unit.gc";
		}
		else if (name.equals(TEST_INTEGRATION_TASK_NAME)) {
			name = "junit.java.integration.gc";
		}

		String value = GradleUtil.getProperty(
			test.getProject(), name, (String)null);

		if (Validator.isNotNull(value)) {
			test.jvmArgs((Object[])value.split("\\s+"));
		}
	}

	protected void configureTaskTestSystemProperties(
		Test test, LiferayExtension liferayExtension) {

		Map<String, Object> systemProperties = test.getSystemProperties();

		if (systemProperties.containsKey("app.server.tomcat.dir")) {
			return;
		}

		AppServer appServer = liferayExtension.getAppServer("tomcat");

		test.systemProperty(
			"app.server.tomcat.dir",
			FileUtil.getAbsolutePath(appServer.getDir()));
	}

	protected void configureTaskTestWhip(Test test) {
		WhipTaskExtension whipTaskExtension = GradleUtil.getExtension(
			test, WhipTaskExtension.class);

		whipTaskExtension.excludes(
			".*Test", ".*Test\\$.*", ".*\\$Proxy.*", "com/liferay/whip/.*");
		whipTaskExtension.includes("com/liferay/.*");
	}

	protected void configureTaskTranspileJS(Project project) {
		TranspileJSTask transpileJSTask = (TranspileJSTask)GradleUtil.getTask(
			project, JSTranspilerPlugin.TRANSPILE_JS_TASK_NAME);

		configureTaskTranspileJSDependsOn(transpileJSTask);
		configureTaskTranspileJSSourceDir(transpileJSTask);
		configureTaskTranspileJSIncludes(transpileJSTask);
	}

	protected void configureTaskTranspileJSDependsOn(
		TranspileJSTask transpileJSTask) {

		transpileJSTask.dependsOn(JavaPlugin.PROCESS_RESOURCES_TASK_NAME);
	}

	protected void configureTaskTranspileJSIncludes(
		TranspileJSTask transpileJSTask) {

		transpileJSTask.setIncludes(Collections.singleton("**/*.es.js"));
	}

	protected void configureTaskTranspileJSSourceDir(
		TranspileJSTask transpileJSTask) {

		transpileJSTask.setSourceDir(
			getResourcesDir(transpileJSTask.getProject()));
	}

	protected void configureTestResultsDir(Project project) {
		JavaPluginConvention javaPluginConvention = GradleUtil.getConvention(
			project, JavaPluginConvention.class);

		File testResultsDir = project.file("test-results/unit");

		javaPluginConvention.setTestResultsDirName(
			FileUtil.relativize(testResultsDir, project.getBuildDir()));
	}

	protected void configureVersion(
		Project project, LiferayExtension liferayExtension) {

		project.setVersion(
			liferayExtension.getVersionPrefix() + "." + project.getVersion());
	}

	protected String getDeployedFileName(Project project, File sourceFile) {
		return sourceFile.getName();
	}

	protected File getJavaDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		return getSrcDir(sourceSet.getJava());
	}

	protected File getLibDir(Project project) {
		return project.file("lib");
	}

	protected File getResourcesDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		return getSrcDir(sourceSet.getResources());
	}

	protected File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	protected boolean isAddDefaultDependencies(Project project) {
		return GradleUtil.getProperty(
			project, _ADD_DEFAULT_DEPENDENCIES_PROPERTY_NAME, true);
	}

	protected boolean isAddTestDefaultDependencies(Project project) {
		return GradleUtil.getProperty(
			project, _ADD_TEST_DEFAULT_DEPENDENCIES_PROPERTY_NAME, true);
	}

	protected boolean isCleanDeployed(Delete delete) {
		return GradleUtil.getProperty(
			delete, CLEAN_DEPLOYED_PROPERTY_NAME, true);
	}

	protected boolean isTestProject(Project project) {
		String projectName = project.getName();

		if (projectName.endsWith("-test")) {
			return true;
		}

		return false;
	}

	protected static final String[] DEFAULT_DEPENDENCY_NOTATIONS = {
		"biz.aQute.bnd:biz.aQute.bnd:2.4.1",
		"com.liferay.portal:portal-service:default",
		"com.liferay.portal:util-bridges:default",
		"com.liferay.portal:util-java:default",
		"com.liferay.portal:util-taglib:default",
		"commons-logging:commons-logging:1.1.3",
		"javax.activation:activation:1.1", "javax.annotation:jsr250-api:1.0",
		"javax.mail:mail:1.4", "javax.servlet.jsp:jsp-api:2.1",
		"javax.servlet:javax.servlet-api:3.0.1", "log4j:log4j:1.2.17"
	};

	private int _updateStartedAppServerStopCounters(
		File appServerBinDir, boolean increment) {

		int originalCounter = 0;

		if (_startedAppServerStopCounters.containsKey(appServerBinDir)) {
			originalCounter = _startedAppServerStopCounters.get(
				appServerBinDir);
		}

		int counter = originalCounter;

		if (increment) {
			counter++;
		}
		else {
			counter--;
		}

		_startedAppServerStopCounters.put(appServerBinDir, counter);

		return originalCounter;
	}

	private static final String _ADD_DEFAULT_DEPENDENCIES_PROPERTY_NAME =
		"com.liferay.adddefaultdependencies";

	private static final String _ADD_TEST_DEFAULT_DEPENDENCIES_PROPERTY_NAME =
		"com.liferay.addtestdefaultdependencies";

	private static final String[] _DEFAULT_EXT_DEPENDENCY_NOTATIONS = {
		"hsqldb:hsqldb:1.8.0.7", "javax.ccpp:ccpp:1.0", "javax.jms:jms:1.1",
		"javax.portlet:portlet-api:2.0", "mysql:mysql-connector-java:5.1.23",
		"net.sourceforge.jtds:jtds:1.2.6",
		"org.eclipse.persistence:javax.persistence:2.0.0",
		"postgresql:postgresql:9.2-1002.jdbc4"
	};

	private static final String[] _DEFAULT_TEST_DEPENDENCY_NOTATIONS = {
		"junit:junit:4.12", "org.mockito:mockito-all:1.9.5",
		"org.powermock:powermock-api-mockito:1.6.1",
		"org.powermock:powermock-api-support:1.6.1",
		"org.powermock:powermock-core:1.6.1",
		"org.powermock:powermock-module-junit4:1.6.1",
		"org.powermock:powermock-module-junit4-common:1.6.1",
		"org.powermock:powermock-reflect:1.6.1",
		"org.springframework:spring-test:3.2.10.RELEASE"
	};

	private static final String _SKIP_MANAGED_APP_SERVER_FILE_NAME =
		"skip.managed.app.server";

	private static final Logger _logger = Logging.getLogger(
		LiferayJavaPlugin.class);

	private static final Set<File> _startedAppServerBinDirs = new HashSet<>();
	private static final ReentrantLock _startedAppServersReentrantLock =
		new ReentrantLock();
	private static final Map<File, Integer> _startedAppServerStopCounters =
		new HashMap<>();

}