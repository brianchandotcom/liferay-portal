/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.service.http;

import com.liferay.cookies.service.ConsentPreferenceServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ConsentPreferenceServiceUtil</code> service
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
public class ConsentPreferenceServiceHttp {

	public static com.liferay.cookies.model.ConsentPreference
			addConsentPreference(
				HttpPrincipal httpPrincipal, long userId, String domain,
				java.util.Date expirationDate, String name, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ConsentPreferenceServiceUtil.class, "addConsentPreference",
				_addConsentPreferenceParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, domain, expirationDate, name, value);

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

			return (com.liferay.cookies.model.ConsentPreference)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteConsentPreference(
			HttpPrincipal httpPrincipal, long userId, String domain,
			String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ConsentPreferenceServiceUtil.class, "deleteConsentPreference",
				_deleteConsentPreferenceParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, domain, name);

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

	public static void deleteConsentPreferences(
		HttpPrincipal httpPrincipal, long userId, String domain) {

		try {
			MethodKey methodKey = new MethodKey(
				ConsentPreferenceServiceUtil.class, "deleteConsentPreferences",
				_deleteConsentPreferencesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, domain);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
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

	public static com.liferay.cookies.model.ConsentPreference
		getConsentPreference(
			HttpPrincipal httpPrincipal, long userId, String domain,
			String name) {

		try {
			MethodKey methodKey = new MethodKey(
				ConsentPreferenceServiceUtil.class, "getConsentPreference",
				_getConsentPreferenceParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, domain, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.cookies.model.ConsentPreference)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.cookies.model.ConsentPreference>
		getConsentPreferences(
			HttpPrincipal httpPrincipal, long userId, String domain) {

		try {
			MethodKey methodKey = new MethodKey(
				ConsentPreferenceServiceUtil.class, "getConsentPreferences",
				_getConsentPreferencesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, domain);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.cookies.model.ConsentPreference>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.cookies.model.ConsentPreference
		updateConsentPreference(
			HttpPrincipal httpPrincipal,
			com.liferay.cookies.model.ConsentPreference consentPreference) {

		try {
			MethodKey methodKey = new MethodKey(
				ConsentPreferenceServiceUtil.class, "updateConsentPreference",
				_updateConsentPreferenceParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, consentPreference);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.cookies.model.ConsentPreference)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ConsentPreferenceServiceHttp.class);

	private static final Class<?>[] _addConsentPreferenceParameterTypes0 =
		new Class[] {
			long.class, String.class, java.util.Date.class, String.class,
			String.class
		};
	private static final Class<?>[] _deleteConsentPreferenceParameterTypes1 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[] _deleteConsentPreferencesParameterTypes2 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getConsentPreferenceParameterTypes3 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[] _getConsentPreferencesParameterTypes4 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateConsentPreferenceParameterTypes5 =
		new Class[] {com.liferay.cookies.model.ConsentPreference.class};

}