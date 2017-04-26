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

import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;
import com.liferay.portal.tools.bundle.support.internal.util.HttpUtil;
import com.liferay.portal.tools.bundle.support.util.StreamLogger;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;

/**
 * @author David Truong
 */
public class DownloadBundleCommand extends BaseCommand implements StreamLogger {

	@Override
	public void execute() throws Exception {
		Path cacheDirPath = null;

		if (cacheDir != null) {
			cacheDirPath = cacheDir.toPath();
		}

		URI uri = url.toURI();

		if ("file".equals(uri.getScheme())) {
			bundlePath = Paths.get(uri);
		}
		else {
			if (token) {
				bundlePath = HttpUtil.downloadFile(
					uri, FileUtil.getToken(), cacheDirPath, this);
			}
			else {
				bundlePath = HttpUtil.downloadFile(
					uri, userName, password, cacheDirPath, this);
			}
		}
	}

	public File getCacheDir() {
		return cacheDir;
	}

	public String getPassword() {
		return password;
	}

	public URL getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public boolean isToken() {
		return token;
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
		onStarted("Download " + url);
	}

	public void setCacheDir(File cacheDir) {
		this.cacheDir = cacheDir;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setToken(boolean token) {
		this.token = token;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	protected static final URL DEFAULT_URL;

	static {
		try {
			DEFAULT_URL = new URL(
				"https://cdn.lfrs.sl/releases.liferay.com/portal/7.0.2-ga3" +
					"/liferay-ce-portal-tomcat-7.0-ga3-20160804222206210.zip");
		}
		catch (MalformedURLException murle) {
			throw new ExceptionInInitializerError(murle);
		}
	}

	protected Path bundlePath;

	@Parameter(
		description = "The directory where to cache the downloaded bundles.",
		names = "--cache-dir"
	)
	protected File cacheDir = new File(
		System.getProperty("user.home"), ".liferay/bundles");

	@Parameter(
		description = "The password if your URL requires authentication.",
		names = {"-p", "--password"}, password = true
	)
	protected String password;

	@Parameter(
		description = "Use token authentication.", names = {"-t", "--token"}
	)
	protected boolean token;

	@Parameter(
		description = "The URL of the Liferay Bundle to expand.",
		names = "--url"
	)
	protected URL url = DEFAULT_URL;

	@Parameter(
		description = "The user name if your URL requires authentication.",
		names = {"-u", "--username", "--user-name"}
	)
	protected String userName;

}