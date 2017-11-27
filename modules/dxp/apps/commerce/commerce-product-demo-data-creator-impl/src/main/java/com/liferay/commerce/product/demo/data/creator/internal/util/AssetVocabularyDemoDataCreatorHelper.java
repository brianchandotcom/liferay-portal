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

package com.liferay.commerce.product.demo.data.creator.internal.util;

import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = AssetVocabularyDemoDataCreatorHelper.class)
public class AssetVocabularyDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public void addAssetVocabularies(long userId, long groupId)
		throws Exception {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		// Asset vocabularies

		JSONArray assetVocabulariesJSONArray = getAssetVocabulariesJSONArray();

		for (int i = 0; i < assetVocabulariesJSONArray.length(); i++) {
			JSONObject assetVocabularyJSONObject =
				assetVocabulariesJSONArray.getJSONObject(i);

			createAssetVocabulary(assetVocabularyJSONObject, serviceContext);
		}
	}

	public AssetVocabulary createAssetVocabulary(
			JSONObject assetVocabularyJSONObject, ServiceContext serviceContext)
		throws Exception {

		String title = assetVocabularyJSONObject.getString("vocabulary");

		AssetVocabulary assetVocabulary = _assetVocabularies.get(title);

		if (assetVocabulary != null) {
			return assetVocabulary;
		}

		assetVocabulary = _assetVocabularyLocalService.fetchGroupVocabulary(
			serviceContext.getScopeGroupId(), title);

		if (assetVocabulary != null) {
			_assetVocabularies.put(title, assetVocabulary);

			return assetVocabulary;
		}

		assetVocabulary = _assetVocabularyLocalService.addVocabulary(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), title,
			serviceContext);

		_assetVocabularies.put(title, assetVocabulary);

		JSONArray categoriesJSONArray = assetVocabularyJSONObject.getJSONArray(
			"categories");

		_assetCategoryDemoDataCreatorHelper.addAssetCategories(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			assetVocabulary.getVocabularyId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			categoriesJSONArray);

		return assetVocabulary;
	}

	public void deleteAssetVocabularies() throws PortalException {
		Set<Map.Entry<String, AssetVocabulary>> entrySet =
			_assetVocabularies.entrySet();

		Iterator<Map.Entry<String, AssetVocabulary>> iterator =
			entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, AssetVocabulary> entry = iterator.next();

			_assetVocabularyLocalService.deleteVocabulary(entry.getValue());

			iterator.remove();
		}
	}

	public void init() {
		_assetVocabularies = new HashMap<>();
	}

	@Activate
	protected void activate() {
		init();
	}

	@Deactivate
	protected void deactivate() {
		_assetVocabularies = null;
	}

	protected JSONArray getAssetVocabulariesJSONArray() throws Exception {
		Class<?> clazz = getClass();

		String assetVocabulariesPath =
			"com/liferay/commerce/product/demo/data/creator/internal" +
				"/dependencies/categories.json";

		String assetVocabulariesJSON = StringUtil.read(
			clazz.getClassLoader(), assetVocabulariesPath, false);

		return JSONFactoryUtil.createJSONArray(assetVocabulariesJSON);
	}

	@Reference
	private AssetCategoryDemoDataCreatorHelper
		_assetCategoryDemoDataCreatorHelper;

	private Map<String, AssetVocabulary> _assetVocabularies;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}