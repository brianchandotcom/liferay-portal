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

package com.liferay.gradle.plugins.jsdoc;

import com.liferay.gradle.plugins.node.tasks.ExecuteNodeScriptTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.resources.TextResource;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class JSDocTask extends ExecuteNodeScriptTask {

	public JSDocTask() {
		Project project = getProject();

		File packageJsonFile = project.file("package.json");

		if (packageJsonFile.exists()) {
			setPackageJsonFile(packageJsonFile);
		}
	}

	@Nested
	@Optional
	public TextResource getConfiguration() {
		return _configurationTextResource;
	}

	@OutputDirectory
	public File getDestinationDir() {
		return GradleUtil.toFile(getProject(), _destinationDir);
	}

	@InputFile
	@Optional
	public File getPackageJsonFile() {
		return GradleUtil.toFile(getProject(), _packageJsonFile);
	}

	@InputFile
	@Optional
	public File getReadmeFile() {
		return GradleUtil.toFile(getProject(), _readmeFile);
	}

	@InputFiles
	public FileCollection getSourceDirs() {
		Project project = getProject();

		return project.files(_sourceDirs);
	}

	@InputDirectory
	@Optional
	public File getTutorialsDir() {
		return GradleUtil.toFile(getProject(), _tutorialsDir);
	}

	public void setConfiguration(TextResource configurationTextResource) {
		_configurationTextResource = configurationTextResource;
	}

	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setPackageJsonFile(Object packageJsonFile) {
		_packageJsonFile = packageJsonFile;
	}

	public void setReadmeFile(Object readmeFile) {
		_readmeFile = readmeFile;
	}

	public void setSourceDirs(Iterable<Object> sourceDirs) {
		_sourceDirs.clear();

		sourceDirs(sourceDirs);
	}

	public void setSourceDirs(Object... sourceDirs) {
		setSourceDirs(Arrays.asList(sourceDirs));
	}

	public void setTutorialsDir(Object tutorialsDir) {
		_tutorialsDir = tutorialsDir;
	}

	public JSDocTask sourceDirs(Iterable<Object> sourceDirs) {
		GUtil.addToCollection(_sourceDirs, sourceDirs);

		return this;
	}

	public JSDocTask sourceDirs(Object... sourceDirs) {
		return sourceDirs(Arrays.asList(sourceDirs));
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		Logger logger = getLogger();

		for (File sourceDir : getSourceDirs()) {
			completeArgs.add(_relativize(sourceDir));
		}

		TextResource configurationTextResource = getConfiguration();

		if (configurationTextResource != null) {
			completeArgs.add("--configure");
			completeArgs.add(
				_relativize(
					configurationTextResource.asFile(
						StandardCharsets.UTF_8.name())));
		}

		if (logger.isDebugEnabled()) {
			completeArgs.add("--debug");
		}

		_addArg(completeArgs, "--destination", getDestinationDir());
		_addArg(completeArgs, "--package", getPackageJsonFile());

		completeArgs.add("--recurse");

		_addArg(completeArgs, "--readme", getReadmeFile());
		_addArg(completeArgs, "--tutorials", getTutorialsDir());

		if (logger.isInfoEnabled()) {
			completeArgs.add("--verbose");
		}

		return completeArgs;
	}

	private void _addArg(List<String> args, String name, File file) {
		if (file == null) {
			return;
		}

		args.add(name);
		args.add(_relativize(file));
	}

	private String _relativize(File file) {
		String relativePath = FileUtil.relativize(file, getWorkingDir());

		if (File.separatorChar != '/') {
			relativePath = relativePath.replace(File.separatorChar, '/');
		}

		return relativePath;
	}

	private TextResource _configurationTextResource;
	private Object _destinationDir;
	private Object _packageJsonFile;
	private Object _readmeFile;
	private final Set<Object> _sourceDirs = new LinkedHashSet<>();
	private Object _tutorialsDir;

}