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
import com.liferay.portal.kernel.service.ResourceBlockServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ResourceBlockServiceUtil</code> service
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
 * @see ResourceBlockServiceSoap
 * @deprecated
 * @generated
 */
@Deprecated
public class ResourceBlockServiceHttp {

	public static void addCompanyScopePermission(
			HttpPrincipal httpPrincipal, long scopeGroupId, long companyId,
			String name, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourceBlockServiceUtil.class, "addCompanyScopePermission",
				_addCompanyScopePermissionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, scopeGroupId, companyId, name, roleId, actionId);

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

	public static void addGroupScopePermission(
			HttpPrincipal httpPrincipal, long scopeGroupId, long companyId,
			long groupId, String name, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourceBlockServiceUtil.class, "addGroupScopePermission",
				_addGroupScopePermissionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, scopeGroupId, companyId, groupId, name, roleId,
				actionId);

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

	public static void addIndividualScopePermission(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			String name, long primKey, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourceBlockServiceUtil.class, "addIndividualScopePermission",
				_addIndividualScopePermissionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, name, primKey, roleId, actionId);

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

	public static void removeAllGroupScopePermissions(
			HttpPrincipal httpPrincipal, long scopeGroupId, long companyId,
			String name, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourceBlockServiceUtil.class,
				"removeAllGroupScopePermissions",
				_removeAllGroupScopePermissionsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, scopeGroupId, companyId, name, roleId, actionId);

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

	public static void removeCompanyScopePermission(
			HttpPrincipal httpPrincipal, long scopeGroupId, long companyId,
			String name, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourceBlockServiceUtil.class, "removeCompanyScopePermission",
				_removeCompanyScopePermissionParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, scopeGroupId, companyId, name, roleId, actionId);

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

	public static void removeGroupScopePermission(
			HttpPrincipal httpPrincipal, long scopeGroupId, long companyId,
			long groupId, String name, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourceBlockServiceUtil.class, "removeGroupScopePermission",
				_removeGroupScopePermissionParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, scopeGroupId, companyId, groupId, name, roleId,
				actionId);

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

	public static void removeIndividualScopePermission(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			String name, long primKey, long roleId, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourceBlockServiceUtil.class,
				"removeIndividualScopePermission",
				_removeIndividualScopePermissionParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, name, primKey, roleId, actionId);

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

	public static void setCompanyScopePermissions(
			HttpPrincipal httpPrincipal, long scopeGroupId, long companyId,
			String name, long roleId, java.util.List<String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourceBlockServiceUtil.class, "setCompanyScopePermissions",
				_setCompanyScopePermissionsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, scopeGroupId, companyId, name, roleId, actionIds);

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

	public static void setGroupScopePermissions(
			HttpPrincipal httpPrincipal, long scopeGroupId, long companyId,
			long groupId, String name, long roleId,
			java.util.List<String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourceBlockServiceUtil.class, "setGroupScopePermissions",
				_setGroupScopePermissionsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, scopeGroupId, companyId, groupId, name, roleId,
				actionIds);

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

	public static void setIndividualScopePermissions(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			String name, long primKey, long roleId,
			java.util.List<String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourceBlockServiceUtil.class, "setIndividualScopePermissions",
				_setIndividualScopePermissionsParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, name, primKey, roleId,
				actionIds);

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

	public static void setIndividualScopePermissions(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			String name, long primKey,
			java.util.Map<Long, String[]> roleIdsToActionIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ResourceBlockServiceUtil.class, "setIndividualScopePermissions",
				_setIndividualScopePermissionsParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, name, primKey,
				roleIdsToActionIds);

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
		ResourceBlockServiceHttp.class);

	private static final Class<?>[] _addCompanyScopePermissionParameterTypes0 =
		new Class[] {
			long.class, long.class, String.class, long.class, String.class
		};
	private static final Class<?>[] _addGroupScopePermissionParameterTypes1 =
		new Class[] {
			long.class, long.class, long.class, String.class, long.class,
			String.class
		};
	private static final Class<?>[]
		_addIndividualScopePermissionParameterTypes2 = new Class[] {
			long.class, long.class, String.class, long.class, long.class,
			String.class
		};
	private static final Class<?>[]
		_removeAllGroupScopePermissionsParameterTypes3 = new Class[] {
			long.class, long.class, String.class, long.class, String.class
		};
	private static final Class<?>[]
		_removeCompanyScopePermissionParameterTypes4 = new Class[] {
			long.class, long.class, String.class, long.class, String.class
		};
	private static final Class<?>[] _removeGroupScopePermissionParameterTypes5 =
		new Class[] {
			long.class, long.class, long.class, String.class, long.class,
			String.class
		};
	private static final Class<?>[]
		_removeIndividualScopePermissionParameterTypes6 = new Class[] {
			long.class, long.class, String.class, long.class, long.class,
			String.class
		};
	private static final Class<?>[] _setCompanyScopePermissionsParameterTypes7 =
		new Class[] {
			long.class, long.class, String.class, long.class,
			java.util.List.class
		};
	private static final Class<?>[] _setGroupScopePermissionsParameterTypes8 =
		new Class[] {
			long.class, long.class, long.class, String.class, long.class,
			java.util.List.class
		};
	private static final Class<?>[]
		_setIndividualScopePermissionsParameterTypes9 = new Class[] {
			long.class, long.class, String.class, long.class, long.class,
			java.util.List.class
		};
	private static final Class<?>[]
		_setIndividualScopePermissionsParameterTypes10 = new Class[] {
			long.class, long.class, String.class, long.class,
			java.util.Map.class
		};

}