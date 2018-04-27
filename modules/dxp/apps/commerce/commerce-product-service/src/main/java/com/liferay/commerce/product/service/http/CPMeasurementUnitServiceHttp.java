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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CPMeasurementUnitServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPMeasurementUnitServiceUtil} service utility. The
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
 * @author Marco Leo
 * @see CPMeasurementUnitServiceSoap
 * @see HttpPrincipal
 * @see CPMeasurementUnitServiceUtil
 * @generated
 */
@ProviderType
public class CPMeasurementUnitServiceHttp {
	public static com.liferay.commerce.product.model.CPMeasurementUnit addCPMeasurementUnit(
		HttpPrincipal httpPrincipal,
		java.util.Map<java.util.Locale, String> nameMap, String key,
		double rate, boolean primary, double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPMeasurementUnitServiceUtil.class,
					"addCPMeasurementUnit", _addCPMeasurementUnitParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, nameMap,
					key, rate, primary, priority, type, serviceContext);

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

			return (com.liferay.commerce.product.model.CPMeasurementUnit)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCPMeasurementUnit(HttpPrincipal httpPrincipal,
		long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPMeasurementUnitServiceUtil.class,
					"deleteCPMeasurementUnit",
					_deleteCPMeasurementUnitParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpMeasurementUnitId);

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

	public static com.liferay.commerce.product.model.CPMeasurementUnit fetchPrimaryCPMeasurementUnit(
		HttpPrincipal httpPrincipal, long groupId, int type) {
		try {
			MethodKey methodKey = new MethodKey(CPMeasurementUnitServiceUtil.class,
					"fetchPrimaryCPMeasurementUnit",
					_fetchPrimaryCPMeasurementUnitParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPMeasurementUnit)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit getCPMeasurementUnit(
		HttpPrincipal httpPrincipal, long cpMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPMeasurementUnitServiceUtil.class,
					"getCPMeasurementUnit", _getCPMeasurementUnitParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpMeasurementUnitId);

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

			return (com.liferay.commerce.product.model.CPMeasurementUnit)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPMeasurementUnit> getCPMeasurementUnits(
		HttpPrincipal httpPrincipal, long groupId, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPMeasurementUnit> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CPMeasurementUnitServiceUtil.class,
					"getCPMeasurementUnits",
					_getCPMeasurementUnitsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					type, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.product.model.CPMeasurementUnit>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCPMeasurementUnitsCount(HttpPrincipal httpPrincipal,
		long groupId, int type) {
		try {
			MethodKey methodKey = new MethodKey(CPMeasurementUnitServiceUtil.class,
					"getCPMeasurementUnitsCount",
					_getCPMeasurementUnitsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPMeasurementUnit updateCPMeasurementUnit(
		HttpPrincipal httpPrincipal, long cpMeasurementUnitId,
		java.util.Map<java.util.Locale, String> nameMap, String key,
		double rate, boolean primary, double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPMeasurementUnitServiceUtil.class,
					"updateCPMeasurementUnit",
					_updateCPMeasurementUnitParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpMeasurementUnitId, nameMap, key, rate, primary, priority,
					type, serviceContext);

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

			return (com.liferay.commerce.product.model.CPMeasurementUnit)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPMeasurementUnitServiceHttp.class);
	private static final Class<?>[] _addCPMeasurementUnitParameterTypes0 = new Class[] {
			java.util.Map.class, String.class, double.class, boolean.class,
			double.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPMeasurementUnitParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchPrimaryCPMeasurementUnitParameterTypes2 =
		new Class[] { long.class, int.class };
	private static final Class<?>[] _getCPMeasurementUnitParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPMeasurementUnitsParameterTypes4 = new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPMeasurementUnitsCountParameterTypes5 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _updateCPMeasurementUnitParameterTypes6 = new Class[] {
			long.class, java.util.Map.class, String.class, double.class,
			boolean.class, double.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}