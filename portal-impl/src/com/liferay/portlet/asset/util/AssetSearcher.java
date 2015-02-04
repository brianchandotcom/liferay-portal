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

import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class AssetSearcher extends BaseSearcher {

	public static Indexer getInstance() {
		return new AssetSearcher();
	}

	public AssetSearcher() {
		setDefaultSelectedFieldNames(
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String[] getSearchClassNames() {
		long[] classNameIds = _assetEntryQuery.getClassNameIds();

		String[] classNames = new String[classNameIds.length];

		for (int i = 0; i < classNames.length; i++) {
			long classNameId = classNameIds[i];

			classNames[i] = PortalUtil.getClassName(classNameId);
		}

		return classNames;
	}

	public void setAssetEntryQuery(AssetEntryQuery assetEntryQuery) {
		_assetEntryQuery = assetEntryQuery;
	}

	protected void addImpossibleTerm(BooleanQuery contextQuery, String field)
		throws Exception {

		contextQuery.addTerm(field, "-1", false, BooleanClauseOccur.MUST);
	}

	protected void addSearchAllCategories(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		long[] allCategoryIds = _assetEntryQuery.getAllCategoryIds();

		if (allCategoryIds.length == 0) {
			return;
		}

		long[] filteredAllCategoryIds = AssetUtil.filterCategoryIds(
			permissionChecker, allCategoryIds);

		if (allCategoryIds.length != filteredAllCategoryIds.length) {
			addImpossibleTerm(contextQuery, Field.ASSET_CATEGORY_IDS);

			return;
		}

		BooleanQuery categoryIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long allCategoryId : filteredAllCategoryIds) {
			AssetCategory assetCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(allCategoryId);

			if (assetCategory == null) {
				continue;
			}

			List<Long> categoryIds = new ArrayList<>();

			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				categoryIds.addAll(
					AssetCategoryLocalServiceUtil.getSubcategoryIds(
						allCategoryId));
			}

			if (categoryIds.isEmpty()) {
				categoryIds.add(allCategoryId);
			}

			BooleanQuery categoryIdQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long categoryId : categoryIds) {
				categoryIdQuery.addTerm(Field.ASSET_CATEGORY_IDS, categoryId);
			}

			categoryIdsQuery.add(categoryIdQuery, BooleanClauseOccur.MUST);
		}

		contextQuery.add(categoryIdsQuery, BooleanClauseOccur.MUST);
	}

	protected void addSearchAllTags(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		long[][] allTagIdsArray = _assetEntryQuery.getAllTagIdsArray();

		if (allTagIdsArray.length == 0) {
			return;
		}

		BooleanQuery tagIdsArrayQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long[] allTagIds : allTagIdsArray) {
			if (allTagIds.length == 0) {
				continue;
			}

			long[] filteredAllTagIds = AssetUtil.filterTagIds(
				permissionChecker, allTagIds);

			if (allTagIds.length != filteredAllTagIds.length) {
				addImpossibleTerm(contextQuery, Field.ASSET_TAG_IDS);

				return;
			}

			BooleanQuery tagIdsQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long tagId : allTagIds) {
				tagIdsQuery.addTerm(Field.ASSET_TAG_IDS, tagId);
			}

			tagIdsArrayQuery.add(tagIdsQuery, BooleanClauseOccur.MUST);
		}

		contextQuery.add(tagIdsArrayQuery, BooleanClauseOccur.MUST);
	}

	protected void addSearchAnyCategories(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		long[] anyCategoryIds = _assetEntryQuery.getAnyCategoryIds();

		if (anyCategoryIds.length == 0) {
			return;
		}

		long[] filteredAnyCategoryIds = AssetUtil.filterCategoryIds(
			permissionChecker, anyCategoryIds);

		if (filteredAnyCategoryIds.length == 0) {
			addImpossibleTerm(contextQuery, Field.ASSET_CATEGORY_IDS);

			return;
		}

		BooleanQuery categoryIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long anyCategoryId : filteredAnyCategoryIds) {
			AssetCategory assetCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(anyCategoryId);

			if (assetCategory == null) {
				continue;
			}

			List<Long> categoryIds = new ArrayList<>();

			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				categoryIds.addAll(
					AssetCategoryLocalServiceUtil.getSubcategoryIds(
						anyCategoryId));
			}

			if (categoryIds.isEmpty()) {
				categoryIds.add(anyCategoryId);
			}

			for (long categoryId : categoryIds) {
				categoryIdsQuery.addTerm(Field.ASSET_CATEGORY_IDS, categoryId);
			}
		}

		contextQuery.add(categoryIdsQuery, BooleanClauseOccur.MUST);
	}

	protected void addSearchAnyTags(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		long[] anyTagIds = _assetEntryQuery.getAnyTagIds();

		if (anyTagIds.length == 0) {
			return;
		}

		long[] filteredAnyTagIds = AssetUtil.filterTagIds(
			permissionChecker, anyTagIds);

		if (filteredAnyTagIds.length == 0) {
			addImpossibleTerm(contextQuery, Field.ASSET_TAG_IDS);

			return;
		}

		BooleanQuery tagIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long tagId : anyTagIds) {
			tagIdsQuery.addTerm(Field.ASSET_TAG_IDS, tagId);
		}

		contextQuery.add(tagIdsQuery, BooleanClauseOccur.MUST);
	}

	@Override
	protected void addSearchAssetCategoryIds(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		addSearchAllCategories(contextQuery, searchContext);
		addSearchAnyCategories(contextQuery, searchContext);
		addSearchNotAnyCategories(contextQuery, searchContext);
		addSearchNotAllCategories(contextQuery, searchContext);
	}

	@Override
	protected void addSearchAssetTagNames(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		addSearchAllTags(contextQuery, searchContext);
		addSearchAnyTags(contextQuery, searchContext);
		addSearchNotAllTags(contextQuery, searchContext);
		addSearchNotAnyTags(contextQuery, searchContext);
	}

	@Override
	protected void addSearchKeywords(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		String keywords = searchContext.getKeywords();

		if (Validator.isNull(keywords)) {
			return;
		}

		super.addSearchKeywords(searchQuery, searchContext);

		String field = DocumentImpl.getLocalizedName(
			searchContext.getLocale(), "localized_title");

		searchQuery.addTerm(field, keywords, true);
	}

	@Override
	protected void addSearchLayout(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		String layoutUuid = (String)searchContext.getAttribute(
			Field.LAYOUT_UUID);

		if (Validator.isNotNull(layoutUuid)) {
			contextQuery.addRequiredTerm(Field.LAYOUT_UUID, layoutUuid);
		}
	}

	protected void addSearchNotAllCategories(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] notAllCategoryIds = _assetEntryQuery.getNotAllCategoryIds();

		if (notAllCategoryIds.length == 0) {
			return;
		}

		BooleanQuery categoryIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long notAllCategoryId : notAllCategoryIds) {
			AssetCategory assetCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(
					notAllCategoryId);

			if (assetCategory == null) {
				continue;
			}

			List<Long> categoryIds = new ArrayList<>();

			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				categoryIds.addAll(
					AssetCategoryLocalServiceUtil.getSubcategoryIds(
						notAllCategoryId));
			}

			if (categoryIds.isEmpty()) {
				categoryIds.add(notAllCategoryId);
			}

			BooleanQuery categoryIdQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long categoryId : categoryIds) {
				categoryIdQuery.addTerm(Field.ASSET_CATEGORY_IDS, categoryId);
			}

			categoryIdsQuery.add(categoryIdQuery, BooleanClauseOccur.MUST);
		}

		contextQuery.add(categoryIdsQuery, BooleanClauseOccur.MUST_NOT);
	}

	protected void addSearchNotAllTags(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[][] notAllTagIdsArray = _assetEntryQuery.getNotAllTagIdsArray();

		if (notAllTagIdsArray.length == 0) {
			return;
		}

		BooleanQuery tagIdsArrayQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long[] notAllTagIds : notAllTagIdsArray) {
			if (notAllTagIds.length == 0) {
				continue;
			}

			BooleanQuery tagIdsQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long tagId : notAllTagIds) {
				tagIdsQuery.addTerm(Field.ASSET_TAG_IDS, tagId);
			}

			tagIdsArrayQuery.add(tagIdsQuery, BooleanClauseOccur.MUST);
		}

		contextQuery.add(tagIdsArrayQuery, BooleanClauseOccur.MUST_NOT);
	}

	protected void addSearchNotAnyCategories(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] notAnyCategoryIds = _assetEntryQuery.getNotAnyCategoryIds();

		if (notAnyCategoryIds.length == 0) {
			return;
		}

		BooleanQuery categoryIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long notAnyCategoryId : notAnyCategoryIds) {
			AssetCategory assetCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(
					notAnyCategoryId);

			if (assetCategory == null) {
				continue;
			}

			List<Long> categoryIds = new ArrayList<>();

			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				categoryIds.addAll(
					AssetCategoryLocalServiceUtil.getSubcategoryIds(
						notAnyCategoryId));
			}

			if (categoryIds.isEmpty()) {
				categoryIds.add(notAnyCategoryId);
			}

			for (long categoryId : categoryIds) {
				categoryIdsQuery.addTerm(Field.ASSET_CATEGORY_IDS, categoryId);
			}
		}

		contextQuery.add(categoryIdsQuery, BooleanClauseOccur.MUST_NOT);
	}

	protected void addSearchNotAnyTags(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] notAnyTagIds = _assetEntryQuery.getNotAnyTagIds();

		if (notAnyTagIds.length == 0) {
			return;
		}

		BooleanQuery tagIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long tagId : _assetEntryQuery.getNotAnyTagIds()) {
			tagIdsQuery.addTerm(Field.ASSET_TAG_IDS, tagId);
		}

		contextQuery.add(tagIdsQuery, BooleanClauseOccur.MUST_NOT);
	}

	@Override
	protected void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {

		fullQuery.addRequiredTerm("visible", true);
	}

	private AssetEntryQuery _assetEntryQuery;

}