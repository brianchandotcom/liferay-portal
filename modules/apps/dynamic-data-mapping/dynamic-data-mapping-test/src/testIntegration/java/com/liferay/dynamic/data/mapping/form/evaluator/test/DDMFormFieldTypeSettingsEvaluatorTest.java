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
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
			PermissionCheckerMethodTestRule.INSTANCE);

	@Ignore
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

		Map<String, Object> ddmDataProviderInstanceOutputFielPropertyChanges =
			evaluateCallFunctionExpression(outputParametersSettings);

		JSONArray jsonArray =
			(JSONArray)ddmDataProviderInstanceOutputFielPropertyChanges.get(
				"value");

		Assert.assertEquals(1, jsonArray.length());

		Assert.assertEquals("Countries", jsonArray.getString(0));
	}

	@Test
	public void testSelectDataSourceTypeManual() throws Exception {
		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType("select");

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

		DDMFormEvaluatorEvaluateRequest.Builder builder =
			DDMFormEvaluatorEvaluateRequest.Builder.newBuilder(
				ddmForm, ddmFormValues, LocaleUtil.US);

		builder.withGroupId(1L);

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			_ddmFormEvaluator.evaluate(builder.build());

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		ddmFormFieldValues = ddmFormFieldValueMap.get(
			"ddmDataProviderInstanceId");

		DDMFormFieldValue ddmDataProviderInstanceIdFormFieldValue =
			ddmFormFieldValues.get(0);

		Map<String, Object> ddmDataProviderInstanceIdFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"ddmDataProviderInstanceId",
					ddmDataProviderInstanceIdFormFieldValue.getInstanceId()));

		Assert.assertFalse(
			(Boolean)ddmDataProviderInstanceIdFieldPropertyChanges.get(
				"visible"));
		Assert.assertFalse(
			(Boolean)ddmDataProviderInstanceIdFieldPropertyChanges.get(
				"required"));

		ddmFormFieldValues = ddmFormFieldValueMap.get(
			"ddmDataProviderInstanceOutput");

		DDMFormFieldValue ddmDataProviderInstanceOutputFormFieldValue =
			ddmFormFieldValues.get(0);

		Map<String, Object> ddmDataProviderInstanceOutputFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"ddmDataProviderInstanceOutput",
					ddmDataProviderInstanceOutputFormFieldValue.
						getInstanceId()));

		Assert.assertFalse(
			(Boolean)ddmDataProviderInstanceOutputFieldPropertyChanges.get(
				"visible"));
		Assert.assertFalse(
			(Boolean)ddmDataProviderInstanceOutputFieldPropertyChanges.get(
				"required"));

		ddmFormFieldValues = ddmFormFieldValueMap.get("options");

		DDMFormFieldValue optionsDDMFormFieldValue = ddmFormFieldValues.get(0);

		Map<String, Object> optionsFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"options", optionsDDMFormFieldValue.getInstanceId()));

		Assert.assertTrue((Boolean)optionsFieldPropertyChanges.get("visible"));
		Assert.assertTrue((Boolean)optionsFieldPropertyChanges.get("required"));
	}

	protected Map<String, Object> evaluateCallFunctionExpression(
			List<DDMDataProviderOutputParametersSettings>
				outputParametersSettings)
		throws Exception {

		DDMDataProviderInstance ddmDataProviderInstance =
			DDMDataProviderTestUtil.createDDMRestDataProviderInstance(
				GroupTestUtil.addGroup(), null, outputParametersSettings);

		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType("select");

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

		DDMFormEvaluatorEvaluateRequest.Builder builder =
			DDMFormEvaluatorEvaluateRequest.Builder.newBuilder(
				ddmForm, ddmFormValues, LocaleUtil.US);

		builder.withGroupId(
			1L
		).withCompanyId(
			1L
		);

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			_ddmFormEvaluator.evaluate(builder.build());

		ddmFormFieldValues = ddmFormFieldValueMap.get(
			"ddmDataProviderInstanceOutput");

		DDMFormFieldValue ddmDataProviderInstanceOutputFormFieldValue =
			ddmFormFieldValues.get(0);

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey =
			new DDMFormEvaluatorFieldContextKey(
				ddmDataProviderInstanceOutputFormFieldValue.getName(),
				ddmDataProviderInstanceOutputFormFieldValue.getInstanceId());

		return ddmFormFieldsPropertyChanges.get(ddmFormFieldContextKey);
	}

	@Inject(type = DDMFormEvaluator.class)
	private DDMFormEvaluator _ddmFormEvaluator;

	@Inject(type = DDMFormFieldTypeServicesTracker.class)
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

}