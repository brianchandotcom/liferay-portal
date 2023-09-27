/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.configurator;

import com.liferay.gradle.plugins.workspace.ProjectConfigurator;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.FileUtil;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.task.UpdatePropertiesTask;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import groovy.lang.Closure;

import java.io.File;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.AntBuilder;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileTree;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.gradle.util.CollectionUtils;

/**
 * @author Andrea Di Giorgi
 * @author David Truong
 */
public class PluginsProjectConfigurator extends BaseProjectConfigurator {

	public static final String PLUGINS_SDK_CONFIGURATION_NAME = "pluginsSDK";

	public static final String UPDATE_PROPERTIES_TASK_NAME = "updateProperties";

	public static final String UPGRADE_PLUGINS_SDK_TASK_NAME =
		"upgradePluginsSDK";

	public PluginsProjectConfigurator(Settings settings) {
		super(settings);

		String defaultRootDirNames = GradleUtil.getProperty(
			settings, getDefaultRootDirPropertyName(), (String)null);

		if (Validator.isNotNull(defaultRootDirNames)) {
			_defaultRootDirs = new HashSet<>();

			for (String dirName : defaultRootDirNames.split("\\s*,\\s*")) {
				_defaultRootDirs.add(new File(settings.getRootDir(), dirName));
			}
		}
		else {
			File dir = new File(settings.getRootDir(), getDefaultRootDirName());

			_defaultRootDirs = Collections.singleton(dir);
		}
	}

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		Task initBundleTask = GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.INIT_BUNDLE_TASK_NAME);

		_configureAnt(project);

		UpdatePropertiesTask updatePropertiesTask = _addTaskUpdateProperties(
			project, workspaceExtension);

		_addTaskBuild(project, updatePropertiesTask);

		Task warTask = GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		_configureTaskWar(warTask, workspaceExtension, initBundleTask);

		_configureRootTaskDistBundle(warTask);
	}

	@Override
	public void configureRootProject(
		Project project, WorkspaceExtension workspaceExtension) {

		Configuration pluginsSDKConfiguration = _addRootConfigurationPluginsSDK(
			project);

		_addRootTaskUpgradePluginsSDK(
			project, pluginsSDKConfiguration, workspaceExtension);
	}

	@Override
	public Iterable<File> getDefaultRootDirs() {
		return _defaultRootDirs;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected Iterable<File> doGetProjectDirs(File rootDir) throws Exception {
		File buildXmlFile = new File(rootDir, "build.xml");

		if (!buildXmlFile.exists()) {
			return Collections.emptySet();
		}

		return Collections.singleton(rootDir);
	}

	@Override
	protected String getDefaultRootDirName() {
		return _DEFAULT_ROOT_DIR_NAME;
	}

	@Override
	protected String getDefaultRootDirPropertyName() {
		return _DEFAULT_ROOT_DIR_PROPERTY_NAME;
	}

	protected static final String NAME = "plugins";

	private Configuration _addRootConfigurationPluginsSDK(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, PLUGINS_SDK_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addRootDependenciesPluginsSDK(project);
				}

			});

		configuration.setDescription(
			"Configures the Plugins SDK for this Liferay Workspace.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addRootDependenciesPluginsSDK(Project project) {
		DependencyHandler dependencyHandler = project.getDependencies();

		Map<String, Object> dependencyNotation = new HashMap<>();

		dependencyNotation.put("classifier", "withdependencies");
		dependencyNotation.put("ext", "zip");
		dependencyNotation.put("group", "com.liferay.portal");
		dependencyNotation.put("name", "com.liferay.portal.plugins.sdk");
		dependencyNotation.put("version", "latest.release");

		dependencyHandler.add(
			PLUGINS_SDK_CONFIGURATION_NAME, dependencyNotation);
	}

	private Task _addRootTaskUpgradePluginsSDK(
		Project project, Configuration pluginsSDKConfiguration,
		WorkspaceExtension workspaceExtension) {

		ProjectConfigurator projectConfigurator =
			workspaceExtension.propertyMissing(PluginsProjectConfigurator.NAME);

		Collection<File> dirs =
			(Collection<File>)projectConfigurator.getDefaultRootDirs();

		if (dirs.size() == 1) {
			return _addRootTaskUpgradePluginsSDK(
				project, UPGRADE_PLUGINS_SDK_TASK_NAME,
				CollectionUtils.first(dirs), pluginsSDKConfiguration);
		}

		Task task = project.task(UPGRADE_PLUGINS_SDK_TASK_NAME);

		for (File dir : projectConfigurator.getDefaultRootDirs()) {
			String taskName =
				UPGRADE_PLUGINS_SDK_TASK_NAME +
					StringUtil.capitalize(dir.getName());

			Copy copy = _addRootTaskUpgradePluginsSDK(
				project, taskName, dir, pluginsSDKConfiguration);

			task.dependsOn(copy);
		}

		task.setDescription(
			"Downloads and upgrades all Plugins SDK directories.");

		return task;
	}

	@SuppressWarnings("serial")
	private Copy _addRootTaskUpgradePluginsSDK(
		final Project project, String taskName, File dir,
		final Configuration pluginsSDKConfiguration) {

		Copy copy = GradleUtil.addTask(project, taskName, Copy.class);

		copy.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() throws Exception {
					return project.zipTree(
						pluginsSDKConfiguration.getSingleFile());
				}

			},
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.eachFile(new StripPathSegmentsAction(1));
				}

			});

		copy.into(dir);
		copy.setDescription(
			"Downloads the Liferay Plugins SDK zip file into " +
				project.relativePath(dir) + ", upgrading if necessary.");
		copy.setIncludeEmptyDirs(false);

		return copy;
	}

	private Task _addTaskBuild(
		Project project, UpdatePropertiesTask updatePropertiesTask) {

		Task task = project.task(LifecycleBasePlugin.BUILD_TASK_NAME);

		task.dependsOn(updatePropertiesTask, WarPlugin.WAR_TASK_NAME);
		task.setDescription("Alias for 'ant war'.");

		return task;
	}

	private UpdatePropertiesTask _addTaskUpdateProperties(
		Project project, final WorkspaceExtension workspaceExtension) {

		UpdatePropertiesTask updatePropertiesTask = GradleUtil.addTask(
			project, UPDATE_PROPERTIES_TASK_NAME, UpdatePropertiesTask.class);

		updatePropertiesTask.property(
			"app.server.parent.dir",
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return FileUtil.getAbsolutePath(
						workspaceExtension.getHomeDir());
				}

			});

		updatePropertiesTask.setDescription(
			"Updates the Plugins SDK build properties with the workspace " +
				"configuration.");

		String userName = System.getProperty("user.name");

		updatePropertiesTask.setPropertiesFile(
			"build." + userName + ".properties");

		return updatePropertiesTask;
	}

	private void _configureAnt(Project project) {
		AntBuilder antBuilder = project.getAnt();

		antBuilder.importBuild("build.xml");
	}

	@SuppressWarnings("serial")
	private void _configureRootTaskDistBundle(final Task warTask) {
		Project project = warTask.getProject();

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		copy.dependsOn(warTask);

		copy.into(
			"osgi/modules",
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					Project project = warTask.getProject();

					ConfigurableFileTree configurableFileTree =
						project.fileTree("dist");

					configurableFileTree.builtBy(warTask);
					configurableFileTree.include("*.war");

					copySpec.from(configurableFileTree);
				}

			});
	}

	private void _configureTaskWar(
		Task warTask, final WorkspaceExtension workspaceExtension,
		final Task initBundleTask) {

		warTask.dependsOn(
			new Callable<Task>() {

				@Override
				public Task call() throws Exception {
					File homeDir = workspaceExtension.getHomeDir();

					if (homeDir.exists()) {
						return null;
					}

					return initBundleTask;
				}

			});
	}

	private static final String _DEFAULT_ROOT_DIR_NAME = "plugins-sdk";

	private static final String _DEFAULT_ROOT_DIR_PROPERTY_NAME =
		WorkspacePlugin.PROPERTY_PREFIX + "plugins.sdk.dir";

	private final Set<File> _defaultRootDirs;

}