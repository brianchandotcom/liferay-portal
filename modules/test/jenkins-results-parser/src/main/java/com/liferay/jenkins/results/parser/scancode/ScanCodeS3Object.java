/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.scancode;

import com.google.cloud.storage.Blob;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.StandardCharsets;

/**
 * @author Brittney Nguyen
 */
public class ScanCodeS3Object {

	public void delete() {
		_blob.delete();
	}

	public boolean exists() {
		return _blob.exists();
	}

	public String getKey() {
		return _blob.getName();
	}

	public ScanCodeS3Bucket getScancodeS3Bucket() {
		return _scanCodeS3Bucket;
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

	protected ScanCodeS3Object(ScanCodeS3Bucket scanCodeS3Bucket, Blob blob) {
		_scanCodeS3Bucket = scanCodeS3Bucket;
		_blob = blob;

		try {
			_url = new URL(
				JenkinsResultsParserUtil.combine(
					scanCodeS3Bucket.getScanCodeS3BaseURL(), "/", getKey()));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private final Blob _blob;
	private final ScanCodeS3Bucket _scanCodeS3Bucket;
	private final URL _url;

}