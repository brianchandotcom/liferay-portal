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

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@RunWith(PowerMockRunner.class)
public class SetOptionsFunctionTest extends BaseDDMFormRuleFunctionTestCase {

	@Before
	public void setUp() throws Exception {
		setUpLanguageUtil();
	}

	@Test
	public void testEvaluate() {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		jsonArray.put(createJSONObject("value a", "label a"));
		jsonArray.put(createJSONObject("value b", "label b"));

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(LanguageUtil.getLanguageId(LocaleUtil.US), jsonArray);

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				"Field_1", StringUtil.randomString());

		Map<String, List<DDMFormFieldEvaluationResult>>
			ddmFormFieldEvaluationResultsMap =
				createDDMFormFieldEvaluationResultsMap(
					ddmFormFieldEvaluationResult);

		SetOptionsFunction setOptionsFunction = new SetOptionsFunction(
			ddmFormFieldEvaluationResultsMap, LocaleUtil.US, _jsonFactory);

		setOptionsFunction.evaluate("Field_1", jsonObject.toJSONString());

		List<KeyValuePair> keyValuePairs =
			ddmFormFieldEvaluationResult.getProperty("options");

		Assert.assertEquals(keyValuePairs.toString(), 2, keyValuePairs.size());

		KeyValuePair keyValuePair1 = keyValuePairs.get(0);

		Assert.assertEquals("label a", keyValuePair1.getKey());
		Assert.assertEquals("value a", keyValuePair1.getValue());

		KeyValuePair keyValuePair2 = keyValuePairs.get(1);

		Assert.assertEquals("label b", keyValuePair2.getKey());
		Assert.assertEquals("value b", keyValuePair2.getValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgument() throws Exception {
		SetOptionsFunction setOptionsFunction = new SetOptionsFunction(
			null, LocaleUtil.US, _jsonFactory);

		setOptionsFunction.evaluate("param1");
	}

	protected JSONObject createJSONObject(String label, String value) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("label", label);
		jsonObject.put("value", value);

		return jsonObject;
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		_language = Mockito.mock(Language.class);

		Mockito.when(
			_language.getLanguageId(LocaleUtil.US)
		).thenReturn(
			"en_US"
		);

		languageUtil.setLanguage(_language);
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private Language _language;

}