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

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class TestrayS3Bucket {

	public static TestrayS3Bucket getInstance() {
		return _testrayS3Bucket;
	}

	public TestrayS3Object createTestrayS3Object(String key, File file) {
		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		try {
			ObjectMetadata objectMetadata = new ObjectMetadata();

			objectMetadata.setContentLength(file.length());

			String fileName = file.getName();

			if (fileName.endsWith(".gz")) {
				objectMetadata.setContentEncoding("gzip");
			}

			if (fileName.endsWith("html") || fileName.endsWith("html.gz")) {
				objectMetadata.setContentType("text/html");
			}
			else if (fileName.endsWith("jpg") || fileName.endsWith("jpg.gz")) {
				objectMetadata.setContentType("image/jpeg");
			}
			else if (fileName.endsWith("txt") || fileName.endsWith("txt.gz")) {
				objectMetadata.setContentType("text/plain");
			}

			PutObjectRequest putObjectRequest = new PutObjectRequest(
				_bucket.getName(), key, new FileInputStream(file),
				objectMetadata);

			putObjectRequest.setAccessControlList(_getAccessControlList());

			_amazonS3.putObject(putObjectRequest);

			TestrayS3Object testrayS3Object = new TestrayS3Object(this, key);

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Created S3 Object ",
					String.valueOf(testrayS3Object.getURL()), " in ",
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

		InputStream inputStream = new ByteArrayInputStream(value.getBytes());

		try {
			byte[] bytes = IOUtils.toByteArray(inputStream);

			ObjectMetadata objectMetadata = new ObjectMetadata();

			objectMetadata.setContentLength(bytes.length);
			objectMetadata.setContentType("text/plain");

			ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(bytes);

			PutObjectRequest putObjectRequest = new PutObjectRequest(
				_bucket.getName(), key, byteArrayInputStream, objectMetadata);

			putObjectRequest.setAccessControlList(_getAccessControlList());

			_amazonS3.putObject(putObjectRequest);

			TestrayS3Object testrayS3Object = new TestrayS3Object(this, key);

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Created S3 Object ",
					String.valueOf(testrayS3Object.getURL()), " in ",
					JenkinsResultsParserUtil.toDurationString(
						JenkinsResultsParserUtil.getCurrentTimeMillis() -
							start)));

			return testrayS3Object;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public List<TestrayS3Object> createTestrayS3Objects(
		String baseKey, File dir) {

		List<TestrayS3Object> testrayS3Objects = new ArrayList<>();

		for (File file : JenkinsResultsParserUtil.findFiles(dir, ".*")) {
			StringBuilder sb = new StringBuilder();

			sb.append(baseKey);

			if (!baseKey.endsWith("/")) {
				sb.append("/");
			}

			sb.append(JenkinsResultsParserUtil.getPathRelativeTo(file, dir));

			TestrayS3Object testrayS3Object = createTestrayS3Object(
				sb.toString(), file);

			testrayS3Objects.add(testrayS3Object);
		}

		return testrayS3Objects;
	}

	public void deleteTestrayS3Object(String key) {
		deleteTestrayS3Object(new TestrayS3Object(this, key));
	}

	public void deleteTestrayS3Object(TestrayS3Object testrayS3Object) {
		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		_amazonS3.deleteObject(_bucket.getName(), testrayS3Object.getKey());

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Deleted S3 Object ", String.valueOf(testrayS3Object.getURL()),
				" in ",
				JenkinsResultsParserUtil.toDurationString(
					JenkinsResultsParserUtil.getCurrentTimeMillis() - start)));
	}

	public void deleteTestrayS3Objects(List<TestrayS3Object> testrayS3Objects) {
		for (TestrayS3Object testrayS3Object : testrayS3Objects) {
			deleteTestrayS3Object(testrayS3Object);
		}
	}

	public String getTestrayS3BaseURL() {
		try {
			return JenkinsResultsParserUtil.getBuildProperty(
				"testray.s3.base.url");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private TestrayS3Bucket() {
		ClientConfiguration clientConfig = new ClientConfiguration();

		clientConfig.setProtocol(Protocol.HTTPS);

		_amazonS3 = new AmazonS3Client(
			new BasicAWSCredentials(_getTestrayS3Key(), _getTestrayS3Secret()),
			clientConfig);

		_amazonS3.setEndpoint(_getTestrayS3Host());

		String bucketName = _getBucketName();

		for (Bucket bucket : _amazonS3.listBuckets()) {
			if (!bucketName.equals(bucket.getName())) {
				continue;
			}

			_bucket = bucket;

			return;
		}

		throw new RuntimeException("Invalid bucket name " + bucketName);
	}

	private AccessControlList _getAccessControlList() {
		AccessControlList accessControlList = new AccessControlList();

		Owner owner = _amazonS3.getS3AccountOwner();

		accessControlList.setOwner(owner);

		accessControlList.grantPermission(
			new CanonicalGrantee(owner.getDisplayName()),
			Permission.FullControl);

		accessControlList.grantPermission(
			GroupGrantee.AllUsers, Permission.Read);

		return accessControlList;
	}

	private String _getBucketName() {
		try {
			return JenkinsResultsParserUtil.getBuildProperty(
				"testray.s3.bucket");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private String _getTestrayS3Host() {
		try {
			return JenkinsResultsParserUtil.getBuildProperty("testray.s3.host");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private String _getTestrayS3Key() {
		try {
			return JenkinsResultsParserUtil.getBuildProperty("testray.s3.key");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private String _getTestrayS3Secret() {
		try {
			return JenkinsResultsParserUtil.getBuildProperty(
				"testray.s3.secret");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final TestrayS3Bucket _testrayS3Bucket =
		new TestrayS3Bucket();

	private final AmazonS3 _amazonS3;
	private final Bucket _bucket;

}