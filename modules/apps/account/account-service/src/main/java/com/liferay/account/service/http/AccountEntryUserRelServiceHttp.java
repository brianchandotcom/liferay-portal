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

package com.liferay.account.service.http;

import com.liferay.account.service.AccountEntryUserRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>AccountEntryUserRelServiceUtil</code> service
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
 * @see AccountEntryUserRelServiceSoap
 * @generated
 */
public class AccountEntryUserRelServiceHttp {

	public static com.liferay.account.model.AccountEntryUserRel
			addAccountEntryUserRel(
				HttpPrincipal httpPrincipal, long accountEntryId,
				long creatorUserId, String screenName, String emailAddress,
				java.util.Locale locale, String firstName, String middleName,
				String lastName, long prefixId, long suffixId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class, "addAccountEntryUserRel",
				_addAccountEntryUserRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, creatorUserId, screenName,
				emailAddress, locale, firstName, middleName, lastName, prefixId,
				suffixId);

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

			return (com.liferay.account.model.AccountEntryUserRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.account.model.AccountEntryUserRel
			addAccountEntryUserRelByEmailAddress(
				HttpPrincipal httpPrincipal, long accountEntryId,
				String emailAddress, long[] accountRoleIds,
				String userExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class,
				"addAccountEntryUserRelByEmailAddress",
				_addAccountEntryUserRelByEmailAddressParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, emailAddress, accountRoleIds,
				userExternalReferenceCode, serviceContext);

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

			return (com.liferay.account.model.AccountEntryUserRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteAccountEntryUserRelByEmailAddress(
			HttpPrincipal httpPrincipal, long accountEntryId,
			String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AccountEntryUserRelServiceUtil.class,
				"deleteAccountEntryUserRelByEmailAddress",
				_deleteAccountEntryUserRelByEmailAddressParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountEntryId, emailAddress);

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
		AccountEntryUserRelServiceHttp.class);

	private static final Class<?>[] _addAccountEntryUserRelParameterTypes0 =
		new Class[] {
			long.class, long.class, String.class, String.class,
			java.util.Locale.class, String.class, String.class, String.class,
			long.class, long.class
		};
	private static final Class<?>[]
		_addAccountEntryUserRelByEmailAddressParameterTypes1 = new Class[] {
			long.class, String.class, long[].class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteAccountEntryUserRelByEmailAddressParameterTypes2 = new Class[] {
			long.class, String.class
		};

}