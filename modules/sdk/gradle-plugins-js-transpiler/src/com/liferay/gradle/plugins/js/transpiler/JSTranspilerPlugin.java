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

import com.liferay.gradle.util.GradleUtil;

import com.moowork.gradle.node.NodeExtension;
import com.moowork.gradle.node.NodePlugin;
import com.moowork.gradle.node.task.NpmSetupTask;
import com.moowork.gradle.node.task.NpmTask;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskOutputs;

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

		final JSTranspilerExtension jsTranspilerExtension =
			GradleUtil.addExtension(
				project, EXTENSION_NAME, JSTranspilerExtension.class);

		final NpmTask downloadBabelTask = addTaskDownloadModule(
			project, DOWNLOAD_BABEL_TASK_NAME);
		final NpmTask downloadLfrAmdLoaderTask = addTaskDownloadModule(
			project, DOWNLOAD_LFR_AMD_LOADER_TASK_NAME);

		final TranspileJSTask transpileJSTask = addTaskTranspileJS(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTaskDownloadModule(
						downloadBabelTask, "babel",
						jsTranspilerExtension.getBabelVersion());
					configureTaskDownloadModule(
						downloadLfrAmdLoaderTask, "lfr-amd-loader",
						jsTranspilerExtension.getLfrAmdLoaderVersion());

					configureTaskTranspileJS(transpileJSTask);
				}

			});
	}

	protected NpmTask addTaskDownloadModule(
		final Project project, String taskName) {

		NpmTask npmTask = GradleUtil.addTask(project, taskName, NpmTask.class);

		npmTask.dependsOn(NpmSetupTask.NAME);

		npmTask.setWorkingDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					NodeExtension nodeExtension = GradleUtil.getExtension(
						project, NodeExtension.class);

					return nodeExtension.getNodeModulesDir();
				}

			});

		return npmTask;
	}

	protected TranspileJSTask addTaskTranspileJS(Project project) {
		TranspileJSTask transpileJSTask = GradleUtil.addTask(
			project, TRANSPILE_JS_TASK_NAME, TranspileJSTask.class);

		transpileJSTask.setDescription("Transpiles JS files.");
		transpileJSTask.setGroup(BasePlugin.BUILD_GROUP);

		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(transpileJSTask);

		return transpileJSTask;
	}

	protected void configureTaskDownloadModule(
		NpmTask npmTask, String moduleName, String moduleVersion) {

		Project project = npmTask.getProject();

		NodeExtension nodeExtension = GradleUtil.getExtension(
			project, NodeExtension.class);

		List<String> args = new ArrayList<>();

		args.add("install");
		args.add(moduleName + "@" + moduleVersion);

		npmTask.setArgs(args);

		npmTask.setWorkingDir(nodeExtension.getNodeModulesDir());

		TaskInputs taskInputs = npmTask.getInputs();

		taskInputs.property("version", moduleVersion);

		TaskOutputs taskOutputs = npmTask.getOutputs();

		File moduleDir = new File(
			nodeExtension.getNodeModulesDir(), "node_modules/" + moduleName);

		taskOutputs.dir(moduleDir);
	}

	protected void configureTaskTranspileJS(TranspileJSTask transpileJSTask) {
		configureTaskTranspileJSOutputDir(transpileJSTask);
	}

	protected void configureTaskTranspileJSOutputDir(
		TranspileJSTask transpileJSTask) {

		if (transpileJSTask.getOutputDir() != null) {
			return;
		}

		SourceSet sourceSet = GradleUtil.getSourceSet(
			transpileJSTask.getProject(), SourceSet.MAIN_SOURCE_SET_NAME);

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		transpileJSTask.setOutputDir(sourceSetOutput.getResourcesDir());
	}

}