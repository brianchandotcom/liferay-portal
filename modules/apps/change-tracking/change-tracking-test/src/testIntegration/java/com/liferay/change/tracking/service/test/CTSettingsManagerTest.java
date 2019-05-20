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

package com.liferay.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.settings.CTSettingsManager;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gergely Mathe
 */
@RunWith(Arquillian.class)
public class CTSettingsManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addUser(_company);
	}

	@Test
	public void testFallBackToGlobalSetting() throws Exception {
		String key = RandomTestUtil.randomString();
		String globalValue = RandomTestUtil.randomString();

		PortletPreferences portletPreferences = _prefsProps.getPreferences(
			_company.getCompanyId());

		portletPreferences.setValue(key, globalValue);

		portletPreferences.store();

		String userValue = _ctSettingsManager.getUserCTSetting(
			_user.getUserId(), key);

		Assert.assertEquals(
			"Value should be " + globalValue, userValue, globalValue);
	}

	@Test
	public void testFallBackToGlobalSettingWithDefaultValue() throws Exception {
		String key = RandomTestUtil.randomString();
		String globalValue = RandomTestUtil.randomString();

		PortletPreferences portletPreferences = _prefsProps.getPreferences(
			_company.getCompanyId());

		portletPreferences.setValue(key, globalValue);

		portletPreferences.store();

		String userValue = _ctSettingsManager.getUserCTSetting(
			_user.getUserId(), key, RandomTestUtil.randomString());

		Assert.assertEquals(
			"Value should be " + globalValue, userValue, globalValue);
	}

	@Test
	public void testFallBackWithDefaultValue() {
		String key = RandomTestUtil.randomString();
		String defaultValue = RandomTestUtil.randomString();

		String userValue = _ctSettingsManager.getUserCTSetting(
			_user.getUserId(), key, defaultValue);

		Assert.assertEquals(
			"Value should be " + defaultValue, userValue, defaultValue);
	}

	@Test
	public void testGetGlobalCTSettingWithDefaultValue() {
		String defaultValue = RandomTestUtil.randomString();

		String value = _ctSettingsManager.getGlobalCTSetting(
			_company.getCompanyId(), RandomTestUtil.randomString(),
			defaultValue);

		Assert.assertEquals(
			"Value should be " + defaultValue, value, defaultValue);
	}

	@Test
	public void testGetGlobalCTSettingWithoutDefaultValue() {
		String value = _ctSettingsManager.getGlobalCTSetting(
			_company.getCompanyId(), RandomTestUtil.randomString());

		Assert.assertNull("Value should be null", value);
	}

	@Test
	public void testGetUserCTSettingWithDefaultValue() {
		String defaultValue = RandomTestUtil.randomString();

		String value = _ctSettingsManager.getUserCTSetting(
			_user.getUserId(), RandomTestUtil.randomString(), defaultValue);

		Assert.assertEquals(
			"Value should be " + defaultValue, value, defaultValue);
	}

	@Test
	public void testGetUserCTSettingWithoutDefaultValue() {
		String value = _ctSettingsManager.getUserCTSetting(
			_user.getUserId(), RandomTestUtil.randomString());

		Assert.assertNull("Value should be null", value);
	}

	@Test
	public void testSetGlobalCTSetting() {
		String key = RandomTestUtil.randomString();
		String value1 = RandomTestUtil.randomString();

		_ctSettingsManager.setGlobalCTSetting(
			_company.getCompanyId(), key, value1);

		PortletPreferences portletPreferences = _prefsProps.getPreferences(
			_company.getCompanyId());

		String value = portletPreferences.getValue(key, null);

		Assert.assertEquals("Value should be " + value1, value, value1);

		String value2 = RandomTestUtil.randomString();

		_ctSettingsManager.setGlobalCTSetting(
			_company.getCompanyId(), key, value2);

		portletPreferences = _prefsProps.getPreferences(
			_company.getCompanyId());

		value = portletPreferences.getValue(key, null);

		Assert.assertEquals("Value should be " + value2, value, value2);
	}

	@Test
	public void testSetUserCTSetting() {
		String key = RandomTestUtil.randomString();
		String value1 = RandomTestUtil.randomString();

		_ctSettingsManager.setUserCTSetting(_user.getUserId(), key, value1);

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(
				_user.getUserId(), !_user.isDefaultUser());

		String value = portalPreferences.getValue(
			CTPortletKeys.CHANGE_LISTS, key);

		Assert.assertEquals("Value should be " + value1, value, value1);

		String value2 = RandomTestUtil.randomString();

		_ctSettingsManager.setUserCTSetting(_user.getUserId(), key, value2);

		portalPreferences = _portletPreferencesFactory.getPortalPreferences(
			_user.getUserId(), !_user.isDefaultUser());

		value = portalPreferences.getValue(CTPortletKeys.CHANGE_LISTS, key);

		Assert.assertEquals("Value should be " + value2, value, value2);
	}

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private CTSettingsManager _ctSettingsManager;

	@Inject
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Inject
	private PrefsProps _prefsProps;

	@DeleteAfterTestRun
	private User _user;

}