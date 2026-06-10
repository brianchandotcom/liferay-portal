/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.version.service.http;

import com.liferay.layout.content.version.service.LayoutContentVersionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>LayoutContentVersionServiceUtil</code> service
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
 * @author Lourdes Fernández Besada
 * @generated
 */
public class LayoutContentVersionServiceHttp {

	public static com.liferay.layout.content.version.model.LayoutContentVersion
			addLayoutContentVersion(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long plid, java.util.Map<java.util.Locale, String> nameMap,
				String data, int status, boolean skipIfUnchanged)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutContentVersionServiceUtil.class,
				"addLayoutContentVersion",
				_addLayoutContentVersionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, plid, nameMap, data, status,
				skipIfUnchanged);

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

			return (com.liferay.layout.content.version.model.
				LayoutContentVersion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.layout.content.version.model.LayoutContentVersion
			deleteLayoutContentVersion(
				HttpPrincipal httpPrincipal, long layoutContentVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutContentVersionServiceUtil.class,
				"deleteLayoutContentVersion",
				_deleteLayoutContentVersionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutContentVersionId);

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

			return (com.liferay.layout.content.version.model.
				LayoutContentVersion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.layout.content.version.model.LayoutContentVersion
			getLayoutContentVersion(
				HttpPrincipal httpPrincipal, long layoutContentVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutContentVersionServiceUtil.class,
				"getLayoutContentVersion",
				_getLayoutContentVersionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutContentVersionId);

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

			return (com.liferay.layout.content.version.model.
				LayoutContentVersion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.layout.content.version.model.LayoutContentVersion
			getLayoutContentVersionByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutContentVersionServiceUtil.class,
				"getLayoutContentVersionByExternalReferenceCode",
				_getLayoutContentVersionByExternalReferenceCodeParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId);

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

			return (com.liferay.layout.content.version.model.
				LayoutContentVersion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.layout.content.version.model.LayoutContentVersion>
				getLayoutContentVersions(HttpPrincipal httpPrincipal, long plid)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutContentVersionServiceUtil.class,
				"getLayoutContentVersions",
				_getLayoutContentVersionsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, plid);

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

			return (java.util.List
				<com.liferay.layout.content.version.model.LayoutContentVersion>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.layout.content.version.model.LayoutContentVersion
			updateLayoutContentVersion(
				HttpPrincipal httpPrincipal, long layoutContentVersionId,
				java.util.Map<java.util.Locale, String> nameMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutContentVersionServiceUtil.class,
				"updateLayoutContentVersion",
				_updateLayoutContentVersionParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, layoutContentVersionId, nameMap);

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

			return (com.liferay.layout.content.version.model.
				LayoutContentVersion)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LayoutContentVersionServiceHttp.class);

	private static final Class<?>[] _addLayoutContentVersionParameterTypes0 =
		new Class[] {
			String.class, long.class, java.util.Map.class, String.class,
			int.class, boolean.class
		};
	private static final Class<?>[] _deleteLayoutContentVersionParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getLayoutContentVersionParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getLayoutContentVersionByExternalReferenceCodeParameterTypes3 =
			new Class[] {String.class, long.class};
	private static final Class<?>[] _getLayoutContentVersionsParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _updateLayoutContentVersionParameterTypes5 =
		new Class[] {long.class, java.util.Map.class};

}
// LIFERAY-SERVICE-BUILDER-HASH:-764992580