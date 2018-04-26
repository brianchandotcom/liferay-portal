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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for Role. This utility wraps
 * {@link com.liferay.portal.service.impl.RoleServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see RoleService
 * @see com.liferay.portal.service.base.RoleServiceBaseImpl
 * @see com.liferay.portal.service.impl.RoleServiceImpl
 * @generated
 */
@ProviderType
public class RoleServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.RoleServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds a role. The user is reindexed after role is added.
	*
	* @param className the name of the class for which the role is created
	* @param classPK the primary key of the class for which the role is
	created (optionally <code>0</code>)
	* @param name the role's name
	* @param titleMap the role's localized titles (optionally
	<code>null</code>)
	* @param descriptionMap the role's localized descriptions (optionally
	<code>null</code>)
	* @param type the role's type (optionally <code>0</code>)
	* @param subtype the role's subtype (optionally <code>null</code>)
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the expando bridge attributes for the
	role.
	* @return the role
	*/
	public static com.liferay.portal.kernel.model.Role addRole(
		String className, long classPK, String name,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap, int type,
		String subtype, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addRole(className, classPK, name, titleMap, descriptionMap,
			type, subtype, serviceContext);
	}

	/**
	* Adds the roles to the user. The user is reindexed after the roles are
	* added.
	*
	* @param userId the primary key of the user
	* @param roleIds the primary keys of the roles
	*/
	public static void addUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addUserRoles(userId, roleIds);
	}

	/**
	* Deletes the role with the primary key and its associated permissions.
	*
	* @param roleId the primary key of the role
	*/
	public static void deleteRole(long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRole(roleId);
	}

	public static com.liferay.portal.kernel.model.Role fetchRole(long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchRole(roleId);
	}

	/**
	* Returns all the roles associated with the group.
	*
	* @param groupId the primary key of the group
	* @return the roles associated with the group
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Role> getGroupRoles(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroupRoles(groupId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> getGroupRolesAndTeamRoles(
		long companyId, String keywords, java.util.List<String> excludedNames,
		int[] types, long excludedTeamRoleId, long teamGroupId, int start,
		int end) {
		return getService()
				   .getGroupRolesAndTeamRoles(companyId, keywords,
			excludedNames, types, excludedTeamRoleId, teamGroupId, start, end);
	}

	public static int getGroupRolesAndTeamRolesCount(long companyId,
		String keywords, java.util.List<String> excludedNames, int[] types,
		long excludedTeamRoleId, long teamGroupId) {
		return getService()
				   .getGroupRolesAndTeamRolesCount(companyId, keywords,
			excludedNames, types, excludedTeamRoleId, teamGroupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Returns the role with the primary key.
	*
	* @param roleId the primary key of the role
	* @return the role with the primary key
	*/
	public static com.liferay.portal.kernel.model.Role getRole(long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRole(roleId);
	}

	/**
	* Returns the role with the name in the company.
	*
	* <p>
	* The method searches the system roles map first for default roles. If a
	* role with the name is not found, then the method will query the database.
	* </p>
	*
	* @param companyId the primary key of the company
	* @param name the role's name
	* @return the role with the name
	*/
	public static com.liferay.portal.kernel.model.Role getRole(long companyId,
		String name) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRole(companyId, name);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> getRoles(
		int type, String subtype)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRoles(type, subtype);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> getRoles(
		long companyId, int[] types)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRoles(companyId, types);
	}

	/**
	* Returns all the user's roles within the user group.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @return the user's roles within the user group
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Role> getUserGroupGroupRoles(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserGroupGroupRoles(userId, groupId);
	}

	/**
	* Returns all the user's roles within the user group.
	*
	* @param userId the primary key of the user
	* @param groupId the primary key of the group
	* @return the user's roles within the user group
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Role> getUserGroupRoles(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserGroupRoles(userId, groupId);
	}

	/**
	* Returns the union of all the user's roles within the groups.
	*
	* @param userId the primary key of the user
	* @param groups the groups (optionally <code>null</code>)
	* @return the union of all the user's roles within the groups
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Role> getUserRelatedRoles(
		long userId,
		java.util.List<com.liferay.portal.kernel.model.Group> groups)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserRelatedRoles(userId, groups);
	}

	/**
	* Returns all the roles associated with the user.
	*
	* @param userId the primary key of the user
	* @return the roles associated with the user
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Role> getUserRoles(
		long userId) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserRoles(userId);
	}

	/**
	* Returns <code>true</code> if the user is associated with the named
	* regular role.
	*
	* @param userId the primary key of the user
	* @param companyId the primary key of the company
	* @param name the name of the role
	* @param inherited whether to include the user's inherited roles in the
	search
	* @return <code>true</code> if the user is associated with the regular
	role; <code>false</code> otherwise
	*/
	public static boolean hasUserRole(long userId, long companyId, String name,
		boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().hasUserRole(userId, companyId, name, inherited);
	}

	/**
	* Returns <code>true</code> if the user has any one of the named regular
	* roles.
	*
	* @param userId the primary key of the user
	* @param companyId the primary key of the company
	* @param names the names of the roles
	* @param inherited whether to include the user's inherited roles in the
	search
	* @return <code>true</code> if the user has any one of the regular roles;
	<code>false</code> otherwise
	*/
	public static boolean hasUserRoles(long userId, long companyId,
		String[] names, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().hasUserRoles(userId, companyId, names, inherited);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> search(
		long companyId, String keywords, Integer[] types,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Role> obc) {
		return getService()
				   .search(companyId, keywords, types, params, start, end, obc);
	}

	public static int searchCount(long companyId, String keywords,
		Integer[] types, java.util.LinkedHashMap<String, Object> params) {
		return getService().searchCount(companyId, keywords, types, params);
	}

	/**
	* Removes the matching roles associated with the user. The user is
	* reindexed after the roles are removed.
	*
	* @param userId the primary key of the user
	* @param roleIds the primary keys of the roles
	*/
	public static void unsetUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unsetUserRoles(userId, roleIds);
	}

	/**
	* Updates the role with the primary key.
	*
	* @param roleId the primary key of the role
	* @param name the role's new name
	* @param titleMap the new localized titles (optionally <code>null</code>)
	to replace those existing for the role
	* @param descriptionMap the new localized descriptions (optionally
	<code>null</code>) to replace those existing for the role
	* @param subtype the role's new subtype (optionally <code>null</code>)
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set the expando bridge attributes for the
	role.
	* @return the role with the primary key
	*/
	public static com.liferay.portal.kernel.model.Role updateRole(long roleId,
		String name, java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap, String subtype,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateRole(roleId, name, titleMap, descriptionMap, subtype,
			serviceContext);
	}

	public static RoleService getService() {
		if (_service == null) {
			_service = (RoleService)PortalBeanLocatorUtil.locate(RoleService.class.getName());

			ReferenceRegistry.registerReference(RoleServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static RoleService _service;
}