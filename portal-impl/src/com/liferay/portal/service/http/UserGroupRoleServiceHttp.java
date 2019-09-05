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
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.UserGroupRoleServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>UserGroupRoleServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupRoleServiceSoap
 * @generated
 */
public class UserGroupRoleServiceHttp {

	public static void addUserGroupRoles(
			HttpPrincipal httpPrincipal, long userId, long groupId,
			long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupRoleServiceUtil.class, "addUserGroupRoles",
				_addUserGroupRolesParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, groupId, roleIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addUserGroupRoles(
			HttpPrincipal httpPrincipal, long[] userIds, long groupId,
			long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupRoleServiceUtil.class, "addUserGroupRoles",
				_addUserGroupRolesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userIds, groupId, roleId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteUserGroupRoles(
			HttpPrincipal httpPrincipal, long userId, long groupId,
			long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupRoleServiceUtil.class, "deleteUserGroupRoles",
				_deleteUserGroupRolesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, groupId, roleIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteUserGroupRoles(
			HttpPrincipal httpPrincipal, long[] userIds, long groupId,
			long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupRoleServiceUtil.class, "deleteUserGroupRoles",
				_deleteUserGroupRolesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userIds, groupId, roleId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateUserGroupRoles(
			HttpPrincipal httpPrincipal, long userId, long groupId,
			long[] addedRoleIds, long[] deletedRoleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupRoleServiceUtil.class, "updateUserGroupRoles",
				_updateUserGroupRolesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, groupId, addedRoleIds, deletedRoleIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		UserGroupRoleServiceHttp.class);

	private static final Class<?>[] _addUserGroupRolesParameterTypes0 =
		new Class[] {long.class, long.class, long[].class};
	private static final Class<?>[] _addUserGroupRolesParameterTypes1 =
		new Class[] {long[].class, long.class, long.class};
	private static final Class<?>[] _deleteUserGroupRolesParameterTypes2 =
		new Class[] {long.class, long.class, long[].class};
	private static final Class<?>[] _deleteUserGroupRolesParameterTypes3 =
		new Class[] {long[].class, long.class, long.class};
	private static final Class<?>[] _updateUserGroupRolesParameterTypes4 =
		new Class[] {long.class, long.class, long[].class, long[].class};

}