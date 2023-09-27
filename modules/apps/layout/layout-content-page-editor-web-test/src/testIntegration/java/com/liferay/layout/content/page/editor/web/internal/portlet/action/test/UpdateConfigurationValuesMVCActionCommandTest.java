/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

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
@Sync
public class UpdateConfigurationValuesMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() {
		_objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			}
		};
	}

	@Test
	public void testMergeEditableValuesJSONObject() throws Exception {
		JSONObject defaultEditableValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_read("default_editable_values.json"));

		JSONObject mergeEditableValuesJSONObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_mergeEditableValuesJSONObject",
			new Class<?>[] {JSONObject.class, String.class},
			defaultEditableValuesJSONObject, _read("editable_values.json"));

		Assert.assertEquals(
			_objectMapper.readTree(
				_read("merged_editable_values_with_same_elements.json")),
			_objectMapper.readTree(mergeEditableValuesJSONObject.toString()));
	}

	@Test
	public void testMergeEditableValuesJSONObjectWithNewElementDefaultEditableValues()
		throws Exception {

		JSONObject defaultEditableValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_read("default_editable_values_with_new_element.json"));

		JSONObject mergeEditableValuesJSONObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_mergeEditableValuesJSONObject",
			new Class<?>[] {JSONObject.class, String.class},
			defaultEditableValuesJSONObject, _read("editable_values.json"));

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"merged_editable_values_with_new_element_default_" +
						"editable_values.json")),
			_objectMapper.readTree(mergeEditableValuesJSONObject.toString()));
	}

	@Test
	public void testMergeEditableValuesJSONObjectWithNewElementEditableValues()
		throws Exception {

		JSONObject defaultEditableValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				_read("default_editable_values.json"));

		JSONObject mergeEditableValuesJSONObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_mergeEditableValuesJSONObject",
			new Class<?>[] {JSONObject.class, String.class},
			defaultEditableValuesJSONObject,
			_read("editable_values_with_new_element.json"));

		Assert.assertEquals(
			_objectMapper.readTree(
				_read(
					"merged_editable_values_with_new_element_editable_values." +
						"json")),
			_objectMapper.readTree(mergeEditableValuesJSONObject.toString()));
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/update_configuration_values"
	)
	private MVCActionCommand _mvcActionCommand;

	private ObjectMapper _objectMapper;

}