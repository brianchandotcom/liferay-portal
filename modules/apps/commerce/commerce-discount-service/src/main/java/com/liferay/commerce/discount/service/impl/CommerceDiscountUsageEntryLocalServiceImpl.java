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

import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountUsageEntry;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.discount.service.base.CommerceDiscountUsageEntryLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Objects;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountUsageEntryLocalServiceImpl
	extends CommerceDiscountUsageEntryLocalServiceBaseImpl {

	@Override
	public CommerceDiscountUsageEntry addCommerceDiscountUsageEntry(
			long commerceAccountId, long commerceOrderId,
			long commerceDiscountId, ServiceContext serviceContext)
		throws PortalException {

		long userId = serviceContext.getUserId();

		User user = _userLocalService.getUser(userId);

		if (user.isDefaultUser()) {
			userId = 0;
		}

		long commerceDiscountUsageEntryId = _counterLocalService.increment();

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			commerceDiscountUsageEntryPersistence.create(
				commerceDiscountUsageEntryId);

		commerceDiscountUsageEntry.setCompanyId(user.getCompanyId());
		commerceDiscountUsageEntry.setUserId(userId);
		commerceDiscountUsageEntry.setUserName(user.getFullName());
		commerceDiscountUsageEntry.setCommerceAccountId(commerceAccountId);
		commerceDiscountUsageEntry.setCommerceOrderId(commerceOrderId);
		commerceDiscountUsageEntry.setCommerceDiscountId(commerceDiscountId);

		return commerceDiscountUsageEntryPersistence.update(
			commerceDiscountUsageEntry);
	}

	@Override
	public void deleteCommerceUsageEntry(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId) {

		CommerceDiscountUsageEntry commerceDiscountUsageEntry =
			commerceDiscountUsageEntryPersistence.fetchByCAI_COI_CDI_First(
				commerceAccountId, commerceOrderId, commerceDiscountId, null);

		if (commerceDiscountUsageEntry != null) {
			commerceDiscountUsageEntryPersistence.remove(
				commerceDiscountUsageEntry);
		}
	}

	@Override
	public void deleteCommerceUsageEntryByDiscountId(long commerceDiscountId) {
		commerceDiscountUsageEntryPersistence.removeByCommerceDiscountId(
			commerceDiscountId);
	}

	@Override
	public int getCommerceDiscountUsageEntriesCount(long commerceDiscountId) {
		return commerceDiscountUsageEntryPersistence.countByCommerceDiscountId(
			commerceDiscountId);
	}

	@Override
	public int getCommerceDiscountUsageEntriesCount(
		long commerceAccountId, long commerceOrderId, long commerceDiscountId) {

		return commerceDiscountUsageEntryPersistence.countByCAI_COI_CDI(
			commerceAccountId, commerceOrderId, commerceDiscountId);
	}

	@Override
	public int getCommerceDiscountUsageEntriesCountByAccountId(
		long commerceAccountId, long commerceDiscountId) {

		return commerceDiscountUsageEntryPersistence.countByCAI_CDI(
			commerceAccountId, commerceDiscountId);
	}

	@Override
	public int getCommerceDiscountUsageEntriesCountByOrderId(
		long commerceOrderId, long commerceDiscountId) {

		return commerceDiscountUsageEntryPersistence.countByCOI_CDI(
			commerceOrderId, commerceDiscountId);
	}

	@Override
	public boolean validateDiscountLimitationUsage(
			long commerceAccountId, long commerceDiscountId)
		throws PortalException {

		CommerceDiscount commerceDiscount =
			_commerceDiscountLocalService.getCommerceDiscount(
				commerceDiscountId);

		if (Objects.equals(
				commerceDiscount.getLimitationType(),
				CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED)) {

			return true;
		}

		int limitationTimes = commerceDiscount.getLimitationTimes();

		if (Objects.equals(
				commerceDiscount.getLimitationType(),
				CommerceDiscountConstants.LIMITATION_TYPE_LIMITED)) {

			int commerceDiscountUsageEntriesCount =
				getCommerceDiscountUsageEntriesCount(commerceDiscountId);

			if (commerceDiscountUsageEntriesCount < limitationTimes) {
				return true;
			}

			return false;
		}

		int limitationTimesPerAccount =
			commerceDiscount.getLimitationTimesPerAccount();

		if (Objects.equals(
				commerceDiscount.getLimitationType(),
				CommerceDiscountConstants.
					LIMITATION_TYPE_LIMITED_FOR_ACCOUNTS)) {

			int commerceDiscountUsageEntriesCount =
				getCommerceDiscountUsageEntriesCountByAccountId(
					commerceAccountId, commerceDiscountId);

			if (commerceDiscountUsageEntriesCount < limitationTimesPerAccount) {
				return true;
			}

			return false;
		}

		int commerceDiscountUsageEntriesTotalCount =
			getCommerceDiscountUsageEntriesCount(commerceDiscountId);

		if (commerceDiscountUsageEntriesTotalCount >= limitationTimes) {
			return false;
		}

		int commerceDiscountUsageEntriesUserCount =
			getCommerceDiscountUsageEntriesCountByAccountId(
				commerceAccountId, commerceDiscountId);

		if (commerceDiscountUsageEntriesUserCount >=
				limitationTimesPerAccount) {

			return false;
		}

		return true;
	}

	@BeanReference(type = CommerceDiscountLocalService.class)
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	@ServiceReference(type = CounterLocalService.class)
	private CounterLocalService _counterLocalService;

	@ServiceReference(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}