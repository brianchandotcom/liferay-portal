/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.UserGroupServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>UserGroupServiceUtil</code> service
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
public class UserGroupServiceHttp {

	public static void addGroupUserGroups(
			HttpPrincipal httpPrincipal, long groupId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "addGroupUserGroups",
				_addGroupUserGroupsParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userGroupIds);

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

	public static com.liferay.portal.kernel.model.UserGroup
			addOrUpdateUserGroup(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				String name, String description,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "addOrUpdateUserGroup",
				_addOrUpdateUserGroupParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, name, description,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.UserGroup)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void addTeamUserGroups(
			HttpPrincipal httpPrincipal, long teamId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "addTeamUserGroups",
				_addTeamUserGroupsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamId, userGroupIds);

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

	public static com.liferay.portal.kernel.model.UserGroup addUserGroup(
			HttpPrincipal httpPrincipal, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "addUserGroup",
				_addUserGroupParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, name, description, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.UserGroup)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteUserGroup(
			HttpPrincipal httpPrincipal, long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "deleteUserGroup",
				_deleteUserGroupParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroupId);

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

	public static com.liferay.portal.kernel.model.UserGroup fetchUserGroup(
			HttpPrincipal httpPrincipal, long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "fetchUserGroup",
				_fetchUserGroupParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.UserGroup)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.UserGroup
			fetchUserGroupByExternalReferenceCode(
				HttpPrincipal httpPrincipal, long companyId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class,
				"fetchUserGroupByExternalReferenceCode",
				_fetchUserGroupByExternalReferenceCodeParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, externalReferenceCode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.UserGroup)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserGroup>
		getGtUserGroups(
			HttpPrincipal httpPrincipal, long gtUserGroupId, long companyId,
			long parentUserGroupId, int size) {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "getGtUserGroups",
				_getGtUserGroupsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, gtUserGroupId, companyId, parentUserGroupId, size);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.UserGroup>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.UserGroup getUserGroup(
			HttpPrincipal httpPrincipal, long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "getUserGroup",
				_getUserGroupParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.UserGroup)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.UserGroup getUserGroup(
			HttpPrincipal httpPrincipal, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "getUserGroup",
				_getUserGroupParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.UserGroup)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.UserGroup
			getUserGroupByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class,
				"getUserGroupByExternalReferenceCode",
				_getUserGroupByExternalReferenceCodeParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, companyId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.UserGroup)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserGroup>
			getUserGroups(HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "getUserGroups",
				_getUserGroupsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (java.util.List<com.liferay.portal.kernel.model.UserGroup>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserGroup>
		getUserGroups(
			HttpPrincipal httpPrincipal, long companyId, String name, int start,
			int end) {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "getUserGroups",
				_getUserGroupsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, name, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.UserGroup>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getUserGroupsCount(
		HttpPrincipal httpPrincipal, long companyId, String name) {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "getUserGroupsCount",
				_getUserGroupsCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserGroup>
			getUserUserGroups(HttpPrincipal httpPrincipal, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "getUserUserGroups",
				_getUserUserGroupsParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (java.util.List<com.liferay.portal.kernel.model.UserGroup>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserGroup>
		search(
			HttpPrincipal httpPrincipal, long companyId, String keywords,
			java.util.LinkedHashMap<String, Object> params, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "search", _searchParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, keywords, params, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.UserGroup>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.UserGroup>
		search(
			HttpPrincipal httpPrincipal, long companyId, String name,
			String description, java.util.LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.UserGroup> orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "search", _searchParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, name, description, params, andOperator,
				start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.UserGroup>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, String keywords,
		java.util.LinkedHashMap<String, Object> params) {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "searchCount",
				_searchCountParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, keywords, params);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, String name,
		String description, java.util.LinkedHashMap<String, Object> params,
		boolean andOperator) {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "searchCount",
				_searchCountParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, name, description, params, andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void unsetGroupUserGroups(
			HttpPrincipal httpPrincipal, long groupId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "unsetGroupUserGroups",
				_unsetGroupUserGroupsParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userGroupIds);

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

	public static void unsetTeamUserGroups(
			HttpPrincipal httpPrincipal, long teamId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "unsetTeamUserGroups",
				_unsetTeamUserGroupsParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamId, userGroupIds);

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

	public static com.liferay.portal.kernel.model.UserGroup
			updateExternalReferenceCode(
				HttpPrincipal httpPrincipal,
				com.liferay.portal.kernel.model.UserGroup userGroup,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "updateExternalReferenceCode",
				_updateExternalReferenceCodeParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroup, externalReferenceCode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.UserGroup)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.UserGroup updateUserGroup(
			HttpPrincipal httpPrincipal, long userGroupId, String name,
			String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserGroupServiceUtil.class, "updateUserGroup",
				_updateUserGroupParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroupId, name, description, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
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

			return (com.liferay.portal.kernel.model.UserGroup)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UserGroupServiceHttp.class);

	private static final Class<?>[] _addGroupUserGroupsParameterTypes0 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _addOrUpdateUserGroupParameterTypes1 =
		new Class[] {
			String.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addTeamUserGroupsParameterTypes2 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _addUserGroupParameterTypes3 = new Class[] {
		String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteUserGroupParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchUserGroupParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[]
		_fetchUserGroupByExternalReferenceCodeParameterTypes6 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getGtUserGroupsParameterTypes7 =
		new Class[] {long.class, long.class, long.class, int.class};
	private static final Class<?>[] _getUserGroupParameterTypes8 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getUserGroupParameterTypes9 = new Class[] {
		String.class
	};
	private static final Class<?>[]
		_getUserGroupByExternalReferenceCodeParameterTypes10 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _getUserGroupsParameterTypes11 =
		new Class[] {long.class};
	private static final Class<?>[] _getUserGroupsParameterTypes12 =
		new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[] _getUserGroupsCountParameterTypes13 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getUserUserGroupsParameterTypes14 =
		new Class[] {long.class};
	private static final Class<?>[] _searchParameterTypes15 = new Class[] {
		long.class, String.class, java.util.LinkedHashMap.class, int.class,
		int.class, com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes16 = new Class[] {
		long.class, String.class, String.class, java.util.LinkedHashMap.class,
		boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchCountParameterTypes17 = new Class[] {
		long.class, String.class, java.util.LinkedHashMap.class
	};
	private static final Class<?>[] _searchCountParameterTypes18 = new Class[] {
		long.class, String.class, String.class, java.util.LinkedHashMap.class,
		boolean.class
	};
	private static final Class<?>[] _unsetGroupUserGroupsParameterTypes19 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _unsetTeamUserGroupsParameterTypes20 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[]
		_updateExternalReferenceCodeParameterTypes21 = new Class[] {
			com.liferay.portal.kernel.model.UserGroup.class, String.class
		};
	private static final Class<?>[] _updateUserGroupParameterTypes22 =
		new Class[] {
			long.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}