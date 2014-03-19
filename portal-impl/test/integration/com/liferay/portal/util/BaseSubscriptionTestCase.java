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

package com.liferay.portal.util;

import com.dumbster.smtp.SmtpMessage;

import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public abstract class BaseSubscriptionTestCase {

	@Before
	public void setUp() throws Exception {
		defaultLocale = LocaleThreadLocal.getDefaultLocale();
		group = GroupTestUtil.addGroup();
		layout = LayoutTestUtil.addLayout(group);
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(group);

		LocaleThreadLocal.setDefaultLocale(defaultLocale);
	}

	@Test
	public void testSubscriptionBaseModelWhenInContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long baseModelId = addBaseModel(containerModelId);

		addSubscriptionBaseModel(baseModelId);

		updateEntry(baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel()
		throws Exception {

		long baseModelId = addBaseModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionBaseModel(baseModelId);

		updateEntry(baseModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionClassType() throws Exception {
		long classTypeId = addClassType();

		addSubscriptionClassType(classTypeId);

		addBaseModelWithClassType(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT, classTypeId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionContainerModelWhenInContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		addBaseModel(containerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionContainerModelWhenInRootContainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		addBaseModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionContainerModelWhenInSubcontainerModel()
		throws Exception {

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addSubscriptionContainerModel(containerModelId);

		long subcontainerModelId = addContainerModel(containerModelId);

		addBaseModel(subcontainerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionDefaultClassType() throws Exception {
		addSubscriptionClassType(_CLASS_TYPE_ID_DEFAULT);

		addBaseModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionLocalizedContent() throws Exception {
		setAddBaseModelSubscriptionBodyPreferences();

		addSubscriptionContainerModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		LocaleThreadLocal.setDefaultLocale(LocaleUtil.GERMANY);

		addBaseModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		List<SmtpMessage> messageList = MailServiceTestUtil.getMessages(
			"Body", _GERMAN_BODY);

		Assert.assertEquals(1, messageList.size());

		LocaleThreadLocal.setDefaultLocale(LocaleUtil.SPAIN);

		addBaseModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		messageList = MailServiceTestUtil.getMessages("Body", _SPANISH_BODY);

		Assert.assertEquals(1, messageList.size());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenInContainerModel()
		throws Exception {

		addSubscriptionContainerModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addBaseModel(containerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenInRootContainerModel()
		throws Exception {

		addSubscriptionContainerModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		addBaseModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionRootContainerModelWhenInSubcontainerModel()
		throws Exception {

		addSubscriptionContainerModel(_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long containerModelId = addContainerModel(
			_PARENT_CONTAINER_MODEL_ID_DEFAULT);

		long subcontainerModelId = addContainerModel(containerModelId);

		addBaseModel(subcontainerModelId);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	protected abstract long addBaseModel(long containerModelId)
		throws Exception;

	protected long addBaseModelWithClassType(
			long containerModelId, long classTypeId)
		throws Exception {

		return 0;
	}

	protected long addClassType() throws Exception {
		return 0;
	}

	protected long addContainerModel(long containerModelId) throws Exception {
		return 0;
	};

	protected void addSubscriptionBaseModel(long baseModelId) throws Exception {
		return;
	}

	protected void addSubscriptionClassType(long classTypeId) throws Exception {
		return;
	}

	protected abstract void addSubscriptionContainerModel(long containerModelId)
		throws Exception;

	protected abstract String getPortletId();

	protected abstract String getSubscriptionBodyPreferenceName()
		throws Exception;

	protected void setAddBaseModelSubscriptionBodyPreferences()
		throws Exception {

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, getPortletId());

		LocalizationUtil.setPreferencesValue(
			jxPortletPreferences, getSubscriptionBodyPreferenceName(),
			LocaleUtil.toLanguageId(LocaleUtil.GERMANY), _GERMAN_BODY);

		LocalizationUtil.setPreferencesValue(
			jxPortletPreferences, getSubscriptionBodyPreferenceName(),
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN), _SPANISH_BODY);

		PortletPreferencesLocalServiceUtil.updatePreferences(
			group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
			PortletKeys.PREFS_PLID_SHARED, getPortletId(),
			jxPortletPreferences);
	}

	protected long updateEntry(long baseModelId) throws Exception {
		return 0;
	};

	protected static final long _PARENT_CONTAINER_MODEL_ID_DEFAULT = 0;

	protected Locale defaultLocale;
	protected Group group;
	protected Layout layout;

	private static final long _CLASS_TYPE_ID_DEFAULT = 0;

	private static final String _GERMAN_BODY = "Hallo Welt";

	private static final String _SPANISH_BODY = "Hola Mundo";

}