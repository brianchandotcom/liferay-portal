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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.functions;

import com.liferay.dynamic.data.mapping.expression.UpdateFieldPropertyRequest;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Leonardo Barros
 */
@PrepareForTest(LanguageUtil.class)
@RunWith(MockitoJUnitRunner.class)
public class SetOptionsFunctionTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_setOptionsFunction = new SetOptionsFunction();

		_setOptionsFunction.jsonFactory = _jsonFactory;

		_setUpLanguageUtil();
	}

	@Test
	public void testApply() {
		when(
			_language.getLanguageId(new Locale("pt", "BR"))
		).thenReturn(
			"pt_BR"
		);

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		jsonArray.put(_createJSONObject("label1", "value1"));
		jsonArray.put(_createJSONObject("label2", "value2"));
		jsonArray.put(_createJSONObject("label3", "value3"));

		jsonObject.put("pt_BR", jsonArray);

		String json = jsonObject.toJSONString();

		DefaultDDMExpressionObserver defaultDDMExpressionObserver =
			new DefaultDDMExpressionObserver();

		DefaultDDMExpressionObserver spy = spy(defaultDDMExpressionObserver);

		_setOptionsFunction.setDDMExpressionObserver(spy);

		_setOptionsFunction.setDDMExpressionParameterAccessor(
			new DefaultDDMExpressionParameterAccessor());

		Boolean result = _setOptionsFunction.apply("optionList", json);

		ArgumentCaptor<UpdateFieldPropertyRequest> argumentCaptor =
			ArgumentCaptor.forClass(UpdateFieldPropertyRequest.class);

		Mockito.verify(
			spy, Mockito.times(1)
		).updateFieldProperty(
			argumentCaptor.capture()
		);

		UpdateFieldPropertyRequest updateFieldPropertyRequest =
			argumentCaptor.getValue();

		Assert.assertEquals(
			"optionList", updateFieldPropertyRequest.getField());

		Map<String, Object> properties =
			updateFieldPropertyRequest.getProperties();

		Assert.assertTrue(properties.containsKey("options"));

		List<KeyValuePair> keyValuePairs = new ArrayList() {
			{
				add(new KeyValuePair("value1", "label1"));
				add(new KeyValuePair("value2", "label2"));
				add(new KeyValuePair("value3", "label3"));
			}
		};

		Assert.assertEquals(keyValuePairs, properties.get("options"));

		Assert.assertTrue(result);
	}

	@Test
	public void testInvalidJSON() {
		when(
			_language.getLanguageId(new Locale("pt", "BR"))
		).thenReturn(
			"pt_BR"
		);

		DefaultDDMExpressionObserver defaultDDMExpressionObserver =
			new DefaultDDMExpressionObserver();

		DefaultDDMExpressionObserver spy = spy(defaultDDMExpressionObserver);

		_setOptionsFunction.setDDMExpressionObserver(spy);

		_setOptionsFunction.setDDMExpressionParameterAccessor(
			new DefaultDDMExpressionParameterAccessor());

		Boolean result = _setOptionsFunction.apply("optionList", "INVALID");

		ArgumentCaptor<UpdateFieldPropertyRequest> argumentCaptor =
			ArgumentCaptor.forClass(UpdateFieldPropertyRequest.class);

		Mockito.verify(
			spy, Mockito.times(1)
		).updateFieldProperty(
			argumentCaptor.capture()
		);

		UpdateFieldPropertyRequest updateFieldPropertyRequest =
			argumentCaptor.getValue();

		Assert.assertEquals(
			"optionList", updateFieldPropertyRequest.getField());

		Map<String, Object> properties =
			updateFieldPropertyRequest.getProperties();

		Assert.assertTrue(properties.containsKey("options"));

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		Assert.assertEquals(keyValuePairs, properties.get("options"));

		Assert.assertTrue(result);
	}

	@Test
	public void testNullObserver() {
		Assert.assertFalse(_setOptionsFunction.apply("field", "json"));
	}

	private JSONObject _createJSONObject(String label, String value) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("label", label);
		jsonObject.put("value", value);

		return jsonObject;
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	private static final JSONFactory _jsonFactory = new JSONFactoryImpl();

	@Mock
	private Language _language;

	private SetOptionsFunction _setOptionsFunction;

}