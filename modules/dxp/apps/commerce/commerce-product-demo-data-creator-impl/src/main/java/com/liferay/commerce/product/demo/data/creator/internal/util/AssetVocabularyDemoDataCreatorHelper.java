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

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
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

		String layoutUuid = _layoutDemoDataCreatorHelper.getLayoutUuid(
			userId, groupId, "Categories");

		AssetVocabulary commerceAssetVocabulary = createAssetVocabulary(
			userId, groupId, "Commerce");

		AssetVocabulary manufacturersAssetVocabulary = createAssetVocabulary(
			userId, groupId, "Manufacturers");

		long commerceVocabularyId = commerceAssetVocabulary.getVocabularyId();
		long manufacturersVocabularyId =
			manufacturersAssetVocabulary.getVocabularyId();

		JSONArray assetCategoriesJSONArray = getAssetCategoriesJSONArray();

		for (int i = 0; i < assetCategoriesJSONArray.length(); i++) {
			JSONObject categoriesJSONObject =
				assetCategoriesJSONArray.getJSONObject(i);

			JSONArray categoriesJSONArray = categoriesJSONObject.getJSONArray(
				"categories");
			JSONArray manufacturersJSONArray =
				categoriesJSONObject.getJSONArray(
					"manufacturers");

			_assetCategoryDemoDataCreatorHelper.addAssetCategories(
				userId, groupId, 0, commerceVocabularyId, layoutUuid,
				categoriesJSONArray);

			_assetCategoryDemoDataCreatorHelper.addAssetCategories(
				userId, groupId, 0, manufacturersVocabularyId, layoutUuid,
				manufacturersJSONArray);
		}
	}

	public AssetVocabulary createAssetVocabulary(
			long userId, long groupId, String title)
		throws PortalException {

		AssetVocabulary assetVocabulary = _assetVocabularies.get(title);

		if (assetVocabulary != null) {
            return assetVocabulary;
		}

		assetVocabulary = _assetVocabularyLocalService.fetchGroupVocabulary(
			groupId, title);

		if (assetVocabulary != null) {
            _assetVocabularies.put(title, assetVocabulary);

            return assetVocabulary;
		}

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		assetVocabulary = _assetVocabularyLocalService.addVocabulary(
			userId, groupId, title, serviceContext);

		_assetVocabularies.put(title, assetVocabulary);

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

	protected JSONArray getAssetCategoriesJSONArray() throws Exception {
		Class<?> clazz = getClass();

		String assetCategoriesPath =
			"com/liferay/commerce/product/demo/data/creator/internal" +
				"/dependencies/categories.json";

		String assetCategoriesJSON = StringUtil.read(
			clazz.getClassLoader(), assetCategoriesPath, false);

		JSONArray assetCategoriesJSONArray = JSONFactoryUtil.createJSONArray(
			assetCategoriesJSON);

		return assetCategoriesJSONArray;
	}

	@Activate
	protected void activate() {
		init();
	}

	@Deactivate
	protected void deactivate() {
		_assetVocabularies = null;
	}

	private Map<String, AssetVocabulary> _assetVocabularies;

	@Reference
	private AssetCategoryDemoDataCreatorHelper
		_assetCategoryDemoDataCreatorHelper;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private LayoutDemoDataCreatorHelper _layoutDemoDataCreatorHelper;

}