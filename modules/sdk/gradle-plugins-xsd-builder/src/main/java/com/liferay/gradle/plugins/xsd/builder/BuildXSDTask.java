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

package com.liferay.gradle.plugins.xsd.builder;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.Zip;

/**
 * @author Andrea Di Giorgi
 */
public class BuildXSDTask extends Zip {

	public BuildXSDTask() {
		setAppendix("xbean");
		setExtension(Jar.DEFAULT_EXTENSION);
	}

	@Override
	public File getDestinationDir() {
		if (_destinationDir != null) {
			return GradleUtil.toFile(getProject(), _destinationDir);
		}

		return super.getDestinationDir();
	}

	@InputDirectory
	public File getInputDir() {
		return GradleUtil.toFile(getProject(), _inputDir);
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getInputFiles() {
		Project project = getProject();

		Map<String, Object> args = new HashMap<>();

		args.put("dir", getInputDir());
		args.put("include", "**/*.*");

		return project.fileTree(args);
	}

	public void setInputDir(Object inputDir) {
		_inputDir = inputDir;
	}

	protected void setDestinationDir(Callable<File> callable) {
		_destinationDir = callable;
	}

	private Callable<File> _destinationDir;
	private Object _inputDir;

}