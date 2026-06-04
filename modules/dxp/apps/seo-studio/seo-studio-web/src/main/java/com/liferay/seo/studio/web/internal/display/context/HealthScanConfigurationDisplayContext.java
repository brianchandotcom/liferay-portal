/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.display.context;

import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneComparator;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.text.NumberFormat;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

/**
 * @author Jonathan McCann
 */
public class HealthScanConfigurationDisplayContext {

	public HealthScanConfigurationDisplayContext(
		HttpServletRequest httpServletRequest,
		ObjectEntry seoStudioDomainObjectEntry) {

		_seoStudioDomainObjectEntry = seoStudioDomainObjectEntry;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getViewProps() {
		return HashMapBuilder.<String, Object>put(
			"defaultTimeZoneId", _getDefaultTimeZoneId()
		).put(
			"domainId", _getDomainId()
		).put(
			"scanConfig", _getScanConfig()
		).put(
			"schedule", _getScheduleJSONObject()
		).put(
			"timeZones", _getTimeZonesJSONArray()
		).build();
	}

	private String _getDefaultTimeZoneId() {
		TimeZone timeZone = _themeDisplay.getTimeZone();

		if (timeZone == null) {
			return "UTC";
		}

		return timeZone.getID();
	}

	private Long _getDomainId() {
		if (_seoStudioDomainObjectEntry == null) {
			return null;
		}

		return _seoStudioDomainObjectEntry.getId();
	}

	private String _getListTypeEntryKey(Object value) {
		ListEntry listEntry = (ListEntry)value;

		if (listEntry == null) {
			return null;
		}

		return listEntry.getKey();
	}

	private String _getScanConfig() {
		if (_seoStudioDomainObjectEntry == null) {
			return null;
		}

		Map<String, Object> properties =
			_seoStudioDomainObjectEntry.getProperties();

		return (String)properties.get("scanConfig");
	}

	private JSONObject _getScheduleJSONObject() {
		if (_seoStudioDomainObjectEntry == null) {
			return null;
		}

		Map<String, Object> properties =
			_seoStudioDomainObjectEntry.getProperties();

		return JSONUtil.put(
			"autoScanEnabled", properties.get("autoScanEnabled")
		).put(
			"scanDayOfMonth", properties.get("scanDayOfMonth")
		).put(
			"scanDayOfWeek",
			_getListTypeEntryKey(properties.get("scanDayOfWeek"))
		).put(
			"scanFrequency",
			_getListTypeEntryKey(properties.get("scanFrequency"))
		).put(
			"scanTime", properties.get("scanTime")
		).put(
			"scanTimeZone", properties.get("scanTimeZone")
		);
	}

	private JSONArray _getTimeZonesJSONArray() {
		JSONArray timeZonesJSONArray = JSONFactoryUtil.createJSONArray();

		Date date = new Date();

		Locale locale = _themeDisplay.getLocale();

		NumberFormat numberFormat = NumberFormat.getInstance(locale);

		numberFormat.setMinimumIntegerDigits(2);

		Set<TimeZone> timeZones = new TreeSet<>(new TimeZoneComparator());

		for (String timeZoneId : PropsUtil.getArray(PropsKeys.TIME_ZONES)) {
			timeZones.add(TimeZoneUtil.getTimeZone(timeZoneId));
		}

		for (TimeZone timeZone : timeZones) {
			int totalOffset = timeZone.getOffset(date.getTime());

			String offset = StringPool.BLANK;

			if (totalOffset != 0) {
				StringBundler sb = new StringBundler(5);

				sb.append(StringPool.SPACE);

				if (totalOffset > 0) {
					sb.append(StringPool.PLUS);
				}

				sb.append(numberFormat.format(totalOffset / Time.HOUR));
				sb.append(StringPool.COLON);
				sb.append(
					numberFormat.format(
						Math.abs(totalOffset % Time.HOUR) / Time.MINUTE));

				offset = sb.toString();
			}

			String timeZoneId = timeZone.getID();

			String label = StringBundler.concat(
				"(UTC", offset, ") ",
				timeZone.getDisplayName(
					timeZone.inDaylightTime(date), TimeZone.LONG, locale));

			if (timeZoneId.equals("America/Phoenix")) {
				label = StringBundler.concat(
					label, StringPool.SPACE, StringPool.OPEN_PARENTHESIS,
					timeZoneId, StringPool.CLOSE_PARENTHESIS);
			}

			timeZonesJSONArray.put(
				JSONUtil.put(
					"label", label
				).put(
					"value", timeZoneId
				));
		}

		return timeZonesJSONArray;
	}

	private final ObjectEntry _seoStudioDomainObjectEntry;
	private final ThemeDisplay _themeDisplay;

}