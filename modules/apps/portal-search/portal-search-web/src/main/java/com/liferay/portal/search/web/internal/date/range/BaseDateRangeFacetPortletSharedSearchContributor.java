/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.date.range;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.util.DateRangeFactoryUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseDateRangeFacetPortletSharedSearchContributor {

	protected void addCustomRange(
		JSONArray rangesJSONArray, String selectedCustomRangeString,
		List<String> selectedRangeStrings) {

		rangesJSONArray.put(
			JSONUtil.put(
				"label", "custom-range"
			).put(
				"range", selectedCustomRangeString
			));

		selectedRangeStrings.add(selectedCustomRangeString);
	}

	protected JSONArray getRangesJSONArray(
		Calendar calendar, JSONArray rangesJSONArray) {

		JSONArray unAliasedRangesJSONArray =
			DateRangeFactoryUtil.replaceAliases(
				rangesJSONArray, CalendarFactoryUtil.getCalendar());

		if (unAliasedRangesJSONArray == null) {
			return DateRangeFactoryUtil.getDefaultRangesJSONArray(calendar);
		}

		return unAliasedRangesJSONArray;
	}

	protected String getSelectedCustomRangeString(
		String parameterName,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		String customRangeFrom = portletSharedSearchSettings.getParameter(
			parameterName + "From");

		String customRangeTo = portletSharedSearchSettings.getParameter(
			parameterName + "To");

		if (!Validator.isBlank(customRangeFrom) &&
			!Validator.isBlank(customRangeTo)) {

			SearchContext searchContext =
				portletSharedSearchSettings.getSearchContext();

			return DateRangeFactoryUtil.getRangeString(
				customRangeFrom, customRangeTo, searchContext.getTimeZone());
		}

		return null;
	}

	protected List<String> getSelectedRangeStrings(
		String parameterName,
		PortletSharedSearchSettings portletSharedSearchSettings,
		JSONArray rangesJSONArray) {

		String[] selectedRanges =
			portletSharedSearchSettings.getParameterValues(parameterName);

		List<String> selectedRangeStrings = new ArrayList<>();

		if (ArrayUtil.isEmpty(selectedRanges)) {
			return selectedRangeStrings;
		}

		Map<String, String> rangesMap = _getRangesMap(rangesJSONArray);

		for (String selectedRange : selectedRanges) {
			if (rangesMap.containsKey(selectedRange)) {
				selectedRangeStrings.add(rangesMap.get(selectedRange));
			}
			else {
				String rangeString = DateRangeFactoryUtil.getRangeString(
					selectedRange, CalendarFactoryUtil.getCalendar());

				if (!Validator.isBlank(rangeString)) {
					selectedRangeStrings.add(rangeString);
				}
			}
		}

		return selectedRangeStrings;
	}

	private Map<String, String> _getRangesMap(JSONArray rangesJSONArray) {
		Map<String, String> rangesMap = new HashMap<>();

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			JSONObject rangeJSONObject = rangesJSONArray.getJSONObject(i);

			rangesMap.put(
				rangeJSONObject.getString("label"),
				rangeJSONObject.getString("range"));
		}

		return rangesMap;
	}

}