/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.page.change.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.form.page.change.DDMFormPageChange;
import com.liferay.dynamic.data.mapping.form.page.change.DDMFormPageChangeRegistry;
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
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

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
		setUpDDMTestFormPageChange();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_serviceRegistration.unregister();
	}

	@Test
	public void testDDMFormPageChangeEvaluate() throws Exception {
		DDMFormPageChange ddmFormPageChange =
			_ddmFormPageChangeRegistry.getDDMFormPageChangeByDDMFormInstanceId(
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
			_ddmFormPageChangeRegistry.getDDMFormPageChangeByDDMFormInstanceId(
				_DDM_FORM_INSTANCE_ID);

		Assert.assertNotNull(ddmFormPageChange);
	}

	protected static void setUpDDMTestFormPageChange() {
		Bundle bundle = FrameworkUtil.getBundle(DDMFormPageChangeTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			DDMFormPageChange.class, new DDMTestFormPageChange(),
			MapUtil.singletonDictionary(
				"ddm.form.instance.id", _DDM_FORM_INSTANCE_ID));
	}

	private DDMFormField _createDDMFormField(
		String name, String type, String dataType) {

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(dataType);

		return ddmFormField;
	}

	private static final String _DDM_FORM_INSTANCE_ID =
		RandomTestUtil.randomString();

	@Inject
	private static DDMFormPageChangeRegistry _ddmFormPageChangeRegistry;

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