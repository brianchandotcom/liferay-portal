/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults;

import aQute.bnd.version.Version;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.FileUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.defaults.task.ReplaceRegexTask;
import com.liferay.gradle.plugins.extensions.LiferayExtension;

import groovy.lang.Closure;

import java.io.File;

import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.util.GUtil;
import org.gradle.util.VersionNumber;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayOSGiPortalCompatDefaultsPlugin
	extends BaseDefaultsPlugin<LiferayOSGiDefaultsPlugin> {

	public static final String BYTECODE_TRANSFORMER_CONFIGURATION_NAME =
		"bytecodeTransformer";

	public static final String IMPORT_FILES_TASK_NAME = "importFiles";

	public static final String TRANSFORM_IMPORTED_FILES_TASK_NAME =
		"transformImportedFiles";

	@Override
	protected void applyPluginDefaults(
		Project project, LiferayOSGiDefaultsPlugin liferayOSGiDefaultsPlugin) {

		File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");

		Configuration bytecodeTransformerConfiguration =
			_addConfigurationBytecodeTransformer(project);

		Copy importFilesTask = _addTaskImportFiles(project);

		JavaExec transformImportedFilesTask = _addTaskTransformImportedFiles(
			importFilesTask, bytecodeTransformerConfiguration);

		_configureLiferay(project, portalRootDir);
		_configureTaskClasses(transformImportedFilesTask);
		_configureTaskImportFiles(importFilesTask);
		_configureTaskUpdateFileVersions(project, portalRootDir);
	}

	@Override
	protected Class<LiferayOSGiDefaultsPlugin> getPluginClass() {
		return LiferayOSGiDefaultsPlugin.class;
	}

	private Configuration _addConfigurationBytecodeTransformer(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, BYTECODE_TRANSFORMER_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesBytecodeTransformer(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay Portal Tools Portal Compat Bytecode " +
				"Transformer.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesBytecodeTransformer(Project project) {
		GradleUtil.addDependency(
			project, BYTECODE_TRANSFORMER_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.portal.tools.portal.compat.bytecode.transformer",
			"1.0.3");
	}

	@SuppressWarnings("serial")
	private Copy _addTaskImportFiles(final Project project) {
		Copy copy = GradleUtil.addTask(
			project, IMPORT_FILES_TASK_NAME, Copy.class);

		ConfigurationContainer configurationContainer =
			project.getConfigurations();
		DependencyHandler dependencyHandler = project.getDependencies();

		Properties properties = GUtil.loadProperties(
			project.file("imported-files.properties"));

		for (String dependencyNotation : properties.stringPropertyNames()) {
			final String dependencyIncludes = properties.getProperty(
				dependencyNotation);

			Dependency dependency = dependencyHandler.create(
				dependencyNotation);

			final Configuration configuration =
				configurationContainer.detachedConfiguration(dependency);

			configuration.setTransitive(false);

			copy.from(
				new Callable<FileTree>() {

					@Override
					public FileTree call() throws Exception {
						return project.zipTree(configuration.getSingleFile());
					}

				},
				new Closure<Void>(copy) {

					@SuppressWarnings("unused")
					public void doCall(CopySpec copySpec) {
						copySpec.include(dependencyIncludes.split(","));
					}

				});
		}

		copy.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					JavaCompile javaCompile = (JavaCompile)GradleUtil.getTask(
						project, JavaPlugin.COMPILE_JAVA_TASK_NAME);

					return javaCompile.getDestinationDir();
				}

			});

		return copy;
	}

	private JavaExec _addTaskTransformImportedFiles(
		final Copy importFilesTask,
		Configuration bytecodeTransformerConfiguration) {

		JavaExec javaExec = GradleUtil.addTask(
			importFilesTask.getProject(), TRANSFORM_IMPORTED_FILES_TASK_NAME,
			JavaExec.class);

		javaExec.dependsOn(importFilesTask);

		Property<String> mainClass = javaExec.getMainClass();

		mainClass.set(
			"com.liferay.portal.tools.portal.compat.bytecode.transformer." +
				"PortalCompatBytecodeTransformer");

		javaExec.setClasspath(bytecodeTransformerConfiguration);
		javaExec.setDescription(
			"Processes imported classes using the Liferay Portal Tools " +
				"Portal Compat Bytecode Transformer.");

		javaExec.systemProperty(
			"classes.dir",
			new Object() {

				@Override
				public String toString() {
					return FileUtil.getAbsolutePath(
						importFilesTask.getDestinationDir());
				}

			});

		return javaExec;
	}

	private void _configureLiferay(Project project, File portalRootDir) {
		LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		liferayExtension.setDeployDir(new File(portalRootDir, "tmp/lib-pre"));
	}

	private void _configureTaskClasses(JavaExec transformImportedFilesTask) {
		Task classesTask = GradleUtil.getTask(
			transformImportedFilesTask.getProject(),
			JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(transformImportedFilesTask);
	}

	private void _configureTaskImportFiles(Copy importFilesTask) {
		importFilesTask.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					DependencyHandler dependencyHandler =
						project.getDependencies();
					Properties properties = GUtil.loadProperties(
						project.file("imported-files.properties"));

					for (String key : properties.stringPropertyNames()) {
						Dependency dependency = dependencyHandler.create(key);

						String dependencyVersion = dependency.getVersion();

						Version version = null;

						try {
							version = Version.parseVersion(dependencyVersion);
						}
						catch (IllegalArgumentException
									illegalArgumentException) {

							throw new GradleException(
								illegalArgumentException.getMessage(),
								illegalArgumentException);
						}

						if (version.isSnapshot()) {
							throw new GradleException(
								"The dependency " + key + " must not be a " +
									"snapshot");
						}
					}
				}

			});
	}

	@SuppressWarnings({"serial", "unchecked"})
	private void _configureTaskUpdateFileVersions(
		Project project, File portalRootDir) {

		String bundleSymbolicName = GradleUtil.getArchivesBaseName(project);

		final Pattern pattern = Pattern.compile(
			Pattern.quote(bundleSymbolicName) + "-(\\d.+?)\\.jar");

		ReplaceRegexTask replaceRegexTask =
			(ReplaceRegexTask)GradleUtil.getTask(
				project,
				LiferayOSGiDefaultsPlugin.UPDATE_FILE_VERSIONS_TASK_NAME);

		replaceRegexTask.match(
			pattern.pattern(), new File(portalRootDir, "build.properties"));

		replaceRegexTask.setReplaceOnlyIf(
			new Closure<Boolean>(replaceRegexTask) {

				@SuppressWarnings("unused")
				public Boolean doCall(
					String group, String replacement, String content,
					File contentFile) {

					Matcher matcher = pattern.matcher(content);

					if (!matcher.matches()) {
						return Boolean.TRUE;
					}

					VersionNumber groupVersionNumber = VersionNumber.parse(
						group);
					VersionNumber replacementVersionNumber =
						VersionNumber.parse(replacement);

					if (groupVersionNumber.getMajor() !=
							replacementVersionNumber.getMajor()) {

						return Boolean.TRUE;
					}

					return Boolean.FALSE;
				}

			});
	}

}