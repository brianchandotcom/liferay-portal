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

package com.liferay.fragment.internal.validator;

import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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
public class FragmentEntryValidatorImplTest {

	@Before
	public void setUp() {
		new FileUtil().setFile(new FileImpl());

		_fragmentEntryValidatorImpl = new FragmentEntryValidatorImpl();

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testValidateConfigurationInvalidColorPaletteFieldDefaultValueCssClassMissing()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/defaultValue: required key [cssClass] " +
					"not found"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_colorpalette_defaultvalue_" +
					"cssclass_missing.json"));
	}

	@Test
	public void testValidateConfigurationInvalidColorPaletteFieldDefaultValueRgbValueMissing()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/defaultValue: required key [rgbValue] " +
					"not found"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_colorpalette_defaultvalue_" +
					"rgbvalue_missing.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldCheckboxDefaultValueUnsupported()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/defaultValue: unsupported is not a " +
					"valid enum value"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_checkbox_defaultvalue_" +
					"unsupported.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldCheckboxExtraProperties()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0: extraneous key [extra] is not " +
					"permitted"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_checkbox_extra_properties.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldColorPaletteDefaultValueExtraProperties()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/defaultValue: extraneous key [extra] " +
					"is not permitted"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_colorpalette_defaultvalue_extra_" +
					"properties.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldColorPaletteExtraProperties()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0: extraneous key [extra] is not " +
					"permitted"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_colorpalette_extra_properties." +
					"json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldItemSelectorDefaultValueClassNameMissing()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/defaultValue: required key " +
					"[className] not found"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_itemselector_defaultvalue_" +
					"classname_missing.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldItemSelectorDefaultValueClassPKMissing()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/defaultValue: required key [classPK] " +
					"not found"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_itemselector_defaultvalue_" +
					"classpk_missing.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldItemSelectorDefaultValueExtraProperties()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/defaultValue: extraneous key [extra] " +
					"is not permitted"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_itemselector_defaultvalue_extra_" +
					"properties.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldItemSelectorExtraProperties()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0: extraneous key [extra] is not " +
					"permitted"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_itemselector_extra_properties." +
					"json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldItemSelectorTypeOptionsExtraProperties()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/typeOptions: extraneous key [extra] " +
					"is not permitted"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_itemselector_typeoptions_extra_" +
					"properties.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldNameEmpty()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/name: expected minLength: 1, actual: " +
					"0"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_invalid_field_name_empty.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldNameMissing()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0: required key [name] not found"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_invalid_field_name_missing.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldNameNonalphanumeric()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/name: string [a_b-c.d?e] does not " +
					"match pattern"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_invalid_field_name_non_alphanumeric.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldNameWithSpace()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/name: string [a b] does not match " +
					"pattern"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_invalid_field_name_with_space.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldSelectDataTypeUnsupported()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/dataType: unsupported is not a valid " +
					"enum value"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_select_datatype_unsupported." +
					"json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldSelectDefaultValueMissing()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0: required key [defaultValue] not " +
					"found"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_select_defaultvalue_missing." +
					"json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldSelectExtraProperties()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0: extraneous key [extra] is not " +
					"permitted"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_invalid_field_select_extra_properties.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldSelectTypeOptionsExtraProperties()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/typeOptions: extraneous key [extra] " +
					"is not permitted"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_select_typeoptions_extra_" +
					"properties.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldSelectTypeOptionsMissing()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0: required key [typeOptions] not found"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_select_typeoptions_missing.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldSelectTypeOptionsValidValuesExtraProperties()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/typeOptions/validValues/0: extraneous " +
					"key [extra] is not permitted"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_select_typeoptions_validvalues_" +
					"extra_properties.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldSelectTypeOptionsValidValuesMissing()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/typeOptions: required key " +
					"[validValues] not found"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_select_typeoptions_validvalues_" +
					"missing.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldSelectTypeOptionsValidValuesValueMissing()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/typeOptions/validValues/0: required " +
					"key [value] not found"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_select_typeoptions_validvalues_" +
					"value_missing.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldSetsExtraProperties()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith("extraneous key [extra] is not permitted"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_invalid_field_sets_extra_properties.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldSetsMissing()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith("required key [fieldSets] not found"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_invalid_field_sets_missing.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldTextDataTypeUnsupported()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0/dataType: unsupported is not a valid " +
					"enum value"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_invalid_field_text_datatype_unsupported.json"));
	}

	@Test
	public void testValidateConfigurationInvalidFieldTextExtraProperties()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			new StringStartsWith(
				"/fieldSets/0/fields/0: extraneous key [extra] is not " +
					"permitted"));

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_invalid_field_text_extra_properties.json"));
	}

	@Test
	public void testValidateConfigurationValidComplete() throws Exception {
		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_complete.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldCheckboxComplete()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_field_checkbox_complete.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldCheckboxDefaultValueBooleanFalse()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_valid_field_checkbox_defaultvalue_boolean_" +
					"false.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldCheckboxDefaultValueBooleanTrue()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_valid_field_checkbox_defaultvalue_boolean_" +
					"true.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldCheckboxDefaultValueStringFalse()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_valid_field_checkbox_defaultvalue_string_" +
					"false.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldCheckboxDefaultValueStringTrue()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_valid_field_checkbox_defaultvalue_string_" +
					"true.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldCheckboxRequired()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_field_checkbox_required.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldColorPaletteComplete()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_field_colorpalette_complete.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldColorPaletteRequired()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_field_colorpalette_required.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldItemSelectorComplete()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_field_itemselector_complete.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldItemSelectorDefaultValueRequired()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_valid_field_itemselector_defaultvalue_" +
					"required.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldItemSelectorRequired()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_field_itemselector_required.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldItemSelectorTypeOptionsEnableSelectTemplate()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_valid_field_itemselector_typeoptions_" +
					"enableselecttemplate.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldItemSelectorTypeOptionsRequired()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read(
				"configuration_valid_field_itemselector_typeoptions_" +
					"required.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldSelectDoubleRequired()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_field_select_double_required.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldSelectIntRequired()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_field_select_int_required.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldSelectStringComplete()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_field_select_string_complete.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldSelectStringRequired()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_field_select_string_required.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldTextComplete()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_field_text_complete.json"));
	}

	@Test
	public void testValidateConfigurationValidFieldTextRequired()
		throws Exception {

		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_field_text_required.json"));
	}

	@Test
	public void testValidateConfigurationValidRequired() throws Exception {
		_fragmentEntryValidatorImpl.validateConfiguration(
			_read("configuration_valid_required.json"));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private FragmentEntryValidatorImpl _fragmentEntryValidatorImpl;

}