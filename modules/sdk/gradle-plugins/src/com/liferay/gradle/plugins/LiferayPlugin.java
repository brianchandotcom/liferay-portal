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
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedModuleVersion;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
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

	protected void configureClean() {
		Task cleanTask = getTask(CLEAN_TASK_NAME);

		// Depends on

		for (Task task : project.getTasks()) {
			String taskName =
				CLEAN_TASK_NAME + StringUtil.capitalize(task.getName());

			cleanTask.dependsOn(taskName);
		}

		Configuration compileConfiguration = getConfiguration(
			JavaPlugin.COMPILE_CONFIGURATION_NAME);

		Set<Dependency> compileDependencies =
			compileConfiguration.getAllDependencies();

		for (Dependency dependency : compileDependencies) {
			if (dependency instanceof ProjectDependency) {
				ProjectDependency projectDependency =
					(ProjectDependency)dependency;

				Project dependencyProject =
					projectDependency.getDependencyProject();

				String taskName =
					dependencyProject.getPath() + Project.PATH_SEPARATOR +
						CLEAN_TASK_NAME;

				cleanTask.dependsOn(taskName);
			}
		}
	}

	protected void configureDependencies() {
		DependencyHandler dependencyHandler = project.getDependencies();

		// Compile

		File serviceJarFile = project.file(
			"docroot/WEB-INF/lib/" + project.getName() + "-service.jar");

		if (serviceJarFile.exists()) {
			dependencyHandler.add(
				JavaPlugin.COMPILE_CONFIGURATION_NAME,
				project.files(serviceJarFile));
		}

		// Provided compile

		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"biz.aQute.bnd:biz.aQute.bnd:2.4.1");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"com.liferay.portal:portal-service:7.0.0-SNAPSHOT");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"hsqldb:hsqldb:1.8.0.7");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"javax.activation:activation:1.1");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"javax.ccpp:ccpp:1.0");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME, "javax.jms:jms:1.1");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"javax.mail:mail:1.4");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"javax.portlet:portlet-api:2.0");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"javax.servlet.jsp:jsp-api:2.1");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"javax.servlet:javax.servlet-api:3.0.1");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"mysql:mysql-connector-java:5.1.23");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME, "net.sf:jargs:1.0");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"net.sourceforge.jtds:jtds:1.2.6");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"org.eclipse.persistence:javax.persistence:2.0.0");
		dependencyHandler.add(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"postgresql:postgresql:9.2-1002.jdbc4");

		String pluginType = _liferayExtension.getPluginType();

		if (!pluginType.equals("theme")) {
			dependencyHandler.add(
				WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
				"com.liferay.portal:util-bridges:7.0.0-SNAPSHOT");
			dependencyHandler.add(
				WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
				"com.liferay.portal:util-java:7.0.0-SNAPSHOT");
			dependencyHandler.add(
				WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
				"com.liferay.portal:util-taglib:7.0.0-SNAPSHOT");
			dependencyHandler.add(
				WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
				"commons-logging:commons-logging:1.1.1");
			dependencyHandler.add(
				WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
				"log4j:log4j:1.2.16");
		}
	}

	protected void configureRepositories() {
		RepositoryHandler repositoryHandler = project.getRepositories();

		repositoryHandler.maven(
			new Action<MavenArtifactRepository>() {

				@Override
				public void execute(
					MavenArtifactRepository mavenArtifactRepository) {

					mavenArtifactRepository.setUrl(REPOSITORY_URL);
				}

			});
	}

	protected void configureSourceSets() {
		SourceSet sourceSet = getSourceSet(SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet javaDirectorySet = sourceSet.getJava();

		Set<File> srcDirs = Collections.singleton(
			_liferayExtension.getPluginSrcDir());

		javaDirectorySet.setSrcDirs(srcDirs);

		SourceDirectorySet resourcesDirectorySet = sourceSet.getResources();

		resourcesDirectorySet.setSrcDirs(srcDirs);
	}

	protected void configureTasks() {
		configureClean();
		configureWar();
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

				version = PORTAL_VERSION + "." + moduleIncrementalVersion;
			}
		}

		project.setVersion(version);
	}

	protected void configureWar() {
		War warTask = (War)getTask(WarPlugin.WAR_TASK_NAME);

		CopySpecInternal rootSpec = warTask.getRootSpec();

		// Duplicates strategy

		warTask.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);

		// Exclude

		warTask.exclude("WEB-INF/lib");

		// Files matching

		final Closure<String> filterLiferayHookXmlClosure =
			new Closure<String>(null) {

			@SuppressWarnings("unused")
			public String doCall(String line) {
				if (line.contains("content/Language*.properties")) {
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

				return line;
			}

		};

		warTask.filesMatching(
			"WEB-INF/liferay-hook.xml",
			new Action<FileCopyDetails>() {

				@Override
				public void execute(FileCopyDetails fileCopyDetails) {
					fileCopyDetails.filter(filterLiferayHookXmlClosure);
				}

			});

		// Manifest

		File manifestFile;

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
			for (CopySpecInternal childSpec : rootSpec.getChildren()) {
				CopySpecResolver copySpecResolver =
					childSpec.buildRootResolver();

				RelativePath destPath = copySpecResolver.getDestPath();

				if ("META-INF".equals(destPath.getPathString())) {
					childSpec.exclude("**");
				}
			}
		}

		// Outputs

		TaskOutputs taskOutputs = warTask.getOutputs();

		taskOutputs.file(warTask.getArchivePath());

		// Rename dependencies

		Closure<String> renameDependencyClosure = new Closure<String>(null) {

			@SuppressWarnings("unused")
			public String doCall(String name) {
				Map<String, String> newDependencyNames =
					_getNewDependencyNames();

				String newName = newDependencyNames.get(name);

				if (Validator.isNotNull(newName)) {
					return newName;
				}

				return name;
			}

		};

		for (CopySpecInternal childSpec : rootSpec.getChildren()) {
			childSpec.rename(renameDependencyClosure);
		}
	}

	protected void configureWebAppDirName() {
		WarPluginConvention warPluginConvention = getPluginConvention(
			WarPluginConvention.class);

		warPluginConvention.setWebAppDirName("docroot");
	}

	@Override
	protected void doApply() throws Exception {
		applyPlugin(WarPlugin.class);

		_liferayExtension = createExtension("liferay", LiferayExtension.class);

		configureDependencies();
		configureRepositories();
		configureSourceSets();
		configureVersion();
		configureWebAppDirName();

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTasks();
				};

			});
	}

	private Map<String, String> _getNewDependencyNames() {
		if (_newDependencyNames == null) {
			_newDependencyNames = new HashMap<>();

			Configuration compileConfiguration = getConfiguration(
				JavaPlugin.COMPILE_CONFIGURATION_NAME);

			ResolvedConfiguration resolvedConfiguration =
				compileConfiguration.getResolvedConfiguration();

			for (ResolvedArtifact resolvedArtifact :
					resolvedConfiguration.getResolvedArtifacts()) {

				ResolvedModuleVersion moduleVersion =
					resolvedArtifact.getModuleVersion();

				ModuleVersionIdentifier moduleVersionIdentifier =
					moduleVersion.getId();

				String oldName =
					moduleVersionIdentifier.getName() + "-" +
						moduleVersionIdentifier.getVersion() + ".jar";
				String newName = moduleVersionIdentifier.getName() + ".jar";

				_newDependencyNames.put(oldName, newName);
			}
		}

		return _newDependencyNames;
	}

	private LiferayExtension _liferayExtension;
	private Map<String, String> _newDependencyNames;

}