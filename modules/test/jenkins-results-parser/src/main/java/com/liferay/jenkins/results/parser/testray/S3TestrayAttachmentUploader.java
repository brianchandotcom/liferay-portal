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

import com.liferay.jenkins.results.parser.Build;

/**
 * @author Michael Hashimoto
 */
public class S3TestrayAttachmentUploader extends BaseTestrayAttachmentUploader {

	public S3TestrayAttachmentUploader(Build build) {
		super(build);
	}

	@Override
	public void upload() {
		if (_uploaded) {
			return;
		}

		TestrayAttachmentRecorder testrayAttachmentRecorder =
			getTestrayAttachmentRecorder();

		testrayAttachmentRecorder.record();

		TestrayS3Bucket testrayS3Bucket = TestrayS3Bucket.getInstance();

		testrayS3Bucket.createTestrayS3Objects(
			testrayAttachmentRecorder.getTestrayLogsDir());

		_uploaded = true;
	}

	private boolean _uploaded;

}