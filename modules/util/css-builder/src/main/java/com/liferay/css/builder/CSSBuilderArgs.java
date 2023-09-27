/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.css.builder;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import com.liferay.css.builder.internal.converters.PathParameterSplitter;

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

	public static final String[] EXCLUDES = {
		"**/_diffs/**", "**/.sass-cache*/**", "**/.sass_cache_*/**",
		"**/_sass_cache_*/**", "**/_styled/**", "**/_unstyled/**",
		"**/css/aui/**", "**/css/clay/**", "**/tmp/**"
	};

	public static final String OUTPUT_DIR_NAME = ".sass-cache/";

	public static final int PRECISION = 9;

	public File getBaseDir() {
		return _baseDir;
	}

	public List<String> getDirNames() {
		return _dirNames;
	}

	public List<String> getExcludes() {
		return _excludes;
	}

	public List<File> getImportPaths() {
		return _importPaths;
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

	public void setDirNames(String dirNames) {
		setDirNames(_split(dirNames));
	}

	public void setDirNames(String[] dirNames) {
		_dirNames = Arrays.asList(dirNames);
	}

	public void setExcludes(String excludes) {
		setExcludes(_split(excludes));
	}

	public void setExcludes(String[] excludes) {
		_excludes = Arrays.asList(excludes);
	}

	public void setGenerateSourceMap(boolean generateSourceMap) {
		_generateSourceMap = generateSourceMap;
	}

	public void setImportPaths(List<File> importPaths) {
		_importPaths = importPaths;
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
		names = "--append-css-import-timestamps"
	)
	private boolean _appendCssImportTimestamps = APPEND_CSS_IMPORT_TIMESTAMPS;

	@Parameter(
		description = "The base directory that contains the SCSS files to compile.",
		names = "--base-dir"
	)
	private File _baseDir = new File(BASE_DIR_NAME);

	@Parameter(
		description = "The name of the directories, relative to base directory, which contain the SCSS files to compile. All sub-directories are searched for SCSS files as well.",
		names = "--dir-names"
	)
	private List<String> _dirNames = Arrays.asList(DIR_NAME);

	@Parameter(
		description = "The SCSS file patterns to exclude from compiling.",
		names = "--excludes"
	)
	private List<String> _excludes = Arrays.asList(EXCLUDES);

	@Parameter(
		arity = 1,
		description = "Whether to generate source maps for easier debugging.",
		names = "--generate-source-map"
	)
	private boolean _generateSourceMap;

	@Parameter(
		description = "Print this message.", help = true,
		names = {"-h", "--help"}
	)
	private boolean _help;

	@Parameter(
		description = "The import directories of Sass libraries.",
		names = "--import-paths", splitter = PathParameterSplitter.class
	)
	private List<File> _importPaths;

	@Parameter(
		description = "The name of the sub-directories where the SCSS files are compiled to. For each directory that contains SCSS files, a sub-directory with this name is created.",
		names = "--output-dir"
	)
	private String _outputDirName = OUTPUT_DIR_NAME;

	@Parameter(
		description = "The numeric precision of numbers in Sass.",
		names = "--precision"
	)
	private int _precision = PRECISION;

	@Parameter(
		description = "The SCSS file patterns to exclude when converting for right-to-left (RTL) support.",
		names = "--rtl-excluded-path-regexps"
	)
	private List<String> _rtlExcludedPathRegexps = new ArrayList<>();

	@Parameter(
		description = "The type of Sass compiler to use. Supported values are \"jni\" and \"ruby\". The Ruby Sass compiler requires \"com.liferay.sass.compiler.ruby.jar\", \"com.liferay.ruby.gems.jar\", and \"jruby-complete.jar\" to be added to the classpath.",
		names = "--compiler"
	)
	private String _sassCompilerClassName = "jni";

}