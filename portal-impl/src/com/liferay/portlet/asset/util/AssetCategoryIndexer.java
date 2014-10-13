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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Istvan Andras Dezsi
 */
public class AssetCategoryIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {AssetCategory.class.getName()};

	public static final String PORTLET_ID = PortletKeys.ASSET_CATEGORIES_ADMIN;

	public AssetCategoryIndexer() {
		setCommitImmediately(true);
		setDefaultSelectedFieldNames(
			Field.ASSET_CATEGORY_ID, Field.COMPANY_ID, Field.GROUP_ID,
			Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
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
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] parentCategoryIds = (long[])searchContext.getAttribute(
			Field.ASSET_PARENT_CATEGORY_IDS);

		if (!ArrayUtil.isEmpty(parentCategoryIds)) {
			BooleanQuery parentCategoryQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			for (long parentCategoryId : parentCategoryIds) {
				parentCategoryQuery.addTerm(
					Field.ASSET_PARENT_CATEGORY_ID,
					String.valueOf(parentCategoryId));
			}

			contextQuery.add(parentCategoryQuery, BooleanClauseOccur.MUST);
		}

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
		AssetCategory assetCategory = (AssetCategory)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, assetCategory.getCategoryId());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), assetCategory.getCompanyId(),
			document.get(Field.UID), isCommitImmediately());
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
			Field.ASSET_PARENT_CATEGORY_ID, category.getParentCategoryId());
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
		Document document, Locale locale, String snippet, PortletURL portletURL,
		PortletRequest portletRequest, PortletResponse portletResponse) {

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
		throws PortalException {

		final ActionableDynamicQuery actionableDynamicQuery =
			AssetCategoryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					AssetCategory category = (AssetCategory)object;

					Document document = getDocument(category);

					if (document != null) {
						actionableDynamicQuery.addDocument(document);
					}
				}

			});
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryIndexer.class);

}