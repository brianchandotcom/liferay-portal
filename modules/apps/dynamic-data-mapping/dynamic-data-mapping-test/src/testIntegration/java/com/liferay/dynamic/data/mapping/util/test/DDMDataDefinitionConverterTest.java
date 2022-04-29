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

package com.liferay.dynamic.data.mapping.util.test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.util.DDMDataDefinitionConverter;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class DDMDataDefinitionConverterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			}
		};
	}

	@Test
	public void testConvertDDMFormDataDefinitionAllFields() throws Exception {
		String dataDefinition =
			_ddmDataDefinitionConverter.convertDDMFormDataDefinition(
				_read(
					"ddm-form-data-definition-json-converter-all-fields.json"),
				0, 0);

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"ddm-form-data-definition-json-converter-all-fields-" +
						"expected-result.json")),
			_objectMapper.readTree(dataDefinition));
	}

	@Test
	public void testConvertDDMFormDataDefinitionEmptyValidation()
		throws Exception {

		String dataDefinition =
			_ddmDataDefinitionConverter.convertDDMFormDataDefinition(
				_read(
					"ddm-form-data-definition-json-converter-empty-" +
						"validation.json"),
				0, 0);

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"ddm-form-data-definition-json-converter-empty-" +
						"validation-expected-result.json")),
			_objectMapper.readTree(dataDefinition));
	}

	@Test
	public void testConvertDDMFormDataDefinitionNestedFields()
		throws Exception {

		String dataDefinition =
			_ddmDataDefinitionConverter.convertDDMFormDataDefinition(
				_read(
					"ddm-form-data-definition-json-converter-nested-" +
						"fields.json"),
				0, 0);

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"ddm-form-data-definition-json-converter-nested-fields-" +
						"expected-result.json")),
			_objectMapper.readTree(dataDefinition));
	}

	@Test
	public void testConvertDDMFormDataDefinitionOptionWithInvalidCharacters()
		throws Exception {

		String dataDefinition =
			_ddmDataDefinitionConverter.convertDDMFormDataDefinition(
				_read(
					"ddm-form-data-definition-json-converter-option-with-" +
						"invalid-characters.json"),
				0, 0);

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"ddm-form-data-definition-json-converter-option-with-" +
						"invalid-characters-expected-result.json")),
			_objectMapper.readTree(dataDefinition));
	}

	@Test
	public void testConvertDDMFormDataDefinitionParentStructure()
		throws Exception {

		String dataDefinition =
			_ddmDataDefinitionConverter.convertDDMFormDataDefinition(
				_read(
					"ddm-form-data-definition-json-converter-parent-" +
						"structure.json"),
				1, 2);

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"ddm-form-data-definition-json-converter-parent-" +
						"structure-expected-result.json")),
			_objectMapper.readTree(dataDefinition));
	}

	@Test
	public void testConvertDDMFormDataDefinitionRepeatableNestedFields()
		throws Exception {

		String dataDefinition =
			_ddmDataDefinitionConverter.convertDDMFormDataDefinition(
				_read(
					"ddm-form-data-definition-json-converter-repeatable-" +
						"nested-fields.json"),
				0, 0);

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"ddm-form-data-definition-json-converter-repeatable-" +
						"nested-fields-expected-result.json")),
			_objectMapper.readTree(dataDefinition));
	}

	@Test
	public void testConvertDDMFormLayoutDataDefinitionLinkToPage()
		throws Exception {

		String structureVersionDataDefinition =
			_ddmDataDefinitionConverter.convertDDMFormDataDefinition(
				_read(
					"ddm-form-data-definition-json-converter-link-to-" +
						"page.json"),
				0, 0);

		String dataDefinition1 = _convertDDMFormLayoutDataDefinition(
			"ddm-form-layout-data-definition-json-converter-link-to-page.json",
			structureVersionDataDefinition);

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"ddm-form-layout-data-definition-json-converter-link-to-" +
						"page-expected-result.json")),
			_objectMapper.readTree(dataDefinition1));

		String dataDefinition2 = _convertDDMFormLayoutDataDefinition(
			"ddm-form-layout-data-definition-json-converter-link-to-page.json",
			structureVersionDataDefinition);

		Assert.assertEquals(
			_objectMapper.readTree(dataDefinition1),
			_objectMapper.readTree(dataDefinition2));
	}

	@Test
	public void testConvertDDMFormLayoutDataDefinitionNestedFields()
		throws Exception {

		String structureVersionDataDefinition =
			_ddmDataDefinitionConverter.convertDDMFormDataDefinition(
				_read(
					"ddm-form-data-definition-json-converter-nested-" +
						"fields.json"),
				0, 0);

		String dataDefinition = _convertDDMFormLayoutDataDefinition(
			"ddm-form-layout-data-definition-json-converter-nested-fields.json",
			structureVersionDataDefinition);

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"ddm-form-layout-data-definition-json-converter-nested-" +
						"fields-expected-result.json")),
			_objectMapper.readTree(dataDefinition));
	}

	private String _convertDDMFormLayoutDataDefinition(
			String fileName, String structureVersionDataDefinition)
		throws Exception {

		return _ddmDataDefinitionConverter.convertDDMFormLayoutDataDefinition(
			TestPropsValues.getGroupId(), 0, _read(fileName), 0,
			structureVersionDataDefinition);
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/dynamic/data/mapping/util/test/dependencies/" +
				fileName);
	}

	@Inject
	private static DDMDataDefinitionConverter _ddmDataDefinitionConverter;

	private ObjectMapper _objectMapper;

}