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

package com.liferay.dynamic.data.mapping.storage;

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldValueTest extends BaseDDMTestCase {

	@Test
	public void testEqualsWithDifferentInstanceId() {
		DDMFormFieldValue ddmFormFieldValue1 = createDDMFormFieldValue(
			StringUtil.randomString(), "Test", new UnlocalizedValue("Value"));

		DDMFormFieldValue ddmFormFieldValue2 = createDDMFormFieldValue(
			StringUtil.randomString(), "Test", new UnlocalizedValue("Value"));

		Assert.assertNotEquals(ddmFormFieldValue1, ddmFormFieldValue2);
	}

	@Test
	public void testEqualsWithDifferentName() {
		DDMFormFieldValue ddmFormFieldValue1 = createDDMFormFieldValue(
			"xhsy", StringUtil.randomString(), new UnlocalizedValue("Value"));

		DDMFormFieldValue ddmFormFieldValue2 = createDDMFormFieldValue(
			"xhsy", StringUtil.randomString(), new UnlocalizedValue("Value"));

		Assert.assertNotEquals(ddmFormFieldValue1, ddmFormFieldValue2);
	}

	@Test
	public void testEqualsWithDifferentNestedDDMFormFieldValues() {
		DDMFormFieldValue ddmFormFieldValue1 = createDDMFormFieldValue(
			"xhsy", "Test", new UnlocalizedValue("Value"));

		ddmFormFieldValue1.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"jamy", "Nested", new UnlocalizedValue("Nested Value")));

		DDMFormFieldValue ddmFormFieldValue2 = createDDMFormFieldValue(
			"xhsy", "Test", new UnlocalizedValue("Value"));

		ddmFormFieldValue2.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"jamy", "Nested",
				new UnlocalizedValue("Different Nested Value")));

		Assert.assertNotEquals(ddmFormFieldValue1, ddmFormFieldValue2);
	}

	@Test
	public void testEqualsWithDifferentValue() {
		DDMFormFieldValue ddmFormFieldValue1 = createDDMFormFieldValue(
			"xhsy", "Test", new UnlocalizedValue("Value"));

		DDMFormFieldValue ddmFormFieldValue2 = createDDMFormFieldValue(
			"xhsy", "Test", new UnlocalizedValue("Different Value"));

		Assert.assertNotEquals(ddmFormFieldValue1, ddmFormFieldValue2);
	}

	@Test
	public void testEqualsWithSameAttributes() {
		DDMFormFieldValue ddmFormFieldValue1 = createDDMFormFieldValue(
			"xhsy", "Test", new UnlocalizedValue("Value"));

		ddmFormFieldValue1.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"jamy", "Nested", new UnlocalizedValue("Nested Value")));

		DDMFormFieldValue ddmFormFieldValue2 = createDDMFormFieldValue(
			"xhsy", "Test", new UnlocalizedValue("Value"));

		ddmFormFieldValue2.addNestedDDMFormFieldValue(
			createDDMFormFieldValue(
				"jamy", "Nested", new UnlocalizedValue("Nested Value")));

		Assert.assertEquals(ddmFormFieldValue1, ddmFormFieldValue2);
	}

}