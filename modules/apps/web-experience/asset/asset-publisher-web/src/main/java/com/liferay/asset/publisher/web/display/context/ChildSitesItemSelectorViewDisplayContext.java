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

package com.liferay.asset.publisher.web.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.portlet.usersadmin.search.GroupSearchTerms;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ChildSitesItemSelectorViewDisplayContext
	extends BaseItemSelectorViewDisplayContext {

	public ChildSitesItemSelectorViewDisplayContext(
		HttpServletRequest request,
		SiteItemSelectorCriterion siteItemSelectorCriterion,
		String itemSelectedEventName, PortletURL portletURL) {

		super(
			request, siteItemSelectorCriterion, itemSelectedEventName,
			portletURL);
	}

	@Override
	public GroupSearch getGroupSearch() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		GroupSearch groupSearch = new GroupSearch(
			getPortletRequest(), getPortletURL());

		GroupSearchTerms groupSearchTerms =
			(GroupSearchTerms)groupSearch.getSearchTerms();

		int total = GroupLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), _CLASS_NAME_IDS,
			groupSearchTerms.getKeywords(), _getGroupParams());

		groupSearch.setTotal(total);

		List<Group> groups = GroupLocalServiceUtil.search(
			themeDisplay.getCompanyId(), _CLASS_NAME_IDS,
			groupSearchTerms.getKeywords(), _getGroupParams(),
			groupSearch.getStart(), groupSearch.getEnd(),
			groupSearch.getOrderByComparator());

		groups = _filterGroups(groups, themeDisplay.getPermissionChecker());

		groupSearch.setTotal(groups.size());

		groupSearch.setResults(groups);

		return groupSearch;
	}

	private List<Group> _filterGroups(
		List<Group> groups, PermissionChecker permissionChecker) {

		List<Group> filteredGroups = new ArrayList();

		for (Group group : groups) {
			if (permissionChecker.isGroupAdmin(group.getGroupId())) {
				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

	private LinkedHashMap<String, Object> _getGroupParams()
		throws PortalException {

		if (_groupParams != null) {
			return _groupParams;
		}

		_groupParams = new LinkedHashMap<>();

		_groupParams.put("active", Boolean.TRUE);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<Group> parentGroups = new ArrayList<>();

		parentGroups.add(themeDisplay.getSiteGroup());

		_groupParams.put("groupsTree", parentGroups);

		_groupParams.put("site", Boolean.TRUE);

		List<Long> excludedGroupIds = new ArrayList<>();

		Group group = themeDisplay.getSiteGroup();

		if (group.isStagingGroup()) {
			excludedGroupIds.add(group.getLiveGroupId());
		}
		else {
			excludedGroupIds.add(themeDisplay.getSiteGroupId());
		}

		_groupParams.put("excludedGroupIds", excludedGroupIds);

		return _groupParams;
	}

	private static final long[] _CLASS_NAME_IDS = new long[] {
		PortalUtil.getClassNameId(Group.class),
		PortalUtil.getClassNameId(Organization.class)
	};

	private LinkedHashMap<String, Object> _groupParams;

}