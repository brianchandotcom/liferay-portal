/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.validator;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URL;

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

		JSONValidator jsonValidator = new JSONValidator(_readJSONSchema());

		jsonValidator.validate(_read("example_invalid_extra_properties.json"));
	}

	@Test
	public void testValidateExampleInvalidRequiredPropertyMissing()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("required key [example] not found"));

		JSONValidator jsonValidator = new JSONValidator(_readJSONSchema());

		jsonValidator.validate(
			_read("example_invalid_required_property_missing.json"));
	}

	@Test
	public void testValidateExampleValidRequired() throws Exception {
		JSONValidator jsonValidator = new JSONValidator(_readJSONSchema());

		jsonValidator.validate(_read("example_valid_required.json"));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private URL _readJSONSchema() {
		return JSONValidatorTest.class.getResource(
			"dependencies/example_json_schema.json");
	}

}