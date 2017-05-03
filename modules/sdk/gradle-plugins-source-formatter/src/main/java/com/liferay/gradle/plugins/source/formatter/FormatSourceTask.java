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

package com.liferay.gradle.plugins.source.formatter;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.source.formatter.SourceFormatterArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.JavaExec;
import org.gradle.util.CollectionUtils;

/**
 * @author Raymond Augé
 * @author Andrea Di Giorgi
 */
public class FormatSourceTask extends JavaExec {

	public FormatSourceTask() {
		setMain("com.liferay.source.formatter.SourceFormatter");
	}

	@Override
	public void exec() {
		setArgs(_getCompleteArgs());

		super.exec();
	}

	public File getBaseDir() {
		return GradleUtil.toFile(
			getProject(), _sourceFormatterArgs.getBaseDirName());
	}

	public String getBaseDirName() {
		return _sourceFormatterArgs.getBaseDirName();
	}

	public List<String> getFileNames() {
		return _sourceFormatterArgs.getFileNames();
	}

	public FileCollection getFiles() {
		Project project = getProject();

		List<String> fileNames = _sourceFormatterArgs.getFileNames();

		if (fileNames == null) {
			fileNames = Collections.emptyList();
		}

		return project.files(fileNames);
	}

	public String getGitWorkingBranchName() {
		return _sourceFormatterArgs.getGitWorkingBranchName();
	}

	public int getMaxLineLength() {
		return _sourceFormatterArgs.getMaxLineLength();
	}

	public int getProcessorThreadCount() {
		return _sourceFormatterArgs.getProcessorThreadCount();
	}

	public boolean isAutoFix() {
		return _sourceFormatterArgs.isAutoFix();
	}

	public boolean isFormatCurrentBranch() {
		return _sourceFormatterArgs.isFormatCurrentBranch();
	}

	public boolean isFormatLatestAuthor() {
		return _sourceFormatterArgs.isFormatLatestAuthor();
	}

	public boolean isFormatLocalChanges() {
		return _sourceFormatterArgs.isFormatLocalChanges();
	}

	public boolean isIncludeSubrepositories() {
		return _sourceFormatterArgs.isIncludeSubrepositories();
	}

	public boolean isPrintErrors() {
		return _sourceFormatterArgs.isPrintErrors();
	}

	public boolean isShowDocumentation() {
		return _sourceFormatterArgs.isShowDocumentation();
	}

	public boolean isThrowException() {
		return _sourceFormatterArgs.isThrowException();
	}

	public void setAutoFix(boolean autoFix) {
		_sourceFormatterArgs.setAutoFix(autoFix);
	}

	public void setBaseDirName(String baseDirName) {
		_sourceFormatterArgs.setBaseDirName(baseDirName);
	}

	public void setFileNames(Iterable<String> fileNames) {
		_sourceFormatterArgs.setFileNames(
			CollectionUtils.toStringList(fileNames));
	}

	public void setFileNames(String... fileNames) {
		setFileNames(Arrays.asList(fileNames));
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

	private List<String> _getCompleteArgs() {
		List<String> args = new ArrayList<>(getArgs());

		args.add("format.current.branch=" + isFormatCurrentBranch());
		args.add("format.latest.author=" + isFormatLatestAuthor());
		args.add("format.local.changes=" + isFormatLocalChanges());
		args.add("git.working.branch.name=" + getGitWorkingBranchName());
		args.add("include.subrepositories=" + isIncludeSubrepositories());
		args.add("max.line.length=" + getMaxLineLength());
		args.add("processor.thread.count=" + getProcessorThreadCount());
		args.add("show.documentation=" + isShowDocumentation());
		args.add("source.auto.fix=" + isAutoFix());
		args.add("source.print.errors=" + isPrintErrors());
		args.add("source.throw.exception=" + isThrowException());

		FileCollection fileCollection = getFiles();

		if (fileCollection.isEmpty()) {
			args.add(
				"source.base.dir=" +
					FileUtil.relativize(getBaseDir(), getWorkingDir()));
		}
		else {
			args.add("source.files=" + _merge(fileCollection, getWorkingDir()));
		}

		return args;
	}

	private String _merge(Iterable<File> files, File startFile) {
		StringBuilder sb = new StringBuilder();

		int i = 0;

		for (File file : files) {
			if (i > 0) {
				sb.append(',');
			}

			sb.append(FileUtil.relativize(file, startFile));

			i++;
		}

		return sb.toString();
	}

	private final SourceFormatterArgs _sourceFormatterArgs =
		new SourceFormatterArgs();

}