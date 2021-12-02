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
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public class S3TestrayAttachmentUploader extends BaseTestrayAttachmentUploader {

	@Override
	public File getPreparedFilesBaseDir() {
		String workspace = System.getenv("WORKSPACE");

		if (JenkinsResultsParserUtil.isNullOrEmpty(workspace)) {
			throw new RuntimeException("Please set WORKSPACE");
		}

		return new File(workspace, "testray/prepared_s3_logs");
	}

	@Override
	public URL getTestrayServerLogsURL() {
		try {
			return new URL(
				JenkinsResultsParserUtil.combine(
					String.valueOf(getTestrayServerURL()),
					"/reports_test/production/logs"));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	@Override
	public void upload() {
		if (_uploaded) {
			return;
		}

		prepareFiles();

		TestrayS3Bucket testrayS3Bucket = TestrayS3Bucket.getInstance();

		testrayS3Bucket.createTestrayS3Objects(getPreparedFilesBaseDir());

		_uploaded = true;
	}

	protected S3TestrayAttachmentUploader(Build build, URL testrayServerURL) {
		super(build, testrayServerURL);
	}

	private boolean _uploaded;

}