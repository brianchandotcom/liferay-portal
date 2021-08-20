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

package com.liferay.commerce.inventory.service.http;

import com.liferay.commerce.inventory.service.CommerceInventoryReplenishmentItemServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceInventoryReplenishmentItemServiceUtil</code> service
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
 * @author Luca Pellizzon
 * @see CommerceInventoryReplenishmentItemServiceSoap
 * @generated
 */
public class CommerceInventoryReplenishmentItemServiceHttp {

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem
				addCommerceInventoryReplenishmentItem(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseId, String sku,
					java.util.Date availabilityDate, int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"addCommerceInventoryReplenishmentItem",
				_addCommerceInventoryReplenishmentItemParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, sku, availabilityDate,
				quantity);

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

			return (com.liferay.commerce.inventory.model.
				CommerceInventoryReplenishmentItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceInventoryReplenishmentItem(
			HttpPrincipal httpPrincipal,
			long commerceInventoryReplenishmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"deleteCommerceInventoryReplenishmentItem",
				_deleteCommerceInventoryReplenishmentItemParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryReplenishmentItemId);

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

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem
				getCommerceInventoryReplenishmentItem(
					HttpPrincipal httpPrincipal,
					long commerceInventoryReplenishmentItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"getCommerceInventoryReplenishmentItem",
				_getCommerceInventoryReplenishmentItemParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryReplenishmentItemId);

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

			return (com.liferay.commerce.inventory.model.
				CommerceInventoryReplenishmentItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.
			CommerceInventoryReplenishmentItem>
					getCommerceInventoryReplenishmentItemsByCompanyIdAndSku(
						HttpPrincipal httpPrincipal, long companyId, String sku,
						int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"getCommerceInventoryReplenishmentItemsByCompanyIdAndSku",
				_getCommerceInventoryReplenishmentItemsByCompanyIdAndSkuParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sku, start, end);

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
				<com.liferay.commerce.inventory.model.
					CommerceInventoryReplenishmentItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long getCommerceInventoryReplenishmentItemsCount(
			HttpPrincipal httpPrincipal, long commerceInventoryWarehouseId,
			String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"getCommerceInventoryReplenishmentItemsCount",
				_getCommerceInventoryReplenishmentItemsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, sku);

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

	public static int
			getCommerceInventoryReplenishmentItemsCountByCompanyIdAndSku(
				HttpPrincipal httpPrincipal, long companyId, String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"getCommerceInventoryReplenishmentItemsCountByCompanyIdAndSku",
				_getCommerceInventoryReplenishmentItemsCountByCompanyIdAndSkuParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sku);

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

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem
				updateCommerceInventoryReplenishmentItem(
					HttpPrincipal httpPrincipal,
					long commerceInventoryReplenishmentItemId,
					java.util.Date availabilityDate, int quantity,
					long mvccVersion)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"updateCommerceInventoryReplenishmentItem",
				_updateCommerceInventoryReplenishmentItemParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryReplenishmentItemId,
				availabilityDate, quantity, mvccVersion);

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

			return (com.liferay.commerce.inventory.model.
				CommerceInventoryReplenishmentItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceInventoryReplenishmentItemServiceHttp.class);

	private static final Class<?>[]
		_addCommerceInventoryReplenishmentItemParameterTypes0 = new Class[] {
			long.class, String.class, java.util.Date.class, int.class
		};
	private static final Class<?>[]
		_deleteCommerceInventoryReplenishmentItemParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceInventoryReplenishmentItemParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceInventoryReplenishmentItemsByCompanyIdAndSkuParameterTypes3 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceInventoryReplenishmentItemsCountParameterTypes4 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getCommerceInventoryReplenishmentItemsCountByCompanyIdAndSkuParameterTypes5 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_updateCommerceInventoryReplenishmentItemParameterTypes6 = new Class[] {
			long.class, java.util.Date.class, int.class, long.class
		};

}