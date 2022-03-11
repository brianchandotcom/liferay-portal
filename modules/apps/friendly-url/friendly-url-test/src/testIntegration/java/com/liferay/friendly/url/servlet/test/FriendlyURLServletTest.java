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

package com.liferay.friendly.url.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author László Csontos
 */
@RunWith(Arquillian.class)
public class FriendlyURLServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		PropsValues.LOCALES_ENABLED = new String[] {"en_US", "hu_HU", "de_DE"};
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = true;

		LanguageUtil.init();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext());

		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group);

		List<Locale> availableLocales = Arrays.asList(
			LocaleUtil.US, LocaleUtil.GERMANY, LocaleUtil.HUNGARY);

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), availableLocales, LocaleUtil.US);

		Class<?> clazz = _servlet.getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		clazz = classLoader.loadClass(
			"com.liferay.friendly.url.internal.servlet.FriendlyURLServlet");

		_getRedirectMethod = clazz.getDeclaredMethod(
			"getRedirect", HttpServletRequest.class, HttpServletResponse.class,
			String.class);

		clazz = classLoader.loadClass(
			"com.liferay.friendly.url.internal.servlet.FriendlyURLServlet" +
				"$Redirect");

		_redirectConstructor1 = clazz.getConstructor(String.class);

		_redirectConstructor2 = clazz.getConstructor(
			String.class, Boolean.TYPE, Boolean.TYPE);
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();

		PropsValues.LOCALES_ENABLED = PropsUtil.getArray(
			PropsKeys.LOCALES_ENABLED);
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE));

		LanguageUtil.init();
	}

	@Test
	public void testGetRedirectWithExistentSite() throws Throwable {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(StringPool.SLASH);

		testGetRedirect(
			mockHttpServletRequest, getPath(_group, _layout), Portal.PATH_MAIN,
			_redirectConstructor1.newInstance(getURL(_layout)));
	}

	@Test(expected = NoSuchGroupException.class)
	public void testGetRedirectWithGroupId() throws Throwable {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(StringPool.SLASH);

		String path = "/" + _group.getGroupId() + _layout.getFriendlyURL();

		testGetRedirect(
			mockHttpServletRequest, path, Portal.PATH_MAIN,
			_redirectConstructor1.newInstance(getURL(_layout)));
	}

	@Test
	public void testGetRedirectWithI18nPath() throws Throwable {
		testGetI18nRedirect("/fr", "/en");
		testGetI18nRedirect("/hu", "/hu");
		testGetI18nRedirect("/en", "/en");
		testGetI18nRedirect("/de_DE", "/de_DE");
		testGetI18nRedirect("/en_US", "/en_US");
	}

	@Test(expected = NoSuchGroupException.class)
	public void testGetRedirectWithNonexistentSite() throws Throwable {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(StringPool.SLASH);

		testGetRedirect(
			mockHttpServletRequest, "/nonexistent-site/home", Portal.PATH_MAIN,
			null);
	}

	@Test
	public void testGetRedirectWithNumericUserScreenName() throws Throwable {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(StringPool.SLASH);

		long numericScreenName = RandomTestUtil.nextLong();

		_user = UserTestUtil.addUser(String.valueOf(numericScreenName));

		Group userGroup = _user.getGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(userGroup);

		testGetRedirect(
			mockHttpServletRequest, getPath(userGroup, _layout),
			Portal.PATH_MAIN,
			_redirectConstructor1.newInstance(getURL(_layout)));
	}

	@Test
	public void testServiceForward() throws Throwable {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		long groupId = _group.getGroupId();

		Locale locale = LocaleUtil.getSiteDefault();

		Map<Locale, String> nameMap = HashMapBuilder.put(
			locale, "careers"
		).build();

		Map<Locale, String> friendlyURLMap = HashMapBuilder.put(
			locale, "/careers"
		).build();

		Layout careerLayout = LayoutTestUtil.addTypePortletLayout(
			groupId, false, nameMap, friendlyURLMap);

		nameMap.put(locale, "friendly");
		friendlyURLMap.put(locale, "/friendly");

		UnicodeProperties typeSettingsUnicodeProperties =
			_group.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.put("url", careerLayout.getFriendlyURL());

		String typeSettings = typeSettingsUnicodeProperties.toString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		Layout redirectLayout = _layoutLocalService.addLayout(
			serviceContext.getUserId(), groupId, false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, nameMap,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), LayoutConstants.TYPE_URL,
			typeSettings, false, friendlyURLMap, serviceContext);

		mockHttpServletRequest.setPathInfo(StringPool.SLASH);

		String requestURI =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				getPath(_group, redirectLayout);

		mockHttpServletRequest.setRequestURI(requestURI);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			"/careers", mockHttpServletResponse.getHeader("Location"));
		Assert.assertEquals(302, mockHttpServletResponse.getStatus());
		Assert.assertTrue(mockHttpServletResponse.isCommitted());
	}

	@Test
	public void testServiceForwardToDefaultLayoutWith404OnDisabledLocale()
		throws Throwable {

		Group group = GroupTestUtil.addGroup();

		Locale locale = LocaleUtil.getSiteDefault();

		Layout homeLayout = LayoutTestUtil.addTypePortletLayout(
			group.getGroupId(), false,
			HashMapBuilder.put(
				locale, "home"
			).build(),
			HashMapBuilder.put(
				locale, "/home"
			).put(
				LocaleUtil.GERMANY, "/home1"
			).build());

		GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), Arrays.asList(LocaleUtil.GERMANY),
			LocaleUtil.GERMANY);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		testGetRedirect(
			new MockHttpServletRequest(
				"GET",
				StringBundler.concat(
					PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
					group.getFriendlyURL(), "/home")),
			mockHttpServletResponse, getPath(group, homeLayout) + "/home",
			Portal.PATH_MAIN,
			_redirectConstructor1.newInstance(getURL(homeLayout)));

		Assert.assertEquals(404, mockHttpServletResponse.getStatus());
	}

	@Test
	public void testServiceForwardToDefaultLayoutWith404OnMissingLayout()
		throws Throwable {

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		testGetRedirect(
			new MockHttpServletRequest(
				"GET",
				StringBundler.concat(
					PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
					_group.getFriendlyURL(), "/path")),
			mockHttpServletResponse, getPath(_group, _layout) + "/path",
			Portal.PATH_MAIN,
			_redirectConstructor1.newInstance(getURL(_layout)));

		Assert.assertEquals(404, mockHttpServletResponse.getStatus());
	}

	@Test
	public void testServiceLinkToURLRedirectWithQueryParams() throws Throwable {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		Layout redirectLayout = LayoutTestUtil.addTypePortletLayout(_group);

		redirectLayout.setType(LayoutConstants.TYPE_URL);

		UnicodeProperties typeSettingsUnicodeProperties =
			_group.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.put("url", _layout.getFriendlyURL());

		redirectLayout.setTypeSettingsProperties(typeSettingsUnicodeProperties);

		_layoutLocalService.updateLayout(redirectLayout);

		mockHttpServletRequest.setParameter("param", "true");
		mockHttpServletRequest.setPathInfo(StringPool.SLASH);

		String requestURI =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				getPath(_group, redirectLayout);

		mockHttpServletRequest.setRequestURI(requestURI);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		String redirectedURL = mockHttpServletResponse.getRedirectedUrl();

		Assert.assertTrue(redirectedURL.contains("?param=true"));
	}

	@Test
	public void testServiceRedirect() throws Throwable {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		Layout redirectLayout = LayoutTestUtil.addTypePortletLayout(_group);

		redirectLayout.setType(LayoutConstants.TYPE_URL);

		UnicodeProperties typeSettingsUnicodeProperties =
			_group.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.put("url", _layout.getFriendlyURL());

		redirectLayout.setTypeSettingsProperties(typeSettingsUnicodeProperties);

		_layoutLocalService.updateLayout(redirectLayout);

		mockHttpServletRequest.setPathInfo(StringPool.SLASH);

		String requestURI =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				getPath(_group, redirectLayout);

		mockHttpServletRequest.setRequestURI(requestURI);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			_layout.getFriendlyURL(),
			mockHttpServletResponse.getRedirectedUrl());

		Assert.assertEquals(302, mockHttpServletResponse.getStatus());
	}

	@Test
	public void testServiceRedirectWithMatchingPermanentRedirectEntry()
		throws Exception {

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				_group.getGroupId(), "http://www.liferay.com", null, true,
				"path", ServiceContextTestUtil.getServiceContext());

		try {
			MockHttpServletResponse mockHttpServletResponse =
				new MockHttpServletResponse();

			_servlet.service(
				new MockHttpServletRequest(
					"GET",
					StringBundler.concat(
						PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
						_group.getFriendlyURL(), "/path")),
				mockHttpServletResponse);

			Assert.assertEquals(301, mockHttpServletResponse.getStatus());
			Assert.assertEquals(
				"http://www.liferay.com",
				mockHttpServletResponse.getHeader("Location"));

			redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
				_group.getGroupId(), "path");

			Assert.assertNotNull(redirectEntry.getLastOccurrenceDate());
		}
		finally {
			_redirectEntryLocalService.deleteRedirectEntry(redirectEntry);
		}
	}

	@Test
	public void testServiceRedirectWithMatchingTemporaryRedirectEntry()
		throws Exception {

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				_group.getGroupId(), "http://www.liferay.com", null, false,
				"path", ServiceContextTestUtil.getServiceContext());

		try {
			MockHttpServletResponse mockHttpServletResponse =
				new MockHttpServletResponse();

			_servlet.service(
				new MockHttpServletRequest(
					"GET",
					StringBundler.concat(
						PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING,
						_group.getFriendlyURL(), "/path")),
				mockHttpServletResponse);

			Assert.assertEquals(302, mockHttpServletResponse.getStatus());
			Assert.assertEquals(
				"http://www.liferay.com",
				mockHttpServletResponse.getHeader("Location"));

			redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
				_group.getGroupId(), "path");

			Assert.assertNotNull(redirectEntry.getLastOccurrenceDate());
		}
		finally {
			_redirectEntryLocalService.deleteRedirectEntry(redirectEntry);
		}
	}

	protected String getI18nLanguageId(HttpServletRequest httpServletRequest) {
		String path = GetterUtil.getString(httpServletRequest.getPathInfo());

		if (Validator.isNull(path)) {
			return null;
		}

		String i18nLanguageId = httpServletRequest.getServletPath();

		int pos = i18nLanguageId.lastIndexOf(CharPool.SLASH);

		i18nLanguageId = i18nLanguageId.substring(pos + 1);

		if (Validator.isNull(i18nLanguageId)) {
			return null;
		}

		Locale locale = LocaleUtil.fromLanguageId(i18nLanguageId, true, false);

		if ((locale == null) || Validator.isNull(locale.getCountry())) {
			locale = LanguageUtil.getLocale(i18nLanguageId);
		}

		if (locale != null) {
			i18nLanguageId = LocaleUtil.toLanguageId(locale);
		}

		if (!PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE &&
			!LanguageUtil.isAvailableLocale(i18nLanguageId)) {

			return null;
		}

		return i18nLanguageId;
	}

	protected String getPath(Group group, Layout layout) {
		return group.getFriendlyURL() + layout.getFriendlyURL();
	}

	protected String getURL(Layout layout) {
		return "/c/portal/layout?p_l_id=" + layout.getPlid() +
			"&p_v_l_s_g_id=0";
	}

	protected void testGetI18nRedirect(String i18nPath, String expectedI18nPath)
		throws Throwable {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(StringPool.SLASH);
		mockHttpServletRequest.setServletPath(i18nPath);

		String i18nLanguageId = getI18nLanguageId(mockHttpServletRequest);

		mockHttpServletRequest.setAttribute(
			WebKeys.I18N_LANGUAGE_ID, i18nLanguageId);

		String requestURI =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				getPath(_group, _layout);

		mockHttpServletRequest.setRequestURI(requestURI);

		Object expectedRedirect = null;

		if (!Objects.equals(i18nPath, expectedI18nPath)) {
			expectedRedirect = _redirectConstructor2.newInstance(
				expectedI18nPath + requestURI, true, true);
		}
		else {
			expectedRedirect = _redirectConstructor1.newInstance(
				getURL(_layout));
		}

		testGetRedirect(
			mockHttpServletRequest, _group.getFriendlyURL(), Portal.PATH_MAIN,
			expectedRedirect);

		testGetRedirect(
			mockHttpServletRequest, getPath(_group, _layout), Portal.PATH_MAIN,
			expectedRedirect);
	}

	protected void testGetRedirect(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String path,
			String mainPath, Object expectedRedirect)
		throws Throwable {

		try {
			Assert.assertEquals(
				expectedRedirect,
				_getRedirectMethod.invoke(
					_servlet, httpServletRequest, httpServletResponse, path));
		}
		catch (InvocationTargetException invocationTargetException) {
			throw invocationTargetException.getCause();
		}
	}

	protected void testGetRedirect(
			HttpServletRequest httpServletRequest, String path, String mainPath,
			Object expectedRedirect)
		throws Throwable {

		testGetRedirect(
			httpServletRequest, new MockHttpServletResponse(), path, mainPath,
			expectedRedirect);
	}

	@Inject
	private static LayoutLocalService _layoutLocalService;

	private Method _getRedirectMethod;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;
	private Constructor<?> _redirectConstructor1;
	private Constructor<?> _redirectConstructor2;

	@Inject
	private RedirectEntryLocalService _redirectEntryLocalService;

	@Inject(
		filter = "(&(servlet.type=friendly-url)(servlet.init.private=false))"
	)
	private Servlet _servlet;

	@DeleteAfterTestRun
	private User _user;

}