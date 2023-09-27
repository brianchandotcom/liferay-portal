/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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