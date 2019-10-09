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

package com.liferay.account.internal.retriever;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.UserLocalService;
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
import com.liferay.portal.vulcan.util.TransformUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = AccountUserRetriever.class)
public class AccountUserRetrieverImpl implements AccountUserRetriever {

	@Override
	public List<User> getAccountUsers(long accountEntryId) {
		return TransformUtil.transform(
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountEntryId(accountEntryId),
			accountEntryUserRel -> _userLocalService.getUserById(
				accountEntryUserRel.getAccountUserId()));
	}

	@Override
	public BaseModelSearchResult<User> searchAccountUsers(
			long accountEntryId, String keywords, int status, int cur,
			int delta, String sortField, boolean reverse)
		throws PortalException {

		SearchResponse searchResponse = _getSearchResponse(
			accountEntryId, keywords, status, cur, delta, sortField, reverse);

		SearchHits searchHits = searchResponse.getSearchHits();

		List<User> users = TransformUtil.transform(
			searchHits.getSearchHits(),
			searchHit -> {
				Document document = searchHit.getDocument();

				long userId = document.getLong("userId");

				return _userLocalService.getUser(userId);
			});

		return new BaseModelSearchResult<>(
			users, searchResponse.getTotalHits());
	}

	private SearchResponse _getSearchResponse(
			long accountEntryId, String keywords, int status, int cur,
			int delta, String sortField, boolean reverse)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			accountEntryId);

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.entryClassNames(
			User.class.getName()
		).withSearchContext(
			searchContext -> _populateSearchContext(
				searchContext, accountEntry, keywords, status)
		).emptySearchEnabled(
			true
		).highlightEnabled(
			false
		);

		if (cur != QueryUtil.ALL_POS) {
			searchRequestBuilder.from(
				cur
			).size(
				delta
			);
		}

		if (Validator.isNotNull(sortField)) {
			SortOrder sortOrder = SortOrder.ASC;

			if (reverse) {
				sortOrder = SortOrder.DESC;
			}

			FieldSort sort = _sorts.field(
				_sortFieldBuilder.getSortField(User.class.getName(), sortField),
				sortOrder);

			searchRequestBuilder.sorts(sort);
		}

		SearchRequest searchRequest = searchRequestBuilder.build();

		return _searcher.search(searchRequest);
	}

	private void _populateSearchContext(
		SearchContext searchContext, AccountEntry accountEntry, String keywords,
		int status) {

		boolean andSearch = false;

		if (Validator.isNull(keywords)) {
			andSearch = true;
		}
		else {
			searchContext.setKeywords(keywords);
		}

		searchContext.setAndSearch(andSearch);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(
			"accountEntryIds", new long[] {accountEntry.getAccountEntryId()});
		attributes.put("city", keywords);
		attributes.put("country", keywords);
		attributes.put("emailAddress", keywords);
		attributes.put("firstName", keywords);
		attributes.put("fullName", keywords);
		attributes.put("lastName", keywords);
		attributes.put("middleName", keywords);
		attributes.put("params", new LinkedHashMap<>());
		attributes.put("region", keywords);
		attributes.put("screenName", keywords);
		attributes.put("status", status);
		attributes.put("street", keywords);
		attributes.put("zip", keywords);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(accountEntry.getCompanyId());
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

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