/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.js.transpiler;

import com.liferay.gradle.plugins.node.task.ExecuteNodeScriptTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GUtil;
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
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@CacheableTask
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

	@InputDirectory
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getSourceDir() {
		return GradleUtil.toFile(getProject(), _sourceDir);
	}

	@InputFiles
	@PathSensitive(PathSensitivity.RELATIVE)
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

	@Input
	public List<String> getSoySrcIncludes() {
		return GradleUtil.toStringList(_soySrcIncludes);
	}

	@Input
	public List<String> getSrcIncludes() {
		return GradleUtil.toStringList(_srcIncludes);
	}

	@OutputDirectory
	@Override
	public File getWorkingDir() {
		return super.getWorkingDir();
	}

	@Input
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

	@Internal
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