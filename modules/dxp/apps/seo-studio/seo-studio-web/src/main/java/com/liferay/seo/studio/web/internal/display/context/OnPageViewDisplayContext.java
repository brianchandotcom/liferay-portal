/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.seo.studio.web.internal.constants.SEOStudioFDSNames;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Noor Najjar
 */
public class OnPageViewDisplayContext {

	public OnPageViewDisplayContext(
		Long currentScanId, HttpServletRequest httpServletRequest,
		Language language, String lastScanDate, JSONArray viewsJSONArray) {

		_currentScanId = currentScanId;
		_httpServletRequest = httpServletRequest;
		_language = language;
		_lastScanDate = lastScanDate;
		_viewsJSONArray = viewsJSONArray;
	}

	public Map<String, Object> getViewProps() {
		return HashMapBuilder.<String, Object>put(
			"apiURL", _getAPIURL()
		).put(
			"emptyState", _getEmptyState()
		).put(
			"fdsId", SEOStudioFDSNames.INSIGHT_TYPE_SECTION
		).put(
			"lastScanDate", _lastScanDate
		).put(
			"views", _viewsJSONArray
		).build();
	}

	private String _getAPIURL() {
		long filterScanId = (_currentScanId != null) ? _currentScanId : -1L;

		String filterString = URLCodec.encodeURL(
			StringBundler.concat(
				"r_seoStudioScanToSEOStudioInsightTypes_seoStudioScanId eq '",
				filterScanId, "'"),
			true);

		return "/o/seo-studio/insight-types?filter=" + filterString;
	}

	private Map<String, Object> _getEmptyState() {
		if (_currentScanId == null) {
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

	private final Long _currentScanId;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final String _lastScanDate;
	private final JSONArray _viewsJSONArray;

}