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

package com.liferay.commerce.search;

import com.liferay.commerce.model.CommerceTirePriceEntry;
import com.liferay.commerce.service.CommerceTirePriceEntryLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
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

import java.util.LinkedHashMap;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = Indexer.class)
public class CommerceTirePriceEntryIndexer
	extends BaseIndexer<CommerceTirePriceEntry> {

	public static final String CLASS_NAME =
		CommerceTirePriceEntry.class.getName();

	public static final String FIELD_COMMERCE_PRICE_ENTRY_ID =
		"commercePriceEntryId";

	public CommerceTirePriceEntryIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			Field.UID);
		setFilterSearch(false);
		setPermissionAware(false);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		long commercePriceEntryId = GetterUtil.getLong(
			searchContext.getAttribute(FIELD_COMMERCE_PRICE_ENTRY_ID));

		if (commercePriceEntryId > 0) {
			contextBooleanFilter.addRequiredTerm(
				FIELD_COMMERCE_PRICE_ENTRY_ID, commercePriceEntryId);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	@Override
	protected void doDelete(CommerceTirePriceEntry commerceTirePriceEntry)
		throws Exception {

		deleteDocument(
			commerceTirePriceEntry.getCompanyId(),
			commerceTirePriceEntry.getCommercePriceEntryId());
	}

	@Override
	protected Document doGetDocument(
			CommerceTirePriceEntry commerceTirePriceEntry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing tire price entry " + commerceTirePriceEntry);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, commerceTirePriceEntry);

		document.addKeyword(
			FIELD_COMMERCE_PRICE_ENTRY_ID,
			commerceTirePriceEntry.getCommerceTirePriceEntryId());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + commerceTirePriceEntry + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, FIELD_COMMERCE_PRICE_ENTRY_ID,
			FIELD_COMMERCE_PRICE_ENTRY_ID);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CommerceTirePriceEntry commerceTirePriceEntry)
		throws Exception {

		Document document = getDocument(commerceTirePriceEntry);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceTirePriceEntry.getCompanyId(),
			document, isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CommerceTirePriceEntry commerceTirePriceEntry =
			_commerceTirePriceEntryLocalService.getCommerceTirePriceEntry(
				classPK);

		doReindex(commerceTirePriceEntry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommerceTirePriceEntry(companyId);
	}

	protected void reindexCommerceTirePriceEntry(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceTirePriceEntryLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<CommerceTirePriceEntry>() {

				@Override
				public void performAction(
					CommerceTirePriceEntry commerceTirePriceEntry) {

					try {
						Document document = getDocument(commerceTirePriceEntry);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce tire price entry " +
									commerceTirePriceEntry.
										getCommerceTirePriceEntryId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTirePriceEntryIndexer.class);

	@Reference
	private CommerceTirePriceEntryLocalService
		_commerceTirePriceEntryLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}