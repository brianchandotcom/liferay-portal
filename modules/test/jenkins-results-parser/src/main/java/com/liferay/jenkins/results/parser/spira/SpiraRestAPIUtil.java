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

package com.liferay.jenkins.results.parser.spira;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraRestAPIUtil {

	public static void summarizeRequests() {
		Set<RequestDataEntry> orderedRequestDataEntriesSet = new HashSet<>();

		for (Map.Entry<String, List<RequestDataEntry>> entry :
				_groupedRequestDataEntriesMap.entrySet()) {

			orderedRequestDataEntriesSet.addAll(entry.getValue());
		}

		List<RequestDataEntry> orderedRequestDataEntries = new ArrayList<>(
			orderedRequestDataEntriesSet);

		Collections.sort(orderedRequestDataEntries);

		long totalRequestCount = orderedRequestDataEntries.size();

		long totalDuration = 0;
		long totalRequestByteCount = 0;
		long totalResponseByteCount = 0;

		long q1Index = totalRequestCount / 4;
		long q2Index = totalRequestCount / 2;

		long q3Index = q1Index + q2Index;

		RequestDataEntry q1RequestDataEntry = orderedRequestDataEntries.get(0);
		RequestDataEntry q2RequestDataEntry = orderedRequestDataEntries.get(0);
		RequestDataEntry q3RequestDataEntry = orderedRequestDataEntries.get(
			orderedRequestDataEntries.size() - 1);

		int i = 0;

		for (RequestDataEntry requestDataEntry : orderedRequestDataEntries) {
			if (i == q1Index) {
				q1RequestDataEntry = requestDataEntry;
			}

			if (i == q2Index) {
				q2RequestDataEntry = requestDataEntry;
			}

			if (i == q3Index) {
				q3RequestDataEntry = requestDataEntry;
			}

			totalDuration += requestDataEntry.getDuration();
			totalRequestByteCount += requestDataEntry.getRequestByteCount();
			totalResponseByteCount += requestDataEntry.getResponseByteCount();

			i++;
		}

		System.out.println();
		System.out.println("#");
		System.out.printf("# Total Requests (%,d)\n", totalRequestCount);
		System.out.println("#");
		System.out.println();
		System.out.println(
			new RequestDataEntry(
				totalDuration, totalRequestByteCount, totalResponseByteCount));

		System.out.println();
		System.out.println("#");
		System.out.println("# Average Request");
		System.out.println("#");
		System.out.println();
		System.out.println(
			new RequestDataEntry(
				totalDuration / totalRequestCount,
				totalRequestByteCount / totalRequestCount,
				totalResponseByteCount / totalRequestCount));

		System.out.println();
		System.out.println("#");
		System.out.println("# Fastest Request");
		System.out.println("#");
		System.out.println();
		System.out.println(orderedRequestDataEntries.get(0));

		System.out.println();
		System.out.println("#");
		System.out.println("# Q1 Request");
		System.out.println("#");
		System.out.println();
		System.out.println(q1RequestDataEntry);

		System.out.println();
		System.out.println("#");
		System.out.println("# Q2 Request (median)");
		System.out.println("#");
		System.out.println();
		System.out.println(q2RequestDataEntry);

		System.out.println();
		System.out.println("#");
		System.out.println("# Q3 Request");
		System.out.println("#");
		System.out.println();
		System.out.println(q3RequestDataEntry);

		System.out.println();
		System.out.println("#");
		System.out.println("# Slowest Request");
		System.out.println("#");
		System.out.println();
		System.out.println(
			orderedRequestDataEntries.get(
				orderedRequestDataEntries.size() - 1));

		for (Map.Entry<String, List<RequestDataEntry>> entry :
				_groupedRequestDataEntriesMap.entrySet()) {

			List<RequestDataEntry> groupRequestDataEntries = entry.getValue();

			long groupDuration = 0;
			long groupRequestByteCount = 0;
			long groupResponseByteCount = 0;

			for (RequestDataEntry groupRequestDataEntry :
					groupRequestDataEntries) {

				groupDuration += groupRequestDataEntry.getDuration();

				groupRequestByteCount +=
					groupRequestDataEntry.getRequestByteCount();

				groupResponseByteCount +=
					groupRequestDataEntry.getResponseByteCount();
			}

			long groupRequestCount = groupRequestDataEntries.size();

			System.out.println();
			System.out.println("#");

			System.out.printf(
				"# Requests - %s (%,d)\n", entry.getKey(), groupRequestCount);

			System.out.println("#");
			System.out.println();

			System.out.println(
				new RequestDataEntry(
					groupDuration, groupRequestByteCount,
					groupResponseByteCount));

			if (groupRequestCount > 1) {
				System.out.println();
				System.out.println("#");

				System.out.printf(
					"# Requests (avg) - %s (%d)\n", entry.getKey(),
					groupRequestCount);

				System.out.println("#");
				System.out.println();
				System.out.println(
					new RequestDataEntry(
						groupDuration / groupRequestCount,
						groupRequestByteCount / groupRequestCount,
						groupResponseByteCount / groupRequestCount));
			}
		}

		System.out.println();
	}

	protected static String request(
			String urlPath, Map<String, String> urlParameters,
			Map<String, String> urlPathReplacements,
			HttpRequestMethod httpRequestMethod, String requestData)
		throws IOException {

		String spiraRestAPIURL =
			SpiraArtifact.SPIRA_BASE_URL + "services/v6_0/RestService.svc";

		if (urlPath.contains("/test-sets/search")) {
			spiraRestAPIURL = spiraRestAPIURL.replace("v6_0", "v5_0");
		}

		int requestCount = 0;

		while (true) {
			long start = System.currentTimeMillis();

			String responseData = null;

			urlPath = _applyURLPathReplacements(urlPath, urlPathReplacements);

			try {
				responseData = JenkinsResultsParserUtil.toString(
					JenkinsResultsParserUtil.combine(
						spiraRestAPIURL, urlPath,
						_toURLParametersString(urlParameters)),
					false, 0, httpRequestMethod, requestData, 0,
					_MILLIS_TIMEOUT_DEFAULT, null, true);

				return responseData;
			}
			catch (IOException ioException) {
				if (requestCount >= _RETRIES_SIZE_MAX_DEFAULT) {
					throw ioException;
				}

				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Retrying ", spiraRestAPIURL, "/", urlPath, " in ",
						String.valueOf(_SECONDS_RETRY_PERIOD_DEFAULT),
						" seconds"));

				JenkinsResultsParserUtil.sleep(
					_SECONDS_RETRY_PERIOD_DEFAULT * 1000);
			}
			finally {
				requestCount++;

				long duration = System.currentTimeMillis() - start;

				RequestDataEntry requestDataEntry = new RequestDataEntry(
					"/" + urlPath, duration, requestData, responseData);

				List<RequestDataEntry> requestDataEntries =
					_groupedRequestDataEntriesMap.get(
						requestDataEntry.getRequestURLPath());

				if (requestDataEntries == null) {
					requestDataEntries = Collections.synchronizedList(
						new ArrayList<RequestDataEntry>());
				}

				requestDataEntries.add(requestDataEntry);

				_groupedRequestDataEntriesMap.put(
					requestDataEntry.getRequestURLPath(), requestDataEntries);
			}
		}
	}

	protected static JSONArray requestJSONArray(
			String urlPath, Map<String, String> urlParameters,
			Map<String, String> urlPathReplacements,
			HttpRequestMethod httpRequestMethod, String requestData)
		throws IOException {

		return JenkinsResultsParserUtil.createJSONArray(
			request(
				urlPath, urlParameters, urlPathReplacements, httpRequestMethod,
				requestData));
	}

	protected static JSONObject requestJSONObject(
			String urlPath, Map<String, String> urlParameters,
			Map<String, String> urlPathReplacements,
			HttpRequestMethod httpRequestMethod, String requestData)
		throws IOException {

		return JenkinsResultsParserUtil.createJSONObject(
			request(
				urlPath, urlParameters, urlPathReplacements, httpRequestMethod,
				requestData));
	}

	protected static String toDateString(long timestamp) {
		return JenkinsResultsParserUtil.toDateString(
			new Date(timestamp), "yyyy-MM-dd'T'HH:mm:ss", "UTC");
	}

	protected static class RequestDataEntry
		implements Comparable<RequestDataEntry> {

		@Override
		public int compareTo(RequestDataEntry requestDataEntry) {
			return _duration.compareTo(requestDataEntry.getDuration());
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();

			if (_requestURLPath != null) {
				sb.append("Request URL Path: ");
				sb.append(_requestURLPath);
				sb.append("\n");
			}

			sb.append("Duration: ");
			sb.append(JenkinsResultsParserUtil.toDurationString(_duration));
			sb.append("\n");

			sb.append("Request Byte Count: ");
			sb.append(
				JenkinsResultsParserUtil.toByteCountString(_requestByteCount));
			sb.append("\n");

			sb.append("Response Byte Count: ");
			sb.append(
				JenkinsResultsParserUtil.toByteCountString(_responseByteCount));

			return sb.toString();
		}

		protected RequestDataEntry(
			long duration, long requestByteCount, long responseByteCount) {

			this(null, duration, requestByteCount, responseByteCount);
		}

		protected RequestDataEntry(
			String requestURLPath, long duration, long requestByteCount,
			long responseByteCount) {

			_requestURLPath = requestURLPath;
			_duration = duration;
			_requestByteCount = requestByteCount;
			_responseByteCount = responseByteCount;
		}

		protected RequestDataEntry(
			String requestURL, long duration, String requestData,
			String responseData) {

			this(
				requestURL, duration, _getByteCount(requestData),
				_getByteCount(responseData));
		}

		protected Long getDuration() {
			return _duration;
		}

		protected Long getRequestByteCount() {
			return _requestByteCount;
		}

		protected String getRequestURLPath() {
			return _requestURLPath;
		}

		protected Long getResponseByteCount() {
			return _responseByteCount;
		}

		private static long _getByteCount(String data) {
			if (data == null) {
				return 0;
			}

			byte[] bytes = data.getBytes(StandardCharsets.UTF_8);

			return bytes.length;
		}

		private final Long _duration;
		private final Long _requestByteCount;
		private final String _requestURLPath;
		private final Long _responseByteCount;

	}

	private static String _applyURLPathReplacements(
		String urlPath, Map<String, String> urlPathReplacements) {

		if (urlPath == null) {
			return "";
		}

		if (urlPathReplacements != null) {
			for (Map.Entry<String, String> urlPathReplacement :
					urlPathReplacements.entrySet()) {

				urlPath = urlPath.replace(
					"{" + urlPathReplacement.getKey() + "}",
					urlPathReplacement.getValue());
			}
		}

		if (urlPath.matches(".*\\{[^\\}]+\\}.*")) {
			throw new RuntimeException("Invalid url path " + urlPath);
		}

		if (urlPath.contains("?")) {
			throw new RuntimeException("Invalid url path " + urlPath);
		}

		if (!urlPath.startsWith("/")) {
			urlPath = "/" + urlPath;
		}

		return urlPath;
	}

	private static String _toURLParametersString(
		Map<String, String> urlParameters) {

		if ((urlParameters == null) || urlParameters.isEmpty()) {
			return "";
		}

		List<String> urlParameterStrings = new ArrayList<>();

		for (Map.Entry<String, String> urlParameter :
				urlParameters.entrySet()) {

			urlParameterStrings.add(
				urlParameter.getKey() + "=" + urlParameter.getValue());
		}

		return "?" + JenkinsResultsParserUtil.join("&", urlParameterStrings);
	}

	private static final int _MILLIS_TIMEOUT_DEFAULT = 0;

	private static final int _RETRIES_SIZE_MAX_DEFAULT = 3;

	private static final int _SECONDS_RETRY_PERIOD_DEFAULT = 5;

	private static final Map<String, List<RequestDataEntry>>
		_groupedRequestDataEntriesMap = Collections.synchronizedMap(
			new LinkedHashMap<String, List<RequestDataEntry>>());

}