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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link UserServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
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
 * @see HttpPrincipal
 * @see UserServiceUtil
 * @generated
 */
@ProviderType
public class UserServiceHttp {
	public static void addGroupUsers(HttpPrincipal httpPrincipal, long groupId,
		long[] userIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addGroupUsers", _addGroupUsersParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userIds, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addOrganizationUsers(HttpPrincipal httpPrincipal,
		long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addOrganizationUsers", _addOrganizationUsersParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					organizationId, userIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addPasswordPolicyUsers(HttpPrincipal httpPrincipal,
		long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addPasswordPolicyUsers",
					_addPasswordPolicyUsersParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					passwordPolicyId, userIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addRoleUsers(HttpPrincipal httpPrincipal, long roleId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addRoleUsers", _addRoleUsersParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId,
					userIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addTeamUsers(HttpPrincipal httpPrincipal, long teamId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addTeamUsers", _addTeamUsersParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, teamId,
					userIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		java.util.Locale locale, String firstName, String middleName,
		String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle,
		long[] groupIds, long[] organizationIds, long[] roleIds,
		long[] userGroupIds, boolean sendEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addUser", _addUserParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, locale, firstName, middleName, lastName, prefixId,
					suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
					sendEmail, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		java.util.Locale locale, String firstName, String middleName,
		String lastName, long prefixId, long suffixId, int birthdayMonth,
		int birthdayDay, int birthdayYear, String jobTitle, long[] groupIds,
		long[] organizationIds, long[] roleIds, long[] userGroupIds,
		boolean sendEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addUser", _addUserParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, locale, firstName, middleName, lastName, prefixId,
					suffixId, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
					sendEmail, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		java.util.Locale locale, String firstName, String middleName,
		String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle,
		long[] groupIds, long[] organizationIds, long[] roleIds,
		long[] userGroupIds,
		java.util.List<com.liferay.portal.kernel.model.Address> addresses,
		java.util.List<com.liferay.portal.kernel.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.kernel.model.Phone> phones,
		java.util.List<com.liferay.portal.kernel.model.Website> websites,
		java.util.List<com.liferay.announcements.kernel.model.AnnouncementsDelivery> announcementsDelivers,
		boolean sendEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addUser", _addUserParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, locale, firstName, middleName, lastName, prefixId,
					suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
					addresses, emailAddresses, phones, websites,
					announcementsDelivers, sendEmail, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		java.util.Locale locale, String firstName, String middleName,
		String lastName, long prefixId, long suffixId, int birthdayMonth,
		int birthdayDay, int birthdayYear, String jobTitle, long[] groupIds,
		long[] organizationIds, long[] roleIds, long[] userGroupIds,
		java.util.List<com.liferay.portal.kernel.model.Address> addresses,
		java.util.List<com.liferay.portal.kernel.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.kernel.model.Phone> phones,
		java.util.List<com.liferay.portal.kernel.model.Website> websites,
		java.util.List<com.liferay.announcements.kernel.model.AnnouncementsDelivery> announcementsDelivers,
		boolean sendEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addUser", _addUserParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, locale, firstName, middleName, lastName, prefixId,
					suffixId, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
					addresses, emailAddresses, phones, websites,
					announcementsDelivers, sendEmail, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addUserGroupUsers(HttpPrincipal httpPrincipal,
		long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addUserGroupUsers", _addUserGroupUsersParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					userGroupId, userIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		java.util.Locale locale, String firstName, String middleName,
		String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle,
		long[] groupIds, long[] organizationIds, long[] roleIds,
		long[] userGroupIds, boolean sendEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addUserWithWorkflow", _addUserWithWorkflowParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, locale, firstName, middleName, lastName, prefixId,
					suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
					sendEmail, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		java.util.Locale locale, String firstName, String middleName,
		String lastName, long prefixId, long suffixId, int birthdayMonth,
		int birthdayDay, int birthdayYear, String jobTitle, long[] groupIds,
		long[] organizationIds, long[] roleIds, long[] userGroupIds,
		boolean sendEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addUserWithWorkflow", _addUserWithWorkflowParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, locale, firstName, middleName, lastName, prefixId,
					suffixId, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
					sendEmail, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		java.util.Locale locale, String firstName, String middleName,
		String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle,
		long[] groupIds, long[] organizationIds, long[] roleIds,
		long[] userGroupIds,
		java.util.List<com.liferay.portal.kernel.model.Address> addresses,
		java.util.List<com.liferay.portal.kernel.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.kernel.model.Phone> phones,
		java.util.List<com.liferay.portal.kernel.model.Website> websites,
		java.util.List<com.liferay.announcements.kernel.model.AnnouncementsDelivery> announcementsDelivers,
		boolean sendEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addUserWithWorkflow", _addUserWithWorkflowParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, locale, firstName, middleName, lastName, prefixId,
					suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
					addresses, emailAddresses, phones, websites,
					announcementsDelivers, sendEmail, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		java.util.Locale locale, String firstName, String middleName,
		String lastName, long prefixId, long suffixId, int birthdayMonth,
		int birthdayDay, int birthdayYear, String jobTitle, long[] groupIds,
		long[] organizationIds, long[] roleIds, long[] userGroupIds,
		java.util.List<com.liferay.portal.kernel.model.Address> addresses,
		java.util.List<com.liferay.portal.kernel.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.kernel.model.Phone> phones,
		java.util.List<com.liferay.portal.kernel.model.Website> websites,
		java.util.List<com.liferay.announcements.kernel.model.AnnouncementsDelivery> announcementsDelivers,
		boolean sendEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"addUserWithWorkflow", _addUserWithWorkflowParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, locale, firstName, middleName, lastName, prefixId,
					suffixId, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
					addresses, emailAddresses, phones, websites,
					announcementsDelivers, sendEmail, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"deletePortrait", _deletePortraitParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteRoleUser(HttpPrincipal httpPrincipal, long roleId,
		long userId) throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"deleteRoleUser", _deleteRoleUserParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId,
					userId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"deleteUser", _deleteUserParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User> getCompanyUsers(
		HttpPrincipal httpPrincipal, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getCompanyUsers", _getCompanyUsersParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.User>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCompanyUsersCount(HttpPrincipal httpPrincipal,
		long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getCompanyUsersCount",
					_getCompanyUsersCountParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(methodKey, companyId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getCurrentUser", _getCurrentUserParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long[] getGroupUserIds(HttpPrincipal httpPrincipal,
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getGroupUserIds", _getGroupUserIdsParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (long[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User> getGroupUsers(
		HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getGroupUsers", _getGroupUsersParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.User>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User> getGroupUsers(
		HttpPrincipal httpPrincipal, long groupId, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.User> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getGroupUsers", _getGroupUsersParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					status, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.User>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User> getGroupUsers(
		HttpPrincipal httpPrincipal, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.User> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getGroupUsers", _getGroupUsersParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					status, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.User>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getGroupUsersCount(HttpPrincipal httpPrincipal,
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getGroupUsersCount", _getGroupUsersCountParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User> getGtCompanyUsers(
		HttpPrincipal httpPrincipal, long gtUserId, long companyId, int size)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getGtCompanyUsers", _getGtCompanyUsersParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					gtUserId, companyId, size);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.User>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User> getGtOrganizationUsers(
		HttpPrincipal httpPrincipal, long gtUserId, long organizationId,
		int size) throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getGtOrganizationUsers",
					_getGtOrganizationUsersParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					gtUserId, organizationId, size);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.User>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User> getGtUserGroupUsers(
		HttpPrincipal httpPrincipal, long gtUserId, long userGroupId, int size)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getGtUserGroupUsers", _getGtUserGroupUsersParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					gtUserId, userGroupId, size);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.User>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getOrganizationsAndUserGroupsUsersCount(
		HttpPrincipal httpPrincipal, long[] organizationIds, long[] userGroupIds)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getOrganizationsAndUserGroupsUsersCount",
					_getOrganizationsAndUserGroupsUsersCountParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					organizationIds, userGroupIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.security.auth.PrincipalException) {
					throw (com.liferay.portal.kernel.security.auth.PrincipalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long[] getOrganizationUserIds(HttpPrincipal httpPrincipal,
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getOrganizationUserIds",
					_getOrganizationUserIdsParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					organizationId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (long[])returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User> getOrganizationUsers(
		HttpPrincipal httpPrincipal, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getOrganizationUsers",
					_getOrganizationUsersParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					organizationId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.User>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User> getOrganizationUsers(
		HttpPrincipal httpPrincipal, long organizationId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.User> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getOrganizationUsers",
					_getOrganizationUsersParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					organizationId, status, start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.User>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User> getOrganizationUsers(
		HttpPrincipal httpPrincipal, long organizationId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.User> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getOrganizationUsers",
					_getOrganizationUsersParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					organizationId, status, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.User>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getOrganizationUsersCount(HttpPrincipal httpPrincipal,
		long organizationId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getOrganizationUsersCount",
					_getOrganizationUsersCountParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					organizationId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long[] getRoleUserIds(HttpPrincipal httpPrincipal, long roleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getRoleUserIds", _getRoleUserIdsParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getUserByEmailAddress",
					_getUserByEmailAddressParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, emailAddress);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getUserById", _getUserByIdParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getUserByScreenName", _getUserByScreenNameParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, screenName);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User> getUserGroupUsers(
		HttpPrincipal httpPrincipal, long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getUserGroupUsers", _getUserGroupUsersParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					userGroupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.User>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User> getUserGroupUsers(
		HttpPrincipal httpPrincipal, long userGroupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getUserGroupUsers", _getUserGroupUsersParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					userGroupId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.User>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long getUserIdByEmailAddress(HttpPrincipal httpPrincipal,
		long companyId, String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getUserIdByEmailAddress",
					_getUserIdByEmailAddressParameterTypes40);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, emailAddress);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long getUserIdByScreenName(HttpPrincipal httpPrincipal,
		long companyId, String screenName)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"getUserIdByScreenName",
					_getUserIdByScreenNameParameterTypes41);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, screenName);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasGroupUser(HttpPrincipal httpPrincipal,
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"hasGroupUser", _hasGroupUserParameterTypes42);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
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

	public static boolean hasRoleUser(HttpPrincipal httpPrincipal, long roleId,
		long userId) throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"hasRoleUser", _hasRoleUserParameterTypes43);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId,
					userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
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

	public static boolean hasRoleUser(HttpPrincipal httpPrincipal,
		long companyId, String name, long userId, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"hasRoleUser", _hasRoleUserParameterTypes44);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, name, userId, inherited);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
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

	public static boolean sendPasswordByEmailAddress(
		HttpPrincipal httpPrincipal, long companyId, String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"sendPasswordByEmailAddress",
					_sendPasswordByEmailAddressParameterTypes45);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, emailAddress);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
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

	public static boolean sendPasswordByScreenName(
		HttpPrincipal httpPrincipal, long companyId, String screenName)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"sendPasswordByScreenName",
					_sendPasswordByScreenNameParameterTypes46);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, screenName);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
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

	public static boolean sendPasswordByUserId(HttpPrincipal httpPrincipal,
		long userId) throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"sendPasswordByUserId",
					_sendPasswordByUserIdParameterTypes47);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
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

	public static void setRoleUsers(HttpPrincipal httpPrincipal, long roleId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"setRoleUsers", _setRoleUsersParameterTypes48);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId,
					userIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void setUserGroupUsers(HttpPrincipal httpPrincipal,
		long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"setUserGroupUsers", _setUserGroupUsersParameterTypes49);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					userGroupId, userIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetGroupTeamsUsers(HttpPrincipal httpPrincipal,
		long groupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"unsetGroupTeamsUsers",
					_unsetGroupTeamsUsersParameterTypes50);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetGroupUsers(HttpPrincipal httpPrincipal,
		long groupId, long[] userIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"unsetGroupUsers", _unsetGroupUsersParameterTypes51);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					userIds, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetOrganizationUsers(HttpPrincipal httpPrincipal,
		long organizationId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"unsetOrganizationUsers",
					_unsetOrganizationUsersParameterTypes52);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					organizationId, userIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetPasswordPolicyUsers(HttpPrincipal httpPrincipal,
		long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"unsetPasswordPolicyUsers",
					_unsetPasswordPolicyUsersParameterTypes53);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					passwordPolicyId, userIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetRoleUsers(HttpPrincipal httpPrincipal, long roleId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"unsetRoleUsers", _unsetRoleUsersParameterTypes54);

			MethodHandler methodHandler = new MethodHandler(methodKey, roleId,
					userIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetTeamUsers(HttpPrincipal httpPrincipal, long teamId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"unsetTeamUsers", _unsetTeamUsersParameterTypes55);

			MethodHandler methodHandler = new MethodHandler(methodKey, teamId,
					userIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetUserGroupUsers(HttpPrincipal httpPrincipal,
		long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"unsetUserGroupUsers", _unsetUserGroupUsersParameterTypes56);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					userGroupId, userIds);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User updateAgreedToTermsOfUse(
		HttpPrincipal httpPrincipal, long userId, boolean agreedToTermsOfUse)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateAgreedToTermsOfUse",
					_updateAgreedToTermsOfUseParameterTypes57);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					agreedToTermsOfUse);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateEmailAddress", _updateEmailAddressParameterTypes58);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					password, emailAddress1, emailAddress2, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		java.util.Locale locale, String firstName, String middleName,
		String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle,
		boolean updateUserInformation, boolean sendEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateIncompleteUser",
					_updateIncompleteUserParameterTypes59);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, locale, firstName, middleName, lastName, prefixId,
					suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, updateUserInformation, sendEmail, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		java.util.Locale locale, String firstName, String middleName,
		String lastName, long prefixId, long suffixId, int birthdayMonth,
		int birthdayDay, int birthdayYear, String jobTitle,
		boolean updateUserInformation, boolean sendEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateIncompleteUser",
					_updateIncompleteUserParameterTypes60);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId,
					openId, locale, firstName, middleName, lastName, prefixId,
					suffixId, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, updateUserInformation, sendEmail, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateLockoutById", _updateLockoutByIdParameterTypes61);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					lockout);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateOpenId", _updateOpenIdParameterTypes62);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					openId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateOrganizations(HttpPrincipal httpPrincipal,
		long userId, long[] organizationIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateOrganizations", _updateOrganizationsParameterTypes63);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					organizationIds, serviceContext);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updatePassword", _updatePasswordParameterTypes64);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					password1, password2, passwordReset);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updatePortrait", _updatePortraitParameterTypes65);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					bytes);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User updateReminderQuery(
		HttpPrincipal httpPrincipal, long userId, String question, String answer)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateReminderQuery", _updateReminderQueryParameterTypes66);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					question, answer);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateScreenName", _updateScreenNameParameterTypes67);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					screenName);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User updateStatus(
		HttpPrincipal httpPrincipal, long userId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateStatus", _updateStatusParameterTypes68);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateStatus", _updateStatusParameterTypes69);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					status, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		boolean portrait, byte[] portraitBytes, String languageId,
		String timeZoneId, String greeting, String comments, String firstName,
		String middleName, String lastName, long prefixId, long suffixId,
		boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
		String smsSn, String facebookSn, String jabberSn, String skypeSn,
		String twitterSn, String jobTitle, long[] groupIds,
		long[] organizationIds, long[] roleIds,
		java.util.List<com.liferay.portal.kernel.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		java.util.List<com.liferay.portal.kernel.model.Address> addresses,
		java.util.List<com.liferay.portal.kernel.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.kernel.model.Phone> phones,
		java.util.List<com.liferay.portal.kernel.model.Website> websites,
		java.util.List<com.liferay.announcements.kernel.model.AnnouncementsDelivery> announcementsDelivers,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateUser", _updateUserParameterTypes70);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					oldPassword, newPassword1, newPassword2, passwordReset,
					reminderQueryQuestion, reminderQueryAnswer, screenName,
					emailAddress, facebookId, openId, portrait, portraitBytes,
					languageId, timeZoneId, greeting, comments, firstName,
					middleName, lastName, prefixId, suffixId, male,
					birthdayMonth, birthdayDay, birthdayYear, smsSn,
					facebookSn, jabberSn, skypeSn, twitterSn, jobTitle,
					groupIds, organizationIds, roleIds, userGroupRoles,
					userGroupIds, addresses, emailAddresses, phones, websites,
					announcementsDelivers, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		boolean portrait, byte[] portraitBytes, String languageId,
		String timeZoneId, String greeting, String comments, String firstName,
		String middleName, String lastName, long prefixId, long suffixId,
		int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn,
		String facebookSn, String jabberSn, String skypeSn, String twitterSn,
		String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds,
		java.util.List<com.liferay.portal.kernel.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		java.util.List<com.liferay.portal.kernel.model.Address> addresses,
		java.util.List<com.liferay.portal.kernel.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.kernel.model.Phone> phones,
		java.util.List<com.liferay.portal.kernel.model.Website> websites,
		java.util.List<com.liferay.announcements.kernel.model.AnnouncementsDelivery> announcementsDelivers,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateUser", _updateUserParameterTypes71);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					oldPassword, newPassword1, newPassword2, passwordReset,
					reminderQueryQuestion, reminderQueryAnswer, screenName,
					emailAddress, facebookId, openId, portrait, portraitBytes,
					languageId, timeZoneId, greeting, comments, firstName,
					middleName, lastName, prefixId, suffixId, birthdayMonth,
					birthdayDay, birthdayYear, smsSn, facebookSn, jabberSn,
					skypeSn, twitterSn, jobTitle, groupIds, organizationIds,
					roleIds, userGroupRoles, userGroupIds, addresses,
					emailAddresses, phones, websites, announcementsDelivers,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		String languageId, String timeZoneId, String greeting, String comments,
		String firstName, String middleName, String lastName, long prefixId,
		long suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, String smsSn, String facebookSn, String jabberSn,
		String skypeSn, String twitterSn, String jobTitle, long[] groupIds,
		long[] organizationIds, long[] roleIds,
		java.util.List<com.liferay.portal.kernel.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		java.util.List<com.liferay.portal.kernel.model.Address> addresses,
		java.util.List<com.liferay.portal.kernel.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.kernel.model.Phone> phones,
		java.util.List<com.liferay.portal.kernel.model.Website> websites,
		java.util.List<com.liferay.announcements.kernel.model.AnnouncementsDelivery> announcementsDelivers,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateUser", _updateUserParameterTypes72);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					oldPassword, newPassword1, newPassword2, passwordReset,
					reminderQueryQuestion, reminderQueryAnswer, screenName,
					emailAddress, facebookId, openId, languageId, timeZoneId,
					greeting, comments, firstName, middleName, lastName,
					prefixId, suffixId, male, birthdayMonth, birthdayDay,
					birthdayYear, smsSn, facebookSn, jabberSn, skypeSn,
					twitterSn, jobTitle, groupIds, organizationIds, roleIds,
					userGroupRoles, userGroupIds, addresses, emailAddresses,
					phones, websites, announcementsDelivers, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
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
		String screenName, String emailAddress, long facebookId, String openId,
		String languageId, String timeZoneId, String greeting, String comments,
		String firstName, String middleName, String lastName, long prefixId,
		long suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, String smsSn, String facebookSn, String jabberSn,
		String skypeSn, String twitterSn, String jobTitle, long[] groupIds,
		long[] organizationIds, long[] roleIds,
		java.util.List<com.liferay.portal.kernel.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(UserServiceUtil.class,
					"updateUser", _updateUserParameterTypes73);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					oldPassword, newPassword1, newPassword2, passwordReset,
					reminderQueryQuestion, reminderQueryAnswer, screenName,
					emailAddress, facebookId, openId, languageId, timeZoneId,
					greeting, comments, firstName, middleName, lastName,
					prefixId, suffixId, male, birthdayMonth, birthdayDay,
					birthdayYear, smsSn, facebookSn, jabberSn, skypeSn,
					twitterSn, jobTitle, groupIds, organizationIds, roleIds,
					userGroupRoles, userGroupIds, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UserServiceHttp.class);
	private static final Class<?>[] _addGroupUsersParameterTypes0 = new Class[] {
			long.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addOrganizationUsersParameterTypes1 = new Class[] {
			long.class, long[].class
		};
	private static final Class<?>[] _addPasswordPolicyUsersParameterTypes2 = new Class[] {
			long.class, long[].class
		};
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
			long.class, long.class, boolean.class, int.class, int.class,
			int.class, String.class, long[].class, long[].class, long[].class,
			long[].class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addUserParameterTypes6 = new Class[] {
			long.class, boolean.class, String.class, String.class, boolean.class,
			String.class, String.class, long.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, int.class, int.class, int.class,
			String.class, long[].class, long[].class, long[].class, long[].class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addUserParameterTypes7 = new Class[] {
			long.class, boolean.class, String.class, String.class, boolean.class,
			String.class, String.class, long.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, boolean.class, int.class, int.class,
			int.class, String.class, long[].class, long[].class, long[].class,
			long[].class, java.util.List.class, java.util.List.class,
			java.util.List.class, java.util.List.class, java.util.List.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addUserParameterTypes8 = new Class[] {
			long.class, boolean.class, String.class, String.class, boolean.class,
			String.class, String.class, long.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, int.class, int.class, int.class,
			String.class, long[].class, long[].class, long[].class, long[].class,
			java.util.List.class, java.util.List.class, java.util.List.class,
			java.util.List.class, java.util.List.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addUserGroupUsersParameterTypes9 = new Class[] {
			long.class, long[].class
		};
	private static final Class<?>[] _addUserWithWorkflowParameterTypes10 = new Class[] {
			long.class, boolean.class, String.class, String.class, boolean.class,
			String.class, String.class, long.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, boolean.class, int.class, int.class,
			int.class, String.class, long[].class, long[].class, long[].class,
			long[].class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addUserWithWorkflowParameterTypes11 = new Class[] {
			long.class, boolean.class, String.class, String.class, boolean.class,
			String.class, String.class, long.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, int.class, int.class, int.class,
			String.class, long[].class, long[].class, long[].class, long[].class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addUserWithWorkflowParameterTypes12 = new Class[] {
			long.class, boolean.class, String.class, String.class, boolean.class,
			String.class, String.class, long.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, boolean.class, int.class, int.class,
			int.class, String.class, long[].class, long[].class, long[].class,
			long[].class, java.util.List.class, java.util.List.class,
			java.util.List.class, java.util.List.class, java.util.List.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addUserWithWorkflowParameterTypes13 = new Class[] {
			long.class, boolean.class, String.class, String.class, boolean.class,
			String.class, String.class, long.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, int.class, int.class, int.class,
			String.class, long[].class, long[].class, long[].class, long[].class,
			java.util.List.class, java.util.List.class, java.util.List.class,
			java.util.List.class, java.util.List.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deletePortraitParameterTypes14 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deleteRoleUserParameterTypes15 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _deleteUserParameterTypes16 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCompanyUsersParameterTypes17 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getCompanyUsersCountParameterTypes18 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCurrentUserParameterTypes19 = new Class[] {
			
		};
	private static final Class<?>[] _getGroupUserIdsParameterTypes20 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getGroupUsersParameterTypes21 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getGroupUsersParameterTypes22 = new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupUsersParameterTypes23 = new Class[] {
			long.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getGroupUsersCountParameterTypes24 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _getGtCompanyUsersParameterTypes25 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[] _getGtOrganizationUsersParameterTypes26 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[] _getGtUserGroupUsersParameterTypes27 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[] _getOrganizationsAndUserGroupsUsersCountParameterTypes28 =
		new Class[] { long[].class, long[].class };
	private static final Class<?>[] _getOrganizationUserIdsParameterTypes29 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getOrganizationUsersParameterTypes30 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getOrganizationUsersParameterTypes31 = new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getOrganizationUsersParameterTypes32 = new Class[] {
			long.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getOrganizationUsersCountParameterTypes33 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _getRoleUserIdsParameterTypes34 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getUserByEmailAddressParameterTypes35 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getUserByIdParameterTypes36 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getUserByScreenNameParameterTypes37 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getUserGroupUsersParameterTypes38 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getUserGroupUsersParameterTypes39 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getUserIdByEmailAddressParameterTypes40 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getUserIdByScreenNameParameterTypes41 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _hasGroupUserParameterTypes42 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _hasRoleUserParameterTypes43 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _hasRoleUserParameterTypes44 = new Class[] {
			long.class, String.class, long.class, boolean.class
		};
	private static final Class<?>[] _sendPasswordByEmailAddressParameterTypes45 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _sendPasswordByScreenNameParameterTypes46 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _sendPasswordByUserIdParameterTypes47 = new Class[] {
			long.class
		};
	private static final Class<?>[] _setRoleUsersParameterTypes48 = new Class[] {
			long.class, long[].class
		};
	private static final Class<?>[] _setUserGroupUsersParameterTypes49 = new Class[] {
			long.class, long[].class
		};
	private static final Class<?>[] _unsetGroupTeamsUsersParameterTypes50 = new Class[] {
			long.class, long[].class
		};
	private static final Class<?>[] _unsetGroupUsersParameterTypes51 = new Class[] {
			long.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _unsetOrganizationUsersParameterTypes52 = new Class[] {
			long.class, long[].class
		};
	private static final Class<?>[] _unsetPasswordPolicyUsersParameterTypes53 = new Class[] {
			long.class, long[].class
		};
	private static final Class<?>[] _unsetRoleUsersParameterTypes54 = new Class[] {
			long.class, long[].class
		};
	private static final Class<?>[] _unsetTeamUsersParameterTypes55 = new Class[] {
			long.class, long[].class
		};
	private static final Class<?>[] _unsetUserGroupUsersParameterTypes56 = new Class[] {
			long.class, long[].class
		};
	private static final Class<?>[] _updateAgreedToTermsOfUseParameterTypes57 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _updateEmailAddressParameterTypes58 = new Class[] {
			long.class, String.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateIncompleteUserParameterTypes59 = new Class[] {
			long.class, boolean.class, String.class, String.class, boolean.class,
			String.class, String.class, long.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, boolean.class, int.class, int.class,
			int.class, String.class, boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateIncompleteUserParameterTypes60 = new Class[] {
			long.class, boolean.class, String.class, String.class, boolean.class,
			String.class, String.class, long.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class, int.class, int.class, int.class,
			String.class, boolean.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateLockoutByIdParameterTypes61 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _updateOpenIdParameterTypes62 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _updateOrganizationsParameterTypes63 = new Class[] {
			long.class, long[].class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updatePasswordParameterTypes64 = new Class[] {
			long.class, String.class, String.class, boolean.class
		};
	private static final Class<?>[] _updatePortraitParameterTypes65 = new Class[] {
			long.class, byte[].class
		};
	private static final Class<?>[] _updateReminderQueryParameterTypes66 = new Class[] {
			long.class, String.class, String.class
		};
	private static final Class<?>[] _updateScreenNameParameterTypes67 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _updateStatusParameterTypes68 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _updateStatusParameterTypes69 = new Class[] {
			long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateUserParameterTypes70 = new Class[] {
			long.class, String.class, String.class, String.class, boolean.class,
			String.class, String.class, String.class, String.class, long.class,
			String.class, boolean.class, byte[].class, String.class,
			String.class, String.class, String.class, String.class, String.class,
			String.class, long.class, long.class, boolean.class, int.class,
			int.class, int.class, String.class, String.class, String.class,
			String.class, String.class, String.class, long[].class, long[].class,
			long[].class, java.util.List.class, long[].class,
			java.util.List.class, java.util.List.class, java.util.List.class,
			java.util.List.class, java.util.List.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateUserParameterTypes71 = new Class[] {
			long.class, String.class, String.class, String.class, boolean.class,
			String.class, String.class, String.class, String.class, long.class,
			String.class, boolean.class, byte[].class, String.class,
			String.class, String.class, String.class, String.class, String.class,
			String.class, long.class, long.class, int.class, int.class,
			int.class, String.class, String.class, String.class, String.class,
			String.class, String.class, long[].class, long[].class, long[].class,
			java.util.List.class, long[].class, java.util.List.class,
			java.util.List.class, java.util.List.class, java.util.List.class,
			java.util.List.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateUserParameterTypes72 = new Class[] {
			long.class, String.class, String.class, String.class, boolean.class,
			String.class, String.class, String.class, String.class, long.class,
			String.class, String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, long.class, long.class,
			boolean.class, int.class, int.class, int.class, String.class,
			String.class, String.class, String.class, String.class, String.class,
			long[].class, long[].class, long[].class, java.util.List.class,
			long[].class, java.util.List.class, java.util.List.class,
			java.util.List.class, java.util.List.class, java.util.List.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateUserParameterTypes73 = new Class[] {
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