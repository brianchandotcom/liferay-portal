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

import com.liferay.account.admin.web.internal.display.AddressDisplay;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * @author Pei-Jung Lan
 */
public class AccountEntryAddressDisplaySearchContainerFactory {

	public static SearchContainer<AddressDisplay> create(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		SearchContainer<AddressDisplay> searchContainer = new SearchContainer(
			liferayPortletRequest,
			PortletURLUtil.getCurrent(
				liferayPortletRequest, liferayPortletResponse),
			null, "no-addresses-were-found");

		searchContainer.setId("accountEntryAddresses");
		searchContainer.setOrderByCol(
			SearchOrderByUtil.getOrderByCol(
				liferayPortletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
				"address-order-by-col", "name"));
		searchContainer.setOrderByType(
			SearchOrderByUtil.getOrderByType(
				liferayPortletRequest, AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
				"address-order-by-type", "asc"));
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));

		String keywords = ParamUtil.getString(
			liferayPortletRequest, "keywords");

		String type = ParamUtil.getString(liferayPortletRequest, "type");

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		if (Validator.isNotNull(type) && !type.equals("all")) {
			params.put(
				"typeNames", new String[] {type, "billing-and-shipping"});
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		BaseModelSearchResult<Address> baseModelSearchResult =
			AddressLocalServiceUtil.searchAddresses(
				themeDisplay.getCompanyId(), AccountEntry.class.getName(),
				ParamUtil.getLong(liferayPortletRequest, "accountEntryId"),
				keywords, params, searchContainer.getStart(),
				searchContainer.getEnd(),
				_getSort(
					searchContainer.getOrderByCol(),
					searchContainer.getOrderByType()));

		searchContainer.setResultsAndTotal(
			() -> TransformUtil.transform(
				baseModelSearchResult.getBaseModels(), AddressDisplay::of),
			baseModelSearchResult.getLength());

		return searchContainer;
	}

	private static Sort _getSort(String orderByCol, String orderByType) {
		return SortFactoryUtil.create(
			orderByCol, Objects.equals("desc", orderByType));
	}

}