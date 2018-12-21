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

package com.liferay.structured.content.apio.internal.architect.resource.test;

import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ruben Pulido
 */
@RunWith(Arquillian.class)
public class ResourceLanguageStructuredContentNestedCollectionResourceTest
	extends BaseStructuredContentNestedCollectionResourceTestCase {

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

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), _siteAvailableLocales, Locale.FRANCE);
	}

	@Test
	public void testGetLocaleWithNullPreferredLocale() throws Exception {
		AcceptLanguage acceptLanguage = () -> null;

		Locale locale = getLocale(acceptLanguage, _group.getGroupId());

		Assert.assertEquals(
			PortalUtil.getSiteDefaultLocale(_group.getGroupId()), locale);
	}

	private static final Collection<Locale> _siteAvailableLocales =
		Arrays.asList(Locale.FRANCE, Locale.GERMANY);

	@DeleteAfterTestRun
	private Group _group;

}