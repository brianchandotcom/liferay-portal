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

package com.liferay.portal.security.sso.opensso.internal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.opensso.constants.LegacyOpenSSOPropsKeys;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOConfigurationKeys;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.test.util.BaseCompanySettingsVerifyProcessTestCase;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Greenwald
 */
@RunWith(Arquillian.class)
public class OpenSSOCompanySettingsVerifyProcessTest
	extends BaseCompanySettingsVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected void doVerify(
		PortletPreferences portletPreferences, Settings settings) {

		for (String key : LegacyOpenSSOPropsKeys.OPENSSO_KEYS) {
			Assert.assertTrue(
				Validator.isNull(
					portletPreferences.getValue(key, StringPool.BLANK)));
		}

		Assert.assertEquals(
			"testEmailAddressAttr",
			settings.getValue(
				OpenSSOConfigurationKeys.EMAIL_ADDRESS_ATTR, StringPool.BLANK));
		Assert.assertTrue(
			GetterUtil.getBoolean(
				settings.getValue(
					OpenSSOConfigurationKeys.AUTH_ENABLED, StringPool.FALSE)));
		Assert.assertEquals(
			"testFirstNameAttr",
			settings.getValue(
				OpenSSOConfigurationKeys.FIRST_NAME_ATTR, StringPool.BLANK));
		Assert.assertTrue(
			GetterUtil.getBoolean(
				settings.getValue(
					OpenSSOConfigurationKeys.IMPORT_FROM_LDAP,
					StringPool.FALSE)));
		Assert.assertEquals(
			"testLastNameAttr",
			settings.getValue(
				OpenSSOConfigurationKeys.LAST_NAME_ATTR, StringPool.BLANK));
		Assert.assertEquals(
			"http://test.com/login/url",
			settings.getValue(
				OpenSSOConfigurationKeys.LOGIN_URL, StringPool.BLANK));
		Assert.assertTrue(
			GetterUtil.getBoolean(
				settings.getValue(
					OpenSSOConfigurationKeys.LOGOUT_ON_SESSION_EXPIRATION,
					StringPool.FALSE)));
		Assert.assertEquals(
			"http://test.com/logout/url",
			settings.getValue(
				OpenSSOConfigurationKeys.LOGOUT_URL, StringPool.BLANK));
		Assert.assertEquals(
			"testScreenNameAttr",
			settings.getValue(
				OpenSSOConfigurationKeys.SCREEN_NAME_ATTR, StringPool.BLANK));
		Assert.assertEquals(
			"http://test.com/service/url",
			settings.getValue(
				OpenSSOConfigurationKeys.SERVICE_URL, StringPool.BLANK));
	}

	@Override
	protected String getSettingsId() {
		return OpenSSOConstants.SERVICE_NAME;
	}

	@Override
	protected String getVerifyProcessName() {
		return "com.liferay.portal.security.sso.opensso";
	}

	@Override
	protected void populateLegacyProperties(UnicodeProperties properties) {
		properties.put(
			LegacyOpenSSOPropsKeys.OPENSSO_EMAIL_ADDRESS_ATTR,
			"testEmailAddressAttr");
		properties.put(
			LegacyOpenSSOPropsKeys.OPENSSO_AUTH_ENABLED, StringPool.TRUE);
		properties.put(
			LegacyOpenSSOPropsKeys.OPENSSO_FIRST_NAME_ATTR,
			"testFirstNameAttr");
		properties.put(
			LegacyOpenSSOPropsKeys.OPENSSO_IMPORT_FROM_LDAP, StringPool.TRUE);
		properties.put(
			LegacyOpenSSOPropsKeys.OPENSSO_LAST_NAME_ATTR, "testLastNameAttr");
		properties.put(
			LegacyOpenSSOPropsKeys.OPENSSO_LOGIN_URL,
			"http://test.com/login/url");
		properties.put(
			LegacyOpenSSOPropsKeys.OPENSSO_LOGOUT_ON_SESSION_EXPIRATION,
			StringPool.TRUE);
		properties.put(
			LegacyOpenSSOPropsKeys.OPENSSO_LOGOUT_URL,
			"http://test.com/logout/url");
		properties.put(
			LegacyOpenSSOPropsKeys.OPENSSO_SCREEN_NAME_ATTR,
			"testScreenNameAttr");
		properties.put(
			LegacyOpenSSOPropsKeys.OPENSSO_SERVICE_URL,
			"http://test.com/service/url");
	}

}