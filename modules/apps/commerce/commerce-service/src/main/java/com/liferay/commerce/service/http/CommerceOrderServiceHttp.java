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

package com.liferay.commerce.service.http;

import com.liferay.commerce.service.CommerceOrderServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceOrderServiceUtil</code> service
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
public class CommerceOrderServiceHttp {

	public static com.liferay.commerce.model.CommerceOrder addCommerceOrder(
			HttpPrincipal httpPrincipal, long groupId, long commerceAccountId,
			long commerceCurrencyId, long commerceOrderTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "addCommerceOrder",
				_addCommerceOrderParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceAccountId, commerceCurrencyId,
				commerceOrderTypeId);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			addOrUpdateCommerceOrder(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId, long commerceAccountId, long commerceCurrencyId,
				long commerceOrderTypeId, long billingAddressId,
				long shippingAddressId, String commercePaymentMethodKey,
				long commerceShippingMethodId, String shippingOptionName,
				String purchaseOrderNumber, java.math.BigDecimal subtotal,
				java.math.BigDecimal shippingAmount,
				java.math.BigDecimal taxAmount, java.math.BigDecimal total,
				java.math.BigDecimal subtotalWithTaxAmount,
				java.math.BigDecimal shippingWithTaxAmount,
				java.math.BigDecimal totalWithTaxAmount, int paymentStatus,
				int orderDateMonth, int orderDateDay, int orderDateYear,
				int orderDateHour, int orderDateMinute, int orderStatus,
				String advanceStatus,
				com.liferay.commerce.context.CommerceContext commerceContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "addOrUpdateCommerceOrder",
				_addOrUpdateCommerceOrderParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, commerceAccountId,
				commerceCurrencyId, commerceOrderTypeId, billingAddressId,
				shippingAddressId, commercePaymentMethodKey,
				commerceShippingMethodId, shippingOptionName,
				purchaseOrderNumber, subtotal, shippingAmount, taxAmount, total,
				subtotalWithTaxAmount, shippingWithTaxAmount,
				totalWithTaxAmount, paymentStatus, orderDateMonth, orderDateDay,
				orderDateYear, orderDateHour, orderDateMinute, orderStatus,
				advanceStatus, commerceContext, serviceContext);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			addOrUpdateCommerceOrder(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId, long commerceAccountId, long commerceCurrencyId,
				long commerceOrderTypeId, long billingAddressId,
				long shippingAddressId, String commercePaymentMethodKey,
				long commerceShippingMethodId, String shippingOptionName,
				String purchaseOrderNumber, java.math.BigDecimal subtotal,
				java.math.BigDecimal shippingAmount,
				java.math.BigDecimal taxAmount, java.math.BigDecimal total,
				java.math.BigDecimal subtotalWithTaxAmount,
				java.math.BigDecimal shippingWithTaxAmount,
				java.math.BigDecimal totalWithTaxAmount, int paymentStatus,
				int orderStatus, String advanceStatus,
				com.liferay.commerce.context.CommerceContext commerceContext,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "addOrUpdateCommerceOrder",
				_addOrUpdateCommerceOrderParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, commerceAccountId,
				commerceCurrencyId, commerceOrderTypeId, billingAddressId,
				shippingAddressId, commercePaymentMethodKey,
				commerceShippingMethodId, shippingOptionName,
				purchaseOrderNumber, subtotal, shippingAmount, taxAmount, total,
				subtotalWithTaxAmount, shippingWithTaxAmount,
				totalWithTaxAmount, paymentStatus, orderStatus, advanceStatus,
				commerceContext, serviceContext);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder applyCouponCode(
			HttpPrincipal httpPrincipal, long commerceOrderId,
			String couponCode,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "applyCouponCode",
				_applyCouponCodeParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, couponCode, commerceContext);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceOrder(
			HttpPrincipal httpPrincipal, long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "deleteCommerceOrder",
				_deleteCommerceOrderParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId);

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

	public static com.liferay.commerce.model.CommerceOrder
			executeWorkflowTransition(
				HttpPrincipal httpPrincipal, long commerceOrderId,
				long workflowTaskId, String transitionName, String comment)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "executeWorkflowTransition",
				_executeWorkflowTransitionParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, workflowTaskId, transitionName,
				comment);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			fetchByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "fetchByExternalReferenceCode",
				_fetchByExternalReferenceCodeParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, companyId);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder fetchCommerceOrder(
			HttpPrincipal httpPrincipal, long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "fetchCommerceOrder",
				_fetchCommerceOrderParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder fetchCommerceOrder(
			HttpPrincipal httpPrincipal, long commerceAccountId, long groupId,
			int orderStatus)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "fetchCommerceOrder",
				_fetchCommerceOrderParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, groupId, orderStatus);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder fetchCommerceOrder(
			HttpPrincipal httpPrincipal, long commerceAccountId, long groupId,
			long userId, int orderStatus)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "fetchCommerceOrder",
				_fetchCommerceOrderParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceAccountId, groupId, userId, orderStatus);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder fetchCommerceOrder(
			HttpPrincipal httpPrincipal, String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "fetchCommerceOrder",
				_fetchCommerceOrderParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, uuid, groupId);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder getCommerceOrder(
			HttpPrincipal httpPrincipal, long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getCommerceOrder",
				_getCommerceOrderParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			getCommerceOrderByUuidAndGroupId(
				HttpPrincipal httpPrincipal, String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class,
				"getCommerceOrderByUuidAndGroupId",
				_getCommerceOrderByUuidAndGroupIdParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, uuid, groupId);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrder>
			getCommerceOrders(
				HttpPrincipal httpPrincipal, long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceOrder>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getCommerceOrders",
				_getCommerceOrdersParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.model.CommerceOrder>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrder>
			getCommerceOrders(
				HttpPrincipal httpPrincipal, long groupId, int[] orderStatuses)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getCommerceOrders",
				_getCommerceOrdersParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, orderStatuses);

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

			return (java.util.List<com.liferay.commerce.model.CommerceOrder>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrder>
			getCommerceOrders(
				HttpPrincipal httpPrincipal, long groupId, int[] orderStatuses,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getCommerceOrders",
				_getCommerceOrdersParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, orderStatuses, start, end);

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

			return (java.util.List<com.liferay.commerce.model.CommerceOrder>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrder>
			getCommerceOrders(
				HttpPrincipal httpPrincipal, long groupId,
				long commerceAccountId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceOrder>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getCommerceOrders",
				_getCommerceOrdersParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceAccountId, start, end,
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

			return (java.util.List<com.liferay.commerce.model.CommerceOrder>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceOrdersCount(
			HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getCommerceOrdersCount",
				_getCommerceOrdersCountParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static int getCommerceOrdersCount(
			HttpPrincipal httpPrincipal, long groupId, long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getCommerceOrdersCount",
				_getCommerceOrdersCountParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceAccountId);

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

	public static java.util.List<com.liferay.commerce.model.CommerceOrder>
			getPendingCommerceOrders(
				HttpPrincipal httpPrincipal, long groupId,
				long commerceAccountId, String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getPendingCommerceOrders",
				_getPendingCommerceOrdersParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceAccountId, keywords, start, end);

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

			return (java.util.List<com.liferay.commerce.model.CommerceOrder>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long getPendingCommerceOrdersCount(
			HttpPrincipal httpPrincipal, long companyId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getPendingCommerceOrdersCount",
				_getPendingCommerceOrdersCountParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getPendingCommerceOrdersCount(
			HttpPrincipal httpPrincipal, long groupId, long commerceAccountId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getPendingCommerceOrdersCount",
				_getPendingCommerceOrdersCountParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceAccountId, keywords);

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

	public static java.util.List<com.liferay.commerce.model.CommerceOrder>
			getPlacedCommerceOrders(
				HttpPrincipal httpPrincipal, long companyId, long groupId,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getPlacedCommerceOrders",
				_getPlacedCommerceOrdersParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, start, end);

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

			return (java.util.List<com.liferay.commerce.model.CommerceOrder>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrder>
			getPlacedCommerceOrders(
				HttpPrincipal httpPrincipal, long groupId,
				long commerceAccountId, String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getPlacedCommerceOrders",
				_getPlacedCommerceOrdersParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceAccountId, keywords, start, end);

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

			return (java.util.List<com.liferay.commerce.model.CommerceOrder>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long getPlacedCommerceOrdersCount(
			HttpPrincipal httpPrincipal, long companyId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getPlacedCommerceOrdersCount",
				_getPlacedCommerceOrdersCountParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getPlacedCommerceOrdersCount(
			HttpPrincipal httpPrincipal, long groupId, long commerceAccountId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getPlacedCommerceOrdersCount",
				_getPlacedCommerceOrdersCountParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceAccountId, keywords);

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

	public static java.util.List<com.liferay.commerce.model.CommerceOrder>
			getUserCommerceOrders(
				HttpPrincipal httpPrincipal, long companyId, long groupId,
				String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getUserCommerceOrders",
				_getUserCommerceOrdersParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, keywords, start, end);

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

			return (java.util.List<com.liferay.commerce.model.CommerceOrder>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long getUserCommerceOrdersCount(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getUserCommerceOrdersCount",
				_getUserCommerceOrdersCountParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, keywords);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrder>
			getUserPendingCommerceOrders(
				HttpPrincipal httpPrincipal, long companyId, long groupId,
				String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getUserPendingCommerceOrders",
				_getUserPendingCommerceOrdersParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, keywords, start, end);

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

			return (java.util.List<com.liferay.commerce.model.CommerceOrder>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long getUserPendingCommerceOrdersCount(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class,
				"getUserPendingCommerceOrdersCount",
				_getUserPendingCommerceOrdersCountParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, keywords);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrder>
			getUserPlacedCommerceOrders(
				HttpPrincipal httpPrincipal, long companyId, long groupId,
				String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "getUserPlacedCommerceOrders",
				_getUserPlacedCommerceOrdersParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, keywords, start, end);

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

			return (java.util.List<com.liferay.commerce.model.CommerceOrder>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long getUserPlacedCommerceOrdersCount(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class,
				"getUserPlacedCommerceOrdersCount",
				_getUserPlacedCommerceOrdersCountParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, keywords);

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void mergeGuestCommerceOrder(
			HttpPrincipal httpPrincipal, long guestCommerceOrderId,
			long userCommerceOrderId,
			com.liferay.commerce.context.CommerceContext commerceContext,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "mergeGuestCommerceOrder",
				_mergeGuestCommerceOrderParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, guestCommerceOrderId, userCommerceOrderId,
				commerceContext, serviceContext);

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

	public static com.liferay.commerce.model.CommerceOrder recalculatePrice(
			HttpPrincipal httpPrincipal, long commerceOrderId,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "recalculatePrice",
				_recalculatePriceParameterTypes33);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, commerceContext);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder reorderCommerceOrder(
			HttpPrincipal httpPrincipal, long commerceOrderId,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "reorderCommerceOrder",
				_reorderCommerceOrderParameterTypes34);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, commerceContext);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			resetTermsAndConditions(
				HttpPrincipal httpPrincipal, long commerceOrderId,
				boolean deliveryCommerceTermEntry,
				boolean paymentCommerceTermEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "resetTermsAndConditions",
				_resetTermsAndConditionsParameterTypes35);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, deliveryCommerceTermEntry,
				paymentCommerceTermEntry);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder updateBillingAddress(
			HttpPrincipal httpPrincipal, long commerceOrderId,
			long billingAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateBillingAddress",
				_updateBillingAddressParameterTypes36);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, billingAddressId);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder updateBillingAddress(
			HttpPrincipal httpPrincipal, long commerceOrderId, String name,
			String description, String street1, String street2, String street3,
			String city, String zip, long regionId, long countryId,
			String phoneNumber,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateBillingAddress",
				_updateBillingAddressParameterTypes37);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, name, description, street1, street2,
				street3, city, zip, regionId, countryId, phoneNumber,
				serviceContext);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder updateCommerceOrder(
			HttpPrincipal httpPrincipal,
			com.liferay.commerce.model.CommerceOrder commerceOrder)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateCommerceOrder",
				_updateCommerceOrderParameterTypes38);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrder);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder updateCommerceOrder(
			HttpPrincipal httpPrincipal, String externalReferenceCode,
			long commerceOrderId, long billingAddressId, long shippingAddressId,
			String commercePaymentMethodKey, long commerceShippingMethodId,
			String shippingOptionName, String purchaseOrderNumber,
			java.math.BigDecimal subtotal, java.math.BigDecimal shippingAmount,
			java.math.BigDecimal taxAmount, java.math.BigDecimal total,
			java.math.BigDecimal subtotalWithTaxAmount,
			java.math.BigDecimal shippingWithTaxAmount,
			java.math.BigDecimal totalWithTaxAmount,
			java.math.BigDecimal totalDiscountAmount, String advanceStatus,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateCommerceOrder",
				_updateCommerceOrderParameterTypes39);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commerceOrderId,
				billingAddressId, shippingAddressId, commercePaymentMethodKey,
				commerceShippingMethodId, shippingOptionName,
				purchaseOrderNumber, subtotal, shippingAmount, taxAmount, total,
				subtotalWithTaxAmount, shippingWithTaxAmount,
				totalWithTaxAmount, totalDiscountAmount, advanceStatus,
				commerceContext);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder updateCommerceOrder(
			HttpPrincipal httpPrincipal, String externalReferenceCode,
			long commerceOrderId, long billingAddressId, long shippingAddressId,
			String commercePaymentMethodKey, long commerceShippingMethodId,
			String shippingOptionName, String purchaseOrderNumber,
			java.math.BigDecimal subtotal, java.math.BigDecimal shippingAmount,
			java.math.BigDecimal taxAmount, java.math.BigDecimal total,
			java.math.BigDecimal subtotalWithTaxAmount,
			java.math.BigDecimal shippingWithTaxAmount,
			java.math.BigDecimal totalWithTaxAmount, String advanceStatus,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateCommerceOrder",
				_updateCommerceOrderParameterTypes40);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commerceOrderId,
				billingAddressId, shippingAddressId, commercePaymentMethodKey,
				commerceShippingMethodId, shippingOptionName,
				purchaseOrderNumber, subtotal, shippingAmount, taxAmount, total,
				subtotalWithTaxAmount, shippingWithTaxAmount,
				totalWithTaxAmount, advanceStatus, commerceContext);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder updateCommerceOrder(
			HttpPrincipal httpPrincipal, String externalReferenceCode,
			long commerceOrderId, long billingAddressId, long shippingAddressId,
			String commercePaymentMethodKey, long commerceShippingMethodId,
			String shippingOptionName, String purchaseOrderNumber,
			java.math.BigDecimal subtotal, java.math.BigDecimal shippingAmount,
			java.math.BigDecimal total, String advanceStatus,
			com.liferay.commerce.context.CommerceContext commerceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateCommerceOrder",
				_updateCommerceOrderParameterTypes41);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commerceOrderId,
				billingAddressId, shippingAddressId, commercePaymentMethodKey,
				commerceShippingMethodId, shippingOptionName,
				purchaseOrderNumber, subtotal, shippingAmount, total,
				advanceStatus, commerceContext);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			updateCommerceOrderExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class,
				"updateCommerceOrderExternalReferenceCode",
				_updateCommerceOrderExternalReferenceCodeParameterTypes42);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commerceOrderId);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			updateCommerceOrderPrices(
				HttpPrincipal httpPrincipal, long commerceOrderId,
				java.math.BigDecimal subtotal,
				java.math.BigDecimal subtotalDiscountAmount,
				java.math.BigDecimal subtotalDiscountPercentageLevel1,
				java.math.BigDecimal subtotalDiscountPercentageLevel2,
				java.math.BigDecimal subtotalDiscountPercentageLevel3,
				java.math.BigDecimal subtotalDiscountPercentageLevel4,
				java.math.BigDecimal shippingAmount,
				java.math.BigDecimal shippingDiscountAmount,
				java.math.BigDecimal shippingDiscountPercentageLevel1,
				java.math.BigDecimal shippingDiscountPercentageLevel2,
				java.math.BigDecimal shippingDiscountPercentageLevel3,
				java.math.BigDecimal shippingDiscountPercentageLevel4,
				java.math.BigDecimal taxAmount, java.math.BigDecimal total,
				java.math.BigDecimal totalDiscountAmount,
				java.math.BigDecimal totalDiscountPercentageLevel1,
				java.math.BigDecimal totalDiscountPercentageLevel2,
				java.math.BigDecimal totalDiscountPercentageLevel3,
				java.math.BigDecimal totalDiscountPercentageLevel4)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateCommerceOrderPrices",
				_updateCommerceOrderPricesParameterTypes43);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, subtotal, subtotalDiscountAmount,
				subtotalDiscountPercentageLevel1,
				subtotalDiscountPercentageLevel2,
				subtotalDiscountPercentageLevel3,
				subtotalDiscountPercentageLevel4, shippingAmount,
				shippingDiscountAmount, shippingDiscountPercentageLevel1,
				shippingDiscountPercentageLevel2,
				shippingDiscountPercentageLevel3,
				shippingDiscountPercentageLevel4, taxAmount, total,
				totalDiscountAmount, totalDiscountPercentageLevel1,
				totalDiscountPercentageLevel2, totalDiscountPercentageLevel3,
				totalDiscountPercentageLevel4);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			updateCommerceOrderPrices(
				HttpPrincipal httpPrincipal, long commerceOrderId,
				java.math.BigDecimal subtotal,
				java.math.BigDecimal subtotalDiscountAmount,
				java.math.BigDecimal subtotalDiscountPercentageLevel1,
				java.math.BigDecimal subtotalDiscountPercentageLevel2,
				java.math.BigDecimal subtotalDiscountPercentageLevel3,
				java.math.BigDecimal subtotalDiscountPercentageLevel4,
				java.math.BigDecimal shippingAmount,
				java.math.BigDecimal shippingDiscountAmount,
				java.math.BigDecimal shippingDiscountPercentageLevel1,
				java.math.BigDecimal shippingDiscountPercentageLevel2,
				java.math.BigDecimal shippingDiscountPercentageLevel3,
				java.math.BigDecimal shippingDiscountPercentageLevel4,
				java.math.BigDecimal taxAmount, java.math.BigDecimal total,
				java.math.BigDecimal totalDiscountAmount,
				java.math.BigDecimal totalDiscountPercentageLevel1,
				java.math.BigDecimal totalDiscountPercentageLevel2,
				java.math.BigDecimal totalDiscountPercentageLevel3,
				java.math.BigDecimal totalDiscountPercentageLevel4,
				java.math.BigDecimal subtotalWithTaxAmount,
				java.math.BigDecimal subtotalDiscountWithTaxAmount,
				java.math.BigDecimal
					subtotalDiscountPercentageLevel1WithTaxAmount,
				java.math.BigDecimal
					subtotalDiscountPercentageLevel2WithTaxAmount,
				java.math.BigDecimal
					subtotalDiscountPercentageLevel3WithTaxAmount,
				java.math.BigDecimal
					subtotalDiscountPercentageLevel4WithTaxAmount,
				java.math.BigDecimal shippingWithTaxAmount,
				java.math.BigDecimal shippingDiscountWithTaxAmount,
				java.math.BigDecimal
					shippingDiscountPercentageLevel1WithTaxAmount,
				java.math.BigDecimal
					shippingDiscountPercentageLevel2WithTaxAmount,
				java.math.BigDecimal
					shippingDiscountPercentageLevel3WithTaxAmount,
				java.math.BigDecimal
					shippingDiscountPercentageLevel4WithTaxAmount,
				java.math.BigDecimal totalWithTaxAmount,
				java.math.BigDecimal totalDiscountWithTaxAmount,
				java.math.BigDecimal totalDiscountPercentageLevel1WithTaxAmount,
				java.math.BigDecimal totalDiscountPercentageLevel2WithTaxAmount,
				java.math.BigDecimal totalDiscountPercentageLevel3WithTaxAmount,
				java.math.BigDecimal totalDiscountPercentageLevel4WithTaxAmount)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateCommerceOrderPrices",
				_updateCommerceOrderPricesParameterTypes44);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, subtotal, subtotalDiscountAmount,
				subtotalDiscountPercentageLevel1,
				subtotalDiscountPercentageLevel2,
				subtotalDiscountPercentageLevel3,
				subtotalDiscountPercentageLevel4, shippingAmount,
				shippingDiscountAmount, shippingDiscountPercentageLevel1,
				shippingDiscountPercentageLevel2,
				shippingDiscountPercentageLevel3,
				shippingDiscountPercentageLevel4, taxAmount, total,
				totalDiscountAmount, totalDiscountPercentageLevel1,
				totalDiscountPercentageLevel2, totalDiscountPercentageLevel3,
				totalDiscountPercentageLevel4, subtotalWithTaxAmount,
				subtotalDiscountWithTaxAmount,
				subtotalDiscountPercentageLevel1WithTaxAmount,
				subtotalDiscountPercentageLevel2WithTaxAmount,
				subtotalDiscountPercentageLevel3WithTaxAmount,
				subtotalDiscountPercentageLevel4WithTaxAmount,
				shippingWithTaxAmount, shippingDiscountWithTaxAmount,
				shippingDiscountPercentageLevel1WithTaxAmount,
				shippingDiscountPercentageLevel2WithTaxAmount,
				shippingDiscountPercentageLevel3WithTaxAmount,
				shippingDiscountPercentageLevel4WithTaxAmount,
				totalWithTaxAmount, totalDiscountWithTaxAmount,
				totalDiscountPercentageLevel1WithTaxAmount,
				totalDiscountPercentageLevel2WithTaxAmount,
				totalDiscountPercentageLevel3WithTaxAmount,
				totalDiscountPercentageLevel4WithTaxAmount);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			updateCommercePaymentMethodKey(
				HttpPrincipal httpPrincipal, long commerceOrderId,
				String commercePaymentMethodKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class,
				"updateCommercePaymentMethodKey",
				_updateCommercePaymentMethodKeyParameterTypes45);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, commercePaymentMethodKey);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			updateCommerceShippingMethod(
				HttpPrincipal httpPrincipal, long commerceOrderId,
				long commerceShippingMethodId,
				String commerceShippingOptionName,
				com.liferay.commerce.context.CommerceContext commerceContext,
				java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateCommerceShippingMethod",
				_updateCommerceShippingMethodParameterTypes46);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, commerceShippingMethodId,
				commerceShippingOptionName, commerceContext, locale);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder updateCustomFields(
			HttpPrincipal httpPrincipal, long commerceOrderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateCustomFields",
				_updateCustomFieldsParameterTypes47);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, serviceContext);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder updateInfo(
			HttpPrincipal httpPrincipal, long commerceOrderId,
			String printedNote, int requestedDeliveryDateMonth,
			int requestedDeliveryDateDay, int requestedDeliveryDateYear,
			int requestedDeliveryDateHour, int requestedDeliveryDateMinute,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateInfo",
				_updateInfoParameterTypes48);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, printedNote,
				requestedDeliveryDateMonth, requestedDeliveryDateDay,
				requestedDeliveryDateYear, requestedDeliveryDateHour,
				requestedDeliveryDateMinute, serviceContext);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder updateOrderDate(
			HttpPrincipal httpPrincipal, long commerceOrderId,
			int orderDateMonth, int orderDateDay, int orderDateYear,
			int orderDateHour, int orderDateMinute,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateOrderDate",
				_updateOrderDateParameterTypes49);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, orderDateMonth, orderDateDay,
				orderDateYear, orderDateHour, orderDateMinute, serviceContext);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder updatePaymentStatus(
			HttpPrincipal httpPrincipal, long commerceOrderId,
			int paymentStatus)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updatePaymentStatus",
				_updatePaymentStatusParameterTypes50);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, paymentStatus);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			updatePaymentStatusAndTransactionId(
				HttpPrincipal httpPrincipal, long commerceOrderId,
				int paymentStatus, String transactionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class,
				"updatePaymentStatusAndTransactionId",
				_updatePaymentStatusAndTransactionIdParameterTypes51);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, paymentStatus, transactionId);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder updatePrintedNote(
			HttpPrincipal httpPrincipal, long commerceOrderId,
			String printedNote)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updatePrintedNote",
				_updatePrintedNoteParameterTypes52);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, printedNote);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			updatePurchaseOrderNumber(
				HttpPrincipal httpPrincipal, long commerceOrderId,
				String purchaseOrderNumber)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updatePurchaseOrderNumber",
				_updatePurchaseOrderNumberParameterTypes53);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, purchaseOrderNumber);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			updateShippingAddress(
				HttpPrincipal httpPrincipal, long commerceOrderId,
				long shippingAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateShippingAddress",
				_updateShippingAddressParameterTypes54);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, shippingAddressId);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			updateShippingAddress(
				HttpPrincipal httpPrincipal, long commerceOrderId, String name,
				String description, String street1, String street2,
				String street3, String city, String zip, long regionId,
				long countryId, String phoneNumber,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateShippingAddress",
				_updateShippingAddressParameterTypes55);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, name, description, street1, street2,
				street3, city, zip, regionId, countryId, phoneNumber,
				serviceContext);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder
			updateTermsAndConditions(
				HttpPrincipal httpPrincipal, long commerceOrderId,
				long deliveryCommerceTermEntryId,
				long paymentCommerceTermEntryId, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateTermsAndConditions",
				_updateTermsAndConditionsParameterTypes56);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, deliveryCommerceTermEntryId,
				paymentCommerceTermEntryId, languageId);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder updateTransactionId(
			HttpPrincipal httpPrincipal, long commerceOrderId,
			String transactionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateTransactionId",
				_updateTransactionIdParameterTypes57);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, transactionId);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceOrder updateUser(
			HttpPrincipal httpPrincipal, long commerceOrderId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceOrderServiceUtil.class, "updateUser",
				_updateUserParameterTypes58);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceOrderId, userId);

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

			return (com.liferay.commerce.model.CommerceOrder)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceOrderServiceHttp.class);

	private static final Class<?>[] _addCommerceOrderParameterTypes0 =
		new Class[] {long.class, long.class, long.class, long.class};
	private static final Class<?>[] _addOrUpdateCommerceOrderParameterTypes1 =
		new Class[] {
			String.class, long.class, long.class, long.class, long.class,
			long.class, long.class, String.class, long.class, String.class,
			String.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			String.class, com.liferay.commerce.context.CommerceContext.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addOrUpdateCommerceOrderParameterTypes2 =
		new Class[] {
			String.class, long.class, long.class, long.class, long.class,
			long.class, long.class, String.class, long.class, String.class,
			String.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class, int.class,
			int.class, String.class,
			com.liferay.commerce.context.CommerceContext.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _applyCouponCodeParameterTypes3 =
		new Class[] {
			long.class, String.class,
			com.liferay.commerce.context.CommerceContext.class
		};
	private static final Class<?>[] _deleteCommerceOrderParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _executeWorkflowTransitionParameterTypes5 =
		new Class[] {long.class, long.class, String.class, String.class};
	private static final Class<?>[]
		_fetchByExternalReferenceCodeParameterTypes6 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _fetchCommerceOrderParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchCommerceOrderParameterTypes8 =
		new Class[] {long.class, long.class, int.class};
	private static final Class<?>[] _fetchCommerceOrderParameterTypes9 =
		new Class[] {long.class, long.class, long.class, int.class};
	private static final Class<?>[] _fetchCommerceOrderParameterTypes10 =
		new Class[] {String.class, long.class};
	private static final Class<?>[] _getCommerceOrderParameterTypes11 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceOrderByUuidAndGroupIdParameterTypes12 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _getCommerceOrdersParameterTypes13 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceOrdersParameterTypes14 =
		new Class[] {long.class, int[].class};
	private static final Class<?>[] _getCommerceOrdersParameterTypes15 =
		new Class[] {long.class, int[].class, int.class, int.class};
	private static final Class<?>[] _getCommerceOrdersParameterTypes16 =
		new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceOrdersCountParameterTypes17 =
		new Class[] {long.class};
	private static final Class<?>[] _getCommerceOrdersCountParameterTypes18 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getPendingCommerceOrdersParameterTypes19 =
		new Class[] {
			long.class, long.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_getPendingCommerceOrdersCountParameterTypes20 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[]
		_getPendingCommerceOrdersCountParameterTypes21 = new Class[] {
			long.class, long.class, String.class
		};
	private static final Class<?>[] _getPlacedCommerceOrdersParameterTypes22 =
		new Class[] {long.class, long.class, int.class, int.class};
	private static final Class<?>[] _getPlacedCommerceOrdersParameterTypes23 =
		new Class[] {
			long.class, long.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_getPlacedCommerceOrdersCountParameterTypes24 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[]
		_getPlacedCommerceOrdersCountParameterTypes25 = new Class[] {
			long.class, long.class, String.class
		};
	private static final Class<?>[] _getUserCommerceOrdersParameterTypes26 =
		new Class[] {
			long.class, long.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_getUserCommerceOrdersCountParameterTypes27 = new Class[] {
			long.class, long.class, String.class
		};
	private static final Class<?>[]
		_getUserPendingCommerceOrdersParameterTypes28 = new Class[] {
			long.class, long.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_getUserPendingCommerceOrdersCountParameterTypes29 = new Class[] {
			long.class, long.class, String.class
		};
	private static final Class<?>[]
		_getUserPlacedCommerceOrdersParameterTypes30 = new Class[] {
			long.class, long.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_getUserPlacedCommerceOrdersCountParameterTypes31 = new Class[] {
			long.class, long.class, String.class
		};
	private static final Class<?>[] _mergeGuestCommerceOrderParameterTypes32 =
		new Class[] {
			long.class, long.class,
			com.liferay.commerce.context.CommerceContext.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _recalculatePriceParameterTypes33 =
		new Class[] {
			long.class, com.liferay.commerce.context.CommerceContext.class
		};
	private static final Class<?>[] _reorderCommerceOrderParameterTypes34 =
		new Class[] {
			long.class, com.liferay.commerce.context.CommerceContext.class
		};
	private static final Class<?>[] _resetTermsAndConditionsParameterTypes35 =
		new Class[] {long.class, boolean.class, boolean.class};
	private static final Class<?>[] _updateBillingAddressParameterTypes36 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _updateBillingAddressParameterTypes37 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, long.class, long.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateCommerceOrderParameterTypes38 =
		new Class[] {com.liferay.commerce.model.CommerceOrder.class};
	private static final Class<?>[] _updateCommerceOrderParameterTypes39 =
		new Class[] {
			String.class, long.class, long.class, long.class, String.class,
			long.class, String.class, String.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, String.class,
			com.liferay.commerce.context.CommerceContext.class
		};
	private static final Class<?>[] _updateCommerceOrderParameterTypes40 =
		new Class[] {
			String.class, long.class, long.class, long.class, String.class,
			long.class, String.class, String.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, com.liferay.commerce.context.CommerceContext.class
		};
	private static final Class<?>[] _updateCommerceOrderParameterTypes41 =
		new Class[] {
			String.class, long.class, long.class, long.class, String.class,
			long.class, String.class, String.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, com.liferay.commerce.context.CommerceContext.class
		};
	private static final Class<?>[]
		_updateCommerceOrderExternalReferenceCodeParameterTypes42 =
			new Class[] {String.class, long.class};
	private static final Class<?>[] _updateCommerceOrderPricesParameterTypes43 =
		new Class[] {
			long.class, java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class
		};
	private static final Class<?>[] _updateCommerceOrderPricesParameterTypes44 =
		new Class[] {
			long.class, java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class
		};
	private static final Class<?>[]
		_updateCommercePaymentMethodKeyParameterTypes45 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_updateCommerceShippingMethodParameterTypes46 = new Class[] {
			long.class, long.class, String.class,
			com.liferay.commerce.context.CommerceContext.class,
			java.util.Locale.class
		};
	private static final Class<?>[] _updateCustomFieldsParameterTypes47 =
		new Class[] {
			long.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateInfoParameterTypes48 = new Class[] {
		long.class, String.class, int.class, int.class, int.class, int.class,
		int.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _updateOrderDateParameterTypes49 =
		new Class[] {
			long.class, int.class, int.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updatePaymentStatusParameterTypes50 =
		new Class[] {long.class, int.class};
	private static final Class<?>[]
		_updatePaymentStatusAndTransactionIdParameterTypes51 = new Class[] {
			long.class, int.class, String.class
		};
	private static final Class<?>[] _updatePrintedNoteParameterTypes52 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updatePurchaseOrderNumberParameterTypes53 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateShippingAddressParameterTypes54 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _updateShippingAddressParameterTypes55 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, long.class, long.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateTermsAndConditionsParameterTypes56 =
		new Class[] {long.class, long.class, long.class, String.class};
	private static final Class<?>[] _updateTransactionIdParameterTypes57 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateUserParameterTypes58 = new Class[] {
		long.class, long.class
	};

}