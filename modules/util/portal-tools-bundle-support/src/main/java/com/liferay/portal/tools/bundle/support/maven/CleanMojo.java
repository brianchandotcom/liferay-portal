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

import com.liferay.portal.tools.bundle.support.commands.CleanCommand;

import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author David Truong
 */
@Mojo(name = "clean")
public class CleanMojo extends AbstractLiferayMojo {

	@Override
	public void execute() throws MojoExecutionException {
		String packaging = project.getPackaging();

		if (!packaging.equals("jar") && !packaging.equals("war")) {
			return;
		}

		try {
			CleanCommand cleanCommand = new CleanCommand();

			cleanCommand.setFileName(fileName);
			cleanCommand.setLiferayHomeDir(getLiferayHomeDir());

			cleanCommand.execute();
		}
		catch (IOException ioe) {
			throw new MojoExecutionException(ioe.getMessage(), ioe);
		}
	}

	@Parameter(
		defaultValue = "${project.artifactId}.${project.packaging}",
		required = true
	)
	protected String fileName;

}