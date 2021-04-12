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

import com.liferay.gradle.util.GradleUtil;
import com.liferay.source.formatter.SourceFormatterArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.JavaExec;
import org.gradle.util.CollectionUtils;

/**
 * @author Raymond Augé
 * @author Andrea Di Giorgi
 */
@CacheableTask
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

	public List<String> getCheckCategoryNames() {
		return _sourceFormatterArgs.getCheckCategoryNames();
	}

	public List<String> getCheckNames() {
		return _sourceFormatterArgs.getCheckNames();
	}

	public List<String> getFileExtensions() {
		return _sourceFormatterArgs.getFileExtensions();
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

	public boolean isFailOnAutoFix() {
		return _sourceFormatterArgs.isFailOnAutoFix();
	}

	public boolean isFailOnHasWarning() {
		return _sourceFormatterArgs.isFailOnHasWarning();
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

	public boolean isShowDebugInformation() {
		return _sourceFormatterArgs.isShowDebugInformation();
	}

	public boolean isShowDocumentation() {
		return _sourceFormatterArgs.isShowDocumentation();
	}

	public boolean isShowStatusUpdates() {
		return _sourceFormatterArgs.isShowStatusUpdates();
	}

	public boolean isValidateCommitMessages() {
		return _sourceFormatterArgs.isValidateCommitMessages();
	}

	public void setAutoFix(boolean autoFix) {
		_sourceFormatterArgs.setAutoFix(autoFix);
	}

	public void setBaseDirName(String baseDirName) {
		_sourceFormatterArgs.setBaseDirName(baseDirName);
	}

	public void setCheckCategoryNames(Iterable<String> checkCategoryNames) {
		_sourceFormatterArgs.setCheckCategoryNames(
			CollectionUtils.toList(checkCategoryNames));
	}

	public void setCheckCategoryNames(String... checkCategoryNames) {
		_sourceFormatterArgs.setCheckCategoryNames(
			CollectionUtils.toList(checkCategoryNames));
	}

	public void setCheckNames(Iterable<String> checkNames) {
		_sourceFormatterArgs.setCheckNames(CollectionUtils.toList(checkNames));
	}

	public void setCheckNames(String... checkNames) {
		_sourceFormatterArgs.setCheckNames(CollectionUtils.toList(checkNames));
	}

	public void setFailOnAutoFix(boolean failOnAutoFix) {
		_sourceFormatterArgs.setFailOnAutoFix(failOnAutoFix);
	}

	public void setFailOnHasWarning(boolean failOnHasWarning) {
		_sourceFormatterArgs.setFailOnHasWarning(failOnHasWarning);
	}

	public void setFileExtensions(Iterable<String> fileExtensions) {
		_sourceFormatterArgs.setFileExtensions(
			CollectionUtils.toList(fileExtensions));
	}

	public void setFileExtensions(String... fileExtensions) {
		_sourceFormatterArgs.setFileExtensions(
			CollectionUtils.toList(fileExtensions));
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

	public void setShowDebugInformation(boolean showDebugInformation) {
		_sourceFormatterArgs.setShowDebugInformation(showDebugInformation);
	}

	public void setShowDocumentation(boolean showDocumentation) {
		_sourceFormatterArgs.setShowDocumentation(showDocumentation);
	}

	public void setShowStatusUpdates(boolean showStatusUpdates) {
		_sourceFormatterArgs.setShowStatusUpdates(showStatusUpdates);
	}

	public void setValidateCommitMessages(boolean validateCommitMessages) {
		_sourceFormatterArgs.setValidateCommitMessages(validateCommitMessages);
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
		args.add("show.debug.information=" + isShowDebugInformation());
		args.add("show.documentation=" + isShowDocumentation());
		args.add("show.status.updates=" + isShowStatusUpdates());
		args.add("source.auto.fix=" + isAutoFix());
		args.add(
			"source.check.category.names=" +
				CollectionUtils.join(",", getCheckCategoryNames()));
		args.add(
			"source.check.names=" + CollectionUtils.join(",", getCheckNames()));
		args.add("source.fail.on.auto.fix=" + isFailOnAutoFix());
		args.add("source.fail.on.has.warning=" + isFailOnHasWarning());
		args.add(
			"source.file.extensions=" +
				CollectionUtils.join(",", getFileExtensions()));
		args.add("source.print.errors=" + isPrintErrors());
		args.add("validate.commit.messages=" + isValidateCommitMessages());

		FileCollection fileCollection = getFiles();

		if (fileCollection.isEmpty()) {
			args.add("source.base.dir=" + _normalize(getBaseDir()));
		}
		else {
			args.add("source.files=" + _merge(fileCollection));
		}

		return args;
	}

	private String _merge(Iterable<File> files) {
		StringBuilder sb = new StringBuilder();

		int i = 0;

		for (File file : files) {
			if (i > 0) {
				sb.append(',');
			}

			sb.append(_normalize(file));

			i++;
		}

		return sb.toString();
	}

	private String _normalize(File file) {
		String pathString = String.valueOf(file.toPath());

		if (File.separatorChar != '/') {
			pathString = pathString.replace(File.separatorChar, '/');
		}

		if (pathString.charAt(pathString.length() - 1) != '/') {
			pathString += '/';
		}

		return pathString;
	}

	private final SourceFormatterArgs _sourceFormatterArgs =
		new SourceFormatterArgs();

}