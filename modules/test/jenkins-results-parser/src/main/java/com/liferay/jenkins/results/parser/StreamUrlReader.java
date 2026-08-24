/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HTTPAuthorization;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.Map;

/**
 * @author Kenji Heigel
 */
public class StreamUrlReader extends UrlReader<InputStream> {

	public static String getResponseHeader(
			String headerName, HTTPAuthorization httpAuthorization,
			HttpRequestMethod httpRequestMethod, String postContent,
			int timeout, String url)
		throws IOException {

		return getResponseHeader(
			headerName, httpAuthorization, httpRequestMethod, postContent, null,
			timeout, url);
	}

	public static String getResponseHeader(
			String headerName, HTTPAuthorization httpAuthorization,
			HttpRequestMethod httpRequestMethod, String postContent,
			Map<String, String> requestHeaders, int timeout, String url)
		throws IOException {

		return _streamUrlReader.doGetResponseHeader(
			headerName, httpAuthorization, httpRequestMethod, postContent,
			requestHeaders, timeout, url);
	}

	public static InputStream read(
			boolean checkCache, HTTPAuthorization httpAuthorization,
			HttpRequestMethod httpRequestMethod, int maxRetries,
			String postContent, int retryPeriod, int timeout, String url)
		throws IOException {

		return _streamUrlReader.doRead(
			checkCache, httpAuthorization, httpRequestMethod, maxRetries,
			postContent, retryPeriod, timeout, url);
	}

	public static void setInstance(StreamUrlReader streamUrlReader) {
		_streamUrlReader = streamUrlReader;
	}

	protected String doGetResponseHeader(
			String headerName, HTTPAuthorization httpAuthorization,
			HttpRequestMethod httpRequestMethod, String postContent,
			Map<String, String> requestHeaders, int timeout, String url)
		throws IOException {

		URL urlObject = new URL(JenkinsResultsParserUtil.fixURL(url));

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)urlObject.openConnection();

		if (timeout != 0) {
			httpURLConnection.setConnectTimeout(timeout);
			httpURLConnection.setReadTimeout(timeout);
		}

		if (httpRequestMethod != null) {
			httpURLConnection.setRequestMethod(httpRequestMethod.name());
		}

		if (httpAuthorization != null) {
			httpURLConnection.setRequestProperty(
				"Authorization", httpAuthorization.toString());
		}

		if (requestHeaders != null) {
			for (Map.Entry<String, String> requestHeader :
					requestHeaders.entrySet()) {

				httpURLConnection.setRequestProperty(
					requestHeader.getKey(), requestHeader.getValue());
			}
		}

		if (postContent != null) {
			httpURLConnection.setDoOutput(true);

			try (OutputStream outputStream =
					httpURLConnection.getOutputStream()) {

				outputStream.write(postContent.getBytes("UTF-8"));

				outputStream.flush();
			}
		}

		httpURLConnection.connect();

		int responseCode = httpURLConnection.getResponseCode();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Response from ", url, ": ", String.valueOf(responseCode), " ",
				httpURLConnection.getResponseMessage()));

		if (responseCode >= 400) {
			return null;
		}

		return httpURLConnection.getHeaderField(headerName);
	}

	@Override
	protected InputStream handleCachedFile(File cachedFile) throws IOException {
		return new FileInputStream(cachedFile);
	}

	/**
	 * The stream reader cannot validate the body without buffering it, so it
	 * retries transport failures only. Writing the cache would require the same
	 * buffering, which is why <code>cacheFileKey</code> and
	 * <code>expectResponse</code> are both ignored here.
	 */
	@Override
	protected InputStream handleResponse(
			String cacheFileKey, boolean expectResponse,
			URLConnection urlConnection)
		throws IOException {

		return urlConnection.getInputStream();
	}

	private static volatile StreamUrlReader _streamUrlReader =
		new StreamUrlReader();

}