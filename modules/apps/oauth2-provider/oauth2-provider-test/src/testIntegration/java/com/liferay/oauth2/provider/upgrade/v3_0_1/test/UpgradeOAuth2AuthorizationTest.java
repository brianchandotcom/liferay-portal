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

package com.liferay.oauth2.provider.upgrade.v3_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class UpgradeOAuth2AuthorizationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.oauth2.provider.internal.upgrade.v3_0_1." +
				"OAuth2AuthorizationUpgradeProcess");

		_oAuth2ScopeGrantId = RandomTestUtil.randomLong();
	}

	@Test
	public void testUpgradeOAuth2Authorizations() throws Exception {
		long now = System.currentTimeMillis();

		_addOAuth2Authorization(new Date(now - Time.DAY), null);

		_addOAuth2Authorization(new Date(), null);

		_addOAuth2Authorization(null, new Date(now - Time.DAY));

		_addOAuth2Authorization(null, new Date());

		_upgradeProcess.upgrade();

		Assert.assertEquals(
			2, _oAuth2AuthorizationLocalService.getOAuth2AuthorizationsCount());

		Assert.assertEquals(
			2,
			_oAuth2AuthorizationLocalService.
				getOAuth2ScopeGrantOAuth2AuthorizationsCount(
					_oAuth2ScopeGrantId));
	}

	@Test
	public void testUpgradeOAuth2AuthorizationWithExpiredAccessToken()
		throws Exception {

		long now = System.currentTimeMillis();

		_addOAuth2Authorization(new Date(now - Time.DAY), null);

		_upgradeProcess.upgrade();

		Assert.assertEquals(
			0, _oAuth2AuthorizationLocalService.getOAuth2AuthorizationsCount());

		Assert.assertEquals(
			0,
			_oAuth2AuthorizationLocalService.
				getOAuth2ScopeGrantOAuth2AuthorizationsCount(
					_oAuth2ScopeGrantId));
	}

	@Test
	public void testUpgradeOAuth2AuthorizationWithExpiredRefreshToken()
		throws Exception {

		long now = System.currentTimeMillis();

		_addOAuth2Authorization(null, new Date(now - Time.DAY));

		_upgradeProcess.upgrade();

		Assert.assertEquals(
			0, _oAuth2AuthorizationLocalService.getOAuth2AuthorizationsCount());

		Assert.assertEquals(
			0,
			_oAuth2AuthorizationLocalService.
				getOAuth2ScopeGrantOAuth2AuthorizationsCount(
					_oAuth2ScopeGrantId));
	}

	@Test
	public void testUpgradeOAuth2AuthorizationWithValidAccessToken()
		throws Exception {

		_addOAuth2Authorization(new Date(), null);

		_upgradeProcess.upgrade();

		Assert.assertEquals(
			1, _oAuth2AuthorizationLocalService.getOAuth2AuthorizationsCount());

		Assert.assertEquals(
			1,
			_oAuth2AuthorizationLocalService.
				getOAuth2ScopeGrantOAuth2AuthorizationsCount(
					_oAuth2ScopeGrantId));
	}

	@Test
	public void testUpgradeOAuth2AuthorizationWithValidRefreshToken()
		throws Exception {

		_addOAuth2Authorization(null, new Date());

		_upgradeProcess.upgrade();

		Assert.assertEquals(
			1, _oAuth2AuthorizationLocalService.getOAuth2AuthorizationsCount());

		Assert.assertEquals(
			1,
			_oAuth2AuthorizationLocalService.
				getOAuth2ScopeGrantOAuth2AuthorizationsCount(
					_oAuth2ScopeGrantId));
	}

	private void _addOAuth2Authorization(
		Date accessTokenExpirationDate, Date refreshTokenExpirationDate) {

		OAuth2Authorization oAuth2Authorization =
			_oAuth2AuthorizationLocalService.createOAuth2Authorization(
				RandomTestUtil.randomLong());

		oAuth2Authorization.setAccessTokenExpirationDate(
			accessTokenExpirationDate);
		oAuth2Authorization.setRefreshTokenExpirationDate(
			refreshTokenExpirationDate);

		_oAuth2Authorizations.add(oAuth2Authorization);

		_oAuth2AuthorizationLocalService.addOAuth2Authorization(
			oAuth2Authorization);

		_oAuth2AuthorizationLocalService.addOAuth2ScopeGrantOAuth2Authorization(
			_oAuth2ScopeGrantId,
			oAuth2Authorization.getOAuth2AuthorizationId());
	}

	private static long _oAuth2ScopeGrantId;
	private static UpgradeProcess _upgradeProcess;

	@Inject(
		filter = "component.name=com.liferay.oauth2.provider.internal.upgrade.registry.OAuth2ServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

	@DeleteAfterTestRun
	private List<OAuth2Authorization> _oAuth2Authorizations = new ArrayList<>();

}