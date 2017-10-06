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

import java.io.File;

import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CSSBuilderArgs {

	public static final boolean APPEND_CSS_IMPORT_TIMESTAMPS = true;

	public static final String DIR_NAME = "/";

	public static final String DOCROOT_DIR_NAME = "src/META-INF/resources";

	public static final String OUTPUT_DIR_NAME = ".sass-cache/";

	public static final int PRECISION = 9;

	public List<String> getDirNames() {
		return _dirNames;
	}

	public File getDocrootDir() {
		return _docrootDir;
	}

	public String getOutputDirName() {
		return _outputDirName;
	}

	public File getPortalCommonPath() {
		return _portalCommonPath;
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

	public void setDirNames(String dirNames) {
		setDirNames(_split(dirNames));
	}

	public void setDirNames(String[] dirNames) {
		_dirNames = Arrays.asList(dirNames);
	}

	public void setDocrootDir(File docrootDir) {
		_docrootDir = docrootDir;
	}

	public void setGenerateSourceMap(boolean generateSourceMap) {
		_generateSourceMap = generateSourceMap;
	}

	public void setOutputDirName(String outputDirName) {
		_outputDirName = outputDirName;
	}

	public void setPortalCommonPath(File portalCommonPath) {
		_portalCommonPath = portalCommonPath;
	}

	public void setPortalCommonPath(String portalCommonPathName) {
		_portalCommonPath = new File(portalCommonPathName);
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

	private String[] _split(String s) {
		return s.split(",");
	}

	@Parameter(
		description = "Whether to append the current timestamp to the URLs in the @import CSS at-rules.",
		names = "sass.append.css.import.timestamps"
	)
	private boolean _appendCssImportTimestamps = APPEND_CSS_IMPORT_TIMESTAMPS;

	@Parameter(
		description = "The name of the directories, relative to docrootDir, which contain the SCSS files to compile. All sub-directories are searched for SCSS files as well.",
		names = "sass.dir"
	)
	private List<String> _dirNames = Arrays.asList(DIR_NAME);

	@Parameter(
		description = "The base directory that contains the SCSS files to compile.",
		names = "sass.docroot.dir"
	)
	private File _docrootDir = Paths.get(".", DOCROOT_DIR_NAME).toFile();

	@Parameter(
		description = "Whether to generate source maps for easier debugging.",
		names = "sass.generate.source.map"
	)
	private boolean _generateSourceMap;

	@Parameter(
		description = "The name of the sub-directories where the SCSS files are compiled to. " +
			"For each directory that contains SCSS files, a sub-directory with this name is created. ",
		names = "sass.output.dir"
	)
	private String _outputDirName = OUTPUT_DIR_NAME;

	@Parameter(
		description = "The META-INF/resources directory of the Liferay Frontend Common CSS artifact. This is required in order to make Bourbon and other CSS libraries available to the compilation.",
		names = {"sass.portal.common.path", "sass.portal.common.dir"}
	)
	private File _portalCommonPath;

	@Parameter(
		description = "The numeric precision of numbers in Sass.",
		names = "sass.precision"
	)
	private int _precision = PRECISION;

	@Parameter(
		description = "The SCSS file patterns to exclude when converting for right-to-left (RTL) support.",
		names = "sass.rtl.excluded.path.regexps"
	)
	private List<String> _rtlExcludedPathRegexps = new ArrayList<>();

	@Parameter(
		description = "The type of Sass compiler to use. Supported values are \"jni\" and \"ruby\". If not set, defaults to \"jni\".",
		names = "sass.compiler.class.name"
	)
	private String _sassCompilerClassName = "jni";

}