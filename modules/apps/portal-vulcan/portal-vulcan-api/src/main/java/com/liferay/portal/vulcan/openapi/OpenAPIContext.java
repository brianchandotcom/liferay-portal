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

package com.liferay.portal.vulcan.openapi;

/**
 * @author Carlos Correa
 */
public class OpenAPIContext {

	public String getBaseUrl() {
		return _baseUrl;
	}

	public String getPath() {
		return _path;
	}

	public String getVersion() {
		return _version;
	}

	public void setBaseUrl(String baseUrl) {
		_baseUrl = baseUrl;
	}

	public void setPath(String path) {
		_path = path;
	}

	public void setVersion(String version) {
		_version = version;
	}

	private String _baseUrl;
	private String _path;
	private String _version;

}