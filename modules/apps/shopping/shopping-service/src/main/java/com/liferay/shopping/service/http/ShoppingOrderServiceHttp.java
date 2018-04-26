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

package com.liferay.shopping.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import com.liferay.shopping.service.ShoppingOrderServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link ShoppingOrderServiceUtil} service utility. The
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
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderServiceSoap
 * @see HttpPrincipal
 * @see ShoppingOrderServiceUtil
 * @generated
 */
@ProviderType
public class ShoppingOrderServiceHttp {
	public static void completeOrder(HttpPrincipal httpPrincipal, long groupId,
		String number, String ppTxnId, String ppPaymentStatus,
		double ppPaymentGross, String ppReceiverEmail, String ppPayerEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingOrderServiceUtil.class,
					"completeOrder", _completeOrderParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					number, ppTxnId, ppPaymentStatus, ppPaymentGross,
					ppReceiverEmail, ppPayerEmail, serviceContext);

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

	public static void deleteOrder(HttpPrincipal httpPrincipal, long groupId,
		long orderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingOrderServiceUtil.class,
					"deleteOrder", _deleteOrderParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					orderId);

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

	public static com.liferay.shopping.model.ShoppingOrder getOrder(
		HttpPrincipal httpPrincipal, long groupId, long orderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingOrderServiceUtil.class,
					"getOrder", _getOrderParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					orderId);

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

			return (com.liferay.shopping.model.ShoppingOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void sendEmail(HttpPrincipal httpPrincipal, long groupId,
		long orderId, String emailType,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingOrderServiceUtil.class,
					"sendEmail", _sendEmailParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					orderId, emailType, serviceContext);

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

	public static com.liferay.shopping.model.ShoppingOrder updateOrder(
		HttpPrincipal httpPrincipal, long groupId, long orderId,
		String ppTxnId, String ppPaymentStatus, double ppPaymentGross,
		String ppReceiverEmail, String ppPayerEmail)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingOrderServiceUtil.class,
					"updateOrder", _updateOrderParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					orderId, ppTxnId, ppPaymentStatus, ppPaymentGross,
					ppReceiverEmail, ppPayerEmail);

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

			return (com.liferay.shopping.model.ShoppingOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.shopping.model.ShoppingOrder updateOrder(
		HttpPrincipal httpPrincipal, long groupId, long orderId,
		String billingFirstName, String billingLastName,
		String billingEmailAddress, String billingCompany,
		String billingStreet, String billingCity, String billingState,
		String billingZip, String billingCountry, String billingPhone,
		boolean shipToBilling, String shippingFirstName,
		String shippingLastName, String shippingEmailAddress,
		String shippingCompany, String shippingStreet, String shippingCity,
		String shippingState, String shippingZip, String shippingCountry,
		String shippingPhone, String ccName, String ccType, String ccNumber,
		int ccExpMonth, int ccExpYear, String ccVerNumber, String comments)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingOrderServiceUtil.class,
					"updateOrder", _updateOrderParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					orderId, billingFirstName, billingLastName,
					billingEmailAddress, billingCompany, billingStreet,
					billingCity, billingState, billingZip, billingCountry,
					billingPhone, shipToBilling, shippingFirstName,
					shippingLastName, shippingEmailAddress, shippingCompany,
					shippingStreet, shippingCity, shippingState, shippingZip,
					shippingCountry, shippingPhone, ccName, ccType, ccNumber,
					ccExpMonth, ccExpYear, ccVerNumber, comments);

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

			return (com.liferay.shopping.model.ShoppingOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingOrderServiceHttp.class);
	private static final Class<?>[] _completeOrderParameterTypes0 = new Class[] {
			long.class, String.class, String.class, String.class, double.class,
			String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteOrderParameterTypes1 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getOrderParameterTypes2 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _sendEmailParameterTypes3 = new Class[] {
			long.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateOrderParameterTypes4 = new Class[] {
			long.class, long.class, String.class, String.class, double.class,
			String.class, String.class
		};
	private static final Class<?>[] _updateOrderParameterTypes5 = new Class[] {
			long.class, long.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class, String.class,
			String.class, String.class, boolean.class, String.class,
			String.class, String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class, String.class,
			String.class, String.class, int.class, int.class, String.class,
			String.class
		};
}