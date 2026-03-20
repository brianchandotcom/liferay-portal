/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.cookies.model.ConsentPreference;
import com.liferay.cookies.service.ConsentPreferenceLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lucas Miranda
 */
@RunWith(Arquillian.class)
public class UserModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext());
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testOnBeforeRemove() throws Exception {
		User user = UserTestUtil.addUser();

		String domain = RandomTestUtil.randomString();

		ConsentPreference consentPreference =
			_consentPreferenceLocalService.addConsentPreference(
				user.getUserId(), domain, RandomTestUtil.nextDate(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		_userLocalService.deleteUser(user);

		Assert.assertEquals(
			Collections.emptyList(),
			_consentPreferenceLocalService.getConsentPreferences(
				consentPreference.getUserId(), domain));
	}

	@Inject
	private ConsentPreferenceLocalService _consentPreferenceLocalService;

	@Inject
	private UserLocalService _userLocalService;

}