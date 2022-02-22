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

import java.io.BufferedOutputStream;
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
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class JenkinsResultsParserUtil {

	public static final String[] CACHED_REPOSITORIES = {
		"liferay-jenkins-ee", "liferay-jenkins-results-parser-samples-ee",
		"liferay-portal"
	};

	public static final int PAGES_GITHUB_API_PAGES_SIZE_MAX = 10;

	public static final int PER_PAGE_GITHUB_API_PAGES_SIZE_MAX = 100;

	public static final String URL_CACHE = initCacheURL();

	public static final String[] URLS_BUILD_PROPERTIES_DEFAULT = {
		URL_CACHE + "/liferay-jenkins-ee/build.properties",
		URL_CACHE + "/liferay-jenkins-ee/commands/build.properties",
		URL_CACHE + "/liferay-portal/build.properties",
		URL_CACHE + "/liferay-portal/ci.properties",
		URL_CACHE + "/liferay-portal/test.properties"
	};

	public static final String[] URLS_GIT_DIRECTORIES_JSON_DEFAULT = {
		URL_CACHE + "/liferay-jenkins-ee/git-directories.json"
	};

	public static final String[] URLS_GIT_WORKING_DIRECTORIES_JSON_DEFAULT = {
		URL_CACHE + "/liferay-jenkins-ee/git-working-directories.json"
	};

	public static final String[] URLS_JENKINS_BUILD_PROPERTIES_DEFAULT = {
		URL_CACHE + "/liferay-jenkins-ee/build.properties",
		URL_CACHE + "/liferay-jenkins-ee/commands/build.properties"
	};

	public static final String[] URLS_JENKINS_PROPERTIES_DEFAULT = {
		URL_CACHE + "/liferay-jenkins-ee/jenkins.properties"
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

	public static void copy(
			final File sourceBaseDir, final File targetBaseDir,
			final List<PathMatcher> includePathMatchers,
			final List<PathMatcher> excludePathMatchers)
		throws IOException {

		if ((sourceBaseDir == null) || !sourceBaseDir.exists() ||
			(targetBaseDir == null)) {

			return;
		}

		if (!targetBaseDir.exists()) {
			targetBaseDir.mkdirs();
		}

		Files.walkFileTree(
			sourceBaseDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (isFileExcluded(
							excludePathMatchers, filePath.toFile())) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (!isFileIncluded(
							excludePathMatchers, includePathMatchers,
							filePath.toFile())) {

						return FileVisitResult.CONTINUE;
					}

					File file = filePath.toFile();

					String relativePath = getPathRelativeTo(
						file, sourceBaseDir);

					copy(file, new File(targetBaseDir, relativePath));

					return FileVisitResult.CONTINUE;
				}

			});
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

	public static URL createURL(String url) throws Exception {
		return encode(new URL(url));
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

	public static void delete(
			File baseDir, final List<PathMatcher> includePathMatchers,
			final List<PathMatcher> excludePathMatchers)
		throws IOException {

		if ((baseDir == null) || !baseDir.exists()) {
			return;
		}

		Files.walkFileTree(
			baseDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path dirPath, IOException ioException)
					throws IOException {

					if (Files.isDirectory(dirPath)) {
						File dir = dirPath.toFile();

						File[] files = dir.listFiles();

						if ((files == null) || (files.length == 0)) {
							delete(dir);
						}
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (isFileExcluded(
							excludePathMatchers, filePath.toFile())) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (!isFileIncluded(
							excludePathMatchers, includePathMatchers,
							filePath.toFile())) {

						return FileVisitResult.CONTINUE;
					}

					delete(filePath.toFile());

					return FileVisitResult.CONTINUE;
				}

			});
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

	public static String escapeForBash(String string) {
		string = string.replaceAll(" ", "\\\\ ");
		string = string.replaceAll("'", "\\\\\\\'");
		string = string.replaceAll("<", "\\\\\\<");
		string = string.replaceAll(">", "\\\\\\>");
		string = string.replaceAll("\"", "\\\\\\\"");
		string = string.replaceAll("\\$", "\\\\\\$");
		string = string.replaceAll("\\(", "\\\\\\(");
		string = string.replaceAll("\\)", "\\\\\\)");
		string = string.replaceAll("\\n", "\\\\n");

		return string;
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

		if (isWindows()) {
			sb.append("export GIT_ASK_YESNO=false");
			sb.append(commandTerminator);
			sb.append(" ");
		}

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

	public static void executeBashCommandService(
		String command, File baseDir, Map<String, String> environments,
		long maxLogSize) {

		_executeCommandService(
			command, baseDir, environments, maxLogSize, false);
	}

	public static void executeBatchCommandService(
		String command, File baseDir, Map<String, String> environments,
		long maxLogSize) {

		if (!isWindows()) {
			throw new RuntimeException("Invalid OS: " + SystemUtils.OS_NAME);
		}

		_executeCommandService(
			command, baseDir, environments, maxLogSize, true);
	}

	public static void executeJenkinsScript(
		String jenkinsMasterName, String script) {

		try {
			String url = fixURL(
				getLocalURL("http://" + jenkinsMasterName + "/script"));

			URL urlObject = new URL(url);

			HttpURLConnection httpURLConnection =
				(HttpURLConnection)urlObject.openConnection();

			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");

			HTTPAuthorization httpAuthorization = null;

			String jenkinsAdminUserName = getBuildProperty(
				"jenkins.admin.user.name");

			if (jenkinsMasterName.contains("test-1-0")) {
				String jenkinsAdminUserPassword = getBuildProperty(
					"jenkins.admin.user.password");

				httpAuthorization = new BasicHTTPAuthorization(
					jenkinsAdminUserPassword, jenkinsAdminUserName);
			}
			else {
				String jenkinsAdminUserToken = getBuildProperty(
					"jenkins.admin.user.token");

				httpAuthorization = new BasicHTTPAuthorization(
					jenkinsAdminUserToken, jenkinsAdminUserName);
			}

			httpURLConnection.setRequestProperty(
				"Authorization", httpAuthorization.toString());

			try (OutputStream outputStream =
					httpURLConnection.getOutputStream()) {

				String post = "script=" + URLEncoder.encode(script, "UTF-8");

				outputStream.write(post.getBytes("UTF-8"));

				outputStream.flush();
			}

			httpURLConnection.connect();

			int responseCode = httpURLConnection.getResponseCode();

			System.out.println(
				combine(
					"Response from ", urlObject.toString(), ": ",
					String.valueOf(responseCode), " ",
					httpURLConnection.getResponseMessage()));

			if (responseCode >= 400) {
				System.out.println(script);
			}
		}
		catch (IOException ioException) {
			System.out.println("Unable to execute Jenkins script");

			ioException.printStackTrace();
		}
	}

	public static boolean exists(URL url) {
		HttpURLConnection httpURLConnection = null;

		try {
			httpURLConnection = (HttpURLConnection)url.openConnection();

			httpURLConnection.setRequestMethod("HEAD");

			httpURLConnection.connect();

			int responseCode = httpURLConnection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				return true;
			}
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException.getMessage(), ioException);
			}
		}
		finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}

		return false;
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

	public static List<File> findDirs(File baseDir, String regex) {
		List<File> dirs = new ArrayList<>();

		for (File dir : baseDir.listFiles()) {
			if (!dir.isDirectory()) {
				continue;
			}

			String dirName = dir.getName();

			if (dirName.matches(regex)) {
				dirs.add(dir);
			}

			dirs.addAll(findDirs(dir, regex));
		}

		return dirs;
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

	public static List<File> findSiblingFiles(File file) {
		return findSiblingFiles(file, false);
	}

	public static List<File> findSiblingFiles(File file, boolean includeFile) {
		if ((file == null) || !file.exists()) {
			if (includeFile) {
				return Arrays.asList(file);
			}

			return Collections.emptyList();
		}

		File parentFile = file.getParentFile();

		if ((parentFile == null) || !parentFile.exists()) {
			if (includeFile) {
				return Arrays.asList(file);
			}

			return Collections.emptyList();
		}

		List<File> siblingFiles = new ArrayList<>(
			Arrays.asList(parentFile.listFiles()));

		if (!includeFile) {
			siblingFiles.remove(file);
		}

		return Collections.unmodifiableList(siblingFiles);
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

	public static String fixFileURL(String fileURL) {
		try {
			return URLDecoder.decode(fileURL, "UTF-8");
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(unsupportedEncodingException);
		}
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

	public static String fixURL(String urlString) {
		URL url = null;

		try {
			url = new URL(urlString);
		}
		catch (MalformedURLException malformedURLException) {
			try {
				urlString = URLEncoder.encode(
					urlString, StandardCharsets.UTF_8.name());

				urlString = urlString.replaceAll("\\+", "%20");

				urlString = urlString.replaceAll("%21", "!");
				urlString = urlString.replaceAll("%25", "%");
				urlString = urlString.replaceAll("%2B", "+");

				return urlString;
			}
			catch (UnsupportedEncodingException unsupportedEncodingException) {
				throw new RuntimeException(unsupportedEncodingException);
			}
		}

		if (!urlString.contains("?")) {
			return urlString;
		}

		StringBuilder sb = new StringBuilder(
			urlString.replaceAll("(.*\\?).*", "$1"));

		String queryString = url.getQuery();

		if ((queryString == null) || queryString.isEmpty()) {
			return sb.toString();
		}

		Matcher matcher = _urlQueryStringPattern.matcher(url.getQuery());

		while (matcher.find()) {
			sb.append(matcher.group(1));
			sb.append("=");

			try {
				String queryParameterValue = matcher.group(2);

				queryParameterValue = URLEncoder.encode(
					queryParameterValue, StandardCharsets.UTF_8.name());

				queryParameterValue = queryParameterValue.replaceAll(
					"\\+", "%20");

				queryParameterValue = queryParameterValue.replaceAll(
					"%21", "!");
				queryParameterValue = queryParameterValue.replaceAll(
					"%25", "%");
				queryParameterValue = queryParameterValue.replaceAll(
					"%2B", "+");

				sb.append(queryParameterValue);
			}
			catch (UnsupportedEncodingException unsupportedEncodingException) {
				throw new RuntimeException(unsupportedEncodingException);
			}

			if (!matcher.hitEnd()) {
				sb.append("&");
			}
		}

		return sb.toString();
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

			if (progressiveText.contains("Finished: UNSTABLE") ||
				progressiveText.contains("Finished: FAILURE")) {

				return "FAILURE";
			}
		}

		return null;
	}

	public static Long getAverage(List<Long> numbers) {
		if ((numbers == null) || numbers.isEmpty()) {
			return 0L;
		}

		List<Long> numbersToAverage = new ArrayList<>(numbers);

		numbersToAverage.removeAll(Collections.singleton(null));

		if (numbersToAverage.isEmpty()) {
			return 0L;
		}

		long total = 0L;

		for (Long number : numbersToAverage) {
			total += number;
		}

		return total / numbersToAverage.size();
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

		Matcher matcher = _axisVariablePattern.matcher(url);

		if (matcher.find()) {
			return matcher.group("axisVariable");
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

	public static String getBuildArtifactURL(
		String buildURL, String artifactName) {

		Matcher matcher = _topLevelBuildURLPattern.matcher(buildURL);

		matcher.find();

		StringBuilder sb = new StringBuilder();

		sb.append("http://test-");
		sb.append(matcher.group("cohortNumber"));
		sb.append("-");
		sb.append(matcher.group("masterNumber"));
		sb.append("/userContent/jobs/");
		sb.append(matcher.group("jobName"));
		sb.append("/builds/");
		sb.append(matcher.group("buildNumber"));
		sb.append("/");
		sb.append(artifactName);

		return sb.toString();
	}

	public static String getBuildDirPath() {
		StringBuilder sb = new StringBuilder();

		sb.append("/tmp/jenkins/");

		String buildNumber = System.getenv("BUILD_NUMBER");
		String jobName = System.getenv("JOB_NAME");
		String masterHostname = System.getenv("MASTER_HOSTNAME");

		if (!isCINode() || isNullOrEmpty(buildNumber) ||
			isNullOrEmpty(jobName) || isNullOrEmpty(masterHostname)) {

			LocalDate localDate = LocalDate.now();

			sb.append(
				localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

			return sb.toString();
		}

		sb.append(masterHostname);
		sb.append("/");
		sb.append(
			jobName.replaceAll("([^/]+)/AXIS_VARIABLE=([^,]+)(,.*)?", "$1/$2"));
		sb.append("/");
		sb.append(buildNumber);

		return sb.toString();
	}

	public static String getBuildID(String topLevelBuildURL) {
		Matcher matcher = _topLevelBuildURLPattern.matcher(topLevelBuildURL);

		matcher.find();

		StringBuilder sb = new StringBuilder();

		sb.append(matcher.group("cohortNumber"));

		String masterNumber = matcher.group("masterNumber");

		sb.append(String.format("%02d", Integer.parseInt(masterNumber)));

		Properties buildProperties = null;

		try {
			buildProperties = getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build.properties", ioException);
		}

		sb.append(
			buildProperties.getProperty(
				"job.id[" + matcher.group("jobName") + "]"));

		sb.append("_");
		sb.append(matcher.group("buildNumber"));

		return sb.toString();
	}

	public static String getBuildParameter(String buildURL, String key) {
		Map<String, String> buildParameters = getBuildParameters(buildURL);

		if (buildParameters.containsKey(key)) {
			return buildParameters.get(key);
		}

		throw new RuntimeException(
			"Unable to find build parameter '" + key + "' at " + buildURL);
	}

	public static Map<String, String> getBuildParameters(String buildURL) {
		if (!buildURL.endsWith("/")) {
			buildURL += "/";
		}

		String buildParametersURL = getLocalURL(
			combine(buildURL, "api/json?tree=actions[parameters[name,value]]"));

		try {
			JSONObject jsonObject = toJSONObject(buildParametersURL);

			return JenkinsAPIUtil.getBuildParameters(jsonObject);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
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
				if (url.startsWith("file://")) {
					properties.putAll(
						getProperties(new File(url.replace("file://", ""))));

					continue;
				}

				properties.load(
					new StringReader(
						toString(
							getLocalURL(url), false, 0, null, null, 0,
							_MILLIS_TIMEOUT_DEFAULT, null, true)));
			}

			_buildProperties.clear();

			_buildProperties.putAll(properties);
		}

		return properties;
	}

	public static String getBuildProperty(
			boolean checkCache, String propertyName)
		throws IOException {

		return getProperty(getBuildProperties(checkCache), propertyName);
	}

	public static String getBuildProperty(String propertyName)
		throws IOException {

		return getBuildProperty(true, propertyName);
	}

	public static List<String> getBuildPropertyAsList(
			boolean checkCache, String key)
		throws IOException {

		String propertyContent = getProperty(
			getBuildProperties(checkCache), key);

		if (propertyContent == null) {
			return Collections.emptyList();
		}

		return Arrays.asList(propertyContent.split(","));
	}

	public static Map<String, JSONObject> getBuildResultJSONObjects(
		List<String> buildResultJsonURLs) {

		final Map<String, JSONObject> buildResultJSONObjects =
			Collections.synchronizedMap(new HashMap<String, JSONObject>());

		List<Callable<Void>> callables = new ArrayList<>();

		for (final String buildResultJsonURL : buildResultJsonURLs) {
			Callable<Void> callable = new Callable<Void>() {

				@Override
				public Void call() {
					JSONObject jsonObject = null;

					try {
						jsonObject = toJSONObject(buildResultJsonURL);
					}
					catch (Exception exception) {
						System.out.println(exception.toString());
					}

					if (jsonObject != null) {
						buildResultJSONObjects.put(
							buildResultJsonURL, jsonObject);
					}

					return null;
				}

			};

			callables.add(callable);
		}

		ThreadPoolExecutor threadPoolExecutor = getNewThreadPoolExecutor(
			25, true);

		ParallelExecutor<Void> parallelExecutor = new ParallelExecutor<>(
			callables, threadPoolExecutor);

		parallelExecutor.execute();

		return buildResultJSONObjects;
	}

	public static List<String> getBuildResultJsonURLs(
		String jobURL, int maxBuildCount) {

		List<String> buildResultJsonURLs = new ArrayList<>();

		int lastCompletedBuildNumber =
			JenkinsAPIUtil.getLastCompletedBuildNumber(jobURL);

		int buildNumber = Math.max(0, lastCompletedBuildNumber - maxBuildCount);

		while (buildNumber <= lastCompletedBuildNumber) {
			String buildURL = jobURL + "/" + buildNumber;

			buildResultJsonURLs.add(
				getBuildArtifactURL(buildURL, "build-result.json"));

			buildNumber++;
		}

		return buildResultJsonURLs;
	}

	public static String getBuildURLByBuildID(String buildID) {
		Matcher matcher = _buildIDPattern.matcher(buildID);

		matcher.find();

		StringBuilder sb = new StringBuilder();

		sb.append("https://test-");
		sb.append(matcher.group("cohortNumber"));
		sb.append("-");
		sb.append(Integer.parseInt(matcher.group("masterNumber")));
		sb.append(".liferay.com/job/");

		Properties buildProperties = null;

		try {
			buildProperties = getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build.properties", ioException);
		}

		String jobName = null;

		for (String propertyName : buildProperties.stringPropertyNames()) {
			if (propertyName.startsWith("job.id[")) {
				String propertyValue = buildProperties.getProperty(
					propertyName);

				if (propertyValue.equals(matcher.group("jobID"))) {
					jobName = propertyName.substring(
						7, propertyName.length() - 1);

					break;
				}
			}
		}

		sb.append(jobName);
		sb.append("/");
		sb.append(matcher.group("buildNumber"));

		return sb.toString();
	}

	public static String getBuildURLByJenkinsReportURL(
		String jenkinsReportURL) {

		Matcher matcher = _jenkinsReportURLPattern.matcher(jenkinsReportURL);

		matcher.find();

		StringBuilder sb = new StringBuilder();

		sb.append(matcher.group("masterURL"));
		sb.append("job/");
		sb.append(matcher.group("jobName"));
		sb.append("/");
		sb.append(matcher.group("buildNumber"));

		return sb.toString();
	}

	public static BufferedReader getCachedFileBufferedReader(String key) {
		File cachedTextFile = _getCacheFile(key);

		if (!cachedTextFile.exists()) {
			return null;
		}

		try {
			return Files.newBufferedReader(
				Paths.get(cachedTextFile.toURI()), StandardCharsets.UTF_8);
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

	public static File getCanonicalFile(File file) {
		return new File(getCanonicalPath(file));
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

	public static String getCIProperty(
		String branchName, String key, String repositoryName) {

		List<String> ciPropertyURLs = new ArrayList<>();

		if (repositoryName.startsWith("com-liferay-")) {
			StringBuilder sb = new StringBuilder();

			sb.append("https://raw.githubusercontent.com/liferay");
			sb.append("/liferay-portal");

			if (!branchName.equals("master")) {
				sb.append("-ee");
			}

			sb.append("/");
			sb.append(branchName);
			sb.append("/ci.properties");

			ciPropertyURLs.add(sb.toString());
		}

		StringBuilder sb = new StringBuilder();

		sb.append("https://raw.githubusercontent.com/liferay/");
		sb.append(repositoryName);
		sb.append("/");
		sb.append(branchName);
		sb.append("/ci.properties");

		ciPropertyURLs.add(sb.toString());

		Properties ciProperties = new Properties();

		for (String ciPropertyURL : ciPropertyURLs) {
			try {
				String ciPropertiesString = toString(ciPropertyURL, true);

				ciProperties.load(new StringReader(ciPropertiesString));
			}
			catch (IOException ioException) {
				System.out.println(
					"Unable to load ci.properties from " + ciPropertyURL);
			}
		}

		return ciProperties.getProperty(key);
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

	public static long getCurrentTimeMillis() {
		if (!isCINode()) {
			return System.currentTimeMillis();
		}

		if (_currentTimeMillisDelta == null) {
			Long remoteCurrentTimeSeconds = null;

			int retry = 0;

			while ((remoteCurrentTimeSeconds == null) && (retry < 3)) {
				retry++;

				remoteCurrentTimeSeconds = getRemoteCurrentTimeSeconds(
					getJenkinsMasterName(getHostName(null)));

				if ((remoteCurrentTimeSeconds == null) && (retry < 3)) {
					sleep(1000);
				}
			}

			if (remoteCurrentTimeSeconds != null) {
				long remoteCurrentTimeMillis = remoteCurrentTimeSeconds * 1000;

				_currentTimeMillisDelta =
					System.currentTimeMillis() - remoteCurrentTimeMillis;
			}
		}

		if (_currentTimeMillisDelta == null) {
			return System.currentTimeMillis();
		}

		return System.currentTimeMillis() - _currentTimeMillisDelta;
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
			String timeStamp = String.valueOf(getCurrentTimeMillis());

			synchronized (_timeStamps) {
				if (_timeStamps.contains(timeStamp)) {
					continue;
				}

				_timeStamps.add(timeStamp);
			}

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

	public static String getDockerImageName(File dockerFile) {
		if ((dockerFile == null) || !dockerFile.exists()) {
			return null;
		}

		String dockerFileContent;

		try {
			dockerFileContent = read(dockerFile);
		}
		catch (IOException ioException) {
			return null;
		}

		if (isNullOrEmpty(dockerFileContent)) {
			return null;
		}

		Matcher dockerFileMatcher = _dockerFilePattern.matcher(
			dockerFileContent);

		if (!dockerFileMatcher.find()) {
			return null;
		}

		return dockerFileMatcher.group("dockerImageName");
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

	public static String getGitDirectoryName(
		String repositoryName, String upstreamBranchName) {

		String targetGitDirectoryName = _getGitDirectoryName(
			repositoryName, upstreamBranchName,
			_getGitWorkingDirectoriesJSONArray());

		if (targetGitDirectoryName == null) {
			targetGitDirectoryName = _getGitDirectoryName(
				repositoryName, upstreamBranchName,
				_getGitDirectoriesJSONArray());
		}

		return targetGitDirectoryName;
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

	public static DateFormat getGitHubDateFormat() {
		return _gitHubDateFormat;
	}

	public static String getGitRepositoryName(String gitDirectoryName) {
		JSONObject gitDirectoryJSONObject = _getGitDirectoryJSONObject(
			gitDirectoryName);

		if (gitDirectoryJSONObject == null) {
			return null;
		}

		return gitDirectoryJSONObject.getString("repository");
	}

	public static String getGitUpstreamBranchName(String gitDirectoryName) {
		JSONObject gitDirectoryJSONObject = _getGitDirectoryJSONObject(
			gitDirectoryName);

		if (gitDirectoryJSONObject == null) {
			return null;
		}

		return gitDirectoryJSONObject.getString("branch");
	}

	public static File getGitWorkingDir(File file) {
		if (file == null) {
			return null;
		}

		File canonicalFile = getCanonicalFile(file);

		File parentFile = canonicalFile.getParentFile();

		if ((parentFile == null) || !parentFile.exists()) {
			return file;
		}

		if (!canonicalFile.isDirectory()) {
			return getGitWorkingDir(parentFile);
		}

		File gitDir = new File(canonicalFile, ".git");

		if (!gitDir.exists()) {
			return getGitWorkingDir(parentFile);
		}

		return canonicalFile;
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

			String hostName = inetAddress.getHostName();

			if (hostName.matches("\\d+-\\d+-\\d+-\\d+")) {
				hostName = "cloud-" + hostName;
			}

			return hostName;
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

	public static Properties getJenkinsBuildProperties() {
		Properties properties = new Properties();

		synchronized (_jenkinsBuildProperties) {
			if (!_jenkinsBuildProperties.isEmpty()) {
				properties.putAll(_jenkinsBuildProperties);

				return properties;
			}

			for (String url : URLS_JENKINS_BUILD_PROPERTIES_DEFAULT) {
				if (url.startsWith("file://")) {
					properties.putAll(
						getProperties(new File(url.replace("file://", ""))));

					continue;
				}

				try {
					properties.load(
						new StringReader(
							toString(
								getLocalURL(url), false, 0, null, null, 0,
								_MILLIS_TIMEOUT_DEFAULT, null, true)));
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}

			_jenkinsBuildProperties.clear();

			_jenkinsBuildProperties.putAll(properties);
		}

		return properties;
	}

	public static String getJenkinsMasterName(String jenkinsSlaveName) {
		jenkinsSlaveName = jenkinsSlaveName.replaceAll("([^\\.]+).*", "$1");

		if (jenkinsSlaveName.matches("test-\\d{1,2}-\\d{1,2}")) {
			return jenkinsSlaveName;
		}

		Map<String, List<String>> jenkinsNodeMap = getJenkinsNodeMap();

		if (jenkinsNodeMap != null) {
			for (Map.Entry<String, List<String>> entry :
					jenkinsNodeMap.entrySet()) {

				List<String> jenkinsSlaveNames = entry.getValue();

				if (jenkinsSlaveNames.contains(jenkinsSlaveName)) {
					return entry.getKey();
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("Unable to get Jenkins master name for slave ");
		sb.append(jenkinsSlaveName);
		sb.append(".\n");
		sb.append("Jenkins Node Map:\n");

		if (jenkinsNodeMap != null) {
			for (Map.Entry<String, List<String>> entry :
					jenkinsNodeMap.entrySet()) {

				sb.append(entry.getKey());
				sb.append("\n    ");

				for (String jenkinsNodeName : entry.getValue()) {
					sb.append(" ");
					sb.append(jenkinsNodeName);
				}

				sb.append("\n");
			}
		}

		NotificationUtil.sendEmail(
			sb.toString(), "jenkins", "Unable to get Jenkins master name",
			"peter.yoo@liferay.com");

		return null;
	}

	public static List<JenkinsMaster> getJenkinsMasters(
		Properties buildProperties, int minimumRAM, int maximumSlavesPerHost,
		String prefix) {

		List<JenkinsMaster> jenkinsMasters = new ArrayList<>();

		Pattern pattern = Pattern.compile(
			"master\\.slaves\\((?<jenkinsMasterName>" + prefix + "-\\d+)\\)");

		for (String buildPropertyName : buildProperties.stringPropertyNames()) {
			Matcher matcher = pattern.matcher(buildPropertyName);

			if (!matcher.matches()) {
				continue;
			}

			JenkinsMaster jenkinsMaster = JenkinsMaster.getInstance(
				matcher.group("jenkinsMasterName"));

			if ((jenkinsMaster.getSlaveRAM() >= minimumRAM) &&
				(jenkinsMaster.getSlavesPerHost() <= maximumSlavesPerHost)) {

				jenkinsMasters.add(jenkinsMaster);
			}
		}

		return jenkinsMasters;
	}

	public static Map<String, List<String>> getJenkinsNodeMap() {
		Retryable<Map<String, List<String>>> retryable =
			new Retryable<Map<String, List<String>>>(false, 2, 10, true) {

				@Override
				public Map<String, List<String>> execute() {
					Properties buildProperties = null;

					try {
						buildProperties = getBuildProperties(_checkCache);
					}
					catch (IOException ioException) {
						throw new RuntimeException(
							"Unable to get build properties", ioException);
					}

					Map<String, List<String>> jenkinsNodeMap = new HashMap<>();

					for (Object propertyName : buildProperties.keySet()) {
						Matcher jenkinsSlavesPropertyNameMatcher =
							_jenkinsSlavesPropertyNamePattern.matcher(
								propertyName.toString());

						if (jenkinsSlavesPropertyNameMatcher.matches()) {
							String jenkinsMasterName =
								jenkinsSlavesPropertyNameMatcher.group(1);

							List<String> jenkinsSlaveNames = getSlaves(
								buildProperties, jenkinsMasterName, null,
								false);

							if (jenkinsSlaveNames.isEmpty()) {
								continue;
							}

							jenkinsNodeMap.put(
								jenkinsMasterName, jenkinsSlaveNames);
						}
					}

					if (jenkinsNodeMap.isEmpty()) {
						System.out.println(
							combine(
								"Jenkins slave name mapping properties could ",
								"not be found. Build properties URLs will be ",
								"reverted to their default values."));

						setBuildProperties(URLS_BUILD_PROPERTIES_DEFAULT);

						_checkCache = false;

						throw new RuntimeException(
							"Unable to load Jenkins node map");
					}

					return jenkinsNodeMap;
				}

				private boolean _checkCache = true;

			};

		return retryable.executeWithRetries();
	}

	public static List<String> getJenkinsNodes() {
		Map<String, List<String>> jenkinsNodeMap = getJenkinsNodeMap();

		Set<String> jenkinsNodes = new TreeSet<>(jenkinsNodeMap.keySet());

		for (List<String> jenkinsSlaves : jenkinsNodeMap.values()) {
			jenkinsNodes.addAll(jenkinsSlaves);
		}

		return new ArrayList<>(jenkinsNodes);
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
		if (remoteURL.contains(Build.DEPENDENCIES_URL_TOKEN)) {
			remoteURL = fixFileName(remoteURL);

			String fileURL = remoteURL.replace(
				Build.DEPENDENCIES_URL_TOKEN, urlDependenciesFile);

			File file = new File(fileURL.substring("file:".length()));

			if (file.exists()) {
				remoteURL = fileURL;
			}
			else {
				remoteURL = remoteURL.replace(
					Build.DEPENDENCIES_URL_TOKEN, urlDependenciesHttp);
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
		Matcher remoteURLAuthorityMatcher3 =
			_remoteURLAuthorityPattern3.matcher(localURL);

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
		else if (remoteURLAuthorityMatcher3.find()) {
			String localURLAuthority = combine(
				"http://mirrors.lax.liferay.com/",
				remoteURLAuthorityMatcher3.group(2));
			String remoteURLAuthority = remoteURLAuthorityMatcher3.group(0);

			localURL = localURL.replaceAll(
				remoteURLAuthority, localURLAuthority);
		}

		return localURL + localURLQueryString;
	}

	public static String getMostAvailableMasterURL(
		String baseInvocationURL, int invokedBatchSize) {

		return getMostAvailableMasterURL(
			baseInvocationURL, null, invokedBatchSize,
			JenkinsMaster.getSlaveRAMMinimumDefault(),
			JenkinsMaster.getSlavesPerHostDefault());
	}

	public static String getMostAvailableMasterURL(
		String baseInvocationURL, String blacklist, int invokedBatchSize,
		int minimumRAM, int maximumSlavesPerHost) {

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
			JSONObject jsonObject = toJSONObject(sb.toString(), false);

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
					blacklist, minimumRAM, maximumSlavesPerHost,
					buildProperties);

			Random random = new Random(getCurrentTimeMillis());

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

		String propertyName = getPropertyName(
			properties, basePropertyName, opts);

		if (!useBasePropertyAsDefault &&
			basePropertyName.equals(propertyName)) {

			return null;
		}

		return _getProperty(properties, new ArrayList<String>(), propertyName);
	}

	public static String getProperty(
		Properties properties, String basePropertyName, String... opts) {

		return getProperty(properties, basePropertyName, true, opts);
	}

	public static String getPropertyName(
		Properties properties, boolean useBasePropertyName,
		String basePropertyName, String... opts) {

		if ((opts == null) || (opts.length == 0)) {
			return basePropertyName;
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
			_getPropertyOptRegexSets(
				basePropertyName, matchingProperties.stringPropertyNames());

		for (Set<String> targetOptSet : targetOptSets) {
			for (Map.Entry<String, Set<String>> propertyOptRegexEntry :
					propertyOptRegexSets.entrySet()) {

				Set<String> propertyOptRegexes =
					propertyOptRegexEntry.getValue();

				if (targetOptSet.size() < propertyOptRegexes.size()) {
					continue;
				}

				boolean matchesAllPropertyOptRegexes = true;

				for (String targetOpt : targetOptSet) {
					boolean matchesPropertyOptRegex = false;

					for (String propertyOptRegex : propertyOptRegexes) {
						if (targetOpt.matches(propertyOptRegex)) {
							matchesPropertyOptRegex = true;
						}
					}

					if (!matchesPropertyOptRegex) {
						matchesAllPropertyOptRegexes = false;

						break;
					}
				}

				if (matchesAllPropertyOptRegexes) {
					propertyName = propertyOptRegexEntry.getKey();

					break;
				}
			}

			if (propertyName != null) {
				break;
			}
		}

		if (propertyName != null) {
			return propertyName;
		}

		if (useBasePropertyName) {
			return basePropertyName;
		}

		return null;
	}

	public static String getPropertyName(
		Properties properties, String basePropertyName, String... opts) {

		return getPropertyName(properties, true, basePropertyName, opts);
	}

	public static List<String> getPropertyOptions(String propertyName) {
		List<String> propertyOptions = new ArrayList<>();

		Stack<Integer> stack = new Stack<>();

		Integer start = null;

		for (int i = 0; i < propertyName.length(); i++) {
			char c = propertyName.charAt(i);

			if (c == '[') {
				stack.push(i);

				if (start == null) {
					start = i;
				}

				continue;
			}

			if ((c != ']') || (start == null)) {
				continue;
			}

			stack.pop();

			if (stack.isEmpty()) {
				propertyOptions.add(propertyName.substring(start + 1, i));

				start = null;
			}
		}

		return propertyOptions;
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

	public static Long getRemoteCurrentTimeSeconds(String hostname) {
		if (isNullOrEmpty(hostname)) {
			return null;
		}

		String command = combine("ssh root@", hostname, " date +%s");

		try {
			Process process = executeBashCommands(
				false, new File("."), 3000, command);

			if (process.exitValue() != 0) {
				return null;
			}

			String output = readInputStream(process.getInputStream());

			try {
				return Long.parseLong(output.replaceAll("(?s)(\\d+).*", "$1"));
			}
			catch (NumberFormatException numberFormatException) {
				return null;
			}
		}
		catch (IOException | TimeoutException exception) {
			return null;
		}
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
		Matcher localURLAuthorityMatcher3 = _localURLAuthorityPattern3.matcher(
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
		else if (localURLAuthorityMatcher3.find()) {
			String localURLAuthority = localURLAuthorityMatcher3.group(0);
			String remoteURLAuthority = combine(
				"https://", localURLAuthorityMatcher3.group(2));

			remoteURL = remoteURL.replaceAll(
				localURLAuthority, remoteURLAuthority);
		}

		return remoteURL + remoteURLQueryString;
	}

	public static String getResourceFileContent(String resourceName)
		throws IOException {

		try (InputStream resourceInputStream =
				JenkinsResultsParserUtil.class.getResourceAsStream(
					resourceName)) {

			return readInputStream(resourceInputStream);
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
					slave = slave.trim();

					if (isNullOrEmpty(slave)) {
						continue;
					}

					slaves.add(slave);
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

	public static void gzip(File sourceFile, File targetGzipFile) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(
				targetGzipFile);
			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
				fileOutputStream);
			FileInputStream fileInputStream = new FileInputStream(sourceFile)) {

			byte[] bytes = new byte[1024];
			int length = 0;

			while ((length = fileInputStream.read(bytes)) > 0) {
				gzipOutputStream.write(bytes, 0, length);
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static boolean isCINode() {
		if (_ciNode != null) {
			return _ciNode;
		}

		String hostName = getHostName("");

		List<String> jenkinsNodes = getJenkinsNodes();

		String hostNameSuffix = ".lax.liferay.com";

		if (hostName.endsWith(hostNameSuffix)) {
			hostName = hostName.substring(
				0, hostName.length() - hostNameSuffix.length());
		}

		if (jenkinsNodes.contains(hostName)) {
			_ciNode = true;
		}
		else {
			_ciNode = false;
		}

		return _ciNode;
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

	public static boolean isInteger(String string) {
		if ((string != null) && string.matches("\\d+")) {
			return true;
		}

		return false;
	}

	public static boolean isJSONArray(String string) {
		if (isNullOrEmpty(string)) {
			return false;
		}

		string = string.trim();

		try {
			new JSONArray(string);
		}
		catch (Exception exception) {
			return false;
		}

		return true;
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

	public static boolean isJSONObject(String string) {
		if (isNullOrEmpty(string)) {
			return false;
		}

		string = string.trim();

		try {
			new JSONObject(string);
		}
		catch (Exception exception) {
			return false;
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

	public static boolean isNullOrEmpty(String string) {
		if (string == null) {
			return true;
		}

		String trimmedString = string.trim();

		if (trimmedString.isEmpty()) {
			return true;
		}

		return false;
	}

	public static boolean isOSX() {
		return SystemUtils.IS_OS_MAC_OSX;
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

	public static boolean isSHA(String sha) {
		if (sha == null) {
			return false;
		}

		Matcher matcher = _shaPattern.matcher(sha);

		return matcher.matches();
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

	public static void pullDockerImageDependencies(
		File baseDir, String[] excludedDockerImageNames) {

		String dockerEnabled = System.getenv("DOCKER_ENABLED");

		if (isNullOrEmpty(dockerEnabled) || !dockerEnabled.equals("true")) {
			return;
		}

		List<String> pulledDockerImageNames = new ArrayList<>();

		for (File dockerFile : findFiles(baseDir, "Dockerfile")) {
			String dockerImageName = getDockerImageName(dockerFile);

			if (isNullOrEmpty(dockerImageName) ||
				pulledDockerImageNames.contains(dockerImageName)) {

				continue;
			}

			boolean excludeDockerImageName = false;

			if (excludedDockerImageNames != null) {
				for (String excludedImageName : excludedDockerImageNames) {
					if (isNullOrEmpty(excludedImageName)) {
						continue;
					}

					if (dockerImageName.matches(excludedImageName)) {
						excludeDockerImageName = true;

						break;
					}
				}
			}

			if (excludeDockerImageName) {
				continue;
			}

			try {
				Process process = executeBashCommands(
					"docker pull " + dockerImageName);

				if (process.exitValue() != 0) {
					System.out.println(
						"Failed to pull Docker image " + dockerImageName);

					return;
				}

				pulledDockerImageNames.add(dockerImageName);
			}
			catch (IOException | TimeoutException exception) {
				System.out.println(
					"Failed to pull Docker image " + dockerImageName);

				exception.printStackTrace();
			}
		}
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

	public static String removeDuplicates(
		String delimiter, String delimitedString) {

		if (isNullOrEmpty(delimitedString)) {
			return delimitedString;
		}

		List<String> strings = new ArrayList<>();

		for (String string : delimitedString.split(delimiter)) {
			if (!strings.contains(string)) {
				strings.add(string);
			}
		}

		return join(",", strings);
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

		if (isNullOrEmpty(text)) {
			if (cacheFile.exists()) {
				cacheFile.delete();
			}

			return;
		}

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

	public static void tarGzip(File sourceDir, File targetTarGzipFile) {
		if (!sourceDir.isDirectory()) {
			throw new RuntimeException(sourceDir + " is not a directory");
		}

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				targetTarGzipFile);
			BufferedOutputStream bufferedOutputStream =
				new BufferedOutputStream(fileOutputStream);
			GzipCompressorOutputStream gzipCompressorOutputStream =
				new GzipCompressorOutputStream(bufferedOutputStream);
			TarArchiveOutputStream tarArchiveOutputStream =
				new TarArchiveOutputStream(gzipCompressorOutputStream)) {

			tarArchiveOutputStream.setLongFileMode(
				TarArchiveOutputStream.LONGFILE_POSIX);

			for (File file : findFiles(sourceDir, ".*")) {
				TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(
					file, getPathRelativeTo(file, sourceDir));

				tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);

				Files.copy(file.toPath(), tarArchiveOutputStream);

				tarArchiveOutputStream.closeArchiveEntry();
			}

			tarArchiveOutputStream.finish();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
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
			HttpRequestMethod httpRequestMethod, String postContent,
			int retryPeriod, int timeout,
			HTTPAuthorization httpAuthorizationHeader)
		throws IOException {

		return new BufferedReader(
			new InputStreamReader(
				toInputStream(
					url, checkCache, maxRetries, httpRequestMethod, postContent,
					retryPeriod, timeout, httpAuthorizationHeader)));
	}

	public static String toDateString(Date date) {
		return toDateString(
			date, "MMM dd, yyyy h:mm:ss a z", "America/Los_Angeles");
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

		remainingDuration = _appendStringForUnit(
			remainingDuration, _MILLIS_DAY, false, sb, "day", "days");

		remainingDuration = _appendStringForUnit(
			remainingDuration, _MILLIS_HOUR, false, sb, "hour", "hours");

		remainingDuration = _appendStringForUnit(
			remainingDuration, _MILLIS_MINUTE, true, sb, "minute", "minutes");

		if (duration < 60000) {
			remainingDuration = _appendStringForUnit(
				remainingDuration, _MILLIS_SECOND, true, sb, "second",
				"seconds");
		}

		if (duration < 1000) {
			_appendStringForUnit(remainingDuration, 1, true, sb, "ms", "ms");
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

			long start = System.currentTimeMillis();

			try (InputStream inputStream = toInputStream(
					url.toString(), false)) {

				if (file.exists()) {
					file.delete();
				}

				File parentFile = file.getParentFile();

				if ((parentFile != null) && !parentFile.exists()) {
					parentFile.mkdirs();
				}

				byte[] bytes = new byte[10240];

				int bytesReadCount = inputStream.read(bytes);

				long totalBytesWrittenCount = 0;

				try (FileOutputStream fileOutputStream = new FileOutputStream(
						file)) {

					while (bytesReadCount != -1) {
						fileOutputStream.write(bytes, 0, bytesReadCount);

						totalBytesWrittenCount += bytesReadCount;

						bytesReadCount = inputStream.read(bytes);
					}

					fileOutputStream.flush();
				}

				System.out.println(
					combine(
						"Finished downloading ",
						toFileSizeString(totalBytesWrittenCount), " in ",
						toDurationString(System.currentTimeMillis() - start)));
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static String toFileSizeString(long byteCount) {
		long remainingByteCount = byteCount;

		StringBuilder sb = new StringBuilder();

		remainingByteCount = _appendStringForUnit(
			remainingByteCount, _BYTES_GIGA, false, sb, "GB", "GB");

		remainingByteCount = _appendStringForUnit(
			remainingByteCount, _BYTES_MEGA, false, sb, "MB", "MB");

		remainingByteCount = _appendStringForUnit(
			remainingByteCount, _BYTES_KILO, true, sb, "KB", "KB");

		if (byteCount < _BYTES_KILO) {
			_appendStringForUnit(remainingByteCount, 1, true, sb, "B", "B");
		}

		String fileSizeString = sb.toString();

		fileSizeString = fileSizeString.trim();

		if (fileSizeString.equals("")) {
			fileSizeString = "0 B";
		}

		return fileSizeString;
	}

	public static InputStream toInputStream(String url, boolean checkCache)
		throws IOException {

		return toInputStream(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, null, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static InputStream toInputStream(
			String url, boolean checkCache, int maxRetries,
			HttpRequestMethod httpRequestMethod, String postContent,
			int retryPeriod, int timeout,
			HTTPAuthorization httpAuthorizationHeader)
		throws IOException {

		if (url.startsWith("file:") &&
			url.contains("liferay-jenkins-results-parser-samples-ee")) {

			File file = new File(url.replace("file:", ""));

			if (!file.exists()) {
				if (url.contains("json?")) {
					url = url.substring(0, url.indexOf("json?") + 4);
				}

				if (url.contains("json[qt]")) {
					url = url.substring(0, url.indexOf("json[qt]") + 4);
				}
			}
		}

		if (url.contains("/userContent/") && (timeout == 0)) {
			timeout = 5000;
		}

		if (httpRequestMethod == null) {
			if (postContent != null) {
				httpRequestMethod = HttpRequestMethod.POST;
			}
			else {
				httpRequestMethod = HttpRequestMethod.GET;
			}
		}

		url = fixURL(url);

		String key = url.replace("//", "/");

		if (url.startsWith("file:")) {
			url = fixFileURL(url);
		}
		else {
			if (checkCache) {
				if (debug) {
					System.out.println("Loading " + url);
				}

				File cachedFile = _getCacheFile(_PREFIX_TO_STRING_CACHE + key);

				if ((cachedFile != null) && cachedFile.exists()) {
					return new FileInputStream(cachedFile);
				}
			}
		}

		int retryCount = 0;

		while (true) {
			try {
				if (debug) {
					System.out.println("Downloading " + url);
				}

				if ((httpAuthorizationHeader == null) &&
					(url.startsWith("https://api.github.com") ||
					 url.startsWith(
						 "https://raw.githubusercontent.com/liferay/"))) {

					Properties buildProperties = getBuildProperties();

					httpAuthorizationHeader = new TokenHTTPAuthorization(
						buildProperties.getProperty("github.access.token"));
				}

				if ((httpAuthorizationHeader == null) &&
					url.startsWith("https://release.liferay.com")) {

					Properties buildProperties = getBuildProperties();

					httpAuthorizationHeader = new BasicHTTPAuthorization(
						buildProperties.getProperty(
							"jenkins.admin.user.password"),
						buildProperties.getProperty("jenkins.admin.user.name"));
				}

				if ((httpAuthorizationHeader == null) &&
					url.matches("https://liferay.spiraservice.net.+")) {

					Properties buildProperties = getBuildProperties();

					httpAuthorizationHeader = new BasicHTTPAuthorization(
						buildProperties.getProperty("spira.admin.user.token"),
						buildProperties.getProperty("spira.admin.user.name"));
				}

				if (url.matches("https://test-\\d+-\\d+.liferay.com/.+")) {
					if (isCINode()) {
						url = getLocalURL(url);

						httpAuthorizationHeader = null;
					}
					else {
						if (httpAuthorizationHeader == null) {
							Properties buildProperties = getBuildProperties();

							httpAuthorizationHeader =
								new BasicHTTPAuthorization(
									buildProperties.getProperty(
										"jenkins.admin.user.password"),
									buildProperties.getProperty(
										"jenkins.admin.user.name"));
						}
					}
				}

				boolean testrayRequest = false;

				if (url.matches("https://testray.liferay.com/?.+") ||
					url.matches(
						"https://webserver-testray-dev.lfr.cloud/?.+")) {

					testrayRequest = true;
				}

				if ((httpAuthorizationHeader == null) && testrayRequest) {
					Properties buildProperties = getBuildProperties();

					httpAuthorizationHeader = new BasicHTTPAuthorization(
						getProperty(
							buildProperties, "testray.admin.user.password"),
						getProperty(
							buildProperties, "testray.admin.user.name"));
				}

				URL urlObject = new URL(url);

				URLConnection urlConnection = urlObject.openConnection();

				if (urlConnection instanceof HttpURLConnection) {
					HttpURLConnection httpURLConnection =
						(HttpURLConnection)urlConnection;

					if (httpRequestMethod == HttpRequestMethod.PATCH) {
						httpURLConnection.setRequestMethod("POST");

						httpURLConnection.setRequestProperty(
							"X-HTTP-Method-Override", "PATCH");
					}
					else {
						httpURLConnection.setRequestMethod(
							httpRequestMethod.name());
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

						if (!testrayRequest) {
							httpURLConnection.setRequestProperty(
								"Content-Type", "application/json");
						}
					}

					if (postContent != null) {
						if (httpRequestMethod == null) {
							httpURLConnection.setRequestMethod("POST");
						}

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
							combine(
								"Unable to parse GitHub API rate limit headers",
								"\nURL:\n    ", url));

						exception.printStackTrace();
					}
				}

				return urlConnection.getInputStream();
			}
			catch (IOException ioException) {
				if ((ioException instanceof UnknownHostException) &&
					url.matches("http://test-\\d+-\\d+/.*")) {

					return toInputStream(
						url.replaceAll(
							"http://(test-\\d+-\\d+)(/.*)",
							"https://$1.liferay.com$2"),
						checkCache, maxRetries, httpRequestMethod, postContent,
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
			timeout, httpAuthorization, true);

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
		if ((url != null) && url.startsWith("file:")) {
			try {
				return toJSONObject(url, false, 1, null, null, 0, 5, null);
			}
			catch (IOException ioException) {
				if (!url.contains("[qt]")) {
					throw ioException;
				}

				return toJSONObject(url.substring(0, url.indexOf("[qt]")));
			}
		}

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
			String url, boolean checkCache, HttpRequestMethod httpRequestMethod)
		throws IOException {

		return toJSONObject(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, httpRequestMethod, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null);
	}

	public static JSONObject toJSONObject(
			String url, boolean checkCache, HttpRequestMethod httpRequestMethod,
			String postContent)
		throws IOException {

		return toJSONObject(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, httpRequestMethod,
			postContent, _SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT,
			null);
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
			HttpRequestMethod httpRequestMethod, String postContent,
			int retryPeriod, int timeout, HTTPAuthorization httpAuthorization)
		throws IOException {

		String response = toString(
			url, checkCache, maxRetries, httpRequestMethod, postContent,
			retryPeriod, timeout, httpAuthorization, true);

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

	public static JSONObject toJSONObject(
			String url, int maxRetries, int retryPeriod, String postContent)
		throws IOException {

		return toJSONObject(
			url, true, maxRetries, null, postContent, retryPeriod,
			_MILLIS_TIMEOUT_DEFAULT, null);
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
		String prefix, List<String> globs) {

		return toPathMatchers(prefix, globs.toArray(new String[0]));
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
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null,
			false);
	}

	public static String toString(String url, boolean checkCache)
		throws IOException {

		return toString(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, null, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null,
			false);
	}

	public static String toString(
			String url, boolean checkCache, boolean requireResponse)
		throws IOException {

		return toString(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, null, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null,
			requireResponse);
	}

	public static String toString(
			String url, boolean checkCache, HttpRequestMethod httpRequestMethod)
		throws IOException {

		return toString(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, httpRequestMethod, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null,
			false);
	}

	public static String toString(
			String url, boolean checkCache, HttpRequestMethod httpRequestMethod,
			String postContent)
		throws IOException {

		return toString(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, httpRequestMethod,
			postContent, _SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT,
			null, false);
	}

	public static String toString(String url, boolean checkCache, int timeout)
		throws IOException {

		return toString(
			url, checkCache, _RETRIES_SIZE_MAX_DEFAULT, null, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, timeout, null, false);
	}

	public static String toString(
			String url, boolean checkCache, int maxRetries,
			HttpRequestMethod httpRequestMethod, String postContent,
			int retryPeriod, int timeout,
			HTTPAuthorization httpAuthorizationHeader, boolean expectResponse)
		throws IOException {

		for (int i = 0; i < 2; i++) {
			try (BufferedReader bufferedReader = toBufferedReader(
					url, checkCache, maxRetries, httpRequestMethod, postContent,
					retryPeriod, timeout, httpAuthorizationHeader)) {

				StringBuilder sb = new StringBuilder();

				String line = bufferedReader.readLine();

				while (line != null) {
					sb.append(line);
					sb.append("\n");

					line = bufferedReader.readLine();
				}

				int bytes = sb.length();

				if (expectResponse && (bytes == 0) && (i < 1)) {
					System.out.println(
						"Unable to get response, retrying request");

					continue;
				}

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

		return "";
	}

	public static String toString(
			String url, boolean checkCache, int maxRetries,
			int retryPeriodSeconds, int timeout)
		throws IOException {

		return toString(
			url, checkCache, maxRetries, null, null, retryPeriodSeconds,
			timeout, null, false);
	}

	public static String toString(
			String url, boolean checkCache, int maxRetries,
			int retryPeriodSeconds, int timeout, boolean expectResponse)
		throws IOException {

		return toString(
			url, checkCache, maxRetries, null, null, retryPeriodSeconds,
			timeout, null, expectResponse);
	}

	public static String toString(
			String url, boolean checkCache, int maxRetries, String postContent,
			int retryPeriod, int timeout)
		throws IOException {

		return toString(
			url, checkCache, maxRetries, null, postContent, retryPeriod,
			timeout, null, false);
	}

	public static String toString(
			String url, HttpRequestMethod httpRequestMethod)
		throws IOException {

		return toString(
			url, true, _RETRIES_SIZE_MAX_DEFAULT, httpRequestMethod, null,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null,
			false);
	}

	public static String toString(
			String url, HttpRequestMethod httpRequestMethod, String postContent)
		throws IOException {

		return toString(
			url, false, _RETRIES_SIZE_MAX_DEFAULT, httpRequestMethod,
			postContent, _SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT,
			null, false);
	}

	public static String toString(String url, String postContent)
		throws IOException {

		return toString(
			url, false, _RETRIES_SIZE_MAX_DEFAULT, null, postContent,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT, null,
			false);
	}

	public static String toString(
			String url, String postContent, HTTPAuthorization httpAuthorization)
		throws IOException {

		return toString(
			url, false, _RETRIES_SIZE_MAX_DEFAULT, null, postContent,
			_SECONDS_RETRY_PERIOD_DEFAULT, _MILLIS_TIMEOUT_DEFAULT,
			httpAuthorization, false);
	}

	public static void unGzip(File sourceGzipFile, File targetFile) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(
				targetFile);
			FileInputStream fileInputStream = new FileInputStream(
				sourceGzipFile);
			GZIPInputStream gzipInputStream = new GZIPInputStream(
				fileInputStream)) {

			byte[] bytes = new byte[1024];
			int length = 0;

			while ((length = gzipInputStream.read(bytes)) > 0) {
				fileOutputStream.write(bytes, 0, length);
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static void unTarGzip(File sourceTarGzipFile, File targetDir) {
		targetDir.mkdirs();

		try (FileInputStream fileInputStream = new FileInputStream(
				sourceTarGzipFile);
			GzipCompressorInputStream gzipCompressorInputStream =
				new GzipCompressorInputStream(fileInputStream);
			TarArchiveInputStream tarArchiveInputStream =
				new TarArchiveInputStream(gzipCompressorInputStream)) {

			ArchiveEntry archiveEntry = tarArchiveInputStream.getNextEntry();

			while (archiveEntry != null) {
				TarArchiveEntry tarArchiveEntry = (TarArchiveEntry)archiveEntry;

				if (tarArchiveEntry.isDirectory()) {
					File dir = new File(targetDir, tarArchiveEntry.getName());

					dir.mkdirs();
				}
				else {
					File file = new File(targetDir, tarArchiveEntry.getName());

					write(file, "");

					try (FileOutputStream fileOutputStream =
							new FileOutputStream(file, false);
						BufferedOutputStream bufferedOutputStream =
							new BufferedOutputStream(fileOutputStream)) {

						int b = tarArchiveInputStream.read();

						while (b != -1) {
							bufferedOutputStream.write(b);

							b = tarArchiveInputStream.read();
						}
					}
				}

				archiveEntry = tarArchiveInputStream.getNextEntry();
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static void unzip(File zipFile, File destDir) {
		try (FileInputStream fileInputStream = new FileInputStream(zipFile);
			ZipInputStream zipInputStream = new ZipInputStream(
				fileInputStream)) {

			ZipEntry zipEntry = zipInputStream.getNextEntry();

			while (zipEntry != null) {
				String zipEntryName = zipEntry.getName();

				File destFile = new File(destDir, zipEntryName);

				if (zipEntryName.endsWith(File.separator)) {
					Files.createDirectories(destFile.toPath());
				}
				else {
					destFile.mkdirs();

					Files.copy(
						zipInputStream, destFile.toPath(),
						StandardCopyOption.REPLACE_EXISTING);
				}

				zipEntry = zipInputStream.getNextEntry();
			}

			zipInputStream.closeEntry();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static void updateBuildDescription(
		String buildDescription, int buildNumber, String jobName,
		String masterHostname) {

		buildDescription = buildDescription.replaceAll("\"", "\\\\\"");
		buildDescription = buildDescription.replaceAll("\'", "\\\\\'");

		jobName = jobName.replace("%28", "(");
		jobName = jobName.replace("%29", ")");

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
		if (path.startsWith(BaseBuild.DEPENDENCIES_URL_TOKEN)) {
			path = path.replace(
				BaseBuild.DEPENDENCIES_URL_TOKEN,
				urlDependenciesFile.replace("file:", ""));
		}

		write(new File(path), content);
	}

	public static void writePropertiesFile(
		File propertiesFile, Properties properties, boolean verbose) {

		if (propertiesFile.exists()) {
			propertiesFile.delete();
		}

		File parentFile = propertiesFile.getParentFile();

		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}

		if (properties.isEmpty()) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("## Autogenerated\n\n");

		Set<String> propertyNames = new TreeSet<>(
			properties.stringPropertyNames());

		for (String propertyName : propertyNames) {
			sb.append(propertyName);
			sb.append("=");

			sb.append(
				StringEscapeUtils.escapeJava(
					getProperty(properties, propertyName)));

			sb.append("\n");
		}

		try {
			write(propertiesFile, sb.toString());
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

	public static void zip(final File sourceDir, File zipFile) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			final ZipOutputStream zipOutputStream = new ZipOutputStream(
				fileOutputStream)) {

			Files.walkFileTree(
				sourceDir.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes) {

						if (basicFileAttributes.isSymbolicLink()) {
							return FileVisitResult.CONTINUE;
						}

						try (FileInputStream fileInputStream =
								new FileInputStream(path.toFile())) {

							zipOutputStream.putNextEntry(
								new ZipEntry(
									getPathRelativeTo(
										path.toFile(), sourceDir)));

							byte[] bytes = new byte[1024];
							int len;

							while ((len = fileInputStream.read(bytes)) > 0) {
								zipOutputStream.write(bytes, 0, len);
							}

							zipOutputStream.closeEntry();
						}
						catch (IOException ioException) {
							throw new RuntimeException(ioException);
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
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

	public static class PropertyNameComparator implements Comparator<String> {

		@Override
		public int compare(String propertyName1, String propertyName2) {
			if ((propertyName1 == null) || (propertyName2 == null)) {
				return ObjectUtils.compare(propertyName1, propertyName2);
			}

			Set<String> propertyOptSet1 = _getPropertyOptSet(propertyName1);
			Set<String> propertyOptSet2 = _getPropertyOptSet(propertyName2);

			if (propertyOptSet1.size() != propertyOptSet2.size()) {
				return Integer.compare(
					propertyOptSet2.size(), propertyOptSet1.size());
			}

			int propertyOptRegexCount1 = _countPropertyOptRegexes(
				propertyOptSet1);
			int propertyOptRegexCount2 = _countPropertyOptRegexes(
				propertyOptSet2);

			if (propertyOptRegexCount1 != propertyOptRegexCount2) {
				return Integer.compare(
					propertyOptRegexCount1, propertyOptRegexCount2);
			}

			if (propertyName1.length() != propertyName2.length()) {
				return Integer.compare(
					propertyName2.length(), propertyName1.length());
			}

			return propertyName2.compareTo(propertyName1);
		}

		private int _countPropertyOptRegexes(Set<String> propertyOptSet) {
			int count = 0;

			for (String propertyOpt : propertyOptSet) {
				if (propertyOpt.contains(".+")) {
					count++;
				}
			}

			return count;
		}

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

	protected static String initCacheURL() {
		String cacheDirPath = System.getenv("CACHE_DIR");

		if (cacheDirPath != null) {
			File cacheDir = new File(cacheDirPath);

			if (cacheDir.exists()) {
				for (String cachedRepository : CACHED_REPOSITORIES) {
					File cacheRepositoryDir = new File(
						cacheDir, cachedRepository);

					if (!cacheRepositoryDir.exists()) {
						break;
					}
				}

				System.out.println(
					"Using " + cacheDirPath + " for cached files");

				return "file://" + cacheDirPath;
			}
		}

		return "http://mirrors-no-cache.lax.liferay.com/github.com/liferay";
	}

	protected static String urlDependenciesFile;
	protected static String urlDependenciesHttp =
		URL_CACHE + "/liferay-jenkins-results-parser-samples-ee/1/";

	static {
		File dependenciesDir = new File("src/test/resources/dependencies/");

		try {
			URI uri = dependenciesDir.toURI();

			URL url = uri.toURL();

			urlDependenciesFile = url.toString();
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private static long _appendStringForUnit(
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

	private static void _executeCommandService(
		final String command, final File baseDir,
		final Map<String, String> environments, final long maxLogSize,
		final boolean batchCommand) {

		if (batchCommand && !isWindows()) {
			throw new RuntimeException("Invalid OS: " + SystemUtils.OS_NAME);
		}

		Runnable runnable = new Runnable() {

			public void run() {
				StringBuilder sb = new StringBuilder();

				try {
					if (isWindows()) {
						if (batchCommand) {
							sb.append("cmd.exe /c ");
						}
						else {
							sb.append("C:\\Program Files\\Git\\bin\\sh.exe ");
						}
					}
					else {
						sb.append("/bin/sh ");
					}

					sb.append(command);

					Runtime runtime = Runtime.getRuntime();

					String[] environmentParameters =
						new String[environments.size()];

					int i = 0;

					for (Map.Entry<String, String> environment :
							environments.entrySet()) {

						environmentParameters[i] = combine(
							environment.getKey(), "=", environment.getValue());

						i++;
					}

					Process process = runtime.exec(
						sb.toString(), environmentParameters, baseDir);

					try (CountingInputStream countingInputStream =
							new CountingInputStream(process.getInputStream());
						InputStreamReader inputStreamReader =
							new InputStreamReader(
								countingInputStream, StandardCharsets.UTF_8)) {

						int logCharInt = 0;

						while ((logCharInt = inputStreamReader.read()) != -1) {
							if (countingInputStream.getCount() > maxLogSize) {
								continue;
							}

							System.out.print((char)logCharInt);
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

	private static synchronized JSONArray _getGitDirectoriesJSONArray() {
		if (_gitDirectoriesJSONArray != null) {
			return _gitDirectoriesJSONArray;
		}

		_gitDirectoriesJSONArray = new JSONArray();

		for (String url : URLS_GIT_DIRECTORIES_JSON_DEFAULT) {
			JSONArray jsonArray;

			try {
				if (url.startsWith("file://")) {
					jsonArray = new JSONArray(
						read(new File(url.replace("file://", ""))));
				}
				else {
					jsonArray = toJSONArray(getLocalURL(url), false);
				}
			}
			catch (IOException ioException) {
				continue;
			}

			if (jsonArray == null) {
				continue;
			}

			for (int i = 0; i < jsonArray.length(); i++) {
				_gitDirectoriesJSONArray.put(jsonArray.get(i));
			}
		}

		return _gitDirectoriesJSONArray;
	}

	private static JSONObject _getGitDirectoryJSONObject(
		String gitDirectoryName) {

		JSONObject jsonObject = _getGitDirectoryJSONObject(
			gitDirectoryName, _getGitWorkingDirectoriesJSONArray());

		if (jsonObject == null) {
			jsonObject = _getGitDirectoryJSONObject(
				gitDirectoryName, _getGitDirectoriesJSONArray());
		}

		return jsonObject;
	}

	private static JSONObject _getGitDirectoryJSONObject(
		String gitDirectoryName, JSONArray gitDirectoriesJSONArray) {

		for (int i = 0; i < gitDirectoriesJSONArray.length(); i++) {
			JSONObject gitDirectoryJSONObject =
				gitDirectoriesJSONArray.getJSONObject(i);

			if ((gitDirectoryJSONObject == JSONObject.NULL) ||
				!gitDirectoryName.equals(
					gitDirectoryJSONObject.getString("name"))) {

				continue;
			}

			return gitDirectoryJSONObject;
		}

		return null;
	}

	private static String _getGitDirectoryName(
		String repositoryName, String upstreamBranchName,
		JSONArray gitDirectoriesJSONArray) {

		for (int i = 0; i < gitDirectoriesJSONArray.length(); i++) {
			JSONObject gitDirectoryJSONObject =
				gitDirectoriesJSONArray.getJSONObject(i);

			if ((gitDirectoryJSONObject == JSONObject.NULL) ||
				!Objects.equals(
					repositoryName,
					gitDirectoryJSONObject.getString("repository")) ||
				!Objects.equals(
					upstreamBranchName,
					gitDirectoryJSONObject.getString("branch"))) {

				continue;
			}

			return gitDirectoryJSONObject.getString("name");
		}

		return null;
	}

	private static String _getGitHubAPIRateLimitStatusMessage(
		int limit, int remaining, long reset) {

		StringBuilder sb = new StringBuilder();

		sb.append(remaining);
		sb.append(" GitHub API calls out of ");
		sb.append(limit);
		sb.append(" remain. GitHub API call limit will reset in ");
		sb.append(toDurationString((1000 * reset) - getCurrentTimeMillis()));
		sb.append(".");

		return sb.toString();
	}

	private static synchronized JSONArray _getGitWorkingDirectoriesJSONArray() {
		if (_gitWorkingDirectoriesJSONArray != null) {
			return _gitWorkingDirectoriesJSONArray;
		}

		_gitWorkingDirectoriesJSONArray = new JSONArray();

		for (String url : URLS_GIT_WORKING_DIRECTORIES_JSON_DEFAULT) {
			JSONArray jsonArray;

			try {
				if (url.startsWith("file://")) {
					jsonArray = new JSONArray(
						read(new File(url.replace("file://", ""))));
				}
				else {
					jsonArray = toJSONArray(getLocalURL(url), false);
				}
			}
			catch (IOException ioException) {
				continue;
			}

			if (jsonArray == null) {
				continue;
			}

			for (int i = 0; i < jsonArray.length(); i++) {
				_gitWorkingDirectoriesJSONArray.put(jsonArray.get(i));
			}
		}

		return _gitWorkingDirectoriesJSONArray;
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
		String basePropertyName, Set<String> propertyNames) {

		Map<String, Set<String>> propertyOptRegexSets = new LinkedHashMap<>();

		List<String> orderedPropertyNames = new ArrayList<>(propertyNames);

		Collections.sort(orderedPropertyNames, new PropertyNameComparator());

		Set<String> basePropertyOptSet = _getPropertyOptSet(basePropertyName);

		for (String propertyName : orderedPropertyNames) {
			Set<String> propertyOptSet = _getPropertyOptSet(propertyName);

			propertyOptSet.removeAll(basePropertyOptSet);

			propertyOptRegexSets.put(propertyName, propertyOptSet);
		}

		return propertyOptRegexSets;
	}

	private static Set<String> _getPropertyOptSet(String propertyName) {
		Set<String> propertyOptSet = new LinkedHashSet<>();

		List<Integer> indices = new ArrayList<>();
		Stack<Integer> stack = new Stack<>();

		Integer start = null;

		for (int i = 0; i < propertyName.length(); i++) {
			char c = propertyName.charAt(i);

			if (c == '[') {
				stack.push(i);

				if (start == null) {
					start = i;

					indices.add(start);
				}
			}

			if (c == ']') {
				if (start == null) {
					continue;
				}

				stack.pop();

				if (stack.isEmpty()) {
					indices.add(i);

					start = null;
				}
			}
		}

		for (int i = 0; i < indices.size(); i++) {
			String opt = propertyName.substring(
				indices.get(i) + 1, indices.get(i + 1));

			opt = Pattern.quote(opt);

			propertyOptSet.add(opt.replaceAll("\\*", "\\\\E.+\\\\Q"));

			i++;
		}

		return propertyOptSet;
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

	private static final long _BYTES_GIGA = 1024 * 1024 * 1024;

	private static final long _BYTES_KILO = 1024;

	private static final long _BYTES_MEGA = 1024 * 1024;

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

	private static final Log _log = LogFactory.getLog(
		JenkinsResultsParserUtil.class);

	private static final Pattern _axisVariablePattern = Pattern.compile(
		".*AXIS_VARIABLE=(?<axisVariable>\\d+).*");
	private static final Pattern _buildIDPattern = Pattern.compile(
		"(?<cohortNumber>[\\d]{1})(?<masterNumber>[\\d]{2})" +
			"(?<jobID>[\\d]+)_(?<buildNumber>[\\d]+)");
	private static final Hashtable<Object, Object> _buildProperties =
		new Hashtable<>();
	private static String[] _buildPropertiesURLs;
	private static Boolean _ciNode;
	private static final Pattern _curlyBraceExpansionPattern = Pattern.compile(
		"\\{.*?\\}");
	private static Long _currentTimeMillisDelta;
	private static final Pattern _dockerFilePattern = Pattern.compile(
		".*FROM (?<dockerImageName>[^\\s]+)( AS builder)?\\n[\\s\\S]*");
	private static JSONArray _gitDirectoriesJSONArray;
	private static final DateFormat _gitHubDateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss");
	private static JSONArray _gitWorkingDirectoriesJSONArray;
	private static final Pattern _javaVersionPattern = Pattern.compile(
		"(\\d+\\.\\d+)");
	private static final Properties _jenkinsBuildProperties = new Properties();
	private static final Pattern _jenkinsMasterPattern = Pattern.compile(
		"(?<cohortName>test-\\d+)-\\d+");
	private static Hashtable<?, ?> _jenkinsProperties;
	private static final Pattern _jenkinsReportURLPattern = Pattern.compile(
		"(?<masterURL>http(?:|s)://test-\\d+-\\d+.*(\\.liferay\\.com)?)" +
			"userContent/+jobs/+(?<jobName>[^/]+)/builds/" +
				"(?<buildNumber>\\d+)/+jenkins-report\\.html");
	private static final Pattern _jenkinsSlavesPropertyNamePattern =
		Pattern.compile("master.slaves\\((.+)\\)");
	private static final Pattern _localURLAuthorityPattern1 = Pattern.compile(
		"http://((release|test)-[0-9]+)/([0-9]+)/");
	private static final Pattern _localURLAuthorityPattern2 = Pattern.compile(
		"http://(test-[0-9]+-[0-9]+)/");
	private static final Pattern _localURLAuthorityPattern3 = Pattern.compile(
		"https?://(mirrors/|mirrors.dlc.liferay.com/|mirrors.lax.liferay.com/" +
			")?((files|releases).liferay.com)");
	private static final Pattern _nestedPropertyPattern = Pattern.compile(
		"\\$\\{([^\\}]+)\\}");
	private static final Set<String> _redactTokens = new HashSet<>();
	private static final Pattern _remoteURLAuthorityPattern1 = Pattern.compile(
		"https://(test).liferay.com/([0-9]+)/");
	private static final Pattern _remoteURLAuthorityPattern2 = Pattern.compile(
		"https://(test-[0-9]+-[0-9]+).liferay.com/");
	private static final Pattern _remoteURLAuthorityPattern3 = Pattern.compile(
		"https?://(mirrors/|mirrors.dlc.liferay.com/|mirrors.lax.liferay.com/" +
			")?((files|releases).liferay.com)");
	private static final Pattern _shaPattern = Pattern.compile(
		"[0-9a-f]{7,40}");

	private static final File _sshDir = new File(
		JenkinsResultsParserUtil._userHomeDir, ".ssh") {

		{
			if (!exists()) {
				mkdirs();
			}
		}
	};

	private static final Set<String> _timeStamps = new HashSet<>();
	private static final Pattern _topLevelBuildURLPattern = Pattern.compile(
		"http(?:|s):\\/\\/test-(?<cohortNumber>[\\d]{1})-" +
			"(?<masterNumber>[\\d]{1,2}).*(?:|\\.liferay\\.com)\\/+job\\/+" +
				"(?<jobName>[\\w\\W]*?)\\/+(?<buildNumber>[0-9]*)");
	private static final Pattern _urlQueryStringPattern = Pattern.compile(
		"\\&??(\\w++)=([^\\&]*)");
	private static final File _userHomeDir = new File(
		System.getProperty("user.home"));

	static {
		try {
			_initializeRedactTokens();

			System.setErr(new SecurePrintStream(System.err));
			System.setOut(new SecurePrintStream(System.out));

			System.out.println("Securing standard error and out");
		}
		catch (Exception exception) {
		}
	}

}