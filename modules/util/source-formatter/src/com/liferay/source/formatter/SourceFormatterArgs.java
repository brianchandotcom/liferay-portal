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

import java.util.List;

/**
 * @author Raymond Augé
 */
public class SourceFormatterArgs {

	public static final boolean AUTO_FIX = true;

	public static final String BASE_DIR_NAME = "./";

	public static final String COPYRIGHT_FILE_NAME = "copyright.txt";

	public static final boolean FORMAT_CURRENT_BRANCH = false;

	public static final boolean FORMAT_LATEST_AUTHOR = false;

	public static final boolean FORMAT_LOCAL_CHANGES = false;

	public static final String OUTPUT_KEY_MODIFIED_FILES =
		"source.formatter.modified.files";

	public static final boolean PRINT_ERRORS = true;

	public static final boolean THROW_EXCEPTION = false;

	public static final boolean USE_PROPERTIES = false;

	public String getBaseDirName() {
		return _baseDirName;
	}

	public String getCopyrightFileName() {
		return _copyrightFileName;
	}

	public List<String> getFileNames() {
		return _fileNames;
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

	public boolean isPrintErrors() {
		return _printErrors;
	}

	public boolean isThrowException() {
		return _throwException;
	}

	public boolean isUseProperties() {
		return _useProperties;
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

	public void setCopyrightFileName(String copyrightFileName) {
		_copyrightFileName = copyrightFileName;
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

	public void setPrintErrors(boolean printErrors) {
		_printErrors = printErrors;
	}

	public void setRecentChangesFileNames(List<String> recentChangesFileNames) {
		_recentChangesFileNames = recentChangesFileNames;
	}

	public void setThrowException(boolean throwException) {
		_throwException = throwException;
	}

	public void setUseProperties(boolean useProperties) {
		_useProperties = useProperties;
	}

	private boolean _autoFix = AUTO_FIX;
	private String _baseDirName = BASE_DIR_NAME;
	private String _copyrightFileName = COPYRIGHT_FILE_NAME;
	private List<String> _fileNames;
	private boolean _formatCurrentBranch = FORMAT_CURRENT_BRANCH;
	private boolean _formatLatestAuthor = FORMAT_LATEST_AUTHOR;
	private boolean _formatLocalChanges = FORMAT_LOCAL_CHANGES;
	private boolean _printErrors = PRINT_ERRORS;
	private List<String> _recentChangesFileNames;
	private boolean _throwException = THROW_EXCEPTION;
	private boolean _useProperties = USE_PROPERTIES;

}