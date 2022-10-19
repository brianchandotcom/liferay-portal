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
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

/**
 * @author Erick Monteiro
 */
public class AccountEntryAccountGroupSearchContainerFactory {

	public static SearchContainer<AccountGroupDisplay> create(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		SearchContainer<AccountGroupDisplay>
			accountGroupDisplaySearchContainer = new SearchContainer(
				liferayPortletRequest,
				PortletURLUtil.getCurrent(
					liferayPortletRequest, liferayPortletResponse),
				null, "no-account-groups-were-found");

		accountGroupDisplaySearchContainer.setId(
			"accountEntryAccountGroupsSearchContainer");
		accountGroupDisplaySearchContainer.setOrderByCol(
			SearchOrderByUtil.getOrderByCol(
				liferayPortletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
				"order-by-col", "name"));
		accountGroupDisplaySearchContainer.setOrderByType(
			SearchOrderByUtil.getOrderByType(
				liferayPortletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
				"order-by-type", "asc"));

		String keywords = ParamUtil.getString(
			liferayPortletRequest, "keywords");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		BaseModelSearchResult<AccountGroup> baseModelSearchResult =
			AccountGroupLocalServiceUtil.searchAccountGroups(
				themeDisplay.getCompanyId(), keywords,
				LinkedHashMapBuilder.<String, Object>put(
					"accountEntryIds",
					ParamUtil.getLongValues(
						liferayPortletRequest, "accountEntryId")
				).build(),
				accountGroupDisplaySearchContainer.getStart(),
				accountGroupDisplaySearchContainer.getEnd(),
				OrderByComparatorFactoryUtil.create(
					"AccountGroup",
					accountGroupDisplaySearchContainer.getOrderByCol(),
					Objects.equals(
						accountGroupDisplaySearchContainer.getOrderByType(),
						"asc")));

		accountGroupDisplaySearchContainer.setResultsAndTotal(
			() -> TransformUtil.transform(
				baseModelSearchResult.getBaseModels(), AccountGroupDisplay::of),
			baseModelSearchResult.getLength());

		return accountGroupDisplaySearchContainer;
	}

}