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

package com.liferay.remote.app.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.remote.app.service.RemoteAppEntryServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>RemoteAppEntryServiceUtil</code> service
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
public class RemoteAppEntryServiceHttp {

	public static com.liferay.remote.app.model.RemoteAppEntry
			addCustomElementRemoteAppEntry(
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
				RemoteAppEntryServiceUtil.class,
				"addCustomElementRemoteAppEntry",
				_addCustomElementRemoteAppEntryParameterTypes0);

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

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry
			addIFrameRemoteAppEntry(
				HttpPrincipal httpPrincipal, String description,
				String friendlyURLMapping, String iFrameURL,
				boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class, "addIFrameRemoteAppEntry",
				_addIFrameRemoteAppEntryParameterTypes1);

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

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry
			deleteRemoteAppEntry(
				HttpPrincipal httpPrincipal, long remoteAppEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class, "deleteRemoteAppEntry",
				_deleteRemoteAppEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, remoteAppEntryId);

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

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry getRemoteAppEntry(
			HttpPrincipal httpPrincipal, long remoteAppEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class, "getRemoteAppEntry",
				_getRemoteAppEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, remoteAppEntryId);

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

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry
			updateCustomElementRemoteAppEntry(
				HttpPrincipal httpPrincipal, long remoteAppEntryId,
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
				RemoteAppEntryServiceUtil.class,
				"updateCustomElementRemoteAppEntry",
				_updateCustomElementRemoteAppEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, remoteAppEntryId, customElementCSSURLs,
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

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.remote.app.model.RemoteAppEntry
			updateIFrameRemoteAppEntry(
				HttpPrincipal httpPrincipal, long remoteAppEntryId,
				String description, String friendlyURLMapping, String iFrameURL,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				RemoteAppEntryServiceUtil.class, "updateIFrameRemoteAppEntry",
				_updateIFrameRemoteAppEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, remoteAppEntryId, description, friendlyURLMapping,
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

			return (com.liferay.remote.app.model.RemoteAppEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		RemoteAppEntryServiceHttp.class);

	private static final Class<?>[]
		_addCustomElementRemoteAppEntryParameterTypes0 = new Class[] {
			String.class, String.class, String.class, String.class,
			boolean.class, String.class, String.class, boolean.class,
			java.util.Map.class, String.class, String.class, String.class
		};
	private static final Class<?>[] _addIFrameRemoteAppEntryParameterTypes1 =
		new Class[] {
			String.class, String.class, String.class, boolean.class,
			java.util.Map.class, String.class, String.class, String.class
		};
	private static final Class<?>[] _deleteRemoteAppEntryParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getRemoteAppEntryParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[]
		_updateCustomElementRemoteAppEntryParameterTypes4 = new Class[] {
			long.class, String.class, String.class, String.class, boolean.class,
			String.class, String.class, java.util.Map.class, String.class,
			String.class, String.class
		};
	private static final Class<?>[] _updateIFrameRemoteAppEntryParameterTypes5 =
		new Class[] {
			long.class, String.class, String.class, String.class,
			java.util.Map.class, String.class, String.class, String.class
		};

}