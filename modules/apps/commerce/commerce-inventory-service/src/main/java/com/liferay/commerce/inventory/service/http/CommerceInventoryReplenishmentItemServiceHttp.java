/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @generated
 */
public class CommerceInventoryReplenishmentItemServiceHttp {

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem
				addCommerceInventoryReplenishmentItem(
					HttpPrincipal httpPrincipal, String externalReferenceCode,
					long commerceInventoryWarehouseId,
					java.util.Date availabilityDate,
					java.math.BigDecimal quantity, String sku,
					String unitOfMeasureKey)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"addCommerceInventoryReplenishmentItem",
				_addCommerceInventoryReplenishmentItemParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commerceInventoryWarehouseId,
				availabilityDate, quantity, sku, unitOfMeasureKey);

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

	public static void deleteCommerceInventoryReplenishmentItems(
			HttpPrincipal httpPrincipal, long companyId, String sku,
			String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"deleteCommerceInventoryReplenishmentItems",
				_deleteCommerceInventoryReplenishmentItemsParameterTypes2);

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
		com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem
				fetchCommerceInventoryReplenishmentItemByExternalReferenceCode(
					HttpPrincipal httpPrincipal, String externalReferenceCode,
					long companyId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"fetchCommerceInventoryReplenishmentItemByExternalReferenceCode",
				_fetchCommerceInventoryReplenishmentItemByExternalReferenceCodeParameterTypes3);

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
				CommerceInventoryReplenishmentItem)returnObj;
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
				_getCommerceInventoryReplenishmentItemParameterTypes4);

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
					getCommerceInventoryReplenishmentItemsByCommerceInventoryWarehouseId(
						HttpPrincipal httpPrincipal,
						long commerceInventoryWarehouseId, int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"getCommerceInventoryReplenishmentItemsByCommerceInventoryWarehouseId",
				_getCommerceInventoryReplenishmentItemsByCommerceInventoryWarehouseIdParameterTypes5);

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
					CommerceInventoryReplenishmentItem>)returnObj;
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
					getCommerceInventoryReplenishmentItemsByCompanyIdSkuAndUnitOfMeasureKey(
						HttpPrincipal httpPrincipal, long companyId, String sku,
						String unitOfMeasureKey, int start, int end)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"getCommerceInventoryReplenishmentItemsByCompanyIdSkuAndUnitOfMeasureKey",
				_getCommerceInventoryReplenishmentItemsByCompanyIdSkuAndUnitOfMeasureKeyParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sku, unitOfMeasureKey, start, end);

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

	public static java.math.BigDecimal
			getCommerceInventoryReplenishmentItemsCount(
				HttpPrincipal httpPrincipal, long commerceInventoryWarehouseId,
				String sku, String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"getCommerceInventoryReplenishmentItemsCount",
				_getCommerceInventoryReplenishmentItemsCountParameterTypes7);

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

			return (java.math.BigDecimal)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int
			getCommerceInventoryReplenishmentItemsCountByCommerceInventoryWarehouseId(
				HttpPrincipal httpPrincipal, long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"getCommerceInventoryReplenishmentItemsCountByCommerceInventoryWarehouseId",
				_getCommerceInventoryReplenishmentItemsCountByCommerceInventoryWarehouseIdParameterTypes8);

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

	public static int
			getCommerceInventoryReplenishmentItemsCountByCompanyIdSkuAndUnitOfMeasureKey(
				HttpPrincipal httpPrincipal, long companyId, String sku,
				String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"getCommerceInventoryReplenishmentItemsCountByCompanyIdSkuAndUnitOfMeasureKey",
				_getCommerceInventoryReplenishmentItemsCountByCompanyIdSkuAndUnitOfMeasureKeyParameterTypes9);

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

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem
				updateCommerceInventoryReplenishmentItem(
					HttpPrincipal httpPrincipal, String externalReferenceCode,
					long commerceInventoryReplenishmentItemId,
					java.util.Date availabilityDate,
					java.math.BigDecimal quantity, long mvccVersion)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryReplenishmentItemServiceUtil.class,
				"updateCommerceInventoryReplenishmentItem",
				_updateCommerceInventoryReplenishmentItemParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode,
				commerceInventoryReplenishmentItemId, availabilityDate,
				quantity, mvccVersion);

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
			String.class, long.class, java.util.Date.class,
			java.math.BigDecimal.class, String.class, String.class
		};
	private static final Class<?>[]
		_deleteCommerceInventoryReplenishmentItemParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_deleteCommerceInventoryReplenishmentItemsParameterTypes2 =
			new Class[] {long.class, String.class, String.class};
	private static final Class<?>[]
		_fetchCommerceInventoryReplenishmentItemByExternalReferenceCodeParameterTypes3 =
			new Class[] {String.class, long.class};
	private static final Class<?>[]
		_getCommerceInventoryReplenishmentItemParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceInventoryReplenishmentItemsByCommerceInventoryWarehouseIdParameterTypes5 =
			new Class[] {long.class, int.class, int.class};
	private static final Class<?>[]
		_getCommerceInventoryReplenishmentItemsByCompanyIdSkuAndUnitOfMeasureKeyParameterTypes6 =
			new Class[] {
				long.class, String.class, String.class, int.class, int.class
			};
	private static final Class<?>[]
		_getCommerceInventoryReplenishmentItemsCountParameterTypes7 =
			new Class[] {long.class, String.class, String.class};
	private static final Class<?>[]
		_getCommerceInventoryReplenishmentItemsCountByCommerceInventoryWarehouseIdParameterTypes8 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceInventoryReplenishmentItemsCountByCompanyIdSkuAndUnitOfMeasureKeyParameterTypes9 =
			new Class[] {long.class, String.class, String.class};
	private static final Class<?>[]
		_updateCommerceInventoryReplenishmentItemParameterTypes10 =
			new Class[] {
				String.class, long.class, java.util.Date.class,
				java.math.BigDecimal.class, long.class
			};

}