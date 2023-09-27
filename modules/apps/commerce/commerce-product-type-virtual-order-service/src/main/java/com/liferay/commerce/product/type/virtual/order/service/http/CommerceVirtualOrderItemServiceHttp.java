/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.service.http;

import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceVirtualOrderItemServiceUtil</code> service
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
public class CommerceVirtualOrderItemServiceHttp {

	public static com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem fetchCommerceVirtualOrderItem(
				HttpPrincipal httpPrincipal, long commerceVirtualOrderItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceVirtualOrderItemServiceUtil.class,
				"fetchCommerceVirtualOrderItem",
				_fetchCommerceVirtualOrderItemParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceVirtualOrderItemId);

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

			return (com.liferay.commerce.product.type.virtual.order.model.
				CommerceVirtualOrderItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem
				fetchCommerceVirtualOrderItemByCommerceOrderItemId(
					HttpPrincipal httpPrincipal, long commerceOrderItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceVirtualOrderItemServiceUtil.class,
				"fetchCommerceVirtualOrderItemByCommerceOrderItemId",
				_fetchCommerceVirtualOrderItemByCommerceOrderItemIdParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderItemId);

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

			return (com.liferay.commerce.product.type.virtual.order.model.
				CommerceVirtualOrderItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.io.File getFile(
			HttpPrincipal httpPrincipal, long commerceVirtualOrderItemId)
		throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceVirtualOrderItemServiceUtil.class, "getFile",
				_getFileParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceVirtualOrderItemId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof Exception) {
					throw (Exception)exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.io.File)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
				HttpPrincipal httpPrincipal, long commerceVirtualOrderItemId,
				long fileEntryId, String url, int activationStatus,
				long duration, int usages, int maxUsages, boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceVirtualOrderItemServiceUtil.class,
				"updateCommerceVirtualOrderItem",
				_updateCommerceVirtualOrderItemParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceVirtualOrderItemId, fileEntryId, url,
				activationStatus, duration, usages, maxUsages, active);

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

			return (com.liferay.commerce.product.type.virtual.order.model.
				CommerceVirtualOrderItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceVirtualOrderItemServiceHttp.class);

	private static final Class<?>[]
		_fetchCommerceVirtualOrderItemParameterTypes0 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCommerceVirtualOrderItemByCommerceOrderItemIdParameterTypes1 =
			new Class[] {long.class};
	private static final Class<?>[] _getFileParameterTypes2 = new Class[] {
		long.class
	};
	private static final Class<?>[]
		_updateCommerceVirtualOrderItemParameterTypes3 = new Class[] {
			long.class, long.class, String.class, int.class, long.class,
			int.class, int.class, boolean.class
		};

}