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
import com.liferay.gradle.plugins.service.builder.BuildServiceTask;
import com.liferay.gradle.plugins.service.builder.ServiceBuilderPlugin;
import com.liferay.gradle.plugins.tasks.BuildCssTask;
import com.liferay.gradle.plugins.tasks.DirectDeployTask;
import com.liferay.gradle.plugins.wsdd.builder.BuildWSDDTask;
import com.liferay.gradle.plugins.wsdd.builder.WSDDBuilderPlugin;
import com.liferay.gradle.plugins.wsdl.builder.BuildWSDLTask;
import com.liferay.gradle.plugins.xsd.builder.BuildXSDTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedModuleVersion;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.RelativePath;
import org.gradle.api.internal.file.copy.CopySpecInternal;
import org.gradle.api.internal.file.copy.CopySpecResolver;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.War;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayWebAppPlugin extends LiferayJavaPlugin {

	public static final String DIRECT_DEPLOY_TASK_NAME = "directDeploy";

	@Override
	protected Configuration addConfigurationProvided(Project project) {
		return null;
	}

	protected Task addTaskBuildServiceCompile(
		BuildServiceTask buildServiceTask) {

		Project project = buildServiceTask.getProject();

		JavaCompile javaCompile = GradleUtil.addTask(
			project, buildServiceTask.getName() + "Compile", JavaCompile.class);

		javaCompile.dependsOn(buildServiceTask);
		javaCompile.dependsOn(
			BasePlugin.CLEAN_TASK_NAME +
				StringUtil.capitalize(javaCompile.getName()));

		FileCollection fileCollection = buildServiceTask.getClasspath();

		final File serviceJarFile = getServiceJarFile(project);

		fileCollection = fileCollection.filter(
			new Spec<File>() {

				@Override
				public boolean isSatisfiedBy(File file) {
					if (file.equals(serviceJarFile)) {
						return false;
					}

					return true;
				}

			});

		javaCompile.setClasspath(fileCollection);

		File destinationDir = new File(
			project.getBuildDir(), javaCompile.getName());

		javaCompile.setDestinationDir(destinationDir);

		javaCompile.setSource(buildServiceTask.getApiDirName());

		return javaCompile;
	}

	protected Task addTaskBuildServiceJar(
		BuildServiceTask buildServiceTask, Task buildServiceCompileTask) {

		Project project = buildServiceTask.getProject();

		Jar jar = GradleUtil.addTask(
			project, buildServiceTask.getName() + "Jar", Jar.class);

		jar.from(buildServiceCompileTask.getOutputs());

		jar.setDescription("Assembles the service JAR file.");

		File serviceJarFile = getServiceJarFile(project);

		jar.setArchiveName(serviceJarFile.getName());
		jar.setDestinationDir(serviceJarFile.getParentFile());

		return jar;
	}

	protected void addTaskBuildServiceTasks(Project project) {
		BuildServiceTask buildServiceTask =
			(BuildServiceTask)GradleUtil.getTask(
				project, ServiceBuilderPlugin.BUILD_SERVICE_TASK_NAME);

		Task buildServiceCompileTask = addTaskBuildServiceCompile(
			buildServiceTask);

		Task buildServiceJarTask = addTaskBuildServiceJar(
			buildServiceTask, buildServiceCompileTask);

		buildServiceTask.finalizedBy(buildServiceJarTask);
	}

	@Override
	protected Copy addTaskDeploy(Project project) {
		Copy copy = super.addTaskDeploy(project);

		copy.setGroup(WarPlugin.WEB_APP_GROUP);

		return copy;
	}

	protected DirectDeployTask addTaskDirectDeploy(Project project) {
		DirectDeployTask directDeployTask = GradleUtil.addTask(
			project, DIRECT_DEPLOY_TASK_NAME, DirectDeployTask.class);

		directDeployTask.dependsOn(WarPlugin.WAR_TASK_NAME);

		directDeployTask.setDescription(
			"Assembles the project into a WAR file and directly deploys it " +
				"to Liferay, skipping the auto deploy directory.");
		directDeployTask.setGroup(WarPlugin.WEB_APP_GROUP);

		return directDeployTask;
	}

	@Override
	protected void addTasks(Project project) {
		super.addTasks(project);

		addTaskDirectDeploy(project);
	}

	@Override
	protected Task addTaskWar(Project project) {
		return null;
	}

	@Override
	protected void applyPlugins(Project project) {
		super.applyPlugins(project);

		GradleUtil.applyPlugin(project, WarPlugin.class);
	}

	@Override
	protected void configureDependencies(Project project) {
		super.configureDependencies(project);

		configureDependenciesProvidedCompile(project);
	}

	@Override
	protected void configureDependenciesCompile(Project project) {
		super.configureDependenciesCompile(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					File serviceJarFile = getServiceJarFile(project);

					if (serviceJarFile.exists()) {
						GradleUtil.addDependency(
							project, JavaPlugin.COMPILE_CONFIGURATION_NAME,
							serviceJarFile);
					}
				}

			});
	}

	protected void configureDependenciesProvidedCompile(Project project) {
		for (String dependencyNotation : COMPILE_DEPENDENCY_NOTATIONS) {
			GradleUtil.addDependency(
				project, WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
				dependencyNotation);
		}
	}

	@Override
	protected void configureProperties(Project project) {
		configureWebAppDirName(project);
	}

	@Override
	protected void configureSourceSetMain(Project project) {
		File classesDir = project.file("docroot/WEB-INF/classes");
		File srcDir = project.file("docroot/WEB-INF/src");

		configureSourceSetMain(project, classesDir, srcDir);
	}

	@Override
	protected void configureTaskBuildCssRootDirs(BuildCssTask buildCssTask) {
		FileCollection rootDirs = buildCssTask.getRootDirs();

		if ((rootDirs != null) && !rootDirs.isEmpty()) {
			return;
		}

		Project project = buildCssTask.getProject();

		buildCssTask.setRootDirs(getWebAppDir(project));
	}

	@Override
	protected void configureTaskBuildWSDD(Project project) {
		super.configureTaskBuildWSDD(project);

		BuildWSDDTask buildWSDDTask = (BuildWSDDTask)GradleUtil.getTask(
			project, WSDDBuilderPlugin.BUILD_WSDD_TASK_NAME);

		configureTaskBuildWSDDInputFileName(buildWSDDTask);
		configureTaskBuildWSDDServerConfigFileName(buildWSDDTask);
	}

	protected void configureTaskBuildWSDDInputFileName(
		BuildWSDDTask buildWSDDTask) {

		Project project = buildWSDDTask.getProject();

		File inputFile = new File(getWebAppDir(project), "WEB-INF/service.xml");

		buildWSDDTask.setInputFileName(project.relativePath(inputFile));
	}

	protected void configureTaskBuildWSDDServerConfigFileName(
		BuildWSDDTask buildWSDDTask) {

		Project project = buildWSDDTask.getProject();

		File serverConfigFile = new File(
			getWebAppDir(project), "WEB-INF/server-config.wsdd");

		buildWSDDTask.setServerConfigFileName(
			project.relativePath(serverConfigFile));
	}

	@Override
	protected void configureTaskBuildWSDLInputDir(BuildWSDLTask buildWSDLTask) {
		File inputDir = buildWSDLTask.getInputDir();

		if (!inputDir.exists()) {
			inputDir = new File(
				getWebAppDir(buildWSDLTask.getProject()), "WEB-INF/wsdl");

			buildWSDLTask.setInputDir(inputDir);
		}
	}

	protected void configureTaskBuildXSDInputDir(BuildXSDTask buildXSDTask) {
		File inputDir = new File(
			getWebAppDir(buildXSDTask.getProject()), "WEB-INF/xsd");

		buildXSDTask.setInputDir(inputDir);
	}

	@Override
	protected void configureTaskDeployFrom(Copy deployTask) {
		War war = (War)GradleUtil.getTask(
			deployTask.getProject(), WarPlugin.WAR_TASK_NAME);

		deployTask.from(war.getOutputs());
	}

	protected void configureTaskDirectDeploy(
		Project project, LiferayExtension liferayExtension) {

		DirectDeployTask directDeployTask =
			(DirectDeployTask)GradleUtil.getTask(
				project, DIRECT_DEPLOY_TASK_NAME);

		configureTaskDirectDeployAppServerDeployDir(
			directDeployTask, liferayExtension);
		configureTaskDirectDeployAppServerLibGlobalDir(
			directDeployTask, liferayExtension);
		configureTaskDirectDeployAppServerPortalDir(
			directDeployTask, liferayExtension);
		configureTaskDirectDeployAppServerType(
			directDeployTask, liferayExtension);
		configureTaskDirectDeployWebAppFile(directDeployTask);
		configureTaskDirectDeployWebAppType(directDeployTask);
	}

	protected void configureTaskDirectDeployAppServerDeployDir(
		DirectDeployTask directDeployTask, LiferayExtension liferayExtension) {

		if (directDeployTask.getAppServerDeployDir() == null) {
			directDeployTask.setAppServerDeployDir(
				liferayExtension.getAppServerDeployDir());
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

	protected void configureTaskDirectDeployWebAppFile(
		DirectDeployTask directDeployTask) {

		if (directDeployTask.getWebAppFile() != null) {
			return;
		}

		War war = (War)GradleUtil.getTask(
			directDeployTask.getProject(), WarPlugin.WAR_TASK_NAME);

		directDeployTask.setWebAppFile(war.getArchivePath());
	}

	protected void configureTaskDirectDeployWebAppType(
		DirectDeployTask directDeployTask) {

		if (Validator.isNull(directDeployTask.getWebAppType())) {
			directDeployTask.setWebAppType(
				getWebAppType(directDeployTask.getProject()));
		}
	}

	@Override
	protected void configureTasks(
		Project project, LiferayExtension liferayExtension) {

		super.configureTasks(project, liferayExtension);

		configureTaskDirectDeploy(project, liferayExtension);
		configureTaskWar(project, liferayExtension);

		addTaskBuildServiceTasks(project);
	}

	protected void configureTaskWar(
		Project project, LiferayExtension liferayExtension) {

		War war = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		configureTaskWarDuplicatesStrategy(war);
		configureTaskWarExcludeManifest(war);
		configureTaskWarFilesMatching(war);
		configureTaskWarOutputs(war);
		configureTaskWarRenameDependencies(war);
	}

	protected void configureTaskWarDuplicatesStrategy(War war) {
		war.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);
	}

	protected void configureTaskWarExcludeManifest(War war) {
		CopySpecInternal copySpecInternal = war.getRootSpec();

		for (CopySpecInternal childCopySpecInternal :
				copySpecInternal.getChildren()) {

			CopySpecResolver copySpecResolver =
				childCopySpecInternal.buildRootResolver();

			RelativePath destRelativePath = copySpecResolver.getDestPath();

			String destRelativePathString = destRelativePath.getPathString();

			if (destRelativePathString.equals("META-INF")) {
				childCopySpecInternal.exclude("**");

				return;
			}
		}
	}

	protected void configureTaskWarFilesMatching(War war) {
		final Project project = war.getProject();

		final Closure<String> closure = new Closure<String>(null) {

			@SuppressWarnings("unused")
			public String doCall(String line) {
				if (!line.contains("content/Language*.properties")) {
					return line;
				}

				StringBuilder sb = new StringBuilder();

				SourceSet sourceSet = GradleUtil.getSourceSet(
					project, SourceSet.MAIN_SOURCE_SET_NAME);

				FileTree fileTree = GradleUtil.getFilteredFileTree(
					sourceSet.getResources(), null,
					new String[] {"content/Language*.properties"});

				Iterator<File> iterator = fileTree.iterator();

				while (iterator.hasNext()) {
					File file = iterator.next();

					sb.append("\t<language-properties>content/");
					sb.append(file.getName());
					sb.append("</language-properties>");
					sb.append("\n");
				}

				if (sb.length() > 0) {
					sb.setLength(sb.length() - 1);
				}

				return sb.toString();
			}

		};

		war.filesMatching(
			"WEB-INF/liferay-hook.xml",
			new Action<FileCopyDetails>() {

				@Override
				public void execute(FileCopyDetails fileCopyDetails) {
					fileCopyDetails.filter(closure);
				}

			});
	}

	protected void configureTaskWarOutputs(War war) {
		TaskOutputs taskOutputs = war.getOutputs();

		taskOutputs.file(war.getArchivePath());
	}

	protected void configureTaskWarRenameDependencies(War war) {
		final Project project = war.getProject();

		Closure<String> closure = new Closure<String>(null) {

			@SuppressWarnings("unused")
			public String doCall(String name) {
				Map<String, String> newDependencyNames =
					_getNewDependencyNames();

				String newDependencyName = newDependencyNames.get(name);

				if (Validator.isNotNull(newDependencyName)) {
					return newDependencyName;
				}

				return name;
			}

			private Map<String, String> _getNewDependencyNames() {
				if (_newDependencyNames != null) {
					return _newDependencyNames;
				}

				_newDependencyNames = new HashMap<>();

				Configuration compileConfiguration =
					GradleUtil.getConfiguration(
						project, JavaPlugin.COMPILE_CONFIGURATION_NAME);

				ResolvedConfiguration resolvedConfiguration =
					compileConfiguration.getResolvedConfiguration();

				for (ResolvedArtifact resolvedArtifact :
						resolvedConfiguration.getResolvedArtifacts()) {

					ResolvedModuleVersion resolvedModuleVersion =
						resolvedArtifact.getModuleVersion();

					ModuleVersionIdentifier moduleVersionIdentifier =
						resolvedModuleVersion.getId();

					String oldDependencyName =
						moduleVersionIdentifier.getName() + "-" +
							moduleVersionIdentifier.getVersion() + ".jar";
					String newDependencyName =
						moduleVersionIdentifier.getName() + ".jar";

					_newDependencyNames.put(
						oldDependencyName, newDependencyName);
				}

				return _newDependencyNames;
			}

			private Map<String, String> _newDependencyNames;

		};

		CopySpecInternal copySpecInternal = war.getRootSpec();

		for (CopySpecInternal childCopySpecInternal :
				copySpecInternal.getChildren()) {

			childCopySpecInternal.rename(closure);
		}
	}

	@Override
	protected void configureVersion(
		Project project, LiferayExtension liferayExtension) {

		Object versionObj = project.getVersion();

		if (!versionObj.equals(Project.DEFAULT_VERSION)) {
			super.configureVersion(project, liferayExtension);

			return;
		}

		File pluginPackagePropertiesFile = new File(
			getWebAppDir(project), "WEB-INF/liferay-plugin-package.properties");

		Properties pluginPackageProperties;

		try {
			pluginPackageProperties = FileUtil.readProperties(
				pluginPackagePropertiesFile);
		}
		catch (Exception e) {
			throw new GradleException(
				"Unable to read " + pluginPackagePropertiesFile, e);
		}

		String version = pluginPackageProperties.getProperty(
			"module-full-version");

		if (Validator.isNull(version)) {
			version = pluginPackageProperties.getProperty(
				"module-incremental-version");

			version = liferayExtension.getVersionPrefix() + "." + version;
		}

		project.setVersion(version);
	}

	protected void configureWebAppDirName(Project project) {
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		warPluginConvention.setWebAppDirName("docroot");
	}

	@Override
	protected File getLibDir(Project project) {
		return new File(getWebAppDir(project), "WEB-INF/lib");
	}

	@Override
	protected File getServiceBaseDir(Project project) {
		return new File(getWebAppDir(project), "WEB-INF");
	}

	protected File getServiceJarFile(Project project) {
		return new File(getLibDir(project), project.getName() + "-service.jar");
	}

	protected File getWebAppDir(Project project) {
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		return warPluginConvention.getWebAppDir();
	}

	protected String getWebAppType(Project project) {
		String projectName = project.getName();

		int index = projectName.lastIndexOf("-");

		return projectName.substring(index + 1);
	}

}