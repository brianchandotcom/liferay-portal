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
import com.liferay.portlet.asset.service.persistence.AssetCategoryActionableDynamicQuery;

import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Istvan Andras Dezsi
 */
public class AssetCategoryIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {AssetCategory.class.getName()};

	public static final String PORTLET_ID = PortletKeys.ASSET_CATEGORIES_ADMIN;

	public AssetCategoryIndexer() {
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

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategory(
			entryClassPK);

		return AssetCategoryPermission.contains(
			permissionChecker, assetCategory, ActionKeys.VIEW);
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchEntryClassNames(searchQuery, searchContext);
		addGroupIdTerm(searchQuery, searchContext, Field.GROUP_ID);
		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, true);
		addVocabularyIdsTerm(searchQuery, searchContext, Field.VOCABULARY_ID);
	}

	protected void addGroupIdTerm(
			BooleanQuery searchQuery, SearchContext searchContext, String field)
		throws Exception {

		if (Validator.isNull(field)) {
			return;
		}

		long[] groupIds = searchContext.getGroupIds();

		if (ArrayUtil.isEmpty(groupIds)) {
			return;
		}

		searchQuery.addRequiredTerm(Field.GROUP_ID, groupIds[0]);
	}

	@Override
	protected void addSearchLocalizedTerm(
			BooleanQuery searchQuery, SearchContext searchContext, String field,
			boolean like)
		throws Exception {

		if (Validator.isNull(field)) {
			return;
		}

		String value = String.valueOf(searchContext.getAttribute(field));

		if (Validator.isNull(value)) {
			value = searchContext.getKeywords();
		}

		if (Validator.isNull(value)) {
			return;
		}

		String localizedField = DocumentImpl.getLocalizedName(
			searchContext.getLocale(), field);

		BooleanQuery titleQuery = BooleanQueryFactoryUtil.create(searchContext);

		titleQuery.addTerm(field, value, like);
		titleQuery.addTerm(localizedField, value, like);

		searchQuery.add(titleQuery, BooleanClauseOccur.MUST);
	}

	protected void addVocabularyIdsTerm(
			BooleanQuery searchQuery, SearchContext searchContext, String field)
		throws Exception {

		if (Validator.isNull(field)) {
			return;
		}

		long[] vocabularyIds = (long[])searchContext.getAttribute(
			"vocabularyIds");

		if (ArrayUtil.isEmpty(vocabularyIds)) {
			return;
		}

		BooleanQuery vocabularyQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long vocabularyId : vocabularyIds) {
			vocabularyQuery.addTerm(
				Field.VOCABULARY_ID, String.valueOf(vocabularyId), false);
		}

		BooleanClauseOccur booleanClauseOccur = BooleanClauseOccur.MUST;

		searchQuery.add(vocabularyQuery, booleanClauseOccur);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		AssetCategory assetCategory = (AssetCategory)obj;

		deleteDocument(
			assetCategory.getCompanyId(), assetCategory.getCategoryId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		AssetCategory assetCategory = (AssetCategory)obj;

		Document document = getBaseModelDocument(PORTLET_ID, assetCategory);

		document.addKeyword(Field.CATEGORY_ID, assetCategory.getCategoryId());
		document.addLocalizedText(
			Field.DESCRIPTION, assetCategory.getDescriptionMap());
		document.addText(Field.NAME, assetCategory.getName());
		document.addLocalizedText(Field.TITLE, assetCategory.getTitleMap());
		document.addKeyword(
			Field.VOCABULARY_ID, assetCategory.getVocabularyId());

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
		AssetCategory assetCategory = (AssetCategory)obj;

		Document document = getDocument(assetCategory);

		if (document != null) {
			SearchEngineUtil.updateDocument(
				getSearchEngineId(), assetCategory.getCompanyId(), document);
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategory(
			classPK);

		doReindex(assetCategory);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexAssetCategories(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexAssetCategories(long companyId)
		throws PortalException, SystemException {

		ActionableDynamicQuery actionableDynamicQuery =
			new AssetCategoryActionableDynamicQuery() {

			@Override
			protected void performAction(Object object) throws PortalException {
				AssetCategory assetCategory = (AssetCategory)object;

				Document document = getDocument(assetCategory);

				if (document != null) {
					addDocument(document);
				}
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

}