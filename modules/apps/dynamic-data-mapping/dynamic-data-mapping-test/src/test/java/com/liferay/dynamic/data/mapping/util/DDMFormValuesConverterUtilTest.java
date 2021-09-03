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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class DDMFormValuesConverterUtilTest extends BaseDDMTestCase {

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

		Stream<DDMFormFieldValue> stream = nestedDDMFormFieldValues.stream();

		Assert.assertThat(
			stream.map(
				DDMFormFieldValue::getName
			).collect(
				Collectors.toSet()
			),
			CoreMatchers.hasItems("Text1", "Text2"));

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