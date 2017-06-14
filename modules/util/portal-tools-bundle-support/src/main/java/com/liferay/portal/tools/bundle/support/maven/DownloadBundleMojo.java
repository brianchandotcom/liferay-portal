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

import com.liferay.portal.tools.bundle.support.commands.DownloadBundleCommand;
import com.liferay.portal.tools.bundle.support.internal.util.BundleSupportUtil;
import com.liferay.portal.tools.bundle.support.internal.util.MavenUtil;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.settings.Proxy;

/**
 * @author David Truong
 */
@Mojo(inheritByDefault = false, name = "download")
public class DownloadBundleMojo extends AbstractBundleMojo {

	@Override
	public void execute() throws MojoExecutionException {
		if (project.hasParent()) {
			return;
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
			DownloadBundleCommand downloadCommand = new DownloadBundleCommand();

			downloadCommand.setCacheDir(cacheDir);
			downloadCommand.setLiferayHomeDir(getLiferayHomeDir());
			downloadCommand.setPassword(password);
			downloadCommand.setToken(token);
			downloadCommand.setTokenFile(tokenFile);
			downloadCommand.setUrl(url);
			downloadCommand.setUserName(userName);

			downloadCommand.execute();
		}
		catch (Exception e) {
			throw new MojoExecutionException("Unable to initialize bundle", e);
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

	@Parameter(property = "session", readonly = true)
	private MavenSession _mavenSession;

}