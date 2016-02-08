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

package com.liferay.shopping.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import com.liferay.shopping.service.ShoppingCouponServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link ShoppingCouponServiceUtil} service utility. The
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
 * @author Brian Wing Shun Chan
 * @see ShoppingCouponServiceSoap
 * @see HttpPrincipal
 * @see ShoppingCouponServiceUtil
 * @generated
 */
@ProviderType
public class ShoppingCouponServiceHttp {
	public static com.liferay.shopping.model.ShoppingCoupon addCoupon(
		HttpPrincipal httpPrincipal, java.lang.String code, boolean autoCode,
		java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCouponServiceUtil.class,
					"addCoupon", _addCouponParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, code,
					autoCode, name, description, startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute,
					endDateMonth, endDateDay, endDateYear, endDateHour,
					endDateMinute, neverExpire, active, limitCategories,
					limitSkus, minOrder, discount, discountType, serviceContext);

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

			return (com.liferay.shopping.model.ShoppingCoupon)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCoupon(HttpPrincipal httpPrincipal, long groupId,
		long couponId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCouponServiceUtil.class,
					"deleteCoupon", _deleteCouponParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					couponId);

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

	public static com.liferay.shopping.model.ShoppingCoupon getCoupon(
		HttpPrincipal httpPrincipal, long groupId, long couponId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCouponServiceUtil.class,
					"getCoupon", _getCouponParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					couponId);

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

			return (com.liferay.shopping.model.ShoppingCoupon)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.shopping.model.ShoppingCoupon> search(
		HttpPrincipal httpPrincipal, long groupId, long companyId,
		java.lang.String code, boolean active, java.lang.String discountType,
		boolean andOperator, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCouponServiceUtil.class,
					"search", _searchParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					companyId, code, active, discountType, andOperator, start,
					end);

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

			return (java.util.List<com.liferay.shopping.model.ShoppingCoupon>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.shopping.model.ShoppingCoupon updateCoupon(
		HttpPrincipal httpPrincipal, long couponId, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		double minOrder, double discount, java.lang.String discountType,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ShoppingCouponServiceUtil.class,
					"updateCoupon", _updateCouponParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					couponId, name, description, startDateMonth, startDateDay,
					startDateYear, startDateHour, startDateMinute,
					endDateMonth, endDateDay, endDateYear, endDateHour,
					endDateMinute, neverExpire, active, limitCategories,
					limitSkus, minOrder, discount, discountType, serviceContext);

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

			return (com.liferay.shopping.model.ShoppingCoupon)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingCouponServiceHttp.class);
	private static final Class<?>[] _addCouponParameterTypes0 = new Class[] {
			java.lang.String.class, boolean.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			boolean.class, boolean.class, java.lang.String.class,
			java.lang.String.class, double.class, double.class,
			java.lang.String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCouponParameterTypes1 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getCouponParameterTypes2 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _searchParameterTypes3 = new Class[] {
			long.class, long.class, java.lang.String.class, boolean.class,
			java.lang.String.class, boolean.class, int.class, int.class
		};
	private static final Class<?>[] _updateCouponParameterTypes4 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, boolean.class,
			boolean.class, java.lang.String.class, java.lang.String.class,
			double.class, double.class, java.lang.String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}