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

package com.liferay.source.formatter.ant;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.SourceFormatter;
import com.liferay.source.formatter.SourceFormatterArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

/**
 * @author Raymond Augé
 */
public class FormatSourceTask extends Task {

	public void addFileset(FileSet fileSet) {
		_fileSets.add(fileSet);
	}

	@Override
	public void execute() throws BuildException {
		if (!_fileSets.isEmpty()) {
			_collectFromFileSets();
		}

		try {
			SourceFormatter sourceFormatter = new SourceFormatter(
				_sourceFormatterArgs);

			sourceFormatter.format();

			List<String> modifiedFileNames =
				sourceFormatter.getModifiedFileNames();

			Project project = getProject();

			project.addIdReference(
				SourceFormatterArgs.OUTPUT_KEY_MODIFIED_FILES,
				modifiedFileNames);
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	public void setAutoFix(boolean autoFix) {
		_sourceFormatterArgs.setAutoFix(autoFix);
	}

	public void setBaseDir(String baseDir) {
		_sourceFormatterArgs.setBaseDirName(baseDir);
	}

	public void setFileNames(String fileNames) {
		_sourceFormatterArgs.setFileNames(
			Arrays.asList(StringUtil.split(fileNames)));
	}

	public void setFormatCurrentBranch(boolean formatCurrentBranch) {
		_sourceFormatterArgs.setFormatCurrentBranch(formatCurrentBranch);
	}

	public void setFormatLatestAuthor(boolean formatLatestAuthor) {
		_sourceFormatterArgs.setFormatLatestAuthor(formatLatestAuthor);
	}

	public void setFormatLocalChanges(boolean formatLocalChanges) {
		_sourceFormatterArgs.setFormatLocalChanges(formatLocalChanges);
	}

	public void setGitWorkingBranchName(String gitWorkingBranchName) {
		_sourceFormatterArgs.setGitWorkingBranchName(gitWorkingBranchName);
	}

	public void setIncludeSubrepositories(boolean includeSubrepositories) {
		_sourceFormatterArgs.setIncludeSubrepositories(includeSubrepositories);
	}

	public void setMaxLineLength(int maxLineLength) {
		_sourceFormatterArgs.setMaxLineLength(maxLineLength);
	}

	public void setPrintErrors(boolean printErrors) {
		_sourceFormatterArgs.setPrintErrors(printErrors);
	}

	public void setProcessorThreadCount(int processorThreadCount) {
		_sourceFormatterArgs.setProcessorThreadCount(processorThreadCount);
	}

	public void setShowDocumentation(boolean showDocumentation) {
		_sourceFormatterArgs.setShowDocumentation(showDocumentation);
	}

	public void setThrowException(boolean throwException) {
		_sourceFormatterArgs.setThrowException(throwException);
	}

	private void _collectFromFileSets() {
		List<String> fileNames = new ArrayList<>();

		for (FileSet fileSet : _fileSets) {
			DirectoryScanner directoryScanner = fileSet.getDirectoryScanner(
				getProject());

			File baseDir = directoryScanner.getBasedir();

			String[] includedFiles = directoryScanner.getIncludedFiles();

			for (int i = 0; i < includedFiles.length; i++) {
				File file = new File(baseDir, includedFiles[i]);

				includedFiles[i] = file.getAbsolutePath();
			}

			Collections.addAll(fileNames, includedFiles);
		}

		_sourceFormatterArgs.setFileNames(fileNames);
	}

	private final Set<FileSet> _fileSets = new HashSet<>();
	private final SourceFormatterArgs _sourceFormatterArgs =
		new SourceFormatterArgs();

}