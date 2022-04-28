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
			addCustomElementClientExtensionEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				boolean customElementUseESM, String description,
				String friendlyURLMapping, boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"addCustomElementClientExtensionEntry",
				_addCustomElementClientExtensionEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, customElementCSSURLs,
				customElementHTMLElementName, customElementURLs,
				customElementUseESM, description, friendlyURLMapping,
				instanceable, nameMap, portletCategoryName, properties,
				sourceCodeURL);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			addIFrameClientExtensionEntry(
				HttpPrincipal httpPrincipal, String description,
				String friendlyURLMapping, String iFrameURL,
				boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class, "addIFrameClientExtensionEntry",
				_addIFrameClientExtensionEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, description, friendlyURLMapping, iFrameURL,
				instanceable, nameMap, portletCategoryName, properties,
				sourceCodeURL);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)returnObj;
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
				ClientExtensionEntryServiceUtil.class, "deleteClientExtensionEntry",
				_deleteClientExtensionEntryParameterTypes2);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)returnObj;
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
				ClientExtensionEntryServiceUtil.class, "getClientExtensionEntry",
				_getClientExtensionEntryParameterTypes3);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			updateCustomElementClientExtensionEntry(
				HttpPrincipal httpPrincipal, long clientExtensionEntryId,
				String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				boolean customElementUseESM, String description,
				String friendlyURLMapping,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"updateCustomElementClientExtensionEntry",
				_updateCustomElementClientExtensionEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, clientExtensionEntryId, customElementCSSURLs,
				customElementHTMLElementName, customElementURLs,
				customElementUseESM, description, friendlyURLMapping, nameMap,
				portletCategoryName, properties, sourceCodeURL);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			updateIFrameClientExtensionEntry(
				HttpPrincipal httpPrincipal, long clientExtensionEntryId,
				String description, String friendlyURLMapping, String iFrameURL,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class, "updateIFrameClientExtensionEntry",
				_updateIFrameClientExtensionEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, clientExtensionEntryId, description, friendlyURLMapping,
				iFrameURL, nameMap, portletCategoryName, properties,
				sourceCodeURL);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClientExtensionEntryServiceHttp.class);

	private static final Class<?>[]
		_addCustomElementClientExtensionEntryParameterTypes0 = new Class[] {
			String.class, String.class, String.class, String.class,
			boolean.class, String.class, String.class, boolean.class,
			java.util.Map.class, String.class, String.class, String.class
		};
	private static final Class<?>[] _addIFrameClientExtensionEntryParameterTypes1 =
		new Class[] {
			String.class, String.class, String.class, boolean.class,
			java.util.Map.class, String.class, String.class, String.class
		};
	private static final Class<?>[] _deleteClientExtensionEntryParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getClientExtensionEntryParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[]
		_updateCustomElementClientExtensionEntryParameterTypes4 = new Class[] {
			long.class, String.class, String.class, String.class, boolean.class,
			String.class, String.class, java.util.Map.class, String.class,
			String.class, String.class
		};
	private static final Class<?>[] _updateIFrameClientExtensionEntryParameterTypes5 =
		new Class[] {
			long.class, String.class, String.class, String.class,
			java.util.Map.class, String.class, String.class, String.class
		};

}