/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @generated
 */
public class CommerceInventoryWarehouseItemServiceHttp {

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				addCommerceInventoryWarehouseItem(
					HttpPrincipal httpPrincipal, String externalReferenceCode,
					long commerceInventoryWarehouseId,
					java.math.BigDecimal quantity, String sku,
					String unitOfMeasureKey)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"addCommerceInventoryWarehouseItem",
				_addCommerceInventoryWarehouseItemParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commerceInventoryWarehouseId,
				quantity, sku, unitOfMeasureKey);

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
					java.math.BigDecimal quantity, String sku,
					String unitOfMeasureKey)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"addOrUpdateCommerceInventoryWarehouseItem",
				_addOrUpdateCommerceInventoryWarehouseItemParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, companyId,
				commerceInventoryWarehouseId, quantity, sku, unitOfMeasureKey);

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
				_deleteCommerceInventoryWarehouseItemParameterTypes2);

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
			HttpPrincipal httpPrincipal, long companyId, String sku,
			String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"deleteCommerceInventoryWarehouseItems",
				_deleteCommerceInventoryWarehouseItemsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sku, unitOfMeasureKey);

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
					long commerceInventoryWarehouseId, String sku,
					String unitOfMeasureKey)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"fetchCommerceInventoryWarehouseItem",
				_fetchCommerceInventoryWarehouseItemParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, sku, unitOfMeasureKey);

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
				_fetchCommerceInventoryWarehouseItemByExternalReferenceCodeParameterTypes5);

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
				_getCommerceInventoryWarehouseItemParameterTypes6);

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
				getCommerceInventoryWarehouseItem(
					HttpPrincipal httpPrincipal,
					long commerceInventoryWarehouseId, String sku,
					String unitOfMeasureKey)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItem",
				_getCommerceInventoryWarehouseItemParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseId, sku, unitOfMeasureKey);

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
				_getCommerceInventoryWarehouseItemsParameterTypes8);

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
				getCommerceInventoryWarehouseItemsByCompanyId(
					HttpPrincipal httpPrincipal, long companyId, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItemsByCompanyId",
				_getCommerceInventoryWarehouseItemsByCompanyIdParameterTypes9);

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
				getCommerceInventoryWarehouseItemsByCompanyIdSkuAndUnitOfMeasureKey(
					HttpPrincipal httpPrincipal, long companyId, String sku,
					String unitOfMeasureKey, int start, int end)
			throws com.liferay.portal.kernel.security.auth.PrincipalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItemsByCompanyIdSkuAndUnitOfMeasureKey",
				_getCommerceInventoryWarehouseItemsByCompanyIdSkuAndUnitOfMeasureKeyParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sku, unitOfMeasureKey, start, end);

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
				_getCommerceInventoryWarehouseItemsCountParameterTypes11);

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
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			String sku, String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItemsCount",
				_getCommerceInventoryWarehouseItemsCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, sku, unitOfMeasureKey);

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
			HttpPrincipal httpPrincipal, long companyId, String sku,
			String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getCommerceInventoryWarehouseItemsCount",
				_getCommerceInventoryWarehouseItemsCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sku, unitOfMeasureKey);

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
				_getCommerceInventoryWarehouseItemsCountByCompanyIdParameterTypes14);

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
				_getCommerceInventoryWarehouseItemsCountByModifiedDateParameterTypes15);

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
				_getCommerceInventoryWarehouseItemsCountByModifiedDateParameterTypes16);

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

	public static java.math.BigDecimal getStockQuantity(
		HttpPrincipal httpPrincipal, long companyId, long groupId, String sku,
		String unitOfMeasureKey) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getStockQuantity", _getStockQuantityParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, sku, unitOfMeasureKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.math.BigDecimal)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.math.BigDecimal getStockQuantity(
		HttpPrincipal httpPrincipal, long companyId, String sku,
		String unitOfMeasureKey) {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"getStockQuantity", _getStockQuantityParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sku, unitOfMeasureKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.math.BigDecimal)returnObj;
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
					long commerceInventoryWarehouseItemId,
					java.math.BigDecimal quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"increaseCommerceInventoryWarehouseItemQuantity",
				_increaseCommerceInventoryWarehouseItemQuantityParameterTypes19);

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
			long toCommerceInventoryWarehouseId, java.math.BigDecimal quantity,
			String sku, String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"moveQuantitiesBetweenWarehouses",
				_moveQuantitiesBetweenWarehousesParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fromCommerceInventoryWarehouseId,
				toCommerceInventoryWarehouseId, quantity, sku,
				unitOfMeasureKey);

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
					long commerceInventoryWarehouseItemId,
					java.math.BigDecimal quantity,
					java.math.BigDecimal reservedQuantity, long mvccVersion)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"updateCommerceInventoryWarehouseItem",
				_updateCommerceInventoryWarehouseItemParameterTypes21);

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
					long commerceInventoryWarehouseItemId, long mvccVersion,
					java.math.BigDecimal quantity, String unitOfMeasureKey)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryWarehouseItemServiceUtil.class,
				"updateCommerceInventoryWarehouseItem",
				_updateCommerceInventoryWarehouseItemParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceInventoryWarehouseItemId, mvccVersion,
				quantity, unitOfMeasureKey);

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
			String.class, long.class, java.math.BigDecimal.class, String.class,
			String.class
		};
	private static final Class<?>[]
		_addOrUpdateCommerceInventoryWarehouseItemParameterTypes1 =
			new Class[] {
				String.class, long.class, long.class,
				java.math.BigDecimal.class, String.class, String.class
			};
	private static final Class<?>[]
		_deleteCommerceInventoryWarehouseItemParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_deleteCommerceInventoryWarehouseItemsParameterTypes3 = new Class[] {
			long.class, String.class, String.class
		};
	private static final Class<?>[]
		_fetchCommerceInventoryWarehouseItemParameterTypes4 = new Class[] {
			long.class, String.class, String.class
		};
	private static final Class<?>[]
		_fetchCommerceInventoryWarehouseItemByExternalReferenceCodeParameterTypes5 =
			new Class[] {String.class, long.class};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemParameterTypes7 = new Class[] {
			long.class, String.class, String.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsParameterTypes8 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsByCompanyIdParameterTypes9 =
			new Class[] {long.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsByCompanyIdSkuAndUnitOfMeasureKeyParameterTypes10 =
			new Class[] {
				long.class, String.class, String.class, int.class, int.class
			};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsCountParameterTypes11 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsCountParameterTypes12 = new Class[] {
			long.class, long.class, String.class, String.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsCountParameterTypes13 = new Class[] {
			long.class, String.class, String.class
		};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsCountByCompanyIdParameterTypes14 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsCountByModifiedDateParameterTypes15 =
			new Class[] {
				long.class, java.util.Date.class, java.util.Date.class
			};
	private static final Class<?>[]
		_getCommerceInventoryWarehouseItemsCountByModifiedDateParameterTypes16 =
			new Class[] {
				long.class, java.util.Date.class, java.util.Date.class,
				int.class, int.class
			};
	private static final Class<?>[] _getStockQuantityParameterTypes17 =
		new Class[] {long.class, long.class, String.class, String.class};
	private static final Class<?>[] _getStockQuantityParameterTypes18 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[]
		_increaseCommerceInventoryWarehouseItemQuantityParameterTypes19 =
			new Class[] {long.class, java.math.BigDecimal.class};
	private static final Class<?>[]
		_moveQuantitiesBetweenWarehousesParameterTypes20 = new Class[] {
			long.class, long.class, java.math.BigDecimal.class, String.class,
			String.class
		};
	private static final Class<?>[]
		_updateCommerceInventoryWarehouseItemParameterTypes21 = new Class[] {
			long.class, java.math.BigDecimal.class, java.math.BigDecimal.class,
			long.class
		};
	private static final Class<?>[]
		_updateCommerceInventoryWarehouseItemParameterTypes22 = new Class[] {
			long.class, long.class, java.math.BigDecimal.class, String.class
		};

}