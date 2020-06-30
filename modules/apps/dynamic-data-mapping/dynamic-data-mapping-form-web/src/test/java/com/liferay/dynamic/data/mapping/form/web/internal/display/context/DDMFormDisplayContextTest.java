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

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsImpl;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.setProps(new PropsImpl());
	}

	@Before
	public void setUp() throws PortalException {
		setUpLanguageUtil();
		setUpLocaleUtil();
		setUpPortalUtil();
	}

	@Test
	public void testDDMFormRenderingContextLocaleIsThemeDisplayLocale()
		throws Exception {

		DDMFormDisplayContext ddmFormDisplayContext =
			createDDMFormDisplayContext();

		Locale defaultLocale = LocaleUtil.BRAZIL;

		Set<Locale> availableLocales = new HashSet<>();

		availableLocales.add(defaultLocale);
		availableLocales.add(LocaleUtil.SPAIN);

		DDMForm ddmForm = createDDMForm(availableLocales, defaultLocale);

		_request.addParameter(
			"languageId", LocaleUtil.toLanguageId(LocaleUtil.SPAIN));

		DDMFormRenderingContext ddmFormRenderingContext =
			ddmFormDisplayContext.createDDMFormRenderingContext(ddmForm);

		Assert.assertEquals(
			LocaleUtil.SPAIN, ddmFormRenderingContext.getLocale());
	}

	@Test
	public void testIsFormAvailableForGuest() throws Exception {
		DDMFormInstance ddmFormInstance = mockDDMFormInstance();

		when(
			_ddmFormInstanceLocalService.fetchFormInstance(Matchers.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		when(
			_ddmFormInstanceService.fetchFormInstance(Matchers.anyLong())
		).thenReturn(
			null
		);

		DDMFormDisplayContext ddmFormDisplayContext =
			createDDMFormDisplayContext();

		Assert.assertFalse(ddmFormDisplayContext.isFormAvailable());
	}

	@Test
	public void testIsFormAvailableForLoggedUser() throws Exception {
		DDMFormInstance ddmFormInstance = mockDDMFormInstance();

		when(
			_ddmFormInstanceLocalService.fetchFormInstance(Matchers.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		when(
			_ddmFormInstanceService.fetchFormInstance(Matchers.anyLong())
		).thenReturn(
			ddmFormInstance
		);

		DDMFormDisplayContext ddmFormDisplayContext =
			createDDMFormDisplayContext();

		Assert.assertTrue(ddmFormDisplayContext.isFormAvailable());
	}

	@Test
	public void testIsSharedFormWithoutPortletSession() throws Exception {
		MockRenderRequest renderRequest = mockRenderRequest();

		Assert.assertNull(renderRequest.getPortletSession(false));

		renderRequest.setParameter("shared", Boolean.TRUE.toString());

		DDMFormDisplayContext createDDMFormDisplayContext =
			createDDMFormDisplayContext(renderRequest);

		Assert.assertTrue(createDDMFormDisplayContext.isFormShared());
	}

	@Test
	public void testIsSharedFormWithPortletSession() throws Exception {
		MockRenderRequest renderRequest = mockRenderRequest();

		PortletSession portletSession = renderRequest.getPortletSession(true);

		Assert.assertNotNull(portletSession);

		portletSession.setAttribute("shared", Boolean.TRUE);

		DDMFormDisplayContext createDDMFormDisplayContext =
			createDDMFormDisplayContext(renderRequest);

		Assert.assertTrue(createDDMFormDisplayContext.isFormShared());
	}

	protected DDMForm createDDMForm(
		Set<Locale> availableLocales, Locale locale) {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(availableLocales);
		ddmForm.setDefaultLocale(locale);

		return ddmForm;
	}

	protected DDMFormDisplayContext createDDMFormDisplayContext()
		throws PortalException {

		return createDDMFormDisplayContext(mockRenderRequest());
	}

	protected DDMFormDisplayContext createDDMFormDisplayContext(
			RenderRequest renderRequest)
		throws PortalException {

		return new DDMFormDisplayContext(
			renderRequest, new MockRenderResponse(),
			mock(DDMFormFieldTypeServicesTracker.class),
			_ddmFormInstanceLocalService,
			mock(DDMFormInstanceRecordVersionLocalService.class),
			_ddmFormInstanceService,
			mock(DDMFormInstanceVersionLocalService.class),
			mock(DDMFormRenderer.class), mock(DDMFormValuesFactory.class),
			mock(DDMFormValuesMerger.class), mock(GroupLocalService.class),
			new JSONFactoryImpl(),
			mock(WorkflowDefinitionLinkLocalService.class), mock(Portal.class));
	}

	protected DDMFormInstance mockDDMFormInstance() throws PortalException {
		DDMFormInstance formInstance = mock(DDMFormInstance.class);

		DDMFormInstanceSettings formInstanceSettings = mock(
			DDMFormInstanceSettings.class);

		when(
			formInstance.getSettingsModel()
		).thenReturn(
			formInstanceSettings
		);

		return formInstance;
	}

	protected MockRenderRequest mockRenderRequest() {
		MockRenderRequest mockRenderRequest = new MockRenderRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(mock(Layout.class));
		themeDisplay.setLocale(LocaleUtil.SPAIN);

		mockRenderRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return mockRenderRequest;
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

		when(
			PortalUtil.getHttpServletRequest(Matchers.any(RenderRequest.class))
		).thenReturn(
			_request
		);
	}

	@Mock
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Mock
	private DDMFormInstanceService _ddmFormInstanceService;

	@Mock
	private Language _language;

	@Mock
	private MockHttpServletRequest _request;

}