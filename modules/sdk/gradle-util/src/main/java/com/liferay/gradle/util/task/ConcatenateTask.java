/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.util.task;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
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
	@PathSensitive(PathSensitivity.RELATIVE)
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

	public ConcatenateTask sourceFiles(Iterable<Object>... sourceFiles) {
		GUtil.addToCollection(_sourceFiles, sourceFiles);

		return this;
	}

	public ConcatenateTask sourceFiles(Object... sourceFiles) {
		return sourceFiles(Arrays.asList(sourceFiles));
	}

	private Object _destinationFile;
	private final List<Object> _sourceFiles = new ArrayList<>();

}