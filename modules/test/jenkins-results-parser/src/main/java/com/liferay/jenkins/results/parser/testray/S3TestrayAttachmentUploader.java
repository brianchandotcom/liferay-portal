/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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