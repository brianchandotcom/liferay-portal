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

package com.liferay.portal.service.util.test;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;

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
			fetchLayoutJxPortletPreferences(Layout layout, Portlet portlet)
		throws Exception {

		return PortletPreferencesLocalServiceUtil.fetchPreferences(
			TestPropsValues.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			portlet.getPortletId());
	}

	public static String getPortletPreferencesXML() {
		return getPortletPreferencesXML(null, null);
	}

	public static String getPortletPreferencesXML(String name) {
		return getPortletPreferencesXML(name, null);
	}

	public static String getPortletPreferencesXML(
		String name, String[] values) {

		StringBundler sb = new StringBundler();

		sb.append("<portlet-preferences>");

		if ((name != null) || (values != null)) {
			sb.append("<preference>");

			if (name != null) {
				sb.append("<name>");
				sb.append(name);
				sb.append("</name>");
			}

			if (values != null) {
				for (String value : values) {
					sb.append("<value>");
					sb.append(value);
					sb.append("</value>");
				}
			}

			sb.append("</preference>");
		}

		sb.append("</portlet-preferences>");

		return sb.toString();
	}

	public static String getPortletPreferencesXML(String[] values) {
		return getPortletPreferencesXML(null, values);
	}

}