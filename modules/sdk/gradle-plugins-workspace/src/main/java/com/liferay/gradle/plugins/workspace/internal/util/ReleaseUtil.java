/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.internal.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.attribute.FileTime;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import org.gradle.api.GradleException;

/**
 * @author Drew Brokke
 */
public class ReleaseUtil {

	public static ReleaseUtil instance = null;

	public static <T> T getFromReleaseProperties(
		String releaseKey, Function<ReleaseProperties, T> function) {

		return function.apply(getReleaseProperties(releaseKey));
	}

	public static ReleaseProperties getReleaseProperties(String releaseKey) {
		if (releaseKey == null) {
			return _EMPTY_RELEASE_PROPERTIES;
		}

		if (instance == null) {
			initialize(_DEFAULT_MAX_AGE);
		}

		return instance._releasePropertiesMap.computeIfAbsent(
			releaseKey, instance::_createReleaseProperties);
	}

	public static void initialize(long maxAge) {
		ArrayList<String> releasesMirrors = new ArrayList<>(
			StringUtil.split(System.getenv("LIFERAY_RELEASES_MIRRORS")));

		releasesMirrors.add("https://releases-cdn.liferay.com");

		initialize(maxAge, releasesMirrors, _DEFAULT_WORKSPACE_CACHE_DIR);
	}

	public static void initialize(
		long maxAge, List<String> releaseMirrors, File workspaceCacheDir) {

		instance = new ReleaseUtil(maxAge, releaseMirrors, workspaceCacheDir);
	}

	public static class ReleaseProperties {

		public String getAppServerTomcatVersion() {
			return _appServerTomcatVersion;
		}

		public String getBuildTimestamp() {
			return _buildTimestamp;
		}

		public String getBundleChecksumSHA512() {
			return _bundleChecksumSHA512;
		}

		public String getBundleUrl() {
			return _bundleUrl;
		}

		public String getGitHashLiferayDocker() {
			return _gitHashLiferayDocker;
		}

		public String getGitHashLiferayPortalEE() {
			return _gitHashLiferayPortalEE;
		}

		public String getLiferayDockerImage() {
			return _liferayDockerImage;
		}

		public String getLiferayDockerTags() {
			return _liferayDockerTags;
		}

		public String getLiferayProductVersion() {
			return _liferayProductVersion;
		}

		public String getReleaseDate() {
			return _releaseDate;
		}

		public String getTargetPlatformVersion() {
			return _targetPlatformVersion;
		}

		private ReleaseProperties() {
			this(new Properties());
		}

		private ReleaseProperties(Properties properties) {
			this(
				properties.getProperty("app.server.tomcat.version"),
				properties.getProperty("build.timestamp"),
				properties.getProperty("bundle.checksum.sha512"),
				properties.getProperty("bundle.url"),
				properties.getProperty("git.hash.liferay-docker"),
				properties.getProperty("git.hash.liferay-portal-ee"),
				properties.getProperty("liferay.docker.image"),
				properties.getProperty("liferay.docker.tags"),
				properties.getProperty("liferay.product.version"),
				properties.getProperty("release.date"),
				properties.getProperty("target.platform.version"));
		}

		private ReleaseProperties(
			String appServerTomcatVersion, String buildTimestamp,
			String bundleChecksumSHA512, String bundleUrl,
			String gitHashLiferayDocker, String gitHashLiferayPortalEE,
			String liferayDockerImage, String liferayDockerTags,
			String liferayProductVersion, String releaseDate,
			String targetPlatformVersion) {

			_appServerTomcatVersion = appServerTomcatVersion;
			_buildTimestamp = buildTimestamp;
			_bundleChecksumSHA512 = bundleChecksumSHA512;
			_bundleUrl = bundleUrl;
			_gitHashLiferayDocker = gitHashLiferayDocker;
			_gitHashLiferayPortalEE = gitHashLiferayPortalEE;
			_liferayDockerImage = liferayDockerImage;
			_liferayDockerTags = liferayDockerTags;
			_liferayProductVersion = liferayProductVersion;
			_releaseDate = releaseDate;
			_targetPlatformVersion = targetPlatformVersion;
		}

		private final String _appServerTomcatVersion;
		private final String _buildTimestamp;
		private final String _bundleChecksumSHA512;
		private final String _bundleUrl;
		private final String _gitHashLiferayDocker;
		private final String _gitHashLiferayPortalEE;
		private final String _liferayDockerImage;
		private final String _liferayDockerTags;
		private final String _liferayProductVersion;
		private final String _releaseDate;
		private final String _targetPlatformVersion;

	}

	private ReleaseUtil(
		long maxAge, List<String> releaseMirrors, File workspaceCacheDir) {

		_releaseMirrors = releaseMirrors;

		_workspaceCacheDir = workspaceCacheDir;

		File releasesJsonFile = new File(_workspaceCacheDir, "releases.json");

		ReleaseEntryList releaseEntries = ResourceUtil.readJson(
			ReleaseEntryList.class,
			ResourceUtil.getLocalFileResolver(
				releasesJsonFile, maxAge, ChronoUnit.DAYS));

		if (releaseEntries == null) {
			for (String releaseMirror : _releaseMirrors) {
				releaseEntries = ResourceUtil.readJson(
					ReleaseEntryList.class,
					ResourceUtil.getURLResolver(
						workspaceCacheDir, releaseMirror + "/releases.json"));

				if (releaseEntries != null) {
					break;
				}
			}
		}

		if (releaseEntries == null) {
			releaseEntries = ResourceUtil.readJson(
				ReleaseEntryList.class,
				ResourceUtil.getLocalFileResolver(releasesJsonFile));

			if (releaseEntries != null) {
				try {
					Files.setLastModifiedTime(
						releasesJsonFile.toPath(),
						FileTime.from(Instant.now()));
				}
				catch (IOException ioException) {
					throw new GradleException(ioException.getMessage());
				}
			}
		}

		if (releaseEntries == null) {
			ResourceUtil.Resolver classLoaderResolver =
				ResourceUtil.getClassLoaderResolver("/releases.json");

			releaseEntries = ResourceUtil.readJson(
				ReleaseEntryList.class, classLoaderResolver);

			try (InputStream inputStream = classLoaderResolver.resolve()) {
				Files.copy(inputStream, releasesJsonFile.toPath());
			}
			catch (Exception exception) {
				throw new GradleException(exception.getMessage());
			}
		}

		if (releaseEntries == null) {
			throw new GradleException("Unable to read releases.json");
		}

		for (ReleaseEntry releaseEntry : releaseEntries) {
			_releaseEntryMap.put(releaseEntry.getReleaseKey(), releaseEntry);
		}
	}

	private ReleaseProperties _createReleaseProperties(String releaseKey) {
		ReleaseEntry releaseEntry = _releaseEntryMap.get(releaseKey);

		if (releaseEntry == null) {
			return _EMPTY_RELEASE_PROPERTIES;
		}

		String product = releaseEntry.getProduct();

		File productReleasePropertiesCacheDir = new File(
			new File(_workspaceCacheDir, "releaseProperties"),
			String.format("%s/%s", product, releaseKey));

		Properties properties = ResourceUtil.readProperties(
			ResourceUtil.getLocalFileResolver(
				new File(
					productReleasePropertiesCacheDir, "release.properties")));

		if (properties == null) {
			String releasesCDNUrl =
				releaseEntry.getUrl() + "/release.properties";

			URI cdnURI = URI.create(releasesCDNUrl);

			String cdnURIPath = cdnURI.getPath();

			for (String releaseMirror : _releaseMirrors) {
				String fullMirrorPath = releaseMirror + cdnURIPath;

				properties = ResourceUtil.readProperties(
					ResourceUtil.getURLResolver(
						productReleasePropertiesCacheDir, fullMirrorPath));

				if (properties != null) {
					break;
				}
			}
		}

		if (properties == null) {
			throw new GradleException(
				"No release properties found for product key " + releaseKey);
		}

		return new ReleaseProperties(properties);
	}

	private static final long _DEFAULT_MAX_AGE = 7;

	private static final File _DEFAULT_WORKSPACE_CACHE_DIR = new File(
		System.getProperty("user.home"), ".liferay/workspace");

	private static final ReleaseProperties _EMPTY_RELEASE_PROPERTIES =
		new ReleaseProperties();

	private final Map<String, ReleaseEntry> _releaseEntryMap = new HashMap<>();
	private final List<String> _releaseMirrors;
	private final Map<String, ReleaseProperties> _releasePropertiesMap =
		new HashMap<>();
	private final File _workspaceCacheDir;

	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class ReleaseEntry {

		public String getProduct() {
			return _product;
		}

		public String getReleaseKey() {
			return _releaseKey;
		}

		public String getUrl() {
			return _url;
		}

		@JsonProperty("product")
		private String _product;

		@JsonProperty("releaseKey")
		private String _releaseKey;

		@JsonProperty("url")
		private String _url;

	}

	private static class ReleaseEntryList extends ArrayList<ReleaseEntry> {
	}

}