/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.importer.validator;

import com.liferay.portal.json.validator.JSONValidatorException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringStartsWith;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Rubén Pulido
 */
public class PageDefinitionValidatorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testValidatePageDefinitionInvalidColumnExtraProperties()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/pageElement/pageElements/0/pageElements/0/definition: " +
					"extraneous key [extra] is not permitted"));

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_invalid_column_extra_properties.json"));
	}

	@Test
	public void testValidatePageDefinitionInvalidDropZoneAllowedFragmentsUnallowedFragments()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringContains(
				"/pageElement/pageElements/0/definition/fragmentSettings: " +
					"extraneous key [allowedFragments] is not permitted"));
		expectedException.expectMessage(
			new StringContains(
				"/pageElement/pageElements/0/definition/fragmentSettings: " +
					"extraneous key [unallowedFragments] is not permitted"));

		PageDefinitionValidator.validatePageDefinition(
			_read(
				"page_definition_invalid_drop_zone_allowed_fragments_" +
					"unallowed_fragments.json"));
	}

	@Test
	public void testValidatePageDefinitionInvalidExtraProperties()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith("extraneous key [extra] is not permitted"));

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_invalid_extra_properties.json"));
	}

	@Test
	public void testValidatePageDefinitionInvalidFragmentExtraProperties()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/pageElement/pageElements/0/definition: extraneous key " +
					"[extra] is not permitted"));

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_invalid_fragment_extra_properties.json"));
	}

	@Test
	public void testValidatePageDefinitionInvalidFragmentMissingKey()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/pageElement/pageElements/0/definition/fragment: required " +
					"key [key] not found"));

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_invalid_fragment_missing_key.json"));
	}

	@Test
	public void testValidatePageDefinitionInvalidPageElementExtraProperties()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
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

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/pageElement/definition: extraneous key [extra] is not " +
					"permitted"));

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_invalid_root_extra_properties.json"));
	}

	@Test
	public void testValidatePageDefinitionInvalidRowExtraProperties()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/pageElement/pageElements/0/pageElements/0/definition: " +
					"extraneous key [extra] is not permitted"));

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_invalid_row_extra_properties.json"));
	}

	@Test
	public void testValidatePageDefinitionInvalidSectionBackgroundExtraProperties()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/pageElement/pageElements/0/definition/backgroundImage: " +
					"extraneous key [extra] is not permitted"));

		PageDefinitionValidator.validatePageDefinition(
			_read(
				"page_definition_invalid_section_background_image_extra_" +
					"properties.json"));
	}

	@Test
	public void testValidatePageDefinitionInvalidSectionExtraProperties()
		throws Exception {

		expectedException.expect(JSONValidatorException.class);
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
	public void testValidatePageDefinitionValidDropZoneAllowedFragmentsComplete()
		throws Exception {

		PageDefinitionValidator.validatePageDefinition(
			_read(
				"page_definition_valid_drop_zone_allowed_fragments_complete." +
					"json"));
	}

	@Test
	public void testValidatePageDefinitionValidDropZoneUnallowedFragmentsComplete()
		throws Exception {

		PageDefinitionValidator.validatePageDefinition(
			_read(
				"page_definition_valid_drop_zone_unallowed_fragments_" +
					"complete.json"));
	}

	@Test
	public void testValidatePageDefinitionValidFragmentComplete()
		throws Exception {

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_valid_fragment_complete.json"));
	}

	@Test
	public void testValidatePageDefinitionValidFragmentFieldBackgroundImageComplete()
		throws Exception {

		PageDefinitionValidator.validatePageDefinition(
			_read(
				"page_definition_valid_fragment_field_background_image_" +
					"complete.json"));
	}

	@Test
	public void testValidatePageDefinitionValidFragmentFieldHTMLComplete()
		throws Exception {

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_valid_fragment_field_html_complete.json"));
	}

	@Test
	public void testValidatePageDefinitionValidFragmentFieldImageComplete()
		throws Exception {

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_valid_fragment_field_image_complete.json"));
	}

	@Test
	public void testValidatePageDefinitionValidFragmentFieldTextComplete()
		throws Exception {

		PageDefinitionValidator.validatePageDefinition(
			_read("page_definition_valid_fragment_field_text_complete.json"));
	}

	@Test
	public void testValidatePageDefinitionValidFragmentFieldTextFragmentLinkHrefFragmentMappedValueMappingItemReferenceClassFieldReference()
		throws Exception {

		PageDefinitionValidator.validatePageDefinition(
			_read(
				"page_definition_valid_fragment_field_text_fragment_link_href" +
					"_fragment_mapped_value_mapping_item_reference_class_" +
						"field_reference.json"));
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