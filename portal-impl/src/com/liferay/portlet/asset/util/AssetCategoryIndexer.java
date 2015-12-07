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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Istvan Andras Dezsi
 */
@OSGiBeanProperties
public class AssetCategoryIndexer extends BaseIndexer<AssetCategory> {

	public static final String CLASS_NAME = AssetCategory.class.getName();

	public AssetCategoryIndexer() {
		setDefaultSelectedFieldNames(
			Field.ASSET_CATEGORY_ID, Field.COMPANY_ID, Field.GROUP_ID,
			Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		AssetCategory category = AssetCategoryLocalServiceUtil.getCategory(
			entryClassPK);

		return AssetCategoryPermission.contains(
			permissionChecker, category, ActionKeys.VIEW);
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long[] parentCategoryIds = (long[])searchContext.getAttribute(
			Field.ASSET_PARENT_CATEGORY_IDS);

		if (!ArrayUtil.isEmpty(parentCategoryIds)) {
			TermsFilter parentCategoryTermsFilter = new TermsFilter(
				Field.ASSET_PARENT_CATEGORY_ID);

			parentCategoryTermsFilter.addValues(
				ArrayUtil.toStringArray(parentCategoryIds));

			contextBooleanFilter.add(
				parentCategoryTermsFilter, BooleanClauseOccur.MUST);
		}

		long[] vocabularyIds = (long[])searchContext.getAttribute(
			Field.ASSET_VOCABULARY_IDS);

		if (!ArrayUtil.isEmpty(vocabularyIds)) {
			TermsFilter vocabularyTermsFilter = new TermsFilter(
				Field.ASSET_VOCABULARY_ID);

			vocabularyTermsFilter.addValues(
				ArrayUtil.toStringArray(vocabularyIds));

			contextBooleanFilter.add(
				vocabularyTermsFilter, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		String title = (String)searchContext.getAttribute(Field.TITLE);

		if (Validator.isNotNull(title)) {
			BooleanQuery localizedQuery = new BooleanQueryImpl();

			searchContext.setAttribute(Field.ASSET_CATEGORY_TITLE, title);

			addSearchLocalizedTerm(
				localizedQuery, searchContext, Field.ASSET_CATEGORY_TITLE,
				true);
			addSearchLocalizedTerm(
				localizedQuery, searchContext, Field.TITLE, true);

			searchQuery.add(localizedQuery, BooleanClauseOccur.SHOULD);
		}
	}

	@Override
	protected void doDelete(AssetCategory assetCategory) throws Exception {
		Document document = new DocumentImpl();

		document.addUID(CLASS_NAME, assetCategory.getCategoryId());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), assetCategory.getCompanyId(),
			document.get(Field.UID), isCommitImmediately());
	}

	@Override
	protected Document doGetDocument(AssetCategory assetCategory)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing asset category " + assetCategory);
		}

		Document document = getBaseModelDocument(CLASS_NAME, assetCategory);

		document.addKeyword(
			Field.ASSET_CATEGORY_ID, assetCategory.getCategoryId());

		List<AssetCategory> categories = new ArrayList<>(1);

		categories.add(assetCategory);

		addSearchAssetCategoryTitles(
			document, Field.ASSET_CATEGORY_TITLE, categories);

		document.addKeyword(
			Field.ASSET_PARENT_CATEGORY_ID,
			assetCategory.getParentCategoryId());
		document.addKeyword(
			Field.ASSET_VOCABULARY_ID, assetCategory.getVocabularyId());
		document.addLocalizedText(
			Field.DESCRIPTION, assetCategory.getDescriptionMap());
		document.addText(Field.NAME, assetCategory.getName());
		document.addLocalizedText(Field.TITLE, assetCategory.getTitleMap());

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + assetCategory + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return null;
	}

	@Override
	protected void doReindex(AssetCategory assetCategory) throws Exception {
		Document document = getDocument(assetCategory);

		if (document != null) {
			SearchEngineUtil.updateDocument(
				getSearchEngineId(), assetCategory.getCompanyId(), document,
				isCommitImmediately());
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		AssetCategory category = AssetCategoryLocalServiceUtil.getCategory(
			classPK);

		doReindex(category);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCategories(companyId);
	}

	protected void reindexCategories(final long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			AssetCategoryLocalServiceUtil.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<AssetCategory>() {

				@Override
				public void performAction(AssetCategory category) {

					try {
						Document document = getDocument(category);

						if (document != null) {
							indexableActionableDynamicQuery.addDocuments(
								document);
						}
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index asset category " +
									category.getCategoryId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryIndexer.class);

}