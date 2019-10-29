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

package com.liferay.account.admin.web.internal.dao.search;

import com.liferay.account.admin.web.internal.display.AccountUserDisplay;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = {})
public class AccountUserDisplaySearchContainerFactory {

	public static SearchContainer create(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		String accountNavigation = ParamUtil.getString(
			liferayPortletRequest, "accountNavigation", "all");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long[] accountEntryIds = null;

		if (accountNavigation.equals("all")) {
			List<AccountEntry> accountEntries =
				_accountEntryLocalService.getAccountEntries(
					themeDisplay.getCompanyId(),
					WorkflowConstants.STATUS_APPROVED, -1, -1, null);

			if (!ListUtil.isEmpty(accountEntries)) {
				List<Long> accountEntryIdList = TransformUtil.transform(
					accountEntries, AccountEntry::getAccountEntryId);

				accountEntryIdList.add(
					AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);

				accountEntryIds = ArrayUtil.toLongArray(accountEntryIdList);
			}
			else {
				accountEntryIds = new long[] {
					AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT
				};
			}
		}
		else if (accountNavigation.equals("accounts")) {
			accountEntryIds = ParamUtil.getLongValues(
				liferayPortletRequest, "accountEntryIds");
		}
		else if (accountNavigation.equals("no-assigned-account")) {
			accountEntryIds = new long[] {
				AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT
			};
		}

		return _create(
			accountEntryIds, "no-users-were-found", liferayPortletRequest,
			liferayPortletResponse);
	}

	public static SearchContainer create(
			long accountEntryId, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		String emptyResultsMessage =
			"there-are-no-users-associated-with-this-account";

		if (_accountUserRetriever.getAccountUsersCount(accountEntryId) > 0) {
			emptyResultsMessage = "no-users-were-found";
		}

		return _create(
			new long[] {accountEntryId}, emptyResultsMessage,
			liferayPortletRequest, liferayPortletResponse);
	}

	@Reference(unbind = "-")
	protected void setAccountEntryLocalService(
		AccountEntryLocalService accountEntryLocalService) {

		_accountEntryLocalService = accountEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setAccountUserRetriever(
		AccountUserRetriever accountUserRetriever) {

		_accountUserRetriever = accountUserRetriever;
	}

	private static SearchContainer _create(
			long[] accountEntryIds, String emptyResultsMessage,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		SearchContainer accountUserDisplaySearchContainer = new SearchContainer(
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

		accountUserDisplaySearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));

		String keywords = ParamUtil.getString(
			liferayPortletRequest, "keywords", null);
		String navigation = ParamUtil.getString(
			liferayPortletRequest, "navigation", "active");

		BaseModelSearchResult<User> baseModelSearchResult =
			_accountUserRetriever.searchAccountUsers(
				accountEntryIds, keywords, _getStatus(navigation),
				accountUserDisplaySearchContainer.getStart(),
				accountUserDisplaySearchContainer.getDelta(),
				accountUserDisplaySearchContainer.getOrderByCol(),
				_isReverseOrder(
					accountUserDisplaySearchContainer.getOrderByType()));

		accountUserDisplaySearchContainer.setResults(
			TransformUtil.transform(
				baseModelSearchResult.getBaseModels(), AccountUserDisplay::of));
		accountUserDisplaySearchContainer.setTotal(
			baseModelSearchResult.getLength());

		return accountUserDisplaySearchContainer;
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

	private static AccountEntryLocalService _accountEntryLocalService;
	private static AccountUserRetriever _accountUserRetriever;

}