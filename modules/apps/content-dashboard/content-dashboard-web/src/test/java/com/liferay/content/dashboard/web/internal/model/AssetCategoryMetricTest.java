/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.model;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Arques
 */
public class AssetCategoryMetricTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToJSONObject() {
		AssetCategoryMetric childAssetCategoryMetric = new AssetCategoryMetric(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLong());

		AssetVocabularyMetric assetVocabularyMetric = new AssetVocabularyMetric(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			Collections.singletonList(childAssetCategoryMetric));

		AssetCategoryMetric assetCategoryMetric = new AssetCategoryMetric(
			assetVocabularyMetric, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomLong());

		String vocabularyName = RandomTestUtil.randomString();

		Assert.assertEquals(
			JSONUtil.put(
				"categories",
				JSONUtil.put(
					JSONUtil.put(
						"key", childAssetCategoryMetric.getKey()
					).put(
						"name", childAssetCategoryMetric.getName()
					).put(
						"value", childAssetCategoryMetric.getValue()
					).put(
						"vocabularyName", assetVocabularyMetric.getName()
					))
			).put(
				"key", assetCategoryMetric.getKey()
			).put(
				"name", assetCategoryMetric.getName()
			).put(
				"value", assetCategoryMetric.getValue()
			).put(
				"vocabularyName", vocabularyName
			).toString(),
			String.valueOf(assetCategoryMetric.toJSONObject(vocabularyName)));
	}

	@Test
	public void testToJSONObjectWithEmptyAssetVocabularyMetric() {
		AssetCategoryMetric assetCategoryMetric = new AssetCategoryMetric(
			new AssetVocabularyMetric(
				RandomTestUtil.randomString(), RandomTestUtil.randomString()),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLong());

		String vocabularyName = RandomTestUtil.randomString();

		Assert.assertEquals(
			JSONUtil.put(
				"key", assetCategoryMetric.getKey()
			).put(
				"name", assetCategoryMetric.getName()
			).put(
				"value", assetCategoryMetric.getValue()
			).put(
				"vocabularyName", vocabularyName
			).toString(),
			String.valueOf(assetCategoryMetric.toJSONObject(vocabularyName)));
	}

	@Test
	public void testToJSONObjectWithNullAssetVocabularyMetric() {
		AssetCategoryMetric assetCategoryMetric = new AssetCategoryMetric(
			null, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLong());

		String vocabularyName = RandomTestUtil.randomString();

		Assert.assertEquals(
			JSONUtil.put(
				"key", assetCategoryMetric.getKey()
			).put(
				"name", assetCategoryMetric.getName()
			).put(
				"value", assetCategoryMetric.getValue()
			).put(
				"vocabularyName", vocabularyName
			).toString(),
			String.valueOf(assetCategoryMetric.toJSONObject(vocabularyName)));
	}

}