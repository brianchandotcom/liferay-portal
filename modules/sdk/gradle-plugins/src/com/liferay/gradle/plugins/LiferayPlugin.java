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
import com.liferay.gradle.plugins.extensions.LiferayThemeExtension;
import com.liferay.gradle.plugins.tasks.BuildCssTask;
import com.liferay.gradle.plugins.tasks.FormatSourceTask;
import com.liferay.gradle.plugins.tasks.InitGradleTask;
import com.liferay.gradle.plugins.util.StringUtil;
import com.liferay.gradle.plugins.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencyResolveDetails;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.ModuleVersionSelector;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedModuleVersion;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.file.RelativePath;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.file.copy.CopySpecInternal;
import org.gradle.api.internal.file.copy.CopySpecResolver;
import org.gradle.api.java.archives.Manifest;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.War;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayPlugin extends BasePlugin {

	public static final String EXTENSION_NAME = "liferay";

	protected void addLiferayExtension() {
		String projectName = project.getName();

		if (projectName.endsWith("-theme")) {
			_liferayExtension = addExtension(
				EXTENSION_NAME, LiferayThemeExtension.class);
		}
		else {
			_liferayExtension = addExtension(
				EXTENSION_NAME, LiferayExtension.class);
		}
	}

	protected void addTaskBuildCss() {
		Task task = addTask("buildCss", BuildCssTask.class);

		task.setDescription("Compiles CSS files.");
		task.setGroup(org.gradle.api.plugins.BasePlugin.BUILD_GROUP);
	}

	protected void addTaskFormatSource() {
		Task task = addTask("formatSource", FormatSourceTask.class);

		task.setDescription("Runs Liferay Source Formatter to format files.");
	}

	protected void addTaskInitGradle() {
		Task task = addTask("initGradle", InitGradleTask.class);

		task.setDescription(
			"Initializes build.gradle by migrating information from legacy " +
				"files.");
	}

	protected void addTasks() {
		addTaskBuildCss();
		addTaskFormatSource();
		addTaskInitGradle();
	}

	protected void configureConfigurations() {
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

									dependencyResolveDetails.useVersion(
										_liferayExtension.getPortalVersion());
								}
						}

					});
			}

		};

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		configurationContainer.all(action);
	}

	protected void configureDependencies() {
		configureDependenciesCompile();
		configureDependenciesProvidedCompile();
	}

	protected void configureDependenciesCompile() {
		File serviceJarFile = project.file(
			"docroot/WEB-INF/lib/" + project.getName() + "-service.jar");

		if (serviceJarFile.exists()) {
			DependencyHandler dependencyHandler = project.getDependencies();

			dependencyHandler.add(
				JavaPlugin.COMPILE_CONFIGURATION_NAME,
				project.files(serviceJarFile));
		}
	}

	protected void configureDependenciesProvidedCompile() {
		addDependencies(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"biz.aQute.bnd:biz.aQute.bnd:2.4.1",
			"com.liferay.portal:portal-service:default",
			"hsqldb:hsqldb:1.8.0.7", "javax.activation:activation:1.1",
			"javax.ccpp:ccpp:1.0", "javax.jms:jms:1.1", "javax.mail:mail:1.4",
			"javax.portlet:portlet-api:2.0", "javax.servlet.jsp:jsp-api:2.1",
			"javax.servlet:javax.servlet-api:3.0.1",
			"mysql:mysql-connector-java:5.1.23", "net.sf:jargs:1.0",
			"net.sourceforge.jtds:jtds:1.2.6",
			"org.eclipse.persistence:javax.persistence:2.0.0",
			"postgresql:postgresql:9.2-1002.jdbc4");

		String pluginType = _liferayExtension.getPluginType();

		if (!pluginType.equals("theme")) {
			addDependencies(
				WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
				"com.liferay.portal:util-bridges:default",
				"com.liferay.portal:util-java:default",
				"com.liferay.portal:util-taglib:default",
				"commons-logging:commons-logging:1.1.1", "log4j:log4j:1.2.16");
		}
	}

	protected void configureSourceSets() {
		SourceSet sourceSet = getSourceSet(SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet javaSourceDirectorySet = sourceSet.getJava();

		Set<File> srcDirs = Collections.singleton(
			_liferayExtension.getPluginSrcDir());

		javaSourceDirectorySet.setSrcDirs(srcDirs);

		SourceDirectorySet resourcesSourceDirectorySet =
			sourceSet.getResources();

		resourcesSourceDirectorySet.setSrcDirs(srcDirs);
	}

	protected void configureTaskClean() {
		Task cleanTask = getTask(
			org.gradle.api.plugins.BasePlugin.CLEAN_TASK_NAME);

		configureTaskCleanDependsOn(cleanTask);
	}

	protected void configureTaskCleanDependsOn(Task cleanTask) {
		for (Task task : project.getTasks()) {
			String taskName =
				org.gradle.api.plugins.BasePlugin.CLEAN_TASK_NAME +
					StringUtil.capitalize(task.getName());

			cleanTask.dependsOn(taskName);
		}

		Configuration compileConfiguration = getConfiguration(
			JavaPlugin.COMPILE_CONFIGURATION_NAME);

		Set<Dependency> compileDependencies =
			compileConfiguration.getAllDependencies();

		for (Dependency dependency : compileDependencies) {
			if (!(dependency instanceof ProjectDependency)) {
				continue;
			}

			ProjectDependency projectDependency = (ProjectDependency)dependency;

			Project dependencyProject =
				projectDependency.getDependencyProject();

			String taskName =
				dependencyProject.getPath() + Project.PATH_SEPARATOR +
					org.gradle.api.plugins.BasePlugin.CLEAN_TASK_NAME;

			cleanTask.dependsOn(taskName);
		}
	}

	protected void configureTasks() {
		configureTaskClean();
		configureTaskWar();
	}

	protected void configureTaskWar() {
		War warTask = (War)getTask(WarPlugin.WAR_TASK_NAME);

		configureTaskWarDuplicatesStrategy(warTask);
		configureTaskWarExclude(warTask);
		configureTaskWarFilesMatching(warTask);
		configureTaskWarOutputs(warTask);
		configureTaskWarRenameDependencies(warTask);
	}

	protected void configureTaskWarDuplicatesStrategy(War warTask) {
		warTask.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);
	}

	protected void configureTaskWarExclude(War warTask) {
		warTask.exclude("WEB-INF/lib");
	}

	protected void configureTaskWarFilesMatching(War warTask) {
		final Closure<String> closure = new Closure<String>(null) {

			@SuppressWarnings("unused")
			public String doCall(String line) {
				if (!line.contains("content/Language*.properties")) {
					return line;
				}

				StringBuilder sb = new StringBuilder();

				File contentDir = new File(
					_liferayExtension.getPluginSrcDir(), "content");

				File[] files = contentDir.listFiles();

				for (int i = 0; i < files.length; i++) {
					File file = files[i];

					sb.append("\t<language-properties>content/");
					sb.append(file.getName());
					sb.append("</language-properties>");

					if ((i + 1) < files.length) {
						sb.append("\n");
					}
				}

				return sb.toString();
			}

		};

		warTask.filesMatching(
			"WEB-INF/liferay-hook.xml",
			new Action<FileCopyDetails>() {

				@Override
				public void execute(FileCopyDetails fileCopyDetails) {
					fileCopyDetails.filter(closure);
				}

			});
	}

	protected void configureTaskWarManifest(War warTask) {
		File manifestFile = null;

		if (_liferayExtension.isOsgiPlugin()) {
			manifestFile = project.file("src/META-INF/MANIFEST.MF");
		}
		else {
			manifestFile = project.file("docroot/META-INF/MANIFEST.MF");
		}

		Manifest manifest = warTask.getManifest();

		if (manifestFile.exists()) {
			manifest.from(manifestFile);
		}
		else {
			CopySpecInternal copySpecInternal = warTask.getRootSpec();

			for (CopySpecInternal childCopySpecInternal :
					copySpecInternal.getChildren()) {

				CopySpecResolver copySpecResolver =
					childCopySpecInternal.buildRootResolver();

				RelativePath destRelativePath = copySpecResolver.getDestPath();

				String destRelativePathString =
					destRelativePath.getPathString();

				if (destRelativePathString.equals("META-INF")) {
					childCopySpecInternal.exclude("**");
				}
			}
		}
	}

	protected void configureTaskWarOutputs(War warTask) {
		TaskOutputs taskOutputs = warTask.getOutputs();

		taskOutputs.file(warTask.getArchivePath());
	}

	protected void configureTaskWarRenameDependencies(War warTask) {
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

				Configuration compileConfiguration = getConfiguration(
					JavaPlugin.COMPILE_CONFIGURATION_NAME);

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

		CopySpecInternal copySpecInternal = warTask.getRootSpec();

		for (CopySpecInternal childCopySpecInternal :
				copySpecInternal.getChildren()) {

			childCopySpecInternal.rename(closure);
		}
	}

	protected void configureVersion() {
		String version = null;

		String moduleFullVersion = _liferayExtension.getPluginPackageProperty(
			"module-full-version");

		if (Validator.isNotNull(moduleFullVersion)) {
			version = moduleFullVersion;
		}
		else {
			String bundleVersion = _liferayExtension.getBndProperty(
				"Bundle-Version");

			if (Validator.isNotNull(bundleVersion)) {
				version = bundleVersion;
			}
			else {
				String moduleIncrementalVersion =
					_liferayExtension.getPluginPackageProperty(
						"module-incremental-version");

				String portalVersion = _liferayExtension.getPortalVersion();

				int index = portalVersion.indexOf("-");

				if (index != -1) {
					portalVersion = portalVersion.substring(0, index);
				}

				version = portalVersion + "." + moduleIncrementalVersion;
			}
		}

		project.setVersion(version);
	}

	protected void configureWebAppDirName() {
		WarPluginConvention warPluginConvention = getPluginConvention(
			WarPluginConvention.class);

		warPluginConvention.setWebAppDirName("docroot");
	}

	@Override
	protected void doApply() throws Exception {
		applyPlugin(WarPlugin.class);

		addLiferayExtension();

		configureConfigurations();
		configureDependencies();
		configureRepositories();
		configureSourceSets();
		configureVersion();
		configureWebAppDirName();

		addTasks();

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTasks();
				};

			});
	}

	private LiferayExtension _liferayExtension;

}