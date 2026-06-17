/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.CloudBucketUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import java.util.concurrent.TimeoutException;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

/**
 * @author Charlotte Wong
 */
public class HeapDumpCloudUploader {

	public HeapDumpCloudUploader(
		String buildNumber, String jobName, String masterHostname, String phase,
		String slaveHostname) {

		_buildNumber = buildNumber;
		_jobName = jobName;
		_masterHostname = masterHostname;
		_phase = phase;
		_slaveHostname = slaveHostname;
	}

	public void upload() {
		File hprofFile = new File(
			JenkinsResultsParserUtil.combine(
				"/tmp/ant-heap-dump-", _phase, ".hprof"));

		if (!hprofFile.exists()) {
			return;
		}

		String s3BucketName = _getS3BucketName();

		if (s3BucketName == null) {
			return;
		}

		String heapDumpsBasePath = JenkinsResultsParserUtil.combine(
			"s3://", s3BucketName, "/heap-dumps");

		if (_isThrottled(heapDumpsBasePath, hprofFile)) {
			return;
		}

		File gzippedFile = _gzip(hprofFile);

		if (gzippedFile == null) {
			return;
		}

		LocalDateTime currentLocalDateTime = LocalDateTime.now(ZoneOffset.UTC);

		String currentYearMonth = currentLocalDateTime.format(
			DateTimeFormatter.ofPattern("yyyy-MM"));

		String s3Key = JenkinsResultsParserUtil.combine(
			heapDumpsBasePath, "/", currentYearMonth, "/", _masterHostname, "/",
			_jobName, "/", _buildNumber, "/", _phase, "/", _slaveHostname,
			".hprof.gz");

		try {
			CloudBucketUtil.uploadS3File(s3Key, gzippedFile);

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"INFO: Heap dump uploaded. To download:\naws s3 cp ", s3Key,
					" /tmp/", _slaveHostname, ".hprof.gz"));
		}
		catch (IOException ioException) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"ERROR: Unable to upload heap dump: ",
					ioException.getMessage()));
		}
		finally {
			gzippedFile.delete();
		}
	}

	private String _getS3BucketName() {
		String s3BucketName;

		try {
			s3BucketName = JenkinsResultsParserUtil.getBuildProperty(
				"env.S3_BUCKET_NAME");
		}
		catch (IOException ioException) {
			System.out.println(
				"ERROR: Unable to read \"env.S3_BUCKET_NAME\": " +
					ioException.getMessage());

			return null;
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(s3BucketName)) {
			System.out.println(
				"INFO: \"S3_BUCKET_NAME\" is not set, skipping heap dump " +
					"upload");

			return null;
		}

		return s3BucketName;
	}

	private File _gzip(File file) {
		File gzippedFile = new File(file.getAbsolutePath() + ".gz");

		try (FileInputStream fileInputStream = new FileInputStream(file);

			FileOutputStream fileOutputStream = new FileOutputStream(
				gzippedFile);

			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
				fileOutputStream) {

				{
					def.setLevel(Deflater.BEST_COMPRESSION);
				}
			}) {

			byte[] buffer = new byte[8192];
			int length;

			while ((length = fileInputStream.read(buffer)) > 0) {
				gzipOutputStream.write(buffer, 0, length);
			}
		}
		catch (IOException ioException) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"ERROR: Unable to gzip heap dump ", file.getAbsolutePath(),
					": ", ioException.getMessage()));

			return null;
		}

		return gzippedFile;
	}

	private boolean _isThrottled(String heapDumpsS3Path, File hprofFile) {
		long newestS3ObjectLastModified;

		try {
			newestS3ObjectLastModified =
				CloudBucketUtil.getNewestS3ObjectLastModified(heapDumpsS3Path);
		}
		catch (IOException | TimeoutException exception) {
			return false;
		}

		if (newestS3ObjectLastModified == Long.MIN_VALUE) {
			return false;
		}

		long elapsedMillis =
			JenkinsResultsParserUtil.getCurrentTimeMillis() -
				newestS3ObjectLastModified;

		long minutesAgo = elapsedMillis / (60 * 1000);

		if (minutesAgo >= _THROTTLE_MINUTES) {
			return false;
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"INFO: Skipping heap dump upload, last was ",
				String.valueOf(minutesAgo), " min ago (throttle window: ",
				String.valueOf(_THROTTLE_MINUTES), " min). Local file: ",
				hprofFile.getAbsolutePath()));

		return true;
	}

	private static final int _THROTTLE_MINUTES = 30;

	private final String _buildNumber;
	private final String _jobName;
	private final String _masterHostname;
	private final String _phase;
	private final String _slaveHostname;

}