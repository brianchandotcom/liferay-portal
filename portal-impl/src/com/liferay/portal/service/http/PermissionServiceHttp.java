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
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.PermissionServiceUtil;

/**
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portal.service.PermissionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
 * </p>
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
 * @author    Brian Wing Shun Chan
 * @see       PermissionServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portal.service.PermissionServiceUtil
 * @generated
 */
public class PermissionServiceHttp {
	public static void checkPermission(HttpPrincipal httpPrincipal,
		long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"checkPermission", _checkPermissionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					resourceId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void checkPermission(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String name, long primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"checkPermission", _checkPermissionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name, primKey);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void checkPermission(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String name, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"checkPermission", _checkPermissionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name, primKey);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasGroupPermission(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String actionId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"hasGroupPermission", _hasGroupPermissionParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					actionId, resourceId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasUserPermission(HttpPrincipal httpPrincipal,
		long userId, java.lang.String actionId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"hasUserPermission", _hasUserPermissionParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					actionId, resourceId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasUserPermissions(HttpPrincipal httpPrincipal,
		long userId, long groupId,
		java.util.List<com.liferay.portal.model.Resource> resources,
		java.lang.String actionId,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"hasUserPermissions", _hasUserPermissionsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					groupId, resources, actionId, permissionCheckerBag);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void setGroupPermissions(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"setGroupPermissions", _setGroupPermissionsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					actionIds, resourceId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void setGroupPermissions(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String classPK, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"setGroupPermissions", _setGroupPermissionsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					className, classPK, groupId, actionIds, resourceId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void setIndividualPermissions(HttpPrincipal httpPrincipal,
		long groupId, long companyId,
		java.util.Map<java.lang.Long, java.lang.String[]> roleIdsToActionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"setIndividualPermissions",
					_setIndividualPermissionsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					companyId, roleIdsToActionIds, resourceId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void setOrgGroupPermissions(HttpPrincipal httpPrincipal,
		long organizationId, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"setOrgGroupPermissions",
					_setOrgGroupPermissionsParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					organizationId, groupId, actionIds, resourceId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void setRolePermission(HttpPrincipal httpPrincipal,
		long roleId, long groupId, java.lang.String name, int scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"setRolePermission", _setRolePermissionParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId,
					groupId, name, scope, primKey, actionId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void setRolePermissions(HttpPrincipal httpPrincipal,
		long roleId, long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"setRolePermissions", _setRolePermissionsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId,
					groupId, actionIds, resourceId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void setUserPermissions(HttpPrincipal httpPrincipal,
		long userId, long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"setUserPermissions", _setUserPermissionsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					groupId, actionIds, resourceId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetRolePermission(HttpPrincipal httpPrincipal,
		long roleId, long groupId, long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"unsetRolePermission", _unsetRolePermissionParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId,
					groupId, permissionId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetRolePermission(HttpPrincipal httpPrincipal,
		long roleId, long groupId, java.lang.String name, int scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"unsetRolePermission", _unsetRolePermissionParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId,
					groupId, name, scope, primKey, actionId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetRolePermissions(HttpPrincipal httpPrincipal,
		long roleId, long groupId, java.lang.String name, int scope,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"unsetRolePermissions",
					_unsetRolePermissionsParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId,
					groupId, name, scope, actionId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetUserPermissions(HttpPrincipal httpPrincipal,
		long userId, long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PermissionServiceUtil.class.getName(),
					"unsetUserPermissions",
					_unsetUserPermissionsParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					groupId, actionIds, resourceId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PermissionServiceHttp.class);
	private static final Class<?>[] _checkPermissionParameterTypes0 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _checkPermissionParameterTypes1 = new Class[] {
			long.class, java.lang.String.class, long.class
		};
	private static final Class<?>[] _checkPermissionParameterTypes2 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class
		};
	private static final Class<?>[] _hasGroupPermissionParameterTypes3 = new Class[] {
			long.class, java.lang.String.class, long.class
		};
	private static final Class<?>[] _hasUserPermissionParameterTypes4 = new Class[] {
			long.class, java.lang.String.class, long.class
		};
	private static final Class<?>[] _hasUserPermissionsParameterTypes5 = new Class[] {
			long.class, long.class, java.util.List.class, java.lang.String.class,
			com.liferay.portal.security.permission.PermissionCheckerBag.class
		};
	private static final Class<?>[] _setGroupPermissionsParameterTypes6 = new Class[] {
			long.class, java.lang.String[].class, long.class
		};
	private static final Class<?>[] _setGroupPermissionsParameterTypes7 = new Class[] {
			java.lang.String.class, java.lang.String.class, long.class,
			java.lang.String[].class, long.class
		};
	private static final Class<?>[] _setIndividualPermissionsParameterTypes8 = new Class[] {
			long.class, long.class, java.util.Map.class, long.class
		};
	private static final Class<?>[] _setOrgGroupPermissionsParameterTypes9 = new Class[] {
			long.class, long.class, java.lang.String[].class, long.class
		};
	private static final Class<?>[] _setRolePermissionParameterTypes10 = new Class[] {
			long.class, long.class, java.lang.String.class, int.class,
			java.lang.String.class, java.lang.String.class
		};
	private static final Class<?>[] _setRolePermissionsParameterTypes11 = new Class[] {
			long.class, long.class, java.lang.String[].class, long.class
		};
	private static final Class<?>[] _setUserPermissionsParameterTypes12 = new Class[] {
			long.class, long.class, java.lang.String[].class, long.class
		};
	private static final Class<?>[] _unsetRolePermissionParameterTypes13 = new Class[] {
			long.class, long.class, long.class
		};
	private static final Class<?>[] _unsetRolePermissionParameterTypes14 = new Class[] {
			long.class, long.class, java.lang.String.class, int.class,
			java.lang.String.class, java.lang.String.class
		};
	private static final Class<?>[] _unsetRolePermissionsParameterTypes15 = new Class[] {
			long.class, long.class, java.lang.String.class, int.class,
			java.lang.String.class
		};
	private static final Class<?>[] _unsetUserPermissionsParameterTypes16 = new Class[] {
			long.class, long.class, java.lang.String[].class, long.class
		};
}