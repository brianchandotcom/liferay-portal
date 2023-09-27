/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.upgrade.v3_0_0;

import com.liferay.calendar.model.CalendarResource;
import com.liferay.portal.upgrade.util.BaseUpgradeResourceBlock;

/**
 * @author Preston Crary
 */
public class UpgradeCalendarResourceResourceBlock
	extends BaseUpgradeResourceBlock {

	@Override
	protected String getClassName() {
		return CalendarResource.class.getName();
	}

	@Override
	protected String getPrimaryKeyName() {
		return "calendarResourceId";
	}

	@Override
	protected String getTableName() {
		return "CalendarResource";
	}

	@Override
	protected boolean hasUserId() {
		return true;
	}

}