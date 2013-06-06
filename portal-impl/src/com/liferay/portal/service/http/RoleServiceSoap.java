/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.service.RoleServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portal.service.RoleServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.model.RoleSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.model.Role}, that is translated to a
 * {@link com.liferay.portal.model.RoleSoap}. Methods that SOAP cannot
 * safely wire are skipped.
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
 * @see com.liferay.portal.model.RoleSoap
 * @see com.liferay.portal.service.RoleServiceUtil
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
	* @throws PortalException if a user with the primary key could not be
	found, if the user did not have permission to add roles, if the
	class name or the role name were invalid, or if the role is a
	duplicate
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RoleSoap addRole(
		java.lang.String className, long classPK, java.lang.String name,
		java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, int type,
		java.lang.String subtype,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portal.model.Role returnValue = RoleServiceUtil.addRole(className,
					classPK, name, titleMap, descriptionMap, type, subtype,
					serviceContext);

			return com.liferay.portal.model.RoleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Adds a role. The user is reindexed after role is added.
	*
	* @param name the role's name
	* @param titleMap the role's localized titles (optionally
	<code>null</code>)
	* @param descriptionMap the role's localized descriptions (optionally
	<code>null</code>)
	* @param type the role's type (optionally <code>0</code>)
	* @return the role
	* @throws PortalException if a user with the primary key could not be
	found, if the user did not have permission to add roles, if
	the class name or the role name were invalid, or if the role
	is a duplicate
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #addRole(String, long,
	String, Map, Map, int, String, ServiceContext)}
	*/
	public static com.liferay.portal.model.RoleSoap addRole(
		java.lang.String name, java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, int type)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portal.model.Role returnValue = RoleServiceUtil.addRole(name,
					titleMap, descriptionMap, type);

			return com.liferay.portal.model.RoleSoap.toSoapModel(returnValue);
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
	* @throws PortalException if a user with the primary key could not be found
	or if the user did not have permission to assign members to one
	of the roles
	* @throws SystemException if a system exception occurred
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
	* @throws PortalException if the user did not have permission to delete the
	role, if a role with the primary key could not be found, if the
	role is a default system role, or if the role's resource could
	not be found
	* @throws SystemException if a system exception occurred
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

	/**
	* Returns all the roles associated with the group.
	*
	* @param groupId the primary key of the group
	* @return the roles associated with the group
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RoleSoap[] getGroupRoles(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Role> returnValue = RoleServiceUtil.getGroupRoles(groupId);

			return com.liferay.portal.model.RoleSoap.toSoapModels(returnValue);
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
	* @throws PortalException if a role with the primary key could not be found
	or if the user did not have permission to view the role
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RoleSoap getRole(long roleId)
		throws RemoteException {
		try {
			com.liferay.portal.model.Role returnValue = RoleServiceUtil.getRole(roleId);

			return com.liferay.portal.model.RoleSoap.toSoapModel(returnValue);
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
	* @throws PortalException if a role with the name could not be found in the
	company or if the user did not have permission to view the role
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RoleSoap getRole(long companyId,
		java.lang.String name) throws RemoteException {
		try {
			com.liferay.portal.model.Role returnValue = RoleServiceUtil.getRole(companyId,
					name);

			return com.liferay.portal.model.RoleSoap.toSoapModel(returnValue);
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
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RoleSoap[] getUserGroupGroupRoles(
		long userId, long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Role> returnValue = RoleServiceUtil.getUserGroupGroupRoles(userId,
					groupId);

			return com.liferay.portal.model.RoleSoap.toSoapModels(returnValue);
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
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RoleSoap[] getUserGroupRoles(
		long userId, long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Role> returnValue = RoleServiceUtil.getUserGroupRoles(userId,
					groupId);

			return com.liferay.portal.model.RoleSoap.toSoapModels(returnValue);
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
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RoleSoap[] getUserRelatedRoles(
		long userId, com.liferay.portal.model.GroupSoap[] groups)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Role> returnValue = RoleServiceUtil.getUserRelatedRoles(userId,
					com.liferay.portal.model.impl.GroupModelImpl.toModels(
						groups));

			return com.liferay.portal.model.RoleSoap.toSoapModels(returnValue);
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
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RoleSoap[] getUserRoles(long userId)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Role> returnValue = RoleServiceUtil.getUserRoles(userId);

			return com.liferay.portal.model.RoleSoap.toSoapModels(returnValue);
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
	* @throws PortalException if a role with the name could not be found in the
	company or if a default user for the company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasUserRole(long userId, long companyId,
		java.lang.String name, boolean inherited) throws RemoteException {
		try {
			boolean returnValue = RoleServiceUtil.hasUserRole(userId,
					companyId, name, inherited);

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
	* @throws PortalException if any one of the roles with the names could not
	be found in the company or if the default user for the company
	could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasUserRoles(long userId, long companyId,
		java.lang.String[] names, boolean inherited) throws RemoteException {
		try {
			boolean returnValue = RoleServiceUtil.hasUserRoles(userId,
					companyId, names, inherited);

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
	* @throws PortalException if a user with the primary key could not be
	found, if the user did not have permission to remove members from
	a role, or if a role with any one of the primary keys could not
	be found
	* @throws SystemException if a system exception occurred
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
	* @throws PortalException if the user did not have permission to update the
	role, if a role with the primary could not be found, or if the
	role's name was invalid
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.RoleSoap updateRole(long roleId,
		java.lang.String name, java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, java.lang.String subtype,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portal.model.Role returnValue = RoleServiceUtil.updateRole(roleId,
					name, titleMap, descriptionMap, subtype, serviceContext);

			return com.liferay.portal.model.RoleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RoleServiceSoap.class);
}