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

package com.liferay.portal.search.test.util;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.Collections;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.portlet.MockPortletResponse;
import org.springframework.mock.web.portlet.MockRenderRequest;

/**
 * @author Adam Brandizzi
 */
public class SummaryFixture<T> {

	public SummaryFixture(
		Class<T> clazz, Group group, Locale defaultLocale, User user) {

		_class = clazz;
		_indexer = IndexerRegistryUtil.getIndexer(clazz);
		_group = group;
		_defaultLocale = defaultLocale;
		_user = user;
	}

	public void assertSummary(String title, String content, Document document)
		throws Exception {

		assertSummary(title, content, _defaultLocale, document);
	}

	public void assertSummary(
			String title, String content, Locale locale, Document document)
		throws Exception {

		Assert.assertEquals(
			_class.getName(), document.get(Field.ENTRY_CLASS_NAME));

		Summary summary = getSummary(document, locale);

		Assert.assertEquals(content, summary.getContent());
		Assert.assertEquals(title, summary.getTitle());
	}

	public Summary getSummary(Document document, Locale locale)
		throws Exception {

		return _indexer.getSummary(
			document, document.get(Field.SNIPPET), createPortletRequest(locale),
			createPortletResponse());
	}

	protected HttpServletRequest createHttpServletRequest(
		PortletRequest portletRequest, Locale locale) {

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST, portletRequest);
		httpServletRequest.setPreferredLocales(
			Collections.singletonList(locale));

		return httpServletRequest;
	}

	protected HttpServletResponse createHttpServletResponse() {
		return new MockHttpServletResponse();
	}

	protected PortletRequest createPortletRequest(Locale locale)
		throws Exception {

		MockRenderRequest portletRequest = new MockRenderRequest();

		HttpServletRequest request = createHttpServletRequest(
			portletRequest, locale);

		HttpServletResponse response = createHttpServletResponse();

		ThemeDisplay themeDisplay = createThemeDisplay(
			request, response, locale);

		portletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		portletRequest.addPreferredLocale(locale);

		request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return portletRequest;
	}

	protected PortletResponse createPortletResponse() {
		return new MockPortletResponse();
	}

	protected ThemeDisplay createThemeDisplay(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Locale locale)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			CompanyLocalServiceUtil.getCompany(_group.getCompanyId()));
		themeDisplay.setLayout(LayoutTestUtil.addLayout(_group));

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLayoutSet(layoutSet);

		themeDisplay.setLocale(locale);

		Theme theme = ThemeLocalServiceUtil.getTheme(
			_group.getCompanyId(), layoutSet.getThemeId());

		themeDisplay.setLookAndFeel(theme, null);

		themeDisplay.setRealUser(_user);
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setResponse(httpServletResponse);
		themeDisplay.setTimeZone(TimeZoneUtil.getDefault());
		themeDisplay.setUser(_user);

		return themeDisplay;
	}

	private final Class<T> _class;
	private final Locale _defaultLocale;
	private final Group _group;
	private final Indexer<T> _indexer;
	private final User _user;

}