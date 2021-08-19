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

package com.liferay.commerce.discount.service.impl;

import com.liferay.commerce.discount.constants.CommerceDiscountActionKeys;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.base.CommerceDiscountServiceBaseImpl;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.math.BigDecimal;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountServiceImpl
	extends CommerceDiscountServiceBaseImpl {

	@Override
	public CommerceDiscount addCommerceDiscount(
			String title, String target, boolean useCouponCode,
			String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceDiscountResourcePermission.getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceDiscountActionKeys.ADD_COMMERCE_DISCOUNT);

		return commerceDiscountLocalService.addCommerceDiscount(
			getUserId(), title, target, useCouponCode, couponCode,
			usePercentage, maximumDiscountAmount, level1, level2, level3,
			level4, limitationType, limitationTimes, active, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommerceDiscount addCommerceDiscount(
			String title, String target, boolean useCouponCode,
			String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, String level, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes,
			boolean rulesConjunction, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceDiscountResourcePermission.getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceDiscountActionKeys.ADD_COMMERCE_DISCOUNT);

		return commerceDiscountLocalService.addCommerceDiscount(
			getUserId(), title, target, useCouponCode, couponCode,
			usePercentage, maximumDiscountAmount, level, level1, level2, level3,
			level4, limitationType, limitationTimes, rulesConjunction, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public CommerceDiscount addCommerceDiscount(
			String externalReferenceCode, String title, String target,
			boolean useCouponCode, String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, String level, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes,
			int limitationTimesPerAccount, boolean rulesConjunction,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceDiscountResourcePermission.getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceDiscountActionKeys.ADD_COMMERCE_DISCOUNT);

		return commerceDiscountLocalService.addCommerceDiscount(
			externalReferenceCode, getUserId(), title, target, useCouponCode,
			couponCode, usePercentage, maximumDiscountAmount, level, level1,
			level2, level3, level4, limitationType, limitationTimes,
			limitationTimesPerAccount, rulesConjunction, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public CommerceDiscount addOrUpdateCommerceDiscount(
			String externalReferenceCode, long commerceDiscountId, String title,
			String target, boolean useCouponCode, String couponCode,
			boolean usePercentage, BigDecimal maximumDiscountAmount,
			BigDecimal level1, BigDecimal level2, BigDecimal level3,
			BigDecimal level4, String limitationType, int limitationTimes,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return addOrUpdateCommerceDiscount(
			externalReferenceCode, commerceDiscountId, title, target,
			useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
			StringPool.BLANK, level1, level2, level3, level4, limitationType,
			limitationTimes, true, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommerceDiscount addOrUpdateCommerceDiscount(
			String externalReferenceCode, long commerceDiscountId, String title,
			String target, boolean useCouponCode, String couponCode,
			boolean usePercentage, BigDecimal maximumDiscountAmount,
			String level, BigDecimal level1, BigDecimal level2,
			BigDecimal level3, BigDecimal level4, String limitationType,
			int limitationTimes, boolean rulesConjunction, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return addOrUpdateCommerceDiscount(
			externalReferenceCode, commerceDiscountId, title, target,
			useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
			level, level1, level2, level3, level4, limitationType,
			limitationTimes, 0, rulesConjunction, active, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommerceDiscount addOrUpdateCommerceDiscount(
			String externalReferenceCode, long commerceDiscountId, String title,
			String target, boolean useCouponCode, String couponCode,
			boolean usePercentage, BigDecimal maximumDiscountAmount,
			String level, BigDecimal level1, BigDecimal level2,
			BigDecimal level3, BigDecimal level4, String limitationType,
			int limitationTimes, int limitationTimesPerAccount,
			boolean rulesConjunction, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		// Update

		if (commerceDiscountId > 0) {
			try {
				return updateCommerceDiscount(
					commerceDiscountId, title, target, useCouponCode,
					couponCode, usePercentage, maximumDiscountAmount, level,
					level1, level2, level3, level4, limitationType,
					limitationTimes, limitationTimesPerAccount,
					rulesConjunction, active, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);
			}
			catch (NoSuchDiscountException noSuchDiscountException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find discount with ID: " +
							commerceDiscountId,
						noSuchDiscountException);
				}
			}
		}

		if (!Validator.isBlank(externalReferenceCode)) {
			CommerceDiscount commerceDiscount =
				commerceDiscountPersistence.fetchByC_ERC(
					serviceContext.getCompanyId(), externalReferenceCode);

			if (commerceDiscount != null) {
				return updateCommerceDiscount(
					commerceDiscountId, title, target, useCouponCode,
					couponCode, usePercentage, maximumDiscountAmount, level,
					level1, level2, level3, level4, limitationType,
					limitationTimes, limitationTimesPerAccount,
					rulesConjunction, active, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);
			}
		}

		// Add

		return addCommerceDiscount(
			externalReferenceCode, title, target, useCouponCode, couponCode,
			usePercentage, maximumDiscountAmount, level, level1, level2, level3,
			level4, limitationType, limitationTimes, limitationTimesPerAccount,
			rulesConjunction, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public void deleteCommerceDiscount(long commerceDiscountId)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.DELETE);

		commerceDiscountLocalService.deleteCommerceDiscount(commerceDiscountId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Override
	public CommerceDiscount fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return fetchByExternalReferenceCode(externalReferenceCode, companyId);
	}

	@Override
	public CommerceDiscount fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		CommerceDiscount commerceDiscount =
			commerceDiscountLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, companyId);

		if (commerceDiscount != null) {
			_commerceDiscountResourcePermission.check(
				getPermissionChecker(), commerceDiscount, ActionKeys.VIEW);
		}

		return commerceDiscount;
	}

	@Override
	public CommerceDiscount fetchCommerceDiscount(long commerceDiscountId)
		throws PortalException {

		CommerceDiscount commerceDiscount =
			commerceDiscountLocalService.fetchCommerceDiscount(
				commerceDiscountId);

		if (commerceDiscount != null) {
			_commerceDiscountResourcePermission.check(
				getPermissionChecker(), commerceDiscount, ActionKeys.VIEW);
		}

		return commerceDiscount;
	}

	@Override
	public CommerceDiscount getCommerceDiscount(long commerceDiscountId)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.VIEW);

		return commerceDiscountLocalService.getCommerceDiscount(
			commerceDiscountId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public List<CommerceDiscount> getCommerceDiscounts(
			long companyId, String couponCode)
		throws PortalException {

		return commerceDiscountPersistence.filterFindByC_C(
			companyId, couponCode);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public int getCommerceDiscountsCount(long companyId, String couponCode)
		throws PortalException {

		return commerceDiscountPersistence.filterCountByC_C(
			companyId, couponCode);
	}

	@Override
	public int getCommerceDiscountsCountByPricingClassId(
			long commercePricingClassId, String title)
		throws PrincipalException {

		return commerceDiscountFinder.countByCommercePricingClassId(
			commercePricingClassId, title, true);
	}

	@Override
	public List<CommerceDiscount> searchByCommercePricingClassId(
			long commercePricingClassId, String title, int start, int end)
		throws PrincipalException {

		return commerceDiscountFinder.findByCommercePricingClassId(
			commercePricingClassId, title, start, end, true);
	}

	@Override
	public BaseModelSearchResult<CommerceDiscount> searchCommerceDiscounts(
			long companyId, String keywords, int status, int start, int end,
			Sort sort)
		throws PortalException {

		List<CommerceChannel> commerceChannels = _commerceChannelService.search(
			companyId);

		Stream<CommerceChannel> stream = commerceChannels.stream();

		long[] commerceChannelGroupIds = stream.mapToLong(
			CommerceChannel::getGroupId
		).toArray();

		return commerceDiscountLocalService.searchCommerceDiscounts(
			companyId, commerceChannelGroupIds, keywords, status, start, end,
			sort);
	}

	@Override
	public CommerceDiscount updateCommerceDiscount(
			long commerceDiscountId, String title, String target,
			boolean useCouponCode, String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.UPDATE);

		return commerceDiscountLocalService.updateCommerceDiscount(
			commerceDiscountId, title, target, useCouponCode, couponCode,
			usePercentage, maximumDiscountAmount, level1, level2, level3,
			level4, limitationType, limitationTimes, active, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommerceDiscount updateCommerceDiscount(
			long commerceDiscountId, String title, String target,
			boolean useCouponCode, String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, String level, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes,
			boolean rulesConjunction, boolean active, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.UPDATE);

		return commerceDiscountLocalService.updateCommerceDiscount(
			commerceDiscountId, title, target, useCouponCode, couponCode,
			usePercentage, maximumDiscountAmount, level, level1, level2, level3,
			level4, limitationType, limitationTimes, rulesConjunction, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public CommerceDiscount updateCommerceDiscount(
			long commerceDiscountId, String title, String target,
			boolean useCouponCode, String couponCode, boolean usePercentage,
			BigDecimal maximumDiscountAmount, String level, BigDecimal level1,
			BigDecimal level2, BigDecimal level3, BigDecimal level4,
			String limitationType, int limitationTimes,
			int limitationTimesPerAccount, boolean rulesConjunction,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.UPDATE);

		return commerceDiscountLocalService.updateCommerceDiscount(
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
	 *             #updateCommerceDiscountExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Override
	public CommerceDiscount updateCommerceDiscountExternalReferenceCode(
			long commerceDiscountId, String externalReferenceCode)
		throws PortalException {

		return updateCommerceDiscountExternalReferenceCode(
			externalReferenceCode, commerceDiscountId);
	}

	@Override
	public CommerceDiscount updateCommerceDiscountExternalReferenceCode(
			String externalReferenceCode, long commerceDiscountId)
		throws PortalException {

		_commerceDiscountResourcePermission.check(
			getPermissionChecker(), commerceDiscountId, ActionKeys.UPDATE);

		return commerceDiscountLocalService.
			updateCommerceDiscountExternalReferenceCode(
				externalReferenceCode, commerceDiscountId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountServiceImpl.class);

	private static volatile ModelResourcePermission<CommerceDiscount>
		_commerceDiscountResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceDiscountServiceImpl.class,
				"_commerceDiscountResourcePermission", CommerceDiscount.class);

	@ServiceReference(type = CommerceChannelService.class)
	private CommerceChannelService _commerceChannelService;

}