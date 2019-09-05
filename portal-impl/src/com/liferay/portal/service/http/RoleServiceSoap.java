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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.RoleServiceUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>RoleServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.kernel.model.RoleSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.kernel.model.Role</code>, that is translated to a
 * <code>com.liferay.portal.kernel.model.RoleSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RoleServiceHttp
 * @generated
 */
public class RoleServiceSoap {

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
	public static com.liferay.portal.kernel.model.RoleSoap addRole(
			String className, long classPK, String name,
			String[] titleMapLanguageIds, String[] titleMapValues,
			String[] descriptionMapLanguageIds, String[] descriptionMapValues,
			int type, String subtype,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.portal.kernel.model.Role returnValue =
				RoleServiceUtil.addRole(
					className, classPK, name, titleMap, descriptionMap, type,
					subtype, serviceContext);

			return com.liferay.portal.kernel.model.RoleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Adds the roles to the user. The user is reindexed after the roles are
	 * added.
	 *
	 * @param userId the primary key of the user
	 * @param roleIds the primary keys of the roles
	 */
	public static void addUserRoles(long userId, long[] roleIds)
		throws RemoteException {

		try {
			RoleServiceUtil.addUserRoles(userId, roleIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Deletes the role with the primary key and its associated permissions.
	 *
	 * @param roleId the primary key of the role
	 */
	public static void deleteRole(long roleId) throws RemoteException {
		try {
			RoleServiceUtil.deleteRole(roleId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RoleSoap fetchRole(
			long roleId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Role returnValue =
				RoleServiceUtil.fetchRole(roleId);

			return com.liferay.portal.kernel.model.RoleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns all the roles associated with the group.
	 *
	 * @param groupId the primary key of the group
	 * @return the roles associated with the group
	 */
	public static com.liferay.portal.kernel.model.RoleSoap[] getGroupRoles(
			long groupId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Role> returnValue =
				RoleServiceUtil.getGroupRoles(groupId);

			return com.liferay.portal.kernel.model.RoleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RoleSoap[]
			getGroupRolesAndTeamRoles(
				long companyId, String keywords,
				java.util.List<String> excludedNames, int[] types,
				long excludedTeamRoleId, long teamGroupId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Role> returnValue =
				RoleServiceUtil.getGroupRolesAndTeamRoles(
					companyId, keywords, excludedNames, types,
					excludedTeamRoleId, teamGroupId, start, end);

			return com.liferay.portal.kernel.model.RoleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupRolesAndTeamRolesCount(
			long companyId, String keywords,
			java.util.List<String> excludedNames, int[] types,
			long excludedTeamRoleId, long teamGroupId)
		throws RemoteException {

		try {
			int returnValue = RoleServiceUtil.getGroupRolesAndTeamRolesCount(
				companyId, keywords, excludedNames, types, excludedTeamRoleId,
				teamGroupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the role with the primary key.
	 *
	 * @param roleId the primary key of the role
	 * @return the role with the primary key
	 */
	public static com.liferay.portal.kernel.model.RoleSoap getRole(long roleId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Role returnValue =
				RoleServiceUtil.getRole(roleId);

			return com.liferay.portal.kernel.model.RoleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.kernel.model.RoleSoap getRole(
			long companyId, String name)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Role returnValue =
				RoleServiceUtil.getRole(companyId, name);

			return com.liferay.portal.kernel.model.RoleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RoleSoap[] getRoles(
			int type, String subtype)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Role> returnValue =
				RoleServiceUtil.getRoles(type, subtype);

			return com.liferay.portal.kernel.model.RoleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RoleSoap[] getRoles(
			long companyId, int[] types)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Role> returnValue =
				RoleServiceUtil.getRoles(companyId, types);

			return com.liferay.portal.kernel.model.RoleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns all the user's roles within the user group.
	 *
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @return the user's roles within the user group
	 */
	public static com.liferay.portal.kernel.model.RoleSoap[]
			getUserGroupGroupRoles(long userId, long groupId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Role> returnValue =
				RoleServiceUtil.getUserGroupGroupRoles(userId, groupId);

			return com.liferay.portal.kernel.model.RoleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns all the user's roles within the user group.
	 *
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 * @return the user's roles within the user group
	 */
	public static com.liferay.portal.kernel.model.RoleSoap[] getUserGroupRoles(
			long userId, long groupId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Role> returnValue =
				RoleServiceUtil.getUserGroupRoles(userId, groupId);

			return com.liferay.portal.kernel.model.RoleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the union of all the user's roles within the groups.
	 *
	 * @param userId the primary key of the user
	 * @param groups the groups (optionally <code>null</code>)
	 * @return the union of all the user's roles within the groups
	 */
	public static com.liferay.portal.kernel.model.RoleSoap[]
			getUserRelatedRoles(
				long userId, com.liferay.portal.kernel.model.GroupSoap[] groups)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Role> returnValue =
				RoleServiceUtil.getUserRelatedRoles(
					userId,
					com.liferay.portal.model.impl.GroupModelImpl.toModels(
						groups));

			return com.liferay.portal.kernel.model.RoleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns all the roles associated with the user.
	 *
	 * @param userId the primary key of the user
	 * @return the roles associated with the user
	 */
	public static com.liferay.portal.kernel.model.RoleSoap[] getUserRoles(
			long userId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Role> returnValue =
				RoleServiceUtil.getUserRoles(userId);

			return com.liferay.portal.kernel.model.RoleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static boolean hasUserRole(
			long userId, long companyId, String name, boolean inherited)
		throws RemoteException {

		try {
			boolean returnValue = RoleServiceUtil.hasUserRole(
				userId, companyId, name, inherited);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static boolean hasUserRoles(
			long userId, long companyId, String[] names, boolean inherited)
		throws RemoteException {

		try {
			boolean returnValue = RoleServiceUtil.hasUserRoles(
				userId, companyId, names, inherited);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Removes the matching roles associated with the user. The user is
	 * reindexed after the roles are removed.
	 *
	 * @param userId the primary key of the user
	 * @param roleIds the primary keys of the roles
	 */
	public static void unsetUserRoles(long userId, long[] roleIds)
		throws RemoteException {

		try {
			RoleServiceUtil.unsetUserRoles(userId, roleIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.kernel.model.RoleSoap updateRole(
			long roleId, String name, String[] titleMapLanguageIds,
			String[] titleMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues, String subtype,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.portal.kernel.model.Role returnValue =
				RoleServiceUtil.updateRole(
					roleId, name, titleMap, descriptionMap, subtype,
					serviceContext);

			return com.liferay.portal.kernel.model.RoleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RoleServiceSoap.class);

}