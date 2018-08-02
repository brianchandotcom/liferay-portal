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

	public static final String BASE_DIR_NAME = "src/META-INF/resources";

	public static final String DIR_NAME = "/";

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #BASE_DIR_NAME}
	 */
	@Deprecated
	public static final String DOCROOT_DIR_NAME = BASE_DIR_NAME;

	public static final String OUTPUT_DIR_NAME = ".sass-cache/";

	public static final int PRECISION = 9;

	public File getBaseDir() {
		return _baseDir;
	}

	public List<String> getDirNames() {
		return _dirNames;
	}

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #getBaseDir()}
	 */
	@Deprecated
	public File getDocrootDir() {
		return getBaseDir();
	}

	public File getImportDir() {
		return _importDir;
	}

	public String getOutputDirName() {
		return _outputDirName;
	}

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #getImportDir()}
	 */
	@Deprecated
	public File getPortalCommonPath() {
		return getImportDir();
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

	public void setDirNames(String dirNames) {
		setDirNames(_split(dirNames));
	}

	public void setDirNames(String[] dirNames) {
		_dirNames = Arrays.asList(dirNames);
	}

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #setDocrootDir(File)}
	 */
	@Deprecated
	public void setDocrootDir(File docrootDir) {
		setBaseDir(docrootDir);
	}

	public void setGenerateSourceMap(boolean generateSourceMap) {
		_generateSourceMap = generateSourceMap;
	}

	public void setImportDir(File importDir) {
		_importDir = importDir;
	}

	public void setOutputDirName(String outputDirName) {
		_outputDirName = outputDirName;
	}

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #setImportDir(File)}
	 */
	@Deprecated
	public void setPortalCommonPath(File portalCommonPath) {
		setImportDir(portalCommonPath);
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
		names = {
			"append-css-import-timestamps", "sass.append.css.import.timestamps"
		}
	)
	private boolean _appendCssImportTimestamps = APPEND_CSS_IMPORT_TIMESTAMPS;

	@Parameter(
		description = "The base directory that contains the SCSS files to compile.",
		names = {"base-dir", "sass.docroot.dir"}
	)
	private File _baseDir = new File(BASE_DIR_NAME);

	@Parameter(
		description = "The name of the directories, relative to base directory, which contain the SCSS files to compile. All sub-directories are searched for SCSS files as well.",
		names = {"dir-names", "sass.dir"}
	)
	private List<String> _dirNames = Arrays.asList(DIR_NAME);

	@Parameter(
		arity = 1,
		description = "Whether to generate source maps for easier debugging.",
		names = {"generate-source-map", "sass.generate.source.map"}
	)
	private boolean _generateSourceMap;

	@Parameter(
		description = "Print this message.", help = true,
		names = {"-h", "--help"}
	)
	private boolean _help;

	@Parameter(
		description = "The import directory of Sass libraries.",
		names = {
			"import-dir", "import-path", "sass.portal.common.dir",
			"sass.portal.common.path"
		}
	)
	private File _importDir;

	@Parameter(
		description = "The name of the sub-directories where the SCSS files are compiled to. For each directory that contains SCSS files, a sub-directory with this name is created.",
		names = {"output-dir", "sass.output.dir"}
	)
	private String _outputDirName = OUTPUT_DIR_NAME;

	@Parameter(
		description = "The numeric precision of numbers in Sass.",
		names = {"precision", "sass.precision"}
	)
	private int _precision = PRECISION;

	@Parameter(
		description = "The SCSS file patterns to exclude when converting for right-to-left (RTL) support.",
		names = {"rtl-excluded-path-regexps", "sass.rtl.excluded.path.regexps"}
	)
	private List<String> _rtlExcludedPathRegexps = new ArrayList<>();

	@Parameter(
		description = "The type of Sass compiler to use. Supported values are \"jni\" and \"ruby\". The Ruby Sass compiler requires \"com.liferay.sass.compiler.ruby.jar\", \"com.liferay.ruby.gems.jar\", and \"jruby-complete.jar\" to be added to the classpath.",
		names = {"compiler", "sass.compiler.class.name"}
	)
	private String _sassCompilerClassName = "jni";

}