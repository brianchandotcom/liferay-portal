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

import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public class S3TestrayAttachment extends BaseTestrayAttachment {

	public S3TestrayAttachment(
		TestrayCaseResult testrayCaseResult, String name, String key) {

		super(testrayCaseResult, name, key);

		TestrayS3Bucket testrayS3Bucket = TestrayS3Bucket.getInstance();

		_testrayS3Object = testrayS3Bucket.getTestrayS3Object(key);
	}

	@Override
	public URL getURL() {
		if (_testrayS3Object == null) {
			return null;
		}

		return _testrayS3Object.getURL();
	}

	@Override
	public String getValue() {
		if (_testrayS3Object == null) {
			return null;
		}

		return _testrayS3Object.getValue();
	}

	private final TestrayS3Object _testrayS3Object;

}