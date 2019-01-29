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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adolfo Pérez
 */
@XmlRootElement
public class BulkAssetEntryCommonCategoriesModel {

	public BulkAssetEntryCommonCategoriesModel() {
	}

	public BulkAssetEntryCommonCategoriesModel(
		String description, List<AssetCategory> assetCategories) {

		_description = description;

		_categories = new ArrayList<>();

		for (AssetCategory assetCategory : assetCategories) {
			_categories.add(
				new AssetCategoryModel(
					assetCategory.getCategoryId(), assetCategory.getName()));
		}

		_status = "success";
	}

	public BulkAssetEntryCommonCategoriesModel(Throwable throwable) {
		_description = throwable.getMessage();
		_status = "error";
		_categories = null;
	}

	public List<AssetCategoryModel> getCategories() {
		return _categories;
	}

	public String getDescription() {
		return _description;
	}

	public String getStatus() {
		return _status;
	}

	public void setCategories(List<AssetCategoryModel> categories) {
		_categories = categories;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setStatus(String status) {
		_status = status;
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

	private List<AssetCategoryModel> _categories;
	private String _description;
	private String _status;

}