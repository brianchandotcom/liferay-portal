/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public String getHTMLReport() {
		StringBuilder sb = new StringBuilder();

		sb.append("<ul>");

		URL[] urls = {
			getPluginsWarZipURL(), getPortalBundleGlassFishURL(),
			getPortalBundleJBossURL(), getPortalBundleTomcatURL(),
			getPortalBundleWildFlyURL(), getPortalDependenciesZipURL(),
			getPortalOSGiZipURL(), getPortalSQLZipURL(), getPortalToolsZipURL(),
			getPortalWarURL()
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

	public URL getPluginsWarZipLocalURL() {
		return _getLocalURL(_pluginsWarZipURLString);
	}

	public URL getPluginsWarZipURL() {
		return _getRemoteURL(_pluginsWarZipURLString);
	}

	public URL getPortalBundleGlassFishLocalURL() {
		return _getLocalURL(_portalBundleGlassFishURLString);
	}

	public URL getPortalBundleGlassFishURL() {
		return _getRemoteURL(_portalBundleGlassFishURLString);
	}

	public URL getPortalBundleJBossLocalURL() {
		return _getLocalURL(_portalBundleJBossURLString);
	}

	public URL getPortalBundleJBossURL() {
		return _getRemoteURL(_portalBundleJBossURLString);
	}

	public URL getPortalBundleTomcatLocalURL() {
		return _getLocalURL(_portalBundleTomcatURLString);
	}

	public URL getPortalBundleTomcatURL() {
		return _getRemoteURL(_portalBundleTomcatURLString);
	}

	public URL getPortalBundleWildFlyLocalURL() {
		return _getLocalURL(_portalBundleWildFlyURLString);
	}

	public URL getPortalBundleWildFlyURL() {
		return _getRemoteURL(_portalBundleWildFlyURLString);
	}

	public URL getPortalDependenciesZipLocalURL() {
		return _getLocalURL(_portalDependenciesZipURLString);
	}

	public URL getPortalDependenciesZipURL() {
		return _getRemoteURL(_portalDependenciesZipURLString);
	}

	public URL getPortalOSGiZipLocalURL() {
		return _getLocalURL(_portalOSGiZipURLString);
	}

	public URL getPortalOSGiZipURL() {
		return _getRemoteURL(_portalOSGiZipURLString);
	}

	public URL getPortalSQLZipLocalURL() {
		return _getLocalURL(_portalSQLZipURLString);
	}

	public URL getPortalSQLZipURL() {
		return _getRemoteURL(_portalSQLZipURLString);
	}

	public URL getPortalToolsZipLocalURL() {
		return _getLocalURL(_portalToolsZipURLString);
	}

	public URL getPortalToolsZipURL() {
		return _getRemoteURL(_portalToolsZipURLString);
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

		String dxpVersion = matcher.group("dxpVersion");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(dxpVersion)) {
			sb.append("-dxp-");
			sb.append(dxpVersion);
		}

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

	public void setPluginsWarZipURL(URL pluginsWarZipURL) {
		if (pluginsWarZipURL == null) {
			_pluginsWarZipURLString = null;

			return;
		}

		_pluginsWarZipURLString = JenkinsResultsParserUtil.getLocalURL(
			pluginsWarZipURL.toString());
	}

	public void setPortalBundleGlassFishURL(URL portalBundleGlassFishURL) {
		if (portalBundleGlassFishURL == null) {
			_portalBundleGlassFishURLString = null;

			return;
		}

		_portalBundleGlassFishURLString = JenkinsResultsParserUtil.getLocalURL(
			portalBundleGlassFishURL.toString());
	}

	public void setPortalBundleJBossURL(URL portalBundleJBossURL) {
		if (portalBundleJBossURL == null) {
			_portalBundleJBossURLString = null;

			return;
		}

		_portalBundleJBossURLString = JenkinsResultsParserUtil.getLocalURL(
			portalBundleJBossURL.toString());
	}

	public void setPortalBundleTomcatURL(URL portalBundleTomcatURL) {
		if (portalBundleTomcatURL == null) {
			_portalBundleTomcatURLString = null;

			return;
		}

		_portalBundleTomcatURLString = JenkinsResultsParserUtil.getLocalURL(
			portalBundleTomcatURL.toString());
	}

	public void setPortalBundleWildFlyURL(URL portalBundleWildFlyURL) {
		if (portalBundleWildFlyURL == null) {
			_portalBundleWildFlyURLString = null;

			return;
		}

		_portalBundleWildFlyURLString = JenkinsResultsParserUtil.getLocalURL(
			portalBundleWildFlyURL.toString());
	}

	public void setPortalDependenciesZipURL(URL portalDependenciesZipURL) {
		if (portalDependenciesZipURL == null) {
			_portalDependenciesZipURLString = null;

			return;
		}

		_portalDependenciesZipURLString = JenkinsResultsParserUtil.getLocalURL(
			portalDependenciesZipURL.toString());
	}

	public void setPortalOSGiZipURL(URL portalOSGiZipURL) {
		if (portalOSGiZipURL == null) {
			_portalOSGiZipURLString = null;

			return;
		}

		_portalOSGiZipURLString = JenkinsResultsParserUtil.getLocalURL(
			portalOSGiZipURL.toString());
	}

	public void setPortalSQLZipURL(URL portalSQLZipURL) {
		if (portalSQLZipURL == null) {
			_portalSQLZipURLString = null;

			return;
		}

		_portalSQLZipURLString = JenkinsResultsParserUtil.getLocalURL(
			portalSQLZipURL.toString());
	}

	public void setPortalToolsZipURL(URL portalToolsZipURL) {
		if (portalToolsZipURL == null) {
			_portalToolsZipURLString = null;

			return;
		}

		_portalToolsZipURLString = JenkinsResultsParserUtil.getLocalURL(
			portalToolsZipURL.toString());
	}

	public void setPortalWarURL(URL portalWarURL) {
		if (portalWarURL == null) {
			_portalWarURLString = null;

			return;
		}

		_portalWarURLString = JenkinsResultsParserUtil.getLocalURL(
			portalWarURL.toString());
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
		String pluginsWarZipURLString = _getURLStringFromBuildProperties(
			"plugins.war.zip.url");

		if (JenkinsResultsParserUtil.isURL(pluginsWarZipURLString)) {
			_pluginsWarZipURLString = pluginsWarZipURLString;
		}

		String portalBundleGlassFishURLString =
			_getURLStringFromBuildProperties("portal.bundle.glassfish");

		if (JenkinsResultsParserUtil.isURL(portalBundleGlassFishURLString)) {
			_portalBundleGlassFishURLString = portalBundleGlassFishURLString;
		}

		String portalBundleJBossURLString = _getURLStringFromBuildProperties(
			"portal.bundle.jboss");

		if (JenkinsResultsParserUtil.isURL(portalBundleJBossURLString)) {
			_portalBundleJBossURLString = portalBundleJBossURLString;
		}

		String portalBundleTomcatURLString = _getURLStringFromBuildProperties(
			"portal.bundle.tomcat");

		if (JenkinsResultsParserUtil.isURL(portalBundleTomcatURLString)) {
			_portalBundleTomcatURLString = portalBundleTomcatURLString;
		}

		String portalBundleWildFlyURLString = _getURLStringFromBuildProperties(
			"portal.bundle.wildfly");

		if (JenkinsResultsParserUtil.isURL(portalBundleWildFlyURLString)) {
			_portalBundleWildFlyURLString = portalBundleWildFlyURLString;
		}

		String portalDependenciesZipURLString =
			_getURLStringFromBuildProperties("portal.dependencies.zip.url");

		if (JenkinsResultsParserUtil.isURL(portalDependenciesZipURLString)) {
			_portalDependenciesZipURLString = portalDependenciesZipURLString;
		}

		String portalOSGiZipURLString = _getURLStringFromBuildProperties(
			"portal.osgi.zip.url");

		if (JenkinsResultsParserUtil.isURL(portalOSGiZipURLString)) {
			_portalOSGiZipURLString = portalOSGiZipURLString;
		}

		String portalSQLZipURLString = _getURLStringFromBuildProperties(
			"portal.sql.zip.url");

		if (JenkinsResultsParserUtil.isURL(portalSQLZipURLString)) {
			_portalSQLZipURLString = portalSQLZipURLString;
		}

		String portalToolsZipURLString = _getURLStringFromBuildProperties(
			"portal.tools.zip.url");

		if (JenkinsResultsParserUtil.isURL(portalToolsZipURLString)) {
			_portalToolsZipURLString = portalToolsZipURLString;
		}

		String portalWarURLString = _getURLStringFromBuildProperties(
			"portal.war.url");

		if (JenkinsResultsParserUtil.isURL(portalWarURLString)) {
			_portalWarURLString = portalWarURLString;
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				_portalBundleTomcatURLString)) {

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

		_portalBundleGlassFishURLString = _getURLString(
			bundlesBaseURLContent, _portalBundleGlassFishFileNamePattern);
		_portalBundleJBossURLString = _getURLString(
			bundlesBaseURLContent, _portalBundleJBossFileNamePattern);
		_portalBundleTomcatURLString = _getURLString(
			bundlesBaseURLContent, _portalBundleTomcatFileNamePattern);
		_portalBundleWildFlyURLString = _getURLString(
			bundlesBaseURLContent, _portalBundleWildFlyFileNamePattern);
		_portalDependenciesZipURLString = _getURLString(
			bundlesBaseURLContent, _portalDependenciesZipFileNamePattern);
		_portalOSGiZipURLString = _getURLString(
			bundlesBaseURLContent, _portalOSGiZipFileNamePattern);
		_portalSQLZipURLString = _getURLString(
			bundlesBaseURLContent, _portalSQLZipFileNamePattern);
		_portalToolsZipURLString = _getURLString(
			bundlesBaseURLContent, _portalToolsZipFileNamePattern);
		_portalWarURLString = _getURLString(
			bundlesBaseURLContent, _portalWarFileNamePattern);
	}

	private static final String[] _BASE_URL_STRINGS = {
		"http://mirrors.lax.liferay.com/releases.liferay.com/portal",
		"http://mirrors.lax.liferay.com/files.liferay.com/private/ee/portal",
		"https://releases.liferay.com/portal",
		"https://files.liferay.com/private/ee/portal"
	};

	private static final String _PORTAL_VERSION_REGEX =
		"(?<portalVersion>\\d\\.([u\\d\\.]+)(-ee)?(-dxp-\\d+)?" +
			"(\\-(ep|ga|rc|sp)\\d+)?)";

	private static final Pattern _bundleFileNamePattern = Pattern.compile(
		".+\\-" + _PORTAL_VERSION_REGEX + ".*\\.(7z|tar.gz|zip)");
	private static final Pattern _bundlesBaseURLPattern = Pattern.compile(
		"https?://.+/" + _PORTAL_VERSION_REGEX);
	private static final Pattern _bundleURLPattern = Pattern.compile(
		"(?<bundlesBaseURL>https?://.+)/(?<bundleFileName>[^\\/]+" +
			"\\.(7z|tar.gz|zip))");
	private static final Pattern _portalBundleGlassFishFileNamePattern =
		Pattern.compile(
			"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-glassfish-[^\\\"]+" +
				"\\.(7z|tar.gz|zip))\\\"");
	private static final Pattern _portalBundleJBossFileNamePattern =
		Pattern.compile(
			"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-jboss-[^\\\"]+" +
				"\\.(7z|tar.gz|zip))\\\"");
	private static final Pattern _portalBundleTomcatFileNamePattern =
		Pattern.compile(
			"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-tomcat-[^\\\"]+" +
				"\\.(7z|tar.gz|zip))\\\"");
	private static final Pattern _portalBundleWildFlyFileNamePattern =
		Pattern.compile(
			"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-wildfly-[^\\\"]+" +
				"\\.(7z|tar.gz|zip))\\\"");
	private static final Pattern _portalDependenciesZipFileNamePattern =
		Pattern.compile(
			"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-dependencies-" +
				"[^\\\"]+\\.zip)\\\"");
	private static final Pattern _portalOSGiZipFileNamePattern =
		Pattern.compile(
			"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-osgi-[^\\\"]+" +
				"\\.zip)\\\"");
	private static final Pattern _portalSQLZipFileNamePattern = Pattern.compile(
		"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-sql-[^\\\"]+" +
			"\\.zip)\\\"");
	private static final Pattern _portalToolsZipFileNamePattern =
		Pattern.compile(
			"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+-tools-[^\\\"]+" +
				"\\.zip)\\\"");
	private static final Pattern _portalVersionPattern = Pattern.compile(
		"(?<majorVersion>\\d)\\.(?<minorVersion>\\d)\\.(?<fixVersion>\\d+)" +
			"([-\\.](?<updatePrefix>u)?(?<updateVersion>\\d+))?" +
				"(-dxp-(?<dxpVersion>\\d+))?.*");
	private static final Pattern _portalWarFileNamePattern = Pattern.compile(
		"href=\\\"[^\\\"]*(?<fileName>liferay-[^\\\"]+\\.war)\\\"");

	private final URL _bundlesBaseURL;
	private String _pluginsWarZipURLString;
	private String _portalBundleGlassFishURLString;
	private String _portalBundleJBossURLString;
	private String _portalBundleTomcatURLString;
	private String _portalBundleWildFlyURLString;
	private String _portalDependenciesZipURLString;
	private String _portalOSGiZipURLString;
	private String _portalSQLZipURLString;
	private String _portalToolsZipURLString;
	private final String _portalVersion;
	private String _portalWarURLString;

}