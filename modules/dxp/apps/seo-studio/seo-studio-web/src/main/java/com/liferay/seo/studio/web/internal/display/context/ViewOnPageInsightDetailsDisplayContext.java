/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.display.context;

import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Noor Najjar
 */
public class ViewOnPageInsightDetailsDisplayContext {

	public ViewOnPageInsightDetailsDisplayContext(
		HttpServletRequest httpServletRequest, String objectEntryExternalReferenceCode,
		ThemeDisplay themeDisplay) {

		_httpServletRequest = httpServletRequest;
		_objectEntryExternalReferenceCode = objectEntryExternalReferenceCode;
		_themeDisplay = themeDisplay;
	}

	public String getBackURL() throws Exception {
		String backURL = ParamUtil.getString(_httpServletRequest, "backURL");

		if (Validator.isNotNull(backURL)) {
			return backURL;
		}

		return PortalUtil.getLayoutFullURL(
			LayoutLocalServiceUtil.getLayoutByFriendlyURL(
				_themeDisplay.getScopeGroupId(), false, "/content-seo"),
			_themeDisplay);
	}

	public Map<String, Object> getReactData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"backURL", getBackURL()
		).put(
			"externalReferenceCode", _objectEntryExternalReferenceCode
		).build();
	}

	private final HttpServletRequest _httpServletRequest;
	private final String _objectEntryExternalReferenceCode;
	private final ThemeDisplay _themeDisplay;

}
