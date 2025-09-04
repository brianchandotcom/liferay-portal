/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.persistence.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.service.OpenIdConnectSessionLocalService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class OpenIdConnectSessionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testFetchOpenIdConnectSession() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		Assert.assertNull(
			_openIdConnectSessionLocalService.fetchOpenIdConnectSession(
				mockHttpServletRequest, TestPropsValues.getUserId()));

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpServletRequest.setSession(mockHttpSession);

		Assert.assertNull(
			_openIdConnectSessionLocalService.fetchOpenIdConnectSession(
				mockHttpServletRequest, TestPropsValues.getUserId()));

		OpenIdConnectSession openIdConnectSession =
			_openIdConnectSessionLocalService.createOpenIdConnectSession(
				_counterLocalService.increment(
					OpenIdConnectSession.class.getName()));

		openIdConnectSession.setUserId(TestPropsValues.getUserId());
		openIdConnectSession.setAccessToken(RandomTestUtil.randomString());

		openIdConnectSession =
			_openIdConnectSessionLocalService.addOpenIdConnectSession(
				openIdConnectSession);

		mockHttpSession.setAttribute(
			OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION_ID,
			openIdConnectSession.getOpenIdConnectSessionId());

		Assert.assertNull(
			_openIdConnectSessionLocalService.fetchOpenIdConnectSession(
				mockHttpServletRequest, RandomTestUtil.randomInt()));

		Assert.assertEquals(
			openIdConnectSession,
			_openIdConnectSessionLocalService.fetchOpenIdConnectSession(
				mockHttpServletRequest, TestPropsValues.getUserId()));
	}

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private OpenIdConnectSessionLocalService _openIdConnectSessionLocalService;

}