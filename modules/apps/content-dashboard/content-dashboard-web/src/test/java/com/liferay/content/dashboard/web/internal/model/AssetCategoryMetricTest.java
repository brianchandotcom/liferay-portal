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