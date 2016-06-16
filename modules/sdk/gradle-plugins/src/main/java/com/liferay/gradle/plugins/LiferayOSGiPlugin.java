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

import com.liferay.gradle.plugins.cache.CachePlugin;
import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.LiferayOSGiExtension;
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
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.CopySpec;
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
import org.gradle.api.tasks.bundling.AbstractArchiveTask;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.War;
import org.gradle.api.tasks.compile.CompileOptions;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.api.tasks.testing.Test;
import org.gradle.internal.Factory;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayOSGiPlugin implements Plugin<Project> {

	public static final String AUTO_CLEAN_PROPERTY_NAME = "autoClean";

	public static final String AUTO_UPDATE_XML_TASK_NAME = "autoUpdateXml";

	public static final String CLEAN_DEPLOYED_PROPERTY_NAME = "cleanDeployed";

	public static final String PLUGIN_NAME = "liferayOSGi";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);

		final LiferayOSGiExtension liferayOSGiExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, LiferayOSGiExtension.class);

		applyPlugins(project);

		addDeployedFile(project, JavaPlugin.JAR_TASK_NAME, false);

		addTaskAutoUpdateXml(project);
		addTasksBuildWSDDJar(project);

		configureArchivesBaseName(project);
		configureDescription(project);
		configureSourceSetMain(project);
		configureTaskClean(project);
		configureTaskJar(project);
		configureTaskJavadoc(project);
		configureTaskTest(project);
		configureTasksTest(project);

		if (GradleUtil.isRunningInsideDaemon()) {
			configureTasksJavaCompileFork(project, true);
		}

		configureVersion(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureBundleExtensionDefaults(
						project, liferayOSGiExtension);
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

	protected void addDeployedFile(
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
								return getDeployedFileName(abstractArchiveTask);
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
						return new File(
							copy.getDestinationDir(),
							getDeployedFileName(abstractArchiveTask));
					}

				});
		}
	}

	protected void addDeployedFile(
		Project project, String taskName, boolean lazy) {

		AbstractArchiveTask abstractArchiveTask =
			(AbstractArchiveTask)GradleUtil.getTask(project, taskName);

		addDeployedFile(abstractArchiveTask, lazy);
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

	protected Jar addTaskBuildWSDDJar(final BuildWSDDTask buildWSDDTask) {
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

					String bundleName = getBundleInstruction(
						project, Constants.BUNDLE_NAME);

					if (Validator.isNotNull(bundleName)) {
						properties.put(
							Constants.BUNDLE_NAME,
							bundleName + " WSDD descriptors");
					}

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

		addDeployedFile(jar, true);

		return jar;
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

	protected void applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, BundlePlugin.class);

		configureBundleExtension(project);

		// "bundle" must be applied before "java", otherwise it will be too late
		// to replace the JarBuilderFactory.

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
		GradleUtil.applyPlugin(project, ServiceBuilderDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
		GradleUtil.applyPlugin(project, SoyPlugin.class);
		GradleUtil.applyPlugin(project, TLDDocBuilderPlugin.class);
		GradleUtil.applyPlugin(project, TLDFormatterDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, TLDFormatterPlugin.class);
		GradleUtil.applyPlugin(project, TestIntegrationDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, TestIntegrationPlugin.class);
		GradleUtil.applyPlugin(
			project, UpgradeTableBuilderDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, WSDDBuilderDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, XMLFormatterDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, XMLFormatterPlugin.class);
	}

	protected void configureArchivesBaseName(Project project) {
		BasePluginConvention basePluginConvention = GradleUtil.getConvention(
			project, BasePluginConvention.class);

		String bundleSymbolicName = getBundleInstruction(
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

	protected void configureBundleExtension(Project project) {
		replaceJarBuilderFactory(project);

		BundleExtension bundleExtension = GradleUtil.getExtension(
			project, BundleExtension.class);

		bundleExtension.setFailOnError(true);

		Map<String, String> bundleInstructions = getBundleInstructions(
			bundleExtension);

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

		if (Validator.isNotNull(description)) {
			project.setDescription(description);
		}
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

	protected void configureTaskClean(Project project) {
		Task task = GradleUtil.getTask(project, BasePlugin.CLEAN_TASK_NAME);

		if (task instanceof Delete) {
			configureTaskCleanDependsOn((Delete)task);
		}
	}

	protected void configureTaskCleanDependsOn(Delete delete) {
		Project project = delete.getProject();

		Closure<Set<String>> closure = new Closure<Set<String>>(project) {

			@SuppressWarnings("unused")
			public Set<String> doCall(Delete delete) {
				Set<String> cleanTaskNames = new HashSet<>();

				Project project = delete.getProject();

				for (Task task : project.getTasks()) {
					String taskName = task.getName();

					if (taskName.equals(LiferayBasePlugin.DEPLOY_TASK_NAME) ||
						taskName.equals(
							EclipsePlugin.getECLIPSE_CP_TASK_NAME()) ||
						taskName.equals(
							EclipsePlugin.getECLIPSE_PROJECT_TASK_NAME()) ||
						taskName.equals("ideaModule") ||
						(task instanceof BuildSoyTask)) {

						continue;
					}

					if (GradleUtil.hasPlugin(project, CachePlugin.class) &&
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

	protected void configureTaskJar(Project project) {
		File bndFile = project.file("bnd.bnd");

		if (!bndFile.exists()) {
			return;
		}

		Task jarTask = GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		TaskInputs taskInputs = jarTask.getInputs();

		taskInputs.file(bndFile);
	}

	protected void configureTaskJavaCompileFork(
		JavaCompile javaCompile, boolean fork) {

		CompileOptions compileOptions = javaCompile.getOptions();

		compileOptions.setFork(fork);
	}

	protected void configureTaskJavadoc(Project project) {
		String bundleName = getBundleInstruction(
			project, Constants.BUNDLE_NAME);
		String bundleVersion = getBundleInstruction(
			project, Constants.BUNDLE_VERSION);

		if (Validator.isNull(bundleName) || Validator.isNull(bundleVersion)) {
			return;
		}

		Javadoc javadoc = (Javadoc)GradleUtil.getTask(
			project, JavaPlugin.JAVADOC_TASK_NAME);

		String title = String.format("%s %s API", bundleName, bundleVersion);

		javadoc.setTitle(title);
	}

	protected void configureTasksJavaCompileFork(
		Project project, final boolean fork) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			JavaCompile.class,
			new Action<JavaCompile>() {

				@Override
				public void execute(JavaCompile javaCompile) {
					configureTaskJavaCompileFork(javaCompile, fork);
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

		test.jvmArgs(
			"-Djava.net.preferIPv4Stack=true", "-Dliferay.mode=test",
			"-Duser.timezone=GMT");

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

	protected void configureVersion(Project project) {
		String bundleVersion = getBundleInstruction(
			project, Constants.BUNDLE_VERSION);

		if (Validator.isNotNull(bundleVersion)) {
			project.setVersion(bundleVersion);
		}
	}

	protected String getBundleInstruction(Project project, String key) {
		Map<String, String> bundleInstructions = getBundleInstructions(project);

		return bundleInstructions.get(key);
	}

	protected Map<String, String> getBundleInstructions(
		BundleExtension bundleExtension) {

		return (Map<String, String>)bundleExtension.getInstructions();
	}

	protected Map<String, String> getBundleInstructions(Project project) {
		BundleExtension bundleExtension = GradleUtil.getExtension(
			project, BundleExtension.class);

		return getBundleInstructions(bundleExtension);
	}

	protected String getDeployedFileName(
		AbstractArchiveTask abstractArchiveTask) {

		String fileName = abstractArchiveTask.getBaseName();

		String appendix = abstractArchiveTask.getAppendix();

		if (Validator.isNotNull(appendix)) {
			fileName += "-" + appendix;
		}

		fileName += "." + abstractArchiveTask.getExtension();

		return fileName;
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