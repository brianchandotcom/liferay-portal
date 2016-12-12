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
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.UnknownHostException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class JenkinsResultsParserUtil {

	public static JSONObject createJSONObject(String jsonString)
		throws IOException {

		JSONObject jsonObject = new JSONObject(jsonString);

		if (jsonObject.isNull("duration") || jsonObject.isNull("result") ||
			jsonObject.isNull("url")) {

			return jsonObject;
		}

		String url = jsonObject.getString("url");

		if (!url.contains("AXIS_VARIABLE")) {
			return jsonObject;
		}

		Object result = jsonObject.get("result");

		if (result instanceof JSONObject) {
			return jsonObject;
		}

		if ((jsonObject.getInt("duration") == 0) && result.equals("FAILURE")) {
			String actualResult = getActualResult(url);

			System.out.println("Actual Result: " + actualResult);

			jsonObject.putOpt("result", actualResult);
		}

		return jsonObject;
	}

	public static URL createURL(String urlString) throws Exception {
		URL url = new URL(urlString);

		return encode(url);
	}

	public static String decode(String url)
		throws UnsupportedEncodingException {

		return URLDecoder.decode(url, "UTF-8");
	}

	public static String encode(String url)
		throws MalformedURLException, URISyntaxException {

		URL encodedURL = encode(new URL(url));

		return encodedURL.toExternalForm();
	}

	public static URL encode(URL url)
		throws MalformedURLException, URISyntaxException {

		URI uri = new URI(
			url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(),
			url.getPath(), url.getQuery(), url.getRef());

		String uriASCIIString = uri.toASCIIString();

		return new URL(uriASCIIString.replace("#", "%23"));
	}

	public static Process executeBashCommands(
			boolean exitOnFirstFail, String... commands)
		throws InterruptedException, IOException {

		System.out.print("Executing commands: ");

		for (String command : commands) {
			System.out.println(command);
		}

		Runtime runtime = Runtime.getRuntime();

		String[] bashCommands = new String[3];

		bashCommands[0] = "/bin/sh";
		bashCommands[1] = "-c";

		String commandTerminator = ";";

		if (exitOnFirstFail) {
			commandTerminator = "&&";
		}

		StringBuffer sb = new StringBuffer();

		for (String command : commands) {
			sb.append(command);
			sb.append(commandTerminator);
			sb.append(" ");
		}

		sb.append("echo Finished executing Bash commands.\n");

		bashCommands[2] = sb.toString();

		Process process = runtime.exec(bashCommands);

		System.out.println(
			"Output stream: " + readInputStream(process.getInputStream()));

		int returnCode = process.waitFor();

		if (returnCode != 0) {
			System.out.println(
				"Error stream: " + readInputStream(process.getErrorStream()));
		}

		return process;
	}

	public static Process executeBashCommands(String... commands)
		throws InterruptedException, IOException {

		return executeBashCommands(true, commands);
	}

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

	public static String fixFileName(String fileName) {
		String prefix = "";

		if (fileName.startsWith("file:")) {
			prefix = "file:";

			fileName = fileName.substring(prefix.length());
		}

		fileName = fileName.replace(">", "[gt]");
		fileName = fileName.replace("<", "[lt]");
		fileName = fileName.replace("|", "[pi]");
		fileName = fileName.replace("?", "[qt]");
		fileName = fileName.replace(":", "[sc]");

		return prefix + fileName;
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

	public static String format(Element element) throws IOException {
		Writer writer = new CharArrayWriter();

		XMLWriter xmlWriter = new XMLWriter(
			writer, OutputFormat.createPrettyPrint());

		xmlWriter.write(element);

		return writer.toString();
	}

	public static String getActualResult(String buildURL) throws IOException {
		String progressiveText = toString(
			getLocalURL(buildURL + "/logText/progressiveText"), false);

		if (progressiveText.contains("Finished:")) {
			if (progressiveText.contains("Finished: SUCCESS")) {
				return "SUCCESS";
			}

			if (progressiveText.contains("Finished: UNSTABLE")) {
				return "FAILURE";
			}

			if (progressiveText.contains("Finished: FAILURE")) {
				return "FAILURE";
			}
		}

		return null;
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

	public static String getAxisVariable(String axisBuildURL) throws Exception {
		String url = decode(axisBuildURL);

		String label = "AXIS_VARIABLE=";

		int x = url.indexOf(label);

		if (x != -1) {
			url = url.substring(x + label.length());

			int y = url.indexOf(",");

			return url.substring(0, y);
		}

		return "";
	}

	public static Properties getBuildProperties() throws IOException {
		Properties properties = new Properties();

		String url =
			"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
				"/liferay-jenkins-ee/build.properties";

		properties.load(new StringReader(toString(getLocalURL(url), false)));

		url =
			"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
				"/liferay-jenkins-ee/commands/build.properties";

		properties.load(new StringReader(toString(getLocalURL(url), false)));

		return properties;
	}

	public static String getHostName(String defaultHostName) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();

			return inetAddress.getHostName();
		}
		catch (UnknownHostException uhe) {
			return defaultHostName;
		}
	}

	public static String getJobVariant(JSONObject jsonObject) throws Exception {
		JSONArray actionsJSONArray = jsonObject.getJSONArray("actions");

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			Object object = actionsJSONArray.get(i);

			if (object.equals(JSONObject.NULL)) {
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
		if (remoteURL.contains("${dependencies.url}")) {
			remoteURL = fixFileName(remoteURL);

			String fileURL = remoteURL.replace(
				"${dependencies.url}", DEPENDENCIES_URL_FILE);

			File file = new File(fileURL.substring("file:".length()));

			if (file.exists()) {
				remoteURL = fileURL;
			}
			else {
				remoteURL = remoteURL.replace(
					"${dependencies.url}", DEPENDENCIES_URL_HTTP);
			}
		}

		if (remoteURL.startsWith("file")) {
			remoteURL = fixFileName(remoteURL);
		}

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

	public static List<String> getMasters(
		Properties buildProperties, String prefix) {

		List<String> masters = new ArrayList<>();

		for (int i = 1; buildProperties.containsKey(
				"master.slaves(" + prefix + "-" + i + ")"); i++) {

			masters.add(prefix + "-" + i);
		}

		return masters;
	}

	public static List<String> getRandomList(List<String> list, int size) {
		if (list.size() < size) {
			throw new IllegalStateException(
				"Size must not exceed the size of the list");
		}

		if (size == list.size()) {
			return list;
		}

		List<String> randomList = new ArrayList<>(size);

		for (int i = 0; i < size; i++) {
			String item = null;

			while (true) {
				item = list.get(getRandomValue(0, list.size() - 1));

				if (randomList.contains(item)) {
					continue;
				}

				randomList.add(item);

				break;
			}
		}

		return randomList;
	}

	public static int getRandomValue(int start, int end) {
		int size = Math.abs(end - start);

		double randomDouble = Math.random();

		return start + (int)Math.round(size * randomDouble);
	}

	public static String getRegexLiteral(String string) {
		if (string == null) {
			throw new NullPointerException("String may not be null");
		}

		String specialCharactersString = "\\^$.|?*+()[]{}";

		StringBuilder sb = new StringBuilder();

		for (char character : string.toCharArray()) {
			if (specialCharactersString.indexOf(character) != -1) {
				sb.append('\\');
			}

			sb.append(character);
		}

		return sb.toString();
	}

	public static List<String> getSlaves(
		Properties buildProperties, String masterPatternString) {

		List<String> slaves = new ArrayList<>();

		Pattern masterPattern = Pattern.compile(
			"master.slaves\\(" + masterPatternString + "\\)");

		for (Object key : buildProperties.keySet()) {
			Matcher keyMatcher = masterPattern.matcher(key.toString());

			if (keyMatcher.find()) {
				String slavesString = expandSlaveRange(
					buildProperties.getProperty(key.toString()));

				for (String slave : slavesString.split(",")) {
					slaves.add(slave.trim());
				}
			}
		}

		return slaves;
	}

	public static List<String> getSlaves(String masterPatternString)
		throws Exception {

		return getSlaves(getBuildProperties(), masterPatternString);
	}

	public static String read(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.toURI())));
	}

	public static String readInputStream(InputStream inputStream)
		throws IOException {

		StringBuffer sb = new StringBuffer();

		byte[] bytes = new byte[1024];

		int size = inputStream.read(bytes);

		while (size > 0) {
			sb.append(new String(Arrays.copyOf(bytes, size)));

			size = inputStream.read(bytes);
		}

		return sb.toString();
	}

	public static void sendEmail(
			String body, String from, String subject, String to)
		throws Exception {

		File file = new File("/tmp/" + body.hashCode() + ".txt");

		write(file, body);

		try {
			StringBuffer sb = new StringBuffer();

			sb.append("cat ");
			sb.append(file.getAbsolutePath());
			sb.append(" | mail -v -s ");
			sb.append("\"");
			sb.append(subject);
			sb.append("\" -r \"");
			sb.append(from);
			sb.append("\" \"");
			sb.append(to);
			sb.append("\"");

			executeBashCommands(sb.toString());
		}
		finally {
			file.delete();
		}
	}

	public static void sleep(long duration) {
		try {
			Thread.sleep(duration);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}
	}

	public static JSONObject toJSONObject(String url) throws IOException {
		return toJSONObject(
			url, true, _MAX_RETRIES_DEFAULT, _RETRY_PERIOD_DEFAULT,
			_TIMEOUT_DEFAULT);
	}

	public static JSONObject toJSONObject(String url, boolean checkCache)
		throws IOException {

		return createJSONObject(
			toString(
				url, checkCache, _MAX_RETRIES_DEFAULT, _RETRY_PERIOD_DEFAULT,
				_TIMEOUT_DEFAULT));
	}

	public static JSONObject toJSONObject(
			String url, boolean checkCache, int timeout)
		throws IOException {

		return toJSONObject(
			url, checkCache, _MAX_RETRIES_DEFAULT, _RETRY_PERIOD_DEFAULT,
			timeout);
	}

	public static JSONObject toJSONObject(
			String url, boolean checkCache, int maxRetries, int retryPeriod,
			int timeout)
		throws IOException {

		String response = toString(
			url, checkCache, maxRetries, retryPeriod, timeout);

		if ((response == null) ||
			response.endsWith("was truncated due to its size.")) {

			return null;
		}

		return createJSONObject(response);
	}

	public static String toString(String url) throws IOException {
		return toString(
			url, true, _MAX_RETRIES_DEFAULT, _RETRY_PERIOD_DEFAULT,
			_TIMEOUT_DEFAULT);
	}

	public static String toString(String url, boolean checkCache)
		throws IOException {

		return toString(
			url, checkCache, _MAX_RETRIES_DEFAULT, _RETRY_PERIOD_DEFAULT,
			_TIMEOUT_DEFAULT);
	}

	public static String toString(String url, boolean checkCache, int timeout)
		throws IOException {

		return toString(
			url, checkCache, _MAX_RETRIES_DEFAULT, _RETRY_PERIOD_DEFAULT,
			timeout);
	}

	public static String toString(
			String url, boolean checkCache, int maxRetries, int retryPeriod,
			int timeout)
		throws IOException {

		url = fixURL(url);

		String key = url.replace("//", "/");

		if (checkCache && _toStringCache.containsKey(key) &&
			!url.startsWith("file:")) {

			System.out.println("Loading " + url);

			String response = _toStringCache.get(key);

			if (response != null) {
				return response;
			}

			_toStringCache.remove(key);
		}

		int retryCount = 0;

		while (true) {
			try {
				System.out.println("Downloading " + url);

				StringBuilder sb = new StringBuilder();

				URL urlObject = new URL(url);

				URLConnection urlConnection = urlObject.openConnection();

				if (timeout != 0) {
					urlConnection.setConnectTimeout(timeout);
					urlConnection.setReadTimeout(timeout);
				}

				int bytes = 0;
				String line = null;

				try (BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(
							urlConnection.getInputStream()))) {

					while ((line = bufferedReader.readLine()) != null) {
						byte[] lineBytes = line.getBytes();

						bytes += lineBytes.length;

						if (bytes > (30 * 1024 * 1024)) {
							sb.append("Response for ");
							sb.append(url);
							sb.append(" was truncated due to its size.");

							break;
						}

						sb.append(line);
						sb.append("\n");
					}
				}

				if (!url.startsWith("file:") && (bytes < (3 * 1024 * 1024))) {
					_toStringCache.put(key, sb.toString());
				}

				return sb.toString();
			}
			catch (IOException ioe) {
				retryCount++;

				if ((maxRetries >= 0) && (retryCount >= maxRetries)) {
					throw ioe;
				}

				System.out.println("Retry in " + retryPeriod + " seconds");

				sleep(1000 * retryPeriod);
			}
		}
	}

	public static void write(File file, String content) throws IOException {
		System.out.println(
			"Write file " + file + " with length " + content.length());

		File parentDir = file.getParentFile();

		if ((parentDir != null) && !parentDir.exists()) {
			System.out.println("Make parent directories for " + file);

			parentDir.mkdirs();
		}

		Files.write(Paths.get(file.toURI()), content.getBytes());
	}

	public static void write(String path, String content) throws IOException {
		if (path.startsWith("${dependencies.url}")) {
			path = path.replace(
				"${dependencies.url}",
				DEPENDENCIES_URL_FILE.replace("file:", ""));
		}

		write(new File(path), content);
	}

	protected static final String DEPENDENCIES_URL_FILE;

	protected static final String DEPENDENCIES_URL_HTTP =
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-jenkins-results-parser-samples-ee/1/";

	static {
		File dependenciesDir = new File("src/test/resources/dependencies/");

		try {
			URI uri = dependenciesDir.toURI();

			URL url = uri.toURL();

			DEPENDENCIES_URL_FILE = url.toString();
		}
		catch (MalformedURLException murle) {
			throw new RuntimeException(murle);
		}
	}

	private static final int _MAX_RETRIES_DEFAULT = 3;

	private static final int _RETRY_PERIOD_DEFAULT = 5;

	private static final int _TIMEOUT_DEFAULT = 0;

	private static final Pattern _localURLPattern1 = Pattern.compile(
		"https://test.liferay.com/([0-9]+)/");
	private static final Pattern _localURLPattern2 = Pattern.compile(
		"https://(test-[0-9]+-[0-9]+).liferay.com/");

	private static final Map<String, String> _toStringCache =
		new LinkedHashMap<String, String>(50) {

			@Override
			protected boolean removeEldestEntry(Entry<String, String> entry) {
				if (size() > 50) {
					return true;
				}

				return false;
			}

		};

}