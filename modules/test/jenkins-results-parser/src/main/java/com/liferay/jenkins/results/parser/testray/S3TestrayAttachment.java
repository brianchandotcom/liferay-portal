/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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