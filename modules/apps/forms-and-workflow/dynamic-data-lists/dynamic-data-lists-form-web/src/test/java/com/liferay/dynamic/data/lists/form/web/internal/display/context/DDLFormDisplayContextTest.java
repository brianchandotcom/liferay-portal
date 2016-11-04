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

package com.liferay.dynamic.data.lists.form.web.internal.display.context;

import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.RenderRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;

/**
 * @author Adam Brandizzi
 */
public class DDLFormDisplayContextTest extends PowerMockito {

	@Before
	public void setUp() throws PortalException {
		setUpDDLFormDisplayContext();
		setUpPortalUtil();
	}

	@Test
	public void testDDMFormRenderingContextLocaleIsSiteLocale() {
		DDMForm ddmForm = createDDMForm(LocaleUtil.BRAZIL);

		DDMFormRenderingContext ddmFormRenderingContext =
			_ddlFormDisplayContext.createDDMFormRenderingContext(ddmForm);

		Assert.assertEquals(
			LocaleUtil.BRAZIL, ddmFormRenderingContext.getLocale());
	}

	protected DDMForm createDDMForm(Locale locale) {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(locale);

		return ddmForm;
	}

	protected RenderRequest mockRenderRequest() {
		RenderRequest renderRequest = new MockRenderRequest();

		renderRequest.setAttribute(WebKeys.THEME_DISPLAY, new ThemeDisplay());

		return renderRequest;
	}

	protected void setUpDDLFormDisplayContext() throws PortalException {
		_ddlFormDisplayContext = new DDLFormDisplayContext(
			mockRenderRequest(), new MockRenderResponse(),
			mock(DDLRecordSetService.class), mock(DDMFormRenderer.class),
			mock(DDMFormValuesFactory.class),
			mock(WorkflowDefinitionLinkLocalService.class));
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(mock(Portal.class));
	}

	private DDLFormDisplayContext _ddlFormDisplayContext;

}