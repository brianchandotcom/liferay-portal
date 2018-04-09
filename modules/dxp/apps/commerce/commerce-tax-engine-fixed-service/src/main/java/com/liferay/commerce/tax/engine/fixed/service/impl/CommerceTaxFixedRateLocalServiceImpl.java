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

import com.liferay.commerce.service.CommerceTaxCategoryLocalService;
import com.liferay.commerce.tax.engine.fixed.exception.DuplicateCommerceTaxFixedRateException;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRateLocalServiceImpl
	extends CommerceTaxFixedRateLocalServiceBaseImpl {

	@Override
	public CommerceTaxFixedRate addCommerceTaxFixedRate(
			long commerceTaxMethodId, long commerceTaxCategoryId, double rate,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		validate(commerceTaxCategoryId, commerceTaxMethodId);

		long commerceTaxFixedRateId = counterLocalService.increment();

		CommerceTaxFixedRate commerceTaxFixedRate =
			commerceTaxFixedRatePersistence.create(commerceTaxFixedRateId);

		commerceTaxFixedRate.setGroupId(serviceContext.getScopeGroupId());
		commerceTaxFixedRate.setCompanyId(user.getCompanyId());
		commerceTaxFixedRate.setUserId(user.getUserId());
		commerceTaxFixedRate.setUserName(user.getFullName());
		commerceTaxFixedRate.setCommerceTaxMethodId(commerceTaxMethodId);
		commerceTaxFixedRate.setCommerceTaxCategoryId(commerceTaxCategoryId);
		commerceTaxFixedRate.setRate(rate);

		commerceTaxFixedRatePersistence.update(commerceTaxFixedRate);

		return commerceTaxFixedRate;
	}

	@Override
	public void deleteCommerceTaxFixedRateByCommerceTaxCategoryId(
		long commerceTaxCategoryId) {

		commerceTaxFixedRatePersistence.removeByCommerceTaxCategoryId(
			commerceTaxCategoryId);
	}

	@Override
	public void deleteCommerceTaxFixedRateByCommerceTaxMethodId(
		long commerceTaxMethodId) {

		commerceTaxFixedRatePersistence.removeByCommerceTaxMethodId(
			commerceTaxMethodId);
	}

	@Override
	public CommerceTaxFixedRate fetchCommerceTaxFixedRateByCTC_CTM(
			long commerceTaxCategoryId, long commerceTaxMethodId)
		throws PortalException {

		return commerceTaxFixedRatePersistence.fetchByCTC_CTM(
			commerceTaxCategoryId, commerceTaxMethodId);
	}

	@Override
	public CommerceTaxFixedRate getCommerceTaxFixedRateByCTC_CTM(
			long commerceTaxCategoryId, long commerceTaxMethodId)
		throws PortalException {

		return commerceTaxFixedRatePersistence.findByCTC_CTM(
			commerceTaxCategoryId, commerceTaxMethodId);
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

	protected void validate(
			long commerceTaxCategoryId, long commerceTaxMethodId)
		throws PortalException {

		int count = commerceTaxFixedRatePersistence.countByCTC_CTM(
			commerceTaxCategoryId, commerceTaxMethodId);

		if (count > 0) {
			throw new DuplicateCommerceTaxFixedRateException();
		}
	}

	@ServiceReference(type = CommerceTaxCategoryLocalService.class)
	private CommerceTaxCategoryLocalService _commerceTaxCategoryLocalService;

}