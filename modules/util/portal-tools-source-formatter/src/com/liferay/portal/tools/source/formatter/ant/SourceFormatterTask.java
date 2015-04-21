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

package com.liferay.portal.tools.source.formatter.ant;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.source.formatter.SourceFormatter;
import com.liferay.portal.tools.source.formatter.SourceFormatterBean;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
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
public class SourceFormatterTask extends Task {

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
				_sourceFormatterBean);

			sourceFormatter.format();

			List<String> processedFiles = sourceFormatter.getProcessedFiles();

			Project project = getProject();

			project.addIdReference(
				SourceFormatter.PROCESSED_FILES_ATTRIBUTE, processedFiles);
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	public void setAutoFix(boolean autoFix) {
		_sourceFormatterBean.setAutoFix(autoFix);
	}

	public void setBaseDir(String baseDir) {
		_sourceFormatterBean.setBaseDir(baseDir);
	}

	public void setCopyright(String copyright) {
		_sourceFormatterBean.setCopyright(copyright);
	}

	public void setFileNames(String fileNames) {
		_sourceFormatterBean.setFileNames(
			Arrays.asList(StringUtil.split(fileNames)));
	}

	public void setPrintErrors(boolean printErrors) {
		_sourceFormatterBean.setPrintErrors(printErrors);
	}

	public void setThrowException(boolean throwException) {
		_sourceFormatterBean.setThrowException(throwException);
	}

	public void setUseProperties(boolean useProperties) {
		_sourceFormatterBean.setUseProperties(useProperties);
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

			fileNames.addAll(Arrays.asList(includedFiles));
		}

		_sourceFormatterBean.setFileNames(fileNames);
	}

	private final Set<FileSet> _fileSets = new HashSet<>();
	private final SourceFormatterBean _sourceFormatterBean =
		new SourceFormatterBean();

}