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

package com.liferay.commerce.discount.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.service.CommerceDiscountServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceDiscountServiceUtil} service utility. The
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
 * @see CommerceDiscountServiceSoap
 * @see HttpPrincipal
 * @see CommerceDiscountServiceUtil
 * @generated
 */
@ProviderType
public class CommerceDiscountServiceHttp {
	public static com.liferay.commerce.discount.model.CommerceDiscount addCommerceDiscount(
		HttpPrincipal httpPrincipal, java.lang.String title,
		java.lang.String target, java.lang.String type,
		java.lang.String typeSettings, boolean useCouponCode,
		java.lang.String couponCode, java.lang.String limitationType,
		int limitationTimes, int numberOfUse, boolean cumulative,
		boolean usePercentage, java.math.BigDecimal level1,
		java.math.BigDecimal level2, java.math.BigDecimal level3,
		java.math.BigDecimal maximumDiscountAmount, boolean active,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceDiscountServiceUtil.class,
					"addCommerceDiscount", _addCommerceDiscountParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, title,
					target, type, typeSettings, useCouponCode, couponCode,
					limitationType, limitationTimes, numberOfUse, cumulative,
					usePercentage, level1, level2, level3,
					maximumDiscountAmount, active, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceDiscount(HttpPrincipal httpPrincipal,
		long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceDiscountServiceUtil.class,
					"deleteCommerceDiscount",
					_deleteCommerceDiscountParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceDiscountId);

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

	public static com.liferay.commerce.discount.model.CommerceDiscount updateCommerceDiscount(
		HttpPrincipal httpPrincipal, long commerceDiscountId,
		java.lang.String title, java.lang.String target, java.lang.String type,
		java.lang.String typeSettings, boolean useCouponCode,
		java.lang.String couponCode, java.lang.String limitationType,
		int limitationTimes, int numberOfUse, boolean cumulative,
		boolean usePercentage, java.math.BigDecimal level1,
		java.math.BigDecimal level2, java.math.BigDecimal level3,
		java.math.BigDecimal maximumDiscountAmount, boolean active,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceDiscountServiceUtil.class,
					"updateCommerceDiscount",
					_updateCommerceDiscountParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceDiscountId, title, target, type, typeSettings,
					useCouponCode, couponCode, limitationType, limitationTimes,
					numberOfUse, cumulative, usePercentage, level1, level2,
					level3, maximumDiscountAmount, active, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceDiscountServiceHttp.class);
	private static final Class<?>[] _addCommerceDiscountParameterTypes0 = new Class[] {
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, boolean.class,
			java.lang.String.class, java.lang.String.class, int.class, int.class,
			boolean.class, boolean.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, boolean.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceDiscountParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updateCommerceDiscountParameterTypes2 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, boolean.class,
			java.lang.String.class, java.lang.String.class, int.class, int.class,
			boolean.class, boolean.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, boolean.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}