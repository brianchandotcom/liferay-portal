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
		_validate(oasURL);
	}

	public String getAuthorityWithScheme() {
		StringBuilder sb = new StringBuilder();

		sb.append(getScheme());
		sb.append("://");
		sb.append(getHost());
		sb.append(":");
		sb.append(getPort());

		return sb.toString();
	}

	public String getHost() {
		return _oasURLMatcher.group(2);
	}

	public String getJaxRSAppBase() {
		return _oasURLMatcher.group(4);
	}

	public String getPort() {
		return _oasURLMatcher.group(3);
	}

	public String getScheme() {
		return _oasURLMatcher.group(1);
	}

	public String getServerBaseURL() {
		StringBuilder sb = new StringBuilder();

		sb.append(getAuthorityWithScheme());
		sb.append("/o/");
		sb.append(getJaxRSAppBase());

		return sb.toString();
	}

	private void _validate(String oasURL) throws MalformedURLException {
		_oasURLMatcher = _oasURLPattern.matcher(oasURL);

		if (!_oasURLMatcher.matches()) {
			throw new MalformedURLException(
				"Unable to parse OpenAPI specification endpoint URL: " +
					oasURL);
		}
	}

	private static final Pattern _oasURLPattern = Pattern.compile(
		"(.*)://(.+):(\\d+)/o/(.+)/v(.+)/openapi\\.(yaml|json)");

	private Matcher _oasURLMatcher;

}