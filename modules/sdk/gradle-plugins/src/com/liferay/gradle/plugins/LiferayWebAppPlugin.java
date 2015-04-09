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
import com.liferay.gradle.plugins.tasks.BuildCss;
import com.liferay.gradle.plugins.tasks.DirectDeploy;
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
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.War;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayWebAppPlugin extends LiferayJavaPlugin {

	@Override
	public void apply(Project project) {
		super.apply(project);

		configureWebAppDirName(project);
	}

	protected DirectDeploy addTaskDirectDeploy(Project project) {
		DirectDeploy directDeploy = GradleUtil.addTask(
			project, DIRECT_DEPLOY_TASK_NAME, DirectDeploy.class);

		directDeploy.dependsOn(WarPlugin.WAR_TASK_NAME);

		directDeploy.setDescription(
			"Processes a WAR file as if it was picked up by the Liferay auto " +
				"deploy directory");
		directDeploy.setGroup(WarPlugin.WEB_APP_GROUP);

		return directDeploy;
	}

	@Override
	protected void addTasks(
		Project project, LiferayExtension liferayExtension) {

		super.addTasks(project, liferayExtension);

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
	protected void configureTaskBuildCssRootDirs(BuildCss buildCss) {
		FileCollection rootDirs = buildCss.getRootDirs();

		if ((rootDirs != null) && !rootDirs.isEmpty()) {
			return;
		}

		Project project = buildCss.getProject();

		buildCss.setRootDirs(getWebAppDir(project));
	}

	protected void configureTaskDirectDeploy(
		Project project, LiferayExtension liferayExtension) {

		DirectDeploy directDeploy = (DirectDeploy)GradleUtil.getTask(
			project, DIRECT_DEPLOY_TASK_NAME);

		configureTaskDirectDeployAppServerDeployDir(
			directDeploy, liferayExtension);
		configureTaskDirectDeployAppServerLibGlobalDir(
			directDeploy, liferayExtension);
		configureTaskDirectDeployAppServerPortalDir(
			directDeploy, liferayExtension);
		configureTaskDirectDeployAppServerType(directDeploy, liferayExtension);
		configureTaskDirectDeployWebAppFile(directDeploy);
		configureTaskDirectDeployWebAppType(directDeploy);
	}

	protected void configureTaskDirectDeployAppServerDeployDir(
		DirectDeploy directDeploy, LiferayExtension liferayExtension) {

		if (directDeploy.getAppServerDeployDir() == null) {
			directDeploy.setAppServerDeployDir(
				liferayExtension.getAppServerDeployDir());
		}
	}

	protected void configureTaskDirectDeployAppServerLibGlobalDir(
		DirectDeploy directDeploy, LiferayExtension liferayExtension) {

		if (directDeploy.getAppServerLibGlobalDir() == null) {
			directDeploy.setAppServerLibGlobalDir(
				liferayExtension.getAppServerLibGlobalDir());
		}
	}

	protected void configureTaskDirectDeployAppServerPortalDir(
		DirectDeploy directDeploy, LiferayExtension liferayExtension) {

		if (directDeploy.getAppServerPortalDir() == null) {
			directDeploy.setAppServerPortalDir(
				liferayExtension.getAppServerPortalDir());
		}
	}

	protected void configureTaskDirectDeployAppServerType(
		DirectDeploy directDeploy, LiferayExtension liferayExtension) {

		if (Validator.isNull(directDeploy.getAppServerType())) {
			directDeploy.setAppServerType(liferayExtension.getAppServerType());
		}
	}

	protected void configureTaskDirectDeployWebAppFile(
		DirectDeploy directDeploy) {

		if (directDeploy.getWebAppFile() != null) {
			return;
		}

		War war = (War)GradleUtil.getTask(
			directDeploy.getProject(), WarPlugin.WAR_TASK_NAME);

		directDeploy.setWebAppFile(war.getArchivePath());
	}

	protected void configureTaskDirectDeployWebAppType(
		DirectDeploy directDeploy) {

		if (Validator.isNull(directDeploy.getWebAppType())) {
			directDeploy.setWebAppType(
				getWebAppType(directDeploy.getProject()));
		}
	}

	@Override
	protected void configureTasks(
		Project project, LiferayExtension liferayExtension) {

		super.configureTasks(project, liferayExtension);

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

	protected static final String DIRECT_DEPLOY_TASK_NAME = "directDeploy";

}