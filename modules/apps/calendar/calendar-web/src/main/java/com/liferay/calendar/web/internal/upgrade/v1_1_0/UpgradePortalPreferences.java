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

package com.liferay.calendar.web.internal.upgrade.v1_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.PortalPreferencesUpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bryan Engler
 */
public class UpgradePortalPreferences extends PortalPreferencesUpgradeProcess {

	public UpgradePortalPreferences() {
		_preferenceNamesMap.put(
			_NAMESPACE_OLD_SESSION_CLICKS +
				"calendar-portlet-column-options-visible",
			_NAMESPACE_NEW_SESSION_CLICKS +
				"com.liferay.calendar.web_columnOptionsVisible");
		_preferenceNamesMap.put(
			_NAMESPACE_OLD_SESSION_CLICKS + "calendar-portlet-default-view",
			_NAMESPACE_NEW_SESSION_CLICKS +
				"com.liferay.calendar.web_defaultView");
		_preferenceNamesMap.put(
			_NAMESPACE_OLD_SESSION_CLICKS + "calendar-portlet-other-calendars",
			_NAMESPACE_NEW_SESSION_CLICKS +
				"com.liferay.calendar.web_otherCalendars");
	}

	@Override
	protected void doUpgrade() throws Exception {
		populatePreferenceNamesMap();

		super.doUpgrade();
	}

	protected String getNewPreferenceName(String preferenceName) {
		for (Pattern pattern : _oldPreferencePatterns) {
			Matcher matcher = pattern.matcher(preferenceName);

			if (matcher.matches()) {
				Matcher idMatcher = _idPattern.matcher(preferenceName);
				Matcher preferenceMatcher = _preferencePattern.matcher(
					preferenceName);

				idMatcher.find();
				preferenceMatcher.find();

				String id = idMatcher.group(0);
				String preference = preferenceMatcher.group(0);

				return StringUtil.replace(
					_newPreferencePatternsMap.get(preference), "{calendarId}",
					id);
			}
		}

		return null;
	}

	@Override
	protected Map<String, String> getPreferenceNamesMap() {
		return _preferenceNamesMap;
	}

	protected void populatePreferenceNamesMap() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select key_ from PortalPreferenceValue where namespace = ",
					"'com.liferay.portal.util.SessionClicks' and key_ like ",
					"'calendar-%'"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String preferenceName =
					_NAMESPACE_OLD_SESSION_CLICKS + resultSet.getString("key_");

				String newPreferenceName = null;

				if (!_preferenceNamesMap.containsKey(preferenceName)) {
					newPreferenceName = getNewPreferenceName(preferenceName);
				}

				if (newPreferenceName != null) {
					_preferenceNamesMap.put(preferenceName, newPreferenceName);
				}
			}
		}
	}

	private static final String _NAMESPACE_NEW_SESSION_CLICKS =
		"com.liferay.portal.kernel.util.SessionClicks#";

	private static final String _NAMESPACE_OLD_SESSION_CLICKS =
		"com.liferay.portal.util.SessionClicks#";

	private static final Pattern _idPattern = Pattern.compile("[0-9]+");
	private static final Map<String, String> _newPreferencePatternsMap =
		HashMapBuilder.put(
			"color",
			_NAMESPACE_NEW_SESSION_CLICKS +
				"com.liferay.calendar.web_calendar{calendarId}Color"
		).put(
			"visible",
			_NAMESPACE_NEW_SESSION_CLICKS +
				"com.liferay.calendar.web_calendar{calendarId}Visible"
		).build();
	private static final Pattern[] _oldPreferencePatterns = {
		Pattern.compile(
			_NAMESPACE_OLD_SESSION_CLICKS +
				"calendar-portlet-calendar-[0-9]+-color"),
		Pattern.compile(
			_NAMESPACE_OLD_SESSION_CLICKS +
				"calendar-portlet-calendar-[0-9]+-visible")
	};
	private static final Pattern _preferencePattern = Pattern.compile(
		"[a-z]+$");

	private final Map<String, String> _preferenceNamesMap = new HashMap<>();

}