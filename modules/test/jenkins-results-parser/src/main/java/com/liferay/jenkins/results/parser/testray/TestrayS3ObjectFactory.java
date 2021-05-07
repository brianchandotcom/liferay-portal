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