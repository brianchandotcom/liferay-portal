/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HTTPAuthorization;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

import java.io.IOException;

/**
 * @author Kenji Heigel
 */
public class TextUrlReader extends BaseBodyUrlReader<String> {

	public static TextUrlReader getInstance() {
		return _textUrlReader;
	}

	public static String read(
			boolean checkCache, boolean expectResponse,
			HTTPAuthorization httpAuthorization,
			HttpRequestMethod httpRequestMethod, int maxRetries,
			String postContent, int retryPeriod, int timeout, String url)
		throws IOException {

		return _textUrlReader.doRead(
			checkCache, expectResponse, httpAuthorization, httpRequestMethod,
			maxRetries, postContent, retryPeriod, timeout, url);
	}

	public static void setInstance(TextUrlReader textUrlReader) {
		_textUrlReader = textUrlReader;
	}

	@Override
	protected String parse(String content) {
		return content;
	}

	private static volatile TextUrlReader _textUrlReader = new TextUrlReader();

}