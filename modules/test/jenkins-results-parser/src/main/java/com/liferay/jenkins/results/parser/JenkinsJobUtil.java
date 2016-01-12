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

package com.liferay.jenkins.results.parser;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Kevin Yen
 */
public class JenkinsJobUtil {

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("usage: <jobURL> <username> <password>");
			System.exit(1);
		}

		String jobURL = args[0];
		String username = args[1];
		String password = args[2];

		try {
			stopJenkinsJob(jobURL, username, password);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void stopJenkinsJob(
			String jobURL, String username, String password)
		throws Exception {

		stopJob(jobURL, username, password);

		stopDownstreamJobs(jobURL, username, password);
	}

	protected static String encodeAuthorizationFields(
		String username, String password) {

		String authorizationString = username + ":" + password;

		return new String(Base64.encodeBase64(authorizationString.getBytes()));
	}

	private static List<String> getDownstreamURLs(String jobURL)
		throws Exception {

		String consoleOutput = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(
				jobURL + "/logText/progressiveText"));
		List<String> downstreamURLs = new ArrayList<>();

		Matcher progressiveTextMatcher = _progressiveTextPattern.matcher(
			consoleOutput);

		while (progressiveTextMatcher.find()) {
			String urlString = progressiveTextMatcher.group("url");

			Matcher jobNameMatcher = _jobNamePattern.matcher(urlString);

			if (jobNameMatcher.find()) {
				downstreamURLs.add(urlString);
			}
		}

		return downstreamURLs;
	}

	private static void stopDownstreamJobs(
			String jobURL, String username, String password)
		throws Exception {

		List<String> downstreamURLs = getDownstreamURLs(jobURL);

		for (String downstreamURL : downstreamURLs) {
			stopJob(downstreamURL, username, password);
		}
	}

	private static void stopJob(String jobURL, String username, String password)
		throws Exception {

		String stopURL = JenkinsResultsParserUtil.fixURL(
			JenkinsResultsParserUtil.getLocalURL(jobURL + "/stop"));

		String encodedString = encodeAuthorizationFields(username, password);
		URL urlObject = new URL(stopURL);

		HttpURLConnection httpConnection =
			(HttpURLConnection)urlObject.openConnection();

		httpConnection.setRequestMethod("POST");

		httpConnection.setRequestProperty(
			"Authorization", "Basic " + encodedString);

		System.out.println(
			"Response from " + jobURL + "/stop: " +
				httpConnection.getResponseCode() + " " +
					httpConnection.getResponseMessage());
	}

	private static final Pattern _jobNamePattern = Pattern.compile(
		".+://(?<hostName>[^.]+).liferay.com/job/(?<jobName>[^/]+).*/" +
			"(?<buildNumber>\\d+)/");
	private static final Pattern _progressiveTextPattern = Pattern.compile(
		"\\[echo\\] Build \\'.*\\' started at (?<url>.+)\\.");

}