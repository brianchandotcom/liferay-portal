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

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest(RequestBackedPortletURLFactoryUtil.class)
@RunWith(PowerMockRunner.class)
public class CheckboxDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpRequestBackedPortletURLFactoryUtil();
	}

	@Test
	public void testGetNotDefinedPredefinedValue() {
		DDMFormField ddmFormField = new DDMFormField("field", "checkbox");

		Map<String, Object> parameters =
			_checkboxDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, _createDDMFormFieldRenderingContext());

		boolean predefinedValue = (boolean)parameters.get("predefinedValue");

		Assert.assertFalse(predefinedValue);
	}

	@Test
	public void testGetParametersShouldContainBlankSystemSettingsURL() {
		Map<String, Object> parameters =
			_checkboxDDMFormFieldTemplateContextContributor.getParameters(
				new DDMFormField("field", "checkbox"),
				_createDDMFormFieldRenderingContext());

		String systemSettingsURL = String.valueOf(
			parameters.get("systemSettingsURL"));

		Assert.assertTrue(Validator.isBlank(systemSettingsURL));
	}

	@Test
	public void testGetParametersShouldContainShowMaximumRepetitionsInfo() {
		DDMFormField ddmFormField = new DDMFormField("field", "checkbox");

		ddmFormField.setProperty("showMaximumRepetitionsInfo", true);

		Map<String, Object> parameters =
			_checkboxDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, _createDDMFormFieldRenderingContext());

		boolean showMaximumRepetitionsInfo = (boolean)parameters.get(
			"showMaximumRepetitionsInfo");

		Assert.assertTrue(showMaximumRepetitionsInfo);
	}

	@Test
	public void testGetParametersShouldContainSystemSettingsURL() {
		DDMFormField ddmFormField = new DDMFormField("field", "checkbox");

		ddmFormField.setProperty("showMaximumRepetitionsInfo", true);

		Map<String, Object> parameters =
			_checkboxDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, _createDDMFormFieldRenderingContext());

		String systemSettingsURL = String.valueOf(
			parameters.get("systemSettingsURL"));

		Assert.assertThat(
			systemSettingsURL,
			CoreMatchers.containsString(
				"param_factoryPid=com.liferay.dynamic.data.mapping.form.web." +
					"internal.configuration.DDMFormWebConfiguration"));
		Assert.assertThat(
			systemSettingsURL,
			CoreMatchers.containsString(
				"param_mvcRenderCommandName=/configuration_admin" +
					"/edit_configuration"));
	}

	@Test
	public void testGetPredefinedValueFalse() {
		DDMFormField ddmFormField = new DDMFormField("field", "checkbox");

		LocalizedValue predefinedValue = new LocalizedValue(LocaleUtil.US);

		predefinedValue.addString(LocaleUtil.US, StringPool.FALSE);

		ddmFormField.setProperty("predefinedValue", predefinedValue);

		Map<String, Object> parameters =
			_checkboxDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, _createDDMFormFieldRenderingContext());

		boolean actualPredefinedValue = (boolean)parameters.get(
			"predefinedValue");

		Assert.assertFalse(actualPredefinedValue);
	}

	@Test
	public void testGetPredefinedValueTrue() {
		DDMFormField ddmFormField = new DDMFormField("field", "checkbox");

		LocalizedValue predefinedValue = new LocalizedValue(LocaleUtil.US);

		predefinedValue.addString(LocaleUtil.US, StringPool.TRUE);

		ddmFormField.setProperty("predefinedValue", predefinedValue);

		Map<String, Object> parameters =
			_checkboxDDMFormFieldTemplateContextContributor.getParameters(
				ddmFormField, _createDDMFormFieldRenderingContext());

		boolean actualPredefinedValue = (boolean)parameters.get(
			"predefinedValue");

		Assert.assertTrue(actualPredefinedValue);
	}

	private DDMFormFieldRenderingContext _createDDMFormFieldRenderingContext() {
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setHttpServletRequest(
			new MockHttpServletRequest());
		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		return ddmFormFieldRenderingContext;
	}

	private RequestBackedPortletURLFactory
		_mockRequestBackedPortletURLFactory() {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory = mock(
			RequestBackedPortletURLFactory.class);

		when(
			requestBackedPortletURLFactory.createActionURL(
				ConfigurationAdminPortletKeys.SYSTEM_SETTINGS)
		).thenReturn(
			new MockLiferayPortletURL()
		);

		return requestBackedPortletURLFactory;
	}

	private void _setUpRequestBackedPortletURLFactoryUtil() {
		PowerMockito.mockStatic(RequestBackedPortletURLFactoryUtil.class);

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			_mockRequestBackedPortletURLFactory();

		PowerMockito.when(
			RequestBackedPortletURLFactoryUtil.create(
				Matchers.any(HttpServletRequest.class))
		).thenReturn(
			requestBackedPortletURLFactory
		);
	}

	private final CheckboxDDMFormFieldTemplateContextContributor
		_checkboxDDMFormFieldTemplateContextContributor =
			new CheckboxDDMFormFieldTemplateContextContributor();

}