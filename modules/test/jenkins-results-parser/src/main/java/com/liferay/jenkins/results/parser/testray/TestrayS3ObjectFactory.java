/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.google.cloud.storage.Blob;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class TestrayS3ObjectFactory {

	public static TestrayS3Object newTestrayS3Object(
		TestrayS3Bucket testrayS3Bucket, Blob blob) {

		if (blob == null) {
			return null;
		}

		String mapKey = JenkinsResultsParserUtil.combine(
			testrayS3Bucket.getName(), "/", blob.getName());

		if (_testrayS3Objects.containsKey(mapKey)) {
			return _testrayS3Objects.get(mapKey);
		}

		TestrayS3Object testrayS3Object = new TestrayS3Object(
			testrayS3Bucket, blob);

		_testrayS3Objects.put(mapKey, testrayS3Object);

		return testrayS3Object;
	}

	private static final Map<String, TestrayS3Object> _testrayS3Objects =
		new HashMap<>();

}