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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONValidator;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Pablo Carvalho
 */
public class JSONValidatorTest {

	@Before
	public void setUp() throws Exception {
		setupJSONFactory();
		setupJSONValidator();
	}

	@Test
	public void testInvalidAdditionalProperty() throws Exception {
		String json = readJSON("invalid_additional_property.json");

		Assert.assertFalse(_jsonValidator.isValid(json));
	}

	@Test
	public void testInvalidDataType() throws Exception {
		String json = readJSON("invalid_data_type.json");

		Assert.assertFalse(_jsonValidator.isValid(json));
	}

	@Test
	public void testInvalidEmail() throws Exception {
		String json = readJSON("invalid_email.json");

		Assert.assertFalse(_jsonValidator.isValid(json));
	}

	@Test
	public void testMissingRequiredProperty() throws Exception {
		String json = readJSON("missing_required_property.json");

		Assert.assertFalse(_jsonValidator.isValid(json));
	}

	@Test
	public void testValidJSON() throws Exception {
		String json = readJSON("valid.json");

		Assert.assertTrue(_jsonValidator.isValid(json));
	}

	protected String readJSON(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected void setupJSONFactory() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	protected void setupJSONValidator() throws Exception {
		String jsonSchema = readJSON("schema.json");

		_jsonValidator = JSONFactoryUtil.createJSONValidator(jsonSchema);
	}

	private JSONValidator _jsonValidator;

}