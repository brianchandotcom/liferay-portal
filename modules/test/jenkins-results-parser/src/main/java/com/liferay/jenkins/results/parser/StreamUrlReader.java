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

import java.net.URLConnection;

/**
 * @author Kenji Heigel
 */
public class StreamUrlReader extends UrlReader<InputStream> {

	public static StreamUrlReader getInstance() {
		return _streamUrlReader;
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

	@Override
	protected InputStream handleCachedFile(File cachedFile) throws IOException {
		return new FileInputStream(cachedFile);
	}

	/**
	 * The stream reader cannot validate the body without buffering it, so it
	 * retries transport failures only. Writing the cache would require the same
	 * buffering, which is why <code>cacheFileKey</code> is ignored here.
	 */
	@Override
	protected InputStream handleResponse(
			String cacheFileKey, URLConnection urlConnection)
		throws IOException {

		return urlConnection.getInputStream();
	}

	private static volatile StreamUrlReader _streamUrlReader =
		new StreamUrlReader();

}