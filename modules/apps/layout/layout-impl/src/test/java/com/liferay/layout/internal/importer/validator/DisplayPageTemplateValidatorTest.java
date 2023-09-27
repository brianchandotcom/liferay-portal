/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.importer.validator;

import com.liferay.portal.json.validator.JSONValidatorException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.hamcrest.core.StringStartsWith;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Rubén Pulido
 */
public class DisplayPageTemplateValidatorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testValidatePageTemplateInvalidExtraProperties()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("extraneous key [extra] is not permitted"));

		DisplayPageTemplateValidator.validateDisplayPageTemplate(
			_read("display_page_template_invalid_extra_properties.json"));
	}

	@Test
	public void testValidatePageTemplateInvalidMissingContentType()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("required key [contentType] not found"));

		DisplayPageTemplateValidator.validateDisplayPageTemplate(
			_read("display_page_template_invalid_missing_content_type.json"));
	}

	@Test
	public void testValidatePageTemplateInvalidMissingContentTypeClassName()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/contentType: required key [className] not found"));

		DisplayPageTemplateValidator.validateDisplayPageTemplate(
			_read(
				"display_page_template_invalid_missing_content_type_class_" +
					"name.json"));
	}

	@Test
	public void testValidatePageTemplateInvalidMissingName() throws Exception {
		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("required key [name] not found"));

		DisplayPageTemplateValidator.validateDisplayPageTemplate(
			_read("display_page_template_invalid_missing_name.json"));
	}

	@Test
	public void testValidatePageTemplateValidComplete() throws Exception {
		DisplayPageTemplateValidator.validateDisplayPageTemplate(
			_read("display_page_template_valid_complete.json"));
	}

	@Test
	public void testValidatePageTemplateValidRequired() throws Exception {
		DisplayPageTemplateValidator.validateDisplayPageTemplate(
			_read("display_page_template_valid_required.json"));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

}