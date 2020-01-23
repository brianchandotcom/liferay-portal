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

import com.liferay.account.retriever.AccountOrganizationRetriever;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.OrganizationLocalService;
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

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = AccountOrganizationRetriever.class)
public class AccountOrganizationRetrieverImpl
	implements AccountOrganizationRetriever {

	@Override
	public BaseModelSearchResult<Organization> searchAccountOrganizations(
		long accountEntryId, String keywords, int cur, int delta,
		String sortField, boolean reverse) {

		SearchResponse searchResponse = _searcher.search(
			_getSearchRequest(
				accountEntryId, keywords, cur, delta, sortField, reverse));

		SearchHits searchHits = searchResponse.getSearchHits();

		List<Organization> organizations = TransformUtil.transform(
			searchHits.getSearchHits(),
			searchHit -> {
				Document document = searchHit.getDocument();

				long organizationId = document.getLong("organizationId");

				return _organizationLocalService.getOrganization(
					organizationId);
			});

		return new BaseModelSearchResult<>(
			organizations, searchResponse.getTotalHits());
	}

	private SearchRequest _getSearchRequest(
		long accountEntryId, String keywords, int cur, int delta,
		String sortField, boolean reverse) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.entryClassNames(
			Organization.class.getName()
		).emptySearchEnabled(
			true
		).highlightEnabled(
			false
		).withSearchContext(
			searchContext -> _populateSearchContext(
				searchContext, accountEntryId, keywords)
		);

		if (cur != QueryUtil.ALL_POS) {
			searchRequestBuilder.from(cur);
			searchRequestBuilder.size(delta);
		}

		if (Validator.isNotNull(sortField)) {
			SortOrder sortOrder = SortOrder.ASC;

			if (reverse) {
				sortOrder = SortOrder.DESC;
			}

			FieldSort fieldSort = _sorts.field(
				_sortFieldBuilder.getSortField(
					Organization.class.getName(), sortField),
				sortOrder);

			searchRequestBuilder.sorts(fieldSort);
		}

		return searchRequestBuilder.build();
	}

	private void _populateSearchContext(
		SearchContext searchContext, long accountEntryId, String keywords) {

		searchContext.setCompanyId(CompanyThreadLocal.getCompanyId());

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		searchContext.setAttribute(
			"accountEntryIds", new long[] {accountEntryId});
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SortFieldBuilder _sortFieldBuilder;

	@Reference
	private Sorts _sorts;

}