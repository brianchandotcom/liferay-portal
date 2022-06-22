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

package com.liferay.oauth2.provider.client.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;

/**
 * @author Jose L. Bango
 */
@RunWith(Arquillian.class)
public class ExpiredAuthorizationsAfterlifeTest extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		_oAuth2AuthorizationLocalService.deleteExpiredOAuth2Authorizations();

		OAuth2Authorization oAuth2Authorization1 =
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent("accessToken1");

		Assert.assertNull(oAuth2Authorization1);

		OAuth2Authorization oAuth2Authorization2 =
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent("accessToken2");

		Assert.assertNotNull(oAuth2Authorization2);

		OAuth2Authorization oAuth2Authorization3 =
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent("accessToken3");

		Assert.assertNotNull(oAuth2Authorization3);
	}

	public static class
		ExpiredAuthorizationsAfterlifeTestPreparatorBundleActivator
			extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			updateOAuth2ProviderConfiguration(
				MapUtil.singletonDictionary(
					"oauth2.expired.authorizations.afterlife.duration",
					Time.HOUR * 12 / Time.SECOND));

			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			OAuth2Application oAuth2Application = createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplication",
				Arrays.asList("everything.read"));

			Map<String, Date> accessTokenMap = HashMapBuilder.<String, Date>put(
				"accessToken1",
				new Date(System.currentTimeMillis() - (Time.HOUR * 15))
			).put(
				"accessToken2",
				new Date(System.currentTimeMillis() - (Time.HOUR * 10))
			).put(
				"accessToken3",
				new Date(System.currentTimeMillis() + (Time.HOUR * 1))
			).build();

			accessTokenMap.forEach(
				(accessTokenContent, accessTokenExpirationDate) ->
					addOAuth2Authorization(
						defaultCompanyId, user, oAuth2Application,
						accessTokenContent, new Date(),
						accessTokenExpirationDate));
		}

	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new ExpiredAuthorizationsAfterlifeTestPreparatorBundleActivator();
	}

	@Inject
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

}