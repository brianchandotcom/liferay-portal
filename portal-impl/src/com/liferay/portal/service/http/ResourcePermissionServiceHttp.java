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
import com.liferay.portal.kernel.service.ResourcePermissionServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ResourcePermissionServiceUtil</code> service
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
 * @see ResourcePermissionServiceSoap
 * @generated
 */
public class ResourcePermissionServiceHttp {

	public static void addResourcePermission(
			HttpPrincipal httpPrincipal, long groupId, long companyId,
			String name, int scope, String primKey, long roleId,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourcePermissionServiceUtil.class, "addResourcePermission",
				_addResourcePermissionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, companyId, name, scope, primKey, roleId,
				actionId);

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

	public static void removeResourcePermission(
			HttpPrincipal httpPrincipal, long groupId, long companyId,
			String name, int scope, String primKey, long roleId,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourcePermissionServiceUtil.class, "removeResourcePermission",
				_removeResourcePermissionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, companyId, name, scope, primKey, roleId,
				actionId);

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

	public static void removeResourcePermissions(
			HttpPrincipal httpPrincipal, long groupId, long companyId,
			String name, int scope, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourcePermissionServiceUtil.class,
				"removeResourcePermissions",
				_removeResourcePermissionsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, companyId, name, scope, roleId, actionId);

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

	public static void setIndividualResourcePermissions(
			HttpPrincipal httpPrincipal, long groupId, long companyId,
			String name, String primKey, long roleId, String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourcePermissionServiceUtil.class,
				"setIndividualResourcePermissions",
				_setIndividualResourcePermissionsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, companyId, name, primKey, roleId,
				actionIds);

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

	public static void setIndividualResourcePermissions(
			HttpPrincipal httpPrincipal, long groupId, long companyId,
			String name, String primKey,
			java.util.Map<Long, String[]> roleIdsToActionIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourcePermissionServiceUtil.class,
				"setIndividualResourcePermissions",
				_setIndividualResourcePermissionsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, companyId, name, primKey,
				roleIdsToActionIds);

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
		ResourcePermissionServiceHttp.class);

	private static final Class<?>[] _addResourcePermissionParameterTypes0 =
		new Class[] {
			long.class, long.class, String.class, int.class, String.class,
			long.class, String.class
		};
	private static final Class<?>[] _removeResourcePermissionParameterTypes1 =
		new Class[] {
			long.class, long.class, String.class, int.class, String.class,
			long.class, String.class
		};
	private static final Class<?>[] _removeResourcePermissionsParameterTypes2 =
		new Class[] {
			long.class, long.class, String.class, int.class, long.class,
			String.class
		};
	private static final Class<?>[]
		_setIndividualResourcePermissionsParameterTypes3 = new Class[] {
			long.class, long.class, String.class, String.class, long.class,
			String[].class
		};
	private static final Class<?>[]
		_setIndividualResourcePermissionsParameterTypes4 = new Class[] {
			long.class, long.class, String.class, String.class,
			java.util.Map.class
		};

}