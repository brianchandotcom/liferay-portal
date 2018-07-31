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
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderOutputParametersSettings;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorContext;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMDataProviderTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Leonardo Barros
 */
@RunWith(Arquillian.class)
public class DDMFormFieldTypeSettingsEvaluatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Test
	public void testSelectCallGetDataProviderInstanceOutputParameters()
		throws Exception {

		List<DDMDataProviderOutputParametersSettings> outputParametersSettings =
			ListUtil.fromArray(
				new DDMDataProviderOutputParametersSettings[] {
					new DDMDataProviderOutputParametersSettings() {

						@Override
						public String outputParameterName() {
							return "Countries";
						}

						@Override
						public String outputParameterPath() {
							return "nameCurrentValue";
						}

						@Override
						public String outputParameterType() {
							return "[\"list\"]";
						}

					}
				});

		DDMFormFieldEvaluationResult
			ddmDataProviderInstanceOutputFieldEvaluationResult =
				evaluateCallFunctionExpression(outputParametersSettings);

		JSONArray jsonArray =
			ddmDataProviderInstanceOutputFieldEvaluationResult.getValue();

		Assert.assertEquals(1, jsonArray.length());

		Assert.assertEquals("Countries", jsonArray.getString(0));
	}

	@Test
	public void testSelectCallGetDataProviderInstanceOutputParameters2()
		throws Exception {

		List<DDMDataProviderOutputParametersSettings> outputParametersSettings =
			ListUtil.fromArray(
				new DDMDataProviderOutputParametersSettings[] {
					new DDMDataProviderOutputParametersSettings() {

						@Override
						public String outputParameterName() {
							return "Countries";
						}

						@Override
						public String outputParameterPath() {
							return "nameCurrentValue";
						}

						@Override
						public String outputParameterType() {
							return "[\"list\"]";
						}

					},
					new DDMDataProviderOutputParametersSettings() {

						@Override
						public String outputParameterName() {
							return "Countries2";
						}

						@Override
						public String outputParameterPath() {
							return "nameCurrentValue";
						}

						@Override
						public String outputParameterType() {
							return "[\"list\"]";
						}

					}
				});

		DDMFormFieldEvaluationResult
			ddmDataProviderInstanceOutputFieldEvaluationResult =
				evaluateCallFunctionExpression(outputParametersSettings);

		List<KeyValuePair> options =
			ddmDataProviderInstanceOutputFieldEvaluationResult.getProperty(
				"options");

		Assert.assertEquals(options.toString(), 2, options.size());

		KeyValuePair keyValuePair = options.get(0);

		Assert.assertEquals("Countries", keyValuePair.getKey());

		keyValuePair = options.get(1);

		Assert.assertEquals("Countries2", keyValuePair.getKey());
	}

	@Test
	public void testSelectDataSourceTypeDataProvider() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			registry.getService(DDMFormFieldTypeServicesTracker.class);

		DDMFormFieldType ddmFormFieldType =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType("select");

		DDMForm ddmForm = DDMFormFactory.create(
			ddmFormFieldType.getDDMFormFieldTypeSettings());

		DDMFormValues ddmFormValues =
			DDMFormValuesTestUtil.createDDMFormValuesWithDefaultFieldValues(
				ddmForm, SetUtil.fromArray(new Locale[] {LocaleUtil.US}),
				LocaleUtil.US);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValueMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValueMap.get(
			"dataSourceType");

		DDMFormFieldValue dataSourceTypeFormFieldValue = ddmFormFieldValues.get(
			0);

		dataSourceTypeFormFieldValue.setValue(
			new UnlocalizedValue("data-provider"));

		DDMFormEvaluator ddmFormEvaluator = registry.getService(
			DDMFormEvaluator.class);

		DDMFormEvaluatorContext ddmFormEvaluatorContext =
			new DDMFormEvaluatorContext(ddmForm, ddmFormValues, LocaleUtil.US);

		ddmFormEvaluatorContext.addProperty("groupId", 1L);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluator.evaluate(ddmFormEvaluatorContext);

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultsMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		ddmFormFieldValues = ddmFormFieldValueMap.get(
			"ddmDataProviderInstanceId");

		DDMFormFieldValue ddmDataProviderInstanceIdFormFieldValue =
			ddmFormFieldValues.get(0);

		DDMFormFieldEvaluationResult
			ddmDataProviderInstanceIdFieldEvaluationResult =
				ddmFormFieldEvaluationResultsMap.get(
					String.format(
						"ddmDataProviderInstanceId_INSTANCE_%s",
						ddmDataProviderInstanceIdFormFieldValue.
							getInstanceId()));

		Assert.assertTrue(
			ddmDataProviderInstanceIdFieldEvaluationResult.isVisible());
		Assert.assertTrue(
			ddmDataProviderInstanceIdFieldEvaluationResult.isRequired());

		ddmFormFieldValues = ddmFormFieldValueMap.get(
			"ddmDataProviderInstanceOutput");

		DDMFormFieldValue ddmDataProviderInstanceOutputFormFieldValue =
			ddmFormFieldValues.get(0);

		DDMFormFieldEvaluationResult
			ddmDataProviderInstanceOutputFieldEvaluationResult =
				ddmFormFieldEvaluationResultsMap.get(
					String.format(
						"ddmDataProviderInstanceOutput_INSTANCE_%s",
						ddmDataProviderInstanceOutputFormFieldValue.
							getInstanceId()));

		Assert.assertTrue(
			ddmDataProviderInstanceOutputFieldEvaluationResult.isVisible());
		Assert.assertTrue(
			ddmDataProviderInstanceOutputFieldEvaluationResult.isRequired());

		ddmFormFieldValues = ddmFormFieldValueMap.get("options");

		DDMFormFieldValue optionsDDMFormFieldValue = ddmFormFieldValues.get(0);

		DDMFormFieldEvaluationResult optionsFieldEvaluationResult =
			ddmFormFieldEvaluationResultsMap.get(
				String.format(
					"options_INSTANCE_%s",
					optionsDDMFormFieldValue.getInstanceId()));

		Assert.assertFalse(optionsFieldEvaluationResult.isVisible());
		Assert.assertFalse(optionsFieldEvaluationResult.isRequired());
	}

	@Test
	public void testSelectDataSourceTypeManual() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			registry.getService(DDMFormFieldTypeServicesTracker.class);

		DDMFormFieldType ddmFormFieldType =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType("select");

		DDMForm ddmForm = DDMFormFactory.create(
			ddmFormFieldType.getDDMFormFieldTypeSettings());

		DDMFormValues ddmFormValues =
			DDMFormValuesTestUtil.createDDMFormValuesWithDefaultFieldValues(
				ddmForm, SetUtil.fromArray(new Locale[] {LocaleUtil.US}),
				LocaleUtil.US);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValueMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValueMap.get(
			"dataSourceType");

		DDMFormFieldValue dataSourceTypeFormFieldValue = ddmFormFieldValues.get(
			0);

		dataSourceTypeFormFieldValue.setValue(new UnlocalizedValue("manual"));

		DDMFormEvaluator ddmFormEvaluator = registry.getService(
			DDMFormEvaluator.class);

		DDMFormEvaluatorContext ddmFormEvaluatorContext =
			new DDMFormEvaluatorContext(ddmForm, ddmFormValues, LocaleUtil.US);

		ddmFormEvaluatorContext.addProperty("groupId", 1L);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluator.evaluate(ddmFormEvaluatorContext);

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultsMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		ddmFormFieldValues = ddmFormFieldValueMap.get(
			"ddmDataProviderInstanceId");

		DDMFormFieldValue ddmDataProviderInstanceIdFormFieldValue =
			ddmFormFieldValues.get(0);

		DDMFormFieldEvaluationResult
			ddmDataProviderInstanceIdFieldEvaluationResult =
				ddmFormFieldEvaluationResultsMap.get(
					String.format(
						"ddmDataProviderInstanceId_INSTANCE_%s",
						ddmDataProviderInstanceIdFormFieldValue.
							getInstanceId()));

		Assert.assertFalse(
			ddmDataProviderInstanceIdFieldEvaluationResult.isVisible());
		Assert.assertFalse(
			ddmDataProviderInstanceIdFieldEvaluationResult.isRequired());

		ddmFormFieldValues = ddmFormFieldValueMap.get(
			"ddmDataProviderInstanceOutput");

		DDMFormFieldValue ddmDataProviderInstanceOutputFormFieldValue =
			ddmFormFieldValues.get(0);

		DDMFormFieldEvaluationResult
			ddmDataProviderInstanceOutputFieldEvaluationResult =
				ddmFormFieldEvaluationResultsMap.get(
					String.format(
						"ddmDataProviderInstanceOutput_INSTANCE_%s",
						ddmDataProviderInstanceOutputFormFieldValue.
							getInstanceId()));

		Assert.assertFalse(
			ddmDataProviderInstanceOutputFieldEvaluationResult.isVisible());
		Assert.assertFalse(
			ddmDataProviderInstanceOutputFieldEvaluationResult.isRequired());

		ddmFormFieldValues = ddmFormFieldValueMap.get("options");

		DDMFormFieldValue optionsDDMFormFieldValue = ddmFormFieldValues.get(0);

		DDMFormFieldEvaluationResult optionsFieldEvaluationResult =
			ddmFormFieldEvaluationResultsMap.get(
				String.format(
					"options_INSTANCE_%s",
					optionsDDMFormFieldValue.getInstanceId()));

		Assert.assertTrue(optionsFieldEvaluationResult.isVisible());
		Assert.assertTrue(optionsFieldEvaluationResult.isRequired());
	}

	protected DDMFormFieldEvaluationResult evaluateCallFunctionExpression(
			List<DDMDataProviderOutputParametersSettings>
				outputParametersSettings)
		throws Exception {

		DDMDataProviderInstance ddmDataProviderInstance =
			DDMDataProviderTestUtil.createDDMRestDataProviderInstance(
				GroupTestUtil.addGroup(), null, outputParametersSettings);

		Registry registry = RegistryUtil.getRegistry();

		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker =
			registry.getService(DDMFormFieldTypeServicesTracker.class);

		DDMFormFieldType ddmFormFieldType =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType("select");

		DDMForm ddmForm = DDMFormFactory.create(
			ddmFormFieldType.getDDMFormFieldTypeSettings());

		DDMFormValues ddmFormValues =
			DDMFormValuesTestUtil.createDDMFormValuesWithDefaultFieldValues(
				ddmForm, SetUtil.fromArray(new Locale[] {LocaleUtil.US}),
				LocaleUtil.US);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValueMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValueMap.get(
			"dataSourceType");

		DDMFormFieldValue dataSourceTypeFormFieldValue = ddmFormFieldValues.get(
			0);

		dataSourceTypeFormFieldValue.setValue(
			new UnlocalizedValue("data-provider"));

		ddmFormFieldValues = ddmFormFieldValueMap.get(
			"ddmDataProviderInstanceId");

		DDMFormFieldValue ddmDataProviderInstanceIdFormFieldValue =
			ddmFormFieldValues.get(0);

		ddmDataProviderInstanceIdFormFieldValue.setValue(
			new UnlocalizedValue(
				String.format(
					"['%d']", ddmDataProviderInstance.getPrimaryKey())));

		DDMFormEvaluator ddmFormEvaluator = registry.getService(
			DDMFormEvaluator.class);

		DDMFormEvaluatorContext ddmFormEvaluatorContext =
			new DDMFormEvaluatorContext(ddmForm, ddmFormValues, LocaleUtil.US);

		ddmFormEvaluatorContext.addProperty("groupId", 1L);

		MockHttpServletRequest request = new MockHttpServletRequest();

		request.setAttribute(WebKeys.COMPANY_ID, 1L);

		ddmFormEvaluatorContext.addProperty("request", request);

		DDMFormEvaluationResult ddmFormEvaluationResult =
			ddmFormEvaluator.evaluate(ddmFormEvaluatorContext);

		ddmFormFieldValues = ddmFormFieldValueMap.get(
			"ddmDataProviderInstanceOutput");

		DDMFormFieldValue ddmDataProviderInstanceOutputFormFieldValue =
			ddmFormFieldValues.get(0);

		Map<String, DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultsMap =
				ddmFormEvaluationResult.getDDMFormFieldEvaluationResultsMap();

		DDMFormFieldEvaluationResult
			ddmDataProviderInstanceOutputFieldEvaluationResult =
				ddmFormFieldEvaluationResultsMap.get(
					String.format(
						"ddmDataProviderInstanceOutput_INSTANCE_%s",
						ddmDataProviderInstanceOutputFormFieldValue.
							getInstanceId()));

		return ddmDataProviderInstanceOutputFieldEvaluationResult;
	}

}