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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter;

import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFunctionTrackerImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRule;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleSerializerContext;
import com.liferay.dynamic.data.mapping.storage.constants.FieldConstants;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest(ServiceContextThreadLocal.class)
@RunWith(PowerMockRunner.class)
public class DDMFormRuleToDDMFormRuleModelConverterTest
	extends BaseDDMConverterTestCase {

	@Before
	public void setUp() throws Exception {
		_setUpDDMExpressionFactory();
		_setUpDDMFormRuleConverter();
		_setUpDDMFormRuleDeserializer();
	}

	@Test
	public void testAndOrCondition1() throws Exception {
		_assertConversionToModel(
			"ddm-form-rules-and-or-condition.json",
			"ddm-form-rules-model-and-or-condition.json");
	}

	@Test
	public void testAndOrCondition2() throws Exception {
		_assertConversionToConvertModel(
			"ddm-form-rules-model-and-or-condition.json",
			"ddm-form-rules-and-or-condition.json");
	}

	@Test
	public void testAutoFillActions1() throws Exception {
		JSONArray expectedDDMFormRulesJSONArray = jsonFactory.createJSONArray(
			read("ddm-form-rules-model-auto-fill-actions.json"));

		List<DDMFormRule> actualDDMFormRules = convert(
			"ddm-form-rules-auto-fill-actions.json");

		JSONArray actualDDMFormRulesJSONArray = jsonFactory.createJSONArray(
			serialize(actualDDMFormRules));

		Assert.assertEquals(
			expectedDDMFormRulesJSONArray.length(),
			actualDDMFormRulesJSONArray.length());

		JSONObject expectedAutoFillDDMRuleJSONObject =
			expectedDDMFormRulesJSONArray.getJSONObject(0);

		JSONObject actualAutoFillDDMRuleJSONObject =
			actualDDMFormRulesJSONArray.getJSONObject(0);

		Assert.assertEquals(
			expectedAutoFillDDMRuleJSONObject.get("condition"),
			actualAutoFillDDMRuleJSONObject.get("condition"));

		JSONArray expectedActionDDMRuleJSONArray =
			expectedAutoFillDDMRuleJSONObject.getJSONArray("actions");

		JSONArray actualActionDDMRuleJSONArray =
			actualAutoFillDDMRuleJSONObject.getJSONArray("actions");

		Assert.assertEquals(
			expectedActionDDMRuleJSONArray.length(),
			actualActionDDMRuleJSONArray.length());

		String expectedCallFunction = expectedActionDDMRuleJSONArray.getString(
			0);
		String actualCallFunction = actualActionDDMRuleJSONArray.getString(0);

		List<String> expectedCallFunctionParameters =
			_extractCallFunctionParameters(expectedCallFunction);

		List<String> actualCallFunctionParameters =
			_extractCallFunctionParameters(actualCallFunction);

		String expectedDDMDataProviderInstanceUUID =
			expectedCallFunctionParameters.get(0);

		String actualDDMDataProviderInstanceUUID =
			actualCallFunctionParameters.get(0);

		Assert.assertEquals(
			expectedDDMDataProviderInstanceUUID,
			actualDDMDataProviderInstanceUUID);

		String expectedInputParametersExpression =
			expectedCallFunctionParameters.get(1);

		String actualInputParametersExpression =
			actualCallFunctionParameters.get(1);

		_assertCallFunctionParametersExpression(
			expectedInputParametersExpression, actualInputParametersExpression);

		String expectedOutputParametersExpression =
			expectedCallFunctionParameters.get(2);

		String actualOutputParametersExpression =
			actualCallFunctionParameters.get(2);

		_assertCallFunctionParametersExpression(
			expectedOutputParametersExpression,
			actualOutputParametersExpression);
	}

	@Test
	public void testAutoFillActions2() throws Exception {
		_assertConversionToConvertModel(
			"ddm-form-rules-model-auto-fill-actions.json",
			"ddm-form-rules-auto-fill-actions.json");
	}

	@Test
	public void testBelongsToCondition1() throws Exception {
		_assertConversionToModel(
			"ddm-form-rules-belongs-to-condition.json",
			"ddm-form-rules-model-belongs-to-condition.json");
	}

	@Test
	public void testBelongsToCondition2() throws Exception {
		_assertConversionToConvertModel(
			"ddm-form-rules-model-belongs-to-condition.json",
			"ddm-form-rules-belongs-to-condition-without-user-operand.json");
	}

	@Test
	public void testBooleanActions1() throws Exception {
		_assertConversionToModel(
			"ddm-form-rules-boolean-actions.json",
			"ddm-form-rules-model-boolean-actions.json");
	}

	@Test
	public void testBooleanActions2() throws Exception {
		_assertConversionToConvertModel(
			"ddm-form-rules-model-boolean-actions.json",
			"ddm-form-rules-boolean-actions.json");
	}

	@Test
	public void testCalculateAction1() throws Exception {
		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField0 = new DDMFormField(
			"field0", FieldConstants.INTEGER);
		DDMFormField ddmFormField1 = new DDMFormField(
			"field1", FieldConstants.INTEGER);
		DDMFormField ddmFormField2 = new DDMFormField(
			"field2", FieldConstants.INTEGER);

		ddmForm.setDDMFormFields(
			Arrays.asList(ddmFormField0, ddmFormField1, ddmFormField2));

		PowerMockito.when(
			_spiDDMFormRuleSerializerContext.getAttribute("form")
		).thenReturn(
			ddmForm
		);

		_assertConversionToModel(
			"ddm-form-rules-calculate-action.json",
			"ddm-form-rules-model-calculate-action.json");
	}

	@Test
	public void testCalculateAction2() throws Exception {
		_assertConversionToConvertModel(
			"ddm-form-rules-model-calculate-action.json",
			"ddm-form-rules-calculate-action.json");
	}

	@Test
	public void testComparisonOperatorsCondition1() throws Exception {
		_assertConversionToModel(
			"ddm-form-rules-comparison-operators-condition.json",
			"ddm-form-rules-model-comparison-operators-condition.json");
	}

	@Test
	public void testComparisonOperatorsCondition2() throws Exception {
		_assertConversionToConvertModel(
			"ddm-form-rules-model-comparison-operators-condition.json",
			"ddm-form-rules-comparison-operators-condition.json");
	}

	@Test
	public void testCustomCondition1() throws Exception {
		_assertConversionToModel(
			"ddm-form-rules-custom-condition.json",
			"ddm-form-rules-model-custom-condition.json");
	}

	@Test
	public void testCustomCondition2() throws Exception {
		_assertConversionToConvertModel(
			"ddm-form-rules-model-custom-condition.json",
			"ddm-form-rules-custom-condition.json");
	}

	@Test
	public void testIsEmptyCondition1() throws Exception {
		_assertConversionToModel(
			"ddm-form-rules-is-empty-condition.json",
			"ddm-form-rules-model-is-empty-condition.json");
	}

	@Test
	public void testIsEmptyCondition2() throws Exception {
		_assertConversionToConvertModel(
			"ddm-form-rules-model-is-empty-condition.json",
			"ddm-form-rules-is-empty-condition.json");
	}

	@Test
	public void testIsNotEmptyCondition1() throws Exception {
		_assertConversionToModel(
			"ddm-form-rules-is-not-empty-condition.json",
			"ddm-form-rules-model-is-not-empty-condition.json");
	}

	@Test
	public void testIsNotEmptyCondition2() throws Exception {
		_assertConversionToConvertModel(
			"ddm-form-rules-model-is-not-empty-condition.json",
			"ddm-form-rules-is-not-empty-condition.json");
	}

	@Test
	public void testJumpToPageActions1() throws Exception {
		_assertConversionToConvertModel(
			"ddm-form-rules-model-jump-to-page-actions.json",
			"ddm-form-rules-jump-to-page-actions.json");
	}

	@Test
	public void testJumpToPageActions2() throws Exception {
		_assertConversionToModel(
			"ddm-form-rules-jump-to-page-actions.json",
			"ddm-form-rules-model-jump-to-page-actions.json");
	}

	protected List<DDMFormRule> convert(String fileName) throws Exception {
		String serializedDDMFormRules = read(fileName);

		return _ddmFormRuleConverterImpl.convert(
			_ddmFormRuleDeserializerImpl.deserialize(serializedDDMFormRules),
			_spiDDMFormRuleSerializerContext);
	}

	private void _assertCallFunctionParametersExpression(
		String expectedParametersExpression,
		String actualParametersExpression) {

		Map<String, String> expectedParametersExpressionMap =
			MapUtil.toLinkedHashMap(
				StringUtil.split(
					expectedParametersExpression, CharPool.SEMICOLON),
				StringPool.EQUAL);

		Map<String, String> actualParametersExpressionMap =
			MapUtil.toLinkedHashMap(
				StringUtil.split(
					actualParametersExpression, CharPool.SEMICOLON),
				StringPool.EQUAL);

		Assert.assertEquals(
			actualParametersExpressionMap.toString(),
			expectedParametersExpressionMap.size(),
			actualParametersExpressionMap.size());

		for (Map.Entry<String, String> expectedParameterExpression :
				expectedParametersExpressionMap.entrySet()) {

			String expectedParameterName = expectedParameterExpression.getKey();

			String expectedParameterValue =
				expectedParameterExpression.getValue();

			Assert.assertTrue(
				actualParametersExpressionMap.containsKey(
					expectedParameterName));

			String actualParameterValue = actualParametersExpressionMap.get(
				expectedParameterName);

			Assert.assertEquals(expectedParameterValue, actualParameterValue);
		}
	}

	private void _assertConversionToConvertModel(
			String fromFileName, String toFileName)
		throws Exception {

		String serializedDDMFormRules = read(fromFileName);

		DDMFormRule[] ddmFormRules = deserialize(
			serializedDDMFormRules, DDMFormRule[].class);

		List<SPIDDMFormRule> spiDDMFormRules =
			_ddmFormRuleConverterImpl.convert(ListUtil.fromArray(ddmFormRules));

		JSONAssert.assertEquals(
			read(toFileName), serialize(spiDDMFormRules), false);
	}

	private void _assertConversionToModel(
			String fromFileName, String toFileName)
		throws Exception {

		List<DDMFormRule> ddmFormRules = convert(fromFileName);

		JSONAssert.assertEquals(
			read(toFileName), serialize(ddmFormRules), false);
	}

	private List<String> _extractCallFunctionParameters(String callFunction) {
		Matcher matcher = _callFunctionPattern.matcher(callFunction);

		matcher.find();

		return ListUtil.fromArray(
			matcher.group(1), matcher.group(2), matcher.group(3));
	}

	private void _setUpDDMExpressionFactory() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			_ddmExpressionFactoryImpl.getClass(),
			"ddmExpressionFunctionTracker");

		field.set(
			_ddmExpressionFactoryImpl, new DDMExpressionFunctionTrackerImpl());
	}

	private void _setUpDDMFormRuleConverter() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			_ddmFormRuleConverterImpl.getClass(), "ddmExpressionFactory");

		field.set(_ddmFormRuleConverterImpl, _ddmExpressionFactoryImpl);
	}

	private void _setUpDDMFormRuleDeserializer() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			_ddmFormRuleDeserializerImpl.getClass(), "_jsonFactory");

		field.set(_ddmFormRuleDeserializerImpl, new JSONFactoryImpl());

		field = ReflectionUtil.getDeclaredField(
			_ddmFormRuleDeserializerImpl.getClass(),
			"_spiDDMFormRuleConverter");

		field.set(_ddmFormRuleDeserializerImpl, _ddmFormRuleConverterImpl);
	}

	private static final Pattern _callFunctionPattern = Pattern.compile(
		"call\\(\\s*\'([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-" +
			"[0-9a-f]{12})\'\\s*,\\s*\'(.*)\'\\s*,\\s*\'(.*)\'\\s*\\)");

	private final DDMExpressionFactoryImpl _ddmExpressionFactoryImpl =
		new DDMExpressionFactoryImpl();
	private final DDMFormRuleConverterImpl _ddmFormRuleConverterImpl =
		new DDMFormRuleConverterImpl();
	private final DDMFormRuleDeserializerImpl _ddmFormRuleDeserializerImpl =
		new DDMFormRuleDeserializerImpl();

	@Mock
	private SPIDDMFormRuleSerializerContext _spiDDMFormRuleSerializerContext;

}