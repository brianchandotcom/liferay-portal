/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.provider;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.search.GroupSearch;

import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Julio Camarero
 */
public class GroupSearchProvider {

	public static GroupSearch getGroupSearch(
			PortletRequest portletRequest, PortletURL portletURL)
		throws PortalException {

		GroupSearch groupSearch = new GroupSearch(portletRequest, portletURL);

		setResultsAndTotal(groupSearch, portletRequest);

		return groupSearch;
	}

	public static void setResultsAndTotal(
			GroupSearch groupSearch, PortletRequest portletRequest)
		throws PortalException {

		long parentGroupId = getParentGroupId(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_isSearch(portletRequest) &&
			isFilterManageableGroups(portletRequest) && (parentGroupId <= 0)) {

			groupSearch.setResultsAndTotal(
				ListUtil.sort(
					getAllGroups(portletRequest),
					groupSearch.getOrderByComparator()));
		}
		else if (_isSearch(portletRequest)) {
			groupSearch.setResultsAndTotal(
				() -> GroupLocalServiceUtil.search(
					themeDisplay.getCompanyId(), _getClassNameIds(),
					_getKeywords(portletRequest),
					getGroupParams(portletRequest, parentGroupId),
					groupSearch.getStart(), groupSearch.getEnd(),
					groupSearch.getOrderByComparator()),
				GroupLocalServiceUtil.searchCount(
					themeDisplay.getCompanyId(), _getClassNameIds(),
					_getKeywords(portletRequest),
					getGroupParams(portletRequest, parentGroupId)));
		}
		else {
			long groupId = ParamUtil.getLong(
				portletRequest, "groupId",
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			groupSearch.setResultsAndTotal(
				() -> GroupLocalServiceUtil.search(
					themeDisplay.getCompanyId(), _getClassNameIds(), groupId,
					_getKeywords(portletRequest),
					getGroupParams(portletRequest, parentGroupId),
					groupSearch.getStart(), groupSearch.getEnd(),
					groupSearch.getOrderByComparator()),
				GroupLocalServiceUtil.searchCount(
					themeDisplay.getCompanyId(), _getClassNameIds(), groupId,
					_getKeywords(portletRequest),
					getGroupParams(portletRequest, parentGroupId)));
		}
	}

	protected static List<Group> getAllGroups(PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		List<Group> groups = user.getMySiteGroups(
			new String[] {
				Company.class.getName(), Group.class.getName(),
				Organization.class.getName()
			},
			QueryUtil.ALL_POS);

		long groupId = ParamUtil.getLong(
			portletRequest, "groupId", GroupConstants.DEFAULT_PARENT_GROUP_ID);

		if (groupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
			groups.clear();

			groups.add(GroupLocalServiceUtil.getGroup(groupId));
		}

		return groups;
	}

	protected static LinkedHashMap<String, Object> getGroupParams(
			PortletRequest portletRequest, long parentGroupId)
		throws PortalException {

		LinkedHashMap<String, Object> groupParams =
			LinkedHashMapBuilder.<String, Object>put(
				"actionId", ActionKeys.VIEW
			).put(
				"site", Boolean.TRUE
			).build();

		if (_isSearch(portletRequest)) {
			if (isFilterManageableGroups(portletRequest)) {
				groupParams.put("groupsTree", getAllGroups(portletRequest));
			}
			else if (parentGroupId > 0) {
				List<Group> groupsTree = ListUtil.fromArray(
					GroupLocalServiceUtil.getGroup(parentGroupId));

				groupParams.put("groupsTree", groupsTree);
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			if (!permissionChecker.isCompanyAdmin() &&
				!GroupPermissionUtil.contains(
					permissionChecker, ActionKeys.VIEW)) {

				User user = themeDisplay.getUser();

				groupParams.put("usersGroups", Long.valueOf(user.getUserId()));
			}
		}

		return groupParams;
	}

	protected static long getParentGroupId(PortletRequest portletRequest) {
		Group group = null;

		long groupId = ParamUtil.getLong(
			portletRequest, "groupId", GroupConstants.DEFAULT_PARENT_GROUP_ID);

		if (groupId > 0) {
			group = GroupLocalServiceUtil.fetchGroup(groupId);
		}

		if (group != null) {
			return group.getGroupId();
		}

		if (isFilterManageableGroups(portletRequest)) {
			return GroupConstants.ANY_PARENT_GROUP_ID;
		}

		return GroupConstants.DEFAULT_PARENT_GROUP_ID;
	}

	protected static boolean isFilterManageableGroups(
		PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isCompanyAdmin() ||
			GroupPermissionUtil.contains(permissionChecker, ActionKeys.VIEW)) {

			return false;
		}

		return true;
	}

	private static long[] _getClassNameIds() {
		return new long[] {
			PortalUtil.getClassNameId(Company.class),
			PortalUtil.getClassNameId(Group.class),
			PortalUtil.getClassNameId(Organization.class)
		};
	}

	private static String _getKeywords(PortletRequest portletRequest) {
		return ParamUtil.getString(portletRequest, "keywords");
	}

	private static boolean _isSearch(PortletRequest portletRequest) {
		if (Validator.isNotNull(_getKeywords(portletRequest))) {
			return true;
		}

		return false;
	}

}