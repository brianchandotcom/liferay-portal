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

import com.liferay.css.builder.CSSBuilderArgs;
import com.liferay.css.builder.CSSBuilderInvoker;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Andrea Di Giorgi
 */
public class BuildCSSTask extends Task {

	@Override
	public void execute() throws BuildException {
		Project project = getProject();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			BuildCSSTask.class.getClassLoader());

		try {
			CSSBuilderInvoker.invoke(project.getBaseDir(), _cssBuilderArgs);
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void setDirNames(String dirNames) {
		_cssBuilderArgs.setDirNames(dirNames);
	}

	public void setDocrootDirName(String docrootDirName) {
		_cssBuilderArgs.setDocrootDirName(docrootDirName);
	}

	public void setGenerateSourceMap(boolean generateSourceMap) {
		_cssBuilderArgs.setGenerateSourceMap(generateSourceMap);
	}

	public void setPortalCommonDirName(String portalCommonDirName) {
		_cssBuilderArgs.setPortalCommonDirName(portalCommonDirName);
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