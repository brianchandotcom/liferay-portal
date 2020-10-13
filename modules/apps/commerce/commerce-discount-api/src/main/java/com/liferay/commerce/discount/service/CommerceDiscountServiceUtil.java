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

package com.liferay.commerce.discount.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceDiscount. This utility wraps
 * <code>com.liferay.commerce.discount.service.impl.CommerceDiscountServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceDiscountService
 * @generated
 */
public class CommerceDiscountServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.discount.service.impl.CommerceDiscountServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.discount.model.CommerceDiscount
			addCommerceDiscount(
				long userId, String title, String target, boolean useCouponCode,
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

		return getService().addCommerceDiscount(
			userId, title, target, useCouponCode, couponCode, usePercentage,
			maximumDiscountAmount, level1, level2, level3, level4,
			limitationType, limitationTimes, active, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			addCommerceDiscount(
				long userId, String title, String target, boolean useCouponCode,
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

		return getService().addCommerceDiscount(
			userId, title, target, useCouponCode, couponCode, usePercentage,
			maximumDiscountAmount, level, level1, level2, level3, level4,
			limitationType, limitationTimes, rulesConjunction, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addCommerceDiscount(String, long, String, String boolean,
	 String, boolean, BigDecimal, String, BigDecimal, BigDecimal,
	 BigDecimal, BigDecimal, BigDecimal, String, int, int,
	 boolean, boolean, int, int, int, int, int, int, int, int,
	 int, int, boolean, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.commerce.discount.model.CommerceDiscount
			addCommerceDiscount(
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
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceDiscount(
			userId, title, target, useCouponCode, couponCode, usePercentage,
			maximumDiscountAmount, level, level1, level2, level3, level4,
			limitationType, limitationTimes, limitationTimesPerAccount,
			rulesConjunction, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, externalReferenceCode,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			addCommerceDiscount(
				String externalReferenceCode, long userId, String title,
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

		return getService().addCommerceDiscount(
			externalReferenceCode, userId, title, target, useCouponCode,
			couponCode, usePercentage, maximumDiscountAmount, level, level1,
			level2, level3, level4, limitationType, limitationTimes,
			limitationTimesPerAccount, rulesConjunction, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static void deleteCommerceDiscount(long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceDiscount(commerceDiscountId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	public static com.liferay.commerce.discount.model.CommerceDiscount
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			fetchCommerceDiscount(long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceDiscount(commerceDiscountId);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			getCommerceDiscount(long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceDiscount(commerceDiscountId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscount>
				getCommerceDiscounts(long companyId, String couponCode)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceDiscounts(companyId, couponCode);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static int getCommerceDiscountsCount(
			long companyId, String couponCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceDiscountsCount(companyId, couponCode);
	}

	public static int getCommerceDiscountsCountByPricingClassId(
			long commercePricingClassId, String title)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().getCommerceDiscountsCountByPricingClassId(
			commercePricingClassId, title);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscount>
				searchByCommercePricingClassId(
					long commercePricingClassId, String title, int start,
					int end)
			throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().searchByCommercePricingClassId(
			commercePricingClassId, title, start, end);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.discount.model.CommerceDiscount>
				searchCommerceDiscounts(
					long companyId, String keywords, int status, int start,
					int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceDiscounts(
			companyId, keywords, status, start, end, sort);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			updateCommerceDiscount(
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
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceDiscount(
			commerceDiscountId, title, target, useCouponCode, couponCode,
			usePercentage, maximumDiscountAmount, level1, level2, level3,
			level4, limitationType, limitationTimes, active, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			updateCommerceDiscount(
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
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceDiscount(
			commerceDiscountId, title, target, useCouponCode, couponCode,
			usePercentage, maximumDiscountAmount, level, level1, level2, level3,
			level4, limitationType, limitationTimes, rulesConjunction, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			updateCommerceDiscount(
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
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceDiscount(
			commerceDiscountId, title, target, useCouponCode, couponCode,
			usePercentage, maximumDiscountAmount, level, level1, level2, level3,
			level4, limitationType, limitationTimes, limitationTimesPerAccount,
			rulesConjunction, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #updateCommerceDiscountExternalReferenceCode(String, long)}
	 */
	@Deprecated
	public static com.liferay.commerce.discount.model.CommerceDiscount
			updateCommerceDiscountExternalReferenceCode(
				long commerceDiscountId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceDiscountExternalReferenceCode(
			commerceDiscountId, externalReferenceCode);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			updateCommerceDiscountExternalReferenceCode(
				String externalReferenceCode, long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceDiscountExternalReferenceCode(
			externalReferenceCode, commerceDiscountId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #upsertCommerceDiscount(String, long, long, String, String,
	 boolean, String, boolean, BigDecimal, BigDecimal,
	 BigDecimal, BigDecimal, BigDecimal, String, int, boolean,
	 int, int, int, int, int, int, int, int, int, int, boolean,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.commerce.discount.model.CommerceDiscount
			upsertCommerceDiscount(
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
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceDiscount(
			userId, commerceDiscountId, title, target, useCouponCode,
			couponCode, usePercentage, maximumDiscountAmount, level1, level2,
			level3, level4, limitationType, limitationTimes, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #upsertCommerceDiscount(String, long, long, String, String,
	 boolean, String, boolean, BigDecimal, String, BigDecimal,
	 BigDecimal, BigDecimal, BigDecimal, String, int, boolean,
	 boolean, int, int, int, int, int, int, int, int, int, int,
	 boolean, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.commerce.discount.model.CommerceDiscount
			upsertCommerceDiscount(
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
				String externalReferenceCode, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceDiscount(
			userId, commerceDiscountId, title, target, useCouponCode,
			couponCode, usePercentage, maximumDiscountAmount, level, level1,
			level2, level3, level4, limitationType, limitationTimes,
			rulesConjunction, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, externalReferenceCode,
			neverExpire, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #upsertCommerceDiscount(String, long, long, String, String,
	 boolean, String, boolean, BigDecimal, String, BigDecimal,
	 BigDecimal, BigDecimal, BigDecimal, String, int, int,
	 boolean, boolean, int, int, int, int, int, int, int, int,
	 int, int, boolean, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.commerce.discount.model.CommerceDiscount
			upsertCommerceDiscount(
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
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceDiscount(
			userId, commerceDiscountId, title, target, useCouponCode,
			couponCode, usePercentage, maximumDiscountAmount, level, level1,
			level2, level3, level4, limitationType, limitationTimes,
			limitationTimesPerAccount, rulesConjunction, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			upsertCommerceDiscount(
				String externalReferenceCode, long userId,
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
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceDiscount(
			externalReferenceCode, userId, commerceDiscountId, title, target,
			useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
			level1, level2, level3, level4, limitationType, limitationTimes,
			active, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			upsertCommerceDiscount(
				String externalReferenceCode, long userId,
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
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceDiscount(
			externalReferenceCode, userId, commerceDiscountId, title, target,
			useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
			level, level1, level2, level3, level4, limitationType,
			limitationTimes, rulesConjunction, active, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount
			upsertCommerceDiscount(
				String externalReferenceCode, long userId,
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
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceDiscount(
			externalReferenceCode, userId, commerceDiscountId, title, target,
			useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
			level, level1, level2, level3, level4, limitationType,
			limitationTimes, limitationTimesPerAccount, rulesConjunction,
			active, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	public static CommerceDiscountService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceDiscountService, CommerceDiscountService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceDiscountService.class);

		ServiceTracker<CommerceDiscountService, CommerceDiscountService>
			serviceTracker =
				new ServiceTracker
					<CommerceDiscountService, CommerceDiscountService>(
						bundle.getBundleContext(),
						CommerceDiscountService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}