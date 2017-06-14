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
@Mojo(name = "create-token")
public class CreateTokenMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			CreateTokenCommand createTokenCommand = new CreateTokenCommand();

			createTokenCommand.setEmailAddress(emailAddress);
			createTokenCommand.setPassword(password);
			createTokenCommand.setTokenFile(tokenFile);
			createTokenCommand.setTokenUrl(tokenUrl);

			createTokenCommand.execute();
		}
		catch (Exception e) {
			throw new MojoExecutionException(
				"Unable to create Liferay.com download token", e);
		}
	}

	@Parameter
	protected String emailAddress;

	@Parameter
	protected String password;

	@Parameter(
		defaultValue = "${user.home}/" + BundleSupportConstants.DEFAULT_TOKEN_FILE_NAME
	)
	protected File tokenFile;

	@Parameter(defaultValue = BundleSupportConstants.DEFAULT_BUNDLE_URL)
	protected URL tokenUrl;

}