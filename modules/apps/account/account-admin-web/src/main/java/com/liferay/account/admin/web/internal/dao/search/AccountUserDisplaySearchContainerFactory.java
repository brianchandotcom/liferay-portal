/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.dao.search;

import com.liferay.account.admin.web.internal.display.AccountUserDisplay;
import com.liferay.account.admin.web.internal.security.permission.resource.AccountEntryPermission;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.util.List;
import java.util.Objects;

/**
 * @author Pei-Jung Lan
 */
public class AccountUserDisplaySearchContainerFactory {

	public static SearchContainer<AccountUserDisplay> create(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		String accountEntriesNavigation = ParamUtil.getString(
			liferayPortletRequest, "accountEntriesNavigation", "any-account");

		long[] accountEntryIds = null;

		if (accountEntriesNavigation.equals("any-account")) {
			accountEntryIds = new long[] {
				AccountConstants.ACCOUNT_ENTRY_ID_ANY
			};
		}
		else if (accountEntriesNavigation.equals("selected-accounts")) {
			accountEntryIds = ParamUtil.getLongValues(
				liferayPortletRequest, "accountEntryIds");
		}
		else if (accountEntriesNavigation.equals("no-assigned-account")) {
			accountEntryIds = new long[0];
		}

		return _create(
			accountEntryIds, "no-users-were-found", liferayPortletRequest,
			liferayPortletResponse);
	}

	public static SearchContainer<AccountUserDisplay> create(
			long accountEntryId, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		String emptyResultsMessage =
			"there-are-no-users-associated-with-this-account";

		AccountUserRetriever accountUserRetriever =
			_accountUserRetrieverSnapshot.get();

		if (accountUserRetriever.getAccountUsersCount(accountEntryId) > 0) {
			emptyResultsMessage = "no-users-were-found";
		}

		SearchContainer<AccountUserDisplay> searchContainer = _create(
			new long[] {accountEntryId}, emptyResultsMessage,
			liferayPortletRequest, liferayPortletResponse);

		if (!AccountEntryPermission.contains(
				PermissionCheckerFactoryUtil.create(
					PortalUtil.getUser(liferayPortletRequest)),
				accountEntryId, ActionKeys.MANAGE_USERS)) {

			searchContainer.setRowChecker(null);
		}

		return searchContainer;
	}

	public static SearchContainer<AccountUserDisplay> create(
			long accountEntryId, long roleId,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		String emptyResultsMessage =
			"there-are-no-users-associated-with-this-role";

		AccountEntryLocalService accountEntryLocalService =
			_accountEntryLocalServiceSnapshot.get();

		AccountEntry accountEntry = accountEntryLocalService.getAccountEntry(
			accountEntryId);

		UserGroupRoleLocalService userGroupRoleLocalService =
			_userGroupRoleLocalServiceSnapshot.get();

		List<UserGroupRole> userGroupRoles =
			userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
				accountEntry.getAccountEntryGroupId(), roleId);

		if (ListUtil.isNotEmpty(userGroupRoles)) {
			emptyResultsMessage = "no-users-were-found";
		}

		return _create(
			new long[] {accountEntryId}, emptyResultsMessage,
			liferayPortletRequest, liferayPortletResponse);
	}

	private static SearchContainer<AccountUserDisplay> _create(
			long[] accountEntryIds, String emptyResultsMessage,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		SearchContainer<AccountUserDisplay> accountUserDisplaySearchContainer =
			new SearchContainer(
				liferayPortletRequest,
				PortletURLUtil.getCurrent(
					liferayPortletRequest, liferayPortletResponse),
				null, emptyResultsMessage);

		accountUserDisplaySearchContainer.setId("accountUsers");

		String orderByCol = ParamUtil.getString(
			liferayPortletRequest, "orderByCol", "last-name");

		accountUserDisplaySearchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			liferayPortletRequest, "orderByType", "asc");

		accountUserDisplaySearchContainer.setOrderByType(orderByType);

		String keywords = ParamUtil.getString(
			liferayPortletRequest, "keywords", null);

		BaseModelSearchResult<User> baseModelSearchResult;

		long accountRoleId = ParamUtil.getLong(
			liferayPortletRequest, "accountRoleId");

		if (accountRoleId > 0) {
			baseModelSearchResult = _getBaseModelSearchResult(
				accountEntryIds[0], accountRoleId, keywords,
				accountUserDisplaySearchContainer.getStart(),
				accountUserDisplaySearchContainer.getEnd(), orderByCol,
				orderByType);
		}
		else {
			String navigation = ParamUtil.getString(
				liferayPortletRequest, "navigation", "active");

			baseModelSearchResult = _getBaseModelSearchResult(
				accountEntryIds, keywords, _getStatus(navigation),
				accountUserDisplaySearchContainer.getStart(),
				accountUserDisplaySearchContainer.getDelta(), orderByCol,
				orderByType);
		}

		accountUserDisplaySearchContainer.setResultsAndTotal(
			() -> TransformUtil.transform(
				baseModelSearchResult.getBaseModels(), AccountUserDisplay::of),
			baseModelSearchResult.getLength());
		accountUserDisplaySearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));

		return accountUserDisplaySearchContainer;
	}

	private static BaseModelSearchResult<User> _getBaseModelSearchResult(
			long accountEntryId, long accountRoleId, String keywords, int start,
			int end, String orderByCol, String orderByType)
		throws PortalException {

		AccountUserRetriever accountUserRetriever =
			_accountUserRetrieverSnapshot.get();

		UsersAdmin usersAdmin = _usersAdminSnapshot.get();

		return accountUserRetriever.searchAccountRoleUsers(
			accountEntryId, accountRoleId, keywords, start, end,
			usersAdmin.getUserOrderByComparator(orderByCol, orderByType));
	}

	private static BaseModelSearchResult<User> _getBaseModelSearchResult(
			long[] accountEntryIds, String keywords, int status, int start,
			int delta, String orderByCol, String orderByType)
		throws PortalException {

		AccountUserRetriever accountUserRetriever =
			_accountUserRetrieverSnapshot.get();

		return accountUserRetriever.searchAccountUsers(
			accountEntryIds, keywords, null, status, start, delta, orderByCol,
			_isReverseOrder(orderByType));
	}

	private static int _getStatus(String navigation) {
		if (Objects.equals(navigation, "inactive")) {
			return WorkflowConstants.STATUS_INACTIVE;
		}

		return WorkflowConstants.STATUS_APPROVED;
	}

	private static boolean _isReverseOrder(String orderByType) {
		if (Objects.equals(orderByType, "desc")) {
			return true;
		}

		return false;
	}

	private static final Snapshot<AccountEntryLocalService>
		_accountEntryLocalServiceSnapshot = new Snapshot<>(
			AccountUserDisplaySearchContainerFactory.class,
			AccountEntryLocalService.class);
	private static final Snapshot<AccountUserRetriever>
		_accountUserRetrieverSnapshot = new Snapshot<>(
			AccountUserDisplaySearchContainerFactory.class,
			AccountUserRetriever.class);
	private static final Snapshot<UserGroupRoleLocalService>
		_userGroupRoleLocalServiceSnapshot = new Snapshot<>(
			AccountUserDisplaySearchContainerFactory.class,
			UserGroupRoleLocalService.class);
	private static final Snapshot<UsersAdmin> _usersAdminSnapshot =
		new Snapshot<>(
			AccountUserDisplaySearchContainerFactory.class, UsersAdmin.class);

}