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

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class PortalFixpackRelease {

	public PortalFixpackRelease(
		String portalFixpackVersion, PortalRelease portalRelease) {

		_portalFixpackVersion = portalFixpackVersion;
		_portalRelease = portalRelease;

		try {
			String portalVersion = _portalRelease.getPortalVersion();
			String portalFixpackType = "dxp";

			if (portalVersion.contains("7.0")) {
				portalFixpackType = "de";
			}

			String portalBaseVersion = portalVersion.replaceAll(
				"(\\d)\\.(\\d)\\.(\\d\\d).*", "$1.$2.$3");

			String portalBaseBuildVersion = portalBaseVersion.replaceAll(
				"\\.", "");

			_portalFixpackURL = new URL(
				JenkinsResultsParserUtil.combine(
					"https://files.liferay.com/private/ee/fix-packs/",
					portalBaseVersion, "/", portalFixpackType,
					"/liferay-fix-pack-", portalFixpackType, "-",
					_portalFixpackVersion, "-", portalBaseBuildVersion,
					".zip"));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	public PortalFixpackRelease(
		String portalFixpackVersion, String portalVersion) {

		this(portalFixpackVersion, new PortalRelease(portalVersion));
	}

	public PortalFixpackRelease(URL portalFixpackURL) {
		Matcher fixpackURLMatcher = _fixpackURLPattern.matcher(
			portalFixpackURL.toString());

		if (!fixpackURLMatcher.find()) {
			throw new RuntimeException("Invalid URL " + portalFixpackURL);
		}

		String fixpackFileName = fixpackURLMatcher.group("fixpackFileName");

		Matcher fixpackFileNameMatcher = _fixpackFileNamePattern.matcher(
			fixpackFileName);

		if (!fixpackFileNameMatcher.find()) {
			throw new RuntimeException(
				"Invalid fixpack file name " + fixpackFileName);
		}

		_portalFixpackVersion = fixpackFileNameMatcher.group(
			"portalFixpackVersion");

		_portalRelease = new PortalRelease(
			_getPortalVersion(
				fixpackFileNameMatcher.group("portalBuildVersion"),
				_portalFixpackVersion));

		String portalFixpackURLString = portalFixpackURL.toString();

		portalFixpackURLString = portalFixpackURLString.replace(
			"http://mirrors/", "https://");
		portalFixpackURLString = portalFixpackURLString.replace(
			"http://mirrors.lax.liferay.com/", "https://");

		try {
			_portalFixpackURL = new URL(portalFixpackURLString);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	public String getHTMLReport() {
		StringBuilder sb = new StringBuilder();

		String urlString = String.valueOf(getPortalFixpackURL());

		sb.append("<ul>");

		sb.append("<li><a href=\"");
		sb.append(urlString);
		sb.append("\">");
		sb.append(urlString.replaceAll(".+/([^/]+)", "$1"));
		sb.append("</a></li>");

		sb.append("</ul>");

		return sb.toString();
	}

	public URL getPortalFixpackURL() {
		return _portalFixpackURL;
	}

	public String getPortalFixpackVersion() {
		return _portalFixpackVersion;
	}

	public PortalRelease getPortalRelease() {
		return _portalRelease;
	}

	private String _getPortalVersion(
		String portalBuildVersion, String portalFixpackVersion) {

		String basePortalVersionRegex = "(\\d)(\\d)(\\d\\d)";

		StringBuilder sb = new StringBuilder();

		sb.append(portalBuildVersion.replaceAll(basePortalVersionRegex, "$1"));
		sb.append(".");
		sb.append(portalBuildVersion.replaceAll(basePortalVersionRegex, "$2"));
		sb.append(".");
		sb.append(portalBuildVersion.replaceAll(basePortalVersionRegex, "$3"));

		String basePortalVersion = sb.toString();

		String portalVersion = basePortalVersion;

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			int latestPortalFixVersion = -1;

			for (String propertyName : buildProperties.stringPropertyNames()) {
				String propertyNameRegex =
					_FIXPACK_VERSION_PROPERTY_NAME +
						"\\[\\d\\.\\d\\.\\d{2}\\.(\\d+)\\]";

				if (!propertyName.matches(propertyNameRegex)) {
					continue;
				}

				int portalFixVersion = Integer.valueOf(
					propertyName.replaceAll(propertyNameRegex, "$1"));

				if (portalFixVersion > latestPortalFixVersion) {
					latestPortalFixVersion = portalFixVersion;
				}
			}

			if (latestPortalFixVersion == -1) {
				return portalVersion;
			}

			for (int i = 1; i <= latestPortalFixVersion; i++) {
				String portalVersionCandidate = basePortalVersion + "." + i;

				String portalReleaseFixpackVersion =
					JenkinsResultsParserUtil.getProperty(
						buildProperties, _FIXPACK_VERSION_PROPERTY_NAME,
						portalVersionCandidate);

				if ((portalReleaseFixpackVersion == null) ||
					portalReleaseFixpackVersion.isEmpty()) {

					continue;
				}

				if (Integer.valueOf(portalFixpackVersion) >= Integer.valueOf(
						portalReleaseFixpackVersion)) {

					portalVersion = portalVersionCandidate;
				}
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return portalVersion;
	}

	private static final String _FIXPACK_VERSION_PROPERTY_NAME =
		"portal.release.fixpack.version";

	private static final Pattern _fixpackFileNamePattern = Pattern.compile(
		"liferay-fix-pack-(de|dxp|portal)-(?<portalFixpackVersion>\\d+)-" +
			"(?<portalBuildVersion>\\d+)(-build\\d*)?(-src)?.zip");
	private static final Pattern _fixpackURLPattern = Pattern.compile(
		"https?://.+/(?<fixpackFileName>[^/]+.zip)");

	private final URL _portalFixpackURL;
	private final String _portalFixpackVersion;
	private final PortalRelease _portalRelease;

}