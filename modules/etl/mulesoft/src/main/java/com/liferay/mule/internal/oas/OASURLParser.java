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

package com.liferay.mule.internal.oas;

import java.net.MalformedURLException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Matija Petanjek
 */
public class OASURLParser {

	public OASURLParser(String oasURL) throws MalformedURLException {
		Matcher matcher = _oasURLPattern.matcher(oasURL);

		if (!matcher.matches()) {
			throw new MalformedURLException(
				"Unable to parse OpenAPI specification endpoint URL: " +
					oasURL);
		}

		_host = matcher.group(2);
		_jaxRSAppBase = matcher.group(5);

		if (matcher.group(4) == null) {
			_port = "";
		}
		else {
			_port = matcher.group(4);
		}

		_scheme = matcher.group(1);
	}

	public String getAuthorityWithScheme() {
		StringBuilder sb = new StringBuilder();

		sb.append(_scheme);
		sb.append("://");
		sb.append(_host);
		sb.append(":");
		sb.append(_port);

		return sb.toString();
	}

	public String getHost() {
		return _host;
	}

	public String getJaxRSAppBase() {
		return _jaxRSAppBase;
	}

	public String getPort() {
		return _port;
	}

	public String getScheme() {
		return _scheme;
	}

	public String getServerBaseURL() {
		StringBuilder sb = new StringBuilder();

		sb.append(getAuthorityWithScheme());
		sb.append("/o/");
		sb.append(_jaxRSAppBase);

		return sb.toString();
	}

	private static final Pattern _oasURLPattern = Pattern.compile(
		"(.*)://(.+?)(:(\\d+))?/o/(.+)/v(.+)/openapi\\.(yaml|json)");

	private final String _host;
	private final String _jaxRSAppBase;
	private final String _port;
	private final String _scheme;

}