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

package com.liferay.commerce.product.internal.search;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(enabled = false, immediate = true, service = Indexer.class)
public class CommerceCatalogIndexer extends BaseIndexer<CommerceCatalog> {

	public static final String CLASS_NAME = CommerceCatalog.class.getName();

	public CommerceCatalogIndexer() {
		setDefaultSelectedFieldNames(
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		contextBooleanFilter.addRequiredTerm(
			Field.COMPANY_ID, searchContext.getCompanyId());

		String catalogId = GetterUtil.getString(
			searchContext.getAttribute(Field.ENTRY_CLASS_PK));

		if (!Validator.isBlank(catalogId)) {
			contextBooleanFilter.addTerm(
				Field.ENTRY_CLASS_PK, catalogId, BooleanClauseOccur.MUST);
		}

		String catalogName = GetterUtil.getString(
			searchContext.getAttribute(Field.NAME));

		if (!Validator.isBlank(catalogName)) {
			contextBooleanFilter.addTerm(
				Field.NAME, catalogName, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, false);
	}

	@Override
	protected void doDelete(CommerceCatalog commerceCatalog) throws Exception {
		deleteDocument(
			commerceCatalog.getCompanyId(),
			commerceCatalog.getCommerceCatalogId());
	}

	@Override
	protected Document doGetDocument(CommerceCatalog commerceCatalog)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing commerce catalog " + commerceCatalog);
		}

		Document document = getBaseModelDocument(CLASS_NAME, commerceCatalog);

		document.addKeyword(Field.GROUP_ID, commerceCatalog.getGroupId());
		document.addKeyword(Field.NAME, commerceCatalog.getName());
		document.addKeyword(
			CPField.CATALOG_DEFAULT_LANGUAGE_ID,
			commerceCatalog.getCatalogDefaultLanguageId());

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + commerceCatalog + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.ENTRY_CLASS_PK, Field.NAME);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CommerceCatalog commerceCatalog) throws Exception {
		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceCatalog.getCompanyId(),
			getDocument(commerceCatalog), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_commerceCatalogLocalService.getCommerceCatalog(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		_reindexCommerceCatalogs(companyId);
	}

	private void _reindexCommerceCatalogs(long companyId)
		throws PortalException {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceCatalogLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CommerceCatalog commerceCatalog) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(commerceCatalog));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index commerce catalog " +
								commerceCatalog.getCommerceCatalogId(),
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCatalogIndexer.class);

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}