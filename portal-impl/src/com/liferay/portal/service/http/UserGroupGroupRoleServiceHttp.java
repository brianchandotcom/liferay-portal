/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.UserGroupGroupRoleServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>UserGroupGroupRoleServiceUtil</code> service
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
 * @generated
 */
public class UserGroupGroupRoleServiceHttp {

	public static void addUserGroupGroupRoles(
			HttpPrincipal httpPrincipal, long userGroupId, long groupId,
			long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupGroupRoleServiceUtil.class, "addUserGroupGroupRoles",
				_addUserGroupGroupRolesParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroupId, groupId, roleIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void addUserGroupGroupRoles(
			HttpPrincipal httpPrincipal, long[] userGroupIds, long groupId,
			long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupGroupRoleServiceUtil.class, "addUserGroupGroupRoles",
				_addUserGroupGroupRolesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroupIds, groupId, roleId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteUserGroupGroupRoles(
			HttpPrincipal httpPrincipal, long userGroupId, long groupId,
			long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupGroupRoleServiceUtil.class,
				"deleteUserGroupGroupRoles",
				_deleteUserGroupGroupRolesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroupId, groupId, roleIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteUserGroupGroupRoles(
			HttpPrincipal httpPrincipal, long[] userGroupIds, long groupId,
			long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupGroupRoleServiceUtil.class,
				"deleteUserGroupGroupRoles",
				_deleteUserGroupGroupRolesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroupIds, groupId, roleId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		UserGroupGroupRoleServiceHttp.class);

	private static final Class<?>[] _addUserGroupGroupRolesParameterTypes0 =
		new Class[] {long.class, long.class, long[].class};
	private static final Class<?>[] _addUserGroupGroupRolesParameterTypes1 =
		new Class[] {long[].class, long.class, long.class};
	private static final Class<?>[] _deleteUserGroupGroupRolesParameterTypes2 =
		new Class[] {long.class, long.class, long[].class};
	private static final Class<?>[] _deleteUserGroupGroupRolesParameterTypes3 =
		new Class[] {long[].class, long.class, long.class};

}