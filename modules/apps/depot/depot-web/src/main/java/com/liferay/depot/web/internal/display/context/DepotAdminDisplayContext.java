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

package com.liferay.depot.web.internal.display.context;

import com.liferay.depot.web.internal.constants.DepotAdminWebKeys;
import com.liferay.depot.web.internal.util.DepotAdminGroupSearchProvider;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portlet.usersadmin.search.GroupSearch;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DepotAdminDisplayContext {

	public DepotAdminDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_depotAdminGroupSearchProvider =
			(DepotAdminGroupSearchProvider)httpServletRequest.getAttribute(
				DepotAdminWebKeys.DEPOT_ADMIN_GROUP_SEARCH_PROVIDER);
	}

	public String getDisplayStyle() {
		return "icon";
	}

	public GroupSearch getSearchContainer() {
		GroupSearch groupSearch = _depotAdminGroupSearchProvider.getGroupSearch(
			_liferayPortletRequest, _liferayPortletResponse.createRenderURL());

		groupSearch.setId("depotEntries");

		return groupSearch;
	}

	private final DepotAdminGroupSearchProvider _depotAdminGroupSearchProvider;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}