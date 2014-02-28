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
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.permission.AssetVocabularyPermission;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyActionableDynamicQuery;

import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Istvan Andras Dezsi
 */
public class AssetVocabularyIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {
		AssetVocabulary.class.getName()
	};

	public static final String PORTLET_ID = PortletKeys.ASSET_CATEGORIES_ADMIN;

	public AssetVocabularyIndexer() {
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

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.getVocabulary(entryClassPK);

		return AssetVocabularyPermission.contains(
			permissionChecker, assetVocabulary, ActionKeys.VIEW);
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchLocalizedTerm(searchQuery, searchContext, Field.TITLE, true);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		AssetVocabulary assetVocabulary = (AssetVocabulary)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, assetVocabulary.getVocabularyId());

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), assetVocabulary.getCompanyId(),
			document.get(Field.UID));
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		AssetVocabulary assetVocabulary = (AssetVocabulary)obj;

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing folder " + assetVocabulary);
		}

		Document document = getBaseModelDocument(PORTLET_ID, assetVocabulary);

		document.addKeyword(
			Field.ASSET_VOCABULARY_ID, assetVocabulary.getVocabularyId());
		document.addLocalizedText(
			Field.DESCRIPTION, assetVocabulary.getDescriptionMap());
		document.addText(Field.NAME, assetVocabulary.getName());
		document.addLocalizedText(Field.TITLE, assetVocabulary.getTitleMap());

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + assetVocabulary + " indexed successfully");
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
		AssetVocabulary assetVocabulary = (AssetVocabulary)obj;

		Document document = getDocument(assetVocabulary);

		if (document != null) {
			SearchEngineUtil.updateDocument(
				getSearchEngineId(), assetVocabulary.getCompanyId(), document);
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.getVocabulary(classPK);

		doReindex(assetVocabulary);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexAssetVocabularies(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexAssetVocabularies(final long companyId)
		throws PortalException, SystemException {

		ActionableDynamicQuery actionableDynamicQuery =
			new AssetVocabularyActionableDynamicQuery() {

			@Override
			protected void performAction(Object object) throws PortalException {
				AssetVocabulary assetVocabulary = (AssetVocabulary)object;

				Document document = getDocument(assetVocabulary);

				if (document != null) {
					addDocument(document);
				}
			}
		};

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

	private static Log _log = LogFactoryUtil.getLog(
		AssetVocabularyIndexer.class);

}