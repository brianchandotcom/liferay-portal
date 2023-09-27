/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carolina Barbosa
 */
public class DDMFormValuesConverterUtilTest extends BaseDDMTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAddMissingNestedDDMFormFieldValues() {
		DDMFormField ddmFormField = new DDMFormField("Fieldset", "fieldset");

		addNestedTextDDMFormFields(ddmFormField, "Text1", "Text2");

		DDMFormFieldValue textDDMFormFieldValue = createDDMFormFieldValue(
			"Text1");

		List<DDMFormFieldValue> ddmFormFieldValues =
			DDMFormValuesConverterUtil.addMissingDDMFormFieldValues(
				ListUtil.fromArray(ddmFormField),
				HashMapBuilder.<String, List<DDMFormFieldValue>>put(
					"Text1", ListUtil.fromArray(textDDMFormFieldValue)
				).build());

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 1, ddmFormFieldValues.size());

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Assert.assertEquals("Fieldset", ddmFormFieldValue.getName());

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		Assert.assertEquals(
			nestedDDMFormFieldValues.toString(), 2,
			nestedDDMFormFieldValues.size());

		Set<String> names = new HashSet<>();

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				nestedDDMFormFieldValues) {

			names.add(nestedDDMFormFieldValue.getName());
		}

		Assert.assertThat(names, CoreMatchers.hasItems("Text1", "Text2"));

		Assert.assertEquals(
			nestedDDMFormFieldValues.get(0), textDDMFormFieldValue);
	}

	@Test
	public void testRemoveExtraNestedDDMFormFieldValues() {
		DDMFormField ddmFormField = new DDMFormField("Fieldset", "fieldset");

		addNestedTextDDMFormFields(ddmFormField, "Text");

		DDMFormFieldValue dateDDMFormFieldValue = createDDMFormFieldValue(
			"Date");
		DDMFormFieldValue numericDDMFormFieldValue = createDDMFormFieldValue(
			"Numeric");

		DDMFormFieldValue nestedFieldsetDDMFormFieldValue =
			_getDDMFormFieldValue(
				"NestedFieldset", dateDDMFormFieldValue,
				numericDDMFormFieldValue);

		DDMFormFieldValue textDDMFormFieldValue = createDDMFormFieldValue(
			"Text");

		DDMFormFieldValue fieldsetDDMFormFieldValue = _getDDMFormFieldValue(
			"Fieldset", nestedFieldsetDDMFormFieldValue, textDDMFormFieldValue);

		List<DDMFormFieldValue> ddmFormFieldValues =
			DDMFormValuesConverterUtil.addMissingDDMFormFieldValues(
				ListUtil.fromArray(ddmFormField),
				HashMapBuilder.<String, List<DDMFormFieldValue>>put(
					"Date", ListUtil.fromArray(dateDDMFormFieldValue)
				).put(
					"Fieldset", ListUtil.fromArray(fieldsetDDMFormFieldValue)
				).put(
					"NestedFieldset",
					ListUtil.fromArray(nestedFieldsetDDMFormFieldValue)
				).put(
					"Numeric", ListUtil.fromArray(numericDDMFormFieldValue)
				).put(
					"Text", ListUtil.fromArray(textDDMFormFieldValue)
				).build());

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 1, ddmFormFieldValues.size());

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Assert.assertEquals("Fieldset", ddmFormFieldValue.getName());

		List<DDMFormFieldValue> nestedDDMFormFieldValues =
			ddmFormFieldValue.getNestedDDMFormFieldValues();

		Assert.assertEquals(
			nestedDDMFormFieldValues.toString(), 1,
			nestedDDMFormFieldValues.size());
		Assert.assertEquals(
			nestedDDMFormFieldValues.get(0), textDDMFormFieldValue);
	}

	private DDMFormFieldValue _getDDMFormFieldValue(
		String name, DDMFormFieldValue... nestedDDMFormFieldValues) {

		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(name);

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				nestedDDMFormFieldValues) {

			ddmFormFieldValue.addNestedDDMFormFieldValue(
				nestedDDMFormFieldValue);
		}

		return ddmFormFieldValue;
	}

}