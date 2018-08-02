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

package com.liferay.css.builder.ant;

import com.liferay.css.builder.CSSBuilder;
import com.liferay.css.builder.CSSBuilderArgs;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Andrea Di Giorgi
 */
public class BuildCSSTask extends Task {

	@Override
	public void execute() throws BuildException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			BuildCSSTask.class.getClassLoader());

		try (CSSBuilder cssBuilder = new CSSBuilder(_cssBuilderArgs)) {
			cssBuilder.execute();
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void setAppendCssImportTimestamps(
		boolean appendCssImportTimestamps) {

		_cssBuilderArgs.setAppendCssImportTimestamps(appendCssImportTimestamps);
	}

	public void setBaseDir(File baseDir) {
		_cssBuilderArgs.setBaseDir(baseDir);
	}

	public void setDirNames(String dirNames) {
		_cssBuilderArgs.setDirNames(dirNames);
	}

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #setBaseDir(File)}
	 */
	@Deprecated
	public void setDocrootDir(File docrootDir) {
		setBaseDir(docrootDir);
	}

	public void setGenerateSourceMap(boolean generateSourceMap) {
		_cssBuilderArgs.setGenerateSourceMap(generateSourceMap);
	}

	public void setImportDir(File importDir) {
		_cssBuilderArgs.setImportDir(importDir);
	}

	public void setOutputDirName(String outputDirName) {
		_cssBuilderArgs.setOutputDirName(outputDirName);
	}

	/**
	 * @deprecated As of 2.1.0, replaced by {@link #setImportDir(File)}
	 */
	@Deprecated
	public void setPortalCommonPath(File portalCommonPath) {
		setImportDir(portalCommonPath);
	}

	public void setPrecision(int precision) {
		_cssBuilderArgs.setPrecision(precision);
	}

	public void setRtlExcludedPathRegexps(String rtlExcludedPathRegexps) {
		_cssBuilderArgs.setRtlExcludedPathRegexps(rtlExcludedPathRegexps);
	}

	public void setSassCompilerClassName(String sassCompilerClassName) {
		_cssBuilderArgs.setSassCompilerClassName(sassCompilerClassName);
	}

	private final CSSBuilderArgs _cssBuilderArgs = new CSSBuilderArgs();

}