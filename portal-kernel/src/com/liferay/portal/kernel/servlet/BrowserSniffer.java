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

import org.osgi.annotation.versioning.ProviderType;

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

	public boolean acceptsGzip(HttpServletRequest httpServletRequest);

	public String getBrowserId(HttpServletRequest httpServletRequest);

	public BrowserMetadata getBrowserMetadata(
		HttpServletRequest httpServletRequest);

	/**
	 * Returns the browser's version number as a float. This differs from {@link
	 * BrowserSniffer#getVersion(HttpServletRequest)}, which returns the version
	 * number as a String.
	 *
	 * <p>
	 * Note that the version returned is defined as the real version of the
	 * browser software, not the one used to render the page. For example, the
	 * browser can be IE10 but it may be using a compatibility view emulating
	 * IE8 to render the page. In such a case, this method would return
	 * <code>10.0</code>, not <code>8.0</code>.
	 * </p>
	 *
	 * @param  httpServletRequest the servlet request
	 * @return a float representing the version number
	 */
	public float getMajorVersion(HttpServletRequest httpServletRequest);

	/**
	 * Returns the browser's revision.
	 *
	 * <p>
	 * Note that the revision returned is defined as the real revision of the
	 * browser software, not the one used to render the page. For example, the
	 * browser can be IE10 but it may be using a compatibility view emulating
	 * IE8 to render the page. In such a case, this method would return
	 * <code>10.0</code>, not <code>8.0</code>.
	 * </p>
	 *
	 * @param  httpServletRequest the servlet request
	 * @return a String containing the revision number
	 */
	public String getRevision(HttpServletRequest httpServletRequest);

	/**
	 * Returns the browser's version.
	 *
	 * <p>
	 * Note that the version returned is defined as the real version of the
	 * browser software, not the one used to render the page. For example, the
	 * browser can be an IE10 but it may be using a compatibility view emulating
	 * IE8 to render the page. In such a case, this method would return
	 * <code>10.0</code>, not <code>8.0</code>.
	 * </p>
	 *
	 * @param  httpServletRequest the servlet request
	 * @return a String containing the version number
	 */
	public String getVersion(HttpServletRequest httpServletRequest);

	public boolean isAir(HttpServletRequest httpServletRequest);

	public boolean isAndroid(HttpServletRequest httpServletRequest);

	public boolean isChrome(HttpServletRequest httpServletRequest);

	public boolean isEdge(HttpServletRequest httpServletRequest);

	public boolean isFirefox(HttpServletRequest httpServletRequest);

	public boolean isGecko(HttpServletRequest httpServletRequest);

	public boolean isIe(HttpServletRequest httpServletRequest);

	public boolean isIeOnWin32(HttpServletRequest httpServletRequest);

	public boolean isIeOnWin64(HttpServletRequest httpServletRequest);

	public boolean isIphone(HttpServletRequest httpServletRequest);

	public boolean isLinux(HttpServletRequest httpServletRequest);

	public boolean isMac(HttpServletRequest httpServletRequest);

	public boolean isMobile(HttpServletRequest httpServletRequest);

	public boolean isMozilla(HttpServletRequest httpServletRequest);

	public boolean isOpera(HttpServletRequest httpServletRequest);

	public boolean isRtf(HttpServletRequest httpServletRequest);

	public boolean isSafari(HttpServletRequest httpServletRequest);

	public boolean isSun(HttpServletRequest httpServletRequest);

	public boolean isWebKit(HttpServletRequest httpServletRequest);

	public boolean isWindows(HttpServletRequest httpServletRequest);

}