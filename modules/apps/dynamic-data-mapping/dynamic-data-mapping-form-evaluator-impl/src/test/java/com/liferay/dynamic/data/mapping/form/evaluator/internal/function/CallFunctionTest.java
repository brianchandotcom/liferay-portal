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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.expression.DDMFormEvaluatorExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.expression.DDMFormEvaluatorExpressionObserver;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.helper.DDMFormEvaluatorFormValuesHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Leonardo Barros
 */
@RunWith(MockitoJUnitRunner.class)
public class CallFunctionTest extends PowerMockito {

	@Before
	public void setUp() {
		_callFunction = new CallFunction(null, _jsonFactory);
	}

	@Test
	public void testGetFieldValueFromJSONArray() {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		jsonArray.put("test");

		mockDDMExpressionFieldAccessor(jsonArray);

		Assert.assertEquals(
			"test", _callFunction.getDDMFormFieldValue("field0"));
	}

	@Test
	public void testGetFieldValueFromString() {
		mockDDMExpressionFieldAccessor("test");

		Assert.assertEquals(
			"test", _callFunction.getDDMFormFieldValue("field0"));
	}

	@Test
	public void testNotAutoSelectOption() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("field0");

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"1", "field0", new UnlocalizedValue("a")));

		MockDDMExpressionObserver mockDDMExpressionObserver =
			mockDDMExpressionObserver(ddmFormValues);

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		keyValuePairs.add(new KeyValuePair("key_1", "value_1"));
		keyValuePairs.add(new KeyValuePair("key_2", "value_2"));

		_callFunction.setDDMFormFieldOptions("field0", keyValuePairs);

		Assert.assertNull(
			mockDDMExpressionObserver.getFieldPropertyValue(
				"field0", "1", "value"));
	}

	@Test
	public void testSetDDMFormFieldOptionsRepeatableFields() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("field0");

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"1", "field0", new UnlocalizedValue("a")));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"2", "field0", new UnlocalizedValue("b")));

		MockDDMExpressionObserver mockDDMExpressionObserver =
			mockDDMExpressionObserver(ddmFormValues);

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		keyValuePairs.add(new KeyValuePair("key_1", "value_1"));
		keyValuePairs.add(new KeyValuePair("key_2", "value_2"));

		_callFunction.setDDMFormFieldOptions("field0", keyValuePairs);

		Assert.assertEquals(
			keyValuePairs,
			mockDDMExpressionObserver.getFieldPropertyValue(
				"field0", "1", "options"));
		Assert.assertEquals(
			keyValuePairs,
			mockDDMExpressionObserver.getFieldPropertyValue(
				"field0", "2", "options"));
	}

	public static class MockDDMExpressionObserver
		extends DDMFormEvaluatorExpressionObserver {

		public MockDDMExpressionObserver(DDMFormValues ddmFormValues) {
			super(
				new DDMFormEvaluatorFormValuesHelper(ddmFormValues),
				_createFieldsPropertiesMap());
		}

		public Object getFieldPropertyValue(
			String fieldName, String instanceId, String property) {

			Map<String, Object> fieldProperties =
				_fieldsPropertiesMap.getOrDefault(
					new DDMFormEvaluatorFieldContextKey(fieldName, instanceId),
					Collections.emptyMap());

			return fieldProperties.get(property);
		}

		private static Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			_createFieldsPropertiesMap() {

			_fieldsPropertiesMap = new HashMap<>();

			return _fieldsPropertiesMap;
		}

		private static Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			_fieldsPropertiesMap;

	}

	protected void mockDDMExpressionFieldAccessor(Object... values) {
		DDMFormEvaluatorExpressionFieldAccessor
			ddmFormEvaluatorExpressionFieldAccessor = mock(
				DDMFormEvaluatorExpressionFieldAccessor.class);

		when(
			ddmFormEvaluatorExpressionFieldAccessor.getFieldProperty(
				Matchers.any(GetFieldPropertyRequest.class))
		).then(
			new Answer<GetFieldPropertyResponse>() {

				@Override
				public GetFieldPropertyResponse answer(
						InvocationOnMock invocation)
					throws Throwable {

					GetFieldPropertyResponse.Builder builder =
						GetFieldPropertyResponse.Builder.newBuilder(values);

					return builder.build();
				}

			}
		);

		_callFunction.setDDMExpressionFieldAccessor(
			ddmFormEvaluatorExpressionFieldAccessor);
	}

	protected MockDDMExpressionObserver mockDDMExpressionObserver(
		DDMFormValues ddmFormValues) {

		MockDDMExpressionObserver mockDDMExpressionObserver =
			new MockDDMExpressionObserver(ddmFormValues);

		_callFunction.setDDMExpressionObserver(mockDDMExpressionObserver);

		return mockDDMExpressionObserver;
	}

	private CallFunction _callFunction;
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}