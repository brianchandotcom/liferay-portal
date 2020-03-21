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