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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.service.permission.UserPermission;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

/**
 * @author Charles May
 * @author Jorge Ferrer
 */
@OSGiBeanProperties(
	property = "model.class.name=com.liferay.portal.kernel.model.User"
)
public class UserPermissionImpl
	implements BaseModelPermissionChecker, UserPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long userId,
			long[] organizationIds, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, userId, organizationIds, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, User.class.getName(), userId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long userId, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, userId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, User.class.getName(), userId, actionId);
		}
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getUserOrganizations(primaryKey);

		long[] organizationsIds = new long[organizations.size()];

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = organizations.get(i);

			organizationsIds[i] = organization.getOrganizationId();
		}

		check(permissionChecker, primaryKey, organizationsIds, actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long userId,
		long[] organizationIds, String actionId) {

		try {
			User user = null;

			if (userId != ResourceConstants.PRIMKEY_DNE) {
				user = UserLocalServiceUtil.getUserById(userId);

				if (!actionId.equals(ActionKeys.VIEW) &&
					!permissionChecker.isOmniadmin() &&
					PortalUtil.isOmniadmin(user)) {

					return false;
				}

				Contact contact = user.getContact();

				if (permissionChecker.hasOwnerPermission(
						permissionChecker.getCompanyId(), User.class.getName(),
						userId, contact.getUserId(), actionId) ||
					(permissionChecker.getUserId() == userId)) {

					return true;
				}

				if (permissionChecker.hasPermission(
						null, User.class.getName(), userId, actionId)) {

					return true;
				}
			}
			else {
				if (permissionChecker.hasPermission(
						null, User.class.getName(), User.class.getName(),
						actionId)) {

					return true;
				}
			}

			if (user == null) {
				return false;
			}

			if (organizationIds == null) {
				organizationIds = user.getOrganizationIds();
			}

			for (long organizationId : organizationIds) {
				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						organizationId);

				if (OrganizationPermissionUtil.contains(
						permissionChecker, organization,
						ActionKeys.MANAGE_USERS)) {

					if (permissionChecker.getUserId() == user.getUserId()) {
						return true;
					}

					Organization curOrganization = organization;

					while (curOrganization != null) {

						// Organization owners can manage all users

						if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
								permissionChecker.getUserId(),
								curOrganization.getGroupId(),
								RoleConstants.ORGANIZATION_OWNER, true)) {

							return true;
						}

						// Organization administrators can only manage normal
						// users

						if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
								permissionChecker.getUserId(),
								curOrganization.getGroupId(),
								RoleConstants.ORGANIZATION_ADMINISTRATOR,
								true) &&
							!UserGroupRoleLocalServiceUtil.hasUserGroupRole(
								user.getUserId(), organization.getGroupId(),
								RoleConstants.ORGANIZATION_ADMINISTRATOR,
								true) &&
							!UserGroupRoleLocalServiceUtil.hasUserGroupRole(
								user.getUserId(), organization.getGroupId(),
								RoleConstants.ORGANIZATION_OWNER, true)) {

							return true;
						}

						curOrganization =
							curOrganization.getParentOrganization();
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long userId, String actionId) {

		return contains(permissionChecker, userId, null, actionId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserPermissionImpl.class);

}