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

package com.liferay.source.formatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Raymond Augé
 */
public class SourceFormatterArgs {

	public static final boolean AUTO_FIX = true;

	public static final String BASE_DIR_NAME = "./";

	public static final boolean FORMAT_CURRENT_BRANCH = false;

	public static final boolean FORMAT_LATEST_AUTHOR = false;

	public static final boolean FORMAT_LOCAL_CHANGES = false;

	public static final String GIT_WORKING_BRANCH_NAME = "master";

	public static final boolean INCLUDE_GENERATED_FILES = false;

	public static final boolean INCLUDE_SUBREPOSITORIES = false;

	public static final int MAX_LINE_LENGTH = 80;

	public static final String OUTPUT_KEY_MODIFIED_FILES =
		"source.formatter.modified.files";

	public static final boolean PRINT_ERRORS = true;

	public static final int PROCESSOR_THREAD_COUNT = 5;

	public static final boolean SHOW_DEBUG_INFORMATION = false;

	public static final boolean SHOW_DOCUMENTATION = false;

	public static final boolean SHOW_STATUS_UPDATES = false;

	public static final boolean THROW_EXCEPTION = false;

	public void addRecentChangesFileNames(Set<String> fileNames) {
		if (_recentChangesFileNames != null) {
			_recentChangesFileNames.addAll(fileNames);
		}
	}

	public String getBaseDirName() {
		return _baseDirName;
	}

	public String getCheckName() {
		return _checkName;
	}

	public List<String> getFileExtensions() {
		return _fileExtensions;
	}

	public List<String> getFileNames() {
		return _fileNames;
	}

	public String getGitWorkingBranchName() {
		return _gitWorkingBranchName;
	}

	public int getMaxLineLength() {
		return _maxLineLength;
	}

	public int getProcessorThreadCount() {
		return _processorThreadCount;
	}

	public List<String> getRecentChangesFileNames() {
		return _recentChangesFileNames;
	}

	public boolean isAutoFix() {
		return _autoFix;
	}

	public boolean isFormatCurrentBranch() {
		return _formatCurrentBranch;
	}

	public boolean isFormatLatestAuthor() {
		return _formatLatestAuthor;
	}

	public boolean isFormatLocalChanges() {
		return _formatLocalChanges;
	}

	public boolean isIncludeGeneratedFiles() {
		return _includeGeneratedFiles;
	}

	public boolean isIncludeSubrepositories() {
		return _includeSubrepositories;
	}

	public boolean isPrintErrors() {
		return _printErrors;
	}

	public boolean isShowDebugInformation() {
		return _showDebugInformation;
	}

	public boolean isShowDocumentation() {
		return _showDocumentation;
	}

	public boolean isShowStatusUpdates() {
		return _showStatusUpdates;
	}

	public boolean isThrowException() {
		return _throwException;
	}

	public void setAutoFix(boolean autoFix) {
		_autoFix = autoFix;
	}

	public void setBaseDirName(String baseDirName) {
		if (_fileNames != null) {
			throw new RuntimeException("File names are already initialized");
		}

		if (!baseDirName.endsWith("/")) {
			baseDirName += "/";
		}

		_baseDirName = baseDirName;
	}

	public void setCheckName(String checkName) {
		_checkName = checkName;
	}

	public void setFileExtensions(List<String> fileExtensions) {
		_fileExtensions = fileExtensions;
	}

	public void setFileNames(List<String> fileNames) {
		if (_fileNames != null) {
			throw new RuntimeException("File names are already initialized");
		}

		if (_baseDirName != BASE_DIR_NAME) {
			throw new RuntimeException("Base directory was already set");
		}

		_baseDirName = "";
		_fileNames = fileNames;
	}

	public void setFormatCurrentBranch(boolean formatCurrentBranch) {
		_formatCurrentBranch = formatCurrentBranch;
	}

	public void setFormatLatestAuthor(boolean formatLatestAuthor) {
		_formatLatestAuthor = formatLatestAuthor;
	}

	public void setFormatLocalChanges(boolean formatLocalChanges) {
		_formatLocalChanges = formatLocalChanges;
	}

	public void setGitWorkingBranchName(String gitWorkingBranchName) {
		_gitWorkingBranchName = gitWorkingBranchName;
	}

	public void setIncludeGeneratedFiles(boolean includeGeneratedFiles) {
		_includeGeneratedFiles = includeGeneratedFiles;
	}

	public void setIncludeSubrepositories(boolean includeSubrepositories) {
		_includeSubrepositories = includeSubrepositories;
	}

	public void setMaxLineLength(int maxLineLength) {
		_maxLineLength = maxLineLength;
	}

	public void setPrintErrors(boolean printErrors) {
		_printErrors = printErrors;
	}

	public void setProcessorThreadCount(int processorThreadCount) {
		_processorThreadCount = processorThreadCount;
	}

	public void setRecentChangesFileNames(List<String> recentChangesFileNames) {
		_recentChangesFileNames = recentChangesFileNames;
	}

	public void setShowDebugInformation(boolean showDebugInformation) {
		_showDebugInformation = showDebugInformation;
	}

	public void setShowDocumentation(boolean showDocumentation) {
		_showDocumentation = showDocumentation;
	}

	public void setShowStatusUpdates(boolean showStatusUpdates) {
		_showStatusUpdates = showStatusUpdates;
	}

	public void setThrowException(boolean throwException) {
		_throwException = throwException;
	}

	private boolean _autoFix = AUTO_FIX;
	private String _baseDirName = BASE_DIR_NAME;
	private String _checkName;
	private List<String> _fileExtensions = new ArrayList<>();
	private List<String> _fileNames;
	private boolean _formatCurrentBranch = FORMAT_CURRENT_BRANCH;
	private boolean _formatLatestAuthor = FORMAT_LATEST_AUTHOR;
	private boolean _formatLocalChanges = FORMAT_LOCAL_CHANGES;
	private String _gitWorkingBranchName = GIT_WORKING_BRANCH_NAME;
	private boolean _includeGeneratedFiles = INCLUDE_GENERATED_FILES;
	private boolean _includeSubrepositories = INCLUDE_SUBREPOSITORIES;
	private int _maxLineLength = MAX_LINE_LENGTH;
	private boolean _printErrors = PRINT_ERRORS;
	private int _processorThreadCount = PROCESSOR_THREAD_COUNT;
	private List<String> _recentChangesFileNames;
	private boolean _showDebugInformation = SHOW_DEBUG_INFORMATION;
	private boolean _showDocumentation = SHOW_DOCUMENTATION;
	private boolean _showStatusUpdates = SHOW_STATUS_UPDATES;
	private boolean _throwException = THROW_EXCEPTION;

}