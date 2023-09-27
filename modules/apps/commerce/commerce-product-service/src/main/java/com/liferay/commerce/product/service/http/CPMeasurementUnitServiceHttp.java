/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.http;

import com.liferay.commerce.product.service.CPMeasurementUnitServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CPMeasurementUnitServiceUtil</code> service
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
public class CPMeasurementUnitServiceHttp {

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			addCPMeasurementUnit(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				java.util.Map<java.util.Locale, String> nameMap, String key,
				double rate, boolean primary, double priority, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class, "addCPMeasurementUnit",
				_addCPMeasurementUnitParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, nameMap, key, rate, primary,
				priority, type, serviceContext);

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

			return (com.liferay.commerce.product.model.CPMeasurementUnit)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCPMeasurementUnit(
			HttpPrincipal httpPrincipal, long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class, "deleteCPMeasurementUnit",
				_deleteCPMeasurementUnitParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpMeasurementUnitId);

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

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			fetchCPMeasurementUnit(
				HttpPrincipal httpPrincipal, long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class, "fetchCPMeasurementUnit",
				_fetchCPMeasurementUnitParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpMeasurementUnitId);

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

			return (com.liferay.commerce.product.model.CPMeasurementUnit)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			fetchCPMeasurementUnitByExternalReferenceCode(
				HttpPrincipal httpPrincipal, long companyId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class,
				"fetchCPMeasurementUnitByExternalReferenceCode",
				_fetchCPMeasurementUnitByExternalReferenceCodeParameterTypes3);

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

			return (com.liferay.commerce.product.model.CPMeasurementUnit)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			fetchCPMeasurementUnitByKey(
				HttpPrincipal httpPrincipal, long companyId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class,
				"fetchCPMeasurementUnitByKey",
				_fetchCPMeasurementUnitByKeyParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, key);

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

			return (com.liferay.commerce.product.model.CPMeasurementUnit)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			fetchPrimaryCPMeasurementUnit(
				HttpPrincipal httpPrincipal, long companyId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class,
				"fetchPrimaryCPMeasurementUnit",
				_fetchPrimaryCPMeasurementUnitParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type);

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

			return (com.liferay.commerce.product.model.CPMeasurementUnit)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			fetchPrimaryCPMeasurementUnitByType(
				HttpPrincipal httpPrincipal, long companyId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class,
				"fetchPrimaryCPMeasurementUnitByType",
				_fetchPrimaryCPMeasurementUnitByTypeParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type);

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

			return (com.liferay.commerce.product.model.CPMeasurementUnit)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			getCPMeasurementUnit(
				HttpPrincipal httpPrincipal, long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class, "getCPMeasurementUnit",
				_getCPMeasurementUnitParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpMeasurementUnitId);

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

			return (com.liferay.commerce.product.model.CPMeasurementUnit)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			getCPMeasurementUnitByKey(
				HttpPrincipal httpPrincipal, long companyId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class, "getCPMeasurementUnitByKey",
				_getCPMeasurementUnitByKeyParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, key);

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

			return (com.liferay.commerce.product.model.CPMeasurementUnit)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPMeasurementUnit>
				getCPMeasurementUnits(
					HttpPrincipal httpPrincipal, long companyId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class, "getCPMeasurementUnits",
				_getCPMeasurementUnitsParameterTypes9);

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

			return (java.util.List
				<com.liferay.commerce.product.model.CPMeasurementUnit>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPMeasurementUnit>
				getCPMeasurementUnits(
					HttpPrincipal httpPrincipal, long companyId, int type,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.product.model.CPMeasurementUnit>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class, "getCPMeasurementUnits",
				_getCPMeasurementUnitsParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type, start, end, orderByComparator);

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
				<com.liferay.commerce.product.model.CPMeasurementUnit>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPMeasurementUnit>
				getCPMeasurementUnits(
					HttpPrincipal httpPrincipal, long companyId, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.product.model.CPMeasurementUnit>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class, "getCPMeasurementUnits",
				_getCPMeasurementUnitsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end, orderByComparator);

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
				<com.liferay.commerce.product.model.CPMeasurementUnit>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPMeasurementUnit>
				getCPMeasurementUnitsByType(
					HttpPrincipal httpPrincipal, long companyId, int type)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class,
				"getCPMeasurementUnitsByType",
				_getCPMeasurementUnitsByTypeParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type);

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
				<com.liferay.commerce.product.model.CPMeasurementUnit>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CPMeasurementUnit>
				getCPMeasurementUnitsByType(
					HttpPrincipal httpPrincipal, long companyId, int type,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.product.model.CPMeasurementUnit>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class,
				"getCPMeasurementUnitsByType",
				_getCPMeasurementUnitsByTypeParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type, start, end, orderByComparator);

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
				<com.liferay.commerce.product.model.CPMeasurementUnit>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCPMeasurementUnitsCount(
			HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class,
				"getCPMeasurementUnitsCount",
				_getCPMeasurementUnitsCountParameterTypes14);

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

	public static int getCPMeasurementUnitsCount(
			HttpPrincipal httpPrincipal, long companyId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class,
				"getCPMeasurementUnitsCount",
				_getCPMeasurementUnitsCountParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type);

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

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			setPrimary(
				HttpPrincipal httpPrincipal, long cpMeasurementUnitId,
				boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class, "setPrimary",
				_setPrimaryParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cpMeasurementUnitId, primary);

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

			return (com.liferay.commerce.product.model.CPMeasurementUnit)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit
			updateCPMeasurementUnit(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long cpMeasurementUnitId,
				java.util.Map<java.util.Locale, String> nameMap, String key,
				double rate, boolean primary, double priority, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CPMeasurementUnitServiceUtil.class, "updateCPMeasurementUnit",
				_updateCPMeasurementUnitParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, cpMeasurementUnitId, nameMap,
				key, rate, primary, priority, type, serviceContext);

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

			return (com.liferay.commerce.product.model.CPMeasurementUnit)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPMeasurementUnitServiceHttp.class);

	private static final Class<?>[] _addCPMeasurementUnitParameterTypes0 =
		new Class[] {
			String.class, java.util.Map.class, String.class, double.class,
			boolean.class, double.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPMeasurementUnitParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchCPMeasurementUnitParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[]
		_fetchCPMeasurementUnitByExternalReferenceCodeParameterTypes3 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_fetchCPMeasurementUnitByKeyParameterTypes4 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_fetchPrimaryCPMeasurementUnitParameterTypes5 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[]
		_fetchPrimaryCPMeasurementUnitByTypeParameterTypes6 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _getCPMeasurementUnitParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _getCPMeasurementUnitByKeyParameterTypes8 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getCPMeasurementUnitsParameterTypes9 =
		new Class[] {long.class};
	private static final Class<?>[] _getCPMeasurementUnitsParameterTypes10 =
		new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPMeasurementUnitsParameterTypes11 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCPMeasurementUnitsByTypeParameterTypes12 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[]
		_getCPMeasurementUnitsByTypeParameterTypes13 = new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getCPMeasurementUnitsCountParameterTypes14 = new Class[] {long.class};
	private static final Class<?>[]
		_getCPMeasurementUnitsCountParameterTypes15 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _setPrimaryParameterTypes16 = new Class[] {
		long.class, boolean.class
	};
	private static final Class<?>[] _updateCPMeasurementUnitParameterTypes17 =
		new Class[] {
			String.class, long.class, java.util.Map.class, String.class,
			double.class, boolean.class, double.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}