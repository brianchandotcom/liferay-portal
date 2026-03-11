/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JobCacheUtil {

	public static void cacheJob(Job job) {
		if (!JenkinsResultsParserUtil.isCloudCINode()) {
			return;
		}

		PortalGitWorkingDirectory portalGitWorkingDirectory = null;

		if (job instanceof PortalGitRepositoryJob) {
			PortalGitRepositoryJob portalGitRepositoryJob =
				(PortalGitRepositoryJob)job;

			portalGitWorkingDirectory =
				portalGitRepositoryJob.getPortalGitWorkingDirectory();
		}

		String testSuiteName = null;

		if (job instanceof TestSuiteJob) {
			TestSuiteJob testSuiteJob = (TestSuiteJob)job;

			testSuiteName = testSuiteJob.getTestSuiteName();
		}

		String cachedJobS3ObjectPath = _getCachedJobS3ObjectPath(
			job.getJobName(), portalGitWorkingDirectory, testSuiteName);

		if (JenkinsResultsParserUtil.isNullOrEmpty(cachedJobS3ObjectPath)) {
			return;
		}

		File jobFile = new File("job.json");
		File jobGzFile = new File("job.json.gz");

		JSONObject jsonObject = job.getJSONObject();

		try {
			JenkinsResultsParserUtil.write(jobFile, String.valueOf(jsonObject));

			JenkinsResultsParserUtil.gzip(jobFile, jobGzFile);

			CloudBucketUtil.uploadS3File(cachedJobS3ObjectPath, jobGzFile);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static JSONObject getCachedJobJSONObject(
		String jobName, PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		if (!JenkinsResultsParserUtil.isCloudCINode()) {
			return null;
		}

		String cachedJobS3ObjectPath = _getCachedJobS3ObjectPath(
			jobName, portalGitWorkingDirectory, testSuiteName);

		if (!CloudBucketUtil.isS3ObjectPathAvailable(cachedJobS3ObjectPath)) {
			return null;
		}

		synchronized (_jobJSONObjects) {
			File file = new File(
				System.getProperty("java.io.tmpdir"),
				JenkinsResultsParserUtil.getDistinctTimeStamp() + ".json.gz");

			JSONObject jobJSONObject = null;

			try {
				CloudBucketUtil.downloadS3File(file, cachedJobS3ObjectPath);

				String fileContent = JenkinsResultsParserUtil.read(file);

				if (JenkinsResultsParserUtil.isJSONObject(fileContent)) {
					jobJSONObject = new JSONObject(fileContent);
				}
			}
			catch (IOException | JSONException exception) {
				return null;
			}
			finally {
				JenkinsResultsParserUtil.delete(file);

				_jobJSONObjects.put(cachedJobS3ObjectPath, jobJSONObject);
			}

			return _jobJSONObjects.get(cachedJobS3ObjectPath);
		}
	}

	public static boolean isJobCached(
		String jobName, PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		if (!JenkinsResultsParserUtil.isCloudCINode()) {
			return false;
		}

		JSONObject cachedJobJSONObject = getCachedJobJSONObject(
			jobName, portalGitWorkingDirectory, testSuiteName);

		if (cachedJobJSONObject != null) {
			return true;
		}

		return false;
	}

	private static String _getCachedJobS3ObjectPath(
		String jobName, PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName) {

		if (portalGitWorkingDirectory == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		try {
			sb.append(
				JenkinsResultsParserUtil.getBuildProperty(
					"cloud.ci.s3.bucket.job.cache.path"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		LocalGitBranch currentLocalGitBranch =
			portalGitWorkingDirectory.getCurrentLocalGitBranch();
		LocalGitBranch upstreamLocalGitBranch =
			portalGitWorkingDirectory.getUpstreamLocalGitBranch();

		sb.append("/");
		sb.append(jobName.replaceAll("[\\/\\(\\)]+", "__"));
		sb.append("/");
		sb.append(portalGitWorkingDirectory.getGitRepositoryName());
		sb.append("/");
		sb.append(upstreamLocalGitBranch.getSHA());
		sb.append("/");
		sb.append(currentLocalGitBranch.getSHA());
		sb.append("/");

		if (JenkinsResultsParserUtil.isNullOrEmpty(testSuiteName)) {
			testSuiteName = "default";
		}

		sb.append(testSuiteName);

		sb.append(".json.gz");

		return sb.toString();
	}

	private static final Map<String, JSONObject> _jobJSONObjects =
		new HashMap<>();

}