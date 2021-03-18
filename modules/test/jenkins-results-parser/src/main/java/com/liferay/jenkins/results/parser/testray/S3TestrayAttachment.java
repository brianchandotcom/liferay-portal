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

		_testrayS3Object = TestrayS3ObjectFactory.newTestrayS3Object(
			TestrayS3Bucket.getInstance(), key);
	}

	@Override
	public boolean exists() {
		return _testrayS3Object.exists();
	}

	@Override
	public String getKey() {
		return _testrayS3Object.getKey();
	}

	@Override
	public URL getURL() {
		return _testrayS3Object.getURL();
	}

	private final TestrayS3Object _testrayS3Object;

}