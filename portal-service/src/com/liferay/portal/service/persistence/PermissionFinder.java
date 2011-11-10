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

/**
 * @author Brian Wing Shun Chan
 */
public interface PermissionFinder {
	public boolean containsPermissions_2(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId, java.util.List<com.liferay.portal.model.Group> groups,
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsPermissions_4(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId, java.util.List<com.liferay.portal.model.Group> groups,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByGroupsPermissions(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByGroupsRoles(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByRolesPermissions(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserGroupRole(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUsersPermissions(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUsersRoles(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	public int countByR_A_C(long roleId, java.lang.String actionId, long codeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByA_C(
		java.lang.String actionId, long codeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByA_R(
		java.lang.String actionId, long[] resourceIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByG_R(
		long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByR_R(
		long roleId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByR_S(
		long roleId, int[] scopes)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByU_R(
		long userId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByO_G_R(
		long organizationId, long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByU_A_R(
		long userId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByG_C_N_S_P(
		long groupId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByU_C_N_S_P(
		long userId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;
}