/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.http;

import com.liferay.commerce.service.CommerceShipmentItemServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceShipmentItemServiceUtil</code> service
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
public class CommerceShipmentItemServiceHttp {

	public static com.liferay.commerce.model.CommerceShipmentItem
			addCommerceShipmentItem(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long commerceShipmentId, long commerceOrderItemId,
				long commerceInventoryWarehouseId,
				java.math.BigDecimal quantity, String unitOfMeasureKey,
				boolean validateInventory,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"addCommerceShipmentItem",
				_addCommerceShipmentItemParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commerceShipmentId,
				commerceOrderItemId, commerceInventoryWarehouseId, quantity,
				unitOfMeasureKey, validateInventory, serviceContext);

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

			return (com.liferay.commerce.model.CommerceShipmentItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentItem
			addOrUpdateCommerceShipmentItem(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long commerceShipmentId, long commerceOrderItemId,
				long commerceInventoryWarehouseId,
				java.math.BigDecimal quantity, String unitOfMeasureKey,
				boolean validateInventory,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"addOrUpdateCommerceShipmentItem",
				_addOrUpdateCommerceShipmentItemParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commerceShipmentId,
				commerceOrderItemId, commerceInventoryWarehouseId, quantity,
				unitOfMeasureKey, validateInventory, serviceContext);

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

			return (com.liferay.commerce.model.CommerceShipmentItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceShipmentItem(
			HttpPrincipal httpPrincipal, long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"deleteCommerceShipmentItem",
				_deleteCommerceShipmentItemParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShipmentItemId);

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

	public static void deleteCommerceShipmentItem(
			HttpPrincipal httpPrincipal, long commerceShipmentItemId,
			boolean restoreStockQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"deleteCommerceShipmentItem",
				_deleteCommerceShipmentItemParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShipmentItemId, restoreStockQuantity);

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

	public static void deleteCommerceShipmentItems(
			HttpPrincipal httpPrincipal, long commerceShipmentId,
			boolean restoreStockQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"deleteCommerceShipmentItems",
				_deleteCommerceShipmentItemsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShipmentId, restoreStockQuantity);

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

	public static com.liferay.commerce.model.CommerceShipmentItem
			fetchCommerceShipmentItem(
				HttpPrincipal httpPrincipal, long commerceShipmentId,
				long commerceOrderItemId, long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"fetchCommerceShipmentItem",
				_fetchCommerceShipmentItemParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShipmentId, commerceOrderItemId,
				commerceInventoryWarehouseId);

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

			return (com.liferay.commerce.model.CommerceShipmentItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentItem
			fetchCommerceShipmentItemByExternalReferenceCode(
				HttpPrincipal httpPrincipal, long companyId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"fetchCommerceShipmentItemByExternalReferenceCode",
				_fetchCommerceShipmentItemByExternalReferenceCodeParameterTypes6);

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

			return (com.liferay.commerce.model.CommerceShipmentItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentItem
			getCommerceShipmentItem(
				HttpPrincipal httpPrincipal, long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"getCommerceShipmentItem",
				_getCommerceShipmentItemParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShipmentItemId);

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

			return (com.liferay.commerce.model.CommerceShipmentItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.model.CommerceShipmentItem>
				getCommerceShipmentItems(
					HttpPrincipal httpPrincipal, long commerceOrderItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"getCommerceShipmentItems",
				_getCommerceShipmentItemsParameterTypes8);

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

			return (java.util.List
				<com.liferay.commerce.model.CommerceShipmentItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.model.CommerceShipmentItem>
				getCommerceShipmentItems(
					HttpPrincipal httpPrincipal, long commerceShipmentId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.model.CommerceShipmentItem>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"getCommerceShipmentItems",
				_getCommerceShipmentItemsParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShipmentId, start, end, orderByComparator);

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
				<com.liferay.commerce.model.CommerceShipmentItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.model.CommerceShipmentItem>
				getCommerceShipmentItemsByCommerceOrderItemId(
					HttpPrincipal httpPrincipal, long commerceOrderItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"getCommerceShipmentItemsByCommerceOrderItemId",
				_getCommerceShipmentItemsByCommerceOrderItemIdParameterTypes10);

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

			return (java.util.List
				<com.liferay.commerce.model.CommerceShipmentItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceShipmentItemsCount(
			HttpPrincipal httpPrincipal, long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"getCommerceShipmentItemsCount",
				_getCommerceShipmentItemsCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShipmentId);

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

	public static int getCommerceShipmentItemsCountByCommerceOrderItemId(
			HttpPrincipal httpPrincipal, long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"getCommerceShipmentItemsCountByCommerceOrderItemId",
				_getCommerceShipmentItemsCountByCommerceOrderItemIdParameterTypes12);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceShipmentOrderItemsQuantity(
			HttpPrincipal httpPrincipal, long commerceShipmentId,
			long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"getCommerceShipmentOrderItemsQuantity",
				_getCommerceShipmentOrderItemsQuantityParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShipmentId, commerceOrderItemId);

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

	public static com.liferay.commerce.model.CommerceShipmentItem
			updateCommerceShipmentItem(
				HttpPrincipal httpPrincipal, long commerceShipmentItemId,
				long commerceInventoryWarehouseId,
				java.math.BigDecimal quantity, boolean validateInventory)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"updateCommerceShipmentItem",
				_updateCommerceShipmentItemParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShipmentItemId, commerceInventoryWarehouseId,
				quantity, validateInventory);

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

			return (com.liferay.commerce.model.CommerceShipmentItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentItem
			updateExternalReferenceCode(
				HttpPrincipal httpPrincipal, long commerceShipmentItemId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShipmentItemServiceUtil.class,
				"updateExternalReferenceCode",
				_updateExternalReferenceCodeParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShipmentItemId, externalReferenceCode);

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

			return (com.liferay.commerce.model.CommerceShipmentItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceShipmentItemServiceHttp.class);

	private static final Class<?>[] _addCommerceShipmentItemParameterTypes0 =
		new Class[] {
			String.class, long.class, long.class, long.class,
			java.math.BigDecimal.class, String.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_addOrUpdateCommerceShipmentItemParameterTypes1 = new Class[] {
			String.class, long.class, long.class, long.class,
			java.math.BigDecimal.class, String.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceShipmentItemParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteCommerceShipmentItemParameterTypes3 =
		new Class[] {long.class, boolean.class};
	private static final Class<?>[]
		_deleteCommerceShipmentItemsParameterTypes4 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[] _fetchCommerceShipmentItemParameterTypes5 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[]
		_fetchCommerceShipmentItemByExternalReferenceCodeParameterTypes6 =
			new Class[] {long.class, String.class};
	private static final Class<?>[] _getCommerceShipmentItemParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _getCommerceShipmentItemsParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[] _getCommerceShipmentItemsParameterTypes9 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceShipmentItemsByCommerceOrderItemIdParameterTypes10 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceShipmentItemsCountParameterTypes11 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceShipmentItemsCountByCommerceOrderItemIdParameterTypes12 =
			new Class[] {long.class};
	private static final Class<?>[]
		_getCommerceShipmentOrderItemsQuantityParameterTypes13 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[]
		_updateCommerceShipmentItemParameterTypes14 = new Class[] {
			long.class, long.class, java.math.BigDecimal.class, boolean.class
		};
	private static final Class<?>[]
		_updateExternalReferenceCodeParameterTypes15 = new Class[] {
			long.class, String.class
		};

}