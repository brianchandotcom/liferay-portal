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

import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceInventoryWarehouseItemServiceUtil</code> service
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
 * @see CommerceInventoryWarehouseItemServiceSoap
 * @generated
 */
public class CommerceInventoryWarehouseItemServiceHttp {

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				addCommerceInventoryWarehouseItem(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseId, String sku, int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"addCommerceInventoryWarehouseItem",
				_addCommerceInventoryWarehouseItemParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, sku, quantity);

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
				CommerceInventoryWarehouseItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				addCommerceInventoryWarehouseItem(
					HttpPrincipal httpPrincipal, String externalReferenceCode,
					long commerceInventoryWarehouseId, String sku, int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"addCommerceInventoryWarehouseItem",
				_addCommerceInventoryWarehouseItemParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commerceInventoryWarehouseId,
				sku, quantity);

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
				CommerceInventoryWarehouseItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				addOrUpdateCommerceInventoryWarehouseItem(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseId, String sku, int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"addOrUpdateCommerceInventoryWarehouseItem",
				_addOrUpdateCommerceInventoryWarehouseItemParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, sku, quantity);

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
				CommerceInventoryWarehouseItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				addOrUpdateCommerceInventoryWarehouseItem(
					HttpPrincipal httpPrincipal, String externalReferenceCode,
					long companyId, long commerceInventoryWarehouseId,
					String sku, int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"addOrUpdateCommerceInventoryWarehouseItem",
				_addOrUpdateCommerceInventoryWarehouseItemParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, companyId,
				commerceInventoryWarehouseId, sku, quantity);

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
				CommerceInventoryWarehouseItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceInventoryWarehouseItem(
			HttpPrincipal httpPrincipal, long commerceInventoryWarehouseItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"deleteCommerceInventoryWarehouseItem",
				_deleteCommerceInventoryWarehouseItemParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseItemId);

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

	public static void deleteCommerceInventoryWarehouseItems(
			HttpPrincipal httpPrincipal, long companyId, String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"deleteCommerceInventoryWarehouseItems",
				_deleteCommerceInventoryWarehouseItemsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sku);

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
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				fetchCommerceInventoryWarehouseItem(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseId, String sku)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"fetchCommerceInventoryWarehouseItem",
				_fetchCommerceInventoryWarehouseItemParameterTypes6);

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

			return (com.liferay.commerce.inventory.model.
				CommerceInventoryWarehouseItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				fetchCommerceInventoryWarehouseItemByExternalReferenceCode(
					HttpPrincipal httpPrincipal, String externalReferenceCode,
					long companyId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"fetchCommerceInventoryWarehouseItemByExternalReferenceCode",
				_fetchCommerceInventoryWarehouseItemByExternalReferenceCodeParameterTypes7);

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

			return (com.liferay.commerce.inventory.model.
				CommerceInventoryWarehouseItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				getCommerceInventoryWarehouseItem(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItem",
				_getCommerceInventoryWarehouseItemParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseItemId);

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
				CommerceInventoryWarehouseItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				getCommerceInventoryWarehouseItemByReferenceCode(
					HttpPrincipal httpPrincipal, long companyId,
					String externalReferenceCode)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItemByReferenceCode",
				_getCommerceInventoryWarehouseItemByReferenceCodeParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, externalReferenceCode);

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
				CommerceInventoryWarehouseItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				getCommerceInventoryWarehouseItemByReferenceCode(
					HttpPrincipal httpPrincipal, String externalReferenceCode,
					long companyId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItemByReferenceCode",
				_getCommerceInventoryWarehouseItemByReferenceCodeParameterTypes10);

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

			return (com.liferay.commerce.inventory.model.
				CommerceInventoryWarehouseItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
				getCommerceInventoryWarehouseItems(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItems",
				_getCommerceInventoryWarehouseItemsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, start, end);

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
					CommerceInventoryWarehouseItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
				getCommerceInventoryWarehouseItems(
					HttpPrincipal httpPrincipal, long companyId, String sku,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItems",
				_getCommerceInventoryWarehouseItemsParameterTypes12);

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
					CommerceInventoryWarehouseItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
				getCommerceInventoryWarehouseItemsByCompanyId(
					HttpPrincipal httpPrincipal, long companyId, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItemsByCompanyId",
				_getCommerceInventoryWarehouseItemsByCompanyIdParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end);

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
					CommerceInventoryWarehouseItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
				getCommerceInventoryWarehouseItemsByCompanyIdAndSku(
					HttpPrincipal httpPrincipal, long companyId, String sku,
					int start, int end)
			throws com.liferay.portal.kernel.security.auth.PrincipalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItemsByCompanyIdAndSku",
				_getCommerceInventoryWarehouseItemsByCompanyIdAndSkuParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sku, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.security.auth.
							PrincipalException) {

					throw (com.liferay.portal.kernel.security.auth.
						PrincipalException)exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.commerce.inventory.model.
					CommerceInventoryWarehouseItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceInventoryWarehouseItemsCount(
			HttpPrincipal httpPrincipal, long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItemsCount",
				_getCommerceInventoryWarehouseItemsCountParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId);

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

	public static int getCommerceInventoryWarehouseItemsCount(
			HttpPrincipal httpPrincipal, long companyId, String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItemsCount",
				_getCommerceInventoryWarehouseItemsCountParameterTypes16);

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

	public static int getCommerceInventoryWarehouseItemsCountByCompanyId(
			HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItemsCountByCompanyId",
				_getCommerceInventoryWarehouseItemsCountByCompanyIdParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

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

	public static int getCommerceInventoryWarehouseItemsCountByModifiedDate(
			HttpPrincipal httpPrincipal, long companyId,
			java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItemsCountByModifiedDate",
				_getCommerceInventoryWarehouseItemsCountByModifiedDateParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, startDate, endDate);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.security.auth.
							PrincipalException) {

					throw (com.liferay.portal.kernel.security.auth.
						PrincipalException)exception;
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

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
				getCommerceInventoryWarehouseItemsCountByModifiedDate(
					HttpPrincipal httpPrincipal, long companyId,
					java.util.Date startDate, java.util.Date endDate, int start,
					int end)
			throws com.liferay.portal.kernel.security.auth.PrincipalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItemsCountByModifiedDate",
				_getCommerceInventoryWarehouseItemsCountByModifiedDateParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, startDate, endDate, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.security.auth.
							PrincipalException) {

					throw (com.liferay.portal.kernel.security.auth.
						PrincipalException)exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.commerce.inventory.model.
					CommerceInventoryWarehouseItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				increaseCommerceInventoryWarehouseItemQuantity(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseItemId, int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"increaseCommerceInventoryWarehouseItemQuantity",
				_increaseCommerceInventoryWarehouseItemQuantityParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseItemId, quantity);

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
				CommerceInventoryWarehouseItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void moveQuantitiesBetweenWarehouses(
			HttpPrincipal httpPrincipal, long fromCommerceInventoryWarehouseId,
			long toCommerceInventoryWarehouseId, String sku, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"moveQuantitiesBetweenWarehouses",
				_moveQuantitiesBetweenWarehousesParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fromCommerceInventoryWarehouseId,
				toCommerceInventoryWarehouseId, sku, quantity);

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
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				updateCommerceInventoryWarehouseItem(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseItemId, int quantity,
					int reservedQuantity, long mvccVersion)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"updateCommerceInventoryWarehouseItem",
				_updateCommerceInventoryWarehouseItemParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseItemId, quantity,
				reservedQuantity, mvccVersion);

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
				CommerceInventoryWarehouseItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				updateCommerceInventoryWarehouseItem(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseItemId, int quantity,
					long mvccVersion)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"updateCommerceInventoryWarehouseItem",
				_updateCommerceInventoryWarehouseItemParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseItemId, quantity,
				mvccVersion);

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
				CommerceInventoryWarehouseItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceInventoryWarehouseItemServiceHttp.class);

	private static final Class<?>[]
		_addCommerceInventoryWarehouseItemParameterTypes0 = new Class[] {
			long.class, String.class, int.class
		};
	private static final Class<?>[]
		_addCommerceInventoryWarehouseItemParameterTypes1 = new Class[] {
			String.class, long.class, String.class, int.class
		};
	private static final Class<?>[]
		_addOrUpdateCommerceInventoryWarehouseItemParameterTypes2 =
			new Class[] {long.class, String.class, int.class};
	private static final Class<?>[]
		_addOrUpdateCommerceInventoryWarehouseItemParameterTypes3 =
			new Class[] {
				String.class, long.class, long.class, String.class, int.class
			};
	private static final Class<?>[]
		_deleteCommerceInventoryWarehouseItemParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_deleteCommerceInventoryWarehouseItemsParameterTypes5 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_fetchCommerceInventoryWarehouseItemParameterTypes6 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_fetchCommerceInventoryWarehouseItemByExternalReferenceCodeParameterTypes7 =
			new Class[] {String.class, long.class};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemParameterTypes8 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemByReferenceCodeParameterTypes9 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemByReferenceCodeParameterTypes10 =
			new Class[] {String.class, long.class};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsParameterTypes11 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsParameterTypes12 = new Class[] {
			long.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsByCompanyIdParameterTypes13 =
			new Class[] {long.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsByCompanyIdAndSkuParameterTypes14 =
			new Class[] {long.class, String.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsCountParameterTypes15 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsCountParameterTypes16 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsCountByCompanyIdParameterTypes17 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsCountByModifiedDateParameterTypes18 =
			new Class[] {
				long.class, java.util.Date.class, java.util.Date.class
			};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsCountByModifiedDateParameterTypes19 =
			new Class[] {
				long.class, java.util.Date.class, java.util.Date.class,
				int.class, int.class
			};
	private static final Class<?>[]
		_increaseCommerceInventoryWarehouseItemQuantityParameterTypes20 =
			new Class[] {long.class, int.class};
	private static final Class<?>[]
		_moveQuantitiesBetweenWarehousesParameterTypes21 = new Class[] {
			long.class, long.class, String.class, int.class
		};
	private static final Class<?>[]
		_updateCommerceInventoryWarehouseItemParameterTypes22 = new Class[] {
			long.class, int.class, int.class, long.class
		};
	private static final Class<?>[]
		_updateCommerceInventoryWarehouseItemParameterTypes23 = new Class[] {
			long.class, int.class, long.class
		};

}