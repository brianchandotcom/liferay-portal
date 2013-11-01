/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.VirtualLayoutConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class PortalImplLocalizedFriendlyURLTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_availableLocales = LanguageUtil.getAvailableLocales();

		CompanyTestUtil.resetCompanyLocales(
			PortalUtil.getDefaultCompanyId(),
			new Locale[] {
				LocaleUtil.CANADA_FRENCH, LocaleUtil.SPAIN, LocaleUtil.US});

		_nameMap = new HashMap<Locale, String>();

		_nameMap.put(LocaleUtil.CANADA_FRENCH, "Accueil");
		_nameMap.put(LocaleUtil.SPAIN, "Inicio");
		_nameMap.put(LocaleUtil.US, "Home");

		_friendlyURLMap = new HashMap<Locale, String>();

		_friendlyURLMap.put(LocaleUtil.CANADA_FRENCH, "/accueil");
		_friendlyURLMap.put(LocaleUtil.SPAIN, "/inicio");
		_friendlyURLMap.put(LocaleUtil.US, "/home");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		CompanyTestUtil.resetCompanyLocales(
			PortalUtil.getDefaultCompanyId(), _availableLocales);
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

		testWronglyLocalizedSiteLayoutFriendlyURL(true, LocaleUtil.US, "/home");
	}

	@Test
	public void testWronglyLocalizedSiteLayoutPrivateFriendlyURL2()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			true, LocaleUtil.CANADA_FRENCH, "/accueil");
	}

	@Test
	public void testWronglyLocalizedSiteLayoutPublicFriendlyURL1()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			false, LocaleUtil.US, "/home");
	}

	@Test
	public void testWronglyLocalizedSiteLayoutPublicFriendlyURL2()
		throws Exception {

		testWronglyLocalizedSiteLayoutFriendlyURL(
			false, LocaleUtil.CANADA_FRENCH, "/accueil");
	}

	@Test
	public void testWronglyLocalizedVirtualPrivateLayoutFriendlyURL1()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			true, LocaleUtil.US, "/home");
	}

	@Test
	public void testWronglyLocalizedVirtualPrivateLayoutFriendlyURL2()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			true, LocaleUtil.CANADA_FRENCH, "/accueil");
	}

	@Test
	public void testWronglyLocalizedVirtualPublicLayoutFriendlyURL1()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			false, LocaleUtil.US, "/home");
	}

	@Test
	public void testWronglyLocalizedVirtualPublicLayoutFriendlyURL2()
		throws Exception {

		testWronglyLocalizedVirtualLayoutFriendlyURL(
			false, LocaleUtil.CANADA_FRENCH, "/accueil");
	}

	protected void assertLocalizedSiteLayoutFriendlyURL(
			long groupId, Layout layout, String layoutFriendlyURL,
			Locale locale, Locale originalLocale,
			String expectedLayoutFriendlyURL)
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

		StringBundler sb = new StringBundler();

		sb.append(StringPool.SLASH);
		sb.append(PortalUtil.getI18nPathLanguageId(locale, StringPool.BLANK));
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
			String expectedLayoutFriendlyURL)
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

		sb = new StringBundler(7);

		sb.append(StringPool.SLASH);
		sb.append(PortalUtil.getI18nPathLanguageId(locale, StringPool.BLANK));
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

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), privateLayout, _nameMap, _friendlyURLMap);

		assertLocalizedSiteLayoutFriendlyURL(
			group.getGroupId(), layout, "/inicio", LocaleUtil.SPAIN,
			LocaleUtil.SPAIN, "/inicio");
	}

	protected void testLocalizedVirtualLayoutFriendlyURL(boolean privateLayout)
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		UserGroup userGroup = UserGroupTestUtil.addUserGroup(
			group.getGroupId());

		Group userGroupGroup = userGroup.getGroup();

		Layout layout = LayoutTestUtil.addLayout(
			userGroupGroup.getGroupId(), privateLayout, _nameMap,
			_friendlyURLMap);

		UserGroupLocalServiceUtil.addUserUserGroup(
			serviceContext.getUserId(), userGroup.getUserGroupId());

		assertLocalizedVirtualLayoutFriendlyURL(
			userGroupGroup.getGroupId(), layout, "/inicio", LocaleUtil.SPAIN,
			LocaleUtil.SPAIN, "/inicio");
	}

	protected void testNonexistentLocalizedSiteLayoutFriendlyURL(
			boolean privateLayout)
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), privateLayout, _nameMap, _friendlyURLMap);

		assertLocalizedSiteLayoutFriendlyURL(
			group.getGroupId(), layout, "/home", LocaleUtil.GERMANY,
			LocaleUtil.US, "/home");
	}

	protected void testNonexistentLocalizedVirtualLayoutFriendlyURL(
			boolean privateLayout)
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		UserGroup userGroup = UserGroupTestUtil.addUserGroup(
			group.getGroupId());

		Group userGroupGroup = userGroup.getGroup();

		Layout layout = LayoutTestUtil.addLayout(
			userGroupGroup.getGroupId(), privateLayout, _nameMap,
			_friendlyURLMap);

		UserGroupLocalServiceUtil.addUserUserGroup(
			serviceContext.getUserId(), userGroup.getUserGroupId());

		assertLocalizedVirtualLayoutFriendlyURL(
			userGroupGroup.getGroupId(), layout, "/home", LocaleUtil.GERMANY,
			LocaleUtil.US, "/home");
	}

	protected void testNonexistentWronglyLocalizedSiteLayoutFriendlyURL(
			boolean privateLayout)
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), privateLayout, _nameMap, _friendlyURLMap);

		assertLocalizedSiteLayoutFriendlyURL(
			group.getGroupId(), layout, "/inicio", LocaleUtil.GERMANY,
			LocaleUtil.SPAIN, "/home");
	}

	protected void testNonexistentWronglyLocalizedVirtualLayoutFriendlyURL(
			boolean privateLayout)
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		UserGroup userGroup = UserGroupTestUtil.addUserGroup(
			group.getGroupId());

		Group userGroupGroup = userGroup.getGroup();

		Layout layout = LayoutTestUtil.addLayout(
			userGroupGroup.getGroupId(), privateLayout, _nameMap,
			_friendlyURLMap);

		UserGroupLocalServiceUtil.addUserUserGroup(
			serviceContext.getUserId(), userGroup.getUserGroupId());

		assertLocalizedVirtualLayoutFriendlyURL(
			userGroupGroup.getGroupId(), layout, "/inicio", LocaleUtil.US,
			LocaleUtil.SPAIN, "/home");
	}

	protected void testWronglyLocalizedSiteLayoutFriendlyURL(
			boolean privateLayout, Locale locale,
			String expectedLayoutFriendlyURL)
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), privateLayout, _nameMap, _friendlyURLMap);

		assertLocalizedSiteLayoutFriendlyURL(
			group.getGroupId(), layout, "/inicio", locale, LocaleUtil.SPAIN,
			expectedLayoutFriendlyURL);
	}

	protected void testWronglyLocalizedVirtualLayoutFriendlyURL(
			boolean privateLayout, Locale locale,
			String expectedLayoutFriendlyURL)
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		UserGroup userGroup = UserGroupTestUtil.addUserGroup(
			group.getGroupId());

		Group userGroupGroup = userGroup.getGroup();

		Layout layout = LayoutTestUtil.addLayout(
			userGroupGroup.getGroupId(), privateLayout, _nameMap,
			_friendlyURLMap);

		UserGroupLocalServiceUtil.addUserUserGroup(
			serviceContext.getUserId(), userGroup.getUserGroupId());

		assertLocalizedVirtualLayoutFriendlyURL(
			userGroupGroup.getGroupId(), layout, "/inicio", locale,
			LocaleUtil.SPAIN, expectedLayoutFriendlyURL);
	}

	private static final String _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

	private static Locale[] _availableLocales;
	private static Map<Locale, String> _friendlyURLMap;
	private static Map<Locale, String> _nameMap;

}