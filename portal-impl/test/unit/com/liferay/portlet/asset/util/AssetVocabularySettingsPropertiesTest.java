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

package unit.com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.util.AssetVocabularySettingsProperties;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author José Manuel Navarro
 */
public class AssetVocabularySettingsPropertiesTest {

	@Test
	public void testIsAssociatedToAssetWhenAssociatedToAll() {
		_settingProperties = getSettingProperties(true, 0L, 0L, true);

		Assert.assertTrue(_settingProperties.isAssociatedToAsset(1L, 0L));
		Assert.assertTrue(_settingProperties.isAssociatedToAsset(1L, 1L));
	}

	@Test
	public void testIsAssociatedToAssetWhenAssociatedToClassName() {

		_settingProperties = getSettingProperties(true, 1L, 0L, true);

		Assert.assertTrue(_settingProperties.isAssociatedToAsset(1L, 0L));
		Assert.assertTrue(_settingProperties.isAssociatedToAsset(1L, 1L));

		Assert.assertFalse(_settingProperties.isAssociatedToAsset(2L, 0L));
		Assert.assertFalse(_settingProperties.isAssociatedToAsset(2L, 2L));
	}

	@Test
	public void testIsAssociatedToAssetWhenAssociatedToClassNameAndClassType() {

		_settingProperties = getSettingProperties(true, 1L, 1L, true);

		Assert.assertTrue(_settingProperties.isAssociatedToAsset(1L, 1L));

		Assert.assertFalse(_settingProperties.isAssociatedToAsset(1L, 0L));
		Assert.assertFalse(_settingProperties.isAssociatedToAsset(1L, 2L));
		Assert.assertFalse(_settingProperties.isAssociatedToAsset(2L, 0L));
		Assert.assertFalse(_settingProperties.isAssociatedToAsset(2L, 2L));
	}

	@Test
	public void testIsMultiValuedWhenTrue() {
		_settingProperties = getSettingProperties(true, 1L, 1L, true);

		Assert.assertTrue(_settingProperties.isMultiValued());
	}

	@Test
	public void testIsMultiValuedWhenFalse() {
		_settingProperties = getSettingProperties(false, 1L, 1L, true);

		Assert.assertFalse(_settingProperties.isMultiValued());
	}

	@Test
	public void testIsRequiredReturnsFalseWhenAllIsNotRequired() {
		_settingProperties = getSettingProperties(true, 0L, 0L, false);

		Assert.assertFalse(_settingProperties.isRequired(1L));
		Assert.assertFalse(_settingProperties.isRequired(2L));
		Assert.assertFalse(_settingProperties.isRequired(1L, 1L));
		Assert.assertFalse(_settingProperties.isRequired(2L, 1L));
	}

	@Test
	public void testIsRequiredReturnsFalseWhenClassNameAndTypeIsNotRequired() {
		_settingProperties = getSettingProperties(true, 1L, 1L, false);

		Assert.assertFalse(_settingProperties.isRequired(1L));
		Assert.assertFalse(_settingProperties.isRequired(2L));
		Assert.assertFalse(_settingProperties.isRequired(1L, 1L));
		Assert.assertFalse(_settingProperties.isRequired(2L, 1L));
	}

	@Test
	public void testIsRequiredReturnsFalseWhenClassNameIsNotRequired() {
		_settingProperties = getSettingProperties(true, 1L, 0L, false);

		Assert.assertFalse(_settingProperties.isRequired(1L));
		Assert.assertFalse(_settingProperties.isRequired(2L));
		Assert.assertFalse(_settingProperties.isRequired(1L, 0L));
		Assert.assertFalse(_settingProperties.isRequired(2L, 0L));
	}

	@Test
	public void testIsRequiredReturnsTrueWhenAllIsRequired() {
		_settingProperties = getSettingProperties(true, 0L, 0L, true);

		Assert.assertTrue(_settingProperties.isRequired(1L));
		Assert.assertTrue(_settingProperties.isRequired(2L));
		Assert.assertTrue(_settingProperties.isRequired(1L, 1L));
		Assert.assertTrue(_settingProperties.isRequired(2L, 1L));
	}

	@Test
	public void testIsRequiredReturnsTrueWhenClassNameAndTypeIsRequired() {
		_settingProperties = getSettingProperties(true, 1L, 1L, true);

		Assert.assertTrue(_settingProperties.isRequired(1L));
		Assert.assertTrue(_settingProperties.isRequired(1L, 1L));
		Assert.assertFalse(_settingProperties.isRequired(2L));
		Assert.assertFalse(_settingProperties.isRequired(2L, 1L));
	}

	@Test
	public void testIsRequiredReturnsTrueWhenClassNameIsRequired() {
		_settingProperties = getSettingProperties(true, 1L, 0L, true);

		Assert.assertTrue(_settingProperties.isRequired(1L));
		Assert.assertTrue(_settingProperties.isRequired(1L, 1L));
		Assert.assertFalse(_settingProperties.isRequired(2L));
		Assert.assertFalse(_settingProperties.isRequired(2L, 1L));
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

	private AssetVocabularySettingsProperties _settingProperties;

}
