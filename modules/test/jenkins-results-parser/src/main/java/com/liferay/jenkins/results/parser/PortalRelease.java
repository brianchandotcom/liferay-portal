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

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;

/**
 * @author Michael Hashimoto
 */
public class PortalRelease {

	public PortalRelease(String portalVersion) {
		URL bundlesBaseURL = null;

		for (String baseURLString : _BASE_URL_STRINGS) {
			String bundlesBaseURLString =
				baseURLString + "/" + portalVersion.replaceAll("\\.u", "-u");

			String bundlesBaseURLContent = null;

			try {
				bundlesBaseURLContent = JenkinsResultsParserUtil.toString(
					bundlesBaseURLString + "/", true, 0, 5, 0);

				bundlesBaseURL = new URL(bundlesBaseURLString);

				break;
			}
			catch (IOException ioException) {
			}

			try {
				String xml = JenkinsResultsParserUtil.toString(
					baseURLString + "/");

				xml = xml.substring(xml.indexOf("<html>"));

				xml = xml.replaceAll("&nbsp;", "");
				xml = xml.replaceAll("<img[^>]+>", "");
				xml = xml.replaceAll("<hr>", "");

				Document document = Dom4JUtil.parse(xml);

				for (Node node : Dom4JUtil.getNodesByXPath(document, "//a")) {
					String text = node.getText();

					text = text.trim();
					text = text.replace("/", "");

					if (!text.startsWith(portalVersion + "-")) {
						continue;
					}

					bundlesBaseURLString = baseURLString + "/" + text;

					try {
						bundlesBaseURLContent =
							JenkinsResultsParserUtil.toString(
								bundlesBaseURLString + "/", true, 0, 5, 0);

						portalVersion = text;

						break;
					}
					catch (IOException ioException) {
					}
				}

				if (bundlesBaseURLContent != null) {
					bundlesBaseURL = new URL(bundlesBaseURLString);

					break;
				}
			}
			catch (DocumentException | IOException exception) {
				throw new RuntimeException(exception);
			}
		}

		if (bundlesBaseURL == null) {
			throw new RuntimeException(
				"Invalid portal version " + portalVersion);
		}

		_portalVersion = portalVersion;

		_bundlesBaseURL = _getLocalURL(bundlesBaseURL.toString());

		_initializeURLs();
	}

	public PortalRelease(URL bundleURL) {
		Matcher bundleURLMatcher = _bundleURLPattern.matcher(
			bundleURL.toString());

		if (!bundleURLMatcher.find()) {
			throw new RuntimeException("Invalid URL " + bundleURL);
		}

		String portalVersion = null;

		String bundleFileName = bundleURLMatcher.group("bundleFileName");

		Matcher bundleFileNameMatcher = _bundleFileNamePattern.matcher(
			bundleFileName);

		if (bundleFileNameMatcher.find()) {
			portalVersion = bundleFileNameMatcher.group("portalVersion");
		}

		String bundlesBaseURLString = bundleURLMatcher.group("bundlesBaseURL");

		if (portalVersion == null) {
			Matcher bundlesBaseURLMatcher = _bundlesBaseURLPattern.matcher(
				bundlesBaseURLString);

			if (!bundlesBaseURLMatcher.find()) {
				throw new RuntimeException(
					"Invalid bundle file name " + bundleFileName);
			}

			portalVersion = bundlesBaseURLMatcher.group("portalVersion");
		}

		_bundlesBaseURL = _getLocalURL(bundlesBaseURLString);
		_portalVersion = portalVersion;

		_initializeURLs();
	}

	public PortalRelease(URL bundlesBaseURL, String portalVersion) {
		if (bundlesBaseURL == null) {
			throw new RuntimeException("Bundles base URL is null");
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(portalVersion)) {
			throw new RuntimeException("Portal version is null or empty");
		}

		_portalVersion = portalVersion;

		String bundlesBaseURLString = bundlesBaseURL.toString();

		if (bundlesBaseURLString.endsWith("/")) {
			bundlesBaseURLString = bundlesBaseURLString.substring(
				0, bundlesBaseURLString.length() - 1);
		}

		_bundlesBaseURL = _getLocalURL(bundlesBaseURLString);

		_initializeURLs();
	}

	public URL getBundlesBaseLocalURL() {
		return _getLocalURL(_bundlesBaseURL.toString());
	}

	public URL getBundlesBaseURL() {
		return _getRemoteURL(_bundlesBaseURL.toString());
	}

	public URL getDependenciesLocalURL() {
		return _getLocalURL(_dependenciesURLString);
	}

	public URL getDependenciesURL() {
		return _getRemoteURL(_dependenciesURLString);
	}

	public URL getGlassFishLocalURL() {
		return _getLocalURL(_glassFishURLString);
	}

	public URL getGlassFishURL() {
		return _getRemoteURL(_glassFishURLString);
	}

	public String getHTMLReport() {
		StringBuilder sb = new StringBuilder();

		sb.append("<ul>");

		URL[] urls = {
			getDependenciesURL(), getGlassFishURL(), getJBossURL(),
			getOSGiURL(), getPortalWarURL(), getSQLURL(), getTomcatURL(),
			getToolsURL(), getWildFlyURL()
		};

		for (URL url : urls) {
			if (url == null) {
				continue;
			}

			String urlString = url.toString();

			sb.append("<li><a href=\"");
			sb.append(urlString);
			sb.append("\">");
			sb.append(urlString.replaceAll(".+/([^/]+)", "$1"));
			sb.append("</a></li>");
		}

		sb.append("</ul>");

		return sb.toString();
	}

	public URL getJBossLocalURL() {
		return _getLocalURL(_jbossURLString);
	}

	public URL getJBossURL() {
		return _getRemoteURL(_jbossURLString);
	}

	public URL getOSGiLocalURL() {
		return _getLocalURL(_osgiURLString);
	}

	public URL getOSGiURL() {
		return _getRemoteURL(_osgiURLString);
	}

	public URL getPluginsWarLocalURL() {
		return _getLocalURL(_pluginsWarURLString);
	}

	public URL getPluginsWarURL() {
		return _getRemoteURL(_pluginsWarURLString);
	}

	public String getPortalUpstreamBranchName() {
		try {
			String portalUpstreamBranchName =
				JenkinsResultsParserUtil.getProperty(
					JenkinsResultsParserUtil.getBuildProperties(),
					"portal.branch.name", getPortalVersionPropertyOption());

			if (!JenkinsResultsParserUtil.isNullOrEmpty(
					portalUpstreamBranchName)) {

				return portalUpstreamBranchName;
			}
		}
		catch (IOException ioException) {
		}

		Matcher matcher = _portalVersionPattern.matcher(_portalVersion);

		if (!matcher.find()) {
			return "master";
		}

		StringBuilder sb = new StringBuilder();

		String majorVersion = matcher.group("majorVersion");

		if (majorVersion.equals("6")) {
			sb.append("ee-");
		}

		sb.append(majorVersion);
		sb.append(".");
		sb.append(matcher.group("minorVersion"));
		sb.append(".x");

		return sb.toString();
	}

	public String getPortalVersion() {
		return _portalVersion;
	}

	public String getPortalVersionPropertyOption() {
		Matcher matcher = _portalVersionPattern.matcher(_portalVersion);

		if (!matcher.find()) {
			return _portalVersion;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(matcher.group("majorVersion"));
		sb.append(".");
		sb.append(matcher.group("minorVersion"));
		sb.append(".");
		sb.append(matcher.group("fixVersion"));

		String updateVersion = matcher.group("updateVersion");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(updateVersion)) {
			sb.append(".");

			String updatePrefix = matcher.group("updatePrefix");

			if (!JenkinsResultsParserUtil.isNullOrEmpty(updatePrefix)) {
				sb.append(updatePrefix);
			}

			sb.append(updateVersion);
		}

		return sb.toString();
	}

	public URL getPortalWarLocalURL() {
		return _getLocalURL(_portalWarURLString);
	}

	public URL getPortalWarURL() {
		return _getRemoteURL(_portalWarURLString);
	}

	public URL getSQLLocalURL() {
		return _getLocalURL(_sqlURLString);
	}

	public URL getSQLURL() {
		return _getRemoteURL(_sqlURLString);
	}

	public URL getTomcatLocalURL() {
		return _getLocalURL(_tomcatURLString);
	}

	public URL getTomcatURL() {
		return _getRemoteURL(_tomcatURLString);
	}

	public URL getToolsLocalURL() {
		return _getLocalURL(_toolsURLString);
	}

	public URL getToolsURL() {
		return _getRemoteURL(_toolsURLString);
	}

	public URL getWildFlyLocalURL() {
		return _getLocalURL(_wildFlyURLString);
	}

	public URL getWildFlyURL() {
		return _getRemoteURL(_wildFlyURLString);
	}

	public void setDependenciesURL(URL dependenciesURL) {
		if (dependenciesURL == null) {
			_dependenciesURLString = null;

			return;
		}

		_dependenciesURLString = JenkinsResultsParserUtil.getLocalURL(
			dependenciesURL.toString());
	}

	public void setGlassFishURL(URL glassFishURL) {
		if (glassFishURL == null) {
			_glassFishURLString = null;

			return;
		}

		_glassFishURLString = JenkinsResultsParserUtil.getLocalURL(
			glassFishURL.toString());
	}

	public void setJBossURL(URL jbossURL) {
		if (jbossURL == null) {
			_jbossURLString = null;

			return;
		}

		_jbossURLString = JenkinsResultsParserUtil.getLocalURL(
			jbossURL.toString());
	}

	public void setOSGiURL(URL osgiURL) {
		if (osgiURL == null) {
			_osgiURLString = null;

			return;
		}

		_osgiURLString = JenkinsResultsParserUtil.getLocalURL(
			osgiURL.toString());
	}

	public void setPortalWarURL(URL portalWarURL) {
		if (portalWarURL == null) {
			_portalWarURLString = null;

			return;
		}

		_portalWarURLString = JenkinsResultsParserUtil.getLocalURL(
			portalWarURL.toString());
	}

	public void setSQLURL(URL sqlURL) {
		if (sqlURL == null) {
			_sqlURLString = null;

			return;
		}

		_sqlURLString = JenkinsResultsParserUtil.getLocalURL(sqlURL.toString());
	}

	public void setTomcatURL(URL tomcatURL) {
		if (tomcatURL == null) {
			_tomcatURLString = null;

			return;
		}

		_tomcatURLString = JenkinsResultsParserUtil.getLocalURL(
			tomcatURL.toString());
	}

	public void setToolsURL(URL toolsURL) {
		if (toolsURL == null) {
			_toolsURLString = null;

			return;
		}

		_toolsURLString = JenkinsResultsParserUtil.getLocalURL(
			toolsURL.toString());
	}

	public void setWildFlyURL(URL wildFlyURL) {
		if (wildFlyURL == null) {
			_wildFlyURLString = null;

			return;
		}

		_wildFlyURLString = JenkinsResultsParserUtil.getLocalURL(
			wildFlyURL.toString());
	}

	private URL _getLocalURL(String urlString) {
		if (urlString == null) {
			return null;
		}

		try {
			return new URL(
				JenkinsResultsParserUtil.getLocalURL(
					urlString.replaceAll("([^:])//", "$1/")));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private URL _getRemoteURL(String urlString) {
		if (urlString == null) {
			return null;
		}

		try {
			return new URL(
				JenkinsResultsParserUtil.getRemoteURL(
					urlString.replaceAll("([^:])//", "$1/")));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private String _getURLString(
		String bundlesBaseURLContent, Pattern pattern) {

		Matcher matcher = pattern.matcher(bundlesBaseURLContent);

		if (!matcher.find()) {
			return null;
		}

		return getBundlesBaseLocalURL() + "/" + matcher.group("fileName");
	}

	private String _getURLStringFromBuildProperties(String basePropertyName) {
		try {
			String urlString = JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getBuildProperties(), basePropertyName,
				getPortalVersionPropertyOption());

			String portalBuildProfile = "portal";

			Matcher matcher = _portalVersionPattern.matcher(_portalVersion);

			if (matcher.find()) {
				String fixVersion = matcher.group("fixVersion");

				if (fixVersion.length() >= 2) {
					portalBuildProfile = "dxp";
				}
			}

			if (JenkinsResultsParserUtil.isNullOrEmpty(urlString)) {
				urlString = JenkinsResultsParserUtil.getProperty(
					JenkinsResultsParserUtil.getBuildProperties(),
					basePropertyName, getPortalUpstreamBranchName(),
					portalBuildProfile);
			}

			if (!JenkinsResultsParserUtil.isNullOrEmpty(urlString)) {
				return urlString;
			}

			return null;
		}
		catch (IOException ioException) {
			return null;
		}
	}

	private void _initializeURLs() {
		String dependenciesURLString = _getURLStringFromBuildProperties(
			"portal.dependencies.zip.url");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(dependenciesURLString)) {
			_dependenciesURLString = dependenciesURLString;
		}

		String glassFishURLString = _getURLStringFromBuildProperties(
			"portal.bundle.glassfish");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(glassFishURLString)) {
			_glassFishURLString = glassFishURLString;
		}

		String jbossURLString = _getURLStringFromBuildProperties(
			"portal.bundle.jboss");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(jbossURLString)) {
			_jbossURLString = jbossURLString;
		}

		String osgiURLString = _getURLStringFromBuildProperties(
			"portal.osgi.zip.url");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(osgiURLString)) {
			_osgiURLString = osgiURLString;
		}

		String pluginsWarURLString = _getURLStringFromBuildProperties(
			"plugins.war.zip.url");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(pluginsWarURLString)) {
			_pluginsWarURLString = pluginsWarURLString;
		}

		String portalWarURLString = _getURLStringFromBuildProperties(
			"portal.war.url");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(portalWarURLString)) {
			_portalWarURLString = portalWarURLString;
		}

		String sqlURLString = _getURLStringFromBuildProperties(
			"portal.sql.zip.url");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(sqlURLString)) {
			_sqlURLString = sqlURLString;
		}

		String tomcatURLString = _getURLStringFromBuildProperties(
			"portal.bundle.tomcat");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(tomcatURLString)) {
			_tomcatURLString = tomcatURLString;
		}

		String toolsURLString = _getURLStringFromBuildProperties(
			"portal.tools.zip.url");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(toolsURLString)) {
			_toolsURLString = toolsURLString;
		}

		String wildFlyURLString = _getURLStringFromBuildProperties(
			"portal.bundle.wildfly");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(wildFlyURLString)) {
			_wildFlyURLString = wildFlyURLString;
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(_tomcatURLString)) {
			return;
		}

		String bundlesBaseURLContent = null;

		try {
			bundlesBaseURLContent = JenkinsResultsParserUtil.toString(
				getBundlesBaseLocalURL() + "/", false, 0, 5, 0);
		}
		catch (IOException ioException) {
			return;
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(bundlesBaseURLContent)) {
			return;
		}

		_dependenciesURLString = _getURLString(
			bundlesBaseURLContent, _dependenciesFileNamePattern);
		_glassFishURLString = _getURLString(
			bundlesBaseURLContent, _glassFishFileNamePattern);
		_jbossURLString = _getURLString(
			bundlesBaseURLContent, _jbossFileNamePattern);
		_osgiURLString = _getURLString(
			bundlesBaseURLContent, _osgiFileNamePattern);
		_portalWarURLString = _getURLString(
			bundlesBaseURLContent, _portalWarFileNamePattern);
		_sqlURLString = _getURLString(
			bundlesBaseURLContent, _sqlFileNamePattern);
		_tomcatURLString = _getURLString(
			bundlesBaseURLContent, _tomcatFileNamePattern);
		_toolsURLString = _getURLString(
			bundlesBaseURLContent, _toolsFileNamePattern);
		_wildFlyURLString = _getURLString(
			bundlesBaseURLContent, _wildFlyFileNamePattern);
	}

	private static final String[] _BASE_URL_STRINGS = {
		"http://mirrors.lax.liferay.com/releases.liferay.com/portal",
		"http://mirrors.lax.liferay.com/files.liferay.com/private/ee/portal",
		"https://releases.liferay.com/portal",
		"https://files.liferay.com/private/ee/portal"
	};

	private static final String _PORTAL_VERSION_REGEX =
		"(?<portalVersion>\\d\\.([u\\d\\.]+)(-ee)?(\\-(ep|ga|rc|sp)\\d+)?)";

	private static final Pattern _bundleFileNamePattern = Pattern.compile(
		".+\\-" + _PORTAL_VERSION_REGEX + ".*\\.(7z|tar.gz|zip)");
	private static final Pattern _bundlesBaseURLPattern = Pattern.compile(
		"https?://.+/" + _PORTAL_VERSION_REGEX);
	private static final Pattern _bundleURLPattern = Pattern.compile(
		"(?<bundlesBaseURL>https?://.+)/(?<bundleFileName>[^\\/]+" +
			"\\.(7z|tar.gz|zip))");
	private static final Pattern _dependenciesFileNamePattern = Pattern.compile(
		"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-dependencies-[^\\\"]+" +
			"\\.zip)\\\"");
	private static final Pattern _glassFishFileNamePattern = Pattern.compile(
		"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-glassfish-[^\\\"]+" +
			"\\.(7z|tar.gz|zip))\\\"");
	private static final Pattern _jbossFileNamePattern = Pattern.compile(
		"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-jboss-[^\\\"]+" +
			"\\.(7z|tar.gz|zip))\\\"");
	private static final Pattern _osgiFileNamePattern = Pattern.compile(
		"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-osgi-[^\\\"]+\\.zip)" +
			"\\\"");
	private static final Pattern _portalVersionPattern = Pattern.compile(
		"(?<majorVersion>\\d)\\.(?<minorVersion>\\d)\\.(?<fixVersion>\\d+)" +
			"([-\\.](?<updatePrefix>u)?(?<updateVersion>\\d+))?.*");
	private static final Pattern _portalWarFileNamePattern = Pattern.compile(
		"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+\\.war)\\\"");
	private static final Pattern _sqlFileNamePattern = Pattern.compile(
		"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-sql-[^\\\"]+" +
			"\\.zip)\\\"");
	private static final Pattern _tomcatFileNamePattern = Pattern.compile(
		"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-tomcat-[^\\\"]+" +
			"\\.(7z|tar.gz|zip))\\\"");
	private static final Pattern _toolsFileNamePattern = Pattern.compile(
		"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-tools-[^\\\"]+" +
			"\\.zip)\\\"");
	private static final Pattern _wildFlyFileNamePattern = Pattern.compile(
		"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-wildfly-[^\\\"]+" +
			"\\.(7z|tar.gz|zip))\\\"");

	private final URL _bundlesBaseURL;
	private String _dependenciesURLString;
	private String _glassFishURLString;
	private String _jbossURLString;
	private String _osgiURLString;
	private String _pluginsWarURLString;
	private final String _portalVersion;
	private String _portalWarURLString;
	private String _sqlURLString;
	private String _tomcatURLString;
	private String _toolsURLString;
	private String _wildFlyURLString;

}