/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.functional.test.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.poshi.runner.util.StringPool;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpResponseException;

/**
 * @author Cheryl Tang
 */
public class FaroTestDataUtil {

	public static synchronized void addDataSourceId(String name, String id) {
		_removeId(_dataSourceIds, id);

		_dataSourceIds.put(name, id);
	}

	public static synchronized void addSegmentId(String name, String id) {
		_removeId(_segmentIds, id);

		_segmentIds.put(name, id);
	}

	/**
	 * Asserts if a date is following a given SimpleDate pattern
	 *
	 * @param  dateToValidate the string of the date to be validated
	 * @param  pattern the pattern to check against the date
	 * @return <code>true</code> if the date is following the pattern and
	 *         <code>false</code> if not
	 */
	public static boolean assertDateFormat(
		String dateToValidate, String pattern) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		simpleDateFormat.setLenient(false);

		try {
			simpleDateFormat.parse(dateToValidate);

			return true;
		}
		catch (ParseException parseException1) {
			try {
				simpleDateFormat = new SimpleDateFormat("MMM d - MMM d");

				simpleDateFormat.setLenient(false);

				simpleDateFormat.parse(dateToValidate);

				return true;
			}
			catch (ParseException parseException2) {
				return false;
			}
		}
	}

	/**
	 * Cleans up all data sources, segments and any placeholder values created
	 * during testing.
	 */
	public static synchronized void cleanUp() {
		_deleteAll(
			FaroPagePool.getEndpoint("/contacts", "/data_source"),
			_dataSourceIds.entrySet());
		_deleteAll(
			FaroPagePool.getEndpoint("/contacts", "/individual_segment"),
			_segmentIds.entrySet());

		_placeholders.clear();
	}

	/**
	 * Deletes a data source created during testing by ID.
	 *
	 * @param  dataSourceId the ID of the data source to delete
	 * @throws Exception if an exception occurred
	 */
	public static synchronized void deleteDataSource(String dataSourceId)
		throws Exception {

		_delete(
			FaroPagePool.getEndpoint("/contacts", "/data_source"),
			dataSourceId);

		_dataSourceIds.remove(dataSourceId);
	}

	/**
	 * Deletes a segment created during testing by ID.
	 *
	 * @param  segmentId the ID of the segment to delete
	 * @throws Exception if an exception occurred
	 */
	public static synchronized void deleteSegment(String segmentId)
		throws Exception {

		_delete(
			FaroPagePool.getEndpoint("/contacts", "/individual_segment"),
			segmentId);

		_segmentIds.remove(segmentId);
	}

	public static void deleteTokenDataSource() throws Exception {
		String endpointUrl = FaroPagePool.getEndpoint(
			"/contacts", "/data_source");

		AppResponse appResponse = FaroRestUtil.get(
			endpointUrl + "?name=Liferay%20DXP&delta=1", false);

		JsonNode jsonNode = _objectMapper.readTree(
			appResponse.getResponseBody()
		).findPath(
			"id"
		);

		String dataSourceId = jsonNode.textValue();

		if (dataSourceId == null) {
			return;
		}

		deleteDataSource(dataSourceId);

		_assureDataSourceDeletion(endpointUrl, dataSourceId, 15, 1500);
	}

	public static synchronized AppResponse getAppResponse() {
		return _appResponse;
	}

	public static synchronized String getDataSourceId(String name) {
		return _dataSourceIds.get(name);
	}

	public static String getPlaceholder(String key) {
		return _placeholders.get(key);
	}

	public static synchronized String getSegmentId(String name) {
		return _segmentIds.get(name);
	}

	/**
	 * Parses a string and replaces ${Random.n} placeholders with a unique
	 * random string generated from current unix time.
	 *
	 * @param  string the string to parse
	 * @return returns the parsed string with its placeholders replaced
	 */
	public static String parsePlaceholders(String string) {
		if (string == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		if (string.contains("Random")) {
			Matcher matcher = _randomPlaceholderPattern.matcher(string);

			while (matcher.find()) {
				String key = matcher.group();

				String value = _placeholders.get(key);

				if (value == null) {
					value =
						(System.currentTimeMillis() / 1000) + matcher.group(1);

					_placeholders.put(key, value);
				}

				matcher.appendReplacement(sb, value);
			}

			matcher.appendTail(sb);
		}
		else {
			Matcher matcher = _placeholderPattern.matcher(string);

			while (matcher.find()) {
				String key = matcher.group(1);

				String value = _placeholders.get(key);

				matcher.appendReplacement(sb, value);
			}

			matcher.appendTail(sb);
		}

		return sb.toString();
	}

	public static void setPlaceholder(String key, String value) {
		_placeholders.put(key, value);
	}

	public static synchronized void storeAppResponse(AppResponse appResponse) {
		_appResponse = appResponse;
	}

	private static void _assureDataSourceDeletion(
			String endpointURL, String id, int maxRetries, int pollInterval)
		throws Exception {

		int retryCount = 0;

		while (retryCount <= maxRetries) {
			AppResponse appResponse = FaroRestUtil.get(
				endpointURL.concat(
					StringPool.FORWARD_SLASH
				).concat(
					id
				),
				true);

			if ((appResponse.getHttpStatusCode() == 404) ||
				(appResponse.getHttpStatusCode() == 500)) {

				return;
			}

			JsonNode jsonNode = _objectMapper.readTree(
				appResponse.getResponseBody()
			).path(
				"state"
			);

			String state = jsonNode.textValue();

			if (state.equals("IN_PROGRESS_DELETING")) {
				System.out.println("Entity deletion is in progress: " + id);
			}
			else if (state.equals("DELETE_ERROR")) {
				throw new Exception("Unable to delete the entity: " + id);
			}
			else if (state.equals("READY")) {
				System.out.println("Retrying deletion: " + id);

				_delete(endpointURL, id);
			}

			Thread.sleep(pollInterval);

			retryCount++;
		}
	}

	private static void _delete(String endpointURL, String id)
		throws Exception {

		FaroRestUtil.delete(
			endpointURL.concat(
				StringPool.FORWARD_SLASH
			).concat(
				id
			),
			false);
	}

	private static void _deleteAll(
		String endpointURL, Set<Map.Entry<String, String>> entries) {

		entries.removeIf(
			entry -> {
				try {
					_delete(endpointURL, entry.getValue());

					if (endpointURL.contains("data_source")) {
						_assureDataSourceDeletion(
							endpointURL, entry.getValue(), 60, 1500);
					}
				}
				catch (Exception exception) {
					if (exception instanceof HttpResponseException) {
						HttpResponseException httpResponseException =
							(HttpResponseException)exception;

						if (httpResponseException.getStatusCode() != 404) {
							exception.printStackTrace();
						}
					}
					else {
						exception.printStackTrace();
					}
				}

				return true;
			});
	}

	private static void _removeId(Map<String, String> map, String id) {
		if (!map.containsValue(id)) {
			return;
		}

		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (id.equals(entry.getValue())) {
				map.remove(entry.getKey());

				break;
			}
		}
	}

	private static AppResponse _appResponse;
	private static final Map<String, String> _dataSourceIds = new HashMap<>();
	private static final ObjectMapper _objectMapper = new ObjectMapper();
	private static final Pattern _placeholderPattern = Pattern.compile(
		"\\$\\{([0-9a-zA-Z_]+)}");
	private static final Map<String, String> _placeholders = new HashMap<>();
	private static final Pattern _randomPlaceholderPattern = Pattern.compile(
		"\\$\\{Random\\.(\\d+)}");
	private static final Map<String, String> _segmentIds = new HashMap<>();

}