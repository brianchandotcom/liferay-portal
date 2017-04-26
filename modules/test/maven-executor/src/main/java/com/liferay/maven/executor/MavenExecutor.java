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

package com.liferay.maven.executor;

import com.liferay.maven.executor.internal.FileUtil;
import com.liferay.maven.executor.internal.Validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.rules.ExternalResource;

/**
 * @author Andrea Di Giorgi
 */
public class MavenExecutor extends ExternalResource {

	public Result execute(File projectDir, String... args) throws Exception {
		Path mavenHomeDirPath = _checkMavenHomeDirPath();

		List<String> commands = new ArrayList<>();

		String mavenExecutableFileName = "mvn";

		boolean mavenDebug = isMavenDebug();

		if (mavenDebug) {
			mavenExecutableFileName = "mvnDebug";
		}

		if (_isWindows()) {
			mavenExecutableFileName += ".cmd";
		}

		Path mavenExecutablePath = mavenHomeDirPath.resolve(
			"bin/" + mavenExecutableFileName);

		commands.add(String.valueOf(mavenExecutablePath.toAbsolutePath()));

		commands.add("--errors");

		for (String arg : args) {
			commands.add(arg);
		}

		writeSettingsXmlFile();

		ProcessBuilder processBuilder = new ProcessBuilder(commands);

		processBuilder.directory(projectDir);

		if (mavenDebug) {
			processBuilder.inheritIO();
		}

		Map<String, String> environment = processBuilder.environment();

		environment.put(
			"M2_HOME", String.valueOf(mavenHomeDirPath.toAbsolutePath()));
		environment.put("MAVEN_OPTS", getMavenOpts());

		Process process = processBuilder.start();

		StringBuilder sb = new StringBuilder();

		_append(sb, process.getInputStream());
		_append(sb, process.getErrorStream());

		int exitCode = process.waitFor();

		return new Result(exitCode, sb.toString());
	}

	public String getHttpNonProxyHosts() {
		return System.getProperty("http.nonProxyHosts");
	}

	public String getHttpProxyHost() {
		return System.getProperty("http.proxyHost");
	}

	public String getHttpProxyPassword() {
		return System.getProperty("http.proxyPassword");
	}

	public int getHttpProxyPort() {
		String port = System.getProperty("http.proxyPort");

		if (Validator.isNull(port)) {
			return 0;
		}

		return Integer.parseInt(port);
	}

	public String getHttpProxyUser() {
		return System.getProperty("http.proxyUser");
	}

	public Path getMavenHomeDirPath() {
		return _checkMavenHomeDirPath();
	}

	public Path getMavenLocalRepositoryDirPath() {
		String dirName = System.getProperty("maven.repo.local");

		if (Validator.isNull(dirName)) {
			return null;
		}

		return Paths.get(dirName);
	}

	public String getRepositoryUrl() {
		return System.getProperty("repository.url");
	}

	public static class Result {

		public final int exitCode;
		public final String output;

		private Result(int exitCode, String output) {
			this.exitCode = exitCode;
			this.output = output;
		}

	}

	@Override
	protected void after() {
		if (_mavenHomeDirPath != null) {
			try {
				FileUtil.deleteDirectory(_mavenHomeDirPath);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	@Override
	protected void before() throws Throwable {
		String mavenDistributionFileName = getMavenDistributionFileName();

		if (Validator.isNull(mavenDistributionFileName)) {
			throw new IllegalArgumentException(
				"Please set the \"maven.distribution.file.name\" system " +
					"property");
		}

		_mavenHomeDirPath = Files.createTempDirectory("maven");

		FileUtil.unzip(mavenDistributionFileName, _mavenHomeDirPath, true);

		if (!_isWindows()) {
			Set<PosixFilePermission> posixFilePermissions =
				PosixFilePermissions.fromString("rwxr-xr-x");

			Files.setPosixFilePermissions(
				_mavenHomeDirPath.resolve("bin/mvn"), posixFilePermissions);
		}
	};

	protected String getMavenDistributionFileName() {
		return System.getProperty("maven.distribution.file.name");
	}

	protected String getMavenOpts() {
		return "-Dfile.encoding=UTF-8";
	}

	protected boolean isMavenDebug() {
		return Boolean.getBoolean("maven.debug");
	}

	protected void writeSettingsXmlFile() throws IOException {
		boolean localRepository = false;
		boolean mirrors = false;
		boolean proxies = false;

		Path localRepositoryDirPath = getMavenLocalRepositoryDirPath();

		if (localRepositoryDirPath != null) {
			localRepository = true;
		}

		String repositoryUrl = getRepositoryUrl();

		if (Validator.isNotNull(repositoryUrl)) {
			mirrors = true;
		}

		String httpProxyHost = getHttpProxyHost();
		int httpProxyPort = getHttpProxyPort();

		if (Validator.isNotNull(httpProxyHost) && (httpProxyPort > 0)) {
			proxies = true;
		}

		if (!localRepository && !mirrors && !proxies) {
			return;
		}

		String mavenSettingsXml = FileUtil.read(
			MavenExecutor.class, "dependencies/settings_xml.tmpl");

		if (localRepository) {
			mavenSettingsXml = mavenSettingsXml.replace(
				"[$LOCAL_REPOSITORY_DIR$]",
				FileUtil.getAbsolutePath(localRepositoryDirPath));
		}
		else {
			mavenSettingsXml = mavenSettingsXml.replaceFirst(
				"<localRepository>[\\s\\S]+<\\/localRepository>", "");
		}

		if (mirrors) {
			mavenSettingsXml = mavenSettingsXml.replace(
				"[$REPOSITORY_URL$]", repositoryUrl);
		}
		else {
			mavenSettingsXml = mavenSettingsXml.replaceFirst(
				"<mirrors>[\\s\\S]+<\\/mirrors>", "");
		}

		if (proxies) {
			mavenSettingsXml = mavenSettingsXml.replace(
				"[$HTTP_PROXY_HOST$]", httpProxyHost);
			mavenSettingsXml = mavenSettingsXml.replace(
				"[$HTTP_PROXY_PORT$]", String.valueOf(httpProxyPort));

			mavenSettingsXml = _replaceSettingsXmlElement(
				mavenSettingsXml, "[$HTTP_PROXY_USERNAME$]",
				getHttpProxyUser());
			mavenSettingsXml = _replaceSettingsXmlElement(
				mavenSettingsXml, "[$HTTP_PROXY_PASSWORD$]",
				getHttpProxyPassword());
			mavenSettingsXml = _replaceSettingsXmlElement(
				mavenSettingsXml, "[$HTTP_PROXY_NON_PROXY_HOSTS$]",
				getHttpNonProxyHosts());
		}
		else {
			mavenSettingsXml = mavenSettingsXml.replaceFirst(
				"<proxies>[\\s\\S]+<\\/proxies>", "");
		}

		Path settingsXmlPath = _mavenHomeDirPath.resolve("conf/settings.xml");

		Files.write(
			settingsXmlPath, mavenSettingsXml.getBytes(StandardCharsets.UTF_8));
	}

	private static void _append(StringBuilder sb, InputStream inputStream)
		throws IOException {

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				if (sb.length() > 0) {
					sb.append(System.lineSeparator());
				}

				sb.append(line);
			}
		}
	}

	private static boolean _isWindows() {
		if (File.pathSeparatorChar == ';') {
			return true;
		}

		return false;
	}

	private static String _replaceSettingsXmlElement(
		String settingsXml, String placeholder, String value) {

		if (Validator.isNotNull(value)) {
			settingsXml = settingsXml.replace(placeholder, value);
		}
		else {
			settingsXml = settingsXml.replaceFirst(
				"<\\w+>" + Pattern.quote(placeholder) + "<\\/\\w+>\\s+", "");
		}

		return settingsXml;
	}

	private Path _checkMavenHomeDirPath() {
		if (_mavenHomeDirPath == null) {
			throw new IllegalStateException(
				"The Maven home directory has not yet been created");
		}

		return _mavenHomeDirPath;
	}

	private Path _mavenHomeDirPath;

}