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

import com.google.cloud.storage.Blob;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.StandardCharsets;

/**
 * @author Michael Hashimoto
 */
public class TestrayS3Object {

	public void delete() {
		_blob.delete();
	}

	public boolean exists() {
		return _blob.exists();
	}

	public String getKey() {
		return _blob.getName();
	}

	public TestrayS3Bucket getTestrayS3Bucket() {
		return _testrayS3Bucket;
	}

	public URL getURL() {
		return _url;
	}

	public String getURLString() {
		return JenkinsResultsParserUtil.fixURL(String.valueOf(_url));
	}

	public String getValue() {
		if (!exists()) {
			return null;
		}

		return new String(_blob.getContent(), StandardCharsets.UTF_8);
	}

	@Override
	public String toString() {
		return getURLString();
	}

	protected TestrayS3Object(TestrayS3Bucket testrayS3Bucket, Blob blob) {
		_testrayS3Bucket = testrayS3Bucket;
		_blob = blob;

		try {
			_url = new URL(
				JenkinsResultsParserUtil.combine(
					_testrayS3Bucket.getTestrayS3BaseURL(), "/", getKey()));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private final Blob _blob;
	private final TestrayS3Bucket _testrayS3Bucket;
	private final URL _url;

}