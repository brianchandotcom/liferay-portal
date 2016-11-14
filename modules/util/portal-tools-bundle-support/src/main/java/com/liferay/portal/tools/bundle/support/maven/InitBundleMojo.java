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

import com.liferay.portal.tools.bundle.support.commands.InitBundleCommand;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * @author David Truong
 */
@Mojo(inheritByDefault = false, name = "init-bundle")
public class InitBundleMojo extends AbstractBundleMojo {

	@Override
	public void execute() throws MojoExecutionException {
		if (project.hasParent()) {
			return;
		}

		try {
			InitBundleCommand initBundleCommand = new InitBundleCommand();

			initBundleCommand.setConfigsDir(
				new File(project.getBasedir(), configs));
			initBundleCommand.setEnvironment(environment);
			initBundleCommand.setLiferayHomeDir(getLiferayHomeDir());
			initBundleCommand.setPassword(password);
			initBundleCommand.setProxyHost(proxyHost);
			initBundleCommand.setProxyPassword(proxyPassword);
			initBundleCommand.setProxyPort(proxyPort);
			initBundleCommand.setProxyProtocol(proxyProtocol);
			initBundleCommand.setProxyUsername(proxyUsername);
			initBundleCommand.setStripComponents(stripComponents);
			initBundleCommand.setUrl(url.toString());
			initBundleCommand.setUsername(username);

			initBundleCommand.execute();
		}
		catch (Exception e) {
			throw new MojoExecutionException("Unable to initialize bundle", e);
		}
	}

}