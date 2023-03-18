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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author David Arques
 */
public class AssetVocabularyMetric {

	public static AssetVocabularyMetric empty() {
		return _EMPTY;
	}

	public AssetVocabularyMetric(String key, String name) {
		this(key, name, Collections.emptyList());
	}

	public AssetVocabularyMetric(
		String key, String name,
		List<AssetCategoryMetric> assetCategoryMetrics) {

		_key = key;
		_name = name;
		_assetCategoryMetrics = Optional.ofNullable(
			assetCategoryMetrics
		).map(
			Collections::unmodifiableList
		).orElse(
			Collections.emptyList()
		);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetVocabularyMetric)) {
			return false;
		}

		AssetVocabularyMetric assetVocabularyMetric =
			(AssetVocabularyMetric)object;

		if (Objects.equals(
				_assetCategoryMetrics,
				assetVocabularyMetric._assetCategoryMetrics) &&
			Objects.equals(_key, assetVocabularyMetric._key) &&
			Objects.equals(_name, assetVocabularyMetric._name)) {

			return true;
		}

		return false;
	}

	public List<AssetCategoryMetric> getAssetCategoryMetrics() {
		return _assetCategoryMetrics;
	}

	public String getKey() {
		return _key;
	}

	public String getName() {
		return _name;
	}

	public List<String> getVocabularyNames() {
		if (_assetCategoryMetrics.isEmpty()) {
			return Collections.emptyList();
		}

		Stream<AssetCategoryMetric> stream = _assetCategoryMetrics.stream();

		return stream.map(
			AssetCategoryMetric::getAssetVocabularyMetric
		).filter(
			assetVocabularyMetric -> ListUtil.isNotEmpty(
				assetVocabularyMetric.getAssetCategoryMetrics())
		).findFirst(
		).map(
			AssetVocabularyMetric::getName
		).map(
			name -> Collections.unmodifiableList(Arrays.asList(_name, name))
		).orElse(
			Collections.singletonList(_name)
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(_assetCategoryMetrics);
	}

	public JSONArray toJSONArray() {
		Stream<AssetCategoryMetric> stream = _assetCategoryMetrics.stream();

		return JSONUtil.putAll(
			stream.map(
				assetCategoryMetric -> assetCategoryMetric.toJSONObject(_name)
			).toArray());
	}

	private static final AssetVocabularyMetric _EMPTY =
		new AssetVocabularyMetric(StringPool.BLANK, StringPool.BLANK);

	private final List<AssetCategoryMetric> _assetCategoryMetrics;
	private final String _key;
	private final String _name;

}