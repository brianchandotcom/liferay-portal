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

/**
 * @author Andrea Di Giorgi
 */
public class CSSBuilderArgs {

	public static final String DIR_NAME = "/";

	public static final String DOCROOT_DIR_NAME = "src/META-INF/resources";

	public static final int PRECISION = 5;

	public String[] getDirNames() {
		return _dirNames;
	}

	public String getDocrootDirName() {
		return _docrootDirName;
	}

	public String getPortalCommonDirName() {
		return _portalCommonDirName;
	}

	public int getPrecision() {
		return _precision;
	}

	public String[] getRtlExcludedPathRegexps() {
		return _rtlExcludedPathRegexps;
	}

	public String getSassCompilerClassName() {
		return _sassCompilerClassName;
	}

	public boolean isGenerateSourceMap() {
		return _generateSourceMap;
	}

	public void setDirNames(String dirNames) {
		setDirNames(_split(dirNames));
	}

	public void setDirNames(String[] dirNames) {
		_dirNames = dirNames;
	}

	public void setDocrootDirName(String docrootDirName) {
		_docrootDirName = docrootDirName;
	}

	public void setGenerateSourceMap(boolean generateSourceMap) {
		_generateSourceMap = generateSourceMap;
	}

	public void setPortalCommonDirName(String portalCommonDirName) {
		_portalCommonDirName = portalCommonDirName;
	}

	public void setPrecision(int precision) {
		_precision = precision;
	}

	public void setRtlExcludedPathRegexps(String rtlExcludedPathRegexps) {
		setRtlExcludedPathRegexps(_split(rtlExcludedPathRegexps));
	}

	public void setRtlExcludedPathRegexps(String[] rtlExcludedPathRegexps) {
		_rtlExcludedPathRegexps = rtlExcludedPathRegexps;
	}

	public void setSassCompilerClassName(String sassCompilerClassName) {
		_sassCompilerClassName = sassCompilerClassName;
	}

	private String[] _split(String s) {
		return s.split(",");
	}

	private String[] _dirNames = {DIR_NAME};
	private String _docrootDirName = DOCROOT_DIR_NAME;
	private boolean _generateSourceMap;
	private String _portalCommonDirName;
	private int _precision;
	private String[] _rtlExcludedPathRegexps = new String[0];
	private String _sassCompilerClassName;

}