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

package com.liferay.portal.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.VirtualLayoutConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Sergio González
 */
public class PortalImplLocalizedFriendlyURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_availableLocales = LanguageUtil.getAvailableLocales();
		_defaultLocale = LocaleUtil.getDefault();

		CompanyTestUtil.resetCompanyLocales(
			PortalUtil.getDefaultCompanyId(),
			new Locale[] {
				LocaleUtil.CANADA_FRENCH, LocaleUtil.SPAIN, LocaleUtil.US
			},
			LocaleUtil.US
		);

		_nameMap = new HashMap<>();

		_nameMap.put(LocaleUtil.CANADA_FRENCH, "Accueil");
		_nameMap.put(LocaleUtil.SPAIN, "Inicio");
		_nameMap.put(LocaleUtil.US, "Home");

		_friendlyURLMap = new HashMap<>();

		_friendlyURLMap.put(LocaleUtil.CANADA_FRENCH, "/accueil");
		_friendlyURLMap.put(LocaleUtil.SPAIN, "/inicio");
		_friendlyURLMap.put(LocaleUtil.US, "/home");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		CompanyTestUtil.resetCompanyLocales(
			PortalUtil.getDefaultCompanyId(), _availableLocales,
			_defaultLocale);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testIncludeI18nPathCustomLocaleAlgorithm0() throws Exception {
		int originalLocalePrependFriendlyURLStyle =
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		try {
			Layout layout = LayoutTestUtil.addLayout(
				_group.getGroupId(), false, _nameMap, _friendlyURLMap);

			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE = 0;

			assertLocalizedSiteLayoutFriendlyURL(
				_group.getGroupId(), layout, "/inicio", LocaleUtil.SPAIN,
				LocaleUtil.SPAIN, "/inicio", false);
		}
		finally {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				originalLocalePrependFriendlyURLStyle;
		}
	}

	@Test
	public void testIncludeI18nPathCustomLocaleAlgorithm1() throws Exception {
		int originalLocalePrependFriendlyURLStyle =
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		try {
			Layout layout = LayoutTestUtil.addLayout(
				_group.getGroupId(), false, _nameMap, _friendlyURLMap);

			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE = 1;

			assertLocalizedSiteLayoutFriendlyURL(
				_group.getGroupId(), layout, "/inicio", LocaleUtil.SPAIN,
				LocaleUtil.SPAIN, "/inicio", true);
		}
		finally {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				originalLocalePrependFriendlyURLStyle;
		}
	}

	@Test
	public void testIncludeI18nPathCustomLocaleAlgorithm2() throws Exception {
		int originalLocalePrependFriendlyURLStyle =
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		try {
			Layout layout = LayoutTestUtil.addLayout(
				_group.getGroupId(), false, _nameMap, _friendlyURLMap);

			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE = 2;

			assertLocalizedSiteLayoutFriendlyURL(
				_group.getGroupId(), layout, "/inicio", LocaleUtil.SPAIN,
				LocaleUtil.SPAIN, "/inicio", true);
		}
		finally {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				originalLocalePrependFriendlyURLStyle;
		}
	}

	@Test
	public void testIncludeI18nPathDefaultLocaleAlgorithm0() throws Exception {
		int originalLocalePrependFriendlyURLStyle =
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		try {
			Layout layout = LayoutTestUtil.addLayout(
				_group.getGroupId(), false, _nameMap, _friendlyURLMap);

			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE = 0;

			assertLocalizedSiteLayoutFriendlyURL(
				_group.getGroupId(), layout, "/home", LocaleUtil.US,
				LocaleUtil.US, "/home", false);
		}
		finally {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				originalLocalePrependFriendlyURLStyle;
		}
	}

	@Test
	public void testIncludeI18nPathDefaultLocaleAlgorithm1() throws Exception {
		int originalLocalePrependFriendlyURLStyle =
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		try {
			Layout layout = LayoutTestUtil.addLayout(
				_group.getGroupId(), false, _nameMap, _friendlyURLMap);

			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE = 1;

			assertLocalizedSiteLayoutFriendlyURL(
				_group.getGroupId(), layout, "/home", LocaleUtil.US,
				LocaleUtil.US, "/home", false);
		}
		finally {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				originalLocalePrependFriendlyURLStyle;
		}
	}

	@Test
	public void testIncludeI18nPathDefaultLocaleAlgorithm2() throws Exception {
		int originalLocalePrependFriendlyURLStyle =
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		try {
			Layout layout = LayoutTestUtil.addLayout(
				_group.getGroupId(), false, _nameMap, _friendlyURLMap);

			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE = 2;

			assertLocalizedSiteLayoutFriendlyURL(
				_group.getGroupId(), layout, "/home", LocaleUtil.US,
				LocaleUtil.US, "/home", true);
		}
		finally {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				originalLocalePrependFriendlyURLStyle;
		}
	}

	@Test
	public void testLocalizedSitePrivateLayoutFriendlyURL() throws Exception {
		testLocalizedSiteLayoutFriendlyURL(true);
	}

	@Test
	public void testLocalizedSitePublicLayoutFriendlyURL() throws Exception {
		testLocalizedSiteLayoutFriendlyURL(false);
	}

	@Test
	public void testLocalizedVirtualPrivateLayoutFriendlyURL()
		throws Exception {

		testLocalizedVirtualLayoutFriendlyURL(true);
	}

	@Test
	public void testLocalizedVirtualPublicLayoutFriendlyURL() throws Exception {
		testLocalizedVirtualLayoutFriendlyURL(false);
	}

	@Test
	public void testNonexistentLocalizedSitePrivateLayoutFriendlyURL()
		throws Exception {

		testNonexistentLocalizedSiteLayoutFriendlyURL(true);
	}

	@Test
	public void testNonexistentLocalizedSitePublicLayoutFriendlyURL()
		throws Exception {

		testNonexistentLocalizedSiteLayoutFriendlyURL(false);
	}

	@Test
	public void testNonexistentLocalizedVirtualPrivateLayoutFriendlyURL()
		throws Exception {

		testNonexistentLocalizedVirtualLayoutFriendlyURL(true);
	}

	@Test
	public void testNonexistentLocalizedVirtualPublicLayoutFriendlyURL()
		throws Exception {

		testNonexistentLocalizedVirtualLayoutFriendlyURL(false);
	}

	@Test
	public void testNonexistentWronglyLocalizedSiteLayoutPrivateFriendlyURL()
		throws Exception {

		testNonexistentWronglyLocalizedSiteLayoutFriendlyURL(true);
	}

	@Test
	public void testNonexistentWronglyLocalizedSiteLayoutPublicFriendlyURL()
		throws Exception {

		testNonexistentWronglyLocalizedSiteLayoutFriendlyURL(false);
	}

	@Test
	public void testNonexistentWronglyLocalizedVirtualLayoutPrivateFriendlyURL()
		throws Exception {

		testNonexistentWronglyLocalizedVirtualLayoutFriendlyURL(true);
	}

	@Test
	public void testNonexistentWronglyLocalizedVirtualLayoutPublicFriendlyURL()
		throws Exception {

		testNonexistentWronglyLocalizedVirtualLayoutFriendlyURL(false);
	}

	@Test
	public void testWronglyLocalizedSiteLayoutPrivateFriendlyURL1()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			true, LocaleUtil.US, null, "/home");
	}

	@Test
	public void testWronglyLocalizedSiteLayoutPrivateFriendlyURL2()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			true, LocaleUtil.CANADA_FRENCH, null, "/accueil");
	}

	@Test
	public void
			testWronglyLocalizedSiteLayoutPrivateFriendlyURLWithBlogsMapping1()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			true, LocaleUtil.US, "/-/blogs/one", "/home");
	}

	@Test
	public void
			testWronglyLocalizedSiteLayoutPrivateFriendlyURLWithBlogsMapping2()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			true, LocaleUtil.CANADA_FRENCH, "/-/blogs/one", "/accueil");
	}

	@Test
	public void testWronglyLocalizedSiteLayoutPrivateFriendlyURLWithParams1()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			true, LocaleUtil.US, "?param=value", "/home");
	}

	@Test
	public void testWronglyLocalizedSiteLayoutPrivateFriendlyURLWithParams2()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			true, LocaleUtil.CANADA_FRENCH, "?param=value", "/accueil");
	}

	@Test
	public void
			testWronglyLocalizedSiteLayoutPrivateFriendlyURLWithTagsMapping1()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			true, LocaleUtil.US, "/tags/one", "/home");
	}

	@Test
	public void
			testWronglyLocalizedSiteLayoutPrivateFriendlyURLWithTagsMapping2()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			true, LocaleUtil.CANADA_FRENCH, "/tags/one", "/accueil");
	}

	@Test
	public void testWronglyLocalizedSiteLayoutPublicFriendlyURL1()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			false, LocaleUtil.US, null, "/home");
	}

	@Test
	public void testWronglyLocalizedSiteLayoutPublicFriendlyURL2()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			false, LocaleUtil.CANADA_FRENCH, null, "/accueil");
	}

	@Test
	public void
			testWronglyLocalizedSiteLayoutPublicFriendlyURLWithBlogsMapping1()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			false, LocaleUtil.US, "/-/blogs/one", "/home");
	}

	@Test
	public void
			testWronglyLocalizedSiteLayoutPublicFriendlyURLWithBlogsMapping2()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			false, LocaleUtil.CANADA_FRENCH, "/-/blogs/one", "/accueil");
	}

	@Test
	public void testWronglyLocalizedSiteLayoutPublicFriendlyURLWithParams1()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			false, LocaleUtil.US, "?param=value", "/home");
	}

	@Test
	public void testWronglyLocalizedSiteLayoutPublicFriendlyURLWithParams2()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			false, LocaleUtil.CANADA_FRENCH, "?param=value", "/accueil");
	}

	@Test
	public void
			testWronglyLocalizedSiteLayoutPublicFriendlyURLWithTagsMapping1()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			false, LocaleUtil.US, "/tags/one", "/home");
	}

	@Test
	public void
			testWronglyLocalizedSiteLayoutPublicFriendlyURLWithTagsMapping2()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			false, LocaleUtil.CANADA_FRENCH, "/tags/one", "/accueil");
	}

	@Test
	public void testWronglyLocalizedVirtualPrivateLayoutFriendlyURL1()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			true, LocaleUtil.US, null, "/home");
	}

	@Test
	public void testWronglyLocalizedVirtualPrivateLayoutFriendlyURL2()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			true, LocaleUtil.CANADA_FRENCH, null, "/accueil");
	}

	@Test
	public void
			testWronglyLocalizedVirtualPrivateLayoutFriendlyURLWithBlogsMapping1()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			true, LocaleUtil.US, "/-/blogs/one", "/home");
	}

	@Test
	public void
			testWronglyLocalizedVirtualPrivateLayoutFriendlyURLWithBlogsMapping2()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			true, LocaleUtil.CANADA_FRENCH, "/-/blogs/one", "/accueil");
	}

	@Test
	public void
			testWronglyLocalizedVirtualPrivateLayoutFriendlyURLWithParams1()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			true, LocaleUtil.US, "?param=value", "/home");
	}

	@Test
	public void
			testWronglyLocalizedVirtualPrivateLayoutFriendlyURLWithParams2()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			true, LocaleUtil.CANADA_FRENCH, "?param=value", "/accueil");
	}

	@Test
	public void
			testWronglyLocalizedVirtualPrivateLayoutFriendlyURLWithTagsMapping1()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			true, LocaleUtil.US, "/tags/one", "/home");
	}

	@Test
	public void
			testWronglyLocalizedVirtualPrivateLayoutFriendlyURLWithTagsMapping2()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			true, LocaleUtil.CANADA_FRENCH, "/tags/one", "/accueil");
	}

	@Test
	public void testWronglyLocalizedVirtualPublicLayoutFriendlyURL1()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			false, LocaleUtil.US, null, "/home");
	}

	@Test
	public void testWronglyLocalizedVirtualPublicLayoutFriendlyURL2()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			false, LocaleUtil.CANADA_FRENCH, null, "/accueil");
	}

	@Test
	public void
			testWronglyLocalizedVirtualPublicLayoutFriendlyURLWithBlogsMapping1()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			false, LocaleUtil.US, "/-/blogs/one", "/home");
	}

	@Test
	public void
			testWronglyLocalizedVirtualPublicLayoutFriendlyURLWithBlogsMapping2()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			false, LocaleUtil.CANADA_FRENCH, "/-/blogs/one", "/accueil");
	}

	@Test
	public void testWronglyLocalizedVirtualPublicLayoutFriendlyURLWithParams1()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			false, LocaleUtil.US, "?param=value", "/home");
	}

	@Test
	public void testWronglyLocalizedVirtualPublicLayoutFriendlyURLWithParams2()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			false, LocaleUtil.CANADA_FRENCH, "?param=value", "/accueil");
	}

	@Test
	public void
			testWronglyLocalizedVirtualPublicLayoutFriendlyURLWithTagsMapping1()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			false, LocaleUtil.US, "/tags/one", "/home");
	}

	@Test
	public void
			testWronglyLocalizedVirtualPublicLayoutFriendlyURLWithTagsMapping2()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			false, LocaleUtil.CANADA_FRENCH, "/tags/one", "/accueil");
	}

	protected void assertLocalizedSiteLayoutFriendlyURL(
			long groupId, Layout layout, String layoutFriendlyURL,
			Locale locale, Locale originalLocale,
			String expectedLayoutFriendlyURL, boolean includeI18nPath)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		mockHttpServletRequest.setPathInfo(
			group.getFriendlyURL() + layoutFriendlyURL);

		String groupServletMapping = _PUBLIC_GROUP_SERVLET_MAPPING;

		if (layout.isPrivateLayout()) {
			groupServletMapping = _PRIVATE_GROUP_SERVLET_MAPPING;
		}

		mockHttpServletRequest.setRequestURI(
			groupServletMapping + group.getFriendlyURL() + layoutFriendlyURL);

		StringBundler sb = new StringBundler(includeI18nPath ? 5 : 3);

		if (includeI18nPath) {
			sb.append(StringPool.SLASH);
			sb.append(
				PortalUtil.getI18nPathLanguageId(locale, StringPool.BLANK));
		}

		sb.append(groupServletMapping);
		sb.append(group.getFriendlyURL());
		sb.append(expectedLayoutFriendlyURL);

		String localizedFriendlyURL = PortalUtil.getLocalizedFriendlyURL(
			mockHttpServletRequest, layout, locale, originalLocale);

		Assert.assertEquals(sb.toString(), localizedFriendlyURL);
	}

	protected void assertLocalizedVirtualLayoutFriendlyURL(
			long userGroupGroupId, Layout layout, String layoutFriendlyURL,
			Locale locale, Locale originalLocale,
			String expectedLayoutFriendlyURL, boolean includeI18nPath)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		StringBundler sb = new StringBundler(4);

		User user = TestPropsValues.getUser();

		Group groupUser = user.getGroup();

		sb.append(groupUser.getFriendlyURL());

		sb.append(VirtualLayoutConstants.CANONICAL_URL_SEPARATOR);

		Group userGroupGroup = GroupLocalServiceUtil.getGroup(userGroupGroupId);

		sb.append(userGroupGroup.getFriendlyURL());

		sb.append(layoutFriendlyURL);

		mockHttpServletRequest.setPathInfo(sb.toString());

		sb = new StringBundler(5);

		String groupServletMapping = _PUBLIC_GROUP_SERVLET_MAPPING;

		if (layout.isPrivateLayout()) {
			groupServletMapping = _PRIVATE_GROUP_SERVLET_MAPPING;
		}

		sb.append(groupServletMapping);

		sb.append(groupUser.getFriendlyURL());
		sb.append(VirtualLayoutConstants.CANONICAL_URL_SEPARATOR);
		sb.append(userGroupGroup.getFriendlyURL());
		sb.append(layoutFriendlyURL);

		mockHttpServletRequest.setRequestURI(sb.toString());

		sb = new StringBundler(includeI18nPath ? 7 : 5);

		if (includeI18nPath) {
			sb.append(StringPool.SLASH);
			sb.append(
				PortalUtil.getI18nPathLanguageId(locale, StringPool.BLANK));
		}

		sb.append(groupServletMapping);
		sb.append(groupUser.getFriendlyURL());
		sb.append(VirtualLayoutConstants.CANONICAL_URL_SEPARATOR);
		sb.append(userGroupGroup.getFriendlyURL());
		sb.append(expectedLayoutFriendlyURL);

		String localizedFriendlyURL = PortalUtil.getLocalizedFriendlyURL(
			mockHttpServletRequest, layout, locale, originalLocale);

		Assert.assertEquals(sb.toString(), localizedFriendlyURL);
	}

	protected void testLocalizedSiteLayoutFriendlyURL(boolean privateLayout)
		throws Exception {

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), privateLayout, _nameMap, _friendlyURLMap);

		assertLocalizedSiteLayoutFriendlyURL(
			_group.getGroupId(), layout, "/inicio", LocaleUtil.SPAIN,
			LocaleUtil.SPAIN, "/inicio", true);
	}

	protected void testLocalizedVirtualLayoutFriendlyURL(boolean privateLayout)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		UserGroup userGroup = UserGroupTestUtil.addUserGroup(
			_group.getGroupId());

		Group userGroupGroup = userGroup.getGroup();

		Layout layout = LayoutTestUtil.addLayout(
			userGroupGroup.getGroupId(), privateLayout, _nameMap,
			_friendlyURLMap);

		UserGroupLocalServiceUtil.addUserUserGroup(
			serviceContext.getUserId(), userGroup.getUserGroupId());

		assertLocalizedVirtualLayoutFriendlyURL(
			userGroupGroup.getGroupId(), layout, "/inicio", LocaleUtil.SPAIN,
			LocaleUtil.SPAIN, "/inicio", true);
	}

	protected void testNonexistentLocalizedSiteLayoutFriendlyURL(
			boolean privateLayout)
		throws Exception {

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), privateLayout, _nameMap, _friendlyURLMap);

		assertLocalizedSiteLayoutFriendlyURL(
			_group.getGroupId(), layout, "/home", LocaleUtil.GERMANY,
			LocaleUtil.US, "/home", true);
	}

	protected void testNonexistentLocalizedVirtualLayoutFriendlyURL(
			boolean privateLayout)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		UserGroup userGroup = UserGroupTestUtil.addUserGroup(
			_group.getGroupId());

		Group userGroupGroup = userGroup.getGroup();

		Layout layout = LayoutTestUtil.addLayout(
			userGroupGroup.getGroupId(), privateLayout, _nameMap,
			_friendlyURLMap);

		UserGroupLocalServiceUtil.addUserUserGroup(
			serviceContext.getUserId(), userGroup.getUserGroupId());

		assertLocalizedVirtualLayoutFriendlyURL(
			userGroupGroup.getGroupId(), layout, "/home", LocaleUtil.GERMANY,
			LocaleUtil.US, "/home", true);
	}

	protected void testNonexistentWronglyLocalizedSiteLayoutFriendlyURL(
			boolean privateLayout)
		throws Exception {

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), privateLayout, _nameMap, _friendlyURLMap);

		assertLocalizedSiteLayoutFriendlyURL(
			_group.getGroupId(), layout, "/inicio", LocaleUtil.GERMANY,
			LocaleUtil.SPAIN, "/home", true);
	}

	protected void testNonexistentWronglyLocalizedVirtualLayoutFriendlyURL(
			boolean privateLayout)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		UserGroup userGroup = UserGroupTestUtil.addUserGroup(
			_group.getGroupId());

		Group userGroupGroup = userGroup.getGroup();

		Layout layout = LayoutTestUtil.addLayout(
			userGroupGroup.getGroupId(), privateLayout, _nameMap,
			_friendlyURLMap);

		UserGroupLocalServiceUtil.addUserUserGroup(
			serviceContext.getUserId(), userGroup.getUserGroupId());

		assertLocalizedVirtualLayoutFriendlyURL(
			userGroupGroup.getGroupId(), layout, "/inicio", LocaleUtil.US,
			LocaleUtil.SPAIN, "/home", true);
	}

	protected void testWronglyLocalizedSiteLayoutFriendlyURL(
			boolean privateLayout, Locale locale, String queryString,
			String expectedLayoutFriendlyURL)
		throws Exception {

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), privateLayout, _nameMap, _friendlyURLMap);

		String requestedFriendlyURL = "/inicio";

		if (Validator.isNotNull(queryString)) {
			requestedFriendlyURL += queryString;

			expectedLayoutFriendlyURL += queryString;
		}

		assertLocalizedSiteLayoutFriendlyURL(
			_group.getGroupId(), layout, requestedFriendlyURL, locale,
			LocaleUtil.SPAIN, expectedLayoutFriendlyURL, true);
	}

	protected void testWronglyLocalizedVirtualLayoutFriendlyURL(
			boolean privateLayout, Locale locale, String queryString,
			String expectedLayoutFriendlyURL)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		UserGroup userGroup = UserGroupTestUtil.addUserGroup(
			_group.getGroupId());

		Group userGroupGroup = userGroup.getGroup();

		Layout layout = LayoutTestUtil.addLayout(
			userGroupGroup.getGroupId(), privateLayout, _nameMap,
			_friendlyURLMap);

		UserGroupLocalServiceUtil.addUserUserGroup(
			serviceContext.getUserId(), userGroup.getUserGroupId());

		String requestedFriendlyURL = "/inicio";

		if (Validator.isNotNull(queryString)) {
			requestedFriendlyURL += queryString;

			expectedLayoutFriendlyURL += queryString;
		}

		assertLocalizedVirtualLayoutFriendlyURL(
			userGroupGroup.getGroupId(), layout, requestedFriendlyURL, locale,
			LocaleUtil.SPAIN, expectedLayoutFriendlyURL, true);
	}

	private static final String _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

	private static Locale[] _availableLocales;
	private static Locale _defaultLocale;
	private static Map<Locale, String> _friendlyURLMap;
	private static Map<Locale, String> _nameMap;

	@DeleteAfterTestRun
	private Group _group;

}