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

package com.liferay.talend.properties;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.talend.daikon.properties.property.StringProperty;

/**
 * @author Zoltán Takács
 */
public class WebSiteProperty extends StringProperty {

	public WebSiteProperty(String name) {
		super(name);
	}

	public String getHost() {
		return _host;
	}

	public String getWebSiteURL() {
		if (_host == null) {
			throw new IllegalArgumentException("Host is not set");
		}

		UriBuilder uriBuilder = UriBuilder.fromPath(_host);

		URI webSiteURI = uriBuilder.path(
			"p"
		).path(
			"web-site"
		).path(
			"{webSiteId}"
		).build(
			getValue()
		);

		return webSiteURI.toString();
	}

	public void setHost(String host) {
		_host = _removeQuotes(host);
	}

	private String _removeQuotes(String s) {
		return s.replace("\"", "");
	}

	private String _host;

}