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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.PrefixPredicateFilter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;


import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
public class AssetVocabularyImpl extends AssetVocabularyBaseImpl {

	public AssetVocabularyImpl() {
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
	public UnicodeProperties getSettingsProperties() {
		if (_settingsProperties == null) {
			_settingsProperties = new UnicodeProperties(true);

			_settingsProperties.fastLoad(super.getSettings());
		}

		return _settingsProperties;
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
		return _isSettingAssociatedToAsset(
			"selectedClassNameIds", assetClassNameId);
	}

	@Override
	public boolean isAssociatedToAsset(
		long assetClassNameId, long assetClassTypeId) {

		return _isSettingAssociatedToAsset(
			"selectedClassNameIds", assetClassNameId, assetClassTypeId);
	}

	@Override
	public boolean isDuplicatedCategory(final long[] selectedCategoryIds)
		throws SystemException {

		PredicateFilter<AssetCategory> existingCategoryFilter =
			new PredicateFilter<AssetCategory>() {
				@Override
				public boolean filter(AssetCategory assetCategory) {
					return ArrayUtil.contains(
						selectedCategoryIds, assetCategory.getCategoryId());
				}
			};

		if (ListUtil.count(getCategories(), existingCategoryFilter) > 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMultiValued() {
		if (_settingsProperties == null) {
			_settingsProperties = getSettingsProperties();
		}

		return GetterUtil.getBoolean(
			_settingsProperties.getProperty("multiValued"), true);
	}

	@Override
	public boolean isMissingRequiredCategory(
			long assetClassNameId, long assetClassTypeId,
			final long[] selectedCategoryIds)
		throws SystemException {

		if (_isSettingAssociatedToAsset(
			"requiredClassNameIds", assetClassNameId, assetClassTypeId)) {

			List<AssetCategory> categories = getCategories();
			if (ListUtil.isEmpty(categories)) {

				return false;
			}

			PredicateFilter<AssetCategory> categoryFilter =
				new PredicateFilter<AssetCategory>() {
					@Override
					public boolean filter(AssetCategory assetCategory) {
						return ArrayUtil.contains(
							selectedCategoryIds, assetCategory.getCategoryId());
					}
				};

			if (!ListUtil.exists(categories, categoryFilter)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isRequired(long classNameId) {
		return _isSettingAssociatedToAsset("requiredClassNameIds", classNameId);
	}

	@Override
	public void setSettings(String settings) {
		_settingsProperties = null;

		super.setSettings(settings);
	}

	@Override
	public void setSettingsProperties(UnicodeProperties settingsProperties) {
		_settingsProperties = settingsProperties;

		super.setSettings(settingsProperties.toString());
	}

	private boolean _isSettingAssociatedToAsset(
		String settingName, long assetClassNameId) {

		if (_settingsProperties == null) {
			_settingsProperties = getSettingsProperties();
		}

		String[] settingValueIds = StringUtil.split(
			_settingsProperties.getProperty(settingName),
			StringPool.COMMA);

		if (settingValueIds.length == 0) {
			return false;
		}

		if (ArrayUtil.contains(
			settingValueIds, AssetCategoryConstants.ALL_CLASS_NAME_AND_TYPE_IDS)) {

			return true;
		}

		StringBundler sb = new StringBundler(2);
		sb.append(assetClassNameId);
		sb.append(StringPool.COLON);
		String classNamePrefix = sb.toString();

		if (ArrayUtil.exists(settingValueIds,
			new PrefixPredicateFilter(classNamePrefix, true))) {

			return true;
		}

		return false;
	}

	private boolean _isSettingAssociatedToAsset(
		String settingName, long assetClassNameId, long assetClassTypeId) {

		if (_settingsProperties == null) {
			_settingsProperties = getSettingsProperties();
		}

		String[] settingAssetIds = StringUtil.split(
			_settingsProperties.getProperty(settingName), StringPool.COMMA);

		if (settingAssetIds.length == 0) {
			return false;
		}

		StringBundler sb = new StringBundler(3);
		sb.append(assetClassNameId);
		sb.append(StringPool.COLON);
		sb.append(assetClassTypeId);
		String classNameAndType = sb.toString();

		sb = new StringBundler(3);
		sb.append(assetClassNameId);
		sb.append(StringPool.COLON);
		sb.append(AssetCategoryConstants.ALL_CLASS_TYPE_IDS);
		String classNameOnly = sb.toString();

		String allClasses = AssetCategoryConstants.ALL_CLASS_NAME_AND_TYPE_IDS;

		if (ArrayUtil.contains(settingAssetIds, classNameAndType) ||
			ArrayUtil.contains(settingAssetIds, classNameOnly) ||
			ArrayUtil.contains(settingAssetIds, allClasses)) {

			return true;
		}

		return false;
	}


	private UnicodeProperties _settingsProperties;

}