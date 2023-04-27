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

package com.liferay.commerce.payment.service.impl;

import com.liferay.commerce.constants.CommercePaymentEntryConstants;
import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.service.CommercePaymentEntryAuditLocalService;
import com.liferay.commerce.payment.service.base.CommercePaymentEntryLocalServiceBaseImpl;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortFieldBuilder;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;

import java.math.BigDecimal;

import java.util.LinkedHashMap;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "model.class.name=com.liferay.commerce.payment.model.CommercePaymentEntry",
	service = AopService.class
)
public class CommercePaymentEntryLocalServiceImpl
	extends CommercePaymentEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePaymentEntry addCommercePaymentEntry(
			long userId, long classNameId, long classPK, BigDecimal amount,
			String currencyCode, String paymentMethodName,
			String transactionCode, ServiceContext serviceContext)
		throws PortalException {

		CommercePaymentEntry commercePaymentEntry =
			commercePaymentEntryPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		commercePaymentEntry.setCompanyId(user.getCompanyId());
		commercePaymentEntry.setUserId(user.getUserId());
		commercePaymentEntry.setUserName(user.getFullName());

		commercePaymentEntry.setClassNameId(classNameId);
		commercePaymentEntry.setClassPK(classPK);
		commercePaymentEntry.setAmount(amount);
		commercePaymentEntry.setCurrencyCode(currencyCode);
		commercePaymentEntry.setPaymentMethodName(paymentMethodName);
		commercePaymentEntry.setPaymentStatus(
			CommercePaymentEntryConstants.STATUS_PENDING);
		commercePaymentEntry.setTransactionCode(transactionCode);

		commercePaymentEntry = commercePaymentEntryPersistence.update(
			commercePaymentEntry);

		_resourceLocalService.addModelResources(
			commercePaymentEntry, serviceContext);

		return commercePaymentEntry;
	}

	@Override
	public void deleteCommercePaymentEntries(long companyId)
		throws PortalException {

		List<CommercePaymentEntry> commercePaymentEntries =
			commercePaymentEntryPersistence.findByCompanyId(companyId);

		for (CommercePaymentEntry commercePaymentEntry :
				commercePaymentEntries) {

			commercePaymentEntryLocalService.deleteCommercePaymentEntry(
				commercePaymentEntry.getCommercePaymentEntryId());
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommercePaymentEntry deleteCommercePaymentEntry(
			long commercePaymentEntryId)
		throws PortalException {

		CommercePaymentEntry commercePaymentEntry =
			commercePaymentEntryPersistence.remove(commercePaymentEntryId);

		_resourceLocalService.deleteResource(
			commercePaymentEntry.getCompanyId(),
			CommercePaymentEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			commercePaymentEntry.getCommercePaymentEntryId());

		_commercePaymentEntryAuditLocalService.deleteCommercePaymentEntryAudits(
			commercePaymentEntryId);

		return commercePaymentEntry;
	}

	@Override
	public List<CommercePaymentEntry> getCommercePaymentEntries(
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<CommercePaymentEntry> orderByComparator) {

		return commercePaymentEntryPersistence.findByC_C_C(
			companyId, classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommercePaymentEntriesCount(
		long companyId, long classNameId, long classPK) {

		return commercePaymentEntryPersistence.countByC_C_C(
			companyId, classNameId, classPK);
	}

	@Override
	public BaseModelSearchResult<CommercePaymentEntry>
		searchCommercePaymentEntries(
			long companyId, String keywords,
			LinkedHashMap<String, Object> params, int start, int end,
			String orderByField, boolean reverse) {

		SearchResponse searchResponse = _searcher.search(
			_getSearchRequest(
				companyId, keywords, params, start, end, orderByField,
				reverse));

		SearchHits searchHits = searchResponse.getSearchHits();

		List<CommercePaymentEntry> commercePaymentEntries =
			TransformUtil.transform(
				searchHits.getSearchHits(),
				searchHit -> {
					Document document = searchHit.getDocument();

					long commercePaymentEntryId = document.getLong(
						Field.ENTRY_CLASS_PK);

					CommercePaymentEntry commercePaymentEntry =
						fetchCommercePaymentEntry(commercePaymentEntryId);

					if (commercePaymentEntry == null) {
						Indexer<CommercePaymentEntry>
							commercePaymentEntryIndexer =
								IndexerRegistryUtil.getIndexer(
									CommercePaymentEntry.class);

						commercePaymentEntryIndexer.delete(
							document.getLong(Field.COMPANY_ID),
							document.getString(Field.UID));
					}

					return commercePaymentEntry;
				});

		return new BaseModelSearchResult<>(
			commercePaymentEntries, searchResponse.getTotalHits());
	}

	@Override
	public CommercePaymentEntry updateCommercePaymentEntry(
			long commercePaymentEntryId, int paymentStatus,
			String transactionCode)
		throws PortalException {

		CommercePaymentEntry commercePaymentEntry =
			commercePaymentEntryLocalService.getCommercePaymentEntry(
				commercePaymentEntryId);

		commercePaymentEntry.setPaymentStatus(paymentStatus);

		if (Validator.isNotNull(transactionCode)) {
			commercePaymentEntry.setTransactionCode(transactionCode);
		}

		return commercePaymentEntryLocalService.updateCommercePaymentEntry(
			commercePaymentEntry);
	}

	private SearchRequest _getSearchRequest(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end, String orderByField, boolean reverse) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.entryClassNames(
			CommercePaymentEntry.class.getName()
		).emptySearchEnabled(
			true
		).highlightEnabled(
			false
		).withSearchContext(
			searchContext -> _populateSearchContext(
				searchContext, companyId, keywords, params)
		);

		if (start != QueryUtil.ALL_POS) {
			searchRequestBuilder.from(start);
			searchRequestBuilder.size(end);
		}

		if (Validator.isNotNull(orderByField)) {
			SortOrder sortOrder = SortOrder.ASC;

			if (reverse) {
				sortOrder = SortOrder.DESC;
			}

			FieldSort fieldSort = _sorts.field(
				_sortFieldBuilder.getSortField(
					CommercePaymentEntry.class, orderByField),
				sortOrder);

			searchRequestBuilder.sorts(fieldSort);
		}

		return searchRequestBuilder.build();
	}

	private void _populateSearchContext(
		SearchContext searchContext, long companyId, String keywords,
		LinkedHashMap<String, Object> params) {

		searchContext.setCompanyId(companyId);

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (MapUtil.isEmpty(params)) {
			return;
		}

		long[] classNameIds = (long[])params.get("classNameIds");

		if (ArrayUtil.isNotEmpty(classNameIds)) {
			searchContext.setAttribute("classNameIds", classNameIds);
		}

		long[] classPKs = (long[])params.get("classPKs");

		if (ArrayUtil.isNotEmpty(classPKs)) {
			searchContext.setAttribute("classPKs", classPKs);
		}

		String[] currencyCodes = (String[])params.get("currencyCodes");

		if (ArrayUtil.isNotEmpty(currencyCodes)) {
			searchContext.setAttribute("currencyCodes", currencyCodes);
		}

		String[] paymentMethodNames = (String[])params.get(
			"paymentMethodNames");

		if (ArrayUtil.isNotEmpty(paymentMethodNames)) {
			searchContext.setAttribute(
				"paymentMethodNames", paymentMethodNames);
		}

		long permissionUserId = GetterUtil.getLong(
			params.get("permissionUserId"));

		if (permissionUserId > 0) {
			searchContext.setUserId(permissionUserId);
		}

		int[] statuses = (int[])params.get("paymentStatuses");

		if (ArrayUtil.isNotEmpty(statuses)) {
			searchContext.setAttribute("paymentStatuses", statuses);
		}

		boolean excludeStatuses = GetterUtil.getBoolean(
			params.get("excludePaymentStatuses"));

		searchContext.setAttribute("excludePaymentStatuses", excludeStatuses);
	}

	@Reference
	private CommercePaymentEntryAuditLocalService
		_commercePaymentEntryAuditLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SortFieldBuilder _sortFieldBuilder;

	@Reference
	private Sorts _sorts;

	@Reference
	private UserLocalService _userLocalService;

}