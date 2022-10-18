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

package com.liferay.dispatch.service.http;

import com.liferay.dispatch.service.DispatchTriggerServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DispatchTriggerServiceUtil</code> service
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
 * @author Matija Petanjek
 * @generated
 */
public class DispatchTriggerServiceHttp {

	public static com.liferay.dispatch.model.DispatchTrigger addDispatchTrigger(
			HttpPrincipal httpPrincipal, String externalReferenceCode,
			long userId, String dispatchTaskExecutorType,
			com.liferay.portal.kernel.util.UnicodeProperties
				dispatchTaskSettingsUnicodeProperties,
			String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DispatchTriggerServiceUtil.class, "addDispatchTrigger",
				_addDispatchTriggerParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, userId,
				dispatchTaskExecutorType, dispatchTaskSettingsUnicodeProperties,
				name);

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

			return (com.liferay.dispatch.model.DispatchTrigger)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteDispatchTrigger(
			HttpPrincipal httpPrincipal, long dispatchTriggerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DispatchTriggerServiceUtil.class, "deleteDispatchTrigger",
				_deleteDispatchTriggerParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, dispatchTriggerId);

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

	public static com.liferay.dispatch.model.DispatchTrigger getDispatchTrigger(
			HttpPrincipal httpPrincipal, long dispatchTriggerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DispatchTriggerServiceUtil.class, "getDispatchTrigger",
				_getDispatchTriggerParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, dispatchTriggerId);

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

			return (com.liferay.dispatch.model.DispatchTrigger)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.dispatch.model.DispatchTrigger>
			getDispatchTriggers(HttpPrincipal httpPrincipal, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DispatchTriggerServiceUtil.class, "getDispatchTriggers",
				_getDispatchTriggersParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, start, end);

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

			return (java.util.List<com.liferay.dispatch.model.DispatchTrigger>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getDispatchTriggersCount(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DispatchTriggerServiceUtil.class, "getDispatchTriggersCount",
				_getDispatchTriggersCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey);

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

	public static com.liferay.dispatch.model.DispatchTrigger
			updateDispatchTrigger(
				HttpPrincipal httpPrincipal, long dispatchTriggerId,
				boolean active, String cronExpression,
				com.liferay.dispatch.executor.DispatchTaskClusterMode
					dispatchTaskClusterMode,
				int endDateMonth, int endDateDay, int endDateYear,
				int endDateHour, int endDateMinute, boolean neverEnd,
				boolean overlapAllowed, int startDateMonth, int startDateDay,
				int startDateYear, int startDateHour, int startDateMinute,
				String timeZoneId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DispatchTriggerServiceUtil.class, "updateDispatchTrigger",
				_updateDispatchTriggerParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, dispatchTriggerId, active, cronExpression,
				dispatchTaskClusterMode, endDateMonth, endDateDay, endDateYear,
				endDateHour, endDateMinute, neverEnd, overlapAllowed,
				startDateMonth, startDateDay, startDateYear, startDateHour,
				startDateMinute, timeZoneId);

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

			return (com.liferay.dispatch.model.DispatchTrigger)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dispatch.model.DispatchTrigger
			updateDispatchTrigger(
				HttpPrincipal httpPrincipal, long dispatchTriggerId,
				com.liferay.portal.kernel.util.UnicodeProperties
					dispatchTaskSettingsUnicodeProperties,
				String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DispatchTriggerServiceUtil.class, "updateDispatchTrigger",
				_updateDispatchTriggerParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, dispatchTriggerId,
				dispatchTaskSettingsUnicodeProperties, name);

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

			return (com.liferay.dispatch.model.DispatchTrigger)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DispatchTriggerServiceHttp.class);

	private static final Class<?>[] _addDispatchTriggerParameterTypes0 =
		new Class[] {
			String.class, long.class, String.class,
			com.liferay.portal.kernel.util.UnicodeProperties.class, String.class
		};
	private static final Class<?>[] _deleteDispatchTriggerParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getDispatchTriggerParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getDispatchTriggersParameterTypes3 =
		new Class[] {int.class, int.class};
	private static final Class<?>[] _getDispatchTriggersCountParameterTypes4 =
		new Class[] {};
	private static final Class<?>[] _updateDispatchTriggerParameterTypes5 =
		new Class[] {
			long.class, boolean.class, String.class,
			com.liferay.dispatch.executor.DispatchTaskClusterMode.class,
			int.class, int.class, int.class, int.class, int.class,
			boolean.class, boolean.class, int.class, int.class, int.class,
			int.class, int.class, String.class
		};
	private static final Class<?>[] _updateDispatchTriggerParameterTypes6 =
		new Class[] {
			long.class, com.liferay.portal.kernel.util.UnicodeProperties.class,
			String.class
		};

}