/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.service.http;

import com.liferay.commerce.notification.service.CommerceNotificationTemplateCommerceAccountGroupRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceNotificationTemplateCommerceAccountGroupRelServiceUtil</code> service
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
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceNotificationTemplateCommerceAccountGroupRelServiceHttp {

	public static com.liferay.commerce.notification.model.
		CommerceNotificationTemplateCommerceAccountGroupRel
				addCommerceNotificationTemplateCommerceAccountGroupRel(
					HttpPrincipal httpPrincipal,
					long commerceNotificationTemplateId,
					long commerceAccountGroupId,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceNotificationTemplateCommerceAccountGroupRelServiceUtil.
					class,
				"addCommerceNotificationTemplateCommerceAccountGroupRel",
				_addCommerceNotificationTemplateCommerceAccountGroupRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceNotificationTemplateId,
				commerceAccountGroupId, serviceContext);

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

			return (com.liferay.commerce.notification.model.
				CommerceNotificationTemplateCommerceAccountGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void
			deleteCommerceNotificationTemplateCommerceAccountGroupRel(
				HttpPrincipal httpPrincipal,
				long commerceNotificationTemplateCommerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceNotificationTemplateCommerceAccountGroupRelServiceUtil.
					class,
				"deleteCommerceNotificationTemplateCommerceAccountGroupRel",
				_deleteCommerceNotificationTemplateCommerceAccountGroupRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey,
				commerceNotificationTemplateCommerceAccountGroupRelId);

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

	public static com.liferay.commerce.notification.model.
		CommerceNotificationTemplateCommerceAccountGroupRel
				fetchCommerceNotificationTemplateCommerceAccountGroupRel(
					HttpPrincipal httpPrincipal,
					long commerceNotificationTemplateId,
					long commerceAccountGroupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceNotificationTemplateCommerceAccountGroupRelServiceUtil.
					class,
				"fetchCommerceNotificationTemplateCommerceAccountGroupRel",
				_fetchCommerceNotificationTemplateCommerceAccountGroupRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceNotificationTemplateId,
				commerceAccountGroupId);

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

			return (com.liferay.commerce.notification.model.
				CommerceNotificationTemplateCommerceAccountGroupRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.notification.model.
			CommerceNotificationTemplateCommerceAccountGroupRel>
					getCommerceNotificationTemplateCommerceAccountGroupRels(
						HttpPrincipal httpPrincipal,
						long commerceNotificationTemplateId, int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.notification.model.
								CommerceNotificationTemplateCommerceAccountGroupRel>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceNotificationTemplateCommerceAccountGroupRelServiceUtil.
					class,
				"getCommerceNotificationTemplateCommerceAccountGroupRels",
				_getCommerceNotificationTemplateCommerceAccountGroupRelsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceNotificationTemplateId, start, end,
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
				<com.liferay.commerce.notification.model.
					CommerceNotificationTemplateCommerceAccountGroupRel>)
						returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceNotificationTemplateCommerceAccountGroupRelServiceHttp.class);

	private static final Class<?>[]
		_addCommerceNotificationTemplateCommerceAccountGroupRelParameterTypes0 =
			new Class[] {
				long.class, long.class,
				com.liferay.portal.kernel.service.ServiceContext.class
			};
	private static final Class<?>[]
		_deleteCommerceNotificationTemplateCommerceAccountGroupRelParameterTypes1 =
			new Class[] {long.class};
	private static final Class<?>[]
		_fetchCommerceNotificationTemplateCommerceAccountGroupRelParameterTypes2 =
			new Class[] {long.class, long.class};
	private static final Class<?>[]
		_getCommerceNotificationTemplateCommerceAccountGroupRelsParameterTypes3 =
			new Class[] {
				long.class, int.class, int.class,
				com.liferay.portal.kernel.util.OrderByComparator.class
			};

}