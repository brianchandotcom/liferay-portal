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

package com.liferay.gradle.plugins.node.tasks;

import com.liferay.gradle.plugins.node.internal.util.FileUtil;
import com.liferay.gradle.plugins.node.internal.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.List;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.util.PatternFilterable;
import org.gradle.api.tasks.util.PatternSet;

/**
 * @author David Truong
 * @author Peter Shin
 */
public class NpmRunTask extends ExecuteNpmTask implements PatternFilterable {

	public NpmRunTask() {
		exclude(_EXCLUDE_DIR_NAMES);
		include(_INCLUDES);

		Project project = getProject();

		setSourceDir(project.getProjectDir());
	}

	@Override
	public NpmRunTask exclude(
		@SuppressWarnings("rawtypes") Closure excludeSpec) {

		_patternFilterable.exclude(excludeSpec);

		return this;
	}

	@Override
	public NpmRunTask exclude(Iterable<String> excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	@Override
	public NpmRunTask exclude(Spec<FileTreeElement> excludeSpec) {
		_patternFilterable.exclude(excludeSpec);

		return this;
	}

	@Override
	public NpmRunTask exclude(String... excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	@Override
	public void executeNode() throws Exception {
		String digest = FileUtil.getDigest(getSourceFiles());

		super.executeNode();

		_writeSourceDigestFile(digest.getBytes(StandardCharsets.UTF_8));
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
	@Optional
	public String getNodeVersion() {
		return GradleUtil.toString(_nodeVersion);
	}

	@Input
	@Optional
	public String getNpmVersion() {
		return GradleUtil.toString(_npmVersion);
	}

	@Input
	public String getScriptName() {
		return GradleUtil.toString(_scriptName);
	}

	@OutputFile
	public File getSourceDigestFile() {
		Project project = getProject();

		String pathname = "npm/script/" + getName() + "/.digest";

		return new File(project.getBuildDir(), pathname);
	}

	@Input
	@Optional
	public File getSourceDir() {
		return GradleUtil.toFile(getProject(), _sourceDir);
	}

	@InputFiles
	@Optional
	public FileCollection getSourceFiles() {
		File sourceDir = getSourceDir();

		if ((sourceDir == null) || !sourceDir.exists()) {
			return null;
		}

		Project project = getProject();

		FileTree fileTree = project.fileTree(sourceDir);

		FileCollection fileCollection = fileTree.matching(_patternFilterable);

		if (fileCollection.isEmpty()) {
			return null;
		}

		return fileCollection;
	}

	@Override
	public NpmRunTask include(
		@SuppressWarnings("rawtypes") Closure includeSpec) {

		_patternFilterable.include(includeSpec);

		return this;
	}

	@Override
	public NpmRunTask include(Iterable<String> includes) {
		_patternFilterable.include(includes);

		return this;
	}

	@Override
	public NpmRunTask include(Spec<FileTreeElement> includeSpec) {
		_patternFilterable.include(includeSpec);

		return this;
	}

	@Override
	public NpmRunTask include(String... includes) {
		_patternFilterable.include(includes);

		return this;
	}

	@Override
	public NpmRunTask setExcludes(Iterable<String> excludes) {
		_patternFilterable.setExcludes(excludes);

		return this;
	}

	@Override
	public NpmRunTask setIncludes(Iterable<String> includes) {
		_patternFilterable.setIncludes(includes);

		return this;
	}

	public void setNodeVersion(Object nodeVersion) {
		_nodeVersion = nodeVersion;
	}

	public void setNpmVersion(Object npmVersion) {
		_npmVersion = npmVersion;
	}

	public void setScriptName(Object scriptName) {
		_scriptName = scriptName;
	}

	public void setSourceDir(Object sourceDir) {
		_sourceDir = sourceDir;
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add("run-script");
		completeArgs.add(getScriptName());

		return completeArgs;
	}

	private void _writeSourceDigestFile(byte[] bytes) throws Exception {
		File file = getSourceDigestFile();

		File dir = file.getParentFile();

		if (dir != null) {
			Files.createDirectories(dir.toPath());
		}

		Files.write(file.toPath(), bytes);
	}

	private static final String[] _EXCLUDE_DIR_NAMES = {
		"bin", "build", "classes", "node_modules", "test-classes", "tmp"
	};

	private static final String[] _INCLUDES = {
		"**/*.*rc", "**/*.css", "**/*.js", "**/*.json", "**/*.jsx"
	};

	private Object _nodeVersion;
	private Object _npmVersion;
	private final PatternFilterable _patternFilterable = new PatternSet();
	private Object _scriptName;
	private Object _sourceDir;

}