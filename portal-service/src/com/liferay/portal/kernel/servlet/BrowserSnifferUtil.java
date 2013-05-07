/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import javax.servlet.http.HttpServletRequest;

/**
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class BrowserSnifferUtil {

	public static boolean acceptsGzip(HttpServletRequest request) {
		return getBrowserSniffer().acceptsGzip(request);
	}

	public static String getBrowserId(HttpServletRequest request) {
		return getBrowserSniffer().getBrowserId(request);
	}

	public static BrowserSniffer getBrowserSniffer() {
		PortalRuntimePermission.checkGetBeanProperty(BrowserSnifferUtil.class);

		return _browserSniffer;
	}

	public static float getMajorVersion(HttpServletRequest request) {
		return getBrowserSniffer().getMajorVersion(request);
	}

	public static String getRevision(HttpServletRequest request) {
		return getBrowserSniffer().getRevision(request);
	}

	public static String getVersion(HttpServletRequest request) {
		return getBrowserSniffer().getVersion(request);
	}

	public static boolean isAir(HttpServletRequest request) {
		return getBrowserSniffer().isAir(request);
	}

	public static boolean isChrome(HttpServletRequest request) {
		return getBrowserSniffer().isChrome(request);
	}

	public static boolean isFirefox(HttpServletRequest request) {
		return getBrowserSniffer().isFirefox(request);
	}

	public static boolean isGecko(HttpServletRequest request) {
		return getBrowserSniffer().isGecko(request);
	}

	public static boolean isIe(HttpServletRequest request) {
		return getBrowserSniffer().isIe(request);
	}

	public static boolean isIeOnWin32Bit(HttpServletRequest request) {
		return getBrowserSniffer().isIeOnWin32Bit(request);
	}

	public static boolean isIeOnWin64Bit(HttpServletRequest request) {
		return getBrowserSniffer().isIeOnWin64Bit(request);
	}

	public static boolean isIphone(HttpServletRequest request) {
		return getBrowserSniffer().isIphone(request);
	}

	public static boolean isLinux(HttpServletRequest request) {
		return getBrowserSniffer().isLinux(request);
	}

	public static boolean isMac(HttpServletRequest request) {
		return getBrowserSniffer().isMac(request);
	}

	public static boolean isMobile(HttpServletRequest request) {
		return getBrowserSniffer().isMobile(request);
	}

	public static boolean isMozilla(HttpServletRequest request) {
		return getBrowserSniffer().isMozilla(request);
	}

	public static boolean isOpera(HttpServletRequest request) {
		return getBrowserSniffer().isOpera(request);
	}

	public static boolean isRtf(HttpServletRequest request) {
		return getBrowserSniffer().isRtf(request);
	}

	public static boolean isSafari(HttpServletRequest request) {
		return getBrowserSniffer().isSafari(request);
	}

	public static boolean isSun(HttpServletRequest request) {
		return getBrowserSniffer().isSun(request);
	}

	public static boolean isWap(HttpServletRequest request) {
		return getBrowserSniffer().isWap(request);
	}

	public static boolean isWapXhtml(HttpServletRequest request) {
		return getBrowserSniffer().isWapXhtml(request);
	}

	public static boolean isWebKit(HttpServletRequest request) {
		return getBrowserSniffer().isWebKit(request);
	}

	public static boolean isWindows(HttpServletRequest request) {
		return getBrowserSniffer().isWindows(request);
	}

	public static boolean isWml(HttpServletRequest request) {
		return getBrowserSniffer().isWml(request);
	}

	public void setBrowserSniffer(BrowserSniffer browserSniffer) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_browserSniffer = browserSniffer;
	}

	private static BrowserSniffer _browserSniffer;

}