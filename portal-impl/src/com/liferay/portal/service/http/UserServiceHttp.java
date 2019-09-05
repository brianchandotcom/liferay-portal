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
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>UserServiceUtil</code> service
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
 * @see UserServiceSoap
 * @generated
 */
public class UserServiceHttp {

	public static void addGroupUsers(
			HttpPrincipal httpPrincipal, long groupId, long[] userIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "addGroupUsers",
				_addGroupUsersParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userIds, serviceContext);

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

	public static void addOrganizationUsers(
			HttpPrincipal httpPrincipal, long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "addOrganizationUsers",
				_addOrganizationUsersParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId, userIds);

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

	public static void addPasswordPolicyUsers(
			HttpPrincipal httpPrincipal, long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "addPasswordPolicyUsers",
				_addPasswordPolicyUsersParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, passwordPolicyId, userIds);

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

	public static void addRoleUsers(
			HttpPrincipal httpPrincipal, long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "addRoleUsers",
				_addRoleUsersParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, roleId, userIds);

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

	public static void addTeamUsers(
			HttpPrincipal httpPrincipal, long teamId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "addTeamUsers",
				_addTeamUsersParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamId, userIds);

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

	public static com.liferay.portal.kernel.model.User addUser(
			HttpPrincipal httpPrincipal, long companyId, boolean autoPassword,
			String password1, String password2, boolean autoScreenName,
			String screenName, String emailAddress, long facebookId,
			String openId, java.util.Locale locale, String firstName,
			String middleName, String lastName, long prefixId, long suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, long[] groupIds, long[] organizationIds,
			long[] roleIds, long[] userGroupIds, boolean sendEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "addUser", _addUserParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, facebookId, openId,
				locale, firstName, middleName, lastName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User addUser(
			HttpPrincipal httpPrincipal, long companyId, boolean autoPassword,
			String password1, String password2, boolean autoScreenName,
			String screenName, String emailAddress, long facebookId,
			String openId, java.util.Locale locale, String firstName,
			String middleName, String lastName, long prefixId, long suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, long[] groupIds, long[] organizationIds,
			long[] roleIds, long[] userGroupIds,
			java.util.List<com.liferay.portal.kernel.model.Address> addresses,
			java.util.List<com.liferay.portal.kernel.model.EmailAddress>
				emailAddresses,
			java.util.List<com.liferay.portal.kernel.model.Phone> phones,
			java.util.List<com.liferay.portal.kernel.model.Website> websites,
			java.util.List
				<com.liferay.announcements.kernel.model.AnnouncementsDelivery>
					announcementsDelivers,
			boolean sendEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "addUser", _addUserParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, facebookId, openId,
				locale, firstName, middleName, lastName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				groupIds, organizationIds, roleIds, userGroupIds, addresses,
				emailAddresses, phones, websites, announcementsDelivers,
				sendEmail, serviceContext);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addUserGroupUsers(
			HttpPrincipal httpPrincipal, long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "addUserGroupUsers",
				_addUserGroupUsersParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroupId, userIds);

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

	public static com.liferay.portal.kernel.model.User addUserWithWorkflow(
			HttpPrincipal httpPrincipal, long companyId, boolean autoPassword,
			String password1, String password2, boolean autoScreenName,
			String screenName, String emailAddress, long facebookId,
			String openId, java.util.Locale locale, String firstName,
			String middleName, String lastName, long prefixId, long suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, long[] groupIds, long[] organizationIds,
			long[] roleIds, long[] userGroupIds, boolean sendEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "addUserWithWorkflow",
				_addUserWithWorkflowParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, facebookId, openId,
				locale, firstName, middleName, lastName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User addUserWithWorkflow(
			HttpPrincipal httpPrincipal, long companyId, boolean autoPassword,
			String password1, String password2, boolean autoScreenName,
			String screenName, String emailAddress, long facebookId,
			String openId, java.util.Locale locale, String firstName,
			String middleName, String lastName, long prefixId, long suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, long[] groupIds, long[] organizationIds,
			long[] roleIds, long[] userGroupIds,
			java.util.List<com.liferay.portal.kernel.model.Address> addresses,
			java.util.List<com.liferay.portal.kernel.model.EmailAddress>
				emailAddresses,
			java.util.List<com.liferay.portal.kernel.model.Phone> phones,
			java.util.List<com.liferay.portal.kernel.model.Website> websites,
			java.util.List
				<com.liferay.announcements.kernel.model.AnnouncementsDelivery>
					announcementsDelivers,
			boolean sendEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "addUserWithWorkflow",
				_addUserWithWorkflowParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, facebookId, openId,
				locale, firstName, middleName, lastName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				groupIds, organizationIds, roleIds, userGroupIds, addresses,
				emailAddresses, phones, websites, announcementsDelivers,
				sendEmail, serviceContext);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deletePortrait(HttpPrincipal httpPrincipal, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "deletePortrait",
				_deletePortraitParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

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

	public static void deleteRoleUser(
			HttpPrincipal httpPrincipal, long roleId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "deleteRoleUser",
				_deleteRoleUserParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, roleId, userId);

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

	public static void deleteUser(HttpPrincipal httpPrincipal, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "deleteUser",
				_deleteUserParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

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

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getCompanyUsers(
				HttpPrincipal httpPrincipal, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getCompanyUsers",
				_getCompanyUsersParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end);

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

			return (java.util.List<com.liferay.portal.kernel.model.User>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCompanyUsersCount(
			HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getCompanyUsersCount",
				_getCompanyUsersCountParameterTypes14);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User getCurrentUser(
			HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getCurrentUser",
				_getCurrentUserParameterTypes15);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long[] getGroupUserIds(
			HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getGroupUserIds",
				_getGroupUserIdsParameterTypes16);

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

			return (long[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getGroupUsers(HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getGroupUsers",
				_getGroupUsersParameterTypes17);

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

			return (java.util.List<com.liferay.portal.kernel.model.User>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getGroupUsers(
				HttpPrincipal httpPrincipal, long groupId, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.User> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getGroupUsers",
				_getGroupUsersParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status, start, end, obc);

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

			return (java.util.List<com.liferay.portal.kernel.model.User>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getGroupUsers(
				HttpPrincipal httpPrincipal, long groupId, int status,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.User> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getGroupUsers",
				_getGroupUsersParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status, obc);

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

			return (java.util.List<com.liferay.portal.kernel.model.User>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupUsersCount(
			HttpPrincipal httpPrincipal, long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getGroupUsersCount",
				_getGroupUsersCountParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, status);

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

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getGtCompanyUsers(
				HttpPrincipal httpPrincipal, long gtUserId, long companyId,
				int size)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getGtCompanyUsers",
				_getGtCompanyUsersParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, gtUserId, companyId, size);

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

			return (java.util.List<com.liferay.portal.kernel.model.User>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getGtOrganizationUsers(
				HttpPrincipal httpPrincipal, long gtUserId, long organizationId,
				int size)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getGtOrganizationUsers",
				_getGtOrganizationUsersParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, gtUserId, organizationId, size);

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

			return (java.util.List<com.liferay.portal.kernel.model.User>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getGtUserGroupUsers(
				HttpPrincipal httpPrincipal, long gtUserId, long userGroupId,
				int size)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getGtUserGroupUsers",
				_getGtUserGroupUsersParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, gtUserId, userGroupId, size);

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

			return (java.util.List<com.liferay.portal.kernel.model.User>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getOrganizationsAndUserGroupsUsersCount(
			HttpPrincipal httpPrincipal, long[] organizationIds,
			long[] userGroupIds)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class,
				"getOrganizationsAndUserGroupsUsersCount",
				_getOrganizationsAndUserGroupsUsersCountParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationIds, userGroupIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.security.auth.
							PrincipalException) {

					throw (com.liferay.portal.kernel.security.auth.
						PrincipalException)e;
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

	public static long[] getOrganizationUserIds(
			HttpPrincipal httpPrincipal, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getOrganizationUserIds",
				_getOrganizationUserIdsParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId);

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

			return (long[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getOrganizationUsers(
				HttpPrincipal httpPrincipal, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getOrganizationUsers",
				_getOrganizationUsersParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId);

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

			return (java.util.List<com.liferay.portal.kernel.model.User>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getOrganizationUsers(
				HttpPrincipal httpPrincipal, long organizationId, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.User> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getOrganizationUsers",
				_getOrganizationUsersParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId, status, start, end, obc);

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

			return (java.util.List<com.liferay.portal.kernel.model.User>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getOrganizationUsers(
				HttpPrincipal httpPrincipal, long organizationId, int status,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.User> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getOrganizationUsers",
				_getOrganizationUsersParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId, status, obc);

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

			return (java.util.List<com.liferay.portal.kernel.model.User>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getOrganizationUsersCount(
			HttpPrincipal httpPrincipal, long organizationId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getOrganizationUsersCount",
				_getOrganizationUsersCountParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId, status);

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

	public static long[] getRoleUserIds(
			HttpPrincipal httpPrincipal, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getRoleUserIds",
				_getRoleUserIdsParameterTypes30);

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

			return (long[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User getUserByEmailAddress(
			HttpPrincipal httpPrincipal, long companyId, String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getUserByEmailAddress",
				_getUserByEmailAddressParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, emailAddress);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User getUserById(
			HttpPrincipal httpPrincipal, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getUserById",
				_getUserByIdParameterTypes32);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User getUserByScreenName(
			HttpPrincipal httpPrincipal, long companyId, String screenName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getUserByScreenName",
				_getUserByScreenNameParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, screenName);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getUserGroupUsers(HttpPrincipal httpPrincipal, long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getUserGroupUsers",
				_getUserGroupUsersParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroupId);

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

			return (java.util.List<com.liferay.portal.kernel.model.User>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getUserGroupUsers(
				HttpPrincipal httpPrincipal, long userGroupId, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getUserGroupUsers",
				_getUserGroupUsersParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroupId, start, end);

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

			return (java.util.List<com.liferay.portal.kernel.model.User>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long getUserIdByEmailAddress(
			HttpPrincipal httpPrincipal, long companyId, String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getUserIdByEmailAddress",
				_getUserIdByEmailAddressParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, emailAddress);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long getUserIdByScreenName(
			HttpPrincipal httpPrincipal, long companyId, String screenName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "getUserIdByScreenName",
				_getUserIdByScreenNameParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, screenName);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasGroupUser(
			HttpPrincipal httpPrincipal, long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "hasGroupUser",
				_hasGroupUserParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userId);

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

	public static boolean hasRoleUser(
			HttpPrincipal httpPrincipal, long roleId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "hasRoleUser",
				_hasRoleUserParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, roleId, userId);

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

	public static boolean hasRoleUser(
			HttpPrincipal httpPrincipal, long companyId, String name,
			long userId, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "hasRoleUser",
				_hasRoleUserParameterTypes40);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, name, userId, inherited);

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

	public static boolean sendPasswordByEmailAddress(
			HttpPrincipal httpPrincipal, long companyId, String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "sendPasswordByEmailAddress",
				_sendPasswordByEmailAddressParameterTypes41);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, emailAddress);

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

	public static boolean sendPasswordByScreenName(
			HttpPrincipal httpPrincipal, long companyId, String screenName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "sendPasswordByScreenName",
				_sendPasswordByScreenNameParameterTypes42);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, screenName);

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

	public static boolean sendPasswordByUserId(
			HttpPrincipal httpPrincipal, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "sendPasswordByUserId",
				_sendPasswordByUserIdParameterTypes43);

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void setRoleUsers(
			HttpPrincipal httpPrincipal, long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "setRoleUsers",
				_setRoleUsersParameterTypes44);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, roleId, userIds);

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

	public static void setUserGroupUsers(
			HttpPrincipal httpPrincipal, long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "setUserGroupUsers",
				_setUserGroupUsersParameterTypes45);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroupId, userIds);

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

	public static void unsetGroupTeamsUsers(
			HttpPrincipal httpPrincipal, long groupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "unsetGroupTeamsUsers",
				_unsetGroupTeamsUsersParameterTypes46);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userIds);

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

	public static void unsetGroupUsers(
			HttpPrincipal httpPrincipal, long groupId, long[] userIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "unsetGroupUsers",
				_unsetGroupUsersParameterTypes47);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, userIds, serviceContext);

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

	public static void unsetOrganizationUsers(
			HttpPrincipal httpPrincipal, long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "unsetOrganizationUsers",
				_unsetOrganizationUsersParameterTypes48);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, organizationId, userIds);

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

	public static void unsetPasswordPolicyUsers(
			HttpPrincipal httpPrincipal, long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "unsetPasswordPolicyUsers",
				_unsetPasswordPolicyUsersParameterTypes49);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, passwordPolicyId, userIds);

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

	public static void unsetRoleUsers(
			HttpPrincipal httpPrincipal, long roleId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "unsetRoleUsers",
				_unsetRoleUsersParameterTypes50);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, roleId, userIds);

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

	public static void unsetTeamUsers(
			HttpPrincipal httpPrincipal, long teamId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "unsetTeamUsers",
				_unsetTeamUsersParameterTypes51);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamId, userIds);

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

	public static void unsetUserGroupUsers(
			HttpPrincipal httpPrincipal, long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "unsetUserGroupUsers",
				_unsetUserGroupUsersParameterTypes52);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userGroupId, userIds);

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

	public static com.liferay.portal.kernel.model.User updateAgreedToTermsOfUse(
			HttpPrincipal httpPrincipal, long userId,
			boolean agreedToTermsOfUse)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "updateAgreedToTermsOfUse",
				_updateAgreedToTermsOfUseParameterTypes53);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, agreedToTermsOfUse);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User updateEmailAddress(
			HttpPrincipal httpPrincipal, long userId, String password,
			String emailAddress1, String emailAddress2,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "updateEmailAddress",
				_updateEmailAddressParameterTypes54);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, password, emailAddress1, emailAddress2,
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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User updateIncompleteUser(
			HttpPrincipal httpPrincipal, long companyId, boolean autoPassword,
			String password1, String password2, boolean autoScreenName,
			String screenName, String emailAddress, long facebookId,
			String openId, java.util.Locale locale, String firstName,
			String middleName, String lastName, long prefixId, long suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, boolean updateUserInformation, boolean sendEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "updateIncompleteUser",
				_updateIncompleteUserParameterTypes55);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, facebookId, openId,
				locale, firstName, middleName, lastName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				updateUserInformation, sendEmail, serviceContext);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User updateLockoutById(
			HttpPrincipal httpPrincipal, long userId, boolean lockout)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "updateLockoutById",
				_updateLockoutByIdParameterTypes56);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, lockout);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User updateOpenId(
			HttpPrincipal httpPrincipal, long userId, String openId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "updateOpenId",
				_updateOpenIdParameterTypes57);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, openId);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateOrganizations(
			HttpPrincipal httpPrincipal, long userId, long[] organizationIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "updateOrganizations",
				_updateOrganizationsParameterTypes58);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, organizationIds, serviceContext);

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

	public static com.liferay.portal.kernel.model.User updatePassword(
			HttpPrincipal httpPrincipal, long userId, String password1,
			String password2, boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "updatePassword",
				_updatePasswordParameterTypes59);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, password1, password2, passwordReset);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User updatePortrait(
			HttpPrincipal httpPrincipal, long userId, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "updatePortrait",
				_updatePortraitParameterTypes60);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, bytes);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User updateReminderQuery(
			HttpPrincipal httpPrincipal, long userId, String question,
			String answer)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "updateReminderQuery",
				_updateReminderQueryParameterTypes61);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, question, answer);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User updateScreenName(
			HttpPrincipal httpPrincipal, long userId, String screenName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "updateScreenName",
				_updateScreenNameParameterTypes62);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, screenName);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User updateStatus(
			HttpPrincipal httpPrincipal, long userId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "updateStatus",
				_updateStatusParameterTypes63);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, status, serviceContext);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User updateUser(
			HttpPrincipal httpPrincipal, long userId, String oldPassword,
			String newPassword1, String newPassword2, boolean passwordReset,
			String reminderQueryQuestion, String reminderQueryAnswer,
			String screenName, String emailAddress, long facebookId,
			String openId, boolean hasPortrait, byte[] portraitBytes,
			String languageId, String timeZoneId, String greeting,
			String comments, String firstName, String middleName,
			String lastName, long prefixId, long suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn,
			String facebookSn, String jabberSn, String skypeSn,
			String twitterSn, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds,
			java.util.List<com.liferay.portal.kernel.model.UserGroupRole>
				userGroupRoles,
			long[] userGroupIds,
			java.util.List<com.liferay.portal.kernel.model.Address> addresses,
			java.util.List<com.liferay.portal.kernel.model.EmailAddress>
				emailAddresses,
			java.util.List<com.liferay.portal.kernel.model.Phone> phones,
			java.util.List<com.liferay.portal.kernel.model.Website> websites,
			java.util.List
				<com.liferay.announcements.kernel.model.AnnouncementsDelivery>
					announcementsDelivers,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "updateUser",
				_updateUserParameterTypes64);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, oldPassword, newPassword1, newPassword2,
				passwordReset, reminderQueryQuestion, reminderQueryAnswer,
				screenName, emailAddress, facebookId, openId, hasPortrait,
				portraitBytes, languageId, timeZoneId, greeting, comments,
				firstName, middleName, lastName, prefixId, suffixId, male,
				birthdayMonth, birthdayDay, birthdayYear, smsSn, facebookSn,
				jabberSn, skypeSn, twitterSn, jobTitle, groupIds,
				organizationIds, roleIds, userGroupRoles, userGroupIds,
				addresses, emailAddresses, phones, websites,
				announcementsDelivers, serviceContext);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User updateUser(
			HttpPrincipal httpPrincipal, long userId, String oldPassword,
			String newPassword1, String newPassword2, boolean passwordReset,
			String reminderQueryQuestion, String reminderQueryAnswer,
			String screenName, String emailAddress, long facebookId,
			String openId, String languageId, String timeZoneId,
			String greeting, String comments, String firstName,
			String middleName, String lastName, long prefixId, long suffixId,
			boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
			String smsSn, String facebookSn, String jabberSn, String skypeSn,
			String twitterSn, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds,
			java.util.List<com.liferay.portal.kernel.model.UserGroupRole>
				userGroupRoles,
			long[] userGroupIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				UserServiceUtil.class, "updateUser",
				_updateUserParameterTypes65);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, oldPassword, newPassword1, newPassword2,
				passwordReset, reminderQueryQuestion, reminderQueryAnswer,
				screenName, emailAddress, facebookId, openId, languageId,
				timeZoneId, greeting, comments, firstName, middleName, lastName,
				prefixId, suffixId, male, birthdayMonth, birthdayDay,
				birthdayYear, smsSn, facebookSn, jabberSn, skypeSn, twitterSn,
				jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
				userGroupIds, serviceContext);

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

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UserServiceHttp.class);

	private static final Class<?>[] _addGroupUsersParameterTypes0 =
		new Class[] {
			long.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addOrganizationUsersParameterTypes1 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _addPasswordPolicyUsersParameterTypes2 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _addRoleUsersParameterTypes3 = new Class[] {
		long.class, long[].class
	};
	private static final Class<?>[] _addTeamUsersParameterTypes4 = new Class[] {
		long.class, long[].class
	};
	private static final Class<?>[] _addUserParameterTypes5 = new Class[] {
		long.class, boolean.class, String.class, String.class, boolean.class,
		String.class, String.class, long.class, String.class,
		java.util.Locale.class, String.class, String.class, String.class,
		long.class, long.class, boolean.class, int.class, int.class, int.class,
		String.class, long[].class, long[].class, long[].class, long[].class,
		boolean.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addUserParameterTypes6 = new Class[] {
		long.class, boolean.class, String.class, String.class, boolean.class,
		String.class, String.class, long.class, String.class,
		java.util.Locale.class, String.class, String.class, String.class,
		long.class, long.class, boolean.class, int.class, int.class, int.class,
		String.class, long[].class, long[].class, long[].class, long[].class,
		java.util.List.class, java.util.List.class, java.util.List.class,
		java.util.List.class, java.util.List.class, boolean.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addUserGroupUsersParameterTypes7 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _addUserWithWorkflowParameterTypes8 =
		new Class[] {
			long.class, boolean.class, String.class, String.class,
			boolean.class, String.class, String.class, long.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, boolean.class, int.class, int.class,
			int.class, String.class, long[].class, long[].class, long[].class,
			long[].class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addUserWithWorkflowParameterTypes9 =
		new Class[] {
			long.class, boolean.class, String.class, String.class,
			boolean.class, String.class, String.class, long.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, boolean.class, int.class, int.class,
			int.class, String.class, long[].class, long[].class, long[].class,
			long[].class, java.util.List.class, java.util.List.class,
			java.util.List.class, java.util.List.class, java.util.List.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deletePortraitParameterTypes10 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteRoleUserParameterTypes11 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _deleteUserParameterTypes12 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getCompanyUsersParameterTypes13 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getCompanyUsersCountParameterTypes14 =
		new Class[] {long.class};
	private static final Class<?>[] _getCurrentUserParameterTypes15 =
		new Class[] {};
	private static final Class<?>[] _getGroupUserIdsParameterTypes16 =
		new Class[] {long.class};
	private static final Class<?>[] _getGroupUsersParameterTypes17 =
		new Class[] {long.class};
	private static final Class<?>[] _getGroupUsersParameterTypes18 =
		new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupUsersParameterTypes19 =
		new Class[] {
			long.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupUsersCountParameterTypes20 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getGtCompanyUsersParameterTypes21 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[] _getGtOrganizationUsersParameterTypes22 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[] _getGtUserGroupUsersParameterTypes23 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[]
		_getOrganizationsAndUserGroupsUsersCountParameterTypes24 = new Class[] {
			long[].class, long[].class
		};
	private static final Class<?>[] _getOrganizationUserIdsParameterTypes25 =
		new Class[] {long.class};
	private static final Class<?>[] _getOrganizationUsersParameterTypes26 =
		new Class[] {long.class};
	private static final Class<?>[] _getOrganizationUsersParameterTypes27 =
		new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getOrganizationUsersParameterTypes28 =
		new Class[] {
			long.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getOrganizationUsersCountParameterTypes29 =
		new Class[] {long.class, int.class};
	private static final Class<?>[] _getRoleUserIdsParameterTypes30 =
		new Class[] {long.class};
	private static final Class<?>[] _getUserByEmailAddressParameterTypes31 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getUserByIdParameterTypes32 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getUserByScreenNameParameterTypes33 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getUserGroupUsersParameterTypes34 =
		new Class[] {long.class};
	private static final Class<?>[] _getUserGroupUsersParameterTypes35 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getUserIdByEmailAddressParameterTypes36 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getUserIdByScreenNameParameterTypes37 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _hasGroupUserParameterTypes38 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _hasRoleUserParameterTypes39 = new Class[] {
		long.class, long.class
	};
	private static final Class<?>[] _hasRoleUserParameterTypes40 = new Class[] {
		long.class, String.class, long.class, boolean.class
	};
	private static final Class<?>[]
		_sendPasswordByEmailAddressParameterTypes41 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _sendPasswordByScreenNameParameterTypes42 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _sendPasswordByUserIdParameterTypes43 =
		new Class[] {long.class};
	private static final Class<?>[] _setRoleUsersParameterTypes44 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _setUserGroupUsersParameterTypes45 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _unsetGroupTeamsUsersParameterTypes46 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _unsetGroupUsersParameterTypes47 =
		new Class[] {
			long.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _unsetOrganizationUsersParameterTypes48 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _unsetPasswordPolicyUsersParameterTypes49 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _unsetRoleUsersParameterTypes50 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _unsetTeamUsersParameterTypes51 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _unsetUserGroupUsersParameterTypes52 =
		new Class[] {long.class, long[].class};
	private static final Class<?>[] _updateAgreedToTermsOfUseParameterTypes53 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _updateEmailAddressParameterTypes54 =
		new Class[] {
			long.class, String.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateIncompleteUserParameterTypes55 =
		new Class[] {
			long.class, boolean.class, String.class, String.class,
			boolean.class, String.class, String.class, long.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, boolean.class, int.class, int.class,
			int.class, String.class, boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateLockoutByIdParameterTypes56 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[] _updateOpenIdParameterTypes57 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateOrganizationsParameterTypes58 =
		new Class[] {
			long.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updatePasswordParameterTypes59 =
		new Class[] {long.class, String.class, String.class, boolean.class};
	private static final Class<?>[] _updatePortraitParameterTypes60 =
		new Class[] {long.class, byte[].class};
	private static final Class<?>[] _updateReminderQueryParameterTypes61 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[] _updateScreenNameParameterTypes62 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateStatusParameterTypes63 =
		new Class[] {
			long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateUserParameterTypes64 = new Class[] {
		long.class, String.class, String.class, String.class, boolean.class,
		String.class, String.class, String.class, String.class, long.class,
		String.class, boolean.class, byte[].class, String.class, String.class,
		String.class, String.class, String.class, String.class, String.class,
		long.class, long.class, boolean.class, int.class, int.class, int.class,
		String.class, String.class, String.class, String.class, String.class,
		String.class, long[].class, long[].class, long[].class,
		java.util.List.class, long[].class, java.util.List.class,
		java.util.List.class, java.util.List.class, java.util.List.class,
		java.util.List.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _updateUserParameterTypes65 = new Class[] {
		long.class, String.class, String.class, String.class, boolean.class,
		String.class, String.class, String.class, String.class, long.class,
		String.class, String.class, String.class, String.class, String.class,
		String.class, String.class, String.class, long.class, long.class,
		boolean.class, int.class, int.class, int.class, String.class,
		String.class, String.class, String.class, String.class, String.class,
		long[].class, long[].class, long[].class, java.util.List.class,
		long[].class, com.liferay.portal.kernel.service.ServiceContext.class
	};

}