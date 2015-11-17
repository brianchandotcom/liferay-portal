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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class JenkinsResultsParserUtil {

	public static String expandSlaveRange(String value) {
		StringBuilder sb = new StringBuilder();

		for (String hostName : value.split(",")) {
			hostName = hostName.trim();

			int x = hostName.indexOf("..");

			if (x == -1) {
				if (sb.length() > 0) {
					sb.append(",");
				}

				sb.append(hostName);

				continue;
			}

			int y = hostName.lastIndexOf("-") + 1;

			String prefix = hostName.substring(0, y);

			int first = Integer.parseInt(hostName.substring(y, x));
			int last = Integer.parseInt(hostName.substring(x + 2));

			for (int current = first; current <= last; current++) {
				if (sb.length() > 0) {
					sb.append(",");
				}

				sb.append(prefix);
				sb.append(current);
			}
		}

		return sb.toString();
	}

	public static String fixJSON(String json) {
		json = json.replaceAll("'", "&#39;");
		json = json.replaceAll("<", "&#60;");
		json = json.replaceAll(">", "&#62;");
		json = json.replaceAll("\\(", "&#40;");
		json = json.replaceAll("\\)", "&#41;");
		json = json.replaceAll("\\[", "&#91;");
		json = json.replaceAll("\\\"", "&#34;");
		json = json.replaceAll("\\\\", "&#92;");
		json = json.replaceAll("\\]", "&#93;");
		json = json.replaceAll("\\{", "&#123;");
		json = json.replaceAll("\\}", "&#125;");
		json = json.replaceAll("\n", "<br />");
		json = json.replaceAll("\t", "&#09;");
		json = json.replaceAll("\u00BB", "&raquo;");

		return json;
	}

	public static String fixURL(String url) {
		url = url.replace("(", "%28");
		url = url.replace(")", "%29");
		url = url.replace("[", "%5B");
		url = url.replace("]", "%5D");

		return url;
	}

	public static String getAxisVariable(JSONObject jsonObject)
		throws Exception {

		JSONArray actionsJSONArray = (JSONArray)jsonObject.get("actions");

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			Object object = actionsJSONArray.get(i);

			if (object.equals(JSONObject.NULL)) {
				continue;
			}

			JSONObject actionsJSONObject = actionsJSONArray.getJSONObject(i);

			JSONArray parametersJSONArray = actionsJSONObject.optJSONArray(
				"parameters");

			if (parametersJSONArray == null) {
				continue;
			}

			for (int j = 0; j < parametersJSONArray.length(); j++) {
				JSONObject parametersJSONObject =
					parametersJSONArray.getJSONObject(j);

				String name = parametersJSONObject.getString("name");

				if (name.contains("AXIS_VARIABLE")) {
					return parametersJSONObject.getString("value");
				}
			}
		}

		return "";
	}

	public static String getJobVariant(JSONObject jsonObject) throws Exception {
		JSONArray actionsJSONArray = jsonObject.getJSONArray("actions");

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			Object object = actionsJSONArray.get(i);

			if (object.equals(org.json.JSONObject.NULL)) {
				continue;
			}

			JSONObject actionsJSONObject = actionsJSONArray.getJSONObject(i);

			if (actionsJSONObject.has("parameters")) {
				JSONArray parametersJSONArray = actionsJSONObject.getJSONArray(
					"parameters");

				for (int j = 0; j < parametersJSONArray.length(); j++) {
					JSONObject parametersJSONObject =
						parametersJSONArray.getJSONObject(j);

					if ("JOB_VARIANT".contains(
							parametersJSONObject.getString("name"))) {

						return parametersJSONObject.getString("value");
					}
				}
			}
		}

		return "";
	}

	public static String getJobVariant(String json) throws Exception {
		return getJobVariant(new JSONObject(json));
	}

	public static String getLocalURL(String remoteURL) {
		remoteURL = remoteURL.replace(
			"${user.dir}", System.getProperty("user.dir"));

		Matcher matcher = _localURLPattern1.matcher(remoteURL);

		if (matcher.find()) {
			StringBuilder sb = new StringBuilder();

			sb.append("http://test-");
			sb.append(matcher.group(1));
			sb.append("/");
			sb.append(matcher.group(1));
			sb.append("/");

			return remoteURL.replaceAll(matcher.group(0), sb.toString());
		}

		matcher = _localURLPattern2.matcher(remoteURL);

		if (matcher.find()) {
			StringBuilder sb = new StringBuilder();

			sb.append("http://");
			sb.append(matcher.group(1));
			sb.append("/");

			return remoteURL.replaceAll(matcher.group(0), sb.toString());
		}

		return remoteURL;
	}

	public static String read(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.toURI())));
	}

	public static JSONObject toJSONObject(String url) throws Exception {
		return toJSONObject(url, true);
	}

	public static JSONObject toJSONObject(String url, boolean checkCache)
		throws Exception {

		return new JSONObject(toString(url, checkCache));
	}

	public static String toString(String url) throws Exception {
		return toString(url, true);
	}

	public static String toString(String url, boolean checkCache)
		throws Exception {

		url = fixURL(url);

		String key = url.replace("//", "/");

		if (checkCache && _toStringCache.containsKey(key) &&
			!url.startsWith("file:")) {

			System.out.println("Loading " + url);

			return _toStringCache.get(key);
		}

		int retryCount = 0;

		while (true) {
			try {
				System.out.println("Downloading " + url);

				StringBuilder sb = new StringBuilder();

				URL urlObject = new URL(url);

				InputStreamReader inputStreamReader = new InputStreamReader(
					urlObject.openStream());

				BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

				String line = null;

				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line);
					sb.append("\n");
				}

				bufferedReader.close();

				if (!url.startsWith("file:")) {
					_toStringCache.put(key, sb.toString());
				}

				return sb.toString();
			}
			catch (FileNotFoundException e) {
				retryCount++;

				if (retryCount > _MAX_RETRIES) {
					throw e;
				}

				System.out.println(
					"Download file not found. Waiting 5 seconds before retry." +
						" Retry " + retryCount + " of " + _MAX_RETRIES);

				Thread.sleep(5000);
			}
		}
	}

	private static final int _MAX_RETRIES = 3;

	private static final Pattern _localURLPattern1 = Pattern.compile(
		"https://test.liferay.com/([0-9]+)/");
	private static final Pattern _localURLPattern2 = Pattern.compile(
		"https://(test-[0-9]+-[0-9]+).liferay.com/");
	private static final Map<String, String> _toStringCache = new HashMap<>();

}