/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Preston Crary
 */
public class BrowserMetadata {

	public BrowserMetadata(String userAgent) {
		_userAgent = userAgent;
	}

	public boolean isAir() {
		if (_userAgent.contains("adobeair")) {
			return true;
		}

		return false;
	}

	public boolean isAndroid() {
		if (_userAgent.contains("android")) {
			return true;
		}

		return false;
	}

	public boolean isChrome() {
		if (isEdge()) {
			return false;
		}

		if (_userAgent.contains("chrome")) {
			return true;
		}

		return false;
	}

	public boolean isEdge() {
		if (_userAgent.contains("edge")) {
			return true;
		}

		return false;
	}

	public boolean isFirefox() {
		if (!isMozilla()) {
			return false;
		}

		for (String firefoxAlias : _FIREFOX_ALIASES) {
			if (_userAgent.contains(firefoxAlias)) {
				return true;
			}
		}

		return false;
	}

	public boolean isGecko() {
		if (isEdge()) {
			return false;
		}

		if (_userAgent.contains("gecko")) {
			return true;
		}

		return false;
	}

	public boolean isIe() {
		if ((_userAgent.contains("msie") || _userAgent.contains("trident")) &&
			!_userAgent.contains("opera")) {

			return true;
		}

		return false;
	}

	public boolean isIeOnWin32() {
		if (isIe() && !_userAgent.contains("wow64") &&
			!_userAgent.contains("win64")) {

			return true;
		}

		return false;
	}

	public boolean isIeOnWin64() {
		if (isIe() &&
			(_userAgent.contains("wow64") || _userAgent.contains("win64"))) {

			return true;
		}

		return false;
	}

	public boolean isIphone() {
		if (_userAgent.contains("iphone")) {
			return true;
		}

		return false;
	}

	public boolean isLinux() {
		if (_userAgent.contains("linux")) {
			return true;
		}

		return false;
	}

	public boolean isMac() {
		if (_userAgent.contains("mac")) {
			return true;
		}

		return false;
	}

	public boolean isMobile() {
		if (_userAgent.contains("mobile") ||
			(_userAgent.contains("android") && _userAgent.contains("nexus"))) {

			return true;
		}

		return false;
	}

	public boolean isMozilla() {
		if (isEdge() || _userAgent.contains("compatible") ||
			_userAgent.contains("webkit")) {

			return false;
		}

		if (_userAgent.contains("mozilla")) {
			return true;
		}

		return false;
	}

	public boolean isOpera() {
		if (_userAgent.contains("opera")) {
			return true;
		}

		return false;
	}

	public boolean isRtf(String version) {
		if (isAndroid() || isChrome() || isEdge()) {
			return true;
		}

		float majorVersion = GetterUtil.getFloat(version);

		if (isIe() && (majorVersion >= 5.5)) {
			return true;
		}

		if (isMozilla() && (majorVersion >= 1.3)) {
			return true;
		}

		if (isOpera()) {
			if (isMobile() && (majorVersion >= 10.0)) {
				return true;
			}
			else if (!isMobile()) {
				return true;
			}
		}

		if (isSafari()) {
			if (isMobile() && (majorVersion >= 5.0)) {
				return true;
			}
			else if (!isMobile() && (majorVersion >= 3.0)) {
				return true;
			}
		}

		return false;
	}

	public boolean isSafari() {
		if (isWebKit() && _userAgent.contains("safari")) {
			return true;
		}

		return false;
	}

	public boolean isSun() {
		if (_userAgent.contains("sunos")) {
			return true;
		}

		return false;
	}

	public boolean isWebKit() {
		if (isEdge()) {
			return false;
		}

		for (String webKitAlias : _WEBKIT_ALIASES) {
			if (_userAgent.contains(webKitAlias)) {
				return true;
			}
		}

		return false;
	}

	public boolean isWindows() {
		for (String windowsAlias : _WINDOWS_ALIASES) {
			if (_userAgent.contains(windowsAlias)) {
				return true;
			}
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

	private final String _userAgent;

}