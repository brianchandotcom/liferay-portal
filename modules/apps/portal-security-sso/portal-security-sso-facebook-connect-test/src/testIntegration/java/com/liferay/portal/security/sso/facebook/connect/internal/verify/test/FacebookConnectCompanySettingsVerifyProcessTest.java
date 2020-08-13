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

package com.liferay.portal.security.sso.facebook.connect.internal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectConfigurationKeys;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectConstants;
import com.liferay.portal.security.sso.facebook.connect.constants.LegacyFacebookConnectPropsKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.test.util.BaseCompanySettingsVerifyProcessTestCase;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Stian Sigvartsen
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
@RunWith(Arquillian.class)
public class FacebookConnectCompanySettingsVerifyProcessTest
	extends BaseCompanySettingsVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected void doVerify(
		PortletPreferences portletPreferences, Settings settings) {

		for (String key :
				LegacyFacebookConnectPropsKeys.FACEBOOK_CONNECT_KEYS) {

			Assert.assertTrue(
				Validator.isNull(
					portletPreferences.getValue(key, StringPool.BLANK)));
		}

		Assert.assertEquals(
			StringPool.TRUE,
			settings.getValue(
				FacebookConnectConfigurationKeys.AUTH_ENABLED,
				StringPool.FALSE));
		Assert.assertEquals(
			"test_app_id",
			settings.getValue(
				FacebookConnectConfigurationKeys.APP_ID, StringPool.BLANK));
		Assert.assertEquals(
			"test_app_secret",
			settings.getValue(
				FacebookConnectConfigurationKeys.APP_SECRET, StringPool.BLANK));
		Assert.assertEquals(
			"test_graph_url",
			settings.getValue(
				FacebookConnectConfigurationKeys.GRAPH_URL, StringPool.BLANK));
		Assert.assertEquals(
			"test_oauth_auth_url",
			settings.getValue(
				FacebookConnectConfigurationKeys.OAUTH_AUTH_URL,
				StringPool.BLANK));
		Assert.assertEquals(
			"http://localhost:8080/c/portal/facebook_connect_oauth",
			settings.getValue(
				FacebookConnectConfigurationKeys.OAUTH_REDIRECT_URL,
				StringPool.BLANK));
		Assert.assertEquals(
			"test_oauth_token_url",
			settings.getValue(
				FacebookConnectConfigurationKeys.OAUTH_TOKEN_URL,
				StringPool.BLANK));
		Assert.assertEquals(
			StringPool.TRUE,
			settings.getValue(
				FacebookConnectConfigurationKeys.VERIFIED_ACCOUNT_REQUIRED,
				StringPool.FALSE));
	}

	@Override
	protected String getSettingsId() {
		return FacebookConnectConstants.SERVICE_NAME;
	}

	@Override
	protected String getVerifyProcessName() {
		return "com.liferay.portal.security.sso.facebook.connect";
	}

	@Override
	protected void populateLegacyProperties(
		UnicodeProperties unicodeProperties) {

		unicodeProperties.put(
			LegacyFacebookConnectPropsKeys.AUTH_ENABLED, "true");
		unicodeProperties.put(
			LegacyFacebookConnectPropsKeys.APP_ID, "test_app_id");
		unicodeProperties.put(
			LegacyFacebookConnectPropsKeys.APP_SECRET, "test_app_secret");
		unicodeProperties.put(
			LegacyFacebookConnectPropsKeys.GRAPH_URL, "test_graph_url");
		unicodeProperties.put(
			LegacyFacebookConnectPropsKeys.OAUTH_AUTH_URL,
			"test_oauth_auth_url");
		unicodeProperties.put(
			LegacyFacebookConnectPropsKeys.OAUTH_REDIRECT_URL,
			"http://localhost:8080/c/login/facebook_connect_oauth");
		unicodeProperties.put(
			LegacyFacebookConnectPropsKeys.OAUTH_TOKEN_URL,
			"test_oauth_token_url");
		unicodeProperties.put(
			LegacyFacebookConnectPropsKeys.VERIFIED_ACCOUNT_REQUIRED, "true");
	}

}