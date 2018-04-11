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

package com.liferay.commerce.tax.engine.fixed.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceTaxFixedRateServiceUtil} service utility. The
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
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateServiceSoap
 * @see HttpPrincipal
 * @see CommerceTaxFixedRateServiceUtil
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateServiceHttp {
	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate addCommerceTaxFixedRate(
		HttpPrincipal httpPrincipal, long commerceTaxMethodId,
		long cpTaxCategoryId, double rate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxFixedRateServiceUtil.class,
					"addCommerceTaxFixedRate",
					_addCommerceTaxFixedRateParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTaxMethodId, cpTaxCategoryId, rate, serviceContext);

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

			return (com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceTaxFixedRate(HttpPrincipal httpPrincipal,
		long commerceTaxFixedRateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxFixedRateServiceUtil.class,
					"deleteCommerceTaxFixedRate",
					_deleteCommerceTaxFixedRateParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTaxFixedRateId);

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

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate fetchCommerceTaxFixedRate(
		HttpPrincipal httpPrincipal, long commerceTaxFixedRateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxFixedRateServiceUtil.class,
					"fetchCommerceTaxFixedRate",
					_fetchCommerceTaxFixedRateParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTaxFixedRateId);

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

			return (com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate fetchCommerceTaxFixedRate(
		HttpPrincipal httpPrincipal, long cpTaxCategoryId,
		long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxFixedRateServiceUtil.class,
					"fetchCommerceTaxFixedRate",
					_fetchCommerceTaxFixedRateParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpTaxCategoryId, commerceTaxMethodId);

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

			return (com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate> getCommerceTaxFixedRates(
		HttpPrincipal httpPrincipal, long groupId, long commerceTaxMethodId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxFixedRateServiceUtil.class,
					"getCommerceTaxFixedRates",
					_getCommerceTaxFixedRatesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					commerceTaxMethodId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceTaxFixedRatesCount(
		HttpPrincipal httpPrincipal, long groupId, long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxFixedRateServiceUtil.class,
					"getCommerceTaxFixedRatesCount",
					_getCommerceTaxFixedRatesCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					commerceTaxMethodId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate updateCommerceTaxFixedRate(
		HttpPrincipal httpPrincipal, long commerceTaxFixedRateId, double rate)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceTaxFixedRateServiceUtil.class,
					"updateCommerceTaxFixedRate",
					_updateCommerceTaxFixedRateParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceTaxFixedRateId, rate);

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

			return (com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceTaxFixedRateServiceHttp.class);
	private static final Class<?>[] _addCommerceTaxFixedRateParameterTypes0 = new Class[] {
			long.class, long.class, double.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceTaxFixedRateParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommerceTaxFixedRateParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommerceTaxFixedRateParameterTypes3 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getCommerceTaxFixedRatesParameterTypes4 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceTaxFixedRatesCountParameterTypes5 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _updateCommerceTaxFixedRateParameterTypes6 = new Class[] {
			long.class, double.class
		};
}