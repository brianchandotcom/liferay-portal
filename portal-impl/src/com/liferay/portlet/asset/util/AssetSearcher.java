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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.StringQuery;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class AssetSearcher extends BaseSearcher {

	public static Indexer<?> getInstance() {
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

	protected void addImpossibleTerm(
			BooleanFilter queryBooleanFilter, String field)
		throws Exception {

		queryBooleanFilter.addTerm(field, "-1", BooleanClauseOccur.MUST);
	}

	protected void addSearchAllKeywords(BooleanFilter queryBooleanFilter)
		throws Exception {

		String[] allKeywords = _assetEntryQuery.getAllKeywords();

		if (allKeywords.length == 0) {
			return;
		}

		BooleanQuery keywordsQuery = new BooleanQueryImpl();

		for (String keyword : allKeywords) {
			if (keyword.contains(" ")) {
				keyword = StringUtil.quote(keyword, CharPool.QUOTE);
			}

			StringQuery stringQuery = new StringQuery(keyword);

			keywordsQuery.add(stringQuery, BooleanClauseOccur.MUST);
		}

		queryBooleanFilter.add(
			new QueryFilter(keywordsQuery), BooleanClauseOccur.MUST);
	}

	protected void addSearchAllTags(BooleanFilter queryBooleanFilter)
		throws Exception {

		long[][] allTagIdsArray = _assetEntryQuery.getAllTagIdsArray();

		if (allTagIdsArray.length == 0) {
			return;
		}

		BooleanFilter tagIdsArrayBooleanFilter = new BooleanFilter();

		for (long[] allTagIds : allTagIdsArray) {
			if (allTagIds.length == 0) {
				continue;
			}

			TermsFilter tagIdsTermsFilter = new TermsFilter(
				Field.ASSET_TAG_IDS);

			tagIdsTermsFilter.addValues(ArrayUtil.toStringArray(allTagIds));

			tagIdsArrayBooleanFilter.add(
				tagIdsTermsFilter, BooleanClauseOccur.MUST);
		}

		queryBooleanFilter.add(
			tagIdsArrayBooleanFilter, BooleanClauseOccur.MUST);
	}

	protected void addSearchAnyKeywords(BooleanFilter queryBooleanFilter)
		throws Exception {

		String[] anyKeywords = _assetEntryQuery.getAnyKeywords();

		if (anyKeywords.length == 0) {
			return;
		}

		BooleanQuery keywordsQuery = new BooleanQueryImpl();

		for (String keyword : anyKeywords) {
			if (keyword.contains(" ")) {
				keyword = StringUtil.quote(keyword, CharPool.QUOTE);
			}

			StringQuery stringQuery = new StringQuery(keyword);

			keywordsQuery.add(stringQuery, BooleanClauseOccur.SHOULD);
		}

		queryBooleanFilter.add(
			new QueryFilter(keywordsQuery), BooleanClauseOccur.MUST);
	}

	protected void addSearchAnyTags(BooleanFilter queryBooleanFilter)
		throws Exception {

		long[] anyTagIds = _assetEntryQuery.getAnyTagIds();

		if (anyTagIds.length == 0) {
			return;
		}

		TermsFilter tagIdsTermsFilter = new TermsFilter(Field.ASSET_TAG_IDS);

		tagIdsTermsFilter.addValues(ArrayUtil.toStringArray(anyTagIds));

		queryBooleanFilter.add(tagIdsTermsFilter, BooleanClauseOccur.MUST);
	}

	@Override
	protected void addSearchAssetCategoryIds(
			BooleanFilter queryBooleanFilter, SearchContext searchContext)
		throws Exception {

		String[] fieldNamesArray = {Field.ASSET_CATEGORY_IDS};

		if (searchContext.isIncludeInternalAssetCategories()) {
			fieldNamesArray = new String[] {
				Field.ASSET_CATEGORY_IDS, Field.ASSET_INTERNAL_CATEGORY_IDS
			};
		}

		_addSearchAllCategories(queryBooleanFilter, fieldNamesArray);
		_addSearchAnyCategories(queryBooleanFilter, fieldNamesArray);
		_addSearchNotAllCategories(queryBooleanFilter, fieldNamesArray);
		_addSearchNotAnyCategories(queryBooleanFilter, fieldNamesArray);
	}

	@Override
	protected void addSearchAssetTagNames(
			BooleanFilter queryBooleanFilter, SearchContext searchContext)
		throws Exception {

		addSearchAllTags(queryBooleanFilter);
		addSearchAnyTags(queryBooleanFilter);
		addSearchNotAllTags(queryBooleanFilter);
		addSearchNotAnyTags(queryBooleanFilter);
	}

	@Override
	protected Map<String, Query> addSearchKeywords(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		String keywords = searchContext.getKeywords();

		if (Validator.isNull(keywords)) {
			return Collections.emptyMap();
		}

		Map<String, Query> queries = super.addSearchKeywords(
			searchQuery, searchContext);

		String field = Field.getLocalizedName(
			searchContext.getLocale(), "localized_title");

		Query query = searchQuery.addTerm(field, keywords, true);

		queries.put(field, query);

		return queries;
	}

	@Override
	protected void addSearchLayout(
			BooleanFilter queryBooleanFilter, SearchContext searchContext)
		throws Exception {

		String layoutUuid = (String)searchContext.getAttribute(
			Field.LAYOUT_UUID);

		if (Validator.isNotNull(layoutUuid)) {
			queryBooleanFilter.addRequiredTerm(Field.LAYOUT_UUID, layoutUuid);
		}
	}

	protected void addSearchNotAllKeywords(BooleanFilter queryBooleanFilter)
		throws Exception {

		String[] notAllKeywords = _assetEntryQuery.getNotAllKeywords();

		if (notAllKeywords.length == 0) {
			return;
		}

		BooleanQuery keywordsQuery = new BooleanQueryImpl();

		for (String keyword : notAllKeywords) {
			if (keyword.contains(" ")) {
				keyword = StringUtil.quote(keyword, CharPool.QUOTE);
			}

			StringQuery stringQuery = new StringQuery(keyword);

			keywordsQuery.add(stringQuery, BooleanClauseOccur.MUST);
		}

		queryBooleanFilter.add(
			new QueryFilter(keywordsQuery), BooleanClauseOccur.MUST_NOT);
	}

	protected void addSearchNotAllTags(BooleanFilter queryBooleanFilter)
		throws Exception {

		long[][] notAllTagIdsArray = _assetEntryQuery.getNotAllTagIdsArray();

		if (notAllTagIdsArray.length == 0) {
			return;
		}

		BooleanFilter tagIdsArrayBooleanFilter = new BooleanFilter();

		for (long[] notAllTagIds : notAllTagIdsArray) {
			if (notAllTagIds.length == 0) {
				continue;
			}

			TermsFilter tagIdsTermsFilter = new TermsFilter(
				Field.ASSET_TAG_IDS);

			tagIdsTermsFilter.addValues(ArrayUtil.toStringArray(notAllTagIds));

			tagIdsArrayBooleanFilter.add(
				tagIdsTermsFilter, BooleanClauseOccur.MUST);
		}

		queryBooleanFilter.add(
			tagIdsArrayBooleanFilter, BooleanClauseOccur.MUST_NOT);
	}

	protected void addSearchNotAnyKeywords(BooleanFilter queryBooleanFilter)
		throws Exception {

		String[] notAnyKeywords = _assetEntryQuery.getNotAnyKeywords();

		if (notAnyKeywords.length == 0) {
			return;
		}

		BooleanQuery keywordsQuery = new BooleanQueryImpl();

		for (String keyword : notAnyKeywords) {
			if (keyword.contains(" ")) {
				keyword = StringUtil.quote(keyword, CharPool.QUOTE);
			}

			StringQuery stringQuery = new StringQuery(keyword);

			keywordsQuery.add(stringQuery, BooleanClauseOccur.SHOULD);
		}

		queryBooleanFilter.add(
			new QueryFilter(keywordsQuery), BooleanClauseOccur.MUST_NOT);
	}

	protected void addSearchNotAnyTags(BooleanFilter queryBooleanFilter)
		throws Exception {

		long[] notAnyTagIds = _assetEntryQuery.getNotAnyTagIds();

		if (notAnyTagIds.length == 0) {
			return;
		}

		TermsFilter tagIgsTermsFilter = new TermsFilter(Field.ASSET_TAG_IDS);

		tagIgsTermsFilter.addValues(ArrayUtil.toStringArray(notAnyTagIds));

		queryBooleanFilter.add(tagIgsTermsFilter, BooleanClauseOccur.MUST_NOT);
	}

	@Override
	protected BooleanQuery createFullQuery(
			BooleanFilter fullQueryBooleanFilter, SearchContext searchContext)
		throws Exception {

		addSearchAllKeywords(fullQueryBooleanFilter);
		addSearchAnyKeywords(fullQueryBooleanFilter);
		addSearchNotAnyKeywords(fullQueryBooleanFilter);
		addSearchNotAllKeywords(fullQueryBooleanFilter);

		return super.createFullQuery(fullQueryBooleanFilter, searchContext);
	}

	@Override
	protected void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {

		boolean showInvisible = GetterUtil.getBoolean(
			_assetEntryQuery.getAttribute("showInvisible"));

		if (showInvisible) {
			return;
		}

		BooleanFilter booleanFilter = fullQuery.getPreBooleanFilter();

		if (booleanFilter == null) {
			booleanFilter = new BooleanFilter();
		}

		booleanFilter.addRequiredTerm("visible", true);

		if (booleanFilter.hasClauses() && !showInvisible) {
			fullQuery.setPreBooleanFilter(booleanFilter);
		}
	}

	private void _addSearchAllCategories(
			BooleanFilter queryBooleanFilter, String... fieldNamesArray)
		throws Exception {

		for (String fieldName : fieldNamesArray) {
			long[] allCategoryIds = _filterCategoryIdsByVisibilityType(
				_assetEntryQuery.getAllCategoryIds(), fieldName);

			if (allCategoryIds.length == 0) {
				continue;
			}

			long[] filteredAllCategoryIds = AssetUtil.filterCategoryIds(
				PermissionThreadLocal.getPermissionChecker(), allCategoryIds);

			if (allCategoryIds.length != filteredAllCategoryIds.length) {
				addImpossibleTerm(queryBooleanFilter, fieldName);

				continue;
			}

			queryBooleanFilter.add(
				_getCategoryIdsBooleanFilter(fieldName, filteredAllCategoryIds),
				BooleanClauseOccur.MUST);
		}
	}

	private void _addSearchAnyCategories(
			BooleanFilter queryBooleanFilter, String... fieldNamesArray)
		throws Exception {

		BooleanFilter categoryIdsQueryBooleanFilter = new BooleanFilter();

		for (String fieldName : fieldNamesArray) {
			long[] anyCategoryIds = _filterCategoryIdsByVisibilityType(
				_assetEntryQuery.getAnyCategoryIds(), fieldName);

			if (anyCategoryIds.length == 0) {
				continue;
			}

			long[] filteredAnyCategoryIds = AssetUtil.filterCategoryIds(
				PermissionThreadLocal.getPermissionChecker(), anyCategoryIds);

			filteredAnyCategoryIds = _filterCategoryIdsByVisibilityType(
				filteredAnyCategoryIds, fieldName);

			if (filteredAnyCategoryIds.length == 0) {
				addImpossibleTerm(queryBooleanFilter, fieldName);

				continue;
			}

			categoryIdsQueryBooleanFilter.add(
				_getCategoryIdsTermsFilter(fieldName, filteredAnyCategoryIds),
				BooleanClauseOccur.SHOULD);
		}

		if (categoryIdsQueryBooleanFilter.hasClauses()) {
			queryBooleanFilter.add(
				categoryIdsQueryBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

	private void _addSearchNotAllCategories(
		BooleanFilter queryBooleanFilter, String... fieldNamesArray) {

		BooleanFilter categoryIdsQueryBooleanFilter = new BooleanFilter();

		for (String fieldName : fieldNamesArray) {
			long[] filteredNotAllCategoryIds =
				_filterCategoryIdsByVisibilityType(
					_assetEntryQuery.getNotAllCategoryIds(), fieldName);

			if (filteredNotAllCategoryIds.length == 0) {
				continue;
			}

			categoryIdsQueryBooleanFilter.add(
				_getCategoryIdsBooleanFilter(
					fieldName, filteredNotAllCategoryIds),
				BooleanClauseOccur.MUST);
		}

		if (categoryIdsQueryBooleanFilter.hasClauses()) {
			queryBooleanFilter.add(
				categoryIdsQueryBooleanFilter, BooleanClauseOccur.MUST_NOT);
		}
	}

	private void _addSearchNotAnyCategories(
		BooleanFilter queryBooleanFilter, String... fieldNamesArray) {

		for (String fieldName : fieldNamesArray) {
			long[] filteredNotAnyCategoryIds =
				_filterCategoryIdsByVisibilityType(
					_assetEntryQuery.getNotAnyCategoryIds(), fieldName);

			if (filteredNotAnyCategoryIds.length == 0) {
				continue;
			}

			queryBooleanFilter.add(
				_getCategoryIdsTermsFilter(
					fieldName, filteredNotAnyCategoryIds),
				BooleanClauseOccur.MUST_NOT);
		}
	}

	private long[] _filterCategoryIdsByVisibilityType(
		long[] assetCategoryIds, String fieldName) {

		List<Long> filteredCategoryIds = new ArrayList<>();

		for (long assetCategoryId : assetCategoryIds) {
			AssetCategory assetCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(
					assetCategoryId);

			if (assetCategory == null) {
				continue;
			}

			AssetVocabulary assetVocabulary =
				AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
					assetCategory.getVocabularyId());

			if ((assetVocabulary == null) ||
				((assetVocabulary.getVisibilityType() ==
					AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL) &&
				 Objects.equals(fieldName, Field.ASSET_CATEGORY_IDS))) {

				continue;
			}

			if ((assetVocabulary.getVisibilityType() ==
					AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC) &&
				Objects.equals(fieldName, Field.ASSET_INTERNAL_CATEGORY_IDS)) {

				continue;
			}

			filteredCategoryIds.add(assetCategoryId);
		}

		return ArrayUtil.toArray(filteredCategoryIds.toArray(new Long[0]));
	}

	private BooleanFilter _getCategoryIdsBooleanFilter(
		String fieldName, long[] filteredCategoryIds) {

		BooleanFilter categoryIdsBooleanFilter = new BooleanFilter();

		for (long categoryId : filteredCategoryIds) {
			List<Long> categoryIds = new ArrayList<>();

			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				categoryIds.addAll(
					AssetCategoryLocalServiceUtil.getSubcategoryIds(
						categoryId));
			}

			if (categoryIds.isEmpty()) {
				categoryIds.add(categoryId);
			}

			TermsFilter categoryIdTermsFilter = new TermsFilter(fieldName);

			categoryIdTermsFilter.addValues(
				ArrayUtil.toStringArray(categoryIds.toArray(new Long[0])));

			categoryIdsBooleanFilter.add(
				categoryIdTermsFilter, BooleanClauseOccur.MUST);
		}

		return categoryIdsBooleanFilter;
	}

	private TermsFilter _getCategoryIdsTermsFilter(
		String fieldName, long[] filteredCategoryIds) {

		TermsFilter categoryIdsTermsFilter = new TermsFilter(fieldName);

		for (long categoryId : filteredCategoryIds) {
			List<Long> categoryIds = new ArrayList<>();

			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				categoryIds.addAll(
					AssetCategoryLocalServiceUtil.getSubcategoryIds(
						categoryId));
			}

			if (categoryIds.isEmpty()) {
				categoryIds.add(categoryId);
			}

			categoryIdsTermsFilter.addValues(
				ArrayUtil.toStringArray(categoryIds.toArray(new Long[0])));
		}

		return categoryIdsTermsFilter;
	}

	private AssetEntryQuery _assetEntryQuery;

}