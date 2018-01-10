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

import aQute.bnd.annotation.ProviderType;

import javax.servlet.http.HttpServletRequest;

/**
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface BrowserSniffer {

	public static final String BROWSER_ID_EDGE = "edge";

	public static final String BROWSER_ID_FIREFOX = "firefox";

	public static final String BROWSER_ID_IE = "ie";

	public static final String BROWSER_ID_OTHER = "other";

	public boolean acceptsGzip(HttpServletRequest request);

	public String getBrowserId(HttpServletRequest request);

	public BrowserMetadata getBrowserMetadata(HttpServletRequest request);

	/**
	 * <p>
	 * Get the browser's version number as a float. Same as
	 * {@link BrowserSniffer#getVersion(HttpServletRequest)} but as a float
	 * instead of as a {@link String}.
	 * </p>
	 * <p>
	 * Note that the version returned by this method is defined as the real
	 * version of the browser software, not the one used to render the page.
	 * This is an important detail because, for example, the browser can be an
	 * IE10 but it may be using a compatibility view emulating IE8 to render the
	 * page. In such case, this method would return 10.0, not 8.0.
	 * </p>
	 * @param request
	 * @return a float representing the version number
	 * @see BrowserSniffer#getVersion(HttpServletRequest)
	 * @review
	 */
	public float getMajorVersion(HttpServletRequest request);

	/**
	 * <p>
	 * Get the browser's revision.
	 * </p>
	 * <p>
	 * Note that the revision returned by this method is defined as the real
	 * revision of the browser software, not the one used to render the page.
	 * This is an important detail because, for example, the browser can be an
	 * IE10 but it may be using a compatibility view emulating IE8 to render the
	 * page. In such case, this method would return "10.0", not "8.0".
	 * </p>
	 * @param request
	 * @return a String containing the revision number
	 * @review
	 */
	public String getRevision(HttpServletRequest request);

	/**
	 * <p>
	 * Get the browser's version.
	 * </p>
	 * <p>
	 * Note that the version returned by this method is defined as the real
	 * version of the browser software, not the one used to render the page.
	 * This is an important detail because, for example, the browser can be an
	 * IE10 but it may be using a compatibility view emulating IE8 to render the
	 * page. In such case, this method would return "10.0", not "8.0".
	 * </p>
	 * @param request
	 * @return a String containing the version number
	 * @review
	 */
	public String getVersion(HttpServletRequest request);

	public boolean isAir(HttpServletRequest request);

	public boolean isAndroid(HttpServletRequest request);

	public boolean isChrome(HttpServletRequest request);

	public boolean isEdge(HttpServletRequest request);

	public boolean isFirefox(HttpServletRequest request);

	public boolean isGecko(HttpServletRequest request);

	public boolean isIe(HttpServletRequest request);

	public boolean isIeOnWin32(HttpServletRequest request);

	public boolean isIeOnWin64(HttpServletRequest request);

	public boolean isIphone(HttpServletRequest request);

	public boolean isLinux(HttpServletRequest request);

	public boolean isMac(HttpServletRequest request);

	public boolean isMobile(HttpServletRequest request);

	public boolean isMozilla(HttpServletRequest request);

	public boolean isOpera(HttpServletRequest request);

	public boolean isRtf(HttpServletRequest request);

	public boolean isSafari(HttpServletRequest request);

	public boolean isSun(HttpServletRequest request);

	public boolean isWebKit(HttpServletRequest request);

	public boolean isWindows(HttpServletRequest request);

}