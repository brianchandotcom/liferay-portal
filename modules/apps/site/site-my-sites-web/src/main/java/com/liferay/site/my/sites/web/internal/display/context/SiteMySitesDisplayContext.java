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

package com.liferay.site.my.sites.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.portlet.usersadmin.search.GroupSearchTerms;
import com.liferay.site.my.sites.web.internal.servlet.taglib.util.SiteActionDropdownItemsProvider;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SiteMySitesDisplayContext {

	public SiteMySitesDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	public List<DropdownItem> getArticleActionDropdownItems(Group group)
		throws Exception {

		SiteActionDropdownItemsProvider siteActionDropdownItemsProvider =
			new SiteActionDropdownItemsProvider(
				group, _renderRequest, _renderResponse, getTabs1());

		return siteActionDropdownItemsProvider.getActionDropdownItems();
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_renderRequest, "displayStyle", "descriptive");

		return _displayStyle;
	}

	public GroupSearch getGroupSearchContainer() {
		if (_groupSearch != null) {
			return _groupSearch;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		GroupSearch groupSearch = new GroupSearch(
			_renderRequest, getPortletURL());

		OrderByComparator<Group> orderByComparator =
			UsersAdminUtil.getGroupOrderByComparator(
				getOrderByCol(), getOrderByType());

		groupSearch.setOrderByCol(getOrderByCol());
		groupSearch.setOrderByComparator(orderByComparator);
		groupSearch.setOrderByType(getOrderByType());

		GroupSearchTerms searchTerms =
			(GroupSearchTerms)groupSearch.getSearchTerms();

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("site", Boolean.TRUE);

		if (Objects.equals(getTabs1(), "my-sites")) {
			groupParams.put("usersGroups", themeDisplay.getUserId());
			groupParams.put("active", Boolean.TRUE);
		}
		else {
			List<Integer> types = new ArrayList<>();

			types.add(GroupConstants.TYPE_SITE_OPEN);
			types.add(GroupConstants.TYPE_SITE_RESTRICTED);

			groupParams.put("types", types);

			groupParams.put("active", Boolean.TRUE);
		}

		int groupsCount = GroupLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			groupParams);

		groupSearch.setTotal(groupsCount);

		List<Group> groups = GroupLocalServiceUtil.search(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(), groupParams,
			groupSearch.getStart(), groupSearch.getEnd(),
			groupSearch.getOrderByComparator());

		groupSearch.setResults(groups);

		_groupSearch = groupSearch;

		return _groupSearch;
	}

	public int getGroupUsersCounts(long groupId) {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		GroupSearch groupSearch = getGroupSearchContainer();

		long[] groupIds = ListUtil.toLongArray(
			groupSearch.getResults(), Group.GROUP_ID_ACCESSOR);

		Map<Long, Integer> groupUsersCounts = UserLocalServiceUtil.searchCounts(
			themeDisplay.getCompanyId(), WorkflowConstants.STATUS_APPROVED,
			groupIds);

		return GetterUtil.getInteger(groupUsersCounts.get(groupId));
	}

	public List<NavigationItem> getNavigationItems() {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(
							Objects.equals(getTabs1(), "my-sites"));
						navigationItem.setHref(
							getPortletURL(), "tabs1", "my-sites");
						navigationItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "my-sites"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(
							Objects.equals(getTabs1(), "available-sites"));
						navigationItem.setHref(
							getPortletURL(), "tabs1", "available-sites");
						navigationItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "available-sites"));
					});
			}
		};
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "name");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("tabs1", getTabs1());
		portletURL.setParameter("displayStyle", getDisplayStyle());

		return portletURL;
	}

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(_renderRequest, "tabs1", "my-sites");

		if (!_tabs1.equals("my-sites") && !_tabs1.equals("available-sites")) {
			_tabs1 = "my-sites";
		}

		return _tabs1;
	}

	public int getTotalItems() {
		GroupSearch groupSearch = getGroupSearchContainer();

		return groupSearch.getTotal();
	}

	private String _displayStyle;
	private GroupSearch _groupSearch;
	private final HttpServletRequest _httpServletRequest;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private String _tabs1;

}