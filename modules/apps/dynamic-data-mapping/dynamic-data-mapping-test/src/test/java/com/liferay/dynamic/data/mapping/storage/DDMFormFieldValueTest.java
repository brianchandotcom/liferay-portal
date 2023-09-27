/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.storage;

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldValueTest extends BaseDDMTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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