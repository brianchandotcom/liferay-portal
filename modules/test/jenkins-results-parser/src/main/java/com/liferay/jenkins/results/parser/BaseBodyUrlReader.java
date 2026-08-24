/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URLConnection;

/**
 * @author Kenji Heigel
 */
public abstract class BaseBodyUrlReader<T> extends UrlReader<T> {

	@Override
	protected T handleCachedFile(File cachedFile) throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(
				new FileReader(cachedFile))) {

			return _parseBody(_readBody(bufferedReader), cachedFile.toString());
		}
	}

	@Override
	protected T handleResponse(
			String cacheFileKey, boolean expectResponse,
			URLConnection urlConnection)
		throws IOException {

		String content = null;

		try (InputStream inputStream = urlConnection.getInputStream();

			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream))) {

			content = _readBody(bufferedReader);
		}

		if (expectResponse && JenkinsResultsParserUtil.isNullOrEmpty(content)) {
			throw new IOException(
				"Unable to read a response body from " +
					urlConnection.getURL());
		}

		if (cacheFileKey != null) {
			JenkinsResultsParserUtil.saveToCacheFile(cacheFileKey, content);
		}

		return _parseBody(content, String.valueOf(urlConnection.getURL()));
	}

	protected boolean isTruncated(String content) {
		String trimmedContent = content.trim();

		return trimmedContent.endsWith("was truncated due to its size.");
	}

	protected boolean isTruncationFatal() {
		return false;
	}

	protected abstract T parse(String content) throws IOException;

	private T _parseBody(String content, String source) throws IOException {
		if (isTruncationFatal() && isTruncated(content)) {
			throw new TruncatedResponseException(source);
		}

		return parse(content);
	}

	private String _readBody(BufferedReader bufferedReader) throws IOException {
		StringBuilder sb = new StringBuilder();

		String line = bufferedReader.readLine();

		while (line != null) {
			sb.append(line);
			sb.append("\n");

			line = bufferedReader.readLine();
		}

		return sb.toString();
	}

}