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

package com.liferay.commerce.term.web.internal.dao.search;

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalServiceUtil;
import com.liferay.commerce.term.web.internal.display.CommerceTermEntryDisplay;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

/**
 * @author Andrea Sbarra
 */
public class AccountEntryCommerceTermEntryDisplaySearchContainerFactory {

	public static SearchContainer<CommerceTermEntryDisplay> create(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		SearchContainer<CommerceTermEntryDisplay> searchContainer =
			new SearchContainer(
				liferayPortletRequest,
				PortletURLUtil.getCurrent(
					liferayPortletRequest, liferayPortletResponse),
				null, "no-terms-were-found");

		searchContainer.setId("accountEntryCommerceTermEntry");
		searchContainer.setOrderByCol(
			SearchOrderByUtil.getOrderByCol(
				liferayPortletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
				"commerce-term-order-by-col", "name"));
		searchContainer.setOrderByType(
			SearchOrderByUtil.getOrderByType(
				liferayPortletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
				"commerce-term-order-by-type", "asc"));

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		BaseModelSearchResult<CommerceTermEntry> baseModelSearchResult =
			CommerceTermEntryLocalServiceUtil.searchCommerceTermEntries(
				themeDisplay.getCompanyId(),
				ParamUtil.getLong(liferayPortletRequest, "accountEntryId"),
				ParamUtil.getString(liferayPortletRequest, "type"),
				ParamUtil.getString(liferayPortletRequest, "keywords"),
				searchContainer.getStart(), searchContainer.getEnd(),
				_getSort(
					searchContainer.getOrderByCol(),
					searchContainer.getOrderByType()));

		searchContainer.setResultsAndTotal(
			() -> TransformUtil.transform(
				baseModelSearchResult.getBaseModels(),
				CommerceTermEntryDisplay::of),
			baseModelSearchResult.getLength());

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));

		return searchContainer;
	}

	private static Sort _getSort(String orderByCol, String orderByType) {
		return SortFactoryUtil.create(
			orderByCol, Objects.equals("desc", orderByType));
	}

}