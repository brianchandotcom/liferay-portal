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

package com.liferay.commerce.tax.engine.fixed.service.impl;

import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.service.CommerceTaxCategoryLocalService;
import com.liferay.commerce.tax.engine.fixed.exception.CommerceTaxFixedRateCommerceTaxCategoryIdException;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRateLocalServiceImpl
	extends CommerceTaxFixedRateLocalServiceBaseImpl {

	@Override
	public CommerceTaxFixedRate addCommerceTaxFixedRate(
			long commerceTaxMethodId, long commerceTaxCategoryId, double rate,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceTaxCategory commerceTaxCategory =
			_commerceTaxCategoryLocalService.getCommerceTaxCategory(
				commerceTaxCategoryId);
		User user = userLocalService.getUser(serviceContext.getUserId());

		validate(commerceTaxCategory);

		long commerceTaxFixedRateId = counterLocalService.increment();

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRatePersistence.create(commerceTaxFixedRateId);

		commerceTaxFixedRate.setGroupId(commerceTaxCategory.getGroupId());
		commerceTaxFixedRate.setCompanyId(user.getCompanyId());
		commerceTaxFixedRate.setUserId(user.getUserId());
		commerceTaxFixedRate.setUserName(user.getFullName());
		commerceTaxFixedRate.setCommerceTaxMethodId(commerceTaxMethodId);
		commerceTaxFixedRate.setCommerceTaxCategoryId(
			commerceTaxCategory.getCommerceTaxCategoryId());
		commerceTaxFixedRate.setRate(rate);

		commerceTaxFixedRatePersistence.update(commerceTaxFixedRate);

		return commerceTaxFixedRate;
	}

	@Override
	public CommerceTaxFixedRate deleteCommerceTaxFixedRate(
		CommerceTaxFixedRate commerceTaxFixedRate) {

		// Commerce tax fixed rate

		commerceTaxFixedRatePersistence.remove(commerceTaxFixedRate);

		// Commerce tax fixed rate address rels

		commerceTaxFixedRateAddressRelLocalService.
			deleteCommerceTaxFixedRateAddressRels(
				commerceTaxFixedRate.getCommerceTaxFixedRateId());

		return commerceTaxFixedRate;
	}

	@Override
	public void deleteCommerceTaxFixedRates(long commerceTaxCategoryId)
		throws PortalException {

		commerceTaxFixedRatePersistence.removeByCommerceTaxCategoryId(
			commerceTaxCategoryId);
	}

	@Override
	public void deleteCommerceTaxMethodFixedRates(long commerceTaxMethodId) {
		commerceTaxFixedRatePersistence.removeByCommerceTaxMethodId(
			commerceTaxMethodId);
	}

	@Override
	public List<CommerceTaxCategory> getAvailableCommerceTaxCategories(
			long groupId)
		throws PortalException {

		List<CommerceTaxCategory> availableCommerceTaxCategories =
			ListUtil.copy(
				_commerceTaxCategoryLocalService.getCommerceTaxCategories(
					groupId));

		long[] commerceTaxCategoryIds = ArrayUtil.toLongArray(
			commerceTaxFixedRateFinder.getCommerceTaxCategoryIds());

		for (long commerceTaxCategoryId : commerceTaxCategoryIds) {
			CommerceTaxCategory commerceTaxCategory =
				_commerceTaxCategoryLocalService.getCommerceTaxCategory(
					commerceTaxCategoryId);

			if (commerceTaxCategory != null) {
				availableCommerceTaxCategories.remove(commerceTaxCategory);
			}
		}

		return availableCommerceTaxCategories;
	}

	@Override
	public List<CommerceTaxFixedRate> getCommerceTaxFixedRates(
		long commerceTaxMethodId, int start, int end) {

		return commerceTaxFixedRatePersistence.findByCommerceTaxMethodId(
			commerceTaxMethodId, start, end);
	}

	@Override
	public List<CommerceTaxFixedRate> getCommerceTaxFixedRates(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxFixedRate> orderByComparator) {

		return commerceTaxFixedRatePersistence.findByCommerceTaxMethodId(
			commerceTaxMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxFixedRatesCount(long commerceTaxMethodId) {
		return commerceTaxFixedRatePersistence.countByCommerceTaxMethodId(
			commerceTaxMethodId);
	}

	@Override
	public CommerceTaxFixedRate updateCommerceTaxFixedRate(
			long commerceTaxFixedRateId, double rate)
		throws PortalException {

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRatePersistence.findByPrimaryKey(
				commerceTaxFixedRateId);

		commerceTaxFixedRate.setRate(rate);

		commerceTaxFixedRatePersistence.update(commerceTaxFixedRate);

		return commerceTaxFixedRate;
	}

	protected void validate(CommerceTaxCategory commerceTaxCategory)
		throws PortalException {

		List<Long> commerceTaxCategoryIds =
			commerceTaxFixedRateFinder.getCommerceTaxCategoryIds();

		if (commerceTaxCategoryIds.contains(
				commerceTaxCategory.getCommerceTaxCategoryId())) {

			throw new CommerceTaxFixedRateCommerceTaxCategoryIdException();
		}
	}

	@ServiceReference(type = CommerceTaxCategoryLocalService.class)
	private CommerceTaxCategoryLocalService _commerceTaxCategoryLocalService;

}