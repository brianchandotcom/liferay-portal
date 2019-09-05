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
import com.liferay.portal.kernel.service.EmailAddressServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>EmailAddressServiceUtil</code> service
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
 * @see EmailAddressServiceSoap
 * @generated
 */
public class EmailAddressServiceHttp {

	public static com.liferay.portal.kernel.model.EmailAddress addEmailAddress(
			HttpPrincipal httpPrincipal, String className, long classPK,
			String address, long typeId, boolean primary,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				EmailAddressServiceUtil.class, "addEmailAddress",
				_addEmailAddressParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK, address, typeId, primary,
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

			return (com.liferay.portal.kernel.model.EmailAddress)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteEmailAddress(
			HttpPrincipal httpPrincipal, long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				EmailAddressServiceUtil.class, "deleteEmailAddress",
				_deleteEmailAddressParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, emailAddressId);

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

	public static com.liferay.portal.kernel.model.EmailAddress
			fetchEmailAddress(HttpPrincipal httpPrincipal, long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				EmailAddressServiceUtil.class, "fetchEmailAddress",
				_fetchEmailAddressParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, emailAddressId);

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

			return (com.liferay.portal.kernel.model.EmailAddress)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.EmailAddress getEmailAddress(
			HttpPrincipal httpPrincipal, long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				EmailAddressServiceUtil.class, "getEmailAddress",
				_getEmailAddressParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, emailAddressId);

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

			return (com.liferay.portal.kernel.model.EmailAddress)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.EmailAddress>
			getEmailAddresses(
				HttpPrincipal httpPrincipal, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				EmailAddressServiceUtil.class, "getEmailAddresses",
				_getEmailAddressesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, className, classPK);

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

			return (java.util.List
				<com.liferay.portal.kernel.model.EmailAddress>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.EmailAddress
			updateEmailAddress(
				HttpPrincipal httpPrincipal, long emailAddressId,
				String address, long typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				EmailAddressServiceUtil.class, "updateEmailAddress",
				_updateEmailAddressParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, emailAddressId, address, typeId, primary);

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

			return (com.liferay.portal.kernel.model.EmailAddress)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		EmailAddressServiceHttp.class);

	private static final Class<?>[] _addEmailAddressParameterTypes0 =
		new Class[] {
			String.class, long.class, String.class, long.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteEmailAddressParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchEmailAddressParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getEmailAddressParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getEmailAddressesParameterTypes4 =
		new Class[] {String.class, long.class};
	private static final Class<?>[] _updateEmailAddressParameterTypes5 =
		new Class[] {long.class, String.class, long.class, boolean.class};

}