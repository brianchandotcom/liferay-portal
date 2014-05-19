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

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portlet.asset.model.AssetCategoryConstants;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author José Manuel Navarro
 */
public class AssetVocabularySettingsPropertiesTest {

	@Test
	public void testIsAssociatedToAssetWhenAssociatedToAll() {
		_settingProperties = getSettingProperties(true, 0, 0, true);

		Assert.assertTrue(_settingProperties.isAssociatedToAsset(1, 0));
		Assert.assertTrue(_settingProperties.isAssociatedToAsset(1, 1));
	}

	@Test
	public void testIsAssociatedToAssetWhenAssociatedToClassName() {

		_settingProperties = getSettingProperties(
			true, 1, AssetCategoryConstants.ALL_CLASS_TYPE_IDS, true);

		Assert.assertTrue(_settingProperties.isAssociatedToAsset(1, 0));
		Assert.assertTrue(_settingProperties.isAssociatedToAsset(1, 1));

		Assert.assertFalse(_settingProperties.isAssociatedToAsset(2, 0));
		Assert.assertFalse(_settingProperties.isAssociatedToAsset(2, 2));
	}

	@Test
	public void testIsAssociatedToAssetWhenAssociatedToClassNameAndClassType() {

		_settingProperties = getSettingProperties(true, 1, 1, true);

		Assert.assertTrue(_settingProperties.isAssociatedToAsset(1, 1));

		Assert.assertFalse(_settingProperties.isAssociatedToAsset(1, 0));
		Assert.assertFalse(_settingProperties.isAssociatedToAsset(1, 2));
		Assert.assertFalse(_settingProperties.isAssociatedToAsset(2, 0));
		Assert.assertFalse(_settingProperties.isAssociatedToAsset(2, 2));
	}

	@Test
	public void testIsMultiValuedWhenFalse() {
		_settingProperties = getSettingProperties(false, 1, 1, true);

		Assert.assertFalse(_settingProperties.isMultiValued());
	}

	@Test
	public void testIsMultiValuedWhenTrue() {
		_settingProperties = getSettingProperties(true, 1, 1, true);

		Assert.assertTrue(_settingProperties.isMultiValued());
	}

	@Test
	public void testIsRequiredReturnsFalseWhenAllIsNotRequired() {
		_settingProperties = getSettingProperties(true, 0, 0, false);

		Assert.assertFalse(_settingProperties.isRequired(1));
		Assert.assertFalse(_settingProperties.isRequired(2));
		Assert.assertFalse(_settingProperties.isRequired(1, 1));
		Assert.assertFalse(_settingProperties.isRequired(2, 1));
	}

	@Test
	public void testIsRequiredReturnsFalseWhenClassNameAndTypeIsNotRequired() {
		_settingProperties = getSettingProperties(true, 1, 1, false);

		Assert.assertFalse(_settingProperties.isRequired(1));
		Assert.assertFalse(_settingProperties.isRequired(2));
		Assert.assertFalse(_settingProperties.isRequired(1, 1));
		Assert.assertFalse(_settingProperties.isRequired(2, 1));
	}

	@Test
	public void testIsRequiredReturnsFalseWhenClassNameIsNotRequired() {
		_settingProperties = getSettingProperties(
			true, 1, AssetCategoryConstants.ALL_CLASS_TYPE_IDS, false);

		Assert.assertFalse(_settingProperties.isRequired(1));
		Assert.assertFalse(_settingProperties.isRequired(2));
		Assert.assertFalse(_settingProperties.isRequired(1, 0));
		Assert.assertFalse(_settingProperties.isRequired(2, 0));
	}

	@Test
	public void testIsRequiredReturnsTrueWhenAllIsRequired() {
		_settingProperties = getSettingProperties(true, 0, 0, true);

		Assert.assertTrue(_settingProperties.isRequired(1));
		Assert.assertTrue(_settingProperties.isRequired(2));
		Assert.assertTrue(_settingProperties.isRequired(1, 1));
		Assert.assertTrue(_settingProperties.isRequired(2, 1));
	}

	@Test
	public void testIsRequiredReturnsTrueWhenClassNameAndTypeIsRequired() {
		_settingProperties = getSettingProperties(true, 1, 1, true);

		Assert.assertTrue(_settingProperties.isRequired(1));
		Assert.assertTrue(_settingProperties.isRequired(1, 1));
		Assert.assertFalse(_settingProperties.isRequired(2));
		Assert.assertFalse(_settingProperties.isRequired(2, 1));
	}

	@Test
	public void testIsRequiredReturnsTrueWhenClassNameIsRequired() {
		_settingProperties = getSettingProperties(
			true, 1, AssetCategoryConstants.ALL_CLASS_TYPE_IDS, true);

		Assert.assertTrue(_settingProperties.isRequired(1));
		Assert.assertTrue(_settingProperties.isRequired(1, 1));
		Assert.assertFalse(_settingProperties.isRequired(2));
		Assert.assertFalse(_settingProperties.isRequired(2, 1));
	}

	protected AssetVocabularySettingsProperties getSettingProperties(
			boolean multiValued, List<Long> classNames, List<Long> classTypes,
			List<Boolean> assetsRequired) {

		AssetVocabularySettingsProperties settingsProperties =
			new AssetVocabularySettingsProperties();

		settingsProperties.setMultiValued(multiValued);

		settingsProperties.addAssociatedAssets(
			classNames, classTypes, assetsRequired);

		return settingsProperties;
	}

	protected AssetVocabularySettingsProperties getSettingProperties(
			boolean multiValued, long assetClassName, long assetClassType,
			boolean assetRequired) {

		List<Long> classNames = ListUtil.toList(new long[]{assetClassName});
		List<Long> classTypes = ListUtil.toList(new long[]{assetClassType});
		List<Boolean> required = ListUtil.toList(new boolean[]{assetRequired});

		return getSettingProperties(
			multiValued, classNames, classTypes, required);
	}

	private AssetVocabularySettingsProperties _settingProperties;

}