/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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