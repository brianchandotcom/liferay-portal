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

import com.liferay.commerce.service.CPDefinitionInventoryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPDefinitionInventoryServiceUtil</code> service
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
 * @see CPDefinitionInventoryServiceSoap
 * @generated
 */
public class CPDefinitionInventoryServiceHttp {

	public static com.liferay.commerce.model.CPDefinitionInventory
			addCPDefinitionInventory(
				HttpPrincipal httpPrincipal, long cpDefinitionId,
				String cpDefinitionInventoryEngine, String lowStockActivity,
				boolean displayAvailability, boolean displayStockQuantity,
				int minStockQuantity, boolean backOrders, int minOrderQuantity,
				int maxOrderQuantity, String allowedOrderQuantities,
				int multipleOrderQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionInventoryServiceUtil.class,
				"addCPDefinitionInventory",
				_addCPDefinitionInventoryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId, cpDefinitionInventoryEngine,
				lowStockActivity, displayAvailability, displayStockQuantity,
				minStockQuantity, backOrders, minOrderQuantity,
				maxOrderQuantity, allowedOrderQuantities,
				multipleOrderQuantity);

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

			return (com.liferay.commerce.model.CPDefinitionInventory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCPDefinitionInventory(
			HttpPrincipal httpPrincipal, long cpDefinitionInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionInventoryServiceUtil.class,
				"deleteCPDefinitionInventory",
				_deleteCPDefinitionInventoryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionInventoryId);

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

	public static com.liferay.commerce.model.CPDefinitionInventory
			fetchCPDefinitionInventoryByCPDefinitionId(
				HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionInventoryServiceUtil.class,
				"fetchCPDefinitionInventoryByCPDefinitionId",
				_fetchCPDefinitionInventoryByCPDefinitionIdParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionId);

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

			return (com.liferay.commerce.model.CPDefinitionInventory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CPDefinitionInventory
			updateCPDefinitionInventory(
				HttpPrincipal httpPrincipal, long groupId,
				long cpDefinitionInventoryId,
				String cpDefinitionInventoryEngine, String lowStockActivity,
				boolean displayAvailability, boolean displayStockQuantity,
				int minStockQuantity, boolean backOrders, int minOrderQuantity,
				int maxOrderQuantity, String allowedOrderQuantities,
				int multipleOrderQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionInventoryServiceUtil.class,
				"updateCPDefinitionInventory",
				_updateCPDefinitionInventoryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, cpDefinitionInventoryId,
				cpDefinitionInventoryEngine, lowStockActivity,
				displayAvailability, displayStockQuantity, minStockQuantity,
				backOrders, minOrderQuantity, maxOrderQuantity,
				allowedOrderQuantities, multipleOrderQuantity);

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

			return (com.liferay.commerce.model.CPDefinitionInventory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CPDefinitionInventory
			updateCPDefinitionInventory(
				HttpPrincipal httpPrincipal, long cpDefinitionInventoryId,
				String cpDefinitionInventoryEngine, String lowStockActivity,
				boolean displayAvailability, boolean displayStockQuantity,
				int minStockQuantity, boolean backOrders, int minOrderQuantity,
				int maxOrderQuantity, String allowedOrderQuantities,
				int multipleOrderQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionInventoryServiceUtil.class,
				"updateCPDefinitionInventory",
				_updateCPDefinitionInventoryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpDefinitionInventoryId, cpDefinitionInventoryEngine,
				lowStockActivity, displayAvailability, displayStockQuantity,
				minStockQuantity, backOrders, minOrderQuantity,
				maxOrderQuantity, allowedOrderQuantities,
				multipleOrderQuantity);

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

			return (com.liferay.commerce.model.CPDefinitionInventory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionInventoryServiceHttp.class);

	private static final Class<?>[] _addCPDefinitionInventoryParameterTypes0 =
		new Class[] {
			long.class, String.class, String.class, boolean.class,
			boolean.class, int.class, boolean.class, int.class, int.class,
			String.class, int.class
		};
	private static final Class<?>[]
		_deleteCPDefinitionInventoryParameterTypes1 = new Class[] {long.class};
	private static final Class<?>[]
		_fetchCPDefinitionInventoryByCPDefinitionIdParameterTypes2 =
			new Class[] {long.class};
	private static final Class<?>[]
		_updateCPDefinitionInventoryParameterTypes3 = new Class[] {
			long.class, long.class, String.class, String.class, boolean.class,
			boolean.class, int.class, boolean.class, int.class, int.class,
			String.class, int.class
		};
	private static final Class<?>[]
		_updateCPDefinitionInventoryParameterTypes4 = new Class[] {
			long.class, String.class, String.class, boolean.class,
			boolean.class, int.class, boolean.class, int.class, int.class,
			String.class, int.class
		};

}