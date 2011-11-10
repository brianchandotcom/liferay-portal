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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionFinderUtil {
	public static boolean containsPermissions_2(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId, java.util.List<com.liferay.portal.model.Group> groups,
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .containsPermissions_2(permissions, userId, groups, groupId);
	}

	public static boolean containsPermissions_4(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId, java.util.List<com.liferay.portal.model.Group> groups,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .containsPermissions_4(permissions, userId, groups, roles);
	}

	public static int countByGroupsPermissions(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByGroupsPermissions(permissions, groups);
	}

	public static int countByGroupsRoles(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByGroupsRoles(permissions, groups);
	}

	public static int countByRolesPermissions(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByRolesPermissions(permissions, roles);
	}

	public static int countByUserGroupRole(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByUserGroupRole(permissions, userId, groupId);
	}

	public static int countByUsersPermissions(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByUsersPermissions(permissions, userId);
	}

	public static int countByUsersRoles(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByUsersRoles(permissions, userId);
	}

	public static int countByR_A_C(long roleId, java.lang.String actionId,
		long codeId) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByR_A_C(roleId, actionId, codeId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByA_C(
		java.lang.String actionId, long codeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByA_C(actionId, codeId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByA_R(
		java.lang.String actionId, long[] resourceIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByA_R(actionId, resourceIds);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByG_R(
		long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByG_R(groupId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByR_R(
		long roleId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByR_R(roleId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByR_S(
		long roleId, int[] scopes)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByR_S(roleId, scopes);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByU_R(
		long userId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByU_R(userId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByO_G_R(
		long organizationId, long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByO_G_R(organizationId, groupId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByU_A_R(
		long userId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByU_A_R(userId, actionIds, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByG_C_N_S_P(
		long groupId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByG_C_N_S_P(groupId, companyId, name, scope, primKey);
	}

	public static java.util.List<com.liferay.portal.model.Permission> findByU_C_N_S_P(
		long userId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByU_C_N_S_P(userId, companyId, name, scope, primKey);
	}

	public static PermissionFinder getFinder() {
		if (_finder == null) {
			_finder = (PermissionFinder)PortalBeanLocatorUtil.locate(PermissionFinder.class.getName());

			ReferenceRegistry.registerReference(PermissionFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(PermissionFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(PermissionFinderUtil.class,
			"_finder");
	}

	private static PermissionFinder _finder;
}