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
import com.liferay.depot.web.internal.servlet.taglib.clay.DepotEntryVerticalCard;
import com.liferay.depot.web.internal.servlet.taglib.util.DepotActionDropdownItemsProvider;
import com.liferay.depot.web.internal.util.DepotAdminGroupSearchProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.sitesadmin.search.SiteChecker;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.site.util.GroupURLProvider;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DepotAdminDisplayContext {

	public DepotAdminDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_depotAdminGroupSearchProvider =
			(DepotAdminGroupSearchProvider)httpServletRequest.getAttribute(
				DepotAdminWebKeys.DEPOT_ADMIN_GROUP_SEARCH_PROVIDER);
		_groupURLProvider = (GroupURLProvider)httpServletRequest.getAttribute(
			DepotAdminWebKeys.DEPOT_ADMIN_GROUP_URL_PROVIDER);
	}

	public List<DropdownItem> getActionDropdownItems(Group group) {
		DepotActionDropdownItemsProvider depotActionDropdownItemsProvider =
			new DepotActionDropdownItemsProvider(
				group, _liferayPortletRequest, _liferayPortletResponse);

		return depotActionDropdownItemsProvider.getActionDropdownItems();
	}

	public String getDefaultDisplayStyle() {
		return "icon";
	}

	public DepotEntryVerticalCard getDepotEntryVerticalCard(Group group) {
		GroupSearch groupSearch = getGroupSearch();

		return new DepotEntryVerticalCard(
			group, _groupURLProvider, _liferayPortletRequest,
			_liferayPortletResponse, groupSearch.getRowChecker());
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_liferayPortletRequest, "displayStyle", getDefaultDisplayStyle());

		return _displayStyle;
	}

	public GroupSearch getGroupSearch() {
		GroupSearch groupSearch = _depotAdminGroupSearchProvider.getGroupSearch(
			_liferayPortletRequest, _getPortletURL());

		groupSearch.setId("repositories");

		SiteChecker siteChecker = new SiteChecker(_liferayPortletResponse);

		StringBundler sb = new StringBundler(5);

		sb.append("^(?!.*");
		sb.append(_liferayPortletResponse.getNamespace());
		sb.append("redirect).*(groupId=");
		sb.append(_getGroupId());
		sb.append(")");

		siteChecker.setRememberCheckBoxStateURLRegex(sb.toString());

		groupSearch.setRowChecker(siteChecker);

		return groupSearch;
	}

	public String getHref(Group curGroup) {
		return _groupURLProvider.getGroupURL(curGroup, _liferayPortletRequest);
	}

	public boolean isDisplayStyleDescriptive() {
		return Objects.equals(getDisplayStyle(), "descriptive");
	}

	public boolean isDisplayStyleIcon() {
		return Objects.equals(getDisplayStyle(), "icon");
	}

	private long _getGroupId() {
		if (_groupId <= 0) {
			_groupId = ParamUtil.getLong(
				_httpServletRequest, "groupId",
				GroupConstants.DEFAULT_PARENT_GROUP_ID);
		}

		return _groupId;
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("displayStyle", getDisplayStyle());

		return portletURL;
	}

	private final DepotAdminGroupSearchProvider _depotAdminGroupSearchProvider;
	private String _displayStyle;
	private long _groupId;
	private final GroupURLProvider _groupURLProvider;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}