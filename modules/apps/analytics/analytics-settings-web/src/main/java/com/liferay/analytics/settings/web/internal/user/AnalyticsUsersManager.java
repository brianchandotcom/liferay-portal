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

package com.liferay.analytics.settings.web.internal.user;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = AnalyticsUsersManager.class)
public class AnalyticsUsersManager {

	public int getCompanyUsersCount(long companyId) {
		if (!_isIndexerEnabled()) {
			int activeUsersCount = _userLocalService.getUsersCount(
				companyId, false, WorkflowConstants.STATUS_APPROVED);

			int analyticsAdministratorsCount = 0;

			Role analyticsAdministratorRole =
				_fetchAnalyticsAdministratorRole();

			if (analyticsAdministratorRole != null) {
				try {
					analyticsAdministratorsCount =
						_userLocalService.getRoleUsersCount(
							analyticsAdministratorRole.getRoleId(),
							WorkflowConstants.STATUS_APPROVED);
				}
				catch (Exception e) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get analytics administrators count");
					}
				}
			}

			return activeUsersCount - analyticsAdministratorsCount;
		}

		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder();

		SearchRequest searchRequest = searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setCompanyId(companyId);

				_populateSearchContext(searchContext);
			}
		).build();

		return _getUsersCount(searchRequest);
	}

	public int getOrganizationsAndUserGroupsUsersCount(
		long[] organizationIds, long[] userGroupIds) {

		if (ArrayUtil.isEmpty(organizationIds) &&
			ArrayUtil.isEmpty(userGroupIds)) {

			return 0;
		}

		if (!_isIndexerEnabled()) {
			return _userLocalService.getOrganizationsAndUserGroupsUsersCount(
				organizationIds, userGroupIds);
		}

		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder();

		SearchRequest searchRequest = searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					"selectedOrganizationIds", organizationIds);
				searchContext.setAttribute(
					"selectedUserGroupIds", userGroupIds);
				searchContext.setCompanyId(CompanyThreadLocal.getCompanyId());

				_populateSearchContext(searchContext);
			}
		).build();

		return _getUsersCount(searchRequest);
	}

	public int getOrganizationUsersCount(long organizationId) {
		if (!_isIndexerEnabled()) {
			try {
				return _userLocalService.getOrganizationUsersCount(
					organizationId, WorkflowConstants.STATUS_APPROVED);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to get organization users count");
				}

				return 0;
			}
		}

		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder();

		SearchRequest searchRequest = searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					"selectedOrganizationIds", new long[] {organizationId});
				searchContext.setCompanyId(CompanyThreadLocal.getCompanyId());

				_populateSearchContext(searchContext);
			}
		).build();

		return _getUsersCount(searchRequest);
	}

	public int getUserGroupUsersCount(long userGroupId) {
		if (!_isIndexerEnabled()) {
			try {
				return _userLocalService.getUserGroupUsersCount(
					userGroupId, WorkflowConstants.STATUS_APPROVED);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to get user group users count");
				}

				return 0;
			}
		}

		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder();

		SearchRequest searchRequest = searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					"selectedUserGroupIds", new long[] {userGroupId});
				searchContext.setCompanyId(CompanyThreadLocal.getCompanyId());

				_populateSearchContext(searchContext);
			}
		).build();

		return _getUsersCount(searchRequest);
	}

	private Role _fetchAnalyticsAdministratorRole() {
		return _roleLocalService.fetchRole(
			CompanyThreadLocal.getCompanyId(),
			RoleConstants.ANALYTICS_ADMINISTRATOR);
	}

	private SearchRequestBuilder _getSearchRequestBuilder() {
		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		return searchRequestBuilder.entryClassNames(
			User.class.getName()
		).emptySearchEnabled(
			true
		).highlightEnabled(
			false
		);
	}

	private int _getUsersCount(SearchRequest searchRequest) {
		SearchResponse searchResponse = _searcher.search(searchRequest);

		SearchHits searchHits = searchResponse.getSearchHits();

		Long totalHits = Long.valueOf(searchHits.getTotalHits());

		return totalHits.intValue();
	}

	private boolean _isIndexerEnabled() {
		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		return indexer.isIndexerEnabled();
	}

	private void _populateSearchContext(SearchContext searchContext) {
		Role role = _fetchAnalyticsAdministratorRole();

		if (role != null) {
			searchContext.setAttribute(
				"excludedRoleIds", new long[] {role.getRoleId()});
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsUsersManager.class);

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private UserLocalService _userLocalService;

}