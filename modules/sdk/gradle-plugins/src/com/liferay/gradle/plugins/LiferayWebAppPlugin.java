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
import com.liferay.gradle.plugins.tasks.BuildCssTask;
import com.liferay.gradle.plugins.tasks.BuildWsdlTask;
import com.liferay.gradle.plugins.tasks.DirectDeployTask;
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.plugins.util.GradleUtil;
import com.liferay.gradle.plugins.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.file.copy.CopySpecInternal;
import org.gradle.api.internal.file.copy.CopySpecResolver;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.War;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayWebAppPlugin extends LiferayJavaPlugin {

	public static final String DEPLOY_TASK_NAME = "deploy";

	public static final String DIRECT_DEPLOY_TASK_NAME = "directDeploy";

	@Override
	public void apply(Project project) {
		super.apply(project);

		configureWebAppDirName(project);
	}

	protected Copy addTaskDeploy(Project project) {
		Copy copy = GradleUtil.addTask(project, DEPLOY_TASK_NAME, Copy.class);

		copy.dependsOn(WarPlugin.WAR_TASK_NAME);

		copy.setDescription(
			"Assembles the project into a WAR file and deploys it to Liferay.");
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
	protected void addTasks(
		Project project, LiferayExtension liferayExtension) {

		super.addTasks(project, liferayExtension);

		addTaskDeploy(project);
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
	protected void configureDependencies(
		Project project, LiferayExtension liferayExtension) {

		super.configureDependencies(project, liferayExtension);

		configureDependenciesProvidedCompile(project, liferayExtension);
	}

	@Override
	protected void configureDependenciesCompile(
		Project project, LiferayExtension liferayExtension) {

		super.configureDependenciesCompile(project, liferayExtension);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					File serviceJarFile = new File(
						getWebAppDir(project),
						"WEB-INF/lib/" + project.getName() + "-service.jar");

					if (serviceJarFile.exists()) {
						GradleUtil.addDependency(
							project, JavaPlugin.COMPILE_CONFIGURATION_NAME,
							serviceJarFile);
					}
				}

			});
	}

	protected void configureDependenciesProvidedCompile(
		Project project, LiferayExtension liferayExtension) {

		for (String dependencyNotation : COMPILE_DEPENDENCY_NOTATIONS) {
			GradleUtil.addDependency(
				project, WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
				dependencyNotation);
		}
	}

	@Override
	protected void configureSourceSetMain(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet javaSourceDirectorySet = sourceSet.getJava();

		File srcDir = project.file("docroot/WEB-INF/src");

		Set<File> srcDirs = Collections.singleton(srcDir);

		javaSourceDirectorySet.setSrcDirs(srcDirs);

		SourceDirectorySet resourcesSourceDirectorySet =
			sourceSet.getResources();

		resourcesSourceDirectorySet.setSrcDirs(srcDirs);
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
	protected void configureTaskBuildWsdlDestinationDir(
		BuildWsdlTask buildWsdlTask) {

		if (buildWsdlTask.getDestinationDir() != null) {
			return;
		}

		Project project = buildWsdlTask.getProject();

		File destinationDir = new File(getWebAppDir(project), "WEB-INF/lib");

		buildWsdlTask.setDestinationDir(destinationDir);
	}

	@Override
	protected void configureTaskBuildWsdlRootDirs(BuildWsdlTask buildWsdlTask) {
		FileCollection rootDirs = buildWsdlTask.getRootDirs();

		if ((rootDirs != null) && !rootDirs.isEmpty()) {
			return;
		}

		Project project = buildWsdlTask.getProject();

		File rootDir = new File(getWebAppDir(project), "WEB-INF/wsdl");

		buildWsdlTask.rootDirs(rootDir);
	}

	protected void configureTaskDeploy(
		Project project, LiferayExtension liferayExtension) {

		Copy copy = (Copy)GradleUtil.getTask(project, DEPLOY_TASK_NAME);

		War war = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		copy.from(war.getArchivePath());
		copy.into(liferayExtension.getDeployDir());
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

		configureTaskDeploy(project, liferayExtension);
		configureTaskDirectDeploy(project, liferayExtension);
		configureTaskWar(project, liferayExtension);
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