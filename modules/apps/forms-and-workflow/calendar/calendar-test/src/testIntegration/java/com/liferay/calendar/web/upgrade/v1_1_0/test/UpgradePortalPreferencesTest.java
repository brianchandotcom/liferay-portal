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

package com.liferay.calendar.web.upgrade.v1_1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.test.util.CalendarUpgradeTestUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PortalPreferences;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.impl.PortalPreferencesImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class UpgradePortalPreferencesTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		_portalPreferences =
			PortalPreferencesLocalServiceUtil.addPortalPreferences(
				_user.getUserId(), PortletKeys.PREFS_OWNER_TYPE_USER, "");

		setUpUpgradePortalPreferences();
	}

	@Test
	public void testUpgradeCalendarColorPreferences() throws Exception {
		long calendarId = RandomTestUtil.randomLong();
		long color = RandomTestUtil.randomLong();

		String preferencesXML = getPreferences(
			_NAMESPACE_OLD_SESSION_CLICKS,
			"calendar-portlet-calendar-" + calendarId + "-color", color);

		_portalPreferences.setPreferences(preferencesXML);

		PortalPreferencesLocalServiceUtil.updatePortalPreferences(
			_portalPreferences);

		_upgradeProcess.upgrade();

		PortalPreferences portalPreferences = reloadPortalPreferences(
			_portalPreferences);

		String value = getPreference(
			portalPreferences.getPreferences(), _NAMESPACE_NEW_SESSION_CLICKS,
			"com.liferay.calendar.web_calendar" + calendarId + "Color");

		Assert.assertEquals(String.valueOf(color), value);
	}

	@Test
	public void testUpgradeCalendarVisiblePreferences() throws Exception {
		long calendarId = RandomTestUtil.randomLong();
		boolean visible = RandomTestUtil.randomBoolean();

		String preferencesXML = getPreferences(
			_NAMESPACE_OLD_SESSION_CLICKS,
			"calendar-portlet-calendar-" + calendarId + "-visible", visible);

		_portalPreferences.setPreferences(preferencesXML);

		PortalPreferencesLocalServiceUtil.updatePortalPreferences(
			_portalPreferences);

		_upgradeProcess.upgrade();

		PortalPreferences portalPreferences = reloadPortalPreferences(
			_portalPreferences);

		String value = getPreference(
			portalPreferences.getPreferences(), _NAMESPACE_NEW_SESSION_CLICKS,
			"com.liferay.calendar.web_calendar" + calendarId + "Visible");

		Assert.assertEquals(String.valueOf(visible), value);
	}

	@Test
	public void testUpgradeColumnOptionsVisiblePreferences() throws Exception {
		boolean visible = RandomTestUtil.randomBoolean();

		String preferencesXML = getPreferences(
			_NAMESPACE_OLD_SESSION_CLICKS,
			"calendar-portlet-column-options-visible", visible);

		_portalPreferences.setPreferences(preferencesXML);

		PortalPreferencesLocalServiceUtil.updatePortalPreferences(
			_portalPreferences);

		_upgradeProcess.upgrade();

		PortalPreferences portalPreferences = reloadPortalPreferences(
			_portalPreferences);

		String value = getPreference(
			portalPreferences.getPreferences(), _NAMESPACE_NEW_SESSION_CLICKS,
			"com.liferay.calendar.web_columnOptionsVisible");

		Assert.assertEquals(String.valueOf(visible), value);
	}

	@Test
	public void testUpgradeDefaultViewPreferences() throws Exception {
		String[] views = {"day", "month", "week", "agenda"};

		String view = views[RandomTestUtil.randomInt(0, views.length - 1)];

		String preferencesXML = getPreferences(
			_NAMESPACE_OLD_SESSION_CLICKS, "calendar-portlet-default-view",
			view);

		_portalPreferences.setPreferences(preferencesXML);

		PortalPreferencesLocalServiceUtil.updatePortalPreferences(
			_portalPreferences);

		_upgradeProcess.upgrade();

		PortalPreferences portalPreferences = reloadPortalPreferences(
			_portalPreferences);

		String value = getPreference(
			portalPreferences.getPreferences(), _NAMESPACE_NEW_SESSION_CLICKS,
			"com.liferay.calendar.web_defaultView");

		Assert.assertEquals(view, value);
	}

	@Test
	public void testUpgradeOtherCalendarsPreferences() throws Exception {
		long otherCalendarId = RandomTestUtil.randomLong();

		String preferencesXML = getPreferences(
			_NAMESPACE_OLD_SESSION_CLICKS, "calendar-portlet-other-calendars",
			otherCalendarId);

		_portalPreferences.setPreferences(preferencesXML);

		PortalPreferencesLocalServiceUtil.updatePortalPreferences(
			_portalPreferences);

		_upgradeProcess.upgrade();

		PortalPreferences portalPreferences = reloadPortalPreferences(
			_portalPreferences);

		String value = getPreference(
			portalPreferences.getPreferences(), _NAMESPACE_NEW_SESSION_CLICKS,
			"com.liferay.calendar.web_otherCalendars");

		Assert.assertEquals(String.valueOf(otherCalendarId), value);
	}

	protected String getPreference(
			String preferencesXML, String namespace, String name)
		throws DocumentException {

		String value = null;

		Document document = SAXReaderUtil.read(preferencesXML);

		Element rootElement = document.getRootElement();

		Iterator<Element> iterator = rootElement.elementIterator();

		while (iterator.hasNext()) {
			Element preferenceElement = iterator.next();

			String preferenceName = preferenceElement.elementText("name");

			if (preferenceName.equals(namespace + "#" + name)) {
				value = preferenceElement.elementText("value");
			}
		}

		return value;
	}

	protected String getPreferences(
		String namespace, String name, Object value) {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("portlet-preferences");

		Element preferenceElement = rootElement.addElement("preference");

		Element nameElement = preferenceElement.addElement("name");

		nameElement.setText(namespace + "#" + name);

		Element valueElement = preferenceElement.addElement("value");

		valueElement.setText(String.valueOf(value));

		return document.asXML();
	}

	protected PortalPreferences reloadPortalPreferences(
			PortalPreferences portalPreferences)
		throws PortalException {

		EntityCacheUtil.clearCache(PortalPreferencesImpl.class);

		portalPreferences =
			PortalPreferencesLocalServiceUtil.getPortalPreferences(
				portalPreferences.getPortalPreferencesId());

		return portalPreferences;
	}

	protected void setUpUpgradePortalPreferences() {
		_upgradeProcess = CalendarUpgradeTestUtil.getWebUpgradeStep(
			"UpgradePortalPreferences");
	}

	private static final String _NAMESPACE_NEW_SESSION_CLICKS =
		"com.liferay.portal.kernel.util.SessionClicks";

	private static final String _NAMESPACE_OLD_SESSION_CLICKS =
		"com.liferay.portal.util.SessionClicks";

	@DeleteAfterTestRun
	private PortalPreferences _portalPreferences;

	private UpgradeProcess _upgradeProcess;

	@DeleteAfterTestRun
	private User _user;

}