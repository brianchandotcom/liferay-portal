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
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.constants.SearchContextAttributes;

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
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get analytics administrators count");
					}
				}
			}

			return activeUsersCount - analyticsAdministratorsCount;
		}

		SearchContext searchContext = _getSearchContext();

		searchContext.setCompanyId(companyId);

		_populateSearchContext(searchContext);

		return _getUsersCount(searchContext);
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

		SearchContext searchContext = _getSearchContext();

		searchContext.setAttribute("selectedOrganizationIds", organizationIds);
		searchContext.setAttribute("selectedUserGroupIds", userGroupIds);
		searchContext.setCompanyId(CompanyThreadLocal.getCompanyId());

		_populateSearchContext(searchContext);

		return _getUsersCount(searchContext);
	}

	public int getOrganizationUsersCount(long organizationId) {
		if (!_isIndexerEnabled()) {
			try {
				return _userLocalService.getOrganizationUsersCount(
					organizationId, WorkflowConstants.STATUS_APPROVED);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to get organization users count");
				}

				return 0;
			}
		}

		SearchContext searchContext = _getSearchContext();

		searchContext.setAttribute(
			"selectedOrganizationIds", new long[] {organizationId});
		searchContext.setCompanyId(CompanyThreadLocal.getCompanyId());

		_populateSearchContext(searchContext);

		return _getUsersCount(searchContext);
	}

	public int getUserGroupUsersCount(long userGroupId) {
		if (!_isIndexerEnabled()) {
			try {
				return _userLocalService.getUserGroupUsersCount(
					userGroupId, WorkflowConstants.STATUS_APPROVED);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to get user group users count");
				}

				return 0;
			}
		}

		SearchContext searchContext = _getSearchContext();

		searchContext.setAttribute(
			"selectedUserGroupIds", new long[] {userGroupId});
		searchContext.setCompanyId(CompanyThreadLocal.getCompanyId());

		_populateSearchContext(searchContext);

		return _getUsersCount(searchContext);
	}

	private Role _fetchAnalyticsAdministratorRole() {
		return _roleLocalService.fetchRole(
			CompanyThreadLocal.getCompanyId(),
			RoleConstants.ANALYTICS_ADMINISTRATOR);
	}

	private SearchContext _getSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			SearchContextAttributes.ATTRIBUTE_KEY_EMPTY_SEARCH, Boolean.TRUE);
		searchContext.setEntryClassNames(new String[] {User.class.getName()});

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);

		return searchContext;
	}

	private int _getUsersCount(SearchContext searchContext) {
		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		try {
			Hits hits = indexer.search(searchContext);

			return hits.getLength();
		}
		catch (SearchException searchException) {
			_log.error(searchException, searchException);

			return 0;
		}
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
	private UserLocalService _userLocalService;

}