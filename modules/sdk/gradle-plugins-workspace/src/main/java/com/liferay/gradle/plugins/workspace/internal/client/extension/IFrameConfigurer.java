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

import com.liferay.gradle.plugins.extensions.BundleExtension;
import com.liferay.gradle.plugins.util.BndUtil;
import com.liferay.gradle.plugins.workspace.configurators.ClientExtensionProjectConfigurator;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.internal.util.StringUtil;
import com.liferay.petra.string.StringBundler;

import groovy.lang.Closure;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.jvm.tasks.Jar;

/**
 * @author Gregory Amerson
 */
public class IFrameConfigurer extends BaseClientExtensionConfigurer {

	public static final String BUILD_IFRAME_TASK_NAME = "buildIFrame";

	@Override
	public void apply(Project project, ClientExtension clientExtension) {
		Copy iframeTask = _addTaskBuildIFrame(project, clientExtension);

		TaskProvider<Jar> jarTaskProvider = GradleUtil.getTaskProvider(
			project, JavaPlugin.JAR_TASK_NAME, Jar.class);

		jarTaskProvider.configure(
			new Action<Jar>() {

				@Override
				public void execute(Jar jar) {
					jar.dependsOn(iframeTask);
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					BundleExtension bundleExtension =
						BndUtil.getBundleExtension(project.getExtensions());

					try {
						_iframeBundleInstructions(
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
	private Copy _addTaskBuildIFrame(
		Project project, ClientExtension clientExtension) {

		Copy copy = GradleUtil.addTask(
			project, BUILD_IFRAME_TASK_NAME + clientExtension.id.toUpperCase(),
			Copy.class);

		copy.setDescription("Assembles iframe.");
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
					fromSrc.include("*.css");
					fromSrc.include("*.html");
					fromSrc.include("*.js");
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

	private void _iframeBundleInstructions(
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
			"-includeresource.js." + clientExtension.id,
			"META-INF/resources/=build/;recursive:=false");

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

}