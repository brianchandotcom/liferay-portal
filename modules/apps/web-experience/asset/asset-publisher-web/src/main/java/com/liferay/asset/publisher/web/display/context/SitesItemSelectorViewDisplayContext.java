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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.portlet.usersadmin.search.GroupSearchTerms;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SitesItemSelectorViewDisplayContext
	extends BaseItemSelectorViewDisplayContext {

	public SitesItemSelectorViewDisplayContext(
		HttpServletRequest request,
		SiteItemSelectorCriterion siteItemSelectorCriterion,
		String itemSelectedEventName, PortletURL portletURL) {

		super(
			request, siteItemSelectorCriterion, itemSelectedEventName,
			portletURL);
	}

	@Override
	public GroupSearch getGroupSearch() throws Exception {
		String type = _getType();

		if (type.equals("parent-sites")) {
			return _getParentSitesGroupSearch();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		GroupSearch groupSearch = new GroupSearch(
			getPortletRequest(), getPortletURL());

		GroupSearchTerms groupSearchTerms =
			(GroupSearchTerms)groupSearch.getSearchTerms();

		long[] classNameIds = _CLASS_NAME_IDS;

		int total = GroupLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), classNameIds,
			groupSearchTerms.getKeywords(), _getGroupParams());

		groupSearch.setTotal(total);

		List<Group> groups = GroupLocalServiceUtil.search(
			themeDisplay.getCompanyId(), classNameIds,
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

	private List<Group> _filterParentSitesGroups(List<Group> groups) {
		List<Group> filteredGroups = new ArrayList();

		for (Group group : groups) {
			if (SitesUtil.isContentSharingWithChildrenEnabled(group)) {
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

		String type = _getType();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();
		User user = themeDisplay.getUser();

		boolean filterManageableGroups = true;

		if (permissionChecker.isCompanyAdmin()) {
			filterManageableGroups = false;
		}

		_groupParams = new LinkedHashMap<>();

		_groupParams.put("active", Boolean.TRUE);

		if (type.equals("child-sites")) {
			Group parentGroup = GroupLocalServiceUtil.getGroup(getGroupId());

			List<Group> parentGroups = new ArrayList<>();

			parentGroups.add(parentGroup);

			_groupParams.put("groupsTree", parentGroups);
		}
		else if (filterManageableGroups) {
			_groupParams.put("usersGroups", user.getUserId());
		}

		_groupParams.put("site", Boolean.TRUE);

		if (getGroupId() > 0) {
			List<Long> excludedGroupIds = new ArrayList<>();

			Group group = GroupLocalServiceUtil.getGroup(getGroupId());

			if (group.isStagingGroup()) {
				excludedGroupIds.add(group.getLiveGroupId());
			}
			else {
				excludedGroupIds.add(getGroupId());
			}

			_groupParams.put("excludedGroupIds", excludedGroupIds);
		}

		return _groupParams;
	}

	private GroupSearch _getParentSitesGroupSearch() throws Exception {
		GroupSearch groupSearch = new GroupSearch(
			getPortletRequest(), getPortletURL());

		Group group = GroupLocalServiceUtil.getGroup(getGroupId());

		List<Group> groups = group.getAncestors();

		groups = _filterParentSitesGroups(groups);

		groupSearch.setTotal(groups.size());

		groupSearch.setResults(groups);

		return groupSearch;
	}

	private String _getType() {
		if (_type != null) {
			return _type;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (PrefsPropsUtil.getBoolean(
				themeDisplay.getCompanyId(),
				PropsKeys.
					SITES_CONTENT_SHARING_THROUGH_ADMINISTRATORS_ENABLED)) {

			_type = "sites-that-i-administer";

			return _type;
		}

		Layout layout = themeDisplay.getLayout();

		int groupsCount = GroupLocalServiceUtil.getGroupsCount(
			themeDisplay.getCompanyId(), layout.getGroupId(), Boolean.TRUE);

		if (groupsCount > 0) {
			_type = "child-sites";

			return _type;
		}

		Group siteGroup = themeDisplay.getSiteGroup();

		if (!siteGroup.isRoot()) {
			_type = "parent-sites";

			return _type;
		}

		_type = "sites-that-i-administer";

		return _type;
	}

	private static final long[] _CLASS_NAME_IDS = new long[] {
		PortalUtil.getClassNameId(Group.class),
		PortalUtil.getClassNameId(Organization.class)
	};

	private LinkedHashMap<String, Object> _groupParams;
	private String _type;

}