/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.search.test;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 * @author Luca Marques
 */
public class AssetCategoryFixture {

	public AssetCategoryFixture(
		AssetCategoryService assetCategoryService,
		AssetVocabularyFixture assetVocabularyFixture, Group group) {

		_assetCategoryService = assetCategoryService;
		_assetVocabularyFixture = assetVocabularyFixture;
		_group = group;
	}

	public AssetCategory createAssetCategory() throws Exception {
		return createAssetCategory(RandomTestUtil.randomString());
	}

	public AssetCategory createAssetCategory(
			LocalizedValuesMap titleMap, LocalizedValuesMap descriptionMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyFixture.createAssetVocabulary();

		AssetCategory assetCategory = _assetCategoryService.addCategory(
			serviceContext.getScopeGroupId(), 0L, titleMap.getValues(),
			descriptionMap.getValues(), assetVocabulary.getVocabularyId(),
			new String[0], serviceContext);

		_assetCategories.add(assetCategory);

		return assetCategory;
	}

	public AssetCategory createAssetCategory(String title) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyFixture.createAssetVocabulary();

		AssetCategory assetCategory = _assetCategoryService.addCategory(
			serviceContext.getScopeGroupId(), title,
			assetVocabulary.getVocabularyId(), serviceContext);

		_assetCategories.add(assetCategory);

		return assetCategory;
	}

	public List<AssetCategory> getAssetCategories() {
		return _assetCategories;
	}

	public void updateDisplaySettings(Locale locale) throws Exception {
		Group group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, locale);

		_group.setModelAttributes(group.getModelAttributes());
	}

	protected long getUserId() throws Exception {
		return TestPropsValues.getUserId();
	}

	private final List<AssetCategory> _assetCategories = new ArrayList<>();
	private final AssetCategoryService _assetCategoryService;
	private final AssetVocabularyFixture _assetVocabularyFixture;
	private final Group _group;

}