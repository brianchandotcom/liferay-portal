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

import com.liferay.gradle.plugins.node.tasks.ExecuteNodeTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
public class TranspileJSTask extends ExecuteNodeTask {

	public TranspileJSTask() {
		dependsOn(JSTranspilerPlugin.DOWNLOAD_BABEL_TASK_NAME);
		dependsOn(JSTranspilerPlugin.DOWNLOAD_LFR_AMD_LOADER_TASK_NAME);

		include("**/*.es.js");
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
	public void executeNode() {
		setArgs(getCompleteArgs());

		super.setWorkingDir(getWorkingDir());

		super.executeNode();
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

	public String getScriptFileName() {
		return _scriptFileName;
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

	public void setScriptFileName(String scriptFileName) {
		_scriptFileName = scriptFileName;
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

	public static enum SourceMaps {

		DISABLED, ENABLED, ENABLED_INLINE

	}

	protected List<Object> getCompleteArgs() {
		File sourceDir = getSourceDir();

		List<Object> completeArgs = new ArrayList<>();

		File scriptFile = new File(getNodeDir(), getScriptFileName());

		completeArgs.add(FileUtil.getAbsolutePath(scriptFile));

		GUtil.addToCollection(completeArgs, getArgs());

		completeArgs.add("--modules");
		completeArgs.add(getModules());

		completeArgs.add("--out-dir");
		completeArgs.add(FileUtil.relativize(getOutputDir(), sourceDir));

		SourceMaps sourceMaps = getSourceMaps();

		if (sourceMaps != SourceMaps.DISABLED) {
			completeArgs.add("--source-maps");
		}

		if (sourceMaps == SourceMaps.ENABLED_INLINE) {
			completeArgs.add("inline");
		}

		completeArgs.add("--stage");
		completeArgs.add(getStage());

		for (File file : getSourceFiles()) {
			completeArgs.add(FileUtil.relativize(file, sourceDir));
		}

		return completeArgs;
	}

	private Object _modules = "amd";
	private Object _outputDir;
	private final PatternFilterable _patternFilterable = new PatternSet();
	private String _scriptFileName = "node_modules/babel/bin/babel.js";
	private Object _sourceDir;
	private SourceMaps _sourceMaps = SourceMaps.ENABLED;
	private int _stage = 0;

}