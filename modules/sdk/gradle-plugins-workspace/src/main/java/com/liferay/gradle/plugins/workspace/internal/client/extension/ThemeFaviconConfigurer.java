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
import com.liferay.gradle.plugins.extensions.BundleExtension;
import com.liferay.gradle.plugins.util.BndUtil;
import com.liferay.gradle.plugins.workspace.configurators.ClientExtensionProjectConfigurator;
import com.liferay.gradle.plugins.workspace.configurators.RootProjectConfigurator;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.internal.util.StringUtil;
import com.liferay.petra.string.StringBundler;

import groovy.lang.Closure;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFile;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.jvm.tasks.Jar;

/**
 * @author Gregory Amerson
 */
public class ThemeFaviconConfigurer implements ClientExtensionConfigurer {

	public static final String BUILD_FAVICON_TASK_NAME = "buildFavicon";

	@Override
	public void apply(Project project, ClientExtension clientExtension) {
		Copy faviconTask = _addTaskBuildFavicon(project, clientExtension);

		TaskProvider<Jar> jarTaskProvider = GradleUtil.getTaskProvider(
			project, JavaPlugin.JAR_TASK_NAME, Jar.class);

		jarTaskProvider.configure(
			new Action<Jar>() {

				@Override
				public void execute(Jar jar) {
					jar.dependsOn(faviconTask);

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
						_themeFaviconBundleInstructions(
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

	@SuppressWarnings("serial")
	private Copy _addTaskBuildFavicon(
		Project project, ClientExtension clientExtension) {

		Copy copy = GradleUtil.addTask(
			project, BUILD_FAVICON_TASK_NAME + clientExtension.id.toUpperCase(),
			Copy.class);

		copy.setDescription("Assembles favicon.");
		copy.setGroup(BasePlugin.BUILD_GROUP);
		copy.into(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return "";
				}

			},
			new Closure<Void>(copy) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					CopySpec fromSrc = copySpec.from(project.file("src"));

					fromSrc.setIncludeEmptyDirs(false);
					fromSrc.include("*.ico");
				}

			});

		copy.into(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return "configurator";
				}

			},
			new Closure<Void>(copy) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					CopySpec fromSrc = copySpec.from(
						project.file("configurator"));

					fromSrc.setIncludeEmptyDirs(false);
					fromSrc.include("*.json");
				}

			});

		copy.setDestinationDir(project.getBuildDir());

		return copy;
	}

	private void _configureArtifacts(Project project, Jar jar) {
		ArtifactHandler artifacts = project.getArtifacts();

		artifacts.add(Dependency.ARCHIVES_CONFIGURATION, jar);
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

	private void _configureTaskDeploy(Project project, Jar jar) {
		Copy copy = (Copy)GradleUtil.getTask(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME);

		copy.dependsOn(BasePlugin.ASSEMBLE_TASK_NAME);
		copy.from(jar);
	}

	private void _defaultClientExtensionBundleInstructions(
		BundleExtension bundleExtension) {

		bundleExtension.instruction("Bundle-SymbolicName", "${project.name}");
		bundleExtension.instruction("Bundle-Version", "${project.version}");
		bundleExtension.instruction(
			"Require-Capability", _OSGI_EXTENDER_CAPABILITY);
		bundleExtension.instruction("Web-ContextPath", "/${project.name}");
	}

	private void _themeFaviconBundleInstructions(
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
			"META-INF/resources/=build/;filter:=*.ico;recursive:=false");

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
			"-fixupmessages" + clientExtension.id,
			"No translation found for macro");
	}

	private static final String _OSGI_EXTENDER_CAPABILITY =
		"osgi.extender;filter:=\"(&(osgi.extender=osgi.configurator)" +
			"(version>=1.0)(!(version>=2.0)))\"";

}