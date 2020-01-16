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

import com.liferay.portal.tools.bundle.support.commands.CopyConfigsCommand;
import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;

import java.io.File;

import java.util.Arrays;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author David Truong
 */
@Mojo(inheritByDefault = false, name = "copy-configs")
public class CopyConfigsMojo extends AbstractLiferayMojo {

	@Override
	public void execute() throws MojoExecutionException {
		if (project.hasParent()) {
			return;
		}

		if ((environment == null) || environment.isEmpty()) {
			environment = BundleSupportConstants.DEFAULT_ENVIRONMENT;
		}

		try {
			CopyConfigsCommand copyConfigsCommand = new CopyConfigsCommand();

			copyConfigsCommand.setConfigsDirs(
				Arrays.asList(new File(configsDirName)));
			copyConfigsCommand.setEnvironment(environment);
			copyConfigsCommand.setLiferayHomeDir(getLiferayHomeDir());

			copyConfigsCommand.execute();
		}
		catch (Exception exception) {
			throw new MojoExecutionException(
				"Unable to initialize bundle", exception);
		}
	}

	@Parameter(defaultValue = BundleSupportConstants.DEFAULT_CONFIGS_DIR_NAME)
	protected String configsDirName;

	@Parameter(defaultValue = "${liferay.workspace.environment}")
	protected String environment;

	@Parameter(property = "session", readonly = true)
	private MavenSession _mavenSession;

}