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

package com.liferay.portal.servlet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.BrowserMetadata;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
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
public class BrowserSnifferImpl implements BrowserSniffer {

	@Override
	public boolean acceptsGzip(HttpServletRequest request) {
		String acceptEncoding = request.getHeader(HttpHeaders.ACCEPT_ENCODING);

		if ((acceptEncoding != null) && acceptEncoding.contains("gzip")) {
			return true;
		}

		return false;
	}

	@Override
	public String getBrowserId(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		if (browserMetadata.isEdge()) {
			return BROWSER_ID_EDGE;
		}
		else if (browserMetadata.isIe()) {
			return BROWSER_ID_IE;
		}
		else if (browserMetadata.isFirefox()) {
			return BROWSER_ID_FIREFOX;
		}

		return BROWSER_ID_OTHER;
	}

	@Override
	public BrowserMetadata getBrowserMetadata(HttpServletRequest request) {
		return new BrowserMetadata(getUserAgent(request));
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
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isAir();
	}

	@Override
	public boolean isAndroid(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isAndroid();
	}

	@Override
	public boolean isChrome(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isChrome();
	}

	@Override
	public boolean isEdge(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isEdge();
	}

	@Override
	public boolean isFirefox(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isFirefox();
	}

	@Override
	public boolean isGecko(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isGecko();
	}

	@Override
	public boolean isIe(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isIe();
	}

	@Override
	public boolean isIeOnWin32(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isIeOnWin32();
	}

	@Override
	public boolean isIeOnWin64(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isIeOnWin64();
	}

	@Override
	public boolean isIphone(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isIphone();
	}

	@Override
	public boolean isLinux(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isLinux();
	}

	@Override
	public boolean isMac(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isMac();
	}

	@Override
	public boolean isMobile(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isMobile();
	}

	@Override
	public boolean isMozilla(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isMozilla();
	}

	@Override
	public boolean isOpera(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isOpera();
	}

	@Override
	public boolean isRtf(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isRtf(getVersion(request));
	}

	@Override
	public boolean isSafari(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isSafari();
	}

	@Override
	public boolean isSun(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isSun();
	}

	@Override
	public boolean isWebKit(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isWebKit();
	}

	@Override
	public boolean isWindows(HttpServletRequest request) {
		BrowserMetadata browserMetadata = getBrowserMetadata(request);

		return browserMetadata.isWindows();
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

			String version = major.concat(StringPool.PERIOD).concat(minor);

			if (leading.equals("trident")) {
				if (version.equals("7.0")) {
					version = "11.0";
				}
				else if (version.equals("6.0")) {
					version = "10.0";
				}
				else if (version.equals("5.0")) {
					version = "9.0";
				}
				else if (version.equals("4.0")) {
					version = "8.0";
				}
			}

			return version;
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
		if (request == null) {
			return StringPool.BLANK;
		}

		Object userAgentObject = request.getAttribute(HttpHeaders.USER_AGENT);

		if (userAgentObject != null) {
			return userAgentObject.toString();
		}

		String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

		if (userAgent != null) {
			userAgent = StringUtil.toLowerCase(userAgent);
		}
		else {
			userAgent = StringPool.BLANK;
		}

		request.setAttribute(HttpHeaders.USER_AGENT, userAgent);

		return userAgent;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             BrowserMetadata#isIe()}
	 */
	@Deprecated
	protected boolean isIe(String userAgent) {
		BrowserMetadata browserMetadata = new BrowserMetadata(userAgent);

		return browserMetadata.isIe();
	}

	protected static String[] revisionLeadings =
		{"rv", "it", "ra", "trident", "ie"};
	protected static char[] revisionSeparators =
		{CharPool.BACK_SLASH, CharPool.COLON, CharPool.SLASH, CharPool.SPACE};
	protected static String[] versionLeadings =
		{"edge", "chrome", "firefox", "version", "minefield", "trident"};
	protected static char[] versionSeparators =
		{CharPool.BACK_SLASH, CharPool.SLASH};

}