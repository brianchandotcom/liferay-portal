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

package com.liferay.dynamic.data.mapping.form.page.change.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.form.page.change.DDMFormPageChange;
import com.liferay.dynamic.data.mapping.form.page.change.DDMFormPageChangeTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bruno Oliveira
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class DDMFormPageChangeTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		setUpDDMFormPageChangeTracker();
		setUpDDMTestFormPageChange();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_serviceRegistration.unregister();
	}

	@Test
	public void testDDMFormPageChangeEvaluate() throws Exception {
		DDMFormPageChange ddmFormPageChange =
			_ddmFormPageChangeTracker.getDDMFormPageChangeByDDMFormInstanceId(
				_DDM_FORM_INSTANCE_ID);

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			_createDDMFormField("field0", "text", "string"));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field0_instanceId", "field0", new UnlocalizedValue("")));

		DDMFormEvaluatorEvaluateRequest.Builder builder =
			DDMFormEvaluatorEvaluateRequest.Builder.newBuilder(
				ddmForm, ddmFormValues, LocaleUtil.US);

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			ddmFormPageChange.evaluate(builder.build());

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Map<String, Object> ddmFormFieldPropertyChanges =
			ddmFormFieldsPropertyChanges.get(
				new DDMFormEvaluatorFieldContextKey(
					"field0", "field0_instanceId"));

		Assert.assertEquals(
			"New Value", ddmFormFieldPropertyChanges.get("value"));
	}

	@Test
	public void testGetDDMFormPageChangeByDDMFormInstanceId() {
		DDMFormPageChange ddmFormPageChange =
			_ddmFormPageChangeTracker.getDDMFormPageChangeByDDMFormInstanceId(
				_DDM_FORM_INSTANCE_ID);

		Assert.assertNotNull(ddmFormPageChange);
	}

	protected static void setUpDDMFormPageChangeTracker() {
		Registry registry = RegistryUtil.getRegistry();

		_ddmFormPageChangeTracker = registry.getService(
			registry.getServiceReference(DDMFormPageChangeTracker.class));
	}

	protected static void setUpDDMTestFormPageChange() {
		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"ddm.form.instance.id", _DDM_FORM_INSTANCE_ID
		).build();

		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			DDMFormPageChange.class, new DDMTestFormPageChange(), properties);
	}

	private DDMFormField _createDDMFormField(
		String name, String type, String dataType) {

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(dataType);

		return ddmFormField;
	}

	private static final String _DDM_FORM_INSTANCE_ID =
		RandomTestUtil.randomString();

	private static DDMFormPageChangeTracker _ddmFormPageChangeTracker;
	private static ServiceRegistration<DDMFormPageChange> _serviceRegistration;

	private static class DDMTestFormPageChange implements DDMFormPageChange {

		@Override
		public DDMFormEvaluatorEvaluateResponse evaluate(
			DDMFormEvaluatorEvaluateRequest ddmFormEvaluatorEvaluateRequest) {

			DDMFormEvaluatorEvaluateResponse.Builder
				ddmFormEvaluatorEvaluateResponse =
					DDMFormEvaluatorEvaluateResponse.Builder.newBuilder(
						_getDDMFormFieldsPropertyChanges(
							ddmFormEvaluatorEvaluateRequest));

			return ddmFormEvaluatorEvaluateResponse.build();
		}

		private Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			_getDDMFormFieldsPropertyChanges(
				DDMFormEvaluatorEvaluateRequest
					ddmFormEvaluatorEvaluateRequest) {

			return HashMapBuilder.
				<DDMFormEvaluatorFieldContextKey, Map<String, Object>>put(
					() -> {
						DDMFormValues ddmFormValues =
							ddmFormEvaluatorEvaluateRequest.getDDMFormValues();

						Map<String, List<DDMFormFieldValue>>
							ddmFormFieldValuesReferencesMap =
								ddmFormValues.
									getDDMFormFieldValuesReferencesMap(true);

						String fieldReference = "field0";

						List<DDMFormFieldValue> ddmFormFieldValues =
							ddmFormFieldValuesReferencesMap.get(fieldReference);

						DDMFormFieldValue ddmFormFieldValue =
							ddmFormFieldValues.get(0);

						return new DDMFormEvaluatorFieldContextKey(
							fieldReference, ddmFormFieldValue.getInstanceId());
					},
					HashMapBuilder.<String, Object>put(
						"value", "New Value"
					).build()
				).build();
		}

	}

}