/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Marcellus Tavares
 */
public class CheckboxDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		PortletURLFactoryUtil portletURLFactoryUtil =
			new PortletURLFactoryUtil();

		PortletURLFactory portletURLFactory = Mockito.mock(
			PortletURLFactory.class);

		LiferayPortletURL liferayPortletURL = new MockLiferayPortletURL();

		Mockito.doReturn(
			liferayPortletURL
		).when(
			portletURLFactory
		).create(
			Mockito.any(PortletRequest.class), Mockito.anyString(),
			Mockito.anyString()
		);

		Mockito.doReturn(
			liferayPortletURL
		).when(
			portletURLFactory
		).create(
			Mockito.any(HttpServletRequest.class), Mockito.anyString(),
			Mockito.anyLong(), Mockito.anyString()
		);

		portletURLFactoryUtil.setPortletURLFactory(portletURLFactory);
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

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.doReturn(
			0L
		).when(
			themeDisplay
		).getPlid();

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		ddmFormFieldRenderingContext.setHttpServletRequest(httpServletRequest);

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		return ddmFormFieldRenderingContext;
	}

	private final CheckboxDDMFormFieldTemplateContextContributor
		_checkboxDDMFormFieldTemplateContextContributor =
			new CheckboxDDMFormFieldTemplateContextContributor();

}