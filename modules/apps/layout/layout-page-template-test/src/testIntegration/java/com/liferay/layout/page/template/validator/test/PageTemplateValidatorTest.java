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

package com.liferay.layout.page.template.validator.test;

import com.liferay.layout.page.template.exception.PageTemplateValidatorException;
import com.liferay.layout.page.template.validator.PageTemplateValidator;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;

import org.hamcrest.core.StringStartsWith;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Rubén Pulido
 */
public class PageTemplateValidatorTest {

	@Before
	public void setUp() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());
	}

	@Test
	public void testValidatePageTemplateInvalidExtraProperties()
		throws Exception {

		expectedException.expect(PageTemplateValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("extraneous key [extra] is not permitted"));

		PageTemplateValidator.validatePageTemplate(
			_read("page_template_invalid_extra_properties.json"));
	}

	@Test
	public void testValidatePageTemplateInvalidMissingName() throws Exception {
		expectedException.expect(PageTemplateValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("required key [name] not found"));

		PageTemplateValidator.validatePageTemplate(
			_read("page_template_invalid_missing_name.json"));
	}

	@Test
	public void testValidatePageTemplateValidRequired() throws Exception {
		PageTemplateValidator.validatePageTemplate(
			_read("page_template_valid_required.json"));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

}