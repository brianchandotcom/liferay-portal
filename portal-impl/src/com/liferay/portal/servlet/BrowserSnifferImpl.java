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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		float majorVersion = 0;

		String version = getVersion(request);

		Matcher matcher = _majorVersionPattern.matcher(version);

		if (matcher.find()) {
			majorVersion = GetterUtil.getFloat(matcher.group(1));
		}

		return majorVersion;
	}

	@Override
	public String getRevision(HttpServletRequest request) {
		String revision = StringPool.BLANK;

		String userAgent = getUserAgent(request);

		Matcher matcher = _revisionPattern.matcher(userAgent);

		if (matcher.find()) {
			revision = matcher.group(1);
		}

		return revision;
	}

	@Override
	public String getVersion(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		String version = StringPool.BLANK;

		Matcher matcher = _versionPattern.matcher(userAgent);

		if (matcher.find()) {
			version = matcher.group(1);
		}
		else if (isFirefox(request)) {
			Matcher versionFirefoxMatcher = _versionFirefoxPattern.matcher(
				userAgent);

			if (versionFirefoxMatcher.find()) {
				version = versionFirefoxMatcher.group(1);
			}
		}
		else if (isChrome(request)) {
			Matcher versionChromeMatcher = _versionChromePattern.matcher(
				userAgent);

			if (versionChromeMatcher.find()) {
				version = versionChromeMatcher.group(1);
			}
		}
		else {
			version = getRevision(request);
		}

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

		if (userAgent.contains("mobile")) {
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
		float majorVersion = getMajorVersion(request);

		if (isIe(request) && (majorVersion >= 5.5)) {
			return true;
		}

		if (isMozilla(request) && (majorVersion >= 1.3)) {
			return true;
		}

		if (!isMobile(request)) {
			if (isOpera(request) && (majorVersion >= 10.0)) {
				return true;
			}

			if (isSafari(request) && (majorVersion >= 3.0)) {
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
			accept = accept.toLowerCase();
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
			userAgent = userAgent.toLowerCase();
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

	private static final String[] _FIREFOX_ALIASES = {
		"firefox", "minefield", "granparadiso", "bonecho", "firebird",
		"phoenix", "camino"
	};

	private static final String[] _WEBKIT_ALIASES = {"khtml", "applewebkit"};

	private static final String[] _WINDOWS_ALIASES = {
		"windows", "win32", "16bit"
	};

	private static Pattern _majorVersionPattern = Pattern.compile(
		"(\\d+[.]\\d+)");
	private static Pattern _revisionPattern = Pattern.compile(
		"(?:rv|it|ra|ie)[\\/: ]([\\d.]+)");
	private static Pattern _versionChromePattern = Pattern.compile(
		"(?:chrome)[\\/]([\\d.]+)");
	private static Pattern _versionFirefoxPattern = Pattern.compile(
		"(?:firefox|minefield)[\\/]([\\d.]+)");
	private static Pattern _versionPattern = Pattern.compile(
		"(?:version)[\\/]([\\d.]+)");

}