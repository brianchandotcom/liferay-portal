/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.storage;

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesTest extends BaseDDMTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDDMFormFieldValuesMap() {
		DDMFormValues ddmFormValues = createDDMFormValues(null);

		String fieldName = StringUtil.randomString();

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(fieldName, null));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(fieldName, null));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(fieldName, null));

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			fieldName);

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 3, ddmFormFieldValues.size());
	}

	@Test
	public void testDDMFormFieldValuesReferencesMap() {
		DDMFormValues ddmFormValues = createDDMFormValues(null);

		String fieldName = StringUtil.randomString();

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(fieldName, null));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(fieldName, null));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(fieldName, null));

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesReferencesMap =
			ddmFormValues.getDDMFormFieldValuesReferencesMap(false);

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormFieldValuesReferencesMap.get(fieldName);

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 3, ddmFormFieldValues.size());
	}

	@Test
	public void testDDMFormFieldValuesReferencesMapIncludingNestedFields() {
		DDMFormValues ddmFormValues = createDDMFormValues(null);

		String fieldName = StringUtil.randomString();

		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(fieldName, null));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(fieldName, null));
		ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(fieldName, null));

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesReferencesMap =
			ddmFormValues.getDDMFormFieldValuesReferencesMap(true);

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormFieldValuesReferencesMap.get(fieldName);

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 3, ddmFormFieldValues.size());
	}

	@Test
	public void testEqualsWithDifferentAvailableLocales() {
		DDMFormValues ddmFormValues1 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormValues ddmFormValues2 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US),
			LocaleUtil.US);

		Assert.assertFalse(ddmFormValues1.equals(ddmFormValues2));
	}

	@Test
	public void testEqualsWithDifferentDDMFormFieldValues() {
		DDMFormValues ddmFormValues1 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmFormValues1.addDDMFormFieldValue(
			createDDMFormFieldValue(
				StringUtil.randomString(), StringUtil.randomString(),
				new UnlocalizedValue(StringUtil.randomString())));

		DDMFormValues ddmFormValues2 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmFormValues2.addDDMFormFieldValue(
			createDDMFormFieldValue(
				StringUtil.randomString(), StringUtil.randomString(),
				new UnlocalizedValue(StringUtil.randomString())));

		Assert.assertFalse(ddmFormValues1.equals(ddmFormValues2));
	}

	@Test
	public void testEqualsWithDifferentDefaultLocale() {
		DDMFormValues ddmFormValues1 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormValues ddmFormValues2 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.BRAZIL);

		Assert.assertFalse(ddmFormValues1.equals(ddmFormValues2));
	}

	@Test
	public void testEqualsWithDifferentOrderOfDDMFormFieldValues() {
		DDMFormValues ddmFormValues1 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormFieldValue nestedDDMFormFieldValue1 = createDDMFormFieldValue(
			StringUtil.randomString(), StringUtil.randomString(),
			new UnlocalizedValue(StringUtil.randomString()));

		DDMFormFieldValue nestedDDMFormFieldValue2 = createDDMFormFieldValue(
			StringUtil.randomString(), StringUtil.randomString(),
			new UnlocalizedValue(StringUtil.randomString()));

		ddmFormValues1.addDDMFormFieldValue(nestedDDMFormFieldValue1);
		ddmFormValues1.addDDMFormFieldValue(nestedDDMFormFieldValue2);

		DDMFormValues ddmFormValues2 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmFormValues2.addDDMFormFieldValue(nestedDDMFormFieldValue2);
		ddmFormValues2.addDDMFormFieldValue(nestedDDMFormFieldValue1);

		Assert.assertFalse(ddmFormValues1.equals(ddmFormValues2));
	}

	@Test
	public void testEqualsWithSameAttributes() {
		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			StringUtil.randomString(), StringUtil.randomString(),
			new UnlocalizedValue(StringUtil.randomString()));

		DDMFormValues ddmFormValues1 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmFormValues1.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormValues ddmFormValues2 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmFormValues2.addDDMFormFieldValue(ddmFormFieldValue);

		Assert.assertTrue(ddmFormValues1.equals(ddmFormValues2));
	}

}