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

package com.liferay.site.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.portlet.usersadmin.search.GroupSearchTerms;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 *
 * @author Julio Camarero
 */
@Component(immediate = true, service = GroupSearchProvider.class)
public class GroupSearchProvider {

	public GroupSearch getGroupSearch(
			PortletRequest portletRequest, PortletURL portletURL)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		GroupSearch groupSearch = new GroupSearch(portletRequest, portletURL);

		GroupSearchTerms searchTerms =
			(GroupSearchTerms)groupSearch.getSearchTerms();

		long parentGroupId = getParentGroupId(portletRequest);

		Company company = themeDisplay.getCompany();

		List results = null;

		if (!searchTerms.hasSearchTerms() &&
			isFilterManageableGroups(portletRequest) && (parentGroupId <= 0)) {

			List<Group> groups = getAllGroups(portletRequest);

			groupSearch.setTotal(groups.size());

			results = ListUtil.subList(
				groups, groupSearch.getStart(), groupSearch.getEnd());
		}
		else if (searchTerms.hasSearchTerms()) {
			int total = _groupLocalService.searchCount(
				company.getCompanyId(), _classNameIds,
				searchTerms.getKeywords(),
				getGroupParams(portletRequest, searchTerms, parentGroupId));

			groupSearch.setTotal(total);

			results = _groupLocalService.search(
				company.getCompanyId(), _classNameIds,
				searchTerms.getKeywords(),
				getGroupParams(portletRequest, searchTerms, parentGroupId),
				groupSearch.getStart(), groupSearch.getEnd(),
				groupSearch.getOrderByComparator());
		}
		else {
			long groupId = ParamUtil.getLong(
				portletRequest, "groupId",
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			int total = _groupLocalService.searchCount(
				company.getCompanyId(), _classNameIds, groupId,
				searchTerms.getKeywords(),
				getGroupParams(portletRequest, searchTerms, parentGroupId));

			groupSearch.setTotal(total);

			results = _groupLocalService.search(
				company.getCompanyId(), _classNameIds, groupId,
				searchTerms.getKeywords(),
				getGroupParams(portletRequest, searchTerms, parentGroupId),
				groupSearch.getStart(), groupSearch.getEnd(),
				groupSearch.getOrderByComparator());
		}

		groupSearch.setResults(results);

		return groupSearch;
	}

	protected List<Group> getAllGroups(PortletRequest portletRequest)
		throws PortalException {

		List<Group> groups = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (isFilterManageableGroups(portletRequest)) {
			User user = themeDisplay.getUser();

			groups = user.getSiteGroups(true);
		}

		long groupId = ParamUtil.getLong(
			portletRequest, "groupId", GroupConstants.DEFAULT_PARENT_GROUP_ID);

		if (groupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			groups.clear();

			groups.add(_groupLocalService.getGroup(groupId));
		}

		return groups;
	}

	protected LinkedHashMap<String, Object> getGroupParams(
			PortletRequest portletRequest, GroupSearchTerms searchTerms,
			long parentGroupId)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("site", Boolean.TRUE);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		User user = themeDisplay.getUser();

		if (searchTerms.hasSearchTerms()) {
			if (isFilterManageableGroups(portletRequest)) {
				groupParams.put("groupsTree", getAllGroups(portletRequest));
			}
			else if (parentGroupId > 0) {
				List<Group> groupsTree = new ArrayList<>();

				Group parentGroup = _groupLocalService.getGroup(parentGroupId);

				groupsTree.add(parentGroup);

				groupParams.put("groupsTree", groupsTree);
			}

			if (!permissionChecker.isCompanyAdmin() &&
				!GroupPermissionUtil.contains(
					permissionChecker, ActionKeys.VIEW)) {

				groupParams.put("usersGroups", Long.valueOf(user.getUserId()));
			}
		}

		return groupParams;
	}

	protected long getParentGroupId(PortletRequest portletRequest)
		throws PortalException {

		Group group = null;

		long groupId = ParamUtil.getLong(
			portletRequest, "groupId", GroupConstants.DEFAULT_PARENT_GROUP_ID);

		if (groupId > 0) {
			group = _groupService.getGroup(groupId);
		}

		if (group != null) {
			return group.getGroupId();
		}

		if (isFilterManageableGroups(portletRequest)) {
			return GroupConstants.ANY_PARENT_GROUP_ID;
		}

		return GroupConstants.DEFAULT_PARENT_GROUP_ID;
	}

	protected boolean isFilterManageableGroups(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			return false;
		}

		if (GroupPermissionUtil.contains(permissionChecker, ActionKeys.VIEW)) {
			return false;
		}

		return true;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupService(GroupService groupService) {
		_groupService = groupService;
	}

	private static final long[] _classNameIds = new long[] {
		PortalUtil.getClassNameId(Company.class),
		PortalUtil.getClassNameId(Group.class),
		PortalUtil.getClassNameId(Organization.class)
	};

	private GroupLocalService _groupLocalService;
	private GroupService _groupService;

}