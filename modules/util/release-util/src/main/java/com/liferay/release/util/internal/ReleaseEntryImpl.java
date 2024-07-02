/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.util.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.release.util.ReleaseEntry;

import java.util.Properties;
import java.util.function.Supplier;

/**
 * @author Drew Brokke
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleaseEntryImpl implements ReleaseEntry {

	@Override
	public String getAppServerTomcatVersion() {
		if (_appServerTomcatVersion != null) {
			return _appServerTomcatVersion;
		}

		return _getFromProperties("app.server.tomcat.version");
	}

	@Override
	public String getBuildTimestamp() {
		if (_buildTimestamp != null) {
			return _buildTimestamp;
		}

		return _getFromProperties("build.timestamp");
	}

	@Override
	public String getBundleChecksumSHA512() {
		if (_bundleChecksumSHA512 != null) {
			return _bundleChecksumSHA512;
		}

		return _getFromProperties("bundle.checksum.sha512");
	}

	@Override
	public String getBundleURL() {
		if (_bundleURL != null) {
			return _bundleURL;
		}

		return _getFromProperties("bundle.url");
	}

	@Override
	public String getGitHashLiferayDocker() {
		if (_gitHashLiferayDocker != null) {
			return _gitHashLiferayDocker;
		}

		return _getFromProperties("git.hash.liferay-docker");
	}

	@Override
	public String getGitHashLiferayPortalEE() {
		if (_gitHashLiferayPortalEE != null) {
			return _gitHashLiferayPortalEE;
		}

		return _getFromProperties("git.hash.liferay-portal-ee");
	}

	@Override
	public String getLiferayDockerImage() {
		if (_liferayDockerImage != null) {
			return _liferayDockerImage;
		}

		return _getFromProperties("liferay.docker.image");
	}

	@Override
	public String getLiferayDockerTags() {
		if (_liferayDockerTags != null) {
			return _liferayDockerTags;
		}

		return _getFromProperties("liferay.docker.tags");
	}

	@Override
	public String getLiferayProductVersion() {
		if (_liferayProductVersion != null) {
			return _liferayProductVersion;
		}

		return _getFromProperties("liferay.product.version");
	}

	@Override
	public String getProduct() {
		return _product;
	}

	@Override
	public String getProductGroupVersion() {
		return _productGroupVersion;
	}

	@Override
	public String getProductVersion() {
		return _productVersion;
	}

	@Override
	public String getReleaseDate() {
		if (_releaseDate != null) {
			return _releaseDate;
		}

		return _getFromProperties("release.date");
	}

	@Override
	public String getReleaseKey() {
		return _releaseKey;
	}

	@Override
	public String getTargetPlatformVersion() {
		if (_targetPlatformVersion != null) {
			return _targetPlatformVersion;
		}

		return _getFromProperties("target.platform.version");
	}

	@Override
	public String getURL() {
		return _url;
	}

	@Override
	public boolean isPromoted() {
		return _promoted;
	}

	public void setPropertiesSupplier(Supplier<Properties> propertiesSupplier) {
		_propertiesSupplier = propertiesSupplier;
	}

	private String _getFromProperties(String key) {
		if (_properties == null) {
			_properties = _propertiesSupplier.get();
		}

		return _properties.getProperty(key);
	}

	@JsonProperty("appServerTomcatVersion")
	private String _appServerTomcatVersion;

	@JsonProperty("buildTimestamp")
	private String _buildTimestamp;

	@JsonProperty("bundleChecksumSHA512")
	private String _bundleChecksumSHA512;

	@JsonProperty("bundleURL")
	private String _bundleURL;

	@JsonProperty("gitHashLiferayDocker")
	private String _gitHashLiferayDocker;

	@JsonProperty("gitHashLiferayPortalEE")
	private String _gitHashLiferayPortalEE;

	@JsonProperty("liferayDockerImage")
	private String _liferayDockerImage;

	@JsonProperty("liferayDockerTags")
	private String _liferayDockerTags;

	@JsonProperty("liferayProductVersion")
	private String _liferayProductVersion;

	@JsonProperty("product")
	private String _product;

	@JsonProperty("productGroupVersion")
	private String _productGroupVersion;

	@JsonProperty("productVersion")
	private String _productVersion;

	@JsonProperty("promoted")
	private boolean _promoted;

	private Properties _properties;
	private Supplier<Properties> _propertiesSupplier = Properties::new;

	@JsonProperty("releaseDate")
	private String _releaseDate;

	@JsonProperty("releaseKey")
	private String _releaseKey;

	@JsonProperty("targetPlatformVersion")
	private String _targetPlatformVersion;

	@JsonProperty("url")
	private String _url;

}