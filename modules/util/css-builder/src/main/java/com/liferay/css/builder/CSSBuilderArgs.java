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

package com.liferay.css.builder;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
@Parameters(separators = " =")
public class CSSBuilderArgs {

	public static final boolean APPEND_CSS_IMPORT_TIMESTAMPS = true;

	public static final String OUTPUT_DIR_NAME = "/";

	public static final int PRECISION = 9;

	public File getBaseDir() {
		return _baseDir;
	}

	public File getImportDir() {
		return _importDir;
	}

	public List<String> getIncludes() {
		return _includes;
	}

	public String getOutputDirName() {
		return _outputDirName;
	}

	public int getPrecision() {
		return _precision;
	}

	public List<String> getRtlExcludedPathRegexps() {
		return _rtlExcludedPathRegexps;
	}

	public String getSassCompilerClassName() {
		return _sassCompilerClassName;
	}

	public boolean isAppendCssImportTimestamps() {
		return _appendCssImportTimestamps;
	}

	public boolean isGenerateSourceMap() {
		return _generateSourceMap;
	}

	public void setAppendCssImportTimestamps(
		boolean appendCssImportTimestamps) {

		_appendCssImportTimestamps = appendCssImportTimestamps;
	}

	public void setBaseDir(File baseDir) {
		_baseDir = baseDir;
	}

	public void setGenerateSourceMap(boolean generateSourceMap) {
		_generateSourceMap = generateSourceMap;
	}

	public void setImportDir(File importDir) {
		_importDir = importDir;
	}

	public void setIncludes(String includes) {
		setIncludes(_split(includes));
	}

	public void setIncludes(String[] includes) {
		_includes = Arrays.asList(includes);
	}

	public void setOutputDirName(String outputDirName) {
		_outputDirName = outputDirName;
	}

	public void setPrecision(int precision) {
		_precision = precision;
	}

	public void setRtlExcludedPathRegexps(List<String> rtlExcludedPathRegexps) {
		_rtlExcludedPathRegexps = rtlExcludedPathRegexps;
	}

	public void setRtlExcludedPathRegexps(String rtlExcludedPathRegexps) {
		setRtlExcludedPathRegexps(
			Arrays.asList(_split(rtlExcludedPathRegexps)));
	}

	public void setSassCompilerClassName(String sassCompilerClassName) {
		_sassCompilerClassName = sassCompilerClassName;
	}

	protected boolean isHelp() {
		return _help;
	}

	private String[] _split(String s) {
		return s.split(",");
	}

	@Parameter(
		arity = 1,
		description = "Whether to append the current timestamp to the URLs in the @import CSS at-rules.",
		names = "append-css-import-timestamps"
	)
	private boolean _appendCssImportTimestamps = APPEND_CSS_IMPORT_TIMESTAMPS;

	@Parameter(
		description = "The base directory that contains the SCSS files to compile.",
		names = "base-dir"
	)
	private File _baseDir;

	@Parameter(
		arity = 1,
		description = "Whether to generate source maps for easier debugging.",
		names = "generate-source-map"
	)
	private boolean _generateSourceMap;

	@Parameter(
		description = "Print this message.", help = true,
		names = {"-h", "--help"}
	)
	private boolean _help;

	@Parameter(
		description = "The import directory of sass libraries.",
		names = {"import-dir", "import-path"}
	)
	private File _importDir;

	@Parameter(
		description = "The name of the directories, relative to base directory, which contain the SCSS files to compile. All sub-directories are searched for SCSS files as well.",
		names = "includes"
	)
	private List<String> _includes;

	@Parameter(
		description = "The name of the sub-directories where the SCSS files are compiled to. For each directory that contains SCSS files, a sub-directory with this name is created.",
		names = "output-dir"
	)
	private String _outputDirName = OUTPUT_DIR_NAME;

	@Parameter(
		description = "The numeric precision of numbers in Sass.",
		names = "precision"
	)
	private int _precision = PRECISION;

	@Parameter(
		description = "The SCSS file patterns to exclude when converting for right-to-left (RTL) support.",
		names = "rtl-excluded-path-regexps"
	)
	private List<String> _rtlExcludedPathRegexps = new ArrayList<>();

	@Parameter(
		description = "The type of Sass compiler to use. Supported values are \"jni\" and \"ruby\". If not set, defaults to \"jni\".",
		names = "compiler"
	)
	private String _sassCompilerClassName = "jni";

}