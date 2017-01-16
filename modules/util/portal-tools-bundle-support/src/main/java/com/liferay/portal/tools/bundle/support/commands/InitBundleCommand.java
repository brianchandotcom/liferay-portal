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

package com.liferay.portal.tools.bundle.support.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Parameters(
	commandDescription = "Download and expand a new bundle.",
	commandNames = "initBundle"
)
public class InitBundleCommand extends BaseCommand {

	@Override
	public void execute() throws Exception {
		_deleteBundle();

		Path path = FileUtil.downloadFile(
			_url.toURI(), _userName, _password, bundlesCacheDirPath);

		FileUtil.unpack(path, getLiferayHomePath(), _stripComponents);

		_copyConfigs();
	}

	public File getConfigsDir() {
		return _configsDir;
	}

	public String getEnvironment() {
		return _environment;
	}

	public String getPassword() {
		return _password;
	}

	public int getStripComponents() {
		return _stripComponents;
	}

	public URL getUrl() {
		return _url;
	}

	public String getUserName() {
		return _userName;
	}

	public void setConfigsDir(File configsDir) {
		_configsDir = configsDir;
	}

	public void setEnvironment(String environment) {
		_environment = environment;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setStripComponents(int stripComponents) {
		_stripComponents = stripComponents;
	}

	public void setUrl(URL url) {
		_url = url;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	private void _copyConfigs() throws IOException {
		if ((_configsDir == null) || !_configsDir.exists()) {
			return;
		}

		Path configsDirPath = _configsDir.toPath();

		Path configsCommonDirPath = configsDirPath.resolve("common");

		if (Files.exists(configsCommonDirPath)) {
			FileUtil.copyDirectory(configsCommonDirPath, getLiferayHomePath());
		}

		Path configsEnvironmentDirPath = configsDirPath.resolve(_environment);

		if (Files.exists(configsEnvironmentDirPath)) {
			FileUtil.copyDirectory(
				configsEnvironmentDirPath, getLiferayHomePath());
		}
	}

	private void _deleteBundle() throws IOException {
		Path dirPath = getLiferayHomePath();

		if (Files.exists(dirPath)) {
			FileUtil.deleteDirectory(dirPath);
		}
	}

	protected static final Path bundlesCacheDirPath = Paths.get(
		System.getProperty("user.home"), ".liferay/bundles");

	private static final int _DEFAULT_STRIP_COMPONENTS = 1;

	private static final URL _DEFAULT_URL;

	static {
		try {
			_DEFAULT_URL = new URL(
				"https://sourceforge.net/projects/lportal/files/Liferay%20" +
					"Portal/7.0.2%20GA3/liferay-ce-portal-tomcat-7.0-ga3-" +
						"20160804222206210.zip");
		}
		catch (MalformedURLException murle) {
			throw new ExceptionInInitializerError(murle);
		}
	}

	@Parameter(
		description = "The directory that contains the configuration files.",
		names = {"--configs"}
	)
	private File _configsDir;

	@Parameter (
		description = "The environment of your Liferay home deployment.",
		names = {"--environment"}
	)
	private String _environment;

	@Parameter (
		description = "The password if your URL requires authentication.",
		names = {"-p", "--password"}, password = true
	)
	private String _password;

	@Parameter (
		description = "The number of directories to strip when expanding your bundle.",
		names = {"--strip-components"}
	)
	private int _stripComponents = _DEFAULT_STRIP_COMPONENTS;

	@Parameter (
		description = "The URL of the Liferay Bundle to expand.",
		names = {"--url"}
	)
	private URL _url = _DEFAULT_URL;

	@Parameter (
		description = "The user name if your URL requires authentication.",
		names = {"-u", "--username", "--user-name"}
	)
	private String _userName;

}