/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service.http;

import com.liferay.message.boards.service.MBSuspiciousActivityServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>MBSuspiciousActivityServiceUtil</code> service
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
public class MBSuspiciousActivityServiceHttp {

	public static com.liferay.message.boards.model.MBSuspiciousActivity
			addOrUpdateMessageSuspiciousActivity(
				HttpPrincipal httpPrincipal, long messageId, String reason)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBSuspiciousActivityServiceUtil.class,
				"addOrUpdateMessageSuspiciousActivity",
				_addOrUpdateMessageSuspiciousActivityParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId, reason);

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

			return (com.liferay.message.boards.model.MBSuspiciousActivity)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.message.boards.model.MBSuspiciousActivity
			addOrUpdateThreadSuspiciousActivity(
				HttpPrincipal httpPrincipal, long threadId, String reason)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBSuspiciousActivityServiceUtil.class,
				"addOrUpdateThreadSuspiciousActivity",
				_addOrUpdateThreadSuspiciousActivityParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, threadId, reason);

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

			return (com.liferay.message.boards.model.MBSuspiciousActivity)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.message.boards.model.MBSuspiciousActivity
			deleteSuspiciousActivity(
				HttpPrincipal httpPrincipal, long suspiciousActivityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBSuspiciousActivityServiceUtil.class,
				"deleteSuspiciousActivity",
				_deleteSuspiciousActivityParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, suspiciousActivityId);

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

			return (com.liferay.message.boards.model.MBSuspiciousActivity)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.message.boards.model.MBSuspiciousActivity>
			getMessageSuspiciousActivities(
				HttpPrincipal httpPrincipal, long messageId) {

		try {
			MethodKey methodKey = new MethodKey(
				MBSuspiciousActivityServiceUtil.class,
				"getMessageSuspiciousActivities",
				_getMessageSuspiciousActivitiesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, messageId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.message.boards.model.MBSuspiciousActivity>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.message.boards.model.MBSuspiciousActivity
			getSuspiciousActivity(
				HttpPrincipal httpPrincipal, long suspiciousActivityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBSuspiciousActivityServiceUtil.class, "getSuspiciousActivity",
				_getSuspiciousActivityParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, suspiciousActivityId);

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

			return (com.liferay.message.boards.model.MBSuspiciousActivity)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.message.boards.model.MBSuspiciousActivity>
			getThreadSuspiciousActivities(
				HttpPrincipal httpPrincipal, long threadId) {

		try {
			MethodKey methodKey = new MethodKey(
				MBSuspiciousActivityServiceUtil.class,
				"getThreadSuspiciousActivities",
				_getThreadSuspiciousActivitiesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, threadId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.message.boards.model.MBSuspiciousActivity>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.message.boards.model.MBSuspiciousActivity
			updateValidated(
				HttpPrincipal httpPrincipal, long suspiciousActivityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MBSuspiciousActivityServiceUtil.class, "updateValidated",
				_updateValidatedParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, suspiciousActivityId);

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

			return (com.liferay.message.boards.model.MBSuspiciousActivity)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBSuspiciousActivityServiceHttp.class);

	private static final Class<?>[]
		_addOrUpdateMessageSuspiciousActivityParameterTypes0 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_addOrUpdateThreadSuspiciousActivityParameterTypes1 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _deleteSuspiciousActivityParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getMessageSuspiciousActivitiesParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getSuspiciousActivityParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getThreadSuspiciousActivitiesParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updateValidatedParameterTypes6 =
		new Class[] {long.class};

}