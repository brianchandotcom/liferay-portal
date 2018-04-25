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

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceDiscount. This utility wraps
 * {@link com.liferay.commerce.discount.service.impl.CommerceDiscountServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceDiscountService
 * @see com.liferay.commerce.discount.service.base.CommerceDiscountServiceBaseImpl
 * @see com.liferay.commerce.discount.service.impl.CommerceDiscountServiceImpl
 * @generated
 */
@ProviderType
public class CommerceDiscountServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.discount.service.impl.CommerceDiscountServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.discount.model.CommerceDiscount addCommerceDiscount(
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
		return getService()
				   .addCommerceDiscount(title, target, type, typeSettings,
			useCouponCode, couponCode, limitationType, limitationTimes,
			numberOfUse, cumulative, usePercentage, level1, level2, level3,
			maximumDiscountAmount, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static void deleteCommerceDiscount(long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceDiscount(commerceDiscountId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount updateCommerceDiscount(
		long commerceDiscountId, java.lang.String title,
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
		return getService()
				   .updateCommerceDiscount(commerceDiscountId, title, target,
			type, typeSettings, useCouponCode, couponCode, limitationType,
			limitationTimes, numberOfUse, cumulative, usePercentage, level1,
			level2, level3, maximumDiscountAmount, active, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static CommerceDiscountService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceDiscountService, CommerceDiscountService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceDiscountService.class);

		ServiceTracker<CommerceDiscountService, CommerceDiscountService> serviceTracker =
			new ServiceTracker<CommerceDiscountService, CommerceDiscountService>(bundle.getBundleContext(),
				CommerceDiscountService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}