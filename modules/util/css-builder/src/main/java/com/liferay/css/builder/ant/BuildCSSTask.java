/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.css.builder.ant;

import com.liferay.css.builder.CSSBuilder;
import com.liferay.css.builder.CSSBuilderArgs;

import java.io.File;

import java.util.Arrays;

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
		catch (Exception exception) {
			throw new BuildException(exception);
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

	public void setExcludes(String excludes) {
		_cssBuilderArgs.setExcludes(excludes);
	}

	public void setGenerateSourceMap(boolean generateSourceMap) {
		_cssBuilderArgs.setGenerateSourceMap(generateSourceMap);
	}

	public void setImportDir(File importDir) {
		_cssBuilderArgs.setImportPaths(Arrays.asList(importDir));
	}

	public void setOutputDirName(String outputDirName) {
		_cssBuilderArgs.setOutputDirName(outputDirName);
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