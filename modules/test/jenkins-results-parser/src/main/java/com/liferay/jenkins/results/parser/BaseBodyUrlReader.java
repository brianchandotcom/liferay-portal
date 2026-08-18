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
 * Reads the whole body inside the attempt, so a response that arrives but is
 * unusable fails the same way a connection reset does and the retry loop in
 * {@link UrlReader} can act on it.
 *
 * @author Kenji Heigel
 */
public abstract class BaseBodyUrlReader<T> extends UrlReader<T> {

	@Override
	protected T handleCachedFile(File cachedFile) throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(
				new FileReader(cachedFile))) {

			return parse(_read(bufferedReader));
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

			content = _read(bufferedReader);
		}

		if (expectResponse && JenkinsResultsParserUtil.isNullOrEmpty(content)) {
			throw new IOException(
				"Unable to read a response body from " +
					urlConnection.getURL());
		}

		if (cacheFileKey != null) {
			JenkinsResultsParserUtil.saveToCacheFile(cacheFileKey, content);
		}

		return parse(content);
	}

	/**
	 * Turns a body that has already been read and accepted into the value the
	 * caller asked for. Anything thrown here fails the attempt, which is what
	 * puts a malformed response under the same retry policy as a transport
	 * failure.
	 */
	protected abstract T parse(String content) throws IOException;

	/**
	 * Appends a newline per line rather than copying the stream verbatim. The
	 * trailing newline and the normalized line endings are what every caller
	 * has always been handed, so reading the bytes straight through would
	 * change the returned content fleet wide.
	 */
	private String _read(BufferedReader bufferedReader) throws IOException {
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