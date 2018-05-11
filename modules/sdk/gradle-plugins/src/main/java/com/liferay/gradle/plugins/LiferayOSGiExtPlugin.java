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

import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.internal.CSSBuilderDefaultsPlugin;
import com.liferay.gradle.plugins.internal.EclipseDefaultsPlugin;
import com.liferay.gradle.plugins.internal.IdeaDefaultsPlugin;
import com.liferay.gradle.plugins.internal.WatchOSGiPlugin;
import com.liferay.gradle.plugins.internal.XMLFormatterDefaultsPlugin;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.xml.formatter.XMLFormatterPlugin;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileTree;
import org.gradle.api.java.archives.Manifest;
import org.gradle.api.java.archives.ManifestMergeDetails;
import org.gradle.api.java.archives.ManifestMergeSpec;
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.idea.IdeaPlugin;
import org.gradle.util.VersionNumber;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class LiferayOSGiExtPlugin implements Plugin<Project> {

	public static final String ORIGINAL_MODULE_CONFIGURATION_NAME =
		"originalModule";

	public static final String UNZIP_ORIGINAL_MODULE_TASK_NAME =
		"unzipOriginalModule";

	@Override
	public void apply(final Project project) {
		_applyPlugins(project);

		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		final Configuration originalModuleConfiguration =
			_addConfigurationOriginalModule(project);

		Sync unzipOriginalModuleTask = _addTaskUnzipOriginalModule(
			project, originalModuleConfiguration);

		_configureConfigurationCompileOnly(
			project, originalModuleConfiguration);

		_configureLiferay(project);
		_configureTaskDeploy(jar);

		_configureTaskJar(jar, unzipOriginalModuleTask);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureProject(project, originalModuleConfiguration);
				}

			});
	}

	private Configuration _addConfigurationOriginalModule(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, ORIGINAL_MODULE_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures the original module to extend.");
		configuration.setTransitive(false);
		configuration.setVisible(false);

		return configuration;
	}

	private Sync _addTaskUnzipOriginalModule(
		final Project project, final Configuration configuration) {

		Sync sync = GradleUtil.addTask(
			project, UNZIP_ORIGINAL_MODULE_TASK_NAME, Sync.class);

		sync.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() {
					return project.zipTree(configuration.getSingleFile());
				}

			});

		sync.into(
			new Callable<File>() {

				@Override
				public File call() {
					return new File(project.getBuildDir(), "original-module");
				}

			});

		sync.setDescription(
			"Unzips the original module into a temporary directory.");

		return sync;
	}

	private void _applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, CSSBuilderPlugin.class);
		GradleUtil.applyPlugin(project, EclipsePlugin.class);
		GradleUtil.applyPlugin(project, IdeaPlugin.class);
		GradleUtil.applyPlugin(project, JavaPlugin.class);
		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
		GradleUtil.applyPlugin(project, XMLFormatterPlugin.class);

		CSSBuilderDefaultsPlugin.INSTANCE.apply(project);
		EclipseDefaultsPlugin.INSTANCE.apply(project);
		IdeaDefaultsPlugin.INSTANCE.apply(project);
		WatchOSGiPlugin.INSTANCE.apply(project);
		XMLFormatterDefaultsPlugin.INSTANCE.apply(project);
	}

	private Configuration _configureConfigurationCompileOnly(
		Project project, Configuration originalModuleConfiguration) {

		Configuration configuration = GradleUtil.getConfiguration(
			project, JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME);

		configuration.extendsFrom(originalModuleConfiguration);

		return configuration;
	}

	private void _configureLiferay(Project project) {
		final LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		liferayExtension.setDeployDir(
			new Callable<File>() {

				@Override
				public File call() {
					return new File(
						liferayExtension.getAppServerParentDir(),
						"osgi/marketplace/override");
				}

			});
	}

	private void _configureProject(
		Project project, Configuration originalModuleConfiguration) {

		try (JarFile jarFile = new JarFile(
				originalModuleConfiguration.getSingleFile())) {

			java.util.jar.Manifest manifest = jarFile.getManifest();

			Attributes attributes = manifest.getMainAttributes();

			String version = attributes.getValue(Constants.BUNDLE_VERSION);

			VersionNumber versionNumber = VersionNumber.parse(version);

			if (Validator.isNull(versionNumber.getQualifier())) {
				version += '.' + _VERSION_SUFFIX;
			}
			else {
				version += '-' + _VERSION_SUFFIX;
			}

			project.setVersion(version);

			BasePluginConvention basePluginConvention =
				GradleUtil.getConvention(project, BasePluginConvention.class);

			basePluginConvention.setArchivesBaseName(
				attributes.getValue(Constants.BUNDLE_SYMBOLICNAME));
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	private void _configureTaskDeploy(Jar jar) {
		Copy copy = (Copy)GradleUtil.getTask(
			jar.getProject(), LiferayBasePlugin.DEPLOY_TASK_NAME);

		copy.from(jar);
	}

	private Jar _configureTaskJar(
		final Jar jar, final Sync unzipOriginalModuleTask) {

		jar.dependsOn(unzipOriginalModuleTask);

		jar.from(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return unzipOriginalModuleTask.getDestinationDir();
				}

			});

		jar.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);

		Manifest manifest = jar.getManifest();

		manifest.attributes(
			Collections.singletonMap(
				Constants.BUNDLE_VERSION,
				new Object() {

					@Override
					public String toString() {
						return jar.getVersion();
					}

				}));

		manifest.from(
			new Callable<File>() {

				@Override
				public File call() {
					return new File(
						unzipOriginalModuleTask.getDestinationDir(),
						"META-INF/MANIFEST.MF");
				}

			},
			new Closure<Void>(jar) {

				@SuppressWarnings("unused")
				public void doCall(ManifestMergeSpec manifestMergeSpec) {
					manifestMergeSpec.eachEntry(
						new Action<ManifestMergeDetails>() {

							@Override
							public void execute(
								ManifestMergeDetails manifestMergeDetails) {

								if (Constants.BUNDLE_VERSION.equals(
										manifestMergeDetails.getKey())) {

									manifestMergeDetails.setValue(
										manifestMergeDetails.getBaseValue());
								}
							}

						});
				}

			});

		return jar;
	}

	private static final String _VERSION_SUFFIX = "ext";

}