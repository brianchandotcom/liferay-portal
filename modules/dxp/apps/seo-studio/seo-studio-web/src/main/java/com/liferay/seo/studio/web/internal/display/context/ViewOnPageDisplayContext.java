/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.display.context;

import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.seo.studio.web.internal.constants.SEOStudioFDSNames;

import jakarta.servlet.http.HttpServletRequest;

import java.text.Format;

import java.util.Date;
import java.util.Map;

/**
 * @author Noor Najjar
 */
public class ViewOnPageDisplayContext {

	public ViewOnPageDisplayContext(
		HttpServletRequest httpServletRequest, Language language,
		ObjectEntry objectEntry, JSONArray viewsJSONArray) {

		_httpServletRequest = httpServletRequest;
		_language = language;
		_objectEntry = objectEntry;
		_viewsJSONArray = viewsJSONArray;
	}

	public Map<String, Object> getReactData() {
		String lastScanDateString = null;

		if (_objectEntry != null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
				"MMM d, yyyy 'at' h:mm a", themeDisplay.getLocale(),
				themeDisplay.getTimeZone());

			Map<String, Object> properties = _objectEntry.getProperties();

			Date requestDate = GetterUtil.getDate(
				properties.get("requestDate"),
				DateFormatFactoryUtil.getSimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
				null);

			lastScanDateString = format.format(requestDate);
		}

		return HashMapBuilder.<String, Object>put(
			"apiURL", _getAPIURL()
		).put(
			"emptyState", _getEmptyState()
		).put(
			"fdsId", SEOStudioFDSNames.INSIGHT_TYPE_SECTION
		).put(
			"lastScanDate", lastScanDateString
		).put(
			"views", _viewsJSONArray
		).build();
	}

	private String _getAPIURL() {
		long seoStudioScanId =
			(_objectEntry != null) ? _objectEntry.getId() : -1L;

		String filterString = URLCodec.encodeURL(
			StringBundler.concat(
				"r_seoStudioScanToSEOStudioInsightTypes_seoStudioScanId eq '",
				seoStudioScanId, "'"),
			true);

		return "/o/seo-studio/insight-types?filter=" + filterString;
	}

	private Map<String, Object> _getEmptyState() {
		if (_objectEntry == null) {
			return HashMapBuilder.<String, Object>put(
				"description",
				_language.get(_httpServletRequest, "run-a-scan-to-see-insights")
			).put(
				"title", _language.get(_httpServletRequest, "no-scans-yet")
			).build();
		}

		return HashMapBuilder.<String, Object>put(
			"description",
			_language.get(
				_httpServletRequest, "there-are-no-insights-for-the-last-scan")
		).put(
			"title", _language.get(_httpServletRequest, "no-insights-found")
		).build();
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final ObjectEntry _objectEntry;
	private final JSONArray _viewsJSONArray;

}