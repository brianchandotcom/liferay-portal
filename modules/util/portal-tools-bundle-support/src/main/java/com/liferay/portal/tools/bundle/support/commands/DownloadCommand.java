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

import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;
import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;
import com.liferay.portal.tools.bundle.support.internal.util.HttpUtil;
import com.liferay.portal.tools.bundle.support.util.StreamLogger;

import java.io.File;

import java.net.URI;
import java.net.URL;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Parameters(commandDescription = "Download a file.", commandNames = "download")
public class DownloadCommand extends BaseCommand implements StreamLogger {

	@Override
	public void execute() throws Exception {
		Path cacheDirPath = null;

		if (_cacheDir != null) {
			cacheDirPath = _cacheDir.toPath();
		}

		URI uri = _url.toURI();

		if (Objects.equals(uri.getScheme(), "file")) {
			_downloadPath = Paths.get(uri);
		}
		else if (_token) {
			String tokenContent = FileUtil.read(_tokenFile);

			tokenContent = tokenContent.trim();

			_downloadPath = HttpUtil.downloadFile(
				uri, tokenContent, cacheDirPath, this);
		}
		else {
			_downloadPath = HttpUtil.downloadFile(
				uri, _userName, _password, cacheDirPath, this);
		}
	}

	public File getCacheDir() {
		return _cacheDir;
	}

	public Path getDownloadPath() {
		return _downloadPath;
	}

	public String getPassword() {
		return _password;
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

	public void setPassword(String password) {
		_password = password;
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

	@Parameter(
		description = "The directory where to cache the downloaded bundles.",
		names = "--cache-dir"
	)
	private File _cacheDir = new File(
		System.getProperty("user.home"),
		BundleSupportConstants.DEFAULT_BUNDLE_CACHE_DIR_NAME);

	private Path _downloadPath;

	@Parameter(
		description = "The password if your URL requires authentication.",
		names = {"-p", "--password"}, password = true
	)
	private String _password;

	@Parameter(
		description = "Use token authentication.", names = {"-t", "--token"}
	)
	private boolean _token;

	@Parameter(
		description = "The file where your liferay.com download token is stored.",
		names = "--token-file"
	)
	private File _tokenFile = BundleSupportConstants.DEFAULT_TOKEN_FILE;

	@Parameter(
		description = "The URL of the Liferay Bundle to expand.",
		names = "--url"
	)
	private URL _url = BundleSupportConstants.DEFAULT_BUNDLE_URL_OBJECT;

	@Parameter(
		description = "The user name if your URL requires authentication.",
		names = {"-u", "--username", "--user-name"}
	)
	private String _userName;

}