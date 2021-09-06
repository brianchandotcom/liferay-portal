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

package com.liferay.dynamic.data.mapping.form.report.web.internal.display.context;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Rodrigo Paulino
 */
@PrepareForTest({LanguageUtil.class, ResourceBundleUtil.class})
@RunWith(PowerMockRunner.class)
public class DDMFormReportDisplayContextTest extends PowerMockito {

	@Before
	public void setUp() {
		_setUpJSONFactoryUtil();
		_setUpLanguageUtil();
		_setUpResourceBundleUtil();
	}

	@Test
	public void testGetSearchLocationFieldJSONArray() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField =
			DDMFormTestUtil.createSearchLocationDDMFormField(
				DDMFormValuesTestUtil.createLocalizedValue(
					StringPool.BLANK, LocaleUtil.US),
				"field1",
				DDMFormValuesTestUtil.createLocalizedValue(
					Arrays.toString(new String[] {"city", "country"}),
					LocaleUtil.US));

		ddmFormField.setLabel(
			DDMFormValuesTestUtil.createLocalizedValue(
				"Search Location Label", LocaleUtil.US));

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormReportDisplayContext ddmFormReportDisplayContext =
			new DDMFormReportDisplayContext(
				_mockDDMFormInstanceReport(_mockDDMFormInstance(ddmForm)),
				new MockLiferayPortletRenderRequest(),
				new MockLiferayPortletRenderResponse());

		JSONAssert.assertEquals(
			String.valueOf(
				JSONUtil.put(
					JSONUtil.put(
						"columns", JSONFactoryUtil.createJSONObject()
					).put(
						"label",
						String.valueOf(
							JSONUtil.put(
								"city", "City"
							).put(
								"country", "Country"
							).put(
								"place", "Search Location Label"
							))
					).put(
						"name", "field1"
					).put(
						"options", JSONFactoryUtil.createJSONObject()
					).put(
						"rows", JSONFactoryUtil.createJSONObject()
					).put(
						"type", DDMFormFieldTypeConstants.SEARCH_LOCATION
					))),
			String.valueOf(ddmFormReportDisplayContext.getFieldsJSONArray()),
			JSONCompareMode.STRICT_ORDER);
	}

	@Test
	public void testGetTextFieldJSONArray() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("field1");

		DDMFormReportDisplayContext ddmFormReportDisplayContext =
			new DDMFormReportDisplayContext(
				_mockDDMFormInstanceReport(_mockDDMFormInstance(ddmForm)),
				new MockLiferayPortletRenderRequest(),
				new MockLiferayPortletRenderResponse());

		JSONAssert.assertEquals(
			String.valueOf(
				JSONUtil.put(
					JSONUtil.put(
						"columns", JSONFactoryUtil.createJSONObject()
					).put(
						"label", "field1"
					).put(
						"name", "field1"
					).put(
						"options", JSONFactoryUtil.createJSONObject()
					).put(
						"rows", JSONFactoryUtil.createJSONObject()
					).put(
						"type", DDMFormFieldTypeConstants.TEXT
					))),
			String.valueOf(ddmFormReportDisplayContext.getFieldsJSONArray()),
			JSONCompareMode.STRICT_ORDER);
	}

	private DDMFormInstance _mockDDMFormInstance(DDMForm ddmForm)
		throws Exception {

		DDMFormInstance ddmFormInstance = mock(DDMFormInstance.class);

		when(
			ddmFormInstance.getDDMForm()
		).thenReturn(
			ddmForm
		);

		return ddmFormInstance;
	}

	private DDMFormInstanceReport _mockDDMFormInstanceReport(
			DDMFormInstance ddmFormInstance)
		throws Exception {

		DDMFormInstanceReport ddmFormInstanceReport = mock(
			DDMFormInstanceReport.class);

		when(
			ddmFormInstanceReport.getFormInstance()
		).thenReturn(
			ddmFormInstance
		);

		return ddmFormInstanceReport;
	}

	private void _mockGet(String key, String message) {
		PowerMockito.when(
			LanguageUtil.get(
				Matchers.any(ResourceBundle.class), Matchers.eq(key))
		).thenReturn(
			message
		);
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _setUpLanguageUtil() {
		PowerMockito.mockStatic(LanguageUtil.class);

		_mockGet("city", "City");
		_mockGet("country", "Country");
	}

	private void _setUpResourceBundleUtil() {
		PowerMockito.mockStatic(ResourceBundleUtil.class);

		PowerMockito.when(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				Matchers.any(Locale.class), Matchers.any())
		).thenReturn(
			PowerMockito.mock(ResourceBundle.class)
		);
	}

}