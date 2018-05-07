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

package com.liferay.commerce.price.list.internal.search;

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListUserSegmentEntryRelLocalService;
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
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.filter.TermsSetFilterBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = Indexer.class)
public class CommercePriceListIndexer extends BaseIndexer<CommercePriceList> {

	public static final String CLASS_NAME = CommercePriceList.class.getName();

	public CommercePriceListIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.NAME,
			Field.SCOPE_GROUP_ID, Field.UID);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		int[] statuses = GetterUtil.getIntegerValues(
			searchContext.getAttribute(Field.STATUS), null);

		if (ArrayUtil.isEmpty(statuses)) {
			int status = GetterUtil.getInteger(
				searchContext.getAttribute(Field.STATUS),
				WorkflowConstants.STATUS_APPROVED);

			statuses = new int[] {status};
		}

		if (!ArrayUtil.contains(statuses, WorkflowConstants.STATUS_ANY)) {
			TermsFilter statusesTermsFilter = new TermsFilter(Field.STATUS);

			statusesTermsFilter.addValues(ArrayUtil.toStringArray(statuses));

			contextBooleanFilter.add(
				statusesTermsFilter, BooleanClauseOccur.MUST);
		}
		else {
			contextBooleanFilter.addTerm(
				Field.STATUS, String.valueOf(WorkflowConstants.STATUS_IN_TRASH),
				BooleanClauseOccur.MUST_NOT);
		}

		long[] commerceUserSegmentEntryIds = GetterUtil.getLongValues(
			"commerceUserSegmentEntryIds", null);

		if (commerceUserSegmentEntryIds != null) {
			TermsSetFilterBuilder termsSetFilterBuilder =
				_filterBuilders.termsSetFilterBuilder();

			termsSetFilterBuilder.setFieldName("commerceUserSegmentEntryIds");
			termsSetFilterBuilder.setMinimumShouldMatchField(
				"commerceUserSegmentEntryIds_required_matches");

			List<String> values = new ArrayList<>(
				commerceUserSegmentEntryIds.length);

			for (long commerceUserSegmentEntryId :
					commerceUserSegmentEntryIds) {

				values.add(String.valueOf(commerceUserSegmentEntryId));
			}

			termsSetFilterBuilder.setValues(values);

			contextBooleanFilter.add(
				termsSetFilterBuilder.build(), BooleanClauseOccur.MUST);
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
	protected void doDelete(CommercePriceList commercePriceList)
		throws Exception {

		deleteDocument(
			commercePriceList.getCompanyId(),
			commercePriceList.getCommercePriceListId());
	}

	@Override
	protected Document doGetDocument(CommercePriceList commercePriceList)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing price list " + commercePriceList);
		}

		Document document = getBaseModelDocument(CLASS_NAME, commercePriceList);

		document.addNumber(
			Field.ENTRY_CLASS_PK, commercePriceList.getCommercePriceListId());
		document.addText(Field.NAME, commercePriceList.getName());
		document.addText(Field.USER_NAME, commercePriceList.getUserName());
		document.addNumberSortable(
			Field.PRIORITY, commercePriceList.getPriority());

		List<CommercePriceListUserSegmentEntryRel>
			commercePriceListUserSegmentEntryRels =
				_commercePriceListUserSegmentEntryRelLocalService.
					getCommercePriceListUserSegmentEntryRels(
						commercePriceList.getCommercePriceListId());

		Stream<CommercePriceListUserSegmentEntryRel> stream =
			commercePriceListUserSegmentEntryRels.stream();

		long[] commerceUserSegmentEntryIds = stream.mapToLong(
			CommercePriceListUserSegmentEntryRel::getCommerceUserSegmentEntryId
		).toArray();

		document.addNumber(
			"commerceUserSegmentEntryIds", commercePriceList.getPriority());
		document.addNumber(
			"commerceUserSegmentEntryIds_required_matches",
			commerceUserSegmentEntryIds.length);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + commercePriceList + " indexed successfully");
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
	protected void doReindex(CommercePriceList commercePriceList)
		throws Exception {

		Document document = getDocument(commercePriceList);

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commercePriceList.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CommercePriceList commercePriceList =
			_commercePriceListLocalService.getCommercePriceList(classPK);

		doReindex(commercePriceList);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommercePriceLists(companyId);
	}

	protected void reindexCommercePriceLists(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commercePriceListLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<CommercePriceList>() {

				@Override
				public void performAction(CommercePriceList commercePriceList) {
					try {
						Document document = getDocument(commercePriceList);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index commerce price list " +
									commercePriceList.getCommercePriceListId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListIndexer.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private CommercePriceListUserSegmentEntryRelLocalService
		_commercePriceListUserSegmentEntryRelLocalService;

	@Reference
	private FilterBuilders _filterBuilders;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

}