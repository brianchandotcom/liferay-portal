/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.model;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Arques
 */
public class AssetVocabularyMetricTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetVocabularyNames() {
		AssetVocabularyMetric childAssetVocabularyMetric =
			new AssetVocabularyMetric(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				Collections.singletonList(
					new AssetCategoryMetric(
						RandomTestUtil.randomString(),
						RandomTestUtil.randomString(),
						RandomTestUtil.randomLong())));

		AssetVocabularyMetric assetVocabularyMetric = new AssetVocabularyMetric(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			Collections.singletonList(
				new AssetCategoryMetric(
					childAssetVocabularyMetric, RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					RandomTestUtil.randomLong())));

		Assert.assertEquals(
			Arrays.asList(
				assetVocabularyMetric.getName(),
				childAssetVocabularyMetric.getName()),
			assetVocabularyMetric.getVocabularyNames());
	}

	@Test
	public void testGetVocabularyNamesWithEmptyAssetCategoryMetrics() {
		AssetVocabularyMetric assetVocabularyMetric = new AssetVocabularyMetric(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertEquals(
			Collections.emptyList(),
			assetVocabularyMetric.getVocabularyNames());
	}

	@Test
	public void testGetVocabularyNamesWithEmptyAssetVocabulary() {
		AssetVocabularyMetric assetVocabularyMetric = new AssetVocabularyMetric(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			Collections.singletonList(
				new AssetCategoryMetric(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					RandomTestUtil.randomLong())));

		Assert.assertEquals(
			Collections.singletonList(assetVocabularyMetric.getName()),
			assetVocabularyMetric.getVocabularyNames());
	}

	@Test
	public void testGetVocabularyNamesWithEmptyFirstAssetCategoryMetricAndNonemptySecondAssetCategoryMetric() {
		AssetVocabularyMetric childAssetVocabularyMetric1 =
			new AssetVocabularyMetric(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				Collections.emptyList());

		AssetVocabularyMetric childAssetVocabularyMetric2 =
			new AssetVocabularyMetric(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				Collections.singletonList(
					new AssetCategoryMetric(
						RandomTestUtil.randomString(),
						RandomTestUtil.randomString(),
						RandomTestUtil.randomLong())));

		AssetVocabularyMetric assetVocabularyMetric = new AssetVocabularyMetric(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			Arrays.asList(
				new AssetCategoryMetric(
					childAssetVocabularyMetric1, RandomTestUtil.randomString(),
					RandomTestUtil.randomString(), RandomTestUtil.randomLong()),
				new AssetCategoryMetric(
					childAssetVocabularyMetric2, RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					RandomTestUtil.randomLong())));

		Assert.assertEquals(
			Arrays.asList(
				assetVocabularyMetric.getName(),
				childAssetVocabularyMetric2.getName()),
			assetVocabularyMetric.getVocabularyNames());
	}

	@Test
	public void testToJSONArray() {
		AssetCategoryMetric assetCategoryMetric1 = new AssetCategoryMetric(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLong());

		AssetCategoryMetric assetCategoryMetric2 = new AssetCategoryMetric(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLong());

		AssetVocabularyMetric assetVocabularyMetric = new AssetVocabularyMetric(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			Arrays.asList(assetCategoryMetric1, assetCategoryMetric2));

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"key", assetCategoryMetric1.getKey()
				).put(
					"name", assetCategoryMetric1.getName()
				).put(
					"value", assetCategoryMetric1.getValue()
				).put(
					"vocabularyName", assetVocabularyMetric.getName()
				),
				JSONUtil.put(
					"key", assetCategoryMetric2.getKey()
				).put(
					"name", assetCategoryMetric2.getName()
				).put(
					"value", assetCategoryMetric2.getValue()
				).put(
					"vocabularyName", assetVocabularyMetric.getName()
				)
			).toString(),
			String.valueOf(assetVocabularyMetric.toJSONArray()));
	}

	@Test
	public void testToJSONArrayWithEmptyAssetCategoryMetrics() {
		AssetVocabularyMetric assetVocabularyMetric = new AssetVocabularyMetric(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertEquals(
			String.valueOf(JSONFactoryUtil.createJSONArray()),
			String.valueOf(assetVocabularyMetric.toJSONArray()));
	}

}