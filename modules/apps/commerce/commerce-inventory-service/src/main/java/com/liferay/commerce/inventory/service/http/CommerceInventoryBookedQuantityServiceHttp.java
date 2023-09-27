/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.http;

import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceInventoryBookedQuantityServiceUtil</code> service
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
public class CommerceInventoryBookedQuantityServiceHttp {

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity>
				getCommerceInventoryBookedQuantities(
					HttpPrincipal httpPrincipal, long companyId, String sku,
					String unitOfMeasureKey, int start, int end)
			throws com.liferay.portal.kernel.security.auth.PrincipalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryBookedQuantityServiceUtil.class,
				"getCommerceInventoryBookedQuantities",
				_getCommerceInventoryBookedQuantitiesParameterTypes0);

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
					CommerceInventoryBookedQuantity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity>
				getCommerceInventoryBookedQuantities(
					HttpPrincipal httpPrincipal, long companyId,
					String keywords, String sku, String unitOfMeasureKey,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryBookedQuantityServiceUtil.class,
				"getCommerceInventoryBookedQuantities",
				_getCommerceInventoryBookedQuantitiesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, keywords, sku, unitOfMeasureKey, start,
				end);

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
					CommerceInventoryBookedQuantity>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceInventoryBookedQuantitiesCount(
			HttpPrincipal httpPrincipal, long companyId, String sku,
			String unitOfMeasureKey)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryBookedQuantityServiceUtil.class,
				"getCommerceInventoryBookedQuantitiesCount",
				_getCommerceInventoryBookedQuantitiesCountParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, sku, unitOfMeasureKey);

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

	public static int getCommerceInventoryBookedQuantitiesCount(
			HttpPrincipal httpPrincipal, long companyId, String keywords,
			String sku, String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceInventoryBookedQuantityServiceUtil.class,
				"getCommerceInventoryBookedQuantitiesCount",
				_getCommerceInventoryBookedQuantitiesCountParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, keywords, sku, unitOfMeasureKey);

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

	private static Log _log = LogFactoryUtil.getLog(
		CommerceInventoryBookedQuantityServiceHttp.class);

	private static final Class<?>[]
		_getCommerceInventoryBookedQuantitiesParameterTypes0 = new Class[] {
			long.class, String.class, String.class, int.class, int.class
		};
	private static final Class<?>[]
		_getCommerceInventoryBookedQuantitiesParameterTypes1 = new Class[] {
			long.class, String.class, String.class, String.class, int.class,
			int.class
		};
	private static final Class<?>[]
		_getCommerceInventoryBookedQuantitiesCountParameterTypes2 =
			new Class[] {long.class, String.class, String.class};
	private static final Class<?>[]
		_getCommerceInventoryBookedQuantitiesCountParameterTypes3 =
			new Class[] {long.class, String.class, String.class, String.class};

}