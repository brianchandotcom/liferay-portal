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

package com.liferay.petra.json.validator;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.InputStream;

import org.hamcrest.core.StringStartsWith;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Rubén Pulido
 */
public class JSONValidatorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testValidateExampleInvalidExtraProperties() throws Exception {
		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("extraneous key [extra] is not permitted"));

		JSONValidator jsonValidator = new JSONValidator(
			_readJSONSchemaAsStream());

		jsonValidator.validate(_read("example_invalid_extra_properties.json"));
	}

	@Test
	public void testValidateExampleInvalidRequiredPropertyMissing()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("required key [example] not found"));

		JSONValidator jsonValidator = new JSONValidator(
			_readJSONSchemaAsStream());

		jsonValidator.validate(
			_read("example_invalid_required_property_missing.json"));
	}

	@Test
	public void testValidateExampleValidRequired() throws Exception {
		JSONValidator jsonValidator = new JSONValidator(
			_readJSONSchemaAsStream());

		jsonValidator.validate(_read("example_valid_required.json"));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private InputStream _readJSONSchemaAsStream() {
		return JSONValidatorTest.class.getResourceAsStream(
			"dependencies/example_json_schema.json");
	}

}