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

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import com.moowork.gradle.node.NodeExtension;
import com.moowork.gradle.node.task.NodeTask;
import com.moowork.gradle.node.task.SetupTask;

import groovy.lang.Closure;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.util.PatternFilterable;
import org.gradle.api.tasks.util.PatternSet;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class TranspileJSTask extends NodeTask {

	public TranspileJSTask() {
		dependsOn(JSTranspilerPlugin.DOWNLOAD_BABEL_TASK_NAME);
		dependsOn(JSTranspilerPlugin.DOWNLOAD_LFR_AMD_LOADER_TASK_NAME);
		dependsOn(SetupTask.NAME);

		Project project = getProject();

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					NodeExtension nodeExtension = GradleUtil.getExtension(
						project, NodeExtension.class);

					File scriptFile = new File(
						nodeExtension.getNodeModulesDir(),
						"node_modules/babel/bin/babel/index.js");

					setScript(scriptFile);

					setWorkingDir(_sourceDir);
				}

			});
	}

	public TranspileJSTask exclude(Closure<?> closure) {
		_patternFilterable.exclude(closure);

		return this;
	}

	public TranspileJSTask exclude(Iterable<String> excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	public TranspileJSTask exclude(Spec<FileTreeElement> spec) {
		_patternFilterable.exclude(spec);

		return this;
	}

	public TranspileJSTask exclude(String ... excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	@Override
	public void exec() {
		setArgs(getCompleteArgs());

		super.exec();
	}

	public Set<String> getExcludes() {
		return _patternFilterable.getExcludes();
	}

	public Set<String> getIncludes() {
		return _patternFilterable.getIncludes();
	}

	@Input
	public String getModules() {
		return GradleUtil.toString(_modules);
	}

	@OutputDirectory
	public File getOutputDir() {
		return GradleUtil.toFile(getProject(), _outputDir);
	}

	public File getSourceDir() {
		return GradleUtil.toFile(getProject(), _sourceDir);
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getSourceFiles() {
		Project project = getProject();

		if (_sourceDir == null) {
			return project.files();
		}

		FileTree fileTree = project.fileTree(_sourceDir);

		return fileTree.matching(_patternFilterable);
	}

	public TranspileJSTask include(Closure<?> closure) {
		_patternFilterable.include(closure);

		return this;
	}

	public TranspileJSTask include(Iterable<String> includes) {
		_patternFilterable.include(includes);

		return this;
	}

	public TranspileJSTask include(Spec<FileTreeElement> spec) {
		_patternFilterable.include(spec);

		return this;
	}

	public TranspileJSTask include(String ... includes) {
		_patternFilterable.include(includes);

		return this;
	}

	public TranspileJSTask setExcludes(Iterable<String> excludes) {
		_patternFilterable.setExcludes(excludes);

		return this;
	}

	public TranspileJSTask setIncludes(Iterable<String> includes) {
		_patternFilterable.setIncludes(includes);

		return this;
	}

	public void setModules(Object modules) {
		_modules = modules;
	}

	public void setOutputDir(Object outputDir) {
		_outputDir = outputDir;
	}

	public void setSourceDir(Object sourceDir) {
		_sourceDir = sourceDir;
	}

	protected List<Object> getCompleteArgs() {
		File sourceDir = getSourceDir();

		List<Object> completeArgs = new ArrayList<>();

		GUtil.addToCollection(completeArgs, getArgs());

		completeArgs.add("--modules");
		completeArgs.add(getModules());

		completeArgs.add("--out-dir");
		completeArgs.add(FileUtil.relativize(getOutputDir(), sourceDir));

		for (File file : getSourceFiles()) {
			completeArgs.add(FileUtil.relativize(file, sourceDir));
		}

		return completeArgs;
	}

	private Object _modules = "amd";
	private Object _outputDir;
	private final PatternFilterable _patternFilterable = new PatternSet();
	private Object _sourceDir;

}