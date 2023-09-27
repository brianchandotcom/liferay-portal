/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.HtmlImpl;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Renato Rego
 */
public class SelectDDMFormFieldValueRendererAccessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testRender() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Select", "Select", "select", "string", false, false, false);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel(
			"value 1", LocaleUtil.US, "option 1");
		ddmFormFieldOptions.addOptionLabel(
			"value 2", LocaleUtil.US, "option 2");
		ddmFormFieldOptions.addOptionLabel(
			"value 3", LocaleUtil.US, "option with &");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setProperty("dataSourceType", "manual");

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Select",
				new UnlocalizedValue(
					JSONUtil.putAll(
						"value 1", "value 2", "value 3"
					).toString()));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		SelectDDMFormFieldValueRenderer selectDDMFormFieldValueRenderer =
			_createSelectDDMFormFieldValueRenderer();

		Assert.assertEquals(
			"option 1, option 2, option with &amp;",
			selectDDMFormFieldValueRenderer.render(
				ddmFormFieldValue, LocaleUtil.US));
	}

	private SelectDDMFormFieldValueAccessor
		_createSelectDDMFormFieldValueAccessor() {

		SelectDDMFormFieldValueAccessor selectDDMFormFieldValueAccessor =
			new SelectDDMFormFieldValueAccessor();

		selectDDMFormFieldValueAccessor.jsonFactory = _jsonFactory;

		return selectDDMFormFieldValueAccessor;
	}

	private SelectDDMFormFieldValueRenderer
			_createSelectDDMFormFieldValueRenderer()
		throws Exception {

		SelectDDMFormFieldValueRenderer selectDDMFormFieldValueRenderer =
			new SelectDDMFormFieldValueRenderer();

		selectDDMFormFieldValueRenderer.selectDDMFormFieldValueAccessor =
			_createSelectDDMFormFieldValueAccessor();

		ReflectionTestUtil.setFieldValue(
			selectDDMFormFieldValueRenderer, "_html", new HtmlImpl());

		return selectDDMFormFieldValueRenderer;
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}