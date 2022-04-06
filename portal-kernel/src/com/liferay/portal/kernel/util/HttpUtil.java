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

package com.liferay.portal.kernel.util;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import javax.servlet.http.Cookie;

/**
 * @author Brian Wing Shun Chan
 */
public class HttpUtil {

	public static Cookie[] getCookies() {
		return _http.getCookies();
	}

	public static Http getHttp() {
		return _http;
	}

	public static boolean hasProxyConfig() {
		return _http.hasProxyConfig();
	}

	public static boolean isNonProxyHost(String host) {
		return _http.isNonProxyHost(host);
	}

	public static boolean isProxyHost(String host) {
		return _http.isProxyHost(host);
	}

	public static byte[] URLtoByteArray(Http.Options options)
		throws IOException {

		return _http.URLtoByteArray(options);
	}

	public static byte[] URLtoByteArray(String location) throws IOException {
		return _http.URLtoByteArray(location);
	}

	public static byte[] URLtoByteArray(String location, boolean post)
		throws IOException {

		return _http.URLtoByteArray(location, post);
	}

	public static InputStream URLtoInputStream(Http.Options options)
		throws IOException {

		return _http.URLtoInputStream(options);
	}

	public static InputStream URLtoInputStream(String location)
		throws IOException {

		return _http.URLtoInputStream(location);
	}

	public static InputStream URLtoInputStream(String location, boolean post)
		throws IOException {

		return _http.URLtoInputStream(location, post);
	}

	public static String URLtoString(Http.Options options) throws IOException {
		return _http.URLtoString(options);
	}

	public static String URLtoString(String location) throws IOException {
		return _http.URLtoString(location);
	}

	public static String URLtoString(String location, boolean post)
		throws IOException {

		return _http.URLtoString(location, post);
	}

	/**
	 * This method only uses the default Commons HttpClient implementation when
	 * the URL object represents a HTTP resource. The URL object could also
	 * represent a file or some JNDI resource. In that case, the default Java
	 * implementation is used.
	 *
	 * @param  url the URL
	 * @return A string representation of the resource referenced by the URL
	 *         object
	 * @throws IOException if an IO Exception occurred
	 */
	public static String URLtoString(URL url) throws IOException {
		return _http.URLtoString(url);
	}

	public void setHttp(Http http) {
		_http = http;
	}

	private static Http _http;

}