/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Joshua Cords
 */
public class AssetListFiltersUpgradeUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToUpgradedTypeSettingsCategories() throws Exception {
		String assetCategoryId1 = String.valueOf(RandomTestUtil.randomLong());
		String assetCategoryId2 = String.valueOf(RandomTestUtil.randomLong());

		_assertFilter(
			"assetCategories", "true", "true", "contains", "all",
			assetCategoryId1, assetCategoryId2);
		_assertFilter(
			"assetCategories", "true", "false", "contains", "any",
			assetCategoryId1, assetCategoryId2);
		_assertFilter(
			"assetCategories", "false", "true", "not-contains", "all",
			assetCategoryId1, assetCategoryId2);
		_assertFilter(
			"assetCategories", "false", "false", "not-contains", "any",
			assetCategoryId1, assetCategoryId2);
	}

	@Test
	public void testToUpgradedTypeSettingsKeywords() throws Exception {
		String keyword = RandomTestUtil.randomString();

		_assertFilter("keywords", "true", "true", "contains", "all", keyword);
		_assertFilter("keywords", "true", "false", "contains", "any", keyword);
		_assertFilter(
			"keywords", "false", "true", "not-contains", "all", keyword);
		_assertFilter(
			"keywords", "false", "false", "not-contains", "any", keyword);
	}

	@Test
	public void testToUpgradedTypeSettingsMultipleKeywords() throws Exception {
		String[] keywords = {
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString()
		};

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.fastLoad(
			AssetListFiltersUpgradeUtil.toUpgradedTypeSettings(
				UnicodePropertiesBuilder.put(
					"queryAndOperator0", "false"
				).put(
					"queryContains0", "true"
				).put(
					"queryName0", "keywords"
				).put(
					"queryValues0", StringUtil.merge(keywords)
				).buildString())
		).build();

		JSONArray filtersJSONArray = JSONFactoryUtil.createJSONArray(
			unicodeProperties.getProperty("filters"));

		Assert.assertEquals(
			filtersJSONArray.toString(), 1, filtersJSONArray.length());

		JSONObject filterJSONObject = filtersJSONArray.getJSONObject(0);

		Assert.assertEquals(
			"contains", filterJSONObject.getString("operatorName"));
		Assert.assertEquals(
			"keywords", filterJSONObject.getString("propertyName"));
		Assert.assertEquals("any", filterJSONObject.getString("quantifier"));
		Assert.assertEquals(
			StringUtil.merge(keywords, StringPool.SPACE),
			filterJSONObject.getString("value"));
	}

	@Test
	public void testToUpgradedTypeSettingsQuotedKeywords() throws Exception {
		String keyword = RandomTestUtil.randomString();
		String keywordPhrase =
			RandomTestUtil.randomString() + StringPool.SPACE +
				RandomTestUtil.randomString();

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.fastLoad(
			AssetListFiltersUpgradeUtil.toUpgradedTypeSettings(
				UnicodePropertiesBuilder.put(
					"queryAndOperator0", "false"
				).put(
					"queryContains0", "true"
				).put(
					"queryName0", "keywords"
				).put(
					"queryValues0",
					StringUtil.merge(new String[] {keywordPhrase, keyword})
				).buildString())
		).build();

		JSONArray filtersJSONArray = JSONFactoryUtil.createJSONArray(
			unicodeProperties.getProperty("filters"));

		Assert.assertEquals(
			filtersJSONArray.toString(), 1, filtersJSONArray.length());

		Assert.assertEquals(
			StringBundler.concat(
				StringPool.QUOTE, keywordPhrase, StringPool.QUOTE,
				StringPool.SPACE, keyword),
			JSONUtil.getValueAsString(
				filtersJSONArray, "JSONObject/0", "Object/value"));
	}

	@Test
	public void testToUpgradedTypeSettingsRemovesLegacyKeys() throws Exception {
		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.fastLoad(
			AssetListFiltersUpgradeUtil.toUpgradedTypeSettings(
				UnicodePropertiesBuilder.put(
					"anyAssetType", "true"
				).put(
					"queryAndOperator0", "true"
				).put(
					"queryAndOperator1", "false"
				).put(
					"queryContains0", "true"
				).put(
					"queryContains1", "false"
				).put(
					"queryName0", "assetCategories"
				).put(
					"queryName1", "assetTags"
				).put(
					"queryValues0", String.valueOf(RandomTestUtil.randomLong())
				).put(
					"queryValues1", RandomTestUtil.randomString()
				).buildString())
		).build();

		Assert.assertEquals(
			"true", unicodeProperties.getProperty("anyAssetType"));
		Assert.assertNotNull(unicodeProperties.getProperty("filters"));

		for (String key : unicodeProperties.keySet()) {
			Assert.assertFalse(key, key.startsWith("query"));
		}
	}

	@Test
	public void testToUpgradedTypeSettingsTags() throws Exception {
		String assetTagName1 = RandomTestUtil.randomString();
		String assetTagName2 = RandomTestUtil.randomString();

		_assertFilter(
			"assetTags", "true", "true", "contains", "all", assetTagName1,
			assetTagName2);
		_assertFilter(
			"assetTags", "true", "false", "contains", "any", assetTagName1,
			assetTagName2);
		_assertFilter(
			"assetTags", "false", "true", "not-contains", "all", assetTagName1,
			assetTagName2);
		_assertFilter(
			"assetTags", "false", "false", "not-contains", "any", assetTagName1,
			assetTagName2);
	}

	private void _assertFilter(
			String propertyName, String queryContains, String queryAndOperator,
			String expectedOperatorName, String expectedQuantifier,
			String... expectedValues)
		throws Exception {

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.fastLoad(
			AssetListFiltersUpgradeUtil.toUpgradedTypeSettings(
				UnicodePropertiesBuilder.put(
					"queryAndOperator0", queryAndOperator
				).put(
					"queryContains0", queryContains
				).put(
					"queryName0", propertyName
				).put(
					"queryValues0",
					StringUtil.merge(expectedValues, StringPool.COMMA)
				).buildString())
		).build();

		JSONArray filtersJSONArray = JSONFactoryUtil.createJSONArray(
			unicodeProperties.getProperty("filters"));

		Assert.assertEquals(
			filtersJSONArray.toString(), 1, filtersJSONArray.length());

		JSONObject filterJSONObject = filtersJSONArray.getJSONObject(0);

		Assert.assertEquals(
			expectedOperatorName, filterJSONObject.getString("operatorName"));
		Assert.assertEquals(
			propertyName, filterJSONObject.getString("propertyName"));
		Assert.assertEquals(
			expectedQuantifier, filterJSONObject.getString("quantifier"));

		Object value = filterJSONObject.get("value");

		if (value instanceof JSONArray) {
			JSONArray valueJSONArray = (JSONArray)value;

			Assert.assertArrayEquals(
				new String[0], JSONUtil.toStringArray(valueJSONArray, "label"));
			Assert.assertArrayEquals(
				expectedValues,
				JSONUtil.toStringArray(valueJSONArray, "value"));
		}
		else {
			Assert.assertArrayEquals(
				expectedValues, new String[] {GetterUtil.getString(value)});
		}
	}

}