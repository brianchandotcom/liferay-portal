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

package com.liferay.gradle.plugins.workspace.internal.client.extension;

import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.css.builder.BuildCSSTask;
import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.extensions.BundleExtension;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.theme.builder.BuildThemeTask;
import com.liferay.gradle.plugins.theme.builder.ThemeBuilderPlugin;
import com.liferay.gradle.plugins.util.BndUtil;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.configurators.ClientExtensionProjectConfigurator;
import com.liferay.gradle.plugins.workspace.configurators.RootProjectConfigurator;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.internal.util.StringUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.petra.string.StringBundler;

import groovy.json.JsonSlurper;

import groovy.lang.Closure;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFile;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.War;
import org.gradle.jvm.tasks.Jar;

/**
 * @author Gregory Amerson
 */
public class ThemeCSSConfigurer implements ClientExtensionConfigurer {

	public static final String BUILD_DESIGN_PACK_TASK_NAME = "buildDesignPack";

	public static final String BUILD_FAVICON_TASK_NAME = "buildFavicon";

	@Override
	public void apply(Project project, ClientExtension clientExtension) {
		if (_defaultRepositoryEnabled) {
			GradleUtil.addDefaultRepositories(project);
		}

		GradleUtil.applyPlugin(project, NodePlugin.class);
		GradleUtil.applyPlugin(project, ThemeBuilderPlugin.class);

		_addDependenciesParentThemes(project);
		_addDependenciesPortalCommonCSS(project);

		Map<String, Object> packageJsonMap = _getPackageJsonMap(project);

		_configureVersion(project, packageJsonMap);

		_configureConfigurationDefault(project);

		_configureTaskBuildTheme(project);

		BuildCSSTask buildCSSTask = _configureTaskBuildCSS(project);

		War war = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		war.setEnabled(false);

		TaskProvider<Jar> jarTaskProvider = GradleUtil.getTaskProvider(
			project, JavaPlugin.JAR_TASK_NAME, Jar.class);

		jarTaskProvider.configure(
			new Action<Jar>() {

				@Override
				public void execute(Jar jar) {
					jar.dependsOn(buildCSSTask);

					DirectoryProperty destinationDirectoryProperty =
						jar.getDestinationDirectory();

					destinationDirectoryProperty.set(
						new File(project.getProjectDir(), "dist"));

					Property<String> archiveExtensionProperty =
						jar.getArchiveExtension();

					archiveExtensionProperty.set("zip");

					_configureArtifacts(project, jar);
					_configureTaskDeploy(project, jar);
					_configureRootTaskDistBundle(project, jar);
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					BundleExtension bundleExtension =
						BndUtil.getBundleExtension(project.getExtensions());

					_defaultClientExtensionBundleInstructions(bundleExtension);

					try {
						_themeCSSBundleInstructions(
							project, bundleExtension, clientExtension);
					}
					catch (Exception exception) {
						throw new GradleException(
							"Failed to configure bundle instructions",
							exception);
					}
				}

			});
	}

	private void _addDependenciesParentThemes(Project project) {
		GradleUtil.addDependency(
			project, ThemeBuilderPlugin.PARENT_THEMES_CONFIGURATION_NAME,
			"com.liferay", "com.liferay.frontend.theme.styled",
			"latest.release");
		GradleUtil.addDependency(
			project, ThemeBuilderPlugin.PARENT_THEMES_CONFIGURATION_NAME,
			"com.liferay", "com.liferay.frontend.theme.unstyled",
			"latest.release");
		GradleUtil.addDependency(
			project, ThemeBuilderPlugin.PARENT_THEMES_CONFIGURATION_NAME,
			"com.liferay.plugins", "classic-theme", "latest.release");
	}

	private void _addDependenciesPortalCommonCSS(Project project) {
		GradleUtil.addDependency(
			project, CSSBuilderPlugin.PORTAL_COMMON_CSS_CONFIGURATION_NAME,
			"com.liferay", "com.liferay.frontend.css.common", "latest.release",
			false);
	}

	private void _configureArtifacts(Project project, Jar jar) {
		ArtifactHandler artifacts = project.getArtifacts();

		artifacts.add(Dependency.ARCHIVES_CONFIGURATION, jar);
	}

	private void _configureConfigurationDefault(Project project) {
		Configuration defaultConfiguration = GradleUtil.getConfiguration(
			project, Dependency.DEFAULT_CONFIGURATION);

		Configuration archivesConfiguration = GradleUtil.getConfiguration(
			project, Dependency.ARCHIVES_CONFIGURATION);

		defaultConfiguration.extendsFrom(archivesConfiguration);
	}

	@SuppressWarnings({"serial", "unused"})
	private void _configureRootTaskDistBundle(Project project, Jar jar) {
		Task assembleTask = GradleUtil.getTask(
			project, BasePlugin.ASSEMBLE_TASK_NAME);

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		assembleTask.dependsOn(jar);

		copy.dependsOn(assembleTask);

		copy.into(
			"deploy",
			new Closure<Void>(project) {

				public void doCall(CopySpec copySpec) {
					Project project = assembleTask.getProject();

					Provider<RegularFile> fileProvider = jar.getArchiveFile();

					ConfigurableFileCollection configurableFileCollection =
						project.files(fileProvider);

					configurableFileCollection.builtBy(assembleTask);

					copySpec.from(fileProvider);
				}

			});
	}

	private BuildCSSTask _configureTaskBuildCSS(Project project) {
		BuildCSSTask buildCSSTask = (BuildCSSTask)GradleUtil.getTask(
			project, CSSBuilderPlugin.BUILD_CSS_TASK_NAME);

		buildCSSTask.setOutputDirName(".");

		return buildCSSTask;
	}

	@SuppressWarnings("unchecked")
	private BuildThemeTask _configureTaskBuildTheme(Project project) {
		BuildThemeTask buildThemeTask = (BuildThemeTask)GradleUtil.getTask(
			project, ThemeBuilderPlugin.BUILD_THEME_TASK_NAME);

		buildThemeTask.setDiffsDir(project.file("src"));

		File packageJsonFile = project.file("package.json");

		if (!packageJsonFile.exists()) {
			return buildThemeTask;
		}

		Map<String, Object> packageJsonMap = _getPackageJsonMap(
			packageJsonFile);

		Map<String, String> liferayDesignPackMap =
			(Map<String, String>)packageJsonMap.get("liferayDesignPack");

		String baseTheme = liferayDesignPackMap.get("baseTheme");

		if (baseTheme.equals("styled") || baseTheme.equals("unstyled")) {
			baseTheme = "_" + baseTheme;
		}

		buildThemeTask.setParentName(baseTheme);

		Map<String, String> allDependencyMap = new HashMap<>();

		Map<String, String> dependenciesMap =
			(Map<String, String>)packageJsonMap.get("dependencies");

		Map<String, String> devDependenciesMap =
			(Map<String, String>)packageJsonMap.get("devDependencies");

		if (Objects.nonNull(dependenciesMap)) {
			allDependencyMap.putAll(dependenciesMap);
		}

		if (Objects.nonNull(devDependenciesMap)) {
			allDependencyMap.putAll(devDependenciesMap);
		}

		if (!allDependencyMap.isEmpty()) {
			buildThemeTask.dependsOn(NodePlugin.NPM_INSTALL_TASK_NAME);

			buildThemeTask.doFirst(
				new Action<Task>() {

					@Override
					public void execute(Task task) {
						WorkspaceExtension workspaceExtension =
							GradleUtil.getExtension(
								(ExtensionAware)project.getGradle(),
								WorkspaceExtension.class);

						String nodePackageManager =
							workspaceExtension.getNodePackageManager();

						File nodeMoudleDir;

						if (Objects.equals(nodePackageManager, "yarn")) {
							Project rootProject = project.getRootProject();

							nodeMoudleDir = rootProject.file("node_modules");
						}
						else {
							nodeMoudleDir = project.file("node_modules");
						}

						for (String key : allDependencyMap.keySet()) {
							final File dependencyDir = new File(
								nodeMoudleDir, key);

							if (!dependencyDir.exists()) {
								continue;
							}

							project.copy(
								new Action<CopySpec>() {

									@Override
									public void execute(CopySpec copySpec) {
										copySpec.from(dependencyDir);

										copySpec.into(
											buildThemeTask.getOutputDir() +
												"/css/" + key);
										copySpec.setIncludeEmptyDirs(false);
									}

								});
						}
					}

				});
		}

		return buildThemeTask;
	}

	private void _configureTaskDeploy(Project project, Jar jar) {
		Copy copy = (Copy)GradleUtil.getTask(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME);

		copy.dependsOn(BasePlugin.ASSEMBLE_TASK_NAME);
		copy.from(jar);
	}

	private void _configureVersion(
		Project project, Map<String, Object> packageJsonMap) {

		String version = (String)packageJsonMap.get("version");

		if (Validator.isNotNull(version)) {
			project.setVersion(version);
		}
	}

	private void _defaultClientExtensionBundleInstructions(
		BundleExtension bundleExtension) {

		bundleExtension.instruction("Bundle-SymbolicName", "${project.name}");
		bundleExtension.instruction("Bundle-Version", "${project.version}");
		bundleExtension.instruction(
			"Require-Capability", _OSGI_EXTENDER_CAPABILITY);
		bundleExtension.instruction("Web-ContextPath", "/${project.name}");
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> _getPackageJsonMap(File packageJsonFile) {
		if (!packageJsonFile.exists()) {
			return Collections.emptyMap();
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		return (Map<String, Object>)jsonSlurper.parse(packageJsonFile);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> _getPackageJsonMap(Project project) {
		File file = project.file("package.json");

		if (!file.exists()) {
			return Collections.emptyMap();
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		return (Map<String, Object>)jsonSlurper.parse(file);
	}

	private void _themeCSSBundleInstructions(
			Project project, BundleExtension bundleExtension,
			ClientExtension clientExtension)
		throws Exception {

		bundleExtension.instruction(
			clientExtension.id + "OsgiConfigJsonValue",
			clientExtension.toJSON());

		String key = StringBundler.concat(
			"-includeresource.", clientExtension.id, ".osgi.config.json");
		String value = StringBundler.concat(
			"OSGI-INF/configurator/", clientExtension.id,
			".osgi.config.json;literal='${", clientExtension.id,
			"OsgiConfigJsonValue}'");

		bundleExtension.instruction(key, value);

		bundleExtension.instruction(
			"-includeresource." + clientExtension.id,
			"META-INF/resources/=build/buildTheme/css;filter:=*.css;recursive:=false");

		File lcpJsonFile = project.file("LCP.json");

		if (lcpJsonFile.exists()) {
			bundleExtension.instruction(
				"-includeresource.lcp.json", "LCP.json");
		}
		else {
			String lcpJsonValue = StringUtil.read(
				ClientExtensionProjectConfigurator.class.getResourceAsStream(
					"LcpJson_template"));

			bundleExtension.instruction(
				"-includeresource.lcp.json",
				"LCP.json;literal='${lcpJsonValue}'");
			bundleExtension.instruction("lcpJsonValue", lcpJsonValue);
		}

		File dockerfile = project.file("Dockerfile");

		if (dockerfile.exists()) {
			bundleExtension.instruction(
				"-includeresource.dockerfile", "Dockerfile");
		}
		else {
			String dockerfileValue = StringUtil.read(
				ClientExtensionProjectConfigurator.class.getResourceAsStream(
					"Dockerfile_template"));

			bundleExtension.instruction(
				"-includeresource.dockerfile",
				"Dockerfile;literal='${dockerfileValue}'");
			bundleExtension.instruction("dockerfileValue", dockerfileValue);
		}

		bundleExtension.instruction(
			"-fixupmessages." + clientExtension.id,
			"No translation found for macro");
	}

	private static final String _OSGI_EXTENDER_CAPABILITY =
		"osgi.extender;filter:=\"(&(osgi.extender=osgi.configurator)" +
			"(version>=1.0)(!(version>=2.0)))\"";

	private static final boolean _defaultRepositoryEnabled = true;

}