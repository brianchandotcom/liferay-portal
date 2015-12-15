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
import com.liferay.gradle.plugins.extensions.TomcatAppServer;
import com.liferay.gradle.plugins.jasper.jspc.JspCPlugin;
import com.liferay.gradle.plugins.javadoc.formatter.JavadocFormatterPlugin;
import com.liferay.gradle.plugins.js.module.config.generator.ConfigJSModulesTask;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorExtension;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorPlugin;
import com.liferay.gradle.plugins.js.transpiler.JSTranspilerExtension;
import com.liferay.gradle.plugins.js.transpiler.JSTranspilerPlugin;
import com.liferay.gradle.plugins.lang.builder.LangBuilderPlugin;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;
import com.liferay.gradle.plugins.patcher.PatchTask;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.soy.BuildSoyTask;
import com.liferay.gradle.plugins.soy.SoyPlugin;
import com.liferay.gradle.plugins.tasks.DirectDeployTask;
import com.liferay.gradle.plugins.tasks.InitGradleTask;
import com.liferay.gradle.plugins.test.integration.TestIntegrationBasePlugin;
import com.liferay.gradle.plugins.test.integration.TestIntegrationPlugin;
import com.liferay.gradle.plugins.test.integration.TestIntegrationTomcatExtension;
import com.liferay.gradle.plugins.test.integration.tasks.SetupTestableTomcatTask;
import com.liferay.gradle.plugins.test.integration.tasks.StartTestableTomcatTask;
import com.liferay.gradle.plugins.test.integration.tasks.StopAppServerTask;
import com.liferay.gradle.plugins.tld.formatter.TLDFormatterPlugin;
import com.liferay.gradle.plugins.upgrade.table.builder.BuildUpgradeTableTask;
import com.liferay.gradle.plugins.whip.WhipPlugin;
import com.liferay.gradle.plugins.xml.formatter.XMLFormatterPlugin;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import nebula.plugin.extraconfigurations.OptionalBasePlugin;
import nebula.plugin.extraconfigurations.ProvidedBasePlugin;

import org.gradle.api.Action;
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
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
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
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.Zip;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.api.tasks.testing.Test;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;

import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayJavaPlugin implements Plugin<Project> {

	public static final String AUTO_CLEAN_PROPERTY_NAME = "autoClean";

	public static final String CLEAN_DEPLOYED_PROPERTY_NAME = "cleanDeployed";

	public static final String DEPLOY_TASK_NAME = "deploy";

	public static final String INIT_GRADLE_TASK_NAME = "initGradle";

	public static final String JAR_SOURCES_TASK_NAME = "jarSources";

	public static final String ZIP_JAVADOC_TASK_NAME = "zipJavadoc";

	@Override
	public void apply(Project project) {
		final LiferayExtension liferayExtension = addLiferayExtension(project);

		applyPlugins(project);

		configureConf2ScopeMappings(project);
		configureConfigurations(project);
		configureProperties(project);
		configureSourceSets(project);

		addConfigurations(project);
		addTasks(project);

		applyConfigScripts(project);

		configureJSModuleConfigGenerator(project);
		configureJSTranspiler(project);
		configureTestIntegrationTomcat(project, liferayExtension);

		configureTaskClean(project);
		configureTaskConfigJSModules(project);
		configureTaskSetupTestableTomcat(project, liferayExtension);
		configureTaskStartTestableTomcat(project, liferayExtension);
		configureTaskStopTestableTomcat(project, liferayExtension);
		configureTaskTest(project);
		configureTaskTestIntegration(project);
		configureTasksBuildUpgradeTable(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					addDependenciesJspC(project, liferayExtension);

					configureArtifacts(project);
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

	protected Copy addTaskDeploy(Project project) {
		Copy copy = GradleUtil.addTask(project, DEPLOY_TASK_NAME, Copy.class);

		copy.setDescription("Assembles the project and deploys it to Liferay.");

		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		copy.from(jar);

		return copy;
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

			if (isTestProject(project)) {
				sourceSet = GradleUtil.getSourceSet(
					project, SourceSet.TEST_SOURCE_SET_NAME);

				jar.from(sourceSet.getAllSource());

				sourceSet = GradleUtil.getSourceSet(
					project,
					TestIntegrationBasePlugin.TEST_INTEGRATION_SOURCE_SET_NAME);

				jar.from(sourceSet.getAllSource());
			}
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
		addTaskInitGradle(project);
		addTaskJarSources(project);
		addTaskZipJavadoc(project);
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
		zip.from(javadoc);

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

		GradleUtil.applyPlugin(project, CSSBuilderDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, CSSBuilderPlugin.class);
		GradleUtil.applyPlugin(project, JSModuleConfigGeneratorPlugin.class);
		GradleUtil.applyPlugin(project, JSTranspilerPlugin.class);
		GradleUtil.applyPlugin(project, JavadocFormatterDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, JavadocFormatterPlugin.class);
		GradleUtil.applyPlugin(project, JspCPlugin.class);
		GradleUtil.applyPlugin(project, LangBuilderDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, LangBuilderPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
		GradleUtil.applyPlugin(project, SoyPlugin.class);
		GradleUtil.applyPlugin(project, TLDFormatterDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, TLDFormatterPlugin.class);
		GradleUtil.applyPlugin(project, TestIntegrationPlugin.class);
		GradleUtil.applyPlugin(project, WhipDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, WhipPlugin.class);
		GradleUtil.applyPlugin(project, XMLFormatterDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, XMLFormatterPlugin.class);
	}

	protected void configureArtifacts(Project project) {
		ArtifactHandler artifactHandler = project.getArtifacts();

		Task jarSourcesTask = GradleUtil.getTask(
			project, JAR_SOURCES_TASK_NAME);

		Spec<File> spec = new Spec<File>() {

			@Override
			public boolean isSatisfiedBy(File file) {
				String fileName = file.getName();

				if (fileName.equals("MANIFEST.MF")) {
					return false;
				}

				return true;
			}

		};

		if (hasSourceFiles(jarSourcesTask, spec)) {
			artifactHandler.add(
				Dependency.ARCHIVES_CONFIGURATION, jarSourcesTask);
		}

		Task javadocTask = GradleUtil.getTask(
			project, JavaPlugin.JAVADOC_TASK_NAME);

		spec = new Spec<File>() {

			@Override
			public boolean isSatisfiedBy(File file) {
				String fileName = file.getName();

				if (fileName.endsWith(".java")) {
					return true;
				}

				return false;
			}

		};

		if (hasSourceFiles(javadocTask, spec)) {
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

	protected void configureJSModuleConfigGenerator(final Project project) {
		JSModuleConfigGeneratorExtension jsModuleConfigGeneratorExtension =
			GradleUtil.getExtension(
				project, JSModuleConfigGeneratorExtension.class);

		String version = GradleUtil.getProperty(
			project, "nodejs.lfr.module.config.generator.version",
			(String)null);

		if (Validator.isNotNull(version)) {
			jsModuleConfigGeneratorExtension.setVersion(version);
		}
	}

	protected void configureJSTranspiler(Project project) {
		JSTranspilerExtension jsTranspilerExtension = GradleUtil.getExtension(
			project, JSTranspilerExtension.class);

		String babelVersion = GradleUtil.getProperty(
			project, "nodejs.babel.version", (String)null);

		if (Validator.isNotNull(babelVersion)) {
			jsTranspilerExtension.setBabelVersion(babelVersion);
		}

		String lfrAmdLoaderVersion = GradleUtil.getProperty(
			project, "nodejs.lfr.amd.loader.version", (String)null);

		if (Validator.isNotNull(lfrAmdLoaderVersion)) {
			jsTranspilerExtension.setLfrAmdLoaderVersion(lfrAmdLoaderVersion);
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
			project, TestIntegrationBasePlugin.TEST_INTEGRATION_SOURCE_SET_NAME,
			classesDir, null);
	}

	protected void configureTaskBuildUpgradeTableDir(
		BuildUpgradeTableTask buildUpgradeTableTask) {

		File file = GradleUtil.getProperty(
			buildUpgradeTableTask.getProject(), "upgrade.table.dir",
			(File)null);

		buildUpgradeTableTask.setUpgradeTableDir(file);
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

	protected void configureTaskConfigJSModules(Project project) {
		ConfigJSModulesTask configJSModulesTask =
			(ConfigJSModulesTask)GradleUtil.getTask(
				project,
				JSModuleConfigGeneratorPlugin.CONFIG_JS_MODULES_TASK_NAME);

		configureTaskConfigJSModulesConfigVariable(configJSModulesTask);
		configureTaskConfigJSModulesIgnorePath(configJSModulesTask);
		configureTaskConfigJSModulesModuleExtension(configJSModulesTask);
		configureTaskConfigJSModulesModuleFormat(configJSModulesTask);
	}

	protected void configureTaskConfigJSModulesConfigVariable(
		ConfigJSModulesTask configJSModulesTask) {

		configJSModulesTask.setConfigVariable("");
	}

	protected void configureTaskConfigJSModulesIgnorePath(
		ConfigJSModulesTask configJSModulesTask) {

		configJSModulesTask.setIgnorePath(true);
	}

	protected void configureTaskConfigJSModulesModuleExtension(
		ConfigJSModulesTask configJSModulesTask) {

		configJSModulesTask.setModuleExtension("");
	}

	protected void configureTaskConfigJSModulesModuleFormat(
		ConfigJSModulesTask configJSModulesTask) {

		configJSModulesTask.setModuleFormat("/_/g,-");
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
				project,
				TestIntegrationBasePlugin.TEST_INTEGRATION_SOURCE_SET_NAME);

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

		configureTaskDeploy(project, liferayExtension);
		configureTaskInitGradle(project);
		configureTaskJar(project);

		configureTasksDirectDeploy(project);
		configureTasksPublishNodeModule(project);
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
				project, TestIntegrationPlugin.SETUP_TESTABLE_TOMCAT_TASK_NAME);

		final TomcatAppServer tomcatAppServer =
			(TomcatAppServer)liferayExtension.getAppServer("tomcat");

		setupTestableTomcatTask.setZipUrl(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return tomcatAppServer.getZipUrl();
				}

			});
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

		StartTestableTomcatTask startTestableTomcatTask =
			(StartTestableTomcatTask)GradleUtil.getTask(
				project, TestIntegrationPlugin.START_TESTABLE_TOMCAT_TASK_NAME);

		final TomcatAppServer tomcatAppServer =
			(TomcatAppServer)liferayExtension.getAppServer("tomcat");

		startTestableTomcatTask.setExecutable(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return tomcatAppServer.getStartExecutable();
				}

			});

		startTestableTomcatTask.setExecutableArgs(
			new Callable<List<String>>() {

				@Override
				public List<String> call() throws Exception {
					return tomcatAppServer.getStartExecutableArgs();
				}

			});
	}

	protected void configureTaskStopTestableTomcat(
		Project project, LiferayExtension liferayExtension) {

		StopAppServerTask stopAppServerTask =
			(StopAppServerTask)GradleUtil.getTask(
				project, TestIntegrationPlugin.STOP_TESTABLE_TOMCAT_TASK_NAME);

		final TomcatAppServer tomcatAppServer =
			(TomcatAppServer)liferayExtension.getAppServer("tomcat");

		stopAppServerTask.setExecutable(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return tomcatAppServer.getStopExecutable();
				}

			});

		stopAppServerTask.setExecutableArgs(
			new Callable<List<String>>() {

				@Override
				public List<String> call() throws Exception {
					return tomcatAppServer.getStopExecutableArgs();
				}

			});
	}

	protected void configureTaskTest(Project project) {
		final Test test = (Test)GradleUtil.getTask(
			project, JavaPlugin.TEST_TASK_NAME);

		test.setForkEvery(1L);

		configureTaskTestDefaultCharacterEncoding(test);
		configureTaskTestIgnoreFailures(test);
		configureTaskTestJvmArgs(test);

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

	protected void configureTaskTestIgnoreFailures(Test test) {
		test.setIgnoreFailures(true);
	}

	protected void configureTaskTestIncludes(Test test) {
		Set<String> includes = test.getIncludes();

		if (includes.isEmpty()) {
			test.setIncludes(Collections.singleton("**/*Test.class"));
		}
	}

	protected void configureTaskTestIntegration(Project project) {
		Test test = (Test)GradleUtil.getTask(
			project, TestIntegrationBasePlugin.TEST_INTEGRATION_TASK_NAME);

		configureTaskTestDefaultCharacterEncoding(test);
		configureTaskTestIgnoreFailures(test);
		configureTaskTestJvmArgs(test);
	}

	protected void configureTaskTestJvmArgs(Test test) {
		String name = test.getName();

		if (name.equals(JavaPlugin.TEST_TASK_NAME)) {
			name = "junit.java.unit.gc";

			test.jvmArgs("-Djava.net.preferIPv4Stack=true");
			test.jvmArgs("-Dliferay.mode=test");
			test.jvmArgs("-Duser.timezone=GMT");
		}
		else if (name.equals(
					TestIntegrationBasePlugin.TEST_INTEGRATION_TASK_NAME)) {

			name = "junit.java.integration.gc";
		}

		String value = GradleUtil.getProperty(
			test.getProject(), name, (String)null);

		if (Validator.isNotNull(value)) {
			test.jvmArgs((Object[])value.split("\\s+"));
		}
	}

	protected void configureTestIntegrationTomcat(
		Project project, final LiferayExtension liferayExtension) {

		TestIntegrationTomcatExtension testIntegrationTomcatExtension =
			GradleUtil.getExtension(
				project, TestIntegrationTomcatExtension.class);

		final TomcatAppServer tomcatAppServer =
			(TomcatAppServer)liferayExtension.getAppServer("tomcat");

		testIntegrationTomcatExtension.setCheckPath(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return tomcatAppServer.getCheckPath();
				}

			});

		testIntegrationTomcatExtension.setPortNumber(
			new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					return tomcatAppServer.getPortNumber();
				}

			});

		testIntegrationTomcatExtension.setDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return tomcatAppServer.getDir();
				}

			});

		testIntegrationTomcatExtension.setJmxRemotePort(
			new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					return liferayExtension.getJmxRemotePort();
				}

			});

		testIntegrationTomcatExtension.setLiferayHome(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return liferayExtension.getLiferayHome();
				}

			});

		testIntegrationTomcatExtension.setManagerPassword(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return tomcatAppServer.getManagerPassword();
				}

			});

		testIntegrationTomcatExtension.setManagerUserName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return tomcatAppServer.getManagerUserName();
				}

			});
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

	protected boolean hasSourceFiles(Task task, Spec<File> spec) {
		TaskInputs taskInputs = task.getInputs();

		FileCollection fileCollection = taskInputs.getSourceFiles();

		fileCollection = fileCollection.filter(spec);

		if (fileCollection.isEmpty()) {
			return false;
		}

		return true;
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

	private static final Logger _logger = Logging.getLogger(
		LiferayJavaPlugin.class);

}