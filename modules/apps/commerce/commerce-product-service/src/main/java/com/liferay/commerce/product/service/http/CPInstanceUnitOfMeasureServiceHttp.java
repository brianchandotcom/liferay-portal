/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			addOrUpdateCPInstanceUnitOfMeasure(
				HttpPrincipal httpPrincipal, long cpInstanceId, boolean active,
				java.math.BigDecimal incrementalOrderQuantity, String key,
				java.util.Map<java.util.Locale, String> nameMap, int precision,
				boolean primary, double priority, java.math.BigDecimal rate,
				String sku)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPInstanceUnitOfMeasureServiceUtil.class,
				"addOrUpdateCPInstanceUnitOfMeasure",
				_addOrUpdateCPInstanceUnitOfMeasureParameterTypes1);

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
			deleteCPInstanceUnitOfMeasure(
				HttpPrincipal httpPrincipal, long cpInstanceUnitOfMeasureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPInstanceUnitOfMeasureServiceUtil.class,
				"deleteCPInstanceUnitOfMeasure",
				_deleteCPInstanceUnitOfMeasureParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceUnitOfMeasureId);

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
				HttpPrincipal httpPrincipal, long cpInstanceUnitOfMeasureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPInstanceUnitOfMeasureServiceUtil.class,
				"fetchCPInstanceUnitOfMeasure",
				_fetchCPInstanceUnitOfMeasureParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceUnitOfMeasureId);

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
				_fetchCPInstanceUnitOfMeasureParameterTypes4);

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
			fetchPrimaryCPInstanceUnitOfMeasure(
				HttpPrincipal httpPrincipal, long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPInstanceUnitOfMeasureServiceUtil.class,
				"fetchPrimaryCPInstanceUnitOfMeasure",
				_fetchPrimaryCPInstanceUnitOfMeasureParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceId);

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
				getActiveCPInstanceUnitOfMeasures(
					HttpPrincipal httpPrincipal, long cpInstanceId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPInstanceUnitOfMeasureServiceUtil.class,
				"getActiveCPInstanceUnitOfMeasures",
				_getActiveCPInstanceUnitOfMeasuresParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceId);

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

	public static int getActiveCPInstanceUnitOfMeasuresCount(
			HttpPrincipal httpPrincipal, long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPInstanceUnitOfMeasureServiceUtil.class,
				"getActiveCPInstanceUnitOfMeasuresCount",
				_getActiveCPInstanceUnitOfMeasuresCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceId);

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

	public static com.liferay.commerce.product.model.CPInstanceUnitOfMeasure
			getCPInstanceUnitOfMeasure(
				HttpPrincipal httpPrincipal, long cpInstanceUnitOfMeasureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPInstanceUnitOfMeasureServiceUtil.class,
				"getCPInstanceUnitOfMeasure",
				_getCPInstanceUnitOfMeasureParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceUnitOfMeasureId);

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
				_getCPInstanceUnitOfMeasureParameterTypes9);

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
				_getCPInstanceUnitOfMeasuresParameterTypes10);

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

	public static int getCPInstanceUnitOfMeasuresCount(
			HttpPrincipal httpPrincipal, long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPInstanceUnitOfMeasureServiceUtil.class,
				"getCPInstanceUnitOfMeasuresCount",
				_getCPInstanceUnitOfMeasuresCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpInstanceId);

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
				_updateCPInstanceUnitOfMeasureParameterTypes12);

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
		_addOrUpdateCPInstanceUnitOfMeasureParameterTypes1 = new Class[] {
			long.class, boolean.class, java.math.BigDecimal.class, String.class,
			java.util.Map.class, int.class, boolean.class, double.class,
			java.math.BigDecimal.class, String.class
		};
	private static final Class<?>[]
		_deleteCPInstanceUnitOfMeasureParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchCPInstanceUnitOfMeasureParameterTypes3 = new Class[] {long.class};
	private static final Class<?>[]
		_fetchCPInstanceUnitOfMeasureParameterTypes4 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_fetchPrimaryCPInstanceUnitOfMeasureParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getActiveCPInstanceUnitOfMeasuresParameterTypes6 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getActiveCPInstanceUnitOfMeasuresCountParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPInstanceUnitOfMeasureParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[] _getCPInstanceUnitOfMeasureParameterTypes9 =
		new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getCPInstanceUnitOfMeasuresParameterTypes10 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCPInstanceUnitOfMeasuresCountParameterTypes11 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_updateCPInstanceUnitOfMeasureParameterTypes12 = new Class[] {
			long.class, long.class, boolean.class, java.math.BigDecimal.class,
			String.class, java.util.Map.class, int.class, boolean.class,
			double.class, java.math.BigDecimal.class, String.class
		};

}