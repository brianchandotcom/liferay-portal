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
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.portlet.usersadmin.search.UserSearch;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = {})
public class AccountUserDisplaySearchContainerFactory {

	public static SearchContainer create(
		long accountEntryId, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		SearchContainer accountUserDisplaySearchContainer = new UserSearch(
			liferayPortletRequest, liferayPortletResponse.createRenderURL());

		accountUserDisplaySearchContainer.setEmptyResultsMessage(
			"there-are-no-users-associated-with-this-account");
		accountUserDisplaySearchContainer.setId("accountUsers");
		accountUserDisplaySearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));

		List<User> accountUsers = _accountUserRetriever.getAccountUsers(
			accountEntryId);

		accountUserDisplaySearchContainer.setTotal(accountUsers.size());

		accountUsers = ListUtil.sort(
			accountUsers,
			accountUserDisplaySearchContainer.getOrderByComparator());

		accountUsers = ListUtil.subList(
			accountUsers, accountUserDisplaySearchContainer.getStart(),
			accountUserDisplaySearchContainer.getEnd());

		List<AccountUserDisplay> accountUserDisplays = TransformUtil.transform(
			accountUsers, AccountUserDisplay::of);

		accountUserDisplaySearchContainer.setResults(accountUserDisplays);

		return accountUserDisplaySearchContainer;
	}

	@Reference(unbind = "-")
	protected void setAccountUserRetriever(
		AccountUserRetriever accountUserRetriever) {

		_accountUserRetriever = accountUserRetriever;
	}

	private static AccountUserRetriever _accountUserRetriever;

}