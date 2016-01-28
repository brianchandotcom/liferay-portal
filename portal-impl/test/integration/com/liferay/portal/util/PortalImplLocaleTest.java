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
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.servlet.I18nServlet;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Filipe Afonso
 */
public class PortalImplLocaleTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		PropsValues.LOCALES_ENABLED = new String[] {
			"ca_ES", "en_US", "fr_FR", "de_DE", "pt_BR", "es_ES", "en_GB"
		};

		LanguageUtil.init();

		LanguageResources.getSuperLocale(LocaleUtil.GERMANY);
		LanguageResources.getSuperLocale(LocaleUtil.US);

		_group = GroupTestUtil.addGroup();
		_layout = LayoutTestUtil.addLayout(_group);

		_availableLocales = LanguageUtil.getAvailableLocales();

		List<Locale> availableLocales = Arrays.asList(
			LocaleUtil.fromLanguageId("ca_ES"), LocaleUtil.US,
			LocaleUtil.FRANCE, LocaleUtil.GERMANY, LocaleUtil.BRAZIL,
			LocaleUtil.SPAIN, LocaleUtil.UK);

		CompanyTestUtil.resetCompanyLocales(
			_group.getCompanyId(), availableLocales, LocaleUtil.getDefault());

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(LocaleUtil.UK, LocaleUtil.GERMANY),
			LocaleUtil.GERMANY);
	}

	@After
	public void tearDown() throws Exception {
		PropsValues.LOCALES_ENABLED = PropsUtil.getArray(
			PropsKeys.LOCALES_ENABLED);

		LanguageUtil.init();

		CompanyTestUtil.resetCompanyLocales(
			TestPropsValues.getCompanyId(), _availableLocales,
			LocaleUtil.getDefault());
	}

	@Test
	public void testInvalidResourceWithLocale() throws Exception {
		MockHttpServletResponse httpServletResponse = testLocaleForLanguageId(
			"/en", "/WEB-INF/web.xml;.js", LocaleUtil.GERMANY);

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND, httpServletResponse.getStatus());
	}

	@Test
	public void testSiteAvailableLanguageId() throws Exception {
		testLocaleForLanguageId("/en", LocaleUtil.UK);
	}

	@Test
	public void testSiteAvailableLocale() throws Exception {
		testLocaleForLanguageId("/en_GB", LocaleUtil.UK);
	}

	@Test
	public void testSiteDefaultLanguageId() throws Exception {
		testLocaleForLanguageId("/de", LocaleUtil.GERMANY);
	}

	@Test
	public void testSiteDefaultLocale() throws Exception {
		testLocaleForLanguageId("/de_DE", LocaleUtil.GERMANY);
	}

	protected MockHttpServletRequest createHttpRequest(
		ServletContext servletContext, String servletPath, String pathInfo) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(
				servletContext, HttpMethods.GET, servletPath+pathInfo);

		mockHttpServletRequest.setPathInfo(pathInfo);
		mockHttpServletRequest.setServletPath(servletPath);

		return mockHttpServletRequest;
	}

	protected String getPath(Group group, Layout layout) {
		return group.getFriendlyURL() + layout.getFriendlyURL();
	}

	protected void testLocaleForLanguageId(
			String i18nLanguageId, Locale expectedLocale)
		throws IOException, ServletException {

		testLocaleForLanguageId(
			i18nLanguageId, getPath(_group, _layout), expectedLocale);
	}

	protected MockHttpServletResponse testLocaleForLanguageId(
			String i18nLanguageId, String pathInfo, Locale expectedLocale)
		throws IOException, ServletException {

		MockServletContext mockServletContext = new MockServletContext() {};

		mockServletContext.setContextPath(StringPool.BLANK);
		mockServletContext.setServletContextName(StringPool.BLANK);

		_i18nServlet.init(new MockServletConfig(mockServletContext));

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			mockServletContext, i18nLanguageId, pathInfo);

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		mockHttpServletRequest.setCookies(mockHttpServletResponse.getCookies());

		_i18nServlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Locale actualLocale = _portalImpl.getLocale(
			mockHttpServletRequest, mockHttpServletResponse, false);

		Assert.assertEquals(expectedLocale, actualLocale);

		return mockHttpServletResponse;
	}

	private Set<Locale> _availableLocales;

	@DeleteAfterTestRun
	private Group _group;

	private final I18nServlet _i18nServlet = new I18nServlet();

	@DeleteAfterTestRun
	private Layout _layout;

	private final PortalImpl _portalImpl = new PortalImpl();

}