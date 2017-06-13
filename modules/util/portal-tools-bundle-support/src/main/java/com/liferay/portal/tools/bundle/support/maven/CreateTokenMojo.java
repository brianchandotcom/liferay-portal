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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * @author David Truong
 */
@Mojo(name = "createToken")
public class CreateTokenMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			CreateTokenCommand createTokenCommand = new CreateTokenCommand();

			createTokenCommand.execute();
		}
		catch (Exception e) {
			throw new MojoExecutionException(
				"Unable to create authentication token", e);
		}
	}

}