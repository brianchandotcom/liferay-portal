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

package com.liferay.dynamic.data.mapping.form.web.internal.display.context;

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.portlet.RenderRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;

/**
 * @author Adam Brandizzi
 */
@PrepareForTest(LocaleUtil.class)
@RunWith(PowerMockRunner.class)
public class DDMFormDisplayContextTest extends PowerMockito {

	@Before
	public void setUp() throws PortalException {
		setUpDDMFormDisplayContext();
		setUpPortalUtil();
		setUpLanguageUtil();
		setUpLocaleUtil();
	}

	@Test
	public void testDDMFormRenderingContextLocaleIsThemeDisplayLocale() {
		Locale defaultLocale = LocaleUtil.BRAZIL;

		Set<Locale> availableLocales = new HashSet<>();

		availableLocales.add(defaultLocale);
		availableLocales.add(LocaleUtil.SPAIN);

		DDMForm ddmForm = createDDMForm(availableLocales, defaultLocale);

		_request.addParameter(
			"languageId", LocaleUtil.toLanguageId(LocaleUtil.SPAIN));

		DDMFormRenderingContext ddmFormRenderingContext =
			_ddmFormDisplayContext.createDDMFormRenderingContext(ddmForm);

		Assert.assertEquals(
			LocaleUtil.SPAIN, ddmFormRenderingContext.getLocale());
	}

	protected DDMForm createDDMForm(
		Set<Locale> availableLocales, Locale locale) {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(availableLocales);
		ddmForm.setDefaultLocale(locale);

		return ddmForm;
	}

	protected RenderRequest mockRenderRequest() {
		RenderRequest renderRequest = new MockRenderRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.SPAIN);

		renderRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return renderRequest;
	}

	protected void setUpDDMFormDisplayContext() throws PortalException {
		_ddmFormDisplayContext = new DDMFormDisplayContext(
			mockRenderRequest(), new MockRenderResponse(),
			mock(DDMFormInstanceService.class),
			mock(DDMFormInstanceRecordVersionLocalService.class),
			mock(DDMFormRenderer.class), mock(DDMFormValuesFactory.class),
			mock(DDMFormValuesMerger.class),
			mock(WorkflowDefinitionLinkLocalService.class));
	}

	protected void setUpLanguageUtil() {
		when(
			_language.getLanguageId(Matchers.eq(_request))
		).thenReturn(
			"es_ES"
		);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	protected void setUpLocaleUtil() {
		mockStatic(LocaleUtil.class);

		when(
			LocaleUtil.fromLanguageId("es_ES")
		).thenReturn(
			LocaleUtil.SPAIN
		);

		when(
			LocaleUtil.fromLanguageId("pt_BR")
		).thenReturn(
			LocaleUtil.BRAZIL
		);
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(mock(Portal.class));

		_request = new MockHttpServletRequest();

		when(
			PortalUtil.getHttpServletRequest(Matchers.any(RenderRequest.class))
		).thenReturn(
			_request
		);
	}

	private DDMFormDisplayContext _ddmFormDisplayContext;

	@Mock
	private Language _language;

	private MockHttpServletRequest _request;

}