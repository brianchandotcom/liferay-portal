/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HTTPAuthorization;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author Kenji Heigel
 */
public class JSONArrayUrlReader extends BaseBodyUrlReader<JSONArray> {

	public static JSONArrayUrlReader getInstance() {
		return _jsonArrayUrlReader;
	}

	public static JSONArray read(
			boolean checkCache, HTTPAuthorization httpAuthorization,
			int maxRetries, String postContent, int retryPeriod, int timeout,
			String url)
		throws IOException {

		return _jsonArrayUrlReader.doRead(
			checkCache, true, httpAuthorization, null, maxRetries, postContent,
			retryPeriod, timeout, url);
	}

	public static void setInstance(JSONArrayUrlReader jsonArrayUrlReader) {
		_jsonArrayUrlReader = jsonArrayUrlReader;
	}

	/**
	 * Parsing inside the attempt gives the array reader the retry on a
	 * malformed body that it never had, matching what LRCI-5564 established for
	 * the object reader.
	 */
	@Override
	protected JSONArray parse(String content) throws IOException {
		if (content.endsWith(_SUFFIX_TRUNCATED)) {
			return null;
		}

		try {
			return new JSONArray(content);
		}
		catch (JSONException jsonException) {
			throw new IOException(
				"Unable to create a JSON array from the response body",
				jsonException);
		}
	}

	private static final String _SUFFIX_TRUNCATED =
		"was truncated due to its size.";

	private static volatile JSONArrayUrlReader _jsonArrayUrlReader =
		new JSONArrayUrlReader();

}