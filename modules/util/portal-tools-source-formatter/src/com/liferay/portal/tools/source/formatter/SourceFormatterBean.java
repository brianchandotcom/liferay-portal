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

package com.liferay.portal.tools.source.formatter;

import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

/**
 * @author Raymond Augé
 */
public class SourceFormatterBean {


	public String getCopyright() {
		return _copyright;
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

	public String getBaseDir() {
		return _baseDir;
	}

	public List<String> getFileNames() {
		return _fileNames;
	}

	public void setAutoFix(boolean autoFix) {
		_autoFix = autoFix;
	}

	public void setBaseDir(String baseDir) {
		if (_fileNames != null) {
			throw new RuntimeException("Filenames are already initialized.");
		}

		if (!baseDir.endsWith(StringPool.SLASH)) {
			baseDir += StringPool.SLASH;
		}

		_baseDir = baseDir;
	}


	public void setCopyright(String copyright) {
		_copyright = copyright;
	}

	public void setFileNames(List<String> fileNames) {
		if (_fileNames != null) {
			throw new RuntimeException("Filenames are already initialized.");
		}
		if (_baseDir != _BASE_DIR) {
			throw new RuntimeException("Base directory was already set.");
		}

		_baseDir = StringPool.BLANK;
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

	private static final String _BASE_DIR = "./";

	private boolean _autoFix = true;
	private String _baseDir = _BASE_DIR;
	private String _copyright = "copyright.txt";
	private List<String> _fileNames;
	private boolean _printErrors = true;
	private boolean _throwException = false;
	private boolean _useProperties = false;

}