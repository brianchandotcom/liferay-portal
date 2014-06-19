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

package com.liferay.portal.util.subscriptions;

import com.dumbster.smtp.MailMessage;

import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.util.test.MailServiceTestUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public abstract class BaseSubscriptionLocalizedContentTestCase
	extends BaseSubscriptionTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		defaultLocale = LocaleThreadLocal.getDefaultLocale();
		layout = LayoutTestUtil.addLayout(group);
	}

	@After
	public void tearDown() throws Exception {
		LocaleThreadLocal.setDefaultLocale(defaultLocale);
	}

	@Test
	public void testSubscriptionLocalizedContent() throws Exception {
		setAddBaseModelSubscriptionBodyPreferences();

		addSubscriptionContainerModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		LocaleThreadLocal.setDefaultLocale(LocaleUtil.GERMANY);

		addBaseModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		List<MailMessage> messages = MailServiceTestUtil.getMailMessages(
			"Body", GERMAN_BODY);

		Assert.assertEquals(1, messages.size());

		LocaleThreadLocal.setDefaultLocale(LocaleUtil.SPAIN);

		addBaseModel(PARENT_CONTAINER_MODEL_ID_DEFAULT);

		messages = MailServiceTestUtil.getMailMessages("Body", SPANISH_BODY);

		Assert.assertEquals(1, messages.size());
	}

	protected abstract void addSubscriptionContainerModel(long containerModelId)
		throws Exception;

	protected abstract String getPortletId();

	protected abstract String getSubscriptionBodyPreferenceName()
		throws Exception;

	protected void setAddBaseModelSubscriptionBodyPreferences()
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, getPortletId());

		LocalizationUtil.setPreferencesValue(
			portletPreferences, getSubscriptionBodyPreferenceName(),
			LocaleUtil.toLanguageId(LocaleUtil.GERMANY), GERMAN_BODY);
		LocalizationUtil.setPreferencesValue(
			portletPreferences, getSubscriptionBodyPreferenceName(),
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN), SPANISH_BODY);

		PortletPreferencesLocalServiceUtil.updatePreferences(
			group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
			PortletKeys.PREFS_PLID_SHARED, getPortletId(), portletPreferences);
	}

	protected static final String GERMAN_BODY = "Hallo Welt";

	protected static final String SPANISH_BODY = "Hola Mundo";

	protected Locale defaultLocale;
	protected Layout layout;

}