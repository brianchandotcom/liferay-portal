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

package com.liferay.gradle.util.tasks;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class ConcatenateTask extends DefaultTask {

	@TaskAction
	public void concatenate() throws Exception {
		FileUtil.concatenate(getDestinationFile(), getSourceFiles());
	}

	@OutputFile
	public File getDestinationFile() {
		return GradleUtil.toFile(getProject(), _destinationFile);
	}

	@InputFiles
	public FileCollection getSourceFiles() {
		Project project = getProject();

		return project.files(_sourceFiles);
	}

	public void setDestinationFile(Object destinationFile) {
		_destinationFile = destinationFile;
	}

	public void setSourceFiles(Iterable<Object> sourceFiles) {
		_sourceFiles.clear();

		sourceFiles(sourceFiles);
	}

	public ConcatenateTask sourceFiles(Iterable<Object> ... sourceFiles) {
		GUtil.addToCollection(_sourceFiles, sourceFiles);

		return this;
	}

	public ConcatenateTask sourceFiles(Object ... sourceFiles) {
		return sourceFiles(Arrays.asList(sourceFiles));
	}

	private Object _destinationFile;
	private final List<Object> _sourceFiles = new ArrayList<>();

}