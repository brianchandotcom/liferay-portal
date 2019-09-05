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
import com.liferay.portal.kernel.service.RoleServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>RoleServiceUtil</code> service
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
 * @see RoleServiceSoap
 * @generated
 */
public class RoleServiceHttp {

	public static com.liferay.portal.kernel.model.Role addRole(
			HttpPrincipal httpPrincipal, String className, long classPK,
			String name, java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap, int type,
			String subtype,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "addRole", _addRoleParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, name, titleMap, descriptionMap,
				type, subtype, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.Role)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addUserRoles(
			HttpPrincipal httpPrincipal, long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "addUserRoles",
				_addUserRolesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, roleIds);

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

	public static void deleteRole(HttpPrincipal httpPrincipal, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "deleteRole",
				_deleteRoleParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId);

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

	public static com.liferay.portal.kernel.model.Role fetchRole(
			HttpPrincipal httpPrincipal, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "fetchRole", _fetchRoleParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.Role)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
			getGroupRoles(HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "getGroupRoles",
				_getGroupRolesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (java.util.List<com.liferay.portal.kernel.model.Role>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
		getGroupRolesAndTeamRoles(
			HttpPrincipal httpPrincipal, long companyId, String keywords,
			java.util.List<String> excludedNames, int[] types,
			long excludedTeamRoleId, long teamGroupId, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "getGroupRolesAndTeamRoles",
				_getGroupRolesAndTeamRolesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, keywords, excludedNames, types,
				excludedTeamRoleId, teamGroupId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Role>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupRolesAndTeamRolesCount(
		HttpPrincipal httpPrincipal, long companyId, String keywords,
		java.util.List<String> excludedNames, int[] types,
		long excludedTeamRoleId, long teamGroupId) {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "getGroupRolesAndTeamRolesCount",
				_getGroupRolesAndTeamRolesCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, keywords, excludedNames, types,
				excludedTeamRoleId, teamGroupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.Role getRole(
			HttpPrincipal httpPrincipal, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "getRole", _getRoleParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.Role)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.Role getRole(
			HttpPrincipal httpPrincipal, long companyId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "getRole", _getRoleParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.Role)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> getRoles(
			HttpPrincipal httpPrincipal, int type, String subtype)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "getRoles", _getRolesParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, type, subtype);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (java.util.List<com.liferay.portal.kernel.model.Role>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> getRoles(
			HttpPrincipal httpPrincipal, long companyId, int[] types)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "getRoles", _getRolesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, types);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (java.util.List<com.liferay.portal.kernel.model.Role>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
			getUserGroupGroupRoles(
				HttpPrincipal httpPrincipal, long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "getUserGroupGroupRoles",
				_getUserGroupGroupRolesParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (java.util.List<com.liferay.portal.kernel.model.Role>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
			getUserGroupRoles(
				HttpPrincipal httpPrincipal, long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "getUserGroupRoles",
				_getUserGroupRolesParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (java.util.List<com.liferay.portal.kernel.model.Role>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
			getUserRelatedRoles(
				HttpPrincipal httpPrincipal, long userId,
				java.util.List<com.liferay.portal.kernel.model.Group> groups)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "getUserRelatedRoles",
				_getUserRelatedRolesParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, groups);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (java.util.List<com.liferay.portal.kernel.model.Role>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role>
			getUserRoles(HttpPrincipal httpPrincipal, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "getUserRoles",
				_getUserRolesParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (java.util.List<com.liferay.portal.kernel.model.Role>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasUserRole(
			HttpPrincipal httpPrincipal, long userId, long companyId,
			String name, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "hasUserRole",
				_hasUserRoleParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, companyId, name, inherited);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasUserRoles(
			HttpPrincipal httpPrincipal, long userId, long companyId,
			String[] names, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "hasUserRoles",
				_hasUserRolesParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, companyId, names, inherited);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Role> search(
		HttpPrincipal httpPrincipal, long companyId, String keywords,
		Integer[] types, java.util.LinkedHashMap<String, Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Role> obc) {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "search", _searchParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, keywords, types, params, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Role>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, String keywords,
		Integer[] types, java.util.LinkedHashMap<String, Object> params) {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "searchCount",
				_searchCountParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, keywords, types, params);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetUserRoles(
			HttpPrincipal httpPrincipal, long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "unsetUserRoles",
				_unsetUserRolesParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, roleIds);

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

	public static com.liferay.portal.kernel.model.Role updateRole(
			HttpPrincipal httpPrincipal, long roleId, String name,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String subtype,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RoleServiceUtil.class, "updateRole",
				_updateRoleParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, roleId, name, titleMap, descriptionMap, subtype,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.Role)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RoleServiceHttp.class);

	private static final Class<?>[] _addRoleParameterTypes0 = new Class[] {
		String.class, long.class, String.class, java.util.Map.class,
		java.util.Map.class, int.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addUserRolesParameterTypes1 = new Class[] {
		long.class, long[].class
	};
	private static final Class<?>[] _deleteRoleParameterTypes2 = new Class[] {
		long.class
	};
	private static final Class<?>[] _fetchRoleParameterTypes3 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getGroupRolesParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _getGroupRolesAndTeamRolesParameterTypes5 =
		new Class[] {
			long.class, String.class, java.util.List.class, int[].class,
			long.class, long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getGroupRolesAndTeamRolesCountParameterTypes6 = new Class[] {
			long.class, String.class, java.util.List.class, int[].class,
			long.class, long.class
		};
	private static final Class<?>[] _getRoleParameterTypes7 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getRoleParameterTypes8 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _getRolesParameterTypes9 = new Class[] {
		int.class, String.class
	};
	private static final Class<?>[] _getRolesParameterTypes10 = new Class[] {
		long.class, int[].class
	};
	private static final Class<?>[] _getUserGroupGroupRolesParameterTypes11 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getUserGroupRolesParameterTypes12 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getUserRelatedRolesParameterTypes13 =
		new Class[] {long.class, java.util.List.class};
	private static final Class<?>[] _getUserRolesParameterTypes14 =
		new Class[] {long.class};
	private static final Class<?>[] _hasUserRoleParameterTypes15 = new Class[] {
		long.class, long.class, String.class, boolean.class
	};
	private static final Class<?>[] _hasUserRolesParameterTypes16 =
		new Class[] {long.class, long.class, String[].class, boolean.class};
	private static final Class<?>[] _searchParameterTypes17 = new Class[] {
		long.class, String.class, Integer[].class,
		java.util.LinkedHashMap.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchCountParameterTypes18 = new Class[] {
		long.class, String.class, Integer[].class, java.util.LinkedHashMap.class
	};
	private static final Class<?>[] _unsetUserRolesParameterTypes19 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _updateRoleParameterTypes20 = new Class[] {
		long.class, String.class, java.util.Map.class, java.util.Map.class,
		String.class, com.liferay.portal.kernel.service.ServiceContext.class
	};

}