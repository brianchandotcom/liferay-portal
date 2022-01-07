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

import com.liferay.account.admin.web.internal.display.AccountGroupDisplay;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountGroupLocalServiceUtil;
import com.liferay.account.service.AccountGroupRelLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

/**
 * @author Erick Monteiro
 */
public class AccountEntryAccountGroupSearchContainerFactory {

	public static SearchContainer<AccountGroupDisplay> create(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		SearchContainer<AccountGroupDisplay>
			accountGroupDisplaySearchContainer = new SearchContainer(
				liferayPortletRequest,
				PortletURLUtil.getCurrent(
					liferayPortletRequest, liferayPortletResponse),
				null, "no-account-groups-were-found");

		accountGroupDisplaySearchContainer.setId(
			"accountEntryAccountGroupsSearchContainer");

		accountGroupDisplaySearchContainer.setResultsAndTotal(
			() -> TransformUtil.transform(
				TransformUtil.transform(
					AccountGroupRelLocalServiceUtil.getAccountGroupRels(
						AccountEntry.class.getName(),
						ParamUtil.getLong(
							liferayPortletRequest, "accountEntryId"),
						accountGroupDisplaySearchContainer.getStart(),
						accountGroupDisplaySearchContainer.getEnd(), null),
					accountGroupRel ->
						AccountGroupLocalServiceUtil.getAccountGroup(
							accountGroupRel.getAccountGroupId())),
				AccountGroupDisplay::of),
			AccountGroupRelLocalServiceUtil.getAccountGroupRelsCount(
				AccountEntry.class.getName(),
				ParamUtil.getLong(liferayPortletRequest, "accountEntryId")));

		return accountGroupDisplaySearchContainer;
	}

}