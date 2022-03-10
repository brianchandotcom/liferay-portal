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

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

/**
 * @author Michael Hashimoto
 */
public class TestrayS3Bucket {

	public static TestrayS3Bucket getInstance() {
		return _testrayS3Bucket;
	}

	public static boolean googleCredentialsAvailable() {
		String googleApplicationCredentials = System.getenv(
			"GOOGLE_APPLICATION_CREDENTIALS");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				googleApplicationCredentials)) {

			try {
				_testrayS3Bucket._getBucket();
			}
			catch (Exception exception) {
				return false;
			}

			return true;
		}

		return false;
	}

	public TestrayS3Object createTestrayS3Object(String key, File file) {
		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		BlobId blobId = BlobId.of(getName(), key);

		String fileName = file.getName();

		Matcher matcher = _fileNamePattern.matcher(fileName);

		BlobInfo.Builder blobInfoBuilder = BlobInfo.newBuilder(blobId);

		if (matcher.find()) {
			String fileExtension = matcher.group("fileExtension");

			if (fileExtension.equals("html")) {
				blobInfoBuilder.setContentType("text/html");
			}
			else if (fileExtension.equals("jpg")) {
				blobInfoBuilder.setContentType("image/jpeg");
			}
			else if (fileExtension.equals("json") ||
					 fileExtension.equals("txt")) {

				blobInfoBuilder.setContentType("text/plain");
			}
			else if (fileExtension.equals("xml")) {
				blobInfoBuilder.setContentType("text/xml");
			}

			String gzipFileExtension = matcher.group("gzipFileExtension");

			if (!JenkinsResultsParserUtil.isNullOrEmpty(gzipFileExtension)) {
				blobInfoBuilder.setContentEncoding("gzip");
			}
		}

		BlobInfo blobInfo = blobInfoBuilder.build();

		try {
			Storage storage = _getStorage();

			Blob blob = storage.create(
				blobInfo, FileUtils.readFileToByteArray(file));

			TestrayS3Object testrayS3Object =
				TestrayS3ObjectFactory.newTestrayS3Object(this, blob);

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Created S3 Object ", testrayS3Object.getURLString(),
					" in ",
					JenkinsResultsParserUtil.toDurationString(
						JenkinsResultsParserUtil.getCurrentTimeMillis() -
							start)));

			return testrayS3Object;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public TestrayS3Object createTestrayS3Object(String key, String value) {
		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		BlobId blobId = BlobId.of(getName(), key);

		BlobInfo.Builder blobInfoBuilder = BlobInfo.newBuilder(blobId);

		BlobInfo blobInfo = blobInfoBuilder.build();

		Storage storage = _getStorage();

		Blob blob = storage.create(
			blobInfo, value.getBytes(StandardCharsets.UTF_8));

		TestrayS3Object testrayS3Object =
			TestrayS3ObjectFactory.newTestrayS3Object(this, blob);

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Created S3 Object ", testrayS3Object.getURLString(), " in ",
				JenkinsResultsParserUtil.toDurationString(
					JenkinsResultsParserUtil.getCurrentTimeMillis() - start)));

		return testrayS3Object;
	}

	public List<TestrayS3Object> createTestrayS3Objects(File dir) {
		List<TestrayS3Object> testrayS3Objects = new ArrayList<>();

		if ((dir == null) || !dir.isDirectory()) {
			return testrayS3Objects;
		}

		for (File file : JenkinsResultsParserUtil.findFiles(dir, ".*")) {
			TestrayS3Object testrayS3Object = createTestrayS3Object(
				JenkinsResultsParserUtil.getPathRelativeTo(file, dir), file);

			testrayS3Objects.add(testrayS3Object);
		}

		return testrayS3Objects;
	}

	public void deleteTestrayS3Object(String key) {
		deleteTestrayS3Object(getTestrayS3Object(key));
	}

	public void deleteTestrayS3Object(TestrayS3Object testrayS3Object) {
		testrayS3Object.delete();
	}

	public void deleteTestrayS3Objects(List<TestrayS3Object> testrayS3Objects) {
		for (TestrayS3Object testrayS3Object : testrayS3Objects) {
			deleteTestrayS3Object(testrayS3Object);
		}
	}

	public String getName() {
		try {
			return JenkinsResultsParserUtil.getBuildProperty(
				"testray.s3.bucket");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public String getTestrayS3BaseURL() {
		return JenkinsResultsParserUtil.combine(
			"https://storage.cloud.google.com/", getName());
	}

	public TestrayS3Object getTestrayS3Object(String key) {
		Bucket bucket = _getBucket();

		Blob blob = bucket.get(key);

		if (blob == null) {
			return null;
		}

		return TestrayS3ObjectFactory.newTestrayS3Object(this, blob);
	}

	public List<TestrayS3Object> getTestrayS3Objects() {
		List<TestrayS3Object> testrayS3Objects = new ArrayList<>();

		Storage storage = _getStorage();

		Page<Blob> blobPage = storage.list(getName());

		for (Blob blob : blobPage.iterateAll()) {
			testrayS3Objects.add(
				TestrayS3ObjectFactory.newTestrayS3Object(this, blob));
		}

		return testrayS3Objects;
	}

	public URL getURL() {
		try {
			return new URL(
				JenkinsResultsParserUtil.combine(
					"https://console.cloud.google.com/storage/browser/",
					getName(), "?authuser=0"));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private Bucket _getBucket() {
		Storage storage = _getStorage();

		return storage.get(getName());
	}

	private Storage _getStorage() {
		StorageOptions storageOptions = StorageOptions.getDefaultInstance();

		return storageOptions.getService();
	}

	private static final Pattern _fileNamePattern = Pattern.compile(
		".*\\.(?!gz)(?<fileExtension>([^\\.]+))(?<gzipFileExtension>\\.gz)?");
	private static final TestrayS3Bucket _testrayS3Bucket =
		new TestrayS3Bucket();

}