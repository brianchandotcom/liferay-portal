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

package com.liferay.gradle.plugins.js.transpiler;

import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeModuleTask;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;

/**
 * @author Andrea Di Giorgi
 */
public class JSTranspilerPlugin implements Plugin<Project> {

	public static final String DOWNLOAD_BABEL_TASK_NAME = "downloadBabel";

	public static final String DOWNLOAD_LFR_AMD_LOADER_TASK_NAME =
		"downloadLfrAmdLoader";

	public static final String EXTENSION_NAME = "jsTranspiler";

	public static final String TRANSPILE_JS_TASK_NAME = "transpileJS";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, NodePlugin.class);

		JSTranspilerExtension jsTranspilerExtension = GradleUtil.addExtension(
			project, EXTENSION_NAME, JSTranspilerExtension.class);

		addTaskDownloadBabel(project, jsTranspilerExtension);
		addTaskDownloadLfrAmdLoader(project, jsTranspilerExtension);
		addTaskTranspileJS(project);
	}

	protected DownloadNodeModuleTask addTaskDownloadBabel(
		Project project, final JSTranspilerExtension jsTranspilerExtension) {

		DownloadNodeModuleTask downloadNodeModuleTask = GradleUtil.addTask(
			project, DOWNLOAD_BABEL_TASK_NAME, DownloadNodeModuleTask.class);

		downloadNodeModuleTask.setModuleName("babel");

		downloadNodeModuleTask.setModuleVersion(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return jsTranspilerExtension.getBabelVersion();
				}

			});

		return downloadNodeModuleTask;
	}

	protected DownloadNodeModuleTask addTaskDownloadLfrAmdLoader(
		Project project, final JSTranspilerExtension jsTranspilerExtension) {

		DownloadNodeModuleTask downloadNodeModuleTask = GradleUtil.addTask(
			project, DOWNLOAD_LFR_AMD_LOADER_TASK_NAME,
			DownloadNodeModuleTask.class);

		downloadNodeModuleTask.setModuleName("lfr-amd-loader");

		downloadNodeModuleTask.setModuleVersion(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return jsTranspilerExtension.getLfrAmdLoaderVersion();
				}

			});

		return downloadNodeModuleTask;
	}

	protected TranspileJSTask addTaskTranspileJS(final Project project) {
		TranspileJSTask transpileJSTask = GradleUtil.addTask(
			project, TRANSPILE_JS_TASK_NAME, TranspileJSTask.class);

		transpileJSTask.setDescription("Transpiles JS files.");
		transpileJSTask.setGroup(BasePlugin.BUILD_GROUP);

		transpileJSTask.setOutputDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSet sourceSet = GradleUtil.getSourceSet(
						project, SourceSet.MAIN_SOURCE_SET_NAME);

					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					return sourceSetOutput.getResourcesDir();
				}

			});

		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(transpileJSTask);

		return transpileJSTask;
	}

}