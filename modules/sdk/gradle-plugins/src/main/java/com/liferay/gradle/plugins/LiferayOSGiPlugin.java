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

import aQute.bnd.gradle.BndBuilderPlugin;
import aQute.bnd.gradle.BndUtils;
import aQute.bnd.gradle.BundleTaskConvention;
import aQute.bnd.gradle.PropertiesWrapper;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Processor;
import aQute.bnd.version.MavenVersion;
import aQute.bnd.version.Version;

import aQute.lib.utf8properties.UTF8Properties;

import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.extensions.BundleExtension;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.LiferayOSGiExtension;
import com.liferay.gradle.plugins.internal.AlloyTaglibDefaultsPlugin;
import com.liferay.gradle.plugins.internal.CSSBuilderDefaultsPlugin;
import com.liferay.gradle.plugins.internal.DBSupportDefaultsPlugin;
import com.liferay.gradle.plugins.internal.EclipseDefaultsPlugin;
import com.liferay.gradle.plugins.internal.FindBugsDefaultsPlugin;
import com.liferay.gradle.plugins.internal.IdeaDefaultsPlugin;
import com.liferay.gradle.plugins.internal.JSModuleConfigGeneratorDefaultsPlugin;
import com.liferay.gradle.plugins.internal.JavadocFormatterDefaultsPlugin;
import com.liferay.gradle.plugins.internal.RESTBuilderDefaultsPlugin;
import com.liferay.gradle.plugins.internal.ServiceBuilderDefaultsPlugin;
import com.liferay.gradle.plugins.internal.TLDFormatterDefaultsPlugin;
import com.liferay.gradle.plugins.internal.TestIntegrationDefaultsPlugin;
import com.liferay.gradle.plugins.internal.UpgradeTableBuilderDefaultsPlugin;
import com.liferay.gradle.plugins.internal.WSDDBuilderDefaultsPlugin;
import com.liferay.gradle.plugins.internal.WatchOSGiPlugin;
import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.internal.util.IncludeResourceCompileIncludeInstruction;
import com.liferay.gradle.plugins.internal.util.copy.RenameDependencyAction;
import com.liferay.gradle.plugins.jasper.jspc.JspCPlugin;
import com.liferay.gradle.plugins.javadoc.formatter.JavadocFormatterPlugin;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorPlugin;
import com.liferay.gradle.plugins.js.transpiler.JSTranspilerBasePlugin;
import com.liferay.gradle.plugins.js.transpiler.JSTranspilerPlugin;
import com.liferay.gradle.plugins.lang.builder.LangBuilderPlugin;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeModuleTask;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.soy.SoyPlugin;
import com.liferay.gradle.plugins.soy.SoyTranslationPlugin;
import com.liferay.gradle.plugins.soy.tasks.BuildSoyTask;
import com.liferay.gradle.plugins.tasks.DirectDeployTask;
import com.liferay.gradle.plugins.test.integration.TestIntegrationPlugin;
import com.liferay.gradle.plugins.tld.formatter.TLDFormatterPlugin;
import com.liferay.gradle.plugins.tlddoc.builder.TLDDocBuilderPlugin;
import com.liferay.gradle.plugins.util.BndBuilderUtil;
import com.liferay.gradle.plugins.wsdd.builder.BuildWSDDTask;
import com.liferay.gradle.plugins.wsdd.builder.WSDDBuilderPlugin;
import com.liferay.gradle.plugins.wsdl.builder.WSDLBuilderPlugin;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.file.RelativePath;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.ApplicationPlugin;
import org.gradle.api.plugins.ApplicationPluginConvention;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.plugins.Convention;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.War;
import org.gradle.api.tasks.compile.CompileOptions;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.api.tasks.testing.Test;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;

/**
 * @author Andrea Di Giorgi
 * @author Raymond Augé
 */
public class LiferayOSGiPlugin implements Plugin<Project> {

	public static final String AUTO_CLEAN_PROPERTY_NAME = "autoClean";

	public static final String AUTO_UPDATE_XML_TASK_NAME = "autoUpdateXml";

	public static final String CLEAN_DEPLOYED_PROPERTY_NAME = "cleanDeployed";

	public static final String COMPILE_INCLUDE_CONFIGURATION_NAME =
		"compileInclude";

	public static final String DEPLOY_DEPENDENCIES_TASK_NAME =
		"deployDependencies";

	public static final String DEPLOY_FAST_TASK_NAME = "deployFast";

	public static final String PLUGIN_NAME = "liferayOSGi";

	@Override
	public void apply(final Project project) {
		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);

		LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		final LiferayOSGiExtension liferayOSGiExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, LiferayOSGiExtension.class);

		_applyPlugins(project);

		_addDeployedFile(
			project, liferayExtension, JavaPlugin.JAR_TASK_NAME, false);

		final Configuration compileIncludeConfiguration =
			_addConfigurationCompileInclude(project);

		_addTaskAutoUpdateXml(project);
		_addTaskDeployFast(project, liferayExtension);
		_addTasksBuildWSDDJar(project, liferayExtension);

		Copy deployDependenciesTask = _addTaskDeployDependencies(
			project, liferayExtension);

		_configureArchivesBaseName(project);
		_configureDescription(project);
		_configureLiferay(project, liferayExtension);
		_configureSourceSetMain(project);
		_configureTaskClean(project);
		_configureTaskDeploy(project, deployDependenciesTask);
		_configureTaskJar(project);
		_configureTaskJavadoc(project);
		_configureTaskTest(project);
		_configureTasksTest(project);

		if (GradleUtil.isRunningInsideDaemon()) {
			_configureTasksJavaCompileFork(project, true);
		}

		_configureVersion(project);

		GradleUtil.withPlugin(
			project, ApplicationPlugin.class,
			new Action<ApplicationPlugin>() {

				@Override
				public void execute(ApplicationPlugin applicationPlugin) {
					_configureApplication(project);
					_configureTaskRun(project, compileIncludeConfiguration);
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureBundleExtensionDefaults(
						project, liferayOSGiExtension,
						compileIncludeConfiguration);
				}

			});
	}

	private Configuration _addConfigurationCompileInclude(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, COMPILE_INCLUDE_CONFIGURATION_NAME);

		configuration.setDescription(
			"Additional dependencies to include in the final JAR.");
		configuration.setVisible(false);

		Configuration compileOnlyConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME);

		compileOnlyConfiguration.extendsFrom(configuration);

		return configuration;
	}

	@SuppressWarnings("serial")
	private void _addDeployedFile(
		final LiferayExtension liferayExtension,
		final AbstractArchiveTask abstractArchiveTask, boolean lazy) {

		final Project project = abstractArchiveTask.getProject();

		Task task = GradleUtil.getTask(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME);

		if (!(task instanceof Copy)) {
			return;
		}

		final Copy copy = (Copy)task;

		Object sourcePath = abstractArchiveTask;

		if (lazy) {
			sourcePath = new Callable<File>() {

				@Override
				public File call() throws Exception {
					return abstractArchiveTask.getArchivePath();
				}

			};
		}

		copy.from(
			sourcePath,
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.rename(
						new Closure<String>(project) {

							public String doCall(String fileName) {
								Closure<String> deployedFileNameClosure =
									liferayExtension.
										getDeployedFileNameClosure();

								return deployedFileNameClosure.call(
									abstractArchiveTask);
							}

						});
				}

			});

		Delete delete = (Delete)GradleUtil.getTask(
			project, BasePlugin.CLEAN_TASK_NAME);

		delete.delete(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					boolean cleanDeployed = GradleUtil.getProperty(
						delete, CLEAN_DEPLOYED_PROPERTY_NAME, true);

					if (!cleanDeployed) {
						return null;
					}

					Closure<String> deployedFileNameClosure =
						liferayExtension.getDeployedFileNameClosure();

					return new File(
						copy.getDestinationDir(),
						deployedFileNameClosure.call(abstractArchiveTask));
				}

			});
	}

	private void _addDeployedFile(
		Project project, LiferayExtension liferayExtension, String taskName,
		boolean lazy) {

		AbstractArchiveTask abstractArchiveTask =
			(AbstractArchiveTask)GradleUtil.getTask(project, taskName);

		_addDeployedFile(liferayExtension, abstractArchiveTask, lazy);
	}

	private DirectDeployTask _addTaskAutoUpdateXml(final Project project) {
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
					Logger logger = task.getLogger();

					Project project = task.getProject();

					project.delete("liferay/logs");

					File liferayDir = project.file("liferay");

					boolean deleted = liferayDir.delete();

					if (!deleted && logger.isInfoEnabled()) {
						logger.info("Unable to delete " + liferayDir);
					}
				}

			});

		directDeployTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					DirectDeployTask directDeployTask = (DirectDeployTask)task;

					Project project = directDeployTask.getProject();

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

					String[][] filesets = {
						{
							project.relativePath(
								liferayExtension.getAppServerPortalDir()),
							"WEB-INF/tld/c.tld"
						},
						{
							deployedPluginDirName,
							"WEB-INF/liferay-web.xml,WEB-INF/web.xml"
						},
						{deployedPluginDirName, "WEB-INF/tld/*"}
					};

					File warFile = directDeployTask.getWebAppFile();

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

	private Jar _addTaskBuildWSDDJar(
		final BuildWSDDTask buildWSDDTask, LiferayExtension liferayExtension) {

		Project project = buildWSDDTask.getProject();

		Jar jar = GradleUtil.addTask(
			project, buildWSDDTask.getName() + "Jar", Jar.class);

		jar.setActions(Collections.emptyList());

		jar.dependsOn(buildWSDDTask);

		jar.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					Logger logger = project.getLogger();

					Properties gradleProperties = new PropertiesWrapper();

					gradleProperties.put("project", project);
					gradleProperties.put("task", task);

					try (Builder builder = new Builder(
							new Processor(gradleProperties, false))) {

						Map<String, String> properties = _getProperties(
							project);

						File buildFile = project.getBuildFile();

						builder.setBase(buildFile.getParentFile());

						builder.putAll(properties, true);

						SourceSet sourceSet = GradleUtil.getSourceSet(
							project, SourceSet.MAIN_SOURCE_SET_NAME);

						SourceDirectorySet sourceDirectorySet =
							sourceSet.getJava();

						SourceSetOutput sourceSetOutput = sourceSet.getOutput();

						FileCollection buildDirs = project.files(
							sourceDirectorySet.getOutputDir(),
							sourceSetOutput.getResourcesDir());

						builder.setClasspath(
							buildDirs.getFiles(
							).toArray(
								new File[0]
							));
						builder.setProperty(
							"project.buildpath", buildDirs.getAsPath());

						if (logger.isDebugEnabled()) {
							logger.debug(
								"Builder Classpath: {}", buildDirs.getAsPath());
						}

						SourceDirectorySet allSource = sourceSet.getAllSource();

						Set<File> srcDirs = allSource.getSrcDirs();

						Stream<File> stream = srcDirs.stream();

						FileCollection sourceDirs = project.files(
							stream.filter(
								File::exists
							).collect(
								Collectors.toList()
							));

						builder.setProperty(
							"project.sourcepath", sourceDirs.getAsPath());
						builder.setSourcepath(
							sourceDirs.getFiles(
							).toArray(
								new File[0]
							));

						if (logger.isDebugEnabled()) {
							logger.debug(
								"Builder Sourcepath: {}",
								builder.getSourcePath());
						}

						String bundleSymbolicName = builder.getProperty(
							Constants.BUNDLE_SYMBOLICNAME);

						if (Validator.isNull(bundleSymbolicName) ||
							Constants.EMPTY_HEADER.equals(bundleSymbolicName)) {

							builder.setProperty(
								Constants.BUNDLE_SYMBOLICNAME,
								project.getName());
						}

						String bundleVersion = builder.getProperty(
							Constants.BUNDLE_VERSION);

						if ((Validator.isNull(bundleVersion) ||
							 Constants.EMPTY_HEADER.equals(bundleVersion)) &&
							(project.getVersion() != null)) {

							Object version = project.getVersion();

							MavenVersion mavenVersion =
								MavenVersion.parseString(version.toString());

							Version osgiVersion = mavenVersion.getOSGiVersion();

							builder.setProperty(
								Constants.BUNDLE_VERSION,
								osgiVersion.toString());
						}

						if (logger.isDebugEnabled()) {
							logger.debug("Builder Properties: {}", properties);
						}

						aQute.bnd.osgi.Jar bndJar = builder.build();

						if (!builder.isOk()) {
							BndUtils.logReport(builder, logger);

							new GradleException(buildWSDDTask + " failed");
						}

						TaskOutputs taskOutputs = task.getOutputs();

						FileCollection fileCollection = taskOutputs.getFiles();

						bndJar.write(fileCollection.getSingleFile());

						BndUtils.logReport(builder, logger);

						if (!builder.isOk()) {
							new GradleException(buildWSDDTask + " failed");
						}
					}
					catch (Exception exception) {
						new GradleException(
							buildWSDDTask + " failed", exception);
					}
				}

				private Map<String, String> _getProperties(Project project) {
					LiferayOSGiExtension liferayOSGiExtension =
						GradleUtil.getExtension(
							project, LiferayOSGiExtension.class);

					Map<String, String> properties = GradleUtil.toStringMap(
						liferayOSGiExtension.getBundleDefaultInstructions());

					Map<String, ?> projectProperties = project.getProperties();

					for (Map.Entry<String, ?> entry :
							projectProperties.entrySet()) {

						String key = entry.getKey();

						if (Character.isLowerCase(key.charAt(0))) {
							properties.put(
								key, GradleUtil.toString(entry.getValue()));
						}
					}

					properties.remove(Constants.DONOTCOPY);
					properties.remove(
						LiferayOSGiExtension.
							BUNDLE_DEFAULT_INSTRUCTION_LIFERAY_SERVICE_XML);

					String bundleName = BndBuilderUtil.getInstruction(
						project, Constants.BUNDLE_NAME);

					if (Validator.isNotNull(bundleName)) {
						properties.put(
							Constants.BUNDLE_NAME,
							bundleName + " WSDD descriptors");
					}

					String bundleSymbolicName = BndBuilderUtil.getInstruction(
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

					return properties;
				}

			});

		String taskName = buildWSDDTask.getName();

		if (taskName.equals(WSDDBuilderPlugin.BUILD_WSDD_TASK_NAME)) {
			jar.setAppendix("wsdd");
		}
		else {
			jar.setAppendix("wsdd-" + taskName);
		}

		buildWSDDTask.finalizedBy(jar);

		_addDeployedFile(liferayExtension, jar, true);

		return jar;
	}

	private Copy _addTaskDeployDependencies(
		Project project, final LiferayExtension liferayExtension) {

		final Copy copy = GradleUtil.addTask(
			project, DEPLOY_DEPENDENCIES_TASK_NAME, Copy.class);

		final boolean keepVersions = Boolean.getBoolean(
			"deploy.dependencies.keep.versions");

		GradleUtil.setProperty(
			copy, LiferayOSGiPlugin.AUTO_CLEAN_PROPERTY_NAME, false);
		GradleUtil.setProperty(copy, "keepVersions", keepVersions);

		String renameSuffix = ".jar";

		if (keepVersions) {
			renameSuffix = "-$1.jar";
		}

		GradleUtil.setProperty(copy, "renameSuffix", renameSuffix);

		copy.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return liferayExtension.getDeployDir();
				}

			});

		copy.setDescription("Deploys additional dependencies.");

		TaskOutputs taskOutputs = copy.getOutputs();

		taskOutputs.upToDateWhen(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					return false;
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					copy.eachFile(new RenameDependencyAction(keepVersions));
				}

			});

		return copy;
	}

	@SuppressWarnings("serial")
	private Copy _addTaskDeployFast(
		Project project, LiferayExtension liferayExtension) {

		Copy deployFastTask = GradleUtil.addTask(
			project, DEPLOY_FAST_TASK_NAME, Copy.class);

		deployFastTask.setDescription(
			"Builds and deploys resources to the Liferay work directory.");
		deployFastTask.setGroup(LifecycleBasePlugin.BUILD_GROUP);

		deployFastTask.setDestinationDir(liferayExtension.getLiferayHome());
		deployFastTask.setIncludeEmptyDirs(false);

		String bundleSymbolicName = BndBuilderUtil.getInstruction(
			project, Constants.BUNDLE_SYMBOLICNAME);
		String bundleVersion = BndBuilderUtil.getInstruction(
			project, Constants.BUNDLE_VERSION);

		StringBuilder sb = new StringBuilder();

		sb.append("work/");
		sb.append(bundleSymbolicName);
		sb.append("-");
		sb.append(bundleVersion);

		final String pathName = sb.toString();

		deployFastTask.from(
			GradleUtil.getTask(project, JspCPlugin.COMPILE_JSP_TASK_NAME),
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.into(pathName);
				}

			});

		deployFastTask.from(
			GradleUtil.getTask(project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME),
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.eachFile(
						new Action<FileCopyDetails>() {

							@Override
							public void execute(
								FileCopyDetails fileCopyDetails) {

								RelativePath relativePath =
									fileCopyDetails.getRelativePath();

								String[] segments = relativePath.getSegments();

								if ((segments.length > 4) &&
									segments[2].equals("META-INF") &&
									segments[3].equals("resources")) {

									List<String> list = new ArrayList<>();

									list.add(segments[0]);
									list.add(segments[1]);

									for (int i = 4; i < segments.length; i++) {
										String segment = segments[i];

										if (!segment.equals(".sass-cache")) {
											list.add(segment);
										}
									}

									segments = list.toArray(new String[0]);
								}

								fileCopyDetails.setRelativePath(
									new RelativePath(true, segments));
							}

						});

					copySpec.include("**/*.css");
					copySpec.include("**/*.css.map");
					copySpec.into(pathName);
				}

			});

		deployFastTask.dependsOn(
			GradleUtil.getTask(project, JavaPlugin.CLASSES_TASK_NAME));

		SourceSet mainSourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		deployFastTask.from(
			mainSourceSet.getOutput(),
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.eachFile(
						new Action<FileCopyDetails>() {

							@Override
							public void execute(
								FileCopyDetails fileCopyDetails) {

								RelativePath relativePath =
									fileCopyDetails.getRelativePath();

								String[] segments = relativePath.getSegments();

								if ((segments.length > 4) &&
									segments[2].equals("META-INF") &&
									segments[3].equals("resources")) {

									List<String> list = new ArrayList<>();

									list.add(segments[0]);
									list.add(segments[1]);

									for (int i = 4; i < segments.length; i++) {
										list.add(segments[i]);
									}

									segments = list.toArray(new String[0]);
								}

								fileCopyDetails.setRelativePath(
									new RelativePath(true, segments));
							}

						});

					copySpec.include("**/*.js");
					copySpec.include("**/*.js.map");
					copySpec.into(pathName);
				}

			});

		return deployFastTask;
	}

	private void _addTasksBuildWSDDJar(
		Project project, final LiferayExtension liferayExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildWSDDTask.class,
			new Action<BuildWSDDTask>() {

				@Override
				public void execute(BuildWSDDTask buildWSDDTask) {
					_addTaskBuildWSDDJar(buildWSDDTask, liferayExtension);
				}

			});
	}

	private void _applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, BndBuilderPlugin.class);

		_configureBundleExtension(project);

		// "bundle" must be applied before "java", otherwise it will be too late
		// to replace the JarBuilderFactory.

		GradleUtil.applyPlugin(project, JavaPlugin.class);

		GradleUtil.applyPlugin(project, CSSBuilderPlugin.class);

		GradleUtil.applyPlugin(project, NodePlugin.class);

		if (GradleUtil.hasTask(
				project, NodePlugin.PACKAGE_RUN_BUILD_TASK_NAME)) {

			GradleUtil.applyPlugin(project, JSTranspilerBasePlugin.class);
		}
		else {
			GradleUtil.applyPlugin(
				project, JSModuleConfigGeneratorPlugin.class);
			GradleUtil.applyPlugin(project, JSTranspilerPlugin.class);
		}

		GradleUtil.applyPlugin(project, EclipsePlugin.class);
		GradleUtil.applyPlugin(project, JavadocFormatterPlugin.class);
		GradleUtil.applyPlugin(project, JspCPlugin.class);
		GradleUtil.applyPlugin(project, LangBuilderPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
		GradleUtil.applyPlugin(project, SoyPlugin.class);
		GradleUtil.applyPlugin(project, SoyTranslationPlugin.class);
		GradleUtil.applyPlugin(project, TLDDocBuilderPlugin.class);
		GradleUtil.applyPlugin(project, TLDFormatterPlugin.class);
		GradleUtil.applyPlugin(project, TestIntegrationPlugin.class);

		AlloyTaglibDefaultsPlugin.INSTANCE.apply(project);
		CSSBuilderDefaultsPlugin.INSTANCE.apply(project);
		DBSupportDefaultsPlugin.INSTANCE.apply(project);
		EclipseDefaultsPlugin.INSTANCE.apply(project);
		FindBugsDefaultsPlugin.INSTANCE.apply(project);
		IdeaDefaultsPlugin.INSTANCE.apply(project);
		JSModuleConfigGeneratorDefaultsPlugin.INSTANCE.apply(project);
		JavadocFormatterDefaultsPlugin.INSTANCE.apply(project);
		JspCDefaultsPlugin.INSTANCE.apply(project);
		RESTBuilderDefaultsPlugin.INSTANCE.apply(project);
		ServiceBuilderDefaultsPlugin.INSTANCE.apply(project);
		TLDFormatterDefaultsPlugin.INSTANCE.apply(project);
		TestIntegrationDefaultsPlugin.INSTANCE.apply(project);
		UpgradeTableBuilderDefaultsPlugin.INSTANCE.apply(project);
		WSDDBuilderDefaultsPlugin.INSTANCE.apply(project);
		WatchOSGiPlugin.INSTANCE.apply(project);
	}

	private void _configureApplication(Project project) {
		ApplicationPluginConvention applicationPluginConvention =
			GradleUtil.getConvention(
				project, ApplicationPluginConvention.class);

		String mainClassName = BndBuilderUtil.getInstruction(
			project, "Main-Class");

		if (Validator.isNotNull(mainClassName)) {
			applicationPluginConvention.setMainClassName(mainClassName);
		}
	}

	private void _configureArchivesBaseName(Project project) {
		BasePluginConvention basePluginConvention = GradleUtil.getConvention(
			project, BasePluginConvention.class);

		String bundleSymbolicName = BndBuilderUtil.getInstruction(
			project, Constants.BUNDLE_SYMBOLICNAME);

		if (Validator.isNull(bundleSymbolicName)) {
			return;
		}

		Parameters parameters = new Parameters(bundleSymbolicName);

		Set<String> keys = parameters.keySet();

		Iterator<String> iterator = keys.iterator();

		bundleSymbolicName = iterator.next();

		basePluginConvention.setArchivesBaseName(bundleSymbolicName);
	}

	private void _configureBundleExtension(Project project) {
		Logger logger = project.getLogger();

		BundleExtension bundleExtension = new BundleExtension();

		ExtensionContainer extensionContainer = project.getExtensions();

		extensionContainer.add(
			BundleExtension.class, "bundle", bundleExtension);

		File file = project.file("bnd.bnd");

		if (!file.exists()) {
			return;
		}

		UTF8Properties utf8Properties = new UTF8Properties();

		try (Processor processor = new Processor()) {
			utf8Properties.load(file, processor);

			Enumeration<Object> keys = utf8Properties.keys();

			while (keys.hasMoreElements()) {
				String key = (String)keys.nextElement();

				String value = utf8Properties.getProperty(key);

				if (Objects.equals(key, Constants.INCLUDERESOURCE) &&
					value.contains("[0-9]*")) {

					value = value.replace("[0-9]*", "[0-9.]*");

					logger.lifecycle(
						"DEPRECATED: Update \"{}\" to \"{}\" to remove this " +
							"message",
						Constants.INCLUDERESOURCE, value);
				}

				bundleExtension.put(key, value);
			}
		}
		catch (Exception exception) {
			throw new GradleException("Could not read " + file, exception);
		}
	}

	private void _configureBundleExtensionDefaults(
		Project project, final LiferayOSGiExtension liferayOSGiExtension,
		final Configuration compileIncludeConfiguration) {

		Map<String, Object> bundleInstructions = BndBuilderUtil.getInstructions(
			project);

		IncludeResourceCompileIncludeInstruction
			includeResourceCompileIncludeInstruction =
				new IncludeResourceCompileIncludeInstruction(
					new Callable<Iterable<File>>() {

						@Override
						public Iterable<File> call() throws Exception {
							return compileIncludeConfiguration;
						}

					},
					new Callable<Boolean>() {

						@Override
						public Boolean call() throws Exception {
							return liferayOSGiExtension.
								isExpandCompileInclude();
						}

					});

		bundleInstructions.put(
			Constants.INCLUDERESOURCE + "." +
				compileIncludeConfiguration.getName(),
			includeResourceCompileIncludeInstruction);

		Map<String, Object> bundleDefaultInstructions =
			liferayOSGiExtension.getBundleDefaultInstructions();

		for (Map.Entry<String, Object> entry :
				bundleDefaultInstructions.entrySet()) {

			String key = entry.getKey();

			if (!bundleInstructions.containsKey(key)) {
				bundleInstructions.put(key, entry.getValue());
			}
		}
	}

	private void _configureDescription(Project project) {
		String description = BndBuilderUtil.getInstruction(
			project, Constants.BUNDLE_DESCRIPTION);

		if (Validator.isNull(description)) {
			description = BndBuilderUtil.getInstruction(
				project, Constants.BUNDLE_NAME);
		}

		if (Validator.isNotNull(description)) {
			project.setDescription(description);
		}
	}

	private void _configureLiferay(
		final Project project, final LiferayExtension liferayExtension) {

		liferayExtension.setDeployDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File dir = new File(
						liferayExtension.getAppServerParentDir(),
						"osgi/modules");

					return GradleUtil.getProperty(
						project, "auto.deploy.dir", dir);
				}

			});
	}

	private void _configureSourceSetMain(Project project) {
		File docrootDir = project.file("docroot");

		if (!docrootDir.exists()) {
			return;
		}

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		File javaClassesDir = new File(docrootDir, "WEB-INF/classes");

		SourceDirectorySet javaSourceDirectorySet = sourceSet.getJava();

		javaSourceDirectorySet.setOutputDir(javaClassesDir);

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		sourceSetOutput.setResourcesDir(javaClassesDir);

		File srcDir = new File(docrootDir, "WEB-INF/src");

		Set<File> srcDirs = Collections.singleton(srcDir);

		javaSourceDirectorySet.setSrcDirs(srcDirs);

		SourceDirectorySet resourcesSourceDirectorySet =
			sourceSet.getResources();

		resourcesSourceDirectorySet.setSrcDirs(srcDirs);
	}

	private void _configureTaskClean(Project project) {
		Task task = GradleUtil.getTask(project, BasePlugin.CLEAN_TASK_NAME);

		if (task instanceof Delete) {
			_configureTaskCleanDependsOn((Delete)task);
		}
	}

	private void _configureTaskCleanDependsOn(Delete delete) {
		Project project = delete.getProject();

		@SuppressWarnings("serial")
		Closure<Set<String>> closure = new Closure<Set<String>>(project) {

			@SuppressWarnings("unused")
			public Set<String> doCall(Delete delete) {
				Set<String> cleanTaskNames = new HashSet<>();

				Project project = delete.getProject();

				for (Task task : project.getTasks()) {
					String taskName = task.getName();

					if (taskName.equals(DEPLOY_FAST_TASK_NAME) ||
						taskName.equals(LiferayBasePlugin.DEPLOY_TASK_NAME) ||
						taskName.equals("eclipseClasspath") ||
						taskName.equals("eclipseProject") ||
						taskName.equals("ideaModule") ||
						(task instanceof BuildSoyTask) ||
						(task instanceof DownloadNodeModuleTask) ||
						(task instanceof NpmInstallTask)) {

						continue;
					}

					if (GradleUtil.hasPlugin(project, _CACHE_PLUGIN_ID) &&
						taskName.startsWith("save") &&
						taskName.endsWith("Cache")) {

						continue;
					}

					if (GradleUtil.hasPlugin(
							project, WSDLBuilderPlugin.class) &&
						taskName.startsWith(
							WSDLBuilderPlugin.BUILD_WSDL_TASK_NAME +
								"Generate")) {

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

	private void _configureTaskDeploy(
		Project project, Copy deployDepenciesTask) {

		Task deployTask = GradleUtil.getTask(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME);

		deployTask.finalizedBy(deployDepenciesTask);
	}

	private void _configureTaskJar(final Project project) {
		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		jar.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Map<String, Object> instructions =
						BndBuilderUtil.getInstructions(project);

					instructions.forEach(
						(k, v) -> instructions.put(k, GradleUtil.toString(v)));

					Map<String, ?> projectProperties = project.getProperties();

					for (Map.Entry<String, ?> entry :
							projectProperties.entrySet()) {

						String key = entry.getKey();
						Object value = entry.getValue();

						Matcher matcher = _keyRegex.matcher(key);

						if (matcher.matches() && (value instanceof String)) {
							instructions.put(key, entry.getValue());
						}
					}

					Convention convention = jar.getConvention();

					BundleTaskConvention bundleTaskConvention =
						convention.getPlugin(BundleTaskConvention.class);

					bundleTaskConvention.setBndfile(
						new File("$$$DOESNOTEXIST$$$"));

					bundleTaskConvention.setBnd(instructions);
				}

			});

		File bndFile = project.file("bnd.bnd");

		if (!bndFile.exists()) {
			return;
		}

		TaskInputs taskInputs = jar.getInputs();

		taskInputs.file(bndFile);
	}

	private void _configureTaskJavaCompileFork(
		JavaCompile javaCompile, boolean fork) {

		CompileOptions compileOptions = javaCompile.getOptions();

		compileOptions.setFork(fork);
	}

	private void _configureTaskJavadoc(Project project) {
		String bundleName = BndBuilderUtil.getInstruction(
			project, Constants.BUNDLE_NAME);
		String bundleVersion = BndBuilderUtil.getInstruction(
			project, Constants.BUNDLE_VERSION);

		if (Validator.isNull(bundleName) || Validator.isNull(bundleVersion)) {
			return;
		}

		Javadoc javadoc = (Javadoc)GradleUtil.getTask(
			project, JavaPlugin.JAVADOC_TASK_NAME);

		String title = String.format("%s %s API", bundleName, bundleVersion);

		javadoc.setTitle(title);
	}

	private void _configureTaskRun(
		Project project, Configuration compileIncludeConfiguration) {

		JavaExec javaExec = (JavaExec)GradleUtil.getTask(
			project, ApplicationPlugin.TASK_RUN_NAME);

		javaExec.classpath(compileIncludeConfiguration);
	}

	private void _configureTasksJavaCompileFork(
		Project project, final boolean fork) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			JavaCompile.class,
			new Action<JavaCompile>() {

				@Override
				public void execute(JavaCompile javaCompile) {
					_configureTaskJavaCompileFork(javaCompile, fork);
				}

			});
	}

	private void _configureTasksTest(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			Test.class,
			new Action<Test>() {

				@Override
				public void execute(Test test) {
					_configureTaskTestDefaultCharacterEncoding(test);
				}

			});
	}

	private void _configureTaskTest(Project project) {
		final Test test = (Test)GradleUtil.getTask(
			project, JavaPlugin.TEST_TASK_NAME);

		test.jvmArgs(
			"-Djava.net.preferIPv4Stack=true", "-Dliferay.mode=test",
			"-Duser.timezone=GMT");

		test.setForkEvery(1L);
	}

	private void _configureTaskTestDefaultCharacterEncoding(Test test) {
		test.setDefaultCharacterEncoding(StandardCharsets.UTF_8.name());
	}

	private void _configureVersion(Project project) {
		String bundleVersion = BndBuilderUtil.getInstruction(
			project, Constants.BUNDLE_VERSION);

		if (Validator.isNotNull(bundleVersion)) {
			project.setVersion(bundleVersion);
		}
	}

	private static final String _CACHE_PLUGIN_ID = "com.liferay.cache";

	private static final Logger _logger = Logging.getLogger(
		LiferayOSGiPlugin.class);

	private static final Pattern _keyRegex = Pattern.compile(
		"[a-z][\\p{Alnum}-_.]*");

}