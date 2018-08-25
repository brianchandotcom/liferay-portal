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
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorContext;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.test.BaseDDMServiceTestCase;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

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
	public void testSumValuesForRepeatableField() throws Exception {
		String serializedDDMForm = read(
			"ddm-form-evaluator-sum-values-repeatable-field.json");

		DDMForm ddmForm = deserialize(serializedDDMForm);

		String serializedDDMFormValues = read(
			"ddm-form-evaluator-sum-values-repeatable-field-test-data.json");

		DDMFormValues ddmFormValues = deserialize(
			serializedDDMFormValues, ddmForm);

		Registry registry = RegistryUtil.getRegistry();

		DDMFormEvaluator ddmFormEvaluator = registry.getService(
			DDMFormEvaluator.class);

		DDMFormEvaluatorContext ddmFormEvaluatorContext =
			new DDMFormEvaluatorContext(ddmForm, ddmFormValues, LocaleUtil.US);

		ddmFormEvaluatorContext.addProperty("groupId", 1L);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluator.evaluate(ddmFormEvaluatorContext);

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		String actualResult = jsonSerializer.serializeDeep(
			ddmFormEvaluationResult);

		String expectedResult = read(
			"ddm-form-evaluator-result-sum-values-repeatable-field.json");

		JSONAssert.assertEquals(expectedResult, actualResult, false);
	}

	@Test
	public void testValidFields() throws Exception {
		String serializedDDMForm = read(
			"ddm-form-evaluator-form-valid-fields-test-data.json");

		DDMForm ddmForm = deserialize(serializedDDMForm);

		String serializedDDMFormValues = read(
			"ddm-form-evaluator-form-values-valid-fields-test-data.json");

		DDMFormValues ddmFormValues = deserialize(
			serializedDDMFormValues, ddmForm);

		Registry registry = RegistryUtil.getRegistry();

		DDMFormEvaluator ddmFormEvaluator = registry.getService(
			DDMFormEvaluator.class);

		DDMFormEvaluatorContext ddmFormEvaluatorContext =
			new DDMFormEvaluatorContext(ddmForm, ddmFormValues, LocaleUtil.US);

		ddmFormEvaluatorContext.addProperty("groupId", 1L);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluator.evaluate(ddmFormEvaluatorContext);

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
			"ddm-form-evaluator-form-visible-fields-test-data-1.json");

		DDMForm ddmForm = deserialize(serializedDDMForm);

		String serializedDDMFormValues = read(
			"ddm-form-evaluator-form-values-visible-fields-test-data-1.json");

		DDMFormValues ddmFormValues = deserialize(
			serializedDDMFormValues, ddmForm);

		Registry registry = RegistryUtil.getRegistry();

		DDMFormEvaluator ddmFormEvaluator = registry.getService(
			DDMFormEvaluator.class);

		DDMFormEvaluatorContext ddmFormEvaluatorContext =
			new DDMFormEvaluatorContext(ddmForm, ddmFormValues, LocaleUtil.US);

		ddmFormEvaluatorContext.addProperty("groupId", 1L);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluator.evaluate(ddmFormEvaluatorContext);

		DDMFormFieldEvaluationResult checkboxDDMFormFieldEvaluationResult =
			ddmFormEvaluationResult.getDDMFormFieldEvaluationResult(
				"Confirmation", "hany");

		Assert.assertFalse(checkboxDDMFormFieldEvaluationResult.isVisible());
	}

	@Test
	public void testVisibleFields2() throws Exception {
		String serializedDDMForm = read(
			"ddm-form-evaluator-form-visible-fields-test-data-2.json");

		DDMForm ddmForm = deserialize(serializedDDMForm);

		String serializedDDMFormValues = read(
			"ddm-form-evaluator-form-values-visible-fields-test-data-2.json");

		DDMFormValues ddmFormValues = deserialize(
			serializedDDMFormValues, ddmForm);

		Registry registry = RegistryUtil.getRegistry();

		DDMFormEvaluator ddmFormEvaluator = registry.getService(
			DDMFormEvaluator.class);

		DDMFormEvaluatorContext ddmFormEvaluatorContext =
			new DDMFormEvaluatorContext(ddmForm, ddmFormValues, LocaleUtil.US);

		ddmFormEvaluatorContext.addProperty("groupId", 1L);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluator.evaluate(ddmFormEvaluatorContext);

		DDMFormFieldEvaluationResult checkboxDDMFormFieldEvaluationResult =
			ddmFormEvaluationResult.getDDMFormFieldEvaluationResult(
				"Phone", "hany");

		Assert.assertTrue(checkboxDDMFormFieldEvaluationResult.isVisible());
	}

	@Test
	public void testVisibleFields3() throws Exception {
		String serializedDDMForm = read(
			"ddm-form-evaluator-form-visible-fields-test-data-3.json");

		DDMForm ddmForm = deserialize(serializedDDMForm);

		String serializedDDMFormValues = read(
			"ddm-form-evaluator-form-values-visible-fields-test-data-3.json");

		DDMFormValues ddmFormValues = deserialize(
			serializedDDMFormValues, ddmForm);

		Registry registry = RegistryUtil.getRegistry();

		DDMFormEvaluator ddmFormEvaluator = registry.getService(
			DDMFormEvaluator.class);

		DDMFormEvaluatorContext ddmFormEvaluatorContext =
			new DDMFormEvaluatorContext(ddmForm, ddmFormValues, LocaleUtil.US);

		ddmFormEvaluatorContext.addProperty("groupId", 1L);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluator.evaluate(ddmFormEvaluatorContext);

		DDMFormFieldEvaluationResult phoneDDMFormFieldEvaluationResult =
			ddmFormEvaluationResult.getDDMFormFieldEvaluationResult(
				"Phone", "hany");

		Assert.assertFalse(phoneDDMFormFieldEvaluationResult.isVisible());
	}

	@Test
	public void testVisibleFields4() throws Exception {
		String serializedDDMForm = read(
			"ddm-form-evaluator-form-visible-fields-test-data-4.json");

		DDMForm ddmForm = deserialize(serializedDDMForm);

		String serializedDDMFormValues = read(
			"ddm-form-evaluator-form-values-visible-fields-test-data-4.json");

		DDMFormValues ddmFormValues = deserialize(
			serializedDDMFormValues, ddmForm);

		Registry registry = RegistryUtil.getRegistry();

		DDMFormEvaluator ddmFormEvaluator = registry.getService(
			DDMFormEvaluator.class);

		DDMFormEvaluatorContext ddmFormEvaluatorContext =
			new DDMFormEvaluatorContext(ddmForm, ddmFormValues, LocaleUtil.US);

		ddmFormEvaluatorContext.addProperty("groupId", 1L);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluator.evaluate(ddmFormEvaluatorContext);

		DDMFormFieldEvaluationResult phoneDDMFormFieldEvaluationResult =
			ddmFormEvaluationResult.getDDMFormFieldEvaluationResult(
				"Phone", "hany");

		Assert.assertTrue(phoneDDMFormFieldEvaluationResult.isVisible());
	}

	protected DDMForm deserialize(String content) {
		DDMFormDeserializer ddmFormDeserializer =
			_ddmFormDeserializerTracker.getDDMFormDeserializer("json");

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	protected DDMFormValues deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializer ddmFormValuesDeserializer =
			_ddmFormValuesDeserializerTracker.getDDMFormValuesDeserializer(
				"json");

		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				ddmFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	@Inject
	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;

	@Inject
	private DDMFormValuesDeserializerTracker _ddmFormValuesDeserializerTracker;

}