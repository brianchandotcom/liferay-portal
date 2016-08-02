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

package com.liferay.dynamic.data.mapping.form.values.query;

import com.liferay.dynamic.data.mapping.form.values.query.internal.DDMFormValuesQueryFactoryImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest(LocaleUtil.class)
@RunWith(PowerMockRunner.class)
public class DDMFormValuesQueryTest extends PowerMockito {

	@Before
	public void setUp() {
		_ddmFormValues = createDDMFormValues();
		_ddmFormValuesQueryFactory = new DDMFormValuesQueryFactoryImpl();

		setUpLocaleUtil();
	}

	@Test
	public void testSelectAllFromRootUsingMultipleStep() throws Exception {
		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery("//*");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(18, ddmFormFieldValues.size());
	}

	@Test
	public void testSelectAllFromRootUsingSingleStep() throws Exception {
		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery("/*");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(3, ddmFormFieldValues.size());
		Assert.assertEquals(
			"text1", getDDMFormFieldValueFieldName(ddmFormFieldValues.get(0)));
		Assert.assertEquals(
			"text2", getDDMFormFieldValueFieldName(ddmFormFieldValues.get(1)));
		Assert.assertEquals(
			"text3", getDDMFormFieldValueFieldName(ddmFormFieldValues.get(2)));
	}

	@Test
	public void testSelectAllFromText1UsingSingleAndMultipleStep()
		throws Exception {

		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"/text1//*");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(4, ddmFormFieldValues.size());
		Assert.assertEquals(
			"text11", getDDMFormFieldValueFieldName(ddmFormFieldValues.get(0)));
		Assert.assertEquals(
			"text11", getDDMFormFieldValueFieldName(ddmFormFieldValues.get(1)));
		Assert.assertEquals(
			"text11", getDDMFormFieldValueFieldName(ddmFormFieldValues.get(2)));
		Assert.assertEquals(
			"text12", getDDMFormFieldValueFieldName(ddmFormFieldValues.get(3)));
	}

	@Test
	public void testSelectAllFromText3UsingMultipleStep() throws Exception {
		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"//text3//*");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(7, ddmFormFieldValues.size());
	}

	@Test
	public void testSelectAllFromText3UsingSingleStep() throws Exception {
		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"/text3/*");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(2, ddmFormFieldValues.size());
	}

	@Test
	public void testSelectText2FromRootUsingSingleStep() throws Exception {
		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"/text2");

		DDMFormFieldValue ddmFormFieldValue =
			ddmFormValuesQuery.selectSingleDDMFormFieldValue();

		Assert.assertEquals("text2", ddmFormFieldValue.getName());
	}

	@Test
	public void testSelectText12FromRootUsingMultipleStep() throws Exception {
		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"//text12");

		DDMFormFieldValue ddmFormFieldValue =
			ddmFormValuesQuery.selectSingleDDMFormFieldValue();

		Assert.assertEquals("text12", ddmFormFieldValue.getName());
	}

	@Test
	public void testSelectText12FromRootUsingSingleStep() throws Exception {
		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"/text12");

		DDMFormFieldValue ddmFormFieldValue =
			ddmFormValuesQuery.selectSingleDDMFormFieldValue();

		Assert.assertNull(ddmFormFieldValue);
	}

	@Test
	public void testSelectText311From31UsingMultipleAndSingleStep()
		throws Exception {

		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"//text31/text313");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(1, ddmFormFieldValues.size());
		Assert.assertEquals(
			"text313",
			getDDMFormFieldValueFieldName(ddmFormFieldValues.get(0)));
	}

	@Test
	public void testSelectText311From31UsingMultipleStep() throws Exception {
		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"//text313");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(1, ddmFormFieldValues.size());
		Assert.assertEquals(
			"text313",
			getDDMFormFieldValueFieldName(ddmFormFieldValues.get(0)));
	}

	@Test
	public void testSelectUsingAndExpressionAndSingleStep() throws Exception {
		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"//*[@value('en_US') = 'En text22' and " +
				"@value('pt_BR') = 'Pt text22']");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(3, ddmFormFieldValues.size());

		ddmFormValuesQuery = createDDMFormValuesQuery(
			"//*[@value('en_US') = 'En text22' and @type = 'text']");

		ddmFormFieldValues = ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(3, ddmFormFieldValues.size());

		ddmFormValuesQuery = createDDMFormValuesQuery(
			"//*[@value('en_US') = 'En text22' and @value('pt_BR') = 'wrong']");

		ddmFormFieldValues = ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(0, ddmFormFieldValues.size());
	}

	@Test
	public void testSelectUsingAndExpressionForLocalizedFieldAndMulitpleStep()
		throws Exception {

		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"//*[@value('en_US') = 'En text22' and " +
				"@value('pt_BR') = 'Pt text22']");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(3, ddmFormFieldValues.size());

		ddmFormValuesQuery = createDDMFormValuesQuery(
			"//*[@value('en_US') = 'En text22' and @type = 'text']");

		ddmFormFieldValues = ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(3, ddmFormFieldValues.size());

		ddmFormValuesQuery = createDDMFormValuesQuery(
			"//*[@value('en_US') = 'En text22' and @value('pt_BR') = 'wrong']");

		ddmFormFieldValues = ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(0, ddmFormFieldValues.size());
	}

	@Test
	public void testSelectUsingLocalizedValueMatcherAndMulitpleStep()
		throws Exception {

		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"//*[@value('en_US') = 'En text22']");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(3, ddmFormFieldValues.size());

		ddmFormValuesQuery = createDDMFormValuesQuery(
			"//*[@value('en_US') = 'Pt text22']");

		ddmFormFieldValues = ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(0, ddmFormFieldValues.size());
	}

	@Test
	public void testSelectUsingLocalizedValueMatcherAndSingleStep()
		throws Exception {

		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"/text3[@value('en_US') = 'text3']");

		DDMFormFieldValue ddmFormFieldValue =
			ddmFormValuesQuery.selectSingleDDMFormFieldValue();

		Assert.assertEquals(
			"text3", getDDMFormFieldValueFieldName(ddmFormFieldValue));
	}

	@Test
	public void testSelectUsingMixedBooleanExpressionMatchers()
		throws Exception {

		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"//*[@value('en_US') = 'wrong' and @value('en_US') = 'wrong' or" +
				"@value('pt_BR') = 'Pt text22']");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(3, ddmFormFieldValues.size());
	}

	@Test
	public void testSelectUsingMultipleAndSingleSteps() throws Exception {
		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"//text31/text311");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(1, ddmFormFieldValues.size());

		ddmFormValuesQuery = createDDMFormValuesQuery("//text3/text311");

		ddmFormFieldValues = ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(0, ddmFormFieldValues.size());

		ddmFormValuesQuery = createDDMFormValuesQuery("/text3//text311");

		ddmFormFieldValues = ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(1, ddmFormFieldValues.size());

		ddmFormValuesQuery = createDDMFormValuesQuery("/text31//text311");

		ddmFormFieldValues = ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(0, ddmFormFieldValues.size());

		ddmFormValuesQuery = createDDMFormValuesQuery(
			"//*/text22");

		ddmFormFieldValues = ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(3, ddmFormFieldValues.size());
	}

	@Test
	public void testSelectUsingOrPredicateMatchers() throws Exception {
		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"/*[@value('en_US') = 'En text22' or @value('pt_BR') = 'wrong']");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(0, ddmFormFieldValues.size());

		ddmFormValuesQuery = createDDMFormValuesQuery(
			"/*[@value('en_US') = 'wrong' or @type = 'text']");

		ddmFormFieldValues = ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(3, ddmFormFieldValues.size());

		ddmFormValuesQuery = createDDMFormValuesQuery(
			"/*[@value('en_US') = 'En text1' or @value('pt_BR') = 'wrong']");

		ddmFormFieldValues = ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(1, ddmFormFieldValues.size());
	}

	@Test
	public void testSelectUsingTypePredicateMatcherAndMultipleStep()
		throws Exception {

		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"//*[@type = 'text']");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(18, ddmFormFieldValues.size());

		ddmFormValuesQuery = createDDMFormValuesQuery("/*[@type = 'date']");

		ddmFormFieldValues = ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(0, ddmFormFieldValues.size());
	}

	@Test
	public void testSelectUsingValuePredicateMatcherForLocalizedField()
		throws Exception {

		DDMFormValuesQuery ddmFormValuesQuery = createDDMFormValuesQuery(
			"//*[@value = 'En text22']");

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(3, ddmFormFieldValues.size());

		ddmFormValuesQuery = createDDMFormValuesQuery(
			"//*[@value = 'Pt text22']");

		ddmFormFieldValues = ddmFormValuesQuery.selectDDMFormFieldValues();

		Assert.assertEquals(3, ddmFormFieldValues.size());
	}

	@Test(expected = DDMFormValuesQuerySyntaxException.class)
	public void testSyntaxError() throws Exception {
		createDDMFormValuesQuery("~/]");
	}

	protected Set<Locale> createAvailableLocales(Locale... locales) {
		Set<Locale> availableLocales = new LinkedHashSet<>();

		for (Locale locale : locales) {
			availableLocales.add(locale);
		}

		return availableLocales;
	}

	protected DDMForm createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(
			createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US));
		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormField text1DDMFormField = createTextDDMFormField(
			"text1", true, true);

		text1DDMFormField.addNestedDDMFormField(
			createTextDDMFormField("text11", true, true));
		text1DDMFormField.addNestedDDMFormField(
			createTextDDMFormField("text12", false, false));

		ddmForm.addDDMFormField(text1DDMFormField);

		DDMFormField text2DDMFormField = createTextDDMFormField(
			"text2", false, false);

		text2DDMFormField.addNestedDDMFormField(
			createTextDDMFormField("text21", false, false));
		text2DDMFormField.addNestedDDMFormField(
			createTextDDMFormField("text22", true, true));

		ddmForm.addDDMFormField(text2DDMFormField);

		DDMFormField text3DDMFormField = createTextDDMFormField(
			"text3", false, false);

		DDMFormField text31DDMFormField = createTextDDMFormField(
			"text31", false, false);

		text31DDMFormField.addNestedDDMFormField(
			createTextDDMFormField("text311", false, false));
		text31DDMFormField.addNestedDDMFormField(
			createTextDDMFormField("text312", false, false));
		text31DDMFormField.addNestedDDMFormField(
			createTextDDMFormField("text313", false, false));

		text3DDMFormField.addNestedDDMFormField(text31DDMFormField);

		DDMFormField text32DDMFormField = createTextDDMFormField(
			"text32", false, false);

		text32DDMFormField.addNestedDDMFormField(
			createTextDDMFormField("text321", false, false));
		text32DDMFormField.addNestedDDMFormField(
			createTextDDMFormField("text322", false, false));

		text3DDMFormField.addNestedDDMFormField(text32DDMFormField);

		ddmForm.addDDMFormField(text3DDMFormField);

		return ddmForm;
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		String name, Value value) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(StringUtil.randomString());
		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	protected DDMFormFieldValue createDDMFormFieldValueLocalizedInstance(
		String fieldName) {

		return createDDMFormFieldValue(
			fieldName,
			createLocalizedValue(
				"En " + fieldName, "Pt " + fieldName, LocaleUtil.US));
	}

	protected DDMFormFieldValue createDDMFormFieldValueUnlocalizedInstance(
		String fieldName) {

		return createDDMFormFieldValue(
			fieldName, new UnlocalizedValue(fieldName));
	}

	protected DDMFormValues createDDMFormValues() {
		DDMFormValues ddmFormValues = new DDMFormValues(createDDMForm());

		DDMFormFieldValue text1DDMFormFieldValue =
			createDDMFormFieldValueLocalizedInstance("text1");

		text1DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValueLocalizedInstance("text11"));
		text1DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValueLocalizedInstance("text11"));
		text1DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValueLocalizedInstance("text11"));
		text1DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValueUnlocalizedInstance("text12"));

		ddmFormValues.addDDMFormFieldValue(text1DDMFormFieldValue);

		DDMFormFieldValue text2DDMFormFieldValue =
			createDDMFormFieldValueUnlocalizedInstance("text2");

		text2DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValueUnlocalizedInstance("text21"));
		text2DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValueLocalizedInstance("text22"));
		text2DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValueLocalizedInstance("text22"));
		text2DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValueLocalizedInstance("text22"));

		ddmFormValues.addDDMFormFieldValue(text2DDMFormFieldValue);

		DDMFormFieldValue text3DDMFormFieldValue =
			createDDMFormFieldValueUnlocalizedInstance("text3");

		DDMFormFieldValue text31DDMFormFieldValue =
			createDDMFormFieldValueUnlocalizedInstance("text31");

		text31DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValueUnlocalizedInstance("text311"));
		text31DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValueUnlocalizedInstance("text312"));
		text31DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValueUnlocalizedInstance("text313"));

		text3DDMFormFieldValue.addNestedDDMFormFieldValue(
			text31DDMFormFieldValue);

		DDMFormFieldValue text32DDMFormFieldValue =
			createDDMFormFieldValueUnlocalizedInstance("text32");

		text32DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValueUnlocalizedInstance("text321"));
		text32DDMFormFieldValue.addNestedDDMFormFieldValue(
			createDDMFormFieldValueUnlocalizedInstance("text322"));

		text3DDMFormFieldValue.addNestedDDMFormFieldValue(
			text32DDMFormFieldValue);

		ddmFormValues.addDDMFormFieldValue(text3DDMFormFieldValue);

		return ddmFormValues;
	}

	protected DDMFormValuesQuery createDDMFormValuesQuery(String query)
		throws Exception {

		DDMFormValuesQuery ddmFormValuesQuery =
			_ddmFormValuesQueryFactory.create(_ddmFormValues, query);

		return ddmFormValuesQuery;
	}

	protected Value createLocalizedValue(
		String enValue, String ptValue, Locale defaultLocale) {

		Value value = new LocalizedValue(defaultLocale);

		value.addString(LocaleUtil.BRAZIL, ptValue);
		value.addString(LocaleUtil.US, enValue);

		return value;
	}

	protected DDMFormField createTextDDMFormField(
		String name, boolean localizable, boolean repeatable) {

		DDMFormField ddmFormField = new DDMFormField(name, "text");

		ddmFormField.setDataType("string");
		ddmFormField.setLocalizable(localizable);
		ddmFormField.setRepeatable(repeatable);

		LocalizedValue localizedValue = ddmFormField.getLabel();

		localizedValue.addString(LocaleUtil.US, name);

		return ddmFormField;
	}

	protected String getDDMFormFieldValueFieldName(
		DDMFormFieldValue ddmFormFieldValue) {

		return ddmFormFieldValue.getName();
	}

	protected void setUpLocaleUtil() {
		mockStatic(LocaleUtil.class);

		when(
			LocaleUtil.fromLanguageId("en_US")
		).thenReturn(
			LocaleUtil.US
		);

		when(
			LocaleUtil.fromLanguageId("pt_BR")
		).thenReturn(
			LocaleUtil.BRAZIL
		);
	}

	private DDMFormValues _ddmFormValues;
	private DDMFormValuesQueryFactory _ddmFormValuesQueryFactory;

}