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

package com.liferay.portal.kernel.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class BrowserSnifferUtil {

	public static boolean acceptsGzip(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().acceptsGzip(httpServletRequest);
	}

	public static String getBrowserId(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().getBrowserId(httpServletRequest);
	}

	public static BrowserMetadata getBrowserMetadata(
		HttpServletRequest httpServletRequest) {

		return getBrowserSniffer().getBrowserMetadata(httpServletRequest);
	}

	public static BrowserSniffer getBrowserSniffer() {
		return _browserSniffer;
	}

	public static float getMajorVersion(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().getMajorVersion(httpServletRequest);
	}

	public static String getRevision(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().getRevision(httpServletRequest);
	}

	public static String getVersion(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().getVersion(httpServletRequest);
	}

	public static boolean isAir(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isAir(httpServletRequest);
	}

	public static boolean isAndroid(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isAndroid(httpServletRequest);
	}

	public static boolean isChrome(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isChrome(httpServletRequest);
	}

	public static boolean isEdge(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isEdge(httpServletRequest);
	}

	public static boolean isFirefox(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isFirefox(httpServletRequest);
	}

	public static boolean isGecko(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isGecko(httpServletRequest);
	}

	public static boolean isIe(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isIe(httpServletRequest);
	}

	public static boolean isIeOnWin32(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isIeOnWin32(httpServletRequest);
	}

	public static boolean isIeOnWin64(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isIeOnWin64(httpServletRequest);
	}

	public static boolean isIphone(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isIphone(httpServletRequest);
	}

	public static boolean isLinux(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isLinux(httpServletRequest);
	}

	public static boolean isMac(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isMac(httpServletRequest);
	}

	public static boolean isMobile(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isMobile(httpServletRequest);
	}

	public static boolean isMozilla(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isMozilla(httpServletRequest);
	}

	public static boolean isOpera(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isOpera(httpServletRequest);
	}

	public static boolean isRtf(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isRtf(httpServletRequest);
	}

	public static boolean isSafari(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isSafari(httpServletRequest);
	}

	public static boolean isSun(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isSun(httpServletRequest);
	}

	public static boolean isWebKit(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isWebKit(httpServletRequest);
	}

	public static boolean isWindows(HttpServletRequest httpServletRequest) {
		return getBrowserSniffer().isWindows(httpServletRequest);
	}

	public void setBrowserSniffer(BrowserSniffer browserSniffer) {
		_browserSniffer = browserSniffer;
	}

	private static BrowserSniffer _browserSniffer;

}