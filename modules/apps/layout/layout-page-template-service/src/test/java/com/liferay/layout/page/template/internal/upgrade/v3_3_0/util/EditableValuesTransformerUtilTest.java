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

package com.liferay.layout.page.template.internal.upgrade.v3_3_0.util;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 */
public class EditableValuesTransformerUtilTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			}
		};
	}

	@Test
	public void testGetBackGroundImagesEditableValues() throws Exception {
		String editableValues = _read(
			"fragment_entry_link_background_images_editable_values.json");

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"fragment_entry_link_background_images_editable_values_" +
						"segments_experience_0.json")),
			_objectMapper.readTree(
				EditableValuesTransformerUtil.getEditableValues(
					editableValues, 0)));

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"fragment_entry_link_background_images_editable_values_" +
						"segments_experience_1.json")),
			_objectMapper.readTree(
				EditableValuesTransformerUtil.getEditableValues(
					editableValues, 1)));
	}

	@Test
	public void testGetEditableEditableValues() throws Exception {
		String editableValues = _read(
			"fragment_entry_link_editable_editable_values.json");

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"fragment_entry_link_editable_editable_values_segments_" +
						"experience_0.json")),
			_objectMapper.readTree(
				EditableValuesTransformerUtil.getEditableValues(
					editableValues, 0)));

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"fragment_entry_link_editable_editable_values_segments_" +
						"experience_1.json")),
			_objectMapper.readTree(
				EditableValuesTransformerUtil.getEditableValues(
					editableValues, 1)));

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"fragment_entry_link_editable_editable_values_segments_" +
						"experience_2.json")),
			_objectMapper.readTree(
				EditableValuesTransformerUtil.getEditableValues(
					editableValues, 2)));
	}

	@Test
	public void testGetEditableValuesWithoutDefaultExperience()
		throws Exception {

		String editableValues = _read(
			"fragment_entry_link_editable_values_without_default_experience." +
				"json");

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"fragment_entry_link_editable_values_without_default_" +
						"experience_segments_experience_0.json")),
			_objectMapper.readTree(
				EditableValuesTransformerUtil.getEditableValues(
					editableValues, 0)));

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"fragment_entry_link_editable_values_without_default_" +
						"experience_segments_experience_2.json")),
			_objectMapper.readTree(
				EditableValuesTransformerUtil.getEditableValues(
					editableValues, 2)));
	}

	@Test
	public void testGetEditableValuesWithoutExperience() throws Exception {
		String editableValues = _read(
			"fragment_entry_link_editable_values_without_experience.json");

		Assert.assertEquals(
			_objectMapper.readTree(editableValues),
			_objectMapper.readTree(
				EditableValuesTransformerUtil.getEditableValues(
					editableValues, 0)));
	}

	@Test
	public void testGetFreeMarkerEditableValues() throws Exception {
		String editableValues = _read(
			"fragment_entry_link_free_marker_editable_values.json");

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"fragment_entry_link_free_marker_editable_values_" +
						"segments_experience_0.json")),
			_objectMapper.readTree(
				EditableValuesTransformerUtil.getEditableValues(
					editableValues, 0)));

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"fragment_entry_link_free_marker_editable_values_" +
						"segments_experience_1.json")),
			_objectMapper.readTree(
				EditableValuesTransformerUtil.getEditableValues(
					editableValues, 1)));

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"fragment_entry_link_free_marker_editable_values_" +
						"segments_experience_2.json")),
			_objectMapper.readTree(
				EditableValuesTransformerUtil.getEditableValues(
					editableValues, 2)));
	}

	@Test
	public void testGetMappedEditableValues() throws Exception {
		String editableValues = _read(
			"fragment_entry_link_mapped_editable_values.json");

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"fragment_entry_link_mapped_editable_values_segments_" +
						"experience_0.json")),
			_objectMapper.readTree(
				EditableValuesTransformerUtil.getEditableValues(
					editableValues, 0)));
	}

	@Test
	public void testGetPortletEditableValues() throws Exception {
		String editableValues = _read(
			"fragment_entry_link_portlet_editable_values.json");

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"fragment_entry_link_portlet_editable_values_segments_" +
						"experience_0.json")),
			_objectMapper.readTree(
				EditableValuesTransformerUtil.getEditableValues(
					editableValues, 0)));
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private ObjectMapper _objectMapper;

}