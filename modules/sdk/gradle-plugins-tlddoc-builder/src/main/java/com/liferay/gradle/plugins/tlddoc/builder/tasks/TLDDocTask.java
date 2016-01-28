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

package com.liferay.gradle.plugins.tlddoc.builder.tasks;

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
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.util.PatternFilterable;
import org.gradle.api.tasks.util.PatternSet;

/**
 * @author Andrea Di Giorgi
 */
public class TLDDocTask extends JavaExec implements PatternFilterable {

	public TLDDocTask() {
		setMain("com.sun.tlddoc.TLDDoc");
		setMaxHeapSize("256m");
	}

	@Override
	public TLDDocTask exclude(
		@SuppressWarnings("rawtypes") Closure excludeSpec) {

		_patternFilterable.exclude(excludeSpec);

		return this;
	}

	@Override
	public TLDDocTask exclude(Iterable<String> excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	@Override
	public TLDDocTask exclude(Spec<FileTreeElement> excludeSpec) {
		_patternFilterable.exclude(excludeSpec);

		return this;
	}

	@Override
	public TLDDocTask exclude(String ... excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	@Override
	public void exec() {
		setArgs(getCompleteArgs());

		super.exec();
	}

	@OutputDirectory
	public File getDestinationDir() {
		return GradleUtil.toFile(getProject(), _destinationDir);
	}

	@Override
	public Set<String> getExcludes() {
		return _patternFilterable.getExcludes();
	}

	@Override
	public Set<String> getIncludes() {
		return _patternFilterable.getIncludes();
	}

	@InputFiles
	@SkipWhenEmpty
	public FileTree getSource() {
		Project project = getProject();

		FileCollection fileCollection = project.files(_source);

		FileTree fileTree = fileCollection.getAsFileTree();

		return fileTree.matching(_patternFilterable);
	}

	@Override
	public TLDDocTask include(
		@SuppressWarnings("rawtypes") Closure includeSpec) {

		_patternFilterable.include(includeSpec);

		return this;
	}

	@Override
	public TLDDocTask include(Iterable<String> includes) {
		_patternFilterable.include(includes);

		return this;
	}

	@Override
	public TLDDocTask include(Spec<FileTreeElement> includeSpec) {
		_patternFilterable.include(includeSpec);

		return this;
	}

	@Override
	public TLDDocTask include(String ... includes) {
		_patternFilterable.include(includes);

		return this;
	}

	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	@Override
	public TLDDocTask setExcludes(Iterable<String> excludes) {
		_patternFilterable.setExcludes(excludes);

		return this;
	}

	@Override
	public TLDDocTask setIncludes(Iterable<String> includes) {
		_patternFilterable.setIncludes(includes);

		return this;
	}

	public void setSource(Object source) {
		_source.clear();

		_source.add(source);
	}

	public TLDDocTask source(Object ... sources) {
		for (Object source : sources) {
			_source.add(source);
		}

		return this;
	}

	protected List<String> getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add("-d");
		args.add(FileUtil.relativize(getDestinationDir(), getWorkingDir()));

		for (File file : getSource()) {
			args.add(FileUtil.relativize(file, getWorkingDir()));
		}

		return args;
	}

	private Object _destinationDir;
	private final PatternFilterable _patternFilterable = new PatternSet();
	private final List<Object> _source = new ArrayList<>();

}