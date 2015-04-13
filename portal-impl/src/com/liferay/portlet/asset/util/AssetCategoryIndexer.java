/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;
import com.liferay.portlet.asset.service.persistence.AssetCategoryActionableDynamicQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Istvan Andras Dezsi
 */
public class AssetCategoryIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {AssetCategory.class.getName()};

	public static final String PORTLET_ID = PortletKeys.ASSET_CATEGORIES_ADMIN;

	public AssetCategoryIndexer() {
		setCommitImmediately(true);
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getPortletId() {
		return PORTLET_ID;
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
	public boolean isFilterSearch() {
		return _FILTER_SEARCH;
	}

	@Override
	public boolean isPermissionAware() {
		return _PERMISSION_AWARE;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] vocabularyIds = (long[])searchContext.getAttribute(
			Field.ASSET_VOCABULARY_IDS);

		if (!ArrayUtil.isEmpty(vocabularyIds)) {
			BooleanQuery vocabularyQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long vocabularyId : vocabularyIds) {
				vocabularyQuery.addTerm(
					Field.ASSET_VOCABULARY_ID, String.valueOf(vocabularyId));
			}

			contextQuery.add(vocabularyQuery, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		String title = (String)searchContext.getAttribute(Field.TITLE);

		if (Validator.isNotNull(title)) {
			BooleanQuery localizedQuery = BooleanQueryFactoryUtil.create(
				searchContext);

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
	protected void doDelete(Object obj) throws Exception {
		AssetCategory category = (AssetCategory)obj;

		deleteDocument(category.getCompanyId(), category.getCategoryId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		AssetCategory category = (AssetCategory)obj;

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing category " + category);
		}

		Document document = getBaseModelDocument(PORTLET_ID, category);

		document.addKeyword(Field.ASSET_CATEGORY_ID, category.getCategoryId());

		List<AssetCategory> categories = new ArrayList<AssetCategory>(1);

		categories.add(category);

		addSearchAssetCategoryTitles(
			document, Field.ASSET_CATEGORY_TITLE, categories);

		document.addKeyword(
			Field.ASSET_VOCABULARY_ID, category.getVocabularyId());
		document.addLocalizedText(
			Field.DESCRIPTION, category.getDescriptionMap());
		document.addText(Field.NAME, category.getName());
		document.addLocalizedText(Field.TITLE, category.getTitleMap());

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + category + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		return null;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		AssetCategory category = (AssetCategory)obj;

		Document document = getDocument(category);

		if (document != null) {
			SearchEngineUtil.updateDocument(
				getSearchEngineId(), category.getCompanyId(), document,
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

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexCategories(final long companyId)
		throws PortalException, SystemException {

		final Collection<Document> documents = new ArrayList<Document>();

		ActionableDynamicQuery actionableDynamicQuery =
			new AssetCategoryActionableDynamicQuery() {

			@Override
			protected void performAction(Object object) throws PortalException {
				AssetCategory category = (AssetCategory)object;

				Document document = getDocument(category);

				if (document != null) {
					documents.add(document);
				}
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

	private static final boolean _FILTER_SEARCH = true;

	private static final boolean _PERMISSION_AWARE = true;

	private static Log _log = LogFactoryUtil.getLog(AssetCategoryIndexer.class);

}