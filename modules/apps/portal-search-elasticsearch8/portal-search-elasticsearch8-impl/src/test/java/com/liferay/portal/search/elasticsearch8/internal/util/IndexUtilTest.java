/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.util;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Selena Aungst
 */
public class IndexUtilTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testMergeToJsonObjectWithDynamicTemplates() {
		JSONObject baseJSONObject = _createMappingsWithDynamicTemplates(
			JSONUtil.putAll(
				_createDynamicTemplate("template_a", "text"),
				_createDynamicTemplate("template_b", "text")));

		JSONObject mergeJSONObject = _createMappingsWithDynamicTemplates(
			JSONUtil.putAll(
				_createDynamicTemplate("template_b", "keyword"),
				_createDynamicTemplate("template_c", "text")));

		IndexUtil.mergeToJsonObject(baseJSONObject, mergeJSONObject);

		JSONObject mappingsJSONObject = baseJSONObject.getJSONObject(
			"mappings");

		JSONArray dynamicTemplatesJSONArray = mappingsJSONObject.getJSONArray(
			"dynamic_templates");

		Assert.assertEquals(3, dynamicTemplatesJSONArray.length());

		JSONObject firstTemplateJSONObject =
			dynamicTemplatesJSONArray.getJSONObject(0);

		Assert.assertTrue(firstTemplateJSONObject.has("template_a"));

		JSONObject secondTemplateJSONObject =
			dynamicTemplatesJSONArray.getJSONObject(1);

		JSONObject overriddenTemplateJSONObject =
			secondTemplateJSONObject.getJSONObject("template_b");

		JSONObject overriddenMappingJSONObject =
			overriddenTemplateJSONObject.getJSONObject("mapping");

		Assert.assertEquals(
			"keyword", overriddenMappingJSONObject.getString("type"));

		JSONObject thirdTemplateJSONObject =
			dynamicTemplatesJSONArray.getJSONObject(2);

		Assert.assertTrue(thirdTemplateJSONObject.has("template_c"));
	}

	@Test
	public void testMergeToJsonObjectWithStemmerOverrideRules() {
		JSONObject baseJSONObject = _createDutchOverrideSettings(
			JSONUtil.putAll("old=>old"));

		JSONObject mergeJSONObject = _createDutchOverrideSettings(
			JSONUtil.putAll(
				"fiets=>fiets", "bromfiets=>bromfiets", "ei=>eier",
				"kind=>kinder"));

		IndexUtil.mergeToJsonObject(baseJSONObject, mergeJSONObject);

		JSONObject indexJSONObject = baseJSONObject.getJSONObject("index");

		JSONObject analysisJSONObject = indexJSONObject.getJSONObject(
			"analysis");

		JSONObject filterJSONObject = analysisJSONObject.getJSONObject(
			"filter");

		JSONObject dutchOverrideJSONObject = filterJSONObject.getJSONObject(
			"dutch_override");

		JSONArray rulesJSONArray = dutchOverrideJSONObject.getJSONArray(
			"rules");

		Assert.assertEquals(4, rulesJSONArray.length());
		Assert.assertEquals("fiets=>fiets", rulesJSONArray.getString(0));
	}

	private JSONObject _createDutchOverrideSettings(JSONArray rulesJSONArray) {
		return JSONUtil.put(
			"index",
			JSONUtil.put(
				"analysis",
				JSONUtil.put(
					"filter",
					JSONUtil.put(
						"dutch_override",
						JSONUtil.put(
							"rules", rulesJSONArray
						).put(
							"type", "stemmer_override"
						)))));
	}

	private JSONObject _createDynamicTemplate(String name, String type) {
		return JSONUtil.put(
			name,
			JSONUtil.put(
				"mapping", JSONUtil.put("type", type)
			).put(
				"match_mapping_type", "string"
			));
	}

	private JSONObject _createMappingsWithDynamicTemplates(
		JSONArray dynamicTemplatesJSONArray) {

		return JSONUtil.put(
			"mappings",
			JSONUtil.put("dynamic_templates", dynamicTemplatesJSONArray));
	}

}