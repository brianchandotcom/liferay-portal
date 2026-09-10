/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
		_assertCategories("true", "true", "contains", "all");
		_assertCategories("true", "false", "contains", "any");
		_assertCategories("false", "true", "not-contains", "all");
		_assertCategories("false", "false", "not-contains", "any");
	}

	@Test
	public void testToUpgradedTypeSettingsKeywordPhraseQuoted()
		throws Exception {

		String keyword = RandomTestUtil.randomString();
		String keywordPhrase =
			RandomTestUtil.randomString() + StringPool.SPACE +
				RandomTestUtil.randomString();

		UnicodeProperties unicodeProperties = _toUpgradedUnicodeProperties(
			UnicodePropertiesBuilder.put(
				"queryAndOperator0", "false"
			).put(
				"queryContains0", "true"
			).put(
				"queryName0", "keywords"
			).put(
				"queryValues0",
				StringUtil.merge(new String[] {keywordPhrase, keyword})
			).buildString());

		JSONArray filtersJSONArray = _getFiltersJSONArray(unicodeProperties);

		Assert.assertEquals(
			filtersJSONArray.toString(), 1, filtersJSONArray.length());

		JSONObject filterJSONObject = filtersJSONArray.getJSONObject(0);

		Assert.assertEquals(
			StringUtil.quote(keywordPhrase, CharPool.QUOTE) + StringPool.SPACE +
				keyword,
			filterJSONObject.getString("value"));
	}

	@Test
	public void testToUpgradedTypeSettingsKeywords() throws Exception {
		_assertKeywords("true", "true", "contains", "all");
		_assertKeywords("true", "false", "contains", "any");
		_assertKeywords("false", "true", "not-contains", "all");
		_assertKeywords("false", "false", "not-contains", "any");
	}

	@Test
	public void testToUpgradedTypeSettingsMultipleKeywords() throws Exception {
		String[] keywords = {
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString()
		};

		UnicodeProperties unicodeProperties = _toUpgradedUnicodeProperties(
			UnicodePropertiesBuilder.put(
				"queryAndOperator0", "false"
			).put(
				"queryContains0", "true"
			).put(
				"queryName0", "keywords"
			).put(
				"queryValues0", StringUtil.merge(keywords)
			).buildString());

		JSONArray filtersJSONArray = _getFiltersJSONArray(unicodeProperties);

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
	public void testToUpgradedTypeSettingsRemovesLegacyKeys() throws Exception {
		UnicodeProperties unicodeProperties = _toUpgradedUnicodeProperties(
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
			).buildString());

		Assert.assertEquals(
			"true", unicodeProperties.getProperty("anyAssetType"));
		Assert.assertNotNull(unicodeProperties.getProperty("filters"));

		for (String key : unicodeProperties.keySet()) {
			Assert.assertFalse(key, key.startsWith("query"));
		}
	}

	@Test
	public void testToUpgradedTypeSettingsTags() throws Exception {
		_assertTags("true", "true", "contains", "all");
		_assertTags("true", "false", "contains", "any");
		_assertTags("false", "true", "not-contains", "all");
		_assertTags("false", "false", "not-contains", "any");
	}

	private void _assertCategories(
			String queryContains, String queryAndOperator,
			String expectedOperatorName, String expectedQuantifier)
		throws Exception {

		String assetCategoryId1 = String.valueOf(RandomTestUtil.randomLong());
		String assetCategoryId2 = String.valueOf(RandomTestUtil.randomLong());

		JSONObject filterJSONObject = _getSingleFilterJSONObject(
			"assetCategories", queryContains, queryAndOperator,
			StringUtil.merge(
				new String[] {assetCategoryId1, assetCategoryId2},
				StringPool.COMMA));

		Assert.assertEquals(
			expectedOperatorName, filterJSONObject.getString("operatorName"));
		Assert.assertEquals(
			"assetCategories", filterJSONObject.getString("propertyName"));
		Assert.assertEquals(
			expectedQuantifier, filterJSONObject.getString("quantifier"));

		JSONArray valueJSONArray = filterJSONObject.getJSONArray("value");

		Assert.assertEquals(
			valueJSONArray.toString(), 2, valueJSONArray.length());

		JSONObject valueJSONObject = valueJSONArray.getJSONObject(0);

		Assert.assertEquals(
			assetCategoryId1, valueJSONObject.getString("value"));
		Assert.assertFalse(valueJSONObject.has("label"));

		valueJSONObject = valueJSONArray.getJSONObject(1);

		Assert.assertEquals(
			assetCategoryId2, valueJSONObject.getString("value"));
	}

	private void _assertKeywords(
			String queryContains, String queryAndOperator,
			String expectedOperatorName, String expectedQuantifier)
		throws Exception {

		String keyword = RandomTestUtil.randomString();

		JSONObject filterJSONObject = _getSingleFilterJSONObject(
			"keywords", queryContains, queryAndOperator, keyword);

		Assert.assertEquals(
			expectedOperatorName, filterJSONObject.getString("operatorName"));
		Assert.assertEquals(
			"keywords", filterJSONObject.getString("propertyName"));
		Assert.assertEquals(
			expectedQuantifier, filterJSONObject.getString("quantifier"));
		Assert.assertEquals(keyword, filterJSONObject.getString("value"));
	}

	private void _assertTags(
			String queryContains, String queryAndOperator,
			String expectedOperatorName, String expectedQuantifier)
		throws Exception {

		String assetTagName1 = RandomTestUtil.randomString();
		String assetTagName2 = RandomTestUtil.randomString();

		JSONObject filterJSONObject = _getSingleFilterJSONObject(
			"assetTags", queryContains, queryAndOperator,
			StringUtil.merge(
				new String[] {assetTagName1, assetTagName2}, StringPool.COMMA));

		Assert.assertEquals(
			expectedOperatorName, filterJSONObject.getString("operatorName"));
		Assert.assertEquals(
			"assetTags", filterJSONObject.getString("propertyName"));
		Assert.assertEquals(
			expectedQuantifier, filterJSONObject.getString("quantifier"));

		JSONArray valueJSONArray = filterJSONObject.getJSONArray("value");

		Assert.assertEquals(
			valueJSONArray.toString(), 2, valueJSONArray.length());

		JSONObject valueJSONObject = valueJSONArray.getJSONObject(0);

		Assert.assertEquals(assetTagName1, valueJSONObject.getString("value"));
		Assert.assertFalse(valueJSONObject.has("label"));

		valueJSONObject = valueJSONArray.getJSONObject(1);

		Assert.assertEquals(assetTagName2, valueJSONObject.getString("value"));
		Assert.assertFalse(valueJSONObject.has("label"));
	}

	private JSONArray _getFiltersJSONArray(UnicodeProperties unicodeProperties)
		throws Exception {

		String filtersJSON = unicodeProperties.getProperty("filters");

		Assert.assertNotNull(filtersJSON);

		return JSONFactoryUtil.createJSONArray(filtersJSON);
	}

	private JSONObject _getSingleFilterJSONObject(
			String queryName, String queryContains, String queryAndOperator,
			String queryValues)
		throws Exception {

		UnicodeProperties unicodeProperties = _toUpgradedUnicodeProperties(
			UnicodePropertiesBuilder.put(
				"queryAndOperator0", queryAndOperator
			).put(
				"queryContains0", queryContains
			).put(
				"queryName0", queryName
			).put(
				"queryValues0", queryValues
			).buildString());

		JSONArray filtersJSONArray = _getFiltersJSONArray(unicodeProperties);

		Assert.assertEquals(
			filtersJSONArray.toString(), 1, filtersJSONArray.length());

		return filtersJSONArray.getJSONObject(0);
	}

	private UnicodeProperties _toUpgradedUnicodeProperties(
		String typeSettings) {

		String upgradedTypeSettings =
			AssetListFiltersUpgradeUtil.toUpgradedTypeSettings(typeSettings);

		Assert.assertNotNull(upgradedTypeSettings);

		return UnicodePropertiesBuilder.fastLoad(
			upgradedTypeSettings
		).build();
	}

}