/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @generated
 */
public class CPDefinitionInventoryServiceHttp {

	public static com.liferay.commerce.model.CPDefinitionInventory
			addCPDefinitionInventory(
				HttpPrincipal httpPrincipal, long cpDefinitionId,
				String cpDefinitionInventoryEngine, String lowStockActivity,
				boolean displayAvailability, boolean displayStockQuantity,
				java.math.BigDecimal minStockQuantity, boolean backOrders,
				java.math.BigDecimal minOrderQuantity,
				java.math.BigDecimal maxOrderQuantity,
				String allowedOrderQuantities,
				java.math.BigDecimal multipleOrderQuantity)
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
				HttpPrincipal httpPrincipal, long cpDefinitionInventoryId,
				String cpDefinitionInventoryEngine, String lowStockActivity,
				boolean displayAvailability, boolean displayStockQuantity,
				java.math.BigDecimal minStockQuantity, boolean backOrders,
				java.math.BigDecimal minOrderQuantity,
				java.math.BigDecimal maxOrderQuantity,
				String allowedOrderQuantities,
				java.math.BigDecimal multipleOrderQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPDefinitionInventoryServiceUtil.class,
				"updateCPDefinitionInventory",
				_updateCPDefinitionInventoryParameterTypes3);

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
			boolean.class, java.math.BigDecimal.class, boolean.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, java.math.BigDecimal.class
		};
	private static final Class<?>[]
		_deleteCPDefinitionInventoryParameterTypes1 = new Class[] {long.class};
	private static final Class<?>[]
		_fetchCPDefinitionInventoryByCPDefinitionIdParameterTypes2 =
			new Class[] {long.class};
	private static final Class<?>[]
		_updateCPDefinitionInventoryParameterTypes3 = new Class[] {
			long.class, String.class, String.class, boolean.class,
			boolean.class, java.math.BigDecimal.class, boolean.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, java.math.BigDecimal.class
		};

}