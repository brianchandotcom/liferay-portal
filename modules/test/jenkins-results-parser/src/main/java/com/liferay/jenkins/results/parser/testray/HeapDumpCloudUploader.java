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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

/**
 * @author Charlotte Wong
 */
public class HeapDumpCloudUploader {

	public HeapDumpCloudUploader(
		String phase, String masterHostname, String jobName, String buildNumber,
		String slaveHostname) {

		_phase = phase;
		_masterHostname = masterHostname;
		_jobName = jobName;
		_buildNumber = buildNumber;
		_slaveHostname = slaveHostname;
	}

	public void upload() {
		File hprofFile = new File(
			JenkinsResultsParserUtil.combine(
				"/tmp/ant-heap-dump-", _phase, ".hprof"));

		if (!hprofFile.exists()) {
			return;
		}

		String s3BucketName;

		try {
			s3BucketName = JenkinsResultsParserUtil.getBuildProperty(
				"env.S3_BUCKET_NAME");
		}
		catch (IOException ioException) {
			System.out.println(
				"ERROR: Unable to read \"env.S3_BUCKET_NAME\": " +
					ioException.getMessage());

			return;
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(s3BucketName)) {
			System.out.println(
				"INFO: \"S3_BUCKET_NAME\" is not set, skipping heap dump " +
					"upload");

			return;
		}

		String heapDumpsBasePath = JenkinsResultsParserUtil.combine(
			"s3://", s3BucketName, "/heap-dumps");

		String sentinelS3Path = heapDumpsBasePath + "/last-upload";

		try {
			String listing = CloudBucketUtil.listS3Files(sentinelS3Path, true);

			if (!JenkinsResultsParserUtil.isNullOrEmpty(listing)) {
				Matcher matcher = _s3ListingDateTimePattern.matcher(listing);

				if (matcher.find()) {
					String dateTimeString = JenkinsResultsParserUtil.combine(
						matcher.group("date"), " ", matcher.group("time"));

					LocalDateTime uploadDateTime = LocalDateTime.parse(
						dateTimeString,
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

					long uploadMillis = uploadDateTime.toInstant(
						ZoneOffset.UTC
					).toEpochMilli();

					long elapsedMillis =
						JenkinsResultsParserUtil.getCurrentTimeMillis() -
							uploadMillis;

					long minutesAgo = elapsedMillis / (60 * 1000);

					if (minutesAgo < _THROTTLE_MINUTES) {
						System.out.println(
							JenkinsResultsParserUtil.combine(
								"INFO: Skipping heap dump upload, last was ",
								String.valueOf(minutesAgo),
								" min ago (throttle window: ",
								String.valueOf(_THROTTLE_MINUTES),
								" min). Local file: ",
								hprofFile.getAbsolutePath()));

						return;
					}
				}
			}
		}
		catch (IOException | TimeoutException exception) {
		}

		File gzippedFile = _gzip(hprofFile);

		if (gzippedFile == null) {
			return;
		}

		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

		String yearMonth = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));

		String s3Key = JenkinsResultsParserUtil.combine(
			heapDumpsBasePath, "/", yearMonth, "/", _masterHostname, "/",
			_jobName, "/", _buildNumber, "/", _phase, "/", _slaveHostname,
			".hprof.gz");

		try {
			CloudBucketUtil.uploadS3File(s3Key, gzippedFile);

			CloudBucketUtil.uploadS3Object("", sentinelS3Path);

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

	private static final int _THROTTLE_MINUTES = 30;

	private static final Pattern _s3ListingDateTimePattern = Pattern.compile(
		"(?<date>\\d{4}-\\d{2}-\\d{2})\\s+(?<time>\\d{2}:\\d{2}:\\d{2})");

	private final String _buildNumber;
	private final String _jobName;
	private final String _masterHostname;
	private final String _phase;
	private final String _slaveHostname;

}