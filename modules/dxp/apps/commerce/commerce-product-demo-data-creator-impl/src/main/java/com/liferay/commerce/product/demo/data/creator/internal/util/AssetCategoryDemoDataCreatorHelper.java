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
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
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
			long userId, long groupId, long assetCategoryId, long vocabularyId,
			String layoutUuid, JSONArray jsonArray)
		throws PortalException {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject categoryJSONObject = jsonArray.getJSONObject(i);

			AssetCategory assetCategory = getAssetCategory(
				userId, groupId, assetCategoryId, vocabularyId, layoutUuid,
				categoryJSONObject);

			long parentCategoryId = assetCategory.getCategoryId();

			addSubcategories(
				userId, groupId, parentCategoryId, vocabularyId, layoutUuid,
				categoryJSONObject);
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

	protected void addSubcategories(
			long userId, long groupId, long parentCategoryId, long vocabularyId,
			String layoutUuid, JSONObject jsonObject)
		throws PortalException {

		JSONArray jsonArray = jsonObject.getJSONArray("subcategories");

		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject categoryJSONObject = jsonArray.getJSONObject(i);

				AssetCategory assetCategory = getAssetCategory(
					userId, groupId, parentCategoryId, vocabularyId, layoutUuid,
					categoryJSONObject);

				long subcategoryId = assetCategory.getCategoryId();

				addSubcategories(
					userId, groupId, subcategoryId, vocabularyId, layoutUuid,
					categoryJSONObject);
			}
		}
	}

	protected AssetCategory getAssetCategory(
			long userId, long groupId, long parentCategoryId, long vocabularyId,
			String layoutUuid, JSONObject jsonObject)
		throws PortalException {

		String path = jsonObject.getString("path");
		String title = jsonObject.getString("title");

		AssetCategory assetCategory = _assetCategories.get(path);

		if (assetCategory != null) {
			return assetCategory;
		}

		assetCategory = _assetCategoryLocalService.fetchCategory(
			groupId, parentCategoryId, title, vocabularyId);

		if (assetCategory != null) {
			_assetCategories.put(path, assetCategory);

			return assetCategory;
		}

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		Map<Locale, String> titleMap = Collections.singletonMap(
			Locale.US, title);

		assetCategory = _assetCategoryLocalService.addCategory(
			userId, groupId, parentCategoryId, titleMap, null, vocabularyId,
			null, serviceContext);

		titleMap = _getUniqueUrlTitles(assetCategory);

		_cpFriendlyURLEntryLocalService.addCPFriendlyURLEntries(
			groupId, serviceContext.getCompanyId(), AssetCategory.class,
			assetCategory.getCategoryId(), titleMap);

		_cpDisplayLayoutLocalService.addCPDisplayLayout(
			AssetCategory.class, assetCategory.getCategoryId(), layoutUuid,
			serviceContext);

		_assetCategories.put(path, assetCategory);

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
				assetCategory.getGroupId(), assetCategory.getCompanyId(),
				classNameId, assetCategory.getCategoryId(), languageId,
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
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

	@Reference
	private CPFriendlyURLEntryLocalService _cpFriendlyURLEntryLocalService;

}