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

package com.liferay.commerce.user.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.service.CommerceUserServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceUserServiceUtil} service utility. The
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
 * @author Alessio Antonio Rendina
 * @see CommerceUserServiceSoap
 * @see HttpPrincipal
 * @see CommerceUserServiceUtil
 * @generated
 */
@ProviderType
public class CommerceUserServiceHttp {
	public static com.liferay.portal.kernel.model.User getUser(
		HttpPrincipal httpPrincipal, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserServiceUtil.class,
					"getUser", _getUserParameterTypes0);

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

	public static com.liferay.portal.kernel.model.User updatePassword(
		HttpPrincipal httpPrincipal, long userId, String password1,
		String password2, boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserServiceUtil.class,
					"updatePassword", _updatePasswordParameterTypes1);

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

	public static com.liferay.portal.kernel.model.User updatePasswordReset(
		HttpPrincipal httpPrincipal, long userId, boolean passwordReset)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserServiceUtil.class,
					"updatePasswordReset", _updatePasswordResetParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					passwordReset);

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
			MethodKey methodKey = new MethodKey(CommerceUserServiceUtil.class,
					"updateReminderQuery", _updateReminderQueryParameterTypes3);

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

	public static com.liferay.portal.kernel.model.User updateUser(
		HttpPrincipal httpPrincipal, long userId, String screenName,
		String emailAddress, boolean portrait, byte[] portraitBytes,
		String languageId, String firstName, String middleName,
		String lastName, long prefixId, long suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserServiceUtil.class,
					"updateUser", _updateUserParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					screenName, emailAddress, portrait, portraitBytes,
					languageId, firstName, middleName, lastName, prefixId,
					suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, serviceContext);

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

	public static void updateUserRoles(HttpPrincipal httpPrincipal,
		long userId, long groupId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceUserServiceUtil.class,
					"updateUserRoles", _updateUserRolesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					groupId, roleIds);

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

	private static Log _log = LogFactoryUtil.getLog(CommerceUserServiceHttp.class);
	private static final Class<?>[] _getUserParameterTypes0 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updatePasswordParameterTypes1 = new Class[] {
			long.class, String.class, String.class, boolean.class
		};
	private static final Class<?>[] _updatePasswordResetParameterTypes2 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _updateReminderQueryParameterTypes3 = new Class[] {
			long.class, String.class, String.class
		};
	private static final Class<?>[] _updateUserParameterTypes4 = new Class[] {
			long.class, String.class, String.class, boolean.class, byte[].class,
			String.class, String.class, String.class, String.class, long.class,
			long.class, boolean.class, int.class, int.class, int.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateUserRolesParameterTypes5 = new Class[] {
			long.class, long.class, long[].class
		};
}