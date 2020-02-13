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

package com.liferay.layout.page.template.admin.web.internal.validator;

import com.liferay.layout.page.template.admin.web.internal.exception.PageDefinitionValidatorException;
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
public class PageDefinitionValidatorTest {

	@Before
	public void setUp() {
		new FileUtil().setFile(new FileImpl());
	}

	@Test
	public void testValidatePageDefinitionInvalidColumnExtraProperties()
		throws Exception {

		expectedException.expect(PageDefinitionValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/pageElement/pageElements/0/pageElements/0/definition: " +
					"extraneous key [extra] is not permitted"));

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_invalid_column_extra_properties.json"));
	}

	@Test
	public void testValidatePageDefinitionInvalidExtraProperties()
		throws Exception {

		expectedException.expect(PageDefinitionValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("extraneous key [extra] is not permitted"));

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_invalid_extra_properties.json"));
	}

	@Test
	public void testValidatePageDefinitionInvalidPageElementExtraProperties()
		throws Exception {

		expectedException.expect(PageDefinitionValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/pageElement: extraneous key [extra] is not permitted"));

		PageDefinitionValidator.validatePageDefinition(
			_read(
				"page_definition_invalid_page_element_extra_properties.json"));
	}

	@Test
	public void testValidatePageDefinitionInvalidRootExtraProperties()
		throws Exception {

		expectedException.expect(PageDefinitionValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/pageElement/definition: extraneous key [extra] is not " +
					"permitted"));

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_invalid_root_extra_properties.json"));
	}

	@Test
	public void testValidatePageDefinitionInvalidSectionExtraProperties()
		throws Exception {

		expectedException.expect(PageDefinitionValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/pageElement/pageElements/0/definition: extraneous key " +
					"[extra] is not permitted"));

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_invalid_section_extra_properties.json"));
	}

	@Test
	public void testValidatePageDefinitionValidColumnComplete()
		throws Exception {

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_valid_column_complete.json"));
	}

	@Test
	public void testValidatePageDefinitionValidRequired() throws Exception {
		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_valid_required.json"));
	}

	@Test
	public void testValidatePageDefinitionValidRootComplete() throws Exception {
		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_valid_root_complete.json"));
	}

	@Test
	public void testValidatePageDefinitionValidRowComplete() throws Exception {
		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_valid_row_complete.json"));
	}

	@Test
	public void testValidatePageDefinitionValidSectionComplete()
		throws Exception {

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_valid_section_complete.json"));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

}