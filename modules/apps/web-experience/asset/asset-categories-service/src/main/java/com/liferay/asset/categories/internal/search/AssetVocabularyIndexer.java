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

package com.liferay.asset.categories.internal.search;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.service.permission.AssetVocabularyPermission;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Istvan Andras Dezsi
 */
@Component(immediate = true, service = Indexer.class)
public class AssetVocabularyIndexer extends BaseIndexer<AssetVocabulary> {

	public static final String CLASS_NAME = AssetVocabulary.class.getName();

	public AssetVocabularyIndexer() {
		setDefaultSelectedFieldNames(
			Field.ASSET_VOCABULARY_ID, Field.COMPANY_ID, Field.GROUP_ID,
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

		AssetVocabulary vocabulary = _assetVocabularyLocalService.getVocabulary(
			entryClassPK);

		return AssetVocabularyPermission.contains(
			permissionChecker, vocabulary, ActionKeys.VIEW);
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		String title = (String)searchContext.getAttribute(Field.TITLE);

		if (Validator.isNotNull(title)) {
			BooleanQuery localizedQuery = new BooleanQueryImpl();

			addSearchLocalizedTerm(
				localizedQuery, searchContext, Field.TITLE, true);

			searchQuery.add(localizedQuery, BooleanClauseOccur.SHOULD);
		}
	}

	@Override
	protected void doDelete(AssetVocabulary assetVocabulary) throws Exception {
		deleteDocument(
			assetVocabulary.getCompanyId(), assetVocabulary.getVocabularyId());
	}

	@Override
	protected Document doGetDocument(AssetVocabulary assetVocabulary)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing asset vocabulary " + assetVocabulary);
		}

		Document document = getBaseModelDocument(CLASS_NAME, assetVocabulary);

		document.addKeyword(
			Field.ASSET_VOCABULARY_ID, assetVocabulary.getVocabularyId());

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(
			assetVocabulary.getGroupId());

		addLocalizedField(
			document, Field.DESCRIPTION, siteDefaultLocale,
			assetVocabulary.getDescriptionMap());

		document.addText(Field.NAME, assetVocabulary.getName());
		addLocalizedField(
			document, Field.TITLE, siteDefaultLocale,
			assetVocabulary.getTitleMap());

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + assetVocabulary + " indexed successfully");
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
	protected void doReindex(AssetVocabulary assetVocabulary) throws Exception {
		Document document = getDocument(assetVocabulary);

		IndexWriterHelperUtil.updateDocument(
			getSearchEngineId(), assetVocabulary.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		AssetVocabulary vocabulary = _assetVocabularyLocalService.getVocabulary(
			classPK);

		doReindex(vocabulary);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexVocabularies(companyId);
	}

	protected void reindexVocabularies(final long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_assetVocabularyLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<AssetVocabulary>() {

				@Override
				public void performAction(AssetVocabulary assetVocabulary) {
					try {
						Document document = getDocument(assetVocabulary);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index asset vocabulary " +
									assetVocabulary.getVocabularyId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetVocabularyIndexer.class);

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private Portal _portal;

}