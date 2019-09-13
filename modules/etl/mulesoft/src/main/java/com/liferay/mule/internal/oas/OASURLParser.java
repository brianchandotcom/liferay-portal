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

	public OASURLParser(String oasURL) {
		_oasURL = oasURL;
	}

	public String getHost() throws MalformedURLException {
		return _getGroup(2);
	}

	public String getJaxRSAppBase() throws MalformedURLException {
		return _getGroup(4);
	}

	public String getPort() throws MalformedURLException {
		return _getGroup(3);
	}

	public String getProtocol() throws MalformedURLException {
		return _getGroup(1);
	}

	public String getServerBaseURL() throws MalformedURLException {
		StringBuilder sb = new StringBuilder();

		sb.append(getProtocol());
		sb.append("://");
		sb.append(getHost());
		sb.append(getPort());
		sb.append("/o/");
		sb.append(getJaxRSAppBase());

		return sb.toString();
	}

	private String _getGroup(int group) throws MalformedURLException {
		Matcher matcher = _oasURLPattern.matcher(_oasURL);

		if (!matcher.matches()) {
			throw new MalformedURLException(
				"Unable to parse OpenAPI specification endpoint URL: " +
					_oasURL);
		}

		return matcher.group(group);
	}

	private static final Pattern _oasURLPattern = Pattern.compile(
		"(.*)://(.+)(:\\d+)/o/(.+)/v(.+)/openapi\\.(yaml|json)");

	private final String _oasURL;

}