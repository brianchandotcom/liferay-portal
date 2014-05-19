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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.util.AssetVocabularySettingsProperties;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
public class AssetVocabularyImpl extends AssetVocabularyBaseImpl {

	public AssetVocabularyImpl() {
		super();
	}

	@Override
	public String[] getAssociatedAssetClassNameIds() {
		return getSettingProperties().getAssociatedAssetIds();
	}

	@Override
	public String[] getAssociatedRequiredAssetClassNameIds() {
		return getSettingProperties().getAssociatedRequiredAssetIds();
	}

	@Override
	public List<AssetCategory> getCategories() throws SystemException {
		return AssetCategoryLocalServiceUtil.getVocabularyCategories(
			getVocabularyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	@Override
	public String getSettings() {
		if (_settingsProperties == null) {
			return super.getSettings();
		}
		else {
			return _settingsProperties.toString();
		}
	}

	@Override
	public String getTitle(String languageId) {
		String value = super.getTitle(languageId);

		if (Validator.isNull(value)) {
			value = getName();
		}

		return value;
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		String value = super.getTitle(languageId, useDefault);

		if (Validator.isNull(value)) {
			value = getName();
		}

		return value;
	}

	@Override
	public boolean isAssociatedToAsset(long assetClassNameId) {
		return getSettingProperties().isAssociatedToAsset(assetClassNameId);
	}

	@Override
	public boolean isAssociatedToAsset(
		long assetClassNameId, long assetClassTypeId) {

		return getSettingProperties().isAssociatedToAsset(
			assetClassNameId, assetClassTypeId);
	}

	@Override
	public boolean isDuplicatedCategory(final long[] selectedCategoryIds)
		throws SystemException {

		PredicateFilter<AssetCategory> existingCategoryFilter =
			new PredicateFilter<AssetCategory>() {

			@Override
			public boolean filter(AssetCategory assetCategory) {
				if (ArrayUtil.contains(
					selectedCategoryIds, assetCategory.getCategoryId())) {

					return true;
				}

				return false;
			}

		};

		if (ListUtil.count(getCategories(), existingCategoryFilter) > 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMissingRequiredCategory(
			long classNameId, long classTypeId,
			final long[] selectedCategoryIds)
		throws SystemException {

		if (isRequired(classNameId, classTypeId)) {
			List<AssetCategory> categories = getCategories();

			if (ListUtil.isEmpty(categories)) {
				return false;
			}

			PredicateFilter<AssetCategory> categoryFilter =
				new PredicateFilter<AssetCategory>() {

				@Override
				public boolean filter(AssetCategory assetCategory) {
					if (ArrayUtil.contains(
						selectedCategoryIds, assetCategory.getCategoryId())) {

						return true;
					}

					return false;
				}

			};

			if (!ListUtil.exists(categories, categoryFilter)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isMultiValued() {
		return getSettingProperties().isMultiValued();
	}

	@Override
	public boolean isRequired(long classNameId) {
		return getSettingProperties().isRequired(classNameId);
	}

	@Override
	public boolean isRequired(long classNameId, long classTypeId) {
		return getSettingProperties().isRequired(classNameId, classTypeId);
	}

	@Override
	public void setSettings(String settings) {
		_settingsProperties = null;

		super.setSettings(settings);
	}

	protected AssetVocabularySettingsProperties getSettingProperties() {
		if (_settingsProperties == null) {
			_settingsProperties = new AssetVocabularySettingsProperties();
			_settingsProperties.fastLoad(super.getSettings());
		}

		return _settingsProperties;
	}

	private AssetVocabularySettingsProperties _settingsProperties;

}