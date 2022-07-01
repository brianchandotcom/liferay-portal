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

package com.liferay.notification.service.http;

import com.liferay.notification.service.NotificationTemplateServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>NotificationTemplateServiceUtil</code> service
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
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationTemplateServiceHttp {

	public static com.liferay.notification.model.NotificationTemplate
			addNotificationTemplate(
				HttpPrincipal httpPrincipal, long userId,
				long objectDefinitionId, String bcc,
				java.util.Map<java.util.Locale, String> bodyMap, String cc,
				String description, String from,
				java.util.Map<java.util.Locale, String> fromNameMap,
				String name, java.util.Map<java.util.Locale, String> subjectMap,
				java.util.Map<java.util.Locale, String> toMap,
				java.util.List<Long> attachmentObjectFieldIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				NotificationTemplateServiceUtil.class,
				"addNotificationTemplate",
				_addNotificationTemplateParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, objectDefinitionId, bcc, bodyMap, cc,
				description, from, fromNameMap, name, subjectMap, toMap,
				attachmentObjectFieldIds);

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

			return (com.liferay.notification.model.NotificationTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.notification.model.NotificationTemplate
			deleteNotificationTemplate(
				HttpPrincipal httpPrincipal, long notificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				NotificationTemplateServiceUtil.class,
				"deleteNotificationTemplate",
				_deleteNotificationTemplateParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, notificationTemplateId);

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

			return (com.liferay.notification.model.NotificationTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.notification.model.NotificationTemplate
			deleteNotificationTemplate(
				HttpPrincipal httpPrincipal,
				com.liferay.notification.model.NotificationTemplate
					notificationTemplate)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				NotificationTemplateServiceUtil.class,
				"deleteNotificationTemplate",
				_deleteNotificationTemplateParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, notificationTemplate);

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

			return (com.liferay.notification.model.NotificationTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.notification.model.NotificationTemplate
			getNotificationTemplate(
				HttpPrincipal httpPrincipal, long notificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				NotificationTemplateServiceUtil.class,
				"getNotificationTemplate",
				_getNotificationTemplateParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, notificationTemplateId);

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

			return (com.liferay.notification.model.NotificationTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.notification.model.NotificationTemplate
			updateNotificationTemplate(
				HttpPrincipal httpPrincipal, long notificationTemplateId,
				long objectDefinitionId, String bcc,
				java.util.Map<java.util.Locale, String> bodyMap, String cc,
				String description, String from,
				java.util.Map<java.util.Locale, String> fromNameMap,
				String name, java.util.Map<java.util.Locale, String> subjectMap,
				java.util.Map<java.util.Locale, String> toMap,
				java.util.List<Long> attachmentObjectFieldIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				NotificationTemplateServiceUtil.class,
				"updateNotificationTemplate",
				_updateNotificationTemplateParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, notificationTemplateId, objectDefinitionId, bcc,
				bodyMap, cc, description, from, fromNameMap, name, subjectMap,
				toMap, attachmentObjectFieldIds);

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

			return (com.liferay.notification.model.NotificationTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		NotificationTemplateServiceHttp.class);

	private static final Class<?>[] _addNotificationTemplateParameterTypes0 =
		new Class[] {
			long.class, long.class, String.class, java.util.Map.class,
			String.class, String.class, String.class, java.util.Map.class,
			String.class, java.util.Map.class, java.util.Map.class,
			java.util.List.class
		};
	private static final Class<?>[] _deleteNotificationTemplateParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteNotificationTemplateParameterTypes2 =
		new Class[] {com.liferay.notification.model.NotificationTemplate.class};
	private static final Class<?>[] _getNotificationTemplateParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _updateNotificationTemplateParameterTypes4 =
		new Class[] {
			long.class, long.class, String.class, java.util.Map.class,
			String.class, String.class, String.class, java.util.Map.class,
			String.class, java.util.Map.class, java.util.Map.class,
			java.util.List.class
		};

}