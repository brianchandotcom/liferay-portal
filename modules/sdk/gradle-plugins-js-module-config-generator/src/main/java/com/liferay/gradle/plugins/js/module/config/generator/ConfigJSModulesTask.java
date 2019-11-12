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

package com.liferay.gradle.plugins.js.module.config.generator;

import com.liferay.gradle.plugins.node.tasks.ExecuteNodeScriptTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.List;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.util.PatternFilterable;
import org.gradle.api.tasks.util.PatternSet;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ConfigJSModulesTask
	extends ExecuteNodeScriptTask implements PatternFilterable {

	public ConfigJSModulesTask() {
		include("**/*.es.js*", "**/*.soy.js*");
	}

	@Override
	public ConfigJSModulesTask exclude(
		@SuppressWarnings("rawtypes") Closure excludeSpec) {

		_patternFilterable.exclude(excludeSpec);

		return this;
	}

	@Override
	public ConfigJSModulesTask exclude(Iterable<String> excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	@Override
	public ConfigJSModulesTask exclude(Spec<FileTreeElement> excludeSpec) {
		_patternFilterable.exclude(excludeSpec);

		return this;
	}

	@Override
	public ConfigJSModulesTask exclude(String... excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	@Override
	public void executeNode() throws Exception {
		Project project = getProject();

		final File outputDir = getOutputDir();

		project.delete(getOutputFile(), outputDir);

		project.copy(
			new Action<CopySpec>() {

				@Override
				public void execute(CopySpec copySpec) {
					copySpec.from(getSourceFiles());
					copySpec.into(outputDir);
				}

			});

		super.executeNode();

		project.copy(
			new Action<CopySpec>() {

				@Override
				public void execute(CopySpec copySpec) {
					copySpec.from(outputDir);
					copySpec.into(getSourceDir());
				}

			});
	}

	@Input
	@Optional
	public String getConfigVariable() {
		return GradleUtil.toString(_configVariable);
	}

	@Input
	@Optional
	public String getCustomDefine() {
		return GradleUtil.toString(_customDefine);
	}

	@Override
	public Set<String> getExcludes() {
		return _patternFilterable.getExcludes();
	}

	@Override
	public Set<String> getIncludes() {
		return _patternFilterable.getIncludes();
	}

	@InputFile
	@Optional
	public File getModuleConfigFile() {
		return GradleUtil.toFile(getProject(), _moduleConfigFile);
	}

	@Input
	@Optional
	public String getModuleExtension() {
		return GradleUtil.toString(_moduleExtension);
	}

	@Input
	@Optional
	public String getModuleFormat() {
		return GradleUtil.toString(_moduleFormat);
	}

	public File getOutputDir() {
		return new File(getTemporaryDir(), "files");
	}

	public File getOutputFile() {
		return GradleUtil.toFile(getProject(), _outputFile);
	}

	@Input
	public File getSourceDir() {
		return GradleUtil.toFile(getProject(), _sourceDir);
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getSourceFiles() {
		Project project = getProject();

		File sourceDir = getSourceDir();

		if (sourceDir == null) {
			return project.files();
		}

		FileTree fileTree = project.fileTree(sourceDir);

		return fileTree.matching(_patternFilterable);
	}

	@Override
	public ConfigJSModulesTask include(
		@SuppressWarnings("rawtypes") Closure includeSpec) {

		_patternFilterable.include(includeSpec);

		return this;
	}

	@Override
	public ConfigJSModulesTask include(Iterable<String> includes) {
		_patternFilterable.include(includes);

		return this;
	}

	@Override
	public ConfigJSModulesTask include(Spec<FileTreeElement> includeSpec) {
		_patternFilterable.include(includeSpec);

		return this;
	}

	@Override
	public ConfigJSModulesTask include(String... includes) {
		_patternFilterable.include(includes);

		return this;
	}

	@Input
	public boolean isIgnorePath() {
		return _ignorePath;
	}

	@Input
	public boolean isKeepFileExtension() {
		return _keepFileExtension;
	}

	@Input
	public boolean isLowerCase() {
		return _lowerCase;
	}

	public void setConfigVariable(Object configVariable) {
		_configVariable = configVariable;
	}

	public void setCustomDefine(Object customDefine) {
		_customDefine = customDefine;
	}

	@Override
	public ConfigJSModulesTask setExcludes(Iterable<String> excludes) {
		_patternFilterable.setExcludes(excludes);

		return this;
	}

	public void setIgnorePath(boolean ignorePath) {
		_ignorePath = ignorePath;
	}

	@Override
	public ConfigJSModulesTask setIncludes(Iterable<String> includes) {
		_patternFilterable.setIncludes(includes);

		return this;
	}

	public void setKeepFileExtension(boolean keepFileExtension) {
		_keepFileExtension = keepFileExtension;
	}

	public void setLowerCase(boolean lowerCase) {
		_lowerCase = lowerCase;
	}

	public void setModuleConfigFile(Object moduleConfigFile) {
		_moduleConfigFile = moduleConfigFile;
	}

	public void setModuleExtension(Object moduleExtension) {
		_moduleExtension = moduleExtension;
	}

	public void setModuleFormat(Object moduleFormat) {
		_moduleFormat = moduleFormat;
	}

	public void setOutputFile(Object outputFile) {
		_outputFile = outputFile;
	}

	public void setSourceDir(Object sourceDir) {
		_sourceDir = sourceDir;
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		String configVariable = getConfigVariable();

		if (configVariable != null) {
			completeArgs.add("--config");
			completeArgs.add(configVariable);
		}

		String moduleExtension = getModuleExtension();

		if (moduleExtension != null) {
			completeArgs.add("--extension");
			completeArgs.add(moduleExtension);
		}

		String moduleFormat = getModuleFormat();

		if (moduleFormat != null) {
			completeArgs.add("--format");
			completeArgs.add(moduleFormat);
		}

		boolean ignorePath = isIgnorePath();

		if (ignorePath) {
			completeArgs.add("--ignorePath");
			completeArgs.add(String.valueOf(ignorePath));
		}

		boolean keepFileExtension = isKeepFileExtension();

		if (keepFileExtension) {
			completeArgs.add("--keepExtension");
			completeArgs.add(String.valueOf(keepFileExtension));
		}

		boolean lowerCase = isLowerCase();

		if (lowerCase) {
			completeArgs.add("--lowerCase");
			completeArgs.add(String.valueOf(lowerCase));
		}

		completeArgs.add("--moduleConfig");
		completeArgs.add(FileUtil.getAbsolutePath(getModuleConfigFile()));

		String customDefine = getCustomDefine();

		if (Validator.isNotNull(customDefine)) {
			completeArgs.add("--namespace");
			completeArgs.add(customDefine);
		}

		completeArgs.add("--output");
		completeArgs.add(FileUtil.getAbsolutePath(getOutputFile()));

		File outputDir = getOutputDir();

		completeArgs.add("--moduleRoot");
		completeArgs.add(FileUtil.getAbsolutePath(outputDir));

		completeArgs.add(FileUtil.getAbsolutePath(outputDir.getParentFile()));

		return completeArgs;
	}

	private Object _configVariable;
	private Object _customDefine = "Liferay.Loader";
	private boolean _ignorePath;
	private boolean _keepFileExtension;
	private boolean _lowerCase;
	private Object _moduleConfigFile;
	private Object _moduleExtension;
	private Object _moduleFormat;
	private Object _outputFile;
	private final PatternFilterable _patternFilterable = new PatternSet();
	private Object _sourceDir;

}