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

package com.liferay.calendar.web.internal.upgrade.v1_0_1;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Bryan Engler
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	@Override
	protected String getUpdatePortletPreferencesWhereClause() {
		StringBundler sb = new StringBundler(5);

		sb.append("(preferences like '%classNameIds%");
		sb.append(
			PortalUtil.getClassNameId(
				"com.liferay.portlet.calendar.model.CalEvent"));
		sb.append("%') or (preferences like '%anyAssetType%");
		sb.append(
			PortalUtil.getClassNameId(
				"com.liferay.portlet.calendar.model.CalEvent"));
		sb.append("%')");

		return sb.toString();
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		_replaceClassNameId(portletPreferences, "anyAssetType");
		_replaceClassNameId(portletPreferences, "classNameIds");

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	private void _replaceClassNameId(
			PortletPreferences portletPreferences, String name)
		throws Exception {

		String[] values = GetterUtil.getStringValues(
			portletPreferences.getValues(name, null));

		ArrayUtil.replace(
			values,
			String.valueOf("com.liferay.portlet.calendar.model.CalEvent"),
			String.valueOf(PortalUtil.getClassNameId(CalendarBooking.class)));

		portletPreferences.setValues(name, values);
	}

}