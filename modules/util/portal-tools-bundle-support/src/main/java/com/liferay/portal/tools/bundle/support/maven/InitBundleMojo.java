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
import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;
import com.liferay.portal.tools.bundle.support.internal.util.BundleSupportUtil;
import com.liferay.portal.tools.bundle.support.internal.util.MavenUtil;

import java.io.File;

import java.net.URL;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.Proxy;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Mojo(inheritByDefault = false, name = "init")
public class InitBundleMojo extends AbstractLiferayMojo {

	@Override
	public void execute() throws MojoExecutionException {
		if (project.hasParent()) {
			return;
		}

		if ((environment == null) || environment.isEmpty()) {
			environment = BundleSupportConstants.DEFAULT_ENVIRONMENT;
		}

		if (url == null) {
			url = BundleSupportConstants.DEFAULT_BUNDLE_URL_OBJECT;
		}

		Proxy proxy = MavenUtil.getProxy(_mavenSession);

		String proxyProtocol = url.getProtocol();
		String proxyHost = null;
		Integer proxyPort = null;
		String proxyUser = null;
		String proxyPassword = null;
		String nonProxyHosts = null;

		if (proxy != null) {
			proxyHost = BundleSupportUtil.setSystemProperty(
				proxyProtocol + ".proxyHost", proxy.getHost());
			proxyPort = BundleSupportUtil.setSystemProperty(
				proxyProtocol + ".proxyPort", proxy.getPort());
			proxyUser = BundleSupportUtil.setSystemProperty(
				proxyProtocol + ".proxyUser", proxy.getUsername());
			proxyPassword = BundleSupportUtil.setSystemProperty(
				proxyProtocol + ".proxyPassword", proxy.getPassword());
			nonProxyHosts = BundleSupportUtil.setSystemProperty(
				proxyProtocol + ".nonProxyHosts", proxy.getNonProxyHosts());
		}

		try {
			InitBundleCommand initBundleCommand = new InitBundleCommand();

			initBundleCommand.setCacheDir(cacheDir);
			initBundleCommand.setConfigsDir(
				new File(project.getBasedir(), configs));
			initBundleCommand.setEnvironment(environment);
			initBundleCommand.setLiferayHomeDir(getLiferayHomeDir());
			initBundleCommand.setPassword(password);
			initBundleCommand.setStripComponents(stripComponents);
			initBundleCommand.setToken(token);
			initBundleCommand.setTokenFile(tokenFile);
			initBundleCommand.setUrl(url);
			initBundleCommand.setUserName(userName);

			initBundleCommand.execute();
		}
		catch (Exception exception) {
			throw new MojoExecutionException(
				"Unable to initialize bundle", exception);
		}
		finally {
			if (proxy != null) {
				BundleSupportUtil.setSystemProperty(
					proxyProtocol + ".proxyHost", proxyHost);
				BundleSupportUtil.setSystemProperty(
					proxyProtocol + ".proxyPort", proxyPort);
				BundleSupportUtil.setSystemProperty(
					proxyProtocol + ".proxyUser", proxyUser);
				BundleSupportUtil.setSystemProperty(
					proxyProtocol + ".proxyPassword", proxyPassword);
				BundleSupportUtil.setSystemProperty(
					proxyProtocol + ".nonProxyHosts", nonProxyHosts);
			}
		}
	}

	@Parameter(
		defaultValue = "${user.home}/" + BundleSupportConstants.DEFAULT_BUNDLE_CACHE_DIR_NAME
	)
	protected File cacheDir;

	@Parameter(defaultValue = BundleSupportConstants.DEFAULT_CONFIGS_DIR_NAME)
	protected String configs;

	@Parameter(defaultValue = "${liferay.workspace.environment}")
	protected String environment;

	@Parameter
	protected String password;

	@Parameter(
		defaultValue = "" + BundleSupportConstants.DEFAULT_STRIP_COMPONENTS
	)
	protected int stripComponents;

	@Parameter
	protected boolean token;

	@Parameter(
		defaultValue = "${user.home}/" + BundleSupportConstants.DEFAULT_TOKEN_FILE_NAME
	)
	protected File tokenFile;

	@Parameter(defaultValue = "${liferay.workspace.bundle.url}")
	protected URL url;

	@Parameter
	protected String userName;

	@Parameter(property = "session", readonly = true)
	private MavenSession _mavenSession;

}