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

package com.liferay.portlet.preferences.test;

import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;

/**
 * @author Cristina González
 */
public abstract class BasePortletPreferencesTestCase {

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		testLayout = LayoutTestUtil.addTypePortletLayout(testGroup);

		testPortlet = portletLocalService.getPortletById(
			testLayout.getCompanyId(), getPortletId());
	}

	protected PortletPreferences addGroupPortletPreferences(
		Layout layout, Portlet portlet) {

		return addGroupPortletPreferences(layout, portlet, null);
	}

	protected PortletPreferences addGroupPortletPreferences(
		Layout layout, Portlet portlet, String defaultPreferences) {

		return portletPreferencesLocalService.addPortletPreferences(
			layout.getCompanyId(), layout.getGroupId(),
			PortletKeys.PREFS_OWNER_TYPE_GROUP, layout.getPlid(),
			portlet.getPortletId(), portlet, defaultPreferences);
	}

	protected PortletPreferences addLayoutPortletPreferences(
			Layout layout, Portlet portlet)
		throws Exception {

		return addLayoutPortletPreferences(layout, portlet, null);
	}

	protected PortletPreferences addLayoutPortletPreferences(
			Layout layout, Portlet portlet, String defaultPreferences)
		throws Exception {

		return portletPreferencesLocalService.addPortletPreferences(
			TestPropsValues.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			portlet.getPortletId(), portlet, defaultPreferences);
	}

	protected javax.portlet.PortletPreferences fetchLayoutJxPortletPreferences(
			Layout layout, Portlet portlet)
		throws Exception {

		return portletPreferencesLocalService.fetchPreferences(
			TestPropsValues.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			portlet.getPortletId());
	}

	protected abstract String getPortletId();

	protected String getPortletPreferencesXML(String name, String[] values) {
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

	@Inject
	protected PortletLocalService portletLocalService;

	@Inject
	protected PortletPreferencesFactory portletPreferencesFactory;

	@Inject
	protected PortletPreferencesLocalService portletPreferencesLocalService;

	@DeleteAfterTestRun
	protected Group testGroup;

	@DeleteAfterTestRun
	protected Layout testLayout;

	@DeleteAfterTestRun
	protected Portlet testPortlet;

}