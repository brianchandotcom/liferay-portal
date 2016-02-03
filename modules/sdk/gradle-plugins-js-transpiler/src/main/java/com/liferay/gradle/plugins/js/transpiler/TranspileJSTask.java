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

import com.liferay.gradle.plugins.node.tasks.ExecuteNodeScriptTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

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

/**
 * @author Andrea Di Giorgi
 */
public class TranspileJSTask
	extends ExecuteNodeScriptTask implements PatternFilterable {

	public TranspileJSTask() {
		dependsOn(JSTranspilerPlugin.DOWNLOAD_LFR_AMD_LOADER_TASK_NAME);
		dependsOn(JSTranspilerPlugin.DOWNLOAD_METAL_CLI_TASK_NAME);

		include("**/*.es.js");

		setScriptFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						getNodeDir(), "node_modules/metal-cli/index.js");
				}

			});
	}

	@Override
	public TranspileJSTask exclude(
		@SuppressWarnings("rawtypes") Closure excludeSpec) {

		_patternFilterable.exclude(excludeSpec);

		return this;
	}

	@Override
	public TranspileJSTask exclude(Iterable<String> excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	@Override
	public TranspileJSTask exclude(Spec<FileTreeElement> excludeSpec) {
		_patternFilterable.exclude(excludeSpec);

		return this;
	}

	@Override
	public TranspileJSTask exclude(String ... excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	@Override
	public void executeNode() {
		super.setWorkingDir(getWorkingDir());

		super.executeNode();
	}

	@Override
	public Set<String> getExcludes() {
		return _patternFilterable.getExcludes();
	}

	@Override
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

	@Input
	public SourceMaps getSourceMaps() {
		return _sourceMaps;
	}

	@Input
	public int getStage() {
		return _stage;
	}

	@Override
	public File getWorkingDir() {
		return getSourceDir();
	}

	@Override
	public TranspileJSTask include(
		@SuppressWarnings("rawtypes") Closure includeSpec) {

		_patternFilterable.include(includeSpec);

		return this;
	}

	@Override
	public TranspileJSTask include(Iterable<String> includes) {
		_patternFilterable.include(includes);

		return this;
	}

	@Override
	public TranspileJSTask include(Spec<FileTreeElement> includeSpec) {
		_patternFilterable.include(includeSpec);

		return this;
	}

	@Override
	public TranspileJSTask include(String ... includes) {
		_patternFilterable.include(includes);

		return this;
	}

	@Override
	public TranspileJSTask setExcludes(Iterable<String> excludes) {
		_patternFilterable.setExcludes(excludes);

		return this;
	}

	@Override
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

	public void setSourceMaps(SourceMaps sourceMaps) {
		_sourceMaps = sourceMaps;
	}

	public void setStage(int stage) {
		_stage = stage;
	}

	@Override
	public void setWorkingDir(Object workingDir) {
		throw new UnsupportedOperationException();
	}

	public static enum SourceMaps {

		DISABLED, ENABLED, ENABLED_INLINE

	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		File sourceDir = getSourceDir();

		completeArgs.add("build");

		completeArgs.add("--dest");
		completeArgs.add(FileUtil.relativize(getOutputDir(), sourceDir));

		completeArgs.add("--format");
		completeArgs.add(getModules());

		completeArgs.add("--moduleName");
		completeArgs.add("");

		SourceMaps sourceMaps = getSourceMaps();

		if (sourceMaps != SourceMaps.DISABLED) {
			completeArgs.add("--source-maps");
		}

		if (sourceMaps == SourceMaps.ENABLED_INLINE) {
			completeArgs.add("inline");
		}

		completeArgs.add("--src");

		for (File file : getSourceFiles()) {
			completeArgs.add(FileUtil.relativize(file, sourceDir));
		}

		completeArgs.add("--stage");
		completeArgs.add(String.valueOf(getStage()));

		return completeArgs;
	}

	private Object _modules = "amd";
	private Object _outputDir;
	private final PatternFilterable _patternFilterable = new PatternSet();
	private Object _sourceDir;
	private SourceMaps _sourceMaps = SourceMaps.ENABLED;
	private int _stage = 0;

}