/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public void setForcePredictableOperationId(
		Boolean forcePredictableOperationId) {

		_restBuilderArgs.setForcePredictableOperationId(
			forcePredictableOperationId);
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