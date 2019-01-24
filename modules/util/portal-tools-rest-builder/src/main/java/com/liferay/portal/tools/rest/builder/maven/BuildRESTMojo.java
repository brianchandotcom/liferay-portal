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

package com.liferay.portal.tools.rest.builder.maven;

import com.liferay.portal.tools.rest.builder.RESTBuilderArgs;
import com.liferay.portal.tools.rest.builder.RESTBuilderInvoker;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Builds Liferay REST services.
 *
 * @author Peter Shin
 * @goal build
 */
public class BuildRESTMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			RESTBuilderInvoker.invoke(baseDir, _restBuilderArgs);
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	/**
	 * @parameter
	 */
	public void setApiDirName(String apiDirName) {
		_restBuilderArgs.setApiDirName(apiDirName);
	}

	/**
	 * @parameter
	 */
	public void setApiPackagePath(String apiPackagePath) {
		_restBuilderArgs.setApiPackagePath(apiPackagePath);
	}

	/**
	 * @parameter
	 */
	public void setAuthor(String author) {
		_restBuilderArgs.setAuthor(author);
	}

	/**
	 * @parameter
	 */
	public void setInputFileName(String inputFileName) {
		_restBuilderArgs.setInputFileName(inputFileName);
	}

	/**
	 * @parameter default-value="${project.basedir}"
	 * @readonly
	 */
	protected File baseDir;

	private final RESTBuilderArgs _restBuilderArgs = new RESTBuilderArgs();

}