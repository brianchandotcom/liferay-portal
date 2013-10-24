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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author Eduardo Lundgren
 * @author Nate Cavanaugh
 */
@DoPrivileged
public class BrowserSnifferImpl implements BrowserSniffer {

	@Override
	public boolean acceptsGzip(HttpServletRequest request) {
		String acceptEncoding = request.getHeader(HttpHeaders.ACCEPT_ENCODING);

		if ((acceptEncoding != null) && acceptEncoding.contains("gzip")) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String getBrowserId(HttpServletRequest request) {
		if (isIe(request)) {
			return BROWSER_ID_IE;
		}
		else if (isFirefox(request)) {
			return BROWSER_ID_FIREFOX;
		}
		else {
			return BROWSER_ID_OTHER;
		}
	}

	@Override
	public float getMajorVersion(HttpServletRequest request) {
		return GetterUtil.getFloat(getVersion(request));
	}

	@Override
	public String getRevision(HttpServletRequest request) {
		String revision = (String)request.getAttribute(
			WebKeys.BROWSER_SNIFFER_REVISION);

		if (revision != null) {
			return revision;
		}

		revision = parseVersion(
			getUserAgent(request), revisionLeadings, revisionSeparators);

		request.setAttribute(WebKeys.BROWSER_SNIFFER_REVISION, revision);

		return revision;
	}

	@Override
	public String getVersion(HttpServletRequest request) {
		String version = (String)request.getAttribute(
			WebKeys.BROWSER_SNIFFER_VERSION);

		if (version != null) {
			return version;
		}

		String userAgent = getUserAgent(request);

		version = parseVersion(userAgent, versionLeadings, versionSeparators);

		if (version.isEmpty()) {
			version = parseVersion(
				userAgent, revisionLeadings, revisionSeparators);
		}

		request.setAttribute(WebKeys.BROWSER_SNIFFER_VERSION, version);

		return version;
	}

	@Override
	public boolean isAir(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("adobeair")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isAndroid(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("android")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isChrome(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("chrome")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isFirefox(HttpServletRequest request) {
		if (!isMozilla(request)) {
			return false;
		}

		String userAgent = getUserAgent(request);

		for (String firefoxAlias : _FIREFOX_ALIASES) {
			if (userAgent.contains(firefoxAlias)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isGecko(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("gecko")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isIe(HttpServletRequest request) {
		return isIe(getUserAgent(request));
	}

	@Override
	public boolean isIeOnWin32(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (isIe(userAgent) &&
			!(userAgent.contains("wow64") || userAgent.contains("win64"))) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isIeOnWin64(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (isIe(userAgent) &&
			(userAgent.contains("wow64") || userAgent.contains("win64"))) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isIphone(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("iphone")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isLinux(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("linux")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMac(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("mac")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMobile(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		// LPS-41086

		if (userAgent.contains("mobile") ||
			(isAndroid(request) && userAgent.contains("nexus"))) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isMozilla(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("mozilla") &&
			!(userAgent.contains("compatible") ||
			  userAgent.contains("webkit"))) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isOpera(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("opera")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRtf(HttpServletRequest request) {
		if (isAndroid(request)) {
			return true;
		}

		if (isChrome(request)) {
			return true;
		}

		float majorVersion = getMajorVersion(request);

		if (isIe(request) && (majorVersion >= 5.5)) {
			return true;
		}

		if (isMozilla(request) && (majorVersion >= 1.3)) {
			return true;
		}

		if (isOpera(request)) {
			if (isMobile(request) && (majorVersion >= 10.0)) {
				return true;
			}
			else if (!isMobile(request)) {
				return true;
			}
		}

		if (isSafari(request)) {
			if (isMobile(request) && (majorVersion >= 5.0)) {
				return true;
			}
			else if (!isMobile(request) && (majorVersion >= 3.0)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isSafari(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (isWebKit(request) && userAgent.contains("safari")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isSun(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("sunos")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isWap(HttpServletRequest request) {
		return isWapXhtml(request);
	}

	@Override
	public boolean isWapXhtml(HttpServletRequest request) {
		String accept = getAccept(request);

		if (accept.contains("wap.xhtml")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isWebKit(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		for (String webKitAlias : _WEBKIT_ALIASES) {
			if (userAgent.contains(webKitAlias)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isWindows(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		for (String windowsAlias : _WINDOWS_ALIASES) {
			if (userAgent.contains(windowsAlias)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isWml(HttpServletRequest request) {
		String accept = getAccept(request);

		if (accept.contains("wap.wml")) {
			return true;
		}

		return false;
	}

	protected static String parseVersion(
		String userAgent, String[] leadings, char[] separators) {

		leading:
		for (String leading : leadings) {
			int index = 0;

			version:
			while (true) {
				index = userAgent.indexOf(leading, index);

				if ((index < 0) ||
					(((index += leading.length()) + 2) > userAgent.length())) {

					continue leading;
				}

				char c1 = userAgent.charAt(index);
				char c2 = userAgent.charAt(++index);

				if (((c2 >= '0') && (c2 <= '9')) || (c2 == '.')) {
					for (char separator : separators) {
						if (c1 == separator) {
							break version;
						}
					}
				}
			}

			// Major

			int majorStart = index;
			int majorEnd = index + 1;

			for (int i = majorStart; i < userAgent.length(); i++) {
				char c = userAgent.charAt(i);

				if ((c < '0') || (c > '9')) {
					majorEnd = i;

					break;
				}
			}

			String major = userAgent.substring(majorStart, majorEnd);

			if (userAgent.charAt(majorEnd) != '.') {
				return major;
			}

			// Minor

			int minorStart = majorEnd + 1;
			int minorEnd = userAgent.length();

			for (int i = minorStart; i < userAgent.length(); i++) {
				char c = userAgent.charAt(i);

				if ((c < '0') || (c > '9')) {
					minorEnd = i;

					break;
				}
			}

			String minor = userAgent.substring(minorStart, minorEnd);

			return major.concat(StringPool.PERIOD).concat(minor);
		}

		return StringPool.BLANK;
	}

	protected String getAccept(HttpServletRequest request) {
		String accept = StringPool.BLANK;

		if (request == null) {
			return accept;
		}

		accept = String.valueOf(request.getAttribute(HttpHeaders.ACCEPT));

		if (Validator.isNotNull(accept)) {
			return accept;
		}

		accept = request.getHeader(HttpHeaders.ACCEPT);

		if (accept != null) {
			accept = StringUtil.toLowerCase(accept);
		}
		else {
			accept = StringPool.BLANK;
		}

		request.setAttribute(HttpHeaders.ACCEPT, accept);

		return accept;
	}

	protected String getUserAgent(HttpServletRequest request) {
		String userAgent = StringPool.BLANK;

		if (request == null) {
			return userAgent;
		}

		userAgent = String.valueOf(
			request.getAttribute(HttpHeaders.USER_AGENT));

		if (Validator.isNotNull(userAgent)) {
			return userAgent;
		}

		userAgent = request.getHeader(HttpHeaders.USER_AGENT);

		if (userAgent != null) {
			userAgent = StringUtil.toLowerCase(userAgent);
		}
		else {
			userAgent = StringPool.BLANK;
		}

		request.setAttribute(HttpHeaders.USER_AGENT, userAgent);

		return userAgent;
	}

	protected boolean isIe(String userAgent) {
		if (userAgent.contains("msie") && !userAgent.contains("opera")) {
			return true;
		}

		return false;
	}

	protected static String[] revisionLeadings = {"rv", "it", "ra", "ie"};
	protected static char[] revisionSeparators =
		{CharPool.BACK_SLASH, CharPool.COLON, CharPool.SLASH, CharPool.SPACE};
	protected static String[] versionLeadings =
		{"version", "firefox", "minefield", "chrome"};
	protected static char[] versionSeparators =
		{CharPool.BACK_SLASH, CharPool.SLASH};

	private static final String[] _FIREFOX_ALIASES = {
		"firefox", "minefield", "granparadiso", "bonecho", "firebird",
		"phoenix", "camino"
	};

	private static final String[] _WEBKIT_ALIASES = {"khtml", "applewebkit"};

	private static final String[] _WINDOWS_ALIASES = {
		"windows", "win32", "16bit"
	};

}