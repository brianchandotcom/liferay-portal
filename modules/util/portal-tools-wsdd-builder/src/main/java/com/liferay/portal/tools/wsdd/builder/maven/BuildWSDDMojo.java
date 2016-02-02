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

package com.liferay.portal.tools.wsdd.builder.maven;

import com.liferay.portal.tools.wsdd.builder.WSDDBuilderArgs;
import com.liferay.portal.tools.wsdd.builder.WSDDBuilderInvoker;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Builds the WSDD files.
 *
 * @author Andrea Di Giorgi
 * @goal build-wsdd
 */
public class BuildWSDDMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			WSDDBuilderInvoker.invoke(baseDir, _wsddBuilderArgs);
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	/**
	 * @parameter
	 */
	public void setClassPath(String classPath) {
		_wsddBuilderArgs.setClassPath(classPath);
	}

	/**
	 * @parameter
	 */
	public void setInputFileName(String inputFileName) {
		_wsddBuilderArgs.setFileName(inputFileName);
	}

	/**
	 * @parameter
	 */
	public void setOutputDirName(String outputDirName) {
		_wsddBuilderArgs.setOutputPath(outputDirName);
	}

	/**
	 * @parameter
	 */
	public void setServerConfigFileName(String serverConfigFileName) {
		_wsddBuilderArgs.setServerConfigFileName(serverConfigFileName);
	}

	/**
	 * @parameter
	 */
	public void setServiceNamespace(String serviceNamespace) {
		_wsddBuilderArgs.setServiceNamespace(serviceNamespace);
	}

	/**
	 * @parameter default-value="${project.basedir}"
	 * @readonly
	 */
	protected File baseDir;

	private final WSDDBuilderArgs _wsddBuilderArgs = new WSDDBuilderArgs();

}