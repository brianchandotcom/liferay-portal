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

package com.liferay.commerce.product.service.http;

import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPInstanceUnitOfMeasureServiceUtil</code> service
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
 * @author Marco Leo
 * @generated
 */
public class CPInstanceUnitOfMeasureServiceHttp {

	public static com.liferay.commerce.product.model.CPInstanceUnitOfMeasure
			addCPInstanceUnitOfMeasure(
				HttpPrincipal httpPrincipal, long cpInstanceId, boolean active,
				java.math.BigDecimal incrementalOrderQuantity, String key,
				java.util.Map<java.util.Locale, String> nameMap, int precision,
				boolean primary, double priority, java.math.BigDecimal rate,
				String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPInstanceUnitOfMeasureServiceUtil.class,
				"addCPInstanceUnitOfMeasure",
				_addCPInstanceUnitOfMeasureParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceId, active, incrementalOrderQuantity, key,
				nameMap, precision, primary, priority, rate, sku);

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

			return (com.liferay.commerce.product.model.CPInstanceUnitOfMeasure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceUnitOfMeasure
			fetchCPInstanceUnitOfMeasure(
				HttpPrincipal httpPrincipal, long cpInstanceId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPInstanceUnitOfMeasureServiceUtil.class,
				"fetchCPInstanceUnitOfMeasure",
				_fetchCPInstanceUnitOfMeasureParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceId, key);

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

			return (com.liferay.commerce.product.model.CPInstanceUnitOfMeasure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceUnitOfMeasure
			getCPInstanceUnitOfMeasure(
				HttpPrincipal httpPrincipal, long cpInstanceId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPInstanceUnitOfMeasureServiceUtil.class,
				"getCPInstanceUnitOfMeasure",
				_getCPInstanceUnitOfMeasureParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceId, key);

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

			return (com.liferay.commerce.product.model.CPInstanceUnitOfMeasure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPInstanceUnitOfMeasure>
				getCPInstanceUnitOfMeasures(
					HttpPrincipal httpPrincipal, long cpInstanceId, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.product.model.
							CPInstanceUnitOfMeasure> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPInstanceUnitOfMeasureServiceUtil.class,
				"getCPInstanceUnitOfMeasures",
				_getCPInstanceUnitOfMeasuresParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceId, start, end, orderByComparator);

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
				<com.liferay.commerce.product.model.CPInstanceUnitOfMeasure>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPInstanceUnitOfMeasure
			updateCPInstanceUnitOfMeasure(
				HttpPrincipal httpPrincipal, long cpInstanceUnitOfMeasureId,
				long cpInstanceId, boolean active,
				java.math.BigDecimal incrementalOrderQuantity, String key,
				java.util.Map<java.util.Locale, String> nameMap, int precision,
				boolean primary, double priority, java.math.BigDecimal rate,
				String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPInstanceUnitOfMeasureServiceUtil.class,
				"updateCPInstanceUnitOfMeasure",
				_updateCPInstanceUnitOfMeasureParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceUnitOfMeasureId, cpInstanceId, active,
				incrementalOrderQuantity, key, nameMap, precision, primary,
				priority, rate, sku);

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

			return (com.liferay.commerce.product.model.CPInstanceUnitOfMeasure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPInstanceUnitOfMeasureServiceHttp.class);

	private static final Class<?>[] _addCPInstanceUnitOfMeasureParameterTypes0 =
		new Class[] {
			long.class, boolean.class, java.math.BigDecimal.class, String.class,
			java.util.Map.class, int.class, boolean.class, double.class,
			java.math.BigDecimal.class, String.class
		};
	private static final Class<?>[]
		_fetchCPInstanceUnitOfMeasureParameterTypes1 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getCPInstanceUnitOfMeasureParameterTypes2 =
		new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getCPInstanceUnitOfMeasuresParameterTypes3 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_updateCPInstanceUnitOfMeasureParameterTypes4 = new Class[] {
			long.class, long.class, boolean.class, java.math.BigDecimal.class,
			String.class, java.util.Map.class, int.class, boolean.class,
			double.class, java.math.BigDecimal.class, String.class
		};

}