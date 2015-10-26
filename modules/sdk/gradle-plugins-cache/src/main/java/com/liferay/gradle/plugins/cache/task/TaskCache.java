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

package com.liferay.gradle.plugins.cache.task;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.util.PatternFilterable;
import org.gradle.api.tasks.util.PatternSet;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class TaskCache implements PatternFilterable {

	public TaskCache(String name, Project project) {
		_baseDir = project.getProjectDir();
		_cacheDir = project.file(".cache/" + name);
		_name = name;
		_project = project;
	}

	@Override
	public TaskCache exclude(@SuppressWarnings("rawtypes") Closure closure) {
		_patternFilterable.exclude(closure);

		return this;
	}

	@Override
	public TaskCache exclude(Iterable<String> excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	@Override
	public TaskCache exclude(Spec<FileTreeElement> spec) {
		_patternFilterable.exclude(spec);

		return this;
	}

	@Override
	public TaskCache exclude(String ... excludes) {
		_patternFilterable.exclude(excludes);

		return this;
	}

	public File getBaseDir() {
		return GradleUtil.toFile(_project, _baseDir);
	}

	public File getCacheDir() {
		return GradleUtil.toFile(_project, _cacheDir);
	}

	@Override
	public Set<String> getExcludes() {
		return _patternFilterable.getExcludes();
	}

	public FileTree getFiles() {
		FileTree fileTree = _project.fileTree(getBaseDir());

		return fileTree.matching(_patternFilterable);
	}

	@Override
	public Set<String> getIncludes() {
		return _patternFilterable.getIncludes();
	}

	public String getName() {
		return _name;
	}

	public Project getProject() {
		return _project;
	}

	public Set<Task> getSkippedTaskDependencies() {
		Set<Task> skippedTaskDependencies = new HashSet<>();

		for (Object skippedDependencyTask : _skippedTaskDependencies) {
			skippedDependencyTask = GradleUtil.toObject(skippedDependencyTask);

			if (skippedDependencyTask instanceof Task) {
				skippedTaskDependencies.add((Task)skippedDependencyTask);
			}
			else {
				String taskName = GradleUtil.toString(skippedDependencyTask);

				Task task = GradleUtil.getTask(_project, taskName);

				skippedTaskDependencies.add(task);
			}
		}

		return skippedTaskDependencies;
	}

	public Task getTask() {
		return GradleUtil.getTask(_project, _name);
	}

	public FileCollection getTestFiles() {
		return _project.files(_testFiles);
	}

	@Override
	public TaskCache include(@SuppressWarnings("rawtypes") Closure closure) {
		_patternFilterable.include(closure);

		return this;
	}

	@Override
	public TaskCache include(Iterable<String> includes) {
		_patternFilterable.include(includes);

		return this;
	}

	@Override
	public TaskCache include(Spec<FileTreeElement> spec) {
		_patternFilterable.include(spec);

		return this;
	}

	@Override
	public TaskCache include(String ... includes) {
		_patternFilterable.include(includes);

		return this;
	}

	public boolean isFailIfOutOfDate() {
		return _failIfOutOfDate;
	}

	public boolean isUpToDate() {
		FileCollection testFiles = getTestFiles();

		if (testFiles.isEmpty()) {
			throw new GradleException("At least one test file is required");
		}

		File cacheDir = getCacheDir();

		for (File testFile : testFiles) {
			if (!FileUtil.isUpToDate(_project, testFile, cacheDir)) {
				return false;
			}
		}

		return true;
	}

	public void setBaseDir(Object baseDir) {
		_baseDir = baseDir;
	}

	public void setCacheDir(Object cacheDir) {
		_cacheDir = cacheDir;
	}

	@Override
	public TaskCache setExcludes(Iterable<String> excludes) {
		_patternFilterable.setExcludes(excludes);

		return this;
	}

	public void setFailIfOutOfDate(boolean failIfOutOfDate) {
		_failIfOutOfDate = failIfOutOfDate;
	}

	@Override
	public TaskCache setIncludes(Iterable<String> includes) {
		_patternFilterable.setIncludes(includes);

		return this;
	}

	public void setSkippedTaskDependencies(
		Iterable<Object> skippedTaskDependencies) {

		_skippedTaskDependencies.clear();

		skipTaskDependency(skippedTaskDependencies);
	}

	public void setSkippedTaskDependencies(Object ... skippedTaskDependencies) {
		setSkippedTaskDependencies(Arrays.asList(skippedTaskDependencies));
	}

	public void setTestFiles(Iterable<Object> testFiles) {
		_testFiles.clear();

		testFile(testFiles);
	}

	public void setTestFiles(Object ... testFiles) {
		setTestFiles(Arrays.asList(testFiles));
	}

	public TaskCache skipTaskDependency(
		Iterable<Object> skippedTaskDependencies) {

		GUtil.addToCollection(
			_skippedTaskDependencies, skippedTaskDependencies);

		return this;
	}

	public TaskCache skipTaskDependency(Object ... skippedTaskDependencies) {
		return skipTaskDependency(Arrays.asList(skippedTaskDependencies));
	}

	public TaskCache testFile(Iterable<Object> testFiles) {
		GUtil.addToCollection(_testFiles, testFiles);

		return this;
	}

	public TaskCache testFile(Object ... testFiles) {
		return testFile(Arrays.asList(testFiles));
	}

	private Object _baseDir;
	private Object _cacheDir;
	private boolean _failIfOutOfDate;
	private final String _name;
	private final PatternFilterable _patternFilterable = new PatternSet();
	private final Project _project;
	private final List<Object> _skippedTaskDependencies = new ArrayList<>();
	private final List<Object> _testFiles = new ArrayList<>();

}