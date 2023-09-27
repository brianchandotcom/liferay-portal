/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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