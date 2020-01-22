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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.internal.search.searcher.UserSearchRequestBuilder;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.io.Serializable;

import java.util.Collections;
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
	public long getAccountUsersCount(long accountEntryId) {
		return _accountEntryUserRelLocalService.
			getAccountEntryUserRelsCountByAccountEntryId(accountEntryId);
	}

	@Override
	public BaseModelSearchResult<User> searchAccountUsers(
			long accountEntryId, String keywords, int status, int cur,
			int delta, String sortField, boolean reverse)
		throws PortalException {

		return searchAccountUsers(
			new long[] {accountEntryId}, keywords, status, cur, delta,
			sortField, reverse);
	}

	@Override
	public BaseModelSearchResult<User> searchAccountUsers(
			long accountEntryId, String[] emailAddressDomains, String keywords,
			int status, int cur, int delta, String sortField, boolean reverse)
		throws PortalException {

		Map<String, Serializable> attributes =
			HashMapBuilder.<String, Serializable>put(
				"accountEntryIds", new long[] {accountEntryId}
			).put(
				"emailAddressDomains", emailAddressDomains
			).build();

		return _getUserBaseModelSearchResult(
			_getSearchResponse(
				attributes, cur, delta, keywords, reverse, sortField, status));
	}

	@Override
	public BaseModelSearchResult<User> searchAccountUsers(
			long[] accountEntryIds, String keywords, int status, int cur,
			int delta, String sortField, boolean reverse)
		throws PortalException {

		for (long accountEntryId : accountEntryIds) {
			if ((accountEntryId != AccountConstants.ACCOUNT_ENTRY_ID_ANY) &&
				(accountEntryId != AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT)) {

				_accountEntryLocalService.getAccountEntry(accountEntryId);
			}
		}

		Map<String, Serializable> attributes =
			HashMapBuilder.<String, Serializable>put(
				"accountEntryIds", accountEntryIds
			).build();

		return _getUserBaseModelSearchResult(
			_getSearchResponse(
				attributes, cur, delta, keywords, reverse, sortField, status));
	}

	private SearchResponse _getSearchResponse(
		Map<String, Serializable> attributes, int cur, int delta,
		String keywords, boolean reverse, String sortField, int status) {

		SearchRequest searchRequest = _userSearchRequestBuilder.attributes(
			attributes
		).cur(
			cur
		).delta(
			delta
		).keywords(
			keywords
		).reverse(
			reverse
		).sortField(
			sortField
		).status(
			status
		).build();

		return _searcher.search(searchRequest);
	}

	private BaseModelSearchResult<User> _getUserBaseModelSearchResult(
		SearchResponse searchResponse) {

		SearchHits searchHits = searchResponse.getSearchHits();

		if (searchHits == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("SearchHits object is null");
			}

			return new BaseModelSearchResult<>(
				Collections.<User>emptyList(), 0);
		}

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

	private static final Log _log = LogFactoryUtil.getLog(
		AccountUserRetrieverImpl.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserSearchRequestBuilder _userSearchRequestBuilder;

}