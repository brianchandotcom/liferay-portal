/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.audit.storage.service.AuditEventServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>AuditEventServiceUtil</code> service
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
public class AuditEventServiceHttp {

	public static java.util.List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
				getAuditEvents(
					HttpPrincipal httpPrincipal, long companyId, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AuditEventServiceUtil.class, "getAuditEvents",
				_getAuditEventsParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end);

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
				<com.liferay.portal.security.audit.storage.model.AuditEvent>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
				getAuditEvents(
					HttpPrincipal httpPrincipal, long companyId, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.security.audit.storage.model.
							AuditEvent> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AuditEventServiceUtil.class, "getAuditEvents",
				_getAuditEventsParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end, orderByComparator);

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
				<com.liferay.portal.security.audit.storage.model.AuditEvent>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
				getAuditEvents(
					HttpPrincipal httpPrincipal, long companyId, long groupId,
					long userId, String userName, java.util.Date createDateGT,
					java.util.Date createDateLT, String eventType,
					String className, String classPK, String clientHost,
					String clientIP, String serverName, int serverPort,
					String sessionID, boolean andSearch, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AuditEventServiceUtil.class, "getAuditEvents",
				_getAuditEventsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, userId, userName, createDateGT,
				createDateLT, eventType, className, classPK, clientHost,
				clientIP, serverName, serverPort, sessionID, andSearch, start,
				end);

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
				<com.liferay.portal.security.audit.storage.model.AuditEvent>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
				getAuditEvents(
					HttpPrincipal httpPrincipal, long companyId, long groupId,
					long userId, String userName, java.util.Date createDateGT,
					java.util.Date createDateLT, String eventType,
					String className, String classPK, String clientHost,
					String clientIP, String serverName, int serverPort,
					String sessionID, boolean andSearch, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.security.audit.storage.model.
							AuditEvent> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AuditEventServiceUtil.class, "getAuditEvents",
				_getAuditEventsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, userId, userName, createDateGT,
				createDateLT, eventType, className, classPK, clientHost,
				clientIP, serverName, serverPort, sessionID, andSearch, start,
				end, orderByComparator);

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
				<com.liferay.portal.security.audit.storage.model.AuditEvent>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getAuditEventsCount(
			HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AuditEventServiceUtil.class, "getAuditEventsCount",
				_getAuditEventsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getAuditEventsCount(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			long userId, String userName, java.util.Date createDateGT,
			java.util.Date createDateLT, String eventType, String className,
			String classPK, String clientHost, String clientIP,
			String serverName, int serverPort, String sessionID,
			boolean andSearch)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AuditEventServiceUtil.class, "getAuditEventsCount",
				_getAuditEventsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, userId, userName, createDateGT,
				createDateLT, eventType, className, classPK, clientHost,
				clientIP, serverName, serverPort, sessionID, andSearch);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AuditEventServiceHttp.class);

	private static final Class<?>[] _getAuditEventsParameterTypes0 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getAuditEventsParameterTypes1 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getAuditEventsParameterTypes2 =
		new Class[] {
			long.class, long.class, long.class, String.class,
			java.util.Date.class, java.util.Date.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class, int.class, String.class, boolean.class, int.class,
			int.class
		};
	private static final Class<?>[] _getAuditEventsParameterTypes3 =
		new Class[] {
			long.class, long.class, long.class, String.class,
			java.util.Date.class, java.util.Date.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class, int.class, String.class, boolean.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getAuditEventsCountParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _getAuditEventsCountParameterTypes5 =
		new Class[] {
			long.class, long.class, long.class, String.class,
			java.util.Date.class, java.util.Date.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class, int.class, String.class, boolean.class
		};

}