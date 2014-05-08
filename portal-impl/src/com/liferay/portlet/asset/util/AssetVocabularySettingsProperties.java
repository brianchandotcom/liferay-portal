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

package com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefixPredicateFilter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.asset.model.AssetCategoryConstants;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author José Manuel Navarro
 */
public class AssetVocabularySettingsProperties extends UnicodeProperties {

	public AssetVocabularySettingsProperties() {
		super(true);
	}

	public void addAssociatedAssets(
		List<Long> assetClassNameIds, List<Long> assetClassTypeIds,
		List<Boolean> areRequired) {

		Set<String> selectedClassNameIds = new LinkedHashSet<String>();
		Set<String> requiredClassNameIds = new LinkedHashSet<String>();

		for (int i = 0; i < assetClassNameIds.size(); ++i) {
			long classNameId = assetClassNameIds.get(i);
			long classTypeId = assetClassTypeIds.get(i);
			boolean required = areRequired.get(i);

			String classNameAndType =
				classNameId + StringPool.COLON + classTypeId;

			if (classNameId == AssetCategoryConstants.ALL_CLASS_NAME_IDS) {
				selectedClassNameIds.clear();
				selectedClassNameIds.add(classNameAndType);

				if (required) {
					requiredClassNameIds.clear();
					requiredClassNameIds.add(classNameAndType);
				}

				break;
			}
			else {
				selectedClassNameIds.add(classNameAndType);

				if (required) {
					requiredClassNameIds.add(classNameAndType);
				}
			}
		}

		setProperty(
			KEY_SELECTED_CLASSNAMES, StringUtil.merge(selectedClassNameIds));
		setProperty(
			KEY_REQUIRED_CLASSNAMES, StringUtil.merge(requiredClassNameIds));
	}

	public String[] getAssociatedAssetIds() {
		return StringUtil.split(
			getProperty(KEY_SELECTED_CLASSNAMES), StringPool.COMMA);
	}

	public String[] getAssociatedRequiredAssetIds() {
		return StringUtil.split(
			getProperty(KEY_REQUIRED_CLASSNAMES), StringPool.COMMA);
	}

	public boolean isAssociatedToAsset(long assetClassNameId) {
		return _isSettingAssociatedToAsset(
			KEY_SELECTED_CLASSNAMES, assetClassNameId);
	}

	public boolean isAssociatedToAsset(
		long assetClassNameId, long assetClassTypeId) {

		return _isSettingAssociatedToAsset(
			KEY_SELECTED_CLASSNAMES, assetClassNameId, assetClassTypeId);
	}

	public boolean isMultiValued() {
		return GetterUtil.getBoolean(getProperty(KEY_MULTIVALUED), true);
	}

	public boolean isRequired(long classNameId) {
		return _isSettingAssociatedToAsset(
			KEY_REQUIRED_CLASSNAMES, classNameId);
	}

	public boolean isRequired(long classNameId, long classTypeId) {
		return _isSettingAssociatedToAsset(
			KEY_REQUIRED_CLASSNAMES, classNameId, classTypeId);
	}

	public void setMultiValued(boolean multiValued) {
		setProperty(KEY_MULTIVALUED, String.valueOf(multiValued));
	}

	private boolean _isSettingAssociatedToAsset(
		String settingName, long assetClassNameId) {

		String[] settingValueIds = StringUtil.split(
			getProperty(settingName), StringPool.COMMA);

		if (settingValueIds.length == 0) {
			return false;
		}

		if (ArrayUtil.contains(
				settingValueIds,
				AssetCategoryConstants.ALL_CLASS_NAME_AND_TYPE_IDS)) {

			return true;
		}

		String classNamePrefix = assetClassNameId + StringPool.COLON;

		if (ArrayUtil.exists(
				settingValueIds,
				new PrefixPredicateFilter(classNamePrefix, true))) {

			return true;
		}

		return false;
	}

	private boolean _isSettingAssociatedToAsset(
		String settingName, long assetClassNameId, long assetClassTypeId) {

		String[] settingAssetIds = StringUtil.split(
			getProperty(settingName), StringPool.COMMA);

		if (settingAssetIds.length == 0) {
			return false;
		}

		String classNameAndType =
			assetClassNameId + StringPool.COLON + assetClassTypeId;

		String classNameOnly =
			assetClassNameId + StringPool.COLON +
				AssetCategoryConstants.ALL_CLASS_TYPE_IDS;

		String allClasses = AssetCategoryConstants.ALL_CLASS_NAME_AND_TYPE_IDS;

		if (ArrayUtil.contains(settingAssetIds, classNameAndType) ||
			ArrayUtil.contains(settingAssetIds, classNameOnly) ||
			ArrayUtil.contains(settingAssetIds, allClasses)) {

			return true;
		}

		return false;
	}

	private static final String KEY_MULTIVALUED = "multiValued";

	private static final String KEY_REQUIRED_CLASSNAMES =
		"requiredClassNameIds";

	private static final String KEY_SELECTED_CLASSNAMES =
		"selectedClassNameIds";

}