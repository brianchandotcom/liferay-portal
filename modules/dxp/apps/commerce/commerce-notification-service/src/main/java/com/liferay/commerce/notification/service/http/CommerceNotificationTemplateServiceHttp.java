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

package com.liferay.commerce.notification.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.service.CommerceNotificationTemplateServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceNotificationTemplateServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
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
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateServiceSoap
 * @see HttpPrincipal
 * @see CommerceNotificationTemplateServiceUtil
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateServiceHttp {
	public static com.liferay.commerce.notification.model.CommerceNotificationTemplate addCommerceNotificationTemplate(
		HttpPrincipal httpPrincipal, String name, String description,
		String cc, String ccn, String type, boolean enabled,
		java.util.Map<java.util.Locale, String> subjectMap,
		java.util.Map<java.util.Locale, String> bodyMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceNotificationTemplateServiceUtil.class,
					"addCommerceNotificationTemplate",
					_addCommerceNotificationTemplateParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, name,
					description, cc, ccn, type, enabled, subjectMap, bodyMap,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.notification.model.CommerceNotificationTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceNotificationTemplate(
		HttpPrincipal httpPrincipal, long commerceNotificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceNotificationTemplateServiceUtil.class,
					"deleteCommerceNotificationTemplate",
					_deleteCommerceNotificationTemplateParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceNotificationTemplateId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationTemplate getCommerceNotificationTemplate(
		HttpPrincipal httpPrincipal, long commerceNotificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceNotificationTemplateServiceUtil.class,
					"getCommerceNotificationTemplate",
					_getCommerceNotificationTemplateParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceNotificationTemplateId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.notification.model.CommerceNotificationTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.notification.model.CommerceNotificationTemplate> getCommerceNotificationTemplates(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.notification.model.CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceNotificationTemplateServiceUtil.class,
					"getCommerceNotificationTemplates",
					_getCommerceNotificationTemplatesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.notification.model.CommerceNotificationTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceNotificationTemplatesCount(
		HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceNotificationTemplateServiceUtil.class,
					"getCommerceNotificationTemplatesCount",
					_getCommerceNotificationTemplatesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationTemplate updateCommerceNotificationTemplate(
		HttpPrincipal httpPrincipal, long commerceNotificationTemplateId,
		String name, String description, String cc, String ccn, String type,
		boolean enabled, java.util.Map<java.util.Locale, String> subjectMap,
		java.util.Map<java.util.Locale, String> bodyMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceNotificationTemplateServiceUtil.class,
					"updateCommerceNotificationTemplate",
					_updateCommerceNotificationTemplateParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceNotificationTemplateId, name, description, cc, ccn,
					type, enabled, subjectMap, bodyMap, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.notification.model.CommerceNotificationTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceNotificationTemplateServiceHttp.class);
	private static final Class<?>[] _addCommerceNotificationTemplateParameterTypes0 =
		new Class[] {
			String.class, String.class, String.class, String.class, String.class,
			boolean.class, java.util.Map.class, java.util.Map.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceNotificationTemplateParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceNotificationTemplateParameterTypes2 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceNotificationTemplatesParameterTypes3 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceNotificationTemplatesCountParameterTypes4 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCommerceNotificationTemplateParameterTypes5 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, boolean.class, java.util.Map.class,
			java.util.Map.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}