/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.model;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Objects;

/**
 * @author David Arques
 */
public class AssetCategoryMetric {

	public AssetCategoryMetric(
		AssetVocabularyMetric assetVocabularyMetric, String key, String name,
		long value) {

		_assetVocabularyMetric =
			(assetVocabularyMetric == null) ? AssetVocabularyMetric.empty() :
				assetVocabularyMetric;
		_key = key;
		_name = name;
		_value = value;
	}

	public AssetCategoryMetric(String key, String name, long value) {
		this(AssetVocabularyMetric.empty(), key, name, value);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetCategoryMetric)) {
			return false;
		}

		AssetCategoryMetric assetCategoryMetric = (AssetCategoryMetric)object;

		if (Objects.equals(
				_assetVocabularyMetric,
				assetCategoryMetric._assetVocabularyMetric) &&
			Objects.equals(_key, assetCategoryMetric._key) &&
			Objects.equals(_value, assetCategoryMetric._value)) {

			return true;
		}

		return false;
	}

	public AssetVocabularyMetric getAssetVocabularyMetric() {
		return _assetVocabularyMetric;
	}

	public String getKey() {
		return _key;
	}

	public String getName() {
		return _name;
	}

	public long getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_assetVocabularyMetric, _key, _value);
	}

	public JSONObject toJSONObject(String vocabularyName) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (ListUtil.isNotEmpty(
				_assetVocabularyMetric.getAssetCategoryMetrics())) {

			jsonObject.put("categories", _assetVocabularyMetric.toJSONArray());
		}

		return jsonObject.put(
			"key", _key
		).put(
			"name", _name
		).put(
			"value", _value
		).put(
			"vocabularyName", vocabularyName
		);
	}

	private final AssetVocabularyMetric _assetVocabularyMetric;
	private final String _key;
	private final String _name;
	private final long _value;

}