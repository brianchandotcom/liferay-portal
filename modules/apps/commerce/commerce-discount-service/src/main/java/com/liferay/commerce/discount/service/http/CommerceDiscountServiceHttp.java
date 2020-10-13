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

import com.liferay.commerce.discount.service.CommerceDiscountServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CommerceDiscountServiceUtil</code> service
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
 * @see CommerceDiscountServiceSoap
 * @generated
 */
public class CommerceDiscountServiceHttp {

	public static com.liferay.commerce.discount.model.CommerceDiscount
			addCommerceDiscount(
				HttpPrincipal httpPrincipal, long userId, String title,
				String target, boolean useCouponCode, String couponCode,
				boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes, boolean active,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "addCommerceDiscount",
				_addCommerceDiscountParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, title, target, useCouponCode, couponCode,
				usePercentage, maximumDiscountAmount, level1, level2, level3,
				level4, limitationType, limitationTimes, active,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			addCommerceDiscount(
				HttpPrincipal httpPrincipal, long userId, String title,
				String target, boolean useCouponCode, String couponCode,
				boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				boolean rulesConjunction, boolean active, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "addCommerceDiscount",
				_addCommerceDiscountParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, title, target, useCouponCode, couponCode,
				usePercentage, maximumDiscountAmount, level, level1, level2,
				level3, level4, limitationType, limitationTimes,
				rulesConjunction, active, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			addCommerceDiscount(
				HttpPrincipal httpPrincipal, long userId, String title,
				String target, boolean useCouponCode, String couponCode,
				boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				int limitationTimesPerAccount, boolean rulesConjunction,
				boolean active, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "addCommerceDiscount",
				_addCommerceDiscountParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, title, target, useCouponCode, couponCode,
				usePercentage, maximumDiscountAmount, level, level1, level2,
				level3, level4, limitationType, limitationTimes,
				limitationTimesPerAccount, rulesConjunction, active,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, externalReferenceCode, neverExpire,
				serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			addCommerceDiscount(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long userId, String title, String target, boolean useCouponCode,
				String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				int limitationTimesPerAccount, boolean rulesConjunction,
				boolean active, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "addCommerceDiscount",
				_addCommerceDiscountParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, userId, title, target,
				useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
				level, level1, level2, level3, level4, limitationType,
				limitationTimes, limitationTimesPerAccount, rulesConjunction,
				active, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteCommerceDiscount(
			HttpPrincipal httpPrincipal, long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "deleteCommerceDiscount",
				_deleteCommerceDiscountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId);

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

	public static com.liferay.commerce.discount.model.CommerceDiscount
			fetchByExternalReferenceCode(
				HttpPrincipal httpPrincipal, long companyId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class,
				"fetchByExternalReferenceCode",
				_fetchByExternalReferenceCodeParameterTypes5);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			fetchByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class,
				"fetchByExternalReferenceCode",
				_fetchByExternalReferenceCodeParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, companyId);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			fetchCommerceDiscount(
				HttpPrincipal httpPrincipal, long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "fetchCommerceDiscount",
				_fetchCommerceDiscountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			getCommerceDiscount(
				HttpPrincipal httpPrincipal, long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "getCommerceDiscount",
				_getCommerceDiscountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscount>
				getCommerceDiscounts(
					HttpPrincipal httpPrincipal, long companyId,
					String couponCode)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "getCommerceDiscounts",
				_getCommerceDiscountsParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, couponCode);

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
				<com.liferay.commerce.discount.model.CommerceDiscount>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getCommerceDiscountsCount(
			HttpPrincipal httpPrincipal, long companyId, String couponCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "getCommerceDiscountsCount",
				_getCommerceDiscountsCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, couponCode);

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

	public static int getCommerceDiscountsCountByPricingClassId(
			HttpPrincipal httpPrincipal, long commercePricingClassId,
			String title)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class,
				"getCommerceDiscountsCountByPricingClassId",
				_getCommerceDiscountsCountByPricingClassIdParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePricingClassId, title);

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

	public static java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscount>
				searchByCommercePricingClassId(
					HttpPrincipal httpPrincipal, long commercePricingClassId,
					String title, int start, int end)
			throws com.liferay.portal.kernel.security.auth.PrincipalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class,
				"searchByCommercePricingClassId",
				_searchByCommercePricingClassIdParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commercePricingClassId, title, start, end);

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
				<com.liferay.commerce.discount.model.CommerceDiscount>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.discount.model.CommerceDiscount>
				searchCommerceDiscounts(
					HttpPrincipal httpPrincipal, long companyId,
					String keywords, int status, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "searchCommerceDiscounts",
				_searchCommerceDiscountsParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, keywords, status, start, end, sort);

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

			return (com.liferay.portal.kernel.search.BaseModelSearchResult
				<com.liferay.commerce.discount.model.CommerceDiscount>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			updateCommerceDiscount(
				HttpPrincipal httpPrincipal, long commerceDiscountId,
				String title, String target, boolean useCouponCode,
				String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes, boolean active,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "updateCommerceDiscount",
				_updateCommerceDiscountParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, title, target, useCouponCode,
				couponCode, usePercentage, maximumDiscountAmount, level1,
				level2, level3, level4, limitationType, limitationTimes, active,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			updateCommerceDiscount(
				HttpPrincipal httpPrincipal, long commerceDiscountId,
				String title, String target, boolean useCouponCode,
				String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				boolean rulesConjunction, boolean active, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "updateCommerceDiscount",
				_updateCommerceDiscountParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, title, target, useCouponCode,
				couponCode, usePercentage, maximumDiscountAmount, level, level1,
				level2, level3, level4, limitationType, limitationTimes,
				rulesConjunction, active, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			updateCommerceDiscount(
				HttpPrincipal httpPrincipal, long commerceDiscountId,
				String title, String target, boolean useCouponCode,
				String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				int limitationTimesPerAccount, boolean rulesConjunction,
				boolean active, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "updateCommerceDiscount",
				_updateCommerceDiscountParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, title, target, useCouponCode,
				couponCode, usePercentage, maximumDiscountAmount, level, level1,
				level2, level3, level4, limitationType, limitationTimes,
				limitationTimesPerAccount, rulesConjunction, active,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			updateCommerceDiscountExternalReferenceCode(
				HttpPrincipal httpPrincipal, long commerceDiscountId,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class,
				"updateCommerceDiscountExternalReferenceCode",
				_updateCommerceDiscountExternalReferenceCodeParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, commerceDiscountId, externalReferenceCode);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			updateCommerceDiscountExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class,
				"updateCommerceDiscountExternalReferenceCode",
				_updateCommerceDiscountExternalReferenceCodeParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, commerceDiscountId);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			upsertCommerceDiscount(
				HttpPrincipal httpPrincipal, long userId,
				long commerceDiscountId, String title, String target,
				boolean useCouponCode, String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes, boolean active,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "upsertCommerceDiscount",
				_upsertCommerceDiscountParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, commerceDiscountId, title, target,
				useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
				level1, level2, level3, level4, limitationType, limitationTimes,
				active, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, externalReferenceCode, neverExpire,
				serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			upsertCommerceDiscount(
				HttpPrincipal httpPrincipal, long userId,
				long commerceDiscountId, String title, String target,
				boolean useCouponCode, String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				boolean rulesConjunction, boolean active, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				String externalReferenceCode, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "upsertCommerceDiscount",
				_upsertCommerceDiscountParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, commerceDiscountId, title, target,
				useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
				level, level1, level2, level3, level4, limitationType,
				limitationTimes, rulesConjunction, active, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				externalReferenceCode, neverExpire, serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			upsertCommerceDiscount(
				HttpPrincipal httpPrincipal, long userId,
				long commerceDiscountId, String title, String target,
				boolean useCouponCode, String couponCode, boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				int limitationTimesPerAccount, boolean rulesConjunction,
				boolean active, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "upsertCommerceDiscount",
				_upsertCommerceDiscountParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, commerceDiscountId, title, target,
				useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
				level, level1, level2, level3, level4, limitationType,
				limitationTimes, limitationTimesPerAccount, rulesConjunction,
				active, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, externalReferenceCode, neverExpire,
				serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			upsertCommerceDiscount(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long userId, long commerceDiscountId, String title,
				String target, boolean useCouponCode, String couponCode,
				boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes, boolean active,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "upsertCommerceDiscount",
				_upsertCommerceDiscountParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, userId, commerceDiscountId,
				title, target, useCouponCode, couponCode, usePercentage,
				maximumDiscountAmount, level1, level2, level3, level4,
				limitationType, limitationTimes, active, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			upsertCommerceDiscount(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long userId, long commerceDiscountId, String title,
				String target, boolean useCouponCode, String couponCode,
				boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				boolean rulesConjunction, boolean active, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "upsertCommerceDiscount",
				_upsertCommerceDiscountParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, userId, commerceDiscountId,
				title, target, useCouponCode, couponCode, usePercentage,
				maximumDiscountAmount, level, level1, level2, level3, level4,
				limitationType, limitationTimes, rulesConjunction, active,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			upsertCommerceDiscount(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long userId, long commerceDiscountId, String title,
				String target, boolean useCouponCode, String couponCode,
				boolean usePercentage,
				java.math.BigDecimal maximumDiscountAmount, String level,
				java.math.BigDecimal level1, java.math.BigDecimal level2,
				java.math.BigDecimal level3, java.math.BigDecimal level4,
				String limitationType, int limitationTimes,
				int limitationTimesPerAccount, boolean rulesConjunction,
				boolean active, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CommerceDiscountServiceUtil.class, "upsertCommerceDiscount",
				_upsertCommerceDiscountParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, userId, commerceDiscountId,
				title, target, useCouponCode, couponCode, usePercentage,
				maximumDiscountAmount, level, level1, level2, level3, level4,
				limitationType, limitationTimes, limitationTimesPerAccount,
				rulesConjunction, active, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscount)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceDiscountServiceHttp.class);

	private static final Class<?>[] _addCommerceDiscountParameterTypes0 =
		new Class[] {
			long.class, String.class, String.class, boolean.class, String.class,
			boolean.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, int.class, boolean.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCommerceDiscountParameterTypes1 =
		new Class[] {
			long.class, String.class, String.class, boolean.class, String.class,
			boolean.class, java.math.BigDecimal.class, String.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, int.class, boolean.class, boolean.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCommerceDiscountParameterTypes2 =
		new Class[] {
			long.class, String.class, String.class, boolean.class, String.class,
			boolean.class, java.math.BigDecimal.class, String.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, int.class, int.class, boolean.class, boolean.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, String.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCommerceDiscountParameterTypes3 =
		new Class[] {
			String.class, long.class, String.class, String.class, boolean.class,
			String.class, boolean.class, java.math.BigDecimal.class,
			String.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, String.class, int.class, int.class,
			boolean.class, boolean.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceDiscountParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[]
		_fetchByExternalReferenceCodeParameterTypes5 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_fetchByExternalReferenceCodeParameterTypes6 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _fetchCommerceDiscountParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _getCommerceDiscountParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[] _getCommerceDiscountsParameterTypes9 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getCommerceDiscountsCountParameterTypes10 =
		new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getCommerceDiscountsCountByPricingClassIdParameterTypes11 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_searchByCommercePricingClassIdParameterTypes12 = new Class[] {
			long.class, String.class, int.class, int.class
		};
	private static final Class<?>[] _searchCommerceDiscountsParameterTypes13 =
		new Class[] {
			long.class, String.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateCommerceDiscountParameterTypes14 =
		new Class[] {
			long.class, String.class, String.class, boolean.class, String.class,
			boolean.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, int.class, boolean.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateCommerceDiscountParameterTypes15 =
		new Class[] {
			long.class, String.class, String.class, boolean.class, String.class,
			boolean.class, java.math.BigDecimal.class, String.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, int.class, boolean.class, boolean.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateCommerceDiscountParameterTypes16 =
		new Class[] {
			long.class, String.class, String.class, boolean.class, String.class,
			boolean.class, java.math.BigDecimal.class, String.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, int.class, int.class, boolean.class, boolean.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_updateCommerceDiscountExternalReferenceCodeParameterTypes17 =
			new Class[] {long.class, String.class};
	private static final Class<?>[]
		_updateCommerceDiscountExternalReferenceCodeParameterTypes18 =
			new Class[] {String.class, long.class};
	private static final Class<?>[] _upsertCommerceDiscountParameterTypes19 =
		new Class[] {
			long.class, long.class, String.class, String.class, boolean.class,
			String.class, boolean.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, int.class, boolean.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, String.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _upsertCommerceDiscountParameterTypes20 =
		new Class[] {
			long.class, long.class, String.class, String.class, boolean.class,
			String.class, boolean.class, java.math.BigDecimal.class,
			String.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, String.class, int.class, boolean.class,
			boolean.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			String.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _upsertCommerceDiscountParameterTypes21 =
		new Class[] {
			long.class, long.class, String.class, String.class, boolean.class,
			String.class, boolean.class, java.math.BigDecimal.class,
			String.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, String.class, int.class, int.class,
			boolean.class, boolean.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, String.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _upsertCommerceDiscountParameterTypes22 =
		new Class[] {
			String.class, long.class, long.class, String.class, String.class,
			boolean.class, String.class, boolean.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, String.class, int.class, boolean.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _upsertCommerceDiscountParameterTypes23 =
		new Class[] {
			String.class, long.class, long.class, String.class, String.class,
			boolean.class, String.class, boolean.class,
			java.math.BigDecimal.class, String.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, int.class, boolean.class, boolean.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _upsertCommerceDiscountParameterTypes24 =
		new Class[] {
			String.class, long.class, long.class, String.class, String.class,
			boolean.class, String.class, boolean.class,
			java.math.BigDecimal.class, String.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			java.math.BigDecimal.class, java.math.BigDecimal.class,
			String.class, int.class, int.class, boolean.class, boolean.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}