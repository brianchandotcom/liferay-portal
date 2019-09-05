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
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>GroupServiceUtil</code> service
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
 * @see GroupServiceSoap
 * @generated
 */
public class GroupServiceHttp {

	public static com.liferay.portal.kernel.model.Group addGroup(
			HttpPrincipal httpPrincipal, long parentGroupId, long liveGroupId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean site, boolean inheritContent,
			boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "addGroup", _addGroupParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, parentGroupId, liveGroupId, nameMap, descriptionMap,
				type, manualMembership, membershipRestriction, friendlyURL,
				site, inheritContent, active, serviceContext);

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

			return (com.liferay.portal.kernel.model.Group)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.Group addGroup(
			HttpPrincipal httpPrincipal, long parentGroupId, long liveGroupId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean site, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "addGroup", _addGroupParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, parentGroupId, liveGroupId, nameMap, descriptionMap,
				type, manualMembership, membershipRestriction, friendlyURL,
				site, active, serviceContext);

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

			return (com.liferay.portal.kernel.model.Group)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addRoleGroups(
			HttpPrincipal httpPrincipal, long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "addRoleGroups",
				_addRoleGroupsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, roleId, groupIds);

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

	public static void checkRemoteStagingGroup(
			HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "checkRemoteStagingGroup",
				_checkRemoteStagingGroupParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static void deleteGroup(HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "deleteGroup",
				_deleteGroupParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static void disableStaging(HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "disableStaging",
				_disableStagingParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static void enableStaging(HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "enableStaging",
				_enableStagingParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static com.liferay.portal.kernel.model.Group getCompanyGroup(
			HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getCompanyGroup",
				_getCompanyGroupParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

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

			return (com.liferay.portal.kernel.model.Group)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.Group getGroup(
			HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getGroup", _getGroupParameterTypes8);

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

			return (com.liferay.portal.kernel.model.Group)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.Group getGroup(
			HttpPrincipal httpPrincipal, long companyId, String groupKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getGroup", _getGroupParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupKey);

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

			return (com.liferay.portal.kernel.model.Group)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static String getGroupDisplayURL(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			boolean secureConnection)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getGroupDisplayURL",
				_getGroupDisplayURLParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, secureConnection);

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

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getGroups(
				HttpPrincipal httpPrincipal, long companyId, long parentGroupId,
				boolean site)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getGroups",
				_getGroupsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, parentGroupId, site);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getGroups(
				HttpPrincipal httpPrincipal, long companyId, long parentGroupId,
				boolean site, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getGroups",
				_getGroupsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, parentGroupId, site, start, end);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getGroups(
				HttpPrincipal httpPrincipal, long companyId, long parentGroupId,
				String name, boolean site, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getGroups",
				_getGroupsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, parentGroupId, name, site, start, end);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupsCount(
			HttpPrincipal httpPrincipal, long companyId, long parentGroupId,
			boolean site)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getGroupsCount",
				_getGroupsCountParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, parentGroupId, site);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupsCount(
			HttpPrincipal httpPrincipal, long companyId, long parentGroupId,
			String name, boolean site)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getGroupsCount",
				_getGroupsCountParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, parentGroupId, name, site);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupsCount(
			HttpPrincipal httpPrincipal, long companyId, String className,
			long parentGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getGroupsCount",
				_getGroupsCountParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, className, parentGroupId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getGtGroups(
				HttpPrincipal httpPrincipal, long gtGroupId, long companyId,
				long parentGroupId, boolean site, int size)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getGtGroups",
				_getGtGroupsParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, gtGroupId, companyId, parentGroupId, site, size);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getManageableSiteGroups(
				HttpPrincipal httpPrincipal,
				java.util.Collection<com.liferay.portal.kernel.model.Portlet>
					portlets,
				int max)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getManageableSiteGroups",
				_getManageableSiteGroupsParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, portlets, max);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getOrganizationsGroups(
				HttpPrincipal httpPrincipal,
				java.util.List<com.liferay.portal.kernel.model.Organization>
					organizations)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getOrganizationsGroups",
				_getOrganizationsGroupsParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizations);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.Group getUserGroup(
			HttpPrincipal httpPrincipal, long companyId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getUserGroup",
				_getUserGroupParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, userId);

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

			return (com.liferay.portal.kernel.model.Group)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getUserGroupsGroups(
				HttpPrincipal httpPrincipal,
				java.util.List<com.liferay.portal.kernel.model.UserGroup>
					userGroups)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getUserGroupsGroups",
				_getUserGroupsGroupsParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroups);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getUserOrganizationsGroups(
				HttpPrincipal httpPrincipal, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getUserOrganizationsGroups",
				_getUserOrganizationsGroupsParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, start, end);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getUserSitesGroups(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getUserSitesGroups",
				_getUserSitesGroupsParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(methodKey);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getUserSitesGroups(
				HttpPrincipal httpPrincipal, long userId, String[] classNames,
				int max)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getUserSitesGroups",
				_getUserSitesGroupsParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, classNames, max);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group>
			getUserSitesGroups(
				HttpPrincipal httpPrincipal, String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getUserSitesGroups",
				_getUserSitesGroupsParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNames, max);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getUserSitesGroupsCount(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "getUserSitesGroupsCount",
				_getUserSitesGroupsCountParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(methodKey);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasUserGroup(
			HttpPrincipal httpPrincipal, long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "hasUserGroup",
				_hasUserGroupParameterTypes27);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> search(
			HttpPrincipal httpPrincipal, long companyId, long[] classNameIds,
			String keywords, java.util.LinkedHashMap<String, Object> params,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Group> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "search", _searchParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, classNameIds, keywords, params, start,
				end, obc);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> search(
			HttpPrincipal httpPrincipal, long companyId, long[] classNameIds,
			String name, String description,
			java.util.LinkedHashMap<String, Object> params, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Group> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "search", _searchParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, classNameIds, name, description, params,
				andOperator, start, end, obc);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.Group> search(
			HttpPrincipal httpPrincipal, long companyId, String name,
			String description, String[] params, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "search", _searchParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, name, description, params, start, end);

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

			return (java.util.List<com.liferay.portal.kernel.model.Group>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, String name,
		String description, String[] params) {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "searchCount",
				_searchCountParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, name, description, params);

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

	public static void setRoleGroups(
			HttpPrincipal httpPrincipal, long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "setRoleGroups",
				_setRoleGroupsParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, roleId, groupIds);

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

	public static void unsetRoleGroups(
			HttpPrincipal httpPrincipal, long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "unsetRoleGroups",
				_unsetRoleGroupsParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, roleId, groupIds);

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

	public static com.liferay.portal.kernel.model.Group updateFriendlyURL(
			HttpPrincipal httpPrincipal, long groupId, String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "updateFriendlyURL",
				_updateFriendlyURLParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, friendlyURL);

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

			return (com.liferay.portal.kernel.model.Group)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.Group updateGroup(
			HttpPrincipal httpPrincipal, long groupId, long parentGroupId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean inheritContent, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "updateGroup",
				_updateGroupParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentGroupId, nameMap, descriptionMap,
				type, manualMembership, membershipRestriction, friendlyURL,
				inheritContent, active, serviceContext);

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

			return (com.liferay.portal.kernel.model.Group)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.Group updateGroup(
			HttpPrincipal httpPrincipal, long groupId, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "updateGroup",
				_updateGroupParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, typeSettings);

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

			return (com.liferay.portal.kernel.model.Group)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateStagedPortlets(
			HttpPrincipal httpPrincipal, long groupId,
			java.util.Map<String, String> stagedPortletIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				GroupServiceUtil.class, "updateStagedPortlets",
				_updateStagedPortletsParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, stagedPortletIds);

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

	private static Log _log = LogFactoryUtil.getLog(GroupServiceHttp.class);

	private static final Class<?>[] _addGroupParameterTypes0 = new Class[] {
		long.class, long.class, java.util.Map.class, java.util.Map.class,
		int.class, boolean.class, int.class, String.class, boolean.class,
		boolean.class, boolean.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addGroupParameterTypes1 = new Class[] {
		long.class, long.class, java.util.Map.class, java.util.Map.class,
		int.class, boolean.class, int.class, String.class, boolean.class,
		boolean.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addRoleGroupsParameterTypes2 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _checkRemoteStagingGroupParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteGroupParameterTypes4 = new Class[] {
		long.class
	};
	private static final Class<?>[] _disableStagingParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[] _enableStagingParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[] _getCompanyGroupParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _getGroupParameterTypes8 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getGroupParameterTypes9 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _getGroupDisplayURLParameterTypes10 =
		new Class[] {long.class, boolean.class, boolean.class};
	private static final Class<?>[] _getGroupsParameterTypes11 = new Class[] {
		long.class, long.class, boolean.class
	};
	private static final Class<?>[] _getGroupsParameterTypes12 = new Class[] {
		long.class, long.class, boolean.class, int.class, int.class
	};
	private static final Class<?>[] _getGroupsParameterTypes13 = new Class[] {
		long.class, long.class, String.class, boolean.class, int.class,
		int.class
	};
	private static final Class<?>[] _getGroupsCountParameterTypes14 =
		new Class[] {long.class, long.class, boolean.class};
	private static final Class<?>[] _getGroupsCountParameterTypes15 =
		new Class[] {long.class, long.class, String.class, boolean.class};
	private static final Class<?>[] _getGroupsCountParameterTypes16 =
		new Class[] {long.class, String.class, long.class};
	private static final Class<?>[] _getGtGroupsParameterTypes17 = new Class[] {
		long.class, long.class, long.class, boolean.class, int.class
	};
	private static final Class<?>[] _getManageableSiteGroupsParameterTypes18 =
		new Class[] {java.util.Collection.class, int.class};
	private static final Class<?>[] _getOrganizationsGroupsParameterTypes19 =
		new Class[] {java.util.List.class};
	private static final Class<?>[] _getUserGroupParameterTypes20 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getUserGroupsGroupsParameterTypes21 =
		new Class[] {java.util.List.class};
	private static final Class<?>[]
		_getUserOrganizationsGroupsParameterTypes22 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getUserSitesGroupsParameterTypes23 =
		new Class[] {};
	private static final Class<?>[] _getUserSitesGroupsParameterTypes24 =
		new Class[] {long.class, String[].class, int.class};
	private static final Class<?>[] _getUserSitesGroupsParameterTypes25 =
		new Class[] {String[].class, int.class};
	private static final Class<?>[] _getUserSitesGroupsCountParameterTypes26 =
		new Class[] {};
	private static final Class<?>[] _hasUserGroupParameterTypes27 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _searchParameterTypes28 = new Class[] {
		long.class, long[].class, String.class, java.util.LinkedHashMap.class,
		int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes29 = new Class[] {
		long.class, long[].class, String.class, String.class,
		java.util.LinkedHashMap.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes30 = new Class[] {
		long.class, String.class, String.class, String[].class, int.class,
		int.class
	};
	private static final Class<?>[] _searchCountParameterTypes31 = new Class[] {
		long.class, String.class, String.class, String[].class
	};
	private static final Class<?>[] _setRoleGroupsParameterTypes32 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _unsetRoleGroupsParameterTypes33 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _updateFriendlyURLParameterTypes34 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateGroupParameterTypes35 = new Class[] {
		long.class, long.class, java.util.Map.class, java.util.Map.class,
		int.class, boolean.class, int.class, String.class, boolean.class,
		boolean.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _updateGroupParameterTypes36 = new Class[] {
		long.class, String.class
	};
	private static final Class<?>[] _updateStagedPortletsParameterTypes37 =
		new Class[] {long.class, java.util.Map.class};

}