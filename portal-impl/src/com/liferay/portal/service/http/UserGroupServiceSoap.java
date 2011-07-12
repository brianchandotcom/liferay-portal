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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.UserGroupServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portal.service.UserGroupServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.model.UserGroupSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.model.UserGroup}, that is translated to a
 * {@link com.liferay.portal.model.UserGroupSoap}. Methods that SOAP cannot
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
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupServiceHttp
 * @see       com.liferay.portal.model.UserGroupSoap
 * @see       com.liferay.portal.service.UserGroupServiceUtil
 * @generated
 */
public class UserGroupServiceSoap {
	public static void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws RemoteException {
		try {
			UserGroupServiceUtil.addGroupUserGroups(groupId, userGroupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void addTeamUserGroups(long teamId, long[] userGroupIds)
		throws RemoteException {
		try {
			UserGroupServiceUtil.addTeamUserGroups(teamId, userGroupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserGroupSoap addUserGroup(
		java.lang.String name, java.lang.String description,
		long publicLayoutSetPrototypeId, long privateLayoutSetPrototypeId)
		throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.addUserGroup(name,
					description, publicLayoutSetPrototypeId,
					privateLayoutSetPrototypeId);

			return com.liferay.portal.model.UserGroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteUserGroup(long userGroupId)
		throws RemoteException {
		try {
			UserGroupServiceUtil.deleteUserGroup(userGroupId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserGroupSoap getUserGroup(
		long userGroupId) throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.getUserGroup(userGroupId);

			return com.liferay.portal.model.UserGroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserGroupSoap getUserGroup(
		java.lang.String name) throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.getUserGroup(name);

			return com.liferay.portal.model.UserGroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserGroupSoap[] getUserUserGroups(
		long userId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.UserGroup> returnValue = UserGroupServiceUtil.getUserUserGroups(userId);

			return com.liferay.portal.model.UserGroupSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws RemoteException {
		try {
			UserGroupServiceUtil.unsetGroupUserGroups(groupId, userGroupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetTeamUserGroups(long teamId, long[] userGroupIds)
		throws RemoteException {
		try {
			UserGroupServiceUtil.unsetTeamUserGroups(teamId, userGroupIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.UserGroupSoap updateUserGroup(
		long userGroupId, java.lang.String name, java.lang.String description,
		long publicLayoutSetPrototypeId, long privateLayoutSetPrototypeId)
		throws RemoteException {
		try {
			com.liferay.portal.model.UserGroup returnValue = UserGroupServiceUtil.updateUserGroup(userGroupId,
					name, description, publicLayoutSetPrototypeId,
					privateLayoutSetPrototypeId);

			return com.liferay.portal.model.UserGroupSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UserGroupServiceSoap.class);
}