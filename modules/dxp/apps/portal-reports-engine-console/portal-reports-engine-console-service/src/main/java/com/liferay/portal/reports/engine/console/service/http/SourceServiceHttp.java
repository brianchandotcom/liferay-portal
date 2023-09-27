/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.reports.engine.console.service.SourceServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>SourceServiceUtil</code> service
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
public class SourceServiceHttp {

	public static com.liferay.portal.reports.engine.console.model.Source
			addSource(
				HttpPrincipal httpPrincipal, long groupId,
				java.util.Map<java.util.Locale, String> nameMap,
				String driverClassName, String driverUrl, String driverUserName,
				String driverPassword,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SourceServiceUtil.class, "addSource",
				_addSourceParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nameMap, driverClassName, driverUrl,
				driverUserName, driverPassword, serviceContext);

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

			return (com.liferay.portal.reports.engine.console.model.Source)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.reports.engine.console.model.Source
			deleteSource(HttpPrincipal httpPrincipal, long sourceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SourceServiceUtil.class, "deleteSource",
				_deleteSourceParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sourceId);

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

			return (com.liferay.portal.reports.engine.console.model.Source)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.reports.engine.console.model.Source
			getSource(HttpPrincipal httpPrincipal, long sourceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SourceServiceUtil.class, "getSource",
				_getSourceParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sourceId);

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

			return (com.liferay.portal.reports.engine.console.model.Source)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.reports.engine.console.model.Source> getSources(
				HttpPrincipal httpPrincipal, long groupId, String name,
				String driverUrl, boolean andSearch, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.reports.engine.console.model.Source>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SourceServiceUtil.class, "getSources",
				_getSourcesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, driverUrl, andSearch, start, end,
				orderByComparator);

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
				<com.liferay.portal.reports.engine.console.model.Source>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getSourcesCount(
		HttpPrincipal httpPrincipal, long groupId, String name,
		String driverUrl, boolean andSearch) {

		try {
			MethodKey methodKey = new MethodKey(
				SourceServiceUtil.class, "getSourcesCount",
				_getSourcesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, driverUrl, andSearch);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.reports.engine.console.model.Source
			updateSource(
				HttpPrincipal httpPrincipal, long sourceId,
				java.util.Map<java.util.Locale, String> nameMap,
				String driverClassName, String driverUrl, String driverUserName,
				String driverPassword,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SourceServiceUtil.class, "updateSource",
				_updateSourceParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sourceId, nameMap, driverClassName, driverUrl,
				driverUserName, driverPassword, serviceContext);

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

			return (com.liferay.portal.reports.engine.console.model.Source)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SourceServiceHttp.class);

	private static final Class<?>[] _addSourceParameterTypes0 = new Class[] {
		long.class, java.util.Map.class, String.class, String.class,
		String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteSourceParameterTypes1 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getSourceParameterTypes2 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getSourcesParameterTypes3 = new Class[] {
		long.class, String.class, String.class, boolean.class, int.class,
		int.class, com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _getSourcesCountParameterTypes4 =
		new Class[] {long.class, String.class, String.class, boolean.class};
	private static final Class<?>[] _updateSourceParameterTypes5 = new Class[] {
		long.class, java.util.Map.class, String.class, String.class,
		String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};

}