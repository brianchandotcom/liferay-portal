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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author László Csontos
 */
public class FriendlyURLServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		PropsValues.LOCALES_ENABLED = new String[] {"en_US", "hu_HU", "en_GB"};
		PropsValues.LOCALE_USE_DEFAULT_IF_NOT_AVAILABLE = true;

		LanguageUtil.init();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);

		List<Locale> availableLocales = Arrays.asList(
			LocaleUtil.US, LocaleUtil.UK, LocaleUtil.HUNGARY);

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), availableLocales, LocaleUtil.US);
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
	public void testGetRedirectWithExistentSite() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(StringPool.SLASH);

		testGetRedirect(
			mockHttpServletRequest, getPath(_group, _layout), Portal.PATH_MAIN,
			new FriendlyURLServlet.Redirect(getURL(_layout)));
	}

	@Test
	public void testGetRedirectWithI18nPath() throws Exception {
		testGetI18nRedirect("/fr", "/en");
		testGetI18nRedirect("/hu", "/hu");
		testGetI18nRedirect("/en", "/en");
		testGetI18nRedirect("/en_GB", "/en_GB");
		testGetI18nRedirect("/en_US", "/en_US");
	}

	@Test(expected = NoSuchGroupException.class)
	public void testGetRedirectWithNonexistentSite() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(StringPool.SLASH);

		testGetRedirect(
			mockHttpServletRequest, "/nonexistent-site/home", Portal.PATH_MAIN,
			null);
	}

	protected String getPath(Group group, Layout layout) {
		return group.getFriendlyURL() + layout.getFriendlyURL();
	}

	protected String getURL(Layout layout) {
		return "/c/portal/layout?p_l_id=" + layout.getPlid() +
			"&p_v_l_s_g_id=0";
	}

	protected void testGetI18nRedirect(String i18nPath, String expectedI18nPath)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setPathInfo(StringPool.SLASH);
		mockHttpServletRequest.setServletPath(i18nPath);

		I18nServlet.I18nData i18nData = _i18nServlet.getI18nData(
			mockHttpServletRequest);

		mockHttpServletRequest.setAttribute(
			WebKeys.I18N_LANGUAGE_ID,
			(i18nData == null) ? null : i18nData.getLanguageId());

		String requestURI =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				getPath(_group, _layout);

		mockHttpServletRequest.setRequestURI(requestURI);

		FriendlyURLServlet.Redirect expectedRedirect = null;

		if (!Objects.equals(i18nPath, expectedI18nPath)) {
			expectedRedirect = new FriendlyURLServlet.Redirect(
				expectedI18nPath + requestURI, true, true);
		}
		else {
			expectedRedirect = new FriendlyURLServlet.Redirect(getURL(_layout));
		}

		testGetRedirect(
			mockHttpServletRequest, _group.getFriendlyURL(), Portal.PATH_MAIN,
			expectedRedirect);

		testGetRedirect(
			mockHttpServletRequest, getPath(_group, _layout), Portal.PATH_MAIN,
			expectedRedirect);
	}

	protected void testGetRedirect(
			HttpServletRequest request, String path, String mainPath,
			FriendlyURLServlet.Redirect expectedRedirect)
		throws Exception {

		FriendlyURLServlet.Redirect actualRedirect =
			_friendlyURLServlet.getRedirect(request, path);

		Assert.assertEquals(expectedRedirect, actualRedirect);
	}

	private final FriendlyURLServlet _friendlyURLServlet =
		new FriendlyURLServlet();

	@DeleteAfterTestRun
	private Group _group;

	private final I18nServlet _i18nServlet = new I18nServlet();
	private Layout _layout;

}