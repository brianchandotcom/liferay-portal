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

import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
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
import com.liferay.gradle.plugins.internal.JspCDefaultsPlugin;
import com.liferay.gradle.plugins.internal.LangBuilderDefaultsPlugin;
import com.liferay.gradle.plugins.internal.ServiceBuilderDefaultsPlugin;
import com.liferay.gradle.plugins.internal.TLDFormatterDefaultsPlugin;
import com.liferay.gradle.plugins.internal.TestIntegrationDefaultsPlugin;
import com.liferay.gradle.plugins.internal.UpgradeTableBuilderDefaultsPlugin;
import com.liferay.gradle.plugins.internal.WSDDBuilderDefaultsPlugin;
import com.liferay.gradle.plugins.internal.XMLFormatterDefaultsPlugin;
import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.jasper.jspc.JspCPlugin;
import com.liferay.gradle.plugins.javadoc.formatter.JavadocFormatterPlugin;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorPlugin;
import com.liferay.gradle.plugins.js.transpiler.JSTranspilerPlugin;
import com.liferay.gradle.plugins.lang.builder.LangBuilderPlugin;
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
import com.liferay.gradle.plugins.wsdd.builder.BuildWSDDTask;
import com.liferay.gradle.plugins.wsdd.builder.WSDDBuilderPlugin;
import com.liferay.gradle.plugins.wsdl.builder.WSDLBuilderPlugin;
import com.liferay.gradle.plugins.xml.formatter.XMLFormatterPlugin;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;
import java.io.OutputStream;

import java.nio.charset.StandardCharsets;

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
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.ApplicationPlugin;
import org.gradle.api.plugins.ApplicationPluginConvention;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.BasePluginConvention;
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
import org.gradle.internal.Factory;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayOSGiPlugin implements Plugin<Project> {

	public static final String AUTO_CLEAN_PROPERTY_NAME = "autoClean";

	public static final String AUTO_UPDATE_XML_TASK_NAME = "autoUpdateXml";

	public static final String CLEAN_DEPLOYED_PROPERTY_NAME = "cleanDeployed";

	public static final String COMPILE_INCLUDE_CONFIGURATION_NAME =
		"compileInclude";

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
		_addTasksBuildWSDDJar(project, liferayExtension);

		_configureArchivesBaseName(project);
		_configureDescription(project);
		_configureLiferay(project, liferayExtension);
		_configureSourceSetMain(project);
		_configureTaskClean(project);
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

	public static class LiferayJarBuilderFactory
		implements Factory<JarBuilder> {

		@Override
		public JarBuilder create() {
			LiferayJarBuilder liferayJarBuilder = new LiferayJarBuilder();

			return liferayJarBuilder.withContextClassLoader(
				_contextClassLoader);
		}

		public ClassLoader getContextClassLoader() {
			return _contextClassLoader;
		}

		public void setContextClassLoader(ClassLoader contextClassLoader) {
			_contextClassLoader = contextClassLoader;
		}

		private ClassLoader _contextClassLoader;

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

		if (GradleUtil.getProperty(
				delete, CLEAN_DEPLOYED_PROPERTY_NAME, true)) {

			delete.delete(
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						Closure<String> deployedFileNameClosure =
							liferayExtension.getDeployedFileNameClosure();

						return new File(
							copy.getDestinationDir(),
							deployedFileNameClosure.call(abstractArchiveTask));
					}

				});
		}
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
						{deployedPluginDirName, "WEB-INF/tld/*"}
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

	private Jar _addTaskBuildWSDDJar(
		final BuildWSDDTask buildWSDDTask, LiferayExtension liferayExtension) {

		Project project = buildWSDDTask.getProject();

		Jar jar = GradleUtil.addTask(
			project, buildWSDDTask.getName() + "Jar", Jar.class);

		jar.dependsOn(buildWSDDTask);

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

					Map<String, String> properties = _getProperties(project);

					jarBuilder.withBase(BundleUtils.getBase(project));
					jarBuilder.withClasspath(_getClasspath(project));
					jarBuilder.withFailOnError(true);
					jarBuilder.withName(
						properties.get(Constants.BUNDLE_SYMBOLICNAME));
					jarBuilder.withProperties(properties);
					jarBuilder.withResources(new File[0]);
					jarBuilder.withSourcepath(BundleUtils.getSources(project));
					jarBuilder.withTrace(bundleExtension.isTrace());
					jarBuilder.withVersion(BundleUtils.getVersion(project));

					TaskOutputs taskOutputs = task.getOutputs();

					FileCollection fileCollection = taskOutputs.getFiles();

					jarBuilder.writeJarTo(fileCollection.getSingleFile());
				}

				private File[] _getClasspath(Project project) {
					SourceSet sourceSet = GradleUtil.getSourceSet(
						project, SourceSet.MAIN_SOURCE_SET_NAME);

					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					return new File[] {
						sourceSetOutput.getClassesDir(),
						sourceSetOutput.getResourcesDir()
					};
				}

				private Map<String, String> _getProperties(Project project) {
					LiferayOSGiExtension liferayOSGiExtension =
						GradleUtil.getExtension(
							project, LiferayOSGiExtension.class);

					Map<String, String> properties =
						liferayOSGiExtension.getBundleDefaultInstructions();

					properties.remove(Constants.DONOTCOPY);

					String bundleName = _getBundleInstruction(
						project, Constants.BUNDLE_NAME);

					if (Validator.isNotNull(bundleName)) {
						properties.put(
							Constants.BUNDLE_NAME,
							bundleName + " WSDD descriptors");
					}

					String bundleSymbolicName = _getBundleInstruction(
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
		GradleUtil.applyPlugin(project, BundlePlugin.class);

		_configureBundleExtension(project);

		// "bundle" must be applied before "java", otherwise it will be too late
		// to replace the JarBuilderFactory.

		GradleUtil.applyPlugin(project, JavaPlugin.class);

		GradleUtil.applyPlugin(project, CSSBuilderPlugin.class);
		GradleUtil.applyPlugin(project, JSModuleConfigGeneratorPlugin.class);
		GradleUtil.applyPlugin(project, JSTranspilerPlugin.class);
		GradleUtil.applyPlugin(project, JavadocFormatterPlugin.class);
		GradleUtil.applyPlugin(project, JspCPlugin.class);
		GradleUtil.applyPlugin(project, LangBuilderPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
		GradleUtil.applyPlugin(project, SoyPlugin.class);
		GradleUtil.applyPlugin(project, SoyTranslationPlugin.class);
		GradleUtil.applyPlugin(project, TLDDocBuilderPlugin.class);
		GradleUtil.applyPlugin(project, TLDFormatterPlugin.class);
		GradleUtil.applyPlugin(project, TestIntegrationPlugin.class);
		GradleUtil.applyPlugin(project, XMLFormatterPlugin.class);

		AlloyTaglibDefaultsPlugin.INSTANCE.apply(project);
		CSSBuilderDefaultsPlugin.INSTANCE.apply(project);
		DBSupportDefaultsPlugin.INSTANCE.apply(project);
		EclipseDefaultsPlugin.INSTANCE.apply(project);
		FindBugsDefaultsPlugin.INSTANCE.apply(project);
		IdeaDefaultsPlugin.INSTANCE.apply(project);
		JSModuleConfigGeneratorDefaultsPlugin.INSTANCE.apply(project);
		JavadocFormatterDefaultsPlugin.INSTANCE.apply(project);
		JspCDefaultsPlugin.INSTANCE.apply(project);
		LangBuilderDefaultsPlugin.INSTANCE.apply(project);
		ServiceBuilderDefaultsPlugin.INSTANCE.apply(project);
		TLDFormatterDefaultsPlugin.INSTANCE.apply(project);
		TestIntegrationDefaultsPlugin.INSTANCE.apply(project);
		UpgradeTableBuilderDefaultsPlugin.INSTANCE.apply(project);
		WSDDBuilderDefaultsPlugin.INSTANCE.apply(project);
		XMLFormatterDefaultsPlugin.INSTANCE.apply(project);
	}

	private void _configureApplication(Project project) {
		ApplicationPluginConvention applicationPluginConvention =
			GradleUtil.getConvention(
				project, ApplicationPluginConvention.class);

		String mainClassName = _getBundleInstruction(project, "Main-Class");

		if (Validator.isNotNull(mainClassName)) {
			applicationPluginConvention.setMainClassName(mainClassName);
		}
	}

	private void _configureArchivesBaseName(Project project) {
		BasePluginConvention basePluginConvention = GradleUtil.getConvention(
			project, BasePluginConvention.class);

		String bundleSymbolicName = _getBundleInstruction(
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
		_replaceJarBuilderFactory(project);

		BundleExtension bundleExtension = GradleUtil.getExtension(
			project, BundleExtension.class);

		bundleExtension.setFailOnError(true);

		File file = project.file("bnd.bnd");

		if (file.exists()) {
			Map<String, Object> bundleInstructions = _getBundleInstructions(
				bundleExtension);

			Properties properties = GUtil.loadProperties(file);

			Enumeration<Object> keys = properties.keys();

			while (keys.hasMoreElements()) {
				String key = (String)keys.nextElement();

				String value = properties.getProperty(key);

				bundleInstructions.put(key, value);
			}
		}
	}

	private void _configureBundleExtensionDefaults(
		Project project, final LiferayOSGiExtension liferayOSGiExtension,
		final Configuration compileIncludeConfiguration) {

		Map<String, Object> bundleInstructions = _getBundleInstructions(
			project);

		bundleInstructions.put(
			Constants.INCLUDERESOURCE + "." +
				compileIncludeConfiguration.getName(),
			new Object() {

				@Override
				public String toString() {
					boolean expandCompileInclude =
						liferayOSGiExtension.isExpandCompileInclude();

					StringBuilder sb = new StringBuilder();

					for (File file : compileIncludeConfiguration) {
						if (sb.length() > 0) {
							sb.append(',');
						}

						if (expandCompileInclude) {
							sb.append('@');
						}
						else {
							sb.append("lib/=");
						}

						sb.append(file.getAbsolutePath());

						if (!expandCompileInclude) {
							sb.append(";lib:=true");
						}
					}

					return sb.toString();
				}

			});

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

	private void _configureDescription(Project project) {
		String description = _getBundleInstruction(
			project, Constants.BUNDLE_DESCRIPTION);

		if (Validator.isNull(description)) {
			description = _getBundleInstruction(project, Constants.BUNDLE_NAME);
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

	private void _configureTaskClean(Project project) {
		Task task = GradleUtil.getTask(project, BasePlugin.CLEAN_TASK_NAME);

		if (task instanceof Delete) {
			_configureTaskCleanDependsOn((Delete)task);
		}
	}

	private void _configureTaskCleanDependsOn(Delete delete) {
		Project project = delete.getProject();

		Closure<Set<String>> closure = new Closure<Set<String>>(project) {

			@SuppressWarnings("unused")
			public Set<String> doCall(Delete delete) {
				Set<String> cleanTaskNames = new HashSet<>();

				Project project = delete.getProject();

				for (Task task : project.getTasks()) {
					String taskName = task.getName();

					if (taskName.equals(LiferayBasePlugin.DEPLOY_TASK_NAME) ||
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

	private void _configureTaskJar(Project project) {
		File bndFile = project.file("bnd.bnd");

		if (!bndFile.exists()) {
			return;
		}

		Task jarTask = GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		TaskInputs taskInputs = jarTask.getInputs();

		taskInputs.file(bndFile);
	}

	private void _configureTaskJavaCompileFork(
		JavaCompile javaCompile, boolean fork) {

		CompileOptions compileOptions = javaCompile.getOptions();

		compileOptions.setFork(fork);
	}

	private void _configureTaskJavadoc(Project project) {
		String bundleName = _getBundleInstruction(
			project, Constants.BUNDLE_NAME);
		String bundleVersion = _getBundleInstruction(
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

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureTaskTestIncludes(test);
				}

			});
	}

	private void _configureTaskTestDefaultCharacterEncoding(Test test) {
		test.setDefaultCharacterEncoding(StandardCharsets.UTF_8.name());
	}

	private void _configureTaskTestIncludes(Test test) {
		Set<String> includes = test.getIncludes();

		if (includes.isEmpty()) {
			test.setIncludes(Collections.singleton("**/*Test.class"));
		}
	}

	private void _configureVersion(Project project) {
		String bundleVersion = _getBundleInstruction(
			project, Constants.BUNDLE_VERSION);

		if (Validator.isNotNull(bundleVersion)) {
			project.setVersion(bundleVersion);
		}
	}

	private String _getBundleInstruction(Project project, String key) {
		Map<String, Object> bundleInstructions = _getBundleInstructions(
			project);

		return GradleUtil.toString(bundleInstructions.get(key));
	}

	private Map<String, Object> _getBundleInstructions(
		BundleExtension bundleExtension) {

		return (Map<String, Object>)bundleExtension.getInstructions();
	}

	private Map<String, Object> _getBundleInstructions(Project project) {
		BundleExtension bundleExtension = GradleUtil.getExtension(
			project, BundleExtension.class);

		return _getBundleInstructions(bundleExtension);
	}

	private void _replaceJarBuilderFactory(Project project) {
		BundleExtension bundleExtension = GradleUtil.getExtension(
			project, BundleExtension.class);

		bundleExtension.setJarBuilderFactory(new LiferayJarBuilderFactory());
	}

	private static final String _CACHE_PLUGIN_ID = "com.liferay.cache";

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

		public JarBuilder withContextClassLoader(
			ClassLoader contextClassLoader) {

			_contextClassLoader = contextClassLoader;

			return this;
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

		@Override
		public void writeJarTo(File file) {
			ClassLoader contextClassLoader = _replaceContextClassLoader(
				_contextClassLoader);

			try {
				super.writeJarTo(file);
			}
			finally {
				_replaceContextClassLoader(contextClassLoader);
			}
		}

		@Override
		public void writeManifestTo(OutputStream outputStream) {
			ClassLoader contextClassLoader = _replaceContextClassLoader(
				_contextClassLoader);

			try {
				super.writeManifestTo(outputStream);
			}
			finally {
				_replaceContextClassLoader(contextClassLoader);
			}
		}

		@Override
		public void writeManifestTo(
			OutputStream outputStream,
			@SuppressWarnings("rawtypes") Closure closure) {

			ClassLoader contextClassLoader = _replaceContextClassLoader(
				_contextClassLoader);

			try {
				super.writeManifestTo(outputStream, closure);
			}
			finally {
				_replaceContextClassLoader(contextClassLoader);
			}
		}

		private ClassLoader _replaceContextClassLoader(
			ClassLoader newContextClassLoader) {

			if (newContextClassLoader == null) {
				return null;
			}

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			currentThread.setContextClassLoader(newContextClassLoader);

			return contextClassLoader;
		}

		private final Set<File> _classpathFiles = new HashSet<>();
		private ClassLoader _contextClassLoader;
		private final Set<File> _resourceFiles = new HashSet<>();

	}

}