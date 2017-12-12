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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = AssetCategoryDemoDataCreatorHelper.class)
public class AssetCategoryDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public void addAssetCategories(
			long userId, long groupId, long vocabularyId, long parentCategoryId,
			JSONArray jsonArray)
		throws Exception {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject categoryJSONObject = jsonArray.getJSONObject(i);

			AssetCategory assetCategory = getAssetCategory(
				userId, groupId, parentCategoryId, vocabularyId,
				categoryJSONObject);

			JSONArray categoriesJSONArray = categoryJSONObject.getJSONArray(
				"categories");

			if ((categoriesJSONArray != null) &&
				(categoriesJSONArray.length() > 0)) {

				addAssetCategories(
					userId, groupId, vocabularyId,
					assetCategory.getCategoryId(), categoriesJSONArray);
			}
		}
	}

	public long[] getProductAssetCategoryIds(JSONArray jsonArray) {
		List<Long> productAssetCategoryIds = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			String categoryName = jsonArray.getString(i);

			AssetCategory assetCategory = _assetCategories.get(categoryName);

			productAssetCategoryIds.add(assetCategory.getCategoryId());
		}

		return ArrayUtil.toLongArray(productAssetCategoryIds);
	}

	public void init() {
		_assetCategories = new ConcurrentHashMap<>();
	}

	@Activate
	protected void activate() {
		init();
	}

	protected AssetCategory getAssetCategory(
			long userId, long groupId, long parentCategoryId, long vocabularyId,
			JSONObject categoryJSONObject)
		throws Exception {

		String key = categoryJSONObject.getString("key");
		String title = categoryJSONObject.getString("title");

		AssetCategory assetCategory = _assetCategories.get(key);

		if (assetCategory != null) {
			return assetCategory;
		}

		assetCategory = _assetCategoryLocalService.fetchCategory(
			groupId, parentCategoryId, title, vocabularyId);

		if (assetCategory != null) {
			_assetCategories.put(key, assetCategory);

			return assetCategory;
		}

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		Map<Locale, String> titleMap = Collections.singletonMap(
			Locale.US, title);

		assetCategory = _assetCategoryLocalService.addCategory(
			userId, groupId, parentCategoryId, titleMap, null, vocabularyId,
			new String[0], serviceContext);

		_assetCategories.put(key, assetCategory);

		titleMap = _getUniqueUrlTitles(assetCategory);

		_cpFriendlyURLEntryLocalService.addCPFriendlyURLEntries(
			groupId, serviceContext.getCompanyId(), AssetCategory.class,
			assetCategory.getCategoryId(), titleMap);

		JSONArray imagesJSONArray = categoryJSONObject.getJSONArray("images");

		_cpAttachmentFileEntryDemoDataCreatorHelper.
			addAssetCategoryAttachmentFileEntries(
				userId, groupId, assetCategory.getCategoryId(),
				imagesJSONArray);

		return assetCategory;
	}

	private Map<Locale, String> _getUniqueUrlTitles(AssetCategory assetCategory)
		throws PortalException {

		Map<Locale, String> urlTitleMap = new HashMap<>();

		Map<Locale, String> titleMap = assetCategory.getTitleMap();

		long classNameId = _classNameLocalService.getClassNameId(
			AssetCategory.class);

		for (Map.Entry<Locale, String> titleEntry : titleMap.entrySet()) {
			String languageId = "en_US";

			String urlTitle = _cpFriendlyURLEntryLocalService.buildUrlTitle(
				assetCategory.getGroupId(), classNameId,
				assetCategory.getCategoryId(), languageId,
				titleEntry.getValue());

			urlTitleMap.put(titleEntry.getKey(), urlTitle);
		}

		return urlTitleMap;
	}

	private Map<String, AssetCategory> _assetCategories;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CPAttachmentFileEntryDemoDataCreatorHelper
		_cpAttachmentFileEntryDemoDataCreatorHelper;

	@Reference
	private CPFriendlyURLEntryLocalService _cpFriendlyURLEntryLocalService;

}