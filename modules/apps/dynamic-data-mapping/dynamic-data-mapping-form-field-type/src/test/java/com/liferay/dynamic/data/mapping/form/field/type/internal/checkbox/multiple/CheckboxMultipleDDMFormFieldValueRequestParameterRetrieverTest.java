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

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.multiple;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Marcela Cunha
 */
public class CheckboxMultipleDDMFormFieldValueRequestParameterRetrieverTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_checkboxMultipleDDMFormFieldValueRequestParameterRetriever =
			new CheckboxMultipleDDMFormFieldValueRequestParameterRetriever();

		_checkboxMultipleDDMFormFieldValueRequestParameterRetriever.
			jsonFactory = _jsonFactory;
	}

	@Test
	public void testCompletedSubmission() {
		String expectedResult = _createJSONArrayString("Option 2");

		String defaultDDMFormFieldParameterValue = _createJSONArrayString(
			"Option 1");

		String actualResult =
			_checkboxMultipleDDMFormFieldValueRequestParameterRetriever.get(
				_createHttpServletRequest("Option 2"),
				_CHECKBOX_MULTIPLE_SUBMISSION,
				defaultDDMFormFieldParameterValue);

		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testEmptySubmission() {
		String expectedResult = "[]";

		String defaultDDMFormFieldParameterValue = _createJSONArrayString(
			"Option 1");

		String actualResult =
			_checkboxMultipleDDMFormFieldValueRequestParameterRetriever.get(
				_createHttpServletRequest(), _CHECKBOX_MULTIPLE_SUBMISSION,
				defaultDDMFormFieldParameterValue);

		Assert.assertEquals(expectedResult, actualResult);
	}

	private MockHttpServletRequest _createHttpServletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLifecycleAction(true);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private HttpServletRequest _createHttpServletRequest(String... strings) {
		MockHttpServletRequest mockHttpServletRequest =
			_createHttpServletRequest();

		mockHttpServletRequest.addParameter(
			_CHECKBOX_MULTIPLE_SUBMISSION, strings);

		return mockHttpServletRequest;
	}

	private String _createJSONArrayString(String... strings) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (String string : strings) {
			jsonArray.put(string);
		}

		return jsonArray.toString();
	}

	private static final String _CHECKBOX_MULTIPLE_SUBMISSION =
		"checkBoxSubmissionResult";

	private CheckboxMultipleDDMFormFieldValueRequestParameterRetriever
		_checkboxMultipleDDMFormFieldValueRequestParameterRetriever;
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}