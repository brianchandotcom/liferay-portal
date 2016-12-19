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
import com.beust.jcommander.converters.FileConverter;

import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URI;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * @author David Truong
 */
@Parameters(
	commandDescription = "Download and expand a new bundle.",
	commandNames = "initBundle"
)
public class InitBundleCommand extends BaseCommand {

	@Override
	public void execute() throws Exception {
		deleteBundle();

		downloadFile();

		unpack();

		copyConfigs();
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

	public String getProxyHost() {
		return _proxyHost;
	}

	public String getProxyPassword() {
		return _proxyPassword;
	}

	public int getProxyPort() {
		return _proxyPort;
	}

	public String getProxyProtocol() {
		return _proxyProtocol;
	}

	public String getProxyUsername() {
		return _proxyUsername;
	}

	public int getStripComponents() {
		return _stripComponents;
	}

	public String getUrl() {
		return _url;
	}

	public String getUsername() {
		return _username;
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

	public void setProxyHost(String proxyHost) {
		_proxyHost = proxyHost;
	}

	public void setProxyPassword(String proxyPassword) {
		_proxyPassword = proxyPassword;
	}

	public void setProxyPort(int proxyPort) {
		_proxyPort = proxyPort;
	}

	public void setProxyProtocol(String proxyProtocol) {
		_proxyProtocol = proxyProtocol;
	}

	public void setProxyUsername(String proxyUsername) {
		_proxyUsername = proxyUsername;
	}

	public void setStripComponents(int stripComponents) {
		_stripComponents = stripComponents;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setUsername(String username) {
		_username = username;
	}

	protected void copyConfigs() throws IOException {
		if ((_configsDir == null) || !_configsDir.exists()) {
			return;
		}

		File configsCommonDir = new File(_configsDir, "common");

		if (configsCommonDir.exists()) {
			FileUtil.copyDirectory(configsCommonDir, getLiferayHomeDir());
		}

		File configsEnvDir = new File(_configsDir, _environment);

		if (configsEnvDir.exists()) {
			FileUtil.copyDirectory(configsEnvDir, getLiferayHomeDir());
		}
	}

	protected void deleteBundle() throws IOException {
		File liferayHomeDir = getLiferayHomeDir();

		if (!liferayHomeDir.exists()) {
			return;
		}

		FileUtil.deleteDirectory(liferayHomeDir.toPath());
	}

	protected void downloadFile() throws Exception {
		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

		requestConfigBuilder.setRedirectsEnabled(true);

		requestConfigBuilder.setCookieSpec(CookieSpecs.STANDARD);

		HttpClientBuilder httpClientBuilder = HttpClients.custom();

		httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());

		CredentialsProvider credentialsProvider =
			new BasicCredentialsProvider();

		httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

		if (_proxyHost != null) {
			HttpHost proxyHttpHost = new HttpHost(
				_proxyHost, _proxyPort, _proxyProtocol);

			requestConfigBuilder.setProxy(proxyHttpHost);

			if (_proxyUsername != null) {
				credentialsProvider.setCredentials(
					new AuthScope(_proxyHost, _proxyPort),
					new UsernamePasswordCredentials(
						_proxyUsername, _proxyPassword));
			}
		}

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			URI uri = new URI(_url);

			HttpHead httpHead = new HttpHead(uri);

			HttpContext httpContext = new BasicHttpContext();

			if ((_username != null) && (_password != null)) {
				credentialsProvider.setCredentials(
					new AuthScope(uri.getHost(), uri.getPort()),
					new UsernamePasswordCredentials(_username, _password));
			}

			Date lastModified = null;

			try (CloseableHttpResponse closeableHttpResponse =
					closeableHttpClient.execute(httpHead, httpContext)) {

				Header dispositionHeader = closeableHttpResponse.getFirstHeader(
					"Content-Disposition");

				if (dispositionHeader != null) {
					String dispositionValue = dispositionHeader.getValue();

					int index = dispositionValue.indexOf("filename=");

					if (index > 0) {
						_downloadFileName = dispositionValue.substring(
							index + 10, dispositionValue.length() - 1);
					}
				}
				else {
					RedirectLocations redirectLocations = (RedirectLocations)
						httpContext.getAttribute(
							HttpClientContext.REDIRECT_LOCATIONS);

					if (redirectLocations != null) {
						List<URI> redirectLocationsList =
							redirectLocations.getAll();

						uri = redirectLocationsList.get(
							redirectLocationsList.size() - 1);
					}

					_downloadFileName = getDownloadFileName(uri);
				}

				Header lastModifiedHeader =
					closeableHttpResponse.getFirstHeader("Last-Modified");

				if (lastModifiedHeader != null) {
					String lastModifiedValue = lastModifiedHeader.getValue();

					SimpleDateFormat dateFormat = new SimpleDateFormat(
						"EEE, dd MMM yyyy HH:mm:ss zzz");

					lastModified = dateFormat.parse(lastModifiedValue);
				}
				else {
					lastModified = new Date();
				}
			}

			_downloadFile = new File(_BUNDLES_CACHE, _downloadFileName);

			if (_downloadFile.exists() &&
				(_downloadFile.lastModified() == lastModified.getTime())) {

				return;
			}
			else if (_downloadFile.exists()) {
				_downloadFile.delete();
			}

			if (!_BUNDLES_CACHE.exists()) {
				_BUNDLES_CACHE.mkdirs();
			}

			_downloadFile.createNewFile();

			HttpGet httpGet = new HttpGet(uri);

			try (FileOutputStream fileOutputStream = new FileOutputStream(
					_downloadFile);

				CloseableHttpResponse closeableHttpResponse =
					closeableHttpClient.execute(httpGet)) {

				HttpEntity httpEntity = closeableHttpResponse.getEntity();

				fileOutputStream.write(EntityUtils.toByteArray(httpEntity));

				_downloadFile.setLastModified(lastModified.getTime());
			}
		}
	}

	protected String getDownloadFileName(URI uri) {
		String fileName = uri.getPath();

		int index = fileName.lastIndexOf("/") + 1;

		fileName = fileName.substring(index);

		return fileName;
	}

	protected void unpack() throws Exception {
		String extension = FileUtil.getExtension(_downloadFile.getName());

		if (extension.equals("zip")) {
			FileUtil.unzip(
				_downloadFile, getLiferayHomePath(), _stripComponents);
		}
		else if (extension.equals("gz") || extension.equals("tar") ||
				 extension.equals("tar.gz") || extension.equals("tgz")) {

			FileUtil.untar(
				_downloadFile, getLiferayHomePath(), _stripComponents);
		}
	}

	private static final File _BUNDLES_CACHE = new File(
		System.getProperty("user.home"), ".liferay/bundles");

	private static final int _DEFAULT_STRIP_COMPONENTS = 1;

	private static final String _DEFAULT_URL =
		"https://sourceforge.net/projects/lportal/files/Liferay%20Portal/7.0." +
			"2%20GA3/liferay-ce-portal-tomcat-7.0-ga3-20160804222206210.zip";

	@Parameter(
		converter = FileConverter.class,
		description = "The directory that contains the configuration files.",
		names = {"--configs"}
	)
	private File _configsDir;

	private File _downloadFile;
	private String _downloadFileName;

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
		description = "The host name of your proxy.", names = {"--proxy-host"}
	)
	private String _proxyHost;

	@Parameter (
		description = "The password if your proxy requires authentication.",
		names = {"--proxy-password"}, password = true
	)
	private String _proxyPassword;

	@Parameter (
		description = "The port of your proxy.", names = {"--proxy-port"}
	)
	private int _proxyPort;

	@Parameter (
		description = "The protocol of your proxy.",
		names = {"--proxy-protocol"}
	)
	private String _proxyProtocol;

	@Parameter (
		description = "The user name if your proxy requires authentication.",
		names = {"--proxy-username"}
	)
	private String _proxyUsername;

	@Parameter (
		description = "The number of directories to strip when expanding your bundle.",
		names = {"--strip-components"}
	)
	private int _stripComponents = _DEFAULT_STRIP_COMPONENTS;

	@Parameter (
		description = "The URL of the Liferay Bundle to expand.",
		names = {"--url"}
	)
	private String _url = _DEFAULT_URL;

	@Parameter (
		description = "The user name if your URL requires authentication.",
		names = {"-u", "--username"}
	)
	private String _username;

}