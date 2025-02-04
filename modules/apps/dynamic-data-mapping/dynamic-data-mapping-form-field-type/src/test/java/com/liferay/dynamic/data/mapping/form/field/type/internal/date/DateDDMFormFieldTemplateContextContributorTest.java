/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.date;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.BaseDDMFormFieldTemplateContextContributorTestCase;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pedro Leite
 */
public class DateDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTemplateContextContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		setUpLanguageUtil();

		_setUpFastDateFormatFactoryUtil();

		_ddmFormField.setDDMForm(getDDMForm());

		ReflectionTestUtil.setFieldValue(
			_dateDDMFormFieldTemplateContextContributor, "_language", language);
	}

	@Test
	public void testGetLocalizedObjectFieldTrue() {
		_ddmFormField.setProperty("localizedObjectField", true);

		Assert.assertTrue(
			MapUtil.getBoolean(
				_dateDDMFormFieldTemplateContextContributor.getParameters(
					_ddmFormField, createDDMFormFieldRenderingContext()),
				"localizedObjectField"));
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

		Assert.assertEquals(
			value,
			MapUtil.getString(
				_dateDDMFormFieldTemplateContextContributor.getParameters(
					_ddmFormField, ddmFormFieldRenderingContext),
				"value"));
	}

	private void _setUpFastDateFormatFactoryUtil() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());
	}

	private final DateDDMFormFieldTemplateContextContributor
		_dateDDMFormFieldTemplateContextContributor =
			new DateDDMFormFieldTemplateContextContributor();
	private final DDMFormField _ddmFormField = new DDMFormField(
		"field", DDMFormFieldTypeConstants.DATE);

}