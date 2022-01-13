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

package com.liferay.commerce.account.item.selector.web.internal.display.context;

import com.liferay.commerce.account.item.selector.web.internal.display.context.helper.CommerceAccountItemSelectorRequestHelper;
import com.liferay.commerce.account.item.selector.web.internal.search.CommerceAccountGroupItemSelectorChecker;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountGroupItemSelectorViewDisplayContext {

	public CommerceAccountGroupItemSelectorViewDisplayContext(
		CommerceAccountGroupLocalService commerceAccountGroupLocalService,
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName) {

		_commerceAccountGroupLocalService = commerceAccountGroupLocalService;
		_portletURL = portletURL;
		_itemSelectedEventName = itemSelectedEventName;

		_commerceAccountItemSelectorRequestHelper =
			new CommerceAccountItemSelectorRequestHelper(httpServletRequest);
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_commerceAccountItemSelectorRequestHelper.getRenderRequest(),
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "createDate_sortable");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_commerceAccountItemSelectorRequestHelper.getRenderRequest(),
			SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "desc");
	}

	public PortletURL getPortletURL() {
		_portletURL.setParameter(
			"checkedCommerceAccountGroupIds",
			ParamUtil.getString(
				_commerceAccountItemSelectorRequestHelper.getRenderRequest(),
				"checkedCommerceAccountGroupIds"));

		return _portletURL;
	}

	public SearchContainer<CommerceAccountGroup> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_commerceAccountItemSelectorRequestHelper.
				getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-account-groups");

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setResultsAndTotal(
			() -> _commerceAccountGroupLocalService.search(
				_commerceAccountItemSelectorRequestHelper.getCompanyId(),
				getKeywords(), _searchContainer.getStart(),
				_searchContainer.getEnd(), null),
			_commerceAccountGroupLocalService.searchCommerceAccountsGroupCount(
				_commerceAccountItemSelectorRequestHelper.getCompanyId(),
				getKeywords()));
		_searchContainer.setRowChecker(
			new CommerceAccountGroupItemSelectorChecker(
				_commerceAccountItemSelectorRequestHelper.getRenderResponse(),
				_getCheckedCommerceAccountGroupIds()));

		return _searchContainer;
	}

	protected String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			_commerceAccountItemSelectorRequestHelper.getRenderRequest(),
			"keywords");

		return _keywords;
	}

	private long[] _getCheckedCommerceAccountGroupIds() {
		return ParamUtil.getLongValues(
			_commerceAccountItemSelectorRequestHelper.getRenderRequest(),
			"checkedCommerceAccountGroupIds");
	}

	private final CommerceAccountGroupLocalService
		_commerceAccountGroupLocalService;
	private final CommerceAccountItemSelectorRequestHelper
		_commerceAccountItemSelectorRequestHelper;
	private final String _itemSelectedEventName;
	private String _keywords;
	private final PortletURL _portletURL;
	private SearchContainer<CommerceAccountGroup> _searchContainer;

}