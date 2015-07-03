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

import com.liferay.gradle.plugins.css.builder.BuildCSSTask;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.LiferayOSGiExtension;
import com.liferay.gradle.plugins.service.builder.BuildServiceTask;
import com.liferay.gradle.plugins.tasks.DirectDeployTask;
import com.liferay.gradle.plugins.wsdd.builder.BuildWSDDTask;
import com.liferay.gradle.plugins.wsdd.builder.WSDDBuilderPlugin;
import com.liferay.gradle.plugins.xsd.builder.XSDBuilderPlugin;
import com.liferay.gradle.util.ArrayUtil;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;

import nebula.plugin.extraconfigurations.ProvidedBasePlugin;

import org.dm.gradle.plugins.bundle.BundleExtension;
import org.dm.gradle.plugins.bundle.BundlePlugin;
import org.dm.gradle.plugins.bundle.BundleUtils;
import org.dm.gradle.plugins.bundle.JarBuilder;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.War;
import org.gradle.api.tasks.bundling.Zip;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.internal.Factory;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayOSGiPlugin extends LiferayJavaPlugin {

	public static final String AUTO_UPDATE_XML_TASK_NAME = "autoUpdateXml";

	public static final String BUILD_WSDD_JAR_TASK_NAME =
		WSDDBuilderPlugin.BUILD_WSDD_TASK_NAME + "Jar";

	public static final String COPY_LIBS_TASK_NAME = "copyLibs";

	@Override
	public void apply(Project project) {
		super.apply(project);

		configureBundleExtension(project);

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

	protected Task addTaskBuildWSDDJar(final Project project) {
		Task task = project.task(BUILD_WSDD_JAR_TASK_NAME);

		TaskOutputs taskOutputs = task.getOutputs();

		taskOutputs.file(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return getWSDDJarFile(project);
				}

			});

		task.doLast(
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
					properties.put(
						Constants.INCLUDE_RESOURCE,
						"WEB-INF/=server-config.wsdd,classes;filter:=*.wsdd");

					jarBuilder.withProperties(properties);

					jarBuilder.withSourcepath(BundleUtils.getSources(project));
					jarBuilder.withTrace(bundleExtension.isTrace());
					jarBuilder.withVersion(BundleUtils.getVersion(project));

					TaskOutputs taskOutputs = task.getOutputs();

					FileCollection fileCollection = taskOutputs.getFiles();

					jarBuilder.writeJarTo(fileCollection.getSingleFile());
				}

			});

		BuildWSDDTask buildWSDDTask = (BuildWSDDTask)GradleUtil.getTask(
			project, WSDDBuilderPlugin.BUILD_WSDD_TASK_NAME);

		buildWSDDTask.finalizedBy(task);

		return task;
	}

	protected Copy addTaskCopyLibs(final Project project) {
		Copy copy = GradleUtil.addTask(
			project, COPY_LIBS_TASK_NAME, Copy.class);

		copy.from(
			new Callable<FileCollection>() {

				@Override
				public FileCollection call() throws Exception {
					JavaCompile javaCompile = (JavaCompile)GradleUtil.getTask(
						project, JavaPlugin.COMPILE_JAVA_TASK_NAME);

					return javaCompile.getClasspath();
				}

			});

		copy.include(
			new Spec<FileTreeElement>() {

				@Override
				public boolean isSatisfiedBy(FileTreeElement fileTreeElement) {
					File file = fileTreeElement.getFile();

					if (ArrayUtil.contains(
							_COPY_LIBS_FILE_NAMES, file.getName())) {

						return true;
					}

					Gradle gradle = project.getGradle();

					if (FileUtil.isChild(file, gradle.getGradleUserHomeDir())) {
						return true;
					}

					Set<File> projectDependencyDirs =
						_getProjectDependencyDirs();

					if (projectDependencyDirs.contains(file.getParentFile())) {
						return true;
					}

					return false;
				}

				private Set<File> _getProjectDependencyDirs() {
					if (_projectDependencyDirs != null) {
						return _projectDependencyDirs;
					}

					_projectDependencyDirs = new HashSet<>();

					Configuration configuration = GradleUtil.getConfiguration(
						project, JavaPlugin.COMPILE_CONFIGURATION_NAME);

					DependencySet dependencySet =
						configuration.getAllDependencies();

					Set<ProjectDependency> projectDependencies =
						dependencySet.withType(ProjectDependency.class);

					for (ProjectDependency projectDependency :
							projectDependencies) {

						Jar dependencyJar = (Jar)GradleUtil.getTask(
							projectDependency.getDependencyProject(),
							JavaPlugin.JAR_TASK_NAME);

						_projectDependencyDirs.add(
							dependencyJar.getDestinationDir());
					}

					return _projectDependencyDirs;
				}

				private Set<File> _projectDependencyDirs;

			});

		copy.into(getLibDir(project));

		copy.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					Project project = task.getProject();

					String[] includeResourceArray = new String[2];

					includeResourceArray[0] = getBundleInstruction(
						project, Constants.INCLUDERESOURCE);
					includeResourceArray[1] = getBundleInstruction(
						project, Constants.INCLUDE_RESOURCE);

					for (String includeResource : includeResourceArray) {
						if (Validator.isNull(includeResource)) {
							continue;
						}

						String[] resources = includeResource.split(",");

						String libDirName = project.relativePath(
							getLibDir(project));

						libDirName = libDirName.replace('\\', '/') + "/";

						for (String resource : resources) {
							if (resource.contains("@" + libDirName) ||
								resource.startsWith(libDirName)) {

								return true;
							}
						}
					}

					return false;
				}

			});

		Closure<String> closure = new RenameDependencyClosure(
			project, JavaPlugin.RUNTIME_CONFIGURATION_NAME,
			ProvidedBasePlugin.getPROVIDED_CONFIGURATION_NAME());

		copy.rename(closure);

		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		jar.dependsOn(copy);

		return copy;
	}

	@Override
	protected void addTasks(Project project) {
		super.addTasks(project);

		addTaskAutoUpdateXml(project);
		addTaskCopyLibs(project);
		addTaskBuildWSDDJar(project);
	}

	@Override
	protected Task addTaskSetupArquillian(Project project) {
		Task task = super.addTaskSetupArquillian(project);

		task.setEnabled(false);

		return task;
	}

	@Override
	protected void applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, BundlePlugin.class);

		replaceJarBuilderFactory(project);

		super.applyPlugins(project);
	}

	protected void configureBundleExtension(Project project) {
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

	@Override
	protected void configureSourceSetMain(Project project) {
		File docrootDir = project.file("docroot");

		if (!docrootDir.exists()) {
			super.configureSourceSetMain(project);

			return;
		}

		File classesDir = new File(docrootDir, "WEB-INF/classes");
		File srcDir = new File(docrootDir, "WEB-INF/src");

		configureSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME, classesDir, srcDir);
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
	protected void configureTaskBuildCSSDocrootDirName(
		BuildCSSTask buildCSSTask) {

		Project project = buildCSSTask.getProject();

		String docrootDirName = buildCSSTask.getDocrootDirName();

		if (Validator.isNotNull(docrootDirName) &&
			FileUtil.exists(project, docrootDirName)) {

			return;
		}

		File docrootDir = project.file("docroot");

		if (!docrootDir.exists()) {
			super.configureTaskBuildCSSDocrootDirName(buildCSSTask);

			return;
		}

		buildCSSTask.setDocrootDirName(project.relativePath(docrootDir));
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

	@Override
	protected void configureTaskBuildXSD(Project project) {
		Zip zip = (Zip)GradleUtil.getTask(
			project, XSDBuilderPlugin.BUILD_XSD_TASK_NAME);

		configureTaskBuildXSDArchiveName(zip);
	}

	protected void configureTaskBuildXSDArchiveName(Zip zip) {
		String bundleSymbolicName = getBundleInstruction(
			zip.getProject(), Constants.BUNDLE_SYMBOLICNAME);

		zip.setArchiveName(
			bundleSymbolicName + "-xbean." + Jar.DEFAULT_EXTENSION);
	}

	@Override
	protected void configureTaskDeploy(
		Project project, LiferayExtension liferayExtension) {

		super.configureTaskDeploy(project, liferayExtension);

		Copy deployTask = (Copy)GradleUtil.getTask(project, DEPLOY_TASK_NAME);

		configureTaskDeployRename(deployTask);
	}

	@Override
	protected void configureTaskDeployFrom(Copy copy, CopySpec copySpec) {
		super.configureTaskDeployFrom(copy, copySpec);

		File wsddJarFile = getWSDDJarFile(copy.getProject());

		if (wsddJarFile.exists()) {
			copySpec.from(wsddJarFile);
		}
	}

	protected void configureTaskDeployRename(Copy deployTask) {
		final Project project = deployTask.getProject();

		Closure<String> closure = new Closure<String>(null) {

			@SuppressWarnings("unused")
			public String doCall(String fileName) {
				return fileName.replace(
					"-" + project.getVersion() + "." + Jar.DEFAULT_EXTENSION,
					"." + Jar.DEFAULT_EXTENSION);
			}

		};

		deployTask.rename(closure);
	}

	@Override
	protected void configureTaskJar(Project project) {
		super.configureTaskJar(project);

		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		configureTaskJarArchiveName(jar);
	}

	protected void configureTaskJarArchiveName(Jar jar) {
		String bundleSymbolicName = getBundleInstruction(
			jar.getProject(), Constants.BUNDLE_SYMBOLICNAME);

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

		configureTaskBuildXSD(project);

		configureTaskAutoUpdateXml(project);
	}

	@Override
	protected void configureVersion(
		Project project, LiferayExtension liferayExtension) {

		String bundleVersion = getBundleInstruction(
			project, Constants.BUNDLE_VERSION);

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

	protected File getWSDDJarFile(Project project) {
		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		String bundleSymbolicName = getBundleInstruction(
			project, Constants.BUNDLE_SYMBOLICNAME);

		String fileName =
			bundleSymbolicName + "-wsdd-" + project.getVersion() +
				"." + Jar.DEFAULT_EXTENSION;

		return new File(jar.getDestinationDir(), fileName);
	}

	protected void replaceJarBuilderFactory(Project project) {
		BundleExtension bundleExtension = GradleUtil.getExtension(
			project, BundleExtension.class);

		bundleExtension.setJarBuilderFactory(
			new LiferayJarBuilderFactory(project));
	}

	private static final String[] _COPY_LIBS_FILE_NAMES = {
		"util-bridges.jar", "util-java.jar", "util-taglib.jar"
	};

	private static class LiferayJarBuilder extends JarBuilder {

		public void addClasspath(File file) {
			try {
				builder.addClasspath(file);
			}
			catch (Exception e) {
				throw new GradleException(e.getMessage(), e);
			}
		}

		@Override
		public JarBuilder withClasspath(Object files) {

			// Prevent JarBuilderFactoryDecorator from adding
			// configurations.runtime.files.

			return this;
		}

		@Override
		public JarBuilder withResources(Object files) {

			// Prevent JarBuilderFactoryDecorator from adding
			// sourceSets.main.output.classesDir/resourcesDir.

			return this;
		}

	}

	private static class LiferayJarBuilderFactory
		implements Factory<JarBuilder> {

		public LiferayJarBuilderFactory(Project project) {
			_project = project;
		}

		@Override
		public JarBuilder create() {
			LiferayJarBuilder liferayJarBuilder = new LiferayJarBuilder();

			SourceSet sourceSet = GradleUtil.getSourceSet(
				_project, SourceSet.MAIN_SOURCE_SET_NAME);

			SourceSetOutput sourceSetOutput = sourceSet.getOutput();

			liferayJarBuilder.addClasspath(sourceSetOutput.getClassesDir());

			return liferayJarBuilder;
		}

		private final Project _project;

	}

}