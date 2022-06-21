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

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JobReport {

	public static JobReport getInstance(String jenkinsJobURLString) {
		try {
			return getInstance(new URL(jenkinsJobURLString));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	public static JobReport getInstance(URL jenkinsJobURL) {
		String key = JenkinsResultsParserUtil.getRemoteURL(
			jenkinsJobURL.toString());

		if (!_jobReports.containsKey(key)) {
			_jobReports.put(key, new JobReport(jenkinsJobURL));
		}

		return _jobReports.get(key);
	}

	public JenkinsMaster getJenkinsMaster() {
		if (_jenkinsMaster != null) {
			return _jenkinsMaster;
		}

		Matcher matcher = _jobURLPattern.matcher(String.valueOf(getJobURL()));

		if (!matcher.find()) {
			throw new RuntimeException("Invalid Job URL");
		}

		_jenkinsMaster = JenkinsMaster.getInstance(
			matcher.group("masterHostname"));

		return _jenkinsMaster;
	}

	public String getJobName() {
		Matcher matcher = _jobURLPattern.matcher(String.valueOf(getJobURL()));

		if (!matcher.find()) {
			throw new RuntimeException("Invalid Job URL");
		}

		return matcher.group("jobName");
	}

	public URL getJobURL() {
		return _jobURL;
	}

	private JobReport(URL jobURL) {
		Matcher matcher = _jobURLPattern.matcher(String.valueOf(jobURL));

		if (!matcher.find()) {
			throw new RuntimeException("Invalid Job URL");
		}

		try {
			_jobURL = new URL(
				JenkinsResultsParserUtil.combine(
					"https://", matcher.group("masterHostname"),
					".liferay.com/job/", matcher.group("jobName")));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private static final Map<String, JobReport> _jobReports = new HashMap<>();
	private static final Pattern _jobURLPattern = Pattern.compile(
		"https?://(?<masterHostname>test-\\d+-\\d+)(\\.liferay\\.com)?/job/" +
			"(?<jobName>[^/]+)/?");

	private JenkinsMaster _jenkinsMaster;
	private final URL _jobURL;

}