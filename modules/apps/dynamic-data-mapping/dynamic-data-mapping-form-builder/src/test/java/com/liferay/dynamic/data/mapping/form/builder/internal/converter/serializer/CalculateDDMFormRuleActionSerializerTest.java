/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer;

import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.action.CalculateDDMFormRuleAction;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleSerializerContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class CalculateDDMFormRuleActionSerializerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBuildExpression1() {
		CalculateDDMFormRuleActionSerializer
			calculateDDMFormRuleActionSerializer =
				new CalculateDDMFormRuleActionSerializer(null);

		Set<String> ddmFormFields = new HashSet<>();

		ddmFormFields.add("Num1");
		ddmFormFields.add("Num12");
		ddmFormFields.add("Num123");

		String expression = "(Num123+Num1+Num12) * 0.25 * Num123";

		String result = calculateDDMFormRuleActionSerializer.buildExpression(
			expression, ddmFormFields);

		Assert.assertEquals(
			"(getValue('Num123')+getValue('Num1')+getValue('Num12')) * 0.25 " +
				"* getValue('Num123')",
			result);
	}

	@Test
	public void testBuildExpression2() {
		CalculateDDMFormRuleActionSerializer
			calculateDDMFormRuleActionSerializer =
				new CalculateDDMFormRuleActionSerializer(null);

		Set<String> ddmFormFields = new HashSet<>();

		ddmFormFields.add("Num1");
		ddmFormFields.add("Num2");
		ddmFormFields.add("Num3");
		ddmFormFields.add("Num4");

		String expression = "(Num1+Num2+Num3+Num4) * 0.25";

		String result = calculateDDMFormRuleActionSerializer.buildExpression(
			expression, ddmFormFields);

		Assert.assertEquals(
			"(getValue('Num1')+getValue('Num2')+getValue('Num3')+" +
				"getValue('Num4')) * 0.25",
			result);
	}

	@Test
	public void testBuildExpression3() {
		CalculateDDMFormRuleActionSerializer
			calculateDDMFormRuleActionSerializer =
				new CalculateDDMFormRuleActionSerializer(null);

		Set<String> ddmFormFields = new HashSet<>();

		ddmFormFields.add("Num1");
		ddmFormFields.add("Num2");
		ddmFormFields.add("Num3");

		String expression = "Num1+Num2+Num3*3";

		String result = calculateDDMFormRuleActionSerializer.buildExpression(
			expression, ddmFormFields);

		Assert.assertEquals(
			"getValue('Num1')+getValue('Num2')+getValue('Num3')*3", result);
	}

	@Test
	public void testBuildExpression4() {
		CalculateDDMFormRuleActionSerializer
			calculateDDMFormRuleActionSerializer =
				new CalculateDDMFormRuleActionSerializer(null);

		Set<String> ddmFormFields = new HashSet<>();

		ddmFormFields.add("Num1");
		ddmFormFields.add("Num2");
		ddmFormFields.add("Num3");

		String expression = "(Num1-Num2*2)-Num3";

		String result = calculateDDMFormRuleActionSerializer.buildExpression(
			expression, ddmFormFields);

		Assert.assertEquals(
			"(getValue('Num1')-getValue('Num2')*2)-getValue('Num3')", result);
	}

	@Test
	public void testBuildExpression5() {
		CalculateDDMFormRuleActionSerializer
			calculateDDMFormRuleActionSerializer =
				new CalculateDDMFormRuleActionSerializer(null);

		Set<String> ddmFormFields = new HashSet<>();

		ddmFormFields.add("Num1");
		ddmFormFields.add("Num2");

		String expression = "(Num1 * Num2) + 0.25";

		String result = calculateDDMFormRuleActionSerializer.buildExpression(
			expression, ddmFormFields);

		Assert.assertEquals(
			"(getValue('Num1') * getValue('Num2')) + 0.25", result);
	}

	@Test
	public void testSerialize() {
		Mockito.when(
			_calculateDDMFormRuleAction.getExpression()
		).thenReturn(
			"(text + text1) * 2"
		);

		Mockito.when(
			_calculateDDMFormRuleAction.getTarget()
		).thenReturn(
			"text2"
		);

		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField1 = new DDMFormField("text", "string");
		DDMFormField ddmFormField2 = new DDMFormField("text1", "string");

		ddmForm.setDDMFormFields(Arrays.asList(ddmFormField1, ddmFormField2));

		Mockito.when(
			_serviceContext.getAttribute("form")
		).thenReturn(
			ddmForm
		);

		CalculateDDMFormRuleActionSerializer
			calculateDDMFormRuleActionSerializer =
				new CalculateDDMFormRuleActionSerializer(
					_calculateDDMFormRuleAction);

		Mockito.when(
			_spiDDMFormRuleSerializerContext.getAttribute("form")
		).thenReturn(
			ddmForm
		);

		String result = calculateDDMFormRuleActionSerializer.serialize(
			_spiDDMFormRuleSerializerContext);

		Assert.assertEquals(
			"calculate('text2', (getValue('text') + getValue('text1')) * 2)",
			result);
	}

	@Test
	public void testSerializeWithEmptyTarget() {
		Mockito.when(
			_calculateDDMFormRuleAction.getTarget()
		).thenReturn(
			StringPool.BLANK
		);

		CalculateDDMFormRuleActionSerializer
			calculateDDMFormRuleActionSerializer =
				new CalculateDDMFormRuleActionSerializer(
					_calculateDDMFormRuleAction);

		String result = calculateDDMFormRuleActionSerializer.serialize(
			_spiDDMFormRuleSerializerContext);

		Assert.assertNull(result);
	}

	private final CalculateDDMFormRuleAction _calculateDDMFormRuleAction =
		Mockito.mock(CalculateDDMFormRuleAction.class);
	private final ServiceContext _serviceContext = Mockito.mock(
		ServiceContext.class);
	private final SPIDDMFormRuleSerializerContext
		_spiDDMFormRuleSerializerContext = Mockito.mock(
			SPIDDMFormRuleSerializerContext.class);

}