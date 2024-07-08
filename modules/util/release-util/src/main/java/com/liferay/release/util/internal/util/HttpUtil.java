/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.util.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import java.util.Date;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class HttpUtil {

	public static Path downloadFile(
			URI uri, String userName, String password, Path cacheDirPath,
			int connectionTimeout)
		throws Exception {

		Path path;

		try (CloseableHttpClient closeableHttpClient = _getCloseableHttpClient(
				uri, userName, password, connectionTimeout)) {

			path = _downloadFile(closeableHttpClient, uri, cacheDirPath);
		}

		return path;
	}

	private static void _checkResponseStatus(HttpResponse httpResponse)
		throws IOException {

		StatusLine statusLine = httpResponse.getStatusLine();

		if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
			throw new IOException(statusLine.getReasonPhrase());
		}
	}

	private static Path _downloadFile(
			CloseableHttpClient closeableHttpClient, URI uri, Path cacheDirPath)
		throws Exception {

		String fileName = null;
		Date lastModifiedDate = new Date();

		HttpHead httpHead = new HttpHead(uri);
		HttpContext httpContext = new BasicHttpContext();

		try (CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpHead, httpContext)) {

			_checkResponseStatus(closeableHttpResponse);

			Header dispositionHeader = closeableHttpResponse.getFirstHeader(
				"Content-Disposition");

			if (dispositionHeader != null) {
				String dispositionValue = dispositionHeader.getValue();

				int index = dispositionValue.indexOf("filename=");

				if (index > 0) {
					fileName = dispositionValue.substring(
						index + "filename=".length());
				}
			}
			else {
				RedirectLocations redirectLocations =
					(RedirectLocations)httpContext.getAttribute(
						HttpClientContext.REDIRECT_LOCATIONS);

				if (redirectLocations != null) {
					uri = redirectLocations.get(redirectLocations.size() - 1);
				}
			}

			Header lastModifiedHeader = closeableHttpResponse.getFirstHeader(
				HttpHeaders.LAST_MODIFIED);

			if (lastModifiedHeader != null) {
				lastModifiedDate = DateUtils.parseDate(
					lastModifiedHeader.getValue());
			}
		}

		if (fileName == null) {
			String uriPath = uri.getPath();

			fileName = uriPath.substring(uriPath.lastIndexOf('/') + 1);
		}

		if (cacheDirPath == null) {
			cacheDirPath = Files.createTempDirectory(null);
		}

		Path path = cacheDirPath.resolve(fileName);

		if (Files.exists(path)) {
			FileTime fileTime = Files.getLastModifiedTime(path);

			if (fileTime.toMillis() == lastModifiedDate.getTime()) {
				return path;
			}

			Files.delete(path);
		}

		Files.createDirectories(cacheDirPath);

		HttpGet httpGet = new HttpGet(uri);

		try (CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpGet)) {

			_checkResponseStatus(closeableHttpResponse);

			HttpEntity httpEntity = closeableHttpResponse.getEntity();

			try (InputStream inputStream = httpEntity.getContent();
				OutputStream outputStream = Files.newOutputStream(path)) {

				byte[] buffer = new byte[10 * 1024];
				int read = -1;

				while ((read = inputStream.read(buffer)) >= 0) {
					outputStream.write(buffer, 0, read);
				}
			}
		}

		Files.setLastModifiedTime(
			path, FileTime.fromMillis(lastModifiedDate.getTime()));

		return path;
	}

	private static CloseableHttpClient _getCloseableHttpClient(
		URI uri, String userName, String password, int connectionTimeout) {

		HttpClientBuilder httpClientBuilder = _getHttpClientBuilder(
			uri, userName, password, connectionTimeout);

		return httpClientBuilder.build();
	}

	private static HttpClientBuilder _getHttpClientBuilder(
		URI uri, String userName, String password, int connectionTimeout) {

		HttpClientBuilder httpClientBuilder = HttpClients.custom();

		CredentialsProvider credentialsProvider =
			new BasicCredentialsProvider();

		httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

		requestConfigBuilder.setConnectTimeout(connectionTimeout);
		requestConfigBuilder.setCookieSpec(CookieSpecs.STANDARD);
		requestConfigBuilder.setRedirectsEnabled(true);

		httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());

		if ((userName != null) && (password != null)) {
			credentialsProvider.setCredentials(
				new AuthScope(uri.getHost(), uri.getPort()),
				new UsernamePasswordCredentials(userName, password));
		}

		String scheme = uri.getScheme();

		String proxyHost = System.getProperty(scheme + ".proxyHost");
		String proxyPort = System.getProperty(scheme + ".proxyPort");
		String proxyUser = System.getProperty(scheme + ".proxyUser");
		String proxyPassword = System.getProperty(scheme + ".proxyPassword");

		if ((proxyHost != null) && (proxyPort != null) && (proxyUser != null) &&
			(proxyPassword != null)) {

			credentialsProvider.setCredentials(
				new AuthScope(proxyHost, Integer.parseInt(proxyPort)),
				new UsernamePasswordCredentials(proxyUser, proxyPassword));
		}

		httpClientBuilder.useSystemProperties();

		return httpClientBuilder;
	}

}