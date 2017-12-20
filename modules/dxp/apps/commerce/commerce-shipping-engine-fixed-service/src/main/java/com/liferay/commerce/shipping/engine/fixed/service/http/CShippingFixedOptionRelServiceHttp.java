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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.shipping.engine.fixed.service.CShippingFixedOptionRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CShippingFixedOptionRelServiceUtil} service utility. The
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
 * @see CShippingFixedOptionRelServiceSoap
 * @see HttpPrincipal
 * @see CShippingFixedOptionRelServiceUtil
 * @generated
 */
@ProviderType
public class CShippingFixedOptionRelServiceHttp {
	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel addCShippingFixedOptionRel(
		HttpPrincipal httpPrincipal, long commerceShippingMethodId,
		long commerceShippingFixedOptionId, long commerceWarehouseId,
		long commerceCountryId, long commerceRegionId, java.lang.String zip,
		double weightFrom, double weightTo, double fixedPrice,
		double rateUnitWeightPrice, double ratePercentage,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CShippingFixedOptionRelServiceUtil.class,
					"addCShippingFixedOptionRel",
					_addCShippingFixedOptionRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingMethodId, commerceShippingFixedOptionId,
					commerceWarehouseId, commerceCountryId, commerceRegionId,
					zip, weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
					ratePercentage, serviceContext);

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

			return (com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCShippingFixedOptionRel(
		HttpPrincipal httpPrincipal, long cShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CShippingFixedOptionRelServiceUtil.class,
					"deleteCShippingFixedOptionRel",
					_deleteCShippingFixedOptionRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cShippingFixedOptionRelId);

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

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel fetchCShippingFixedOptionRel(
		HttpPrincipal httpPrincipal, long cShippingFixedOptionRelId) {
		try {
			MethodKey methodKey = new MethodKey(CShippingFixedOptionRelServiceUtil.class,
					"fetchCShippingFixedOptionRel",
					_fetchCShippingFixedOptionRelParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cShippingFixedOptionRelId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		HttpPrincipal httpPrincipal, long commerceShippingMethodId, int start,
		int end) {
		try {
			MethodKey methodKey = new MethodKey(CShippingFixedOptionRelServiceUtil.class,
					"getCommerceShippingMethodFixedOptionRels",
					_getCommerceShippingMethodFixedOptionRelsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingMethodId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		HttpPrincipal httpPrincipal, long commerceShippingMethodId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CShippingFixedOptionRelServiceUtil.class,
					"getCommerceShippingMethodFixedOptionRels",
					_getCommerceShippingMethodFixedOptionRelsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingMethodId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceShippingMethodFixedOptionRelsCount(
		HttpPrincipal httpPrincipal, long commerceShippingMethodId) {
		try {
			MethodKey methodKey = new MethodKey(CShippingFixedOptionRelServiceUtil.class,
					"getCommerceShippingMethodFixedOptionRelsCount",
					_getCommerceShippingMethodFixedOptionRelsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingMethodId);

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

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCShippingFixedOptionRels(
		HttpPrincipal httpPrincipal, long commerceShippingFixedOptionId,
		int start, int end) {
		try {
			MethodKey methodKey = new MethodKey(CShippingFixedOptionRelServiceUtil.class,
					"getCShippingFixedOptionRels",
					_getCShippingFixedOptionRelsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingFixedOptionId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCShippingFixedOptionRels(
		HttpPrincipal httpPrincipal, long commerceShippingFixedOptionId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CShippingFixedOptionRelServiceUtil.class,
					"getCShippingFixedOptionRels",
					_getCShippingFixedOptionRelsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingFixedOptionId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel fetchCShippingFixedOptionRel(
		HttpPrincipal httpPrincipal, long commerceShippingFixedOptionId,
		long commerceCountryId, long commerceRegionId, java.lang.String zip,
		double weight) {
		try {
			MethodKey methodKey = new MethodKey(CShippingFixedOptionRelServiceUtil.class,
					"fetchCShippingFixedOptionRel",
					_fetchCShippingFixedOptionRelParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingFixedOptionId, commerceCountryId,
					commerceRegionId, zip, weight);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCShippingFixedOptionRelsCount(
		HttpPrincipal httpPrincipal, long commerceShippingFixedOptionId) {
		try {
			MethodKey methodKey = new MethodKey(CShippingFixedOptionRelServiceUtil.class,
					"getCShippingFixedOptionRelsCount",
					_getCShippingFixedOptionRelsCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShippingFixedOptionId);

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

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel updateCShippingFixedOptionRel(
		HttpPrincipal httpPrincipal, long cShippingFixedOptionRelId,
		long commerceWarehouseId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weightFrom,
		double weightTo, double fixedPrice, double rateUnitWeightPrice,
		double ratePercentage)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CShippingFixedOptionRelServiceUtil.class,
					"updateCShippingFixedOptionRel",
					_updateCShippingFixedOptionRelParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cShippingFixedOptionRelId, commerceWarehouseId,
					commerceCountryId, commerceRegionId, zip, weightFrom,
					weightTo, fixedPrice, rateUnitWeightPrice, ratePercentage);

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

			return (com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CShippingFixedOptionRelServiceHttp.class);
	private static final Class<?>[] _addCShippingFixedOptionRelParameterTypes0 = new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			java.lang.String.class, double.class, double.class, double.class,
			double.class, double.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCShippingFixedOptionRelParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCShippingFixedOptionRelParameterTypes2 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceShippingMethodFixedOptionRelsParameterTypes3 =
		new Class[] { long.class, int.class, int.class };
	private static final Class<?>[] _getCommerceShippingMethodFixedOptionRelsParameterTypes4 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceShippingMethodFixedOptionRelsCountParameterTypes5 =
		new Class[] { long.class };
	private static final Class<?>[] _getCShippingFixedOptionRelsParameterTypes6 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getCShippingFixedOptionRelsParameterTypes7 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _fetchCShippingFixedOptionRelParameterTypes8 =
		new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			double.class
		};
	private static final Class<?>[] _getCShippingFixedOptionRelsCountParameterTypes9 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCShippingFixedOptionRelParameterTypes10 =
		new Class[] {
			long.class, long.class, long.class, long.class,
			java.lang.String.class, double.class, double.class, double.class,
			double.class, double.class
		};
}