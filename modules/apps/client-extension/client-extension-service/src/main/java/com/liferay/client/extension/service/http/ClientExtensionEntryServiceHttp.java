/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.service.http;

import com.liferay.client.extension.service.ClientExtensionEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ClientExtensionEntryServiceUtil</code> service
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
public class ClientExtensionEntryServiceHttp {

	public static com.liferay.client.extension.model.ClientExtensionEntry
			addClientExtensionEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				String description,
				java.util.Map<java.util.Locale, String> nameMap,
				String properties, String sourceCodeURL, String type,
				String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"addClientExtensionEntry",
				_addClientExtensionEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, description, nameMap,
				properties, sourceCodeURL, type, typeSettings);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			deleteClientExtensionEntry(
				HttpPrincipal httpPrincipal, long clientExtensionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"deleteClientExtensionEntry",
				_deleteClientExtensionEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, clientExtensionEntryId);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			deleteClientExtensionEntryByExternalReferenceCode(
				HttpPrincipal httpPrincipal, long companyId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"deleteClientExtensionEntryByExternalReferenceCode",
				_deleteClientExtensionEntryByExternalReferenceCodeParameterTypes2);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			fetchClientExtensionEntryByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"fetchClientExtensionEntryByExternalReferenceCode",
				_fetchClientExtensionEntryByExternalReferenceCodeParameterTypes3);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			getClientExtensionEntry(
				HttpPrincipal httpPrincipal, long clientExtensionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"getClientExtensionEntry",
				_getClientExtensionEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, clientExtensionEntryId);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			updateClientExtensionEntry(
				HttpPrincipal httpPrincipal, long clientExtensionEntryId,
				String description,
				java.util.Map<java.util.Locale, String> nameMap,
				String properties, String sourceCodeURL, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"updateClientExtensionEntry",
				_updateClientExtensionEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, clientExtensionEntryId, description, nameMap,
				properties, sourceCodeURL, typeSettings);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClientExtensionEntryServiceHttp.class);

	private static final Class<?>[] _addClientExtensionEntryParameterTypes0 =
		new Class[] {
			String.class, String.class, java.util.Map.class, String.class,
			String.class, String.class, String.class
		};
	private static final Class<?>[] _deleteClientExtensionEntryParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[]
		_deleteClientExtensionEntryByExternalReferenceCodeParameterTypes2 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_fetchClientExtensionEntryByExternalReferenceCodeParameterTypes3 =
			new Class[] {String.class, long.class};
	private static final Class<?>[] _getClientExtensionEntryParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _updateClientExtensionEntryParameterTypes5 =
		new Class[] {
			long.class, String.class, java.util.Map.class, String.class,
			String.class, String.class
		};

}