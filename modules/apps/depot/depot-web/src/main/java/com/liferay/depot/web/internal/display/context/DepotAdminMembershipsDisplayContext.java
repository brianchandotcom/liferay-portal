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

import com.liferay.admin.kernel.util.PortalMyAccountApplicationType;
import com.liferay.depot.web.internal.item.selector.criteria.depot.group.criterion.DepotGroupItemSelectorCriterion;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.GroupItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.users.admin.kernel.util.UsersAdmin;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

/**
 * @author Cristina González
 */
public class DepotAdminMembershipsDisplayContext {

	public DepotAdminMembershipsDisplayContext(
			ItemSelector itemSelector,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		_itemSelector = itemSelector;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		_user = PortalUtil.getSelectedUser(liferayPortletRequest);
	}

	public List<Group> getDepotGroups(int start, int end)
		throws PortalException {

		List<Group> depotGroups = _getDepotGroups();

		return ListUtil.subList(depotGroups, start, end);
	}

	public int getDepotGroupsCount() throws PortalException {
		List<Group> groups = _getDepotGroups();

		return groups.size();
	}

	public String getItemSelectorEventName() {
		return _liferayPortletResponse.getNamespace() + "selectDepotGroup";
	}

	public PortletURL getItemSelectorURL() {
		ItemSelectorCriterion itemSelectorCriterion =
			new DepotGroupItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new GroupItemSelectorReturnType());

		return _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest),
			getItemSelectorEventName(), itemSelectorCriterion);
	}

	public String getRoles(Group group) {
		if (_user == null) {
			return StringPool.BLANK;
		}

		List<UserGroupRole> userGroupRoles =
			UserGroupRoleLocalServiceUtil.getUserGroupRoles(
				_user.getUserId(), group.getGroupId(), 0,
				PropsValues.USERS_ADMIN_ROLE_COLUMN_LIMIT);

		int userGroupRolesCount =
			UserGroupRoleLocalServiceUtil.getUserGroupRolesCount(
				_user.getUserId(), group.getGroupId());

		return UsersAdminUtil.getUserColumnText(
			_themeDisplay.getLocale(), userGroupRoles,
			UsersAdmin.USER_GROUP_ROLE_TITLE_ACCESSOR, userGroupRolesCount);
	}

	public User getUser() {
		return _user;
	}

	public boolean isDeletable() {
		return isSelectable();
	}

	public boolean isSelectable() {
		String myAccountPortletId = PortletProviderUtil.getPortletId(
			PortalMyAccountApplicationType.MyAccount.CLASS_NAME,
			PortletProvider.Action.VIEW);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		return !Objects.equals(
			portletDisplay.getPortletName(), myAccountPortletId);
	}

	private List<Group> _getDepotGroups() throws PortalException {
		if (_depotGroups != null) {
			return _depotGroups;
		}

		if (_user == null) {
			_depotGroups = Collections.emptyList();

			return _depotGroups;
		}

		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		List<Group> groups = ListUtil.copy(_user.getGroups());

		Iterator<Group> itr = groups.iterator();

		while (itr.hasNext()) {
			Group group = itr.next();

			if (group.getType() != GroupConstants.TYPE_DEPOT) {
				itr.remove();
			}
			else if (!permissionChecker.isCompanyAdmin() &&
					 !GroupPermissionUtil.contains(
						 permissionChecker, group, ActionKeys.ASSIGN_MEMBERS)) {

				itr.remove();
			}
		}

		_depotGroups = groups;

		return _depotGroups;
	}

	private List<Group> _depotGroups;
	private final ItemSelector _itemSelector;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;
	private final User _user;

}