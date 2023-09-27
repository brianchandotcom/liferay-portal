/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.bundle.support.maven;

import com.liferay.portal.tools.bundle.support.commands.CreateTokenCommand;
import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;

import java.io.File;

import java.net.URL;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Mojo(name = "create-token", requiresDirectInvocation = true)
public class CreateTokenMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			CreateTokenCommand createTokenCommand = new CreateTokenCommand();

			createTokenCommand.setEmailAddress(emailAddress);
			createTokenCommand.setForce(force);
			createTokenCommand.setPassword(password);
			createTokenCommand.setPasswordFile(passwordFile);
			createTokenCommand.setTokenFile(tokenFile);
			createTokenCommand.setTokenUrl(tokenUrl);

			createTokenCommand.execute();
		}
		catch (Exception exception) {
			throw new MojoExecutionException(
				"Unable to create liferay.com download token", exception);
		}
	}

	@Parameter
	protected String emailAddress;

	@Parameter
	protected boolean force;

	@Parameter
	protected String password;

	@Parameter
	protected File passwordFile;

	@Parameter(
		defaultValue = "${user.home}/" + BundleSupportConstants.DEFAULT_TOKEN_FILE_NAME
	)
	protected File tokenFile;

	@Parameter(defaultValue = BundleSupportConstants.DEFAULT_TOKEN_URL)
	protected URL tokenUrl;

}