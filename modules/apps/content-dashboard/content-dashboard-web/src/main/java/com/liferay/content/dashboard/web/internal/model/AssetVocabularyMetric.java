/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.model;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
		_assetCategoryMetrics =
			(assetCategoryMetrics == null) ? Collections.emptyList() :
				Collections.unmodifiableList(assetCategoryMetrics);
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

		for (AssetCategoryMetric assetCategoryMetric : _assetCategoryMetrics) {
			AssetVocabularyMetric assetVocabularyMetric =
				assetCategoryMetric.getAssetVocabularyMetric();

			if (ListUtil.isEmpty(
					assetVocabularyMetric.getAssetCategoryMetrics())) {

				continue;
			}

			return Collections.unmodifiableList(
				Arrays.asList(_name, assetVocabularyMetric.getName()));
		}

		return Collections.singletonList(_name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(_assetCategoryMetrics);
	}

	public JSONArray toJSONArray() {
		return JSONUtil.toJSONArray(
			_assetCategoryMetrics,
			assetCategoryMetric -> assetCategoryMetric.toJSONObject(_name),
			_log);
	}

	private static final AssetVocabularyMetric _EMPTY =
		new AssetVocabularyMetric(StringPool.BLANK, StringPool.BLANK);

	private static final Log _log = LogFactoryUtil.getLog(
		AssetVocabularyMetric.class.getName());

	private final List<AssetCategoryMetric> _assetCategoryMetrics;
	private final String _key;
	private final String _name;

}