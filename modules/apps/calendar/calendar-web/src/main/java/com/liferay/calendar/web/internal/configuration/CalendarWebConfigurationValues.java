/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Adam Brandizzi
 */
public class CalendarWebConfigurationValues {

	public static final boolean CALENDAR_SYNC_CALEVENTS_ON_STARTUP =
		GetterUtil.getBoolean(
			CalendarWebConfigurationUtil.get(
				"calendar.sync.calevents.on.startup"));

}