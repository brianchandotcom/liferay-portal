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
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.ResourceBlockServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link com.liferay.portal.service.ResourceBlockServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
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
 * @see       ResourceBlockServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portal.service.ResourceBlockServiceUtil
 * @generated
 */
public class ResourceBlockServiceHttp {
	public static void addCompanyScopePermission(HttpPrincipal httpPrincipal,
		long scopeGroupId, long companyId, java.lang.String name, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ResourceBlockServiceUtil.class,
					"addCompanyScopePermission",
					_addCompanyScopePermissionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					scopeGroupId, companyId, name, roleId, actionId);

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

	public static void addGroupScopePermission(HttpPrincipal httpPrincipal,
		long scopeGroupId, long companyId, long groupId, java.lang.String name,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ResourceBlockServiceUtil.class,
					"addGroupScopePermission",
					_addGroupScopePermissionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					scopeGroupId, companyId, groupId, name, roleId, actionId);

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

	public static void addIndividualScopePermission(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.lang.String name, long primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ResourceBlockServiceUtil.class,
					"addIndividualScopePermission",
					_addIndividualScopePermissionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, name, primKey, roleId, actionId);

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

	public static void removeAllGroupScopePermissions(
		HttpPrincipal httpPrincipal, long scopeGroupId, long companyId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ResourceBlockServiceUtil.class,
					"removeAllGroupScopePermissions",
					_removeAllGroupScopePermissionsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					scopeGroupId, companyId, name, roleId, actionId);

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

	public static void removeCompanyScopePermission(
		HttpPrincipal httpPrincipal, long scopeGroupId, long companyId,
		java.lang.String name, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ResourceBlockServiceUtil.class,
					"removeCompanyScopePermission",
					_removeCompanyScopePermissionParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					scopeGroupId, companyId, name, roleId, actionId);

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

	public static void removeGroupScopePermission(HttpPrincipal httpPrincipal,
		long scopeGroupId, long companyId, long groupId, java.lang.String name,
		long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ResourceBlockServiceUtil.class,
					"removeGroupScopePermission",
					_removeGroupScopePermissionParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					scopeGroupId, companyId, groupId, name, roleId, actionId);

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

	public static void removeIndividualScopePermission(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.lang.String name, long primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ResourceBlockServiceUtil.class,
					"removeIndividualScopePermission",
					_removeIndividualScopePermissionParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, name, primKey, roleId, actionId);

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

	public static void setCompanyScopePermissions(HttpPrincipal httpPrincipal,
		long scopeGroupId, long companyId, java.lang.String name, long roleId,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ResourceBlockServiceUtil.class,
					"setCompanyScopePermissions",
					_setCompanyScopePermissionsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					scopeGroupId, companyId, name, roleId, actionIds);

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

	public static void setGroupScopePermissions(HttpPrincipal httpPrincipal,
		long scopeGroupId, long companyId, long groupId, java.lang.String name,
		long roleId, java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ResourceBlockServiceUtil.class,
					"setGroupScopePermissions",
					_setGroupScopePermissionsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					scopeGroupId, companyId, groupId, name, roleId, actionIds);

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

	public static void setIndividualScopePermissions(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.lang.String name, long primKey, long roleId,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ResourceBlockServiceUtil.class,
					"setIndividualScopePermissions",
					_setIndividualScopePermissionsParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, name, primKey, roleId, actionIds);

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

	public static void setIndividualScopePermissions(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.lang.String name, long primKey,
		java.util.Map<java.lang.Long, java.lang.String[]> roleIdsToActionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(ResourceBlockServiceUtil.class,
					"setIndividualScopePermissions",
					_setIndividualScopePermissionsParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, name, primKey, roleIdsToActionIds);

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

	private static Log _log = LogFactoryUtil.getLog(ResourceBlockServiceHttp.class);
	private static final Class<?>[] _addCompanyScopePermissionParameterTypes0 = new Class[] {
			long.class, long.class, java.lang.String.class, long.class,
			java.lang.String.class
		};
	private static final Class<?>[] _addGroupScopePermissionParameterTypes1 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _addIndividualScopePermissionParameterTypes2 =
		new Class[] {
			long.class, long.class, java.lang.String.class, long.class,
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _removeAllGroupScopePermissionsParameterTypes3 =
		new Class[] {
			long.class, long.class, java.lang.String.class, long.class,
			java.lang.String.class
		};
	private static final Class<?>[] _removeCompanyScopePermissionParameterTypes4 =
		new Class[] {
			long.class, long.class, java.lang.String.class, long.class,
			java.lang.String.class
		};
	private static final Class<?>[] _removeGroupScopePermissionParameterTypes5 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _removeIndividualScopePermissionParameterTypes6 =
		new Class[] {
			long.class, long.class, java.lang.String.class, long.class,
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _setCompanyScopePermissionsParameterTypes7 = new Class[] {
			long.class, long.class, java.lang.String.class, long.class,
			java.util.List.class
		};
	private static final Class<?>[] _setGroupScopePermissionsParameterTypes8 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			long.class, java.util.List.class
		};
	private static final Class<?>[] _setIndividualScopePermissionsParameterTypes9 =
		new Class[] {
			long.class, long.class, java.lang.String.class, long.class,
			long.class, java.util.List.class
		};
	private static final Class<?>[] _setIndividualScopePermissionsParameterTypes10 =
		new Class[] {
			long.class, long.class, java.lang.String.class, long.class,
			java.util.Map.class
		};
}