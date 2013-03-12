/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetpublisher.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 */
public class AssetSearcher extends BaseIndexer {

	public static Indexer getInstance() {
		return new AssetSearcher();
	}

	public AssetSearcher() {
		setFilterSearch(true);
		setPermissionAware(true);
	}

	public String[] getClassNames() {
		long[] classNameIds = _assetEntryQuery.getClassNameIds();
		String[] classNames = new String[classNameIds.length];

		for (int i = 0; i < classNames.length; i++) {
			long classNameId = classNameIds[i];

			classNames[i] = PortalUtil.getClassName(classNameId);
		}

		return classNames;
	}

	@Override
	public IndexerPostProcessor[] getIndexerPostProcessors() {
		throw new UnsupportedOperationException();
	}

	public String getPortletId() {
		return null;
	}

	@Override
	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		throw new UnsupportedOperationException();
	}

	public void setAssetEntryQuery(AssetEntryQuery assetEntryQuery) {
		_assetEntryQuery = assetEntryQuery;
	}

	protected void addSearchAllCategories(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		if (_assetEntryQuery.getAllCategoryIds().length <= 0) {
			return;
		}

		BooleanQuery categoryIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long categoryId : _assetEntryQuery.getAllCategoryIds()) {
			List<Long> treeCategoryIds = new ArrayList<Long>();

			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				treeCategoryIds =
					AssetCategoryLocalServiceUtil.getSubcategoryIds(categoryId);
			}

			if (treeCategoryIds.size() <= 0) {
				treeCategoryIds.add(categoryId);
			}

			BooleanQuery categoryIdQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long treeCategoryId : treeCategoryIds) {
				categoryIdQuery.addTerm(
					Field.ASSET_CATEGORY_IDS, treeCategoryId);
			}

			categoryIdsQuery.add(categoryIdQuery, BooleanClauseOccur.MUST);
		}

		contextQuery.add(categoryIdsQuery, BooleanClauseOccur.MUST);
	}

	protected void addSearchAllTags(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		if (_assetEntryQuery.getAllTagIds().length <= 0) {
			return;
		}

		BooleanQuery tagIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long tagId : _assetEntryQuery.getAllTagIds()) {
			tagIdsQuery.addRequiredTerm(Field.ASSET_TAG_IDS, tagId);
		}

		contextQuery.add(tagIdsQuery, BooleanClauseOccur.MUST);
	}

	protected void addSearchAnyCategories(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		if (_assetEntryQuery.getAnyCategoryIds().length <= 0) {
			return;
		}

		BooleanQuery categoryIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long categoryId : _assetEntryQuery.getAnyCategoryIds()) {
			List<Long> treeCategoryIds = new ArrayList<Long>();

			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				treeCategoryIds =
					AssetCategoryLocalServiceUtil.getSubcategoryIds(categoryId);
			}

			if (treeCategoryIds.size() <= 0) {
				treeCategoryIds.add(categoryId);
			}

			for (long treeCategoryId : treeCategoryIds) {
				categoryIdsQuery.addTerm(
					Field.ASSET_CATEGORY_IDS, treeCategoryId);
			}
		}

		contextQuery.add(categoryIdsQuery, BooleanClauseOccur.MUST);
	}

	protected void addSearchAnyTags(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		if (_assetEntryQuery.getAnyTagIds().length <= 0) {
			return;
		}

		BooleanQuery tagIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long tagId : _assetEntryQuery.getAnyTagIds()) {
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

	protected void addSearchNotAllCategories(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		if (_assetEntryQuery.getNotAllCategoryIds().length <= 0) {
			return;
		}

		BooleanQuery categoryIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long categoryId : _assetEntryQuery.getNotAllCategoryIds()) {
			List<Long> treeCategoryIds = new ArrayList<Long>();

			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				treeCategoryIds =
					AssetCategoryLocalServiceUtil.getSubcategoryIds(categoryId);
			}

			if (treeCategoryIds.size() <= 0) {
				treeCategoryIds.add(categoryId);
			}

			BooleanQuery categoryIdQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long treeCategoryId : treeCategoryIds) {
				categoryIdQuery.addTerm(
					Field.ASSET_CATEGORY_IDS, treeCategoryId);
			}

			categoryIdsQuery.add(categoryIdQuery, BooleanClauseOccur.MUST);
		}

		contextQuery.add(categoryIdsQuery, BooleanClauseOccur.MUST_NOT);
	}

	protected void addSearchNotAllTags(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		if (_assetEntryQuery.getNotAllTagIds().length <= 0) {
			return;
		}

		BooleanQuery tagIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long tagId : _assetEntryQuery.getNotAllTagIds()) {
			tagIdsQuery.addRequiredTerm(Field.ASSET_TAG_IDS, tagId);
		}

		contextQuery.add(tagIdsQuery, BooleanClauseOccur.MUST_NOT);
	}

	protected void addSearchNotAnyCategories(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		if (_assetEntryQuery.getNotAnyCategoryIds().length <= 0) {
			return;
		}

		BooleanQuery categoryIdsQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long categoryId : _assetEntryQuery.getNotAnyCategoryIds()) {
			List<Long> treeCategoryIds = new ArrayList<Long>();

			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				treeCategoryIds =
					AssetCategoryLocalServiceUtil.getSubcategoryIds(categoryId);
			}

			if (treeCategoryIds.size() <= 0) {
				treeCategoryIds.add(categoryId);
			}

			for (long treeCategoryId : treeCategoryIds) {
				categoryIdsQuery.addTerm(
					Field.ASSET_CATEGORY_IDS, treeCategoryId);
			}
		}

		contextQuery.add(categoryIdsQuery, BooleanClauseOccur.MUST_NOT);
	}

	protected void addSearchNotAnyTags(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		if (_assetEntryQuery.getNotAnyTagIds().length <= 0) {
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
	protected void doDelete(Object obj) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletURL portletURL)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return null;
	}

	private AssetEntryQuery _assetEntryQuery;

}