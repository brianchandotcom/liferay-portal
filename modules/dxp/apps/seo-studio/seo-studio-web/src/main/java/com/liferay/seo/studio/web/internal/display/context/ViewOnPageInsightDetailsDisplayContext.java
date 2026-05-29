/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.seo.studio.web.internal.constants.SEOStudioFDSNames;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Noor Najjar
 */
public class ViewOnPageInsightDetailsDisplayContext {

	public ViewOnPageInsightDetailsDisplayContext(
		HttpServletRequest httpServletRequest, Language language,
		ThemeDisplay themeDisplay, JSONArray viewsJSONArray) {

		_httpServletRequest = httpServletRequest;
		_language = language;
		_themeDisplay = themeDisplay;
		_viewsJSONArray = viewsJSONArray;
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
			"apiURL", _getAPIURL()
		).put(
			"backURL", getBackURL()
		).put(
			"externalReferenceCode", _getObjectEntryExternalReferenceCode()
		).put(
			"fdsId", SEOStudioFDSNames.AFFECTED_PAGES_SECTION
		).put(
			"screenName", _language.get(_httpServletRequest, "on-page")
		).put(
			"views", _viewsJSONArray
		).build();
	}

	private String _getAPIURL() {
		return StringBundler.concat(
			"/o/seo-studio/insight-types/by-external-reference-code/",
			URLCodec.encodeURL(_getObjectEntryExternalReferenceCode(), true),
			"/seoStudioInsightTypeToScanInsights?nestedFields=",
			URLCodec.encodeURL(
				"r_seoStudioPageToSEOStudioScanInsights_seoStudioPage", true));
	}

	private String _getObjectEntryExternalReferenceCode() {
		return ParamUtil.getString(
			_httpServletRequest, "objectEntryExternalReferenceCode");
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final ThemeDisplay _themeDisplay;
	private final JSONArray _viewsJSONArray;

}
