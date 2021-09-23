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

import com.liferay.account.admin.web.internal.display.AccountRoleDisplay;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.RoleNameComparator;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;

/**
 * @author Pei-Jung Lan
 */
public class AccountRoleDisplaySearchContainerFactory {

	public static SearchContainer<AccountRoleDisplay> create(
		long accountEntryId, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<AccountRoleDisplay> searchContainer =
			new SearchContainer(
				liferayPortletRequest,
				PortletURLUtil.getCurrent(
					liferayPortletRequest, liferayPortletResponse),
				null, "there-are-no-roles");

		searchContainer.setId("accountRoles");
		searchContainer.setOrderByCol("name");

		String orderByType = ParamUtil.getString(
			liferayPortletRequest, "orderByType", "asc");

		searchContainer.setOrderByType(orderByType);

		searchContainer.setRowChecker(
			new AccountRoleRowChecker(liferayPortletResponse));

		String keywords = ParamUtil.getString(
			liferayPortletRequest, "keywords");

		BaseModelSearchResult<AccountRole> baseModelSearchResult =
			AccountRoleLocalServiceUtil.searchAccountRoles(
				themeDisplay.getCompanyId(),
				new long[] {
					accountEntryId, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT
				},
				keywords,
				LinkedHashMapBuilder.<String, Object>put(
					"excludedRoleNames",
					new String[] {
						AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER
					}
				).build(),
				searchContainer.getStart(), searchContainer.getEnd(),
				new RoleNameComparator(orderByType.equals("asc")));

		List<AccountRoleDisplay> accountRoleDisplays = TransformUtil.transform(
			baseModelSearchResult.getBaseModels(),
			accountRole -> {
				if (!AccountRoleConstants.isImpliedRole(
						accountRole.getRole())) {

					return AccountRoleDisplay.of(accountRole);
				}

				return null;
			});

		searchContainer.setResults(accountRoleDisplays);

		searchContainer.setTotal(baseModelSearchResult.getLength());

		return searchContainer;
	}

}