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

import com.google.common.collect.Lists;
import com.google.common.io.CountingInputStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.UnknownHostException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class JenkinsResultsParserUtil {

	public static final String[] URLS_BUILD_PROPERTIES_DEFAULT = {
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

	public static final String[] URLS_JENKINS_PROPERTIES_DEFAULT = {
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-jenkins-ee/jenkins.properties"
	};

	public static boolean debug;

	public static void append(File file, String content) throws IOException {
		if (debug) {
			System.out.println(
				combine(
					"Append to file ", file.getPath(), " with length ",
					String.valueOf(content.length())));
		}

		File parentDir = file.getParentFile();

		if ((parentDir != null) && !parentDir.exists()) {
			if (debug) {
				System.out.println("Make parent directories for " + file);
			}

			parentDir.mkdirs();
		}

		try (OutputStream outputStream = Files.newOutputStream(
				Paths.get(file.toURI()), StandardOpenOption.CREATE,
				StandardOpenOption.APPEND)) {

			outputStream.write(content.getBytes());
		}
	}

	public static void appendToCacheFile(String key, String content) {
		File cacheFile = _getCacheFile(key);

		boolean cacheFileCreated = false;

		if (!cacheFile.exists()) {
			cacheFileCreated = true;
		}

		try {
			append(cacheFile, content);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to append to cache file", ioException);
		}

		if (cacheFileCreated) {
			System.out.println("Created cache file in " + cacheFile.getPath());

			cacheFile.deleteOnExit();
		}
	}

	public static void clearCache() {
		File cacheDirectory = new File(
			System.getProperty("java.io.tmpdir"), "jenkins-cached-files");

		System.out.println(
			"Clearing cache " + getCanonicalPath(cacheDirectory));

		if (!cacheDirectory.exists()) {
			return;
		}

		delete(cacheDirectory);
	}

	public static String combine(String... strings) {
		if ((strings == null) || (strings.length == 0)) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		for (String string : strings) {
			sb.append(string);
		}

		return sb.toString();
	}

	public static <T> List<T> concatenate(
		List<List<T>> lists, boolean allowDuplicates) {

		Collection<T> concatenatedCollection = new ArrayList<>();

		if (!allowDuplicates) {
			concatenatedCollection = new HashSet<>();
		}

		for (List<T> list : lists) {
			concatenatedCollection.addAll(list);
		}

		return new ArrayList<>(concatenatedCollection);
	}

	public static void copy(File sourceFile, File targetFile)
		throws IOException {

		try {
			if (!sourceFile.exists()) {
				throw new FileNotFoundException(
					sourceFile.getPath() + " does not exist");
			}

			if (sourceFile.isDirectory()) {
				targetFile.mkdir();

				for (File file : sourceFile.listFiles()) {
					copy(file, new File(targetFile, file.getName()));
				}

				return;
			}

			File parentFile = targetFile.getParentFile();

			if ((parentFile != null) && !parentFile.exists()) {
				parentFile.mkdirs();
			}

			try (FileInputStream fileInputStream = new FileInputStream(
					sourceFile)) {

				try (FileOutputStream fileOutputStream = new FileOutputStream(
						targetFile)) {

					Files.copy(Paths.get(sourceFile.toURI()), fileOutputStream);

					fileOutputStream.flush();
				}
			}
		}
		catch (IOException ioException) {
			if (targetFile.exists()) {
				delete(targetFile);
			}

			throw ioException;
		}
	}

	public static JSONArray createJSONArray(String jsonString) {
		jsonString = jsonString.trim();

		while (jsonString.startsWith("\uFEFF")) {
			jsonString = jsonString.substring(1);
		}

		return new JSONArray(jsonString);
	}

	public static JSONObject createJSONObject(String jsonString)
		throws IOException {

		jsonString = jsonString.trim();

		while (jsonString.startsWith("\uFEFF")) {
			jsonString = jsonString.substring(1);
		}

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
			jsonObject.putOpt("result", getActualResult(url));
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

	public static boolean delete(File file) {
		if (!file.exists()) {
			System.out.println(
				"Unable to delete because file does not exist " +
					file.getPath());

			return false;
		}

		boolean successful = true;

		if (file.isDirectory()) {
			for (File subfile : file.listFiles()) {
				if (successful) {
					successful = delete(subfile);
				}
				else {
					delete(subfile);
				}
			}
		}

		if (successful) {
			return file.delete();
		}

		return successful;
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
			boolean exitOnFirstFail, File baseDir, long timeout,
			String... commands)
		throws IOException, TimeoutException {

		System.out.print("Executing commands: ");

		for (String command : commands) {
			System.out.println(command);
		}

		String[] bashCommands = new String[3];

		if (isWindows()) {
			bashCommands[0] = "C:\\Program Files\\Git\\bin\\sh.exe";
		}
		else {
			bashCommands[0] = "/bin/sh";
		}

		bashCommands[1] = "-c";

		String commandTerminator = ";";

		if (exitOnFirstFail) {
			commandTerminator = "&&";
		}

		StringBuffer sb = new StringBuffer();

		for (String command : commands) {
			if (isWindows()) {
				command = command.replaceAll("\\(", "\\\\\\\\(");
				command = command.replaceAll("\\)", "\\\\\\\\)");
			}

			sb.append(command);
			sb.append(commandTerminator);
			sb.append(" ");
		}

		sb.append("echo Finished executing Bash commands.\n");

		bashCommands[2] = sb.toString();

		ProcessBuilder processBuilder = new ProcessBuilder(bashCommands);

		processBuilder.directory(baseDir.getAbsoluteFile());

		Process process = new BufferedProcess(2000000, processBuilder.start());

		long duration = 0;
		long start = System.currentTimeMillis();
		int returnCode = -1;

		while (true) {
			try {
				returnCode = process.exitValue();

				if (returnCode == 0) {
					String standardOut = readInputStream(
						process.getInputStream(), true);

					duration = System.currentTimeMillis() - start;

					while (!standardOut.contains(
								"Finished executing Bash commands.") &&
						   (duration < timeout)) {

						sleep(10);

						standardOut = readInputStream(
							process.getInputStream(), true);

						duration = System.currentTimeMillis() - start;
					}
				}

				break;
			}
			catch (IllegalThreadStateException illegalThreadStateException) {
				duration = System.currentTimeMillis() - start;

				if (duration >= timeout) {
					throw new TimeoutException(
						"Timeout occurred while executing Bash commands: " +
							Arrays.toString(commands));
				}

				returnCode = -1;

				sleep(100);
			}
		}

		if (debug) {
			System.out.println(
				"Output stream: " +
					readInputStream(process.getInputStream(), true));
		}

		if (debug && (returnCode != 0)) {
			System.out.println(
				"Error stream: " +
					readInputStream(process.getErrorStream(), true));
		}

		return process;
	}

	public static Process executeBashCommands(
			boolean exitOnFirstFail, String... commands)
		throws IOException, TimeoutException {

		return executeBashCommands(
			exitOnFirstFail, new File("."),
			_MILLIS_BASH_COMMAND_TIMEOUT_DEFAULT, commands);
	}

	public static Process executeBashCommands(String... commands)
		throws IOException, TimeoutException {

		return executeBashCommands(
			true, new File("."), _MILLIS_BASH_COMMAND_TIMEOUT_DEFAULT,
			commands);
	}

	public static void executeBashService(
		final String command, final File baseDir,
		final Map<String, String> environments, final long maxLogSize) {

		Runnable runnable = new Runnable() {

			public void run() {
				StringBuilder sb = new StringBuilder();

				try {
					if (isWindows()) {
						sb.append("C:\\Program Files\\Git\\bin\\sh.exe ");
					}
					else {
						sb.append("/bin/sh ");
					}

					sb.append(command);

					Runtime runtime = Runtime.getRuntime();

					String[] envp = new String[environments.size()];

					int i = 0;

					for (Map.Entry<String, String> environment :
							environments.entrySet()) {

						envp[i] = combine(
							environment.getKey(), "=", environment.getValue());

						i++;
					}

					Process process = runtime.exec(
						sb.toString(), envp, baseDir);

					InputStream inputStream = process.getInputStream();

					CountingInputStream countingInputStream =
						new CountingInputStream(inputStream);

					InputStreamReader inputStreamReader = new InputStreamReader(
						countingInputStream, StandardCharsets.UTF_8);

					try {
						int logCharInt;

						while ((logCharInt = inputStreamReader.read()) != -1) {
							if (countingInputStream.getCount() > maxLogSize) {
								continue;
							}

							System.out.print((char)logCharInt);
						}
					}
					finally {
						try {
							inputStreamReader.close();
						}
						finally {
							try {
								countingInputStream.close();
							}
							finally {
								inputStream.close();
							}
						}
					}

					process.waitFor();
				}
				catch (InterruptedException | IOException exception) {
					throw new RuntimeException(exception);
				}
			}

		};

		Thread thread = new Thread(runnable);

		thread.start();
	}

	public static void executeJenkinsScript(
		String jenkinsMasterName, String script) {

		try {
			URL urlObject = new URL(
				fixURL(getLocalURL("http://" + jenkinsMasterName + "/script")));

			HttpURLConnection httpURLConnection =
				(HttpURLConnection)urlObject.openConnection();

			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");

			Properties buildProperties = getBuildProperties(false);

			HTTPAuthorization httpAuthorization = new BasicHTTPAuthorization(
				buildProperties.getProperty("jenkins.admin.user.token"),
				buildProperties.getProperty("jenkins.admin.user.name"));

			httpURLConnection.setRequestProperty(
				"Authorization", httpAuthorization.toString());

			try (OutputStream outputStream =
					httpURLConnection.getOutputStream()) {

				script = "script=" + script;

				outputStream.write(script.getBytes("UTF-8"));

				outputStream.flush();
			}

			httpURLConnection.connect();

			System.out.println(
				combine(
					"Response from ", urlObject.toString(), ": ",
					String.valueOf(httpURLConnection.getResponseCode()), " ",
					httpURLConnection.getResponseMessage()));
		}
		catch (IOException ioException) {
			System.out.println("Unable to execute Jenkins script");

			ioException.printStackTrace();
		}
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

	public static List<File> findFiles(File baseDir, String regex) {
		List<File> files = new ArrayList<>();

		for (File file : baseDir.listFiles()) {
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
		url = url.replace(" ", "%20");
		url = url.replace("#", "%23");
		url = url.replace("(", "%28");
		url = url.replace(")", "%29");
		url = url.replace("[", "%5B");
		url = url.replace("]", "%5D");

		return url;
	}

	public static List<Build> flatten(List<Build> builds) {
		List<Build> flattenedBuilds = new ArrayList<>();

		for (Build build : builds) {
			flattenedBuilds.add(build);

			List<Build> downstreamBuilds = build.getDownstreamBuilds(null);

			if (!downstreamBuilds.isEmpty()) {
				flattenedBuilds.addAll(flatten(downstreamBuilds));
			}
		}

		return flattenedBuilds;
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

	public static String getAxisVariable(JSONObject jsonObject) {
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

	public static String getAxisVariable(String axisBuildURL) {
		String url = null;

		try {
			url = decode(axisBuildURL);
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException("Unable to encode " + axisBuildURL);
		}

		String label = "AXIS_VARIABLE=";

		int x = url.indexOf(label);

		if (x != -1) {
			url = url.substring(x + label.length());

			int y = url.indexOf(",");

			return url.substring(0, y);
		}

		return "";
	}

	public static File getBaseGitRepositoryDir() {
		Properties buildProperties = null;

		try {
			buildProperties = getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return new File(buildProperties.getProperty("base.repository.dir"));
	}

	public static String getBuildParameter(String buildURL, String key) {
		Map<String, String> buildParameters = getBuildParameters(buildURL);

		if (buildParameters.containsKey(key)) {
			return buildParameters.get(key);
		}

		throw new RuntimeException("Unable to find build parameter " + key);
	}

	public static Map<String, String> getBuildParameters(String buildURL) {
		Map<String, String> buildParameters = new HashMap<>();

		if (!buildURL.endsWith("/")) {
			buildURL += "/";
		}

		String buildParametersURL = getLocalURL(
			combine(buildURL, "api/json?tree=actions[parameters[name,value]]"));

		try {
			JSONObject jsonObject = toJSONObject(buildParametersURL);

			JSONArray actionsJSONArray = jsonObject.getJSONArray("actions");

			for (int i = 0; i < actionsJSONArray.length(); i++) {
				Object actions = actionsJSONArray.get(i);

				if (actions == JSONObject.NULL) {
					continue;
				}

				JSONObject actionJSONObject = actionsJSONArray.getJSONObject(i);

				if (!actionJSONObject.has("parameters")) {
					continue;
				}

				JSONArray parametersJSONArray = actionJSONObject.getJSONArray(
					"parameters");

				for (int j = 0; j < parametersJSONArray.length(); j++) {
					JSONObject parameterJSONObject =
						parametersJSONArray.getJSONObject(j);

					buildParameters.put(
						parameterJSONObject.getString("name"),
						parameterJSONObject.getString("value"));
				}
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException();
		}

		return buildParameters;
	}

	public static Properties getBuildProperties() throws IOException {
		return getBuildProperties(true);
	}

	public static Properties getBuildProperties(boolean checkCache)
		throws IOException {

		Properties properties = new Properties();

		synchronized (_buildProperties) {
			if (checkCache && !_buildProperties.isEmpty()) {
				properties.putAll(_buildProperties);

				return properties;
			}

			if (_buildPropertiesURLs == null) {
				_buildPropertiesURLs = URLS_BUILD_PROPERTIES_DEFAULT;
			}

			for (String url : _buildPropertiesURLs) {
				properties.load(
					new StringReader(toString(getLocalURL(url), false)));
			}

			_buildProperties.clear();

			_buildProperties.putAll(properties);
		}

		return properties;
	}

	public static String getBuildProperty(
			boolean checkCache, String propertyName)
		throws IOException {

		Properties buildProperties = getBuildProperties(checkCache);

		return buildProperties.getProperty(propertyName);
	}

	public static String getBuildProperty(String propertyName)
		throws IOException {

		return getBuildProperty(true, propertyName);
	}

	public static List<String> getBuildPropertyAsList(
			boolean checkCache, String key)
		throws IOException {

		Properties buildProperties = getBuildProperties(checkCache);

		String propertyContent = buildProperties.getProperty(key);

		if (propertyContent == null) {
			return Collections.emptyList();
		}

		return Arrays.asList(propertyContent.split(","));
	}

	public static BufferedReader getCachedFileBufferedReader(String key) {
		File cachedTextFile = _getCacheFile(key);

		if (!cachedTextFile.exists()) {
			return null;
		}

		try {
			return Files.newBufferedReader(Paths.get(cachedTextFile.toURI()));
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get buffered reader for " + cachedTextFile.getPath(),
				ioException);
		}
	}

	public static String getCachedText(String key) {
		File cachedTextFile = _getCacheFile(key);

		if (!cachedTextFile.exists()) {
			return null;
		}

		try {
			return read(cachedTextFile);
		}
		catch (IOException ioException) {
			return null;
		}
	}

	public static long getCacheFileSize(String key) {
		File cacheFile = _getCacheFile(key);

		if ((cacheFile == null) || !cacheFile.exists()) {
			return 0;
		}

		return cacheFile.length();
	}

	public static String getCanonicalPath(File file) {
		File canonicalFile = null;

		try {
			canonicalFile = file.getCanonicalFile();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get canonical file", ioException);
		}

		return _getCanonicalPath(canonicalFile);
	}

	public static String getCohortName() {
		String jenkinsURL = System.getenv("JENKINS_URL");

		return getCohortName(jenkinsURL);
	}

	public static String getCohortName(String masterHostname) {
		if (masterHostname == null) {
			return null;
		}

		Matcher matcher = _jenkinsMasterPattern.matcher(masterHostname);

		if (!matcher.find()) {
			return null;
		}

		return matcher.group("cohortName");
	}

	public static List<File> getDirectoriesContainingFiles(
		List<File> directories, List<File> files) {

		List<File> directoriesContainingFiles = new ArrayList<>(
			directories.size());

		for (File directory : directories) {
			if (!directory.isDirectory()) {
				continue;
			}

			boolean containsFile = false;

			for (File file : files) {
				if (isFileInDirectory(directory, file)) {
					containsFile = true;

					break;
				}
			}

			if (containsFile) {
				directoriesContainingFiles.add(directory);
			}
		}

		return directoriesContainingFiles;
	}

	public static String getDistinctTimeStamp() {
		while (true) {
			String timeStamp = String.valueOf(System.currentTimeMillis());

			if (_timeStamps.contains(timeStamp)) {
				continue;
			}

			_timeStamps.add(timeStamp);

			return timeStamp;
		}
	}

	public static String getDistPortalBundlesBuildURL(String portalBranchName) {
		try {
			JSONObject jobJSONObject = toJSONObject(
				_getDistPortalJobURL(portalBranchName) +
					"/api/json?tree=builds[number]",
				false);

			JSONArray buildsJSONArray = jobJSONObject.getJSONArray("builds");

			Pattern distPortalBundleFileNamesPattern =
				_getDistPortalBundleFileNamesPattern(portalBranchName);

			for (int i = 0; i < buildsJSONArray.length(); i++) {
				JSONObject buildJSONObject = buildsJSONArray.optJSONObject(i);

				if (buildJSONObject == null) {
					continue;
				}

				String distPortalBundlesBuildURL = combine(
					_getDistPortalBundlesURL(portalBranchName), "/",
					String.valueOf(buildJSONObject.getInt("number")), "/");

				try {
					Matcher matcher = distPortalBundleFileNamesPattern.matcher(
						toString(distPortalBundlesBuildURL, false));

					if (matcher.find()) {
						return distPortalBundlesBuildURL;
					}
				}
				catch (IOException ioException) {
					System.out.println("WARNING: " + ioException.getMessage());
				}
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return null;
	}

	public static String getEnvironmentVariable(
		String environmentVariableName) {

		String environmentVariableValue = System.getenv(
			environmentVariableName);

		if ((environmentVariableValue == null) ||
			environmentVariableValue.isEmpty()) {

			throw new RuntimeException(
				combine(
					"Unable to find required environment variable \'",
					environmentVariableName, "\'"));
		}

		return environmentVariableValue;
	}

	public static List<File> getExcludedFiles(
		List<PathMatcher> excludesPathMatchers, List<File> files) {

		List<File> excludedFiles = new ArrayList<>(files.size());

		for (File file : files) {
			if (isFileExcluded(excludesPathMatchers, file)) {
				excludedFiles.add(file);
			}
		}

		return excludedFiles;
	}

	public static String getGitHubAPIRateLimitStatusMessage() {
		try {
			JSONObject jsonObject = toJSONObject(
				"https://api.github.com/rate_limit");

			jsonObject = jsonObject.getJSONObject("rate");

			return _getGitHubAPIRateLimitStatusMessage(
				jsonObject.getInt("limit"), jsonObject.getInt("remaining"),
				jsonObject.getLong("reset"));
		}
		catch (Exception exception) {
			System.out.println("Unable to get GitHub API rate limit");
		}

		return "";
	}

	public static String getGitHubApiSearchUrl(List<String> filters) {
		return combine(
			"https://api.github.com/search/issues?q=", join("+", filters));
	}

	public static String getGitHubApiUrl(
		String gitRepositoryName, String username, String path) {

		return combine(
			"https://api.github.com/repos/", username, "/", gitRepositoryName,
			"/", path.replaceFirst("^/*", ""));
	}

	public static List<String> getGitHubCacheHostnames() {
		try {
			Properties buildProperties = getBuildProperties();

			String gitHubCacheHostnames = buildProperties.getProperty(
				"github.cache.hostnames");

			String cohortName = getCohortName();

			if ((cohortName != null) &&
				buildProperties.containsKey(
					"github.cache.hostnames[" + cohortName + "]")) {

				gitHubCacheHostnames = buildProperties.getProperty(
					"github.cache.hostnames[" + cohortName + "]");
			}

			return Lists.newArrayList(gitHubCacheHostnames.split(","));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static String[] getGlobsFromProperty(String globProperty) {
		List<String> curlyBraceExpansionList = new ArrayList<>();

		Matcher curlyBraceMatcher = _curlyBraceExpansionPattern.matcher(
			globProperty);

		while (curlyBraceMatcher.find()) {
			int index = curlyBraceExpansionList.size();

			String value = curlyBraceMatcher.group();

			curlyBraceExpansionList.add(value);

			globProperty = globProperty.replace(
				value, combine("${", String.valueOf(index), "}"));
		}

		List<String> globs = new ArrayList<>();

		for (String tempGlob : globProperty.split(",")) {
			Matcher matcher = _nestedPropertyPattern.matcher(tempGlob);

			String glob = tempGlob;

			while (matcher.find()) {
				Integer index = Integer.parseInt(matcher.group(1));

				glob = glob.replace(
					matcher.group(), curlyBraceExpansionList.get(index));
			}

			globs.add(glob);
		}

		return globs.toArray(new String[0]);
	}

	public static String getHostIPAddress() {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();

			return inetAddress.getHostAddress();
		}
		catch (UnknownHostException unknownHostException) {
			return "127.0.0.1";
		}
	}

	public static String getHostName(String defaultHostName) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();

			return inetAddress.getHostName();
		}
		catch (UnknownHostException unknownHostException) {
			return defaultHostName;
		}
	}

	public static List<File> getIncludedFiles(
		File basedir, String[] excludes, String[] includes) {

		if (includes == null) {
			return new ArrayList<>();
		}

		final List<PathMatcher> excludesPathMatchers = new ArrayList<>();

		if ((excludes != null) && (excludes.length > 0)) {
			excludesPathMatchers.addAll(toPathMatchers(null, excludes));
		}

		final List<PathMatcher> includesPathMatchers = toPathMatchers(
			null, includes);

		final List<File> includedFiles = new ArrayList<>();

		try {
			Files.walkFileTree(
				basedir.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						for (PathMatcher pathMatcher : excludesPathMatchers) {
							if (pathMatcher.matches(filePath)) {
								return FileVisitResult.CONTINUE;
							}
						}

						for (PathMatcher pathMatcher : includesPathMatchers) {
							if (pathMatcher.matches(filePath)) {
								includedFiles.add(filePath.toFile());

								break;
							}
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return includedFiles;
	}

	public static List<File> getIncludedFiles(
		List<PathMatcher> excludesPathMatchers,
		List<PathMatcher> includesPathMatchers, List<File> files) {

		List<File> includedFiles = new ArrayList<>(files.size());

		for (File file : files) {
			if (isFileIncluded(
					excludesPathMatchers, includesPathMatchers, file)) {

				includedFiles.add(file);
			}
		}

		return includedFiles;
	}

	public static List<URL> getIncludedResourceURLs(
			String[] resourceIncludesRelativeGlobs, File rootDir)
		throws IOException {

		final List<PathMatcher> pathMatchers = toPathMatchers(
			getCanonicalPath(rootDir) + File.separator,
			resourceIncludesRelativeGlobs);

		final List<URL> includedResourceURLs = new ArrayList<>();

		Path rootDirPath = rootDir.toPath();

		if (!Files.exists(rootDirPath)) {
			System.out.println(
				combine(
					"Directory ", rootDirPath.toString(), " does not exist."));

			return includedResourceURLs;
		}

		Files.walkFileTree(
			rootDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					for (PathMatcher pathMatcher : pathMatchers) {
						if (pathMatcher.matches(filePath)) {
							URI uri = filePath.toUri();

							includedResourceURLs.add(uri.toURL());

							break;
						}
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return includedResourceURLs;
	}

	public static float getJavaVersionNumber() {
		Matcher matcher = _javaVersionPattern.matcher(
			System.getProperty("java.version"));

		if (!matcher.find()) {
			throw new RuntimeException(
				"Unable to determine Java version number");
		}

		return Float.parseFloat(matcher.group(1));
	}

	public static String getJenkinsMasterName(String jenkinSlaveName) {
		jenkinSlaveName = jenkinSlaveName.replaceAll("([^\\.]+).*", "$1");

		Properties buildProperties = null;

		try {
			buildProperties = getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build properties", ioException);
		}

		for (Object propertyName : buildProperties.keySet()) {
			Matcher jenkinsSlavesPropertyNameMatcher =
				_jenkinsSlavesPropertyNamePattern.matcher(
					propertyName.toString());

			if (jenkinsSlavesPropertyNameMatcher.matches()) {
				String jenkinsMasterName =
					jenkinsSlavesPropertyNameMatcher.group(1);

				List<String> jenkinsSlaveNames = getSlaves(
					buildProperties, jenkinsMasterName, null, false);

				if (jenkinsSlaveNames.contains(jenkinSlaveName)) {
					return jenkinsMasterName;
				}
			}
		}

		return null;
	}

	public static List<JenkinsMaster> getJenkinsMasters(
		Properties buildProperties, int minimumRAM, String prefix) {

		List<JenkinsMaster> jenkinsMasters = new ArrayList<>();

		for (int i = 1;
			 buildProperties.containsKey(
				 "master.slaves(" + prefix + "-" + i + ")");
			 i++) {

			JenkinsMaster jenkinsMaster = new JenkinsMaster(prefix + "-" + i);

			if (jenkinsMaster.getSlaveRAM() >= minimumRAM) {
				jenkinsMasters.add(jenkinsMaster);
			}
		}

		return jenkinsMasters;
	}

	public static Properties getJenkinsProperties() throws IOException {
		Properties properties = new Properties();

		if ((_jenkinsProperties != null) && !_jenkinsProperties.isEmpty()) {
			properties.putAll(_jenkinsProperties);

			return properties;
		}

		for (String url : URLS_JENKINS_PROPERTIES_DEFAULT) {
			properties.load(
				new StringReader(toString(getLocalURL(url), false)));
		}

		LocalGitRepository localGitRepository =
			GitRepositoryFactory.getLocalGitRepository(
				"liferay-jenkins-ee", "master");

		File jenkinsPropertiesFile = new File(
			localGitRepository.getDirectory(), "jenkins.properties");

		if (jenkinsPropertiesFile.exists()) {
			properties.putAll(getProperties(jenkinsPropertiesFile));
		}

		_jenkinsProperties = properties;

		return properties;
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

	public static String getJobVariant(String json) {
		return getJobVariant(new JSONObject(json));
	}

	public static Properties getLocalLiferayJenkinsEEBuildProperties() {
		Properties buildProperties = null;

		try {
			buildProperties = getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build properties", ioException);
		}

		File localLiferayJenkinsEEBuildPropertiesFile = new File(
			buildProperties.getProperty("base.repository.dir"),
			combine(
				"liferay-jenkins-ee", File.separator, "commands",
				File.separator, "build.properties"));

		return getProperties(localLiferayJenkinsEEBuildPropertiesFile);
	}

	public static String getLocalURL(String remoteURL) {
		if (remoteURL.contains("${dependencies.url}")) {
			remoteURL = fixFileName(remoteURL);

			String fileURL = remoteURL.replace(
				"${dependencies.url}", URL_DEPENDENCIES_FILE);

			File file = new File(fileURL.substring("file:".length()));

			if (file.exists()) {
				remoteURL = fileURL;
			}
			else {
				remoteURL = remoteURL.replace(
					"${dependencies.url}", URL_DEPENDENCIES_HTTP);
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
				"http://", remoteURLAuthorityMatcher1.group(1), "-",
				remoteURLAuthorityMatcher1.group(2), "/",
				remoteURLAuthorityMatcher1.group(2), "/");
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

		return getMostAvailableMasterURL(
			baseInvocationURL, null, invokedBatchSize,
			JenkinsMaster.SLAVE_RAM_DEFAULT);
	}

	public static String getMostAvailableMasterURL(
		String baseInvocationURL, String blacklist, int invokedBatchSize,
		int minimumRAM) {

		StringBuilder sb = new StringBuilder();

		sb.append(_URL_LOAD_BALANCER);
		sb.append("?baseInvocationURL=");
		sb.append(baseInvocationURL);

		if (blacklist != null) {
			sb.append("&blacklist=");
			sb.append(blacklist);
		}

		if (invokedBatchSize > 0) {
			sb.append("&invokedJobBatchSize=");
			sb.append(invokedBatchSize);
		}

		if (minimumRAM > 0) {
			sb.append("&minimumRAM=");
			sb.append(minimumRAM);
		}

		try {
			JSONObject jsonObject = toJSONObject(sb.toString());

			return jsonObject.getString("mostAvailableMasterURL");
		}
		catch (IOException ioException1) {
			Properties buildProperties = null;

			try {
				buildProperties = getBuildProperties(false);
			}
			catch (IOException ioException2) {
				throw new RuntimeException(
					"Unable to get build properties", ioException2);
			}

			List<JenkinsMaster> availableJenkinsMasters =
				LoadBalancerUtil.getAvailableJenkinsMasters(
					LoadBalancerUtil.getMasterPrefix(baseInvocationURL),
					blacklist, minimumRAM, buildProperties);

			Random random = new Random(System.currentTimeMillis());

			JenkinsMaster randomJenkinsMaster = availableJenkinsMasters.get(
				random.nextInt(availableJenkinsMasters.size()));

			return "http://" + randomJenkinsMaster.getName();
		}
	}

	public static ThreadPoolExecutor getNewThreadPoolExecutor(
		int maximumPoolSize, boolean autoShutDown) {

		ThreadPoolExecutor threadPoolExecutor =
			(ThreadPoolExecutor)Executors.newFixedThreadPool(maximumPoolSize);

		if (autoShutDown) {
			threadPoolExecutor.setKeepAliveTime(5, TimeUnit.SECONDS);

			threadPoolExecutor.allowCoreThreadTimeOut(true);
			threadPoolExecutor.setCorePoolSize(maximumPoolSize);
			threadPoolExecutor.setMaximumPoolSize(maximumPoolSize);
		}

		return threadPoolExecutor;
	}

	public static String getNounForm(
		int count, String plural, String singular) {

		if (count == 1) {
			return singular;
		}

		return plural;
	}

	public static String getPathRelativeTo(File file, File relativeToFile) {
		try {
			String filePath = getCanonicalPath(file);

			return filePath.replace(getCanonicalPath(relativeToFile) + "/", "");
		}
		catch (RuntimeException runtimeException) {
			throw new RuntimeException(
				"Unable to get relative path", runtimeException);
		}
	}

	public static Properties getProperties(File... propertiesFiles) {
		Properties properties = new Properties();

		for (File propertiesFile : propertiesFiles) {
			if ((propertiesFile != null) && propertiesFile.exists()) {
				properties.putAll(_getProperties(propertiesFile));
			}
		}

		return properties;
	}

	public static String getProperty(Properties properties, String name) {
		return _getProperty(properties, new ArrayList<String>(), name);
	}

	public static String getProperty(
		Properties properties, String basePropertyName,
		boolean useBasePropertyAsDefault, String... opts) {

		if ((opts == null) || (opts.length == 0)) {
			return _getProperty(
				properties, new ArrayList<String>(), basePropertyName);
		}

		Set<String> optSet = new LinkedHashSet<>(Arrays.asList(opts));

		optSet.remove(null);

		opts = optSet.toArray(new String[0]);

		Properties matchingProperties = new Properties();

		for (Object key : properties.keySet()) {
			String keyString = key.toString();

			if (keyString.matches(
					Pattern.quote(basePropertyName) + "(\\[.*|$)")) {

				matchingProperties.setProperty(
					keyString, properties.getProperty(keyString));
			}
		}

		Set<Set<String>> targetOptSets = _getOrderedOptSets(opts);

		String propertyName = null;

		Map<String, Set<String>> propertyOptRegexSets =
			_getPropertyOptRegexSets(matchingProperties.stringPropertyNames());

		for (Map.Entry<String, Set<String>> entry :
				propertyOptRegexSets.entrySet()) {

			Set<String> propertyOptRegexSet = entry.getValue();

			for (Set<String> targetOptSet : targetOptSets) {
				if (propertyOptRegexSet.size() > targetOptSet.size()) {
					continue;
				}

				boolean matchesAllPropertyOptRegexes = true;

				for (String targetOpt : targetOptSet) {
					boolean matchesPropertyOptRegex = false;

					for (String propertyOptRegex : propertyOptRegexSet) {
						if (!targetOpt.matches(propertyOptRegex)) {
							continue;
						}

						matchesPropertyOptRegex = true;

						break;
					}

					if (!matchesPropertyOptRegex) {
						matchesAllPropertyOptRegexes = false;
					}
				}

				if (matchesAllPropertyOptRegexes) {
					propertyName = entry.getKey();

					break;
				}
			}

			if (propertyName != null) {
				break;
			}
		}

		if (propertyName != null) {
			return _getProperty(
				properties, new ArrayList<String>(), propertyName);
		}

		if (useBasePropertyAsDefault) {
			return _getProperty(
				properties, new ArrayList<String>(), basePropertyName);
		}

		return null;
	}

	public static String getProperty(
		Properties properties, String basePropertyName, String... opts) {

		return getProperty(properties, basePropertyName, true, opts);
	}

	public static String getRandomGitHubDevNodeHostname() {
		return getRandomGitHubDevNodeHostname(null);
	}

	public static String getRandomGitHubDevNodeHostname(
		List<String> excludedHostnames) {

		List<String> gitHubDevNodeHostnames = getGitHubCacheHostnames();

		if (excludedHostnames != null) {
			for (String excludedHostname : excludedHostnames) {
				gitHubDevNodeHostnames.remove(excludedHostname);
			}
		}

		return getRandomString(gitHubDevNodeHostnames);
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
				item = getRandomString(list);

				if (randomList.contains(item)) {
					continue;
				}

				randomList.add(item);

				break;
			}
		}

		return randomList;
	}

	public static <T> T getRandomListItem(List<T> list) {
		return list.get(getRandomValue(0, list.size() - 1));
	}

	public static String getRandomString(Collection<String> collection) {
		if ((collection == null) || collection.isEmpty()) {
			throw new IllegalArgumentException("Collection is null or empty");
		}

		int randomIndex = getRandomValue(0, collection.size() - 1);

		Iterator<String> iterator = collection.iterator();

		for (int i = 0; i < (randomIndex - 1); i++) {
			iterator.next();
		}

		return iterator.next();
	}

	public static int getRandomValue(int start, int end) {
		int size = Math.abs(end - start) + 1;

		double randomDouble = Math.random();

		return Math.min(start, end) + (int)Math.floor(size * randomDouble);
	}

	public static List<JenkinsSlave> getReachableJenkinsSlaves(
		List<JenkinsMaster> jenkinsMasters, Integer targetSlaveCount) {

		List<Callable<List<JenkinsSlave>>> callables = new ArrayList<>(
			jenkinsMasters.size());

		for (final JenkinsMaster jenkinsMaster : jenkinsMasters) {
			Callable<List<JenkinsSlave>> callable =
				new Callable<List<JenkinsSlave>>() {

					@Override
					public List<JenkinsSlave> call() throws Exception {
						jenkinsMaster.update();

						return jenkinsMaster.getOnlineJenkinsSlaves();
					}

				};

			callables.add(callable);
		}

		ThreadPoolExecutor threadPoolExecutor = getNewThreadPoolExecutor(
			jenkinsMasters.size(), true);

		ParallelExecutor<List<JenkinsSlave>> parallelExecutor =
			new ParallelExecutor<>(callables, threadPoolExecutor);

		List<JenkinsSlave> onlineJenkinsSlaves = concatenate(
			parallelExecutor.execute(), false);

		Collections.sort(onlineJenkinsSlaves);

		if (targetSlaveCount == null) {
			targetSlaveCount = onlineJenkinsSlaves.size();
		}

		List<JenkinsSlave> reachableJenkinsSlaves = new ArrayList<>(
			targetSlaveCount);

		while (reachableJenkinsSlaves.size() < targetSlaveCount) {
			JenkinsSlave randomJenkinsSlave = getRandomListItem(
				onlineJenkinsSlaves);

			onlineJenkinsSlaves.remove(randomJenkinsSlave);

			if (randomJenkinsSlave.isReachable()) {
				reachableJenkinsSlaves.add(randomJenkinsSlave);
			}

			if (onlineJenkinsSlaves.isEmpty() &&
				(reachableJenkinsSlaves.size() < targetSlaveCount)) {

				throw new RuntimeException(
					"Unable to find enough reachable Jenkins slaves");
			}
		}

		return reachableJenkinsSlaves;
	}

	public static String getRegexLiteral(String string) {
		if (string == null) {
			throw new NullPointerException("String is null");
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

	public static String getRemoteURL(String localURL) {
		if (localURL.startsWith("file")) {
			localURL = fixFileName(localURL);
		}

		String remoteURL = localURL;
		String remoteURLQueryString = "";

		int x = localURL.indexOf("?");

		if (x != -1) {
			remoteURL = localURL.substring(0, x);
			remoteURLQueryString = localURL.substring(x);
		}

		Matcher localURLAuthorityMatcher1 = _localURLAuthorityPattern1.matcher(
			remoteURL);
		Matcher localURLAuthorityMatcher2 = _localURLAuthorityPattern2.matcher(
			remoteURL);

		if (localURLAuthorityMatcher1.find()) {
			String localURLAuthority = localURLAuthorityMatcher1.group(0);
			String remoteURLAuthority = combine(
				"https://", localURLAuthorityMatcher1.group(2), ".liferay.com/",
				localURLAuthorityMatcher1.group(3), "/");

			remoteURL = remoteURL.replaceAll(
				localURLAuthority, remoteURLAuthority);
		}
		else if (localURLAuthorityMatcher2.find()) {
			String localURLAuthority = localURLAuthorityMatcher2.group(0);
			String remoteURLAuthority = combine(
				"https://", localURLAuthorityMatcher2.group(1),
				".liferay.com/");

			remoteURL = remoteURL.replaceAll(
				localURLAuthority, remoteURLAuthority);
		}

		return remoteURL + remoteURLQueryString;
	}

	public static String getResourceFileContent(String resourceName)
		throws IOException {

		try (InputStream resourceStream =
				JenkinsResultsParserUtil.class.getResourceAsStream(
					resourceName)) {

			return readInputStream(resourceStream);
		}
	}

	public static List<String> getSlaves(
		Properties buildProperties, String jenkinsMasterPatternString) {

		return getSlaves(
			buildProperties, jenkinsMasterPatternString, null, false);
	}

	public static List<String> getSlaves(
		Properties buildProperties, String jenkinsMasterPatternString,
		Integer targetSlaveCount, boolean validate) {

		Set<String> slaves = new LinkedHashSet<>();

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

		if (targetSlaveCount == null) {
			if (!validate) {
				return new ArrayList<>(slaves);
			}

			targetSlaveCount = slaves.size();
		}

		if (slaves.size() < targetSlaveCount) {
			throw new IllegalStateException(
				"Target size exceeds the number of available slaves");
		}

		List<String> randomSlaves = new ArrayList<>(targetSlaveCount);

		while (randomSlaves.size() < targetSlaveCount) {
			String randomSlave = getRandomString(slaves);

			slaves.remove(randomSlave);

			if (!validate || isReachable(randomSlave)) {
				randomSlaves.add(randomSlave);
			}

			if (slaves.isEmpty() && (randomSlaves.size() < targetSlaveCount)) {
				throw new RuntimeException(
					"Unable to find enough reachable slaves");
			}
		}

		return randomSlaves;
	}

	public static List<String> getSlaves(String jenkinsMasterPatternString)
		throws Exception {

		return getSlaves(getBuildProperties(), jenkinsMasterPatternString);
	}

	public static File getSshDir() {
		return _sshDir;
	}

	public static List<File> getSubdirectories(int depth, File rootDirectory) {
		if (!rootDirectory.isDirectory()) {
			return Collections.emptyList();
		}

		List<File> subdirectories = new ArrayList<>();

		if (depth == 0) {
			subdirectories.add(rootDirectory);
		}
		else {
			for (File file : rootDirectory.listFiles()) {
				if (!file.isDirectory()) {
					continue;
				}

				subdirectories.addAll(getSubdirectories(depth - 1, file));
			}
		}

		return subdirectories;
	}

	public static File getUserHomeDir() {
		return _userHomeDir;
	}

	public static boolean isCINode() {
		String hostName = getHostName("");

		if (hostName.startsWith("cloud-10-0-") ||
			hostName.startsWith("test-")) {

			return true;
		}

		return false;
	}

	public static boolean isFileExcluded(
		List<PathMatcher> excludesPathMatchers, File file) {

		return isFileExcluded(excludesPathMatchers, file.toPath());
	}

	public static boolean isFileExcluded(
		List<PathMatcher> excludesPathMatchers, Path path) {

		if (excludesPathMatchers != null) {
			for (PathMatcher excludesPathMatcher : excludesPathMatchers) {
				if (excludesPathMatcher.matches(path)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isFileIncluded(
		List<PathMatcher> excludesPathMatchers,
		List<PathMatcher> includesPathMatchers, File file) {

		return isFileIncluded(
			excludesPathMatchers, includesPathMatchers, file.toPath());
	}

	public static boolean isFileIncluded(
		List<PathMatcher> excludesPathMatchers,
		List<PathMatcher> includesPathMatchers, Path path) {

		if (isFileExcluded(excludesPathMatchers, path)) {
			return false;
		}

		if ((includesPathMatchers != null) && !includesPathMatchers.isEmpty()) {
			for (PathMatcher includesPathMatcher : includesPathMatchers) {
				if (includesPathMatcher.matches(path)) {
					return true;
				}
			}

			return false;
		}

		return true;
	}

	public static boolean isFileInDirectory(File directory, File file) {
		if (directory == null) {
			throw new IllegalArgumentException("Directory is null");
		}

		if (file == null) {
			throw new IllegalArgumentException("File is null");
		}

		if (!directory.isDirectory()) {
			throw new IllegalArgumentException(
				directory.getName() + " is not a directory");
		}

		String directoryCanonicalPath = getCanonicalPath(directory) + "/";
		String fileCanonicalPath = getCanonicalPath(file);

		if (fileCanonicalPath.startsWith(directoryCanonicalPath)) {
			return true;
		}

		return false;
	}

	public static boolean isJSONArrayEqual(
		JSONArray expectedJSONArray, JSONArray actualJSONArray) {

		if (expectedJSONArray.length() != actualJSONArray.length()) {
			return false;
		}

		for (int i = 0; i < expectedJSONArray.length(); i++) {
			Object actual = actualJSONArray.get(i);
			Object expected = expectedJSONArray.get(i);

			if (!_isJSONExpectedAndActualEqual(expected, actual)) {
				return false;
			}
		}

		return true;
	}

	public static boolean isJSONObjectEqual(
		JSONObject expectedJSONObject, JSONObject actualJSONObject) {

		JSONArray namesJSONArray = expectedJSONObject.names();

		for (int i = 0; i < namesJSONArray.length(); i++) {
			String name = namesJSONArray.getString(i);

			if (!actualJSONObject.has(name)) {
				return false;
			}

			Object expected = expectedJSONObject.get(name);
			Object actual = actualJSONObject.get(name);

			if (!_isJSONExpectedAndActualEqual(expected, actual)) {
				return false;
			}
		}

		return true;
	}

	public static boolean isReachable(String hostname) {
		try {
			InetAddress inetAddress = InetAddress.getByName(hostname);

			return inetAddress.isReachable(5000);
		}
		catch (IOException ioException) {
			System.out.println("Unable to reach " + hostname);

			return false;
		}
	}

	public static boolean isServerPortReachable(String hostname, int port) {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(hostname, port), 5000);

			return true;
		}
		catch (IOException ioException) {
			System.out.println(
				combine(
					"Unable to reach ", hostname, ":", String.valueOf(port)));

			return false;
		}
	}

	public static boolean isWindows() {
		return SystemUtils.IS_OS_WINDOWS;
	}

	public static String join(String delimiter, List<String> list) {
		return join(delimiter, list.toArray(new String[0]));
	}

	public static String join(String delimiter, String... strings) {
		StringBuilder sb = new StringBuilder();

		for (String string : strings) {
			if (sb.length() > 0) {
				sb.append(delimiter);
			}

			sb.append(string);
		}

		return sb.toString();
	}

	public static void keepJenkinsBuild(
		boolean keepBuildLogs, int buildNumber, String jobName,
		String masterHostname) {

		StringBuilder sb = new StringBuilder();

		sb.append("def job = Jenkins.instance.getItemByFullName(\"");
		sb.append(jobName);
		sb.append("\"); ");

		sb.append("def build = job.getBuildByNumber(");
		sb.append(buildNumber);
		sb.append("); ");

		sb.append("build.keepLog(");
		sb.append(keepBuildLogs);
		sb.append(");");

		executeJenkinsScript(masterHostname, sb.toString());
	}

	public static int lastIndexOfRegex(String string, String regex) {
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(string);

		int lastIndex = -1;

		while (matcher.find()) {
			lastIndex = matcher.start();
		}

		return lastIndex;
	}

	public static void move(File sourceFile, File targetFile)
		throws IOException {

		copy(sourceFile, targetFile);

		if (!delete(sourceFile)) {
			throw new IOException("Unable to delete " + sourceFile);
		}
	}

	public static <T> List<List<T>> partitionByCount(List<T> list, int count) {
		int listSize = list.size();

		int partitionSize = 1;

		if (listSize > count) {
			partitionSize = listSize / count;

			if ((listSize % count) > 0) {
				partitionSize++;
			}
		}

		return Lists.partition(list, partitionSize);
	}

	public static void printTable(String[][] table) {
		if (table.length == 0) {
			return;
		}

		int[] maxColumnWidth = new int[table[0].length];

		for (String[] row : table) {
			for (int columnNumber = 0; columnNumber < row.length;
				 columnNumber++) {

				String item = row[columnNumber];

				if (maxColumnWidth[columnNumber] <= item.length()) {
					maxColumnWidth[columnNumber] = item.length();
				}
			}
		}

		StringBuilder rowsStringBuilder = new StringBuilder();

		for (String[] row : table) {
			for (int columnNumber = 0; columnNumber < row.length;
				 columnNumber++) {

				String cellText = row[columnNumber];

				rowsStringBuilder.append(
					String.format(
						combine(
							"| %-",
							String.valueOf(maxColumnWidth[columnNumber]), "s "),
						cellText));
			}

			rowsStringBuilder.append("|\n");
		}

		int rowTotalSize = rowsStringBuilder.indexOf("\n");

		StringBuilder tableStringBuilder = new StringBuilder();

		for (int columnNumber = 0; columnNumber < rowTotalSize;
			 columnNumber++) {

			tableStringBuilder.append("-");
		}

		tableStringBuilder.append("\n");
		tableStringBuilder.append(rowsStringBuilder);

		for (int columnNumber = 0; columnNumber < rowTotalSize;
			 columnNumber++) {

			tableStringBuilder.append("-");
		}

		System.out.println(tableStringBuilder.toString());
	}

	public static String read(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.toURI())));
	}

	public static String readInputStream(InputStream inputStream)
		throws IOException {

		return readInputStream(inputStream, false);
	}

	public static String readInputStream(
			InputStream inputStream, boolean resetAfterReading)
		throws IOException {

		if (resetAfterReading && !inputStream.markSupported()) {
			Class<?> inputStreamClass = inputStream.getClass();

			System.out.println(
				"Unable to reset after reading input stream " +
					inputStreamClass.getName());
		}

		if (resetAfterReading && inputStream.markSupported()) {
			inputStream.mark(Integer.MAX_VALUE);
		}

		StringBuffer sb = new StringBuffer();

		byte[] bytes = new byte[1024];

		int size = inputStream.read(bytes);

		while (size > 0) {
			sb.append(new String(Arrays.copyOf(bytes, size)));

			size = inputStream.read(bytes);
		}

		if (resetAfterReading && inputStream.markSupported()) {
			inputStream.reset();
		}

		return sb.toString();
	}

	public static String redact(String string) {
		if (_redactTokens.isEmpty()) {
			synchronized (_redactTokens) {
				_initializeRedactTokens();
			}
		}

		synchronized (_redactTokens) {
			for (String redactToken : _redactTokens) {
				string = string.replace(redactToken, "[REDACTED]");
			}
		}

		return string;
	}

	public static List<File> removeExcludedFiles(
		List<PathMatcher> excludesPathMatchers, List<File> files) {

		List<File> excludedFiles = getExcludedFiles(
			excludesPathMatchers, files);

		files.removeAll(excludedFiles);

		return files;
	}

	public static void saveToCacheFile(String key, String text) {
		File cacheFile = _getCacheFile(key);

		try {
			write(cacheFile, text);

			cacheFile.deleteOnExit();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to save to cache file", ioException);
		}
	}

	public static void setBuildProperties(
		Hashtable<Object, Object> buildProperties) {

		_buildPropertiesURLs = null;

		synchronized (_buildProperties) {
			_buildProperties.clear();

			if (buildProperties != null) {
				_buildProperties.putAll(buildProperties);
			}
		}
	}

	public static void setBuildProperties(String... urls) {
		synchronized (_buildProperties) {
			_buildProperties.clear();
		}

		_buildPropertiesURLs = urls;
	}

	public static void sleep(long duration) {
		try {
			Thread.sleep(duration);
		}
		catch (InterruptedException interruptedException) {
			throw new RuntimeException(interruptedException);
		}
	}

	public static BufferedReader toBufferedReader(
			String url, boolean checkCache)
		throws IOException {

		return toBufferedReader(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, null, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static BufferedReader toBufferedReader(
			String url, boolean checkCache, int maxRetries,
			HttpRequestMethod method, String postContent, int retryPeriod,
			int timeout, HTTPAuthorization httpAuthorizationHeader)
		throws IOException {

		if (url.contains("/userContent/") && (timeout == 0)) {
			timeout = 5000;
		}

		if (method == null) {
			if (postContent != null) {
				method = HttpRequestMethod.POST;
			}
			else {
				method = HttpRequestMethod.GET;
			}
		}

		url = fixURL(url);

		String key = url.replace("//", "/");

		if (checkCache && !url.startsWith("file:")) {
			if (debug) {
				System.out.println("Loading " + url);
			}

			File cachedFile = _getCacheFile(_PREFIX_TO_STRING_CACHE + key);

			if ((cachedFile != null) && cachedFile.exists()) {
				FileReader fileReader = new FileReader(cachedFile);

				return new BufferedReader(fileReader);
			}
		}

		int retryCount = 0;

		while (true) {
			try {
				if (debug) {
					System.out.println("Downloading " + url);
				}

				if ((httpAuthorizationHeader == null) &&
					url.startsWith("https://api.github.com")) {

					Properties buildProperties = getBuildProperties();

					httpAuthorizationHeader = new TokenHTTPAuthorization(
						buildProperties.getProperty("github.access.token"));
				}

				if ((httpAuthorizationHeader == null) &&
					url.matches("https://liferay.spiraservice.net.+")) {

					Properties buildProperties = getBuildProperties();

					httpAuthorizationHeader = new BasicHTTPAuthorization(
						buildProperties.getProperty("spira.admin.user.token"),
						buildProperties.getProperty("spira.admin.user.name"));
				}

				if ((httpAuthorizationHeader == null) &&
					url.matches("https://test-\\d+-\\d+.liferay.com/.+")) {

					Properties buildProperties = getBuildProperties();

					httpAuthorizationHeader = new BasicHTTPAuthorization(
						buildProperties.getProperty("jenkins.admin.user.token"),
						buildProperties.getProperty("jenkins.admin.user.name"));
				}

				URL urlObject = new URL(url);

				URLConnection urlConnection = urlObject.openConnection();

				if (urlConnection instanceof HttpURLConnection) {
					HttpURLConnection httpURLConnection =
						(HttpURLConnection)urlConnection;

					if (method == HttpRequestMethod.PATCH) {
						httpURLConnection.setRequestMethod("POST");

						httpURLConnection.setRequestProperty(
							"X-HTTP-Method-Override", "PATCH");
					}
					else {
						httpURLConnection.setRequestMethod(method.name());
					}

					if (url.startsWith("https://api.github.com") &&
						(httpURLConnection instanceof HttpsURLConnection)) {

						SSLContext sslContext = null;

						try {
							if (getJavaVersionNumber() < 1.8F) {
								sslContext = SSLContext.getInstance("TLSv1.2");

								sslContext.init(null, null, null);

								HttpsURLConnection httpsURLConnection =
									(HttpsURLConnection)httpURLConnection;

								httpsURLConnection.setSSLSocketFactory(
									sslContext.getSocketFactory());
							}
						}
						catch (KeyManagementException | NoSuchAlgorithmException
									exception) {

							throw new RuntimeException(
								"Unable to set SSL context to TLS v1.2",
								exception);
						}
					}

					if (httpAuthorizationHeader != null) {
						httpURLConnection.setRequestProperty(
							"accept", "application/json");
						httpURLConnection.setRequestProperty(
							"Authorization",
							httpAuthorizationHeader.toString());
						httpURLConnection.setRequestProperty(
							"Content-Type", "application/json");
					}

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

				urlConnection.connect();

				if (url.startsWith("https://api.github.com")) {
					try {
						int limit = Integer.parseInt(
							urlConnection.getHeaderField("X-RateLimit-Limit"));
						int remaining = Integer.parseInt(
							urlConnection.getHeaderField(
								"X-RateLimit-Remaining"));
						long reset = Long.parseLong(
							urlConnection.getHeaderField("X-RateLimit-Reset"));

						System.out.println(
							combine(
								_getGitHubAPIRateLimitStatusMessage(
									limit, remaining, reset),
								"\n    ", url));
					}
					catch (Exception exception) {
						System.out.println(
							"Unable to parse GitHub API rate limit headers");

						exception.printStackTrace();
					}
				}

				return new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()));
			}
			catch (IOException ioException) {
				if ((ioException instanceof UnknownHostException) &&
					url.matches("http://test-\\d+-\\d+/.*")) {

					return toBufferedReader(
						url.replaceAll(
							"http://(test-\\d+-\\d+)(/.*)",
							"https://$1.liferay.com$2"),
						checkCache, maxRetries, method, postContent,
						retryPeriod, timeout, httpAuthorizationHeader);
				}

				retryCount++;

				if ((maxRetries >= 0) && (retryCount >= maxRetries)) {
					throw ioException;
				}

				System.out.println(
					"Retrying " + url + " in " + retryPeriod + " seconds");

				sleep(1000 * retryPeriod);
			}
		}
	}

	public static String toDateString(Date date, String timeZoneName) {
		return toDateString(date, "MMM dd, yyyy h:mm:ss a z", timeZoneName);
	}

	public static String toDateString(
		Date date, String format, String timeZoneName) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		if (timeZoneName != null) {
			sdf.setTimeZone(TimeZone.getTimeZone(timeZoneName));
		}

		return sdf.format(date);
	}

	public static String toDurationString(long duration) {
		long remainingDuration = duration;

		StringBuilder sb = new StringBuilder();

		remainingDuration = _appendDurationStringForUnit(
			remainingDuration, _MILLIS_DAY, false, sb, "day", "days");

		remainingDuration = _appendDurationStringForUnit(
			remainingDuration, _MILLIS_HOUR, false, sb, "hour", "hours");

		remainingDuration = _appendDurationStringForUnit(
			remainingDuration, _MILLIS_MINUTE, true, sb, "minute", "minutes");

		if (duration < 60000) {
			remainingDuration = _appendDurationStringForUnit(
				remainingDuration, _MILLIS_SECOND, true, sb, "second",
				"seconds");
		}

		if (duration < 1000) {
			_appendDurationStringForUnit(
				remainingDuration, 1, true, sb, "ms", "ms");
		}

		String durationString = sb.toString();

		durationString = durationString.trim();

		if (durationString.equals("")) {
			durationString = "0 ms";
		}

		return durationString;
	}

	public static void toFile(URL url, File file) {
		try {
			System.out.println(
				combine(
					"Downloading ", url.toString(), " to ",
					getCanonicalPath(file)));

			FileUtils.copyURLToFile(url, file);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static JSONArray toJSONArray(String url) throws IOException {
		return toJSONArray(
			url, true, _RETRIES_SIZE_MAX_DEFAULT, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static JSONArray toJSONArray(String url, boolean checkCache)
		throws IOException {

		return toJSONArray(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static JSONArray toJSONArray(
			String url, boolean checkCache, int maxRetries, String postContent,
			int retryPeriod, int timeout)
		throws IOException {

		return toJSONArray(
			url, checkCache, maxRetries, postContent, retryPeriod, timeout,
			null);
	}

	public static JSONArray toJSONArray(
			String url, boolean checkCache, int maxRetries, String postContent,
			int retryPeriod, int timeout, HTTPAuthorization httpAuthorization)
		throws IOException {

		String response = toString(
			url, checkCache, maxRetries, null, postContent, retryPeriod,
			timeout, httpAuthorization);

		if ((response == null) ||
			response.endsWith("was truncated due to its size.")) {

			return null;
		}

		return new JSONArray(response);
	}

	public static JSONArray toJSONArray(String url, String postContent)
		throws IOException {

		return toJSONArray(
			url, false, _RETRIES_SIZE_MAX_DEFAULT, postContent,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static JSONArray toJSONArray(
			String url, String postContent, HTTPAuthorization httpAuthorization)
		throws IOException {

		return toJSONArray(
			url, false, _RETRIES_SIZE_MAX_DEFAULT, postContent,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT,
			httpAuthorization);
	}

	public static JSONObject toJSONObject(String url) throws IOException {
		return toJSONObject(
			url, true, _RETRIES_SIZE_MAX_DEFAULT, null, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static JSONObject toJSONObject(String url, boolean checkCache)
		throws IOException {

		return toJSONObject(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, null, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static JSONObject toJSONObject(
			String url, boolean checkCache, HttpRequestMethod method)
		throws IOException {

		return toJSONObject(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, method, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static JSONObject toJSONObject(
			String url, boolean checkCache, int timeout)
		throws IOException {

		return toJSONObject(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, null, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, timeout, null);
	}

	public static JSONObject toJSONObject(
			String url, boolean checkCache, int maxRetries,
			HttpRequestMethod method, String postContent, int retryPeriod,
			int timeout, HTTPAuthorization httpAuthorization)
		throws IOException {

		String response = toString(
			url, checkCache, maxRetries, null, postContent, retryPeriod,
			timeout, httpAuthorization);

		if ((response == null) ||
			response.endsWith("was truncated due to its size.")) {

			return null;
		}

		return createJSONObject(response);
	}

	public static JSONObject toJSONObject(
			String url, boolean checkCache, int maxRetries, int retryPeriod,
			int timeout)
		throws IOException {

		return toJSONObject(
			url, checkCache, maxRetries, null, null, retryPeriod, timeout,
			null);
	}

	public static JSONObject toJSONObject(
			String url, boolean checkCache, int maxRetries, String postContent,
			int retryPeriod, int timeout)
		throws IOException {

		return toJSONObject(
			url, checkCache, maxRetries, null, postContent, retryPeriod,
			timeout, null);
	}

	public static JSONObject toJSONObject(String url, String postContent)
		throws IOException {

		return toJSONObject(
			url, false, _RETRIES_SIZE_MAX_DEFAULT, null, postContent,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static JSONObject toJSONObject(
			String url, String postContent, HTTPAuthorization httpAuthorization)
		throws IOException {

		return toJSONObject(
			url, false, _RETRIES_SIZE_MAX_DEFAULT, null, postContent,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT,
			httpAuthorization);
	}

	public static List<PathMatcher> toPathMatchers(
		String prefix, String... globs) {

		if (prefix == null) {
			prefix = "";
		}

		FileSystem fileSystem = FileSystems.getDefault();

		List<PathMatcher> pathMatchers = new ArrayList<>(globs.length);

		for (String glob : globs) {
			pathMatchers.add(
				fileSystem.getPathMatcher(combine("glob:", prefix, glob)));
		}

		return pathMatchers;
	}

	public static Properties toProperties(String url) throws IOException {
		Properties properties = new Properties();

		properties.load(new StringReader(toString(url)));

		return properties;
	}

	public static String toString(String url) throws IOException {
		return toString(
			url, true, _RETRIES_SIZE_MAX_DEFAULT, null, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static String toString(String url, boolean checkCache)
		throws IOException {

		return toString(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, null, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static String toString(
			String url, boolean checkCache, HttpRequestMethod method)
		throws IOException {

		return toString(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, method, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static String toString(
			String url, boolean checkCache, HttpRequestMethod method,
			String postContent)
		throws IOException {

		return toString(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, method, postContent,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static String toString(String url, boolean checkCache, int timeout)
		throws IOException {

		return toString(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, null, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, timeout, null);
	}

	public static String toString(
			String url, boolean checkCache, int maxRetries,
			HttpRequestMethod method, String postContent, int retryPeriod,
			int timeout, HTTPAuthorization httpAuthorizationHeader)
		throws IOException {

		try (BufferedReader bufferedReader = toBufferedReader(
				url, checkCache, maxRetries, method, postContent, retryPeriod,
				timeout, httpAuthorizationHeader)) {

			StringBuilder sb = new StringBuilder();

			String line = bufferedReader.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");

				line = bufferedReader.readLine();
			}

			int bytes = sb.length();

			String content = sb.toString();

			if (checkCache && !url.startsWith("file:") &&
				(bytes < (3 * 1024 * 1024))) {

				url = fixURL(url);

				String key = url.replace("//", "/");

				saveToCacheFile(_PREFIX_TO_STRING_CACHE + key, content);
			}

			return content;
		}
	}

	public static String toString(
			String url, boolean checkCache, int maxRetries,
			int retryPeriodSeconds, int timeout)
		throws IOException {

		return toString(
			url, checkCache, maxRetries, null, null, retryPeriodSeconds,
			timeout, null);
	}

	public static String toString(
			String url, boolean checkCache, int maxRetries, String postContent,
			int retryPeriod, int timeout)
		throws IOException {

		return toString(
			url, checkCache, maxRetries, null, postContent, retryPeriod,
			timeout, null);
	}

	public static String toString(String url, HttpRequestMethod method)
		throws IOException {

		return toString(
			url, true, _RETRIES_SIZE_MAX_DEFAULT, method, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static String toString(
			String url, HttpRequestMethod method, String postContent)
		throws IOException {

		return toString(
			url, false, _RETRIES_SIZE_MAX_DEFAULT, method, postContent,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static String toString(String url, String postContent)
		throws IOException {

		return toString(
			url, false, _RETRIES_SIZE_MAX_DEFAULT, null, postContent,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static String toString(
			String url, String postContent, HTTPAuthorization httpAuthorization)
		throws IOException {

		return toString(
			url, false, _RETRIES_SIZE_MAX_DEFAULT, null, postContent,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT,
			httpAuthorization);
	}

	public static void updateBuildDescription(
		String buildDescription, int buildNumber, String jobName,
		String masterHostname) {

		buildDescription = buildDescription.replaceAll("\"", "\\\\\"");
		buildDescription = buildDescription.replaceAll("\'", "\\\\\'");

		String jenkinsScript = combine(
			"def job = Jenkins.instance.getItemByFullName(\"", jobName,
			"\"); def build = job.getBuildByNumber(",
			String.valueOf(buildNumber), "); build.description = \"",
			buildDescription, "\";");

		executeJenkinsScript(masterHostname, jenkinsScript);
	}

	public static void updateBuildResult(
		int buildNumber, String buildResult, String jobName,
		String masterHostname) {

		String jenkinsScript = combine(
			"def job = Jenkins.instance.getItemByFullName(\"", jobName, "\"); ",
			"def build = job.getBuildByNumber(", String.valueOf(buildNumber),
			"); build.@result = hudson.model.Result.", buildResult, ";");

		executeJenkinsScript(masterHostname, jenkinsScript);
	}

	public static void write(File file, String content) throws IOException {
		if (debug) {
			System.out.println(
				"Write file " + file + " with length " + content.length());
		}

		if (file.exists()) {
			file.delete();
		}

		append(file, content);
	}

	public static void write(String path, String content) throws IOException {
		if (path.startsWith("${dependencies.url}")) {
			path = path.replace(
				"${dependencies.url}",
				URL_DEPENDENCIES_FILE.replace("file:", ""));
		}

		write(new File(path), content);
	}

	public static void writePropertiesFile(
		File propertiesFile, Properties properties, boolean verbose) {

		if (propertiesFile.exists()) {
			propertiesFile.delete();
		}

		if (properties.isEmpty()) {
			return;
		}

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				propertiesFile)) {

			properties.store(
				fileOutputStream,
				"Generated by com.liferay.jenkins.results.parser");
		}
		catch (IOException ioException) {
			System.out.println(
				"Unable to write properties file " + propertiesFile);

			ioException.printStackTrace();
		}

		if (verbose) {
			System.out.println("#");
			System.out.println("# " + propertiesFile);
			System.out.println("#\n");

			try {
				System.out.println(read(propertiesFile));
			}
			catch (IOException ioException) {
				throw new RuntimeException(
					"Unable to read properties file " + propertiesFile,
					ioException);
			}
		}
	}

	public static class BasicHTTPAuthorization extends HTTPAuthorization {

		public BasicHTTPAuthorization(String password, String username) {
			super(Type.BASIC);

			this.password = password;
			this.username = username;
		}

		@Override
		public String toString() {
			String authorization = combine(username, ":", password);

			return combine(
				"Basic ", Base64.encodeBase64String(authorization.getBytes()));
		}

		protected String password;
		protected String username;

	}

	public abstract static class HTTPAuthorization {

		public Type getType() {
			return type;
		}

		public static enum Type {

			BASIC, TOKEN

		}

		protected HTTPAuthorization(Type type) {
			this.type = type;
		}

		protected Type type;

	}

	public static enum HttpRequestMethod {

		DELETE, GET, HEAD, OPTIONS, PATCH, POST, PUT, TRACE

	}

	public static class TokenHTTPAuthorization extends HTTPAuthorization {

		public TokenHTTPAuthorization(String token) {
			super(Type.TOKEN);

			this.token = token;
		}

		@Override
		public String toString() {
			return combine("token ", token);
		}

		protected String token;

	}

	protected static final String URL_DEPENDENCIES_FILE;

	protected static final String URL_DEPENDENCIES_HTTP =
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-jenkins-results-parser-samples-ee/1/";

	static {
		File dependenciesDir = new File("src/test/resources/dependencies/");

		try {
			URI uri = dependenciesDir.toURI();

			URL url = uri.toURL();

			URL_DEPENDENCIES_FILE = url.toString();
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private static long _appendDurationStringForUnit(
		long duration, long millisInUnit, boolean round, StringBuilder sb,
		String unitDescriptionSingular, String unitDescriptionPlural) {

		if (duration >= millisInUnit) {
			long units = duration / millisInUnit;

			long remainder = duration % millisInUnit;

			if (round && (remainder >= (millisInUnit / 2))) {
				units++;
			}

			sb.append(units);

			sb.append(" ");

			sb.append(
				getNounForm(
					(int)units, unitDescriptionPlural,
					unitDescriptionSingular));

			sb.append(" ");

			return remainder;
		}

		return duration;
	}

	private static File _getCacheFile(String key) {
		String fileName = combine(
			System.getProperty("java.io.tmpdir"), "/jenkins-cached-files/",
			String.valueOf(key.hashCode()), ".txt");

		return new File(fileName);
	}

	private static String _getCanonicalPath(File canonicalFile) {
		File parentCanonicalFile = canonicalFile.getParentFile();

		if (parentCanonicalFile == null) {
			String absolutePath = canonicalFile.getAbsolutePath();

			return absolutePath.substring(
				0, absolutePath.indexOf(File.separator));
		}

		String parentFileCanonicalPath = _getCanonicalPath(parentCanonicalFile);

		return combine(parentFileCanonicalPath, "/", canonicalFile.getName());
	}

	private static Pattern _getDistPortalBundleFileNamesPattern(
		String portalBranchName) {

		try {
			String distPortalBundleFileNames = getProperty(
				getBuildProperties(), "dist.portal.bundle.file.names",
				portalBranchName);

			if (distPortalBundleFileNames == null) {
				distPortalBundleFileNames =
					_DIST_PORTAL_BUNDLE_FILE_NAMES_DEFAULT;
			}

			StringBuilder sb = new StringBuilder();

			List<String> distPortalBundleFileNamesList = Lists.newArrayList(
				distPortalBundleFileNames.split("\\s*,\\s*"));

			Collections.sort(distPortalBundleFileNamesList);

			for (String distPortalBundleFileName :
					distPortalBundleFileNamesList) {

				String quotedDistPortalBundleFileName = Pattern.quote(
					distPortalBundleFileName);

				sb.append("\\<a href=\"");
				sb.append(quotedDistPortalBundleFileName);
				sb.append("\"\\>");
				sb.append(quotedDistPortalBundleFileName);
				sb.append("\\</a\\>.*");
			}

			sb.setLength(sb.length() - 2);

			return Pattern.compile(sb.toString(), Pattern.DOTALL);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to load build properties", ioException);
		}
	}

	private static String _getDistPortalBundlesURL(String portalBranchName) {
		try {
			String distPortalBundlesURL = getProperty(
				getBuildProperties(), "dist.portal.bundles.url",
				portalBranchName);

			if (distPortalBundlesURL != null) {
				return distPortalBundlesURL;
			}
		}
		catch (IOException ioException) {
			System.out.println("WARNING: " + ioException.getMessage());
		}

		return combine(
			_DIST_PORTAL_BUNDLES_URL_DEFAULT, "(", portalBranchName, ")/");
	}

	private static String _getDistPortalJobURL(String portalBranchName) {
		try {
			String distPortalJobURL = getProperty(
				getBuildProperties(), "dist.portal.job.url", portalBranchName);

			if (distPortalJobURL != null) {
				return distPortalJobURL;
			}
		}
		catch (IOException ioException) {
			System.out.println("WARNING: " + ioException.getMessage());
		}

		return combine(
			_DIST_PORTAL_JOB_URL_DEFAULT, "(", portalBranchName, ")");
	}

	private static String _getFilteredPropertyValue(String propertyValue) {
		if (propertyValue == null) {
			return null;
		}

		List<String> propertyValues = new ArrayList<>();

		for (String value : propertyValue.split("\\s*,\\s*")) {
			if (!value.startsWith("#")) {
				propertyValues.add(value);
			}
		}

		return join(",", propertyValues);
	}

	private static String _getGitHubAPIRateLimitStatusMessage(
		int limit, int remaining, long reset) {

		StringBuilder sb = new StringBuilder();

		sb.append(remaining);
		sb.append(" GitHub API calls out of ");
		sb.append(limit);
		sb.append(" remain. GitHub API call limit will reset in ");
		sb.append(
			toDurationString((1000 * reset) - System.currentTimeMillis()));
		sb.append(".");

		return sb.toString();
	}

	private static Set<Set<String>> _getOrderedOptSets(String... opts) {
		Set<Set<String>> optSets = new LinkedHashSet<>();

		optSets.add(new LinkedHashSet<>(Arrays.asList(opts)));

		int optCount = opts.length;

		for (int i = optCount - 1; i >= 0; i--) {
			String[] childOpts = new String[optCount - 1];

			if (childOpts.length == 0) {
				continue;
			}

			for (int j = 0; j < optCount; j++) {
				if (j < i) {
					childOpts[j] = opts[j];
				}
				else if (j > i) {
					childOpts[j - 1] = opts[j];
				}
			}

			optSets.addAll(_getOrderedOptSets(childOpts));
		}

		Set<Set<String>> orderedOptSet = new LinkedHashSet<>();

		for (int i = optCount; i > 0; i--) {
			for (Set<String> optSet : optSets) {
				if (optSet.size() == i) {
					orderedOptSet.add(optSet);
				}
			}
		}

		return orderedOptSet;
	}

	private static Properties _getProperties(File basePropertiesFile) {
		if (!basePropertiesFile.exists()) {
			throw new RuntimeException(
				"Unable to find properties file " +
					basePropertiesFile.getPath());
		}

		List<File> propertiesFiles = new ArrayList<>();

		propertiesFiles.add(basePropertiesFile);

		String propertiesFileName = basePropertiesFile.getName();

		String[] environments = {
			System.getenv("HOSTNAME"), System.getenv("HOST"),
			System.getenv("COMPUTERNAME"), System.getProperty("user.name")
		};

		for (String environment : environments) {
			if (environment == null) {
				continue;
			}

			File environmentPropertyFile = new File(
				basePropertiesFile.getParentFile(),
				propertiesFileName.replace(
					".properties", "." + environment + ".properties"));

			if (environmentPropertyFile.exists()) {
				propertiesFiles.add(environmentPropertyFile);
			}
		}

		Properties properties = new Properties();

		try {
			for (File propertiesFile : propertiesFiles) {
				properties.load(new FileInputStream(propertiesFile));
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to load properties file " +
					basePropertiesFile.getPath(),
				ioException);
		}

		for (String propertyName : properties.stringPropertyNames()) {
			properties.setProperty(
				propertyName, getProperty(properties, propertyName));
		}

		return properties;
	}

	private static String _getProperty(
		Properties properties, List<String> previousNames, String name) {

		if (previousNames.contains(name)) {
			if (previousNames.size() > 1) {
				StringBuilder sb = new StringBuilder();

				sb.append("Circular property reference chain found\n");

				for (String previousName : previousNames) {
					sb.append(previousName);
					sb.append(" -> ");
				}

				sb.append(name);

				throw new IllegalStateException(sb.toString());
			}

			return combine("${", name, "}");
		}

		previousNames.add(name);

		if (!properties.containsKey(name)) {
			return null;
		}

		String value = _getFilteredPropertyValue(properties.getProperty(name));

		Matcher matcher = _nestedPropertyPattern.matcher(value);

		String newValue = value;

		while (matcher.find()) {
			String propertyGroup = matcher.group(0);
			String propertyName = matcher.group(1);

			if (properties.containsKey(propertyName)) {
				newValue = newValue.replace(
					propertyGroup,
					_getProperty(
						properties, new ArrayList<>(previousNames),
						propertyName));
			}
		}

		return newValue;
	}

	private static Map<String, Set<String>> _getPropertyOptRegexSets(
		Set<String> propertyNames) {

		Map<String, Set<String>> propertyOptSets = new HashMap<>();
		Map<String, Integer> propertyOptSetSizes = new HashMap<>();
		Map<String, Integer> propertyRegexCounts = new HashMap<>();

		int maxOptCount = 0;

		for (String propertyName : propertyNames) {
			Matcher matcher = _propertyOptionPattern.matcher(propertyName);

			Set<String> optSet = new LinkedHashSet<>();

			while (matcher.find()) {
				String opt = matcher.group("opt");

				opt = Pattern.quote(opt);

				optSet.add(opt.replaceAll("\\*", "\\\\E.+\\\\Q"));
			}

			if (optSet.size() > maxOptCount) {
				maxOptCount = optSet.size();
			}

			propertyOptSetSizes.put(propertyName, optSet.size());
			propertyOptSets.put(propertyName, optSet);

			int regexCount = 0;

			for (String opt : optSet) {
				if (opt.contains(".+")) {
					regexCount++;
				}
			}

			propertyRegexCounts.put(propertyName, regexCount);
		}

		Map<String, Set<String>> propertyOptRegexSets = new LinkedHashMap<>();

		for (int i = maxOptCount; i >= 0; i--) {
			for (int j = 0; j <= i; j++) {
				for (Map.Entry<String, Set<String>> entry :
						propertyOptSets.entrySet()) {

					String propertyName = entry.getKey();

					int optSetSize = propertyOptSetSizes.get(propertyName);
					int regexCount = propertyRegexCounts.get(propertyName);

					if ((optSetSize == i) && (regexCount == j)) {
						propertyOptRegexSets.put(
							propertyName, entry.getValue());
					}
				}
			}
		}

		return propertyOptRegexSets;
	}

	private static String _getRedactTokenKey(int index) {
		return "github.message.redact.token[" + index + "]";
	}

	private static void _initializeRedactTokens() {
		Properties properties = null;

		try {
			properties = getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build properties", ioException);
		}

		_redactTokens.clear();

		for (int i = 1; properties.containsKey(_getRedactTokenKey(i)); i++) {
			String key = _getRedactTokenKey(i);

			String redactToken = getProperty(properties, key);

			if (redactToken != null) {
				if ((redactToken.length() < 5) && redactToken.matches("\\d+")) {
					System.out.println(
						combine(
							"Ignoring ", key,
							" because the value is numeric and ",
							"less than 5 characters long."));
				}
				else {
					if (!redactToken.isEmpty()) {
						_redactTokens.add(redactToken);
					}
				}
			}
		}

		_redactTokens.remove("test");
	}

	private static boolean _isJSONExpectedAndActualEqual(
		Object expected, Object actual) {

		if (actual instanceof JSONObject) {
			if (!(expected instanceof JSONObject) ||
				!isJSONObjectEqual((JSONObject)expected, (JSONObject)actual)) {

				return false;
			}
		}
		else if (actual instanceof JSONArray) {
			if (!(expected instanceof JSONArray) ||
				!isJSONArrayEqual((JSONArray)expected, (JSONArray)actual)) {

				return false;
			}
		}
		else {
			if (!actual.equals(expected)) {
				return false;
			}
		}

		return true;
	}

	private static final String _DIST_PORTAL_BUNDLE_FILE_NAMES_DEFAULT =
		"git-hash,liferay-portal-bundle-tomcat.tar.gz," +
			"liferay-portal-source.tar.gz";

	private static final String _DIST_PORTAL_BUNDLES_URL_DEFAULT =
		"http://test-1-0/userContent/bundles/test-portal-acceptance-upstream";

	private static final String _DIST_PORTAL_JOB_URL_DEFAULT =
		"http://test-1-1/job/test-portal-acceptance-upstream";

	private static final long _MILLIS_BASH_COMMAND_TIMEOUT_DEFAULT =
		1000 * 60 * 60;

	private static final long _MILLIS_DAY = 24L * 60L * 60L * 1000L;

	private static final long _MILLIS_HOUR = 60L * 60L * 1000L;

	private static final long _MILLIS_MINUTE = 60L * 1000L;

	private static final long _MILLIS_SECOND = 1000L;

	private static final int _MILLIS_TIMEOUT_DEFAULT = 0;

	private static final String _PREFIX_TO_STRING_CACHE = "toStringCache-";

	private static final int _RETRIES_SIZE_MAX_DEFAULT = 3;

	private static final int _SECONDS_RETRY_PERIOD_DEFAULT = 5;

	private static final String _URL_LOAD_BALANCER =
		"http://cloud-10-0-0-31.lax.liferay.com/osb-jenkins-web/load_balancer";

	private static final Hashtable<Object, Object> _buildProperties =
		new Hashtable<>();
	private static String[] _buildPropertiesURLs;
	private static final Pattern _curlyBraceExpansionPattern = Pattern.compile(
		"\\{.*?\\}");
	private static final Pattern _javaVersionPattern = Pattern.compile(
		"(\\d+\\.\\d+)");
	private static final Pattern _jenkinsMasterPattern = Pattern.compile(
		"(?<cohortName>test-\\d+)-\\d+");
	private static Hashtable<?, ?> _jenkinsProperties;
	private static final Pattern _jenkinsSlavesPropertyNamePattern =
		Pattern.compile("master.slaves\\((.+)\\)");
	private static final Pattern _localURLAuthorityPattern1 = Pattern.compile(
		"http://((release|test)-[0-9]+)/([0-9]+)/");
	private static final Pattern _localURLAuthorityPattern2 = Pattern.compile(
		"http://(test-[0-9]+-[0-9]+)/");
	private static final Pattern _nestedPropertyPattern = Pattern.compile(
		"\\$\\{([^\\}]+)\\}");
	private static final Pattern _propertyOptionPattern = Pattern.compile(
		"\\[(?<opt>[^\\]]+)\\]");
	private static final Set<String> _redactTokens = new HashSet<>();
	private static final Pattern _remoteURLAuthorityPattern1 = Pattern.compile(
		"https://(release|test).liferay.com/([0-9]+)/");
	private static final Pattern _remoteURLAuthorityPattern2 = Pattern.compile(
		"https://(test-[0-9]+-[0-9]+).liferay.com/");
	private static final File _sshDir = new File(getUserHomeDir(), ".ssh") {
		{
			if (!exists()) {
				mkdirs();
			}
		}
	};
	private static final Set<String> _timeStamps = new HashSet<>();
	private static final File _userHomeDir = new File(
		System.getProperty("user.home"));

	static {
		_initializeRedactTokens();

		System.out.println("Securing standard error and out");

		System.setErr(new SecurePrintStream(System.err));
		System.setOut(new SecurePrintStream(System.out));
	}

}