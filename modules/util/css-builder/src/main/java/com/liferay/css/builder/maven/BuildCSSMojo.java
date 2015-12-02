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

package com.liferay.css.builder.maven;

import com.liferay.css.builder.CSSBuilderArgs;
import com.liferay.css.builder.CSSBuilderInvoker;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Compiles CSS files.
 *
 * @author Andrea Di Giorgi
 * @goal build-css
 */
public class BuildCSSMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			CSSBuilderInvoker.invoke(baseDir, _cssBuilderArgs);
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	/**
	 * @parameter
	 */
	public void setDirNames(String dirNames) {
		_cssBuilderArgs.setDirNames(dirNames);
	}

	/**
	 * @parameter
	 */
	public void setDocrootDirName(String docrootDirName) {
		_cssBuilderArgs.setDocrootDirName(docrootDirName);
	}

	/**
	 * @parameter
	 */
	public void setGenerateSourceMap(boolean generateSourceMap) {
		_cssBuilderArgs.setGenerateSourceMap(generateSourceMap);
	}

	/**
	 * @parameter
	 * @required
	 */
	public void setPortalCommonDirName(String portalCommonDirName) {
		_cssBuilderArgs.setPortalCommonDirName(portalCommonDirName);
	}

	/**
	 * @parameter
	 */
	public void setPrecision(int precision) {
		_cssBuilderArgs.setPrecision(precision);
	}

	/**
	 * @parameter
	 */
	public void setRtlExcludedPathRegexps(String rtlExcludedPathRegexps) {
		_cssBuilderArgs.setRtlExcludedPathRegexps(rtlExcludedPathRegexps);
	}

	/**
	 * @parameter
	 */
	public void setSassCompilerClassName(String sassCompilerClassName) {
		_cssBuilderArgs.setSassCompilerClassName(sassCompilerClassName);
	}

	/**
	 * @parameter default-value="${project.basedir}
	 * @readonly
	 */
	protected File baseDir;

	private final CSSBuilderArgs _cssBuilderArgs = new CSSBuilderArgs();

}