/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.util;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;

/**
 * @author Cristina González
 */
public class PortletPreferencesTestUtil {

	public static PortletPreferences addGroupPortletPreferences(
			Layout layout, Portlet portlet)
		throws Exception {

		return addGroupPortletPreferences(layout, portlet, null);
	}

	public static PortletPreferences addGroupPortletPreferences(
			Layout layout, Portlet portlet, String defaultPreferences)
		throws Exception {

		return PortletPreferencesLocalServiceUtil.addPortletPreferences(
			layout.getCompanyId(), layout.getGroupId(),
			PortletKeys.PREFS_OWNER_TYPE_GROUP, layout.getPlid(),
			portlet.getPortletId(), portlet, defaultPreferences);
	}

	public static PortletPreferences addLayoutPortletPreferences(
			Layout layout, Portlet portlet)
		throws Exception {

		return addLayoutPortletPreferences(layout, portlet, null);
	}

	public static PortletPreferences addLayoutPortletPreferences(
			Layout layout, Portlet portlet, String defaultPreferences)
		throws Exception {

		return PortletPreferencesLocalServiceUtil.addPortletPreferences(
			TestPropsValues.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			portlet.getPortletId(), portlet, defaultPreferences);
	}

	public static javax.portlet.PortletPreferences
			fetchLayoutJxPortletPreferences(
				Layout layout, Portlet portlet)
		throws Exception {

		return PortletPreferencesLocalServiceUtil.fetchPreferences(
			TestPropsValues.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			portlet.getPortletId());
	}

	public static String getPortletPreferencesXML(
		String name, String[] values) {

		StringBundler sb = new StringBundler(7 + (values.length * 3));

		sb.append("<portlet-preferences>");
		sb.append("<preference>");
		sb.append("<name>");
		sb.append(name);
		sb.append("</name>");

		for (String value : values) {
			sb.append("<value>");
			sb.append(value);
			sb.append("</value>");
		}

		sb.append("</preference>");
		sb.append("</portlet-preferences>");

		return sb.toString();
	}

	public static PortletPreferencesImpl toPortletPreferencesImpl(
			PortletPreferences portletPreferences)
		throws Exception {

		return (PortletPreferencesImpl)PortletPreferencesFactoryUtil.fromXML(
			TestPropsValues.getCompanyId(), portletPreferences.getOwnerId(),
			portletPreferences.getOwnerType(), portletPreferences.getPlid(),
			portletPreferences.getPortletId(),
			portletPreferences.getPreferences());
	}

}