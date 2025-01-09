/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.date;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pedro Leite
 */
public class DateDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_ddmFormField.setDDMForm(getDDMForm());

		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		ReflectionTestUtil.setFieldValue(
			_dateDDMFormFieldTemplateContextContributor, "_language", language);

		setUpLanguageUtil();
	}

	@Test
	public void testGetLocalizedObjectFieldTrue() {
		_ddmFormField.setProperty("localizedObjectField", true);

		Map<String, Object> parameters =
			_dateDDMFormFieldTemplateContextContributor.getParameters(
				_ddmFormField, createDDMFormFieldRenderingContext());

		Assert.assertTrue((boolean)parameters.get("localizedObjectField"));
	}

	@Test
	public void testGetValue() {
		_ddmFormField.setProperty("localizedObjectField", true);

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			createDDMFormFieldRenderingContext();

		String value = JSONUtil.put(
			"en_US", "2010-10-10"
		).put(
			"pt_BR", "2011-11-11"
		).toString();

		ddmFormFieldRenderingContext.setValue(value);

		Map<String, Object> parameters =
			_dateDDMFormFieldTemplateContextContributor.getParameters(
				_ddmFormField, ddmFormFieldRenderingContext);

		Assert.assertEquals(value, String.valueOf(parameters.get("value")));
	}

	private final DateDDMFormFieldTemplateContextContributor
		_dateDDMFormFieldTemplateContextContributor =
			new DateDDMFormFieldTemplateContextContributor();
	private final DDMFormField _ddmFormField = new DDMFormField(
		"field", DDMFormFieldTypeConstants.DATE);

}