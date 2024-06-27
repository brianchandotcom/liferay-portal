/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.info.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Drew Brokke
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleaseEntry {

	public String getProduct() {
		return _product;
	}

	public String getProductGroupVersion() {
		return _productGroupVersion;
	}

	public String getProductVersion() {
		return _productVersion;
	}

	public String getReleaseKey() {
		return _releaseKey;
	}

	public String getTargetPlatformVersion() {
		return _targetPlatformVersion;
	}

	public String getUrl() {
		return _url;
	}

	public boolean isPromoted() {
		return _promoted;
	}

	@JsonProperty("product")
	private String _product;

	@JsonProperty("productGroupVersion")
	private String _productGroupVersion;

	@JsonProperty("productVersion")
	private String _productVersion;

	@JsonProperty("promoted")
	private boolean _promoted;

	@JsonProperty("releaseKey")
	private String _releaseKey;

	@JsonProperty("targetPlatformVersion")
	private String _targetPlatformVersion;

	@JsonProperty("url")
	private String _url;

}