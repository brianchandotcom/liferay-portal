/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.search;

import com.liferay.asset.kernel.configuration.provider.AssetCategoryConfigurationProviderUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPDefinitionSearcher extends BaseSearcher {

	public CPDefinitionSearcher(CPQuery cpQuery) {
		_cpQuery = cpQuery;

		setDefaultSelectedFieldNames(
			CPField.DEFAULT_IMAGE_FILE_URL, CPField.DEFAULT_IMAGE_FILE_URL,
			CPField.DEPTH, CPField.HEIGHT, CPField.IS_IGNORE_SKU_COMBINATIONS,
			CPField.PRODUCT_TYPE_NAME, CPField.SHORT_DESCRIPTION,
			Field.COMPANY_ID, Field.DESCRIPTION, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.NAME, Field.SCOPE_GROUP_ID, Field.UID, Field.URL);

		setDefaultSelectedLocalizedFieldNames(Field.NAME);
	}

	@Override
	public String getClassName() {
		return _CLASS_NAME;
	}

	@Override
	protected void addSearchAssetCategoryIds(
			BooleanFilter queryBooleanFilter, SearchContext searchContext)
		throws Exception {

		_addSearchAllCategories(queryBooleanFilter);
		_addSearchAnyCategories(queryBooleanFilter);
		_addSearchNotAnyCategories(queryBooleanFilter);
		_addSearchNotAllCategories(queryBooleanFilter);
	}

	@Override
	protected void addSearchAssetTagNames(
			BooleanFilter queryBooleanFilter, SearchContext searchContext)
		throws Exception {

		_addSearchAllTags(queryBooleanFilter);
		_addSearchAnyTags(queryBooleanFilter);
		_addSearchNotAllTags(queryBooleanFilter);
		_addSearchNotAnyTags(queryBooleanFilter);
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

	private void _addImpossibleTerm(
			BooleanFilter queryBooleanFilter, String field)
		throws Exception {

		queryBooleanFilter.addTerm(field, "-1", BooleanClauseOccur.MUST);
	}

	private void _addSearchAllCategories(BooleanFilter queryBooleanFilter)
		throws Exception {

		long[] allCategoryIds = _cpQuery.getAllCategoryIds();

		if (allCategoryIds.length == 0) {
			return;
		}

		long[] filteredAllCategoryIds = AssetUtil.filterCategoryIds(
			PermissionThreadLocal.getPermissionChecker(), allCategoryIds);

		if (allCategoryIds.length != filteredAllCategoryIds.length) {
			_addImpossibleTerm(queryBooleanFilter, Field.ASSET_CATEGORY_IDS);

			return;
		}

		BooleanFilter categoryIdsBooleanFilter = new BooleanFilter();

		for (long allCategoryId : filteredAllCategoryIds) {
			AssetCategory assetCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(allCategoryId);

			if (assetCategory == null) {
				continue;
			}

			List<Long> categoryIds = new ArrayList<>();

			if (AssetCategoryConfigurationProviderUtil.isSearchHierarchical(
					CompanyThreadLocal.getCompanyId())) {

				categoryIds.addAll(
					AssetCategoryLocalServiceUtil.getSubcategoryIds(
						allCategoryId));
			}

			if (categoryIds.isEmpty()) {
				categoryIds.add(allCategoryId);
			}

			TermsFilter categoryIdTermsFilter = new TermsFilter(
				Field.ASSET_CATEGORY_IDS);

			categoryIdTermsFilter.addValues(
				ArrayUtil.toStringArray(categoryIds.toArray(new Long[0])));

			categoryIdsBooleanFilter.add(
				categoryIdTermsFilter, BooleanClauseOccur.MUST);
		}

		queryBooleanFilter.add(
			categoryIdsBooleanFilter, BooleanClauseOccur.MUST);
	}

	private void _addSearchAllTags(BooleanFilter queryBooleanFilter)
		throws Exception {

		long[][] allTagIdsArray = _cpQuery.getAllTagIdsArray();

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

	private void _addSearchAnyCategories(BooleanFilter queryBooleanFilter)
		throws Exception {

		long[] anyCategoryIds = _cpQuery.getAnyCategoryIds();

		if (anyCategoryIds.length == 0) {
			return;
		}

		long[] filteredAnyCategoryIds = AssetUtil.filterCategoryIds(
			PermissionThreadLocal.getPermissionChecker(), anyCategoryIds);

		if (filteredAnyCategoryIds.length == 0) {
			_addImpossibleTerm(queryBooleanFilter, Field.ASSET_CATEGORY_IDS);

			return;
		}

		TermsFilter categoryIdsTermsFilter = new TermsFilter(
			Field.ASSET_CATEGORY_IDS);

		for (long anyCategoryId : filteredAnyCategoryIds) {
			AssetCategory assetCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(anyCategoryId);

			if (assetCategory == null) {
				continue;
			}

			List<Long> categoryIds = new ArrayList<>();

			if (AssetCategoryConfigurationProviderUtil.isSearchHierarchical(
					CompanyThreadLocal.getCompanyId())) {

				categoryIds.addAll(
					AssetCategoryLocalServiceUtil.getSubcategoryIds(
						anyCategoryId));
			}

			if (categoryIds.isEmpty()) {
				categoryIds.add(anyCategoryId);
			}

			categoryIdsTermsFilter.addValues(
				ArrayUtil.toStringArray(categoryIds.toArray(new Long[0])));
		}

		queryBooleanFilter.add(categoryIdsTermsFilter, BooleanClauseOccur.MUST);
	}

	private void _addSearchAnyTags(BooleanFilter queryBooleanFilter)
		throws Exception {

		long[] anyTagIds = _cpQuery.getAnyTagIds();

		if (anyTagIds.length == 0) {
			return;
		}

		TermsFilter tagIdsTermsFilter = new TermsFilter(Field.ASSET_TAG_IDS);

		tagIdsTermsFilter.addValues(ArrayUtil.toStringArray(anyTagIds));

		queryBooleanFilter.add(tagIdsTermsFilter, BooleanClauseOccur.MUST);
	}

	private void _addSearchNotAllCategories(BooleanFilter queryBooleanFilter)
		throws Exception {

		long[] notAllCategoryIds = _cpQuery.getNotAllCategoryIds();

		if (notAllCategoryIds.length == 0) {
			return;
		}

		BooleanFilter categoryIdsBooleanFilter = new BooleanFilter();

		for (long notAllCategoryId : notAllCategoryIds) {
			AssetCategory assetCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(
					notAllCategoryId);

			if (assetCategory == null) {
				continue;
			}

			List<Long> categoryIds = new ArrayList<>();

			if (AssetCategoryConfigurationProviderUtil.isSearchHierarchical(
					CompanyThreadLocal.getCompanyId())) {

				categoryIds.addAll(
					AssetCategoryLocalServiceUtil.getSubcategoryIds(
						notAllCategoryId));
			}

			if (categoryIds.isEmpty()) {
				categoryIds.add(notAllCategoryId);
			}

			TermsFilter categoryIdTermsFilter = new TermsFilter(
				Field.ASSET_CATEGORY_IDS);

			categoryIdTermsFilter.addValues(
				ArrayUtil.toStringArray(categoryIds.toArray(new Long[0])));

			categoryIdsBooleanFilter.add(
				categoryIdTermsFilter, BooleanClauseOccur.MUST);
		}

		queryBooleanFilter.add(
			categoryIdsBooleanFilter, BooleanClauseOccur.MUST_NOT);
	}

	private void _addSearchNotAllTags(BooleanFilter queryBooleanFilter)
		throws Exception {

		long[][] notAllTagIdsArray = _cpQuery.getNotAllTagIdsArray();

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

	private void _addSearchNotAnyCategories(BooleanFilter queryBooleanFilter)
		throws Exception {

		long[] notAnyCategoryIds = _cpQuery.getNotAnyCategoryIds();

		if (notAnyCategoryIds.length == 0) {
			return;
		}

		TermsFilter categoryIdsTermsFilter = new TermsFilter(
			Field.ASSET_CATEGORY_IDS);

		for (long notAnyCategoryId : notAnyCategoryIds) {
			AssetCategory assetCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(
					notAnyCategoryId);

			if (assetCategory == null) {
				continue;
			}

			List<Long> categoryIds = new ArrayList<>();

			if (AssetCategoryConfigurationProviderUtil.isSearchHierarchical(
					CompanyThreadLocal.getCompanyId())) {

				categoryIds.addAll(
					AssetCategoryLocalServiceUtil.getSubcategoryIds(
						notAnyCategoryId));
			}

			if (categoryIds.isEmpty()) {
				categoryIds.add(notAnyCategoryId);
			}

			categoryIdsTermsFilter.addValues(
				ArrayUtil.toStringArray(categoryIds.toArray(new Long[0])));
		}

		queryBooleanFilter.add(
			categoryIdsTermsFilter, BooleanClauseOccur.MUST_NOT);
	}

	private void _addSearchNotAnyTags(BooleanFilter queryBooleanFilter)
		throws Exception {

		long[] notAnyTagIds = _cpQuery.getNotAnyTagIds();

		if (notAnyTagIds.length == 0) {
			return;
		}

		TermsFilter tagIgsTermsFilter = new TermsFilter(Field.ASSET_TAG_IDS);

		tagIgsTermsFilter.addValues(ArrayUtil.toStringArray(notAnyTagIds));

		queryBooleanFilter.add(tagIgsTermsFilter, BooleanClauseOccur.MUST_NOT);
	}

	private static final String _CLASS_NAME = CPDefinition.class.getName();

	private final CPQuery _cpQuery;

}