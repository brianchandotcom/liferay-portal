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

package com.liferay.bulk.rest.internal.model;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adolfo Pérez
 */
@XmlRootElement
public class BulkAssetEntryCommonCategoriesModel {

	public BulkAssetEntryCommonCategoriesModel() {
	}

	public BulkAssetEntryCommonCategoriesModel(
		String description, Map<Long, List<AssetCategory>> assetCategoriesMap,
		List<AssetVocabulary> assetVocabularies) {

		_description = description;

		Set<Map.Entry<Long, List<AssetCategory>>> entries =
			assetCategoriesMap.entrySet();

		Stream<Map.Entry<Long, List<AssetCategory>>> entryStream =
			entries.stream();

		_categories = entryStream.collect(
			Collectors.toMap(
				Map.Entry::getKey,
				entry -> _toAssetCategoryModel(entry.getValue())));

		_vocabularies = _toAssetVocabularyModel(assetVocabularies);

		_status = "success";
	}

	public BulkAssetEntryCommonCategoriesModel(Throwable throwable) {
		_description = throwable.getMessage();
		_status = "error";
		_categories = null;
	}

	public Map<Long, List<AssetCategoryModel>> getCategories() {
		return _categories;
	}

	public String getDescription() {
		return _description;
	}

	public String getStatus() {
		return _status;
	}

	public List<AssetVocabularyModel> getVocabularies() {
		return _vocabularies;
	}

	public void setCategories(Map<Long, List<AssetCategoryModel>> categories) {
		_categories = categories;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public void setVocabularies(List<AssetVocabularyModel> vocabularies) {
		_vocabularies = vocabularies;
	}

	public class AssetCategoryModel {

		public AssetCategoryModel(long categoryId, String name) {
			_categoryId = categoryId;
			_name = name;
		}

		public long getCategoryId() {
			return _categoryId;
		}

		public String getName() {
			return _name;
		}

		public void setCategoryId(long categoryId) {
			_categoryId = categoryId;
		}

		public void setName(String name) {
			_name = name;
		}

		private long _categoryId;
		private String _name;

	}

	public class AssetVocabularyModel {

		public AssetVocabularyModel(long vocabularyId, String name) {
			_vocabularyId = vocabularyId;
			_name = name;
		}

		public String getName() {
			return _name;
		}

		public long getVocabularyId() {
			return _vocabularyId;
		}

		public void setName(String name) {
			_name = name;
		}

		public void setVocabularyId(long vocabularyId) {
			_vocabularyId = vocabularyId;
		}

		private String _name;
		private long _vocabularyId;

	}

	private List<AssetCategoryModel> _toAssetCategoryModel(
		List<AssetCategory> assetCategories) {

		Stream<AssetCategory> assetCategoryStream = assetCategories.stream();

		return assetCategoryStream.map(
			assetCategory -> new AssetCategoryModel(
				assetCategory.getCategoryId(), assetCategory.getName())
		).collect(
			Collectors.toList()
		);
	}

	private List<AssetVocabularyModel> _toAssetVocabularyModel(
		List<AssetVocabulary> assetVocabularies) {

		Stream<AssetVocabulary> assetVocabularyStream =
			assetVocabularies.stream();

		return assetVocabularyStream.map(
			assetVocabulary -> new AssetVocabularyModel(
				assetVocabulary.getVocabularyId(), assetVocabulary.getName())
		).collect(
			Collectors.toList()
		);
	}

	private Map<Long, List<AssetCategoryModel>> _categories;
	private String _description;
	private String _status;
	private List<AssetVocabularyModel> _vocabularies;

}