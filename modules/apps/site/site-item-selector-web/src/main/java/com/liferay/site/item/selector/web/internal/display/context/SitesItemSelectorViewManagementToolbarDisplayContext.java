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

package com.liferay.site.item.selector.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SitesItemSelectorViewManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public SitesItemSelectorViewManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest httpServletRequest,
			SitesItemSelectorViewDisplayContext
				sitesItemSelectorViewDisplayContext)
		throws Exception {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			sitesItemSelectorViewDisplayContext.getGroupSearch());

		_sitesItemSelectorViewDisplayContext =
			sitesItemSelectorViewDisplayContext;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSortingOrder() {
		if (_sitesItemSelectorViewDisplayContext.isShowSortFilter()) {
			return super.getSortingOrder();
		}

		return null;
	}

	@Override
	public String getSortingURL() {
		if (_sitesItemSelectorViewDisplayContext.isShowSortFilter()) {
			return super.getSortingURL();
		}

		return null;
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	public Boolean isShowSearch() {
		return _sitesItemSelectorViewDisplayContext.isShowSearch();
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return "icon";
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list", "descriptive", "icon"};
	}

	@Override
	protected String[] getNavigationKeys() {
		if (_sitesItemSelectorViewDisplayContext.isShowSortFilter()) {
			return new String[] {"all"};
		}

		return null;
	}

	@Override
	protected String[] getOrderByKeys() {
		if (_sitesItemSelectorViewDisplayContext.isShowSortFilter()) {
			return new String[] {"name", "type"};
		}

		return null;
	}

	private final SitesItemSelectorViewDisplayContext
		_sitesItemSelectorViewDisplayContext;

}