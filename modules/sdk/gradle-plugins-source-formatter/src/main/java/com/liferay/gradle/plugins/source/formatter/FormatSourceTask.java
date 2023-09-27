/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.util.CollectionUtils;

/**
 * @author Raymond Augé
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class FormatSourceTask extends JavaExec {

	public FormatSourceTask() {
		Property<String> mainClass = getMainClass();

		mainClass.set("com.liferay.source.formatter.SourceFormatter");
	}

	@Override
	public void exec() {
		setArgs(_getCompleteArgs());

		super.exec();
	}

	@InputDirectory
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public File getBaseDir() {
		return GradleUtil.toFile(
			getProject(), _sourceFormatterArgs.getBaseDirName());
	}

	@Input
	@Optional
	public String getBaseDirName() {
		return _sourceFormatterArgs.getBaseDirName();
	}

	@Input
	@Optional
	public List<String> getCheckCategoryNames() {
		return _sourceFormatterArgs.getCheckCategoryNames();
	}

	@Input
	@Optional
	public List<String> getCheckNames() {
		return _sourceFormatterArgs.getCheckNames();
	}

	@Input
	@Optional
	public List<String> getFileExtensions() {
		return _sourceFormatterArgs.getFileExtensions();
	}

	@Input
	@Optional
	public List<String> getFileNames() {
		return _sourceFormatterArgs.getFileNames();
	}

	@InputFiles
	@Optional
	@PathSensitive(PathSensitivity.RELATIVE)
	public FileCollection getFiles() {
		Project project = getProject();

		List<String> fileNames = _sourceFormatterArgs.getFileNames();

		if (fileNames == null) {
			fileNames = Collections.emptyList();
		}

		return project.files(fileNames);
	}

	@Input
	@Optional
	public String getGitWorkingBranchName() {
		return _sourceFormatterArgs.getGitWorkingBranchName();
	}

	@Input
	public int getMaxLineLength() {
		return _sourceFormatterArgs.getMaxLineLength();
	}

	@Input
	public int getProcessorThreadCount() {
		return _sourceFormatterArgs.getProcessorThreadCount();
	}

	@Input
	public boolean isAutoFix() {
		return _sourceFormatterArgs.isAutoFix();
	}

	@Input
	public boolean isFailOnAutoFix() {
		return _sourceFormatterArgs.isFailOnAutoFix();
	}

	@Input
	public boolean isFailOnHasWarning() {
		return _sourceFormatterArgs.isFailOnHasWarning();
	}

	@Input
	public boolean isFormatCurrentBranch() {
		return _sourceFormatterArgs.isFormatCurrentBranch();
	}

	@Input
	public boolean isFormatLatestAuthor() {
		return _sourceFormatterArgs.isFormatLatestAuthor();
	}

	@Input
	public boolean isFormatLocalChanges() {
		return _sourceFormatterArgs.isFormatLocalChanges();
	}

	@Input
	public boolean isIncludeSubrepositories() {
		return _sourceFormatterArgs.isIncludeSubrepositories();
	}

	@Input
	public boolean isJavaParserEnabled() {
		return _sourceFormatterArgs.isJavaParserEnabled();
	}

	@Input
	public boolean isPrintErrors() {
		return _sourceFormatterArgs.isPrintErrors();
	}

	@Input
	public boolean isShowDebugInformation() {
		return _sourceFormatterArgs.isShowDebugInformation();
	}

	@Input
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

	public void setJavaParserEnabled(boolean javaParserEnabled) {
		_sourceFormatterArgs.setJavaParserEnabled(javaParserEnabled);
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
		args.add("java.parser.enabled=" + isJavaParserEnabled());
		args.add("max.line.length=" + getMaxLineLength());
		args.add("processor.thread.count=" + getProcessorThreadCount());
		args.add("show.debug.information=" + isShowDebugInformation());
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