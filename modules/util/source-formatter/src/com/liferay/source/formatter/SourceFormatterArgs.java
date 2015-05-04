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

import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

/**
 * @author Raymond Augé
 */
public class SourceFormatterArgs {

	public static final String OUTPUT_KEY_MODIFIED_FILES =
		"source.formatter.modified.files";

	public String getCopyrightFileName() {
		return _copyrightFileName;
	}

	public boolean isAutoFix() {
		return _autoFix;
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

	public String getBaseDirName() {
		return _baseDirName;
	}

	public List<String> getFileNames() {
		return _fileNames;
	}

	public void setAutoFix(boolean autoFix) {
		_autoFix = autoFix;
	}

	public void setBaseDirName(String baseDirName) {
		if (_fileNames != null) {
			throw new RuntimeException("Filenames are already initialized.");
		}

		if (!baseDirName.endsWith(StringPool.SLASH)) {
			baseDirName += StringPool.SLASH;
		}

		_baseDirName = baseDirName;
	}


	public void setCopyrightFileName(String copyrightFileName) {
		_copyrightFileName = copyrightFileName;
	}

	public void setFileNames(List<String> fileNames) {
		if (_fileNames != null) {
			throw new RuntimeException("Filenames are already initialized.");
		}

		if (_baseDirName != _BASE_DIR_NAME) {
			throw new RuntimeException("Base directory was already set.");
		}

		_baseDirName = StringPool.BLANK;
		_fileNames = fileNames;
	}

	public void setPrintErrors(boolean printErrors) {
		_printErrors = printErrors;
	}

	public void setThrowException(boolean throwException) {
		_throwException = throwException;
	}

	public void setUseProperties(boolean useProperties) {
		_useProperties = useProperties;
	}

	private static final String _BASE_DIR_NAME = "./";

	private boolean _autoFix = true;
	private String _baseDirName = _BASE_DIR_NAME;
	private String _copyrightFileName = "copyright.txt";
	private List<String> _fileNames;
	private boolean _printErrors = true;
	private boolean _throwException = false;
	private boolean _useProperties = false;

}