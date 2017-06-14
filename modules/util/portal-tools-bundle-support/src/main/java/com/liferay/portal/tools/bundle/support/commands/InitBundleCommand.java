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

import com.liferay.portal.tools.bundle.support.internal.BundleSupportConstants;
import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;
import com.liferay.portal.tools.bundle.support.internal.util.HttpUtil;
import com.liferay.portal.tools.bundle.support.util.StreamLogger;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;

import java.util.Arrays;
import java.util.Set;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Parameters(
	commandDescription = "Download and expand a new bundle.",
	commandNames = "initBundle"
)
public class InitBundleCommand extends BaseCommand implements StreamLogger {

	@Override
	public void execute() throws Exception {
		_deleteBundle();

		Path cacheDirPath = null;

		if (_cacheDir != null) {
			cacheDirPath = _cacheDir.toPath();
		}

		Path path;

		URI uri = _url.toURI();

		if ("file".equals(uri.getScheme())) {
			path = Paths.get(uri);
		}
		else if (_token) {
			String tokenContent = FileUtil.read(_tokenFile);

			path = HttpUtil.downloadFile(uri, tokenContent, cacheDirPath, this);
		}
		else {
			path = HttpUtil.downloadFile(
				uri, _userName, _password, cacheDirPath, this);
		}

		File liferayHomeDir = getLiferayHomeDir();

		FileUtil.unpack(path, liferayHomeDir.toPath(), _stripComponents);

		_copyConfigs();
		_fixPosixFilePermissions();
	}

	public File getCacheDir() {
		return _cacheDir;
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

	public File getTokenFile() {
		return _tokenFile;
	}

	public URL getUrl() {
		return _url;
	}

	public String getUserName() {
		return _userName;
	}

	public boolean isToken() {
		return _token;
	}

	@Override
	public void onCompleted() {
		System.out.println();
	}

	@Override
	public void onProgress(long completed, long length) {
		StringBuilder sb = new StringBuilder();

		sb.append(FileUtil.getFileLength(completed));

		if (length > 0) {
			sb.append('/');
			sb.append(FileUtil.getFileLength(length));
		}

		sb.append(" downloaded");

		onProgress(sb.toString());
	}

	@Override
	public void onStarted() {
		onStarted("Download " + _url);
	}

	public void setCacheDir(File cacheDir) {
		_cacheDir = cacheDir;
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

	public void setToken(boolean token) {
		_token = token;
	}

	public void setTokenFile(File tokenFile) {
		_tokenFile = tokenFile;
	}

	public void setUrl(URL url) {
		_url = url;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	protected void onProgress(String message) {
		char[] chars = new char[80];

		System.arraycopy(message.toCharArray(), 0, chars, 0, message.length());

		Arrays.fill(chars, message.length(), chars.length - 2, ' ');

		chars[chars.length - 1] = '\r';

		System.out.print(chars);
	}

	protected void onStarted(String message) {
		System.out.println(message);
	}

	private void _copyConfigs() throws IOException {
		if ((_configsDir == null) || !_configsDir.exists()) {
			return;
		}

		Path configsDirPath = _configsDir.toPath();

		Path configsCommonDirPath = configsDirPath.resolve("common");

		File liferayHomeDir = getLiferayHomeDir();

		Path liferayHomeDirPath = liferayHomeDir.toPath();

		if (Files.exists(configsCommonDirPath)) {
			FileUtil.copyDirectory(configsCommonDirPath, liferayHomeDirPath);
		}

		Path configsEnvironmentDirPath = configsDirPath.resolve(_environment);

		if (Files.exists(configsEnvironmentDirPath)) {
			FileUtil.copyDirectory(
				configsEnvironmentDirPath, liferayHomeDirPath);
		}
	}

	private void _deleteBundle() throws IOException {
		File dir = getLiferayHomeDir();

		FileUtil.deleteDirectory(dir.toPath());
	}

	private void _fixPosixFilePermissions() throws IOException {
		File dir = getLiferayHomeDir();

		Path dirPath = dir.toPath();

		if (!FileUtil.isPosixSupported(dirPath)) {
			return;
		}

		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(path.getFileName());

					if (fileName.endsWith(".sh")) {
						Files.setPosixFilePermissions(
							path, _shPosixFilePermissions);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static final URL _DEFAULT_URL;

	private static final Set<PosixFilePermission> _shPosixFilePermissions =
		PosixFilePermissions.fromString("rwxr-x---");

	static {
		try {
			_DEFAULT_URL = new URL(BundleSupportConstants.DEFAULT_BUNDLE_URL);
		}
		catch (MalformedURLException murle) {
			throw new ExceptionInInitializerError(murle);
		}
	}

	@Parameter(
		description = "The directory where to cache the downloaded bundles.",
		names = "--cache-dir"
	)
	private File _cacheDir = new File(
		System.getProperty("user.home"),
		BundleSupportConstants.DEFAULT_BUNDLE_CACHE_DIR_NAME);

	@Parameter(
		description = "The directory that contains the configuration files.",
		names = "--configs"
	)
	private File _configsDir;

	@Parameter(
		description = "The environment of your Liferay home deployment.",
		names = "--environment"
	)
	private String _environment = BundleSupportConstants.DEFAULT_ENVIRONMENT;

	@Parameter(
		description = "The password if your URL requires authentication.",
		names = {"-p", "--password"}, password = true
	)
	private String _password;

	@Parameter(
		description = "The number of directories to strip when expanding your bundle.",
		names = "--strip-components"
	)
	private int _stripComponents =
		BundleSupportConstants.DEFAULT_STRIP_COMPONENTS;

	@Parameter(
		description = "Use token authentication.", names = {"-t", "--token"}
	)
	private boolean _token;

	@Parameter(
		description = "The file where your Liferay.com download token is stored.",
		names = "--token-file"
	)
	private File _tokenFile = CreateTokenCommand.DEFAULT_TOKEN_FILE;

	@Parameter(
		description = "The URL of the Liferay Bundle to expand.",
		names = "--url"
	)
	private URL _url = _DEFAULT_URL;

	@Parameter(
		description = "The user name if your URL requires authentication.",
		names = {"-u", "--username", "--user-name"}
	)
	private String _userName;

}