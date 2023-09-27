/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @goal build
 */
public class BuildWSDDMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			WSDDBuilderInvoker.invoke(baseDir, _wsddBuilderArgs);
		}
		catch (Exception exception) {
			throw new MojoExecutionException(exception.getMessage(), exception);
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