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

package com.liferay.commerce.product.demo.data.creator.internal;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.commerce.product.demo.data.creator.CPDemoDataCreator;
import com.liferay.commerce.product.demo.data.creator.internal.util.AssetCategoryDemoDataCreatorHelper;
import com.liferay.commerce.product.demo.data.creator.internal.util.AssetVocabularyDemoDataCreatorHelper;
import com.liferay.commerce.product.demo.data.creator.internal.util.CPDefinitionDemoDataCreatorHelper;
import com.liferay.commerce.product.demo.data.creator.internal.util.CPInstanceDemoDataCreatorHelper;
import com.liferay.commerce.product.demo.data.creator.internal.util.CPOptionDemoDataCreatorHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.io.IOException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CPDemoDataCreator.class)
public class CPDemoDataCreatorImpl implements CPDemoDataCreator {

	@Override
	public void create(long userId, long groupId, boolean buildSkus)
		throws IOException, PortalException {

		AssetVocabulary assetVocabulary =
			_assetVocabularyDemoDataCreatorHelper.createAssetVocabulary(
				userId, groupId, "Commerce");

		long vocabularyId = assetVocabulary.getVocabularyId();

		JSONArray catalog = _cpDefinitionDemoDataCreatorHelper.getCatalog();

		for (int i = 0; i < catalog.length(); i++) {
			JSONObject product = catalog.getJSONObject(i);

			String baseSKU = product.getString("baseSKU");
			String name = product.getString("name");
			String title = product.getString("title");
			String description = product.getString("description");
			String productTypeName = product.getString("productTypeName");

			Map<Locale, String> titleMap = new HashMap<>();
			Map<Locale, String> descriptionMap = new HashMap<>();

			titleMap.put(Locale.US, title);
			descriptionMap.put(Locale.US, description);

			JSONArray categories = product.getJSONArray("categories");

			long[] assetCategoryIds =
				_assetCategoryDemoDataCreatorHelper.getAssetCategoryIds(
					userId, groupId, vocabularyId, categories);

			CPDefinition cpDefinition =
				_cpDefinitionDemoDataCreatorHelper.createCPDefinition(
					userId, groupId, baseSKU, name, titleMap, descriptionMap,
					productTypeName, assetCategoryIds);

			long cpDefinitionId = cpDefinition.getCPDefinitionId();

			JSONArray options = product.getJSONArray("options");

			_cpOptionDemoDataCreatorHelper.addCPOptions(
				Locale.US, userId, groupId, cpDefinitionId, options);

			if (buildSkus) {
				_cpInstanceDemoDataCreatorHelper.createCPInstance(
					userId, groupId, cpDefinitionId);
			}
		}
	}

	@Override
	public void delete() throws PortalException {
		_cpDefinitionDemoDataCreatorHelper.deleteCPDefinitions();
		_cpOptionDemoDataCreatorHelper.deleteCPOptions();
		_assetVocabularyDemoDataCreatorHelper.deleteVocabularies();
	}

	@Override
	public void init() {
		_assetCategoryDemoDataCreatorHelper.init();
		_cpOptionDemoDataCreatorHelper.init();
	}

	@Reference
	private AssetCategoryDemoDataCreatorHelper
		_assetCategoryDemoDataCreatorHelper;

	@Reference
	private AssetVocabularyDemoDataCreatorHelper
		_assetVocabularyDemoDataCreatorHelper;

	@Reference
	private CPDefinitionDemoDataCreatorHelper
		_cpDefinitionDemoDataCreatorHelper;

	@Reference
	private CPInstanceDemoDataCreatorHelper _cpInstanceDemoDataCreatorHelper;

	@Reference
	private CPOptionDemoDataCreatorHelper _cpOptionDemoDataCreatorHelper;

}