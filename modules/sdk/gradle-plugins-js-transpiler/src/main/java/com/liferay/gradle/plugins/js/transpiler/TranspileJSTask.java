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

import java.util.ArrayList;
import java.util.Arrays;
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

		_soySrcIncludes.add("**/*.soy.es");

		_srcIncludes.add("**/*.es.js");

		include("**/*.es.js");

		setScriptFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						getNodeDir(), "node_modules/metal-cli/index.js");
				}

			});

		setWorkingDir("classes/META-INF/resources");
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

	@Input
	public String getBundleFileName() {
		return GradleUtil.toString(_bundleFileName);
	}

	@Override
	public Set<String> getExcludes() {
		return _patternFilterable.getExcludes();
	}

	@Input
	public String getGlobalName() {
		return GradleUtil.toString(_globalName);
	}

	@Override
	public Set<String> getIncludes() {
		return _patternFilterable.getIncludes();
	}

	@Input
	public String getModuleName() {
		return GradleUtil.toString(_moduleName);
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
	public List<String> getSoySrcIncludes() {
		return GradleUtil.toStringList(_soySrcIncludes);
	}

	@Input
	public List<String> getSrcIncludes() {
		return GradleUtil.toStringList(_srcIncludes);
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

	public void setBundleFileName(Object bundleFileName) {
		_bundleFileName = bundleFileName;
	}

	public void setGlobalName(Object globalName) {
		_globalName = globalName;
	}

	public void setModuleName(Object moduleName) {
		_moduleName = moduleName;
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

	public void setSoySrcIncludes(Iterable<?> soySrcIncludes) {
		_soySrcIncludes.clear();
	}

	public void setSoySrcIncludes(Object ... soySrcIncludes) {
		setSoySrcIncludes(Arrays.asList(soySrcIncludes));
	}

	public void setSrcIncludes(Iterable<?> srcIncludes) {
		_srcIncludes.clear();
	}

	public void setSrcIncludes(Object ... srcIncludes) {
		setSrcIncludes(Arrays.asList(srcIncludes));
	}

	public static enum SourceMaps {

		DISABLED, ENABLED, ENABLED_INLINE

	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		File sourceDir = getSourceDir();
		File workingDir = getWorkingDir();

		completeArgs.add("build");

		completeArgs.add("--bundleFileName");
		completeArgs.add(getBundleFileName());

		completeArgs.add("--dest");
		completeArgs.add(workingDir.toString());

		completeArgs.add("--format");
		completeArgs.add(getModules());

		completeArgs.add("--globalName");
		completeArgs.add(getGlobalName());

		completeArgs.add("--moduleName");
		completeArgs.add(getModuleName());

		SourceMaps sourceMaps = getSourceMaps();

		if (sourceMaps != SourceMaps.ENABLED) {
			completeArgs.add("--source-maps");
			completeArgs.add(
				sourceMaps == SourceMaps.ENABLED_INLINE ? "inline" : "false");
		}

		completeArgs.add("--soySrc");

		for (String soySrcInclude : getSoySrcIncludes()) {
			completeArgs.add(soySrcInclude);
		}

		completeArgs.add("--soyDest");
		completeArgs.add(workingDir.toString());

		completeArgs.add("--src");

		for (String srcInclude : getSrcIncludes()) {
			completeArgs.add(srcInclude);
		}

		return completeArgs;
	}

	private Object _bundleFileName = "";
	private Object _globalName = "";
	private Object _moduleName = "";
	private Object _modules = "amd";
	private Object _outputDir;
	private final PatternFilterable _patternFilterable = new PatternSet();
	private Object _sourceDir;
	private SourceMaps _sourceMaps = SourceMaps.ENABLED;
	private final List<Object> _soySrcIncludes = new ArrayList<>();
	private final List<Object> _srcIncludes = new ArrayList<>();

}