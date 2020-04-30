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

import com.liferay.layout.page.template.exception.PageTemplateCollectionValidatorException;
import com.liferay.layout.page.template.validator.PageTemplateCollectionValidator;
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
public class PageTemplateCollectionValidatorTest {

	@Before
	public void setUp() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());
	}

	@Test
	public void testValidatePageDefinitionInvalidExtraProperties()
		throws Exception {

		expectedException.expect(
			PageTemplateCollectionValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("extraneous key [extra] is not permitted"));

		PageTemplateCollectionValidator.validatePageTemplateCollection(
			_read("page_template_collection_invalid_extra_properties.json"));
	}

	@Test
	public void testValidatePageDefinitionInvalidMissingName()
		throws Exception {

		expectedException.expect(
			PageTemplateCollectionValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("required key [name] not found"));

		PageTemplateCollectionValidator.validatePageTemplateCollection(
			_read("page_template_collection_invalid_missing_name.json"));
	}

	@Test
	public void testValidatePageDefinitionValidComplete() throws Exception {
		PageTemplateCollectionValidator.validatePageTemplateCollection(
			_read("page_template_collection_valid_complete.json"));
	}

	@Test
	public void testValidatePageDefinitionValidRequired() throws Exception {
		PageTemplateCollectionValidator.validatePageTemplateCollection(
			_read("page_template_collection_valid_required.json"));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

}