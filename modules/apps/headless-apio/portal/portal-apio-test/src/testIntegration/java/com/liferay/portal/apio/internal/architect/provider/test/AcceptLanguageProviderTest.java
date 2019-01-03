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

package com.liferay.portal.apio.internal.architect.provider.test;

import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.apio.architect.provider.Provider;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class AcceptLanguageProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		User adminUser = UserTestUtil.getAdminUser(
			TestPropsValues.getCompanyId());

		String siteName = RandomTestUtil.randomString(10);

		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getDefault(), siteName);

		_group = GroupLocalServiceUtil.addGroup(
			adminUser.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID, null,
			0, GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, nameMap,
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(siteName),
			true, true, ServiceContextTestUtil.getServiceContext());

		_user = UserLocalServiceUtil.addUser(
			UserConstants.USER_ID_DEFAULT, TestPropsValues.getCompanyId(),
			false, Constants.TEST, Constants.TEST, true,
			StringUtil.randomString(20),
			StringUtil.randomString(10) + "@" + StringUtil.randomString(10), 0,
			null, Locale.ITALY, StringUtil.randomString(20), null,
			StringUtil.randomString(10), 0, 0, true, 1, 1, 2000, null,
			new long[] {_group.getGroupId()}, new long[0], new long[0],
			new long[0], false, new ServiceContext());
	}

	@Test
	public void testCreateContextWithNoAcceptLanguageGuestUser()
		throws Exception {

		User defaultUser = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		AcceptLanguage acceptLanguage = _provider.createContext(
			mockHttpServletRequest);

		Assert.assertEquals(
			defaultUser.getLocale(), acceptLanguage.getPreferredLocale());
	}

	@Test
	public void testCreateContextWithNoAcceptLanguageNoGuestUser() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.USER_ID, _user.getUserId());

		AcceptLanguage acceptLanguage = _provider.createContext(
			mockHttpServletRequest);

		Assert.assertEquals(
			_user.getLocale(), acceptLanguage.getPreferredLocale());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.portal.apio.internal.architect.provider.AcceptLanguageProvider"
	)
	private Provider<AcceptLanguage> _provider;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}