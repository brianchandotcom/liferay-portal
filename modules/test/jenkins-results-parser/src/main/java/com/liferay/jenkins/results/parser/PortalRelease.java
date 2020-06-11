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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class PortalRelease {

	public PortalRelease(String portalVersion) {
		_portalVersion = portalVersion;

		String bundlesBaseURLContent = null;
		String bundlesBaseURLString = null;

		for (String baseURLString : _BASE_URL_STRINGS) {
			bundlesBaseURLString = baseURLString + "/" + _portalVersion;

			try {
				bundlesBaseURLContent = JenkinsResultsParserUtil.toString(
					bundlesBaseURLString + "/", true, 0, 5, 0);

				break;
			}
			catch (IOException ioException) {
			}
		}

		if ((bundlesBaseURLString == null) || (bundlesBaseURLContent == null)) {
			throw new RuntimeException(
				"Invalid portal version " + portalVersion);
		}

		_bundlesBaseURLContent = bundlesBaseURLContent;
		_bundlesBaseURLString = _getNonMirrorsURLString(bundlesBaseURLString);
	}

	public PortalRelease(URL bundleURL) {
		Matcher bundleURLMatcher = _bundleURLPattern.matcher(
			bundleURL.toString());

		if (!bundleURLMatcher.find()) {
			throw new RuntimeException("Invalid URL " + bundleURL);
		}

		String portalVersion = null;

		String bundlesBaseURLString = bundleURLMatcher.group("bundlesBaseURL");

		Matcher bundlesBaseURLMatcher = _bundlesBaseURLPattern.matcher(
			bundlesBaseURLString);

		if (bundlesBaseURLMatcher.find()) {
			portalVersion = bundlesBaseURLMatcher.group("portalVersion");
		}

		if (portalVersion == null) {
			String bundleFileName = bundleURLMatcher.group("bundleFileName");

			Matcher bundleFileNameMatcher = _bundleFileNamePattern.matcher(
				bundleFileName);

			if (!bundleFileNameMatcher.find()) {
				throw new RuntimeException(
					"Invalid bundle file name " + bundleFileName);
			}

			portalVersion = bundleFileNameMatcher.group("portalVersion");
		}

		_portalVersion = portalVersion;

		String bundlesBaseURLContent = null;

		String[] bundlesBaseURLStrings = {
			bundlesBaseURLString, _getMirrorsURLString(bundlesBaseURLString),
			JenkinsResultsParserUtil.getLocalURL(bundlesBaseURLString)
		};

		for (String bundlesBaseURLStringCandidate : bundlesBaseURLStrings) {
			try {
				bundlesBaseURLContent = JenkinsResultsParserUtil.toString(
					bundlesBaseURLStringCandidate + "/", true, 0, 5, 0);
			}
			catch (IOException ioException) {
				continue;
			}

			break;
		}

		if (bundlesBaseURLContent == null) {
			throw new RuntimeException("Invalid URL " + bundlesBaseURLString);
		}

		bundlesBaseURLString = JenkinsResultsParserUtil.getRemoteURL(
			bundlesBaseURLString);
		bundlesBaseURLString = _getNonMirrorsURLString(bundlesBaseURLString);

		_bundlesBaseURLContent = bundlesBaseURLContent;
		_bundlesBaseURLString = bundlesBaseURLString;
	}

	public URL getDependenciesURL() {
		return _getURL(_dependenciesFileNamePattern);
	}

	public URL getGlassFishURL() {
		return _getURL(_glassFishFileNamePattern);
	}

	public URL getJBossURL() {
		return _getURL(_jbossFileNamePattern);
	}

	public URL getOSGiURL() {
		return _getURL(_osgiFileNamePattern);
	}

	public String getPortalVersion() {
		return _portalVersion;
	}

	public URL getPortalWarURL() {
		return _getURL(_portalWarFileNamePattern);
	}

	public URL getSQLURL() {
		return _getURL(_sqlFileNamePattern);
	}

	public URL getTomcatURL() {
		return _getURL(_tomcatFileNamePattern);
	}

	public URL getToolsURL() {
		return _getURL(_toolsFileNamePattern);
	}

	public URL getWildFlyURL() {
		return _getURL(_wildFlyFileNamePattern);
	}

	private static String _getMirrorsURLString(String urlString) {
		if (urlString == null) {
			throw new NullPointerException();
		}

		urlString = _getNonMirrorsURLString(urlString);

		return urlString.replaceAll(
			"https?:\\/\\/", "http://mirrors.lax.liferay.com/");
	}

	private static String _getNonMirrorsURLString(String urlString) {
		urlString = urlString.replace("http://mirrors/", "https://");
		urlString = urlString.replace(
			"http://mirrors.lax.liferay.com/", "https://");

		urlString = urlString.replace(
			"https://release-1/1/", "http://release-1/1/");

		return urlString;
	}

	private URL _getURL(Pattern pattern) {
		Matcher matcher = pattern.matcher(_bundlesBaseURLContent);

		if (!matcher.find()) {
			return null;
		}

		try {
			return new URL(
				_getNonMirrorsURLString(
					_bundlesBaseURLString + "/" + matcher.group("fileName")));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private static final String[] _BASE_URL_STRINGS = {
		"http://mirrors.lax.liferay.com/releases.liferay.com/portal",
		"http://mirrors.lax.liferay.com/files.liferay.com/private/ee/portal",
		"https://releases.liferay.com/portal",
		"https://files.liferay.com/private/ee/portal"
	};

	private static final String _PORTAL_VERSION_REGEX =
		"(?<portalVersion>\\d\\.([\\d\\.]+)(\\-\\w\\w\\d?)?)";

	private static final Pattern _bundleFileNamePattern = Pattern.compile(
		".+\\-" + _PORTAL_VERSION_REGEX + ".+\\.(7z|tar.gz|zip)");
	private static final Pattern _bundlesBaseURLPattern = Pattern.compile(
		"https?://.+/" + _PORTAL_VERSION_REGEX);
	private static final Pattern _bundleURLPattern = Pattern.compile(
		"(?<bundlesBaseURL>https?://.+)/(?<bundleFileName>[^\\/]+" +
			"\\.(7z|tar.gz|zip))");
	private static final Pattern _dependenciesFileNamePattern = Pattern.compile(
		"href=\\\"(?<fileName>liferay-[^\\\"]+-dependencies-[^\\\"]+" +
			"\\.zip)\\\"");
	private static final Pattern _glassFishFileNamePattern = Pattern.compile(
		"href=\\\"(?<fileName>liferay-[^\\\"]+-glassfish-[^\\\"]+" +
			"\\.(7z|zip))\\\"");
	private static final Pattern _jbossFileNamePattern = Pattern.compile(
		"href=\\\"(?<fileName>liferay-[^\\\"]+-jboss-[^\\\"]+\\.(7z|zip))\\\"");
	private static final Pattern _osgiFileNamePattern = Pattern.compile(
		"href=\\\"(?<fileName>liferay-[^\\\"]+-osgi-[^\\\"]+\\.zip)\\\"");
	private static final Pattern _portalWarFileNamePattern = Pattern.compile(
		"href=\\\"(?<fileName>liferay-[^\\\"]+-portal-[^\\\"]+\\.war)\\\"");
	private static final Pattern _sqlFileNamePattern = Pattern.compile(
		"href=\\\"(?<fileName>liferay-[^\\\"]+-sql-[^\\\"]+\\.zip)\\\"");
	private static final Pattern _tomcatFileNamePattern = Pattern.compile(
		"href=\\\"(?<fileName>liferay-[^\\\"]+-tomcat-[^\\\"]+" +
			"\\.(7z|zip))\\\"");
	private static final Pattern _toolsFileNamePattern = Pattern.compile(
		"href=\\\"(?<fileName>liferay-[^\\\"]+-tools-[^\\\"]+\\.zip)\\\"");
	private static final Pattern _wildFlyFileNamePattern = Pattern.compile(
		"href=\\\"(?<fileName>liferay-[^\\\"]+-wildfly-[^\\\"]+" +
			"\\.(7z|zip))\\\"");

	private final String _bundlesBaseURLContent;
	private final String _bundlesBaseURLString;
	private final String _portalVersion;

}