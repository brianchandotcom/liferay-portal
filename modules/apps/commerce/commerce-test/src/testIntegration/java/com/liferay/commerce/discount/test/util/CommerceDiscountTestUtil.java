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

package com.liferay.commerce.discount.test.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.commerce.discount.service.CommerceDiscountAccountRelLocalServiceUtil;
import com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelLocalServiceUtil;
import com.liferay.commerce.discount.service.CommerceDiscountLocalServiceUtil;
import com.liferay.commerce.discount.service.CommerceDiscountRelLocalServiceUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CommerceChannelRelLocalServiceUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.math.BigDecimal;

import java.util.Calendar;

/**
 * @author Luca Pellizzon
 */
public class CommerceDiscountTestUtil {

	public static CommerceDiscount addAccountAndChannelDiscount(
			long groupId, long commerceAccountId, long commerceChannelId,
			String level, long cpDefinitionId)
		throws Exception {

		CommerceDiscount commerceDiscount = addPercentageCommerceDiscount(
			groupId, BigDecimal.valueOf(RandomTestUtil.randomDouble()), level,
			CommerceDiscountConstants.TARGET_PRODUCTS, cpDefinitionId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceDiscountAccountRelLocalServiceUtil.
			addCommerceDiscountAccountRel(
				serviceContext.getUserId(),
				commerceDiscount.getCommerceDiscountId(), commerceAccountId,
				serviceContext);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceDiscount.class.getName(),
			commerceDiscount.getCommerceDiscountId(), commerceChannelId,
			serviceContext);

		return commerceDiscount;
	}

	public static CommerceDiscount addAccountAndChannelOrderDiscount(
			long groupId, long commerceAccountId, long commerceChannelId,
			String type)
		throws Exception {

		CommerceDiscount commerceDiscount = addFixedCommerceDiscount(
			groupId, RandomTestUtil.randomDouble(), type, null);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceDiscountAccountRelLocalServiceUtil.
			addCommerceDiscountAccountRel(
				serviceContext.getUserId(),
				commerceDiscount.getCommerceDiscountId(), commerceAccountId,
				serviceContext);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceDiscount.class.getName(),
			commerceDiscount.getCommerceDiscountId(), commerceChannelId,
			serviceContext);

		return commerceDiscount;
	}

	public static CommerceDiscount addAccountDiscount(
			long groupId, long commerceAccountId, String level,
			long cpDefinitionId)
		throws Exception {

		CommerceDiscount commerceDiscount = addPercentageCommerceDiscount(
			groupId, BigDecimal.valueOf(RandomTestUtil.randomDouble()), level,
			CommerceDiscountConstants.TARGET_PRODUCTS, cpDefinitionId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceDiscountAccountRelLocalServiceUtil.
			addCommerceDiscountAccountRel(
				serviceContext.getUserId(),
				commerceDiscount.getCommerceDiscountId(), commerceAccountId,
				serviceContext);

		return commerceDiscount;
	}

	public static CommerceDiscount addAccountGroupAndChannelDiscount(
			long groupId, long[] commerceAccountGroupIds,
			long commerceChannelId, String level, long cpDefinitionId)
		throws Exception {

		CommerceDiscount commerceDiscount = addPercentageCommerceDiscount(
			groupId, BigDecimal.valueOf(RandomTestUtil.randomDouble()), level,
			CommerceDiscountConstants.TARGET_PRODUCTS, cpDefinitionId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		for (long commerceAccountGroupId : commerceAccountGroupIds) {
			CommerceDiscountCommerceAccountGroupRelLocalServiceUtil.
				addCommerceDiscountCommerceAccountGroupRel(
					serviceContext.getUserId(),
					commerceDiscount.getCommerceDiscountId(),
					commerceAccountGroupId, serviceContext);
		}

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceDiscount.class.getName(),
			commerceDiscount.getCommerceDiscountId(), commerceChannelId,
			serviceContext);

		return commerceDiscount;
	}

	public static CommerceDiscount addAccountGroupAndChannelOrderDiscount(
			long groupId, long[] commerceAccountGroupIds,
			long commerceChannelId, String type)
		throws Exception {

		CommerceDiscount commerceDiscount = addFixedCommerceDiscount(
			groupId, RandomTestUtil.randomDouble(), type, null);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		for (long commerceAccountGroupId : commerceAccountGroupIds) {
			CommerceDiscountCommerceAccountGroupRelLocalServiceUtil.
				addCommerceDiscountCommerceAccountGroupRel(
					serviceContext.getUserId(),
					commerceDiscount.getCommerceDiscountId(),
					commerceAccountGroupId, serviceContext);
		}

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceDiscount.class.getName(),
			commerceDiscount.getCommerceDiscountId(), commerceChannelId,
			serviceContext);

		return commerceDiscount;
	}

	public static CommerceDiscount addAccountGroupDiscount(
			long groupId, long[] commerceAccountGroupIds, String level,
			long cpDefinitionId)
		throws Exception {

		CommerceDiscount commerceDiscount = addPercentageCommerceDiscount(
			groupId, BigDecimal.valueOf(RandomTestUtil.randomDouble()), level,
			CommerceDiscountConstants.TARGET_PRODUCTS, cpDefinitionId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		for (long commerceAccountGroupId : commerceAccountGroupIds) {
			CommerceDiscountCommerceAccountGroupRelLocalServiceUtil.
				addCommerceDiscountCommerceAccountGroupRel(
					serviceContext.getUserId(),
					commerceDiscount.getCommerceDiscountId(),
					commerceAccountGroupId, serviceContext);
		}

		return commerceDiscount;
	}

	public static CommerceDiscount addAccountGroupOrderDiscount(
			long groupId, long[] commerceAccountGroupIds, String type)
		throws Exception {

		CommerceDiscount commerceDiscount = addFixedCommerceDiscount(
			groupId, RandomTestUtil.randomDouble(), type, null);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		for (long commerceAccountGroupId : commerceAccountGroupIds) {
			CommerceDiscountCommerceAccountGroupRelLocalServiceUtil.
				addCommerceDiscountCommerceAccountGroupRel(
					serviceContext.getUserId(),
					commerceDiscount.getCommerceDiscountId(),
					commerceAccountGroupId, serviceContext);
		}

		return commerceDiscount;
	}

	public static CommerceDiscountCommerceAccountGroupRel
			addAccountGroupToDiscount(
				long userId, CommerceDiscount commerceDiscount)
		throws Exception {

		CommerceAccountGroup commerceAccountGroup = null;

		return CommerceDiscountCommerceAccountGroupRelLocalServiceUtil.
			addCommerceDiscountCommerceAccountGroupRel(
				userId, commerceDiscount.getCommerceDiscountId(),
				commerceAccountGroup.getCommerceAccountGroupId(),
				ServiceContextTestUtil.getServiceContext());
	}

	public static CommerceDiscount addAccountOrderDiscount(
			long groupId, long commerceAccountId, String type)
		throws Exception {

		CommerceDiscount commerceDiscount = addFixedCommerceDiscount(
			groupId, RandomTestUtil.randomDouble(), type, null);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceDiscountAccountRelLocalServiceUtil.
			addCommerceDiscountAccountRel(
				serviceContext.getUserId(),
				commerceDiscount.getCommerceDiscountId(), commerceAccountId,
				serviceContext);

		return commerceDiscount;
	}

	public static CommerceDiscount addChannelDiscount(
			long groupId, long commerceChannelId, String level,
			long cpDefinitionId)
		throws Exception {

		CommerceDiscount commerceDiscount = addPercentageCommerceDiscount(
			groupId, BigDecimal.valueOf(RandomTestUtil.randomDouble()), level,
			CommerceDiscountConstants.TARGET_PRODUCTS, cpDefinitionId);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceDiscount.class.getName(),
			commerceDiscount.getCommerceDiscountId(), commerceChannelId,
			ServiceContextTestUtil.getServiceContext(groupId));

		return commerceDiscount;
	}

	public static CommerceDiscount addChannelOrderDiscount(
			long groupId, long commerceChannelId, String type)
		throws Exception {

		CommerceDiscount commerceDiscount = addFixedCommerceDiscount(
			groupId, RandomTestUtil.randomDouble(), type, null);

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceDiscount.class.getName(),
			commerceDiscount.getCommerceDiscountId(), commerceChannelId,
			ServiceContextTestUtil.getServiceContext(groupId));

		return commerceDiscount;
	}

	public static CommerceDiscount addCouponDiscount(
			long groupId, double amount, String couponCode,
			String limitationType, int limitationTimes,
			int limitationTimesPerAccount, String target, long... targetIds)
		throws Exception {

		CommerceDiscount commerceDiscount = addFixedCommerceDiscount(
			groupId, amount, target, targetIds);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		User user = UserLocalServiceUtil.getDefaultUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		return CommerceDiscountLocalServiceUtil.updateCommerceDiscount(
			commerceDiscount.getCommerceDiscountId(),
			commerceDiscount.getTitle(), commerceDiscount.getTarget(), true,
			couponCode, commerceDiscount.isUsePercentage(),
			commerceDiscount.getMaximumDiscountAmount(),
			commerceDiscount.getLevel(), commerceDiscount.getLevel1(),
			commerceDiscount.getLevel2(), commerceDiscount.getLevel3(),
			commerceDiscount.getLevel4(), limitationType, limitationTimes,
			limitationTimesPerAccount, commerceDiscount.isRulesConjunction(),
			commerceDiscount.isActive(), calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), true, serviceContext);
	}

	public static CommerceDiscount addCouponDiscount(
			long groupId, double amount, String couponCode, String target,
			long... targetIds)
		throws Exception {

		return addCouponDiscount(
			groupId, amount, couponCode,
			CommerceDiscountConstants.LIMITATION_TYPE_LIMITED, 1, 0, target,
			targetIds);
	}

	public static CommerceDiscountCommerceAccountGroupRel
			addDiscountCommerceAccountGroupRel(
				CommerceDiscount commerceDiscount,
				CommerceAccountGroup commerceAccountGroup)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		return CommerceDiscountCommerceAccountGroupRelLocalServiceUtil.
			addCommerceDiscountCommerceAccountGroupRel(
				serviceContext.getUserId(),
				commerceDiscount.getCommerceDiscountId(),
				commerceAccountGroup.getCommerceAccountGroupId(),
				serviceContext);
	}

	public static CommerceDiscount addFixedCommerceDiscount(
			long groupId, double amount, String target, long... targetIds)
		throws Exception {

		BigDecimal discount = BigDecimal.valueOf(amount);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		User user = UserLocalServiceUtil.getDefaultUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		CommerceDiscount commerceDiscount =
			CommerceDiscountLocalServiceUtil.addCommerceDiscount(
				user.getUserId(), RandomTestUtil.randomString(), target, false,
				null, false, BigDecimal.ZERO, discount, BigDecimal.ZERO,
				BigDecimal.ZERO, BigDecimal.ZERO,
				CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED, 0, true,
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true, serviceContext);

		_addTargetDetails(groupId, commerceDiscount, target, targetIds);

		return commerceDiscount;
	}

	public static CommerceDiscount addPercentageCommerceDiscount(
			long groupId, BigDecimal percentage, String level, String target,
			long... targetIds)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		User user = UserLocalServiceUtil.getDefaultUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		CommerceDiscount commerceDiscount =
			CommerceDiscountLocalServiceUtil.addCommerceDiscount(
				user.getUserId(), RandomTestUtil.randomString(), target, false,
				null, true, BigDecimal.valueOf(10000), level, percentage,
				BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
				CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED, 0, true,
				true, calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true, serviceContext);

		_addTargetDetails(groupId, commerceDiscount, target, targetIds);

		return commerceDiscount;
	}

	public static CommerceDiscount addPercentageCommerceDiscount(
			long groupId, double percentage1, double percentage2,
			double percentage3, double percentage4, String target,
			long... targetIds)
		throws Exception {

		BigDecimal level1 = BigDecimal.valueOf(percentage1);
		BigDecimal level2 = BigDecimal.valueOf(percentage2);
		BigDecimal level3 = BigDecimal.valueOf(percentage3);
		BigDecimal level4 = BigDecimal.valueOf(percentage4);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		User user = UserLocalServiceUtil.getDefaultUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		CommerceDiscount commerceDiscount =
			CommerceDiscountLocalServiceUtil.addCommerceDiscount(
				user.getUserId(), RandomTestUtil.randomString(), target, false,
				null, true, BigDecimal.valueOf(10000), level1, level2, level3,
				level4, CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED, 0,
				true, calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true, serviceContext);

		_addTargetDetails(groupId, commerceDiscount, target, targetIds);

		return commerceDiscount;
	}

	private static void _addDiscountCategoryRel(
			long groupId, CommerceDiscount commerceDiscount, long... targetIds)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		for (long id : targetIds) {
			CommerceDiscountRelLocalServiceUtil.addCommerceDiscountRel(
				commerceDiscount.getCommerceDiscountId(),
				AssetCategory.class.getName(), id, serviceContext);
		}
	}

	private static void _addDiscountProductRel(
			long groupId, CommerceDiscount commerceDiscount, long... targetIds)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		for (long id : targetIds) {
			CommerceDiscountRelLocalServiceUtil.addCommerceDiscountRel(
				commerceDiscount.getCommerceDiscountId(),
				CPDefinition.class.getName(), id, serviceContext);
		}
	}

	private static void _addTargetDetails(
			long groupId, CommerceDiscount commerceDiscount, String target,
			long... targetIds)
		throws Exception {

		if (CommerceDiscountConstants.TARGET_CATEGORIES.equals(target)) {
			_addDiscountCategoryRel(groupId, commerceDiscount, targetIds);
		}

		if (CommerceDiscountConstants.TARGET_PRODUCTS.equals(target)) {
			_addDiscountProductRel(groupId, commerceDiscount, targetIds);
		}
	}

}