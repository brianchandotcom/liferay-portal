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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class JenkinsResultsParserUtil {

	public static final String[] DEFAULT_BUILD_PROPERTIES_URLS = {
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-jenkins-ee/build.properties",
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-jenkins-ee/commands/build.properties",
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-portal/build.properties",
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-portal/ci.properties",
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-portal/test.properties"
	};

	public static boolean debug;

	public static String combine(String...strings) {
		if ((strings == null) || (strings.length == 0)) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		for (String string : strings) {
			sb.append(string);
		}

		return sb.toString();
	}

	public static void copy(File source, File target) throws IOException {
		try {
			if (!source.exists()) {
				throw new FileNotFoundException(
					source.getPath() + " does not exist");
			}

			if (target.exists()) {
				delete(target);
			}

			if (source.isDirectory()) {
				target.mkdir();

				for (File file : source.listFiles()) {
					copy(file, new File(target, file.getName()));
				}

				return;
			}

			try (FileInputStream fileInputStream =
					new FileInputStream(source)) {

				try (FileOutputStream fileOutputStream =
						new FileOutputStream(target)) {

					Files.copy(Paths.get(source.toURI()), fileOutputStream);

					fileOutputStream.flush();
				}
			}
		}
		catch (IOException ioe) {
			if (target.exists()) {
				delete(target);
			}

			throw ioe;
		}
	}

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

	public static void delete(File file) throws IOException {
		if (!file.exists()) {
			System.out.println(
				"Unable to delete because file does not exist " +
					file.getPath());

			return;
		}

		if (file.isDirectory()) {
			for (File subfile : file.listFiles()) {
				delete(subfile);
			}
		}

		file.delete();
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
			boolean exitOnFirstFail, File basedir, long timeout,
			String... commands)
		throws InterruptedException, IOException {

		System.out.print("Executing commands: ");

		for (String command : commands) {
			System.out.println(command);
		}

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

		ProcessBuilder processBuilder = new ProcessBuilder(bashCommands);

		processBuilder.directory(basedir.getAbsoluteFile());

		Process process = processBuilder.start();

		long duration = 0;
		long start = System.currentTimeMillis();
		int returnCode = -1;

		sleep(25);

		while ((returnCode == -1) && (duration < timeout)) {
			duration = System.currentTimeMillis() - start;

			try {
				returnCode = process.exitValue();
			}
			catch (IllegalThreadStateException itse) {
				returnCode = -1;
			}

			sleep(100);
		}

		if (returnCode == -1) {
			process.destroy();

			throw new RuntimeException(
				combine(
					"Timeout occurred while executing bash commands: ",
					bashCommands[2]));
		}

		if (debug) {
			InputStream inputStream = process.getInputStream();

			inputStream.mark(inputStream.available());

			System.out.println(
				"Output stream: " + readInputStream(inputStream));

			inputStream.reset();
		}

		if (debug && (returnCode != 0)) {
			InputStream inputStream = process.getErrorStream();

			inputStream.mark(inputStream.available());

			System.out.println("Error stream: " + readInputStream(inputStream));

			inputStream.reset();
		}

		return process;
	}

	public static Process executeBashCommands(
			boolean exitOnFirstFail, String... commands)
		throws InterruptedException, IOException {

		return executeBashCommands(
			exitOnFirstFail, new File("."), _BASH_COMMAND_TIMEOUT_DEFAULT,
			commands);
	}

	public static Process executeBashCommands(String... commands)
		throws InterruptedException, IOException {

		return executeBashCommands(
			true, new File("."), _BASH_COMMAND_TIMEOUT_DEFAULT, commands);
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

	public static List<File> findFiles(File basedir, String regex) {
		List<File> files = new ArrayList<>();

		for (File file : basedir.listFiles()) {
			String fileName = file.getName();

			if (file.isDirectory()) {
				files.addAll(findFiles(file, regex));
			}
			else if (fileName.matches(regex)) {
				files.add(file);
			}
		}

		return files;
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

		if ((_buildProperties != null) && !_buildProperties.isEmpty()) {
			properties.putAll(_buildProperties);

			return properties;
		}

		if (_buildPropertiesURLs == null) {
			_buildPropertiesURLs = DEFAULT_BUILD_PROPERTIES_URLS;
		}

		for (String url : _buildPropertiesURLs) {
			properties.load(
				new StringReader(toString(getLocalURL(url), false)));
		}

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

	public static List<JenkinsMaster> getJenkinsMasters(
		Properties buildProperties, String prefix) {

		List<JenkinsMaster> jenkinsMasters = new ArrayList<>();

		for (int i = 1;
			buildProperties.containsKey(
				"master.slaves(" + prefix + "-" + i + ")");
			i++) {

			jenkinsMasters.add(new JenkinsMaster(prefix + "-" + i));
		}

		return jenkinsMasters;
	}

	public static String getJobVariant(JSONObject jsonObject) {
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

		String localURL = remoteURL;
		String localURLQueryString = "";

		int x = remoteURL.indexOf("?");

		if (x != -1) {
			localURL = remoteURL.substring(0, x);
			localURLQueryString = remoteURL.substring(x);
		}

		Matcher remoteURLAuthorityMatcher1 =
			_remoteURLAuthorityPattern1.matcher(localURL);
		Matcher remoteURLAuthorityMatcher2 =
			_remoteURLAuthorityPattern2.matcher(localURL);

		if (remoteURLAuthorityMatcher1.find()) {
			String localURLAuthority = combine(
				"http://test-", remoteURLAuthorityMatcher1.group(1), "/",
				remoteURLAuthorityMatcher1.group(1), "/");
			String remoteURLAuthority = remoteURLAuthorityMatcher1.group(0);

			localURL = localURL.replaceAll(
				remoteURLAuthority, localURLAuthority);
		}
		else if (remoteURLAuthorityMatcher2.find()) {
			String localURLAuthority = combine(
				"http://", remoteURLAuthorityMatcher2.group(1), "/");
			String remoteURLAuthority = remoteURLAuthorityMatcher2.group(0);

			localURL = localURL.replaceAll(
				remoteURLAuthority, localURLAuthority);
		}

		return localURL + localURLQueryString;
	}

	public static String getMostAvailableMasterURL(
		String baseInvocationURL, int invokedBatchSize) {

		String loadBalancerServiceURL =
			_LOAD_BALANCER_SERVICE_URL_TEMPLATE.replace(
				"${baseInvocationURL}", baseInvocationURL);

		loadBalancerServiceURL = loadBalancerServiceURL.replace(
			"${invokedBatchSize}", Integer.toString(invokedBatchSize));

		try {
			JSONObject jsonObject = toJSONObject(loadBalancerServiceURL);

			return jsonObject.getString("mostAvailableMasterURL");
		}
		catch (IOException ioe) {
			Properties buildProperties = null;

			try {
				buildProperties = getBuildProperties();
			}
			catch (IOException ioe2) {
				throw new RuntimeException(
					"Unable to get build properties", ioe2);
			}

			List<JenkinsMaster> availableJenkinsMasters =
				LoadBalancerUtil.getAvailableJenkinsMasters(
					LoadBalancerUtil.getMasterPrefix(baseInvocationURL),
					buildProperties);

			Random random = new Random(System.currentTimeMillis());

			JenkinsMaster randomJenkinsMaster = availableJenkinsMasters.get(
				random.nextInt(availableJenkinsMasters.size()));

			return "http://" + randomJenkinsMaster.getMasterName();
		}
	}

	public static String getNounForm(
		int count, String plural, String singular) {

		if (count == 1) {
			return singular;
		}

		return plural;
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
		Properties buildProperties, String jenkinsMasterPatternString) {

		List<String> slaves = new ArrayList<>();

		Pattern jenkinsSlavesPropertyNamePattern = Pattern.compile(
			"master.slaves\\(" + jenkinsMasterPatternString + "\\)");

		for (Object propertyName : buildProperties.keySet()) {
			Matcher jenkinsSlavesPropertyNameMatcher =
				jenkinsSlavesPropertyNamePattern.matcher(
					propertyName.toString());

			if (jenkinsSlavesPropertyNameMatcher.find()) {
				String slavesString = expandSlaveRange(
					buildProperties.getProperty(propertyName.toString()));

				for (String slave : slavesString.split(",")) {
					slaves.add(slave.trim());
				}
			}
		}

		return slaves;
	}

	public static List<String> getSlaves(String jenkinsMasterPatternString)
		throws Exception {

		return getSlaves(getBuildProperties(), jenkinsMasterPatternString);
	}

	public static String merge(String... strings) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < strings.length; i++) {
			sb.append(strings[i]);

			if (i < (strings.length - 1)) {
				sb.append(",");
			}
		}

		return sb.toString();
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

	public static String redact(String string) {
		if (_redactTokens == null) {
			_redactTokens = new HashSet<>();

			Properties properties = null;

			try {
				properties = getBuildProperties();
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to get build properties", ioe);
			}

			for (int i =
				1; properties.containsKey(_getRedactTokenKey(i)); i++) {

				String key = properties.getProperty(_getRedactTokenKey(i));

				String redactToken = key;

				if (key.startsWith("${") && key.endsWith("}")) {
					redactToken = properties.getProperty(
						key.substring(2, key.length() - 1));
				}

				if ((redactToken != null) && !redactToken.isEmpty()) {
					_redactTokens.add(redactToken);
				}
			}

			_redactTokens.remove("test");
		}

		for (String redactToken : _redactTokens) {
			string = string.replace(redactToken, "[REDACTED]");
		}

		return string;
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

	public static void setBuildProperties(Hashtable<?, ?> buildProperties) {
		_buildPropertiesURLs = null;

		_buildProperties = buildProperties;
	}

	public static void setBuildProperties(String... urls) {
		_buildProperties = null;

		_buildPropertiesURLs = urls;
	}

	public static void sleep(long duration) {
		try {
			Thread.sleep(duration);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}
	}

	public static String toDurationString(long duration) {
		StringBuilder sb = new StringBuilder();

		duration = _appendDurationStringForUnit(
			duration, _MILLIS_IN_DAY, sb, "day", "days");

		duration = _appendDurationStringForUnit(
			duration, _MILLIS_IN_HOUR, sb, "hour", "hours");

		duration = _appendDurationStringForUnit(
			duration, _MILLIS_IN_MINUTE, sb, "minute", "minutes");

		duration = _appendDurationStringForUnit(
			duration, _MILLIS_IN_SECOND, sb, "second", "seconds");

		duration = _appendDurationStringForUnit(duration, 1, sb, "ms", "ms");

		String durationString = sb.toString();

		if (durationString.endsWith(" ")) {
			durationString = durationString.substring(
				0, durationString.length() - 1);
		}

		return durationString;
	}

	public static JSONArray toJSONArray(String url) throws IOException {
		return toJSONArray(
			url, true, _MAX_RETRIES_DEFAULT, null, _RETRY_PERIOD_DEFAULT,
			_TIMEOUT_DEFAULT);
	}

	public static JSONArray toJSONArray(String url, boolean checkCache)
		throws IOException {

		return toJSONArray(
			url, checkCache, _MAX_RETRIES_DEFAULT, null, _RETRY_PERIOD_DEFAULT,
			_TIMEOUT_DEFAULT);
	}

	public static JSONArray toJSONArray(
			String url, boolean checkCache, int maxRetries, String postContent,
			int retryPeriod, int timeout)
		throws IOException {

		String response = toString(
			url, checkCache, maxRetries, postContent, retryPeriod, timeout);

		if ((response == null) ||
			response.endsWith("was truncated due to its size.")) {

			return null;
		}

		return new JSONArray(response);
	}

	public static JSONArray toJSONArray(String url, String postContent)
		throws IOException {

		return toJSONArray(
			url, false, _MAX_RETRIES_DEFAULT, postContent,
			_RETRY_PERIOD_DEFAULT, _TIMEOUT_DEFAULT);
	}

	public static JSONObject toJSONObject(String url) throws IOException {
		return toJSONObject(
			url, true, _MAX_RETRIES_DEFAULT, _RETRY_PERIOD_DEFAULT,
			_TIMEOUT_DEFAULT);
	}

	public static JSONObject toJSONObject(String url, boolean checkCache)
		throws IOException {

		return toJSONObject(
			url, checkCache, _MAX_RETRIES_DEFAULT, _RETRY_PERIOD_DEFAULT,
			_TIMEOUT_DEFAULT);
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

		return toJSONObject(
			url, checkCache, maxRetries, null, retryPeriod, timeout);
	}

	public static JSONObject toJSONObject(
			String url, boolean checkCache, int maxRetries, String postContent,
			int retryPeriod, int timeout)
		throws IOException {

		String response = toString(
			url, checkCache, maxRetries, postContent, retryPeriod, timeout);

		if ((response == null) ||
			response.endsWith("was truncated due to its size.")) {

			return null;
		}

		return createJSONObject(response);
	}

	public static JSONObject toJSONObject(String url, String postContent)
		throws IOException {

		return toJSONObject(
			url, false, _MAX_RETRIES_DEFAULT, postContent,
			_RETRY_PERIOD_DEFAULT, _TIMEOUT_DEFAULT);
	}

	public static Properties toProperties(String url) throws IOException {
		Properties properties = new Properties();

		properties.load(new StringReader(toString(url)));

		return properties;
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

		return toString(
			url, checkCache, maxRetries, null, retryPeriod, timeout);
	}

	public static String toString(
			String url, boolean checkCache, int maxRetries, String postContent,
			int retryPeriod, int timeout)
		throws IOException {

		url = fixURL(url);

		String key = url.replace("//", "/");

		if (checkCache && _toStringCache.containsKey(key) &&
			!url.startsWith("file:")) {

			if (debug) {
				System.out.println("Loading " + url);
			}

			String response = _toStringCache.get(key);

			if (response != null) {
				return response;
			}

			_toStringCache.remove(key);
		}

		int retryCount = 0;

		while (true) {
			try {
				if (debug) {
					System.out.println("Downloading " + url);
				}

				StringBuilder sb = new StringBuilder();

				URL urlObject = new URL(url);

				URLConnection urlConnection = urlObject.openConnection();

				if (url.startsWith("https://api.github.com")) {
					HttpURLConnection httpURLConnection =
						(HttpURLConnection)urlConnection;

					httpURLConnection.setRequestMethod("GET");

					Properties buildProperties = getBuildProperties();

					httpURLConnection.setRequestProperty(
						"Authorization",
						"token " +
							buildProperties.getProperty("github.access.token"));

					httpURLConnection.setRequestProperty(
						"Content-Type", "application/json");

					if (postContent != null) {
						httpURLConnection.setRequestMethod("POST");

						httpURLConnection.setDoOutput(true);

						try (OutputStream outputStream =
								httpURLConnection.getOutputStream()) {

							outputStream.write(postContent.getBytes("UTF-8"));

							outputStream.flush();
						}
					}
				}

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

				System.out.println(
					"Retrying " + url + " in " + retryPeriod + " seconds");

				sleep(1000 * retryPeriod);
			}
		}
	}

	public static String toString(String url, String postContent)
		throws IOException {

		return toString(
			url, false, _MAX_RETRIES_DEFAULT, postContent,
			_RETRY_PERIOD_DEFAULT, _TIMEOUT_DEFAULT);
	}

	public static void turnSlavesOff(
		String jenkinsMasterName, String... slaveNames) {

		_setSlaveStatus(jenkinsMasterName, true, slaveNames);
	}

	public static void turnSlavesOn(
		String jenkinsMasterName, String... slaveNames) {

		_setSlaveStatus(jenkinsMasterName, false, slaveNames);
	}

	public static void write(File file, String content) throws IOException {
		if (debug) {
			System.out.println(
				"Write file " + file + " with length " + content.length());
		}

		File parentDir = file.getParentFile();

		if ((parentDir != null) && !parentDir.exists()) {
			if (debug) {
				System.out.println("Make parent directories for " + file);
			}

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

	private static long _appendDurationStringForUnit(
		long duration, long millisInUnit, StringBuilder sb,
		String unitDescriptionSingular, String unitDescriptionPlural) {

		if (duration >= millisInUnit) {
			long units = duration / millisInUnit;

			sb.append(units);

			sb.append(" ");

			sb.append(
				getNounForm(
					(int)units, unitDescriptionPlural,
					unitDescriptionSingular));

			sb.append(" ");

			return duration % millisInUnit;
		}

		return duration;
	}

	private static void _executeJenkinsScript(
		String jenkinsMasterName, String script) {

		try {
			URL urlObject = new URL(
				fixURL(getLocalURL("http://" + jenkinsMasterName + "/script")));

			HttpURLConnection httpURLConnection =
				(HttpURLConnection)urlObject.openConnection();

			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");

			Properties buildProperties = getBuildProperties();

			String authorizationString =
				buildProperties.getProperty("jenkins.admin.user.name") + ":" +
					buildProperties.getProperty("jenkins.admin.user.token");

			String encodedAuthorizationString = Base64.encodeBase64String(
				authorizationString.getBytes());

			httpURLConnection.setRequestProperty(
				"Authorization", "Basic " + encodedAuthorizationString);

			try (OutputStream outputStream =
					httpURLConnection.getOutputStream()) {

				outputStream.write(script.getBytes("UTF-8"));

				outputStream.flush();
			}

			httpURLConnection.connect();

			System.out.println(
				"Response from " + urlObject + ": " +
					httpURLConnection.getResponseCode() + " " +
						httpURLConnection.getResponseMessage());
		}
		catch (IOException ioe) {
			System.out.println("Unable to execute Jenkins script");
		}
	}

	private static String _getRedactTokenKey(int index) {
		return "github.message.redact.token[" + index + "]";
	}

	private static void _setSlaveStatus(
		String jenkinsMasterName, boolean offlineStatus, String... slaveNames) {

		try {
			String script = "script=";

			Class<?> clazz = JenkinsResultsParserUtil.class;

			script += readInputStream(
				clazz.getResourceAsStream(
					"dependencies/set-slave-status.groovy"));

			script = script.replace("${slaves}", merge(slaveNames));
			script = script.replace(
				"${offline.status}", String.valueOf(offlineStatus));

			_executeJenkinsScript(jenkinsMasterName, script);
		}
		catch (IOException ioe) {
			System.out.println(
				"Unable to set the status for slaves: " + slaveNames);

			ioe.printStackTrace();
		}
	}

	private static final long _BASH_COMMAND_TIMEOUT_DEFAULT = 1000 * 60 * 60;

	private static final String _LOAD_BALANCER_SERVICE_URL_TEMPLATE = combine(
		"http://cloud-10-0-0-31.lax.liferay.com/osb-jenkins-web/",
		"load_balancer?baseInvocationURL=${baseInvocationURL}",
		"&invokedJobBatchSize=${invokedBatchSize}");

	private static final int _MAX_RETRIES_DEFAULT = 3;

	private static final long _MILLIS_IN_DAY = 24L * 60L * 60L * 1000L;

	private static final long _MILLIS_IN_HOUR = 60L * 60L * 1000L;

	private static final long _MILLIS_IN_MINUTE = 60L * 1000L;

	private static final long _MILLIS_IN_SECOND = 1000L;

	private static final int _RETRY_PERIOD_DEFAULT = 5;

	private static final int _TIMEOUT_DEFAULT = 0;

	private static Hashtable<?, ?> _buildProperties;
	private static String[] _buildPropertiesURLs;
	private static Set<String> _redactTokens;
	private static final Pattern _remoteURLAuthorityPattern1 = Pattern.compile(
		"https://test.liferay.com/([0-9]+)/");
	private static final Pattern _remoteURLAuthorityPattern2 = Pattern.compile(
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

	static {
		System.out.println("Securing standard error and out");

		System.setErr(SecurePrintStream.getInstance());
		System.setOut(SecurePrintStream.getInstance());
	}

}