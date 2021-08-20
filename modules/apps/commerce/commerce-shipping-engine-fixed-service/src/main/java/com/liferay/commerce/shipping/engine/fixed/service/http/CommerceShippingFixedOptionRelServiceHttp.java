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

package com.liferay.commerce.shipping.engine.fixed.service.http;

import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceShippingFixedOptionRelServiceUtil</code> service
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
 * @see CommerceShippingFixedOptionRelServiceSoap
 * @generated
 */
public class CommerceShippingFixedOptionRelServiceHttp {

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
				HttpPrincipal httpPrincipal, long groupId,
				long commerceShippingMethodId,
				long commerceShippingFixedOptionId,
				long commerceInventoryWarehouseId, long countryId,
				long regionId, String zip, double weightFrom, double weightTo,
				java.math.BigDecimal fixedPrice,
				java.math.BigDecimal rateUnitWeightPrice, double ratePercentage)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionRelServiceUtil.class,
				"addCommerceShippingFixedOptionRel",
				_addCommerceShippingFixedOptionRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, commerceShippingMethodId,
				commerceShippingFixedOptionId, commerceInventoryWarehouseId,
				countryId, regionId, zip, weightFrom, weightTo, fixedPrice,
				rateUnitWeightPrice, ratePercentage);

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

			return (com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
				HttpPrincipal httpPrincipal, long commerceShippingMethodId,
				long commerceShippingFixedOptionId,
				long commerceInventoryWarehouseId, long countryId,
				long regionId, String zip, double weightFrom, double weightTo,
				java.math.BigDecimal fixedPrice,
				java.math.BigDecimal rateUnitWeightPrice, double ratePercentage,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionRelServiceUtil.class,
				"addCommerceShippingFixedOptionRel",
				_addCommerceShippingFixedOptionRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId,
				commerceShippingFixedOptionId, commerceInventoryWarehouseId,
				countryId, regionId, zip, weightFrom, weightTo, fixedPrice,
				rateUnitWeightPrice, ratePercentage, serviceContext);

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

			return (com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceShippingFixedOptionRel(
			HttpPrincipal httpPrincipal, long commerceShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionRelServiceUtil.class,
				"deleteCommerceShippingFixedOptionRel",
				_deleteCommerceShippingFixedOptionRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionRelId);

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

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
				HttpPrincipal httpPrincipal,
				long commerceShippingFixedOptionRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionRelServiceUtil.class,
				"fetchCommerceShippingFixedOptionRel",
				_fetchCommerceShippingFixedOptionRelParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionRelId);

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

			return (com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionRel>
					getCommerceShippingMethodFixedOptionRels(
						HttpPrincipal httpPrincipal,
						long commerceShippingMethodId, int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.shipping.engine.fixed.model.
								CommerceShippingFixedOptionRel>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionRelServiceUtil.class,
				"getCommerceShippingMethodFixedOptionRels",
				_getCommerceShippingMethodFixedOptionRelsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId, start, end,
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

			return (java.util.List
				<com.liferay.commerce.shipping.engine.fixed.model.
					CommerceShippingFixedOptionRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceShippingMethodFixedOptionRelsCount(
			HttpPrincipal httpPrincipal, long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionRelServiceUtil.class,
				"getCommerceShippingMethodFixedOptionRelsCount",
				_getCommerceShippingMethodFixedOptionRelsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingMethodId);

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

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionRel updateCommerceShippingFixedOptionRel(
				HttpPrincipal httpPrincipal,
				long commerceShippingFixedOptionRelId,
				long commerceInventoryWarehouseId, long countryId,
				long regionId, String zip, double weightFrom, double weightTo,
				java.math.BigDecimal fixedPrice,
				java.math.BigDecimal rateUnitWeightPrice, double ratePercentage)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceShippingFixedOptionRelServiceUtil.class,
				"updateCommerceShippingFixedOptionRel",
				_updateCommerceShippingFixedOptionRelParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceShippingFixedOptionRelId,
				commerceInventoryWarehouseId, countryId, regionId, zip,
				weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
				ratePercentage);

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

			return (com.liferay.commerce.shipping.engine.fixed.model.
				CommerceShippingFixedOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceShippingFixedOptionRelServiceHttp.class);

	private static final Class<?>[]
		_addCommerceShippingFixedOptionRelParameterTypes0 = new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			long.class, String.class, double.class, double.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class, double.class
		};
	private static final Class<?>[]
		_addCommerceShippingFixedOptionRelParameterTypes1 = new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			String.class, double.class, double.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			double.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteCommerceShippingFixedOptionRelParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCommerceShippingFixedOptionRelParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCommerceShippingMethodFixedOptionRelsParameterTypes4 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCommerceShippingMethodFixedOptionRelsCountParameterTypes5 =
			new Class[] {long.class};
	private static final Class<?>[]
		_updateCommerceShippingFixedOptionRelParameterTypes6 = new Class[] {
			long.class, long.class, long.class, long.class, String.class,
			double.class, double.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, double.class
		};

}