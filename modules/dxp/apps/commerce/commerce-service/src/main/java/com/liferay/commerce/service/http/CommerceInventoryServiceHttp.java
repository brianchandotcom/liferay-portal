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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceInventoryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceInventoryServiceUtil} service utility. The
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
 * @see CommerceInventoryServiceSoap
 * @see HttpPrincipal
 * @see CommerceInventoryServiceUtil
 * @generated
 */
@ProviderType
public class CommerceInventoryServiceHttp {
	public static com.liferay.commerce.model.CommerceInventory addCommerceInventory(
		HttpPrincipal httpPrincipal, long cpDefinitionId,
		java.lang.String commerceInventoryEngine,
		java.lang.String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minCartQuantity, int maxCartQuantity,
		java.lang.String allowedCartQuantities, int multipleCartQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceInventoryServiceUtil.class,
					"addCommerceInventory", _addCommerceInventoryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, commerceInventoryEngine, lowStockActivity,
					displayAvailability, displayStockQuantity,
					minStockQuantity, backOrders, minCartQuantity,
					maxCartQuantity, allowedCartQuantities,
					multipleCartQuantity, serviceContext);

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

			return (com.liferay.commerce.model.CommerceInventory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceInventory(HttpPrincipal httpPrincipal,
		long commerceInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceInventoryServiceUtil.class,
					"deleteCommerceInventory",
					_deleteCommerceInventoryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceInventoryId);

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

	public static com.liferay.commerce.model.CommerceInventory fetchCommerceInventory(
		HttpPrincipal httpPrincipal, long commerceInventoryId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceInventoryServiceUtil.class,
					"fetchCommerceInventory",
					_fetchCommerceInventoryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceInventoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceInventory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceInventory fetchCommerceInventoryByCPDefinitionId(
		HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceInventoryServiceUtil.class,
					"fetchCommerceInventoryByCPDefinitionId",
					_fetchCommerceInventoryByCPDefinitionIdParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId);

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

			return (com.liferay.commerce.model.CommerceInventory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceInventory updateCommerceInventory(
		HttpPrincipal httpPrincipal, long commerceInventoryId,
		java.lang.String commerceInventoryEngine,
		java.lang.String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minCartQuantity, int maxCartQuantity,
		java.lang.String allowedCartQuantities, int multipleCartQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceInventoryServiceUtil.class,
					"updateCommerceInventory",
					_updateCommerceInventoryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceInventoryId, commerceInventoryEngine,
					lowStockActivity, displayAvailability,
					displayStockQuantity, minStockQuantity, backOrders,
					minCartQuantity, maxCartQuantity, allowedCartQuantities,
					multipleCartQuantity, serviceContext);

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

			return (com.liferay.commerce.model.CommerceInventory)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceInventoryServiceHttp.class);
	private static final Class<?>[] _addCommerceInventoryParameterTypes0 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			boolean.class, boolean.class, int.class, boolean.class, int.class,
			int.class, java.lang.String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceInventoryParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommerceInventoryParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommerceInventoryByCPDefinitionIdParameterTypes3 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCommerceInventoryParameterTypes4 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			boolean.class, boolean.class, int.class, boolean.class, int.class,
			int.class, java.lang.String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}