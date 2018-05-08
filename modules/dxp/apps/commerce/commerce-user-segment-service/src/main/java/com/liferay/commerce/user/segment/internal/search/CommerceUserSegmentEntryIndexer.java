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

package com.liferay.commerce.user.segment.internal.search;

import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionType;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeRegistry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionLocalService;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.commerce.user.segment.util.comparator.CommerceUserSegmentCriterionPriorityComparator;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = Indexer.class)
public class CommerceUserSegmentEntryIndexer
	extends BaseIndexer<CommerceUserSegmentEntry> {

	public static final String CLASS_NAME =
		CommerceUserSegmentEntry.class.getName();

	public CommerceUserSegmentEntryIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.NAME,
			Field.SCOPE_GROUP_ID, Field.UID);
		setFilterSearch(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		boolean criterionType = GetterUtil.getBoolean(
			searchContext.getAttribute("criterionType"));

		boolean active = GetterUtil.getBoolean(
			searchContext.getAttribute("active"));

		if (active) {
			TermFilter termFilter = new TermFilter(
				"active", Boolean.TRUE.toString());

			contextBooleanFilter.add(termFilter, BooleanClauseOccur.MUST);
		}

		if (criterionType) {
			List<CommerceUserSegmentCriterionType>
				commerceUserSegmentCriterionTypes =
					_commerceUserSegmentCriterionTypeRegistry.
						getCommerceUserSegmentCriterionTypes();

			for (CommerceUserSegmentCriterionType
					commerceUserSegmentCriterionType :
						commerceUserSegmentCriterionTypes) {

				commerceUserSegmentCriterionType.
					postProcessContextBooleanFilter(
						contextBooleanFilter, searchContext);
			}
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, false);
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
	protected void doDelete(CommerceUserSegmentEntry commerceUserSegmentEntry)
		throws Exception {

		deleteDocument(
			commerceUserSegmentEntry.getCompanyId(),
			commerceUserSegmentEntry.getCommerceUserSegmentEntryId());
	}

	@Override
	protected Document doGetDocument(
			CommerceUserSegmentEntry commerceUserSegmentEntry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing user segment " + commerceUserSegmentEntry);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, commerceUserSegmentEntry);

		document.addNumber(
			Field.ENTRY_CLASS_PK,
			commerceUserSegmentEntry.getCommerceUserSegmentEntryId());
		document.addText(
			Field.USER_NAME, commerceUserSegmentEntry.getUserName());
		document.addNumberSortable(
			Field.PRIORITY, commerceUserSegmentEntry.getPriority());

		document.addKeyword("active", commerceUserSegmentEntry.isActive());

		String commerceUserSegmentEntryLanguageId =
			LocalizationUtil.getDefaultLanguageId(
				commerceUserSegmentEntry.getName());

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			commerceUserSegmentEntry.getName());

		for (String languageId : languageIds) {
			document.addText(
				LocalizationUtil.getLocalizedName(Field.NAME, languageId),
				commerceUserSegmentEntry.getName(languageId));

			if (languageId.equals(commerceUserSegmentEntryLanguageId)) {
				document.addText(
					Field.NAME, commerceUserSegmentEntry.getName(languageId));
				document.addText("defaultLanguageId", languageId);
			}
		}

		try {
			List<CommerceUserSegmentCriterion> commerceUserSegmentCriteria =
				_commerceUserSegmentCriterionLocalService.
					getCommerceUserSegmentCriteria(
						commerceUserSegmentEntry.
							getCommerceUserSegmentEntryId(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						new CommerceUserSegmentCriterionPriorityComparator(
							true));

			for (CommerceUserSegmentCriterion commerceUserSegmentCriterion :
					commerceUserSegmentCriteria) {

				CommerceUserSegmentCriterionType
					commerceUserSegmentCriterionType =
						_commerceUserSegmentCriterionTypeRegistry.
							getCommerceUserSegmentCriterionType(
								commerceUserSegmentCriterion.getType());

				commerceUserSegmentCriterionType.contributeToDocument(
					commerceUserSegmentCriterion, document);
			}
		}
		catch (Exception ex) {
			_log.error(
				"Error indexing Document" + commerceUserSegmentEntry, ex);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + commerceUserSegmentEntry +
					" indexed successfully");
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
	protected void doReindex(CommerceUserSegmentEntry commerceUserSegmentEntry)
		throws Exception {

		Document document = getDocument(commerceUserSegmentEntry);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceUserSegmentEntry.getCompanyId(),
			document, isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CommerceUserSegmentEntry commerceUserSegmentEntry =
			_commerceUserSegmentEntryLocalService.getCommerceUserSegmentEntry(
				classPK);

		doReindex(commerceUserSegmentEntry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommerceUserSegmentEntries(companyId);
	}

	protected void reindexCommerceUserSegmentEntries(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceUserSegmentEntryLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<CommerceUserSegmentEntry>() {

				@Override
				public void performAction(
					CommerceUserSegmentEntry commerceUserSegmentEntry) {

					try {
						Document document = getDocument(
							commerceUserSegmentEntry);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce user segment " +
									commerceUserSegmentEntry.
										getCommerceUserSegmentEntryId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceUserSegmentEntryIndexer.class);

	@Reference
	private CommerceUserSegmentCriterionLocalService
		_commerceUserSegmentCriterionLocalService;

	@Reference
	private CommerceUserSegmentCriterionTypeRegistry
		_commerceUserSegmentCriterionTypeRegistry;

	@Reference
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}