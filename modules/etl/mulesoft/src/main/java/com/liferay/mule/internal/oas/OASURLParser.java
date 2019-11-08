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

		_oasURL = oasURL;
	}

	public String getAuthorityWithScheme() {
		StringBuilder sb = new StringBuilder();

		sb.append(getScheme());
		sb.append("://");
		sb.append(getHost());
		sb.append(getPort());

		return sb.toString();
	}

	public String getHost() {
		return _getGroup(2);
	}

	public String getJaxRSAppBase() {
		return _getGroup(4);
	}

	public String getPort() {
		return _getGroup(3);
	}

	public String getScheme() {
		return _getGroup(1);
	}

	public String getServerBaseURL() {
		StringBuilder sb = new StringBuilder();

		sb.append(getAuthorityWithScheme());
		sb.append("/o/");
		sb.append(getJaxRSAppBase());

		return sb.toString();
	}

	private String _getGroup(int group) {
		Matcher matcher = _oasURLPattern.matcher(_oasURL);

		matcher.matches();

		return matcher.group(group);
	}

	private void _validate(String oasURL) throws MalformedURLException {
		Matcher matcher = _oasURLPattern.matcher(oasURL);

		if (!matcher.matches()) {
			throw new MalformedURLException(
				"Unable to parse OpenAPI specification endpoint URL: " +
					oasURL);
		}
	}

	private static final Pattern _oasURLPattern = Pattern.compile(
		"(.*)://(.+)(:\\d+)/o/(.+)/v(.+)/openapi\\.(yaml|json)");

	private final String _oasURL;

}