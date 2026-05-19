/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Selena Aungst
 */
public class IndexUtilTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMergeToJSONObjectWithStemmerOverrideRules() {
		JSONObject jsonObject = _createDutchOverrideSettingsJSONObject(
			JSONUtil.putAll("old=>old"));

		JSONObject mergeJSONObject = _createDutchOverrideSettingsJSONObject(
			JSONUtil.putAll(
				"fiets=>fiets", "bromfiets=>bromfiets", "ei=>eier",
				"kind=>kinder"));

		IndexUtil.mergeToJSONObject(jsonObject, mergeJSONObject);

		JSONObject indexJSONObject = jsonObject.getJSONObject("index");

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

	private JSONObject _createDutchOverrideSettingsJSONObject(
		JSONArray rulesJSONArray) {

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

}