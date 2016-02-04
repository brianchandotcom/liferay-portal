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

package com.liferay.site.item.selector.web.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;
import com.liferay.site.util.GroupSearchProvider;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class AllSitesItemSelectorViewDisplayContext
	extends BaseSitesItemSelectorViewDisplayContext {

	public AllSitesItemSelectorViewDisplayContext(
		HttpServletRequest request,
		SiteItemSelectorCriterion siteItemSelectorCriterion,
		String itemSelectedEventName, PortletURL portletURL,
		GroupSearchProvider groupSearchProvider) {

		super(
			request, siteItemSelectorCriterion, itemSelectedEventName,
			portletURL);

		_groupSearchProvider = groupSearchProvider;
		_portletRequest = getPortletRequest();
	}

	@Override
	public GroupSearch getGroupSearch() throws PortalException {
		return _groupSearchProvider.getGroupSearch(_portletRequest, portletURL);
	}

	@Override
	public boolean isShowChildSitesLink() {
		return true;
	}

	private final GroupSearchProvider _groupSearchProvider;
	private final PortletRequest _portletRequest;

}