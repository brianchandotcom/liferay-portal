/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.numeric;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class NumericDDMFormFieldValueRendererTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testRender() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Numeric", "Numeric", "numeric", "double", false, false, false);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Numeric", new UnlocalizedValue("1.25"));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		String enRenderedValue = _numericDDMFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals("1.25", enRenderedValue);

		String ptRenderedValue = _numericDDMFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.BRAZIL);

		Assert.assertEquals("1,25", ptRenderedValue);
	}

	@Test
	public void testRenderShouldNotHaveDecimalLimit() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Numeric", "Numeric", "numeric", "double", false, false, false);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Numeric", new UnlocalizedValue("3.141592"));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		String enRenderedValue = _numericDDMFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals("3.141592", enRenderedValue);

		String ptRenderedValue = _numericDDMFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.BRAZIL);

		Assert.assertEquals("3,141592", ptRenderedValue);
	}

	@Test
	public void testRenderShouldNotHaveGroupingSymbols() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"Numeric", "Numeric", "numeric", "double", false, false, false);

		ddmForm.addDDMFormField(ddmFormField);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		LocalizedValue localizedValue = new LocalizedValue();

		localizedValue.addString(LocaleUtil.US, "111222333.25");

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Numeric", localizedValue);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		String enRenderedValue = _numericDDMFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals("111222333.25", enRenderedValue);

		String ptRenderedValue = _numericDDMFormFieldValueRenderer.render(
			ddmFormFieldValue, LocaleUtil.BRAZIL);

		Assert.assertEquals("111222333,25", ptRenderedValue);
	}

	private final NumericDDMFormFieldValueRenderer
		_numericDDMFormFieldValueRenderer =
			new NumericDDMFormFieldValueRenderer();

}