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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Preston Crary
 */
public class UserBagFactoryImpl implements UserBagFactory {

	@Override
	public UserBag create(long userId) throws PortalException {
		UserBag userBag = PermissionCacheUtil.getUserBag(userId);

		if (userBag != null) {
			return userBag;
		}

		try {
			List<Group> userGroups = GroupLocalServiceUtil.getUserGroups(
				userId, true);

			Set<Organization> userOrgs = getUserOrgs(userId);

			Set<Group> userOrgGroups = new HashSet<>(userOrgs.size());

			for (Organization organization : userOrgs) {
				userOrgGroups.add(organization.getGroup());
			}

			Set<Role> userRoles = new HashSet<>();

			if (!userGroups.isEmpty()) {
				List<Role> userRelatedRoles =
					RoleLocalServiceUtil.getUserRelatedRoles(
						userId, userGroups);

				userRoles.addAll(userRelatedRoles);
			}
			else {
				userRoles.addAll(RoleLocalServiceUtil.getUserRoles(userId));
			}

			userBag = new UserBagImpl(
				userId, SetUtil.fromList(userGroups), userOrgs, userOrgGroups,
				userRoles);

			PermissionCacheUtil.putUserBag(userId, userBag);

			return userBag;
		}
		catch (Exception e) {
			PermissionCacheUtil.removeUserBag(userId);

			throw e;
		}
	}

	protected Set<Organization> getUserOrgs(long userId)
		throws PortalException {

		List<Organization> userOrgs =
			OrganizationLocalServiceUtil.getUserOrganizations(userId);

		if (userOrgs.isEmpty()) {
			return new HashSet<>(userOrgs);
		}

		Set<Organization> organizations = new LinkedHashSet<>();

		for (Organization organization : userOrgs) {
			if (organizations.add(organization) &&
				!PropsValues.ORGANIZATIONS_MEMBERSHIP_STRICT) {

				List<Organization> ancestorOrganizations =
					OrganizationLocalServiceUtil.getParentOrganizations(
						organization.getOrganizationId());

				organizations.addAll(ancestorOrganizations);
			}
		}

		return organizations;
	}

}