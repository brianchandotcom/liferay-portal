/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public boolean acceptsGzip(HttpServletRequest httpServletRequest);

	public String getBrowserId(HttpServletRequest httpServletRequest);

	public BrowserMetadata getBrowserMetadata(
		HttpServletRequest httpServletRequest);

	public float getMajorVersion(HttpServletRequest httpServletRequest);

	public String getRevision(HttpServletRequest httpServletRequest);

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