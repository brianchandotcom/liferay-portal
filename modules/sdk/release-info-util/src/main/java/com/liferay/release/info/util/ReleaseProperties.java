/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.info.util;

import java.util.Properties;

/**
 * @author Drew Brokke
 */
public class ReleaseProperties {

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

	protected ReleaseProperties() {
		this(new Properties());
	}

	protected ReleaseProperties(Properties properties) {
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