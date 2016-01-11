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

package com.liferay.jenkins.results.parser;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Kevin Yen
 */
public class JenkinsJobUtil {

	public static String appendURL(String url, String path) {
		while (url.endsWith("/")) {
			int x = url.length() - 1;

			url = url.substring(0, x);
		}

		if (path.equals("")) {
			return url;
		}

		while (path.startsWith("/")) {
			if (path.equals("/")) {
				return url;
			}
			else {
				path = path.substring(1);
			}
		}

		while (path.endsWith("/")) {
			int x = path.length() - 1;

			path = path.substring(0, x);
		}

		StringBuilder sb = new StringBuilder();

		sb.append(url);
		sb.append("/");
		sb.append(path);

		return sb.toString();
	}

	public static String encodeAuthorizationFields(
		String username, String password) {

		String authorizationString = username + ":" + password;

		byte[] encodedBytes = Base64.encodeBase64(
			authorizationString.getBytes());

		return new String(encodedBytes);
	}

}