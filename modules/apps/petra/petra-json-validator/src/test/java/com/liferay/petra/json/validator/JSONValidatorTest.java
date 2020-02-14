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
import com.liferay.portal.util.FileImpl;

import java.io.InputStream;

import org.hamcrest.core.StringStartsWith;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Rubén Pulido
 */
public class JSONValidatorTest {

	@Before
	public void setUp() {
		new FileUtil().setFile(new FileImpl());
	}

	@Test
	public void testValidateExampleInvalidExtraProperties() throws Exception {
		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("extraneous key [extra] is not permitted"));

		JSONValidator.validate(
			_read("example_invalid_extra_properties.json"),
			_readJSONSchemaAsStream());
	}

	@Test
	public void testValidateExampleInvalidRequiredPropertyMissing()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("required key [example] not found"));

		JSONValidator.validate(
			_read("example_invalid_required_property_missing.json"),
			_readJSONSchemaAsStream());
	}

	@Test
	public void testValidateExampleValidRequired() throws Exception {
		JSONValidator.validate(
			_read("example_valid_required.json"), _readJSONSchemaAsStream());
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static InputStream _readJSONSchemaAsStream() {
		return JSONValidatorTest.class.getResourceAsStream(
			"dependencies/example_json_schema.json");
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

}