/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.search.test;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyService;
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
public class AssetVocabularyFixture {

	public AssetVocabularyFixture(
		AssetVocabularyService assetVocabularyService, Group group) {

		_assetVocabularyService = assetVocabularyService;
		_group = group;
	}

	public AssetVocabulary createAssetVocabulary() throws Exception {
		return createAssetVocabulary(RandomTestUtil.randomString());
	}

	public AssetVocabulary createAssetVocabulary(
			LocalizedValuesMap titleMap, LocalizedValuesMap descriptionMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), getUserId());

		AssetVocabulary assetVocabulary = _assetVocabularyService.addVocabulary(
			serviceContext.getScopeGroupId(), null, titleMap.getValues(),
			descriptionMap.getValues(), "", serviceContext);

		_assetVocabularies.add(assetVocabulary);

		return assetVocabulary;
	}

	public AssetVocabulary createAssetVocabulary(String title)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), getUserId());

		AssetVocabulary assetVocabulary = _assetVocabularyService.addVocabulary(
			serviceContext.getScopeGroupId(), title, serviceContext);

		_assetVocabularies.add(assetVocabulary);

		return assetVocabulary;
	}

	public List<AssetVocabulary> getAssetVocabularies() {
		return _assetVocabularies;
	}

	public void updateDisplaySettings(Locale locale) throws Exception {
		Group group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, locale);

		_group.setModelAttributes(group.getModelAttributes());
	}

	protected long getUserId() throws Exception {
		return TestPropsValues.getUserId();
	}

	private final List<AssetVocabulary> _assetVocabularies = new ArrayList<>();
	private final AssetVocabularyService _assetVocabularyService;
	private final Group _group;

}