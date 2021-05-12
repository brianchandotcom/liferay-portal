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
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.io.Serializable;

import java.util.Collections;
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
	public long getAccountUsersCount(long accountEntryId) {
		return _accountEntryUserRelLocalService.
			getAccountEntryUserRelsCountByAccountEntryId(accountEntryId);
	}

	@Override
	public BaseModelSearchResult<User> searchAccountRoleUsers(
			long accountEntryId, long accountRoleId, String keywords, int start,
			int end, OrderByComparator<User> orderByComparator)
		throws PortalException {

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			accountEntryId);
		AccountRole accountRole = _accountRoleLocalService.getAccountRole(
			accountRoleId);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"userGroupRole",
				(Object)new Long[] {
					accountEntry.getAccountEntryGroupId(),
					accountRole.getRoleId()
				}
			).build();

		List<User> users = _userLocalService.search(
			accountEntry.getCompanyId(), keywords,
			WorkflowConstants.STATUS_APPROVED, params, start, end,
			orderByComparator);

		int total = _userLocalService.searchCount(
			accountEntry.getCompanyId(), keywords,
			WorkflowConstants.STATUS_APPROVED, params);

		return new BaseModelSearchResult<>(users, total);
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

		return _getUserBaseModelSearchResult(
			_getSearchResponse(
				HashMapBuilder.<String, Serializable>put(
					"accountEntryIds", new long[] {accountEntryId}
				).put(
					"emailAddressDomains", emailAddressDomains
				).build(),
				cur, delta, keywords, reverse, sortField, status));
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

		return _getUserBaseModelSearchResult(
			_getSearchResponse(
				HashMapBuilder.<String, Serializable>put(
					"accountEntryIds", accountEntryIds
				).build(),
				cur, delta, keywords, reverse, sortField, status));
	}

	private SearchResponse _getSearchResponse(
		Map<String, Serializable> attributes, int cur, int delta,
		String keywords, boolean reverse, String sortField, int status) {

		return _searcher.search(
			_userSearchRequestBuilder.attributes(
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
			).build());
	}

	private BaseModelSearchResult<User> _getUserBaseModelSearchResult(
		SearchResponse searchResponse) {

		SearchHits searchHits = searchResponse.getSearchHits();

		if (searchHits == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Search hits is null");
			}

			return new BaseModelSearchResult<>(
				Collections.<User>emptyList(), 0);
		}

		List<User> users = TransformUtil.transform(
			searchHits.getSearchHits(),
			searchHit -> {
				Document document = searchHit.getDocument();

				long userId = document.getLong("userId");

				User user = _userLocalService.fetchUser(userId);

				if (user == null) {
					Indexer<User> indexer = IndexerRegistryUtil.getIndexer(
						User.class);

					indexer.delete(
						document.getLong(Field.COMPANY_ID),
						document.getString(Field.UID));
				}

				return user;
			});

		return new BaseModelSearchResult<>(users, searchHits.getTotalHits());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountUserRetrieverImpl.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserSearchRequestBuilder _userSearchRequestBuilder;

}