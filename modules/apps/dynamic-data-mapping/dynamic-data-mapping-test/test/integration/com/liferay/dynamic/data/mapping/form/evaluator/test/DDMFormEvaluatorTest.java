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

package com.liferay.dynamic.data.mapping.form.evaluator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.test.BaseDDMServiceTestCase;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Pablo Carvalho
 */
@RunWith(Arquillian.class)
public class DDMFormEvaluatorTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testValidFields() throws Exception {
		String serializedDDMForm = read(
			"ddm-form-evaluator-form-valid-fields-test-data.json");

		DDMForm ddmForm = DDMFormJSONDeserializerUtil.deserialize(
			serializedDDMForm);

		String serializedDDMFormValues = read(
			"ddm-form-evaluator-form-values-valid-fields-test-data.json");

		DDMFormValues ddmFormValues =
			DDMFormValuesJSONDeserializerUtil.deserialize(
				ddmForm, serializedDDMFormValues);

		Registry registry = RegistryUtil.getRegistry();

		DDMFormEvaluator ddmFormEvaluator = registry.getService(
			DDMFormEvaluator.class);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluator.evaluate(ddmForm, ddmFormValues, LocaleUtil.US);

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		String expectedResult = read(
			"ddm-form-evaluator-result-valid-fields-data.json");

		String actualResult = jsonSerializer.serializeDeep(
			ddmFormEvaluationResult);

		JSONAssert.assertEquals(expectedResult, actualResult, false);
	}

	@Test
	public void testVisibleFields1() throws Exception {
		String serializedDDMForm = read(
			"ddm-form-evaluator-form-visible-fields-test-data.json");

		DDMForm ddmForm = DDMFormJSONDeserializerUtil.deserialize(
			serializedDDMForm);

		String serializedDDMFormValues = read(
			"ddm-form-evaluator-form-values-visible-fields-test-data-1.json");

		DDMFormValues ddmFormValues =
			DDMFormValuesJSONDeserializerUtil.deserialize(
				ddmForm, serializedDDMFormValues);

		Registry registry = RegistryUtil.getRegistry();

		DDMFormEvaluator ddmFormEvaluator = registry.getService(
			DDMFormEvaluator.class);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluator.evaluate(ddmForm, ddmFormValues, LocaleUtil.US);

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		DDMFormFieldEvaluationResult checkboxDDMFormFieldEvaluationResult =
			ddmFormFieldEvaluationResultMap.get("Confirmation");

		Assert.assertFalse(checkboxDDMFormFieldEvaluationResult.isVisible());
	}

	@Test
	public void testVisibleFields2() throws Exception {
		String serializedDDMForm = read(
			"ddm-form-evaluator-form-visible-fields-test-data.json");

		DDMForm ddmForm = DDMFormJSONDeserializerUtil.deserialize(
			serializedDDMForm);

		String serializedDDMFormValues = read(
			"ddm-form-evaluator-form-values-visible-fields-test-data-2.json");

		DDMFormValues ddmFormValues =
			DDMFormValuesJSONDeserializerUtil.deserialize(
				ddmForm, serializedDDMFormValues);

		Registry registry = RegistryUtil.getRegistry();

		DDMFormEvaluator ddmFormEvaluator = registry.getService(
			DDMFormEvaluator.class);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluator.evaluate(ddmForm, ddmFormValues, LocaleUtil.US);

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		DDMFormFieldEvaluationResult checkboxDDMFormFieldEvaluationResult =
			ddmFormFieldEvaluationResultMap.get("Confirmation");

		Assert.assertTrue(checkboxDDMFormFieldEvaluationResult.isVisible());
	}

}