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

import com.liferay.portal.tools.rest.builder.RESTBuilder;
import com.liferay.portal.tools.rest.builder.RESTBuilderArgs;

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
			RESTBuilder restBuilder = new RESTBuilder(_restBuilderArgs);

			restBuilder.build();
		}
		catch (Exception exception) {
			throw new MojoExecutionException(exception.getMessage(), exception);
		}
	}

	/**
	 * @parameter
	 */
	public void setCopyrightFile(File copyrightFile) {
		_restBuilderArgs.setCopyrightFile(copyrightFile);
	}

	/**
	 * @parameter
	 */
	public void setForceClientVersionDescription(
		Boolean forceClientVersionDescription) {

		_restBuilderArgs.setForceClientVersionDescription(
			forceClientVersionDescription);
	}

	/**
	 * @parameter
	 */
	public void setRESTConfigDir(File restConfigDir) {
		_restBuilderArgs.setRESTConfigDir(restConfigDir);
	}

	/**
	 * @parameter default-value="${project.basedir}"
	 * @readonly
	 */
	protected File baseDir;

	private final RESTBuilderArgs _restBuilderArgs = new RESTBuilderArgs();

}