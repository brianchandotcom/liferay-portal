/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet.filters.util;

import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ugurcan Cetin
 */
public class BrowserIdCacheFileNameContributor
	implements CacheFileNameContributor {

	@Override
	public String getParameterName() {
		return "browserId";
	}

	@Override
	public String getParameterValue(HttpServletRequest httpServletRequest) {
		String browserId = ParamUtil.getString(httpServletRequest, "browserId");

		if (ArrayUtil.contains(_BROWSER_IDS, browserId)) {
			return browserId;
		}

		return null;
	}

	private static final String[] _BROWSER_IDS = {
		BrowserSniffer.BROWSER_ID_EDGE, BrowserSniffer.BROWSER_ID_FIREFOX,
		BrowserSniffer.BROWSER_ID_IE, BrowserSniffer.BROWSER_ID_OTHER
	};

}