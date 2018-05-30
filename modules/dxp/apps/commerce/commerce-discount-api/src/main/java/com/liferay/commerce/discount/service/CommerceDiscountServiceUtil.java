/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		String title, String target, boolean useCouponCode, String couponCode,
		boolean usePercentage, java.math.BigDecimal maximumDiscountAmount,
		java.math.BigDecimal level1, java.math.BigDecimal level2,
		java.math.BigDecimal level3, String limitationType,
		int limitationTimes, boolean cumulative, boolean active,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceDiscount(title, target, useCouponCode,
			couponCode, usePercentage, maximumDiscountAmount, level1, level2,
			level3, limitationType, limitationTimes, cumulative, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static void deleteCommerceDiscount(long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceDiscount(commerceDiscountId);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount getCommerceDiscount(
		long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceDiscount(commerceDiscountId);
	}

	public static java.util.List<com.liferay.commerce.discount.model.CommerceDiscount> getCommerceDiscounts(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.discount.model.CommerceDiscount> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceDiscounts(groupId, start, end, orderByComparator);
	}

	public static int getCommerceDiscountsCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceDiscountsCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.discount.model.CommerceDiscount> searchCommerceDiscounts(
		long companyId, long groupId, String keywords, int status, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCommerceDiscounts(companyId, groupId, keywords,
			status, start, end, sort);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscount updateCommerceDiscount(
		long commerceDiscountId, String title, String target,
		boolean useCouponCode, String couponCode, boolean usePercentage,
		java.math.BigDecimal maximumDiscountAmount,
		java.math.BigDecimal level1, java.math.BigDecimal level2,
		java.math.BigDecimal level3, String limitationType,
		int limitationTimes, boolean cumulative, boolean active,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceDiscount(commerceDiscountId, title, target,
			useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
			level1, level2, level3, limitationType, limitationTimes,
			cumulative, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
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