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

package com.liferay.javadoc.formatter;

/**
 * @author Andrea Di Giorgi
 */
public class JavadocFormatterArgs {

	public static final String AUTHOR = "Brian Wing Shun Chan";

	public static final String OUTPUT_FILE_PREFIX = "javadocs";

	public static final String OUTPUT_KEY_MODIFIED_FILES =
		"javadoc.formatter.modified.files";

	public String getAuthor() {
		return _author;
	}

	public String getInputDirName() {
		return _inputDirName;
	}

	public String[] getLimits() {
		return _limits;
	}

	public String getOutputFilePrefix() {
		return _outputFilePrefix;
	}

	public boolean isGenerateXml() {
		return _generateXml;
	}

	public boolean isInitializeMissingJavadocs() {
		return _initializeMissingJavadocs;
	}

	public boolean isUpdateJavadocs() {
		return _updateJavadocs;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public void setGenerateXml(boolean generateXml) {
		_generateXml = generateXml;
	}

	public void setInitializeMissingJavadocs(
		boolean initializeMissingJavadocs) {

		_initializeMissingJavadocs = initializeMissingJavadocs;
	}

	public void setInputDirName(String inputDirName) {
		_inputDirName = inputDirName;
	}

	public void setLimits(String limits) {
		setLimits(_split(limits));
	}

	public void setLimits(String[] limits) {
		_limits = limits;
	}

	public void setOutputFilePrefix(String outputFilePrefix) {
		_outputFilePrefix = outputFilePrefix;
	}

	public void setUpdateJavadocs(boolean updateJavadocs) {
		_updateJavadocs = updateJavadocs;
	}

	private String[] _split(String s) {
		return s.split(",");
	}

	private String _author = AUTHOR;
	private boolean _generateXml;
	private boolean _initializeMissingJavadocs;
	private String _inputDirName = "./";
	private String[] _limits = new String[0];
	private String _outputFilePrefix = OUTPUT_FILE_PREFIX;
	private boolean _updateJavadocs;

}