/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.tlddoc.builder.task;

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
import org.gradle.api.provider.Property;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.util.PatternFilterable;
import org.gradle.api.tasks.util.PatternSet;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class TLDDocTask extends JavaExec implements PatternFilterable {

	public TLDDocTask() {
		Property<String> mainClass = getMainClass();

		mainClass.set("com.sun.tlddoc.TLDDoc");

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
	public TLDDocTask exclude(String... excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	@Override
	public void exec() {
		setArgs(_getCompleteArgs());

		super.exec();
	}

	@OutputDirectory
	public File getDestinationDir() {
		return GradleUtil.toFile(getProject(), _destinationDir);
	}

	@Input
	@Override
	public Set<String> getExcludes() {
		return _patternFilterable.getExcludes();
	}

	@Input
	@Override
	public Set<String> getIncludes() {
		return _patternFilterable.getIncludes();
	}

	@InputFiles
	@PathSensitive(PathSensitivity.RELATIVE)
	@SkipWhenEmpty
	public FileTree getSource() {
		Project project = getProject();

		FileCollection fileCollection = project.files(_source);

		FileTree fileTree = fileCollection.getAsFileTree();

		return fileTree.matching(_patternFilterable);
	}

	@InputDirectory
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getXsltDir() {
		return GradleUtil.toFile(getProject(), _xsltDir);
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
	public TLDDocTask include(String... includes) {
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

	public void setXsltDir(Object xsltDir) {
		_xsltDir = xsltDir;
	}

	public TLDDocTask source(Object... sources) {
		for (Object source : sources) {
			_source.add(source);
		}

		return this;
	}

	private List<String> _getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add("-d");
		args.add(FileUtil.relativize(getDestinationDir(), getWorkingDir()));

		File xsltDir = getXsltDir();

		if (xsltDir != null) {
			args.add("-xslt");
			args.add(FileUtil.relativize(xsltDir, getWorkingDir()));
		}

		for (File file : getSource()) {
			args.add(FileUtil.relativize(file, getWorkingDir()));
		}

		return args;
	}

	private Object _destinationDir;
	private final PatternFilterable _patternFilterable = new PatternSet();
	private final List<Object> _source = new ArrayList<>();
	private Object _xsltDir;

}