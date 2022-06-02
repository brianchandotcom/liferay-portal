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

package com.liferay.client.extension.type.item.selector.web.internal.item.selector;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.item.selector.CETItemSelectorReturnType;
import com.liferay.client.extension.type.item.selector.criterion.CETItemSelectorCriterion;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class CETItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<CET> {

	public CETItemSelectorViewDescriptor(
		CETManager cetManager,
		CETItemSelectorCriterion cetItemSelectorCriterion,
		HttpServletRequest httpServletRequest, PortletURL portletURL) {

		_cetManager = cetManager;
		_cetItemSelectorCriterion = cetItemSelectorCriterion;
		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "descriptive";
	}

	@Override
	public ItemDescriptor getItemDescriptor(CET cet) {
		return new CETItemDescriptor(cet);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return _supportedItemSelectorReturnType;
	}

	@Override
	public SearchContainer<CET> getSearchContainer() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<CET> searchContainer = new SearchContainer<>(
			(PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST),
			_portletURL, null, "there-are-no-items-to-display");

		searchContainer.setResultsAndTotal(
			_cetManager.getCETs(
				themeDisplay.getCompanyId(), null,
				_cetItemSelectorCriterion.getType(),
				Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS), null));

		return searchContainer;
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	private static final ItemSelectorReturnType
		_supportedItemSelectorReturnType = new CETItemSelectorReturnType();

	private final CETItemSelectorCriterion _cetItemSelectorCriterion;
	private final CETManager _cetManager;
	private final HttpServletRequest _httpServletRequest;
	private final PortletURL _portletURL;

}