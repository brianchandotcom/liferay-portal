/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.GroupServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.permission.RolePermissionUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.UniqueList;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class GroupServiceImpl extends GroupServiceBaseImpl {

	public Group addGroup(
			long liveGroupId, String name, String description, int type,
			String friendlyURL, boolean site, boolean active,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), liveGroupId, ActionKeys.UPDATE);

		return groupLocalService.addGroup(
			getUserId(), null, 0, liveGroupId, name, description, type,
			friendlyURL, site, active, serviceContext);
	}

	public Group addGroup(
			String name, String description, int type, String friendlyURL,
			boolean site, boolean active, ServiceContext serviceContext)
		throws PortalException, SystemException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.ADD_COMMUNITY);

		return groupLocalService.addGroup(
			getUserId(), null, 0, name, description, type, friendlyURL, site,
			active, serviceContext);
	}

	public void addRoleGroups(long roleId, long[] groupIds)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.UPDATE);

		groupLocalService.addRoleGroups(roleId, groupIds);
	}

	public void deleteGroup(long groupId)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.DELETE);

		groupLocalService.deleteGroup(groupId);
	}

	public Group getGroup(long groupId)
		throws PortalException, SystemException {

		return groupLocalService.getGroup(groupId);
	}

	public Group getGroup(long companyId, String name)
		throws PortalException, SystemException {

		return groupLocalService.getGroup(companyId, name);
	}

	public List<Group> getManageableSites(Collection<Portlet> portlets, int max)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			LinkedHashMap<String, Object> params =
				new LinkedHashMap<String, Object>();

			params.put("site", Boolean.TRUE);

			return groupLocalService.search(
				permissionChecker.getCompanyId(), null, null, null, params, 0,
				max);
		}

		List<Group> groups = new UniqueList<Group>();

		groups.addAll(
			userPersistence.getGroups(permissionChecker.getUserId(), 0, max));
		groups.addAll(
			getUserOrganizationsGroups(permissionChecker.getUserId(), 0, max));

		Iterator<Group> itr = groups.iterator();

		while (itr.hasNext()) {
			Group group = itr.next();

			if (!group.isSite() ||
				!PortletPermissionUtil.contains(
					permissionChecker, group.getGroupId(), 0L, portlets,
					ActionKeys.ACCESS_IN_CONTROL_PANEL)) {

				itr.remove();
			}
		}

		return groups;
	}

	public List<Group> getOrganizationsGroups(
		List<Organization> organizations) {

		return groupLocalService.getOrganizationsGroups(organizations);
	}

	public Group getUserGroup(long companyId, long userId)
		throws PortalException, SystemException {

		return groupLocalService.getUserGroup(companyId, userId);
	}

	public List<Group> getUserGroupsGroups(List<UserGroup> userGroups)
		throws PortalException, SystemException {

		return groupLocalService.getUserGroupsGroups(userGroups);
	}

	public List<Group> getUserOrganizationsGroups(
			long userId, int start, int end)
		throws PortalException, SystemException {

		return groupLocalService.getUserOrganizationsGroups(userId, start, end);
	}

	public List<Group> getUserSites() throws PortalException, SystemException {
		return getUserPlaces(null, QueryUtil.ALL_POS);
	}

	public List<Group> getUserPlaces(String[] classNames, int max)
		throws PortalException, SystemException {

		return getUserPlaces(getGuestOrUserId(), classNames, max);
	}

	public List<Group> getUserPlaces(long userId, String[] classNames, int max)
		throws PortalException, SystemException {

		User user = userPersistence.fetchByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return Collections.emptyList();
		}

		List<Group> userPlaces = new UniqueList<Group>();

		int start = QueryUtil.ALL_POS;
		int end = QueryUtil.ALL_POS;

		if (max != QueryUtil.ALL_POS) {
			start = 0;
			end = max;
		}

		if ((classNames == null) ||
			ArrayUtil.contains(classNames, Group.class.getName())) {

			LinkedHashMap<String, Object> groupParams =
				new LinkedHashMap<String, Object>();

			groupParams.put("usersGroups", new Long(userId));

			userPlaces.addAll(
				groupLocalService.search(
					user.getCompanyId(), null, null, groupParams, start, end));
		}

		if ((classNames == null) ||
			ArrayUtil.contains(classNames, Organization.class.getName())) {

			LinkedHashMap<String, Object> organizationParams =
				new LinkedHashMap<String, Object>();

			organizationParams.put("usersOrgs", new Long(userId));

			List<Organization> userOrgs = organizationLocalService.search(
				user.getCompanyId(),
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null,
				null, null, null, organizationParams, start, end);

			for (Organization organization : userOrgs) {
				userPlaces.add(0, organization.getGroup());

				if (!PropsValues.ORGANIZATIONS_MEMBERSHIP_STRICT) {
					for (Organization ancestorOrganization :
							organization.getAncestors()) {

						userPlaces.add(0, ancestorOrganization.getGroup());
					}
				}
			}
		}

		if ((classNames == null) ||
			ArrayUtil.contains(classNames, User.class.getName())) {

			if (PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED ||
				PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {

				Group userGroup = user.getGroup();

				userPlaces.add(0, userGroup);
			}
		}

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.getUserId() != userId) {
			try {
				permissionChecker = PermissionCheckerFactoryUtil.create(
					user, true);
			}
			catch (Exception e) {
				throw new PrincipalException(e);
			}
		}

		if (PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.VIEW_CONTROL_PANEL)) {

			Group controlPanelGroup = groupLocalService.getGroup(
				user.getCompanyId(), GroupConstants.CONTROL_PANEL);

			userPlaces.add(0, controlPanelGroup);
		}

		if ((max != QueryUtil.ALL_POS) && (userPlaces.size() > max)) {
			userPlaces = ListUtil.subList(userPlaces, start, end);
		}

		return Collections.unmodifiableList(userPlaces);
	}

	public boolean hasUserGroup(long userId, long groupId)
		throws SystemException {

		return groupLocalService.hasUserGroup(userId, groupId);
	}

	public List<Group> search(
			long companyId, String name, String description, String[] params,
			int start, int end)
		throws SystemException {

		LinkedHashMap<String, Object> paramsObj = MapUtil.toLinkedHashMap(
			params);

		return groupLocalService.search(
			companyId, name, description, paramsObj, start, end);
	}

	public int searchCount(
			long companyId, String name, String description, String[] params)
		throws SystemException {

		LinkedHashMap<String, Object> paramsObj = MapUtil.toLinkedHashMap(
			params);

		return groupLocalService.searchCount(
			companyId, name, description, paramsObj);
	}

	public void setRoleGroups(long roleId, long[] groupIds)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.UPDATE);

		groupLocalService.setRoleGroups(roleId, groupIds);
	}

	public void unsetRoleGroups(long roleId, long[] groupIds)
		throws PortalException, SystemException {

		RolePermissionUtil.check(
			getPermissionChecker(), roleId, ActionKeys.UPDATE);

		groupLocalService.unsetRoleGroups(roleId, groupIds);
	}

	public Group updateFriendlyURL(long groupId, String friendlyURL)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		return groupLocalService.updateFriendlyURL(groupId, friendlyURL);
	}

	public Group updateGroup(long groupId, String typeSettings)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		return groupLocalService.updateGroup(groupId, typeSettings);
	}

	public Group updateGroup(
			long groupId, String name, String description, int type,
			String friendlyURL, boolean active, ServiceContext serviceContext)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.UPDATE);

		return groupLocalService.updateGroup(
			groupId, name, description, type, friendlyURL, active,
			serviceContext);
	}

}