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
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcos Martins
 */
@RunWith(PowerMockRunner.class)
public class DDMFormViewFormInstanceRecordsDisplayContextTest
	extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.setProps(new PropsImpl());
	}

	@Before
	public void setUp() throws PortalException {
		_setUpPortalUtil();

		_setUpDDMFormViewFormInstanceRecordsDisplayContext();
	}

	@Test
	public void testGetAvailableLocalesCount() throws Exception {
		Assert.assertEquals(
			2,
			_ddmFormViewFormInstanceRecordsDisplayContext.
				getAvailableLocalesCount());
	}

	@Test
	public void testGetDefaultLocale() throws Exception {
		DDMFormInstanceRecord ddmFormInstanceRecord = mock(
			DDMFormInstanceRecord.class);

		DDMFormValues ddmFormValues = mock(DDMFormValues.class);

		when(
			ddmFormValues.getDefaultLocale()
		).thenReturn(
			LocaleUtil.US
		);

		when(
			ddmFormInstanceRecord.getDDMFormValues()
		).thenReturn(
			ddmFormValues
		);

		Locale defaultLocale =
			_ddmFormViewFormInstanceRecordsDisplayContext.getDefaultLocale(
				ddmFormInstanceRecord);

		Assert.assertEquals(LocaleUtil.US, defaultLocale);
	}

	private DDMForm _mockDDMForm() {
		DDMForm ddmForm = mock(DDMForm.class);

		when(
			ddmForm.getAvailableLocales()
		).thenReturn(
			new HashSet<Locale>(Arrays.asList(LocaleUtil.US, LocaleUtil.BRAZIL))
		);

		return ddmForm;
	}

	private DDMFormInstance _mockDDMFormInstance() throws PortalException {
		DDMFormInstance ddmFormInstance = mock(DDMFormInstance.class);

		DDMForm ddmForm = _mockDDMForm();

		when(
			ddmFormInstance.getDDMForm()
		).thenReturn(
			ddmForm
		);

		DDMStructure ddmStructure = _mockDDMStructure(ddmForm);

		when(
			ddmFormInstance.getStructure()
		).thenReturn(
			ddmStructure
		);

		return ddmFormInstance;
	}

	private DDMStructure _mockDDMStructure(DDMForm ddmForm) {
		DDMStructure ddmStructure = mock(DDMStructure.class);

		when(
			ddmStructure.getDDMForm()
		).thenReturn(
			ddmForm
		);

		return ddmStructure;
	}

	private ThemeDisplay _mockThemeDisplay() {
		ThemeDisplay themeDisplay = mock(ThemeDisplay.class);

		when(
			themeDisplay.getPortletDisplay()
		).thenReturn(
			new PortletDisplay()
		);

		return themeDisplay;
	}

	private void _setUpDDMFormViewFormInstanceRecordsDisplayContext()
		throws PortalException {

		RenderRequest renderRequest = mock(RenderRequest.class);

		ThemeDisplay themeDisplay = _mockThemeDisplay();

		when(
			renderRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		when(
			renderRequest.getParameter(Mockito.eq("redirect"))
		).thenReturn(
			"test"
		);

		_ddmFormViewFormInstanceRecordsDisplayContext =
			new DDMFormViewFormInstanceRecordsDisplayContext(
				renderRequest, mock(RenderResponse.class),
				_mockDDMFormInstance(),
				mock(DDMFormInstanceRecordLocalService.class),
				mock(DDMFormFieldTypeServicesTracker.class));
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = mock(Portal.class);

		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

		ThemeDisplay themeDisplay = _mockThemeDisplay();

		when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		when(
			portal.getHttpServletRequest(Matchers.any(PortletRequest.class))
		).thenReturn(
			httpServletRequest
		);

		portalUtil.setPortal(portal);
	}

	private DDMFormViewFormInstanceRecordsDisplayContext
		_ddmFormViewFormInstanceRecordsDisplayContext;

}