/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HTTPAuthorization;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class JSONObjectUrlReader extends BaseBodyUrlReader<JSONObject> {

	public static JSONObject read(
			boolean checkCache, HTTPAuthorization httpAuthorization,
			HttpRequestMethod httpRequestMethod, int maxRetries,
			String postContent, int retryPeriod, int timeout, String url)
		throws IOException {

		return _jsonObjectUrlReader.doRead(
			checkCache, true, httpAuthorization, httpRequestMethod, maxRetries,
			postContent, retryPeriod, timeout, url);
	}

	public static void setInstance(JSONObjectUrlReader jsonObjectUrlReader) {
		_jsonObjectUrlReader = jsonObjectUrlReader;
	}

	@Override
	protected boolean isTruncationFatal() {
		return true;
	}

	/**
	 * Parsing inside the attempt is deliberate and load bearing, per LRCI-5564.
	 * A malformed body is a failed attempt the retry loop can act on, which is
	 * the whole reason the parse lives here rather than around the read.
	 */
	@Override
	protected JSONObject parse(String content) throws IOException {
		try {
			return JenkinsResultsParserUtil.createJSONObject(content);
		}
		catch (JSONException jsonException) {
			throw new IOException(
				"Unable to create a JSON object from the response body",
				jsonException);
		}
	}

	private static volatile JSONObjectUrlReader _jsonObjectUrlReader =
		new JSONObjectUrlReader();

}