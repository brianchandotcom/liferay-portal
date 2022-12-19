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

package com.liferay.commerce.product.item.selector.web.internal.display.context;

import com.liferay.commerce.product.item.selector.web.internal.CPDefinitionItemSelectorView;
import com.liferay.commerce.product.item.selector.web.internal.search.CommerceChannelItemSelectorChecker;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceChannelItemSelectorViewDisplayContext
	extends BaseCPItemSelectorViewDisplayContext {

	public CommerceChannelItemSelectorViewDisplayContext(
		CommerceChannelRelService commerceChannelRelService,
		CommerceChannelService commerceChannelService,
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		String itemSelectedEventName) {

		super(
			httpServletRequest, portletURL, itemSelectedEventName,
			CPDefinitionItemSelectorView.class.getSimpleName());

		_commerceChannelRelService = commerceChannelRelService;
		_commerceChannelService = commerceChannelService;
		_portletURL = portletURL;
		_itemSelectedEventName = itemSelectedEventName;
	}

	@Override
	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	@Override
	public String getOrderByCol() {
		return ParamUtil.getString(
			cpRequestHelper.getRenderRequest(),
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "createDate_sortable");
	}

	@Override
	public String getOrderByType() {
		return ParamUtil.getString(
			cpRequestHelper.getRenderRequest(),
			SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "desc");
	}

	@Override
	public PortletURL getPortletURL() {
		_portletURL.setParameter("className", _getClassName());
		_portletURL.setParameter("classPK", String.valueOf(_getClassPK()));

		return _portletURL;
	}

	@Override
	public SearchContainer<CommerceChannel> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			cpRequestHelper.getLiferayPortletRequest(), getPortletURL(), null,
			"there-are-no-channels");

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setResultsAndTotal(
			() -> _commerceChannelService.search(
				cpRequestHelper.getCompanyId(), getKeywords(),
				_searchContainer.getStart(), _searchContainer.getEnd(), null),
			_commerceChannelService.searchCommerceChannelsCount(
				cpRequestHelper.getCompanyId(), getKeywords()));
		_searchContainer.setRowChecker(
			new CommerceChannelItemSelectorChecker(
				cpRequestHelper.getRenderResponse(),
				_getCheckedCommerceChannelIds()));

		return _searchContainer;
	}

	private long[] _getCheckedCommerceChannelIds() {
		return ParamUtil.getLongValues(
			cpRequestHelper.getRenderRequest(), "checkedCommerceChannelIds");
	}

	private String _getClassName() {
		return ParamUtil.getString(
			cpRequestHelper.getRenderRequest(), "className");
	}

	private long _getClassPK() {
		return ParamUtil.getLong(cpRequestHelper.getRenderRequest(), "classPK");
	}

	private final CommerceChannelRelService _commerceChannelRelService;
	private final CommerceChannelService _commerceChannelService;
	private final String _itemSelectedEventName;
	private final PortletURL _portletURL;
	private SearchContainer<CommerceChannel> _searchContainer;

}