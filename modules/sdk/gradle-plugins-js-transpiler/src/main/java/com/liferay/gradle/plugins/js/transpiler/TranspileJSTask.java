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

import java.io.File;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.util.GUtil;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class TranspileJSTask extends ExecuteNodeScriptTask {

	public TranspileJSTask() {
		soySrcInclude("**/*.soy");
		srcInclude("**/*.es.js", "**/*.soy.js");
	}

	@Override
	public void executeNode() throws Exception {
		final File sourceDir = getSourceDir();
		final File workingDir = getWorkingDir();

		if (!sourceDir.equals(workingDir)) {
			Project project = getProject();

			project.copy(
				new Action<CopySpec>() {

					@Override
					public void execute(CopySpec copySpec) {
						copySpec.from(sourceDir);

						copySpec.include(getSoySrcIncludes());
						copySpec.include(getSrcIncludes());

						copySpec.into(workingDir);
						copySpec.setIncludeEmptyDirs(false);
					}

				});
		}

		super.executeNode();
	}

	@Input
	public String getBundleFileName() {
		return GradleUtil.toString(_bundleFileName);
	}

	@Input
	public String getGlobalName() {
		return GradleUtil.toString(_globalName);
	}

	@Input
	public String getModuleName() {
		return GradleUtil.toString(_moduleName);
	}

	@Input
	public String getModules() {
		return GradleUtil.toString(_modules);
	}

	@Input
	public File getSourceDir() {
		return GradleUtil.toFile(getProject(), _sourceDir);
	}

	@InputFiles
	public FileCollection getSourceFiles() {
		Project project = getProject();

		File sourceDir = getSourceDir();

		if (sourceDir == null) {
			return project.files();
		}

		ConfigurableFileTree configurableFileTree = project.fileTree(sourceDir);

		configurableFileTree.include(getSoySrcIncludes());
		configurableFileTree.include(getSrcIncludes());

		return configurableFileTree;
	}

	@Input
	public SourceMaps getSourceMaps() {
		return _sourceMaps;
	}

	@Input
	public List<String> getSoyDependencies() {
		return GradleUtil.toStringList(_soyDependencies);
	}

	public List<String> getSoySrcIncludes() {
		return GradleUtil.toStringList(_soySrcIncludes);
	}

	public List<String> getSrcIncludes() {
		return GradleUtil.toStringList(_srcIncludes);
	}

	@OutputDirectory
	@Override
	public File getWorkingDir() {
		return super.getWorkingDir();
	}

	public boolean isSkipWhenEmpty() {
		return _skipWhenEmpty;
	}

	@Input
	public boolean isSoySkipMetalGeneration() {
		return _soySkipMetalGeneration;
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

	public void setSkipWhenEmpty(boolean skipWhenEmpty) {
		_skipWhenEmpty = skipWhenEmpty;
	}

	public void setSourceDir(Object sourceDir) {
		_sourceDir = sourceDir;
	}

	public void setSourceMaps(SourceMaps sourceMaps) {
		_sourceMaps = sourceMaps;
	}

	public void setSoyDependencies(Iterable<?> soyDependencies) {
		_soyDependencies.clear();

		soyDependency(soyDependencies);
	}

	public void setSoyDependencies(Object... soyDependencies) {
		setSoyDependencies(Arrays.asList(soyDependencies));
	}

	public void setSoySkipMetalGeneration(boolean soySkipMetalGeneration) {
		_soySkipMetalGeneration = soySkipMetalGeneration;
	}

	public void setSoySrcIncludes(Iterable<?> soySrcIncludes) {
		_soySrcIncludes.clear();

		soySrcInclude(soySrcIncludes);
	}

	public void setSoySrcIncludes(Object... soySrcIncludes) {
		setSoySrcIncludes(Arrays.asList(soySrcIncludes));
	}

	public void setSrcIncludes(Iterable<?> srcIncludes) {
		_srcIncludes.clear();

		srcInclude(srcIncludes);
	}

	public void setSrcIncludes(Object... srcIncludes) {
		setSrcIncludes(Arrays.asList(srcIncludes));
	}

	public TranspileJSTask soyDependency(Iterable<?> soyDependencies) {
		GUtil.addToCollection(_soyDependencies, soyDependencies);

		return this;
	}

	public TranspileJSTask soyDependency(Object... soyDependencies) {
		return soyDependency(Arrays.asList(soyDependencies));
	}

	public TranspileJSTask soySrcInclude(Iterable<?> soySrcIncludes) {
		GUtil.addToCollection(_soySrcIncludes, soySrcIncludes);

		return this;
	}

	public TranspileJSTask soySrcInclude(Object... soySrcIncludes) {
		return soySrcInclude(Arrays.asList(soySrcIncludes));
	}

	public TranspileJSTask srcInclude(Iterable<?> srcIncludes) {
		GUtil.addToCollection(_srcIncludes, srcIncludes);

		return this;
	}

	public TranspileJSTask srcInclude(Object... srcIncludes) {
		return srcInclude(Arrays.asList(srcIncludes));
	}

	public static enum SourceMaps {

		DISABLED, ENABLED, ENABLED_INLINE

	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		String destination = FileUtil.getAbsolutePath(getWorkingDir());

		completeArgs.add("build");

		completeArgs.add("--bundleFileName");
		completeArgs.add(getBundleFileName());

		completeArgs.add("--dest");
		completeArgs.add(destination);

		completeArgs.add("--format");
		completeArgs.add(getModules());

		completeArgs.add("--globalName");
		completeArgs.add(getGlobalName());

		String logLevel = "silent";

		Logger logger = getLogger();

		if (logger.isInfoEnabled()) {
			logLevel = "warn";
		}

		completeArgs.add("--logLevel");
		completeArgs.add(logLevel);

		completeArgs.add("--moduleName");
		completeArgs.add(getModuleName());

		SourceMaps sourceMaps = getSourceMaps();

		if (sourceMaps != SourceMaps.ENABLED) {
			completeArgs.add("--source-maps");

			if (sourceMaps == SourceMaps.ENABLED_INLINE) {
				completeArgs.add("inline");
			}
			else {
				completeArgs.add("false");
			}
		}

		List<String> soyDependencies = getSoyDependencies();

		if (!soyDependencies.isEmpty()) {
			completeArgs.add("--soyDeps");
			completeArgs.addAll(soyDependencies);
		}

		completeArgs.add("--soyDest");
		completeArgs.add(destination);

		completeArgs.add("--soySrc");
		completeArgs.addAll(getSoySrcIncludes());

		if (isSoySkipMetalGeneration()) {
			completeArgs.add("--soySkipMetalGeneration");
		}

		completeArgs.add("--src");
		completeArgs.addAll(getSrcIncludes());

		return completeArgs;
	}

	private Object _bundleFileName = "";
	private Object _globalName = "";
	private Object _moduleName = "";
	private Object _modules = "amd";
	private boolean _skipWhenEmpty = true;
	private Object _sourceDir;
	private SourceMaps _sourceMaps = SourceMaps.ENABLED;
	private final Set<Object> _soyDependencies = new LinkedHashSet<>();
	private boolean _soySkipMetalGeneration;
	private final Set<Object> _soySrcIncludes = new LinkedHashSet<>();
	private final Set<Object> _srcIncludes = new LinkedHashSet<>();

}