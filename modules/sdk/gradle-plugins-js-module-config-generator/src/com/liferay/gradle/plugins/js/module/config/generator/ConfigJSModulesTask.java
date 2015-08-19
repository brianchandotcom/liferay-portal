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

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

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
import org.gradle.api.Task;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.util.PatternFilterable;
import org.gradle.api.tasks.util.PatternSet;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class ConfigJSModulesTask extends NodeTask {

	public ConfigJSModulesTask() {
		dependsOn(
			JSModuleConfigGeneratorPlugin.
				DOWNLOAD_LFR_MODULE_CONFIG_GENERATOR_TASK_NAME);
		dependsOn(
			BasePlugin.CLEAN_TASK_NAME + StringUtil.capitalize(getName()));
		dependsOn(SetupTask.NAME);

		onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					ConfigJSModulesTask configJSModulesTask =
						(ConfigJSModulesTask)task;

					File file = configJSModulesTask.getModuleConfigFile();

					if ((file != null) && file.exists()) {
						return true;
					}

					return false;
				}

			});

		Project project = getProject();

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					NodeExtension nodeExtension = GradleUtil.getExtension(
						project, NodeExtension.class);

					File scriptFile = new File(
						nodeExtension.getNodeModulesDir(),
						"node_modules/lfr-module-config-generator/bin" +
							"/index.js");

					setScript(scriptFile);
				}

			});
	}

	public ConfigJSModulesTask exclude(Closure<?> closure) {
		_patternFilterable.exclude(closure);

		return this;
	}

	public ConfigJSModulesTask exclude(Iterable<String> excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	public ConfigJSModulesTask exclude(Spec<FileTreeElement> spec) {
		_patternFilterable.exclude(spec);

		return this;
	}

	public ConfigJSModulesTask exclude(String ... excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	@Override
	public void exec() {
		Project project = getProject();

		project.copy(
			new Action<CopySpec>() {

				@Override
				public void execute(CopySpec copySpec) {
					copySpec.from(getSourceFiles());
					copySpec.into(getOutputDir());
				}

			});

		setArgs(getCompleteArgs());

		super.exec();

		project.copy(
			new Action<CopySpec>() {

				@Override
				public void execute(CopySpec copySpec) {
					copySpec.from(getOutputDir());
					copySpec.into(getSourceDir());
				}

			});
	}

	@Input
	@Optional
	public String getConfigVariable() {
		return GradleUtil.toString(_configVariable);
	}

	public Set<String> getExcludes() {
		return _patternFilterable.getExcludes();
	}

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

	@OutputDirectory
	public File getOutputDir() {
		return new File(getTemporaryDir(), "files");
	}

	@OutputFile
	public File getOutputFile() {
		return GradleUtil.toFile(getProject(), _outputFile);
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

	public ConfigJSModulesTask include(Closure<?> closure) {
		_patternFilterable.include(closure);

		return this;
	}

	public ConfigJSModulesTask include(Iterable<String> includes) {
		_patternFilterable.include(includes);

		return this;
	}

	public ConfigJSModulesTask include(Spec<FileTreeElement> spec) {
		_patternFilterable.include(spec);

		return this;
	}

	public ConfigJSModulesTask include(String ... includes) {
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

	public ConfigJSModulesTask setExcludes(Iterable<String> excludes) {
		_patternFilterable.setExcludes(excludes);

		return this;
	}

	public void setIgnorePath(boolean ignorePath) {
		_ignorePath = ignorePath;
	}

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

	protected List<Object> getCompleteArgs() {
		List<Object> completeArgs = new ArrayList<>();

		GUtil.addToCollection(completeArgs, getArgs());

		String configVariable = getConfigVariable();

		if (Validator.isNotNull(configVariable)) {
			completeArgs.add("--config");
			completeArgs.add(configVariable);
		}

		String moduleExtension = getModuleExtension();

		if (Validator.isNotNull(moduleExtension)) {
			completeArgs.add("--extension");
			completeArgs.add(moduleExtension);
		}

		String moduleFormat = getModuleFormat();

		if (Validator.isNotNull(moduleFormat)) {
			completeArgs.add("--format");
			completeArgs.add(moduleFormat);
		}

		boolean ignorePath = isIgnorePath();

		if (ignorePath) {
			completeArgs.add("--ignorePath");
			completeArgs.add(ignorePath);
		}

		boolean keepFileExtension = isKeepFileExtension();

		if (keepFileExtension) {
			completeArgs.add("--keepExtension");
			completeArgs.add(keepFileExtension);
		}

		boolean lowerCase = isLowerCase();

		if (lowerCase) {
			completeArgs.add("--lowerCase");
			completeArgs.add(lowerCase);
		}

		completeArgs.add("--moduleConfig");
		completeArgs.add(FileUtil.getAbsolutePath(getModuleConfigFile()));

		completeArgs.add("--output");
		completeArgs.add(FileUtil.getAbsolutePath(getOutputFile()));

		File outputDir = getOutputDir();

		completeArgs.add("--moduleRoot");
		completeArgs.add(FileUtil.getAbsolutePath(outputDir));

		completeArgs.add(FileUtil.getAbsolutePath(outputDir.getParentFile()));

		return completeArgs;
	}

	private Object _configVariable;
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