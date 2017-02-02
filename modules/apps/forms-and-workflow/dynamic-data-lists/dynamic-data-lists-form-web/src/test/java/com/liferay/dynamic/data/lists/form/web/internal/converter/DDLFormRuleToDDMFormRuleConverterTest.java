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

package com.liferay.dynamic.data.lists.form.web.internal.converter;

import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDLFormRuleToDDMFormRuleConverterTest
	extends BaseDDLDDMConverterTest {

	@Before
	public void setUp() throws Exception {
		setUpDDLFormRuleDeserializer();

		_ddlFormRulesToDDMFormRulesConverter =
			new DDLFormRuleToDDMFormRuleConverter();
	}

	@Test
	public void testAndOrCondition() throws Exception {
		assertConversion(
			"ddl-form-rules-and-or-condition.json",
			"ddm-form-rules-and-or-condition.json");
	}

	@Test
	public void testAutoFillActions() throws Exception {
		JSONArray expectedDDMFormRulesJSONArray = jsonFactory.createJSONArray(
			read("ddm-form-rules-auto-fill-actions.json"));

		List<DDMFormRule> actualDDMFormRules = convert(
			"ddl-form-rules-auto-fill-actions.json");

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
			extractCallFunctionParameters(expectedCallFunction);

		List<String> actualCallFunctionParameters =
			extractCallFunctionParameters(actualCallFunction);

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

		assertCallFunctionParametersExpression(
			expectedInputParametersExpression, actualInputParametersExpression);

		String expectedOutputParametersExpression =
			expectedCallFunctionParameters.get(2);

		String actualOutputParametersExpression =
			actualCallFunctionParameters.get(2);

		assertCallFunctionParametersExpression(
			expectedOutputParametersExpression,
			actualOutputParametersExpression);
	}

	@Test
	public void testBelongsToCondition() throws Exception {
		assertConversion(
			"ddl-form-rules-belongs-to-condition.json",
			"ddm-form-rules-belongs-to-condition.json");
	}

	@Test
	public void testBooleanActions() throws Exception {
		assertConversion(
			"ddl-form-rules-boolean-actions.json",
			"ddm-form-rules-boolean-actions.json");
	}

	@Test
	public void testComparisonOperatorsCondition() throws Exception {
		assertConversion(
			"ddl-form-rules-comparison-operators-condition.json",
			"ddm-form-rules-comparison-operators-condition.json");
	}

	@Test
	public void testJumpToPageActions() throws Exception {
		assertConversion(
			"ddl-form-rules-jump-to-page-actions.json",
			"ddm-form-rules-jump-to-page-actions.json");
	}

	protected void assertCallFunctionParametersExpression(
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

		for (Entry<String, String> expectedParameterExpression :
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

	protected void assertConversion(String fromFileName, String toFileName)
		throws Exception {

		List<DDMFormRule> actualDDMFormRules = convert(fromFileName);

		JSONAssert.assertEquals(
			read(toFileName), serialize(actualDDMFormRules), false);
	}

	protected List<DDMFormRule> convert(String fileName) throws Exception {
		String serializedDDLFormRules = read(fileName);

		return _ddlFormRulesToDDMFormRulesConverter.convert(
			_ddlFormRulesDeserializer.deserialize(serializedDDLFormRules));
	}

	protected List<String> extractCallFunctionParameters(String callFunction) {
		Matcher matcher = _callFunctionPattern.matcher(callFunction);

		matcher.find();

		List<String> callFunctionParameters = new ArrayList<>(3);

		callFunctionParameters.add(matcher.group(1));
		callFunctionParameters.add(matcher.group(2));
		callFunctionParameters.add(matcher.group(3));

		return callFunctionParameters;
	}

	protected void setUpDDLFormRuleDeserializer()
		throws Exception, IllegalAccessException {

		Field field = ReflectionUtil.getDeclaredField(
			_ddlFormRulesDeserializer.getClass(), "_jsonFactory");

		field.set(_ddlFormRulesDeserializer, new JSONFactoryImpl());
	}

	private final Pattern _callFunctionPattern = Pattern.compile(
		"call\\(\\s*\'([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-" +
			"[0-9a-f]{12})\'\\s*,\\s*\'(.*)\'\\s*,\\s*\'(.*)\'\\s*\\)");
	private final DDLFormRuleDeserializer _ddlFormRulesDeserializer =
		new DDLFormRuleDeserializer();
	private DDLFormRuleToDDMFormRuleConverter
		_ddlFormRulesToDDMFormRulesConverter;

}