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

package com.liferay.jenkins.results.parser.testray;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public class TestrayS3Object {

	public boolean exists() {
		if (_exists != null) {
			return _exists;
		}

		try {
			HttpURLConnection httpURLConnection =
				(HttpURLConnection)_url.openConnection();

			httpURLConnection.setRequestMethod("HEAD");
			httpURLConnection.connect();

			int responseCode = httpURLConnection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				_exists = true;

				return _exists;
			}
		}
		catch (IOException ioException) {
		}

		_exists = false;

		return _exists;
	}

	public String getKey() {
		return _key;
	}

	public TestrayS3Bucket getTestrayS3Bucket() {
		return _testrayS3Bucket;
	}

	public URL getURL() {
		return _url;
	}

	public String getURLString() {
		String urlString = String.valueOf(_url);

		urlString = urlString.replace("(", "%28");
		urlString = urlString.replace(")", "%29");

		return urlString;
	}

	@Override
	public String toString() {
		return getURLString();
	}

	protected TestrayS3Object(TestrayS3Bucket testrayS3Bucket, String key) {
		_testrayS3Bucket = testrayS3Bucket;
		_key = key;

		try {
			_url = new URL(
				_testrayS3Bucket.getTestrayS3BaseURL() + "/" + getKey());
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private Boolean _exists;
	private final String _key;
	private final TestrayS3Bucket _testrayS3Bucket;
	private final URL _url;

}